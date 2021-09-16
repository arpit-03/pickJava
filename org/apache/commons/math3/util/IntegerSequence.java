/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntegerSequence
/*     */ {
/*     */   public static Range range(int start, int end) {
/*  47 */     return range(start, end, 1);
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
/*     */   public static Range range(int start, int max, int step) {
/*  64 */     return new Range(start, max, step);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Range
/*     */     implements Iterable<Integer>
/*     */   {
/*     */     private final int size;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int start;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int max;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int step;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Range(int start, int max, int step) {
/*  93 */       this.start = start;
/*  94 */       this.max = max;
/*  95 */       this.step = step;
/*     */       
/*  97 */       int s = (max - start) / step + 1;
/*  98 */       this.size = (s < 0) ? 0 : s;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 107 */       return this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Integer> iterator() {
/* 112 */       return IntegerSequence.Incrementor.create().withStart(this.start).withMaximalCount(this.max + ((this.step > 0) ? 1 : -1)).withIncrement(this.step);
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
/*     */   public static class Incrementor
/*     */     implements Iterator<Integer>
/*     */   {
/* 129 */     private static final MaxCountExceededCallback CALLBACK = new MaxCountExceededCallback()
/*     */       {
/*     */         public void trigger(int max) throws MaxCountExceededException
/*     */         {
/* 133 */           throw new MaxCountExceededException(Integer.valueOf(max));
/*     */         }
/*     */       };
/*     */     
/*     */     private final int init;
/*     */     private final int maximalCount;
/*     */     private final int increment;
/*     */     private final MaxCountExceededCallback maxCountCallback;
/*     */     
/*     */     public static interface MaxCountExceededCallback {
/*     */       void trigger(int param2Int) throws MaxCountExceededException;
/*     */     }
/*     */     
/* 146 */     private int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Incrementor(int start, int max, int step, MaxCountExceededCallback cb) throws NullArgumentException {
/* 178 */       if (cb == null) {
/* 179 */         throw new NullArgumentException();
/*     */       }
/* 181 */       this.init = start;
/* 182 */       this.maximalCount = max;
/* 183 */       this.increment = step;
/* 184 */       this.maxCountCallback = cb;
/* 185 */       this.count = start;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Incrementor create() {
/* 197 */       return new Incrementor(0, 0, 1, CALLBACK);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Incrementor withStart(int start) {
/* 208 */       return new Incrementor(start, this.maximalCount, this.increment, this.maxCountCallback);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Incrementor withMaximalCount(int max) {
/* 222 */       return new Incrementor(this.init, max, this.increment, this.maxCountCallback);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Incrementor withIncrement(int step) {
/* 236 */       if (step == 0) {
/* 237 */         throw new ZeroException();
/*     */       }
/* 239 */       return new Incrementor(this.init, this.maximalCount, step, this.maxCountCallback);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Incrementor withCallback(MaxCountExceededCallback cb) {
/* 253 */       return new Incrementor(this.init, this.maximalCount, this.increment, cb);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaximalCount() {
/* 265 */       return this.maximalCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 274 */       return this.count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canIncrement() {
/* 285 */       return canIncrement(1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canIncrement(int nTimes) {
/* 297 */       int finalCount = this.count + nTimes * this.increment;
/* 298 */       return (this.increment < 0) ? ((finalCount > this.maximalCount)) : ((finalCount < this.maximalCount));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void increment(int nTimes) throws MaxCountExceededException {
/* 313 */       if (nTimes <= 0) {
/* 314 */         throw new NotStrictlyPositiveException(Integer.valueOf(nTimes));
/*     */       }
/*     */       
/* 317 */       if (!canIncrement(0)) {
/* 318 */         this.maxCountCallback.trigger(this.maximalCount);
/*     */       }
/* 320 */       this.count += nTimes * this.increment;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void increment() throws MaxCountExceededException {
/* 338 */       increment(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 343 */       return canIncrement(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer next() {
/* 348 */       int value = this.count;
/* 349 */       increment();
/* 350 */       return Integer.valueOf(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 359 */       throw new MathUnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/IntegerSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */