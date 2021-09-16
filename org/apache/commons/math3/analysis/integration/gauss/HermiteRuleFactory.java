/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HermiteRuleFactory
/*     */   extends BaseRuleFactory<Double>
/*     */ {
/*     */   private static final double SQRT_PI = 1.772453850905516D;
/*     */   private static final double H0 = 0.7511255444649425D;
/*     */   private static final double H1 = 1.0622519320271968D;
/*     */   
/*     */   protected Pair<Double[], Double[]> computeRule(int numberOfPoints) throws DimensionMismatchException {
/*  64 */     if (numberOfPoints == 1)
/*     */     {
/*  66 */       return new Pair(new Double[] { Double.valueOf(0.0D) }, new Double[] { Double.valueOf(1.772453850905516D) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     int lastNumPoints = numberOfPoints - 1;
/*  74 */     Double[] previousPoints = (Double[])getRuleInternal(lastNumPoints).getFirst();
/*     */ 
/*     */     
/*  77 */     Double[] points = new Double[numberOfPoints];
/*  78 */     Double[] weights = new Double[numberOfPoints];
/*     */     
/*  80 */     double sqrtTwoTimesLastNumPoints = FastMath.sqrt((2 * lastNumPoints));
/*  81 */     double sqrtTwoTimesNumPoints = FastMath.sqrt((2 * numberOfPoints));
/*     */ 
/*     */     
/*  84 */     int iMax = numberOfPoints / 2;
/*  85 */     for (int i = 0; i < iMax; i++) {
/*     */       
/*  87 */       double a = (i == 0) ? -sqrtTwoTimesLastNumPoints : previousPoints[i - 1].doubleValue();
/*     */       
/*  89 */       double b = (iMax == 1) ? -0.5D : previousPoints[i].doubleValue();
/*     */ 
/*     */       
/*  92 */       double hma = 0.7511255444649425D;
/*     */       
/*  94 */       double ha = 1.0622519320271968D * a;
/*     */       
/*  96 */       double hmb = 0.7511255444649425D;
/*     */       
/*  98 */       double hb = 1.0622519320271968D * b;
/*  99 */       for (int j = 1; j < numberOfPoints; j++) {
/*     */         
/* 101 */         double jp1 = (j + 1);
/* 102 */         double s = FastMath.sqrt(2.0D / jp1);
/* 103 */         double sm = FastMath.sqrt(j / jp1);
/* 104 */         double hpa = s * a * ha - sm * hma;
/* 105 */         double hpb = s * b * hb - sm * hmb;
/* 106 */         hma = ha;
/* 107 */         ha = hpa;
/* 108 */         hmb = hb;
/* 109 */         hb = hpb;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 114 */       double c = 0.5D * (a + b);
/*     */       
/* 116 */       double hmc = 0.7511255444649425D;
/*     */       
/* 118 */       double hc = 1.0622519320271968D * c;
/* 119 */       boolean done = false;
/* 120 */       while (!done) {
/* 121 */         done = (b - a <= Math.ulp(c));
/* 122 */         hmc = 0.7511255444649425D;
/* 123 */         hc = 1.0622519320271968D * c;
/* 124 */         for (int k = 1; k < numberOfPoints; k++) {
/*     */           
/* 126 */           double jp1 = (k + 1);
/* 127 */           double s = FastMath.sqrt(2.0D / jp1);
/* 128 */           double sm = FastMath.sqrt(k / jp1);
/* 129 */           double hpc = s * c * hc - sm * hmc;
/* 130 */           hmc = hc;
/* 131 */           hc = hpc;
/*     */         } 
/*     */         
/* 134 */         if (!done) {
/* 135 */           if (ha * hc < 0.0D) {
/* 136 */             b = c;
/* 137 */             hmb = hmc;
/* 138 */             hb = hc;
/*     */           } else {
/* 140 */             a = c;
/* 141 */             hma = hmc;
/* 142 */             ha = hc;
/*     */           } 
/* 144 */           c = 0.5D * (a + b);
/*     */         } 
/*     */       } 
/* 147 */       double d = sqrtTwoTimesNumPoints * hmc;
/* 148 */       double w = 2.0D / d * d;
/*     */       
/* 150 */       points[i] = Double.valueOf(c);
/* 151 */       weights[i] = Double.valueOf(w);
/*     */       
/* 153 */       int idx = lastNumPoints - i;
/* 154 */       points[idx] = Double.valueOf(-c);
/* 155 */       weights[idx] = Double.valueOf(w);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (numberOfPoints % 2 != 0) {
/* 163 */       double hm = 0.7511255444649425D;
/* 164 */       for (int j = 1; j < numberOfPoints; j += 2) {
/* 165 */         double jp1 = (j + 1);
/* 166 */         hm = -FastMath.sqrt(j / jp1) * hm;
/*     */       } 
/* 168 */       double d = sqrtTwoTimesNumPoints * hm;
/* 169 */       double w = 2.0D / d * d;
/*     */       
/* 171 */       points[iMax] = Double.valueOf(0.0D);
/* 172 */       weights[iMax] = Double.valueOf(w);
/*     */     } 
/*     */     
/* 175 */     return new Pair(points, weights);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/HermiteRuleFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */