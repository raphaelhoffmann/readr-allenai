# readr-allenai

A set of examples that show how to use the Readr API to connect with Readr Cloud.
Also, scripts to download and process allenai's datasets.

## Prerequisites

The following has been tested on MacOS X with

Scala 2.10.4, sbt 0.13, Spark 1.0.1

Add `addSbtPlugin("com.frugalmechanic" % "fm-sbt-s3-resolver" % "0.3.0")` to `~/.sbt/0.13/plugins/build.sbt` to enable fetching dependencies from S3.

You must have Apache spark installed in a directory if you would like to process and push new datasets to readr. Fetch spark at `https://spark.apache.org/downloads.html`.

If you would like to run Kevin's preprocessing scripts for the wikipedia corpora, you must also install xml, gsed. 

## API examples

See the examples in `src/main/scala/allenai/example` to see how to push patterns, fetch results, etc. 

## Pushing allenai corpora into readr

1. In `conf/application.conf` set the user and password fields.

2. Run `sbt compile` to see if you can fetch all dependencies. (Note: you must have your Allenai Nexus credentials set up for this to work.)

3. Run `./run.sh` to download the barrons corpus, process the barrons corpus, and upload the indices to Readr Cloud.

## Pushing and Pulling Extraction Rules

Readr Cloud makes it easy to create, manipulate, and test extraction rules. When you are done using Readr Cloud you can fetch the rules you have created to store them locally.

`./fetch_frames.sh`

Finally, if you would like to push this rule set to a dataset in Readr Cloud (it can be the same or a different dataset), run

`./push_frames.sh`
 
