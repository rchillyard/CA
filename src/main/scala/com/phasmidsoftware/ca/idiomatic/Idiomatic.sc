// Idiomatic

// Take a look at [[https://github.com/alexandru/scala-best-practices]] for best practices.

val list = List(1, 2, 3)  // a list

// How NOT to sum...
var total = 0   // this should be a no-brainer by now: do NOT use var. There's always a way around it.
list.foreach(x => total += x)
println(total)

// How to sum...
list.sum

// OK, that was bit TOO easy!
list.foldLeft(0)(_ + _)

// How about this?
list.reduce(_ + _) // BTW, never use this if there's a possibility of list being empty.

// But what if you want to do something that you can't do with foldLeft or reduce?
// You really need to use recursion directly...
// First, you need to declare a tail-recursive method, i.e. one where the result is immediately returned.

// NOT tail-recursive because we still have to multiple the result by n after the recursive call.
def factorial(n: Int): Long = if (n <= 1) 1L else n * factorial(n - 1)

factorial(5)

import scala.annotation.tailrec
import scala.io.Source

@tailrec // This is a useful annotation because it serves to document the fact that your method is tail-recursive
// but also, if for some reason it isn't, then the compiler will warn you.
// A tail-recursive method always has one parameter whose type is the same as the result type and will be returned when the work is finished; and
// another parameter that represents the work still to be done.
def factorialTR(r: Long, x: Int): Long = if (x <= 1) r else factorialTR(r * x, x - 1)

def factorial(n: Int): Long = factorialTR(1L, n)

factorial(5)

// map and flatMap

val square = list map (_ => list) // this gives us a two-dimensional list

// What if we wanted just a flat list?
val flattenedSquare = list map (_ => list) flatten // this will flatten the result BUT you should use flatMap instead.
val flattenedSquare = list flatMap (_ => list)  // this is the right way to do it

val words = Source.fromString("1\n2\n3").getLines().toList

// If you have some code such as the following were you have a call to flatMap whose lambda involves a call to map...
words.flatMap(w => Stream.from(1).take(w.toInt).map(x => x * 2))

// ... then you might be able to rewrite it as a for-comprehension which will be much clearer to the reader.
for (w <- words; x <- Stream.from(1).take(w.toInt)) yield x * 2
