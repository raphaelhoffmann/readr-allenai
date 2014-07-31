ALLENAI=`pwd`

function usage {
  echo "Usage: ./put-frames.sh FROMLOCALDATASET TODATASET"
  exit 1
}

FROMLOCALDATASET=$1
if [ -z "$FROMLOCALDATASET" ];
then
  usage
fi

TODATASET=$2
if [ -z "$TODATASET" ];
then
  usage
fi


echo
echo "==> reading config"
source settings
if [ -z "$HOST" ];
then
echo "HOST not set"
exit 1
fi


# for a name of the project, we take the original dataset
# name and convert - (hyphens) to _ (underscores).
PROJ=${TODATASET//-/_}
DATADIR=$ALLENAI/data

sbt "runMain allenai.util.PutFrames $HOST $USER $PASSWORD $NS $PROJ $DATADIR/frames/$FROMLOCALDATASET"
