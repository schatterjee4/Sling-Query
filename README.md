# SlingQuery
[![Build Status](https://travis-ci.org/Cognifide/Sling-Query.png?branch=master)](https://travis-ci.org/Cognifide/Sling-Query)

SlingQuery is a Sling resource tree traversal tool inspired by the [jQuery](http://api.jquery.com/category/traversing/tree-traversal/).

## Introduction

Recommended way to find resources in the Sling repository is using tree-traversal methods, like `listChildren()` and `getParent()` rather than JCR queries. The latter are great for listing resources with given properties, but we can't leverage the repository tree structure with such queries. On the other hand, using tree-traversal method is quite verbose. Consider following code that takes an resource and returns its first ancestor, being `cq:Page`, with given `jcr:content/cq:template` attribute:

    Resource resource = ...;
    while ((resource = resource.getParent()) != null) {
        if (!resource.isResourceType("cq:Page")) {
            continue;
        }
        Resource template = resource.getChild("jcr:content/cq:template");
        if (template != null && "my/template".equals(template.adaptTo(String.class))) {
            break;
        }
    }
    if (resource != null) {
        // we've found appropriate ancestor
    }

SlingQuery is a tool that helps creating such queries in a more concise way. Above code could be written as:

    $(resource).closest("cq:Page[jcr:content/cq:template=my/template]")

Dollar sign is a static method that takes the resource array and creates SlingQuery object. The `closest()` method returns the first ancestor matching the selector string passed as the argument.

SlingQuery is inspired by the jQuery framework. jQuery is the source of method names, selector string syntax and the dollar sign method used as a collection constructor.

## Installation

Add following Maven dependency to your `pom.xml`:

	<dependency>
		<groupId>com.cognifide.cq</groupId>
		<artifactId>sling-query</artifactId>
		<version>1.0.0</version>
	</dependency>

## Documentation

* [Basic ideas](https://github.com/Cognifide/Sling-Query/wiki/Basic-ideas)
* [Method list](https://github.com/Cognifide/Sling-Query/wiki/Method-list)
* [Selector syntax](https://github.com/Cognifide/Sling-Query/wiki/Selector-syntax)
* [Modifier list](https://github.com/Cognifide/Sling-Query/wiki/Modifier-list)

## External resources

* See the [Apache Sling website](http://sling.apache.org/) for the Sling reference documentation. Apache Sling, Apache and Sling are trademarks of the [Apache Software Foundation](http://apache.org).
