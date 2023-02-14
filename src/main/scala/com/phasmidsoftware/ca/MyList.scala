package com.phasmidsoftware.ca

import scala.annotation.tailrec

trait MyList[+X] {
    def length: Int
}

object MyList {
    def apply[X](xs: X*): MyList[X] = {
        @tailrec
        def inner(r: MyList[X], ys: Seq[X]): MyList[X] = ys match {
            case Nil => r
            case h :: t => inner(Cons(h, r), t)
        }

        inner(Empty, xs.reverse.toList)
    }
}

case class Cons[X](value: X, val next: MyList[X]) extends MyList[X] {
    def length: Int = 1 + next.length
}

case object Empty extends MyList[Nothing] {
    def length: Int = 0
}


