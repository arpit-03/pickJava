/*    */ package org.apache.commons.math3.optimization;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
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
/*    */ public class MultivariateDifferentiableVectorMultiStartOptimizer
/*    */   extends BaseMultivariateVectorMultiStartOptimizer<MultivariateDifferentiableVectorFunction>
/*    */   implements MultivariateDifferentiableVectorOptimizer
/*    */ {
/*    */   public MultivariateDifferentiableVectorMultiStartOptimizer(MultivariateDifferentiableVectorOptimizer optimizer, int starts, RandomVectorGenerator generator) {
/* 51 */     super(optimizer, starts, generator);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/MultivariateDifferentiableVectorMultiStartOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */