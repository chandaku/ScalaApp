package com.learnscala.main

import pureconfig._
import scala.reflect.ClassTag

abstract class App [T:ClassTag:ConfigReader] {

  protected val settings : T = loadSettings

  protected def loadSettings: T = {
    ConfigSource.default.loadOrThrow[T]
  }

  def getSetting() : T = {
    settings
  }

}


