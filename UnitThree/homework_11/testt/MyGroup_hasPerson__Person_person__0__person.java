/*
 * Test data strategy for MyGroup.
 *
 * Generated by JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178), 2020-05-22 19:46 +0800.
 * (do not modify this comment, it is used by JMLUnitNG clean-up routines)
 */

import org.jmlspecs.jmlunitng.iterator.ObjectArrayIterator;
import org.jmlspecs.jmlunitng.iterator.RepeatedAccessIterator;

/**
 * Test data strategy for MyGroup. Provides
 * test values for parameter "Person person" 
 * of method "boolean hasPerson(Person)". 
 * 
 * @author JMLUnitNG 1.4 (116/OpenJML-20131218-REV3178)
 * @version 2020-05-22 19:46 +0800
 */
public /*@ nullable_by_default */ class MyGroup_hasPerson__Person_person__0__person
  extends MyGroup_ClassStrategy_Person {
  /**
   * @return local-scope values for parameter 
   *  "Person person".
   */
  public RepeatedAccessIterator<?> localValues() {
    return new ObjectArrayIterator<Object>
    (new Object[]
     { /* add local-scope Person values or generators here */ });
  }

  /**
   * Constructor.
   * The use of reflection can be controlled here for  
   * "Person person" of method "boolean hasPerson(Person)" 
   * by changing the parameters to <code>setReflective</code>
   * and <code>setMaxRecursionDepth<code>.
   * In addition, the data generators used can be changed by adding 
   * additional data class lines, or by removing some of the automatically 
   * generated ones. Since this is the lowest level of strategy, the 
   * behavior will be exactly as you specify here if you clear the existing 
   * list of classes first.
   *
   * @see NonPrimitiveStrategy#addDataClass(Class<?>)
   * @see NonPrimitiveStrategy#clearDataClasses()
   * @see ObjectStrategy#setReflective(boolean)
   * @see ObjectStrategy#setMaxRecursionDepth(int)
   */
  public MyGroup_hasPerson__Person_person__0__person() {
    super();
    // uncomment to control the maximum reflective instantiation
    // recursion depth, 0 by default
    // setMaxRecursionDepth(0);
  }
}
