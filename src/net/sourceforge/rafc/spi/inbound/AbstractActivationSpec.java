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

package net.sourceforge.rafc.spi.inbound;

import java.io.Serializable;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;

import net.sourceforge.rafc.spi.AbstractResourceAdapterAssociation;

/**
 * Abstract implementation of an activation spec.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public class AbstractActivationSpec extends AbstractResourceAdapterAssociation implements ActivationSpec, Serializable {

    /**
     * Creates an empty instance of this bean as wanted by the JCA
     * specification. To prevent descendents to hide the default constructor,
     * this empty implementation is provided.
     */
    public AbstractActivationSpec() {
        // Intentionally left blank.
    }

    /**
     * Actually does nothing yet.
     */
    public void validate() throws InvalidPropertyException {
        // TODO Implement this.
    }

}