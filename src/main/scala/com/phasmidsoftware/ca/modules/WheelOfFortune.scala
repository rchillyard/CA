package com.phasmidsoftware.ca.modules

import scala.util.Random

/**
 * Wheel of Fortune class for events of type T.
 * The chances in the map are the weights.
 *
 * @param map    the map of events to chances.
 * @param random an implicit Random.
 * @tparam T the event type.
 */
case class WheelOfFortune[T](map: Seq[(T, Int)])(implicit random: Random) {
    // The total of all the chances corresponds to probability 1.0
    // And thus the probability of any event happening is the event's chances divided by the total chances.
    private val (events, odds) = map.unzip
    private val total = odds.sum
    private val accum: Seq[Int] = odds.scanLeft(0)((a, x) => a + x)
    private val accumulated = events zip accum.tail

    def next: T = selectEvent(random.nextInt(total))

    private def selectEvent(n: Int) = {
        def tooSmall(t: (T, Int)): Boolean = t._2 <= n

        (accumulated dropWhile tooSmall).head._1
    }
}

object WheelOfFortune extends App {
    private implicit val random = new Random

    def create[X](m: Tuple2[X, Int]*)(implicit random: Random): WheelOfFortune[X] = new WheelOfFortune[X](m)

    val wheelOfFortune = create[Boolean](true -> 1, false -> 1)
    println(wheelOfFortune.next)
    println(wheelOfFortune.next)
    println(wheelOfFortune.next)
}