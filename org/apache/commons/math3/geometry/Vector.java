package org.apache.commons.math3.geometry;

import java.text.NumberFormat;
import org.apache.commons.math3.exception.MathArithmeticException;

public interface Vector<S extends Space> extends Point<S> {
  Vector<S> getZero();
  
  double getNorm1();
  
  double getNorm();
  
  double getNormSq();
  
  double getNormInf();
  
  Vector<S> add(Vector<S> paramVector);
  
  Vector<S> add(double paramDouble, Vector<S> paramVector);
  
  Vector<S> subtract(Vector<S> paramVector);
  
  Vector<S> subtract(double paramDouble, Vector<S> paramVector);
  
  Vector<S> negate();
  
  Vector<S> normalize() throws MathArithmeticException;
  
  Vector<S> scalarMultiply(double paramDouble);
  
  boolean isInfinite();
  
  double distance1(Vector<S> paramVector);
  
  double distance(Vector<S> paramVector);
  
  double distanceInf(Vector<S> paramVector);
  
  double distanceSq(Vector<S> paramVector);
  
  double dotProduct(Vector<S> paramVector);
  
  String toString(NumberFormat paramNumberFormat);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/Vector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */