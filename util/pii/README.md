# DataMeta PII Utilities

Most basic utilities for Scala, stuff missing from Scala SDK/core:

* `AutoCloseable` implementation, the counterpart of Java 7's _try with resources_ idiom.
* Common datetime idioms: formatting and parsing.

Current (latest) version is `1.0.0`

# Usage

Link to the artifact in your POM:

```xml
        <dependency>
            <groupId>com.github.ebaydatameta</groupId>
            <artifactId>scala-pii_2.11</artifactId>
            <version>1.0.0</version>
        </dependency>
```

Or your `build.sbt`:

```
  "com.github.ebaydatameta" %% "scala-pii" % "1.0.0"
```  

To use the `org.ebay.datameta.util.pii.PiiValue` class, you would first write up your PII fields definition in
DataMetaPii which would look like [&rarr;this](https://github.com/eBayDataMeta/DataMeta-gems/blob/master/meta/security/pii/test/registryShowcase.dmPii),
then you would export it to Scala using the [`dataMetaPii` gem](https://github.com/eBayDataMeta/DataMeta-gems/tree/master/meta/security/pii)
which would give you the PII field keys and mappings to the object with impact level and other properties.

Use the exported Scala object's constants representing the PII keys as the first parameter to `org.ebay.datameta.util.pii.PiiValue`
and the second is the related value whatever type it is.

See [the tests](FIXME) for a quick example.


