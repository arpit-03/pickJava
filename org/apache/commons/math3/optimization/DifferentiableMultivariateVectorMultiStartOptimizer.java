/*    */ package org.apache.commons.math3.optimization;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
/*    */ import org.apache.commons.math3.random.RandomVectorGenerator;
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
/*    */ @Deprecated
/*    */ public class DifferentiableMultivariateVectorMultiStartOptimizer
/*    */   extends BaseMultivariateVectorMultiStartOptimizer<DifferentiableMultivariateVectorFunction>
/*    */   implements DifferentiableMultivariateVectorOptimizer
/*    */ {
/*    */   public DifferentiableMultivariateVectorMultiStartOptimizer(DifferentiableMultivariateVectorOptimizer optimizer, int starts, RandomVectorGenerator generator) {
/* 51 */     super(optimizer, starts, generator);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/DifferentiableMultivariateVectorMultiStartOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */