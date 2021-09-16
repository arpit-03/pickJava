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
/*     */ public class PointValuePair
/*     */   extends Pair<double[], Double>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20120513L;
/*     */   
/*     */   public PointValuePair(double[] point, double value) {
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
/*     */   public PointValuePair(double[] point, double value, boolean copyArray) {
/*  57 */     super(copyArray ? ((point == null) ? null : point.clone()) : point, Double.valueOf(value));
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
/*     */   public double[] getPoint() {
/*  69 */     double[] p = (double[])getKey();
/*  70 */     return (p == null) ? null : (double[])p.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPointRef() {
/*  79 */     return (double[])getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/*  87 */     return new DataTransferObject((double[])getKey(), ((Double)getValue()).doubleValue());
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
/*     */     private final double value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DataTransferObject(double[] point, double value) {
/* 110 */       this.point = (double[])point.clone();
/* 111 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 118 */       return new PointValuePair(this.point, this.value, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/PointValuePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */