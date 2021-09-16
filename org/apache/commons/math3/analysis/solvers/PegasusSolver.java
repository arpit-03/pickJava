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
/*    */ public class PegasusSolver
/*    */   extends BaseSecantSolver
/*    */ {
/*    */   public PegasusSolver() {
/* 49 */     super(1.0E-6D, BaseSecantSolver.Method.PEGASUS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PegasusSolver(double absoluteAccuracy) {
/* 58 */     super(absoluteAccuracy, BaseSecantSolver.Method.PEGASUS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PegasusSolver(double relativeAccuracy, double absoluteAccuracy) {
/* 69 */     super(relativeAccuracy, absoluteAccuracy, BaseSecantSolver.Method.PEGASUS);
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
/*    */   public PegasusSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 82 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, BaseSecantSolver.Method.PEGASUS);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/PegasusSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */