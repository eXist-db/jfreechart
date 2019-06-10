/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2014-2015 The eXist-db Project
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
 */
package org.exist.xquery.modules.jfreechart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exist.xquery.XPathException;
import org.jfree.chart.plot.DefaultDrawingSupplier;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A DrawingSupplier for (Multiple)PiePlots which prepends any supplied colors
 * to the default ones.
 *
 * @author Leif-Jöran Olsson (ljo@exist-db.org)
 */
public class PiePlotDrawingSupplier extends DefaultDrawingSupplier {

    private final static Logger logger = LogManager.getLogger(PiePlotDrawingSupplier.class);
    private final Paint[] paints;
    private int index;

    PiePlotDrawingSupplier() {
        paints = DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE;
    }

    PiePlotDrawingSupplier(final String sectionColors) {
        final List<Paint> list = new ArrayList<>();
        list.addAll(getPaintList(sectionColors));
        list.addAll(Arrays.asList(DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE));
        final Paint[] newPaints = list.toArray(new Paint[0]);
        this.paints = newPaints;
    }

    @Override
    public Paint getNextPaint() {
        final Paint paint = paints[index % paints.length];
        index++;
        return paint;
    }

    public List<Paint> getPaintList(final String colors) {
        final List<Paint> paints = new ArrayList<>();
        final StringTokenizer st = new StringTokenizer(colors, ",");
        while (st.hasMoreTokens()) {
            final String colorName = st.nextToken().trim();

            try {
                final Color color = Colour.getColor(colorName);
                paints.add(color);

            } catch (final XPathException e) {
                logger.warn(String.format("Invalid colour name or hex value specified for sectionColors: %s, default colour will be used instead.", colorName));
            }

        }
        return paints;
    }
}
