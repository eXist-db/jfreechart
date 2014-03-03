(: 
    Advanced Example code for jfreechart module 
    Load the data files into /db 
    
    $Id$ 
:)
import module namespace  jfreechart = "http://exist-db.org/xquery/jfreechart";

(: Example use of all advanced config parameters :)

let $config := 
    <configuration>
        <orientation>Horizontal</orientation>
        <height>500</height>
        <width>700</width>
        <title>Example 4</title>
        <titleColor>red</titleColor>
        <rangeLowerBound>0.0</rangeLowerBound>
        <rangeUpperBound>100.0</rangeUpperBound>
        <categoryItemLabelGeneratorClass>org.jfree.chart.labels.StandardCategoryItemLabelGenerator</categoryItemLabelGeneratorClass>
        <categoryItemLabelGeneratorParameter>- {{2}}</categoryItemLabelGeneratorParameter>
        <seriesColors>orange,blue</seriesColors>
    </configuration>

return
    jfreechart:stream-render( "BarChart", $config, doc( 'categorydata-advanced.xml' ) )
