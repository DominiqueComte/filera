/*
 *	Copyright (C) 2005 Markus KARG (markus-karg@users.sourceforge.net)
 * 
 *	This file is part of FileResourceAdapter.
 *
 *	FileResourceAdapter is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	FileResourceAdapter is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with FileResourceAdapter; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.sourceforge.filera.spi.outbound;

import javax.resource.ResourceException;

import net.sourceforge.rafc.spi.outbound.AbstractManagedConnectionMetaData;

/**
 * Meta data of a managed connection of the file resource adapter.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class FileResourceAdapterManagedConnectionMetaData extends AbstractManagedConnectionMetaData {

    /**
     * Our EIS product is the Local File System. <br>
     * Unfortunately Java doesn't know a method on querying its name or version
     * number.
     * 
     * @see javax.resource.spi.ManagedConnectionMetaData#getEISProductName()
     */
    public final String getEISProductName() throws ResourceException {
        return "Local File System";
    }

    /**
     * The security principal accessing the local file system is taken from the
     * Java VM's environment.
     */
    public final String getUserName() throws ResourceException {

        /*
         * Query the user's name from the Java VM environment.
         */
        final String userName = System.getProperty("user.name");

        return userName;
    }

}