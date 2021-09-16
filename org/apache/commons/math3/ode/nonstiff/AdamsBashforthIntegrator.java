/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.ode.EquationsMapper;
/*     */ import org.apache.commons.math3.ode.ExpandableStatefulODE;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractStepInterpolator;
/*     */ import org.apache.commons.math3.ode.sampling.NordsieckStepInterpolator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdamsBashforthIntegrator
/*     */   extends AdamsIntegrator
/*     */ {
/*     */   private static final String METHOD_NAME = "Adams-Bashforth";
/*     */   
/*     */   public AdamsBashforthIntegrator(int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/* 167 */     super("Adams-Bashforth", nSteps, nSteps, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
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
/*     */   public AdamsBashforthIntegrator(int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
/* 189 */     super("Adams-Bashforth", nSteps, nSteps, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
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
/*     */   private double errorEstimation(double[] previousState, double[] predictedState, double[] predictedScaled, RealMatrix predictedNordsieck) {
/* 209 */     double error = 0.0D;
/* 210 */     for (int i = 0; i < this.mainSetDimension; i++) {
/* 211 */       double yScale = FastMath.abs(predictedState[i]);
/* 212 */       double tol = (this.vecAbsoluteTolerance == null) ? (this.scalAbsoluteTolerance + this.scalRelativeTolerance * yScale) : (this.vecAbsoluteTolerance[i] + this.vecRelativeTolerance[i] * yScale);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       double variation = 0.0D;
/* 219 */       int sign = (predictedNordsieck.getRowDimension() % 2 == 0) ? -1 : 1;
/* 220 */       for (int k = predictedNordsieck.getRowDimension() - 1; k >= 0; k--) {
/* 221 */         variation += sign * predictedNordsieck.getEntry(k, i);
/* 222 */         sign = -sign;
/*     */       } 
/* 224 */       variation -= predictedScaled[i];
/*     */       
/* 226 */       double ratio = (predictedState[i] - previousState[i] + variation) / tol;
/* 227 */       error += ratio * ratio;
/*     */     } 
/*     */ 
/*     */     
/* 231 */     return FastMath.sqrt(error / this.mainSetDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 241 */     sanityChecks(equations, t);
/* 242 */     setEquations(equations);
/* 243 */     boolean forward = (t > equations.getTime());
/*     */ 
/*     */     
/* 246 */     double[] y = equations.getCompleteState();
/* 247 */     double[] yDot = new double[y.length];
/*     */ 
/*     */     
/* 250 */     NordsieckStepInterpolator interpolator = new NordsieckStepInterpolator();
/* 251 */     interpolator.reinitialize(y, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
/*     */ 
/*     */ 
/*     */     
/* 255 */     initIntegration(equations.getTime(), y, t);
/*     */ 
/*     */     
/* 258 */     start(equations.getTime(), y, t);
/* 259 */     interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
/* 260 */     interpolator.storeTime(this.stepStart);
/*     */ 
/*     */     
/* 263 */     double hNew = this.stepSize;
/* 264 */     interpolator.rescale(hNew);
/*     */ 
/*     */     
/* 267 */     this.isLastStep = false;
/*     */     
/*     */     do {
/* 270 */       interpolator.shift();
/* 271 */       double[] predictedY = new double[y.length];
/* 272 */       double[] predictedScaled = new double[y.length];
/* 273 */       Array2DRowRealMatrix predictedNordsieck = null;
/* 274 */       double error = 10.0D;
/* 275 */       while (error >= 1.0D) {
/*     */ 
/*     */         
/* 278 */         double d = this.stepStart + hNew;
/* 279 */         interpolator.storeTime(d);
/* 280 */         ExpandableStatefulODE expandable = getExpandable();
/* 281 */         EquationsMapper primary = expandable.getPrimaryMapper();
/* 282 */         primary.insertEquationData(interpolator.getInterpolatedState(), predictedY);
/* 283 */         int index = 0;
/* 284 */         for (EquationsMapper secondary : expandable.getSecondaryMappers()) {
/* 285 */           secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), predictedY);
/* 286 */           index++;
/*     */         } 
/*     */ 
/*     */         
/* 290 */         computeDerivatives(d, predictedY, yDot);
/*     */ 
/*     */         
/* 293 */         for (int j = 0; j < predictedScaled.length; j++) {
/* 294 */           predictedScaled[j] = hNew * yDot[j];
/*     */         }
/* 296 */         predictedNordsieck = updateHighOrderDerivativesPhase1(this.nordsieck);
/* 297 */         updateHighOrderDerivativesPhase2(this.scaled, predictedScaled, predictedNordsieck);
/*     */ 
/*     */         
/* 300 */         error = errorEstimation(y, predictedY, predictedScaled, (RealMatrix)predictedNordsieck);
/*     */         
/* 302 */         if (error >= 1.0D) {
/*     */           
/* 304 */           double d1 = computeStepGrowShrinkFactor(error);
/* 305 */           hNew = filterStep(hNew * d1, forward, false);
/* 306 */           interpolator.rescale(hNew);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 311 */       this.stepSize = hNew;
/* 312 */       double stepEnd = this.stepStart + this.stepSize;
/* 313 */       interpolator.reinitialize(stepEnd, this.stepSize, predictedScaled, predictedNordsieck);
/*     */ 
/*     */       
/* 316 */       interpolator.storeTime(stepEnd);
/* 317 */       System.arraycopy(predictedY, 0, y, 0, y.length);
/* 318 */       this.stepStart = acceptStep((AbstractStepInterpolator)interpolator, y, yDot, t);
/* 319 */       this.scaled = predictedScaled;
/* 320 */       this.nordsieck = predictedNordsieck;
/* 321 */       interpolator.reinitialize(stepEnd, this.stepSize, this.scaled, this.nordsieck);
/*     */       
/* 323 */       if (this.isLastStep) {
/*     */         continue;
/*     */       }
/* 326 */       interpolator.storeTime(this.stepStart);
/*     */       
/* 328 */       if (this.resetOccurred) {
/*     */ 
/*     */         
/* 331 */         start(this.stepStart, y, t);
/* 332 */         interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
/*     */       } 
/*     */ 
/*     */       
/* 336 */       double factor = computeStepGrowShrinkFactor(error);
/* 337 */       double scaledH = this.stepSize * factor;
/* 338 */       double nextT = this.stepStart + scaledH;
/* 339 */       boolean nextIsLast = forward ? ((nextT >= t)) : ((nextT <= t));
/* 340 */       hNew = filterStep(scaledH, forward, nextIsLast);
/*     */       
/* 342 */       double filteredNextT = this.stepStart + hNew;
/* 343 */       boolean filteredNextIsLast = forward ? ((filteredNextT >= t)) : ((filteredNextT <= t));
/* 344 */       if (filteredNextIsLast) {
/* 345 */         hNew = t - this.stepStart;
/*     */       }
/*     */       
/* 348 */       interpolator.rescale(hNew);
/*     */ 
/*     */     
/*     */     }
/* 352 */     while (!this.isLastStep);
/*     */ 
/*     */     
/* 355 */     equations.setTime(this.stepStart);
/* 356 */     equations.setCompleteState(y);
/*     */     
/* 358 */     resetInternalState();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsBashforthIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */