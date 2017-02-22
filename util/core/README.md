# Scala Core Utilities

Most basic utilities for Scala, stuff missing from Scala SDK/core:

* `AutoCloseable` implementation, the counterpart of Java 7's _try with 
   resources_ idiom.
* Common datetime idioms: obtaining a now value, formatting and parsing.

Current (latest) version is `1.0.0`

# Usage

Link to the artifact in your POM:

```xml
        <dependency>
            <groupId>com.github.ebaydatameta</groupId>
            <artifactId>scala-core_2.11</artifactId>
            <version>1.0.0</version>
        </dependency>
```

Or your `build.sbt`:

```
  "com.github.ebaydatameta" %% "scala-core" % "1.0.0"
```  

For examples how to use any of the features, see [the tests](FIXME).


