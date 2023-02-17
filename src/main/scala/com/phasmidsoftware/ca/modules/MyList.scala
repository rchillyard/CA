package com.phasmidsoftware.ca.modules

import scala.annotation.tailrec

/**
 * Declaration of trait MyList which has only the behavior of knowing the length of the list.
 *
 * NOTE also that this is a Scala module (i.e. a .scala file) -- it is referenced by the worksheet Proofs.sc
 *
 * @tparam X the underlying type of this MyList.
 *           X is marked as a "co-variant" type (a somewhat advanced topic).
 *           This ensures that a MyList[S] is a sub-type of a MyList[T] provided that S is a sub-type of T.
 *           NOTE that X is not actually used by any declarations in the trait, which would be very unusual, not to say non-idiomatic.
 */
trait MyList[+X] {
    def length: Int // method to yield the length of this MyList.
}

/**
 * Declare the companion object to MyList.
 *
 * Usually, the companion object will declare at least one signature of an apply method.
 */
object MyList {
    /**
     * An apply method to yield a MyList from a comma-separated list of X values (known in Java as varargs).
     *
     * @param xs a "varags" list of Xs.
     * @tparam X the underlying type of the resulting MyList.
     * @return a MyList[X].
     */
    def apply[X](xs: X*): MyList[X] = {
        // a tail-recursive method to help construct a MyList object.
        @tailrec
        def inner(r: MyList[X], ys: Seq[X]): MyList[X] = ys match {
            case Nil => r
            case h :: t => inner(Cons(h, r), t)
        }

        // First reverse the given input and then turn it into a sub-class of Seq[X].
        inner(Empty, xs.reverse.toList)
    }
}

/**
 * A (leaf) case class of MyList[X] called Cons which constructs a MyList from a head (value) and a tail(next).
 *
 * @param value the head of the resulting MyList.
 * @param next  the tail of the resulting MyList.
 * @tparam X the underlying type of the resulting MyList.
 */
case class Cons[X](value: X, val next: MyList[X]) extends MyList[X] {
    def length: Int = 1 + next.length // the length of this Cons is 1 plus the length of next.
}

/**
 * An empty MyList which extends Nothing where
 * Nothing is a special type which is the sub-type of ALL other types.
 */
case object Empty extends MyList[Nothing] {
    def length: Int = 0 // the length of an Empty is always zero.
}


