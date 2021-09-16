/*    */ package org.apache.commons.math3.genetics;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*    */ public class FixedElapsedTime
/*    */   implements StoppingCondition
/*    */ {
/*    */   private final long maxTimePeriod;
/* 37 */   private long endTime = -1L;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FixedElapsedTime(long maxTime) throws NumberIsTooSmallException {
/* 46 */     this(maxTime, TimeUnit.SECONDS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FixedElapsedTime(long maxTime, TimeUnit unit) throws NumberIsTooSmallException {
/* 57 */     if (maxTime < 0L) {
/* 58 */       throw new NumberIsTooSmallException(Long.valueOf(maxTime), Integer.valueOf(0), true);
/*    */     }
/* 60 */     this.maxTimePeriod = unit.toNanos(maxTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSatisfied(Population population) {
/* 71 */     if (this.endTime < 0L) {
/* 72 */       this.endTime = System.nanoTime() + this.maxTimePeriod;
/*    */     }
/*    */     
/* 75 */     return (System.nanoTime() >= this.endTime);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/FixedElapsedTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */