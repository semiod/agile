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

package org.headsupdev.agile.app.admin;

import org.headsupdev.agile.api.Permission;

import java.util.Comparator;

/**
 * TODO document me
 *
 * @author Andrew Williams
 * @since 1.0
 */
public class PermissionIdComparator
    implements Comparator<Permission>
{
    public int compare( Permission p1, Permission p2 )
    {
        return p1.getId().compareTo( p2.getId() );
    }
}
