package org.junit.validator;

import java.util.List;
import org.junit.runners.model.TestClass;

public interface TestClassValidator {
  List<Exception> validateTestClass(TestClass paramTestClass);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/validator/TestClassValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */