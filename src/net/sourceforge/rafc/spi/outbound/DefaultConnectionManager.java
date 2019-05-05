/*
 *	Copyright (C) 2005 Markus KARG (markus-karg@users.sourceforge.net)
 * 
 *	This file is part of RA Foundation Classes.
 *
 *	RA Foundation Classes is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	RA Foundation Classes is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with RA Foundation Classes; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.sourceforge.rafc.spi.outbound;

import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

/**
 * Default implemention of the <code>javax.resource.spi.ConnectionManager</code>
 * interface. <br>
 * According to JCA specification, a resource adapter has to come with its own,
 * internal implementation. <br>
 * This default implementation always creates a new connection. Adapter
 * implementations should come with their own connection manager instead to
 * provide more sophisticated behaviour.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public final class DefaultConnectionManager implements ConnectionManager, Serializable {

    /**
     * @return a connection that is always associated to a new created managed
     *              connection.
     */
    public final Object allocateConnection(final ManagedConnectionFactory managedConnectionFactory, final ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {

        /*
         * Create a new managed (physical) connection with every call.
         */
        final ManagedConnection managedConnection = managedConnectionFactory.createManagedConnection(null, connectionRequestInfo);

        /*
         * Create a new connection handle to the managed (physical) connection.
         * According to JCA connection handles should not get shared.
         */
        final Object connection = managedConnection.getConnection(null, connectionRequestInfo);

        return connection;
    }

}