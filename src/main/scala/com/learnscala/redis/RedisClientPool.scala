package com.learnscala.redis

import com.learnscala.settings.RedisSettings
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.client.codec.Codec
import org.redisson.config.Config

import java.io.FileInputStream
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._

object RedisClientPool {

  @transient lazy val clientPool = new ConcurrentHashMap[String, RedissonClient]()

  def getOrCreate(clientId: String, codec: Option[Codec] = None, yamlPath: Option[String] = None)(implicit settings: RedisSettings): RedissonClient =
    clientPool.computeIfAbsent(clientId, new java.util.function.Function[String,RedissonClient] {
      @Override def apply(t :String) :RedissonClient = { 

      println(s"########## INSTANCIATE ############ ")
      val uri = this.getClass.getClassLoader.getResourceAsStream(settings.yamlPath)
      if(uri !=null) {
        val config: Config = Config.fromYAML(uri)
        codec.foreach(config.setCodec)
        println(s"RedisClientPool : ${config.toJSON}")
        Redisson.create(config)
      }
      else {
        val file = new FileInputStream(yamlPath.getOrElse(settings.yamlPath))
        val config: Config = Config.fromYAML(file)
        codec.foreach(config.setCodec)
        println(s"RedisClientPool : ${config.toJSON}")
        Redisson.create(config)
      }

    }})

  /**
   * not-thread safe
   */
    
  def release(clientId: String) {
     
    val client = clientPool.remove(clientId) 
    if (client != null) 
      client.shutdown()
  }
  
  def shutdown() {
    val allConnections = clientPool.values().asScala.toList // cloned explicitly
    clientPool.clear()
    allConnections.foreach(_.shutdown)

  }
}
