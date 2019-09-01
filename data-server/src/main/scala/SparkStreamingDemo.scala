import org.apache.spark.streaming.{Seconds, StreamingContext}

class SparkStreamingDemo {

}

object SparkStreamingDemo{
  def main(args: Array[String]): Unit = {
    // Create a StreamingContext with a local master
    // Spark Streaming needs at least two working thread
    val sc = new StreamingContext("local[2]", "NetworkWordCount", Seconds(10) )
    // Create a DStream that will connect to serverIP:serverPort, like localhost:9999  nc -l 9999
    val lines = sc.socketTextStream("192.168.244.130", 9999)
    // Split each line into words
    // 以空格把收到的每一行数据分割成单词
    val words = lines.flatMap(_.split(" "))
    // 在本批次内计单词的数目
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    // 打印每个RDD中的前10个元素到控制台
    wordCounts.print()

    sc.start()
    sc.awaitTermination()
  }
}
