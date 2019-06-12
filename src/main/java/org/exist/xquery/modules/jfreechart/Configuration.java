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
import org.exist.xquery.XPathException;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.TableOrder;
import org.w3c.dom.Node;

import java.awt.*;
import java.text.MessageFormat;

/**
 * Class for storing all configuration items for charts, except chart type.
 *
 * @author Dannes Wessels (dizzzz@exist-db.org)
 * @author Andrzej Taramina (andrzej@chaeron.com)
 * @author Leif-Jöran Olsson (ljo@exist-db.org)
 */
public class Configuration {

    // Image type
    public static final String DEFAULT_IMAGE_TYPE = "png";
    private final static Logger LOGGER = LogManager.getLogger(Configuration.class);
    // Default dimension of image
    private int imageHeight = 300;
    private int imageWidth = 400;
    // for XYDotRenderer
    private int dotHeight = 1;
    private int dotWidth = 1;
    // for XYBarRenderer
    private Double barWidth = 0.9;
    // Chart title
    private String title;
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
    private Color plotBackgroundColor = Color.white;

    private Color categoryAxisColor;
    private Color timeAxisColor;
    private Color valueAxisColor;

    private Color outlineColor = Color.gray;
    private Color pieShadowColor = new Color(151, 151, 151, 128);

    private String seriesColors;

    private String sectionColors;
    private String sectionColorsDelimiter = ",";

    // Range  
    private Double rangeLowerBound;
    private Double rangeUpperBound;
    private Double rangeLowerMargin;
    private Double rangeUpperMargin;

    // Domain
    private Double domainLowerBound;
    private Double domainUpperBound;
    private Double domainLowerMargin;
    private Double domainUpperMargin;

    // Alpha
    private Float foregroundAlpha;

    // Misc flags
    private boolean generateLegend = false;
    private boolean generateTooltips = false;
    private boolean generateUrls = false;

    private boolean onlyShape = false;

    private boolean rangeAutoRangeIncludesZero = true;
    private boolean domainAutoRangeIncludesZero = true;
    private boolean rangeZeroBaselineVisible = true;
    private boolean domainZeroBaselineVisible = true;
    private boolean rangeIntegerTickUnits = false;
    private boolean domainIntegerTickUnits = false;
    private boolean rangeGridlinesVisible = false;
    private boolean domainGridlinesVisible = false;
    private boolean rangeGridbandsVisible = false;
    private boolean domainGridbandsVisible = false;

    private boolean outlineVisible = true;
    private boolean pieSectionOutlinesVisible = true;

    // for XYBarRenderer
    private boolean useYInterval = false;
    private boolean useDomainSymbolAxis = false;
    private boolean useDomainNumberAxis = false;
    private boolean useRangeSymbolAxis = false;
    private Float lineWidth;

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

    public boolean isOnlyShape() {
        return onlyShape;
    }

    public boolean isRangeAutoRangeIncludesZero() {
        return rangeAutoRangeIncludesZero;
    }

    public boolean isDomainAutoRangeIncludesZero() {
        return domainAutoRangeIncludesZero;
    }

    public boolean isRangeZeroBaselineVisible() {
        return rangeZeroBaselineVisible;
    }

    public boolean isDomainZeroBaselineVisible() {
        return domainZeroBaselineVisible;
    }

    public boolean isRangeIntegerTickUnits() {
        return rangeIntegerTickUnits;
    }

    public boolean isDomainIntegerTickUnits() {
        return domainIntegerTickUnits;
    }

    public boolean isRangeGridlinesVisible() {
        return rangeGridlinesVisible;
    }

    public boolean isDomainGridlinesVisible() {
        return domainGridlinesVisible;
    }

    public boolean isRangeGridbandsVisible() {
        return rangeGridbandsVisible;
    }

    public boolean isDomainGridbandsVisible() {
        return domainGridbandsVisible;
    }

    public boolean isOutlineVisible() {
        return outlineVisible;
    }

    public boolean isPieSectionOutlinesVisible() {
        return pieSectionOutlinesVisible;
    }

    public boolean isUseYInterval() {
        return useYInterval;
    }

    public boolean isUseDomainSymbolAxis() {
        return useDomainSymbolAxis;
    }

    public boolean isUseDomainNumberAxis() {
        return useDomainNumberAxis;
    }

    public boolean isUseRangeSymbolAxis() {
        return useRangeSymbolAxis;
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

    public int getDotHeight() {
        return dotHeight;
    }

    public int getDotWidth() {
        return dotWidth;
    }

    public Double getBarWidth() {
        return barWidth;
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

    public Color getOutlineColor() {
        return outlineColor;
    }

    public Color getPieShadowColor() {
        return pieShadowColor;
    }

    public Double getRangeLowerBound() {
        return rangeLowerBound;
    }

    public Double getRangeUpperBound() {
        return rangeUpperBound;
    }

    public Double getRangeLowerMargin() {
        return rangeLowerMargin;
    }

    public Double getRangeUpperMargin() {
        return rangeUpperMargin;
    }

    public Double getDomainLowerBound() {
        return domainLowerBound;
    }

    public Double getDomainUpperBound() {
        return domainUpperBound;
    }

    public Double getDomainLowerMargin() {
        return domainLowerMargin;
    }

    public Double getDomainUpperMargin() {
        return domainUpperMargin;
    }

    public Float getForegroundAlpha() {
        return foregroundAlpha;
    }

    public Float getLineWidth() {
        return lineWidth;
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
    public void parse(final Node configuration) throws XPathException {

        if (configuration.getNodeType() == Node.ELEMENT_NODE && configuration.getLocalName().equals("configuration")) {

            //Get the First Child
            Node child = configuration.getFirstChild();
            while (child != null) {

                //Parse each of the child nodes
                if (child.getNodeType() == Node.ELEMENT_NODE && child.hasChildNodes()) {

                    final String localName = child.getLocalName();
                    final String value = getValue(child);

                    // Verify that value is found
                    if (value == null) {
                        throw new XPathException(MessageFormat.format("Value for \"{0}\" cannot be parsed", localName));
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
                                throw new XPathException(MessageFormat.format("Wrong value for \"{0}\"", localName));
                            }
                            verifyValue(localName, orientation);
                            break;
                        case "tableOrder":
                            if ("COLUMN".equalsIgnoreCase(value)) {
                                order = TableOrder.BY_COLUMN;

                            } else if ("ROW".equalsIgnoreCase(value)) {
                                order = TableOrder.BY_ROW;

                            } else {
                                throw new XPathException(MessageFormat.format("Wrong value for \"{0}\"", localName));
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

                        case "onlyShape":
                            onlyShape = parseBoolean(value);
                            verifyValue(localName, onlyShape);
                            break;

                        case "rangeAutoRangeIncludesZero":
                            rangeAutoRangeIncludesZero = parseBoolean(value);
                            verifyValue(localName, rangeAutoRangeIncludesZero);
                            break;

                        case "domainAutoRangeIncludesZero":
                            domainAutoRangeIncludesZero = parseBoolean(value);
                            verifyValue(localName, domainAutoRangeIncludesZero);
                            break;

                        case "rangeIntegerTickUnits":
                            rangeIntegerTickUnits = parseBoolean(value);
                            verifyValue(localName, rangeIntegerTickUnits);
                            break;

                        case "domainIntegerTickUnits":
                            domainIntegerTickUnits = parseBoolean(value);
                            verifyValue(localName, domainIntegerTickUnits);
                            break;

                        case "rangeGridlinesVisible":
                            rangeGridlinesVisible = parseBoolean(value);
                            verifyValue(localName, rangeGridlinesVisible);
                            break;

                        case "domainGridlinesVisible":
                            domainGridlinesVisible = parseBoolean(value);
                            verifyValue(localName, domainGridlinesVisible);
                            break;

                        case "rangeGridbandsVisible":
                            rangeGridbandsVisible = parseBoolean(value);
                            verifyValue(localName, rangeGridbandsVisible);
                            break;

                        case "domainGridbandsVisible":
                            domainGridbandsVisible = parseBoolean(value);
                            verifyValue(localName, domainGridbandsVisible);
                            break;

                        case "rangeZeroBaselineVisible":
                            rangeZeroBaselineVisible = parseBoolean(value);
                            verifyValue(localName, rangeZeroBaselineVisible);
                            break;

                        case "domainZeroBaselineVisible":
                            domainZeroBaselineVisible = parseBoolean(value);
                            verifyValue(localName, domainZeroBaselineVisible);
                            break;

                        case "outlineVisible":
                            outlineVisible = parseBoolean(value);
                            verifyValue(localName, outlineVisible);
                            break;

                        case "pieSectionOutlinesVisible":
                            pieSectionOutlinesVisible = parseBoolean(value);
                            verifyValue(localName, pieSectionOutlinesVisible);
                            break;

                        case "useYInterval":
                            useYInterval = parseBoolean(value);
                            verifyValue(localName, useYInterval);
                            break;

                        case "useDomainSymbolAxis":
                            useDomainSymbolAxis = parseBoolean(value);
                            verifyValue(localName, useDomainSymbolAxis);
                            break;

                        case "useDomainNumberAxis":
                            useDomainNumberAxis = parseBoolean(value);
                            verifyValue(localName, useDomainNumberAxis);
                            break;

                        case "useRangeSymbolAxis":
                            useRangeSymbolAxis = parseBoolean(value);
                            verifyValue(localName, useRangeSymbolAxis);
                            break;

                        case "width":
                            imageWidth = parseInteger(value);
                            verifyValue(localName, imageWidth);
                            break;

                        case "height":
                            imageHeight = parseInteger(value);
                            verifyValue(localName, imageHeight);
                            break;

                        case "dotWidth":
                            dotWidth = parseInteger(value);
                            verifyValue(localName, dotWidth);
                            break;

                        case "dotHeight":
                            dotHeight = parseInteger(value);
                            verifyValue(localName, dotHeight);
                            break;

                        case "barWidth":
                            barWidth = parseDouble(value);
                            verifyValue(localName, barWidth);
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

                        case "outlineColor":
                            outlineColor = Colour.getColor(value);
                            break;

                        case "pieShadowColor":
                            pieShadowColor = Colour.getColor(value);
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
                            verifyValue(localName, rangeUpperBound);
                            break;

                        case "rangeLowerMargin":
                            rangeLowerMargin = parseDouble(value);
                            verifyValue(localName, rangeLowerMargin);
                            break;

                        case "rangeUpperMargin":
                            rangeUpperMargin = parseDouble(value);
                            verifyValue(localName, rangeUpperMargin);
                            break;

                        case "domainLowerBound":
                            domainLowerBound = parseDouble(value);
                            verifyValue(localName, domainLowerBound);
                            break;

                        case "domainUpperBound":
                            domainUpperBound = parseDouble(value);
                            verifyValue(localName, domainUpperBound);
                            break;

                        case "domainLowerMargin":
                            domainLowerMargin = parseDouble(value);
                            verifyValue(localName, domainLowerMargin);
                            break;

                        case "domainUpperMargin":
                            domainUpperMargin = parseDouble(value);
                            verifyValue(localName, domainUpperMargin);
                            break;

                        case "foregroundAlpha":
                            foregroundAlpha = parseFloat(value);
                            verifyValue(localName, foregroundAlpha);
                            break;

                        case "lineWidth":
                            lineWidth = parseFloat(value);
                            verifyValue(localName, lineWidth);
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
                                throw new XPathException(MessageFormat.format("Wrong value for \"{0}\"", localName));
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
    private Boolean parseBoolean(final String value) {
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
    private Integer parseInteger(final String value) {

        try {
            return Integer.valueOf(value);

        } catch (final NumberFormatException ex) {
            LOGGER.debug(ex.getMessage());
            return null;
        }
    }

    /**
     * Parse text and return Double. NULL is returned when value cannot be
     * converted.
     */
    private Double parseDouble(final String value) {

        try {
            return Double.valueOf(value);

        } catch (final NumberFormatException ex) {
            LOGGER.debug(ex.getMessage());
            return null;
        }
    }

    /**
     * Parse text and return Float. NULL is returned when value cannot be
     * converted.
     */
    private Float parseFloat(final String value) {

        try {
            return Float.valueOf(value);

        } catch (final NumberFormatException ex) {
            LOGGER.debug(ex.getMessage());
            return null;
        }
    }

    /**
     * Helper method for getting the value of the (first) node.
     */
    private String getValue(final Node child) {
        return child.getFirstChild().getNodeValue();
    }

    /**
     * Verify that an value could be converted
     *
     * @param localName      Name of configuration element
     * @param convertedValue Object representing converted value
     * @throws XPathException Conversion was not OK.
     */
    private void verifyValue(final String localName, final Object convertedValue) throws XPathException {
        if (convertedValue == null) {
            throw new XPathException(MessageFormat.format("Unable to convert value of \"{0}\"", localName));
        }
    }
}
