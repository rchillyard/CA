// Lazy Evaluation

// Defines all positive integers (in Scala 2.13, use LazyList instead of Stream)
val integers = Stream from 1

// prints the first ten integers
integers take 10 foreach println

// prints the first ten even integers
integers filter (_ % 2 == 0) take 10 foreach println

// definition of square using call-by-value (strict) parameter
def squareCallByValue(x: Int): Int = x * x

// definition of square using call-by-name (non-strict) parameter
def squareCallByName(x: => Int): Int = x * x

squareCallByValue(3) // evaluates to 9

squareCallByName(3) // also evaluates to 9

squareCallByValue {
    println("Hello"); 3
}  // evaluates to 9 but also prints Hello once

squareCallByName {
    println("Hello"); 3
}  // evaluates to 9 but also prints Hello twice

// defines the Fibonacci numbers up through infinity
val fib: Stream[BigInt] = 0 #:: fib.scanLeft(BigInt(1))(_ + _)

fib take 10 foreach println // prints the first 10 Fibonacci numbers