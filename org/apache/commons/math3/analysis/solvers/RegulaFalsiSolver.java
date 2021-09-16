/*    */ package org.apache.commons.math3.analysis.solvers;
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
/*    */ 
/*    */ public class RegulaFalsiSolver
/*    */   extends BaseSecantSolver
/*    */ {
/*    */   public RegulaFalsiSolver() {
/* 59 */     super(1.0E-6D, BaseSecantSolver.Method.REGULA_FALSI);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RegulaFalsiSolver(double absoluteAccuracy) {
/* 68 */     super(absoluteAccuracy, BaseSecantSolver.Method.REGULA_FALSI);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RegulaFalsiSolver(double relativeAccuracy, double absoluteAccuracy) {
/* 79 */     super(relativeAccuracy, absoluteAccuracy, BaseSecantSolver.Method.REGULA_FALSI);
/*    */   }
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
/*    */   public RegulaFalsiSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 92 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, BaseSecantSolver.Method.REGULA_FALSI);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/RegulaFalsiSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */