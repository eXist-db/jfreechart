/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2013,
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
 * ------------------
 * XYDatasetReader.java
 * ------------------
 * (C) Copyright 2014,
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Nov-2002 : Version 1 (DG);
 *
 */

package org.jfree.data.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.xml.sax.SAXException;

/**
 * A utility class for reading datasets from XML.
 */
public class XYDatasetReader {

    /**
     * Reads a {@link XYDataset} from an XML file.
     *
     * @param file  the file.
     *
     * @return A dataset.
     *
     * @throws IOException if there is a problem reading the file.
     */
    public static XYDataset readXYDatasetFromXML(File file)
        throws IOException {
        InputStream in = new FileInputStream(file);
        return readXYDatasetFromXML(in);
    }

    /**
     * Reads a {@link XYDataset} from a stream.
     *
     * @param in  the input stream.
     *
     * @return A dataset.
     *
     * @throws IOException if there is an I/O error.
     */
    public static XYDataset readXYDatasetFromXML(InputStream in)
        throws IOException {

        XYDataset result = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XYDatasetHandler handler = new XYDatasetHandler();
            parser.parse(in, handler);
            result = handler.getDataset();
        }
        catch (SAXException e) {
            System.out.println(e.getMessage());
        }
        catch (ParserConfigurationException e2) {
            System.out.println(e2.getMessage());
        }
        return result;

    }

    /**
     * Reads a {@link XYZDataset} from a file.
     *
     * @param file  the file.
     *
     * @return A dataset.
     *
     * @throws IOException if there is a problem reading the file.
     */
    public static XYZDataset readXYZDatasetFromXML(File file)
        throws IOException {
        InputStream in = new FileInputStream(file);
        return readXYZDatasetFromXML(in);
    }

    /**
     * Reads a {@link XYZDataset} from a stream.
     *
     * @param in  the stream.
     *
     * @return A dataset.
     *
     * @throws IOException if there is a problem reading the file.
     */
    public static XYZDataset readXYZDatasetFromXML(InputStream in)
        throws IOException {

        XYZDataset result = null;

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XYZDatasetHandler handler = new XYZDatasetHandler();
            parser.parse(in, handler);
            result = handler.getDataset();
        }
        catch (SAXException e) {
            System.out.println(e.getMessage());
        }
        catch (ParserConfigurationException e2) {
            System.out.println(e2.getMessage());
        }
        return result;

    }
}
