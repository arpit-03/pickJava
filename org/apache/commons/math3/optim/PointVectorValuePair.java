/*     */ package org.apache.commons.math3.optim;
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
/*     */ public class PointVectorValuePair
/*     */   extends Pair<double[], double[]>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20120513L;
/*     */   
/*     */   public PointVectorValuePair(double[] point, double[] value) {
/*  43 */     this(point, value, true);
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
/*  57 */     super(copyArray ? ((point == null) ? null : point.clone()) : point, copyArray ? ((value == null) ? null : value.clone()) : value);
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
/*  73 */     double[] p = (double[])getKey();
/*  74 */     return (p == null) ? null : (double[])p.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPointRef() {
/*  83 */     return (double[])getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getValue() {
/*  93 */     double[] v = (double[])super.getValue();
/*  94 */     return (v == null) ? null : (double[])v.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getValueRef() {
/* 104 */     return (double[])super.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 112 */     return new DataTransferObject((double[])getKey(), getValue());
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
/* 135 */       this.point = (double[])point.clone();
/* 136 */       this.value = (double[])value.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 143 */       return new PointVectorValuePair(this.point, this.value, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/PointVectorValuePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */