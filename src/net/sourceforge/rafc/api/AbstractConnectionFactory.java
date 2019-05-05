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

package net.sourceforge.rafc.api;

import java.io.Serializable;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.Referenceable;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

/**
 * Abstract implementation of a connection factory.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public abstract class AbstractConnectionFactory implements Serializable, Referenceable {

    /**
     * The connection manager used by this connection factory.
     */
    private final ConnectionManager connectionManager;

    /**
     * The managed connection factory to which this connection factory is
     * associated.
     */
    private final ManagedConnectionFactory managedConnectionFactory;

    /**
     * Creates an instance of this connection factory that is associated to the
     * provided connection manager.
     * 
     * @param connectionManager
     *                   The connection manager to which this connection factory is
     *                   associated.
     * @param managedConnectionFactory
     *                   The managed connection factory to which this factory is
     *                   connectio associated.
     */
    public AbstractConnectionFactory(final ConnectionManager connectionManager, final ManagedConnectionFactory managedConnectionFactory) {
        this.connectionManager = connectionManager;
        this.managedConnectionFactory = managedConnectionFactory;
    }

    /**
     * Allows descendents to access the associated connection manager.
     * 
     * @return the associated connection manager.
     */
    protected final ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    /**
     * Allows descendents to access the associated managed connection factory.
     * 
     * @return the associated managed connection factory.
     */
    protected final ManagedConnectionFactory getManagedConnectionFactory() {
        return this.managedConnectionFactory;
    }

    /**
     * Stores the provided reference.
     */
    private Reference reference = null;

    /**
     * Set the reference.
     */
    public final void setReference(final Reference reference) {
        this.reference = reference;
    }

    /**
     * Get the stored reference.
     */
    public final Reference getReference() throws NamingException {
        return this.reference;
    }

}