package com.learnscala.main

import pureconfig._
import pureconfig.generic.auto._

import scala.reflect.ClassTag

abstract class App [T:ClassTag:ConfigReader] {

  protected val settings : T = loadSettings

  protected def loadSettings: T = {
    loadConfigOrThrow[T]
  }

  def getSetting() : T = {
    settings
  }

}


