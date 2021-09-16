/*    */ package org.apache.commons.math3.optim.linear;
/*    */ 
/*    */ import org.apache.commons.math3.optim.OptimizationData;
/*    */ import org.apache.commons.math3.optim.PointValuePair;
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
/*    */ public class SolutionCallback
/*    */   implements OptimizationData
/*    */ {
/*    */   private SimplexTableau tableau;
/*    */   
/*    */   void setTableau(SimplexTableau tableau) {
/* 39 */     this.tableau = tableau;
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
/*    */   public PointValuePair getSolution() {
/* 52 */     return (this.tableau != null) ? this.tableau.getSolution() : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolutionOptimal() {
/* 60 */     return (this.tableau != null) ? this.tableau.isOptimal() : false;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/SolutionCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */