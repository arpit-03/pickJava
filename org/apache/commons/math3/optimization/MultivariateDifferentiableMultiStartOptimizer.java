/*    */ package org.apache.commons.math3.optimization;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableFunction;
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
/*    */ @Deprecated
/*    */ public class MultivariateDifferentiableMultiStartOptimizer
/*    */   extends BaseMultivariateMultiStartOptimizer<MultivariateDifferentiableFunction>
/*    */   implements MultivariateDifferentiableOptimizer
/*    */ {
/*    */   public MultivariateDifferentiableMultiStartOptimizer(MultivariateDifferentiableOptimizer optimizer, int starts, RandomVectorGenerator generator) {
/* 50 */     super(optimizer, starts, generator);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/MultivariateDifferentiableMultiStartOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */