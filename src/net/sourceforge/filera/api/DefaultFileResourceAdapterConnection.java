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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sourceforge.filera.spi.outbound.FileResourceAdapterManagedConnection;

/**
 * Default implementation of <code>FileResourceAdapterConnectio</code>
 * utilizing <code>java.io.File API</code> to access the underlying host OS.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 * @author Hans PRÜLLER (hanzz@users.sourceforge.net)
 */
public final class DefaultFileResourceAdapterConnection implements FileResourceAdapterConnection {

    /**
     * The physical connection that does the actual work for this logical
     * connection.
     */
    private FileResourceAdapterManagedConnection managedConnection = null;

    /**
     * Creates an instance of this logical connection by provision of a the
     * physical connection which serves as delegate for this facade.
     * 
     * @param managedConnection
     *                   the physical connection that does the actual work.
     */
    public DefaultFileResourceAdapterConnection(final FileResourceAdapterManagedConnection managedConnection) {

        /*
         * Store for later use.
         */
        this.managedConnection = managedConnection;
    }

    /**
     * Since we are support connection sharing, we need to support later change
     * of the physical connection.
     * 
     * @param managedConnection
     *                   the newly associated physical connection.
     */
    public final void setManagedConnection(final FileResourceAdapterManagedConnection managedConnection) {

        /*
         * Store for later use.
         */
        this.managedConnection = managedConnection;
    }

    public final InputStream getInputStream(final String pathName) throws FileResourceAdapterException {

        try {

            /*
             * Delegate to physical connection.
             */
            final File file = this.managedConnection.getFile(pathName);
            final InputStream inputStream = this.managedConnection.getInputStream(file);

            return inputStream;

        } catch (final FileNotFoundException e) {
            throw new FileResourceAdapterException(e);
        }
    }

    public final OutputStream getOutputStream(final String pathName) throws FileResourceAdapterException {

        try {

            /*
             * Delegate to physical connection.
             */
            final File file = this.managedConnection.getFile(pathName);
            final OutputStream outputStream = this.managedConnection.getOutputStream(file);

            return outputStream;

        } catch (final FileNotFoundException e) {
            throw new FileResourceAdapterException(e);
        }
    }

    public final void removeFile(final String pathName) throws FileResourceAdapterException {

        /*
         * Delegate to physical connection.
         */
        final File file = this.managedConnection.getFile(pathName);
        final boolean fileIsRemoved = this.managedConnection.removeFile(file);
        if (!fileIsRemoved) throw new FileResourceAdapterException();
    }

    public final String createFile(final String pathName) throws FileResourceAdapterException {

        try {

            /*
             * Delegate to physical connection.
             */
            final File file = this.managedConnection.getFile(pathName);
            final String newPathName = this.managedConnection.createFile(file);

            return newPathName;

        } catch (final IOException e) {
            throw new FileResourceAdapterException(e);
        }
    }

    public final String createTemporaryFile() throws FileResourceAdapterException {

        try {

            /*
             * Delegate to physical connection.
             */
            final File file = this.managedConnection.createTemporaryFile();
            final String pathName = this.managedConnection.getPathName(file);

            return pathName;

        } catch (final IOException e) {
            throw new FileResourceAdapterException(e);
        }
    }

    public final void copyFile(final String fromPathName, final String toPathName) throws FileResourceAdapterException {

        try {

            /*
             * Delegate to physical connection.
             */
            final File sourceFile = this.managedConnection.getFile(fromPathName);
            final File targetFile = this.managedConnection.getFile(toPathName);
            this.managedConnection.copyFile(sourceFile, targetFile);
        } catch (final IOException e) {
            throw new FileResourceAdapterException(e);
        }
    }

    public final void moveFile(final String fromPathName, final String toPathName) throws FileResourceAdapterException {

        try {

            /*
             * Delegate to physical connection.
             */
            final File sourceFile = this.managedConnection.getFile(fromPathName);
            final File targetFile = this.managedConnection.getFile(toPathName);
            this.managedConnection.moveFile(sourceFile, targetFile);
        } catch (final IOException e) {
            throw new FileResourceAdapterException(e);
        }
    }

    public final boolean existsFile(final String pathName) throws FileResourceAdapterException {

        /*
         * Delegate to physical connection.
         */
        final File file = this.managedConnection.getFile(pathName);
        final boolean fileExists = this.managedConnection.existsFile(file);

        return fileExists;
    }

}