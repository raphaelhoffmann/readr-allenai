ALLENAI=`pwd`

function ask {
  echo $1
  read -n 1 -r
  if [[ $REPLY =~ ^[Yy]$ ]]
  then
    return 1;
  else
    exit
    echo "Abort.."
  fi
}

function usage {
  echo "Usage: ./run-dataset.sh DATASET HEADERSFILE HEADERSCHARSET SENTENCESFILE SENTENCESCHARSET"
  exit 1
}

DATASET=$1
if [ -z "$DATASET" ];
then
  usage
fi 
HEADERSFILE=$2
if [ -z "$HEADERSFILE" ];
then
  usage
fi
SENTENCESFILE=$4
if [ -z "$SENTENCESFILE" ];
then
  usage
fi
HEADERSCHARSET=$3
if [ -z "$HEADERSCHARSET" ];
then
  usage
fi
SENTENCESCHARSET=$5
if [ -z "$SENTENCESCHARSET" ];
then
  usage
fi

echo
echo "==> reading config"
source conf/application.conf
if [ -z "$HOST" ];
then
  echo "HOST not set"
  exit 1
fi

echo
echo "==> fetching datasets"
scripts/fetch.sh

echo
echo "==> creating sources"
DATADIR=$ALLENAI/data
if [ -d "$DATADIR/source/$DATASET" ];
then
  echo "Sources exist, skipping source generation. "
else
  sbt "runMain allenai.util.CreateSource $DATADIR/download/$DATASET/$HEADERSFILE $HEADERSCHARSET $DATADIR/download/$DATASET/$SENTENCESFILE $SENTENCESCHARSET $DATADIR/source/$DATASET"
fi

echo
echo "==> creating features"
if [ -d "$DATADIR/processed/$DATASET" ];
then
  echo "Processed features exists, skipping spark processing."
else
  echo "Running spark"
  scripts/spark.sh $DATADIR/source/$DATASET $DATADIR/processed/$DATASET
fi

echo
echo "==> creating project in db"
# for a name of the project, we take the original dataset
# name and convert - (hyphens) to _ (underscores).
PROJ=${DATASET//-/_}
echo "Setting name to $PROJ"
CMD="curl -s -u $USER:$PASSWORD $HOST/api/$NS/$PROJ/exists"
EXISTS=`$CMD`
if [ "$EXISTS" = "true" ]
then
  ask "Project exists in db. Overwrite will remove existing rules and annotations. Continue? [y/N] "
fi

sbt "runMain allenai.util.CreateDB $HOST $USER $PASSWORD $NS $PROJ $DATADIR/processed/$DATASET"

cd $ALLENAI
