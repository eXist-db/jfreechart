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
 * Contributor(s):   Leif-Jöran Olsson;
 *
 * Changes
 * -------
 * 27-Apr-2014 : Version 1 (ljo);
 *
 */

package org.jfree.data.xml;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler for reading a series for a XY dataset.
 */
public class XYSeriesHandler extends DefaultHandler
        implements XYZDatasetTags {

    /**
     * The root handler.
     */
    private final RootHandler root;
    /**
     * The values.
     */
    private final XYSeriesCollection values;
    /**
     * The series key.
     */
    private Comparable seriesKey;

    /**
     * Creates a new item handler.
     *
     * @param root the root handler.
     */
    public XYSeriesHandler(final RootHandler root) {
        this.root = root;
        this.values = new XYSeriesCollection();
    }

    /**
     * Sets the series key.
     *
     * @param key the key.
     */
    public void setSeriesKey(final Comparable key) {
        this.seriesKey = key;
        this.values.addSeries(new XYSeries(this.seriesKey));
    }

    public int getSeriesCount() {
        return this.values.getSeriesCount();
    }

    /**
     * Adds an XY item to the temporary storage for the series.
     *
     * @param valueX the X value.
     * @param valueY the Y value.
     */
    public void addItem(final double valueX, final double valueY) {
        final int series = getSeriesCount() - 1;
        this.values.getSeries(series).add(valueX, valueY);
    }

    /**
     * Adds an XY item with Y empty (null) to the temporary storage for the series.
     *
     * @param valueX the X value.
     */
    public void addItem(final double valueX) {
        final int series = getSeriesCount() - 1;
        this.values.getSeries(series).add(valueX, null);
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
        switch (qName) {
            case SERIES_TAG: {
                setSeriesKey(atts.getValue("name"));
                final XYZItemHandler subhandler = new XYZItemHandler(this.root, this);
                this.root.pushSubHandler(subhandler);
                break;
            }
            case ITEM_TAG: {
                final XYZItemHandler subhandler = new XYZItemHandler(this.root, this);
                this.root.pushSubHandler(subhandler);
                subhandler.startElement(namespaceURI, localName, qName, atts);
                break;
            }
            default:
                throw new SAXException(
                        "Expecting <Series> or <Item> tag...found " + qName
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
        if (this.root instanceof XYDatasetHandler) {
            final XYDatasetHandler handler = (XYDatasetHandler) this.root;
            handler.setDataset(values);
            this.root.popSubHandler();
        }

    }

}
