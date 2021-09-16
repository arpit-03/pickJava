/*     */ package org.apache.commons.math3.optimization.fitting;
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
/*     */ import org.apache.commons.math3.optimization.DifferentiableMultivariateVectorOptimizer;
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
/*     */ 
/*     */ @Deprecated
/*     */ public class GaussianFitter
/*     */   extends CurveFitter<Gaussian.Parametric>
/*     */ {
/*     */   public GaussianFitter(DifferentiableMultivariateVectorOptimizer optimizer) {
/*  67 */     super(optimizer);
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
/*  84 */     Gaussian.Parametric f = new Gaussian.Parametric()
/*     */       {
/*     */         public double value(double x, double... p)
/*     */         {
/*  88 */           double v = Double.POSITIVE_INFINITY;
/*     */           try {
/*  90 */             v = super.value(x, p);
/*  91 */           } catch (NotStrictlyPositiveException notStrictlyPositiveException) {}
/*     */ 
/*     */           
/*  94 */           return v;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public double[] gradient(double x, double... p) {
/* 100 */           double[] v = { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
/*     */ 
/*     */           
/*     */           try {
/* 104 */             v = super.gradient(x, p);
/* 105 */           } catch (NotStrictlyPositiveException notStrictlyPositiveException) {}
/*     */ 
/*     */           
/* 108 */           return v;
/*     */         }
/*     */       };
/*     */     
/* 112 */     return fit(f, initialGuess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] fit() {
/* 122 */     double[] guess = (new ParameterGuesser(getObservations())).guess();
/* 123 */     return fit(guess);
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
/* 150 */       if (observations == null) {
/* 151 */         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */       }
/* 153 */       if (observations.length < 3) {
/* 154 */         throw new NumberIsTooSmallException(Integer.valueOf(observations.length), Integer.valueOf(3), true);
/*     */       }
/*     */       
/* 157 */       WeightedObservedPoint[] sorted = sortObservations(observations);
/* 158 */       double[] params = basicGuess(sorted);
/*     */       
/* 160 */       this.norm = params[0];
/* 161 */       this.mean = params[1];
/* 162 */       this.sigma = params[2];
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
/* 176 */       return new double[] { this.norm, this.mean, this.sigma };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private WeightedObservedPoint[] sortObservations(WeightedObservedPoint[] unsorted) {
/* 186 */       WeightedObservedPoint[] observations = (WeightedObservedPoint[])unsorted.clone();
/* 187 */       Comparator<WeightedObservedPoint> cmp = new Comparator<WeightedObservedPoint>()
/*     */         {
/*     */           
/*     */           public int compare(WeightedObservedPoint p1, WeightedObservedPoint p2)
/*     */           {
/* 192 */             if (p1 == null && p2 == null) {
/* 193 */               return 0;
/*     */             }
/* 195 */             if (p1 == null) {
/* 196 */               return -1;
/*     */             }
/* 198 */             if (p2 == null) {
/* 199 */               return 1;
/*     */             }
/* 201 */             int cmpX = Double.compare(p1.getX(), p2.getX());
/* 202 */             if (cmpX < 0) {
/* 203 */               return -1;
/*     */             }
/* 205 */             if (cmpX > 0) {
/* 206 */               return 1;
/*     */             }
/* 208 */             int cmpY = Double.compare(p1.getY(), p2.getY());
/* 209 */             if (cmpY < 0) {
/* 210 */               return -1;
/*     */             }
/* 212 */             if (cmpY > 0) {
/* 213 */               return 1;
/*     */             }
/* 215 */             int cmpW = Double.compare(p1.getWeight(), p2.getWeight());
/* 216 */             if (cmpW < 0) {
/* 217 */               return -1;
/*     */             }
/* 219 */             if (cmpW > 0) {
/* 220 */               return 1;
/*     */             }
/* 222 */             return 0;
/*     */           }
/*     */         };
/*     */       
/* 226 */       Arrays.sort(observations, cmp);
/* 227 */       return observations;
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
/* 238 */       int maxYIdx = findMaxY(points);
/* 239 */       double n = points[maxYIdx].getY();
/* 240 */       double m = points[maxYIdx].getX();
/*     */ 
/*     */       
/*     */       try {
/* 244 */         double halfY = n + (m - n) / 2.0D;
/* 245 */         double fwhmX1 = interpolateXAtY(points, maxYIdx, -1, halfY);
/* 246 */         double fwhmX2 = interpolateXAtY(points, maxYIdx, 1, halfY);
/* 247 */         fwhmApprox = fwhmX2 - fwhmX1;
/* 248 */       } catch (OutOfRangeException e) {
/*     */         
/* 250 */         fwhmApprox = points[points.length - 1].getX() - points[0].getX();
/*     */       } 
/* 252 */       double s = fwhmApprox / 2.0D * FastMath.sqrt(2.0D * FastMath.log(2.0D));
/*     */       
/* 254 */       return new double[] { n, m, s };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int findMaxY(WeightedObservedPoint[] points) {
/* 264 */       int maxYIdx = 0;
/* 265 */       for (int i = 1; i < points.length; i++) {
/* 266 */         if (points[i].getY() > points[maxYIdx].getY()) {
/* 267 */           maxYIdx = i;
/*     */         }
/*     */       } 
/* 270 */       return maxYIdx;
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
/* 292 */       if (idxStep == 0) {
/* 293 */         throw new ZeroException();
/*     */       }
/* 295 */       WeightedObservedPoint[] twoPoints = getInterpolationPointsForY(points, startIdx, idxStep, y);
/*     */       
/* 297 */       WeightedObservedPoint p1 = twoPoints[0];
/* 298 */       WeightedObservedPoint p2 = twoPoints[1];
/* 299 */       if (p1.getY() == y) {
/* 300 */         return p1.getX();
/*     */       }
/* 302 */       if (p2.getY() == y) {
/* 303 */         return p2.getX();
/*     */       }
/* 305 */       return p1.getX() + (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
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
/* 329 */       if (idxStep == 0) {
/* 330 */         throw new ZeroException();
/*     */       }
/* 332 */       int i = startIdx;
/* 333 */       for (; (idxStep < 0) ? (i + idxStep >= 0) : (i + idxStep < points.length); 
/* 334 */         i += idxStep) {
/* 335 */         WeightedObservedPoint p1 = points[i];
/* 336 */         WeightedObservedPoint p2 = points[i + idxStep];
/* 337 */         if (isBetween(y, p1.getY(), p2.getY())) {
/* 338 */           if (idxStep < 0) {
/* 339 */             return new WeightedObservedPoint[] { p2, p1 };
/*     */           }
/* 341 */           return new WeightedObservedPoint[] { p1, p2 };
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 349 */       throw new OutOfRangeException(Double.valueOf(y), Double.valueOf(Double.NEGATIVE_INFINITY), Double.valueOf(Double.POSITIVE_INFINITY));
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
/* 367 */       return ((value >= boundary1 && value <= boundary2) || (value >= boundary2 && value <= boundary1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/fitting/GaussianFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */