package allenai.example

import scala.collection.mutable._
import scala.io.Source
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io._
import com.readr.model.annotation._
import com.readr.model.Offsets
import com.readr.model.annotation.Annotations
import com.readr.client.util.AnnotationSequenceFileReader
import com.readr.client.Client
import com.readr.model.Project
import com.readr.client.meaning.frames
import com.readr.model.frame.Frame
import com.typesafe.config.ConfigFactory
import com.readr.client.meaning.frames
import com.readr.client.meaning.frameValences
import com.readr.model.annotation.AnnotationConfirmationType
import com.readr.model.annotation.AnnotatedSentence
import com.readr.model.annotation.FrameMatchFeature
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream
import com.readr.client.document.layerDefaults
import com.readr.client.document.frameMatchFeatures
import com.readr.model.FrameMatchFeatureLayerRef

object Example7PutPatternAnnotations {
  
  def main(args:Array[String]) = {
    val conf = ConfigFactory.load
    val host = conf.getString("HOST")
    val user = conf.getString("USER")
    val password = conf.getString("PASSWORD")
    val ns = conf.getString("NS")
    val proj = conf.getString("EXAMPLE_PROJ")
    
    Client.baseUrl = host + "/api"
    Client.user = user
    Client.password = password

    implicit val p = Project(ns, proj)

    val layerID = layerDefaults("FrameMatchFeature", "Manual")    
    implicit val lay = FrameMatchFeatureLayerRef(layerID)
        
    Client.open        
    
    val frameID = frames.idByName("1sttest")
    if (frameID == -1)
      println("Frame not found")

    val dir = "/tmp/test"
    val f = new File(dir + "/annotations")
    val reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"))
    val docPattern = Pattern.compile("doc (.*), sentNum (.*), sentenceTokenOffset (.*), truth (.*)")
    val argPattern = Pattern.compile("arg (.*): (.*)")
    
    var documentID = -1
    var sentNum = -1
    var sentenceTokenOffset = -1
    var truth = false
    var args = ArrayBuffer[FrameMatchFeatureArg]()
    var l:String = null
    while ({ l = reader.readLine(); l != null} ) {
      if (l.startsWith("//")) {
        // ignore sentence
        if (documentID != -1) {
          val fmf = FrameMatchFeature(
            instanceID = -1,
            frameID,
            truth = truth,
            priority = 0,
            args = args
          )
          frameMatchFeatures.add(fmf)
          args.clear
        }
        
      } else if (l.startsWith("doc ")) {
    	val m = docPattern.matcher(l)
    	documentID = m.group(1).toInt
    	sentNum = m.group(2).toInt
    	sentenceTokenOffset = m.group(3).toInt
    	truth = m.group(4).toBoolean
              
      } else if (l.startsWith("arg ")) {
        val m = argPattern.matcher(l)
        val argNum = m.group(1).toByte
        val pos = m.group(2).toInt
        args += FrameMatchFeatureArg(argNum, documentID, sentenceTokenOffset + pos)
      }
    }
    val fmf = FrameMatchFeature(
      instanceID = -1,
      frameID,
      truth = truth,
      priority = 0,
      args = args
    )
    frameMatchFeatures.add(fmf)
    
    reader.close
    
    Client.close     
  } 
}