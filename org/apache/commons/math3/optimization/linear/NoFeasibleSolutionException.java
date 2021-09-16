/*    */ package org.apache.commons.math3.optimization.linear;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*    */ import org.apache.commons.math3.exception.util.Localizable;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*    */ public class NoFeasibleSolutionException
/*    */   extends MathIllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = -3044253632189082760L;
/*    */   
/*    */   public NoFeasibleSolutionException() {
/* 39 */     super((Localizable)LocalizedFormats.NO_FEASIBLE_SOLUTION, new Object[0]);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/NoFeasibleSolutionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */