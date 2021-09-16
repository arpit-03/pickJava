/*     */ package org.apache.commons.math3.filter;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.linear.AnyMatrix;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.CholeskyDecomposition;
/*     */ import org.apache.commons.math3.linear.MatrixDimensionMismatchException;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.NonSquareMatrixException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.linear.SingularMatrixException;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KalmanFilter
/*     */ {
/*     */   private final ProcessModel processModel;
/*     */   private final MeasurementModel measurementModel;
/*     */   private RealMatrix transitionMatrix;
/*     */   private RealMatrix transitionMatrixT;
/*     */   private RealMatrix controlMatrix;
/*     */   private RealMatrix measurementMatrix;
/*     */   private RealMatrix measurementMatrixT;
/*     */   private RealVector stateEstimation;
/*     */   private RealMatrix errorCovariance;
/*     */   
/*     */   public KalmanFilter(ProcessModel process, MeasurementModel measurement) throws NullArgumentException, NonSquareMatrixException, DimensionMismatchException, MatrixDimensionMismatchException {
/* 123 */     MathUtils.checkNotNull(process);
/* 124 */     MathUtils.checkNotNull(measurement);
/*     */     
/* 126 */     this.processModel = process;
/* 127 */     this.measurementModel = measurement;
/*     */     
/* 129 */     this.transitionMatrix = this.processModel.getStateTransitionMatrix();
/* 130 */     MathUtils.checkNotNull(this.transitionMatrix);
/* 131 */     this.transitionMatrixT = this.transitionMatrix.transpose();
/*     */ 
/*     */     
/* 134 */     if (this.processModel.getControlMatrix() == null) {
/* 135 */       this.controlMatrix = (RealMatrix)new Array2DRowRealMatrix();
/*     */     } else {
/* 137 */       this.controlMatrix = this.processModel.getControlMatrix();
/*     */     } 
/*     */     
/* 140 */     this.measurementMatrix = this.measurementModel.getMeasurementMatrix();
/* 141 */     MathUtils.checkNotNull(this.measurementMatrix);
/* 142 */     this.measurementMatrixT = this.measurementMatrix.transpose();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     RealMatrix processNoise = this.processModel.getProcessNoise();
/* 148 */     MathUtils.checkNotNull(processNoise);
/* 149 */     RealMatrix measNoise = this.measurementModel.getMeasurementNoise();
/* 150 */     MathUtils.checkNotNull(measNoise);
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (this.processModel.getInitialStateEstimate() == null) {
/* 155 */       this.stateEstimation = (RealVector)new ArrayRealVector(this.transitionMatrix.getColumnDimension());
/*     */     } else {
/* 157 */       this.stateEstimation = this.processModel.getInitialStateEstimate();
/*     */     } 
/*     */     
/* 160 */     if (this.transitionMatrix.getColumnDimension() != this.stateEstimation.getDimension()) {
/* 161 */       throw new DimensionMismatchException(this.transitionMatrix.getColumnDimension(), this.stateEstimation.getDimension());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (this.processModel.getInitialErrorCovariance() == null) {
/* 168 */       this.errorCovariance = processNoise.copy();
/*     */     } else {
/* 170 */       this.errorCovariance = this.processModel.getInitialErrorCovariance();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (!this.transitionMatrix.isSquare()) {
/* 177 */       throw new NonSquareMatrixException(this.transitionMatrix.getRowDimension(), this.transitionMatrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (this.controlMatrix != null && this.controlMatrix.getRowDimension() > 0 && this.controlMatrix.getColumnDimension() > 0 && this.controlMatrix.getRowDimension() != this.transitionMatrix.getRowDimension())
/*     */     {
/*     */ 
/*     */       
/* 188 */       throw new MatrixDimensionMismatchException(this.controlMatrix.getRowDimension(), this.controlMatrix.getColumnDimension(), this.transitionMatrix.getRowDimension(), this.controlMatrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     MatrixUtils.checkAdditionCompatible((AnyMatrix)this.transitionMatrix, (AnyMatrix)processNoise);
/*     */ 
/*     */     
/* 198 */     if (this.measurementMatrix.getColumnDimension() != this.transitionMatrix.getRowDimension()) {
/* 199 */       throw new MatrixDimensionMismatchException(this.measurementMatrix.getRowDimension(), this.measurementMatrix.getColumnDimension(), this.measurementMatrix.getRowDimension(), this.transitionMatrix.getRowDimension());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     if (measNoise.getRowDimension() != this.measurementMatrix.getRowDimension()) {
/* 207 */       throw new MatrixDimensionMismatchException(measNoise.getRowDimension(), measNoise.getColumnDimension(), this.measurementMatrix.getRowDimension(), measNoise.getColumnDimension());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStateDimension() {
/* 220 */     return this.stateEstimation.getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMeasurementDimension() {
/* 229 */     return this.measurementMatrix.getRowDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getStateEstimation() {
/* 238 */     return this.stateEstimation.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getStateEstimationVector() {
/* 247 */     return this.stateEstimation.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getErrorCovariance() {
/* 256 */     return this.errorCovariance.getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getErrorCovarianceMatrix() {
/* 265 */     return this.errorCovariance.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void predict() {
/* 272 */     predict((RealVector)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void predict(double[] u) throws DimensionMismatchException {
/* 284 */     predict((RealVector)new ArrayRealVector(u, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void predict(RealVector u) throws DimensionMismatchException {
/* 297 */     if (u != null && u.getDimension() != this.controlMatrix.getColumnDimension())
/*     */     {
/* 299 */       throw new DimensionMismatchException(u.getDimension(), this.controlMatrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     this.stateEstimation = this.transitionMatrix.operate(this.stateEstimation);
/*     */ 
/*     */     
/* 308 */     if (u != null) {
/* 309 */       this.stateEstimation = this.stateEstimation.add(this.controlMatrix.operate(u));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 314 */     this.errorCovariance = this.transitionMatrix.multiply(this.errorCovariance).multiply(this.transitionMatrixT).add(this.processModel.getProcessNoise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void correct(double[] z) throws NullArgumentException, DimensionMismatchException, SingularMatrixException {
/* 333 */     correct((RealVector)new ArrayRealVector(z, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void correct(RealVector z) throws NullArgumentException, DimensionMismatchException, SingularMatrixException {
/* 352 */     MathUtils.checkNotNull(z);
/* 353 */     if (z.getDimension() != this.measurementMatrix.getRowDimension()) {
/* 354 */       throw new DimensionMismatchException(z.getDimension(), this.measurementMatrix.getRowDimension());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 359 */     RealMatrix s = this.measurementMatrix.multiply(this.errorCovariance).multiply(this.measurementMatrixT).add(this.measurementModel.getMeasurementNoise());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     RealVector innovation = z.subtract(this.measurementMatrix.operate(this.stateEstimation));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     RealMatrix kalmanGain = (new CholeskyDecomposition(s)).getSolver().solve(this.measurementMatrix.multiply(this.errorCovariance.transpose())).transpose();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     this.stateEstimation = this.stateEstimation.add(kalmanGain.operate(innovation));
/*     */ 
/*     */ 
/*     */     
/* 385 */     RealMatrix identity = MatrixUtils.createRealIdentityMatrix(kalmanGain.getRowDimension());
/* 386 */     this.errorCovariance = identity.subtract(kalmanGain.multiply(this.measurementMatrix)).multiply(this.errorCovariance);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/filter/KalmanFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */