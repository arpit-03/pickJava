/*     */ package org.apache.commons.math3.optimization;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.util.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class PointVectorValuePair
/*     */   extends Pair<double[], double[]>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20120513L;
/*     */   
/*     */   public PointVectorValuePair(double[] point, double[] value) {
/*  48 */     this(point, value, true);
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
/*     */   public PointVectorValuePair(double[] point, double[] value, boolean copyArray) {
/*  62 */     super(copyArray ? ((point == null) ? null : point.clone()) : point, copyArray ? ((value == null) ? null : value.clone()) : value);
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
/*     */   public double[] getPoint() {
/*  78 */     double[] p = (double[])getKey();
/*  79 */     return (p == null) ? null : (double[])p.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPointRef() {
/*  88 */     return (double[])getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getValue() {
/*  98 */     double[] v = (double[])super.getValue();
/*  99 */     return (v == null) ? null : (double[])v.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getValueRef() {
/* 109 */     return (double[])super.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 117 */     return new DataTransferObject((double[])getKey(), getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DataTransferObject
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20120513L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] point;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DataTransferObject(double[] point, double[] value) {
/* 140 */       this.point = (double[])point.clone();
/* 141 */       this.value = (double[])value.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 148 */       return new PointVectorValuePair(this.point, this.value, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/PointVectorValuePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */