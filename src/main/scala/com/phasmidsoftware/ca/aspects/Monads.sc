// For-comprehensions and Monads

// We CAN use a for-comprehension like a loop in Java:
val xs = Array[Int](1, 2, 3)
var sum = 0
for (x <- xs) sum += x
println(sum)

// But we don't NEED to.
val sum = xs.foldLeft(0)(_ + _)

// Or, more simply
val sum = xs.sum

// But, we can do so much more with a for-comprehension
