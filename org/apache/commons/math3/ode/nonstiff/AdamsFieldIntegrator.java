/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.ode.FieldExpandableODE;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ import org.apache.commons.math3.ode.MultistepFieldIntegrator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AdamsFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends MultistepFieldIntegrator<T>
/*     */ {
/*     */   private final AdamsNordsieckFieldTransformer<T> transformer;
/*     */   
/*     */   public AdamsFieldIntegrator(Field<T> field, String name, int nSteps, int order, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
/*  65 */     super(field, name, nSteps, order, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/*  67 */     this.transformer = AdamsNordsieckFieldTransformer.getInstance(field, nSteps);
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
/*     */   public AdamsFieldIntegrator(Field<T> field, String name, int nSteps, int order, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
/*  92 */     super(field, name, nSteps, order, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/*  94 */     this.transformer = AdamsNordsieckFieldTransformer.getInstance(field, nSteps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract FieldODEStateAndDerivative<T> integrate(FieldExpandableODE<T> paramFieldExpandableODE, FieldODEState<T> paramFieldODEState, T paramT) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Array2DRowFieldMatrix<T> initializeHighOrderDerivatives(T h, T[] t, T[][] y, T[][] yDot) {
/* 109 */     return this.transformer.initializeHighOrderDerivatives(h, t, y, yDot);
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
/*     */   public Array2DRowFieldMatrix<T> updateHighOrderDerivativesPhase1(Array2DRowFieldMatrix<T> highOrder) {
/* 124 */     return this.transformer.updateHighOrderDerivativesPhase1(highOrder);
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
/*     */   public void updateHighOrderDerivativesPhase2(T[] start, T[] end, Array2DRowFieldMatrix<T> highOrder) {
/* 142 */     this.transformer.updateHighOrderDerivativesPhase2(start, end, highOrder);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */