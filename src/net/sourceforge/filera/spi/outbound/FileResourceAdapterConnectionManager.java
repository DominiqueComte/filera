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

import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

/**
 * Internal implemention of the
 * <code>javax.resource.spi.ConnectionManager</code> interface, specific to
 * the file resource adapter. <br>
 * Always uses the same managed (physical) connection for all client handles (i.
 * e., implements physical connection multiplexing as allowed by JCP
 * specification). We actually don't care for the managed connection factory
 * instance or connection request info and really shared only one single managed
 * connection amon all clients.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class FileResourceAdapterConnectionManager implements ConnectionManager, Serializable {

    /**
     * The multiplexed managed connection connection that gets shared among all
     * connection handles. <br>
     * Implements singleton pattern.
     */
    private ManagedConnection multiplexedManagedConnection = null;

    /**
     * Internal helper method to get the shared managed connection. <br>
     * Implements singleton pattern.
     * 
     * @return the multiplexed managed connection.
     */
    private final ManagedConnection getMultiplexedManagedConnection(final ManagedConnectionFactory managedConnectionFactory,
            final ConnectionRequestInfo connectionRequestInfo) throws ResourceException {

        /*
         * Implements singleton pattern: Creates a new instance with the first
         * call, uses this reference with any further call.
         */
        if (this.multiplexedManagedConnection == null)
                this.multiplexedManagedConnection = managedConnectionFactory.createManagedConnection(null, connectionRequestInfo);

        return this.multiplexedManagedConnection;
    }

    /**
     * Allocates a connection specific to the file resource adapter.
     * 
     * @see javax.resource.spi.ConnectionManager#allocateConnection(javax.resource.spi.ManagedConnectionFactory,
     *          javax.resource.spi.ConnectionRequestInfo)
     */
    public final Object allocateConnection(final ManagedConnectionFactory managedConnectionFactory, final ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {

        /*
         * We are using a shared (multiplexed) managed ('physical') connection.
         */
        final ManagedConnection managedConnection = this.getMultiplexedManagedConnection(managedConnectionFactory, connectionRequestInfo);

        /*
         * Obtain a connection handle.
         */
        final Object connection = managedConnection.getConnection(null, connectionRequestInfo);

        return connection;
    }

}