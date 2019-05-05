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

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionMetaData;

/**
 * Abstract implementation of connection meta data.
 * 
 * @author Markus KARG (markus-karg@users.sourceforge.net)
 */
public abstract class AbstractManagedConnectionMetaData implements ManagedConnectionMetaData {

    /**
     * Unless otherwise specified by a descendent, this method returns an empty
     * string.
     */
    public String getEISProductName() throws ResourceException {
        return "";
    }

    /**
     * Unless otherwise specified by a descendent, this method returns an empty
     * string.
     */
    public String getEISProductVersion() throws ResourceException {
        return "";
    }

    /**
     * We don't know about the limit, so we have to return 0 as wanted by JCA
     * 1.5 specification.
     */
    public int getMaxConnections() throws ResourceException {
        return 0;
    }

    /**
     * Unless otherwise specified by a descendent, this method returns an empty
     * string.
     */
    public String getUserName() throws ResourceException {
        return "";
    }

}