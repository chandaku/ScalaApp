package org.apache.spark.sql.kafka010

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.streaming.OutputMode

class DefaultKafkaSourceProvider extends KafkaSourceProvider {


  override def createSink(
                           sqlContext: SQLContext,
                           parameters: Map[String, String],
                           partitionColumns: Seq[String],
                           outputMode: OutputMode): Sink = {
    val kafkaSink = super.createSink(sqlContext,
      parameters,
      partitionColumns,
      outputMode)
    new ServiceImpactKafkaSink(kafkaSink.asInstanceOf[KafkaSink])
  }


  override def shortName(): String = "custom_kafka"
}
