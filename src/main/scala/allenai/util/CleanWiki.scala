package allenai.util

import java.util.regex.Pattern
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.FileOutputStream

object CreateWikiSource {

  val dir = "/Users/raphael/readr/import/import-allenai/data/download/test"
  val file = dir + "/out2"
  val out1 = dir + "/p-headers.txt"
  val out2 = dir + "/p-fragments.txt"
  
  val p1 = "^<id>(.*)</id>$".r
  val p2 = "^<title>(.*)</title>$".r
  val p3 = "^<section>(.*)</section>$".r
  val p4 = "^<subsection>(.*)</subsection>$".r
  val p5 = "^<subsubsection>(.*)</subsubsection>$".r
  val p6 = "^Category:(.*)$".r
  val p7 = "^Image:(.*)$".r
  val p8 = "^thumb$".r
  val p9 = "^\\*.*".r
    
  def main(args:Array[String]) = {

    val br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))
    val w1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out1)))
    val w2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out2)))
    var l:String = null
    
    var id = 0
    var id_sub = 0
    var id_subsub = 0
    var id_subsubsub = 0
    var id_subsubsubsub = 0
    var prevLine = ""
    
    while ({l = br.readLine(); l != null}) {
      l match {
        case p1(c) => {
          id = Integer.parseInt(c.trim)
          id_sub = 0
          id_subsub = 0
          id_subsubsub = 0
          id_subsubsubsub = 0
        }
        case p2(c) => {
          w1.write(id + "\t" + c.trim + "\n")
        }
        case p3(c) => {
          id_sub += 1
          id_subsub = 0
          id_subsubsub = 0
          id_subsubsubsub = 0
          w1.write(id + "." + id_sub + "\t" + c.trim + "\n")
        }
        case p4(c) => {
          id_subsub += 1
          id_subsubsub = 0
          id_subsubsubsub = 0
          w1.write(id + "." + id_sub + "." + id_subsub + "\t" + c.trim + "\n")
        }
        case p5(c) => {
          id_subsubsub += 1
          id_subsubsubsub = 0
          w1.write(id + "." + id_sub + "." + id_subsub + "." + id_subsubsub + "\t" + c.trim + "\n")
        }
        case p6(c) => {}
        case p7(c) => {}
        case p8(c) => {}
        case p9(c) => {}
        case _ => {
          l = l.trim
          
          if (l.equals("")) {
            if (prevLine.equals("")) {} // ignore empty line
            else {
            	id_subsubsubsub +=1
				w2.write(id + "." + id_sub + "." + id_subsub + "." + id_subsubsub + "\t" + "\n")
            }
          } else {
            id_subsubsubsub +=1
            w2.write(id + "." + id_sub + "." + id_subsub + "." + id_subsubsub + "\t" + l + "\n")
          }
          prevLine = l
          // todo
        }
      }
      
//      //<id>..</id>
//      val m1 = p1.matcher(l)
//      if (m1.find) {
//        id = m1.group(1).trim
//      } else {
//         val m2 = p2.matcher(l)
//         if (m2.find) {
//           println(id + "\t" + m2.group(1))
//         } else {
//           val m3 = p3.matcher(l)
//           if (m3.find) {
//             println()}
//           }
//         }
      
      //<title>...</title>
      
      //<section>Jagged Little Pill</section>
      //<subsection>Selected songs</subsection>
      //<subsubsection>Selected songs</subsection>
      
      //*
      
      //Category:...
      
      //thumb
      //Image:
      //__TOC__
      
    }
      
    br.close
    w1.close
    w2.close
  }
}