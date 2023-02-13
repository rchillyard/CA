// Expressions

1 + 1 // A simple expression: 1 plus 1 is 2
    // the type of this expression is Int
    // the value of this expression is 2

println("Hello World!") // NOTE that this does not yield a value (there is no res1: String...")
    // That is to say it is a statement.
    // But it does cause a side-effect to occur: a message is printed in the console

def f(x: Int): Int = if (x==1) 1 else x * f(x-1) // non-tail-recursive factorial

f(5)    // should return an Int with value 120

def g(r: BigInt, x: Int): BigInt = // Tail-recursive method to calculate factorial(x)
    if (x==1) r else g(r * x, x - 1)

g(1, 100) // Tail-Recursive version can easily get the 100th Factorial


