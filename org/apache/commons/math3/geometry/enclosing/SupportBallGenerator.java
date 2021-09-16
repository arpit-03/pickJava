package org.apache.commons.math3.geometry.enclosing;

import java.util.List;

public interface SupportBallGenerator<S extends org.apache.commons.math3.geometry.Space, P extends org.apache.commons.math3.geometry.Point<S>> {
  EnclosingBall<S, P> ballOnSupport(List<P> paramList);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/enclosing/SupportBallGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */