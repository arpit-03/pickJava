/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.analysis.RealFieldUnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.util.IntegerSequence;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldBracketingNthOrderBrentSolver<T extends RealFieldElement<T>>
/*     */   implements BracketedRealFieldUnivariateSolver<T>
/*     */ {
/*     */   private static final int MAXIMAL_AGING = 2;
/*     */   private final Field<T> field;
/*     */   private final int maximalOrder;
/*     */   private final T functionValueAccuracy;
/*     */   private final T absoluteAccuracy;
/*     */   private final T relativeAccuracy;
/*     */   private IntegerSequence.Incrementor evaluations;
/*     */   
/*     */   public FieldBracketingNthOrderBrentSolver(T relativeAccuracy, T absoluteAccuracy, T functionValueAccuracy, int maximalOrder) throws NumberIsTooSmallException {
/*  86 */     if (maximalOrder < 2) {
/*  87 */       throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(2), true);
/*     */     }
/*  89 */     this.field = relativeAccuracy.getField();
/*  90 */     this.maximalOrder = maximalOrder;
/*  91 */     this.absoluteAccuracy = absoluteAccuracy;
/*  92 */     this.relativeAccuracy = relativeAccuracy;
/*  93 */     this.functionValueAccuracy = functionValueAccuracy;
/*  94 */     this.evaluations = IntegerSequence.Incrementor.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximalOrder() {
/* 101 */     return this.maximalOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 110 */     return this.evaluations.getMaximalCount();
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
/*     */   public int getEvaluations() {
/* 122 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getAbsoluteAccuracy() {
/* 130 */     return this.absoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getRelativeAccuracy() {
/* 138 */     return this.relativeAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getFunctionValueAccuracy() {
/* 146 */     return this.functionValueAccuracy;
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
/*     */   public T solve(int maxEval, RealFieldUnivariateFunction<T> f, T min, T max, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
/* 168 */     return solve(maxEval, f, min, max, (T)((RealFieldElement)min.add(max)).divide(2.0D), allowedSolution);
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
/*     */   public T solve(int maxEval, RealFieldUnivariateFunction<T> f, T min, T max, T startValue, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
/*     */     int nbPoints, signChangeIndex;
/* 194 */     MathUtils.checkNotNull(f);
/*     */ 
/*     */     
/* 197 */     this.evaluations = this.evaluations.withMaximalCount(maxEval).withStart(0);
/* 198 */     RealFieldElement realFieldElement1 = (RealFieldElement)this.field.getZero();
/* 199 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.add(Double.NaN);
/*     */ 
/*     */     
/* 202 */     RealFieldElement[] arrayOfRealFieldElement1 = (RealFieldElement[])MathArrays.buildArray(this.field, this.maximalOrder + 1);
/* 203 */     RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])MathArrays.buildArray(this.field, this.maximalOrder + 1);
/* 204 */     arrayOfRealFieldElement1[0] = (RealFieldElement)min;
/* 205 */     arrayOfRealFieldElement1[1] = (RealFieldElement)startValue;
/* 206 */     arrayOfRealFieldElement1[2] = (RealFieldElement)max;
/*     */ 
/*     */     
/* 209 */     this.evaluations.increment();
/* 210 */     arrayOfRealFieldElement2[1] = f.value(arrayOfRealFieldElement1[1]);
/* 211 */     if (Precision.equals(arrayOfRealFieldElement2[1].getReal(), 0.0D, 1))
/*     */     {
/* 213 */       return (T)arrayOfRealFieldElement1[1];
/*     */     }
/*     */ 
/*     */     
/* 217 */     this.evaluations.increment();
/* 218 */     arrayOfRealFieldElement2[0] = f.value(arrayOfRealFieldElement1[0]);
/* 219 */     if (Precision.equals(arrayOfRealFieldElement2[0].getReal(), 0.0D, 1))
/*     */     {
/* 221 */       return (T)arrayOfRealFieldElement1[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 226 */     if (((RealFieldElement)arrayOfRealFieldElement2[0].multiply(arrayOfRealFieldElement2[1])).getReal() < 0.0D) {
/*     */ 
/*     */       
/* 229 */       nbPoints = 2;
/* 230 */       signChangeIndex = 1;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 235 */       this.evaluations.increment();
/* 236 */       arrayOfRealFieldElement2[2] = f.value(arrayOfRealFieldElement1[2]);
/* 237 */       if (Precision.equals(arrayOfRealFieldElement2[2].getReal(), 0.0D, 1))
/*     */       {
/* 239 */         return (T)arrayOfRealFieldElement1[2];
/*     */       }
/*     */       
/* 242 */       if (((RealFieldElement)arrayOfRealFieldElement2[1].multiply(arrayOfRealFieldElement2[2])).getReal() < 0.0D) {
/*     */         
/* 244 */         nbPoints = 3;
/* 245 */         signChangeIndex = 2;
/*     */       } else {
/* 247 */         throw new NoBracketingException(arrayOfRealFieldElement1[0].getReal(), arrayOfRealFieldElement1[2].getReal(), arrayOfRealFieldElement2[0].getReal(), arrayOfRealFieldElement2[2].getReal());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     RealFieldElement[] arrayOfRealFieldElement3 = (RealFieldElement[])MathArrays.buildArray(this.field, arrayOfRealFieldElement1.length);
/*     */ 
/*     */     
/* 257 */     RealFieldElement realFieldElement3 = arrayOfRealFieldElement1[signChangeIndex - 1];
/* 258 */     RealFieldElement realFieldElement4 = arrayOfRealFieldElement2[signChangeIndex - 1];
/* 259 */     RealFieldElement realFieldElement5 = (RealFieldElement)realFieldElement3.abs();
/* 260 */     RealFieldElement realFieldElement6 = (RealFieldElement)realFieldElement4.abs();
/* 261 */     int agingA = 0;
/* 262 */     RealFieldElement realFieldElement7 = arrayOfRealFieldElement1[signChangeIndex];
/* 263 */     RealFieldElement realFieldElement8 = arrayOfRealFieldElement2[signChangeIndex];
/* 264 */     RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement7.abs();
/* 265 */     RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement8.abs();
/* 266 */     int agingB = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 272 */       RealFieldElement realFieldElement14, realFieldElement15, realFieldElement11 = (((RealFieldElement)realFieldElement5.subtract(realFieldElement9)).getReal() < 0.0D) ? realFieldElement9 : realFieldElement5;
/* 273 */       RealFieldElement realFieldElement12 = (((RealFieldElement)realFieldElement6.subtract(realFieldElement10)).getReal() < 0.0D) ? realFieldElement10 : realFieldElement6;
/* 274 */       RealFieldElement realFieldElement13 = (RealFieldElement)this.absoluteAccuracy.add(this.relativeAccuracy.multiply(realFieldElement11));
/* 275 */       if (((RealFieldElement)((RealFieldElement)realFieldElement7.subtract(realFieldElement3)).subtract(realFieldElement13)).getReal() <= 0.0D || ((RealFieldElement)realFieldElement12.subtract(this.functionValueAccuracy)).getReal() < 0.0D) {
/*     */         
/* 277 */         switch (allowedSolution) {
/*     */           case ANY_SIDE:
/* 279 */             return (((RealFieldElement)realFieldElement6.subtract(realFieldElement10)).getReal() < 0.0D) ? (T)realFieldElement3 : (T)realFieldElement7;
/*     */           case LEFT_SIDE:
/* 281 */             return (T)realFieldElement3;
/*     */           case RIGHT_SIDE:
/* 283 */             return (T)realFieldElement7;
/*     */           case BELOW_SIDE:
/* 285 */             return (realFieldElement4.getReal() <= 0.0D) ? (T)realFieldElement3 : (T)realFieldElement7;
/*     */           case ABOVE_SIDE:
/* 287 */             return (realFieldElement4.getReal() < 0.0D) ? (T)realFieldElement7 : (T)realFieldElement3;
/*     */         } 
/*     */         
/* 290 */         throw new MathInternalError(null);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       if (agingA >= 2) {
/*     */         
/* 298 */         realFieldElement14 = (RealFieldElement)((RealFieldElement)realFieldElement8.divide(16.0D)).negate();
/* 299 */       } else if (agingB >= 2) {
/*     */         
/* 301 */         realFieldElement14 = (RealFieldElement)((RealFieldElement)realFieldElement4.divide(16.0D)).negate();
/*     */       } else {
/*     */         
/* 304 */         realFieldElement14 = realFieldElement1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 309 */       int start = 0;
/* 310 */       int end = nbPoints;
/*     */ 
/*     */       
/*     */       do {
/* 314 */         System.arraycopy(arrayOfRealFieldElement1, start, arrayOfRealFieldElement3, start, end - start);
/* 315 */         T nextX = guessX((T)realFieldElement14, (T[])arrayOfRealFieldElement3, (T[])arrayOfRealFieldElement2, start, end);
/*     */         
/* 317 */         if (((RealFieldElement)nextX.subtract(realFieldElement3)).getReal() > 0.0D && ((RealFieldElement)nextX.subtract(realFieldElement7)).getReal() < 0.0D) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 323 */         if (signChangeIndex - start >= end - signChangeIndex) {
/*     */           
/* 325 */           start++;
/*     */         } else {
/*     */           
/* 328 */           end--;
/*     */         } 
/*     */ 
/*     */         
/* 332 */         realFieldElement15 = realFieldElement2;
/*     */ 
/*     */       
/*     */       }
/* 336 */       while (Double.isNaN(realFieldElement15.getReal()) && end - start > 1);
/*     */       
/* 338 */       if (Double.isNaN(realFieldElement15.getReal())) {
/*     */         
/* 340 */         realFieldElement15 = (RealFieldElement)realFieldElement3.add(((RealFieldElement)realFieldElement7.subtract(realFieldElement3)).divide(2.0D));
/* 341 */         start = signChangeIndex - 1;
/* 342 */         end = signChangeIndex;
/*     */       } 
/*     */ 
/*     */       
/* 346 */       this.evaluations.increment();
/* 347 */       RealFieldElement realFieldElement16 = f.value(realFieldElement15);
/* 348 */       if (Precision.equals(realFieldElement16.getReal(), 0.0D, 1))
/*     */       {
/*     */         
/* 351 */         return (T)realFieldElement15;
/*     */       }
/*     */       
/* 354 */       if (nbPoints > 2 && end - start != nbPoints) {
/*     */ 
/*     */ 
/*     */         
/* 358 */         nbPoints = end - start;
/* 359 */         System.arraycopy(arrayOfRealFieldElement1, start, arrayOfRealFieldElement1, 0, nbPoints);
/* 360 */         System.arraycopy(arrayOfRealFieldElement2, start, arrayOfRealFieldElement2, 0, nbPoints);
/* 361 */         signChangeIndex -= start;
/*     */       }
/* 363 */       else if (nbPoints == arrayOfRealFieldElement1.length) {
/*     */ 
/*     */         
/* 366 */         nbPoints--;
/*     */ 
/*     */         
/* 369 */         if (signChangeIndex >= (arrayOfRealFieldElement1.length + 1) / 2) {
/*     */           
/* 371 */           System.arraycopy(arrayOfRealFieldElement1, 1, arrayOfRealFieldElement1, 0, nbPoints);
/* 372 */           System.arraycopy(arrayOfRealFieldElement2, 1, arrayOfRealFieldElement2, 0, nbPoints);
/* 373 */           signChangeIndex--;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 380 */       System.arraycopy(arrayOfRealFieldElement1, signChangeIndex, arrayOfRealFieldElement1, signChangeIndex + 1, nbPoints - signChangeIndex);
/* 381 */       arrayOfRealFieldElement1[signChangeIndex] = realFieldElement15;
/* 382 */       System.arraycopy(arrayOfRealFieldElement2, signChangeIndex, arrayOfRealFieldElement2, signChangeIndex + 1, nbPoints - signChangeIndex);
/* 383 */       arrayOfRealFieldElement2[signChangeIndex] = realFieldElement16;
/* 384 */       nbPoints++;
/*     */ 
/*     */       
/* 387 */       if (((RealFieldElement)realFieldElement16.multiply(realFieldElement4)).getReal() <= 0.0D) {
/*     */         
/* 389 */         realFieldElement7 = realFieldElement15;
/* 390 */         realFieldElement8 = realFieldElement16;
/* 391 */         realFieldElement10 = (RealFieldElement)realFieldElement8.abs();
/* 392 */         agingA++;
/* 393 */         agingB = 0;
/*     */         continue;
/*     */       } 
/* 396 */       realFieldElement3 = realFieldElement15;
/* 397 */       realFieldElement4 = realFieldElement16;
/* 398 */       realFieldElement6 = (RealFieldElement)realFieldElement4.abs();
/* 399 */       agingA = 0;
/* 400 */       agingB++;
/*     */ 
/*     */       
/* 403 */       signChangeIndex++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T guessX(T targetY, T[] x, T[] y, int start, int end) {
/* 429 */     for (int i = start; i < end - 1; i++) {
/* 430 */       int delta = i + 1 - start;
/* 431 */       for (int k = end - 1; k > i; k--) {
/* 432 */         x[k] = (T)((RealFieldElement)x[k].subtract(x[k - 1])).divide(y[k].subtract(y[k - delta]));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 437 */     RealFieldElement realFieldElement = (RealFieldElement)this.field.getZero();
/* 438 */     for (int j = end - 1; j >= start; j--) {
/* 439 */       realFieldElement = (RealFieldElement)x[j].add(realFieldElement.multiply(targetY.subtract(y[j])));
/*     */     }
/*     */     
/* 442 */     return (T)realFieldElement;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/FieldBracketingNthOrderBrentSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */