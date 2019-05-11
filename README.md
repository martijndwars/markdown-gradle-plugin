# Markdown Gradle Plugin

A Gradle plugin to compile Markdown to HTML and PDF. This plugin is a wrapper around:

* [Atlassian's CommonMark library](https://github.com/atlassian/commonmark-java)
* [Flying Saucer library](https://github.com/flyingsaucerproject/flyingsaucer)

## Plugins

Applying this plugin implicitly applies the base plugin to your project.

## Tasks

This plugin adds the following tasks:

* _compileMarkdownToHtml_: Compile Markdown file to HTML file.
* _compileHtmlToPdf_: Compile HTML file to PDF file.

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
