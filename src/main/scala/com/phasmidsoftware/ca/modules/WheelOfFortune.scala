package com.phasmidsoftware.ca.modules

import scala.util.Random

case class WheelOfFortune[T](map: Map[T, Int])(implicit random: Random) {
    // The total of all the (weighting) values corresponds to 1.
    // And thus the probability of any event happening is the weighting over the total weights.
    val (events, odds) = map.toSeq.unzip
    private val total = odds.sum
    val accumulated: Map[T, Int] = (events zip (odds.scanLeft(0)((a, x) => a + x))).toMap

    def next: T = {
        val n = random.nextInt(total)

        def tooSmall(t: (T, Int)): Boolean = t._2 < n

        (accumulated dropWhile tooSmall).head._1
    }

}

object WheelOfFortune extends App {
    implicit val random = new Random

    def createFromSeq[X](m: Seq[Tuple2[X, Int]])(implicit random: Random): WheelOfFortune[X] = new WheelOfFortune[X](m.toMap)

    def create[X](m: Tuple2[X, Int]*)(implicit random: Random): WheelOfFortune[X] = createFromSeq[X](m)

    val wheelOfFortune = create[Boolean](true -> 1, false -> 1)
    wheelOfFortune.next
    wheelOfFortune.next
    wheelOfFortune.next
}