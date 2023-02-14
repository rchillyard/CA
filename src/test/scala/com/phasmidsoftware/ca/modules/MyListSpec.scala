package com.phasmidsoftware.ca.modules

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MyListSpec extends AnyFlatSpec with should.Matchers {

    behavior of "MyList"

    it should "apply1" in {
        val mylist = MyList(0)
        mylist shouldBe Cons(0, Empty)
    }
    it should "apply2" in {
        val mylist = MyList(0, 1)
        mylist shouldBe Cons(0, Cons(1, Empty))
    }
    it should "apply3" in {
        val mylist = MyList(0, 1, 2)
        println(mylist)
        mylist shouldBe Cons(0, Cons(1, Cons(2, Empty)))
    }

}
