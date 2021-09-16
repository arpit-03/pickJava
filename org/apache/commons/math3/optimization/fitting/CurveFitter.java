/*     */ package org.apache.commons.math3.optimization.fitting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
/*     */ import org.apache.commons.math3.optimization.DifferentiableMultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.optimization.MultivariateDifferentiableVectorOptimizer;
/*     */ import org.apache.commons.math3.optimization.PointVectorValuePair;
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
/*     */ @Deprecated
/*     */ public class CurveFitter<T extends ParametricUnivariateFunction>
/*     */ {
/*     */   @Deprecated
/*     */   private final DifferentiableMultivariateVectorOptimizer oldOptimizer;
/*     */   private final MultivariateDifferentiableVectorOptimizer optimizer;
/*     */   private final List<WeightedObservedPoint> observations;
/*     */   
/*     */   @Deprecated
/*     */   public CurveFitter(DifferentiableMultivariateVectorOptimizer optimizer) {
/*  70 */     this.oldOptimizer = optimizer;
/*  71 */     this.optimizer = null;
/*  72 */     this.observations = new ArrayList<WeightedObservedPoint>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CurveFitter(MultivariateDifferentiableVectorOptimizer optimizer) {
/*  80 */     this.oldOptimizer = null;
/*  81 */     this.optimizer = optimizer;
/*  82 */     this.observations = new ArrayList<WeightedObservedPoint>();
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
/*  96 */     addObservedPoint(1.0D, x, y);
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
/* 109 */     this.observations.add(new WeightedObservedPoint(weight, x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObservedPoint(WeightedObservedPoint observed) {
/* 119 */     this.observations.add(observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeightedObservedPoint[] getObservations() {
/* 129 */     return this.observations.<WeightedObservedPoint>toArray(new WeightedObservedPoint[this.observations.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearObservations() {
/* 136 */     this.observations.clear();
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
/* 153 */     return fit(2147483647, f, initialGuess);
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
/*     */   public double[] fit(int maxEval, T f, double[] initialGuess) {
/*     */     PointVectorValuePair optimum;
/* 176 */     double[] target = new double[this.observations.size()];
/* 177 */     double[] weights = new double[this.observations.size()];
/* 178 */     int i = 0;
/* 179 */     for (WeightedObservedPoint point : this.observations) {
/* 180 */       target[i] = point.getY();
/* 181 */       weights[i] = point.getWeight();
/* 182 */       i++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (this.optimizer == null) {
/*     */       
/* 189 */       optimum = this.oldOptimizer.optimize(maxEval, (MultivariateVectorFunction)new OldTheoreticalValuesFunction((ParametricUnivariateFunction)f), target, weights, initialGuess);
/*     */     } else {
/*     */       
/* 192 */       optimum = this.optimizer.optimize(maxEval, (MultivariateVectorFunction)new TheoreticalValuesFunction((ParametricUnivariateFunction)f), target, weights, initialGuess);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     return optimum.getPointRef();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private class OldTheoreticalValuesFunction
/*     */     implements DifferentiableMultivariateVectorFunction
/*     */   {
/*     */     private final ParametricUnivariateFunction f;
/*     */ 
/*     */ 
/*     */     
/*     */     OldTheoreticalValuesFunction(ParametricUnivariateFunction f) {
/* 211 */       this.f = f;
/*     */     }
/*     */ 
/*     */     
/*     */     public MultivariateMatrixFunction jacobian() {
/* 216 */       return new MultivariateMatrixFunction()
/*     */         {
/*     */           public double[][] value(double[] point) {
/* 219 */             double[][] jacobian = new double[CurveFitter.this.observations.size()][];
/*     */             
/* 221 */             int i = 0;
/* 222 */             for (WeightedObservedPoint observed : CurveFitter.this.observations) {
/* 223 */               jacobian[i++] = CurveFitter.OldTheoreticalValuesFunction.this.f.gradient(observed.getX(), point);
/*     */             }
/*     */             
/* 226 */             return jacobian;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] value(double[] point) {
/* 234 */       double[] values = new double[CurveFitter.this.observations.size()];
/* 235 */       int i = 0;
/* 236 */       for (WeightedObservedPoint observed : CurveFitter.this.observations) {
/* 237 */         values[i++] = this.f.value(observed.getX(), point);
/*     */       }
/*     */       
/* 240 */       return values;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class TheoreticalValuesFunction
/*     */     implements MultivariateDifferentiableVectorFunction
/*     */   {
/*     */     private final ParametricUnivariateFunction f;
/*     */ 
/*     */ 
/*     */     
/*     */     TheoreticalValuesFunction(ParametricUnivariateFunction f) {
/* 254 */       this.f = f;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] value(double[] point) {
/* 260 */       double[] values = new double[CurveFitter.this.observations.size()];
/* 261 */       int i = 0;
/* 262 */       for (WeightedObservedPoint observed : CurveFitter.this.observations) {
/* 263 */         values[i++] = this.f.value(observed.getX(), point);
/*     */       }
/*     */       
/* 266 */       return values;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DerivativeStructure[] value(DerivativeStructure[] point) {
/* 273 */       double[] parameters = new double[point.length];
/* 274 */       for (int k = 0; k < point.length; k++) {
/* 275 */         parameters[k] = point[k].getValue();
/*     */       }
/*     */ 
/*     */       
/* 279 */       DerivativeStructure[] values = new DerivativeStructure[CurveFitter.this.observations.size()];
/* 280 */       int i = 0;
/* 281 */       for (WeightedObservedPoint observed : CurveFitter.this.observations) {
/*     */ 
/*     */ 
/*     */         
/* 285 */         DerivativeStructure vi = new DerivativeStructure(point.length, 1, this.f.value(observed.getX(), parameters));
/* 286 */         for (int j = 0; j < point.length; j++) {
/* 287 */           vi = vi.add(new DerivativeStructure(point.length, 1, j, 0.0D));
/*     */         }
/*     */         
/* 290 */         values[i++] = vi;
/*     */       } 
/*     */ 
/*     */       
/* 294 */       return values;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/fitting/CurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */