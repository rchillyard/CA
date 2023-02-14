import java.util.Locale
// Objects and Classes

// An object: note that there are no constructor parameters nor any parametric (generic) types
object HelloWorld {
    override def toString: String = "Hello World!"
}

// Print the String representation of HelloWorld
// Note that there's no need to extract HelloWorld into a val--we just refer to it here
println(HelloWorld)


/**
 * If you were creating a main program for some application you would
 * need to extend App.
 * You can do it without App but then you would have to declare
 * a main method (like in Java) which is a bit harder.
 * Any main program must contain at least one object because the JVM has to
 * be able to load a "class" file into the class loader to get started.
 */
object MainProgram extends App {
    // does something useful but cannot return a result
}

// So far, we've seen no need for any class to be defined.
// Let's create a HelloWorld class that can greet in a given language

/**
 * But first, lets' create a trait for a Greeting with one method: greet
 */
trait Greeting {
    def greet: String
}

/**
 * Now, we create a class with a (constructor) parameter locale which determines the text of the greeting.
 *
 * Note that, if this was a .scala module, the earlier object that we called HelloWorld would (?) be considered to be the companion object of the following class.
 * Since this is a .sc worksheet, we are just overriding the previous declaration of HelloWorld.
 * That's why we get a compiler warning.
 *
 * @param locale a locale.
 */
class HelloWorld(locale: Locale) extends Greeting {
    def greet: String = locale match {
        case Locale.FRANCE => "Bonjour le Monde!"
        case Locale.GERMANY => "Hallo Welt!"
        case Locale.CHINA => "你好世界"
        case _ => "Hello World!"
    }
}

// Now we instantiate a French Hello World and greet with it.
// Note that because HelloWorld is a class (not a case class), we need the "new" keyword.
new HelloWorld(Locale.FRANCE).greet