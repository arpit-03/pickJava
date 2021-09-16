/*    */ package org.apache.commons.math3.linear;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MultiDimensionMismatchException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MatrixDimensionMismatchException
/*    */   extends MultiDimensionMismatchException
/*    */ {
/*    */   private static final long serialVersionUID = -8415396756375798143L;
/*    */   
/*    */   public MatrixDimensionMismatchException(int wrongRowDim, int wrongColDim, int expectedRowDim, int expectedColDim) {
/* 44 */     super((Localizable)LocalizedFormats.DIMENSIONS_MISMATCH_2x2, new Integer[] { Integer.valueOf(wrongRowDim), Integer.valueOf(wrongColDim) }, new Integer[] { Integer.valueOf(expectedRowDim), Integer.valueOf(expectedColDim) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWrongRowDimension() {
/* 53 */     return getWrongDimension(0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExpectedRowDimension() {
/* 59 */     return getExpectedDimension(0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWrongColumnDimension() {
/* 65 */     return getWrongDimension(1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExpectedColumnDimension() {
/* 71 */     return getExpectedDimension(1);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/MatrixDimensionMismatchException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */