// Spark, Scala, and the JVM

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
val conf = new SparkConf().setAppName("CA").setMaster("local[*]")
val context = new SparkContext(conf)

val n = 20000
val rdd: RDD[Int] = context.parallelize(Stream.from(1).take(n).toList)

rdd.partitions.size // 8 on my computer

val bigInts: RDD[BigInt] = for (x <- rdd) yield BigInt(x)
val primes: RDD[BigInt] = bigInts filter (_.isProbablePrime(25))
val ws: RDD[String] = for (x <- primes) yield s"$x, "

// Aggregation requires (strict) evaluation of the RDD... takes time
s"The number of primes below $n is:"
ws.count()

//ws foreach println
