/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.linear.ArrayFieldVector;
/*     */ import org.apache.commons.math3.linear.FieldDecompositionSolver;
/*     */ import org.apache.commons.math3.linear.FieldLUDecomposition;
/*     */ import org.apache.commons.math3.linear.FieldMatrix;
/*     */ import org.apache.commons.math3.linear.FieldVector;
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
/*     */ public class AdamsNordsieckFieldTransformer<T extends RealFieldElement<T>>
/*     */ {
/* 139 */   private static final Map<Integer, Map<Field<? extends RealFieldElement<?>>, AdamsNordsieckFieldTransformer<? extends RealFieldElement<?>>>> CACHE = new HashMap<Integer, Map<Field<? extends RealFieldElement<?>>, AdamsNordsieckFieldTransformer<? extends RealFieldElement<?>>>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Field<T> field;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Array2DRowFieldMatrix<T> update;
/*     */ 
/*     */ 
/*     */   
/*     */   private final T[] c1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AdamsNordsieckFieldTransformer(Field<T> field, int n) {
/* 159 */     this.field = field;
/* 160 */     int rows = n - 1;
/*     */ 
/*     */     
/* 163 */     FieldMatrix<T> bigP = buildP(rows);
/* 164 */     FieldDecompositionSolver<T> pSolver = (new FieldLUDecomposition(bigP)).getSolver();
/*     */ 
/*     */     
/* 167 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(field, rows);
/* 168 */     Arrays.fill((Object[])arrayOfRealFieldElement, field.getOne());
/* 169 */     this.c1 = (T[])pSolver.solve((FieldVector)new ArrayFieldVector((FieldElement[])arrayOfRealFieldElement, false)).toArray();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     RealFieldElement[][] arrayOfRealFieldElement1 = (RealFieldElement[][])bigP.getData();
/* 175 */     for (int i = arrayOfRealFieldElement1.length - 1; i > 0; i--)
/*     */     {
/* 177 */       arrayOfRealFieldElement1[i] = arrayOfRealFieldElement1[i - 1];
/*     */     }
/* 179 */     arrayOfRealFieldElement1[0] = (RealFieldElement[])MathArrays.buildArray(field, rows);
/* 180 */     Arrays.fill((Object[])arrayOfRealFieldElement1[0], field.getZero());
/* 181 */     this.update = new Array2DRowFieldMatrix(pSolver.solve((FieldMatrix)new Array2DRowFieldMatrix((FieldElement[][])arrayOfRealFieldElement1, false)).getData());
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
/*     */   public static <T extends RealFieldElement<T>> AdamsNordsieckFieldTransformer<T> getInstance(Field<T> field, int nSteps) {
/* 195 */     synchronized (CACHE) {
/*     */       
/* 197 */       Map<Field<? extends RealFieldElement<?>>, AdamsNordsieckFieldTransformer<? extends RealFieldElement<?>>> map = CACHE.get(Integer.valueOf(nSteps));
/* 198 */       if (map == null) {
/* 199 */         map = new HashMap<Field<? extends RealFieldElement<?>>, AdamsNordsieckFieldTransformer<? extends RealFieldElement<?>>>();
/*     */         
/* 201 */         CACHE.put(Integer.valueOf(nSteps), map);
/*     */       } 
/*     */       
/* 204 */       AdamsNordsieckFieldTransformer<T> t = (AdamsNordsieckFieldTransformer)map.get(field);
/* 205 */       if (t == null) {
/* 206 */         t = new AdamsNordsieckFieldTransformer<T>(field, nSteps);
/* 207 */         map.put(field, t);
/*     */       } 
/* 209 */       return t;
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
/*     */   
/*     */   private FieldMatrix<T> buildP(int rows) {
/* 230 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(this.field, rows, rows);
/*     */     
/* 232 */     for (int i = 1; i <= arrayOfRealFieldElement.length; i++) {
/*     */       
/* 234 */       RealFieldElement[] arrayOfRealFieldElement1 = arrayOfRealFieldElement[i - 1];
/* 235 */       int factor = -i;
/* 236 */       RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)this.field.getZero()).add(factor);
/* 237 */       for (int j = 1; j <= arrayOfRealFieldElement1.length; j++) {
/* 238 */         arrayOfRealFieldElement1[j - 1] = (RealFieldElement)realFieldElement.multiply(j + 1);
/* 239 */         realFieldElement = (RealFieldElement)realFieldElement.multiply(factor);
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     return (FieldMatrix<T>)new Array2DRowFieldMatrix((FieldElement[][])arrayOfRealFieldElement, false);
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
/*     */   public Array2DRowFieldMatrix<T> initializeHighOrderDerivatives(T h, T[] t, T[][] y, T[][] yDot) {
/* 269 */     RealFieldElement[][] arrayOfRealFieldElement1 = (RealFieldElement[][])MathArrays.buildArray(this.field, this.c1.length + 1, this.c1.length + 1);
/* 270 */     RealFieldElement[][] arrayOfRealFieldElement2 = (RealFieldElement[][])MathArrays.buildArray(this.field, this.c1.length + 1, (y[0]).length);
/* 271 */     T[] y0 = y[0];
/* 272 */     T[] yDot0 = yDot[0];
/* 273 */     for (int i = 1; i < y.length; i++) {
/*     */       
/* 275 */       RealFieldElement realFieldElement1 = (RealFieldElement)t[i].subtract(t[0]);
/* 276 */       RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.divide(h);
/* 277 */       RealFieldElement realFieldElement3 = (RealFieldElement)h.reciprocal();
/*     */ 
/*     */ 
/*     */       
/* 281 */       RealFieldElement[] arrayOfRealFieldElement3 = arrayOfRealFieldElement1[2 * i - 2];
/* 282 */       RealFieldElement[] arrayOfRealFieldElement4 = (2 * i - 1 < arrayOfRealFieldElement1.length) ? arrayOfRealFieldElement1[2 * i - 1] : null;
/* 283 */       for (int k = 0; k < arrayOfRealFieldElement3.length; k++) {
/* 284 */         realFieldElement3 = (RealFieldElement)realFieldElement3.multiply(realFieldElement2);
/* 285 */         arrayOfRealFieldElement3[k] = (RealFieldElement)realFieldElement1.multiply(realFieldElement3);
/* 286 */         if (arrayOfRealFieldElement4 != null) {
/* 287 */           arrayOfRealFieldElement4[k] = (RealFieldElement)realFieldElement3.multiply(k + 2);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 292 */       T[] yI = y[i];
/* 293 */       T[] yDotI = yDot[i];
/* 294 */       RealFieldElement[] arrayOfRealFieldElement5 = arrayOfRealFieldElement2[2 * i - 2];
/* 295 */       RealFieldElement[] arrayOfRealFieldElement6 = (2 * i - 1 < arrayOfRealFieldElement2.length) ? arrayOfRealFieldElement2[2 * i - 1] : null;
/* 296 */       for (int m = 0; m < yI.length; m++) {
/* 297 */         arrayOfRealFieldElement5[m] = (RealFieldElement)((RealFieldElement)yI[m].subtract(y0[m])).subtract(realFieldElement1.multiply(yDot0[m]));
/* 298 */         if (arrayOfRealFieldElement6 != null) {
/* 299 */           arrayOfRealFieldElement6[m] = (RealFieldElement)yDotI[m].subtract(yDot0[m]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     FieldLUDecomposition<T> decomposition = new FieldLUDecomposition((FieldMatrix)new Array2DRowFieldMatrix((FieldElement[][])arrayOfRealFieldElement1, false));
/* 308 */     FieldMatrix<T> x = decomposition.getSolver().solve((FieldMatrix)new Array2DRowFieldMatrix((FieldElement[][])arrayOfRealFieldElement2, false));
/*     */ 
/*     */     
/* 311 */     Array2DRowFieldMatrix<T> truncatedX = new Array2DRowFieldMatrix(this.field, x.getRowDimension() - 1, x.getColumnDimension());
/*     */     
/* 313 */     for (int j = 0; j < truncatedX.getRowDimension(); j++) {
/* 314 */       for (int k = 0; k < truncatedX.getColumnDimension(); k++) {
/* 315 */         truncatedX.setEntry(j, k, x.getEntry(j, k));
/*     */       }
/*     */     } 
/* 318 */     return truncatedX;
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
/*     */   public Array2DRowFieldMatrix<T> updateHighOrderDerivativesPhase1(Array2DRowFieldMatrix<T> highOrder) {
/* 334 */     return this.update.multiply(highOrder);
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
/*     */   public void updateHighOrderDerivativesPhase2(T[] start, T[] end, Array2DRowFieldMatrix<T> highOrder) {
/* 353 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])highOrder.getDataRef();
/* 354 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/* 355 */       RealFieldElement[] arrayOfRealFieldElement1 = arrayOfRealFieldElement[i];
/* 356 */       T c1I = this.c1[i];
/* 357 */       for (int j = 0; j < arrayOfRealFieldElement1.length; j++)
/* 358 */         arrayOfRealFieldElement1[j] = (RealFieldElement)arrayOfRealFieldElement1[j].add(c1I.multiply(start[j].subtract(end[j]))); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsNordsieckFieldTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */