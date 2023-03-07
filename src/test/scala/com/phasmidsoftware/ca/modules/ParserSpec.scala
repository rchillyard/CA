package com.phasmidsoftware.ca.modules

import com.phasmidsoftware.ca.modules.Parser.{asDouble, parseNumber}
import com.phasmidsoftware.ca.util.ParseNumber
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ParserSpec extends AnyFlatSpec with should.Matchers {

    behavior of "Parser"

    it should "asDouble" in {
        parseNumber("42").map(asDouble) shouldBe Some(42.0)
        parseNumber("3.1415927").map(asDouble) shouldBe Some(3.1415927)
        parseNumber("").map(asDouble) shouldBe None
    }

    it should "parseNumber" in {
        parseNumber("42") shouldBe Some(Right(42))
        parseNumber("3.1415927") shouldBe Some(Left(3.1415927))
        parseNumber("") shouldBe None
    }

    behavior of "(Java) ParseNumber"

    it should "" in {
        ParseNumber.parseNumber("Hello") shouldBe null
        ParseNumber.parseNumber("42") shouldBe "42"
    }
}
