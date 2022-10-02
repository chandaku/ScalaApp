package com.learnscala.apps
import com.learnscala.main._
import pureconfig.generic.auto.exportReader
import com.learnscala.settings.MainSettings

object MyApp extends App[MainSettings] {
    def main(args : Array[String]) : Unit = {
      println( "Hello World!" )
      val app : App[MainSettings] = new App[MainSettings] {}
      val appSettings =  app.getSetting()
      println(app.getSetting().application.name)
    }
}
