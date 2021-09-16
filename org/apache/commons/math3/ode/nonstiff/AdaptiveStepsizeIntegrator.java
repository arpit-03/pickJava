/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ode.AbstractIntegrator;
/*     */ import org.apache.commons.math3.ode.ExpandableStatefulODE;
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
/*     */ public abstract class AdaptiveStepsizeIntegrator
/*     */   extends AbstractIntegrator
/*     */ {
/*     */   protected double scalAbsoluteTolerance;
/*     */   protected double scalRelativeTolerance;
/*     */   protected double[] vecAbsoluteTolerance;
/*     */   protected double[] vecRelativeTolerance;
/*     */   protected int mainSetDimension;
/*     */   private double initialStep;
/*     */   private double minStep;
/*     */   private double maxStep;
/*     */   
/*     */   public AdaptiveStepsizeIntegrator(String name, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/* 109 */     super(name);
/* 110 */     setStepSizeControl(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/* 111 */     resetInternalState();
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
/*     */   public AdaptiveStepsizeIntegrator(String name, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 132 */     super(name);
/* 133 */     setStepSizeControl(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/* 134 */     resetInternalState();
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
/*     */   public void setStepSizeControl(double minimalStep, double maximalStep, double absoluteTolerance, double relativeTolerance) {
/* 156 */     this.minStep = FastMath.abs(minimalStep);
/* 157 */     this.maxStep = FastMath.abs(maximalStep);
/* 158 */     this.initialStep = -1.0D;
/*     */     
/* 160 */     this.scalAbsoluteTolerance = absoluteTolerance;
/* 161 */     this.scalRelativeTolerance = relativeTolerance;
/* 162 */     this.vecAbsoluteTolerance = null;
/* 163 */     this.vecRelativeTolerance = null;
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
/*     */   public void setStepSizeControl(double minimalStep, double maximalStep, double[] absoluteTolerance, double[] relativeTolerance) {
/* 185 */     this.minStep = FastMath.abs(minimalStep);
/* 186 */     this.maxStep = FastMath.abs(maximalStep);
/* 187 */     this.initialStep = -1.0D;
/*     */     
/* 189 */     this.scalAbsoluteTolerance = 0.0D;
/* 190 */     this.scalRelativeTolerance = 0.0D;
/* 191 */     this.vecAbsoluteTolerance = (double[])absoluteTolerance.clone();
/* 192 */     this.vecRelativeTolerance = (double[])relativeTolerance.clone();
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
/*     */   public void setInitialStepSize(double initialStepSize) {
/* 208 */     if (initialStepSize < this.minStep || initialStepSize > this.maxStep) {
/* 209 */       this.initialStep = -1.0D;
/*     */     } else {
/* 211 */       this.initialStep = initialStepSize;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityChecks(ExpandableStatefulODE equations, double t) throws DimensionMismatchException, NumberIsTooSmallException {
/* 220 */     super.sanityChecks(equations, t);
/*     */     
/* 222 */     this.mainSetDimension = equations.getPrimaryMapper().getDimension();
/*     */     
/* 224 */     if (this.vecAbsoluteTolerance != null && this.vecAbsoluteTolerance.length != this.mainSetDimension) {
/* 225 */       throw new DimensionMismatchException(this.mainSetDimension, this.vecAbsoluteTolerance.length);
/*     */     }
/*     */     
/* 228 */     if (this.vecRelativeTolerance != null && this.vecRelativeTolerance.length != this.mainSetDimension) {
/* 229 */       throw new DimensionMismatchException(this.mainSetDimension, this.vecRelativeTolerance.length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double initializeStep(boolean forward, int order, double[] scale, double t0, double[] y0, double[] yDot0, double[] y1, double[] yDot1) throws MaxCountExceededException, DimensionMismatchException {
/* 252 */     if (this.initialStep > 0.0D)
/*     */     {
/* 254 */       return forward ? this.initialStep : -this.initialStep;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     double yOnScale2 = 0.0D;
/* 261 */     double yDotOnScale2 = 0.0D;
/* 262 */     for (int j = 0; j < scale.length; j++) {
/* 263 */       double ratio = y0[j] / scale[j];
/* 264 */       yOnScale2 += ratio * ratio;
/* 265 */       ratio = yDot0[j] / scale[j];
/* 266 */       yDotOnScale2 += ratio * ratio;
/*     */     } 
/*     */     
/* 269 */     double h = (yOnScale2 < 1.0E-10D || yDotOnScale2 < 1.0E-10D) ? 1.0E-6D : (0.01D * FastMath.sqrt(yOnScale2 / yDotOnScale2));
/*     */     
/* 271 */     if (!forward) {
/* 272 */       h = -h;
/*     */     }
/*     */ 
/*     */     
/* 276 */     for (int i = 0; i < y0.length; i++) {
/* 277 */       y1[i] = y0[i] + h * yDot0[i];
/*     */     }
/* 279 */     computeDerivatives(t0 + h, y1, yDot1);
/*     */ 
/*     */     
/* 282 */     double yDDotOnScale = 0.0D;
/* 283 */     for (int k = 0; k < scale.length; k++) {
/* 284 */       double ratio = (yDot1[k] - yDot0[k]) / scale[k];
/* 285 */       yDDotOnScale += ratio * ratio;
/*     */     } 
/* 287 */     yDDotOnScale = FastMath.sqrt(yDDotOnScale) / h;
/*     */ 
/*     */ 
/*     */     
/* 291 */     double maxInv2 = FastMath.max(FastMath.sqrt(yDotOnScale2), yDDotOnScale);
/* 292 */     double h1 = (maxInv2 < 1.0E-15D) ? FastMath.max(1.0E-6D, 0.001D * FastMath.abs(h)) : FastMath.pow(0.01D / maxInv2, 1.0D / order);
/*     */ 
/*     */     
/* 295 */     h = FastMath.min(100.0D * FastMath.abs(h), h1);
/* 296 */     h = FastMath.max(h, 1.0E-12D * FastMath.abs(t0));
/* 297 */     if (h < getMinStep()) {
/* 298 */       h = getMinStep();
/*     */     }
/* 300 */     if (h > getMaxStep()) {
/* 301 */       h = getMaxStep();
/*     */     }
/* 303 */     if (!forward) {
/* 304 */       h = -h;
/*     */     }
/*     */     
/* 307 */     return h;
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
/*     */   protected double filterStep(double h, boolean forward, boolean acceptSmall) throws NumberIsTooSmallException {
/* 323 */     double filteredH = h;
/* 324 */     if (FastMath.abs(h) < this.minStep) {
/* 325 */       if (acceptSmall) {
/* 326 */         filteredH = forward ? this.minStep : -this.minStep;
/*     */       } else {
/* 328 */         throw new NumberIsTooSmallException(LocalizedFormats.MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION, Double.valueOf(FastMath.abs(h)), Double.valueOf(this.minStep), true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 333 */     if (filteredH > this.maxStep) {
/* 334 */       filteredH = this.maxStep;
/* 335 */     } else if (filteredH < -this.maxStep) {
/* 336 */       filteredH = -this.maxStep;
/*     */     } 
/*     */     
/* 339 */     return filteredH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void integrate(ExpandableStatefulODE paramExpandableStatefulODE, double paramDouble) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCurrentStepStart() {
/* 352 */     return this.stepStart;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resetInternalState() {
/* 357 */     this.stepStart = Double.NaN;
/* 358 */     this.stepSize = FastMath.sqrt(this.minStep * this.maxStep);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinStep() {
/* 365 */     return this.minStep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxStep() {
/* 372 */     return this.maxStep;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdaptiveStepsizeIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */