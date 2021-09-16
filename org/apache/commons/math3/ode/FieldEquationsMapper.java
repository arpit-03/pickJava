/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class FieldEquationsMapper<T extends RealFieldElement<T>>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20151114L;
/*     */   private final int[] start;
/*     */   
/*     */   FieldEquationsMapper(FieldEquationsMapper<T> mapper, int dimension) {
/*  55 */     int index = (mapper == null) ? 0 : mapper.getNumberOfEquations();
/*  56 */     this.start = new int[index + 2];
/*  57 */     if (mapper == null) {
/*  58 */       this.start[0] = 0;
/*     */     } else {
/*  60 */       System.arraycopy(mapper.start, 0, this.start, 0, index + 1);
/*     */     } 
/*  62 */     this.start[index + 1] = this.start[index] + dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfEquations() {
/*  69 */     return this.start.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalDimension() {
/*  79 */     return this.start[this.start.length - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] mapState(FieldODEState<T> state) {
/*  87 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(state.getTime().getField(), getTotalDimension());
/*  88 */     int index = 0;
/*  89 */     insertEquationData(index, state.getState(), (T[])arrayOfRealFieldElement);
/*  90 */     while (++index < getNumberOfEquations()) {
/*  91 */       insertEquationData(index, state.getSecondaryState(index), (T[])arrayOfRealFieldElement);
/*     */     }
/*  93 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] mapDerivative(FieldODEStateAndDerivative<T> state) {
/* 101 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(state.getTime().getField(), getTotalDimension());
/* 102 */     int index = 0;
/* 103 */     insertEquationData(index, state.getDerivative(), (T[])arrayOfRealFieldElement);
/* 104 */     while (++index < getNumberOfEquations()) {
/* 105 */       insertEquationData(index, state.getSecondaryDerivative(index), (T[])arrayOfRealFieldElement);
/*     */     }
/* 107 */     return (T[])arrayOfRealFieldElement;
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
/*     */   public FieldODEStateAndDerivative<T> mapStateAndDerivative(T t, T[] y, T[] yDot) throws DimensionMismatchException {
/* 120 */     if (y.length != getTotalDimension()) {
/* 121 */       throw new DimensionMismatchException(y.length, getTotalDimension());
/*     */     }
/*     */     
/* 124 */     if (yDot.length != getTotalDimension()) {
/* 125 */       throw new DimensionMismatchException(yDot.length, getTotalDimension());
/*     */     }
/*     */     
/* 128 */     int n = getNumberOfEquations();
/* 129 */     int index = 0;
/* 130 */     T[] state = extractEquationData(index, y);
/* 131 */     T[] derivative = extractEquationData(index, yDot);
/* 132 */     if (n < 2) {
/* 133 */       return new FieldODEStateAndDerivative<T>(t, state, derivative);
/*     */     }
/* 135 */     RealFieldElement[][] arrayOfRealFieldElement1 = (RealFieldElement[][])MathArrays.buildArray(t.getField(), n - 1, -1);
/* 136 */     RealFieldElement[][] arrayOfRealFieldElement2 = (RealFieldElement[][])MathArrays.buildArray(t.getField(), n - 1, -1);
/* 137 */     while (++index < getNumberOfEquations()) {
/* 138 */       arrayOfRealFieldElement1[index - 1] = (RealFieldElement[])extractEquationData(index, y);
/* 139 */       arrayOfRealFieldElement2[index - 1] = (RealFieldElement[])extractEquationData(index, yDot);
/*     */     } 
/* 141 */     return new FieldODEStateAndDerivative<T>(t, state, derivative, (T[][])arrayOfRealFieldElement1, (T[][])arrayOfRealFieldElement2);
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
/*     */   public T[] extractEquationData(int index, T[] complete) throws MathIllegalArgumentException, DimensionMismatchException {
/* 156 */     checkIndex(index);
/* 157 */     int begin = this.start[index];
/* 158 */     int end = this.start[index + 1];
/* 159 */     if (complete.length < end) {
/* 160 */       throw new DimensionMismatchException(complete.length, end);
/*     */     }
/* 162 */     int dimension = end - begin;
/* 163 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(complete[0].getField(), dimension);
/* 164 */     System.arraycopy(complete, begin, arrayOfRealFieldElement, 0, dimension);
/* 165 */     return (T[])arrayOfRealFieldElement;
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
/*     */   public void insertEquationData(int index, T[] equationData, T[] complete) throws DimensionMismatchException {
/* 178 */     checkIndex(index);
/* 179 */     int begin = this.start[index];
/* 180 */     int end = this.start[index + 1];
/* 181 */     int dimension = end - begin;
/* 182 */     if (complete.length < end) {
/* 183 */       throw new DimensionMismatchException(complete.length, end);
/*     */     }
/* 185 */     if (equationData.length != dimension) {
/* 186 */       throw new DimensionMismatchException(equationData.length, dimension);
/*     */     }
/* 188 */     System.arraycopy(equationData, 0, complete, begin, dimension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkIndex(int index) throws MathIllegalArgumentException {
/* 197 */     if (index < 0 || index > this.start.length - 2)
/* 198 */       throw new MathIllegalArgumentException(LocalizedFormats.ARGUMENT_OUTSIDE_DOMAIN, new Object[] { Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.start.length - 2) }); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FieldEquationsMapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */