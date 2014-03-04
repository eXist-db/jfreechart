JFreeChart extension
==========

The eXist-db [JFreeChart](http://www.jfree.org/jfreechart/) extension has been part of the eXist-db distribution for a long time, but due to the size of the third party libraries it hasn't been enabled by default. 

This project two XAR files:
- a XAR file containing all required JAR files (can  be used standalone) 
- and a small Showcase web application XAR.

The XAR file is shipped with [JFreeChart](http://www.jfree.org/jfreechart/) *1.0.17* and  [Apache™ Batik SVG Toolkit](https://xmlgraphics.apache.org/batik/) version *1.8*.


Installable XAR files are available on the [releases](../../releases) page. Contributions are welcome via [Pull requests](../../pulls); bugreports, ideas and suggestions can be files using the [Issues](../../issues) page.

==========


The extention can be build with the following command (Java7 is required):

```shell
ant setup
ant
```


Example code:

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
