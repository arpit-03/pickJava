/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.function.Gaussian;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
/*     */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
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
/*     */ 
/*     */ 
/*     */ public class GaussianCurveFitter
/*     */   extends AbstractCurveFitter
/*     */ {
/*  74 */   private static final Gaussian.Parametric FUNCTION = new Gaussian.Parametric()
/*     */     {
/*     */       public double value(double x, double... p)
/*     */       {
/*  78 */         double v = Double.POSITIVE_INFINITY;
/*     */         try {
/*  80 */           v = super.value(x, p);
/*  81 */         } catch (NotStrictlyPositiveException notStrictlyPositiveException) {}
/*     */ 
/*     */         
/*  84 */         return v;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public double[] gradient(double x, double... p) {
/*  90 */         double[] v = { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
/*     */ 
/*     */         
/*     */         try {
/*  94 */           v = super.gradient(x, p);
/*  95 */         } catch (NotStrictlyPositiveException notStrictlyPositiveException) {}
/*     */ 
/*     */         
/*  98 */         return v;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] initialGuess;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxIter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GaussianCurveFitter(double[] initialGuess, int maxIter) {
/* 115 */     this.initialGuess = initialGuess;
/* 116 */     this.maxIter = maxIter;
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
/*     */   public static GaussianCurveFitter create() {
/* 131 */     return new GaussianCurveFitter(null, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussianCurveFitter withStartPoint(double[] newStart) {
/* 140 */     return new GaussianCurveFitter((double[])newStart.clone(), this.maxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussianCurveFitter withMaxIterations(int newMaxIter) {
/* 150 */     return new GaussianCurveFitter(this.initialGuess, newMaxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> observations) {
/* 159 */     int len = observations.size();
/* 160 */     double[] target = new double[len];
/* 161 */     double[] weights = new double[len];
/*     */     
/* 163 */     int i = 0;
/* 164 */     for (WeightedObservedPoint obs : observations) {
/* 165 */       target[i] = obs.getY();
/* 166 */       weights[i] = obs.getWeight();
/* 167 */       i++;
/*     */     } 
/*     */     
/* 170 */     AbstractCurveFitter.TheoreticalValuesFunction model = new AbstractCurveFitter.TheoreticalValuesFunction((ParametricUnivariateFunction)FUNCTION, observations);
/*     */ 
/*     */     
/* 173 */     double[] startPoint = (this.initialGuess != null) ? this.initialGuess : (new ParameterGuesser(observations)).guess();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     return (new LeastSquaresBuilder()).maxEvaluations(2147483647).maxIterations(this.maxIter).start(startPoint).target(target).weight((RealMatrix)new DiagonalMatrix(weights)).model(model.getModelFunction(), model.getModelFunctionJacobian()).build();
/*     */   }
/*     */ 
/*     */ 
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
/*     */ 
/*     */     
/*     */     private final double mean;
/*     */ 
/*     */ 
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
/*     */ 
/*     */     
/*     */     public ParameterGuesser(Collection<WeightedObservedPoint> observations) {
/* 215 */       if (observations == null) {
/* 216 */         throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */       }
/* 218 */       if (observations.size() < 3) {
/* 219 */         throw new NumberIsTooSmallException(Integer.valueOf(observations.size()), Integer.valueOf(3), true);
/*     */       }
/*     */       
/* 222 */       List<WeightedObservedPoint> sorted = sortObservations(observations);
/* 223 */       double[] params = basicGuess(sorted.<WeightedObservedPoint>toArray(new WeightedObservedPoint[0]));
/*     */       
/* 225 */       this.norm = params[0];
/* 226 */       this.mean = params[1];
/* 227 */       this.sigma = params[2];
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
/* 241 */       return new double[] { this.norm, this.mean, this.sigma };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private List<WeightedObservedPoint> sortObservations(Collection<WeightedObservedPoint> unsorted) {
/* 251 */       List<WeightedObservedPoint> observations = new ArrayList<WeightedObservedPoint>(unsorted);
/*     */       
/* 253 */       Comparator<WeightedObservedPoint> cmp = new Comparator<WeightedObservedPoint>()
/*     */         {
/*     */           public int compare(WeightedObservedPoint p1, WeightedObservedPoint p2)
/*     */           {
/* 257 */             if (p1 == null && p2 == null) {
/* 258 */               return 0;
/*     */             }
/* 260 */             if (p1 == null) {
/* 261 */               return -1;
/*     */             }
/* 263 */             if (p2 == null) {
/* 264 */               return 1;
/*     */             }
/* 266 */             int cmpX = Double.compare(p1.getX(), p2.getX());
/* 267 */             if (cmpX < 0) {
/* 268 */               return -1;
/*     */             }
/* 270 */             if (cmpX > 0) {
/* 271 */               return 1;
/*     */             }
/* 273 */             int cmpY = Double.compare(p1.getY(), p2.getY());
/* 274 */             if (cmpY < 0) {
/* 275 */               return -1;
/*     */             }
/* 277 */             if (cmpY > 0) {
/* 278 */               return 1;
/*     */             }
/* 280 */             int cmpW = Double.compare(p1.getWeight(), p2.getWeight());
/* 281 */             if (cmpW < 0) {
/* 282 */               return -1;
/*     */             }
/* 284 */             if (cmpW > 0) {
/* 285 */               return 1;
/*     */             }
/* 287 */             return 0;
/*     */           }
/*     */         };
/*     */       
/* 291 */       Collections.sort(observations, cmp);
/* 292 */       return observations;
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
/* 303 */       int maxYIdx = findMaxY(points);
/* 304 */       double n = points[maxYIdx].getY();
/* 305 */       double m = points[maxYIdx].getX();
/*     */ 
/*     */       
/*     */       try {
/* 309 */         double halfY = n + (m - n) / 2.0D;
/* 310 */         double fwhmX1 = interpolateXAtY(points, maxYIdx, -1, halfY);
/* 311 */         double fwhmX2 = interpolateXAtY(points, maxYIdx, 1, halfY);
/* 312 */         fwhmApprox = fwhmX2 - fwhmX1;
/* 313 */       } catch (OutOfRangeException e) {
/*     */         
/* 315 */         fwhmApprox = points[points.length - 1].getX() - points[0].getX();
/*     */       } 
/* 317 */       double s = fwhmApprox / 2.0D * FastMath.sqrt(2.0D * FastMath.log(2.0D));
/*     */       
/* 319 */       return new double[] { n, m, s };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int findMaxY(WeightedObservedPoint[] points) {
/* 329 */       int maxYIdx = 0;
/* 330 */       for (int i = 1; i < points.length; i++) {
/* 331 */         if (points[i].getY() > points[maxYIdx].getY()) {
/* 332 */           maxYIdx = i;
/*     */         }
/*     */       } 
/* 335 */       return maxYIdx;
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
/* 357 */       if (idxStep == 0) {
/* 358 */         throw new ZeroException();
/*     */       }
/* 360 */       WeightedObservedPoint[] twoPoints = getInterpolationPointsForY(points, startIdx, idxStep, y);
/*     */       
/* 362 */       WeightedObservedPoint p1 = twoPoints[0];
/* 363 */       WeightedObservedPoint p2 = twoPoints[1];
/* 364 */       if (p1.getY() == y) {
/* 365 */         return p1.getX();
/*     */       }
/* 367 */       if (p2.getY() == y) {
/* 368 */         return p2.getX();
/*     */       }
/* 370 */       return p1.getX() + (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
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
/* 394 */       if (idxStep == 0) {
/* 395 */         throw new ZeroException();
/*     */       }
/* 397 */       int i = startIdx;
/* 398 */       for (; (idxStep < 0) ? (i + idxStep >= 0) : (i + idxStep < points.length); 
/* 399 */         i += idxStep) {
/* 400 */         WeightedObservedPoint p1 = points[i];
/* 401 */         WeightedObservedPoint p2 = points[i + idxStep];
/* 402 */         if (isBetween(y, p1.getY(), p2.getY())) {
/* 403 */           if (idxStep < 0) {
/* 404 */             return new WeightedObservedPoint[] { p2, p1 };
/*     */           }
/* 406 */           return new WeightedObservedPoint[] { p1, p2 };
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       throw new OutOfRangeException(Double.valueOf(y), Double.valueOf(Double.NEGATIVE_INFINITY), Double.valueOf(Double.POSITIVE_INFINITY));
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
/* 432 */       return ((value >= boundary1 && value <= boundary2) || (value >= boundary2 && value <= boundary1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/GaussianCurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */