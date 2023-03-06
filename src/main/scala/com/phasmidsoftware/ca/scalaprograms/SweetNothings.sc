// Much Ado about Nothing

val x: Any = 1

/**
 * Type hierarchy of Scala:
 *
 *               Any
 *           /        \
 *       AnyRef      AnyVal
 *          |           |
 *    String, etc.     Int, Double, etc.
 *           \         /
 *             Nothing
  */

// null is in Scala ONLY for compatability with Java.

val x: Any = null
val y = 42

// Scala wraps any value that MIGHT be null inside Option.
val maybeX = Option.apply(x) // same as Option(x)
val maybeY = Option(y)

/**
 * Quick word on Variance:
 *
 * Covariance:
 *
 * Imagine a container type C[X] which contains an X...
 *
 * If C is declared with X covariant, i.e. C[+X]
 * And if Y is a subtype of Z,
 * Then C[Y] is a subtype of C[Z].
 */

// Nothing is the sub-type of every other type.
// Nil, which is declared as List[Nothing],
// can be used therefore as an empty list regardless
// of the underlying type of the declaration.
val xs: List[Int] = Nil

/**
 * Underscore: all the possible usages
 */

// pattern-matching
xs match {
    case _ => "any list"
}

// lambda
val f: Int => String = _.toString
f(2)

// varargs
// This shows the other use of the underscore.
def variableArgs(xs: Int*): Seq[String] = xs.toList match {
    case Nil => Seq("")
    case h :: t => println(h); variableArgs(t:_*)
}

variableArgs(1, 2, 3)

// higher-kinded types (an advanced concept but you might see it somewhere).

trait M[_] {
    def hello: String
}

// partially applied method
def hello(x: String): String = s"Hello $x"

val f = hello _

f("Robin")