/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Combinations
/*     */   implements Iterable<int[]>
/*     */ {
/*     */   private final int n;
/*     */   private final int k;
/*     */   private final IterationOrder iterationOrder;
/*     */   
/*     */   private enum IterationOrder
/*     */   {
/*  49 */     LEXICOGRAPHIC;
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
/*     */   public Combinations(int n, int k) {
/*  76 */     this(n, k, IterationOrder.LEXICOGRAPHIC);
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
/*     */   private Combinations(int n, int k, IterationOrder iterationOrder) {
/* 106 */     CombinatoricsUtils.checkBinomial(n, k);
/* 107 */     this.n = n;
/* 108 */     this.k = k;
/* 109 */     this.iterationOrder = iterationOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN() {
/* 118 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getK() {
/* 127 */     return this.k;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<int[]> iterator() {
/* 132 */     if (this.k == 0 || this.k == this.n)
/*     */     {
/* 134 */       return new SingletonIterator(MathArrays.natural(this.k));
/*     */     }
/*     */     
/* 137 */     switch (this.iterationOrder) {
/*     */       case LEXICOGRAPHIC:
/* 139 */         return new LexicographicIterator(this.n, this.k);
/*     */     } 
/* 141 */     throw new MathInternalError();
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
/*     */   public Comparator<int[]> comparator() {
/* 160 */     return new LexicographicComparator(this.n, this.k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LexicographicIterator
/*     */     implements Iterator<int[]>
/*     */   {
/*     */     private final int k;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean more = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LexicographicIterator(int n, int k) {
/* 207 */       this.k = k;
/* 208 */       this.c = new int[k + 3];
/* 209 */       if (k == 0 || k >= n) {
/* 210 */         this.more = false;
/*     */         
/*     */         return;
/*     */       } 
/* 214 */       for (int i = 1; i <= k; i++) {
/* 215 */         this.c[i] = i - 1;
/*     */       }
/*     */       
/* 218 */       this.c[k + 1] = n;
/* 219 */       this.c[k + 2] = 0;
/* 220 */       this.j = k;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 227 */       return this.more;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] next() {
/* 234 */       if (!this.more) {
/* 235 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 238 */       int[] ret = new int[this.k];
/* 239 */       System.arraycopy(this.c, 1, ret, 0, this.k);
/*     */ 
/*     */ 
/*     */       
/* 243 */       int x = 0;
/* 244 */       if (this.j > 0) {
/* 245 */         x = this.j;
/* 246 */         this.c[this.j] = x;
/* 247 */         this.j--;
/* 248 */         return ret;
/*     */       } 
/*     */       
/* 251 */       if (this.c[1] + 1 < this.c[2]) {
/* 252 */         this.c[1] = this.c[1] + 1;
/* 253 */         return ret;
/*     */       } 
/* 255 */       this.j = 2;
/*     */ 
/*     */       
/* 258 */       boolean stepDone = false;
/* 259 */       while (!stepDone) {
/* 260 */         this.c[this.j - 1] = this.j - 2;
/* 261 */         x = this.c[this.j] + 1;
/* 262 */         if (x == this.c[this.j + 1]) {
/* 263 */           this.j++; continue;
/*     */         } 
/* 265 */         stepDone = true;
/*     */       } 
/*     */ 
/*     */       
/* 269 */       if (this.j > this.k) {
/* 270 */         this.more = false;
/* 271 */         return ret;
/*     */       } 
/*     */       
/* 274 */       this.c[this.j] = x;
/* 275 */       this.j--;
/* 276 */       return ret;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 283 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SingletonIterator
/*     */     implements Iterator<int[]>
/*     */   {
/*     */     private final int[] singleton;
/*     */ 
/*     */     
/*     */     private boolean more = true;
/*     */ 
/*     */ 
/*     */     
/*     */     SingletonIterator(int[] singleton) {
/* 301 */       this.singleton = singleton;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 305 */       return this.more;
/*     */     }
/*     */     
/*     */     public int[] next() {
/* 309 */       if (this.more) {
/* 310 */         this.more = false;
/* 311 */         return this.singleton;
/*     */       } 
/* 313 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 318 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LexicographicComparator
/*     */     implements Comparator<int[]>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130906L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int n;
/*     */ 
/*     */     
/*     */     private final int k;
/*     */ 
/*     */ 
/*     */     
/*     */     LexicographicComparator(int n, int k) {
/* 340 */       this.n = n;
/* 341 */       this.k = k;
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
/*     */     public int compare(int[] c1, int[] c2) {
/* 354 */       if (c1.length != this.k) {
/* 355 */         throw new DimensionMismatchException(c1.length, this.k);
/*     */       }
/* 357 */       if (c2.length != this.k) {
/* 358 */         throw new DimensionMismatchException(c2.length, this.k);
/*     */       }
/*     */ 
/*     */       
/* 362 */       int[] c1s = MathArrays.copyOf(c1);
/* 363 */       Arrays.sort(c1s);
/* 364 */       int[] c2s = MathArrays.copyOf(c2);
/* 365 */       Arrays.sort(c2s);
/*     */       
/* 367 */       long v1 = lexNorm(c1s);
/* 368 */       long v2 = lexNorm(c2s);
/*     */       
/* 370 */       if (v1 < v2)
/* 371 */         return -1; 
/* 372 */       if (v1 > v2) {
/* 373 */         return 1;
/*     */       }
/* 375 */       return 0;
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
/*     */     private long lexNorm(int[] c) {
/* 392 */       long ret = 0L;
/* 393 */       for (int i = 0; i < c.length; i++) {
/* 394 */         int digit = c[i];
/* 395 */         if (digit < 0 || digit >= this.n)
/*     */         {
/* 397 */           throw new OutOfRangeException(Integer.valueOf(digit), Integer.valueOf(0), Integer.valueOf(this.n - 1));
/*     */         }
/*     */         
/* 400 */         ret += (c[i] * ArithmeticUtils.pow(this.n, i));
/*     */       } 
/* 402 */       return ret;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/Combinations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */