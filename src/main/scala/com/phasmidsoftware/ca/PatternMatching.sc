// Pattern Matching

import scala.annotation.tailrec

val m = Map(1 -> "uno", 2 -> "dos", 3 -> "tres")

// Here we define first as 1 -> "uno" and rest as the remaining key-value pairs of m.
val first :: rest = m.toList

@tailrec
def printList(xs: List[Int]): Unit = // Method to print a list of Int.
    xs match {
        // A constant case pattern on the left hand side of =>
        case Nil => // Do nothing (terminating condition)
        // A simple case pattern, based on the case class ::, on the left hand side of =>
        case h :: t => println(h); printList(t) // print the head then invoke printlList on the tail
    }

printList(List(1, 2, -1)) // should show 1 2 and -1 on separate lines

// Method to print a list of Any.
@tailrec
def printListAny(xs: List[Any]): Unit =
    xs match {
        // A constant case pattern on the left hand side of =>
        case Nil => // Do nothing (terminating condition)
        // A nested pattern, similar to the next case, but matching a list of exactly two elements
        case h :: x :: Nil => println(s"$h (penultimate), $x (last)")
        // A simple case pattern, based on the case class ::, on the left hand side of =>
        case h :: t =>
            h match {
                // Another pattern match, this time on the type Int.
                case x: Int => println(x+1); printListAny(t) // if h is an Int, print h+1 then invoke printlList on the tail
                // The following matches anything.
                case _ => println(h); printListAny(t) // print the head then invoke printlList on the tail
            }
    }

printListAny(List(0, "2", -1, math.Pi, math.E)) // should show 1 2, 0, pi (penultimate) and e (last) on separate lines

def printKV(xs: (Int, String)): Unit =
    xs match {
        // A simple case pattern but based on extracting a Tuple2 on the left hand side of =>
        case (k, v) => println(s"$k: $v") // print the values of k and v
    }

// Here, we have a pattern with a guard, this time in a for-comprehension, the generator is embedded in the pattern.
for (kv@(k, v) <- m if k % 2 != 0) printKV(kv) // for every key-value pair in m, if k is odd, printKV for key and value: should see 1: uno and 3: tres

// Define a case class representing a complex number in Cartesian form.
// Case classes are DESIGNED for pattern-matching
case class Complex(r: Double, i: Double)

// Define the Complex for e^ipi
val pi = Complex(-1, 0)

// Here we have an extractor pattern on the left-hand-side of a variable definition
val Complex(minusOne, zero) = pi

// Now print the values of minusOne and zero to show that it works
println(s"minusOne: $minusOne, zero: $zero")

