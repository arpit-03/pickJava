package org.apache.commons.math3.optimization.univariate;

import org.apache.commons.math3.optimization.BaseOptimizer;
import org.apache.commons.math3.optimization.GoalType;

@Deprecated
public interface BaseUnivariateOptimizer<FUNC extends org.apache.commons.math3.analysis.UnivariateFunction> extends BaseOptimizer<UnivariatePointValuePair> {
  UnivariatePointValuePair optimize(int paramInt, FUNC paramFUNC, GoalType paramGoalType, double paramDouble1, double paramDouble2);
  
  UnivariatePointValuePair optimize(int paramInt, FUNC paramFUNC, GoalType paramGoalType, double paramDouble1, double paramDouble2, double paramDouble3);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/BaseUnivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */