package com.phasmidsoftware.ca.modules

import java.lang
import java.util.function.Supplier
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MethodsForJavaSpec extends AnyFlatSpec with should.Matchers {

    behavior of "MethodsForJava"

    it should "randomSupplier Integer" in {
        val clazz: Class[Integer] = classOf[Integer]
        val randomIntSupplier: Supplier[Integer] = MethodsForJava.randomSupplier(clazz, 0L)
        randomIntSupplier.get() shouldBe -1155484576
    }

    it should "randomSupplier Boolean" in {
        val clazz: Class[lang.Boolean] = classOf[lang.Boolean]
        val randomIntSupplier: Supplier[lang.Boolean] = MethodsForJava.randomSupplier(clazz, 0L)
        randomIntSupplier.get() shouldBe true
    }

    it should "randomSupplier Double" in {
        val clazz: Class[lang.Double] = classOf[lang.Double]
        val randomIntSupplier: Supplier[lang.Double] = MethodsForJava.randomSupplier(clazz, 0L)
        randomIntSupplier.get() shouldBe 0.730967787376657
    }

    it should "throw an exception for String" in {
        val clazz: Class[String] = classOf[String]
        val randomIntSupplier: Supplier[String] = MethodsForJava.randomSupplier(clazz, 0L)
        a[Exception] should be thrownBy randomIntSupplier.get()

    }
}
