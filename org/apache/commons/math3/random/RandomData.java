package org.apache.commons.math3.random;

import java.util.Collection;
import org.apache.commons.math3.exception.NotANumberException;
import org.apache.commons.math3.exception.NotFiniteNumberException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;

@Deprecated
public interface RandomData {
  String nextHexString(int paramInt) throws NotStrictlyPositiveException;
  
  int nextInt(int paramInt1, int paramInt2) throws NumberIsTooLargeException;
  
  long nextLong(long paramLong1, long paramLong2) throws NumberIsTooLargeException;
  
  String nextSecureHexString(int paramInt) throws NotStrictlyPositiveException;
  
  int nextSecureInt(int paramInt1, int paramInt2) throws NumberIsTooLargeException;
  
  long nextSecureLong(long paramLong1, long paramLong2) throws NumberIsTooLargeException;
  
  long nextPoisson(double paramDouble) throws NotStrictlyPositiveException;
  
  double nextGaussian(double paramDouble1, double paramDouble2) throws NotStrictlyPositiveException;
  
  double nextExponential(double paramDouble) throws NotStrictlyPositiveException;
  
  double nextUniform(double paramDouble1, double paramDouble2) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException;
  
  double nextUniform(double paramDouble1, double paramDouble2, boolean paramBoolean) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException;
  
  int[] nextPermutation(int paramInt1, int paramInt2) throws NumberIsTooLargeException, NotStrictlyPositiveException;
  
  Object[] nextSample(Collection<?> paramCollection, int paramInt) throws NumberIsTooLargeException, NotStrictlyPositiveException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/RandomData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */