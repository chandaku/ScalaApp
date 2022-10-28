package com.learnscala.apps
import com.learnscala.main._
import com.learnscala.redis.RedisClientPool
import com.learnscala.settings.MainSettings
import pureconfig.ConfigConvert.fromReaderAndWriter

import scala.collection.JavaConverters.iterableAsScalaIterableConverter
import scala.collection.immutable.Nil.toList

object MyApp extends App[MainSettings] {
    def main(args : Array[String]) : Unit = {
      println( "Hello World!" )
      val app : App[MainSettings] = new App[MainSettings] {}
      val appSettings =  app.getSetting()
      println(app.getSetting().redisSetting.yamlPath)
      implicit val redisSetting = app.getSetting().redisSetting
      val redisClient = RedisClientPool.getOrCreate("RedisSampleTestClientId");

      val resultsFromRedis = redisClient.getKeys.getKeysByPattern("sie-topo:idsByTplName:Enterprise_Customer2:version:*").asScala
      println("######### FOUND RESULTS FROM REDIS" + resultsFromRedis.size)
      val topologyTemplateTupleExcludingVersionIt = resultsFromRedis.filterNot(x => x.equals("sie-topo:idsByTplName:Enterprise_Customer2:version:1.0.0")).toList
      println("######### Filteration worked well as well" + topologyTemplateTupleExcludingVersionIt.size)

    }
}
