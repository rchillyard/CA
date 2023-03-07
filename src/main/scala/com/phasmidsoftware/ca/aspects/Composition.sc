// Functional Composition


val doubleIt: Int => Double = _ * 2.0

val addHalf: Double => String = x => (x + 0.5).toString

(doubleIt andThen addHalf)(3)
(addHalf compose doubleIt)(3)

// Curried Functions
def areaOfEllipse(major: Double, minor: Double): Double = math.Pi * major * minor

areaOfEllipse(3, 2)

val areaOfEllipseCurried: Double => Double => Double = major => minor => areaOfEllipse(major, minor)

areaOfEllipseCurried(3)(2)

// Much easier:

val areaOfEllipseCurried = (areaOfEllipse _).curried // Note that we must provide a partially-applied function here

areaOfEllipseCurried(3)(2)

val areaOfEllipseWithMajorFixedAt3 = areaOfEllipseCurried(3)

areaOfEllipseWithMajorFixedAt3(2)

// What does map do to an Option, Try, etc.

import com.phasmidsoftware.ca.util.ParseNumber

val maybeString: Option[String] = Option(ParseNumber.parseNumber("Hello"))
val maybeInt: Option[Int] = maybeString map (_.toInt)

// invoking map with (_.toInt) is equivalent to:
maybeString match {
    case Some(x) => Some(x.toInt)
    case None => None
}

// Lift

def lift[T, R](f: T => R): Option[T] => Option[R] = _ map f

val circumference: Int => Double = _ * math.Pi

// can't do this until 2.13:  val radius = "3".toIntOption

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util._

val maybeRadius: Option[Int] = Try("3".toInt).toOption // This is how we have to do it in Scala 2.12

lift(circumference)(maybeRadius) // yields an option of Double

// Other lifts?

def lift[T, R](f: T => R): Try[T] => Try[R] = _ map f // lifting to Try
def lift[T, R](f: T => R): List[T] => List[R] = _ map f // lifting to List


def lift[T, R](f: T => R): Future[T] => Future[R] = _ map f // lifting to Future

// How about lift2?

val maybeMajorAxis = Try("3".toDouble).toOption
val maybeMinorAxis = Try("2".toDouble).toOption

def lift2[T1, T2, R](f: (T1, T2) => R): (Option[T1], Option[T2]) => Option[R] = ??? // we need _ map2 f

// map2

// How can we combine these and get an Option of the area?
def map2[A, B, C](ao: Option[A], bo: Option[B])(f: (A, B) => C): Option[C] =
    ao match {
        case Some(a) => bo match {
            case Some(b) => Some(f(a, b))
            case None => None
        }
        case None => None
    }

map2(maybeMajorAxis, maybeMinorAxis)(areaOfEllipse)

// Now, let's try lift2 again...

def lift2[T1, T2, R](f: (T1, T2) => R): (Option[T1], Option[T2]) => Option[R] = (x, y) => map2(x, y)(f)

lift2(areaOfEllipse _)(maybeMajorAxis, maybeMinorAxis) // Not sure why areaOfEllipse is requiring the underscore.

// That map2 sure was a nice method for defining lift2.
// But we don't need it (or map3, map4, etc.)
// Here's what we do instead: a for-comprehension:

for (major <- maybeMajorAxis; minor <- maybeMinorAxis) yield areaOfEllipse(major, minor)


// Suppose we have a whole bunch of Options (or any other container).

val numbersTypedOnConsole = List("1", "2", "3-", "4", "", "5")

val xys: Seq[Try[Int]] = numbersTypedOnConsole map (x => Try(x.toInt))

// So, we've got a Seq[Try[Int]].  What if we want a Try[Seq[Int]] instead?
// The sequence isn't much use to us, let's suppose, unless all of the numbers are good.

def sequence[X](xys: Seq[Try[X]]): Try[Seq[X]] = xys.foldLeft[Try[Seq[X]]](Success(Nil)) {
    case (Failure(x), _) => Failure(x)
    case (Success(a), Success(x)) => Success(a :+ x)
    case (_, Failure(x)) => Failure(x)
}

sequence(xys)

// This sequence idea is particularly useful when you've spawned lots of Futures.
// So, in the Future object, there's a sequence method that works just like this.

// How about getting a good typist?

sequence(List("1", "2", "3", "4", "5", "6") map (x => Try(x.toInt)))


