package com.phasmidsoftware.ca.modules


/**
 * Trait which defines an iterable "functor" (just something that supports map).
 *
 * @tparam X the underlying type of this collection.
 */
trait LazyCollection[X] extends Iterable[X] {
    def map[Y](f: X => Y): LazyCollection[Y]
}

/**
 * Companion object to LazyCollection.
 */
object LazyCollection {
    def apply[X](xs: Seq[X]): LazyCollection[X] = DecoratedCollection[X, X](xs)(identity)
}

/**
 * Concrete case class which extends LazyCollection via decoration by a function.
 * NOTE that we can have a second parameter set for a case class but f is not considered a "member"
 * so it won't appear in the unapply method, or equals or hashCode or toString.
 *
 * @param xs a sequence of X.
 * @param f  a function which takes an X and yields a Y.
 * @tparam X the underlying type of xs.
 * @tparam Y the underlying type of this lazy collection.
 */
case class DecoratedCollection[X, Y](xs: Seq[X])(f: X => Y) extends LazyCollection[Y] {
    def iterator: Iterator[Y] = {
        println("evaluating"); xs.iterator map f
    }

    def map[Z](g: Y => Z): LazyCollection[Z] = new DecoratedCollection[X, Z](xs)(f andThen g)

    override def toString(): String = s"DecoratedCollection (opaque)"
}
