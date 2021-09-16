/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AtomicDouble
/*     */   extends Number
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2863252398613925385L;
/*     */   private AtomicLong _al;
/*     */   
/*     */   public AtomicDouble() {
/*  34 */     this(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicDouble(double value) {
/*  42 */     this._al = new AtomicLong(l(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double get() {
/*  50 */     return d(this._al.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double value) {
/*  58 */     this._al.set(l(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getAndSet(double value) {
/*  67 */     return d(this._al.getAndSet(l(value)));
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
/*     */   public final boolean compareAndSet(double expect, double update) {
/*  79 */     return this._al.compareAndSet(l(expect), l(update));
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
/*     */   public final boolean weakCompareAndSet(double expect, double update) {
/*  94 */     return this._al.weakCompareAndSet(l(expect), l(update));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getAndIncrement() {
/* 102 */     return getAndAdd(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getAndDecrement() {
/* 110 */     return getAndAdd(-1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getAndAdd(double delta) {
/*     */     while (true) {
/* 120 */       long lexpect = this._al.get();
/* 121 */       double expect = d(lexpect);
/* 122 */       double update = expect + delta;
/* 123 */       long lupdate = l(update);
/* 124 */       if (this._al.compareAndSet(lexpect, lupdate)) {
/* 125 */         return expect;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double incrementAndGet() {
/* 134 */     return addAndGet(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double decrementAndGet() {
/* 142 */     return addAndGet(-1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double addAndGet(double delta) {
/*     */     while (true) {
/* 152 */       long lexpect = this._al.get();
/* 153 */       double expect = d(lexpect);
/* 154 */       double update = expect + delta;
/* 155 */       long lupdate = l(update);
/* 156 */       if (this._al.compareAndSet(lexpect, lupdate))
/* 157 */         return update; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 162 */     return Double.toString(get());
/*     */   }
/*     */   
/*     */   public int intValue() {
/* 166 */     return (int)get();
/*     */   }
/*     */   
/*     */   public long longValue() {
/* 170 */     return (long)get();
/*     */   }
/*     */   
/*     */   public float floatValue() {
/* 174 */     return (float)get();
/*     */   }
/*     */   
/*     */   public double doubleValue() {
/* 178 */     return get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long l(double d) {
/* 187 */     return Double.doubleToLongBits(d);
/*     */   }
/*     */   private static double d(long l) {
/* 190 */     return Double.longBitsToDouble(l);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/AtomicDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */