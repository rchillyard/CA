// Typeclasses

/**
 * Define a typeclass called Additive which knows how to add Xs.
 *
 * @tparam X the underlying type.
 */
trait Additive[X] {
    /**
     * Method to add two Xs. Note that it not an instance method of X,
     * but an instance method of Additive[X].
     *
     * @param y an X.
     * @param z another X.
     * @return the sum of y and z.
     */
    def +(y: X, z: X): X

    /**
     * Method to convert an Int into an X.
     *
     * @param x an Int.
     * @return an X.
     */
    def fromInt(x: Int): X

    /**
     * Value of zero.
     *
     * @return an X whose values is zero (the "identity" value for addition).
     */
    val zero: X = fromInt(0)
}

def sum[X: Additive](xs: Seq[X]): X = {
    val additiveX = implicitly[Additive[X]]
    xs.foldLeft(additiveX.zero)((a, x) => additiveX.+(a, x))
}

/**
 * For every underlying type which we want to extend with Additive,
 * we need to do the following.
 * Note that we can skip the trait and define all behavior in the implicit object.
 * But, this is the idiomatic way to do it.
 */
trait AdditiveInt extends Additive[Int] {
    def +(y: Int, z: Int): Int = y + z

    def fromInt(x: Int): Int = x
}

// Now, we declare an implicit object (or val) of type Additive[Int]
// so that we can invoke sum on a List[Int].
// Don't worry that we appear to use the name AdditiveInt twice--they are different namespaces.
implicit object AdditiveInt extends AdditiveInt

// Now we can actually sum a list of Int.
sum(List(1, 2, 3))