/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.ode.nonstiff.AdaptiveStepsizeFieldIntegrator;
/*     */ import org.apache.commons.math3.ode.nonstiff.DormandPrince853FieldIntegrator;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepHandler;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepInterpolator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MultistepFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends AdaptiveStepsizeFieldIntegrator<T>
/*     */ {
/*     */   protected T[] scaled;
/*     */   protected Array2DRowFieldMatrix<T> nordsieck;
/*     */   private FirstOrderFieldIntegrator<T> starter;
/*     */   private final int nSteps;
/*     */   private double exp;
/*     */   private double safety;
/*     */   private double minReduction;
/*     */   private double maxGrowth;
/*     */   
/*     */   protected MultistepFieldIntegrator(Field<T> field, String name, int nSteps, int order, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/* 125 */     super(field, name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/* 127 */     if (nSteps < 2) {
/* 128 */       throw new NumberIsTooSmallException(LocalizedFormats.INTEGRATION_METHOD_NEEDS_AT_LEAST_TWO_PREVIOUS_POINTS, Integer.valueOf(nSteps), Integer.valueOf(2), true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     this.starter = (FirstOrderFieldIntegrator<T>)new DormandPrince853FieldIntegrator(field, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */ 
/*     */     
/* 136 */     this.nSteps = nSteps;
/*     */     
/* 138 */     this.exp = -1.0D / order;
/*     */ 
/*     */     
/* 141 */     setSafety(0.9D);
/* 142 */     setMinReduction(0.2D);
/* 143 */     setMaxGrowth(FastMath.pow(2.0D, -this.exp));
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
/*     */   protected MultistepFieldIntegrator(Field<T> field, String name, int nSteps, int order, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 172 */     super(field, name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/* 173 */     this.starter = (FirstOrderFieldIntegrator<T>)new DormandPrince853FieldIntegrator(field, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */ 
/*     */     
/* 176 */     this.nSteps = nSteps;
/*     */     
/* 178 */     this.exp = -1.0D / order;
/*     */ 
/*     */     
/* 181 */     setSafety(0.9D);
/* 182 */     setMinReduction(0.2D);
/* 183 */     setMaxGrowth(FastMath.pow(2.0D, -this.exp));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FirstOrderFieldIntegrator<T> getStarterIntegrator() {
/* 192 */     return this.starter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStarterIntegrator(FirstOrderFieldIntegrator<T> starterIntegrator) {
/* 203 */     this.starter = starterIntegrator;
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
/*     */   protected void start(FieldExpandableODE<T> equations, FieldODEState<T> initialState, T t) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
/* 231 */     this.starter.clearEventHandlers();
/* 232 */     this.starter.clearStepHandlers();
/*     */ 
/*     */     
/* 235 */     this.starter.addStepHandler(new FieldNordsieckInitializer(equations.getMapper(), (this.nSteps + 3) / 2));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 240 */       this.starter.integrate(equations, initialState, t);
/*     */ 
/*     */       
/* 243 */       throw new MathIllegalStateException(LocalizedFormats.MULTISTEP_STARTER_STOPPED_EARLY, new Object[0]);
/*     */     }
/* 245 */     catch (InitializationCompletedMarkerException icme) {
/*     */ 
/*     */ 
/*     */       
/* 249 */       getEvaluationsCounter().increment(this.starter.getEvaluations());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       this.starter.clearStepHandlers();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Array2DRowFieldMatrix<T> initializeHighOrderDerivatives(T paramT, T[] paramArrayOfT, T[][] paramArrayOfT1, T[][] paramArrayOfT2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinReduction() {
/* 274 */     return this.minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinReduction(double minReduction) {
/* 281 */     this.minReduction = minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxGrowth() {
/* 288 */     return this.maxGrowth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxGrowth(double maxGrowth) {
/* 295 */     this.maxGrowth = maxGrowth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSafety() {
/* 302 */     return this.safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSafety(double safety) {
/* 309 */     this.safety = safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNSteps() {
/* 316 */     return this.nSteps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rescale(T newStepSize) {
/* 326 */     RealFieldElement realFieldElement1 = (RealFieldElement)newStepSize.divide(getStepSize());
/* 327 */     for (int i = 0; i < this.scaled.length; i++) {
/* 328 */       this.scaled[i] = (T)this.scaled[i].multiply(realFieldElement1);
/*     */     }
/*     */     
/* 331 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])this.nordsieck.getDataRef();
/* 332 */     RealFieldElement realFieldElement2 = realFieldElement1;
/* 333 */     for (int j = 0; j < arrayOfRealFieldElement.length; j++) {
/* 334 */       realFieldElement2 = (RealFieldElement)realFieldElement2.multiply(realFieldElement1);
/* 335 */       RealFieldElement[] arrayOfRealFieldElement1 = arrayOfRealFieldElement[j];
/* 336 */       for (int k = 0; k < arrayOfRealFieldElement1.length; k++) {
/* 337 */         arrayOfRealFieldElement1[k] = (RealFieldElement)arrayOfRealFieldElement1[k].multiply(realFieldElement2);
/*     */       }
/*     */     } 
/*     */     
/* 341 */     setStepSize((RealFieldElement)newStepSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T computeStepGrowShrinkFactor(T error) {
/* 351 */     return (T)MathUtils.min((RealFieldElement)((RealFieldElement)error.getField().getZero()).add(this.maxGrowth), MathUtils.max((RealFieldElement)((RealFieldElement)error.getField().getZero()).add(this.minReduction), (RealFieldElement)((RealFieldElement)error.pow(this.exp)).multiply(this.safety)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class FieldNordsieckInitializer
/*     */     implements FieldStepHandler<T>
/*     */   {
/*     */     private final FieldEquationsMapper<T> mapper;
/*     */ 
/*     */ 
/*     */     
/*     */     private int count;
/*     */ 
/*     */ 
/*     */     
/*     */     private FieldODEStateAndDerivative<T> savedStart;
/*     */ 
/*     */ 
/*     */     
/*     */     private final T[] t;
/*     */ 
/*     */     
/*     */     private final T[][] y;
/*     */ 
/*     */     
/*     */     private final T[][] yDot;
/*     */ 
/*     */ 
/*     */     
/*     */     FieldNordsieckInitializer(FieldEquationsMapper<T> mapper, int nbStartPoints) {
/* 383 */       this.mapper = mapper;
/* 384 */       this.count = 0;
/* 385 */       this.t = (T[])MathArrays.buildArray(MultistepFieldIntegrator.this.getField(), nbStartPoints);
/* 386 */       this.y = (T[][])MathArrays.buildArray(MultistepFieldIntegrator.this.getField(), nbStartPoints, -1);
/* 387 */       this.yDot = (T[][])MathArrays.buildArray(MultistepFieldIntegrator.this.getField(), nbStartPoints, -1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleStep(FieldStepInterpolator<T> interpolator, boolean isLast) throws MaxCountExceededException {
/* 395 */       if (this.count == 0) {
/*     */         
/* 397 */         FieldODEStateAndDerivative<T> prev = interpolator.getPreviousState();
/* 398 */         this.savedStart = prev;
/* 399 */         this.t[this.count] = prev.getTime();
/* 400 */         this.y[this.count] = this.mapper.mapState(prev);
/* 401 */         this.yDot[this.count] = this.mapper.mapDerivative(prev);
/*     */       } 
/*     */ 
/*     */       
/* 405 */       this.count++;
/* 406 */       FieldODEStateAndDerivative<T> curr = interpolator.getCurrentState();
/* 407 */       this.t[this.count] = curr.getTime();
/* 408 */       this.y[this.count] = this.mapper.mapState(curr);
/* 409 */       this.yDot[this.count] = this.mapper.mapDerivative(curr);
/*     */       
/* 411 */       if (this.count == this.t.length - 1) {
/*     */ 
/*     */         
/* 414 */         MultistepFieldIntegrator.this.setStepSize((RealFieldElement)((RealFieldElement)this.t[this.t.length - 1].subtract(this.t[0])).divide((this.t.length - 1)));
/*     */ 
/*     */         
/* 417 */         MultistepFieldIntegrator.this.scaled = (T[])MathArrays.buildArray(MultistepFieldIntegrator.this.getField(), (this.yDot[0]).length);
/* 418 */         for (int j = 0; j < MultistepFieldIntegrator.this.scaled.length; j++) {
/* 419 */           MultistepFieldIntegrator.this.scaled[j] = (T)this.yDot[0][j].multiply(MultistepFieldIntegrator.this.getStepSize());
/*     */         }
/*     */ 
/*     */         
/* 423 */         MultistepFieldIntegrator.this.nordsieck = MultistepFieldIntegrator.this.initializeHighOrderDerivatives(MultistepFieldIntegrator.this.getStepSize(), (RealFieldElement[])this.t, (RealFieldElement[][])this.y, (RealFieldElement[][])this.yDot);
/*     */ 
/*     */         
/* 426 */         MultistepFieldIntegrator.this.setStepStart(this.savedStart);
/* 427 */         throw new MultistepFieldIntegrator.InitializationCompletedMarkerException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(FieldODEStateAndDerivative<T> initialState, T finalTime) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class InitializationCompletedMarkerException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = -1914085471038046418L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     InitializationCompletedMarkerException() {
/* 449 */       super((Throwable)null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/MultistepFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */