/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ public class GaussIntegrator
/*     */ {
/*     */   private final double[] points;
/*     */   private final double[] weights;
/*     */   
/*     */   public GaussIntegrator(double[] points, double[] weights) throws NonMonotonicSequenceException, DimensionMismatchException {
/*  52 */     if (points.length != weights.length) {
/*  53 */       throw new DimensionMismatchException(points.length, weights.length);
/*     */     }
/*     */ 
/*     */     
/*  57 */     MathArrays.checkOrder(points, MathArrays.OrderDirection.INCREASING, true, true);
/*     */     
/*  59 */     this.points = (double[])points.clone();
/*  60 */     this.weights = (double[])weights.clone();
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
/*     */   public GaussIntegrator(Pair<double[], double[]> pointsAndWeights) throws NonMonotonicSequenceException {
/*  75 */     this((double[])pointsAndWeights.getFirst(), (double[])pointsAndWeights.getSecond());
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
/*     */   public double integrate(UnivariateFunction f) {
/*  89 */     double s = 0.0D;
/*  90 */     double c = 0.0D;
/*  91 */     for (int i = 0; i < this.points.length; i++) {
/*  92 */       double x = this.points[i];
/*  93 */       double w = this.weights[i];
/*  94 */       double y = w * f.value(x) - c;
/*  95 */       double t = s + y;
/*  96 */       c = t - s - y;
/*  97 */       s = t;
/*     */     } 
/*  99 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfPoints() {
/* 107 */     return this.points.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPoint(int index) {
/* 117 */     return this.points[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWeight(int index) {
/* 127 */     return this.weights[index];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/GaussIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */