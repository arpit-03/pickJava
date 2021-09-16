/*     */ package org.apache.commons.math3.optimization.fitting;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.function.HarmonicOscillator;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*     */ @Deprecated
/*     */ public class HarmonicFitter
/*     */   extends CurveFitter<HarmonicOscillator.Parametric>
/*     */ {
/*     */   public HarmonicFitter(DifferentiableMultivariateVectorOptimizer optimizer) {
/*  47 */     super(optimizer);
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
/*     */   public double[] fit(double[] initialGuess) {
/*  63 */     return fit(new HarmonicOscillator.Parametric(), initialGuess);
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
/*     */   public double[] fit() {
/*  78 */     return fit((new ParameterGuesser(getObservations())).guess());
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
/*     */     public ParameterGuesser(WeightedObservedPoint[] observations) {
/* 198 */       if (observations.length < 4) {
/* 199 */         throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, Integer.valueOf(observations.length), Integer.valueOf(4), true);
/*     */       }
/*     */ 
/*     */       
/* 203 */       WeightedObservedPoint[] sorted = sortObservations(observations);
/*     */       
/* 205 */       double[] aOmega = guessAOmega(sorted);
/* 206 */       this.a = aOmega[0];
/* 207 */       this.omega = aOmega[1];
/*     */       
/* 209 */       this.phi = guessPhi(sorted);
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
/* 223 */       return new double[] { this.a, this.omega, this.phi };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private WeightedObservedPoint[] sortObservations(WeightedObservedPoint[] unsorted) {
/* 233 */       WeightedObservedPoint[] observations = (WeightedObservedPoint[])unsorted.clone();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       WeightedObservedPoint curr = observations[0];
/* 239 */       for (int j = 1; j < observations.length; j++) {
/* 240 */         WeightedObservedPoint prec = curr;
/* 241 */         curr = observations[j];
/* 242 */         if (curr.getX() < prec.getX()) {
/*     */           
/* 244 */           int i = j - 1;
/* 245 */           WeightedObservedPoint mI = observations[i];
/* 246 */           while (i >= 0 && curr.getX() < mI.getX()) {
/* 247 */             observations[i + 1] = mI;
/* 248 */             if (i-- != 0) {
/* 249 */               mI = observations[i];
/*     */             }
/*     */           } 
/* 252 */           observations[i + 1] = curr;
/* 253 */           curr = observations[j];
/*     */         } 
/*     */       } 
/*     */       
/* 257 */       return observations;
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
/*     */     private double[] guessAOmega(WeightedObservedPoint[] observations) {
/* 273 */       double[] aOmega = new double[2];
/*     */ 
/*     */       
/* 276 */       double sx2 = 0.0D;
/* 277 */       double sy2 = 0.0D;
/* 278 */       double sxy = 0.0D;
/* 279 */       double sxz = 0.0D;
/* 280 */       double syz = 0.0D;
/*     */       
/* 282 */       double currentX = observations[0].getX();
/* 283 */       double currentY = observations[0].getY();
/* 284 */       double f2Integral = 0.0D;
/* 285 */       double fPrime2Integral = 0.0D;
/* 286 */       double startX = currentX;
/* 287 */       for (int i = 1; i < observations.length; i++) {
/*     */         
/* 289 */         double previousX = currentX;
/* 290 */         double previousY = currentY;
/* 291 */         currentX = observations[i].getX();
/* 292 */         currentY = observations[i].getY();
/*     */ 
/*     */ 
/*     */         
/* 296 */         double dx = currentX - previousX;
/* 297 */         double dy = currentY - previousY;
/* 298 */         double f2StepIntegral = dx * (previousY * previousY + previousY * currentY + currentY * currentY) / 3.0D;
/*     */         
/* 300 */         double fPrime2StepIntegral = dy * dy / dx;
/*     */         
/* 302 */         double x = currentX - startX;
/* 303 */         f2Integral += f2StepIntegral;
/* 304 */         fPrime2Integral += fPrime2StepIntegral;
/*     */         
/* 306 */         sx2 += x * x;
/* 307 */         sy2 += f2Integral * f2Integral;
/* 308 */         sxy += x * f2Integral;
/* 309 */         sxz += x * fPrime2Integral;
/* 310 */         syz += f2Integral * fPrime2Integral;
/*     */       } 
/*     */ 
/*     */       
/* 314 */       double c1 = sy2 * sxz - sxy * syz;
/* 315 */       double c2 = sxy * sxz - sx2 * syz;
/* 316 */       double c3 = sx2 * sy2 - sxy * sxy;
/* 317 */       if (c1 / c2 < 0.0D || c2 / c3 < 0.0D) {
/* 318 */         int last = observations.length - 1;
/*     */ 
/*     */         
/* 321 */         double xRange = observations[last].getX() - observations[0].getX();
/* 322 */         if (xRange == 0.0D) {
/* 323 */           throw new ZeroException();
/*     */         }
/* 325 */         aOmega[1] = 6.283185307179586D / xRange;
/*     */         
/* 327 */         double yMin = Double.POSITIVE_INFINITY;
/* 328 */         double yMax = Double.NEGATIVE_INFINITY;
/* 329 */         for (int j = 1; j < observations.length; j++) {
/* 330 */           double y = observations[j].getY();
/* 331 */           if (y < yMin) {
/* 332 */             yMin = y;
/*     */           }
/* 334 */           if (y > yMax) {
/* 335 */             yMax = y;
/*     */           }
/*     */         } 
/* 338 */         aOmega[0] = 0.5D * (yMax - yMin);
/*     */       } else {
/* 340 */         if (c2 == 0.0D)
/*     */         {
/*     */           
/* 343 */           throw new MathIllegalStateException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*     */         }
/*     */         
/* 346 */         aOmega[0] = FastMath.sqrt(c1 / c2);
/* 347 */         aOmega[1] = FastMath.sqrt(c2 / c3);
/*     */       } 
/*     */       
/* 350 */       return aOmega;
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
/* 361 */       double fcMean = 0.0D;
/* 362 */       double fsMean = 0.0D;
/*     */       
/* 364 */       double currentX = observations[0].getX();
/* 365 */       double currentY = observations[0].getY();
/* 366 */       for (int i = 1; i < observations.length; i++) {
/*     */         
/* 368 */         double previousX = currentX;
/* 369 */         double previousY = currentY;
/* 370 */         currentX = observations[i].getX();
/* 371 */         currentY = observations[i].getY();
/* 372 */         double currentYPrime = (currentY - previousY) / (currentX - previousX);
/*     */         
/* 374 */         double omegaX = this.omega * currentX;
/* 375 */         double cosine = FastMath.cos(omegaX);
/* 376 */         double sine = FastMath.sin(omegaX);
/* 377 */         fcMean += this.omega * currentY * cosine - currentYPrime * sine;
/* 378 */         fsMean += this.omega * currentY * sine + currentYPrime * cosine;
/*     */       } 
/*     */       
/* 381 */       return FastMath.atan2(-fsMean, fcMean);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/fitting/HarmonicFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */