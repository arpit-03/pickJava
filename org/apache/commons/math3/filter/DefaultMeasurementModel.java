/*    */ package org.apache.commons.math3.filter;
/*    */ 
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.exception.NoDataException;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
/*    */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultMeasurementModel
/*    */   implements MeasurementModel
/*    */ {
/*    */   private RealMatrix measurementMatrix;
/*    */   private RealMatrix measurementNoise;
/*    */   
/*    */   public DefaultMeasurementModel(double[][] measMatrix, double[][] measNoise) throws NullArgumentException, NoDataException, DimensionMismatchException {
/* 60 */     this((RealMatrix)new Array2DRowRealMatrix(measMatrix), (RealMatrix)new Array2DRowRealMatrix(measNoise));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultMeasurementModel(RealMatrix measMatrix, RealMatrix measNoise) {
/* 71 */     this.measurementMatrix = measMatrix;
/* 72 */     this.measurementNoise = measNoise;
/*    */   }
/*    */ 
/*    */   
/*    */   public RealMatrix getMeasurementMatrix() {
/* 77 */     return this.measurementMatrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public RealMatrix getMeasurementNoise() {
/* 82 */     return this.measurementNoise;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/filter/DefaultMeasurementModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */