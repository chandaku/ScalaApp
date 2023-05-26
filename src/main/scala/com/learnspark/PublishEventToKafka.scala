package com.learnspark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}

object PublishEventToKafka extends App {
  val spark: SparkSession = SparkSession.builder()
    .master("local[1]").appName("SparkByExamples.com")
    .getOrCreate()
  import org.apache.spark.sql.functions.lit
  val df = spark
    .readStream
    .format("rate")
    .option("numPartitions", 5)
    .option("rowsPerSecond", 5)
    .load()
    .withColumn("key", lit("SomeKey"))
    .withColumn("value", lit("SomeValue"))

  df
    .select()
  .writeStream
    .format("org.apache.spark.sql.kafka010.DefaultKafkaSourceProvider")
    .trigger(Trigger.ProcessingTime(5000))
    .option("kafka.bootstrap.servers", "localhost:9094")
    .option("kafka.max.request.size", "209715200")
    .option("kafka.request.timeout.ms", 300000)
    .option("kafka.linger.ms", "1000")
    .option("kafka.batch.size", "163840")
    .option("kafka.buffer.memory", "209715200")
    .option("topic", "TestMe")
    .option("checkpointLocation","/tmp/checkpoints/")
    .outputMode(OutputMode.Append())
    .queryName("Change Event")
    .start()


}
