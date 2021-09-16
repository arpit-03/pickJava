/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VectorialMean
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8223009086481006892L;
/*     */   private final Mean[] means;
/*     */   
/*     */   public VectorialMean(int dimension) {
/*  40 */     this.means = new Mean[dimension];
/*  41 */     for (int i = 0; i < dimension; i++) {
/*  42 */       this.means[i] = new Mean();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double[] v) throws DimensionMismatchException {
/*  52 */     if (v.length != this.means.length) {
/*  53 */       throw new DimensionMismatchException(v.length, this.means.length);
/*     */     }
/*  55 */     for (int i = 0; i < v.length; i++) {
/*  56 */       this.means[i].increment(v[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getResult() {
/*  65 */     double[] result = new double[this.means.length];
/*  66 */     for (int i = 0; i < result.length; i++) {
/*  67 */       result[i] = this.means[i].getResult();
/*     */     }
/*  69 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/*  77 */     return (this.means.length == 0) ? 0L : this.means[0].getN();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  83 */     int prime = 31;
/*  84 */     int result = 1;
/*  85 */     result = 31 * result + Arrays.hashCode((Object[])this.means);
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  92 */     if (this == obj) {
/*  93 */       return true;
/*     */     }
/*  95 */     if (!(obj instanceof VectorialMean)) {
/*  96 */       return false;
/*     */     }
/*  98 */     VectorialMean other = (VectorialMean)obj;
/*  99 */     if (!Arrays.equals((Object[])this.means, (Object[])other.means)) {
/* 100 */       return false;
/*     */     }
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/VectorialMean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */