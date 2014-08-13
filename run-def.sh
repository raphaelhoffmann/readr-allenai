echo
echo "======= definitions ========"

DATASET="definitions"
SENTENCESFILE="../resources/definitions"
SENTENCESCHARSET="utf-8"

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

echo
echo "==> reading config"
source conf/application.conf
if [ -z "$HOST" ];
then
echo "HOST not set"
exit 1
fi

echo
echo "==> creating sources"
DATADIR=$ALLENAI/data
if [ -d "$DATADIR/source/$DATASET" ];
then
echo "Sources exist, skipping source generation. "
else
sbt "runMain allenai.definition.CreateSource $DATADIR/$SENTENCESFILE $SENTENCESCHARSET $DATADIR/source/$DATASET"
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
