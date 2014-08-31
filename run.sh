#!/bin/bash
# Runs $class on the cluster of $master with given command line arguments.
# Logs are saved to "$class".log and "$class".err in the current directory.

## configure script as per your project configuration.
## TwitterTopTags-assembly-0.1.0.jar -> This jar file will be compiled jar file created using assembly.
## Jar filename might change as per your project. Update it as per your project configuration. 

class="TwitterTopTags"

master="spark://ukko160:7077"

ROOT_DIR_NAME="YOUT-PROJECT-ROOT-DIRECTORY"

jar=/cs/taatto/scratch/msingh/$ROOT_DIR_NAME/target/scala-2.10/TwitterTopTags-assembly-0.1.0.jar
/cs/taatto/scratch/msingh/spark-1.0.2-bin-hadoop1/bin/spark-submit --class "$class" \
--master "$master" --jars /cs/taatto/scratch/msingh/$ROOT_DIR_NAME/spark-streaming-twitter_2.10-1.0.2.jar   "$jar" # \

## uncomment to redirect output or error to files. 
# $@ 1>"$class".log 2>"$class".err
