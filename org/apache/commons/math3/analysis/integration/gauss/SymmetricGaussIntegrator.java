/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
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
/*     */ public class SymmetricGaussIntegrator
/*     */   extends GaussIntegrator
/*     */ {
/*     */   public SymmetricGaussIntegrator(double[] points, double[] weights) throws NonMonotonicSequenceException, DimensionMismatchException {
/*  46 */     super(points, weights);
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
/*     */   public SymmetricGaussIntegrator(Pair<double[], double[]> pointsAndWeights) throws NonMonotonicSequenceException {
/*  61 */     this((double[])pointsAndWeights.getFirst(), (double[])pointsAndWeights.getSecond());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double integrate(UnivariateFunction f) {
/*  69 */     int ruleLength = getNumberOfPoints();
/*     */     
/*  71 */     if (ruleLength == 1) {
/*  72 */       return getWeight(0) * f.value(0.0D);
/*     */     }
/*     */     
/*  75 */     int iMax = ruleLength / 2;
/*  76 */     double s = 0.0D;
/*  77 */     double c = 0.0D;
/*  78 */     for (int i = 0; i < iMax; i++) {
/*  79 */       double p = getPoint(i);
/*  80 */       double w = getWeight(i);
/*     */       
/*  82 */       double f1 = f.value(p);
/*  83 */       double f2 = f.value(-p);
/*     */       
/*  85 */       double y = w * (f1 + f2) - c;
/*  86 */       double t = s + y;
/*     */       
/*  88 */       c = t - s - y;
/*  89 */       s = t;
/*     */     } 
/*     */     
/*  92 */     if (ruleLength % 2 != 0) {
/*  93 */       double w = getWeight(iMax);
/*     */       
/*  95 */       double y = w * f.value(0.0D) - c;
/*  96 */       double t = s + y;
/*     */       
/*  98 */       s = t;
/*     */     } 
/*     */     
/* 101 */     return s;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/SymmetricGaussIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */