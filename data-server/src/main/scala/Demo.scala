class Demo {

}

object Demo {
  def main(args: Array[String]): Unit = {
    val list = for {
      i <- 1 to 5
      j <- 1 to 5
      if i % 2 == 1
    } yield i * j
    list.foreach(println)
  }
}