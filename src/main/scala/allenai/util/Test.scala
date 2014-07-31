package allenai.util

import java.util.regex.Pattern

object Test {

    def main(args:Array[String]) = {
      val text = "bbaa\n<test>this is it</test>\nba"
      
      val p = Pattern.compile("<test>(.*)</test>")

      val m = p.matcher(text)
      while (m.find) {
        println(m.group(1))
      }
      
    }
}