package com.phasmidsoftware.ca.modules

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import scala.util.control.NonFatal

/**
 * To run these tests, you need to use sbt test.
 * You can do that in the sbt shell (just type "test").
 * This will run all your other unit tests, also.
 *
 * See [[https://scalacheck.org]]
 *
 * NOTE there are errors in these tests. This is for demonstration purposes only.
 */
class RationalPropertySpec extends Properties("Rational") {
    def hasCorrectRatio(r: Rational, top: BigInt, bottom: BigInt): Boolean = {
        val _a: Rational = r * Rational(bottom)
        val result = bottom == 0 || _a.isInfinity || (_a.isWhole && _a.toBigInt == top)
        if (!result) throw RationalException(s"incorrect ratio: r=$r, top=$top, bottom=$bottom, _a=${_a}, gcd=${top.gcd(bottom)}")
        result
    }


    property("FromString") = forAll { (a: Int, b: Short) =>
        val r = Rational("$a/$b")
        hasCorrectRatio(r, a, b)
    }

    property("FromIntAndShort") = forAll { (a: Int, b: Short) =>
        val _a: BigInt = BigInt(a) * 1000
        val r = Rational(_a, b)
        hasCorrectRatio(r, _a, b.toLong)
    }

    property("Addition") = forAll { (a: Long, b: Short, c: Int, d: Short) =>
        val r1 = Rational(a, b)
        val r2 = Rational(c, d)
        val r = r1 + r2
        //      println(s"$a/$b, $c/$d => $r1 + $r2 = $r")
        try hasCorrectRatio(r, (BigInt(a) * d.toInt) + (BigInt(c) * b.toInt), b.toLong * d)
        catch {
            case NonFatal(x) => throw new Exception(s"a=$a, b=$b, c=$c, d=$d => $r1 + $r2 = $r ($r) caused by ${x.getLocalizedMessage}")
        }
    }

    property("Double") = forAll { x: Double =>
        import org.scalactic.Tolerance._
        import org.scalactic.TripleEquals._
        // TODO check this is OK. Might need to be Rational(BigDecimal.valueOf(x))
        val r = Rational(x)
        val s = Rational(1.0 / x)
        (r * s).toDouble === 1.0 +- 1E-7
    }

}
