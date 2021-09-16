/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*    */ import org.apache.commons.math3.util.FastMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BisectionSolver
/*    */   extends AbstractUnivariateSolver
/*    */ {
/*    */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*    */   
/*    */   public BisectionSolver() {
/* 37 */     this(1.0E-6D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BisectionSolver(double absoluteAccuracy) {
/* 45 */     super(absoluteAccuracy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BisectionSolver(double relativeAccuracy, double absoluteAccuracy) {
/* 55 */     super(relativeAccuracy, absoluteAccuracy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double doSolve() throws TooManyEvaluationsException {
/* 64 */     double min = getMin();
/* 65 */     double max = getMax();
/* 66 */     verifyInterval(min, max);
/* 67 */     double absoluteAccuracy = getAbsoluteAccuracy();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/* 73 */       double m = UnivariateSolverUtils.midpoint(min, max);
/* 74 */       double fmin = computeObjectiveValue(min);
/* 75 */       double fm = computeObjectiveValue(m);
/*    */       
/* 77 */       if (fm * fmin > 0.0D) {
/*    */         
/* 79 */         min = m;
/*    */       } else {
/*    */         
/* 82 */         max = m;
/*    */       } 
/*    */       
/* 85 */       if (FastMath.abs(max - min) <= absoluteAccuracy) {
/* 86 */         m = UnivariateSolverUtils.midpoint(min, max);
/* 87 */         return m;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BisectionSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */