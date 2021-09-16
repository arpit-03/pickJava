/*     */ package org.apache.commons.math3.optim.nonlinear.scalar;
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
/*     */ public class LeastSquaresConverter
/*     */   implements MultivariateFunction
/*     */ {
/*     */   private final MultivariateVectorFunction function;
/*     */   private final double[] observations;
/*     */   private final double[] weights;
/*     */   private final RealMatrix scale;
/*     */   
/*     */   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations) {
/*  73 */     this.function = function;
/*  74 */     this.observations = (double[])observations.clone();
/*  75 */     this.weights = null;
/*  76 */     this.scale = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, RealMatrix scale) {
/* 147 */     if (observations.length != scale.getColumnDimension()) {
/* 148 */       throw new DimensionMismatchException(observations.length, scale.getColumnDimension());
/*     */     }
/* 150 */     this.function = function;
/* 151 */     this.observations = (double[])observations.clone();
/* 152 */     this.weights = null;
/* 153 */     this.scale = scale.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double[] point) {
/* 159 */     double[] residuals = this.function.value(point);
/* 160 */     if (residuals.length != this.observations.length) {
/* 161 */       throw new DimensionMismatchException(residuals.length, this.observations.length);
/*     */     }
/* 163 */     for (int i = 0; i < residuals.length; i++) {
/* 164 */       residuals[i] = residuals[i] - this.observations[i];
/*     */     }
/*     */ 
/*     */     
/* 168 */     double sumSquares = 0.0D;
/* 169 */     if (this.weights != null) {
/* 170 */       for (int j = 0; j < residuals.length; j++) {
/* 171 */         double ri = residuals[j];
/* 172 */         sumSquares += this.weights[j] * ri * ri;
/*     */       } 
/* 174 */     } else if (this.scale != null) {
/* 175 */       for (double yi : this.scale.operate(residuals)) {
/* 176 */         sumSquares += yi * yi;
/*     */       }
/*     */     } else {
/* 179 */       for (double ri : residuals) {
/* 180 */         sumSquares += ri * ri;
/*     */       }
/*     */     } 
/*     */     
/* 184 */     return sumSquares;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/LeastSquaresConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */