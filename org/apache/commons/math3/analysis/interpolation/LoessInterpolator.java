/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NotFiniteNumberException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoessInterpolator
/*     */   implements UnivariateInterpolator, Serializable
/*     */ {
/*     */   public static final double DEFAULT_BANDWIDTH = 0.3D;
/*     */   public static final int DEFAULT_ROBUSTNESS_ITERS = 2;
/*     */   public static final double DEFAULT_ACCURACY = 1.0E-12D;
/*     */   private static final long serialVersionUID = 5204927143605193821L;
/*     */   private final double bandwidth;
/*     */   private final int robustnessIters;
/*     */   private final double accuracy;
/*     */   
/*     */   public LoessInterpolator() {
/*  95 */     this.bandwidth = 0.3D;
/*  96 */     this.robustnessIters = 2;
/*  97 */     this.accuracy = 1.0E-12D;
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
/*     */   public LoessInterpolator(double bandwidth, int robustnessIters) {
/* 123 */     this(bandwidth, robustnessIters, 1.0E-12D);
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
/*     */   public LoessInterpolator(double bandwidth, int robustnessIters, double accuracy) throws OutOfRangeException, NotPositiveException {
/* 150 */     if (bandwidth < 0.0D || bandwidth > 1.0D)
/*     */     {
/* 152 */       throw new OutOfRangeException(LocalizedFormats.BANDWIDTH, Double.valueOf(bandwidth), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 154 */     this.bandwidth = bandwidth;
/* 155 */     if (robustnessIters < 0) {
/* 156 */       throw new NotPositiveException(LocalizedFormats.ROBUSTNESS_ITERATIONS, Integer.valueOf(robustnessIters));
/*     */     }
/* 158 */     this.robustnessIters = robustnessIters;
/* 159 */     this.accuracy = accuracy;
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
/*     */   
/*     */   public final PolynomialSplineFunction interpolate(double[] xval, double[] yval) throws NonMonotonicSequenceException, DimensionMismatchException, NoDataException, NotFiniteNumberException, NumberIsTooSmallException {
/* 190 */     return (new SplineInterpolator()).interpolate(xval, smooth(xval, yval));
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
/*     */   public final double[] smooth(double[] xval, double[] yval, double[] weights) throws NonMonotonicSequenceException, DimensionMismatchException, NoDataException, NotFiniteNumberException, NumberIsTooSmallException {
/* 220 */     if (xval.length != yval.length) {
/* 221 */       throw new DimensionMismatchException(xval.length, yval.length);
/*     */     }
/*     */     
/* 224 */     int n = xval.length;
/*     */     
/* 226 */     if (n == 0) {
/* 227 */       throw new NoDataException();
/*     */     }
/*     */     
/* 230 */     checkAllFiniteReal(xval);
/* 231 */     checkAllFiniteReal(yval);
/* 232 */     checkAllFiniteReal(weights);
/*     */     
/* 234 */     MathArrays.checkOrder(xval);
/*     */     
/* 236 */     if (n == 1) {
/* 237 */       return new double[] { yval[0] };
/*     */     }
/*     */     
/* 240 */     if (n == 2) {
/* 241 */       return new double[] { yval[0], yval[1] };
/*     */     }
/*     */     
/* 244 */     int bandwidthInPoints = (int)(this.bandwidth * n);
/*     */     
/* 246 */     if (bandwidthInPoints < 2) {
/* 247 */       throw new NumberIsTooSmallException(LocalizedFormats.BANDWIDTH, Integer.valueOf(bandwidthInPoints), Integer.valueOf(2), true);
/*     */     }
/*     */ 
/*     */     
/* 251 */     double[] res = new double[n];
/*     */     
/* 253 */     double[] residuals = new double[n];
/* 254 */     double[] sortedResiduals = new double[n];
/*     */     
/* 256 */     double[] robustnessWeights = new double[n];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     Arrays.fill(robustnessWeights, 1.0D);
/*     */     
/* 263 */     for (int iter = 0; iter <= this.robustnessIters; iter++) {
/* 264 */       int[] bandwidthInterval = { 0, bandwidthInPoints - 1 };
/*     */       
/* 266 */       for (int i = 0; i < n; i++) {
/* 267 */         int edge; double beta, x = xval[i];
/*     */ 
/*     */ 
/*     */         
/* 271 */         if (i > 0) {
/* 272 */           updateBandwidthInterval(xval, weights, i, bandwidthInterval);
/*     */         }
/*     */         
/* 275 */         int ileft = bandwidthInterval[0];
/* 276 */         int iright = bandwidthInterval[1];
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 281 */         if (xval[i] - xval[ileft] > xval[iright] - xval[i]) {
/* 282 */           edge = ileft;
/*     */         } else {
/* 284 */           edge = iright;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 294 */         double sumWeights = 0.0D;
/* 295 */         double sumX = 0.0D;
/* 296 */         double sumXSquared = 0.0D;
/* 297 */         double sumY = 0.0D;
/* 298 */         double sumXY = 0.0D;
/* 299 */         double denom = FastMath.abs(1.0D / (xval[edge] - x));
/* 300 */         for (int k = ileft; k <= iright; k++) {
/* 301 */           double xk = xval[k];
/* 302 */           double yk = yval[k];
/* 303 */           double dist = (k < i) ? (x - xk) : (xk - x);
/* 304 */           double w = tricube(dist * denom) * robustnessWeights[k] * weights[k];
/* 305 */           double xkw = xk * w;
/* 306 */           sumWeights += w;
/* 307 */           sumX += xkw;
/* 308 */           sumXSquared += xk * xkw;
/* 309 */           sumY += yk * w;
/* 310 */           sumXY += yk * xkw;
/*     */         } 
/*     */         
/* 313 */         double meanX = sumX / sumWeights;
/* 314 */         double meanY = sumY / sumWeights;
/* 315 */         double meanXY = sumXY / sumWeights;
/* 316 */         double meanXSquared = sumXSquared / sumWeights;
/*     */ 
/*     */         
/* 319 */         if (FastMath.sqrt(FastMath.abs(meanXSquared - meanX * meanX)) < this.accuracy) {
/* 320 */           beta = 0.0D;
/*     */         } else {
/* 322 */           beta = (meanXY - meanX * meanY) / (meanXSquared - meanX * meanX);
/*     */         } 
/*     */         
/* 325 */         double alpha = meanY - beta * meanX;
/*     */         
/* 327 */         res[i] = beta * x + alpha;
/* 328 */         residuals[i] = FastMath.abs(yval[i] - res[i]);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 333 */       if (iter == this.robustnessIters) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       System.arraycopy(residuals, 0, sortedResiduals, 0, n);
/* 343 */       Arrays.sort(sortedResiduals);
/* 344 */       double medianResidual = sortedResiduals[n / 2];
/*     */       
/* 346 */       if (FastMath.abs(medianResidual) < this.accuracy) {
/*     */         break;
/*     */       }
/*     */       
/* 350 */       for (int j = 0; j < n; j++) {
/* 351 */         double arg = residuals[j] / 6.0D * medianResidual;
/* 352 */         if (arg >= 1.0D) {
/* 353 */           robustnessWeights[j] = 0.0D;
/*     */         } else {
/* 355 */           double w = 1.0D - arg * arg;
/* 356 */           robustnessWeights[j] = w * w;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     return res;
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
/*     */   public final double[] smooth(double[] xval, double[] yval) throws NonMonotonicSequenceException, DimensionMismatchException, NoDataException, NotFiniteNumberException, NumberIsTooSmallException {
/* 387 */     if (xval.length != yval.length) {
/* 388 */       throw new DimensionMismatchException(xval.length, yval.length);
/*     */     }
/*     */     
/* 391 */     double[] unitWeights = new double[xval.length];
/* 392 */     Arrays.fill(unitWeights, 1.0D);
/*     */     
/* 394 */     return smooth(xval, yval, unitWeights);
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
/*     */   private static void updateBandwidthInterval(double[] xval, double[] weights, int i, int[] bandwidthInterval) {
/* 415 */     int left = bandwidthInterval[0];
/* 416 */     int right = bandwidthInterval[1];
/*     */ 
/*     */ 
/*     */     
/* 420 */     int nextRight = nextNonzero(weights, right);
/* 421 */     if (nextRight < xval.length && xval[nextRight] - xval[i] < xval[i] - xval[left]) {
/* 422 */       int nextLeft = nextNonzero(weights, bandwidthInterval[0]);
/* 423 */       bandwidthInterval[0] = nextLeft;
/* 424 */       bandwidthInterval[1] = nextRight;
/*     */     } 
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
/*     */   private static int nextNonzero(double[] weights, int i) {
/* 437 */     int j = i + 1;
/* 438 */     while (j < weights.length && weights[j] == 0.0D) {
/* 439 */       j++;
/*     */     }
/* 441 */     return j;
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
/*     */   private static double tricube(double x) {
/* 453 */     double absX = FastMath.abs(x);
/* 454 */     if (absX >= 1.0D) {
/* 455 */       return 0.0D;
/*     */     }
/* 457 */     double tmp = 1.0D - absX * absX * absX;
/* 458 */     return tmp * tmp * tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkAllFiniteReal(double[] values) {
/* 469 */     for (int i = 0; i < values.length; i++)
/* 470 */       MathUtils.checkFinite(values[i]); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/LoessInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */