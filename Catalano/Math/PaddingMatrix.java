/*     */ package Catalano.Math;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaddingMatrix
/*     */ {
/*     */   private int rows;
/*     */   private int cols;
/*     */   private double value;
/*     */   private boolean replicate;
/*     */   
/*     */   public PaddingMatrix(int rows, int cols) {
/*  42 */     this(rows, cols, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PaddingMatrix(int rows, int cols, double value) {
/*  52 */     this.rows = rows;
/*  53 */     this.cols = cols;
/*  54 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PaddingMatrix(int rows, int cols, boolean replicate) {
/*  64 */     this.rows = rows;
/*  65 */     this.cols = cols;
/*  66 */     this.replicate = replicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] Create(double[][] matrix) {
/*  77 */     if (this.replicate) {
/*  78 */       double[][] arrayOfDouble = Matrix.CreateMatrix2D(matrix.length + 2 * this.rows, (matrix[0]).length + 2 * this.cols, this.value);
/*     */ 
/*     */       
/*  81 */       for (int j = 0; j < arrayOfDouble.length; j++) {
/*  82 */         int r = j - this.rows;
/*  83 */         for (int k = 0; k < (arrayOfDouble[0]).length; k++) {
/*  84 */           int c = k - this.cols;
/*  85 */           if (r >= 0 && r < matrix.length && c >= 0 && c < (matrix[0]).length) {
/*  86 */             arrayOfDouble[j][k] = matrix[r][c];
/*     */           } else {
/*     */             
/*  89 */             int rr = r;
/*  90 */             int cc = c;
/*     */             
/*  92 */             if (rr < 0) rr = 0; 
/*  93 */             if (rr >= matrix.length) rr = matrix.length - 1;
/*     */             
/*  95 */             if (cc < 0) cc = 0; 
/*  96 */             if (cc >= (matrix[0]).length) cc = (matrix[0]).length - 1;
/*     */             
/*  98 */             arrayOfDouble[j][k] = matrix[rr][cc];
/*     */           } 
/*     */         } 
/*     */       } 
/* 102 */       return arrayOfDouble;
/*     */     } 
/*     */     
/* 105 */     double[][] result = Matrix.CreateMatrix2D(matrix.length + 2 * this.rows, (matrix[0]).length + 2 * this.cols, this.value);
/* 106 */     for (int i = 0; i < matrix.length; i++) {
/* 107 */       System.arraycopy(matrix[i], 0, result[i + this.rows], this.cols, (matrix[0]).length);
/*     */     }
/* 109 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/PaddingMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */