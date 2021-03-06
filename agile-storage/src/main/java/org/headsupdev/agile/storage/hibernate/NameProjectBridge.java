/*
 * HeadsUp Agile
 * Copyright 2009-2012 Heads Up Development.
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

package org.headsupdev.agile.storage.hibernate;

import org.hibernate.search.bridge.TwoWayStringBridge;
import org.headsupdev.agile.api.Manager;

/**
 * TODO Document me!
 *
 * @author Andrew Williams
 * @version $Id$
 * @since 1.0
 */
public class NameProjectBridge
    implements TwoWayStringBridge
{
    public String objectToString( Object o )
    {
        if ( !( o instanceof NameProjectId ) )
        {
            return null;
        }

        NameProjectId id = (NameProjectId) o;
        return id.getName() + "#" + id.getProject().getId();
    }

    public Object stringToObject( String s )
    {
        if ( s == null )
        {
            return null;
        }

        int pos = s.indexOf( '#' );
        String name = s.substring( 0, pos );
        String project = s.substring( pos + 1 );
        return new NameProjectId( name, Manager.getStorageInstance().getProject( project ) );
    }
}
