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
        <title>RingChart</title>
        <titleColor>red</titleColor>
        <pieShadowColor>null</pieShadowColor>
        <plotBackgroundColor>null</plotBackgroundColor>
        <outlineVisible>false</outlineVisible>
        <pieSectionOutlinesVisible>false</pieSectionOutlinesVisible>
        <foregroundAlpha>0.9f</foregroundAlpha>
        <legend>false</legend>
    </configuration>

return
    jfreechart:stream-render( "RingChart", $config, doc("piedata-ringchart.xml"))
