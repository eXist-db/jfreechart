/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2009-2014 The eXist-db Project
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
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.exist.xquery.XPathException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xml.DatasetReader;
import org.jfree.data.xml.XYDatasetReader;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;

/**
 * Wrapper for JFreeChart's ChartFactory.
 *
 * @author Dannes Wessels (dizzzz@exist-db.org)
 * @author Andrzej Taramina (andrzej@chaeron.com)
 * @author Leif-Jöran Olsson (ljo@exist-db.org)
 */
public class JFreeChartFactory {

    private final static Logger logger = Logger.getLogger(JFreeChartFactory.class);

    /**
     *  Create JFreeChart graph using the supplied parameters.
     *
     * @param chartType One of the many chart types.
     * @param conf      Chart configuration
     * @param is        Inputstream containing chart data
     * @return          Initialized chart or NULL in case of issues.
     * @throws org.exist.xquery.XPathException Thrown when something unexpected happens
     */
    public static JFreeChart createJFreeChart(String chartType, Configuration conf, InputStream is)
            throws XPathException {

        logger.debug("Generating "+chartType);

        // Currently four dataset types supported
        CategoryDataset categoryDataset = null;
        PieDataset pieDataset = null;
        XYDataset XYDataset = null;
        XYZDataset XYZDataset = null;

        try {
            if ("PieChart".equals(chartType) || "PieChart3D".equals(chartType) || "RingChart".equals(chartType)) {
                logger.debug("Reading XML PieDataset");
                pieDataset = DatasetReader.readPieDatasetFromXML(is);

            } else if ("ScatterPlot".equals(chartType) || "XYAreaChart".equals(chartType) || "XYBarChart".equals(chartType) || "XYLineChart".equals(chartType)) {
                logger.debug("Reading XML XYDataset");
                XYDataset = XYDatasetReader.readXYDatasetFromXML(is);
            } else if ("BubbleChart".equals(chartType)) {
                logger.debug("Reading XML XYZDataset");
                XYZDataset = XYDatasetReader.readXYZDatasetFromXML(is);
            } else {
                logger.debug("Reading XML CategoryDataset");
                categoryDataset = DatasetReader.readCategoryDatasetFromXML(is);
            }

        } catch (IOException ex) {
            throw new XPathException(ex.getMessage());

        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                //
            }
        }

        // Return chart
        JFreeChart chart = null;

        // Big chart type switch, case sensitive now
        switch (chartType) {

            case "AreaChart":
                chart = ChartFactory.createAreaChart(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());
                setCategoryChartParameters(chart, conf);
                break;

            case "BarChart":
                chart = ChartFactory.createBarChart(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "BarChart3D":
                chart = ChartFactory.createBarChart3D(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "LineChart":
                chart = ChartFactory.createLineChart(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "LineChart3D":
                chart = ChartFactory.createLineChart3D(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "MultiplePieChart":
                chart = ChartFactory.createMultiplePieChart(
                        conf.getTitle(), categoryDataset, conf.getOrder(),
                        conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPieChartParameters(chart, conf);
                break;

            case "MultiplePieChart3D":
                chart = ChartFactory.createMultiplePieChart3D(
                        conf.getTitle(), categoryDataset, conf.getOrder(),
                        conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPieChartParameters(chart, conf);
                break;

            case "PieChart":
                chart = ChartFactory.createPieChart(
                        conf.getTitle(), pieDataset,
                        conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPieChartParameters(chart, conf);
                break;

            case "PieChart3D":
                chart = ChartFactory.createPieChart3D(
                        conf.getTitle(), pieDataset,
                        conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPieChartParameters(chart, conf);
                break;

            case "RingChart":
                chart = ChartFactory.createRingChart(
                        conf.getTitle(), pieDataset,
                        conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPieChartParameters(chart, conf);
                break;

            case "SpiderWebChart":
                SpiderWebPlot plot = new SpiderWebPlot(categoryDataset);
                if (conf.isGenerateTooltips()) {
                    plot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
                }
                chart = new JFreeChart(conf.getTitle(), JFreeChart.DEFAULT_TITLE_FONT, plot, false);

                if (conf.isGenerateLegend()) {
                    LegendTitle legend = new LegendTitle(plot);
                    legend.setPosition(RectangleEdge.BOTTOM);
                    chart.addSubtitle(legend);
                } else {
                    TextTitle subTitle = new TextTitle(" ");
                    subTitle.setPosition(RectangleEdge.BOTTOM);
                    chart.addSubtitle(subTitle);
                }

                setCategoryChartParameters(chart, conf);
                break;

            case "StackedAreaChart":
                chart = ChartFactory.createStackedAreaChart(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "StackedBarChart":
                chart = ChartFactory.createStackedBarChart(
                        conf.getTitle(), conf.getDomainAxisLabel(), conf.getRangeAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "StackedBarChart3D":
                chart = ChartFactory.createStackedBarChart3D(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;

            case "WaterfallChart":
                chart = ChartFactory.createWaterfallChart(
                        conf.getTitle(), conf.getCategoryAxisLabel(), conf.getValueAxisLabel(), categoryDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setCategoryChartParameters(chart, conf);
                break;
            case "ScatterPlot":
                chart = ChartFactory.createScatterPlot(
                        conf.getTitle(), conf.getDomainAxisLabel(), conf.getRangeAxisLabel(), XYDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPlotAndNumberAxisParameters(chart, conf);
                break;
            case "XYAreaChart":
                chart = ChartFactory.createXYAreaChart(
                        conf.getTitle(), conf.getDomainAxisLabel(), conf.getRangeAxisLabel(), XYDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPlotAndNumberAxisParameters(chart, conf);
                break;
            case "XYBarChart":
                chart = ChartFactory.createXYBarChart(
			conf.getTitle(), conf.getDomainAxisLabel(), true,
			conf.getRangeAxisLabel(), (IntervalXYDataset) XYDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPlotAndNumberAxisParameters(chart, conf);
                break;
            case "XYLineChart":
                chart = ChartFactory.createXYLineChart(
                        conf.getTitle(), conf.getDomainAxisLabel(), conf.getRangeAxisLabel(), XYDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());

                setPlotAndNumberAxisParameters(chart, conf);
                break;
            case "BubbleChart":
                chart = ChartFactory.createBubbleChart(
                        conf.getTitle(), conf.getDomainAxisLabel(), conf.getRangeAxisLabel(), XYZDataset,
                        conf.getOrientation(), conf.isGenerateLegend(), conf.isGenerateTooltips(), conf.isGenerateUrls());
                setPlotAndNumberAxisParameters(chart, conf);
                break;


            default:
                logger.error("Illegal chart type. Choose one of "
                        + "CategoryDataset/PieDataset: AreaChart BarChart BarChart3D "
			+ "LineChart LineChart3D "
                        + "MultiplePieChart MultiplePieChart3D PieChart PieChart3D "
                        + "RingChart SpiderWebChart StackedAreaChart StackedBarChart "
                        + "StackedBarChart3D WaterfallChart. "
			+ "XYDataset: ScatterPlot XYAreaChart XYBarChart XYLineChart. "
			+ "XYZDataset: BubbleChart.");

        }

        setCommonParameters( chart, conf );

        return chart;
    }
    
    
    private static void setCategoryChartParameters(JFreeChart chart, Configuration config) throws XPathException {
	setPlotAndNumberAxisParameters(chart, config);
        setCategoryRange(chart, config);
        setCategoryItemLabelGenerator(chart, config);
        setCategoryLabelPositions(chart, config);
        setSeriesColors(chart, config);
        setAxisColors(chart, config);
    }
    
    private static void setPlotAndNumberAxisParameters(JFreeChart chart, Configuration config) {
	setRenderer(chart, config);
	if (chart.getPlot() instanceof CategoryPlot) {
	    CategoryPlot plot = (CategoryPlot) chart.getPlot();
	    if (config.getForegroundAlpha() != null) {
		plot.setForegroundAlpha(config.getForegroundAlpha());
	    }
	    plot.setDomainGridlinesVisible(config.isDomainGridlinesVisible());
	    plot.setRangeGridlinesVisible(config.isRangeGridlinesVisible());
	    plot.setRangeZeroBaselineVisible(config.isRangeZeroBaselineVisible());

	    plot.setOutlineVisible(config.isOutlineVisible());

	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    rangeAxis.setAutoRangeIncludesZero(config.isRangeAutoRangeIncludesZero());
	    if (config.isRangeIntegerTickUnits()) {
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    }
	} else if (chart.getPlot() instanceof XYPlot) {
	    XYPlot XYPlot = (XYPlot) chart.getPlot();
	    if (config.getForegroundAlpha() != null) {
		XYPlot.setForegroundAlpha(config.getForegroundAlpha());
	    }

	    XYPlot.setDomainGridlinesVisible(config.isDomainGridlinesVisible());
	    XYPlot.setRangeGridlinesVisible(config.isRangeGridlinesVisible());
	    XYPlot.setDomainZeroBaselineVisible(config.isDomainZeroBaselineVisible());
	    XYPlot.setRangeZeroBaselineVisible(config.isRangeZeroBaselineVisible());

	    XYPlot.setOutlineVisible(config.isOutlineVisible());

	    NumberAxis domainAxis = (NumberAxis) XYPlot.getDomainAxis();
	    Double domainLowerBound = config.getDomainLowerBound();
	    Double domainUpperBound = config.getDomainUpperBound();
	    Double domainLowerMargin = config.getDomainLowerMargin();
	    Double domainUpperMargin = config.getDomainUpperMargin();

	    if (domainUpperBound != null) {
		domainAxis.setUpperBound(domainUpperBound.doubleValue());
	    }
	    if (domainLowerBound != null) {
		domainAxis.setLowerBound(domainLowerBound.doubleValue());
	    }

	    if (domainLowerMargin != null) {
		domainAxis.setLowerMargin(domainLowerMargin.doubleValue());
	    }
	    if (domainUpperMargin != null) {
		domainAxis.setUpperMargin(domainUpperMargin.doubleValue());
	    }

	    if (config.isDomainIntegerTickUnits()) {
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    }
	    domainAxis.setAutoRangeIncludesZero(config.isDomainAutoRangeIncludesZero());

	    NumberAxis rangeAxis = (NumberAxis) XYPlot.getRangeAxis();
	    Double rangeLowerBound = config.getRangeLowerBound();
	    Double rangeUpperBound = config.getRangeUpperBound();
	    Double rangeLowerMargin = config.getRangeLowerMargin();
	    Double rangeUpperMargin = config.getRangeUpperMargin();

	    if (rangeUpperBound != null) {
		XYPlot.getRangeAxis().setUpperBound(rangeUpperBound.doubleValue());
	    }
	    if (rangeLowerBound != null) {
		XYPlot.getRangeAxis().setLowerBound(rangeLowerBound.doubleValue());
	    }

	    if (rangeLowerMargin != null) {
		rangeAxis.setLowerMargin(rangeLowerMargin.doubleValue());
	    }
	    if (rangeUpperMargin != null) {
		rangeAxis.setUpperMargin(rangeUpperMargin.doubleValue());
	    }

	    if (config.isRangeIntegerTickUnits()) {
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    }
	    rangeAxis.setAutoRangeIncludesZero(config.isRangeAutoRangeIncludesZero());
	} else 	if (chart.getPlot() instanceof PiePlot ||
		    chart.getPlot() instanceof MultiplePiePlot) {
	    PiePlot plot;
            if (chart.getPlot() instanceof MultiplePiePlot) {
                plot = (PiePlot) ((MultiplePiePlot) chart.getPlot()).getPieChart().getPlot();
	    } else {
		plot = (PiePlot) chart.getPlot();
	    }
	    if (config.getForegroundAlpha() != null) {
		plot.setForegroundAlpha(config.getForegroundAlpha());
	    }
	    plot.setSectionOutlinesVisible(config.isPieSectionOutlinesVisible());
	    plot.setOutlineVisible(config.isOutlineVisible());
	    plot.setShadowPaint(config.getPieShadowColor()); //null
	}
    }

    private static void setRenderer(JFreeChart chart, Configuration config) {
	if (chart.getPlot() instanceof CategoryPlot && config.isOnlyShape()) {
	    CategoryItemRenderer renderer = new LineAndShapeRenderer(false, true);
	    CategoryPlot plot = (CategoryPlot) chart.getPlot();
	    plot.setDomainGridlinesVisible(true);
	    plot.setRangeGridlinesVisible(true);
	    plot.setRenderer(renderer);
	} else if (chart.getPlot() instanceof XYPlot && (config.getDotWidth() != 1 || config.getDotHeight() != 1)) {
	    XYPlot XYPlot = (XYPlot) chart.getPlot();
	    XYDotRenderer XYDotRenderer = new XYDotRenderer();
	    if (config.getDotWidth() != 1) {
		XYDotRenderer.setDotWidth(config.getDotWidth());
	    }
	    if (config.getDotHeight() != 1) {
		XYDotRenderer.setDotHeight(config.getDotHeight());
	    }
	    XYPlot.setRenderer(XYDotRenderer);
	}
    }

    private static void setCategoryRange(JFreeChart chart, Configuration config) {
        Double rangeLowerBound = config.getRangeLowerBound();
        Double rangeUpperBound = config.getRangeUpperBound();

        if (rangeUpperBound != null) {
            if (chart.getPlot() instanceof SpiderWebPlot) {
                ((SpiderWebPlot) chart.getPlot()).setMaxValue(rangeUpperBound.doubleValue());
                return;
            } else {
                ((CategoryPlot) chart.getPlot()).getRangeAxis().setUpperBound(rangeUpperBound.doubleValue());
            }
        }

        if (rangeLowerBound != null) {
            ((CategoryPlot) chart.getPlot()).getRangeAxis().setLowerBound(rangeLowerBound.doubleValue());
        }
    }
    
    private static void setCategoryItemLabelGenerator(JFreeChart chart, Configuration config) throws XPathException {
        String className = config.getCategoryItemLabelGeneratorClass();
        CategoryItemLabelGenerator generator = null;

        if (className != null) {
            try {
                Class generatorClass = Class.forName(className);

                Class[] argsClass = new Class[]{String.class, NumberFormat.class};
                String param = config.getCategoryItemLabelGeneratorParameter();
                NumberFormat fmt = new DecimalFormat(config.getCategoryItemLabelGeneratorNumberFormat());
                Object[] args = new Object[]{param, fmt};

                Constructor argsConstructor = generatorClass.getConstructor(argsClass);

                generator = (CategoryItemLabelGenerator) argsConstructor.newInstance(args);
            } catch (Exception e) {
                throw (new XPathException("Cannot instantiate CategoryItemLabelGeneratorClass: " + className + ", exception: " + e));
            }

            if (chart.getPlot() instanceof SpiderWebPlot) {
                ((SpiderWebPlot) chart.getPlot()).setLabelGenerator(generator);
            } else {
                CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();

                renderer.setBaseItemLabelGenerator(generator);

                renderer.setItemLabelsVisible(true);
            }
        }
    }
    
    private static void setCategoryLabelPositions( JFreeChart chart, Configuration config )
    {
        CategoryLabelPositions positions  = config.getCategoryLabelPositions();
	if (chart.getPlot() instanceof CategoryPlot) {
	    ((CategoryPlot)chart.getPlot()).getDomainAxis().setCategoryLabelPositions(positions);
	}
    }
    
    private static void setSeriesColors( JFreeChart chart, Configuration config )
    {
        String seriesColors          = config.getSeriesColors();
        

        if (chart.getPlot() instanceof SpiderWebPlot) {
            setSeriesColors((SpiderWebPlot) chart.getPlot(), seriesColors);
        } else {
            CategoryItemRenderer renderer = ((CategoryPlot)chart.getPlot()).getRenderer();
            setSeriesColors(renderer, seriesColors);
        }
    }

    private static void setSeriesColors(Object renderer, final String seriesColors) {
        if (seriesColors != null) {
            StringTokenizer st = new StringTokenizer( seriesColors, "," );

            int i = 0;
            while( st.hasMoreTokens() ) {
                String colorName = st.nextToken().trim();
                Color color = null;
                try {
                    color = Colour.getColor( colorName );
                } catch( XPathException e ) {
                }
                   
                if( color != null ) {
                    if (renderer instanceof SpiderWebPlot) {
                        ((SpiderWebPlot) renderer).setSeriesPaint(i, color);
                    } else {
                        ((CategoryItemRenderer) renderer).setSeriesPaint( i, color );
                    }
                } else {
                    logger.warn( "Invalid colour name or hex value specified for SeriesColors: " + colorName + ", default colour will be used instead." );
                }
                
                i++;
            }
        }
    }	
        
	private static void setAxisColors( JFreeChart chart, Configuration config )
    {
        Color categoryAxisColor          = config.getCategoryAxisColor();
		Color valueAxisColor          	 = config.getValueAxisColor();
        
        if( categoryAxisColor != null ) {
            if (chart.getPlot() instanceof SpiderWebPlot) {
                ((SpiderWebPlot) chart.getPlot()).setAxisLinePaint(categoryAxisColor);
            } else {
                ((CategoryPlot)chart.getPlot()).getDomainAxis().setLabelPaint(categoryAxisColor);         }
        }
		
		if( valueAxisColor != null ) {
            if (chart.getPlot() instanceof SpiderWebPlot) {
                //((SpiderWebPlot) chart.getPlot()).setAxisLinePaint(valueAxisColor);
            } else {
                ((CategoryPlot)chart.getPlot()).getRangeAxis().setLabelPaint( valueAxisColor );  
            }
        }
    }
    
    
    private static void setPieChartParameters( JFreeChart chart, Configuration config )
    {
        setPlotAndNumberAxisParameters(chart, config);
        setPieSectionLabel( chart, config );
        setSectionColors( chart, config );
    }
    
    
    private static void setPieSectionLabel( JFreeChart chart, Configuration config )
    {
        String pieSectionLabel          = config.getPieSectionLabel();
        String pieSectionNumberFormat   = config.getPieSectionNumberFormat();
        String pieSectionPercentFormat  = config.getPieSectionPercentFormat();
        
        if (pieSectionLabel != null) {
            if (chart.getPlot() instanceof MultiplePiePlot) {
                ((PiePlot) ((MultiplePiePlot) chart.getPlot()).getPieChart().getPlot()).setLabelGenerator(new StandardPieSectionLabelGenerator(pieSectionLabel, new DecimalFormat(pieSectionNumberFormat), new DecimalFormat(pieSectionPercentFormat)));
            } else {
                ((PiePlot) chart.getPlot()).setLabelGenerator(new StandardPieSectionLabelGenerator(pieSectionLabel, new DecimalFormat(pieSectionNumberFormat), new DecimalFormat(pieSectionPercentFormat)));
            }
        }
    }
    
    
    private static void setSectionColors( JFreeChart chart, Configuration config )
    {
        String sectionColors          = config.getSectionColors();
        String sectionColorsDelimiter = config.getSectionColorsDelimiter();
        
        if( sectionColors != null ) {
	    PiePlot plot;
	    if (chart.getPlot() instanceof MultiplePiePlot) {
                plot = (PiePlot) ((MultiplePiePlot) chart.getPlot()).getPieChart().getPlot();
	    } else {
		plot = (PiePlot) chart.getPlot();
	    }
            
            StringTokenizer st = new StringTokenizer( sectionColors, sectionColorsDelimiter );
            while( st.hasMoreTokens() ) {
                String sectionName = st.nextToken().trim();
                String colorName = "";
                
                if( st.hasMoreTokens() ) {
                    colorName = st.nextToken().trim();
                }
                
                Color color = null;
                
                try {
                    color = Colour.getColor( colorName );
                } catch( XPathException e ) {
                }

                if( color != null ) {
                    plot.setSectionPaint(sectionName, color);

                } else {
                    logger.warn(MessageFormat.format("Invalid colour name or hex value specified for "
                            + "SectionColors: {0}, default colour will be used instead. "
                            + "Section Name: {1}", colorName, sectionName));
                }
            }
        }
    }
    
    
    private static void setCommonParameters( JFreeChart chart, Configuration config )
    {
        setColors( chart, config );
    }
    
    
     private static void setColors( JFreeChart chart, Configuration config )
    {
        Color titleColor            = config.getTitleColor();
        Color chartBackgroundColor  = config.getChartBackgroundColor();
        Color plotBackgroundColor   = config.getPlotBackgroundColor();
        
        if( titleColor != null ) {
            chart.getTitle().setPaint( titleColor );
        }
        
        if( chartBackgroundColor != null ) {
            chart.setBackgroundPaint( chartBackgroundColor );
        }
        
	chart.getPlot().setBackgroundPaint( plotBackgroundColor ); //null
    }
}
