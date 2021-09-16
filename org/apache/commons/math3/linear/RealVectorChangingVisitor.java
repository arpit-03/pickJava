package org.apache.commons.math3.linear;

public interface RealVectorChangingVisitor {
  void start(int paramInt1, int paramInt2, int paramInt3);
  
  double visit(int paramInt, double paramDouble);
  
  double end();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RealVectorChangingVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */