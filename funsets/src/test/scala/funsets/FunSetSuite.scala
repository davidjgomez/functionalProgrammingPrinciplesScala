package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("intersect contains all elements present in all sets") {
    new TestSets {
      val int1 = intersect(s1, s2)
      assert(!contains(int1, 1), "Intersect 1")
      assert(!contains(int1, 2), "Intersect 2")
      
      val s4 = union(s1, s2)
      val s5 = union(s2, s3)
      val s6 = union(s1, s3)
      val int2 = intersect(s4, s5)
      val int3 = intersect(s5, s6)
      val int4 = intersect(s4, s6)
      assert(contains(int2, 2), "Intersect 3")
      assert(contains(int3, 3), "Intersect 4")
      assert(contains(int4, 1), "Intersect 5")
      assert(!contains(int4, 2), "Intersect 6")
      assert(!contains(int3, 1), "Intersect 7")
      assert(!contains(int2, 3), "Intersect 8")
    }
  }

  test("diff contains all elements in first set that are not in second set") {
    new TestSets {
      val s4 = union(s1, s2)
      val s5 = union(s2, s3)
      val diff1 = diff(s4, s5)
      assert(contains(diff1, 1), "Diff 1")
      assert(!contains(diff1, 2), "Diff 2")
      
      val s6 = union(s4, s5)
      val diff2 = diff(s6, s3)
      assert(contains(diff2, 1), "Diff 1")
      assert(contains(diff2, 2), "Diff 2")
      assert(!contains(diff2, 3), "Diff 3")
    }
  }
  
  test("filter contains all elements in set that hold on function") {
    new TestSets {
      val s = filter(union(s1, s2), x => x > 1)
      assert(contains(s, 2), "Filter 1")
      assert(!contains(s, 1), "Filter 2")
      
      val s4 = filter(union(union(s1, s2), s3), x => x * 3 >= 6)
      assert(contains(s4, 2), "Filter 3")
      assert(contains(s4, 3), "Filter 4")
      assert(!contains(s4, 1), "Filter 5")
    }
  }

  
  
  test("forall finds if all elements in set satisfy a function") {
    def abs(x: Int): Int = if (x >= 0) x else -x
    assert(forall(x => x % 2 == 0, y => abs(y) >= 0), "Forall 1")
    assert(!forall(x => x > 0, y => y % 2 == 0), "Forall 2") 
  }
  
  test("exists finds if any element in set satisfy a function") {
    assert(exists(x => x % 2 == 0, y => y - 2 == 0), "Exists 1")
    assert(!exists(x => x > -500 && x < 150, y => y == -501 || y == 151), "Exists 2") 
  }
  
  test("map maps a set to another one applying a function") {
    def abs(x: Int): Int = if (x >= 0) x else -x
    assert(contains(map(x => x < 100, y => y % 2), 1), "Map 1")
    assert(!contains(map(x => x < 100, y => abs(y)), -50), "Map 2")
  }
}
