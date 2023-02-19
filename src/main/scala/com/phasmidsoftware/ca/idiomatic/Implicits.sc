// Implicits

// NOTE that, with the exception of the "explicit" implicits (method hello()(...)),
// names are generally unimportant because they are never referenced directly in code.

// Declaration of a method with an implicit final parameter set
// Note that all parameters in the final parameter set are implicit.
// And note also that they have to be of different types.
def hello()(implicit x: Int, y: Double) = x * y

hello() // !! cannot find implicit value for x -- to allow compilation to proceed, you must comment this line

implicit val (x, y) = (6, Math.PI)
hello() // You can see the implicit values used by doing (Mac) Command-Shift-P in IntelliJ IDEA

import com.phasmidsoftware.ca.modules.WheelOfFortune
import scala.language.{implicitConversions, postfixOps}
import scala.util.Random // Need this for implicit conversions

/**
 * Define an implicit converter from Double to BigDecimal.
 *
 * @param x a Double.
 * @return the equivalent BigDecimal.
 */
implicit def convert(x: Double): BigDecimal = BigDecimal(x)

val z: BigDecimal = 42.0 // uses implicit converter defined above ("convert")

/**
 * Example of an implicit class.
 * This defines one method called times.
 * If a caller tries to invoke the times method on an Int,
 * this class gets instantiated with the value of the Int.
 *
 * @param n an Int
 */
implicit class Rep(n: Int) {
    /**
     * Method which evaluates x n times and returns the mean execution time in milliseconds
     *
     * @param x a call-by-name value to be evaluated.
     * @tparam X the type of X
     * @return the mean number of milliseconds elapsed per repetition
     */
    def times[X](x: => X): Double = {
        val start = System.nanoTime()
        Stream.continually(x) take n toList
        val nanos = System.nanoTime() - start
        nanos / n * 1E-6 // return average number of milliseconds
    }
}

implicit val random: Random = new Random // Needed for the coin flip.
val flip = WheelOfFortune.coinFlip // Get a coin flip
10 times flip.next // flip the coin 10 times and get the mean execution time in milliseconds



