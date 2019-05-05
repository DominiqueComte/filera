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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.resource.spi.SharingViolationException;
import javax.security.auth.Subject;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import net.sourceforge.filera.api.DefaultFileResourceAdapterConnection;
import net.sourceforge.filera.api.FileResourceAdapterException;
import net.sourceforge.rafc.spi.outbound.AbstractManagedConnection;

/**
 * Managed connection of FileResourceAdapter.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class FileResourceAdapterManagedConnection extends AbstractManagedConnection {

    public FileResourceAdapterManagedConnection(final PrintWriter logWriter) {

        super(logWriter);
    }

    public final ManagedConnectionMetaData getMetaData() throws ResourceException {

        /*
         * Create an instance of the meta data specific to this managed
         * connection. TODO Share a single instance with all calls.
         */
        final ManagedConnectionMetaData managedConnectionMetaData = new FileResourceAdapterManagedConnectionMetaData();

        return managedConnectionMetaData;
    }

    public final Object getConnection(final Subject subject, final ConnectionRequestInfo cxRequestInfo) throws ResourceException {

        /*
         * Create a new logical connection with each call. JCA specification
         * disallows sharing of connections.
         */
        final Object connection = new DefaultFileResourceAdapterConnection(this);

        return connection;
    }

    public final void associateConnection(final Object connection) throws ResourceException {

        /*
         * Safety check to prevent of nuts application servers...
         */
        if (!(connection instanceof DefaultFileResourceAdapterConnection)) throw new SharingViolationException();

        final DefaultFileResourceAdapterConnection connectionHandle = (DefaultFileResourceAdapterConnection) connection;
        connectionHandle.setManagedConnection(this);
    }

    public final InputStream getInputStream(final File file) throws FileNotFoundException {

        /*
         * Create an input stream for the provided file.
         */
        final InputStream inputStream = new FileInputStream(file);

        return inputStream;
    }

    public final OutputStream getOutputStream(final File file) throws FileNotFoundException {

        /*
         * Create an output stream for the provided file.
         */
        this.createParentPathIfNeeded(file);
        final OutputStream outputStream = new FileOutputStream(file);

        return outputStream;
    }

    public final boolean removeFile(final File file) {

        /*
         * Remove the provided file.
         */
        final boolean fileIsRemoved = file.delete();

        return fileIsRemoved;
    }

    public final String createFile(final File file) throws IOException {

        /*
         * Create the provided file.
         */
        this.createParentPathIfNeeded(file);
        file.createNewFile();
        final String canonicalName = file.getCanonicalPath();

        return canonicalName;
    }

    /**
     * Default prefix for temporary files. <br>
     * TODO Should be configurable by administrator.
     */
    private static final String TEMPORARY_FILE_PREFIX = "filera";

    public final File createTemporaryFile() throws IOException {

        /*
         * Create a temporary file using a standard prefix and postfix in the
         * default temporary directory of the host OS.
         */
        final File file = File.createTempFile(FileResourceAdapterManagedConnection.TEMPORARY_FILE_PREFIX, null, null);
        file.deleteOnExit();

        return file;
    }

    /**
     * Internal helper method which copies a file.
     * 
     * @param fromFile
     *                   The file to copy.
     * @param toFile
     *                   The target file.
     * @throws IOException
     *                   If the copy could not be performed.
     */
    public final void copyFile(final File fromFile, final File toFile) throws IOException {

        /*
         * The most performant way of copying a complete file in Java is to
         * delegate the complete job to the VM, which in turn will delegate the
         * complete job to the host OS.
         * 
         * TODO We should check for completeness: If not all bytes where copied,
         * we need to loop.
         */

        this.createParentPathIfNeeded(toFile);
        final FileChannel sourceChannel = new FileInputStream(fromFile).getChannel();
        final FileChannel targetChannel = new FileOutputStream(toFile).getChannel();
        final long sourceFileSize = sourceChannel.size();
        sourceChannel.transferTo(0, sourceFileSize, targetChannel);
    }

    public final void moveFile(final File fromFile, final File toFile) throws IOException {

        /*
         * The most performant way of moving a file in Java is the rename
         * command, since in some OS this will be delegated to the OS as the
         * non-copying command "rename file in directory tree" which actually
         * just moves a link in the directory. Unfortunately this doesn't work
         * on all OSs or it is dependent on special constraints. So we try this
         * first, and if it fails (actually it fails really fast, luckily!) we
         * are performing the much more costly way of doing a real copy and
         * deleting the source afterwards. To save as much time as possible, we
         * are using the NIO transferTo() command.
         */

        // Since exceptions are slow, we are not using the renameFile()
        // method!
        this.createParentPathIfNeeded(toFile);
        final boolean renamedSuccessfully = fromFile.renameTo(toFile);
        if (renamedSuccessfully) return;

        this.copyFile(fromFile, toFile);
        this.removeFile(fromFile);
    }

    public final boolean existsFile(final File file) throws FileResourceAdapterException {

        /*
         * Checks for the existance of the file.
         */
        final boolean fileExists = file.exists();

        return fileExists;
    }

    /**
     * Internal helper method which ensures that the parent path of the path
     * identified by <code>pathName</code> is existing. If necessary, it
     * creates all superordinate directories.
     * 
     * @param file
     *                   The path whose parent directories will be created if needed.
     */
    private final void createParentPathIfNeeded(final File file) {

        /*
         * Create parent directories if it is not existing.
         */
        final File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) parentFile.mkdirs();
    }

    public final File getFile(final String pathName) {

        /*
         * Create a file handle out of a path name respecting the environment of
         * the physical connection.
         */
        final File file = new File(pathName);

        return file;
    }

    public final String getPathName(final File file) throws IOException {

        /*
         * Create a path name out of a file handle respecting the environment of
         * the physical connection.
         */
        final String pathName = file.getCanonicalPath();

        return pathName;
    }

    private final XAResource xaResource = new XAResource() {

        private int transactionTimeout = Integer.MAX_VALUE;

        public final int getTransactionTimeout() throws XAException {
            return this.transactionTimeout;
        }

        public final boolean setTransactionTimeout(final int seconds) throws XAException {
            if (seconds == 0)
                this.transactionTimeout = Integer.MAX_VALUE;
            else
                this.transactionTimeout = seconds;

            return true;
        }

        public final boolean isSameRM(final XAResource xares) throws XAException {
            return this == xares;
        }

        private final Xid[] XIDS = new Xid[] {};

        public final Xid[] recover(final int flag) throws XAException {
            return this.XIDS;
        }

        public final int prepare(final Xid xid) throws XAException {
            return XAResource.XA_OK;
        }

        public final void forget(final Xid xid) throws XAException {
            // Intentionally left blank.
        }

        public final void rollback(final Xid xid) throws XAException {
            // Intentionally left blank.
        }

        public final void end(final Xid xid, final int flags) throws XAException {
            // Intentionally left blank.
        }

        public final void start(final Xid xid, final int flags) throws XAException {
            // Intentionally left blank.
        }

        public final void commit(final Xid xid, final boolean onePhase) throws XAException {
            // Intentionally left blank.
        }

    };

    /*
     * JOnAS Workaround: JOnAS calls getXAResource() even for NoTransaction
     * flagged RAs then stumbles over the thrown NotSupportedException...
     */
    public XAResource getXAResource() throws ResourceException {
        return this.xaResource;
    }

}