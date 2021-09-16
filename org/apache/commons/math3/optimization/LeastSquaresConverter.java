/*     */ package org.apache.commons.math3.optimization;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
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
/*     */ public class LeastSquaresConverter
/*     */   implements MultivariateFunction
/*     */ {
/*     */   private final MultivariateVectorFunction function;
/*     */   private final double[] observations;
/*     */   private final double[] weights;
/*     */   private final RealMatrix scale;
/*     */   
/*     */   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations) {
/*  77 */     this.function = function;
/*  78 */     this.observations = (double[])observations.clone();
/*  79 */     this.weights = null;
/*  80 */     this.scale = null;
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
/*     */   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, double[] weights) {
/* 113 */     if (observations.length != weights.length) {
/* 114 */       throw new DimensionMismatchException(observations.length, weights.length);
/*     */     }
/* 116 */     this.function = function;
/* 117 */     this.observations = (double[])observations.clone();
/* 118 */     this.weights = (double[])weights.clone();
/* 119 */     this.scale = null;
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
/*     */   
/*     */   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, RealMatrix scale) {
/* 143 */     if (observations.length != scale.getColumnDimension()) {
/* 144 */       throw new DimensionMismatchException(observations.length, scale.getColumnDimension());
/*     */     }
/* 146 */     this.function = function;
/* 147 */     this.observations = (double[])observations.clone();
/* 148 */     this.weights = null;
/* 149 */     this.scale = scale.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double[] point) {
/* 155 */     double[] residuals = this.function.value(point);
/* 156 */     if (residuals.length != this.observations.length) {
/* 157 */       throw new DimensionMismatchException(residuals.length, this.observations.length);
/*     */     }
/* 159 */     for (int i = 0; i < residuals.length; i++) {
/* 160 */       residuals[i] = residuals[i] - this.observations[i];
/*     */     }
/*     */ 
/*     */     
/* 164 */     double sumSquares = 0.0D;
/* 165 */     if (this.weights != null) {
/* 166 */       for (int j = 0; j < residuals.length; j++) {
/* 167 */         double ri = residuals[j];
/* 168 */         sumSquares += this.weights[j] * ri * ri;
/*     */       } 
/* 170 */     } else if (this.scale != null) {
/* 171 */       for (double yi : this.scale.operate(residuals)) {
/* 172 */         sumSquares += yi * yi;
/*     */       }
/*     */     } else {
/* 175 */       for (double ri : residuals) {
/* 176 */         sumSquares += ri * ri;
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return sumSquares;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/LeastSquaresConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */