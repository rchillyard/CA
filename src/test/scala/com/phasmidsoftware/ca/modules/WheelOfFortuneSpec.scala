package com.phasmidsoftware.ca.modules

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import scala.util.Random

class WheelOfFortuneSpec extends AnyFlatSpec with should.Matchers {

    behavior of "WheelOfFortune"

    it should "createFromSeq Boolean" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.createFromSeq[Boolean](List(true -> 1, false -> 1))
        wheelOfFortune.next shouldBe false
    }

    it should "create Boolean" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Boolean](true -> 1, false -> 1)
        wheelOfFortune.next shouldBe false
    }

    it should "create Int" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Int](1 -> 1, 2 -> 1, 3 -> 1)
        wheelOfFortune.next shouldBe 1
        wheelOfFortune.next shouldBe 2
        wheelOfFortune.next shouldBe 2
    }


}
