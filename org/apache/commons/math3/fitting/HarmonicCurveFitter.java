/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.function.HarmonicOscillator;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*     */ public class HarmonicCurveFitter
/*     */   extends AbstractCurveFitter
/*     */ {
/*  51 */   private static final HarmonicOscillator.Parametric FUNCTION = new HarmonicOscillator.Parametric();
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
/*     */   private HarmonicCurveFitter(double[] initialGuess, int maxIter) {
/*  66 */     this.initialGuess = initialGuess;
/*  67 */     this.maxIter = maxIter;
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
/*     */   public static HarmonicCurveFitter create() {
/*  82 */     return new HarmonicCurveFitter(null, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HarmonicCurveFitter withStartPoint(double[] newStart) {
/*  91 */     return new HarmonicCurveFitter((double[])newStart.clone(), this.maxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HarmonicCurveFitter withMaxIterations(int newMaxIter) {
/* 101 */     return new HarmonicCurveFitter(this.initialGuess, newMaxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> observations) {
/* 109 */     int len = observations.size();
/* 110 */     double[] target = new double[len];
/* 111 */     double[] weights = new double[len];
/*     */     
/* 113 */     int i = 0;
/* 114 */     for (WeightedObservedPoint obs : observations) {
/* 115 */       target[i] = obs.getY();
/* 116 */       weights[i] = obs.getWeight();
/* 117 */       i++;
/*     */     } 
/*     */     
/* 120 */     AbstractCurveFitter.TheoreticalValuesFunction model = new AbstractCurveFitter.TheoreticalValuesFunction((ParametricUnivariateFunction)FUNCTION, observations);
/*     */ 
/*     */ 
/*     */     
/* 124 */     double[] startPoint = (this.initialGuess != null) ? this.initialGuess : (new ParameterGuesser(observations)).guess();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return (new LeastSquaresBuilder()).maxEvaluations(2147483647).maxIterations(this.maxIter).start(startPoint).target(target).weight((RealMatrix)new DiagonalMatrix(weights)).model(model.getModelFunction(), model.getModelFunctionJacobian()).build();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ParameterGuesser
/*     */   {
/*     */     private final double a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double omega;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double phi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ParameterGuesser(Collection<WeightedObservedPoint> observations) {
/* 259 */       if (observations.size() < 4) {
/* 260 */         throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, Integer.valueOf(observations.size()), Integer.valueOf(4), true);
/*     */       }
/*     */ 
/*     */       
/* 264 */       WeightedObservedPoint[] sorted = sortObservations(observations).<WeightedObservedPoint>toArray(new WeightedObservedPoint[0]);
/*     */ 
/*     */       
/* 267 */       double[] aOmega = guessAOmega(sorted);
/* 268 */       this.a = aOmega[0];
/* 269 */       this.omega = aOmega[1];
/*     */       
/* 271 */       this.phi = guessPhi(sorted);
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
/* 285 */       return new double[] { this.a, this.omega, this.phi };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private List<WeightedObservedPoint> sortObservations(Collection<WeightedObservedPoint> unsorted) {
/* 295 */       List<WeightedObservedPoint> observations = new ArrayList<WeightedObservedPoint>(unsorted);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 300 */       WeightedObservedPoint curr = observations.get(0);
/* 301 */       int len = observations.size();
/* 302 */       for (int j = 1; j < len; j++) {
/* 303 */         WeightedObservedPoint prec = curr;
/* 304 */         curr = observations.get(j);
/* 305 */         if (curr.getX() < prec.getX()) {
/*     */           
/* 307 */           int i = j - 1;
/* 308 */           WeightedObservedPoint mI = observations.get(i);
/* 309 */           while (i >= 0 && curr.getX() < mI.getX()) {
/* 310 */             observations.set(i + 1, mI);
/* 311 */             if (i-- != 0) {
/* 312 */               mI = observations.get(i);
/*     */             }
/*     */           } 
/* 315 */           observations.set(i + 1, curr);
/* 316 */           curr = observations.get(j);
/*     */         } 
/*     */       } 
/*     */       
/* 320 */       return observations;
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
/*     */     private double[] guessAOmega(WeightedObservedPoint[] observations) {
/* 334 */       double[] aOmega = new double[2];
/*     */ 
/*     */       
/* 337 */       double sx2 = 0.0D;
/* 338 */       double sy2 = 0.0D;
/* 339 */       double sxy = 0.0D;
/* 340 */       double sxz = 0.0D;
/* 341 */       double syz = 0.0D;
/*     */       
/* 343 */       double currentX = observations[0].getX();
/* 344 */       double currentY = observations[0].getY();
/* 345 */       double f2Integral = 0.0D;
/* 346 */       double fPrime2Integral = 0.0D;
/* 347 */       double startX = currentX;
/* 348 */       for (int i = 1; i < observations.length; i++) {
/*     */         
/* 350 */         double previousX = currentX;
/* 351 */         double previousY = currentY;
/* 352 */         currentX = observations[i].getX();
/* 353 */         currentY = observations[i].getY();
/*     */ 
/*     */ 
/*     */         
/* 357 */         double dx = currentX - previousX;
/* 358 */         double dy = currentY - previousY;
/* 359 */         double f2StepIntegral = dx * (previousY * previousY + previousY * currentY + currentY * currentY) / 3.0D;
/*     */         
/* 361 */         double fPrime2StepIntegral = dy * dy / dx;
/*     */         
/* 363 */         double x = currentX - startX;
/* 364 */         f2Integral += f2StepIntegral;
/* 365 */         fPrime2Integral += fPrime2StepIntegral;
/*     */         
/* 367 */         sx2 += x * x;
/* 368 */         sy2 += f2Integral * f2Integral;
/* 369 */         sxy += x * f2Integral;
/* 370 */         sxz += x * fPrime2Integral;
/* 371 */         syz += f2Integral * fPrime2Integral;
/*     */       } 
/*     */ 
/*     */       
/* 375 */       double c1 = sy2 * sxz - sxy * syz;
/* 376 */       double c2 = sxy * sxz - sx2 * syz;
/* 377 */       double c3 = sx2 * sy2 - sxy * sxy;
/* 378 */       if (c1 / c2 < 0.0D || c2 / c3 < 0.0D) {
/* 379 */         int last = observations.length - 1;
/*     */ 
/*     */         
/* 382 */         double xRange = observations[last].getX() - observations[0].getX();
/* 383 */         if (xRange == 0.0D) {
/* 384 */           throw new ZeroException();
/*     */         }
/* 386 */         aOmega[1] = 6.283185307179586D / xRange;
/*     */         
/* 388 */         double yMin = Double.POSITIVE_INFINITY;
/* 389 */         double yMax = Double.NEGATIVE_INFINITY;
/* 390 */         for (int j = 1; j < observations.length; j++) {
/* 391 */           double y = observations[j].getY();
/* 392 */           if (y < yMin) {
/* 393 */             yMin = y;
/*     */           }
/* 395 */           if (y > yMax) {
/* 396 */             yMax = y;
/*     */           }
/*     */         } 
/* 399 */         aOmega[0] = 0.5D * (yMax - yMin);
/*     */       } else {
/* 401 */         if (c2 == 0.0D)
/*     */         {
/*     */           
/* 404 */           throw new MathIllegalStateException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*     */         }
/*     */         
/* 407 */         aOmega[0] = FastMath.sqrt(c1 / c2);
/* 408 */         aOmega[1] = FastMath.sqrt(c2 / c3);
/*     */       } 
/*     */       
/* 411 */       return aOmega;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double guessPhi(WeightedObservedPoint[] observations) {
/* 422 */       double fcMean = 0.0D;
/* 423 */       double fsMean = 0.0D;
/*     */       
/* 425 */       double currentX = observations[0].getX();
/* 426 */       double currentY = observations[0].getY();
/* 427 */       for (int i = 1; i < observations.length; i++) {
/*     */         
/* 429 */         double previousX = currentX;
/* 430 */         double previousY = currentY;
/* 431 */         currentX = observations[i].getX();
/* 432 */         currentY = observations[i].getY();
/* 433 */         double currentYPrime = (currentY - previousY) / (currentX - previousX);
/*     */         
/* 435 */         double omegaX = this.omega * currentX;
/* 436 */         double cosine = FastMath.cos(omegaX);
/* 437 */         double sine = FastMath.sin(omegaX);
/* 438 */         fcMean += this.omega * currentY * cosine - currentYPrime * sine;
/* 439 */         fsMean += this.omega * currentY * sine + currentYPrime * cosine;
/*     */       } 
/*     */       
/* 442 */       return FastMath.atan2(-fsMean, fcMean);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/HarmonicCurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */