package org.junit.experimental.theories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DataPoint {
  String[] value() default {};
  
  Class<? extends Throwable>[] ignoredExceptions() default {};
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/experimental/theories/DataPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */