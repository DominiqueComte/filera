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

import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;

/**
 * Abstract implementation of a resource adapter association.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public abstract class AbstractResourceAdapterAssociation implements ResourceAdapterAssociation {

    /**
     * The associated resource adapter.
     */
    private ResourceAdapter resourceAdapter = null;

    /**
     * @return the associated resource adapter. According to the JCA
     *              specification, this will never return null once the instance was
     *              created.
     */
    public final ResourceAdapter getResourceAdapter() {
        return this.resourceAdapter;
    }

    /**
     * Stores the resource adapter.
     */
    public final void setResourceAdapter(final ResourceAdapter resourceAdapter) throws ResourceException {
        this.resourceAdapter = resourceAdapter;
    }

}