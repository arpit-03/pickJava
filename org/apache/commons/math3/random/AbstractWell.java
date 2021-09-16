/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractWell
/*     */   extends BitsStreamGenerator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -817701723016583596L;
/*     */   protected int index;
/*     */   protected final int[] v;
/*     */   protected final int[] iRm1;
/*     */   protected final int[] iRm2;
/*     */   protected final int[] i1;
/*     */   protected final int[] i2;
/*     */   protected final int[] i3;
/*     */   
/*     */   protected AbstractWell(int k, int m1, int m2, int m3) {
/*  73 */     this(k, m1, m2, m3, (int[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractWell(int k, int m1, int m2, int m3, int seed) {
/*  84 */     this(k, m1, m2, m3, new int[] { seed });
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
/*     */   protected AbstractWell(int k, int m1, int m2, int m3, int[] seed) {
/* 100 */     int w = 32;
/* 101 */     int r = (k + 32 - 1) / 32;
/* 102 */     this.v = new int[r];
/* 103 */     this.index = 0;
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.iRm1 = new int[r];
/* 108 */     this.iRm2 = new int[r];
/* 109 */     this.i1 = new int[r];
/* 110 */     this.i2 = new int[r];
/* 111 */     this.i3 = new int[r];
/* 112 */     for (int j = 0; j < r; j++) {
/* 113 */       this.iRm1[j] = (j + r - 1) % r;
/* 114 */       this.iRm2[j] = (j + r - 2) % r;
/* 115 */       this.i1[j] = (j + m1) % r;
/* 116 */       this.i2[j] = (j + m2) % r;
/* 117 */       this.i3[j] = (j + m3) % r;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     setSeed(seed);
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
/*     */   protected AbstractWell(int k, int m1, int m2, int m3, long seed) {
/* 133 */     this(k, m1, m2, m3, new int[] { (int)(seed >>> 32L), (int)(seed & 0xFFFFFFFFL) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/* 143 */     setSeed(new int[] { seed });
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
/*     */   public void setSeed(int[] seed) {
/* 155 */     if (seed == null) {
/* 156 */       setSeed(System.currentTimeMillis() + System.identityHashCode(this));
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     System.arraycopy(seed, 0, this.v, 0, FastMath.min(seed.length, this.v.length));
/*     */     
/* 162 */     if (seed.length < this.v.length) {
/* 163 */       for (int i = seed.length; i < this.v.length; i++) {
/* 164 */         long l = this.v[i - seed.length];
/* 165 */         this.v[i] = (int)(1812433253L * (l ^ l >> 30L) + i & 0xFFFFFFFFL);
/*     */       } 
/*     */     }
/*     */     
/* 169 */     this.index = 0;
/* 170 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 180 */     setSeed(new int[] { (int)(seed >>> 32L), (int)(seed & 0xFFFFFFFFL) });
/*     */   }
/*     */   
/*     */   protected abstract int next(int paramInt);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/AbstractWell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */