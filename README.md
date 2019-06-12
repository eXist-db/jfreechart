JFreeChart extension
==========

The eXist-db [JFreeChart](http://www.jfree.org/jfreechart/) extension has been part of the eXist-db distribution for a long time, but due to the size of the third party libraries it hasn't been enabled by default. 

This project provides two XAR files:
- a XAR file containing all required java libraries files 
- a compact Showcase XAR.

The XAR file is shipped with [JFreeChart](http://www.jfree.org/jfreechart/) version *1.0.19* and Apache [Batik SVG Toolkit](https://xmlgraphics.apache.org/batik/) version *1.11*.

Installable XAR files are available on the [releases](../../releases) page. Contributions are welcome via [Pull requests](../../pulls); bugreports, ideas and suggestions can be filed using the [Issues](../../issues) page.


Some documentation can be found on the wiki of the [JFreeChart extension](https://github.com/eXist-db/jfreechart/wiki).

* Requirements: Java 8, Apache Maven 3.3+, Git.

If you want to create an EXPath Package for the app, you can run:

```bash
$ mvn package
```

There will be a `.xar` file in the `target/` sub-folder.

