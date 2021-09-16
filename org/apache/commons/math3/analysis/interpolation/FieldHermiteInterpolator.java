/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class FieldHermiteInterpolator<T extends FieldElement<T>>
/*     */ {
/*  64 */   private final List<T> abscissae = new ArrayList<T>();
/*  65 */   private final List<T[]> topDiagonal = new ArrayList<T>();
/*  66 */   private final List<T[]> bottomDiagonal = new ArrayList<T>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSamplePoint(T x, T[]... value) throws ZeroException, MathArithmeticException, DimensionMismatchException, NullArgumentException {
/*  94 */     MathUtils.checkNotNull(x);
/*  95 */     FieldElement fieldElement = (FieldElement)x.getField().getOne();
/*  96 */     for (int i = 0; i < value.length; i++) {
/*     */       
/*  98 */       FieldElement[] arrayOfFieldElement1 = (FieldElement[])value[i].clone();
/*  99 */       if (i > 1) {
/* 100 */         fieldElement = (FieldElement)fieldElement.multiply(i);
/* 101 */         FieldElement fieldElement1 = (FieldElement)fieldElement.reciprocal();
/* 102 */         for (int k = 0; k < arrayOfFieldElement1.length; k++) {
/* 103 */           arrayOfFieldElement1[k] = (FieldElement)arrayOfFieldElement1[k].multiply(fieldElement1);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 108 */       int n = this.abscissae.size();
/* 109 */       this.bottomDiagonal.add(n - i, (T[])arrayOfFieldElement1);
/* 110 */       FieldElement[] arrayOfFieldElement2 = arrayOfFieldElement1;
/* 111 */       for (int j = i; j < n; j++) {
/* 112 */         FieldElement[] arrayOfFieldElement = (FieldElement[])this.bottomDiagonal.get(n - j + 1);
/* 113 */         if (x.equals(this.abscissae.get(n - j + 1))) {
/* 114 */           throw new ZeroException(LocalizedFormats.DUPLICATED_ABSCISSA_DIVISION_BY_ZERO, new Object[] { x });
/*     */         }
/* 116 */         FieldElement fieldElement1 = (FieldElement)((FieldElement)x.subtract(this.abscissae.get(n - j + 1))).reciprocal();
/* 117 */         for (int k = 0; k < arrayOfFieldElement1.length; k++) {
/* 118 */           arrayOfFieldElement[k] = (FieldElement)fieldElement1.multiply(arrayOfFieldElement2[k].subtract(arrayOfFieldElement[k]));
/*     */         }
/* 120 */         arrayOfFieldElement2 = arrayOfFieldElement;
/*     */       } 
/*     */ 
/*     */       
/* 124 */       this.topDiagonal.add((T[])arrayOfFieldElement2.clone());
/*     */ 
/*     */       
/* 127 */       this.abscissae.add(x);
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
/*     */   public T[] value(T x) throws NoDataException, NullArgumentException {
/* 142 */     MathUtils.checkNotNull(x);
/* 143 */     if (this.abscissae.isEmpty()) {
/* 144 */       throw new NoDataException(LocalizedFormats.EMPTY_INTERPOLATION_SAMPLE);
/*     */     }
/*     */     
/* 147 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(x.getField(), ((FieldElement[])this.topDiagonal.get(0)).length);
/* 148 */     FieldElement fieldElement = (FieldElement)x.getField().getOne();
/* 149 */     for (int i = 0; i < this.topDiagonal.size(); i++) {
/* 150 */       FieldElement[] arrayOfFieldElement1 = (FieldElement[])this.topDiagonal.get(i);
/* 151 */       for (int k = 0; k < arrayOfFieldElement.length; k++) {
/* 152 */         arrayOfFieldElement[k] = (FieldElement)arrayOfFieldElement[k].add(arrayOfFieldElement1[k].multiply(fieldElement));
/*     */       }
/* 154 */       FieldElement fieldElement1 = (FieldElement)x.subtract(this.abscissae.get(i));
/* 155 */       fieldElement = (FieldElement)fieldElement.multiply(fieldElement1);
/*     */     } 
/*     */     
/* 158 */     return (T[])arrayOfFieldElement;
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
/*     */   public T[][] derivatives(T x, int order) throws NoDataException, NullArgumentException {
/* 173 */     MathUtils.checkNotNull(x);
/* 174 */     if (this.abscissae.isEmpty()) {
/* 175 */       throw new NoDataException(LocalizedFormats.EMPTY_INTERPOLATION_SAMPLE);
/*     */     }
/*     */     
/* 178 */     FieldElement fieldElement1 = (FieldElement)x.getField().getZero();
/* 179 */     FieldElement fieldElement2 = (FieldElement)x.getField().getOne();
/* 180 */     FieldElement[] arrayOfFieldElement1 = (FieldElement[])MathArrays.buildArray(x.getField(), order + 1);
/* 181 */     arrayOfFieldElement1[0] = fieldElement1;
/* 182 */     for (int i = 0; i < order; i++) {
/* 183 */       arrayOfFieldElement1[i + 1] = (FieldElement)arrayOfFieldElement1[i].add(fieldElement2);
/*     */     }
/*     */     
/* 186 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(x.getField(), order + 1, ((FieldElement[])this.topDiagonal.get(0)).length);
/*     */     
/* 188 */     FieldElement[] arrayOfFieldElement2 = (FieldElement[])MathArrays.buildArray(x.getField(), order + 1);
/* 189 */     arrayOfFieldElement2[0] = (FieldElement)x.getField().getOne();
/* 190 */     for (int j = 0; j < this.topDiagonal.size(); j++) {
/* 191 */       FieldElement[] arrayOfFieldElement3 = (FieldElement[])this.topDiagonal.get(j);
/* 192 */       FieldElement fieldElement = (FieldElement)x.subtract(this.abscissae.get(j));
/* 193 */       for (int k = order; k >= 0; k--) {
/* 194 */         for (int m = 0; m < (arrayOfFieldElement[k]).length; m++) {
/* 195 */           arrayOfFieldElement[k][m] = (FieldElement)arrayOfFieldElement[k][m].add(arrayOfFieldElement3[m].multiply(arrayOfFieldElement2[k]));
/*     */         }
/*     */         
/* 198 */         arrayOfFieldElement2[k] = (FieldElement)arrayOfFieldElement2[k].multiply(fieldElement);
/* 199 */         if (k > 0) {
/* 200 */           arrayOfFieldElement2[k] = (FieldElement)arrayOfFieldElement2[k].add(arrayOfFieldElement1[k].multiply(arrayOfFieldElement2[k - 1]));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     return (T[][])arrayOfFieldElement;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/FieldHermiteInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */