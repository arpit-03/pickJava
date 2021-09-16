/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.function.Gaussian;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class GaussianFitter
/*     */   extends CurveFitter<Gaussian.Parametric>
/*     */ {
/*     */   public GaussianFitter(MultivariateVectorOptimizer optimizer) {
/*  66 */     super(optimizer);
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
/*     */   public double[] fit(double[] initialGuess) {
/*  83 */     Gaussian.Parametric f = new Gaussian.Parametric()
/*     */       {
/*     */         public double value(double x, double... p)
/*     */         {
/*  87 */           double v = Double.POSITIVE_INFINITY;
/*     */           try {
/*  89 */             v = super.value(x, p);
/*  90 */           } catch (NotStrictlyPositiveException notStrictlyPositiveException) {}
/*     */ 
/*     */           
/*  93 */           return v;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public double[] gradient(double x, double... p) {
/*  99 */           double[] v = { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
/*     */ 
/*     */           
/*     */           try {
/* 103 */             v = super.gradient(x, p);
/* 104 */           } catch (NotStrictlyPositiveException notStrictlyPositiveException) {}
/*     */ 
/*     */           
/* 107 */           return v;
/*     */         }
/*     */       };
/*     */     
/* 111 */     return fit(f, initialGuess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] fit() {
/* 121 */     double[] guess = (new ParameterGuesser(getObservations())).guess();
/* 122 */     return fit(guess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ParameterGuesser
/*     */   {
/*     */     private final double norm;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double mean;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double sigma;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ParameterGuesser(WeightedObservedPoint[] observations) {
/* 149 */       if (observations == null) {
/* 150 */         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */       }
/* 152 */       if (observations.length < 3) {
/* 153 */         throw new NumberIsTooSmallException(Integer.valueOf(observations.length), Integer.valueOf(3), true);
/*     */       }
/*     */       
/* 156 */       WeightedObservedPoint[] sorted = sortObservations(observations);
/* 157 */       double[] params = basicGuess(sorted);
/*     */       
/* 159 */       this.norm = params[0];
/* 160 */       this.mean = params[1];
/* 161 */       this.sigma = params[2];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] guess() {
/* 175 */       return new double[] { this.norm, this.mean, this.sigma };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private WeightedObservedPoint[] sortObservations(WeightedObservedPoint[] unsorted) {
/* 185 */       WeightedObservedPoint[] observations = (WeightedObservedPoint[])unsorted.clone();
/* 186 */       Comparator<WeightedObservedPoint> cmp = new Comparator<WeightedObservedPoint>()
/*     */         {
/*     */           
/*     */           public int compare(WeightedObservedPoint p1, WeightedObservedPoint p2)
/*     */           {
/* 191 */             if (p1 == null && p2 == null) {
/* 192 */               return 0;
/*     */             }
/* 194 */             if (p1 == null) {
/* 195 */               return -1;
/*     */             }
/* 197 */             if (p2 == null) {
/* 198 */               return 1;
/*     */             }
/* 200 */             int cmpX = Double.compare(p1.getX(), p2.getX());
/* 201 */             if (cmpX < 0) {
/* 202 */               return -1;
/*     */             }
/* 204 */             if (cmpX > 0) {
/* 205 */               return 1;
/*     */             }
/* 207 */             int cmpY = Double.compare(p1.getY(), p2.getY());
/* 208 */             if (cmpY < 0) {
/* 209 */               return -1;
/*     */             }
/* 211 */             if (cmpY > 0) {
/* 212 */               return 1;
/*     */             }
/* 214 */             int cmpW = Double.compare(p1.getWeight(), p2.getWeight());
/* 215 */             if (cmpW < 0) {
/* 216 */               return -1;
/*     */             }
/* 218 */             if (cmpW > 0) {
/* 219 */               return 1;
/*     */             }
/* 221 */             return 0;
/*     */           }
/*     */         };
/*     */       
/* 225 */       Arrays.sort(observations, cmp);
/* 226 */       return observations;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double[] basicGuess(WeightedObservedPoint[] points) {
/*     */       double fwhmApprox;
/* 237 */       int maxYIdx = findMaxY(points);
/* 238 */       double n = points[maxYIdx].getY();
/* 239 */       double m = points[maxYIdx].getX();
/*     */ 
/*     */       
/*     */       try {
/* 243 */         double halfY = n + (m - n) / 2.0D;
/* 244 */         double fwhmX1 = interpolateXAtY(points, maxYIdx, -1, halfY);
/* 245 */         double fwhmX2 = interpolateXAtY(points, maxYIdx, 1, halfY);
/* 246 */         fwhmApprox = fwhmX2 - fwhmX1;
/* 247 */       } catch (OutOfRangeException e) {
/*     */         
/* 249 */         fwhmApprox = points[points.length - 1].getX() - points[0].getX();
/*     */       } 
/* 251 */       double s = fwhmApprox / 2.0D * FastMath.sqrt(2.0D * FastMath.log(2.0D));
/*     */       
/* 253 */       return new double[] { n, m, s };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int findMaxY(WeightedObservedPoint[] points) {
/* 263 */       int maxYIdx = 0;
/* 264 */       for (int i = 1; i < points.length; i++) {
/* 265 */         if (points[i].getY() > points[maxYIdx].getY()) {
/* 266 */           maxYIdx = i;
/*     */         }
/*     */       } 
/* 269 */       return maxYIdx;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double interpolateXAtY(WeightedObservedPoint[] points, int startIdx, int idxStep, double y) throws OutOfRangeException {
/* 291 */       if (idxStep == 0) {
/* 292 */         throw new ZeroException();
/*     */       }
/* 294 */       WeightedObservedPoint[] twoPoints = getInterpolationPointsForY(points, startIdx, idxStep, y);
/*     */       
/* 296 */       WeightedObservedPoint p1 = twoPoints[0];
/* 297 */       WeightedObservedPoint p2 = twoPoints[1];
/* 298 */       if (p1.getY() == y) {
/* 299 */         return p1.getX();
/*     */       }
/* 301 */       if (p2.getY() == y) {
/* 302 */         return p2.getX();
/*     */       }
/* 304 */       return p1.getX() + (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private WeightedObservedPoint[] getInterpolationPointsForY(WeightedObservedPoint[] points, int startIdx, int idxStep, double y) throws OutOfRangeException {
/* 328 */       if (idxStep == 0) {
/* 329 */         throw new ZeroException();
/*     */       }
/* 331 */       int i = startIdx;
/* 332 */       for (; (idxStep < 0) ? (i + idxStep >= 0) : (i + idxStep < points.length); 
/* 333 */         i += idxStep) {
/* 334 */         WeightedObservedPoint p1 = points[i];
/* 335 */         WeightedObservedPoint p2 = points[i + idxStep];
/* 336 */         if (isBetween(y, p1.getY(), p2.getY())) {
/* 337 */           if (idxStep < 0) {
/* 338 */             return new WeightedObservedPoint[] { p2, p1 };
/*     */           }
/* 340 */           return new WeightedObservedPoint[] { p1, p2 };
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 348 */       throw new OutOfRangeException(Double.valueOf(y), Double.valueOf(Double.NEGATIVE_INFINITY), Double.valueOf(Double.POSITIVE_INFINITY));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isBetween(double value, double boundary1, double boundary2) {
/* 366 */       return ((value >= boundary1 && value <= boundary2) || (value >= boundary2 && value <= boundary1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/GaussianFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */