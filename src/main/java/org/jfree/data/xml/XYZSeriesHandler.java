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
 * XYZSeriesHandler.java
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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * A handler for reading a series for a XYZ dataset.
 */
public class XYZSeriesHandler extends DefaultHandler implements XYZDatasetTags {

    /**
     * The root handler.
     */
    private final RootHandler root;

    /**
     * The series key.
     */
    private final ArrayList<Comparable> seriesKeys;

    /**
     * The values.
     */
    private final ArrayList<ArrayList<Double>> valuesX;
    private final ArrayList<ArrayList<Double>> valuesY;
    private final ArrayList<ArrayList<Double>> valuesZ;

    /**
     * Creates a new item handler.
     *
     * @param root the root handler.
     */
    public XYZSeriesHandler(final RootHandler root) {
        this.root = root;
        this.seriesKeys = new ArrayList<>();
        this.valuesX = new ArrayList<>();
        this.valuesY = new ArrayList<>();
        this.valuesZ = new ArrayList<>();
    }

    /**
     * Sets the series key.
     *
     * @param key the key.
     */
    public void setSeriesKey(final Comparable key) {
        this.seriesKeys.add(key);
        this.valuesX.add(new ArrayList<>());
        this.valuesY.add(new ArrayList<>());
        this.valuesZ.add(new ArrayList<>());
    }


    /**
     * Gets the series key.
     */
    public Comparable getSeriesKey(final int i) {
        if (i > getSeriesCount()) {
            return "n/a";
        } else {
            return this.seriesKeys.get(i);
        }
    }

    public int getSeriesCount() {
        return this.seriesKeys.size();
    }

    /**
     * Adds an item to the temporary storage for the series.
     *
     * @param valueX the X value.
     * @param valueY the Y value.
     * @param valueZ the Z value.
     */
    public void addItem(final double valueX, final double valueY, final double valueZ) {
        final int series = getSeriesCount() - 1;
        this.valuesX.get(series).add(valueX);
        this.valuesY.get(series).add(valueY);
        this.valuesZ.get(series).add(valueZ);
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

        if (this.root instanceof XYZDatasetHandler) {
            final XYZDatasetHandler handler = (XYZDatasetHandler) this.root;
            final int seriesCount = getSeriesCount();
            final int itemCount = this.valuesX.get(0).size();
            for (int series = 0; series < seriesCount; series++) {
                final double[] valuesX = new double[itemCount];
                final double[] valuesY = new double[itemCount];
                final double[] valuesZ = new double[itemCount];
                for (int key = 0; key < itemCount; key++) {
                    valuesX[key] = this.valuesX.get(series).get(key);
                    valuesY[key] = this.valuesY.get(series).get(key);
                    valuesZ[key] = this.valuesZ.get(series).get(key);
                    //System.out.println("XYZSeriesHandler::values: " + getSeriesKey(series) + ":" + this.valuesX.get(series).get(key) + ":" + this.valuesY.get(series).get(key) + ":" + this.valuesZ.get(series).get(key));
                }
                handler.addSeries(getSeriesKey(series), valuesX, valuesY, valuesZ);
            }
            this.root.popSubHandler();
        }

    }
}
