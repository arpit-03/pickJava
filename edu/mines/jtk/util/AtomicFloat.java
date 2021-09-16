/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AtomicFloat
/*     */   extends Number
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6792837592345465936L;
/*     */   private AtomicInteger _ai;
/*     */   
/*     */   public AtomicFloat() {
/*  34 */     this(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicFloat(float value) {
/*  42 */     this._ai = new AtomicInteger(i(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float get() {
/*  50 */     return f(this._ai.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float value) {
/*  58 */     this._ai.set(i(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getAndSet(float value) {
/*  67 */     return f(this._ai.getAndSet(i(value)));
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
/*     */   public final boolean compareAndSet(float expect, float update) {
/*  79 */     return this._ai.compareAndSet(i(expect), i(update));
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
/*     */   public final boolean weakCompareAndSet(float expect, float update) {
/*  94 */     return this._ai.weakCompareAndSet(i(expect), i(update));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getAndIncrement() {
/* 102 */     return getAndAdd(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getAndDecrement() {
/* 110 */     return getAndAdd(-1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getAndAdd(float delta) {
/*     */     while (true) {
/* 120 */       int iexpect = this._ai.get();
/* 121 */       float expect = f(iexpect);
/* 122 */       float update = expect + delta;
/* 123 */       int iupdate = i(update);
/* 124 */       if (this._ai.compareAndSet(iexpect, iupdate)) {
/* 125 */         return expect;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float incrementAndGet() {
/* 134 */     return addAndGet(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float decrementAndGet() {
/* 142 */     return addAndGet(-1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float addAndGet(float delta) {
/*     */     while (true) {
/* 152 */       int iexpect = this._ai.get();
/* 153 */       float expect = f(iexpect);
/* 154 */       float update = expect + delta;
/* 155 */       int iupdate = i(update);
/* 156 */       if (this._ai.compareAndSet(iexpect, iupdate))
/* 157 */         return update; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 162 */     return Float.toString(get());
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
/* 174 */     return get();
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
/*     */   private static int i(float f) {
/* 187 */     return Float.floatToIntBits(f);
/*     */   }
/*     */   private static float f(int i) {
/* 190 */     return Float.intBitsToFloat(i);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/AtomicFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */