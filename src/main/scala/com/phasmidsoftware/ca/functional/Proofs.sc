// Proofs

import com.phasmidsoftware.ca.{Cons, MyList}

// Create a MyList of three elements and call it ys0
val ys0: Cons[Int] = MyList(0, 1, 2).asInstanceOf[Cons[Int]]

// According to the definition of length, the following should be true
ys0.length == 1 + ys0.next.length

// ys1 is the tail of ys0
val ys1: Cons[Int] = ys0.next.asInstanceOf[Cons[Int]]

// According to the definition of length, the following should be true
ys0.length == 1 + (1 + ys1.next.length)

// ys2 is the tail of ys1
val ys2: Cons[Int] = ys1.next.asInstanceOf[Cons[Int]]

// According to the definition of length, the following should be true
ys0.length == 1 + (1 + (1 + ys2.next.length))
