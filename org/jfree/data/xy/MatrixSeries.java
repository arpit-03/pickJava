/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.data.general.Series;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatrixSeries
/*     */   extends Series
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7934188527308315704L;
/*     */   protected double[][] data;
/*     */   
/*     */   public MatrixSeries(String name, int rows, int columns) {
/*  77 */     super(name);
/*  78 */     this.data = new double[rows][columns];
/*  79 */     zeroAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnsCount() {
/*  88 */     return (this.data[0]).length;
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
/*     */   public Number getItem(int itemIndex) {
/* 103 */     int i = getItemRow(itemIndex);
/* 104 */     int j = getItemColumn(itemIndex);
/*     */     
/* 106 */     Number n = new Double(get(i, j));
/*     */     
/* 108 */     return n;
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
/*     */   public int getItemColumn(int itemIndex) {
/* 121 */     return itemIndex % getColumnsCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 132 */     return getRowCount() * getColumnsCount();
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
/*     */   public int getItemRow(int itemIndex) {
/* 145 */     return itemIndex / getColumnsCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 155 */     return this.data.length;
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
/*     */   public double get(int i, int j) {
/* 171 */     return this.data[i][j];
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
/*     */   public void update(int i, int j, double mij) {
/* 185 */     this.data[i][j] = mij;
/* 186 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zeroAll() {
/* 196 */     int rows = getRowCount();
/* 197 */     int columns = getColumnsCount();
/*     */     
/* 199 */     for (int row = 0; row < rows; row++) {
/* 200 */       for (int column = 0; column < columns; column++) {
/* 201 */         this.data[row][column] = 0.0D;
/*     */       }
/*     */     } 
/* 204 */     fireSeriesChanged();
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
/* 216 */     if (obj == this) {
/* 217 */       return true;
/*     */     }
/* 219 */     if (!(obj instanceof MatrixSeries)) {
/* 220 */       return false;
/*     */     }
/* 222 */     MatrixSeries that = (MatrixSeries)obj;
/* 223 */     if (getRowCount() != that.getRowCount()) {
/* 224 */       return false;
/*     */     }
/* 226 */     if (getColumnsCount() != that.getColumnsCount()) {
/* 227 */       return false;
/*     */     }
/* 229 */     for (int r = 0; r < getRowCount(); r++) {
/* 230 */       for (int c = 0; c < getColumnsCount(); c++) {
/* 231 */         if (get(r, c) != that.get(r, c)) {
/* 232 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 236 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/MatrixSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */