#!/bin/sh

ALLENAI=`pwd`

set -e    # exit if anything fails

DOWNLOAD=$ALLENAI/data/download
mkdir -p $DOWNLOAD

cd $DOWNLOAD
if [ -f "$DOWNLOAD/SimpleWikipedia.zip" ];
then
  echo "SimpleWikipedia.zip exists, skipping download."
else
  wget http://utility.allenai.org/wiki/images/e/e5/SimpleWikipedia.zip
fi

if [ -f "$DOWNLOAD/Text-resources-11-13-13.zip" ];
then
  echo "Text-resources-11-13-13.zip exists, skipping download."
else
  wget http://utility.allenai.org/wiki/images/6/62/Text-resources-11-13-13.zip
fi

if [ -f "$DOWNLOAD/simplewikipedia-pages/simplewiki-barrons-headers.txt" ];
then
  echo "simplewikipedia-pages exists, skipping unzip."
else
  unzip SimpleWikipedia.zip
fi
if [ -f "$DOWNLOAD/aura-definitions/concept-definition-headers.txt" ];
then
  echo "wikipedia-pages exists, skipping unzip."
else
  unzip Text-resources-11-13-13.zip
fi

cd $ALLENAI
