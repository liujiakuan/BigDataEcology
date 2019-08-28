import java.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession, types}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField}

class CreateDF {

}

object CreateDF {
  def apply: CreateDF = new CreateDF()

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("createDataFrame").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    //    val sc = spark.sparkContext
    /**
      * 创建DataFrame的方式
      * 通过SparkSession读取文件json、Csv、parquet等
      * 通过Seq创建
      * 通过Schema创建
      */
    //通过Seq创建DataFrame
    val dfSeq = spark.createDataFrame(Seq(("ljk", "11"), ("yky", "20"), ("xxx", "18"))) toDF("name", "age")
    //通过Schema创建DataFrame
    val schema = types.StructType(List(
      StructField("name", StringType, nullable = true),
      StructField("age", IntegerType, nullable = true)
    ))
    val valueList = new util.ArrayList[Row]()
    valueList.add(Row("ljk", 22))
    valueList.add(Row("yky", 20))
    valueList.add(Row("xxx", 18))
    val dfSchema = spark.createDataFrame(valueList, schema)

    dfSeq.show()
    dfSchema.show()
  }
}