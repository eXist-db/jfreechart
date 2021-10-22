/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2009-2015 The eXist-db Project
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
import org.exist.dom.QName;
import org.exist.http.servlets.ResponseWrapper;
import org.exist.storage.serializers.Serializer;
import org.exist.validation.internal.node.NodeInputStream;
import org.exist.xquery.*;
import org.exist.xquery.functions.response.StrictResponseFunction;
import org.exist.xquery.modules.jfreechart.render.Renderer;
import org.exist.xquery.modules.jfreechart.render.RendererFactory;
import org.exist.xquery.value.*;
import org.jfree.chart.JFreeChart;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * JFreechart extension functions.
 *
 * @author Dannes Wessels (dizzzz@exist-db.org)
 * @author Andrzej Taramina (andrzej@chaeron.com)
 * @author Leif-Jöran Olsson (ljo@exist-db.org)
 */
public class JFreeCharting extends StrictResponseFunction {

    protected static final Logger LOGGER = LogManager.getLogger(JFreeCharting.class);

    private static final String function1Txt =
            "Render chart using JFreechart. Check documentation on " +
                    "http://www.jfree.org/jfreechart/ for details about chart types, " +
                    "parameters and data structures.";

    private static final String function2Txt = function1Txt +
            " Output is directly streamed into the servlet output stream.";

    // Note these enumerations are also in JFreeChartFactory ... /ljo
    private static final String chartText = "The type of chart to render.  Supported chart types: " +
            "AreaChart BarChart BarChart3D " +
            "LineChart LineChart3D " +
            "MultiplePieChart MultiplePieChart3D PieChart PieChart3D " +
            "RingChart SpiderWebChart StackedAreaChart StackedBarChart " +
            "StackedBarChart3D WaterfallChart. " +
            "XYDataset: ScatterPlot XYAreaChart XYBarChart XYLineChart. " +
            "XYZDataset: BubbleChart. ";
    // see wiki? /ljo
    private static final String parametersText = "The configuration for the chart.  The " +
            "configuration should be supplied as follows: <configuration>" +
            "<param1>Value1</param1><param2>Value2</param2>/<configuration>.  " +
            "Supported parameters: width height title categoryAxisLabel timeAxisLabel " +
            "valueAxisLabel domainAxisLabel rangeAxisLabel pieSectionLabel pieSectionNumberFormat pieSectionPercentFormat orientation " +
            "titleColor chartBackgroundColor plotBackgroundColor rangeLowerBound rangeUpperBound categoryItemLabelGeneratorClass seriesColors sectionColors sectionColorsDelimiter " +
            "categoryAxisColor valueAxisColor timeAxisColor " +
            "order legend tooltips urls " +
            "domainLowerMargin domainUpperMargin foregroundAlpha dotHeight dotWidth " +
            "useDomainSymbolAxis useDomainNumberAxis domainGridbandsVisible useRangeSymbolAxis rangeGridbandsVisible lineWidth " +
            "outlineVisible pieSectionOutlineVisible domainGridlinesVisible rangeGridlinesVisible lineWidth " +
            "onlyShape rangeAutoRangeIncludesZero domainAutoRangeIncludesZero " +
            "rangeZeroBaselineVisible domainZeroBaselineVisible rangeIntegerTickUnits domainIntegerTickUnits ";

    // barWidth useYInterval (no effect with current datasets) /ljo

    public final static FunctionSignature[] signatures = {

            new FunctionSignature(
                    new QName("render", JFreeChartModule.NAMESPACE_URI, JFreeChartModule.PREFIX),
                    function1Txt,
                    new SequenceType[]{
                            new FunctionParameterSequenceType("chart-type", Type.STRING, Cardinality.EXACTLY_ONE, chartText),
                            new FunctionParameterSequenceType("configuration", Type.NODE, Cardinality.EXACTLY_ONE, parametersText),
                            new FunctionParameterSequenceType("data", Type.NODE, Cardinality.EXACTLY_ONE,
                                    "The CategoryDataset or PieDataset, supplied as JFreechart XML.")
                    },
                    new FunctionReturnSequenceType(Type.BASE64_BINARY, Cardinality.ZERO_OR_ONE, "the generated PNG image file")
            ),

            new FunctionSignature(
                    new QName("stream-render", JFreeChartModule.NAMESPACE_URI, JFreeChartModule.PREFIX),
                    function2Txt,
                    new SequenceType[]{
                            new FunctionParameterSequenceType("chart-type", Type.STRING, Cardinality.EXACTLY_ONE, chartText),
                            new FunctionParameterSequenceType("configuration", Type.NODE, Cardinality.EXACTLY_ONE, parametersText),
                            new FunctionParameterSequenceType("data", Type.NODE, Cardinality.EXACTLY_ONE,
                                    "The CategoryDataset or PieDataset, supplied as JFreechart XML.")
                    },
                    new SequenceType(Type.EMPTY, Cardinality.EMPTY_SEQUENCE)
            )
    };

    public JFreeCharting(final XQueryContext context, final FunctionSignature signature) {
        super(context, signature);
    }

    @Override
    public Sequence eval(final Sequence[] args, @Nonnull final ResponseWrapper response) throws XPathException {

        //was an image and a mime-type specified
        if (args[1].isEmpty() || args[2].isEmpty()) {
            return Sequence.EMPTY_SEQUENCE;
        }

        try {
            // Get chart type
            final String chartType = args[0].getStringValue();

            // Get configuration
            final Configuration config = new Configuration();
            config.parse(((NodeValue) args[1].itemAt(0)).getNode());

            // Get datastream
            @SuppressWarnings("resource")
            final Serializer serializer = context.getBroker().getSerializer();

            final NodeValue node = (NodeValue) args[2].itemAt(0);
            final InputStream is = new NodeInputStream(context.getDatabase(), serializer, node);

            // get chart
            final JFreeChart chart;
            try {
                chart = JFreeChartFactory.createJFreeChart(chartType, config, is);

            } catch (final IllegalArgumentException ex) {
                throw new XPathException(this, ex.getMessage());
            }

            // Verify if chart is present
            if (chart == null) {
                throw new XPathException(this, "Unable to create chart '" + chartType + "'");
            }

            final Renderer renderer = RendererFactory.getRenderer(config.getImageType());

            // Render output
            if (isCalledAs("render")) {
                final byte[] image = renderer.render(chart, config);
                return BinaryValueFromInputStream.getInstance(context, new Base64BinaryValueType(), new ByteArrayInputStream(image));

            } else {

                if (!"org.exist.http.servlets.HttpResponseWrapper".equals(response.getClass().getName())) {
                    throw (new XPathException(this, ErrorCodes.XPDY0002, signatures[1] + " can only be used within the EXistServlet or XQueryServlet"));
                }

                writeToResponseWrapper(config, response, chart, renderer);
            }


        } catch (final XPathException ex) {
            LOG.error(ex);
            throw ex;

        } catch (final IOException ex) {
            LOG.error(ex);
            throw new XPathException(this, ex.getMessage());
        }

        return Sequence.EMPTY_SEQUENCE;
    }


    /**
     * Writes chart to response wrapper as PNG image.
     *
     * @throws XPathException Thrown when an IO exception is thrown,
     */
    private void writeToResponseWrapper(final Configuration config, final ResponseWrapper response, final JFreeChart chart, final Renderer renderer)
            throws XPathException {

        response.setContentType(renderer.getContentType());

        final String contentEncoding = renderer.getContentEncoding();
        if (contentEncoding != null) {
            response.setHeader("Content-Encoding", contentEncoding);
        }

        try (OutputStream os = response.getOutputStream()) {
            renderer.render(chart, config, os);

        } catch (final IOException ex) {
            LOG.error(ex);
            throw new XPathException(this, "IO issue while serializing image. " + ex.getMessage());

        }

    }

}
