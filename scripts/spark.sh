SPARKDIR="/Users/raphael/readr-other/spark/spark-1.0.0"
ASSEMBLY="/Users/raphael/readr/spark/spark-readr/target/spark-readr-assembly-1.0-SNAPSHOT.jar"
INDIR=$1
OUTDIR=$2

cat -v scripts/spark-template | sed -e "s,\${INDIR},$INDIR," -e "s,\${OUTDIR},$OUTDIR," | SPARK_MEM=8G $SPARKDIR/bin/spark-shell --master local[6] --jars $ASSEMBLY --driver-java-options "-Dspark.serializer=org.apache.spark.serializer.KryoSerializer -Dspark.kryo.registrator=com.readr.spark.MyRegistrator -Dspark.kryoserializer.buffer.mb=16"

reset
