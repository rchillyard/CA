package com.phasmidsoftware.ca.modules

import org.scalactic.Equality
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import scala.annotation.tailrec


class ComplexSpec extends AnyFlatSpec with should.Matchers {

    // NOTE: this is how you determine equality when you have a more complex sense of equality than standard.
    implicit object ComplexEquality extends Equality[Complex] {
        @tailrec
        def areEqual(a: Complex, b: Any): Boolean = (a, b) match {
            case (c1: ComplexCartesian, c2: ComplexCartesian) => c1.r == c2.r && c1.i == c2.i
            case (c1: ComplexPolar, c2: ComplexPolar) => c1.r == c2.r && c1.theta == c2.theta
            case (c1: ComplexCartesian, c2: ComplexPolar) => areEqual(c1, ComplexCartesian(c2))
            case (c1: ComplexPolar, c2: ComplexCartesian) => areEqual(ComplexCartesian(c1), c2)
        }
    }

    behavior of "ComplexCartesian"

    it should "implement $plus" in {
        val c1 = ComplexCartesian(0, 0)
        val c2 = ComplexCartesian(1, 2)
        c1 + c2 shouldBe ComplexCartesian(1, 2) // this uses areEqual implicitly
    }

}
