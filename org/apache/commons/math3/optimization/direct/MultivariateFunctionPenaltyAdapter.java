/*     */ package org.apache.commons.math3.optimization.direct;
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
/*     */ 
/*     */ @Deprecated
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
/* 129 */     MathUtils.checkNotNull(lower);
/* 130 */     MathUtils.checkNotNull(upper);
/* 131 */     MathUtils.checkNotNull(scale);
/* 132 */     if (lower.length != upper.length) {
/* 133 */       throw new DimensionMismatchException(lower.length, upper.length);
/*     */     }
/* 135 */     if (lower.length != scale.length) {
/* 136 */       throw new DimensionMismatchException(lower.length, scale.length);
/*     */     }
/* 138 */     for (int i = 0; i < lower.length; i++) {
/*     */       
/* 140 */       if (upper[i] < lower[i]) {
/* 141 */         throw new NumberIsTooSmallException(Double.valueOf(upper[i]), Double.valueOf(lower[i]), true);
/*     */       }
/*     */     } 
/*     */     
/* 145 */     this.bounded = bounded;
/* 146 */     this.lower = (double[])lower.clone();
/* 147 */     this.upper = (double[])upper.clone();
/* 148 */     this.offset = offset;
/* 149 */     this.scale = (double[])scale.clone();
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
/* 165 */     for (int i = 0; i < this.scale.length; i++) {
/* 166 */       if (point[i] < this.lower[i] || point[i] > this.upper[i]) {
/*     */         
/* 168 */         double sum = 0.0D;
/* 169 */         for (int j = i; j < this.scale.length; j++) {
/*     */           double overshoot;
/* 171 */           if (point[j] < this.lower[j]) {
/* 172 */             overshoot = this.scale[j] * (this.lower[j] - point[j]);
/* 173 */           } else if (point[j] > this.upper[j]) {
/* 174 */             overshoot = this.scale[j] * (point[j] - this.upper[j]);
/*     */           } else {
/* 176 */             overshoot = 0.0D;
/*     */           } 
/* 178 */           sum += FastMath.sqrt(overshoot);
/*     */         } 
/* 180 */         return this.offset + sum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return this.bounded.value(point);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/MultivariateFunctionPenaltyAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */