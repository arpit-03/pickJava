/*     */ package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiDirectionalSimplex
/*     */   extends AbstractSimplex
/*     */ {
/*     */   private static final double DEFAULT_KHI = 2.0D;
/*     */   private static final double DEFAULT_GAMMA = 0.5D;
/*     */   private final double khi;
/*     */   private final double gamma;
/*     */   
/*     */   public MultiDirectionalSimplex(int n) {
/*  46 */     this(n, 1.0D);
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
/*  58 */     this(n, sideLength, 2.0D, 0.5D);
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
/*  71 */     this(n, 1.0D, khi, gamma);
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
/*  86 */     super(n, sideLength);
/*     */     
/*  88 */     this.khi = khi;
/*  89 */     this.gamma = gamma;
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
/* 100 */     this(steps, 2.0D, 0.5D);
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
/* 114 */     super(steps);
/*     */     
/* 116 */     this.khi = khi;
/* 117 */     this.gamma = gamma;
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
/* 128 */     this(referenceSimplex, 2.0D, 0.5D);
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
/* 145 */     super(referenceSimplex);
/*     */     
/* 147 */     this.khi = khi;
/* 148 */     this.gamma = gamma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void iterate(MultivariateFunction evaluationFunction, Comparator<PointValuePair> comparator) {
/* 156 */     PointValuePair[] original = getPoints();
/* 157 */     PointValuePair best = original[0];
/*     */ 
/*     */     
/* 160 */     PointValuePair reflected = evaluateNewSimplex(evaluationFunction, original, 1.0D, comparator);
/*     */     
/* 162 */     if (comparator.compare(reflected, best) < 0) {
/*     */       
/* 164 */       PointValuePair[] reflectedSimplex = getPoints();
/* 165 */       PointValuePair expanded = evaluateNewSimplex(evaluationFunction, original, this.khi, comparator);
/*     */       
/* 167 */       if (comparator.compare(reflected, expanded) <= 0)
/*     */       {
/* 169 */         setPoints(reflectedSimplex);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 176 */     evaluateNewSimplex(evaluationFunction, original, this.gamma, comparator);
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
/* 196 */     double[] xSmallest = original[0].getPointRef();
/*     */ 
/*     */     
/* 199 */     setPoint(0, original[0]);
/* 200 */     int dim = getDimension();
/* 201 */     for (int i = 1; i < getSize(); i++) {
/* 202 */       double[] xOriginal = original[i].getPointRef();
/* 203 */       double[] xTransformed = new double[dim];
/* 204 */       for (int j = 0; j < dim; j++) {
/* 205 */         xTransformed[j] = xSmallest[j] + coeff * (xSmallest[j] - xOriginal[j]);
/*     */       }
/* 207 */       setPoint(i, new PointValuePair(xTransformed, Double.NaN, false));
/*     */     } 
/*     */ 
/*     */     
/* 211 */     evaluate(evaluationFunction, comparator);
/*     */     
/* 213 */     return getPoint(0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/noderiv/MultiDirectionalSimplex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */