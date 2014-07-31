package allenai.example

import com.readr.client._
import com.readr.model.Project
import com.readr.model.layer._
import com.readr.client.meaning.frames
import com.readr.client.meaning.frameArgs
import com.readr.client.meaning.frameValences
import com.readr.model.frame.Frame
import com.readr.model.frame.FrameArg
import com.readr.model.frame.FrameType

object AnnotationsForFrame {

//  private def usage =
//    println("CreateDB HOST USER PASSWORD NS PROJ DIR")
    
  def main(args:Array[String]) = {
    val host = args(0)        // http://preview.readr.com:9000
    val user = args(1)        // a
    val password = args(2)    // a
    val ns = args(3)          // allenai
    val proj = args(4)        // barrons-4th-grade
    val dir = args(5)         // /Users/.../data
    
    Client.baseUrl = host + "/api"
    Client.user = user
    Client.password = password
    
    implicit val p = Project(ns, proj)
    
    Client.open
    
    
    
    val f = Frame("effect-55", "",
"""In the hot weather our bodies sweat perspiration bringing water to our[our] skin.
("our bodies"/?x "sweat" "perspiration" [ "In the hot weather" ] )	""/EFFECT-55	("our bodies"/?x "bring" "water" [ "to our[our] skin" ] )""", FrameType.Verb
    )
    //val frameID = frames.create(f)
    val rv = frames.addMany(Seq(f))
    val frameID = rv(0)
    frameArgs.add(frameID, FrameArg(-1, frameID, 0, "r", "root", true))
    frameArgs.add(frameID, FrameArg(-1, frameID, 1, "c", "complement", true))
    frameValences.update(frameID, """dep(r, "xcomp", c) & partOfSpeech(c, "VBG") & ~(exists d dep(c, "ccomp", d))

dep(d, "compmod", r)&dep(d, "det", c)&token(d,'.')
dep(a, "xcomp", b)""")

    Client.close        
  }
  
}