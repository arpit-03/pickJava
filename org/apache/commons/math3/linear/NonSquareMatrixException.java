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
/*    */ 
/*    */ public class NonSquareMatrixException
/*    */   extends DimensionMismatchException
/*    */ {
/*    */   private static final long serialVersionUID = -660069396594485772L;
/*    */   
/*    */   public NonSquareMatrixException(int wrong, int expected) {
/* 39 */     super((Localizable)LocalizedFormats.NON_SQUARE_MATRIX, wrong, expected);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/NonSquareMatrixException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */