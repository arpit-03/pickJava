/*     */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultivariateFunctionPenaltyAdapter
/*     */   implements MultivariateFunction
/*     */ {
/*     */   private final MultivariateFunction bounded;
/*     */   private final double[] lower;
/*     */   private final double[] upper;
/*     */   private final double offset;
/*     */   private final double[] scale;
/*     */   
/*     */   public MultivariateFunctionPenaltyAdapter(MultivariateFunction bounded, double[] lower, double[] upper, double offset, double[] scale) {
/* 127 */     MathUtils.checkNotNull(lower);
/* 128 */     MathUtils.checkNotNull(upper);
/* 129 */     MathUtils.checkNotNull(scale);
/* 130 */     if (lower.length != upper.length) {
/* 131 */       throw new DimensionMismatchException(lower.length, upper.length);
/*     */     }
/* 133 */     if (lower.length != scale.length) {
/* 134 */       throw new DimensionMismatchException(lower.length, scale.length);
/*     */     }
/* 136 */     for (int i = 0; i < lower.length; i++) {
/*     */       
/* 138 */       if (upper[i] < lower[i]) {
/* 139 */         throw new NumberIsTooSmallException(Double.valueOf(upper[i]), Double.valueOf(lower[i]), true);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     this.bounded = bounded;
/* 144 */     this.lower = (double[])lower.clone();
/* 145 */     this.upper = (double[])upper.clone();
/* 146 */     this.offset = offset;
/* 147 */     this.scale = (double[])scale.clone();
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
/*     */   public double value(double[] point) {
/* 163 */     for (int i = 0; i < this.scale.length; i++) {
/* 164 */       if (point[i] < this.lower[i] || point[i] > this.upper[i]) {
/*     */         
/* 166 */         double sum = 0.0D;
/* 167 */         for (int j = i; j < this.scale.length; j++) {
/*     */           double overshoot;
/* 169 */           if (point[j] < this.lower[j]) {
/* 170 */             overshoot = this.scale[j] * (this.lower[j] - point[j]);
/* 171 */           } else if (point[j] > this.upper[j]) {
/* 172 */             overshoot = this.scale[j] * (point[j] - this.upper[j]);
/*     */           } else {
/* 174 */             overshoot = 0.0D;
/*     */           } 
/* 176 */           sum += FastMath.sqrt(overshoot);
/*     */         } 
/* 178 */         return this.offset + sum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     return this.bounded.value(point);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/MultivariateFunctionPenaltyAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */