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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  55 */     this(n, 1.0D);
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
/*  68 */     this(n, sideLength, 1.0D, 2.0D, 0.5D, 0.5D);
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
/*  87 */     super(n, sideLength);
/*     */     
/*  89 */     this.rho = rho;
/*  90 */     this.khi = khi;
/*  91 */     this.gamma = gamma;
/*  92 */     this.sigma = sigma;
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
/* 108 */     this(n, 1.0D, rho, khi, gamma, sigma);
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
/* 120 */     this(steps, 1.0D, 2.0D, 0.5D, 0.5D);
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
/* 138 */     super(steps);
/*     */     
/* 140 */     this.rho = rho;
/* 141 */     this.khi = khi;
/* 142 */     this.gamma = gamma;
/* 143 */     this.sigma = sigma;
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
/* 155 */     this(referenceSimplex, 1.0D, 2.0D, 0.5D, 0.5D);
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
/* 175 */     super(referenceSimplex);
/*     */     
/* 177 */     this.rho = rho;
/* 178 */     this.khi = khi;
/* 179 */     this.gamma = gamma;
/* 180 */     this.sigma = sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void iterate(MultivariateFunction evaluationFunction, Comparator<PointValuePair> comparator) {
/* 188 */     int n = getDimension();
/*     */ 
/*     */     
/* 191 */     PointValuePair best = getPoint(0);
/* 192 */     PointValuePair secondBest = getPoint(n - 1);
/* 193 */     PointValuePair worst = getPoint(n);
/* 194 */     double[] xWorst = worst.getPointRef();
/*     */ 
/*     */ 
/*     */     
/* 198 */     double[] centroid = new double[n];
/* 199 */     for (int i = 0; i < n; i++) {
/* 200 */       double[] x = getPoint(i).getPointRef();
/* 201 */       for (int m = 0; m < n; m++) {
/* 202 */         centroid[m] = centroid[m] + x[m];
/*     */       }
/*     */     } 
/* 205 */     double scaling = 1.0D / n;
/* 206 */     for (int j = 0; j < n; j++) {
/* 207 */       centroid[j] = centroid[j] * scaling;
/*     */     }
/*     */ 
/*     */     
/* 211 */     double[] xR = new double[n];
/* 212 */     for (int k = 0; k < n; k++) {
/* 213 */       xR[k] = centroid[k] + this.rho * (centroid[k] - xWorst[k]);
/*     */     }
/* 215 */     PointValuePair reflected = new PointValuePair(xR, evaluationFunction.value(xR), false);
/*     */ 
/*     */     
/* 218 */     if (comparator.compare(best, reflected) <= 0 && comparator.compare(reflected, secondBest) < 0) {
/*     */ 
/*     */       
/* 221 */       replaceWorstPoint(reflected, comparator);
/* 222 */     } else if (comparator.compare(reflected, best) < 0) {
/*     */       
/* 224 */       double[] xE = new double[n];
/* 225 */       for (int m = 0; m < n; m++) {
/* 226 */         xE[m] = centroid[m] + this.khi * (xR[m] - centroid[m]);
/*     */       }
/* 228 */       PointValuePair expanded = new PointValuePair(xE, evaluationFunction.value(xE), false);
/*     */ 
/*     */       
/* 231 */       if (comparator.compare(expanded, reflected) < 0) {
/*     */         
/* 233 */         replaceWorstPoint(expanded, comparator);
/*     */       } else {
/*     */         
/* 236 */         replaceWorstPoint(reflected, comparator);
/*     */       } 
/*     */     } else {
/* 239 */       if (comparator.compare(reflected, worst) < 0) {
/*     */         
/* 241 */         double[] xC = new double[n];
/* 242 */         for (int i1 = 0; i1 < n; i1++) {
/* 243 */           xC[i1] = centroid[i1] + this.gamma * (xR[i1] - centroid[i1]);
/*     */         }
/* 245 */         PointValuePair outContracted = new PointValuePair(xC, evaluationFunction.value(xC), false);
/*     */         
/* 247 */         if (comparator.compare(outContracted, reflected) <= 0) {
/*     */           
/* 249 */           replaceWorstPoint(outContracted, comparator);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } else {
/* 254 */         double[] xC = new double[n];
/* 255 */         for (int i1 = 0; i1 < n; i1++) {
/* 256 */           xC[i1] = centroid[i1] - this.gamma * (centroid[i1] - xWorst[i1]);
/*     */         }
/* 258 */         PointValuePair inContracted = new PointValuePair(xC, evaluationFunction.value(xC), false);
/*     */ 
/*     */         
/* 261 */         if (comparator.compare(inContracted, worst) < 0) {
/*     */           
/* 263 */           replaceWorstPoint(inContracted, comparator);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 269 */       double[] xSmallest = getPoint(0).getPointRef();
/* 270 */       for (int m = 1; m <= n; m++) {
/* 271 */         double[] x = getPoint(m).getPoint();
/* 272 */         for (int i1 = 0; i1 < n; i1++) {
/* 273 */           x[i1] = xSmallest[i1] + this.sigma * (x[i1] - xSmallest[i1]);
/*     */         }
/* 275 */         setPoint(m, new PointValuePair(x, Double.NaN, false));
/*     */       } 
/* 277 */       evaluate(evaluationFunction, comparator);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/noderiv/NelderMeadSimplex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */