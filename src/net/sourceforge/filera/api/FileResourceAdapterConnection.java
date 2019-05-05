/*
 *	Copyright (C) 2005 Markus KARG (markus-karg@users.sourceforge.net)
 * Copyright (C) 2005 Hans PRÜLLER (hanzz@users.sourceforge.net)
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

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Defines the interface of connections to FileResourceAdapter. Can be used to
 * access file resources hosted on local or remote file systems. To identify
 * files, canonical names (path names) are used. The actual syntax of those
 * names are dependent of the host OS. For maximum compatibility, Java-styled
 * path names (forward slashes) should be used.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 * @author Hans PRÜLLER (hanzz@users.sourceforge.net)
 */
public interface FileResourceAdapterConnection {

    /**
     * Creates an <code>InputStream</code> that allows reading from the file
     * specified by <code>pathName</code>.
     * 
     * @param pathName
     *                   The path name that identifies the file to read. Syntax and
     *                   location is relative to the host OS.
     * @return An <code>InputStream</code> attached to the file specified by
     *              <code>pathName</code>.
     */
    public InputStream getInputStream(String pathName) throws FileResourceAdapterException;

    /**
     * Creates an <code>OutputStream</code> that allows writing to the file
     * specified by <code>pathName</code>.
     * 
     * @param pathName
     *                   The path name that identifies the file to write. Syntax and
     *                   location is relative to the host OS.
     * @return An <code>OutputStream</code> attached to the file specified by
     *              <code>pathName</code>.
     */
    public OutputStream getOutputStream(String pathName) throws FileResourceAdapterException;

    /**
     * Immediately removes the file identified by <code>pathName</code>. If
     * this method gets applied to directories, this will result in an exception
     * when the directory is not empty.
     * 
     * @param pathName
     *                   identifies the file to be removed.
     * @throws FileResourceAdapterException
     *                   when the file cannot be removed.
     */
    public void removeFile(String pathName) throws FileResourceAdapterException;

    /**
     * Creates an empty file identified by <code>pathName</code> and returns
     * its canonical name.
     * 
     * @param pathName
     *                   identifies the file to be created.
     * @return The canonical name of the new file.
     * @throws FileResourceAdapterException
     *                   when the file cannot be created.
     */
    public String createFile(String pathName) throws FileResourceAdapterException;

    /**
     * Creates a temporary file in the default temporary directory of the host
     * OS. The canonical name of the file will be returned. The file will be
     * deleted automatically once the VM terminates. Since this might be in long
     * future, it is highly adviseable to remove the file by
     * <code>removeFile()</code> once it is not longer in use. <br>
     * TODO Make the directory configurable to the administrator.
     * 
     * @see removeFile()
     * @return The canonical path name of the temporary file.
     * @throws FileResourceAdapterException
     *                   when creation of the temporary file failed.
     */
    public String createTemporaryFile() throws FileResourceAdapterException;

    /**
     * Immediately copies the file identified by <code>fromPathName</code> to
     * <code>toPathName</code>.
     * 
     * @param fromPathName
     *                   identifies the file to be copied.
     * @param toPathName
     *                   is the new name.
     * @throws FileResourceAdapterException
     *                   when the file could not be copied.
     */
    public void copyFile(String fromPathName, String toPathName) throws FileResourceAdapterException;

    /**
     * Immediately moves or renames the file identified by
     * <code>fromPathName</code> to <code>toPathName</code>. A rename is
     * nothing else than a move with identical parent directories.
     * 
     * @param fromPathName
     *                   identifies the file to be moved or renamed.
     * @param toPathName
     *                   is the new name.
     * @throws FileResourceAdapterException
     *                   when the file could not be moved or renamed.
     */
    public void moveFile(String fromPathName, String toPathName) throws FileResourceAdapterException;

    /**
     * Checks for the existence of the file identified by <code>pathName</code>.
     * 
     * @param pathName
     *                   The file which shall be checked for existence.
     * @return true when the file exists, false otherwise.
     * @throws FileResourceAdapterException
     *                   when the existence could not be checked.
     */
    public boolean existsFile(String pathName) throws FileResourceAdapterException;

}