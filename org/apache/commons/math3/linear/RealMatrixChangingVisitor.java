package org.apache.commons.math3.linear;

public interface RealMatrixChangingVisitor {
  void start(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  double visit(int paramInt1, int paramInt2, double paramDouble);
  
  double end();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RealMatrixChangingVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */