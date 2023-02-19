package com.phasmidsoftware.ca.modules

import scala.util.Random

/**
 * Wheel of Fortune class for events of type E.
 * The values in the map are the frequencies of occurrence for the given event.
 *
 * @param map    the map of events to chances, expressed as a Seq[(E,Int)].
 * @param random an implicit Random.
 * @tparam E the event type.
 */
case class WheelOfFortune[E](map: Seq[(E, Int)])(implicit random: Random) {
    // The total of all the chances corresponds to probability 1.0
    // And thus the probability of any event happening is the event's chances divided by the total chances.
    private val (events, odds) = map.unzip
    private val total = odds.sum
    private val accumulated = events zip odds.scanLeft(0)((a, x) => a + x).tail

    def next: E = selectEvent(random.nextInt(total))

    private def selectEvent(n: Int) = {
        def tooSmall(t: (E, Int)): Boolean = t._2 <= n

        (accumulated dropWhile tooSmall).head._1
    }
}

object WheelOfFortune {

    def create[X](m: (X, Int)*)(implicit random: Random): WheelOfFortune[X] = new WheelOfFortune[X](m)

    def coinFlip(implicit random: Random): WheelOfFortune[Boolean] = create[Boolean](true -> 1, false -> 1)
}


object Main extends App {
    implicit val random: Random = new Random

    val coinFlip = WheelOfFortune.create[Boolean](true -> 1, false -> 1)
    println(coinFlip.next)
    println(coinFlip.next)
    println(coinFlip.next)
}