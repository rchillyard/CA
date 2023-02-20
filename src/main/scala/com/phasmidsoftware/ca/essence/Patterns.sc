// Pattern-matching

case class Complex(r: Double, i: Double) {
    def *(c: Complex): Complex = Complex(r * c.r - i * c.i, r * c.i + i * c.r)
}

val zero = Complex(0, 0)
val unit_pi = Complex(-1, 0)
val unit_piBy2 = Complex(0, 1)

val z = unit_piBy2 * unit_piBy2

def show(c: Complex): String = c match {
    case `zero` => "zero" // variable pattern which matches a variable called zero
    case Complex(0, 1) => "pi/2" // constant extractor pattern
    case Complex(r, i) if i == 0 => s"real number: $r" // guarded extractor
    case Complex(r, i) => s"complex number: $r + i$i" // extractor
    case x => s"error: $x" // variable pattern -- matches everything
    // The following looks OK but causes compiler warnings because it can't be reached.
    case _ => "" // matches everything anonymously.
}

show(z)

// Now, we will demonstrate sage of an explicit unapply method.
case class Factor(f: Int) {
    private def isMultiple(x: Int): Boolean = x % f == 0

    // Unapply always returns an Option of a tuple of all appropriate values (members for a case class)
    // But here, there's only one value so rather than have a 1-tuple, we just use Int directly.
    // Any time there's no "match" we return None.
    def unapply(x: Int): Option[Int] = if (isMultiple(x)) Some(x / f) else None
}

lazy val dividesBy3: Factor = Factor(3)
lazy val dividesBy5 = Factor(5)
lazy val dividesBy3And5 = Factor(3 * 5)

def fizzBuzz(x: Int): String = x match {
    case dividesBy3And5(_) => "FizzBuzz" // we match 3 and 5 but we ignore the actual result of unapply.
    case dividesBy3(_) => "Fizz"  // we match 3 but we ignore the actual result of unapply.
    case dividesBy5(_) => "Buzz" // we match 5 but we ignore the actual result of unapply.
    case _ => x.toString // just return the value x as a String.
}

val strings = for (x <- 1 to 15) yield fizzBuzz(x)
println(strings mkString("", "\n", ""))
