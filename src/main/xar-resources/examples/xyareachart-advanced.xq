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
        <title>XYAreaChart</title>
        <titleColor>red</titleColor>
        <!--rangeLowerBound>0</rangeLowerBound>
        <rangeUpperBound>10</rangeUpperBound-->
        <!--domainLowerBound>0</domainLowerBound>
        <domainUpperBound>10</domainUpperBound-->
        <rangeAxisLabel>Y</rangeAxisLabel>
        <domainAxisLabel>X</domainAxisLabel>
        <legend>true</legend>
        <rangeIntegerTickUnits>true</rangeIntegerTickUnits>
        <domainIntegerTickUnits>true</domainIntegerTickUnits>
        <domainGridlinesVisible>false</domainGridlinesVisible>
        <rangeGridlinesVisible>true</rangeGridlinesVisible>
        <domainZeroBaselineVisible>false</domainZeroBaselineVisible>
        <rangeZeroBaselineVisible>true</rangeZeroBaselineVisible>
        <domainAutoRangeIncludesZero>false</domainAutoRangeIncludesZero>
        <rangeAutoRangeIncludesZero>true</rangeAutoRangeIncludesZero>
    </configuration>

return
    jfreechart:stream-render( "XYAreaChart", $config, doc("xydata-xyareachart.xml"))
