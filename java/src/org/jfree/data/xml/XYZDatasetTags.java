/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ----------------
 * XYZDatasetTags.java
 * ----------------
 * (C) Copyright 2014
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Leif-Jöran Olsson;
 *
 * Changes
 * -------
 * 27-Apr-2014 : Version 1 (ljo);
 *
 */

package org.jfree.data.xml;

/**
 * Constants for the tags that identify the elements in the XML files.
 */
public interface XYZDatasetTags {

    /** The 'PieDataset' element name. */
    public static final String PIEDATASET_TAG = "PieDataset";

    /** The 'CategoryDataset' element name. */
    public static final String CATEGORYDATASET_TAG = "CategoryDataset";

    /** The 'XYDataset' element name. */
    public static final String XYDATASET_TAG = "XYDataset";

    /** The 'XYZDataset' element name. */
    public static final String XYZDATASET_TAG = "XYZDataset";

    /** The 'Series' element name. */
    public static final String SERIES_TAG = "Series";

    /** The 'Item' element name. */
    public static final String ITEM_TAG = "Item";

    /** The 'Key' element name. */
    public static final String KEY_TAG = "Key";

    /** The 'Value' element name. */
    public static final String VALUE_TAG = "Value";

    /** The 'X' element name. */
    public static final String X_VALUE_TAG = "X";

    /** The 'Y' element name. */
    public static final String Y_VALUE_TAG = "Y";

    /** The 'Z' element name. */
    public static final String Z_VALUE_TAG = "Z";

}
