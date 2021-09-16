/*     */ package org.apache.commons.math3.optimization.linear;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
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
/*     */ @Deprecated
/*     */ public class LinearObjectiveFunction
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4531815507568396090L;
/*     */   private final transient RealVector coefficients;
/*     */   private final double constantTerm;
/*     */   
/*     */   public LinearObjectiveFunction(double[] coefficients, double constantTerm) {
/*  59 */     this((RealVector)new ArrayRealVector(coefficients), constantTerm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinearObjectiveFunction(RealVector coefficients, double constantTerm) {
/*  67 */     this.coefficients = coefficients;
/*  68 */     this.constantTerm = constantTerm;
/*     */   }
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
/*     */   public double getConstantTerm() {
/*  84 */     return this.constantTerm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue(double[] point) {
/*  93 */     return this.coefficients.dotProduct((RealVector)new ArrayRealVector(point, false)) + this.constantTerm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue(RealVector point) {
/* 102 */     return this.coefficients.dotProduct(point) + this.constantTerm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 109 */     if (this == other) {
/* 110 */       return true;
/*     */     }
/*     */     
/* 113 */     if (other instanceof LinearObjectiveFunction) {
/* 114 */       LinearObjectiveFunction rhs = (LinearObjectiveFunction)other;
/* 115 */       return (this.constantTerm == rhs.constantTerm && this.coefficients.equals(rhs.coefficients));
/*     */     } 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     return Double.valueOf(this.constantTerm).hashCode() ^ this.coefficients.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream oos) throws IOException {
/* 134 */     oos.defaultWriteObject();
/* 135 */     MatrixUtils.serializeRealVector(this.coefficients, oos);
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
/* 146 */     ois.defaultReadObject();
/* 147 */     MatrixUtils.deserializeRealVector(this, "coefficients", ois);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/LinearObjectiveFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */