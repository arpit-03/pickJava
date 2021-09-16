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
/*     */ public class MullerSolver
/*     */   extends AbstractUnivariateSolver
/*     */ {
/*     */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   
/*     */   public MullerSolver() {
/*  58 */     this(1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MullerSolver(double absoluteAccuracy) {
/*  66 */     super(absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MullerSolver(double relativeAccuracy, double absoluteAccuracy) {
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
/*  89 */     double initial = getStartValue();
/*     */     
/*  91 */     double functionValueAccuracy = getFunctionValueAccuracy();
/*     */     
/*  93 */     verifySequence(min, initial, max);
/*     */ 
/*     */     
/*  96 */     double fMin = computeObjectiveValue(min);
/*  97 */     if (FastMath.abs(fMin) < functionValueAccuracy) {
/*  98 */       return min;
/*     */     }
/* 100 */     double fMax = computeObjectiveValue(max);
/* 101 */     if (FastMath.abs(fMax) < functionValueAccuracy) {
/* 102 */       return max;
/*     */     }
/* 104 */     double fInitial = computeObjectiveValue(initial);
/* 105 */     if (FastMath.abs(fInitial) < functionValueAccuracy) {
/* 106 */       return initial;
/*     */     }
/*     */     
/* 109 */     verifyBracketing(min, max);
/*     */     
/* 111 */     if (isBracketing(min, initial)) {
/* 112 */       return solve(min, initial, fMin, fInitial);
/*     */     }
/* 114 */     return solve(initial, max, fInitial, fMax);
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
/*     */   private double solve(double min, double max, double fMin, double fMax) throws TooManyEvaluationsException {
/* 132 */     double relativeAccuracy = getRelativeAccuracy();
/* 133 */     double absoluteAccuracy = getAbsoluteAccuracy();
/* 134 */     double functionValueAccuracy = getFunctionValueAccuracy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     double x0 = min;
/* 142 */     double y0 = fMin;
/* 143 */     double x2 = max;
/* 144 */     double y2 = fMax;
/* 145 */     double x1 = 0.5D * (x0 + x2);
/* 146 */     double y1 = computeObjectiveValue(x1);
/*     */     
/* 148 */     double oldx = Double.POSITIVE_INFINITY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 154 */       double d01 = (y1 - y0) / (x1 - x0);
/* 155 */       double d12 = (y2 - y1) / (x2 - x1);
/* 156 */       double d012 = (d12 - d01) / (x2 - x0);
/* 157 */       double c1 = d01 + (x1 - x0) * d012;
/* 158 */       double delta = c1 * c1 - 4.0D * y1 * d012;
/* 159 */       double xplus = x1 + -2.0D * y1 / (c1 + FastMath.sqrt(delta));
/* 160 */       double xminus = x1 + -2.0D * y1 / (c1 - FastMath.sqrt(delta));
/*     */ 
/*     */       
/* 163 */       double x = isSequence(x0, xplus, x2) ? xplus : xminus;
/* 164 */       double y = computeObjectiveValue(x);
/*     */ 
/*     */       
/* 167 */       double tolerance = FastMath.max(relativeAccuracy * FastMath.abs(x), absoluteAccuracy);
/* 168 */       if (FastMath.abs(x - oldx) <= tolerance || FastMath.abs(y) <= functionValueAccuracy)
/*     */       {
/* 170 */         return x;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       boolean bisect = ((x < x1 && x1 - x0 > 0.95D * (x2 - x0)) || (x > x1 && x2 - x1 > 0.95D * (x2 - x0)) || x == x1);
/*     */ 
/*     */ 
/*     */       
/* 181 */       if (!bisect) {
/* 182 */         x0 = (x < x1) ? x0 : x1;
/* 183 */         y0 = (x < x1) ? y0 : y1;
/* 184 */         x2 = (x > x1) ? x2 : x1;
/* 185 */         y2 = (x > x1) ? y2 : y1;
/* 186 */         x1 = x; y1 = y;
/* 187 */         oldx = x; continue;
/*     */       } 
/* 189 */       double xm = 0.5D * (x0 + x2);
/* 190 */       double ym = computeObjectiveValue(xm);
/* 191 */       if (FastMath.signum(y0) + FastMath.signum(ym) == 0.0D) {
/* 192 */         x2 = xm; y2 = ym;
/*     */       } else {
/* 194 */         x0 = xm; y0 = ym;
/*     */       } 
/* 196 */       x1 = 0.5D * (x0 + x2);
/* 197 */       y1 = computeObjectiveValue(x1);
/* 198 */       oldx = Double.POSITIVE_INFINITY;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/MullerSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */