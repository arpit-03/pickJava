/*     */ package org.jfree.data.function;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolynomialFunction2D
/*     */   implements Function2D, Serializable
/*     */ {
/*     */   private double[] coefficients;
/*     */   
/*     */   public PolynomialFunction2D(double[] coefficients) {
/*  69 */     ParamChecks.nullNotPermitted(coefficients, "coefficients");
/*  70 */     this.coefficients = (double[])coefficients.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getCoefficients() {
/*  80 */     return (double[])this.coefficients.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/*  89 */     return this.coefficients.length - 1;
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
/*     */   public double getValue(double x) {
/* 101 */     double y = 0.0D;
/* 102 */     for (int i = 0; i < this.coefficients.length; i++) {
/* 103 */       y += this.coefficients[i] * Math.pow(x, i);
/*     */     }
/* 105 */     return y;
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
/*     */   public boolean equals(Object obj) {
/* 117 */     if (!(obj instanceof PolynomialFunction2D)) {
/* 118 */       return false;
/*     */     }
/* 120 */     PolynomialFunction2D that = (PolynomialFunction2D)obj;
/* 121 */     return Arrays.equals(this.coefficients, that.coefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     return HashUtilities.hashCodeForDoubleArray(this.coefficients);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/function/PolynomialFunction2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */