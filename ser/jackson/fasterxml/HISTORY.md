# RELEASES History

## `2.0.0` released `2017-04-18 Tue` by [`mub`](https://github.com/mub)
* 1 major change:
    * Added the `ignoreUnknown` parameter to the [`Jsonable[T]` trait](https://github.com/eBayDataMeta/DataMeta-domScala/blob/master/ser/jackson/fasterxml/src/main/scala/org/ebay/datameta/ser/jackson/fasterxml/Jsonable.scala) and changed the trait method name, otherwise the code won't compile: the Scala compiler apparently 
    has trouble with overloaded methods with default parameters. 

## `1.0.1` released `2017-04-12 Wed` by [`mub`](https://github.com/mub)
* 1 update:
    * added the `writeCollection` method to [`JacksonUtil`](https://github.com/eBayDataMeta/DataMeta-domScala/blob/master/ser/jackson/fasterxml/src/main/scala/org/ebay/datameta/ser/jackson/fasterxml/JacksonUtil.scala) 
        
* `1.0.0` - initial release, `2017-04-04 Tue` by [`mub`](http://github.com/mub)
