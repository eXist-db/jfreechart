/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2011 The eXist Project
 *  http://exist-db.org
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *  $Id$
 */
package org.exist.xquery.modules.jfreechart.render;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.exist.xquery.modules.jfreechart.Configuration;
import org.jfree.chart.JFreeChart;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * SVG renderer
 *
 * @author Dannes Wessels (dannes@exist-db.org)
 */
public class SVGrenderer implements Renderer {

    @Override
    public void render(final JFreeChart chart, final Configuration config, final OutputStream os) throws IOException {

        final Rectangle bounds = new Rectangle(config.getImageWidth(), config.getImageHeight());

        final Graphics2D svgGenerator = getBatik();

        // draw the chart in the SVG generator
        chart.draw(svgGenerator, bounds);

        final Writer out = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        ((SVGGraphics2D)svgGenerator).stream(out, true /* use css */);
        os.flush();
        os.close();
    }

    private Graphics2D getBatik() {
        // Get a DOMImplementation and create an XML document
        final DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();
        final Document document = domImpl.createDocument(null, "svg", null);

        // Create an instance of the SVG Generator
        return  new SVGGraphics2D(document);
    }

    @Override
    public String getContentType() {
        return ("image/svg+xml");
    }

    @Override
    public String getContentEncoding() {
        return null;
    }

    @Override
    public byte[] render(final JFreeChart chart, final Configuration config) throws IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        render(chart, config, os);
        return os.toByteArray();
    }
}
