/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrixPreservingVisitor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdamsMoultonIntegrator
/*     */   extends AdamsIntegrator
/*     */ {
/*     */   private static final String METHOD_NAME = "Adams-Moulton";
/*     */   
/*     */   public AdamsMoultonIntegrator(int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/* 182 */     super("Adams-Moulton", nSteps, nSteps + 1, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
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
/*     */   public AdamsMoultonIntegrator(int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
/* 204 */     super("Adams-Moulton", nSteps, nSteps + 1, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 214 */     sanityChecks(equations, t);
/* 215 */     setEquations(equations);
/* 216 */     boolean forward = (t > equations.getTime());
/*     */ 
/*     */     
/* 219 */     double[] y0 = equations.getCompleteState();
/* 220 */     double[] y = (double[])y0.clone();
/* 221 */     double[] yDot = new double[y.length];
/* 222 */     double[] yTmp = new double[y.length];
/* 223 */     double[] predictedScaled = new double[y.length];
/* 224 */     Array2DRowRealMatrix nordsieckTmp = null;
/*     */ 
/*     */     
/* 227 */     NordsieckStepInterpolator interpolator = new NordsieckStepInterpolator();
/* 228 */     interpolator.reinitialize(y, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
/*     */ 
/*     */ 
/*     */     
/* 232 */     initIntegration(equations.getTime(), y0, t);
/*     */ 
/*     */     
/* 235 */     start(equations.getTime(), y, t);
/* 236 */     interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
/* 237 */     interpolator.storeTime(this.stepStart);
/*     */     
/* 239 */     double hNew = this.stepSize;
/* 240 */     interpolator.rescale(hNew);
/*     */     
/* 242 */     this.isLastStep = false;
/*     */     
/*     */     do {
/* 245 */       double error = 10.0D;
/* 246 */       while (error >= 1.0D) {
/*     */         
/* 248 */         this.stepSize = hNew;
/*     */ 
/*     */         
/* 251 */         double d = this.stepStart + this.stepSize;
/* 252 */         interpolator.setInterpolatedTime(d);
/* 253 */         ExpandableStatefulODE expandable = getExpandable();
/* 254 */         EquationsMapper primary = expandable.getPrimaryMapper();
/* 255 */         primary.insertEquationData(interpolator.getInterpolatedState(), yTmp);
/* 256 */         int index = 0;
/* 257 */         for (EquationsMapper secondary : expandable.getSecondaryMappers()) {
/* 258 */           secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), yTmp);
/* 259 */           index++;
/*     */         } 
/*     */ 
/*     */         
/* 263 */         computeDerivatives(d, yTmp, yDot);
/*     */ 
/*     */         
/* 266 */         for (int i = 0; i < y0.length; i++) {
/* 267 */           predictedScaled[i] = this.stepSize * yDot[i];
/*     */         }
/* 269 */         nordsieckTmp = updateHighOrderDerivativesPhase1(this.nordsieck);
/* 270 */         updateHighOrderDerivativesPhase2(this.scaled, predictedScaled, nordsieckTmp);
/*     */ 
/*     */         
/* 273 */         error = nordsieckTmp.walkInOptimizedOrder(new Corrector(y, predictedScaled, yTmp));
/*     */         
/* 275 */         if (error >= 1.0D) {
/*     */           
/* 277 */           double d1 = computeStepGrowShrinkFactor(error);
/* 278 */           hNew = filterStep(this.stepSize * d1, forward, false);
/* 279 */           interpolator.rescale(hNew);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 284 */       double stepEnd = this.stepStart + this.stepSize;
/* 285 */       computeDerivatives(stepEnd, yTmp, yDot);
/*     */ 
/*     */       
/* 288 */       double[] correctedScaled = new double[y0.length];
/* 289 */       for (int j = 0; j < y0.length; j++) {
/* 290 */         correctedScaled[j] = this.stepSize * yDot[j];
/*     */       }
/* 292 */       updateHighOrderDerivativesPhase2(predictedScaled, correctedScaled, nordsieckTmp);
/*     */ 
/*     */       
/* 295 */       System.arraycopy(yTmp, 0, y, 0, y.length);
/* 296 */       interpolator.reinitialize(stepEnd, this.stepSize, correctedScaled, nordsieckTmp);
/* 297 */       interpolator.storeTime(this.stepStart);
/* 298 */       interpolator.shift();
/* 299 */       interpolator.storeTime(stepEnd);
/* 300 */       this.stepStart = acceptStep((AbstractStepInterpolator)interpolator, y, yDot, t);
/* 301 */       this.scaled = correctedScaled;
/* 302 */       this.nordsieck = nordsieckTmp;
/*     */       
/* 304 */       if (this.isLastStep) {
/*     */         continue;
/*     */       }
/* 307 */       interpolator.storeTime(this.stepStart);
/*     */       
/* 309 */       if (this.resetOccurred) {
/*     */ 
/*     */         
/* 312 */         start(this.stepStart, y, t);
/* 313 */         interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 318 */       double factor = computeStepGrowShrinkFactor(error);
/* 319 */       double scaledH = this.stepSize * factor;
/* 320 */       double nextT = this.stepStart + scaledH;
/* 321 */       boolean nextIsLast = forward ? ((nextT >= t)) : ((nextT <= t));
/* 322 */       hNew = filterStep(scaledH, forward, nextIsLast);
/*     */       
/* 324 */       double filteredNextT = this.stepStart + hNew;
/* 325 */       boolean filteredNextIsLast = forward ? ((filteredNextT >= t)) : ((filteredNextT <= t));
/* 326 */       if (filteredNextIsLast) {
/* 327 */         hNew = t - this.stepStart;
/*     */       }
/*     */       
/* 330 */       interpolator.rescale(hNew);
/*     */     
/*     */     }
/* 333 */     while (!this.isLastStep);
/*     */ 
/*     */     
/* 336 */     equations.setTime(this.stepStart);
/* 337 */     equations.setCompleteState(y);
/*     */     
/* 339 */     resetInternalState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Corrector
/*     */     implements RealMatrixPreservingVisitor
/*     */   {
/*     */     private final double[] previous;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] scaled;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] before;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] after;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Corrector(double[] previous, double[] scaled, double[] state) {
/* 371 */       this.previous = previous;
/* 372 */       this.scaled = scaled;
/* 373 */       this.after = state;
/* 374 */       this.before = (double[])state.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/* 380 */       Arrays.fill(this.after, 0.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void visit(int row, int column, double value) {
/* 385 */       if ((row & 0x1) == 0) {
/* 386 */         this.after[column] = this.after[column] - value;
/*     */       } else {
/* 388 */         this.after[column] = this.after[column] + value;
/*     */       } 
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
/*     */     public double end() {
/* 403 */       double error = 0.0D;
/* 404 */       for (int i = 0; i < this.after.length; i++) {
/* 405 */         this.after[i] = this.after[i] + this.previous[i] + this.scaled[i];
/* 406 */         if (i < AdamsMoultonIntegrator.this.mainSetDimension) {
/* 407 */           double yScale = FastMath.max(FastMath.abs(this.previous[i]), FastMath.abs(this.after[i]));
/* 408 */           double tol = (AdamsMoultonIntegrator.this.vecAbsoluteTolerance == null) ? (AdamsMoultonIntegrator.this.scalAbsoluteTolerance + AdamsMoultonIntegrator.this.scalRelativeTolerance * yScale) : (AdamsMoultonIntegrator.this.vecAbsoluteTolerance[i] + AdamsMoultonIntegrator.this.vecRelativeTolerance[i] * yScale);
/*     */ 
/*     */           
/* 411 */           double ratio = (this.after[i] - this.before[i]) / tol;
/* 412 */           error += ratio * ratio;
/*     */         } 
/*     */       } 
/*     */       
/* 416 */       return FastMath.sqrt(error / AdamsMoultonIntegrator.this.mainSetDimension);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsMoultonIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */