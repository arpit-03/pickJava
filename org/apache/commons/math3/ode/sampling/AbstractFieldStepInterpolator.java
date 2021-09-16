/*     */ package org.apache.commons.math3.ode.sampling;
/*     */ 
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   implements FieldStepInterpolator<T>
/*     */ {
/*     */   private final FieldODEStateAndDerivative<T> globalPreviousState;
/*     */   private final FieldODEStateAndDerivative<T> globalCurrentState;
/*     */   private final FieldODEStateAndDerivative<T> softPreviousState;
/*     */   private final FieldODEStateAndDerivative<T> softCurrentState;
/*     */   private final boolean forward;
/*     */   private FieldEquationsMapper<T> mapper;
/*     */   
/*     */   protected AbstractFieldStepInterpolator(boolean isForward, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> equationsMapper) {
/*  75 */     this.forward = isForward;
/*  76 */     this.globalPreviousState = globalPreviousState;
/*  77 */     this.globalCurrentState = globalCurrentState;
/*  78 */     this.softPreviousState = softPreviousState;
/*  79 */     this.softCurrentState = softCurrentState;
/*  80 */     this.mapper = equationsMapper;
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
/*     */   public AbstractFieldStepInterpolator<T> restrictStep(FieldODEStateAndDerivative<T> previousState, FieldODEStateAndDerivative<T> currentState) {
/*  95 */     return create(this.forward, this.globalPreviousState, this.globalCurrentState, previousState, currentState, this.mapper);
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
/*     */   protected abstract AbstractFieldStepInterpolator<T> create(boolean paramBoolean, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative1, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative2, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative3, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative4, FieldEquationsMapper<T> paramFieldEquationsMapper);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getGlobalPreviousState() {
/* 119 */     return this.globalPreviousState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getGlobalCurrentState() {
/* 127 */     return this.globalCurrentState;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getPreviousState() {
/* 132 */     return this.softPreviousState;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getCurrentState() {
/* 137 */     return this.softCurrentState;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getInterpolatedState(T time) {
/* 142 */     RealFieldElement realFieldElement1 = (RealFieldElement)time.subtract(this.globalPreviousState.getTime());
/* 143 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.globalCurrentState.getTime().subtract(time);
/* 144 */     RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement1.divide(this.globalCurrentState.getTime().subtract(this.globalPreviousState.getTime()));
/* 145 */     return computeInterpolatedStateAndDerivatives(this.mapper, time, (T)realFieldElement3, (T)realFieldElement1, (T)realFieldElement2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isForward() {
/* 150 */     return this.forward;
/*     */   }
/*     */   
/*     */   protected abstract FieldODEStateAndDerivative<T> computeInterpolatedStateAndDerivatives(FieldEquationsMapper<T> paramFieldEquationsMapper, T paramT1, T paramT2, T paramT3, T paramT4) throws MaxCountExceededException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/AbstractFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */