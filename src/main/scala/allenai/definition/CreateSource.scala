package allenai.definition

import scala.collection.mutable._
import scala.io.Source
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io._
import com.readr.model.annotation.TextAnn
import com.readr.model.annotation.TextFragment
import com.readr.model.annotation.TextFragmentAnn
import com.readr.model.Offsets
import com.readr.model.annotation.Annotations
import com.readr.client.util.AnnotationSequenceFileWriter

object CreateSource {
  
  def main(args:Array[String]) = {
    val file = args(0)
    val charset = args(1)
    val outDir = args(2)
    
    val conf = new Configuration()

    val sfText = new AnnotationSequenceFileWriter(conf, outDir + "/data.col0.TextAnn")
    val sfTextFragment = new AnnotationSequenceFileWriter(conf, outDir + "/data.col1.TextFragmentAnn")
    val sfSource = new AnnotationSequenceFileWriter(conf, outDir + "/data.col2.Source")

    for (clazz <- Annotations.annWithDependentClazzes) {
      sfText.register(clazz)
      sfTextFragment.register(clazz)
      sfSource.register(clazz)
    }
    
    val sb = new StringBuilder
    for (line <- Source.fromFile(file, charset).getLines()) {
      sb.append(line)
      sb.append("\n")
    }
    
    val id = 0
    val ta = new TextAnn(sb.toString)
    val tfa = new TextFragmentAnn(Array(TextFragment("all",Offsets(0, ta.text.length), true)))
    val so = com.readr.model.annotation.Source("defs", "", "")

    sfText.write(id, ta)
	sfTextFragment.write(id, tfa)
	sfSource.write(id, so)

    sfText.close
    sfTextFragment.close
    sfSource.close
  } 
}