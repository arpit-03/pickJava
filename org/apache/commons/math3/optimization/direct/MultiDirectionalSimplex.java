/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class MultiDirectionalSimplex
/*     */   extends AbstractSimplex
/*     */ {
/*     */   private static final double DEFAULT_KHI = 2.0D;
/*     */   private static final double DEFAULT_GAMMA = 0.5D;
/*     */   private final double khi;
/*     */   private final double gamma;
/*     */   
/*     */   public MultiDirectionalSimplex(int n) {
/*  49 */     this(n, 1.0D);
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
/*     */   public MultiDirectionalSimplex(int n, double sideLength) {
/*  61 */     this(n, sideLength, 2.0D, 0.5D);
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
/*     */   public MultiDirectionalSimplex(int n, double khi, double gamma) {
/*  74 */     this(n, 1.0D, khi, gamma);
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
/*     */   public MultiDirectionalSimplex(int n, double sideLength, double khi, double gamma) {
/*  89 */     super(n, sideLength);
/*     */     
/*  91 */     this.khi = khi;
/*  92 */     this.gamma = gamma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiDirectionalSimplex(double[] steps) {
/* 103 */     this(steps, 2.0D, 0.5D);
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
/*     */   public MultiDirectionalSimplex(double[] steps, double khi, double gamma) {
/* 117 */     super(steps);
/*     */     
/* 119 */     this.khi = khi;
/* 120 */     this.gamma = gamma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiDirectionalSimplex(double[][] referenceSimplex) {
/* 131 */     this(referenceSimplex, 2.0D, 0.5D);
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
/*     */   public MultiDirectionalSimplex(double[][] referenceSimplex, double khi, double gamma) {
/* 148 */     super(referenceSimplex);
/*     */     
/* 150 */     this.khi = khi;
/* 151 */     this.gamma = gamma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void iterate(MultivariateFunction evaluationFunction, Comparator<PointValuePair> comparator) {
/* 159 */     PointValuePair[] original = getPoints();
/* 160 */     PointValuePair best = original[0];
/*     */ 
/*     */     
/* 163 */     PointValuePair reflected = evaluateNewSimplex(evaluationFunction, original, 1.0D, comparator);
/*     */     
/* 165 */     if (comparator.compare(reflected, best) < 0) {
/*     */       
/* 167 */       PointValuePair[] reflectedSimplex = getPoints();
/* 168 */       PointValuePair expanded = evaluateNewSimplex(evaluationFunction, original, this.khi, comparator);
/*     */       
/* 170 */       if (comparator.compare(reflected, expanded) <= 0)
/*     */       {
/* 172 */         setPoints(reflectedSimplex);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 179 */     evaluateNewSimplex(evaluationFunction, original, this.gamma, comparator);
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
/*     */   private PointValuePair evaluateNewSimplex(MultivariateFunction evaluationFunction, PointValuePair[] original, double coeff, Comparator<PointValuePair> comparator) {
/* 199 */     double[] xSmallest = original[0].getPointRef();
/*     */ 
/*     */     
/* 202 */     setPoint(0, original[0]);
/* 203 */     int dim = getDimension();
/* 204 */     for (int i = 1; i < getSize(); i++) {
/* 205 */       double[] xOriginal = original[i].getPointRef();
/* 206 */       double[] xTransformed = new double[dim];
/* 207 */       for (int j = 0; j < dim; j++) {
/* 208 */         xTransformed[j] = xSmallest[j] + coeff * (xSmallest[j] - xOriginal[j]);
/*     */       }
/* 210 */       setPoint(i, new PointValuePair(xTransformed, Double.NaN, false));
/*     */     } 
/*     */ 
/*     */     
/* 214 */     evaluate(evaluationFunction, comparator);
/*     */     
/* 216 */     return getPoint(0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/MultiDirectionalSimplex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */