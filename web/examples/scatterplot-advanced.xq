xquery version "3.0";
(:
Advanced Example code for jfreechart module
Load the data files into /db
:)

import module namespace jfreechart = "http://exist-db.org/xquery/jfreechart";

(: Example use of all advanced config parameters :)

let $config :=
    <configuration>
        <orientation>Vertical</orientation>
        <height>500</height>
        <width>700</width>
        <title>ScatterPlot XY (foregroundAlpha=0.50, dotWidth=20, dotHeight=25)</title>
        <titleColor>black</titleColor>
        <!--rangeLowerBound>0</rangeLowerBound-->
        <!--rangeUpperBound>3</rangeUpperBound-->
        <!--domainLowerBound>0</domainLowerBound-->
        <!--domainUpperBound>4</domainUpperBound-->
        <rangeAxisLabel>Y</rangeAxisLabel>
        <domainAxisLabel>X</domainAxisLabel>
        <legend>true</legend>
        <foregroundAlpha>0.50f</foregroundAlpha>
        <rangeLowerMargin>0.55</rangeLowerMargin>
        <rangeUpperMargin>0.55</rangeUpperMargin>
        <domainLowerMargin>0.3</domainLowerMargin>
        <domainUpperMargin>0.3</domainUpperMargin>
        <rangeIntegerTickUnits>true</rangeIntegerTickUnits>
        <domainIntegerTickUnits>true</domainIntegerTickUnits>
        <domainGridlinesVisible>true</domainGridlinesVisible>
        <rangeGridlinesVisible>true</rangeGridlinesVisible>
        <domainZeroBaselineVisible>true</domainZeroBaselineVisible>
        <rangeZeroBaselineVisible>true</rangeZeroBaselineVisible>
        <domainAutoRangeIncludesZero>false</domainAutoRangeIncludesZero>
        <rangeAutoRangeIncludesZero>false</rangeAutoRangeIncludesZero>
        <dotHeight>25</dotHeight>
        <dotWidth>20</dotWidth>
    </configuration>

return
    jfreechart:stream-render( "ScatterPlot", $config, doc("xydata.xml"))
