package org.apache.commons.math3.geometry.euclidean.twod.hull;

import java.util.Collection;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.hull.ConvexHullGenerator;

public interface ConvexHullGenerator2D extends ConvexHullGenerator<Euclidean2D, Vector2D> {
  ConvexHull2D generate(Collection<Vector2D> paramCollection) throws NullArgumentException, ConvergenceException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/hull/ConvexHullGenerator2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */