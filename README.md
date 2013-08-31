orion-jclouds
=====


jclouds blobstore implementation for orion based back-ends. This implementation is based on maven archetype of jclouds: http://jclouds.incubator.apache.org/documentation/devguides/creating-providers-with-maven/. 

basic setup
=====

Please read <http://jclouds.incubator.apache.org/documentation/userguide/blobstore-guide/> for more information


BlobStoreContext context = ContextBuilder.newBuilder("orionblob")
                 .credentials(identity, credential)
                 .buildView(BlobStoreContext.class);



Default end-point is http://orionhub.org/ this can be overridden by setting in the end-point in the builder.
The general blob naming convention is like relative paths which start without a slash.

In general names with .metadata and slashes are forbidden. 



mapping to orion concepts
===

A blob is either map to a file or to a folder. Containers are map to the top-level folders in the user workspaces and they can be called as projects as well.

some remarks
====



A file and older with the same name in the same directory are forbidden. In such a case a request removes the existing duplicate and adds the requested item. 




