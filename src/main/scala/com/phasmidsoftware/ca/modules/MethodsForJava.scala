package com.phasmidsoftware.ca.modules

import java.lang
import java.util.function.Supplier
import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.Random

/**
 * This module includes some methods whose API is specifically designed to be called from Java.
 */
object MethodsForJava {

    def reverse[X](xs: java.util.List[X]): java.util.List[X] = {
        val javaXs: mutable.Seq[X] = xs.asScala
        javaXs.reverse.asJava
    }

    /**
     * Method to return a generic random object supplier for a Java class.
     * This method is designed to be called from Java, but can also be called from Scala (see unit test specification).
     * Note that we use a scala.util.Random, but there is no confusion about the random types because the parameter required is a Long.
     * Note also that Supplier[X] is a Java type even though in the Java language it is written as Supplier&lt;X&gt;.
     * In Scala it would simply be a ()=>X, i.e., a Unit=>X.
     *
     * @param clazz the (Java) class of object to be supplied.
     * @param seed  the seed for the random number sequence.
     * @tparam X the type of the object(s) to be returned.
     * @return a Supplier[X].
     */
    def randomSupplier[X](clazz: Class[X], seed: Long): Supplier[X] = randomSupplierPrivate(WrappedClass(clazz), new Random(seed))

    /**
     * Private method to return a generic random object supplier, given a Random.
     *
     * @param clazz  the (Java) class of object to be supplied.
     * @param random Random number generator.
     * @tparam X the type of the object(s) to be returned.
     * @return a Supplier[X].
     */
    private def randomSupplierPrivate[X](clazz: WrappedClass[X], random: Random): Supplier[X] = () => {
        clazz match {
            case WrappedClass(WrappedClass.classOfInteger) => random.nextInt().asInstanceOf[X]
            case WrappedClass(WrappedClass.classOfBoolean) => random.nextBoolean().asInstanceOf[X]
            case WrappedClass(WrappedClass.classOfDouble) => random.nextDouble().asInstanceOf[X]
            case _ => throw new Exception(s"randomSupplier: ${clazz.clazz} not supported")
        }
    }
}

/**
 * This class is defined just so that we can use pattern-matching inside randomSupplierPrivate.
 *
 * @param clazz a Class[X].
 * @tparam X the type of clazz.
 */
case class WrappedClass[X](clazz: Class[X])

/**
 * Companion object to WrappedClass.
 */
object WrappedClass {
    // Constant class values, used by pattern-matching
    val classOfInteger: Class[Integer] = classOf[Integer]
    val classOfBoolean: Class[lang.Boolean] = classOf[lang.Boolean]
    val classOfDouble: Class[lang.Double] = classOf[lang.Double]

    /**
     * This requires a custom-built unapply method.
     *
     * @param wrappedClass an instance of wrappedClass.
     * @tparam X the underlying type of wrappedClass.
     * @return an Option of Class[_].
     */
    def unapply[X](wrappedClass: WrappedClass[X]): Option[Class[_]] = Some(wrappedClass.clazz)
}
