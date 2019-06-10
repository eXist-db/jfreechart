/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2014,
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
 * XYZItemHandler.java
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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler for reading XYZ items.
 */
public class XYZItemHandler extends DefaultHandler implements XYZDatasetTags {

    /**
     * The root handler.
     */
    private final RootHandler root;

    /**
     * The parent handler (can be the same as root, but not always).
     */
    private final DefaultHandler parent;

    /**
     * The valueX.
     */
    private Number valueX;

    /**
     * The valueY.
     */
    private Number valueY;

    /**
     * The valueZ.
     */
    private Number valueZ;

    /**
     * Creates a new item handler.
     *
     * @param root   the root handler.
     * @param parent the parent handler.
     */
    public XYZItemHandler(final RootHandler root, final DefaultHandler parent) {
        this.root = root;
        this.parent = parent;
        this.valueX = null;
        this.valueY = null;
        this.valueZ = null;
    }

    /**
     * Returns the X value that has been read by the handler, or <code>null</code>.
     *
     * @return The valueX.
     */
    public Number getXValue() {
        return this.valueX;
    }

    public void setXValue(final Number valueX) {
        this.valueX = valueX;
    }

    public Number getYValue() {
        return this.valueY;
    }

    public void setYValue(final Number valueY) {
        this.valueY = valueY;
    }

    public Number getZValue() {
        return this.valueZ;
    }

    public void setZValue(final Number valueZ) {
        this.valueZ = valueZ;
    }

    public void addSeriesItem() {
        if (this.parent instanceof XYSeriesHandler) {
            final XYSeriesHandler handler = (XYSeriesHandler) this.parent;
            if (this.valueY != null) {
                handler.addItem((double) this.valueX, (double) this.valueY);
            } else {
                handler.addItem((double) this.valueX);
            }
            //System.out.println("addSeriesItem: X: " + this.valueX + " Y: " + this.valueY);
        } else if (this.parent instanceof XYZSeriesHandler) {
            final XYZSeriesHandler handler = (XYZSeriesHandler) this.parent;
            handler.addItem((double) this.valueX, (double) this.valueY, (double) this.valueZ);
            //System.out.println("addSeriesItem: X: " + this.valueX + " Y: " + this.valueY + " Z: " + this.valueZ);
        }
    }

    /**
     * The start of an element.
     *
     * @param namespaceURI the namespace.
     * @param localName    the element name.
     * @param qName        the element name.
     * @param atts         the attributes.
     * @throws SAXException for errors.
     */
    @Override
    public void startElement(final String namespaceURI,
                             final String localName,
                             final String qName,
                             final Attributes atts) throws SAXException {
        if (qName.equals(ITEM_TAG) || qName.equals(X_VALUE_TAG) || qName.equals(Y_VALUE_TAG) || qName.equals(Z_VALUE_TAG)) {
            final XYZValueHandler subhandler = new XYZValueHandler(this.root, this);
            this.root.pushSubHandler(subhandler);
        } else {
            throw new SAXException(
                    "Expected <Item>, <X>, <Y>, or <Z>...found " + qName
            );
        }

    }

    /**
     * The end of an element.
     *
     * @param namespaceURI the namespace.
     * @param localName    the element name.
     * @param qName        the element name.
     */
    @Override
    public void endElement(final String namespaceURI,
                           final String localName,
                           final String qName) {
        //if (this.parent instanceof XYDatasetHandler) {
        //    XYDatasetHandler handler = (XYDatasetHandler) this.parent;
        //    handler.addItem(this.valueX, this.valueY);
        //    this.root.popSubHandler();
        //}
        //else if (this.parent instanceof XYZDatasetHandler) {
        //    XYZDatasetHandler handler = (XYZDatasetHandler) this.parent;
        //    handler.addItem(this.valueX, this.valueY, this.valueZ);
        //    this.root.popSubHandler();
        //}
        //else
        if (this.parent instanceof XYSeriesHandler || this.parent instanceof XYZSeriesHandler) {
            addSeriesItem();
            this.root.popSubHandler();
        }
    }
}
