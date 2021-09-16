/*    */ package org.apache.commons.math3.ode;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
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
/*    */ public class EquationsMapper
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 20110925L;
/*    */   private final int firstIndex;
/*    */   private final int dimension;
/*    */   
/*    */   public EquationsMapper(int firstIndex, int dimension) {
/* 49 */     this.firstIndex = firstIndex;
/* 50 */     this.dimension = dimension;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFirstIndex() {
/* 57 */     return this.firstIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 64 */     return this.dimension;
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
/*    */   public void extractEquationData(double[] complete, double[] equationData) throws DimensionMismatchException {
/* 76 */     if (equationData.length != this.dimension) {
/* 77 */       throw new DimensionMismatchException(equationData.length, this.dimension);
/*    */     }
/* 79 */     System.arraycopy(complete, this.firstIndex, equationData, 0, this.dimension);
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
/*    */   public void insertEquationData(double[] equationData, double[] complete) throws DimensionMismatchException {
/* 91 */     if (equationData.length != this.dimension) {
/* 92 */       throw new DimensionMismatchException(equationData.length, this.dimension);
/*    */     }
/* 94 */     System.arraycopy(equationData, 0, complete, this.firstIndex, this.dimension);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/EquationsMapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */