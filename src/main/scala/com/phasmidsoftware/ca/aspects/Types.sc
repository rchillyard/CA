// Types and Type constructors:

val b = true // the compiler uses type inference to deduce that b has type Boolean.

val y = 42 // the compiler uses type inference to deduce that x has type Int.

val x = math.Pi // the compiler uses type inference to deduce that x has type Double.

val y: Double = 42 // we overrode type inference to require that y is actually a Double, not an Int.

val z: java.lang.String = "Hello World!" // Hello World is a java.lang.String (Scala uses the Java type as is).

import scala.util.Random

/**
 * Prime will be a BigInt that is a Prime number of 12 bits except once in every million cases.
 */
val prime: BigInt = BigInt(12, 20, new Random())

/**
 * Declaration of a new type MyTrait with no behavior being what it inherits from AnyRef.
 */
trait MyTrait

/**
 * Here, we declare an empty list of MyTrait objects.
 * But, in doing so, we CONSTRUCTED a new type: List[MyTrait].
 */
val xs: List[MyTrait] = Nil

/**
 * Let's create a new list that actually has one MyTrait instance in it.
 * We can instantiate a new MyTrait with the empty braces.
 * Note that we haven't overridden the toString method for a MyTrait.
 */
xs :+ new MyTrait {}

/**
 * A more interesting MyTrait would have a parametric type defined (X).
 *
 * @tparam X the underlying type.
 */
trait MyTrait[X] {
    val x: X // this is the sole property of MyTrait: and it's of type X
}

/**
 * Now, let's create a list with one MyTrait[Int] in it.
 * Again, we've constructed a new concrete type that's never been seen before: MyTrait[Int].
 * And, incidentally, another new type: [[List[MyTrait[Int]]
 * NOTE that the Scaladoc doesn't like multiple [] characters.
 */
val xs = List(new MyTrait[Int] {val x: Int = 42})

/**
 * Let's declare a new type that is a MyTrait[Int].
 * MyIntTrait can serve as a kind of alias of MyTrait[Int].
 * It's really just like writing val x = 42:
 * x is just an alias of 42 but in the value space, rather than the type space.
 */
type MyIntTrait = MyTrait[Int]

val xs = List(new MyIntTrait {val x: Int = 42}) // trying out our new type name.

/**
 * But, it's still not really that useful.
 * Wouldn't it be better to declare a new class instead?
 * With the following MyIntTrait, we can declare its x value also.
 */
class MyIntClass(val x: Int) extends MyTrait[Int]

/**
 * Let's create the equivalent of our earlier MyTrait with the value 42.
 * But, here, the syntax is easier.
 * However, it still isn't getting written out properly!
 * We could write our own toString method.
 * But there's a much better way (see case class MyIntClass...)
 */
val x = new MyIntClass(42)

/**
 * Let's declare it as a case class.
 * Now, it gets written out properly (or at least reasonably).
 * We don't have to declare the field x as a "val".
 * In fact, x has now been promoted from a mere field to a "member."
 */
case class MyIntClass(x: Int) extends MyTrait[Int]

/**
 * Let's instantiate our MyIntClass with the value 42.
 * But, here, we don't have to use the new keyword
 * (because the compiler provides an invisible companion object that defines an "apply" method).
 * And the output is much better.
 * All because we declared it as a case class.
 */
val x = MyIntClass(42)
