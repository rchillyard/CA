package com.phasmidsoftware.ca.modules

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import scala.util.Random

class WheelOfFortuneSpec extends AnyFlatSpec with should.Matchers {

    behavior of "WheelOfFortune"

    it should "createFromSeq" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.createFromSeq[Boolean](List(true -> 1, false -> 1))
        wheelOfFortune.next shouldBe true
    }

    it should "create" in {
        implicit val random: Random = new Random(0)
        val wheelOfFortune = WheelOfFortune.create[Boolean](true -> 1, false -> 1)
        wheelOfFortune.next shouldBe true
    }

}
