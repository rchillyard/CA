package com.phasmidsoftware.ca.modules

import java.util.function.Supplier
import scala.util.Random

/**
 * This module includes some methods whose API is specifically designed to be called from Java.
 */
object MethodsForJava {

    /**
     * Method to return a generic random object supplier for a Java class.
     * Note that we use a scala.util.Random, but there is no confusion about the random types because the parameter required is a Long.
     * Note also that Supplier[X] is a Java type even though in the Java language it is written as Supplier<X>.
     * In Scala it would simply be a ()=>X, i.e., a Unit=>X.
     *
     * @param clazz the (Java) class of object to be supplied.
     * @param seed  the seed for the random number sequence.
     * @tparam X the type of the object(s) to be returned.
     * @return a Supplier[X].
     */
    def randomSupplier[X](clazz: Class[X], seed: Long): Supplier[X] = randomSupplier(clazz, new Random(seed))

    /**
     * Private method to return a generic random object supplier, given a Random.
     *
     * @param clazz  the (Java) class of object to be supplied.
     * @param random Random number generator.
     * @tparam X the type of the object(s) to be returned.
     * @return a Supplier[X].
     */
    private def randomSupplier[X](clazz: Class[X], random: Random): Supplier[X] = () => {
        // It would appear that Class (because it is a Java class) does not have a unapply method for pattern-matching
        if (clazz == classOf[java.lang.Integer]) random.nextInt().asInstanceOf[X]
        else if (clazz == classOf[java.lang.Double]) random.nextDouble().asInstanceOf[X]
        else if (clazz == classOf[java.lang.Boolean]) random.nextBoolean().asInstanceOf[X]
        else throw new Exception(s"randomSupplier: $clazz not supported")
    }
}

case class WrappedClass[X](clazz: Class[X])
