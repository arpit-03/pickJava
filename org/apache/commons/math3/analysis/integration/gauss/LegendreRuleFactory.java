/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.util.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegendreRuleFactory
/*     */   extends BaseRuleFactory<Double>
/*     */ {
/*     */   protected Pair<Double[], Double[]> computeRule(int numberOfPoints) throws DimensionMismatchException {
/*  38 */     if (numberOfPoints == 1)
/*     */     {
/*  40 */       return new Pair(new Double[] { Double.valueOf(0.0D) }, new Double[] { Double.valueOf(2.0D) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     Double[] previousPoints = (Double[])getRuleInternal(numberOfPoints - 1).getFirst();
/*     */ 
/*     */     
/*  50 */     Double[] points = new Double[numberOfPoints];
/*  51 */     Double[] weights = new Double[numberOfPoints];
/*     */ 
/*     */     
/*  54 */     int iMax = numberOfPoints / 2;
/*  55 */     for (int i = 0; i < iMax; i++) {
/*     */       
/*  57 */       double a = (i == 0) ? -1.0D : previousPoints[i - 1].doubleValue();
/*     */       
/*  59 */       double b = (iMax == 1) ? 1.0D : previousPoints[i].doubleValue();
/*     */       
/*  61 */       double pma = 1.0D;
/*     */       
/*  63 */       double pa = a;
/*     */       
/*  65 */       double pmb = 1.0D;
/*     */       
/*  67 */       double pb = b;
/*  68 */       for (int j = 1; j < numberOfPoints; j++) {
/*  69 */         int two_j_p_1 = 2 * j + 1;
/*  70 */         int j_p_1 = j + 1;
/*     */         
/*  72 */         double ppa = (two_j_p_1 * a * pa - j * pma) / j_p_1;
/*     */         
/*  74 */         double ppb = (two_j_p_1 * b * pb - j * pmb) / j_p_1;
/*  75 */         pma = pa;
/*  76 */         pa = ppa;
/*  77 */         pmb = pb;
/*  78 */         pb = ppb;
/*     */       } 
/*     */ 
/*     */       
/*  82 */       double c = 0.5D * (a + b);
/*     */       
/*  84 */       double pmc = 1.0D;
/*     */       
/*  86 */       double pc = c;
/*  87 */       boolean done = false;
/*  88 */       while (!done) {
/*  89 */         done = (b - a <= Math.ulp(c));
/*  90 */         pmc = 1.0D;
/*  91 */         pc = c;
/*  92 */         for (int k = 1; k < numberOfPoints; k++) {
/*     */           
/*  94 */           double ppc = ((2 * k + 1) * c * pc - k * pmc) / (k + 1);
/*  95 */           pmc = pc;
/*  96 */           pc = ppc;
/*     */         } 
/*     */         
/*  99 */         if (!done) {
/* 100 */           if (pa * pc <= 0.0D) {
/* 101 */             b = c;
/* 102 */             pmb = pmc;
/* 103 */             pb = pc;
/*     */           } else {
/* 105 */             a = c;
/* 106 */             pma = pmc;
/* 107 */             pa = pc;
/*     */           } 
/* 109 */           c = 0.5D * (a + b);
/*     */         } 
/*     */       } 
/* 112 */       double d = numberOfPoints * (pmc - c * pc);
/* 113 */       double w = 2.0D * (1.0D - c * c) / d * d;
/*     */       
/* 115 */       points[i] = Double.valueOf(c);
/* 116 */       weights[i] = Double.valueOf(w);
/*     */       
/* 118 */       int idx = numberOfPoints - i - 1;
/* 119 */       points[idx] = Double.valueOf(-c);
/* 120 */       weights[idx] = Double.valueOf(w);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (numberOfPoints % 2 != 0) {
/* 127 */       double pmc = 1.0D;
/* 128 */       for (int j = 1; j < numberOfPoints; j += 2) {
/* 129 */         pmc = -j * pmc / (j + 1);
/*     */       }
/* 131 */       double d = numberOfPoints * pmc;
/* 132 */       double w = 2.0D / d * d;
/*     */       
/* 134 */       points[iMax] = Double.valueOf(0.0D);
/* 135 */       weights[iMax] = Double.valueOf(w);
/*     */     } 
/*     */     
/* 138 */     return new Pair(points, weights);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/LegendreRuleFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */