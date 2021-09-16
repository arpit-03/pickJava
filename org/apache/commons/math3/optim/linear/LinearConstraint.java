/*     */ package org.apache.commons.math3.optim.linear;
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
/*     */ public class LinearConstraint
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -764632794033034092L;
/*     */   private final transient RealVector coefficients;
/*     */   private final Relationship relationship;
/*     */   private final double value;
/*     */   
/*     */   public LinearConstraint(double[] coefficients, Relationship relationship, double value) {
/*  75 */     this((RealVector)new ArrayRealVector(coefficients), relationship, value);
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
/*     */   public LinearConstraint(RealVector coefficients, Relationship relationship, double value) {
/*  95 */     this.coefficients = coefficients;
/*  96 */     this.relationship = relationship;
/*  97 */     this.value = value;
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
/* 122 */     double[] sub = new double[lhsCoefficients.length];
/* 123 */     for (int i = 0; i < sub.length; i++) {
/* 124 */       sub[i] = lhsCoefficients[i] - rhsCoefficients[i];
/*     */     }
/* 126 */     this.coefficients = (RealVector)new ArrayRealVector(sub, false);
/* 127 */     this.relationship = relationship;
/* 128 */     this.value = rhsConstant - lhsConstant;
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
/* 153 */     this.coefficients = lhsCoefficients.subtract(rhsCoefficients);
/* 154 */     this.relationship = relationship;
/* 155 */     this.value = rhsConstant - lhsConstant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getCoefficients() {
/* 164 */     return this.coefficients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Relationship getRelationship() {
/* 173 */     return this.relationship;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/* 182 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 188 */     if (this == other) {
/* 189 */       return true;
/*     */     }
/* 191 */     if (other instanceof LinearConstraint) {
/* 192 */       LinearConstraint rhs = (LinearConstraint)other;
/* 193 */       return (this.relationship == rhs.relationship && this.value == rhs.value && this.coefficients.equals(rhs.coefficients));
/*     */     } 
/*     */ 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 203 */     return this.relationship.hashCode() ^ Double.valueOf(this.value).hashCode() ^ this.coefficients.hashCode();
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
/* 215 */     oos.defaultWriteObject();
/* 216 */     MatrixUtils.serializeRealVector(this.coefficients, oos);
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
/* 227 */     ois.defaultReadObject();
/* 228 */     MatrixUtils.deserializeRealVector(this, "coefficients", ois);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/LinearConstraint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */