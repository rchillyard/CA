// Containers, Collections and Immutability

// Simplest "composed" type is a TUPLE:

// kv is a Tuple2[String,String] which can be rewritten (String, String)
// Note that kv is immutable because it's declared val, as is ("key","value")
val kv = ("key", "value")

val kv = "key" -> "value" // this is an alternative way to define a Tuple2.

val z = ("A", 1, math.Pi) // this is a Tuple3[String,Int,Double], a.k.a. (String,Int,Double)

// Tuples are Products (short for Cartesian product)
// We can, for example, get the ith value (but as an Any)
val x = z.productElement(1)

// We can "zip" two lists together into a list of tuples.
// Both the lists (and the result) are immuntable.
val zs = List(1, 2, 3) zip List("A", "B", "C")

// If we have a list and we want to split it into the first half (left) and the second half (right)...
// Note that what comes between "val" and "=" is, generally, a PATTERN.
val (left, right) = List(1, 2, 3) splitAt 1

// The next type of composed (or constructed) type to discuss are the (monadic) CONTAINERS
// But first a collection (an iterable container type):
val countryCodes: List[String] = List("1", "44", "33", "91", "86")

// Let's wrap the invocation of apply to the countryCodes in Try.
// That way we see the failure but we don't terminate the program.
// We've constructed a new type: Try[String] where Try is a monadic container.

import scala.util.Try

val fifthCountryCode: Try[String] = Try(countryCodes(5))

// Because it's a monad, we can invoke foreach on it.
// But because it failed to find a country code, we see nothing output to the console.
fifthCountryCode foreach println

// Now, we get the index correct and we get a successful result.
val fifthCountryCode: Try[String] = Try(countryCodes(4))

// We can do something similar with Option, another monadic container:
val countryCodeUK: Option[String] = countryCodes.find(_ == "44")

// The following code results in None (a sub-type of Option[...])
countryCodes.find(_ == "45")

// Now, let's look at iterable container type (collections) operations
val regions = List("North America", "UK", "France", "India", "China")

// Here we zip two lists into a list of key-value pairs.
val regionsWithCodes = regions zip countryCodes

// ...and we can easily turn it into a Map:
val countryCodeLookup = regionsWithCodes.toMap

// Now, let's really take advantage of these monads:
for (r <- Try(regions(4)).toOption; code <- countryCodeLookup.get(r)) yield code

// So far, everything has been immutable.
// Let's create an immutable Stack type, based on a List[X].
case class Stack[X](xs: List[X]) {
    // push does the usual push but must return a new Stack.
    def push(x: X): Stack[X] = Stack(x :: xs)

    // push does the usual pop but must return two things:
    // an optional X and the new Stack based on what's left.
    def pop: (Option[X], Stack[X]) = xs match {
        case Nil => None -> this
        case h :: t => Some(h) -> Stack(t)
    }
}

val stack0 = Stack[Int](Nil)

val stack1 = stack0 push 1

val stack2 = stack1.pop

/**
 * Now, let's make a mutable stack.
 *
 * @param xs a variable List[X]. Note that we can set the xs member to be a private var.
 * @tparam X the underlying type of X.
 */
case class MutableStack[X](private var xs: List[X]) {
    // Standard push method which operates by side-effect and yields Unit
    def push(x: X): Unit = {
        xs = x :: xs
    }

    // Standard pop method which operates by side-effect but also yields an X.
    def pop: X = xs match {
        case Nil => throw new Exception("stack is empty")
        case h :: t => xs = t; h
    }
}

object MutableStack {
    def apply[X](): MutableStack[X] = MutableStack(Nil)
}

val stack = MutableStack[Int]()

stack.push(42)

println(stack)

stack.pop
