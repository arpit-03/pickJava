/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ode.AbstractFieldIntegrator;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AdaptiveStepsizeFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends AbstractFieldIntegrator<T>
/*     */ {
/*     */   protected double scalAbsoluteTolerance;
/*     */   protected double scalRelativeTolerance;
/*     */   protected double[] vecAbsoluteTolerance;
/*     */   protected double[] vecRelativeTolerance;
/*     */   protected int mainSetDimension;
/*     */   private T initialStep;
/*     */   private T minStep;
/*     */   private T maxStep;
/*     */   
/*     */   public AdaptiveStepsizeFieldIntegrator(Field<T> field, String name, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/* 115 */     super(field, name);
/* 116 */     setStepSizeControl(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/* 117 */     resetInternalState();
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
/*     */   public AdaptiveStepsizeFieldIntegrator(Field<T> field, String name, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 139 */     super(field, name);
/* 140 */     setStepSizeControl(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/* 141 */     resetInternalState();
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
/* 163 */     this.minStep = (T)((RealFieldElement)getField().getZero()).add(FastMath.abs(minimalStep));
/* 164 */     this.maxStep = (T)((RealFieldElement)getField().getZero()).add(FastMath.abs(maximalStep));
/* 165 */     this.initialStep = (T)((RealFieldElement)getField().getOne()).negate();
/*     */     
/* 167 */     this.scalAbsoluteTolerance = absoluteTolerance;
/* 168 */     this.scalRelativeTolerance = relativeTolerance;
/* 169 */     this.vecAbsoluteTolerance = null;
/* 170 */     this.vecRelativeTolerance = null;
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
/* 192 */     this.minStep = (T)((RealFieldElement)getField().getZero()).add(FastMath.abs(minimalStep));
/* 193 */     this.maxStep = (T)((RealFieldElement)getField().getZero()).add(FastMath.abs(maximalStep));
/* 194 */     this.initialStep = (T)((RealFieldElement)getField().getOne()).negate();
/*     */     
/* 196 */     this.scalAbsoluteTolerance = 0.0D;
/* 197 */     this.scalRelativeTolerance = 0.0D;
/* 198 */     this.vecAbsoluteTolerance = (double[])absoluteTolerance.clone();
/* 199 */     this.vecRelativeTolerance = (double[])relativeTolerance.clone();
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
/*     */   public void setInitialStepSize(T initialStepSize) {
/* 215 */     if (((RealFieldElement)initialStepSize.subtract(this.minStep)).getReal() < 0.0D || ((RealFieldElement)initialStepSize.subtract(this.maxStep)).getReal() > 0.0D) {
/*     */       
/* 217 */       this.initialStep = (T)((RealFieldElement)getField().getOne()).negate();
/*     */     } else {
/* 219 */       this.initialStep = initialStepSize;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityChecks(FieldODEState<T> eqn, T t) throws DimensionMismatchException, NumberIsTooSmallException {
/* 228 */     super.sanityChecks(eqn, (RealFieldElement)t);
/*     */     
/* 230 */     this.mainSetDimension = eqn.getStateDimension();
/*     */     
/* 232 */     if (this.vecAbsoluteTolerance != null && this.vecAbsoluteTolerance.length != this.mainSetDimension) {
/* 233 */       throw new DimensionMismatchException(this.mainSetDimension, this.vecAbsoluteTolerance.length);
/*     */     }
/*     */     
/* 236 */     if (this.vecRelativeTolerance != null && this.vecRelativeTolerance.length != this.mainSetDimension) {
/* 237 */       throw new DimensionMismatchException(this.mainSetDimension, this.vecRelativeTolerance.length);
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
/*     */   public T initializeStep(boolean forward, int order, T[] scale, FieldODEStateAndDerivative<T> state0, FieldEquationsMapper<T> mapper) throws MaxCountExceededException, DimensionMismatchException {
/* 257 */     if (this.initialStep.getReal() > 0.0D)
/*     */     {
/* 259 */       return forward ? this.initialStep : (T)this.initialStep.negate();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 264 */     RealFieldElement[] arrayOfRealFieldElement1 = mapper.mapState((FieldODEState)state0);
/* 265 */     RealFieldElement[] arrayOfRealFieldElement2 = mapper.mapDerivative(state0);
/* 266 */     RealFieldElement realFieldElement1 = (RealFieldElement)getField().getZero();
/* 267 */     RealFieldElement realFieldElement2 = (RealFieldElement)getField().getZero();
/* 268 */     for (int j = 0; j < scale.length; j++) {
/* 269 */       RealFieldElement realFieldElement7 = (RealFieldElement)arrayOfRealFieldElement1[j].divide(scale[j]);
/* 270 */       realFieldElement1 = (RealFieldElement)realFieldElement1.add(realFieldElement7.multiply(realFieldElement7));
/* 271 */       RealFieldElement realFieldElement8 = (RealFieldElement)arrayOfRealFieldElement2[j].divide(scale[j]);
/* 272 */       realFieldElement2 = (RealFieldElement)realFieldElement2.add(realFieldElement8.multiply(realFieldElement8));
/*     */     } 
/*     */     
/* 275 */     RealFieldElement realFieldElement3 = (realFieldElement1.getReal() < 1.0E-10D || realFieldElement2.getReal() < 1.0E-10D) ? (RealFieldElement)((RealFieldElement)getField().getZero()).add(1.0E-6D) : (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.divide(realFieldElement2)).sqrt()).multiply(0.01D);
/*     */ 
/*     */     
/* 278 */     if (!forward) {
/* 279 */       realFieldElement3 = (RealFieldElement)realFieldElement3.negate();
/*     */     }
/*     */ 
/*     */     
/* 283 */     RealFieldElement[] arrayOfRealFieldElement3 = (RealFieldElement[])MathArrays.buildArray(getField(), arrayOfRealFieldElement1.length);
/* 284 */     for (int i = 0; i < arrayOfRealFieldElement1.length; i++) {
/* 285 */       arrayOfRealFieldElement3[i] = (RealFieldElement)arrayOfRealFieldElement1[i].add(arrayOfRealFieldElement2[i].multiply(realFieldElement3));
/*     */     }
/* 287 */     RealFieldElement[] arrayOfRealFieldElement4 = computeDerivatives((RealFieldElement)state0.getTime().add(realFieldElement3), arrayOfRealFieldElement3);
/*     */ 
/*     */     
/* 290 */     RealFieldElement realFieldElement4 = (RealFieldElement)getField().getZero();
/* 291 */     for (int k = 0; k < scale.length; k++) {
/* 292 */       RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)arrayOfRealFieldElement4[k].subtract(arrayOfRealFieldElement2[k])).divide(scale[k]);
/* 293 */       realFieldElement4 = (RealFieldElement)realFieldElement4.add(realFieldElement.multiply(realFieldElement));
/*     */     } 
/* 295 */     realFieldElement4 = (RealFieldElement)((RealFieldElement)realFieldElement4.sqrt()).divide(realFieldElement3);
/*     */ 
/*     */ 
/*     */     
/* 299 */     RealFieldElement realFieldElement5 = MathUtils.max((RealFieldElement)realFieldElement2.sqrt(), realFieldElement4);
/* 300 */     RealFieldElement realFieldElement6 = (realFieldElement5.getReal() < 1.0E-15D) ? MathUtils.max((RealFieldElement)((RealFieldElement)getField().getZero()).add(1.0E-6D), (RealFieldElement)((RealFieldElement)realFieldElement3.abs()).multiply(0.001D)) : (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement5.multiply(100)).reciprocal()).pow(1.0D / order);
/*     */ 
/*     */     
/* 303 */     realFieldElement3 = MathUtils.min((RealFieldElement)((RealFieldElement)realFieldElement3.abs()).multiply(100), realFieldElement6);
/* 304 */     realFieldElement3 = MathUtils.max(realFieldElement3, (RealFieldElement)((RealFieldElement)state0.getTime().abs()).multiply(1.0E-12D));
/* 305 */     realFieldElement3 = MathUtils.max((RealFieldElement)this.minStep, MathUtils.min((RealFieldElement)this.maxStep, realFieldElement3));
/* 306 */     if (!forward) {
/* 307 */       realFieldElement3 = (RealFieldElement)realFieldElement3.negate();
/*     */     }
/*     */     
/* 310 */     return (T)realFieldElement3;
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
/*     */   protected T filterStep(T h, boolean forward, boolean acceptSmall) throws NumberIsTooSmallException {
/*     */     RealFieldElement realFieldElement;
/* 326 */     T filteredH = h;
/* 327 */     if (((RealFieldElement)((RealFieldElement)h.abs()).subtract(this.minStep)).getReal() < 0.0D) {
/* 328 */       if (acceptSmall) {
/* 329 */         filteredH = forward ? this.minStep : (T)this.minStep.negate();
/*     */       } else {
/* 331 */         throw new NumberIsTooSmallException(LocalizedFormats.MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION, Double.valueOf(((RealFieldElement)h.abs()).getReal()), Double.valueOf(this.minStep.getReal()), true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 336 */     if (((RealFieldElement)filteredH.subtract(this.maxStep)).getReal() > 0.0D) {
/* 337 */       filteredH = this.maxStep;
/* 338 */     } else if (((RealFieldElement)filteredH.add(this.maxStep)).getReal() < 0.0D) {
/* 339 */       realFieldElement = (RealFieldElement)this.maxStep.negate();
/*     */     } 
/*     */     
/* 342 */     return (T)realFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetInternalState() {
/* 348 */     setStepStart(null);
/* 349 */     setStepSize((RealFieldElement)((RealFieldElement)this.minStep.multiply(this.maxStep)).sqrt());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMinStep() {
/* 356 */     return this.minStep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMaxStep() {
/* 363 */     return this.maxStep;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdaptiveStepsizeFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */