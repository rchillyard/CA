// Lazy Evaluation

val integers = LazyList from 1

integers take 10 foreach println // prints the first ten integers

integers filter (_ % 2 == 0) take 10 foreach println // prints the first ten even integers

def squareCallByValue(x: Int): Int = x * x // definition of square using call-by-value (strict) parameter

def squareCallByName(x: => Int): Int = x * x // definition of square using call-by-name (non-strict) parameter

squareCallByValue(3) // evaluates to 9

squareCallByName(3) // also evaluates to 9

squareCallByValue{println("Hello"); 3}  // evaluates to 9 but also prints Hello once

squareCallByName{println("Hello"); 3}  // evaluates to 9 but also prints Hello twice

val fib: LazyList[BigInt] = 0 #:: fib.scanLeft(BigInt(1))(_+_) // defines the Fibonacci numbers up through infinity

fib take 10 foreach println // prints the first 10 Fibonacci numbers