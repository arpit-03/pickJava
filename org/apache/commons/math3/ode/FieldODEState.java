/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
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
/*     */ public class FieldODEState<T extends RealFieldElement<T>>
/*     */ {
/*     */   private final T time;
/*     */   private final T[] state;
/*     */   private final T[][] secondaryState;
/*     */   
/*     */   public FieldODEState(T time, T[] state) {
/*  53 */     this(time, state, (T[][])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEState(T time, T[] state, T[][] secondaryState) {
/*  62 */     this.time = time;
/*  63 */     this.state = (T[])state.clone();
/*  64 */     this.secondaryState = copy(time.getField(), secondaryState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T[][] copy(Field<T> field, T[][] original) {
/*  75 */     if (original == null) {
/*  76 */       return (T[][])null;
/*     */     }
/*     */ 
/*     */     
/*  80 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(field, original.length, -1);
/*     */ 
/*     */     
/*  83 */     for (int i = 0; i < original.length; i++) {
/*  84 */       arrayOfRealFieldElement[i] = (RealFieldElement[])original[i].clone();
/*     */     }
/*     */     
/*  87 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getTime() {
/*  95 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStateDimension() {
/* 102 */     return this.state.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] getState() {
/* 109 */     return (T[])this.state.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfSecondaryStates() {
/* 116 */     return (this.secondaryState == null) ? 0 : this.secondaryState.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSecondaryStateDimension(int index) {
/* 126 */     return (index == 0) ? this.state.length : (this.secondaryState[index - 1]).length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] getSecondaryState(int index) {
/* 136 */     return (index == 0) ? (T[])this.state.clone() : (T[])this.secondaryState[index - 1].clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FieldODEState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */