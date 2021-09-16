package org.apache.commons.math3.optimization;

@Deprecated
public interface BaseMultivariateOptimizer<FUNC extends org.apache.commons.math3.analysis.MultivariateFunction> extends BaseOptimizer<PointValuePair> {
  @Deprecated
  PointValuePair optimize(int paramInt, FUNC paramFUNC, GoalType paramGoalType, double[] paramArrayOfdouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/BaseMultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */