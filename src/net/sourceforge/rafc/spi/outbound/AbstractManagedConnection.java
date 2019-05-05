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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.transaction.xa.XAResource;

/**
 * Abstract implementation of a managed connection.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public abstract class AbstractManagedConnection implements ManagedConnection {

    public void destroy() throws ResourceException {
        // Intentionally left blank.
    }

    public void cleanup() throws ResourceException {
        // Intentionally left blank.
    }

    public void associateConnection(final Object connection) throws ResourceException {
        // Intentionally left blank.
    }

    private Collection listeners = new LinkedList();

    public final void addConnectionEventListener(final ConnectionEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeConnectionEventListener(final ConnectionEventListener listener) {
        this.listeners.remove(listener);
    }

    protected final void fireConnectionEvent(final ConnectionEvent event) {
        switch (event.getId()) {
        case ConnectionEvent.CONNECTION_CLOSED:
            this.fireConnectionClosedEvent(event);
            break;
        case ConnectionEvent.CONNECTION_ERROR_OCCURRED:
            this.fireConnectionErrorOccuredEvent(event);
            break;
        case ConnectionEvent.LOCAL_TRANSACTION_COMMITTED:
            this.fireLocalTransactionCommittedEvent(event);
            break;
        case ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK:
            this.fireLocalTransactionRolledbackEvent(event);
            break;
        case ConnectionEvent.LOCAL_TRANSACTION_STARTED:
            this.fireLocalTransactionStartedEvent(event);
            break;
        }
    }

    private final void fireConnectionClosedEvent(final ConnectionEvent event) {
        for (final Iterator i = this.listeners.iterator(); i.hasNext();) {
            final ConnectionEventListener listener = (ConnectionEventListener) i.next();
            listener.connectionClosed(event);
        }
    }

    private final void fireConnectionErrorOccuredEvent(final ConnectionEvent event) {
        for (final Iterator i = this.listeners.iterator(); i.hasNext();) {
            final ConnectionEventListener listener = (ConnectionEventListener) i.next();
            listener.connectionErrorOccurred(event);
        }
    }

    private final void fireLocalTransactionCommittedEvent(final ConnectionEvent event) {
        for (final Iterator i = this.listeners.iterator(); i.hasNext();) {
            final ConnectionEventListener listener = (ConnectionEventListener) i.next();
            listener.localTransactionCommitted(event);
        }
    }

    private final void fireLocalTransactionRolledbackEvent(final ConnectionEvent event) {
        for (final Iterator i = this.listeners.iterator(); i.hasNext();) {
            final ConnectionEventListener listener = (ConnectionEventListener) i.next();
            listener.localTransactionRolledback(event);
        }
    }

    private final void fireLocalTransactionStartedEvent(final ConnectionEvent event) {
        for (final Iterator i = this.listeners.iterator(); i.hasNext();) {
            final ConnectionEventListener listener = (ConnectionEventListener) i.next();
            listener.localTransactionStarted(event);
        }
    }

    /**
     * By default, XA transactions are not supported, which is indicated by
     * throwing <code>NotSupportedException</code> according to JCA
     * specification.
     * 
     * @throws NotSupportedException
     *                   always.
     */
    public XAResource getXAResource() throws ResourceException {
        throw new NotSupportedException();
    }

    /**
     * By default, local transactions are not supported, which is indicated by
     * throwing <code>NotSupportedException</code> according to JCA
     * specification.
     * 
     * @throws NotSupportedException
     *                   always.
     */
    public LocalTransaction getLocalTransaction() throws ResourceException {
        throw new NotSupportedException();
    }

    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        // TODO we have to associate with the current user!
        return new AbstractManagedConnectionMetaData() {
            // Intentionally left blank.
        };
    }

    private PrintWriter logWriter = null;

    /**
     * According to JCA 1.5 specification, a managed connection has to receive a
     * reference to the managed connection factory's log writer initially.
     * 
     * @param logWriter
     */
    protected AbstractManagedConnection(final PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public final void setLogWriter(final PrintWriter logWriter) throws ResourceException {
        this.logWriter = logWriter;
    }

    public final PrintWriter getLogWriter() throws ResourceException {
        return this.logWriter;
    }

    /**
     * To enforce the rule printed in JCA 1.5 specification that a managed
     * connection should never override the default implementation of
     * <code>equals()</code> based on identity, this method is marked as
     * <code>final</code> by intention.
     */
    public final boolean equals(final Object that) {
        return super.equals(that);
    }

}