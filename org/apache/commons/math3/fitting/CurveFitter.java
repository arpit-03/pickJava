/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.optim.InitialGuess;
/*     */ import org.apache.commons.math3.optim.MaxEval;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointVectorValuePair;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.ModelFunction;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.ModelFunctionJacobian;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.Target;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.Weight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class CurveFitter<T extends ParametricUnivariateFunction>
/*     */ {
/*     */   private final MultivariateVectorOptimizer optimizer;
/*     */   private final List<WeightedObservedPoint> observations;
/*     */   
/*     */   public CurveFitter(MultivariateVectorOptimizer optimizer) {
/*  66 */     this.optimizer = optimizer;
/*  67 */     this.observations = new ArrayList<WeightedObservedPoint>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObservedPoint(double x, double y) {
/*  81 */     addObservedPoint(1.0D, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObservedPoint(double weight, double x, double y) {
/*  94 */     this.observations.add(new WeightedObservedPoint(weight, x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObservedPoint(WeightedObservedPoint observed) {
/* 104 */     this.observations.add(observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeightedObservedPoint[] getObservations() {
/* 114 */     return this.observations.<WeightedObservedPoint>toArray(new WeightedObservedPoint[this.observations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearObservations() {
/* 121 */     this.observations.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] fit(T f, double[] initialGuess) {
/* 138 */     return fit(2147483647, f, initialGuess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] fit(int maxEval, T f, double[] initialGuess) {
/* 161 */     double[] target = new double[this.observations.size()];
/* 162 */     double[] weights = new double[this.observations.size()];
/* 163 */     int i = 0;
/* 164 */     for (WeightedObservedPoint point : this.observations) {
/* 165 */       target[i] = point.getY();
/* 166 */       weights[i] = point.getWeight();
/* 167 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 171 */     TheoreticalValuesFunction model = new TheoreticalValuesFunction((ParametricUnivariateFunction)f);
/*     */ 
/*     */     
/* 174 */     PointVectorValuePair optimum = this.optimizer.optimize(new OptimizationData[] { (OptimizationData)new MaxEval(maxEval), (OptimizationData)model.getModelFunction(), (OptimizationData)model.getModelFunctionJacobian(), (OptimizationData)new Target(target), (OptimizationData)new Weight(weights), (OptimizationData)new InitialGuess(initialGuess) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     return optimum.getPointRef();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class TheoreticalValuesFunction
/*     */   {
/*     */     private final ParametricUnivariateFunction f;
/*     */ 
/*     */ 
/*     */     
/*     */     TheoreticalValuesFunction(ParametricUnivariateFunction f) {
/* 194 */       this.f = f;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ModelFunction getModelFunction() {
/* 201 */       return new ModelFunction(new MultivariateVectorFunction()
/*     */           {
/*     */             public double[] value(double[] point)
/*     */             {
/* 205 */               double[] values = new double[CurveFitter.this.observations.size()];
/* 206 */               int i = 0;
/* 207 */               for (WeightedObservedPoint observed : CurveFitter.this.observations) {
/* 208 */                 values[i++] = CurveFitter.TheoreticalValuesFunction.this.f.value(observed.getX(), point);
/*     */               }
/*     */               
/* 211 */               return values;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ModelFunctionJacobian getModelFunctionJacobian() {
/* 220 */       return new ModelFunctionJacobian(new MultivariateMatrixFunction()
/*     */           {
/*     */             public double[][] value(double[] point) {
/* 223 */               double[][] jacobian = new double[CurveFitter.this.observations.size()][];
/* 224 */               int i = 0;
/* 225 */               for (WeightedObservedPoint observed : CurveFitter.this.observations) {
/* 226 */                 jacobian[i++] = CurveFitter.TheoreticalValuesFunction.this.f.gradient(observed.getX(), point);
/*     */               }
/* 228 */               return jacobian;
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/CurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */