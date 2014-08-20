/*
 * HeadsUp Agile
 * Copyright 2014 Heads Up Development Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.headsupdev.agile.app.docs;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValueConversionException;
import org.headsupdev.agile.api.Event;
import org.headsupdev.agile.api.Permission;
import org.headsupdev.agile.app.docs.event.UpdateDocumentEvent;
import org.headsupdev.agile.app.docs.permission.DocEditPermission;
import org.headsupdev.agile.storage.Comment;
import org.headsupdev.agile.storage.HibernateStorage;
import org.headsupdev.agile.storage.docs.Document;
import org.headsupdev.agile.web.BookmarkableMenuLink;
import org.headsupdev.agile.web.HeadsUpPage;
import org.headsupdev.agile.web.MountPoint;

import java.util.Date;


/**
 * Created by Gordon Edwards on 08/07/2014.
 */


@MountPoint("editComment")
public class EditComment
        extends HeadsUpPage

{
    private String submitLabel = "Edit Comment";

    private long commentId;

    private Document doc;

    public Permission getRequiredPermission()
    {
        return new DocEditPermission();
    }

    @Override
    public void layout()
    {
        super.layout();
        add( CSSPackageResource.getHeaderContribution( getClass(), "doc.css" ) );

        String page = getPageParameters().getString( "page" );
        if ( page == null || page.length() == 0 )
        {
            page = "Welcome";
        }

        try
        {
            commentId = getPageParameters().getInt( "commentId" );
        }
        catch ( NumberFormatException e )
        {
            notFoundError();
            return;
        }
        catch ( StringValueConversionException e )
        {
            notFoundError();
            return;
        }

        Document doc = DocsApplication.getDocument( page, getProject() );
        if ( doc == null )
        {

            notFoundError();
            return;
        }

        this.doc = doc;

        add( new CommentForm( "comment" ) );
        addLink( new BookmarkableMenuLink( getPageClass( "docs/" ), getPageParameters(), "view" ) );

    }

    @Override
    public String getTitle()
    {
        return "Edit Comment";
    }

    protected void layoutChild( Form form )
    {
    }

    protected void submitChild( Comment comment )
    {
    }

    protected Event getUpdateEvent( Comment comment )
    {
        return new UpdateDocumentEvent( doc, getSession().getUser(), comment, "commented on" );
    }

    protected boolean willChildConsumeComment()
    {
        return false;
    }

    class CommentForm
            extends Form<Comment>
    {
        private TextArea input;
        private Comment editedComment = new Comment();

        public CommentForm( String id )
        {
            super( id );
            for ( Comment comment : doc.getComments() )
            {
                if ( comment.getId() == commentId )
                {
                    editedComment = comment;
                    break;
                }
            }

            setModel( new CompoundPropertyModel<Comment>( editedComment ) );
            input = new TextArea( "comment" );
            add( input );
            layoutChild( this );

            add( new Button( "submit", new Model<String>()
            {
                public String getObject()
                {
                    return submitLabel;
                }
            } ) );
        }

        public void onSubmit()
        {
            doc = (Document) ( (HibernateStorage) getStorage() ).getHibernateSession().merge( doc );
            editedComment = (Comment) ( (HibernateStorage) getStorage() ).getHibernateSession().merge( editedComment );
            Date now = new Date();
            if ( editedComment.getComment() != null )
            {
                editedComment.setUser( EditComment.this.getSession().getUser() );
                editedComment.setComment( input.getInput() );
            }

            submitChild( editedComment );

            // this line is needed by things that extend our form...
            doc.setUpdated( now );
            getHeadsUpApplication().addEvent( getUpdateEvent( editedComment ) );

            setResponsePage( getPageClass( "docs/" ), getPageParameters() );
        }
    }
}