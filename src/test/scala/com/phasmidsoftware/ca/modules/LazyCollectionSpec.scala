package com.phasmidsoftware.ca.modules

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class LazyCollectionSpec extends AnyFlatSpec with should.Matchers {

    behavior of "LazyCollection"

    it should "apply" in {
        val xs = LazyCollection(List(1, 2, 3))
        val ys = xs map (_.toDouble) // no evaluation
        val zs = ys map (_.toString) // still no evaluation
        zs.toList shouldBe List("1.0","2.0","3.0") // has to evaluate
    }
}
