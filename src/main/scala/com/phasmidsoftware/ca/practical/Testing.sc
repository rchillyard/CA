// Testing

// Take a look at the "specification" files in [[src/test/scala/com/phasmidsoftware/ca/modules/]]

// In particular, for the testing of private methods, see [[src/test/scala/com/phasmidsoftware/ca/modules/WheelOfFortuneSpec.scala]]

// Sometimes, you need to test that an exception will be thrown. See [[src/test/scala/com/phasmidsoftware/ca/modules/MethodsForJavaSpec.scala]] ("throw an exception for String")

// For property-based testing, see RationalPropertySpec.
// Property-based testing is the most thorough way to test.

// See also MockSpec which isn't very complete but gives an idea how to do mocking.
// Mocking is useful/necessary when you have code to test that hasn't been written yet, or
// has a lot of state to deal with (e.g. a database), or
// is just too costly to keep firing up for testing your unit test code.