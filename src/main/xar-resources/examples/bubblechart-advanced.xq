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
        <title>BubbleChart</title>
        <titleColor>black</titleColor>
        <!--rangeLowerBound>0</rangeLowerBound>
        <rangeUpperBound>10</rangeUpperBound-->
        <!--domainLowerBound>0</domainLowerBound>
        <domainUpperBound>10</domainUpperBound-->
        <rangeAxisLabel>Y</rangeAxisLabel>
        <domainAxisLabel>X</domainAxisLabel>
        <legend>true</legend>
        <foregroundAlpha>0.65f</foregroundAlpha>
        <rangeLowerMargin>0.55</rangeLowerMargin>
        <rangeUpperMargin>0.55</rangeUpperMargin>
        <domainLowerMargin>0.3</domainLowerMargin>
        <domainUpperMargin>0.3</domainUpperMargin>
        <rangeIntegerTickUnits>false</rangeIntegerTickUnits>
        <domainIntegerTickUnits>true</domainIntegerTickUnits>
        <domainGridlinesVisible>false</domainGridlinesVisible>
        <rangeGridlinesVisible>true</rangeGridlinesVisible>
        <domainZeroBaselineVisible>true</domainZeroBaselineVisible>
        <rangeZeroBaselineVisible>true</rangeZeroBaselineVisible>
        <domainAutoRangeIncludesZero>false</domainAutoRangeIncludesZero>
        <rangeAutoRangeIncludesZero>false</rangeAutoRangeIncludesZero>
    </configuration>

return
    jfreechart:stream-render( "BubbleChart", $config, doc("xyzdata.xml"))
