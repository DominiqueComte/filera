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

package net.sourceforge.filera.api;

/**
 * Indicates an exception that happened when using the FileResourceAdapter.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class FileResourceAdapterException extends Exception {

    /**
     * Creates a new instance.
     *  
     */
    public FileResourceAdapterException() {
        super();
    }

    /**
     * Creates a new instance by provision of the actual cause of the exception.
     * 
     * @param cause
     *                   The actual problem.
     */
    public FileResourceAdapterException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates an instance of this exception by passing the error message as
     * simple string.
     *  
     */
    public FileResourceAdapterException(final String message) {
        super(message);
    }

}