/*     */ package org.apache.commons.math3.optim.linear;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
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
/*     */ public class LinearObjectiveFunction
/*     */   implements MultivariateFunction, OptimizationData, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4531815507568396090L;
/*     */   private final transient RealVector coefficients;
/*     */   private final double constantTerm;
/*     */   
/*     */   public LinearObjectiveFunction(double[] coefficients, double constantTerm) {
/*  58 */     this((RealVector)new ArrayRealVector(coefficients), constantTerm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinearObjectiveFunction(RealVector coefficients, double constantTerm) {
/*  66 */     this.coefficients = coefficients;
/*  67 */     this.constantTerm = constantTerm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getCoefficients() {
/*  76 */     return this.coefficients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConstantTerm() {
/*  85 */     return this.constantTerm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double[] point) {
/*  95 */     return value((RealVector)new ArrayRealVector(point, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(RealVector point) {
/* 105 */     return this.coefficients.dotProduct(point) + this.constantTerm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 111 */     if (this == other) {
/* 112 */       return true;
/*     */     }
/* 114 */     if (other instanceof LinearObjectiveFunction) {
/* 115 */       LinearObjectiveFunction rhs = (LinearObjectiveFunction)other;
/* 116 */       return (this.constantTerm == rhs.constantTerm && this.coefficients.equals(rhs.coefficients));
/*     */     } 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Double.valueOf(this.constantTerm).hashCode() ^ this.coefficients.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream oos) throws IOException {
/* 135 */     oos.defaultWriteObject();
/* 136 */     MatrixUtils.serializeRealVector(this.coefficients, oos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
/* 147 */     ois.defaultReadObject();
/* 148 */     MatrixUtils.deserializeRealVector(this, "coefficients", ois);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/LinearObjectiveFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */