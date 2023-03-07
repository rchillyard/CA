// For-comprehensions and Monads

import com.phasmidsoftware.ca.modules.Parser.{asDouble, parseNumber}
import com.phasmidsoftware.ca.modules.WheelOfFortune
import scala.util.Random

// We CAN use a for-comprehension like a loop in Java:
val xs = Array[Int](1, 2, 3)
var sum = 0
for (x <- xs) sum += x
println(sum)

// But we don't NEED to...
// foldLeft is one of the many methods available to monads (even monads like Option).
// The 0 is the identity value (what we get if xs is empty)
// and the second parameter is the function to be applied to the accumulated value and each element.
val sum = xs.foldLeft(0)(_ + _)

// Or, more simply
val sum = xs.sum

// But, we can do so much more with a for-comprehension
// We can use it for "map2"--where we know the values in advance.
val (ao, bo) = (Option(1), Option(null))
// We get None as a result because we only have one existing Option value.
for (a <- ao; b <- bo) yield a + b
//ao.flatMap(a => bo.map(b => a + b))

// Using a kind of three-way coin-flip where heads = Some(true),
// return heads if two flips are heads.
implicit val random: Random = new Random()
val wheelOfFortune = WheelOfFortune.create[Option[Boolean]](Some(true) -> 1, Some(false) -> 1, None -> 1)
val twoHeads = for (x <- wheelOfFortune.next; y <- wheelOfFortune.next) yield x && y

// Here's a more complex example of the same thing.
for (xe <- parseNumber("42"); ye <- parseNumber("3.1415927")) yield asDouble(xe) + asDouble(ye)

// But we can also use it where a generator depends on the result of the previous generator, etc.

import java.net.URL
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.Source

val futureString: Future[String] = for {
    u <- Future(new URL("https://www.phasmidsoftware.com"))
    c <- Future(u.openConnection())
    is <- Future(c.getInputStream)
    s = Source.fromInputStream(is)
} yield s.getLines().mkString(" ")

// We do have to block here otherwise the program ends without us seeing a result.
Await.result(futureString, Duration("10 second"))
