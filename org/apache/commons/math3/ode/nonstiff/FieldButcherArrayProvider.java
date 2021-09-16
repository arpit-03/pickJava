package org.apache.commons.math3.ode.nonstiff;

public interface FieldButcherArrayProvider<T extends org.apache.commons.math3.RealFieldElement<T>> {
  T[] getC();
  
  T[][] getA();
  
  T[] getB();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/FieldButcherArrayProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */