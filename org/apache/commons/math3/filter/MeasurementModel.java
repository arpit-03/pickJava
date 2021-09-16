package org.apache.commons.math3.filter;

import org.apache.commons.math3.linear.RealMatrix;

public interface MeasurementModel {
  RealMatrix getMeasurementMatrix();
  
  RealMatrix getMeasurementNoise();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/filter/MeasurementModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */