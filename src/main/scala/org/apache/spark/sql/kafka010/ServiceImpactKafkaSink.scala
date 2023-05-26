package org.apache.spark.sql.kafka010

import org.apache.spark.internal.Logging
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.execution.streaming.Sink
import org.slf4j.{Logger, LoggerFactory}

class ServiceImpactKafkaSink(kafkaSink: KafkaSink) extends Sink with Logging  {

  @transient lazy val logger: Logger = LoggerFactory.getLogger(getClass.getCanonicalName)


  @volatile private var latestBatchId = -1L

  override def toString: String = "KafkaSink"

  override def addBatch(batchId: Long, data: DataFrame): Unit = {
    commit()
  }

  private def commit(): Unit = {
    println("Welcome to the world of Love")
  }
}
