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
/*     */ public class LinearConstraint
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -764632794033034092L;
/*     */   private final transient RealVector coefficients;
/*     */   private final Relationship relationship;
/*     */   private final double value;
/*     */   
/*     */   public LinearConstraint(double[] coefficients, Relationship relationship, double value) {
/*  82 */     this((RealVector)new ArrayRealVector(coefficients), relationship, value);
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
/*     */   public LinearConstraint(RealVector coefficients, Relationship relationship, double value) {
/* 101 */     this.coefficients = coefficients;
/* 102 */     this.relationship = relationship;
/* 103 */     this.value = value;
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
/*     */   public LinearConstraint(double[] lhsCoefficients, double lhsConstant, Relationship relationship, double[] rhsCoefficients, double rhsConstant) {
/* 128 */     double[] sub = new double[lhsCoefficients.length];
/* 129 */     for (int i = 0; i < sub.length; i++) {
/* 130 */       sub[i] = lhsCoefficients[i] - rhsCoefficients[i];
/*     */     }
/* 132 */     this.coefficients = (RealVector)new ArrayRealVector(sub, false);
/* 133 */     this.relationship = relationship;
/* 134 */     this.value = rhsConstant - lhsConstant;
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
/*     */   public LinearConstraint(RealVector lhsCoefficients, double lhsConstant, Relationship relationship, RealVector rhsCoefficients, double rhsConstant) {
/* 159 */     this.coefficients = lhsCoefficients.subtract(rhsCoefficients);
/* 160 */     this.relationship = relationship;
/* 161 */     this.value = rhsConstant - lhsConstant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getCoefficients() {
/* 169 */     return this.coefficients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Relationship getRelationship() {
/* 177 */     return this.relationship;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/* 185 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 192 */     if (this == other) {
/* 193 */       return true;
/*     */     }
/*     */     
/* 196 */     if (other instanceof LinearConstraint) {
/* 197 */       LinearConstraint rhs = (LinearConstraint)other;
/* 198 */       return (this.relationship == rhs.relationship && this.value == rhs.value && this.coefficients.equals(rhs.coefficients));
/*     */     } 
/*     */ 
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 208 */     return this.relationship.hashCode() ^ Double.valueOf(this.value).hashCode() ^ this.coefficients.hashCode();
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
/*     */   private void writeObject(ObjectOutputStream oos) throws IOException {
/* 220 */     oos.defaultWriteObject();
/* 221 */     MatrixUtils.serializeRealVector(this.coefficients, oos);
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
/* 232 */     ois.defaultReadObject();
/* 233 */     MatrixUtils.deserializeRealVector(this, "coefficients", ois);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/LinearConstraint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */