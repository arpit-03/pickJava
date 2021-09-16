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
/*    */ public class IllinoisSolver
/*    */   extends BaseSecantSolver
/*    */ {
/*    */   public IllinoisSolver() {
/* 47 */     super(1.0E-6D, BaseSecantSolver.Method.ILLINOIS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IllinoisSolver(double absoluteAccuracy) {
/* 56 */     super(absoluteAccuracy, BaseSecantSolver.Method.ILLINOIS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IllinoisSolver(double relativeAccuracy, double absoluteAccuracy) {
/* 67 */     super(relativeAccuracy, absoluteAccuracy, BaseSecantSolver.Method.ILLINOIS);
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
/*    */   public IllinoisSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 80 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, BaseSecantSolver.Method.PEGASUS);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/IllinoisSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */