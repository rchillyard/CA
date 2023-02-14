// Function examples

val f1: Int => Int = _ * 2 // a lambda which multiplies by 2

val f2: Int => Double = _ + 0.5 // a lambda which adds one half

// functional composition using andThen
val f3: Int => Double = f1 andThen f2

f3(3) // should yield 6.5

def m1(x: Int): Int = x * 2 // equivalent to f1

def m2(x: Int): Double = x + 0.5 // equivalent to f2

val f4: Int => Double = (m1 _) andThen m2 // equivalent to f3
// But note that the syntax requires the conversion of m1 to be explicit

f4(3) // should yield 6.5

