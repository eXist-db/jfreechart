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
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Jan-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler for reading a series for a XYZ dataset.
 */
public class XYZSeriesHandler extends DefaultHandler
        implements XYZDatasetTags {

    /** The root handler. */
    private RootHandler root;

    /** The series key. */
    private ArrayList<Comparable> seriesKeys;

    /** The values. */
    private ArrayList<ArrayList<Double>> valuesX;
    private ArrayList<ArrayList<Double>> valuesY;
    private ArrayList<ArrayList<Double>> valuesZ;

    /**
     * Creates a new item handler.
     *
     * @param root  the root handler.
     */
    public XYZSeriesHandler(RootHandler root) {
        this.root = root;
	this.seriesKeys = new ArrayList<Comparable>();
	this.valuesX = new ArrayList<ArrayList<Double>>();
	this.valuesY = new ArrayList<ArrayList<Double>>();
	this.valuesZ = new ArrayList<ArrayList<Double>>();
    }

    /**
     * Sets the series key.
     *
     * @param key  the key.
     */
    public void setSeriesKey(Comparable key) {
        this.seriesKeys.add(key);
	this.valuesX.add(new ArrayList<Double>());
	this.valuesY.add(new ArrayList<Double>());
	this.valuesZ.add(new ArrayList<Double>());
    }


    /**
     * Gets the series key.
     *
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
     * @param key  the key.
     * @param value  the value.
     */
    public void addItem(final double valueX, final double valueY, final double valueZ) {
	int series = getSeriesCount() - 1;
        this.valuesX.get(series).add(new Double(valueX));
        this.valuesY.get(series).add(new Double(valueY));
        this.valuesZ.get(series).add(new Double(valueZ));
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

        if (this.root instanceof XYZDatasetHandler) {
            XYZDatasetHandler handler = (XYZDatasetHandler) this.root;
	    int seriesCount = getSeriesCount();
	    int itemCount = this.valuesX.get(0).size();
	    for (int series = 0; series < seriesCount; series++) {
		double[] valuesX = new double[itemCount];
		double[] valuesY = new double[itemCount];
		double[] valuesZ = new double[itemCount];
		for (int key = 0; key < itemCount; key++) {
		    valuesX[key] = this.valuesX.get(series).get(key).doubleValue();
		    valuesY[key] = this.valuesY.get(series).get(key).doubleValue();
		    valuesZ[key] = this.valuesZ.get(series).get(key).doubleValue();
		    //System.out.println("XYZSeriesHandler::values: " + getSeriesKey(series) + ":" + this.valuesX.get(series).get(key) + ":" + this.valuesY.get(series).get(key) + ":" + this.valuesZ.get(series).get(key));
		}
		handler.addSeries(getSeriesKey(series), valuesX, valuesY, valuesZ);
	    }
            this.root.popSubHandler();
        }

    }
}
