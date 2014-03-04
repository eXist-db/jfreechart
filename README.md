JFreeChart extension
==========

This is the eXist-db JFreeChart extension which has been part of the standard distribution for a long time, but due to the size of the thirdparty jar files it hadn't been enabled.

The project generates a XAR file containing all required JAR files (can  be used standalone) and a small Showcase XAR.

build with the following command, Java7 is required.

```shell
ant setup
ant
```

Using the extion is as easy as in the followinf example:

```xquery
import module namespace jfreechart = "http://exist-db.org/xquery/jfreechart";


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
```
