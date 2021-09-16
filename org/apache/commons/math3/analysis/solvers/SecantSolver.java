/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
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
/*     */ public class SecantSolver
/*     */   extends AbstractUnivariateSolver
/*     */ {
/*     */   protected static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   
/*     */   public SecantSolver() {
/*  49 */     super(1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecantSolver(double absoluteAccuracy) {
/*  58 */     super(absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecantSolver(double relativeAccuracy, double absoluteAccuracy) {
/*  69 */     super(relativeAccuracy, absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double doSolve() throws TooManyEvaluationsException, NoBracketingException {
/*  78 */     double x0 = getMin();
/*  79 */     double x1 = getMax();
/*  80 */     double f0 = computeObjectiveValue(x0);
/*  81 */     double f1 = computeObjectiveValue(x1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     if (f0 == 0.0D) {
/*  87 */       return x0;
/*     */     }
/*  89 */     if (f1 == 0.0D) {
/*  90 */       return x1;
/*     */     }
/*     */ 
/*     */     
/*  94 */     verifyBracketing(x0, x1);
/*     */ 
/*     */     
/*  97 */     double ftol = getFunctionValueAccuracy();
/*  98 */     double atol = getAbsoluteAccuracy();
/*  99 */     double rtol = getRelativeAccuracy();
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 104 */       double x = x1 - f1 * (x1 - x0) / (f1 - f0);
/* 105 */       double fx = computeObjectiveValue(x);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (fx == 0.0D) {
/* 111 */         return x;
/*     */       }
/*     */ 
/*     */       
/* 115 */       x0 = x1;
/* 116 */       f0 = f1;
/* 117 */       x1 = x;
/* 118 */       f1 = fx;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       if (FastMath.abs(f1) <= ftol) {
/* 124 */         return x1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 129 */       if (FastMath.abs(x1 - x0) < FastMath.max(rtol * FastMath.abs(x1), atol))
/* 130 */         return x1; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/SecantSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */