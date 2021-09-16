/*    */ package org.apache.commons.math3.optim.univariate;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*    */ import org.apache.commons.math3.exception.OutOfRangeException;
/*    */ import org.apache.commons.math3.optim.OptimizationData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchInterval
/*    */   implements OptimizationData
/*    */ {
/*    */   private final double lower;
/*    */   private final double upper;
/*    */   private final double start;
/*    */   
/*    */   public SearchInterval(double lo, double hi, double init) {
/* 48 */     if (lo >= hi) {
/* 49 */       throw new NumberIsTooLargeException(Double.valueOf(lo), Double.valueOf(hi), false);
/*    */     }
/* 51 */     if (init < lo || init > hi)
/*    */     {
/* 53 */       throw new OutOfRangeException(Double.valueOf(init), Double.valueOf(lo), Double.valueOf(hi));
/*    */     }
/*    */     
/* 56 */     this.lower = lo;
/* 57 */     this.upper = hi;
/* 58 */     this.start = init;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SearchInterval(double lo, double hi) {
/* 68 */     this(lo, hi, 0.5D * (lo + hi));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMin() {
/* 77 */     return this.lower;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMax() {
/* 85 */     return this.upper;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getStartValue() {
/* 93 */     return this.start;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/SearchInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */