// Streams
// Don't forget that when you upgrade to 2.13, all your Streams will be deprecated and thus need (eventually) to be LazyLists.

// Define a Stream of ALL positive integers (unlimited).
val positiveIntegers: Seq[BigInt] = Stream.iterate(BigInt(0))(_+BigInt(1)) // Note we cannot yet "see" the second element

import scala.language.postfixOps
import scala.util.Random

positiveIntegers filter (_ % 2 == 0) take 2 toList

positiveIntegers // Note that now we can see the first four values because they are "memoized"

// Define a stream of random integers less than 1000
val random = new Random()
val randoms = Stream.continually(random.nextInt(1000))

// take the first 10 random numbers and create a list in order to force evaluation
randoms take 10 toList

// Now, something which I hope will blow your mind a little...
val fibonacci: Stream[BigInt] = BigInt(1) #:: fibonacci.scanLeft(BigInt(1))(_ + _)
fibonacci take 10 toList

// And maybe the following will, too...
val xs = Stream from 1 map (x => 1.0 / x / x) // All the inverse squares of the integers
val qs = xs takeWhile (y => y > 1E-13) // Take as many elements as necessary while each is greater than 10^-13
math.sqrt(qs.sum * 6) // What do you see? Thank Leonhard Euler for this little gem (1734).






