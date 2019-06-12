(: 
    Example code for jfreechart module 
    Load the data files into /db
    
    $Id$ 
:)
import module namespace  jfreechart = "http://exist-db.org/xquery/jfreechart";

let $config := <configuration>
    <orientation>Horizontal</orientation>
    <height>500</height>
    <width>500</width>
    <title>BarChart</title>
</configuration>

return
    jfreechart:stream-render("BarChart", $config, doc('categorydata.xml'))
