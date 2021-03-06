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

package org.headsupdev.agile.app.docs;

import org.headsupdev.agile.api.AbstractTask;
import org.headsupdev.agile.api.Project;

/**
 * A task object representing a doxygen build
 *
 * @author Andrew Williams
 * @since 2.0
 */
public class APIDocBuildTask extends AbstractTask
{
    public APIDocBuildTask(Project project)
    {
        super( "Building API documentation", "Building the API documentation for the project", project );
    }
}
