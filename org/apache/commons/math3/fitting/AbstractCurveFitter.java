/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCurveFitter
/*     */ {
/*     */   public double[] fit(Collection<WeightedObservedPoint> points) {
/*  63 */     return getOptimizer().optimize(getProblem(points)).getPoint().toArray();
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
/*     */   protected LeastSquaresOptimizer getOptimizer() {
/*  76 */     return (LeastSquaresOptimizer)new LevenbergMarquardtOptimizer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> paramCollection);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class TheoreticalValuesFunction
/*     */   {
/*     */     private final ParametricUnivariateFunction f;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] points;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TheoreticalValuesFunction(ParametricUnivariateFunction f, Collection<WeightedObservedPoint> observations) {
/* 103 */       this.f = f;
/*     */       
/* 105 */       int len = observations.size();
/* 106 */       this.points = new double[len];
/* 107 */       int i = 0;
/* 108 */       for (WeightedObservedPoint obs : observations) {
/* 109 */         this.points[i++] = obs.getX();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MultivariateVectorFunction getModelFunction() {
/* 117 */       return new MultivariateVectorFunction()
/*     */         {
/*     */           public double[] value(double[] p) {
/* 120 */             int len = AbstractCurveFitter.TheoreticalValuesFunction.this.points.length;
/* 121 */             double[] values = new double[len];
/* 122 */             for (int i = 0; i < len; i++) {
/* 123 */               values[i] = AbstractCurveFitter.TheoreticalValuesFunction.this.f.value(AbstractCurveFitter.TheoreticalValuesFunction.this.points[i], p);
/*     */             }
/*     */             
/* 126 */             return values;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MultivariateMatrixFunction getModelFunctionJacobian() {
/* 135 */       return new MultivariateMatrixFunction()
/*     */         {
/*     */           public double[][] value(double[] p) {
/* 138 */             int len = AbstractCurveFitter.TheoreticalValuesFunction.this.points.length;
/* 139 */             double[][] jacobian = new double[len][];
/* 140 */             for (int i = 0; i < len; i++) {
/* 141 */               jacobian[i] = AbstractCurveFitter.TheoreticalValuesFunction.this.f.gradient(AbstractCurveFitter.TheoreticalValuesFunction.this.points[i], p);
/*     */             }
/* 143 */             return jacobian;
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/AbstractCurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */