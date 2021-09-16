package org.apache.commons.math3.geometry.hull;

import java.util.Collection;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.NullArgumentException;

public interface ConvexHullGenerator<S extends org.apache.commons.math3.geometry.Space, P extends org.apache.commons.math3.geometry.Point<S>> {
  ConvexHull<S, P> generate(Collection<P> paramCollection) throws NullArgumentException, ConvergenceException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/hull/ConvexHullGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */