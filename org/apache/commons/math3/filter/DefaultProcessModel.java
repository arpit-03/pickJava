/*     */ package org.apache.commons.math3.filter;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
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
/*     */ public class DefaultProcessModel
/*     */   implements ProcessModel
/*     */ {
/*     */   private RealMatrix stateTransitionMatrix;
/*     */   private RealMatrix controlMatrix;
/*     */   private RealMatrix processNoiseCovMatrix;
/*     */   private RealVector initialStateEstimateVector;
/*     */   private RealMatrix initialErrorCovMatrix;
/*     */   
/*     */   public DefaultProcessModel(double[][] stateTransition, double[][] control, double[][] processNoise, double[] initialStateEstimate, double[][] initialErrorCovariance) throws NullArgumentException, NoDataException, DimensionMismatchException {
/*  79 */     this((RealMatrix)new Array2DRowRealMatrix(stateTransition), (RealMatrix)new Array2DRowRealMatrix(control), (RealMatrix)new Array2DRowRealMatrix(processNoise), (RealVector)new ArrayRealVector(initialStateEstimate), (RealMatrix)new Array2DRowRealMatrix(initialErrorCovariance));
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
/*     */   public DefaultProcessModel(double[][] stateTransition, double[][] control, double[][] processNoise) throws NullArgumentException, NoDataException, DimensionMismatchException {
/* 110 */     this((RealMatrix)new Array2DRowRealMatrix(stateTransition), (RealMatrix)new Array2DRowRealMatrix(control), (RealMatrix)new Array2DRowRealMatrix(processNoise), (RealVector)null, (RealMatrix)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultProcessModel(RealMatrix stateTransition, RealMatrix control, RealMatrix processNoise, RealVector initialStateEstimate, RealMatrix initialErrorCovariance) {
/* 134 */     this.stateTransitionMatrix = stateTransition;
/* 135 */     this.controlMatrix = control;
/* 136 */     this.processNoiseCovMatrix = processNoise;
/* 137 */     this.initialStateEstimateVector = initialStateEstimate;
/* 138 */     this.initialErrorCovMatrix = initialErrorCovariance;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix getStateTransitionMatrix() {
/* 143 */     return this.stateTransitionMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix getControlMatrix() {
/* 148 */     return this.controlMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix getProcessNoise() {
/* 153 */     return this.processNoiseCovMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealVector getInitialStateEstimate() {
/* 158 */     return this.initialStateEstimateVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix getInitialErrorCovariance() {
/* 163 */     return this.initialErrorCovMatrix;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/filter/DefaultProcessModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */