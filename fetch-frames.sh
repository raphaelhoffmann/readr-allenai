ALLENAI=`pwd`

function usage {
echo "Usage: ./fetch-frames.sh DATASET"
exit 1
}

DATASET=$1
if [ -z "$DATASET" ];
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
PROJ=${DATASET//-/_}
DATADIR=$ALLENAI/data

sbt "runMain allenai.util.FetchFrames $HOST $USER $PASSWORD $NS $PROJ $DATADIR/frames/$DATASET"

