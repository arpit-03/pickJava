/*     */ package org.apache.commons.math3.analysis.differentiation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateMatrixFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateVectorFunction;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FiniteDifferencesDifferentiator
/*     */   implements UnivariateFunctionDifferentiator, UnivariateVectorFunctionDifferentiator, UnivariateMatrixFunctionDifferentiator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20120917L;
/*     */   private final int nbPoints;
/*     */   private final double stepSize;
/*     */   private final double halfSampleSpan;
/*     */   private final double tMin;
/*     */   private final double tMax;
/*     */   
/*     */   public FiniteDifferencesDifferentiator(int nbPoints, double stepSize) throws NotPositiveException, NumberIsTooSmallException {
/* 109 */     this(nbPoints, stepSize, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FiniteDifferencesDifferentiator(int nbPoints, double stepSize, double tLower, double tUpper) throws NotPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
/* 145 */     if (nbPoints <= 1) {
/* 146 */       throw new NumberIsTooSmallException(Double.valueOf(stepSize), Integer.valueOf(1), false);
/*     */     }
/* 148 */     this.nbPoints = nbPoints;
/*     */     
/* 150 */     if (stepSize <= 0.0D) {
/* 151 */       throw new NotPositiveException(Double.valueOf(stepSize));
/*     */     }
/* 153 */     this.stepSize = stepSize;
/*     */     
/* 155 */     this.halfSampleSpan = 0.5D * stepSize * (nbPoints - 1);
/* 156 */     if (2.0D * this.halfSampleSpan >= tUpper - tLower) {
/* 157 */       throw new NumberIsTooLargeException(Double.valueOf(2.0D * this.halfSampleSpan), Double.valueOf(tUpper - tLower), false);
/*     */     }
/* 159 */     double safety = FastMath.ulp(this.halfSampleSpan);
/* 160 */     this.tMin = tLower + this.halfSampleSpan + safety;
/* 161 */     this.tMax = tUpper - this.halfSampleSpan - safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNbPoints() {
/* 170 */     return this.nbPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStepSize() {
/* 178 */     return this.stepSize;
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
/*     */   private DerivativeStructure evaluate(DerivativeStructure t, double t0, double[] y) throws NumberIsTooLargeException {
/* 198 */     double[] top = new double[this.nbPoints];
/* 199 */     double[] bottom = new double[this.nbPoints];
/*     */     
/* 201 */     for (int i = 0; i < this.nbPoints; i++) {
/*     */ 
/*     */       
/* 204 */       bottom[i] = y[i];
/* 205 */       for (int k = 1; k <= i; k++) {
/* 206 */         bottom[i - k] = (bottom[i - k + 1] - bottom[i - k]) / k * this.stepSize;
/*     */       }
/*     */ 
/*     */       
/* 210 */       top[i] = bottom[0];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 215 */     int order = t.getOrder();
/* 216 */     int parameters = t.getFreeParameters();
/* 217 */     double[] derivatives = t.getAllDerivatives();
/* 218 */     double dt0 = t.getValue() - t0;
/* 219 */     DerivativeStructure interpolation = new DerivativeStructure(parameters, order, 0.0D);
/* 220 */     DerivativeStructure monomial = null;
/* 221 */     for (int j = 0; j < this.nbPoints; j++) {
/* 222 */       if (j == 0) {
/*     */         
/* 224 */         monomial = new DerivativeStructure(parameters, order, 1.0D);
/*     */       } else {
/*     */         
/* 227 */         derivatives[0] = dt0 - (j - 1) * this.stepSize;
/* 228 */         DerivativeStructure deltaX = new DerivativeStructure(parameters, order, derivatives);
/* 229 */         monomial = monomial.multiply(deltaX);
/*     */       } 
/* 231 */       interpolation = interpolation.add(monomial.multiply(top[j]));
/*     */     } 
/*     */     
/* 234 */     return interpolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariateDifferentiableFunction differentiate(final UnivariateFunction function) {
/* 245 */     return new UnivariateDifferentiableFunction()
/*     */       {
/*     */         public double value(double x) throws MathIllegalArgumentException
/*     */         {
/* 249 */           return function.value(x);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DerivativeStructure value(DerivativeStructure t) throws MathIllegalArgumentException {
/* 257 */           if (t.getOrder() >= FiniteDifferencesDifferentiator.this.nbPoints) {
/* 258 */             throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(FiniteDifferencesDifferentiator.this.nbPoints), false);
/*     */           }
/*     */ 
/*     */           
/* 262 */           double t0 = FastMath.max(FastMath.min(t.getValue(), FiniteDifferencesDifferentiator.this.tMax), FiniteDifferencesDifferentiator.this.tMin) - FiniteDifferencesDifferentiator.this.halfSampleSpan;
/*     */ 
/*     */           
/* 265 */           double[] y = new double[FiniteDifferencesDifferentiator.this.nbPoints];
/* 266 */           for (int i = 0; i < FiniteDifferencesDifferentiator.this.nbPoints; i++) {
/* 267 */             y[i] = function.value(t0 + i * FiniteDifferencesDifferentiator.this.stepSize);
/*     */           }
/*     */ 
/*     */           
/* 271 */           return FiniteDifferencesDifferentiator.this.evaluate(t, t0, y);
/*     */         }
/*     */       };
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
/*     */   public UnivariateDifferentiableVectorFunction differentiate(final UnivariateVectorFunction function) {
/* 285 */     return new UnivariateDifferentiableVectorFunction()
/*     */       {
/*     */         public double[] value(double x) throws MathIllegalArgumentException
/*     */         {
/* 289 */           return function.value(x);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DerivativeStructure[] value(DerivativeStructure t) throws MathIllegalArgumentException {
/* 297 */           if (t.getOrder() >= FiniteDifferencesDifferentiator.this.nbPoints) {
/* 298 */             throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(FiniteDifferencesDifferentiator.this.nbPoints), false);
/*     */           }
/*     */ 
/*     */           
/* 302 */           double t0 = FastMath.max(FastMath.min(t.getValue(), FiniteDifferencesDifferentiator.this.tMax), FiniteDifferencesDifferentiator.this.tMin) - FiniteDifferencesDifferentiator.this.halfSampleSpan;
/*     */ 
/*     */           
/* 305 */           double[][] y = (double[][])null;
/* 306 */           for (int i = 0; i < FiniteDifferencesDifferentiator.this.nbPoints; i++) {
/* 307 */             double[] v = function.value(t0 + i * FiniteDifferencesDifferentiator.this.stepSize);
/* 308 */             if (i == 0) {
/* 309 */               y = new double[v.length][FiniteDifferencesDifferentiator.this.nbPoints];
/*     */             }
/* 311 */             for (int k = 0; k < v.length; k++) {
/* 312 */               y[k][i] = v[k];
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 317 */           DerivativeStructure[] value = new DerivativeStructure[y.length];
/* 318 */           for (int j = 0; j < value.length; j++) {
/* 319 */             value[j] = FiniteDifferencesDifferentiator.this.evaluate(t, t0, y[j]);
/*     */           }
/*     */           
/* 322 */           return value;
/*     */         }
/*     */       };
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
/*     */   public UnivariateDifferentiableMatrixFunction differentiate(final UnivariateMatrixFunction function) {
/* 336 */     return new UnivariateDifferentiableMatrixFunction()
/*     */       {
/*     */         public double[][] value(double x) throws MathIllegalArgumentException
/*     */         {
/* 340 */           return function.value(x);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DerivativeStructure[][] value(DerivativeStructure t) throws MathIllegalArgumentException {
/* 348 */           if (t.getOrder() >= FiniteDifferencesDifferentiator.this.nbPoints) {
/* 349 */             throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(FiniteDifferencesDifferentiator.this.nbPoints), false);
/*     */           }
/*     */ 
/*     */           
/* 353 */           double t0 = FastMath.max(FastMath.min(t.getValue(), FiniteDifferencesDifferentiator.this.tMax), FiniteDifferencesDifferentiator.this.tMin) - FiniteDifferencesDifferentiator.this.halfSampleSpan;
/*     */ 
/*     */           
/* 356 */           double[][][] y = (double[][][])null;
/* 357 */           for (int i = 0; i < FiniteDifferencesDifferentiator.this.nbPoints; i++) {
/* 358 */             double[][] v = function.value(t0 + i * FiniteDifferencesDifferentiator.this.stepSize);
/* 359 */             if (i == 0) {
/* 360 */               y = new double[v.length][(v[0]).length][FiniteDifferencesDifferentiator.this.nbPoints];
/*     */             }
/* 362 */             for (int k = 0; k < v.length; k++) {
/* 363 */               for (int m = 0; m < (v[k]).length; m++) {
/* 364 */                 y[k][m][i] = v[k][m];
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 370 */           DerivativeStructure[][] value = new DerivativeStructure[y.length][(y[0]).length];
/* 371 */           for (int j = 0; j < value.length; j++) {
/* 372 */             for (int k = 0; k < (y[j]).length; k++) {
/* 373 */               value[j][k] = FiniteDifferencesDifferentiator.this.evaluate(t, t0, y[j][k]);
/*     */             }
/*     */           } 
/*     */           
/* 377 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/FiniteDifferencesDifferentiator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */