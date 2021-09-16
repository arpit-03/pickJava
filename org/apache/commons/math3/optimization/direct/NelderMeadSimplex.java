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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class NelderMeadSimplex
/*     */   extends AbstractSimplex
/*     */ {
/*     */   private static final double DEFAULT_RHO = 1.0D;
/*     */   private static final double DEFAULT_KHI = 2.0D;
/*     */   private static final double DEFAULT_GAMMA = 0.5D;
/*     */   private static final double DEFAULT_SIGMA = 0.5D;
/*     */   private final double rho;
/*     */   private final double khi;
/*     */   private final double gamma;
/*     */   private final double sigma;
/*     */   
/*     */   public NelderMeadSimplex(int n) {
/*  58 */     this(n, 1.0D);
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
/*     */   public NelderMeadSimplex(int n, double sideLength) {
/*  71 */     this(n, sideLength, 1.0D, 2.0D, 0.5D, 0.5D);
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
/*     */   public NelderMeadSimplex(int n, double sideLength, double rho, double khi, double gamma, double sigma) {
/*  90 */     super(n, sideLength);
/*     */     
/*  92 */     this.rho = rho;
/*  93 */     this.khi = khi;
/*  94 */     this.gamma = gamma;
/*  95 */     this.sigma = sigma;
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
/*     */   public NelderMeadSimplex(int n, double rho, double khi, double gamma, double sigma) {
/* 111 */     this(n, 1.0D, rho, khi, gamma, sigma);
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
/*     */   public NelderMeadSimplex(double[] steps) {
/* 123 */     this(steps, 1.0D, 2.0D, 0.5D, 0.5D);
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
/*     */   public NelderMeadSimplex(double[] steps, double rho, double khi, double gamma, double sigma) {
/* 141 */     super(steps);
/*     */     
/* 143 */     this.rho = rho;
/* 144 */     this.khi = khi;
/* 145 */     this.gamma = gamma;
/* 146 */     this.sigma = sigma;
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
/*     */   public NelderMeadSimplex(double[][] referenceSimplex) {
/* 158 */     this(referenceSimplex, 1.0D, 2.0D, 0.5D, 0.5D);
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
/*     */   public NelderMeadSimplex(double[][] referenceSimplex, double rho, double khi, double gamma, double sigma) {
/* 178 */     super(referenceSimplex);
/*     */     
/* 180 */     this.rho = rho;
/* 181 */     this.khi = khi;
/* 182 */     this.gamma = gamma;
/* 183 */     this.sigma = sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void iterate(MultivariateFunction evaluationFunction, Comparator<PointValuePair> comparator) {
/* 191 */     int n = getDimension();
/*     */ 
/*     */     
/* 194 */     PointValuePair best = getPoint(0);
/* 195 */     PointValuePair secondBest = getPoint(n - 1);
/* 196 */     PointValuePair worst = getPoint(n);
/* 197 */     double[] xWorst = worst.getPointRef();
/*     */ 
/*     */ 
/*     */     
/* 201 */     double[] centroid = new double[n];
/* 202 */     for (int i = 0; i < n; i++) {
/* 203 */       double[] x = getPoint(i).getPointRef();
/* 204 */       for (int m = 0; m < n; m++) {
/* 205 */         centroid[m] = centroid[m] + x[m];
/*     */       }
/*     */     } 
/* 208 */     double scaling = 1.0D / n;
/* 209 */     for (int j = 0; j < n; j++) {
/* 210 */       centroid[j] = centroid[j] * scaling;
/*     */     }
/*     */ 
/*     */     
/* 214 */     double[] xR = new double[n];
/* 215 */     for (int k = 0; k < n; k++) {
/* 216 */       xR[k] = centroid[k] + this.rho * (centroid[k] - xWorst[k]);
/*     */     }
/* 218 */     PointValuePair reflected = new PointValuePair(xR, evaluationFunction.value(xR), false);
/*     */ 
/*     */     
/* 221 */     if (comparator.compare(best, reflected) <= 0 && comparator.compare(reflected, secondBest) < 0) {
/*     */ 
/*     */       
/* 224 */       replaceWorstPoint(reflected, comparator);
/* 225 */     } else if (comparator.compare(reflected, best) < 0) {
/*     */       
/* 227 */       double[] xE = new double[n];
/* 228 */       for (int m = 0; m < n; m++) {
/* 229 */         xE[m] = centroid[m] + this.khi * (xR[m] - centroid[m]);
/*     */       }
/* 231 */       PointValuePair expanded = new PointValuePair(xE, evaluationFunction.value(xE), false);
/*     */ 
/*     */       
/* 234 */       if (comparator.compare(expanded, reflected) < 0) {
/*     */         
/* 236 */         replaceWorstPoint(expanded, comparator);
/*     */       } else {
/*     */         
/* 239 */         replaceWorstPoint(reflected, comparator);
/*     */       } 
/*     */     } else {
/* 242 */       if (comparator.compare(reflected, worst) < 0) {
/*     */         
/* 244 */         double[] xC = new double[n];
/* 245 */         for (int i1 = 0; i1 < n; i1++) {
/* 246 */           xC[i1] = centroid[i1] + this.gamma * (xR[i1] - centroid[i1]);
/*     */         }
/* 248 */         PointValuePair outContracted = new PointValuePair(xC, evaluationFunction.value(xC), false);
/*     */         
/* 250 */         if (comparator.compare(outContracted, reflected) <= 0) {
/*     */           
/* 252 */           replaceWorstPoint(outContracted, comparator);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } else {
/* 257 */         double[] xC = new double[n];
/* 258 */         for (int i1 = 0; i1 < n; i1++) {
/* 259 */           xC[i1] = centroid[i1] - this.gamma * (centroid[i1] - xWorst[i1]);
/*     */         }
/* 261 */         PointValuePair inContracted = new PointValuePair(xC, evaluationFunction.value(xC), false);
/*     */ 
/*     */         
/* 264 */         if (comparator.compare(inContracted, worst) < 0) {
/*     */           
/* 266 */           replaceWorstPoint(inContracted, comparator);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 272 */       double[] xSmallest = getPoint(0).getPointRef();
/* 273 */       for (int m = 1; m <= n; m++) {
/* 274 */         double[] x = getPoint(m).getPoint();
/* 275 */         for (int i1 = 0; i1 < n; i1++) {
/* 276 */           x[i1] = xSmallest[i1] + this.sigma * (x[i1] - xSmallest[i1]);
/*     */         }
/* 278 */         setPoint(m, new PointValuePair(x, Double.NaN, false));
/*     */       } 
/* 280 */       evaluate(evaluationFunction, comparator);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/NelderMeadSimplex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */