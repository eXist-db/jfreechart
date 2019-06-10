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
 * -----------------
 * XYZValueHandler.java
 * -----------------
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
 * A handler for reading a 'XYZValue' or 'XYValue' element.
 */
public class XYZValueHandler extends DefaultHandler implements XYZDatasetTags {

    /**
     * The root handler.
     */
    private final RootHandler rootHandler;

    /**
     * The item handler.
     */
    private final XYZItemHandler itemHandler;

    /**
     * Storage for the current CDATA
     */
    private final StringBuffer currentText;

    /**
     * Creates a new XYZ value handler.
     *
     * @param rootHandler the root handler.
     * @param itemHandler the item handler.
     */
    public XYZValueHandler(final RootHandler rootHandler, final XYZItemHandler itemHandler) {
        this.rootHandler = rootHandler;
        this.itemHandler = itemHandler;
        this.currentText = new StringBuffer();
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
            // no attributes to read
            clearCurrentText();
        } else {
            throw new SAXException("Expecting <Item>, <X>, <Y>, or <Z> but found " + qName);
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
        Number value;
        try {
            value = Double.valueOf(getCurrentText());
            if (((Double) value).isNaN()) {
                value = null;
            }
        } catch (final NumberFormatException e1) {
            value = null;
        }
        if ((qName.equals(Z_VALUE_TAG) && rootHandler instanceof XYZDatasetHandler)
                || (qName.equals(Y_VALUE_TAG) && rootHandler instanceof XYDatasetHandler)) {
            if (qName.equals(Z_VALUE_TAG)) {
                this.itemHandler.setZValue(value);
            } else if (qName.equals(Y_VALUE_TAG)) {
                this.itemHandler.setYValue(value);
            }
            //this.rootHandler.popSubHandler();
            //this.rootHandler.pushSubHandler(
            //    new XYZValueHandler(this.rootHandler, this.itemHandler)
            //);
        } else if (qName.equals(X_VALUE_TAG) || (qName.equals(Y_VALUE_TAG) && rootHandler instanceof XYZDatasetHandler)) {
            if (qName.equals(X_VALUE_TAG)) {
                this.itemHandler.setXValue(value);
            } else if (qName.equals(Y_VALUE_TAG)) {
                this.itemHandler.setYValue(value);
            }
            this.rootHandler.popSubHandler();
        } else if (qName.equals(ITEM_TAG)) {
            this.itemHandler.addSeriesItem();
            this.rootHandler.popSubHandler();
        } else {
            throw new SAXException("Expecting </Item>, </X>, </Y>, or </Z> but found " + qName);
        }

    }

    /**
     * Receives some (or all) of the text in the current element.
     *
     * @param ch     character buffer.
     * @param start  the start index.
     * @param length the length of the valid character data.
     */
    @Override
    public void characters(final char[] ch, final int start, final int length) {
        if (this.currentText != null) {
            this.currentText.append(String.copyValueOf(ch, start, length));
        }
    }

    /**
     * Returns the current text of the textbuffer.
     *
     * @return The current text.
     */
    protected String getCurrentText() {
        return this.currentText.toString();
    }

    /**
     * Removes all text from the textbuffer at the end of a CDATA section.
     */
    protected void clearCurrentText() {
        this.currentText.delete(0, this.currentText.length());
    }

}
