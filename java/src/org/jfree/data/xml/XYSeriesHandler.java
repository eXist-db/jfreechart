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
 * --------------------------
 * XYSeriesHandler.java
 * --------------------------
 * (C) Copyright 2014
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Jan-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.xml;

import java.util.Iterator;
import java.util.ArrayList;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler for reading a series for a XY dataset.
 */
public class XYSeriesHandler extends DefaultHandler
        implements XYZDatasetTags {

    /** The root handler. */
    private RootHandler root;

    /** The series key. */
    private Comparable seriesKey;

    /** The values. */
    private XYSeries values;

    /**
     * Creates a new item handler.
     *
     * @param root  the root handler.
     */
    public XYSeriesHandler(RootHandler root) {
        this.root = root;
        this.values = new XYSeries(this.seriesKey);
    }

    /**
     * Sets the series key.
     *
     * @param key  the key.
     */
    public void setSeriesKey(Comparable key) {
        this.seriesKey = key;
    }

    /**
     * Adds an XY item to the temporary storage for the series.
     *
     * @param valueX  the X value.
     * @param valueY  the Y value.
     */
    public void addItem(double valueX, double valueY) {
        this.values.add(valueX, valueY);
    }

    /**
     * The start of an element.
     *
     * @param namespaceURI  the namespace.
     * @param localName  the element name.
     * @param qName  the element name.
     * @param atts  the attributes.
     *
     * @throws SAXException for errors.
     */
    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes atts) throws SAXException {

        if (qName.equals(SERIES_TAG)) {
            setSeriesKey(atts.getValue("name"));
            XYZItemHandler subhandler = new XYZItemHandler(this.root, this);
            this.root.pushSubHandler(subhandler);
        }
        else if (qName.equals(ITEM_TAG)) {
            XYZItemHandler subhandler = new XYZItemHandler(this.root, this);
            this.root.pushSubHandler(subhandler);
            subhandler.startElement(namespaceURI, localName, qName, atts);
        }

        else {
            throw new SAXException(
                "Expecting <Series> or <Item> tag...found " + qName
            );
        }
    }

    /**
     * The end of an element.
     *
     * @param namespaceURI  the namespace.
     * @param localName  the element name.
     * @param qName  the element name.
     */
    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) {

        if (this.root instanceof XYDatasetHandler) {
            XYDatasetHandler handler = (XYDatasetHandler) this.root;
	    handler.addSeries(this.seriesKey, values.toArray()[0], values.toArray()[1]);
            this.root.popSubHandler();
        }

    }

}
