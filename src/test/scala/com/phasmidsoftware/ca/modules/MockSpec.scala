package com.phasmidsoftware.ca.modules

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

/**
 * Trivial example code. See [[https://scalamock.org]]
 */
class MockSpec extends AnyFlatSpec with MockFactory with should.Matchers {

    "A mocked Foo" should "return a mocked bar value" in {
        val mockFoo = mock[Foo]
        mockFoo.bar _ expects() returning 6
        mockFoo.bar should be(6)
    }
}

class Foo {
    def bar = 100
}
