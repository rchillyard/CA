package com.phasmidsoftware.ca.modules

import com.phasmidsoftware.ca.modules.Rational._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class RationalSpec extends AnyFlatSpec with should.Matchers {

    behavior of "apply and normalized"

    it should "implement normalized" in {
        normalized(2, BigInt(2)) shouldBe new Rational(1, 1)
    }

    it should "implement apply(BigInt,Long)" in {
        Rational(2, 2L) shouldBe new Rational(1, 1)
    }

    it should "implement apply(BigInt)" in {
        Rational(1) shouldBe new Rational(1, 1)
    }

    it should "implement apply(Pi)" in {
        Rational(math.Pi) shouldBe new Rational(3141592653589793L, 1000000000000000L)
    }

    it should """implement apply("3.1415927")""" in {
        Rational("3.1415927") shouldBe new Rational(31415927, 10000000)
    }

    behavior of "queries"

    it should "invoke isWhole" in {
        zero.isWhole shouldBe true
        one.isWhole shouldBe true
        Rational(math.Pi).isWhole shouldBe false
    }

    it should "invoke isZero" in {
        zero.isZero shouldBe true
        one.isZero shouldBe false
        Rational(math.Pi).isZero shouldBe false
    }

    it should "invoke isInfinity" in {
        zero.isInfinity shouldBe false
        zero.invert.isInfinity shouldBe true
    }

    behavior of "arithmetic"

    it should "multiply" in {
        one * one shouldBe one
        one * zero shouldBe zero
        zero * zero shouldBe zero
        zero * one shouldBe zero
        Rational(math.Pi) * one shouldBe Rational(math.Pi)
        one * Rational(math.Pi) shouldBe Rational(math.Pi)
    }

    it should "add" in {
        one + zero shouldBe one
        zero + zero shouldBe zero
        zero + one shouldBe one
        Rational(math.Pi) + zero shouldBe Rational(math.Pi)
        zero + Rational(math.Pi) shouldBe Rational(math.Pi)
    }
}
