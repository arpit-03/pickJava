/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
/*     */ public class MullerSolver2
/*     */   extends AbstractUnivariateSolver
/*     */ {
/*     */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   
/*     */   public MullerSolver2() {
/*  58 */     this(1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MullerSolver2(double absoluteAccuracy) {
/*  66 */     super(absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MullerSolver2(double relativeAccuracy, double absoluteAccuracy) {
/*  76 */     super(relativeAccuracy, absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doSolve() throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
/*  87 */     double min = getMin();
/*  88 */     double max = getMax();
/*     */     
/*  90 */     verifyInterval(min, max);
/*     */     
/*  92 */     double relativeAccuracy = getRelativeAccuracy();
/*  93 */     double absoluteAccuracy = getAbsoluteAccuracy();
/*  94 */     double functionValueAccuracy = getFunctionValueAccuracy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     double x0 = min;
/* 101 */     double y0 = computeObjectiveValue(x0);
/* 102 */     if (FastMath.abs(y0) < functionValueAccuracy) {
/* 103 */       return x0;
/*     */     }
/* 105 */     double x1 = max;
/* 106 */     double y1 = computeObjectiveValue(x1);
/* 107 */     if (FastMath.abs(y1) < functionValueAccuracy) {
/* 108 */       return x1;
/*     */     }
/*     */     
/* 111 */     if (y0 * y1 > 0.0D) {
/* 112 */       throw new NoBracketingException(x0, x1, y0, y1);
/*     */     }
/*     */     
/* 115 */     double x2 = 0.5D * (x0 + x1);
/* 116 */     double y2 = computeObjectiveValue(x2);
/*     */     
/* 118 */     double oldx = Double.POSITIVE_INFINITY;
/*     */     
/*     */     while (true) {
/* 121 */       double x, denominator, q = (x2 - x1) / (x1 - x0);
/* 122 */       double a = q * (y2 - (1.0D + q) * y1 + q * y0);
/* 123 */       double b = (2.0D * q + 1.0D) * y2 - (1.0D + q) * (1.0D + q) * y1 + q * q * y0;
/* 124 */       double c = (1.0D + q) * y2;
/* 125 */       double delta = b * b - 4.0D * a * c;
/*     */ 
/*     */       
/* 128 */       if (delta >= 0.0D) {
/*     */         
/* 130 */         double dplus = b + FastMath.sqrt(delta);
/* 131 */         double dminus = b - FastMath.sqrt(delta);
/* 132 */         denominator = (FastMath.abs(dplus) > FastMath.abs(dminus)) ? dplus : dminus;
/*     */       } else {
/*     */         
/* 135 */         denominator = FastMath.sqrt(b * b - delta);
/*     */       } 
/* 137 */       if (denominator != 0.0D) {
/* 138 */         x = x2 - 2.0D * c * (x2 - x1) / denominator;
/*     */ 
/*     */         
/* 141 */         while (x == x1 || x == x2) {
/* 142 */           x += absoluteAccuracy;
/*     */         }
/*     */       } else {
/*     */         
/* 146 */         x = min + FastMath.random() * (max - min);
/* 147 */         oldx = Double.POSITIVE_INFINITY;
/*     */       } 
/* 149 */       double y = computeObjectiveValue(x);
/*     */ 
/*     */       
/* 152 */       double tolerance = FastMath.max(relativeAccuracy * FastMath.abs(x), absoluteAccuracy);
/* 153 */       if (FastMath.abs(x - oldx) <= tolerance || FastMath.abs(y) <= functionValueAccuracy)
/*     */       {
/* 155 */         return x;
/*     */       }
/*     */ 
/*     */       
/* 159 */       x0 = x1;
/* 160 */       y0 = y1;
/* 161 */       x1 = x2;
/* 162 */       y1 = y2;
/* 163 */       x2 = x;
/* 164 */       y2 = y;
/* 165 */       oldx = x;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/MullerSolver2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */