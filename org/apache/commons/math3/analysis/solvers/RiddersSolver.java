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
/*     */ public class RiddersSolver
/*     */   extends AbstractUnivariateSolver
/*     */ {
/*     */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   
/*     */   public RiddersSolver() {
/*  42 */     this(1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RiddersSolver(double absoluteAccuracy) {
/*  50 */     super(absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RiddersSolver(double relativeAccuracy, double absoluteAccuracy) {
/*  60 */     super(relativeAccuracy, absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doSolve() throws TooManyEvaluationsException, NoBracketingException {
/*  70 */     double min = getMin();
/*  71 */     double max = getMax();
/*     */ 
/*     */ 
/*     */     
/*  75 */     double x1 = min;
/*  76 */     double y1 = computeObjectiveValue(x1);
/*  77 */     double x2 = max;
/*  78 */     double y2 = computeObjectiveValue(x2);
/*     */ 
/*     */     
/*  81 */     if (y1 == 0.0D) {
/*  82 */       return min;
/*     */     }
/*  84 */     if (y2 == 0.0D) {
/*  85 */       return max;
/*     */     }
/*  87 */     verifyBracketing(min, max);
/*     */     
/*  89 */     double absoluteAccuracy = getAbsoluteAccuracy();
/*  90 */     double functionValueAccuracy = getFunctionValueAccuracy();
/*  91 */     double relativeAccuracy = getRelativeAccuracy();
/*     */     
/*  93 */     double oldx = Double.POSITIVE_INFINITY;
/*     */     
/*     */     while (true) {
/*  96 */       double x3 = 0.5D * (x1 + x2);
/*  97 */       double y3 = computeObjectiveValue(x3);
/*  98 */       if (FastMath.abs(y3) <= functionValueAccuracy) {
/*  99 */         return x3;
/*     */       }
/* 101 */       double delta = 1.0D - y1 * y2 / y3 * y3;
/* 102 */       double correction = FastMath.signum(y2) * FastMath.signum(y3) * (x3 - x1) / FastMath.sqrt(delta);
/*     */       
/* 104 */       double x = x3 - correction;
/* 105 */       double y = computeObjectiveValue(x);
/*     */ 
/*     */       
/* 108 */       double tolerance = FastMath.max(relativeAccuracy * FastMath.abs(x), absoluteAccuracy);
/* 109 */       if (FastMath.abs(x - oldx) <= tolerance) {
/* 110 */         return x;
/*     */       }
/* 112 */       if (FastMath.abs(y) <= functionValueAccuracy) {
/* 113 */         return x;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (correction > 0.0D) {
/* 119 */         if (FastMath.signum(y1) + FastMath.signum(y) == 0.0D) {
/* 120 */           x2 = x;
/* 121 */           y2 = y;
/*     */         } else {
/* 123 */           x1 = x;
/* 124 */           x2 = x3;
/* 125 */           y1 = y;
/* 126 */           y2 = y3;
/*     */         }
/*     */       
/* 129 */       } else if (FastMath.signum(y2) + FastMath.signum(y) == 0.0D) {
/* 130 */         x1 = x;
/* 131 */         y1 = y;
/*     */       } else {
/* 133 */         x1 = x3;
/* 134 */         x2 = x;
/* 135 */         y1 = y3;
/* 136 */         y2 = y;
/*     */       } 
/*     */       
/* 139 */       oldx = x;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/RiddersSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */