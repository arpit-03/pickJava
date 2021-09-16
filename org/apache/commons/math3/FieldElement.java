package org.apache.commons.math3;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

public interface FieldElement<T> {
  T add(T paramT) throws NullArgumentException;
  
  T subtract(T paramT) throws NullArgumentException;
  
  T negate();
  
  T multiply(int paramInt);
  
  T multiply(T paramT) throws NullArgumentException;
  
  T divide(T paramT) throws NullArgumentException, MathArithmeticException;
  
  T reciprocal() throws MathArithmeticException;
  
  Field<T> getField();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/FieldElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */