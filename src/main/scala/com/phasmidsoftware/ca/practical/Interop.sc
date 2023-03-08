// Interoperability

// See [[src/main/java/com/phasmidsoftware/ca/util/JavaClients.java]] and
// the corresponding methods defined in [[src/main/scala/com/phasmidsoftware/ca/modules/MethodsForJava.scala]]

// The following shows how we can get the actual type of the underlying type of a List, despite the ravages of type erasure.

import scala.annotation.tailrec
import scala.reflect.ClassTag

@tailrec
def printList[X: ClassTag](xs: List[X]): Unit = {
    val clazz: Class[_] = implicitly[ClassTag[X]].runtimeClass
    xs match {
        case Nil =>
        case (h: Int) :: t => println(s"$clazz: $h"); printList(t)
    }
}

printList(List(1, 2, 3))