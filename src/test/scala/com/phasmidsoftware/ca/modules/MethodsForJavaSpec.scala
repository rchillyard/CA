package com.phasmidsoftware.ca.modules

import java.util.function.Supplier
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MethodsForJavaSpec extends AnyFlatSpec with should.Matchers {

    behavior of "MethodsForJava"

    it should "randomSupplier" in {
        val clazz: Class[Integer] = classOf[java.lang.Integer]
        val randomIntSupplier: Supplier[Integer] = MethodsForJava.randomSupplier(clazz, 0L)
        randomIntSupplier.get() shouldBe -1155484576
    }

}
