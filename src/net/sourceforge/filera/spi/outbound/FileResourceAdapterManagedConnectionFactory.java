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
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.security.auth.Subject;

import net.sourceforge.filera.api.DefaultFileResourceAdapterConnectionFactory;
import net.sourceforge.rafc.spi.outbound.AbstractManagedConnectionFactory;

/*
 * TODO Check for non-Serialization in favour of XML persistence.
 */

/**
 * Implementation of the
 * <code>javax.resource.spi.ManagedConnectionFactory</code> interface specific
 * to the file resource adapter. <br>
 * Provides a factory for the creation of physical connections to the file
 * system.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class FileResourceAdapterManagedConnectionFactory extends AbstractManagedConnectionFactory {

    /**
     * @return an instance of a connection factory, specific to the file
     *              resource adapter.
     * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory(javax.resource.spi.ConnectionManager)
     */
    public final Object createConnectionFactory(final ConnectionManager connectionManager) throws ResourceException {

        /*
         * Working around a JOnAS bug: JOnAS supplies null as connectionManager!
         * TODO Remove this workaround as soon as JOnAS has fixed this bug!
         * JOnAS 4.3.4+ has this bug fixed, but is not released yet. Normally
         * this should work like: We are using the provided connection manager
         * specific to the caller, since we are in a managed environment.
         */
        final ConnectionManager usedConnectionManager = connectionManager == null ? this.getInternalConnectionManager() : connectionManager;

        /*
         * Alyways create a new connection factory instance, specific to the
         * file resource adapter. We are using the provided connection manager.
         */
        final Object connectionFactory = new DefaultFileResourceAdapterConnectionFactory(usedConnectionManager, this);

        return connectionFactory;
    }

    /**
     * @return an instance of a connection factory, specific to the file
     *              resource adapter.
     * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory()
     */
    public final Object createConnectionFactory() throws ResourceException {

        /*
         * We are using the internal connection manager specific to the file
         * resource adapter since we are in an unmanaged environment.
         */
        final ConnectionManager connectionManager = this.getInternalConnectionManager();

        /*
         * Alyways create a new connection factory instance, specific to the
         * file resource adapter.
         */
        final Object connectionFactory = new DefaultFileResourceAdapterConnectionFactory(connectionManager, this);

        return connectionFactory;
    }

    /**
     * @return a managed connection specific to the file resource adapter.
     * @see javax.resource.spi.ManagedConnectionFactory#createManagedConnection(javax.security.auth.Subject,
     *          javax.resource.spi.ConnectionRequestInfo)
     */
    public final ManagedConnection createManagedConnection(final Subject subject, final ConnectionRequestInfo connectionRequestInfo) throws ResourceException {

        /*
         * Alyways create a new managed connection instance, specific to the
         * file resource adapter.
         */
        final ManagedConnection managedConnection = new FileResourceAdapterManagedConnection(this.getLogWriter());

        return managedConnection;
    }

    /**
     * Provides an instance of the internal implementation of a connection
     * manager specific to the file resource adapter. Implements the singleton
     * pattern.
     * 
     * @see #getInternalConnectionManager()
     */
    private ConnectionManager internalConnectionManager = null;

    /**
     * @return a shared instance of the connection manager specific to the file
     *              resource adapter.
     */
    protected final ConnectionManager getInternalConnectionManager() {

        /*
         * Uses the singleton pattern to create an instance with the first call,
         * and provides that shared instance for any following call.
         */
        if (this.internalConnectionManager == null) this.internalConnectionManager = new FileResourceAdapterConnectionManager();

        return this.internalConnectionManager;
    }
}