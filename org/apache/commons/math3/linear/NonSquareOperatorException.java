/*    */ package org.apache.commons.math3.linear;
/*    */ 
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
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
/*    */ public class NonSquareOperatorException
/*    */   extends DimensionMismatchException
/*    */ {
/*    */   private static final long serialVersionUID = -4145007524150846242L;
/*    */   
/*    */   public NonSquareOperatorException(int wrong, int expected) {
/* 38 */     super((Localizable)LocalizedFormats.NON_SQUARE_OPERATOR, wrong, expected);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/NonSquareOperatorException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */