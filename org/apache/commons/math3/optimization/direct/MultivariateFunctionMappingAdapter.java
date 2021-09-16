/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.function.Logit;
/*     */ import org.apache.commons.math3.analysis.function.Sigmoid;
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
/*     */ @Deprecated
/*     */ public class MultivariateFunctionMappingAdapter
/*     */   implements MultivariateFunction
/*     */ {
/*     */   private final MultivariateFunction bounded;
/*     */   private final Mapper[] mappers;
/*     */   
/*     */   public MultivariateFunctionMappingAdapter(MultivariateFunction bounded, double[] lower, double[] upper) {
/* 102 */     MathUtils.checkNotNull(lower);
/* 103 */     MathUtils.checkNotNull(upper);
/* 104 */     if (lower.length != upper.length)
/* 105 */       throw new DimensionMismatchException(lower.length, upper.length); 
/*     */     int i;
/* 107 */     for (i = 0; i < lower.length; i++) {
/*     */       
/* 109 */       if (upper[i] < lower[i]) {
/* 110 */         throw new NumberIsTooSmallException(Double.valueOf(upper[i]), Double.valueOf(lower[i]), true);
/*     */       }
/*     */     } 
/*     */     
/* 114 */     this.bounded = bounded;
/* 115 */     this.mappers = new Mapper[lower.length];
/* 116 */     for (i = 0; i < this.mappers.length; i++) {
/* 117 */       if (Double.isInfinite(lower[i])) {
/* 118 */         if (Double.isInfinite(upper[i])) {
/*     */           
/* 120 */           this.mappers[i] = new NoBoundsMapper();
/*     */         } else {
/*     */           
/* 123 */           this.mappers[i] = new UpperBoundMapper(upper[i]);
/*     */         }
/*     */       
/* 126 */       } else if (Double.isInfinite(upper[i])) {
/*     */         
/* 128 */         this.mappers[i] = new LowerBoundMapper(lower[i]);
/*     */       } else {
/*     */         
/* 131 */         this.mappers[i] = new LowerUpperBoundMapper(lower[i], upper[i]);
/*     */       } 
/*     */     } 
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
/*     */   public double[] unboundedToBounded(double[] point) {
/* 145 */     double[] mapped = new double[this.mappers.length];
/* 146 */     for (int i = 0; i < this.mappers.length; i++) {
/* 147 */       mapped[i] = this.mappers[i].unboundedToBounded(point[i]);
/*     */     }
/*     */     
/* 150 */     return mapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] boundedToUnbounded(double[] point) {
/* 161 */     double[] mapped = new double[this.mappers.length];
/* 162 */     for (int i = 0; i < this.mappers.length; i++) {
/* 163 */       mapped[i] = this.mappers[i].boundedToUnbounded(point[i]);
/*     */     }
/*     */     
/* 166 */     return mapped;
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
/*     */   public double value(double[] point) {
/* 181 */     return this.bounded.value(unboundedToBounded(point));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface Mapper
/*     */   {
/*     */     double unboundedToBounded(double param1Double);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     double boundedToUnbounded(double param1Double);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class NoBoundsMapper
/*     */     implements Mapper
/*     */   {
/*     */     public double unboundedToBounded(double y) {
/* 211 */       return y;
/*     */     }
/*     */ 
/*     */     
/*     */     public double boundedToUnbounded(double x) {
/* 216 */       return x;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LowerBoundMapper
/*     */     implements Mapper
/*     */   {
/*     */     private final double lower;
/*     */ 
/*     */ 
/*     */     
/*     */     LowerBoundMapper(double lower) {
/* 231 */       this.lower = lower;
/*     */     }
/*     */ 
/*     */     
/*     */     public double unboundedToBounded(double y) {
/* 236 */       return this.lower + FastMath.exp(y);
/*     */     }
/*     */ 
/*     */     
/*     */     public double boundedToUnbounded(double x) {
/* 241 */       return FastMath.log(x - this.lower);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UpperBoundMapper
/*     */     implements Mapper
/*     */   {
/*     */     private final double upper;
/*     */ 
/*     */ 
/*     */     
/*     */     UpperBoundMapper(double upper) {
/* 256 */       this.upper = upper;
/*     */     }
/*     */ 
/*     */     
/*     */     public double unboundedToBounded(double y) {
/* 261 */       return this.upper - FastMath.exp(-y);
/*     */     }
/*     */ 
/*     */     
/*     */     public double boundedToUnbounded(double x) {
/* 266 */       return -FastMath.log(this.upper - x);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LowerUpperBoundMapper
/*     */     implements Mapper
/*     */   {
/*     */     private final UnivariateFunction boundingFunction;
/*     */ 
/*     */ 
/*     */     
/*     */     private final UnivariateFunction unboundingFunction;
/*     */ 
/*     */ 
/*     */     
/*     */     LowerUpperBoundMapper(double lower, double upper) {
/* 285 */       this.boundingFunction = (UnivariateFunction)new Sigmoid(lower, upper);
/* 286 */       this.unboundingFunction = (UnivariateFunction)new Logit(lower, upper);
/*     */     }
/*     */ 
/*     */     
/*     */     public double unboundedToBounded(double y) {
/* 291 */       return this.boundingFunction.value(y);
/*     */     }
/*     */ 
/*     */     
/*     */     public double boundedToUnbounded(double x) {
/* 296 */       return this.unboundingFunction.value(x);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/MultivariateFunctionMappingAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */