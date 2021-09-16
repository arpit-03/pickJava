/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.linear.FieldMatrix;
/*     */ import org.apache.commons.math3.ode.FieldExpandableODE;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdamsBashforthFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends AdamsFieldIntegrator<T>
/*     */ {
/*     */   private static final String METHOD_NAME = "Adams-Bashforth";
/*     */   
/*     */   public AdamsBashforthFieldIntegrator(Field<T> field, int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/* 171 */     super(field, "Adams-Bashforth", nSteps, nSteps, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
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
/*     */   public AdamsBashforthFieldIntegrator(Field<T> field, int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
/* 194 */     super(field, "Adams-Bashforth", nSteps, nSteps, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
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
/*     */   private T errorEstimation(T[] previousState, T[] predictedState, T[] predictedScaled, FieldMatrix<T> predictedNordsieck) {
/* 214 */     RealFieldElement realFieldElement = (RealFieldElement)getField().getZero();
/* 215 */     for (int i = 0; i < this.mainSetDimension; i++) {
/* 216 */       RealFieldElement realFieldElement1 = (RealFieldElement)predictedState[i].abs();
/* 217 */       RealFieldElement realFieldElement2 = (this.vecAbsoluteTolerance == null) ? (RealFieldElement)((RealFieldElement)realFieldElement1.multiply(this.scalRelativeTolerance)).add(this.scalAbsoluteTolerance) : (RealFieldElement)((RealFieldElement)realFieldElement1.multiply(this.vecRelativeTolerance[i])).add(this.vecAbsoluteTolerance[i]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       RealFieldElement realFieldElement3 = (RealFieldElement)getField().getZero();
/* 224 */       int sign = (predictedNordsieck.getRowDimension() % 2 == 0) ? -1 : 1;
/* 225 */       for (int k = predictedNordsieck.getRowDimension() - 1; k >= 0; k--) {
/* 226 */         realFieldElement3 = (RealFieldElement)realFieldElement3.add(((RealFieldElement)predictedNordsieck.getEntry(k, i)).multiply(sign));
/* 227 */         sign = -sign;
/*     */       } 
/* 229 */       realFieldElement3 = (RealFieldElement)realFieldElement3.subtract(predictedScaled[i]);
/*     */       
/* 231 */       RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)((RealFieldElement)predictedState[i].subtract(previousState[i])).add(realFieldElement3)).divide(realFieldElement2);
/* 232 */       realFieldElement = (RealFieldElement)realFieldElement.add(realFieldElement4.multiply(realFieldElement4));
/*     */     } 
/*     */ 
/*     */     
/* 236 */     return (T)((RealFieldElement)realFieldElement.divide(this.mainSetDimension)).sqrt();
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
/*     */   public FieldODEStateAndDerivative<T> integrate(FieldExpandableODE<T> equations, FieldODEState<T> initialState, T finalTime) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 248 */     sanityChecks(initialState, (RealFieldElement)finalTime);
/* 249 */     RealFieldElement realFieldElement = initialState.getTime();
/* 250 */     RealFieldElement[] arrayOfRealFieldElement = equations.getMapper().mapState(initialState);
/* 251 */     setStepStart(initIntegration(equations, realFieldElement, arrayOfRealFieldElement, (RealFieldElement)finalTime));
/* 252 */     boolean forward = (((RealFieldElement)finalTime.subtract(initialState.getTime())).getReal() > 0.0D);
/*     */ 
/*     */     
/* 255 */     start(equations, (FieldODEState)getStepStart(), (RealFieldElement)finalTime);
/*     */ 
/*     */     
/* 258 */     FieldODEStateAndDerivative<T> stepStart = getStepStart();
/* 259 */     FieldODEStateAndDerivative<T> stepEnd = AdamsFieldStepInterpolator.taylor(stepStart, (T)stepStart.getTime().add(getStepSize()), (T)getStepSize(), (T[])this.scaled, this.nordsieck);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     setIsLastStep(false);
/*     */     do {
/*     */       RealFieldElement[] arrayOfRealFieldElement1;
/* 268 */       T[] predictedY = null;
/* 269 */       RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])MathArrays.buildArray(getField(), arrayOfRealFieldElement.length);
/* 270 */       Array2DRowFieldMatrix<T> predictedNordsieck = null;
/* 271 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)getField().getZero()).add(10.0D);
/* 272 */       while (((RealFieldElement)realFieldElement1.subtract(1.0D)).getReal() >= 0.0D) {
/*     */ 
/*     */         
/* 275 */         arrayOfRealFieldElement1 = stepEnd.getState();
/*     */ 
/*     */         
/* 278 */         RealFieldElement[] arrayOfRealFieldElement3 = computeDerivatives(stepEnd.getTime(), arrayOfRealFieldElement1);
/*     */ 
/*     */         
/* 281 */         for (int j = 0; j < arrayOfRealFieldElement2.length; j++) {
/* 282 */           arrayOfRealFieldElement2[j] = (RealFieldElement)getStepSize().multiply(arrayOfRealFieldElement3[j]);
/*     */         }
/* 284 */         predictedNordsieck = updateHighOrderDerivativesPhase1(this.nordsieck);
/* 285 */         updateHighOrderDerivativesPhase2((T[])this.scaled, (T[])arrayOfRealFieldElement2, predictedNordsieck);
/*     */ 
/*     */         
/* 288 */         realFieldElement1 = (RealFieldElement)errorEstimation((T[])arrayOfRealFieldElement, (T[])arrayOfRealFieldElement1, (T[])arrayOfRealFieldElement2, (FieldMatrix<T>)predictedNordsieck);
/*     */         
/* 290 */         if (((RealFieldElement)realFieldElement1.subtract(1.0D)).getReal() >= 0.0D) {
/*     */           
/* 292 */           RealFieldElement realFieldElement7 = computeStepGrowShrinkFactor(realFieldElement1);
/* 293 */           rescale(filterStep((RealFieldElement)getStepSize().multiply(realFieldElement7), forward, false));
/* 294 */           stepEnd = AdamsFieldStepInterpolator.taylor(getStepStart(), (T)getStepStart().getTime().add(getStepSize()), (T)getStepSize(), (T[])this.scaled, this.nordsieck);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       setStepStart(acceptStep(new AdamsFieldStepInterpolator<RealFieldElement>(getStepSize(), stepEnd, arrayOfRealFieldElement2, predictedNordsieck, forward, getStepStart(), stepEnd, equations.getMapper()), (RealFieldElement)finalTime));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 309 */       this.scaled = arrayOfRealFieldElement2;
/* 310 */       this.nordsieck = predictedNordsieck;
/*     */       
/* 312 */       if (isLastStep())
/*     */         continue; 
/* 314 */       System.arraycopy(arrayOfRealFieldElement1, 0, arrayOfRealFieldElement, 0, arrayOfRealFieldElement.length);
/*     */       
/* 316 */       if (resetOccurred())
/*     */       {
/*     */         
/* 319 */         start(equations, (FieldODEState)getStepStart(), (RealFieldElement)finalTime);
/*     */       }
/*     */ 
/*     */       
/* 323 */       RealFieldElement realFieldElement2 = computeStepGrowShrinkFactor(realFieldElement1);
/* 324 */       RealFieldElement realFieldElement3 = (RealFieldElement)getStepSize().multiply(realFieldElement2);
/* 325 */       RealFieldElement realFieldElement4 = (RealFieldElement)getStepStart().getTime().add(realFieldElement3);
/* 326 */       boolean nextIsLast = forward ? ((((RealFieldElement)realFieldElement4.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement4.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 329 */       RealFieldElement realFieldElement5 = filterStep(realFieldElement3, forward, nextIsLast);
/*     */       
/* 331 */       RealFieldElement realFieldElement6 = (RealFieldElement)getStepStart().getTime().add(realFieldElement5);
/* 332 */       boolean filteredNextIsLast = forward ? ((((RealFieldElement)realFieldElement6.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement6.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 335 */       if (filteredNextIsLast) {
/* 336 */         realFieldElement5 = (RealFieldElement)finalTime.subtract(getStepStart().getTime());
/*     */       }
/*     */       
/* 339 */       rescale(realFieldElement5);
/* 340 */       stepEnd = AdamsFieldStepInterpolator.taylor(getStepStart(), (T)getStepStart().getTime().add(getStepSize()), (T)getStepSize(), (T[])this.scaled, this.nordsieck);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 345 */     while (!isLastStep());
/*     */     
/* 347 */     FieldODEStateAndDerivative<T> finalState = getStepStart();
/* 348 */     setStepStart(null);
/* 349 */     setStepSize(null);
/* 350 */     return finalState;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsBashforthFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */