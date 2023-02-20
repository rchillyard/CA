package com.phasmidsoftware.ca.modules

import com.phasmidsoftware.ca.modules.Rational.{bigOne, bigZero}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

/**
 * Case class to represent a Rational number.
 * If you really need to use rational numbers, see for example [[https://github.com/rchillyard/Number]]
 *
 * The primary purpose of this class is to demonstrate property-based testing.
 * There are also other interesting points.
 *
 * NOTE Client code should never directly invoke the constructor because the result may not be normalized.
 *
 * @param num the numerator.
 * @param den the denominator.
 */
case class Rational(num: BigInt, den: BigInt) {

    lazy val isZero: Boolean = num == bigZero && isWhole

    lazy val isOne: Boolean = num == bigOne && isWhole

    lazy val isWhole: Boolean = den == bigOne

    lazy val isInfinity: Boolean = den == bigZero

    private lazy val normalize: Rational = {
        val gcd = num.gcd(den)
        val sign = if (den < 0) -1 else 1
        val (_num, _den) = (sign * num / gcd, sign * den / gcd)
        new Rational(_num, _den)
    }

    def +(other: Rational): Rational = new Rational(num * other.den + den * other.num, den * other.den).normalize

    def -(other: Rational): Rational = this + other.negate

    def *(other: Rational): Rational = new Rational(num * other.num, den * other.den).normalize

    def /(other: Rational): Rational = this * other.invert

    lazy val invert: Rational = new Rational(den, num)

    lazy val negate: Rational = new Rational(-num, den)

    lazy val toBigInt: BigInt = Rational.toBigInt(this).get // This is not idiomatic Scala code (should never call get) but for here, it's OK

    lazy val toBigDecimal: BigDecimal = BigDecimal(num) / BigDecimal(den)

    lazy val toDouble: Double = toBigDecimal.toDouble

    override def toString: String = {
        val sNum = num.toString()
        den match {
            case Rational.bigOne => sNum
            case _ => s"""$sNum/$den"""
        }
    }
}

object Rational {
    val bigZero: BigInt = BigInt(0)
    val bigOne: BigInt = BigInt(1)

    val zero: Rational = Rational(bigZero)
    val one: Rational = Rational(bigOne)

    def normalized(num: BigInt, den: BigInt): Rational = new Rational(num, den).normalize

    implicit def convertBigIntToRational(x: BigInt): Rational = apply(x)

    def apply(num: BigInt): Rational = new Rational(num, bigOne)

    def apply(num: BigInt, den: Long): Rational = normalized(num, BigInt(den))

    def apply(x: Double): Rational = {
        val bd: BigDecimal = BigDecimal(x)
        val factor: BigInt = BigInt(10).pow(bd.scale)
        new Rational((bd * BigDecimal(factor)).toBigInt, factor).normalize // CONSIDER do we need to normalize?
    }

    def apply(w: String): Rational = {
        val rRat = """(\d+)(/(\d+))""".r // Regular expression for a Rational.
        val rDec = """(\d+)(\.(\d+))""".r  // Regular expression for a Decimal number.
        w match {
            // For these regular expression matches, the underlying method invoked is unapplySeq, rather than unapply.
            case rRat(num, _, null) => apply(BigInt(num)) // We don't care about the second capture group.
            case rDec(num, _, null) => apply(BigInt(num)) // NOTE that this is a legitimate usage of null because the regular expression code is in Java.
            case rRat(num, _, den) => new Rational(BigInt(num), BigInt(den)).normalize
            case dec@rDec(_, _, _) => Rational(dec.toDouble) // NOTE the used of "dec@"
            case _ => throw RationalException("Rational: invalid String: $w")
        }
    }

    def toBigInt(x: Rational): Try[BigInt] = if (x.isWhole) Success(x.num) else Failure(RationalException(s"toBigInt: $x is " + (if (x.den == 0L)
        "infinite" else "not whole")))
}

case class RationalException(str: String) extends Exception(str)
