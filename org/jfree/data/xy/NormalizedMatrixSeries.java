/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NormalizedMatrixSeries
/*     */   extends MatrixSeries
/*     */ {
/*     */   public static final double DEFAULT_SCALE_FACTOR = 1.0D;
/*  59 */   private double m_scaleFactor = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double m_totalSum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NormalizedMatrixSeries(String name, int rows, int columns) {
/*  72 */     super(name, rows, columns);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     this.m_totalSum = Double.MIN_VALUE;
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
/*     */   public Number getItem(int itemIndex) {
/*  94 */     int i = getItemRow(itemIndex);
/*  95 */     int j = getItemColumn(itemIndex);
/*     */     
/*  97 */     double mij = get(i, j) * this.m_scaleFactor;
/*  98 */     Number n = new Double(mij / this.m_totalSum);
/*     */     
/* 100 */     return n;
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
/*     */   public void setScaleFactor(double factor) {
/* 112 */     this.m_scaleFactor = factor;
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
/*     */   public double getScaleFactor() {
/* 124 */     return this.m_scaleFactor;
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
/*     */   public void update(int i, int j, double mij) {
/* 139 */     this.m_totalSum -= get(i, j);
/* 140 */     this.m_totalSum += mij;
/*     */     
/* 142 */     super.update(i, j, mij);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zeroAll() {
/* 150 */     this.m_totalSum = 0.0D;
/* 151 */     super.zeroAll();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/NormalizedMatrixSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */