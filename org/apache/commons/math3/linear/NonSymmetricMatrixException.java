/*    */ package org.apache.commons.math3.linear;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*    */ public class NonSymmetricMatrixException
/*    */   extends MathIllegalArgumentException
/*    */ {
/*    */   private static final long serialVersionUID = -7518495577824189882L;
/*    */   private final int row;
/*    */   private final int column;
/*    */   private final double threshold;
/*    */   
/*    */   public NonSymmetricMatrixException(int row, int column, double threshold) {
/* 47 */     super((Localizable)LocalizedFormats.NON_SYMMETRIC_MATRIX, new Object[] { Integer.valueOf(row), Integer.valueOf(column), Double.valueOf(threshold) });
/* 48 */     this.row = row;
/* 49 */     this.column = column;
/* 50 */     this.threshold = threshold;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRow() {
/* 57 */     return this.row;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getColumn() {
/* 63 */     return this.column;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getThreshold() {
/* 69 */     return this.threshold;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/NonSymmetricMatrixException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */