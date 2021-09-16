package org.apache.commons.math3.linear;

public interface FieldVectorPreservingVisitor<T extends org.apache.commons.math3.FieldElement<?>> {
  void start(int paramInt1, int paramInt2, int paramInt3);
  
  void visit(int paramInt, T paramT);
  
  T end();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/FieldVectorPreservingVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */