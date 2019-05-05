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

import java.io.PrintWriter;
import java.util.Set;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

import net.sourceforge.rafc.spi.AbstractResourceAdapterAssociation;


/**
 * Abstract implementation of the
 * <code>javax.resource.spi.ManagedConnectionFactory</code> interface. <br>
 * Provides the following features: <br>
 * <ul>
 * <li>Empty implementation of each method of the interface according to JCA
 * specification.</li>
 * <li>Singleton pattern for the internal default implementation of the
 * connection manager according to JCA specification.</li>
 * <li>Implements the management of the log writer according to JCA
 * specification by keeping the reference to the provided instance internally
 * for convenience.</li>
 * <li>Implements <code>equals()</code> and <code>hashCode()</code>
 * according to JCA specification by an at least discriminative default
 * algorithm: all instances are equal and have the same hash code.</li>
 * </ul>
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public abstract class AbstractManagedConnectionFactory extends AbstractResourceAdapterAssociation implements ManagedConnectionFactory {

    /**
     * Provides an internal default implementation of a connection manager
     * according to JCA specification. Implements the singleton pattern.
     * 
     * @see #getInternalConnectionManager()
     */
    private ConnectionManager internalConnectionManager = null;

    /**
     * Provides access to the default implementation of the connection manager
     * to descendent classes (according to JCA specification). Descendents need
     * to overwrite this method to provide their own specific connection manager
     * implementation.
     * 
     * @return the singleton instance of the default connection manager
     */
    protected ConnectionManager getInternalConnectionManager() {

        /*
         * Implements the singleton pattern: Creates an instance with the first
         * call, provides the same reference with any further call.
         */
        if (this.internalConnectionManager == null) this.internalConnectionManager = new DefaultConnectionManager();

        return this.internalConnectionManager;
    }

    /**
     * Creates an empty instance of this bean as wanted by the JCA
     * specification. To prevent descendents from hiding the default
     * constructor, this empty implementation is provided as a hint to
     * implementors.
     */
    public AbstractManagedConnectionFactory() {
        // Intentionally left blank.
    }

    /**
     * This default implementation always throws
     * <code>javax.resource.NotSupportedException</code> to indicate that
     * connection matching is not supported by this resource adapter.
     * 
     * @throws NotSupportedException
     *                   always to indicate that connection matching is not supported
     *                   by this default implementation.
     */
    public ManagedConnection matchManagedConnections(final Set set, final Subject subject, final ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        throw new NotSupportedException();
    }

    /**
     * The associated logging facility will be kept internally to allow later
     * usage of it.
     */
    private PrintWriter logWriter = null;

    /**
     * Stores the logging facility for later use.
     * 
     * @see #logWriter
     * @see #getLogWriter()
     */
    public final void setLogWriter(final PrintWriter logWriter) throws ResourceException {
        this.logWriter = logWriter;
    }

    /**
     * @return the associated logging facility, or null.
     * @see #logWriter
     * @see #setLogWriter(PrintWriter)
     */
    public final PrintWriter getLogWriter() throws ResourceException {
        return this.logWriter;
    }

    /**
     * According to JCA specification, this class has to come with its own
     * implementation of <code>equals()</code>.<br>
     * This default implementation is useful only if decendents do not have
     * additional data field. If a decendent has at least one own data field, it
     * has to provide its own equals implementation that takes care of that data
     * field.
     */
    public boolean equals(final Object that) {

        /*
         * All instances are equal by default.
         */
        return true;
    }

    /**
     * According to JCA specification, this class has to come with its own
     * implementation of <code>hashCode()</code>.<br>
     * This default implementation is useful only if decendents do not have
     * additional data field. If a decendent has at least one own data field, it
     * has to provide its own equals implementation that takes care of that data
     * field.
     */
    public int hashCode() {

        /*
         * All instances have the same hash code by default, since they are
         * equal.
         */
        return 0;
    }

}