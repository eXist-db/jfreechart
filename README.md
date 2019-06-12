JFreeChart extension
==========

[![Build Status](https://travis-ci.com/eXist-db/jfreechart.svg?branch=develop)](https://travis-ci.com/eXist-db/jfreechart)
[![Java 8](https://img.shields.io/badge/java-8-blue.svg)](http://java.oracle.com)
[![License](https://img.shields.io/badge/license-LGPL%202.1-blue.svg)](https://www.gnu.org/licenses/lgpl-2.1.html)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/23edb120de61468e8675acc868de5074)](https://app.codacy.com/app/dannes/jfreechart?utm_source=github.com&utm_medium=referral&utm_content=eXist-db/jfreechart&utm_campaign=Badge_Grade_Dashboard)

This XAR extension provides [JFreeChart](http://www.jfree.org/jfreechart/) functionality for eXist-db. 

The XAR file is shipped with [JFreeChart](http://www.jfree.org/jfreechart/) version *1.5* and 
Apache [Batik SVG Toolkit](https://xmlgraphics.apache.org/batik/) version *1.11*.

Installable XAR files are available on the [releases](../../releases) page or via the eXist-db public repository.

# Documentation 
Some documentation can be found on the wiki of the [JFreeChart extension](https://github.com/eXist-db/jfreechart/wiki).

# Requirements
- Java 8
- Apache Maven 3.3+
- Git.
- for XAR version 0.5 and newer: eXist-db v4.6 or newer.

# Contributions
Contributions are welcome via [Pull requests](../../pulls); bugreports, ideas and suggestions can be 
filed using the [Issues](../../issues) page.


# Build
If you want to create an EXPath Package for the app, you can run:

```bash
$ mvn package
```

There will be a `.xar` file in the `target/` sub-folder.

