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

package net.sourceforge.rafc.spi;

import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

/**
 * Abstract implementation of a resource adapter.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public abstract class AbstractResourceAdapter implements ResourceAdapter, Serializable {

    /**
     * Creates an empty instance of this bean as wanted by the JCA
     * specification. To prevent descendents to hide the default constructor,
     * this empty implementation is provided.
     */
    public AbstractResourceAdapter() {
        // Intentionally left blank.
    }

    /**
     * As wanted by the JCA specification, by default, two instances are equal
     * unless differently implemented by a descendent basing on a differential
     * setting of its properties.
     * 
     * @return true always
     */
    public boolean equals(final Object that) {
        return true;
    }

    /**
     * As wanted by the JCA specification, by default, two instances are equal
     * and such have the same, static hash code, unless differently implemented
     * by a descendent basing on a differential setting of its properties.
     * 
     * @return 0 always
     */
    public int hashCode() {
        return 0;
    }

    /**
     * Stores the bootstrap context for later use by descendants.
     * 
     * @see getBootstrapContext()
     * @see start()
     */
    private BootstrapContext bootstrapContext = null;

    /**
     * Stores the bootstrap context for later use by descendants.
     * 
     * @see bootstrapContext
     * @see getBootstrapContext()
     */
    public void start(final BootstrapContext bootstrapContext) throws ResourceAdapterInternalException {
        this.bootstrapContext = bootstrapContext;
    }

    public void stop() {
        // Intentionally left blank.
    }

    /**
     * This default implementation just does nothing.
     */
    public void endpointActivation(final MessageEndpointFactory messageEndpointFactory, final ActivationSpec activationSpec) throws ResourceException {
        // Intentionally left blank.
    }

    /**
     * This default implementation just does nothing.
     */
    public void endpointDeactivation(final MessageEndpointFactory messageEndpointFacotry, final ActivationSpec activationSpec) {
        // Intentionally left blank.
    }

    /**
     * This default implementation just returns null.
     * 
     * @return null always
     */
    public XAResource[] getXAResources(final ActivationSpec[] activationSpecs) throws ResourceException {
        return null;
    }

    /**
     * Allows descendants to access the bootstrap context at any time, so this
     * is a means to access several server facilities at runtime.
     * 
     * @return The bootstrap context as provided to this adapter at start.
     * @see start()
     */
    protected final BootstrapContext getBootstrapContext() {
        return this.bootstrapContext;
    }

}