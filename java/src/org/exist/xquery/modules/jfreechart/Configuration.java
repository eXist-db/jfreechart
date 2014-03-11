/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2009-2013 The eXist-db Project
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
package org.exist.xquery.modules.jfreechart;

import java.awt.Color;
import java.text.MessageFormat;
import org.apache.log4j.Logger;
import org.exist.xquery.XPathException;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.util.TableOrder;
import org.w3c.dom.Node;

/**
 * Class for storing all configuration items for charts, except chart type.
 *
 * @author Dannes Wessels (dizzzz@exist-db.org)
 * @author Andrzej Taramina (andrzej@chaeron.com)
 * @author Leif-Jöran Olsson (ljo@exist-db.org)
 */
public class Configuration {

    private final static Logger logger = Logger.getLogger(Configuration.class);

    // Default dimension of image
    private int imageHeight = 300;
    private int imageWidth = 400;

    // Chart title
    private String title;

    // Image type
    public static final String DEFAULT_IMAGE_TYPE = "png";
    private String imageType = DEFAULT_IMAGE_TYPE;

    // Labels
    private String categoryAxisLabel;
    private String domainAxisLabel;
    private String rangeAxisLabel;
    private String timeAxisLabel;
    private String valueAxisLabel;
    private String pieSectionLabel;
    private String pieSectionNumberFormat = "0";
    private String pieSectionPercentFormat = "0.0%";

    private String categoryItemLabelGeneratorClass;
    private String categoryItemLabelGeneratorParameter = "{2}";
    private String categoryItemLabelGeneratorNumberFormat = "0";
    private CategoryLabelPositions categoryLabelPositions = CategoryLabelPositions.STANDARD;

    // Orientation and Order
    private TableOrder order = TableOrder.BY_COLUMN;
    private PlotOrientation orientation = PlotOrientation.HORIZONTAL;

    // Colors   
    private Color titleColor;
    private Color chartBackgroundColor;
    private Color plotBackgroundColor;

    private Color categoryAxisColor;
    private Color timeAxisColor;
    private Color valueAxisColor;

    private String seriesColors;

    private String sectionColors;
    private String sectionColorsDelimiter = ",";

    // Range  
    private Double rangeLowerBound;
    private Double rangeUpperBound;

    // Misc flags
    private boolean generateLegend = false;
    private boolean generateTooltips = false;
    private boolean generateUrls = false;

    // =========================
    // Getters
    public String getImageType() {
        return imageType;
    }

    public String getTimeAxisLabel() {
        return timeAxisLabel;
    }

    public Color getTimeAxisColor() {
        return timeAxisColor;
    }

    public String getCategoryAxisLabel() {
        return categoryAxisLabel;
    }

    public Color getCategoryAxisColor() {
        return categoryAxisColor;
    }

    public boolean isGenerateLegend() {
        return generateLegend;
    }

    public boolean isGenerateTooltips() {
        return generateTooltips;
    }

    public boolean isGenerateUrls() {
        return generateUrls;
    }

    public PlotOrientation getOrientation() {
        return orientation;
    }

    public TableOrder getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public String getValueAxisLabel() {
        return valueAxisLabel;
    }

    public Color getValueAxisColor() {
        return valueAxisColor;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public String getDomainAxisLabel() {
        return domainAxisLabel;
    }

    public String getRangeAxisLabel() {
        return rangeAxisLabel;
    }

    public String getPieSectionLabel() {
        return pieSectionLabel;
    }

    public String getPieSectionNumberFormat() {
        return pieSectionNumberFormat;
    }

    public String getPieSectionPercentFormat() {
        return pieSectionPercentFormat;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public Color getChartBackgroundColor() {
        return chartBackgroundColor;
    }

    public Color getPlotBackgroundColor() {
        return plotBackgroundColor;
    }

    public Double getRangeLowerBound() {
        return rangeLowerBound;
    }

    public Double getRangeUpperBound() {
        return rangeUpperBound;
    }

    public String getCategoryItemLabelGeneratorClass() {
        return categoryItemLabelGeneratorClass;
    }

    public String getCategoryItemLabelGeneratorParameter() {
        return categoryItemLabelGeneratorParameter;
    }

    public String getCategoryItemLabelGeneratorNumberFormat() {
        return categoryItemLabelGeneratorNumberFormat;
    }

    public CategoryLabelPositions getCategoryLabelPositions() {
        return categoryLabelPositions;
    }

    public String getSeriesColors() {
        return seriesColors;
    }

    public String getSectionColors() {
        return sectionColors;
    }

    public String getSectionColorsDelimiter() {
        return sectionColorsDelimiter;
    }

    /**
     * Read configuration from node and initialize configuration.
     *
     * @param configuration Chart configuration options.
     * @throws XPathException Thrown when an element cannot be read.
     */
    public void parse(Node configuration) throws XPathException {

        if (configuration.getNodeType() == Node.ELEMENT_NODE && configuration.getLocalName().equals("configuration")) {

            //Get the First Child
            Node child = configuration.getFirstChild();
            while (child != null) {

                //Parse each of the child nodes
                if (child.getNodeType() == Node.ELEMENT_NODE && child.hasChildNodes()) {

                    String localName = child.getLocalName();
                    String value = getValue(child);

                    // Verify that value is found
                    if (value == null) {
                        throw new XPathException(MessageFormat.format("Value for '{0}' cannot be parsed", localName));
                    }

                    switch (localName) {
                        case "title":
                            title = value;
                            break;
                        case "categoryAxisLabel":
                            categoryAxisLabel = value;
                            break;
                        case "categoryAxisColor":
                            categoryAxisColor = Colour.getColor(value);
                            break;
                        case "valueAxisLabel":
                            valueAxisLabel = value;
                            break;
                        case "valueAxisColor":
                            valueAxisColor = Colour.getColor(value);
                            break;
                        case "timeAxisLabel":
                            timeAxisLabel = value;
                            break;
                        case "timeAxisColor":
                            timeAxisColor = Colour.getColor(value);
                            break;
                        case "domainAxisLabel":
                            domainAxisLabel = value;
                            break;
                        case "rangeAxisLabel":
                            rangeAxisLabel = value;
                            break;
                        case "pieSectionLabel":
                            pieSectionLabel = value;
                            break;
                        case "pieSectionNumberFormat":
                            pieSectionNumberFormat = value;
                            break;
                        case "pieSectionPercentFormat":
                            pieSectionPercentFormat = value;
                            break;
                        case "orientation":
                            if ("HORIZONTAL".equalsIgnoreCase(value)) {
                                orientation = PlotOrientation.HORIZONTAL;

                            } else if ("VERTICAL".equalsIgnoreCase(value)) {
                                orientation = PlotOrientation.VERTICAL;

                            } else {
                                throw new XPathException(MessageFormat.format("Wrong value for '{0}'", localName));
                            }
                            verifyValue(localName, orientation);
                            break;
                        case "tableOrder":
                            if ("COLUMN".equalsIgnoreCase(value)) {
                                order = TableOrder.BY_COLUMN;

                            } else if ("ROW".equalsIgnoreCase(value)) {
                                order = TableOrder.BY_ROW;

                            } else {
                                throw new XPathException(MessageFormat.format("Wrong value for '{0}'", localName));
                            }
                            verifyValue(localName, order);
                            break;

                        case "legend":
                            generateLegend = parseBoolean(value);
                            verifyValue(localName, generateLegend);
                            break;

                        case "tooltips":
                            generateTooltips = parseBoolean(value);
                            verifyValue(localName, generateTooltips);
                            break;

                        case "urls":
                            generateUrls = parseBoolean(value);
                            verifyValue(localName, generateUrls);
                            break;

                        case "width":
                            imageWidth = parseInteger(value);
                            verifyValue(localName, imageWidth);
                            break;

                        case "height":
                            imageHeight = parseInteger(value);
                            verifyValue(localName, imageHeight);
                            break;

                        case "titleColor":
                            titleColor = Colour.getColor(value);
                            break;

                        case "chartBackgroundColor":
                            chartBackgroundColor = Colour.getColor(value);
                            break;

                        case "plotBackgroundColor":
                            plotBackgroundColor = Colour.getColor(value);
                            break;

                        case "seriesColors":
                            seriesColors = value; // DW: to verify
                            break;

                        case "sectionColors":
                            sectionColors = value; // DW: to verify
                            break;

                        case "sectionColorsDelimiter":
                            sectionColorsDelimiter = value; // DW: to verify
                            break;

                        case "rangeLowerBound":
                            rangeLowerBound = parseDouble(value);
                            verifyValue(localName, rangeLowerBound);
                            break;

                        case "rangeUpperBound":
                            rangeUpperBound = parseDouble(value);
                            verifyValue(localName, rangeAxisLabel);
                            break;

                        case "categoryItemLabelGeneratorClass":
                            categoryItemLabelGeneratorClass = value;
                            break;

                        case "categoryItemLabelGeneratorParameter":
                            categoryItemLabelGeneratorParameter = value;
                            break;
                        case "categoryItemLabelGeneratorNumberFormat":
                            categoryItemLabelGeneratorNumberFormat = value;
                            break;

                        case "categoryLabelPositions":
                            if ("UP_45".equalsIgnoreCase(value)) {
                                categoryLabelPositions = CategoryLabelPositions.UP_45;
                            } else if ("UP_90".equalsIgnoreCase(value)) {
                                categoryLabelPositions = CategoryLabelPositions.UP_90;
                            } else if ("DOWN_45".equalsIgnoreCase(value)) {
                                categoryLabelPositions = CategoryLabelPositions.DOWN_45;

                            } else if ("DOWN_90".equalsIgnoreCase(value)) {
                                categoryLabelPositions = CategoryLabelPositions.DOWN_90;

                            } else {
                                throw new XPathException(MessageFormat.format("Wrong value for '{0}'", localName));
                            }
                            verifyValue(localName, categoryLabelPositions);
                            break;

                        case "imageType":
                            imageType = value;
                            break;

                    }

                }

                //next node
                child = child.getNextSibling();
            }

        }
    }

    /**
     * Parse text and return boolean. Accepted values Yes No True False,
     * otherwise NULL is returned.
     */
    private Boolean parseBoolean(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")) {
            return true;
        } else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no")) {
            return false;
        }
        return null;
    }

    /**
     * Parse text and return Integer. NULL is returned when value cannot be
     * converted.
     */
    private Integer parseInteger(String value) {

        try {
            return Integer.valueOf(value);

        } catch (NumberFormatException ex) {
            logger.debug(ex.getMessage());
            return null;
        }
    }

    /**
     * Parse text and return Double. NULL is returned when value cannot be
     * converted.
     */
    private Double parseDouble(String value) {

        try {
            return Double.valueOf(value);

        } catch (NumberFormatException ex) {
            logger.debug(ex.getMessage());
            return null;
        }
    }

    /**
     * Helper method for getting the value of the (first) node.
     */
    private String getValue(Node child) {
        return child.getFirstChild().getNodeValue();
    }

    /**
     * Verify that an value could be converted
     *
     * @param localName Name of configuration element
     * @param convertedValue Object representing converted value
     * @throws XPathException Conversion was not OK.
     */
    private void verifyValue(String localName, Object convertedValue) throws XPathException {
        if (convertedValue == null) {
            throw new XPathException(MessageFormat.format("Unable to convert value of '{0}'", localName));
        }
    }
}
