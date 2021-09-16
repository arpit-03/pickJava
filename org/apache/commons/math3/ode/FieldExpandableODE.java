/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
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
/*     */ public class FieldExpandableODE<T extends RealFieldElement<T>>
/*     */ {
/*     */   private final FirstOrderFieldDifferentialEquations<T> primary;
/*     */   private List<FieldSecondaryEquations<T>> components;
/*     */   private FieldEquationsMapper<T> mapper;
/*     */   
/*     */   public FieldExpandableODE(FirstOrderFieldDifferentialEquations<T> primary) {
/*  67 */     this.primary = primary;
/*  68 */     this.components = new ArrayList<FieldSecondaryEquations<T>>();
/*  69 */     this.mapper = new FieldEquationsMapper<T>(null, primary.getDimension());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldEquationsMapper<T> getMapper() {
/*  76 */     return this.mapper;
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
/*     */   public int addSecondaryEquations(FieldSecondaryEquations<T> secondary) {
/*  88 */     this.components.add(secondary);
/*  89 */     this.mapper = new FieldEquationsMapper<T>(this.mapper, secondary.getDimension());
/*     */     
/*  91 */     return this.components.size();
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
/*     */   public void init(T t0, T[] y0, T finalTime) {
/* 105 */     int index = 0;
/* 106 */     T[] primary0 = this.mapper.extractEquationData(index, y0);
/* 107 */     this.primary.init(t0, primary0, finalTime);
/*     */ 
/*     */     
/* 110 */     while (++index < this.mapper.getNumberOfEquations()) {
/* 111 */       T[] secondary0 = this.mapper.extractEquationData(index, y0);
/* 112 */       ((FieldSecondaryEquations<T>)this.components.get(index - 1)).init(t0, primary0, secondary0, finalTime);
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
/*     */   public T[] computeDerivatives(T t, T[] y) throws MaxCountExceededException, DimensionMismatchException {
/* 127 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(t.getField(), this.mapper.getTotalDimension());
/*     */ 
/*     */     
/* 130 */     int index = 0;
/* 131 */     T[] primaryState = this.mapper.extractEquationData(index, y);
/* 132 */     T[] primaryStateDot = this.primary.computeDerivatives(t, primaryState);
/* 133 */     this.mapper.insertEquationData(index, primaryStateDot, (T[])arrayOfRealFieldElement);
/*     */ 
/*     */     
/* 136 */     while (++index < this.mapper.getNumberOfEquations()) {
/* 137 */       T[] componentState = this.mapper.extractEquationData(index, y);
/* 138 */       T[] componentStateDot = ((FieldSecondaryEquations<T>)this.components.get(index - 1)).computeDerivatives(t, primaryState, primaryStateDot, componentState);
/*     */       
/* 140 */       this.mapper.insertEquationData(index, componentStateDot, (T[])arrayOfRealFieldElement);
/*     */     } 
/*     */     
/* 143 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FieldExpandableODE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */