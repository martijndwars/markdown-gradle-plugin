# Markdown Gradle Plugin

A Gradle plugin to compile Markdown to HTML and PDF. This plugin is a wrapper around:

* [Atlassian's CommonMark library](https://github.com/atlassian/commonmark-java)
* [Flying Saucer library](https://github.com/flyingsaucerproject/flyingsaucer)

[![Build Status](https://travis-ci.com/MartijnDwars/markdown-gradle-plugin.svg?branch=master)](https://travis-ci.com/MartijnDwars/markdown-gradle-plugin)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/nl/martijndwars/markdown/nl.martijndwars.markdown.gradle.plugin/maven-metadata.xml.svg?label=gradle)](https://plugins.gradle.org/plugin/nl.martijndwars.markdown)

## Plugins

Applying this plugin implicitly applies the base plugin to your project.

## Tasks

This plugin adds the following tasks:

* _compileMarkdownToHtml_: Compile a Markdown file to a HTML file.
* _compileHtmlToPdf_: Compile a HTML file to a PDF file.
* _compileMarkdownToPdf_: Compile a Markdown file to a PDF file (uses above tasks internally).

## Example

```groovy
plugins {
    id 'nl.martijndwars.markdown' version '0.1.0'
    id 'maven-publish'
}

group = 'org.example'
version = '1.2.3'
description = 'Simple example'

repositories {
    jcenter()
}

compileMarkdownToHtml {
    inputFile = file("text.md")
}

compileHtmlToPdf {
    outputFile = file("$buildDir/text.pdf")
}

compileHtmlToPdf.inputFile = compileMarkdownToHtml.outputFile
compileMarkdownToHtml.outputFile = file("$buildDir/text.html")

task textZip(type: Zip) {
    destinationDirectory = file("$buildDir/dist")

    from compileHtmlToPdf.outputFile
}

publishing {
    publications {
        simple(MavenPublication) {
            artifact textZip
        }
    }
}
```
