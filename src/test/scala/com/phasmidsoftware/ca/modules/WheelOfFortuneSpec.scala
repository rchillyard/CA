package com.phasmidsoftware.ca.modules

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import scala.collection.mutable
import scala.util.Random

class WheelOfFortuneSpec extends AnyFlatSpec with should.Matchers with PrivateMethodTester {

    behavior of "next"

    it should "create Boolean uniform" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Boolean](true -> 1, false -> 1)
        wheelOfFortune.next shouldBe false
    }

    it should "create Int uniform" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Int](1 -> 1, 2 -> 1, 3 -> 1)
        wheelOfFortune.next shouldBe 1
        wheelOfFortune.next shouldBe 2
        wheelOfFortune.next shouldBe 2
        wheelOfFortune.next shouldBe 3
    }

    it should "create Int powers of two" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Int](0 -> 32, 1 -> 16, 2 -> 8, 3 -> 4, 4 -> 2, 5 -> 1)
        wheelOfFortune.next shouldBe 1
        wheelOfFortune.next shouldBe 0
        wheelOfFortune.next shouldBe 0
        wheelOfFortune.next shouldBe 0
        wheelOfFortune.next shouldBe 2
        wheelOfFortune.next shouldBe 3
        wheelOfFortune.next shouldBe 1
        wheelOfFortune.next shouldBe 3
    }

    class Frequencies extends mutable.HashMap[String, Int]() {
        def increment(k: String): Option[Int] = put(k, getOrElse(k, 0) + 1)
    }

    // NOTE use of +- to define a tolerance range.
    it should "yield proper relative frequencies in video poker" in {
        implicit val r: Random = new Random()
        val wheel = WheelOfFortune.create("high-card" -> 1302540, "pair" -> 1098240, "two-pair" -> 123552, "trips" -> 54912, "straight" -> 10200, "flush" -> 5108, "full-house" -> 3744, "quads" -> 624, "straight-flush" -> 36, "royal" -> 4)
        val frequencies = new Frequencies
        for (_ <- 1 to 1000000) frequencies.increment(wheel.next)
        frequencies("pair") / 10000.0 shouldBe 42.0 +- 5
        frequencies("two-pair") / 10000.0 shouldBe 4.8 +- 1
        frequencies("trips") / 10000.0 shouldBe 2.0 +- 0.7
    }

    behavior of "selectEvent"

    it should "selectEvent" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Int](0 -> 32, 1 -> 16, 2 -> 8, 3 -> 4, 4 -> 2, 5 -> 1)
        val selectEvent = PrivateMethod[Int](Symbol("selectEvent"))
        wheelOfFortune invokePrivate selectEvent(0) shouldBe 0
        wheelOfFortune invokePrivate selectEvent(31) shouldBe 0
        wheelOfFortune invokePrivate selectEvent(32) shouldBe 1
        wheelOfFortune invokePrivate selectEvent(47) shouldBe 1
        wheelOfFortune invokePrivate selectEvent(48) shouldBe 2
        wheelOfFortune invokePrivate selectEvent(55) shouldBe 2
        wheelOfFortune invokePrivate selectEvent(56) shouldBe 3
        wheelOfFortune invokePrivate selectEvent(59) shouldBe 3
        wheelOfFortune invokePrivate selectEvent(60) shouldBe 4
        wheelOfFortune invokePrivate selectEvent(61) shouldBe 4
        wheelOfFortune invokePrivate selectEvent(62) shouldBe 5
    }
}
