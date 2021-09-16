package org.apache.commons.math3;

public interface Field<T> {
  T getZero();
  
  T getOne();
  
  Class<? extends FieldElement<T>> getRuntimeClass();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/Field.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */