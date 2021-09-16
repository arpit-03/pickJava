/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class Incrementor
/*     */ {
/*     */   private int maximalCount;
/*  42 */   private int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MaxCountExceededCallback maxCountCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Incrementor() {
/*  54 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Incrementor(int max) {
/*  63 */     this(max, new MaxCountExceededCallback()
/*     */         {
/*     */           public void trigger(int max) throws MaxCountExceededException
/*     */           {
/*  67 */             throw new MaxCountExceededException(Integer.valueOf(max));
/*     */           }
/*     */         });
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
/*     */   public Incrementor(int max, MaxCountExceededCallback cb) throws NullArgumentException {
/*  82 */     if (cb == null) {
/*  83 */       throw new NullArgumentException();
/*     */     }
/*  85 */     this.maximalCount = max;
/*  86 */     this.maxCountCallback = cb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximalCount(int max) {
/*  97 */     this.maximalCount = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximalCount() {
/* 106 */     return this.maximalCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 115 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canIncrement() {
/* 126 */     return (this.count < this.maximalCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCount(int value) throws MaxCountExceededException {
/* 137 */     for (int i = 0; i < value; i++) {
/* 138 */       incrementCount();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCount() throws MaxCountExceededException {
/* 156 */     if (++this.count > this.maximalCount) {
/* 157 */       this.maxCountCallback.trigger(this.maximalCount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCount() {
/* 165 */     this.count = 0;
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
/*     */   public static Incrementor wrap(final IntegerSequence.Incrementor incrementor) {
/* 202 */     return new Incrementor()
/*     */       {
/*     */         private IntegerSequence.Incrementor delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void setMaximalCount(int max) {
/* 217 */           super.setMaximalCount(max);
/* 218 */           this.delegate = this.delegate.withMaximalCount(max);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void resetCount() {
/* 224 */           super.resetCount();
/* 225 */           this.delegate = this.delegate.withStart(0);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void incrementCount() {
/* 231 */           super.incrementCount();
/* 232 */           this.delegate.increment();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface MaxCountExceededCallback {
/*     */     void trigger(int param1Int) throws MaxCountExceededException;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/Incrementor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */