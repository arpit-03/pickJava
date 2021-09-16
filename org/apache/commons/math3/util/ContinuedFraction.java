/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ContinuedFraction
/*     */ {
/*     */   private static final double DEFAULT_EPSILON = 1.0E-8D;
/*     */   
/*     */   protected abstract double getA(int paramInt, double paramDouble);
/*     */   
/*     */   protected abstract double getB(int paramInt, double paramDouble);
/*     */   
/*     */   public double evaluate(double x) throws ConvergenceException {
/*  72 */     return evaluate(x, 1.0E-8D, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evaluate(double x, double epsilon) throws ConvergenceException {
/*  83 */     return evaluate(x, epsilon, 2147483647);
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
/*     */   public double evaluate(double x, int maxIterations) throws ConvergenceException, MaxCountExceededException {
/*  96 */     return evaluate(x, 1.0E-8D, maxIterations);
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
/*     */   public double evaluate(double x, double epsilon, int maxIterations) throws ConvergenceException, MaxCountExceededException {
/* 124 */     double small = 1.0E-50D;
/* 125 */     double hPrev = getA(0, x);
/*     */ 
/*     */     
/* 128 */     if (Precision.equals(hPrev, 0.0D, 1.0E-50D)) {
/* 129 */       hPrev = 1.0E-50D;
/*     */     }
/*     */     
/* 132 */     int n = 1;
/* 133 */     double dPrev = 0.0D;
/* 134 */     double cPrev = hPrev;
/* 135 */     double hN = hPrev;
/*     */     
/* 137 */     while (n < maxIterations) {
/* 138 */       double a = getA(n, x);
/* 139 */       double b = getB(n, x);
/*     */       
/* 141 */       double dN = a + b * dPrev;
/* 142 */       if (Precision.equals(dN, 0.0D, 1.0E-50D)) {
/* 143 */         dN = 1.0E-50D;
/*     */       }
/* 145 */       double cN = a + b / cPrev;
/* 146 */       if (Precision.equals(cN, 0.0D, 1.0E-50D)) {
/* 147 */         cN = 1.0E-50D;
/*     */       }
/*     */       
/* 150 */       dN = 1.0D / dN;
/* 151 */       double deltaN = cN * dN;
/* 152 */       hN = hPrev * deltaN;
/*     */       
/* 154 */       if (Double.isInfinite(hN)) {
/* 155 */         throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, new Object[] { Double.valueOf(x) });
/*     */       }
/*     */       
/* 158 */       if (Double.isNaN(hN)) {
/* 159 */         throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE, new Object[] { Double.valueOf(x) });
/*     */       }
/*     */ 
/*     */       
/* 163 */       if (FastMath.abs(deltaN - 1.0D) < epsilon) {
/*     */         break;
/*     */       }
/*     */       
/* 167 */       dPrev = dN;
/* 168 */       cPrev = cN;
/* 169 */       hPrev = hN;
/* 170 */       n++;
/*     */     } 
/*     */     
/* 173 */     if (n >= maxIterations) {
/* 174 */       throw new MaxCountExceededException(LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION, Integer.valueOf(maxIterations), new Object[] { Double.valueOf(x) });
/*     */     }
/*     */ 
/*     */     
/* 178 */     return hN;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/ContinuedFraction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */