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

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

import net.sourceforge.filera.spi.FileResourceAdapter;
import net.sourceforge.rafc.api.AbstractConnectionFactory;

/**
 * Connection factory of the file resource adapter.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class DefaultFileResourceAdapterConnectionFactory extends AbstractConnectionFactory implements FileResourceAdapterConnectionFactory {

    /**
     * Create a connection factory that is associated to a provided connection
     * manager and managed connection factory.
     * 
     * @param connectionManager
     *                   the connection manager to use.
     * @param managedConnectionFactory
     *                   the managed connection factory to which this connection
     *                   factory is associated.
     */
    public DefaultFileResourceAdapterConnectionFactory(final ConnectionManager connectionManager, final ManagedConnectionFactory managedConnectionFactory) {
        super(connectionManager, managedConnectionFactory);
    }

    /**
     * Returns a connection to the FileResourceAdapter. Uses the connection
     * manager and managed connection factory to allocate a connection.
     * 
     * @return a connection to the FileResourceAdapter.
     * @see FileResourceAdapter#getConnection()
     * @throws FileResourceAdapterException
     *                   when no connection could get obtained
     * @see FileResourceAdapterConnectionFactory#getConnection()
     */
    public final FileResourceAdapterConnection getConnection() throws FileResourceAdapterException {

        try {

            /*
             * Creates a connection handle to a 'physical' connection.
             */
            final ConnectionManager connectionManager = this.getConnectionManager();
            final ManagedConnectionFactory managedConnectionFactory = this.getManagedConnectionFactory();
            final FileResourceAdapterConnection connection = (FileResourceAdapterConnection) connectionManager.allocateConnection(managedConnectionFactory,
                    null);

            return connection;

        } catch (final ResourceException e) {
            throw new FileResourceAdapterException(e);
        }
    }

}