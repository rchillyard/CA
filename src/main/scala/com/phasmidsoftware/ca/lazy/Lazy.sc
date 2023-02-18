// Lazy -- Call by name and call by value

// An ordinary call-by-value method to square an Int
def callByValue(x: Int): Int = x * x

// Invoke it with value 9
callByValue(9)
// Invoke it with {println("Hello"); value 9}
callByValue{println("Hello"); 9}

// A call-by-name method to square an Int
def callByName(x: => Int): Int = x * x

// Invoke it with value 9
callByName(9)
// Invoke it with {println("Hello"); value 9}
callByName{println("Hello"); 9}

// A call-by-name method which references its parameter exactly once.
def callByName1(x: => Int): Int = {
    lazy val xx = x // this is the only time we reference x.
    xx * xx // because xx is lazy, it only gets evaluated once.
}

// Invoke it with value 9
callByName1(9)
// Invoke it with {println("Hello"); value 9}: but only one Hello is printed.
callByName1{println("Hello"); 9}

// A call-by-name method which never references its parameter
def callByName2(x: => Int): Int = 42

// Invoke it with value 9
callByName2(9)
// Invoke it with {println("Hello"); value 9}: but no Hello is ever printed.
callByName2{println("Hello"); 9}


// A method which declares x as a Unit=>Int function.
// This is the equivalent of the callByName method--but the syntax and code aren't as elegant.
def callByName3(x: () => Int): Int = x() * x()

// Invoke it with lambda that yields 9
callByName3(() => 9)
// Invoke it with lambda that yields {println("Hello"); value 9}:
// Hello is printed twice as for callByName.
callByName3(() => {println("Hello"); 9})

// If you have a "method" which doesn't have either type- or value-parameters, you can just make it a lazy val
// (provided that it isn't an override of a super-method--in which case you must use def).
def greet = "Howdy, folks!"
greet

// Exact same thing:
lazy val greet = "Howdy, folks!"
greet

