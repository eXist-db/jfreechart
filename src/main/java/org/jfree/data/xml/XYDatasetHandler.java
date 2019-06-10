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
 * ---------------------------
 * XYDatasetHandler.java
 * ---------------------------
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

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX handler for reading a {@link XYDataset} from an XML file.
 */
public class XYDatasetHandler extends RootHandler implements XYZDatasetTags {

    /**
     * The dataset under construction.
     */
    private XYSeriesCollection dataset;

    /**
     * Creates a new handler.
     */
    public XYDatasetHandler() {
        this.dataset = null;
    }

    /**
     * Returns the dataset.
     *
     * @return The dataset.
     */
    public XYDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets a dataset.
     *
     * @param series the dataset.
     */
    public void setDataset(final XYSeriesCollection series) {
        this.dataset = series;
    }

    /**
     * The start of an element.
     *
     * @param namespaceURI the namespace.
     * @param localName    the element name.
     * @param qName        the element name.
     * @param atts         the element attributes.
     * @throws SAXException for errors.
     */
    @Override
    public void startElement(final String namespaceURI,
                             final String localName,
                             final String qName,
                             final Attributes atts) throws SAXException {
        final DefaultHandler current = getCurrentHandler();
        if (current != this) {
            current.startElement(namespaceURI, localName, qName, atts);
        } else if (qName.equals(XYDATASET_TAG)) {
            this.dataset = new XYSeriesCollection();
        } else if (qName.equals(XYZDatasetTags.SERIES_TAG)) {
            final XYSeriesHandler subhandler = new XYSeriesHandler(this);
            getSubHandlers().push(subhandler);
            subhandler.startElement(namespaceURI, localName, qName, atts);
        } else {
            throw new SAXException("Element not recognised: " + qName);
        }

    }

    /**
     * The end of an element.
     *
     * @param namespaceURI the namespace.
     * @param localName    the element name.
     * @param qName        the element name.
     * @throws SAXException for errors.
     */
    @Override
    public void endElement(final String namespaceURI,
                           final String localName,
                           final String qName) throws SAXException {
        final DefaultHandler current = getCurrentHandler();
        if (current != this) {
            current.endElement(namespaceURI, localName, qName);
        }

    }

}
