/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class KthSelector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20140713L;
/*     */   private static final int MIN_SELECT_SIZE = 15;
/*     */   private final PivotingStrategyInterface pivotingStrategy;
/*     */   
/*     */   public KthSelector() {
/*  46 */     this.pivotingStrategy = new MedianOf3PivotingStrategy();
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
/*     */   public KthSelector(PivotingStrategyInterface pivotingStrategy) throws NullArgumentException {
/*  60 */     MathUtils.checkNotNull(pivotingStrategy);
/*  61 */     this.pivotingStrategy = pivotingStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PivotingStrategyInterface getPivotingStrategy() {
/*  68 */     return this.pivotingStrategy;
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
/*     */   public double select(double[] work, int[] pivotsHeap, int k) {
/*  80 */     int begin = 0;
/*  81 */     int end = work.length;
/*  82 */     int node = 0;
/*  83 */     boolean usePivotsHeap = (pivotsHeap != null);
/*  84 */     while (end - begin > 15) {
/*     */       int pivot;
/*     */       
/*  87 */       if (usePivotsHeap && node < pivotsHeap.length && pivotsHeap[node] >= 0) {
/*     */ 
/*     */ 
/*     */         
/*  91 */         pivot = pivotsHeap[node];
/*     */       } else {
/*     */         
/*  94 */         pivot = partition(work, begin, end, this.pivotingStrategy.pivotIndex(work, begin, end));
/*  95 */         if (usePivotsHeap && node < pivotsHeap.length) {
/*  96 */           pivotsHeap[node] = pivot;
/*     */         }
/*     */       } 
/*     */       
/* 100 */       if (k == pivot)
/*     */       {
/* 102 */         return work[k]; } 
/* 103 */       if (k < pivot) {
/*     */         
/* 105 */         end = pivot;
/* 106 */         node = FastMath.min(2 * node + 1, usePivotsHeap ? pivotsHeap.length : end);
/*     */         continue;
/*     */       } 
/* 109 */       begin = pivot + 1;
/* 110 */       node = FastMath.min(2 * node + 2, usePivotsHeap ? pivotsHeap.length : end);
/*     */     } 
/*     */     
/* 113 */     Arrays.sort(work, begin, end);
/* 114 */     return work[k];
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
/*     */   private int partition(double[] work, int begin, int end, int pivot) {
/* 130 */     double value = work[pivot];
/* 131 */     work[pivot] = work[begin];
/*     */     
/* 133 */     int i = begin + 1;
/* 134 */     int j = end - 1;
/* 135 */     while (i < j) {
/* 136 */       while (i < j && work[j] > value) {
/* 137 */         j--;
/*     */       }
/* 139 */       while (i < j && work[i] < value) {
/* 140 */         i++;
/*     */       }
/*     */       
/* 143 */       if (i < j) {
/* 144 */         double tmp = work[i];
/* 145 */         work[i++] = work[j];
/* 146 */         work[j--] = tmp;
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     if (i >= end || work[i] > value) {
/* 151 */       i--;
/*     */     }
/* 153 */     work[begin] = work[i];
/* 154 */     work[i] = value;
/* 155 */     return i;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/KthSelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */