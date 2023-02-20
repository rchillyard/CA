// Modules

// Trait to define the behavior of something which can be identified
trait Identifiable[+ID] {
    val identifier: ID // this is the identifier--it could be declared val or def

    override def toString: String = identifier.toString
}

// Companion object to Identifiable.
// Usual role is to provide an apply method.
object Identifiable {
    def apply[ID](id: ID): Identifiable[ID] = new Identifiable[ID] {
        val identifier: ID = id
    }
}

// Create an Identifiable with id: XXX
val identifier = Identifiable("XXX")

/**
 * Abstract base class for Identifiable objects.
 * Its role is to define common instance properties the sub-classes of Identifiable might need.
 *
 * @param identifier an identifier of type ID.
 * @param maybeV     an optional value of type V.
 * @tparam ID the identifier type (typically a String).
 * @tparam V  the value type.
 */
abstract class BaseIdentifiable[+ID, V](val identifier: ID, maybeV: Option[V]) extends Identifiable[ID] {
    // Override the toString method to include the optional value.
    override def toString: String = s"""{identifier=$identifier${maybeV map (" with value=" + _.toString) getOrElse ""}}"""
}

/**
 * Case class (a leaf type) which extends BaseIdentifiable and has a definite value.
 *
 * @param identifier an identifier of type ID.
 * @param value      the value of type V.
 * @tparam ID the identifier type (typically a String).
 * @tparam V  the value type.
 */
case class ValueIdentifier[+ID, V](override val identifier: ID, value: V) extends BaseIdentifiable(identifier, Some(value))

// NOTE that we could declare a companion object for ValueIdentifier but there's often no need

/**
 * An invalid (empty) identifier whose ID is Unit (i.e. a non-object).
 */
case object InvalidIdentifier extends BaseIdentifiable((), None) {
    override def toString: String = "InvalidIdentifier"
}

// Declare a ValueIdentifier with given id and value (both Strings in this case)
val x = ValueIdentifier("LVII", "Hello World!")

// Declare a InvalidIdentifier
val invalid = InvalidIdentifier