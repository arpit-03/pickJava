/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.linear.FieldMatrixPreservingVisitor;
/*     */ import org.apache.commons.math3.ode.FieldExpandableODE;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdamsMoultonFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends AdamsFieldIntegrator<T>
/*     */ {
/*     */   private static final String METHOD_NAME = "Adams-Moulton";
/*     */   
/*     */   public AdamsMoultonFieldIntegrator(Field<T> field, int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/* 187 */     super(field, "Adams-Moulton", nSteps, nSteps + 1, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
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
/*     */   public AdamsMoultonFieldIntegrator(Field<T> field, int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
/* 210 */     super(field, "Adams-Moulton", nSteps, nSteps + 1, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
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
/* 222 */     sanityChecks(initialState, (RealFieldElement)finalTime);
/* 223 */     RealFieldElement realFieldElement = initialState.getTime();
/* 224 */     RealFieldElement[] arrayOfRealFieldElement = equations.getMapper().mapState(initialState);
/* 225 */     setStepStart(initIntegration(equations, realFieldElement, arrayOfRealFieldElement, (RealFieldElement)finalTime));
/* 226 */     boolean forward = (((RealFieldElement)finalTime.subtract(initialState.getTime())).getReal() > 0.0D);
/*     */ 
/*     */     
/* 229 */     start(equations, (FieldODEState)getStepStart(), (RealFieldElement)finalTime);
/*     */ 
/*     */     
/* 232 */     FieldODEStateAndDerivative<T> stepStart = getStepStart();
/* 233 */     FieldODEStateAndDerivative<T> stepEnd = AdamsFieldStepInterpolator.taylor(stepStart, (T)stepStart.getTime().add(getStepSize()), (T)getStepSize(), (T[])this.scaled, this.nordsieck);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     setIsLastStep(false);
/*     */     do {
/*     */       RealFieldElement[] arrayOfRealFieldElement1;
/* 242 */       T[] predictedY = null;
/* 243 */       RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])MathArrays.buildArray(getField(), arrayOfRealFieldElement.length);
/* 244 */       Array2DRowFieldMatrix<T> predictedNordsieck = null;
/* 245 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)getField().getZero()).add(10.0D);
/* 246 */       while (((RealFieldElement)realFieldElement1.subtract(1.0D)).getReal() >= 0.0D) {
/*     */ 
/*     */         
/* 249 */         arrayOfRealFieldElement1 = stepEnd.getState();
/*     */ 
/*     */         
/* 252 */         RealFieldElement[] arrayOfRealFieldElement5 = computeDerivatives(stepEnd.getTime(), arrayOfRealFieldElement1);
/*     */ 
/*     */         
/* 255 */         for (int i = 0; i < arrayOfRealFieldElement2.length; i++) {
/* 256 */           arrayOfRealFieldElement2[i] = (RealFieldElement)getStepSize().multiply(arrayOfRealFieldElement5[i]);
/*     */         }
/* 258 */         predictedNordsieck = updateHighOrderDerivativesPhase1(this.nordsieck);
/* 259 */         updateHighOrderDerivativesPhase2((T[])this.scaled, (T[])arrayOfRealFieldElement2, predictedNordsieck);
/*     */ 
/*     */         
/* 262 */         realFieldElement1 = (RealFieldElement)predictedNordsieck.walkInOptimizedOrder(new Corrector((T[])arrayOfRealFieldElement, (T[])arrayOfRealFieldElement2, (T[])arrayOfRealFieldElement1));
/*     */         
/* 264 */         if (((RealFieldElement)realFieldElement1.subtract(1.0D)).getReal() >= 0.0D) {
/*     */           
/* 266 */           RealFieldElement realFieldElement7 = computeStepGrowShrinkFactor(realFieldElement1);
/* 267 */           rescale(filterStep((RealFieldElement)getStepSize().multiply(realFieldElement7), forward, false));
/* 268 */           stepEnd = AdamsFieldStepInterpolator.taylor(getStepStart(), (T)getStepStart().getTime().add(getStepSize()), (T)getStepSize(), (T[])this.scaled, this.nordsieck);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 277 */       RealFieldElement[] arrayOfRealFieldElement3 = computeDerivatives(stepEnd.getTime(), arrayOfRealFieldElement1);
/*     */ 
/*     */       
/* 280 */       RealFieldElement[] arrayOfRealFieldElement4 = (RealFieldElement[])MathArrays.buildArray(getField(), arrayOfRealFieldElement.length);
/* 281 */       for (int j = 0; j < arrayOfRealFieldElement4.length; j++) {
/* 282 */         arrayOfRealFieldElement4[j] = (RealFieldElement)getStepSize().multiply(arrayOfRealFieldElement3[j]);
/*     */       }
/* 284 */       updateHighOrderDerivativesPhase2((T[])arrayOfRealFieldElement2, (T[])arrayOfRealFieldElement4, predictedNordsieck);
/*     */ 
/*     */       
/* 287 */       stepEnd = new FieldODEStateAndDerivative(stepEnd.getTime(), arrayOfRealFieldElement1, arrayOfRealFieldElement3);
/* 288 */       setStepStart(acceptStep(new AdamsFieldStepInterpolator<RealFieldElement>(getStepSize(), stepEnd, arrayOfRealFieldElement4, predictedNordsieck, forward, getStepStart(), stepEnd, equations.getMapper()), (RealFieldElement)finalTime));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 293 */       this.scaled = arrayOfRealFieldElement4;
/* 294 */       this.nordsieck = predictedNordsieck;
/*     */       
/* 296 */       if (isLastStep())
/*     */         continue; 
/* 298 */       System.arraycopy(arrayOfRealFieldElement1, 0, arrayOfRealFieldElement, 0, arrayOfRealFieldElement.length);
/*     */       
/* 300 */       if (resetOccurred())
/*     */       {
/*     */         
/* 303 */         start(equations, (FieldODEState)getStepStart(), (RealFieldElement)finalTime);
/*     */       }
/*     */ 
/*     */       
/* 307 */       RealFieldElement realFieldElement2 = computeStepGrowShrinkFactor(realFieldElement1);
/* 308 */       RealFieldElement realFieldElement3 = (RealFieldElement)getStepSize().multiply(realFieldElement2);
/* 309 */       RealFieldElement realFieldElement4 = (RealFieldElement)getStepStart().getTime().add(realFieldElement3);
/* 310 */       boolean nextIsLast = forward ? ((((RealFieldElement)realFieldElement4.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement4.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 313 */       RealFieldElement realFieldElement5 = filterStep(realFieldElement3, forward, nextIsLast);
/*     */       
/* 315 */       RealFieldElement realFieldElement6 = (RealFieldElement)getStepStart().getTime().add(realFieldElement5);
/* 316 */       boolean filteredNextIsLast = forward ? ((((RealFieldElement)realFieldElement6.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement6.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 319 */       if (filteredNextIsLast) {
/* 320 */         realFieldElement5 = (RealFieldElement)finalTime.subtract(getStepStart().getTime());
/*     */       }
/*     */       
/* 323 */       rescale(realFieldElement5);
/* 324 */       stepEnd = AdamsFieldStepInterpolator.taylor(getStepStart(), (T)getStepStart().getTime().add(getStepSize()), (T)getStepSize(), (T[])this.scaled, this.nordsieck);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 329 */     while (!isLastStep());
/*     */     
/* 331 */     FieldODEStateAndDerivative<T> finalState = getStepStart();
/* 332 */     setStepStart(null);
/* 333 */     setStepSize(null);
/* 334 */     return finalState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Corrector
/*     */     implements FieldMatrixPreservingVisitor<T>
/*     */   {
/*     */     private final T[] previous;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final T[] scaled;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final T[] before;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final T[] after;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Corrector(T[] previous, T[] scaled, T[] state) {
/* 366 */       this.previous = previous;
/* 367 */       this.scaled = scaled;
/* 368 */       this.after = state;
/* 369 */       this.before = (T[])state.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/* 375 */       Arrays.fill((Object[])this.after, AdamsMoultonFieldIntegrator.this.getField().getZero());
/*     */     }
/*     */ 
/*     */     
/*     */     public void visit(int row, int column, T value) {
/* 380 */       if ((row & 0x1) == 0) {
/* 381 */         this.after[column] = (T)this.after[column].subtract(value);
/*     */       } else {
/* 383 */         this.after[column] = (T)this.after[column].add(value);
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
/*     */     public T end() {
/* 398 */       RealFieldElement realFieldElement = (RealFieldElement)AdamsMoultonFieldIntegrator.this.getField().getZero();
/* 399 */       for (int i = 0; i < this.after.length; i++) {
/* 400 */         this.after[i] = (T)this.after[i].add(this.previous[i].add(this.scaled[i]));
/* 401 */         if (i < AdamsMoultonFieldIntegrator.this.mainSetDimension) {
/* 402 */           RealFieldElement realFieldElement1 = MathUtils.max((RealFieldElement)this.previous[i].abs(), (RealFieldElement)this.after[i].abs());
/* 403 */           RealFieldElement realFieldElement2 = (AdamsMoultonFieldIntegrator.this.vecAbsoluteTolerance == null) ? (RealFieldElement)((RealFieldElement)realFieldElement1.multiply(AdamsMoultonFieldIntegrator.this.scalRelativeTolerance)).add(AdamsMoultonFieldIntegrator.this.scalAbsoluteTolerance) : (RealFieldElement)((RealFieldElement)realFieldElement1.multiply(AdamsMoultonFieldIntegrator.this.vecRelativeTolerance[i])).add(AdamsMoultonFieldIntegrator.this.vecAbsoluteTolerance[i]);
/*     */ 
/*     */           
/* 406 */           RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)this.after[i].subtract(this.before[i])).divide(realFieldElement2);
/* 407 */           realFieldElement = (RealFieldElement)realFieldElement.add(realFieldElement3.multiply(realFieldElement3));
/*     */         } 
/*     */       } 
/*     */       
/* 411 */       return (T)((RealFieldElement)realFieldElement.divide(AdamsMoultonFieldIntegrator.this.mainSetDimension)).sqrt();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsMoultonFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */