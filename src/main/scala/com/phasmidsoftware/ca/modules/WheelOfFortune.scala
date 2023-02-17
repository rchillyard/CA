package com.phasmidsoftware.ca.modules

import scala.util.Random

case class WheelOfFortune[X](m: Map[X, Int])(implicit random: Random) {
    // The total of all the (weighting) values corresponds to 1.
    // And thus the probability of any event happening is the weighting over the total weights.
    def next: X = ???

}

object WheelOfFortune extends App {
    implicit val random = new Random

    def createFromSeq[X](m: Seq[Tuple2[X, Int]]): WheelOfFortune[X] = new WheelOfFortune[X](m.toMap)

    def create[X](m: Tuple2[X, Int]*): WheelOfFortune[X] = createFromSeq[X](m)

    val wheelOfFortune = create[Boolean](true -> 1, false -> 1)
    wheelOfFortune.next
    wheelOfFortune.next
    wheelOfFortune.next
}