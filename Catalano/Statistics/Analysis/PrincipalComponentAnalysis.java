/*     */ package Catalano.Statistics.Analysis;
/*     */ 
/*     */ import Catalano.Math.Decompositions.SingularValueDecomposition;
/*     */ import Catalano.Math.Matrix;
/*     */ import Catalano.Statistics.Tools;
/*     */ 
/*     */ public class PrincipalComponentAnalysis
/*     */ {
/*     */   private double[][] matrix;
/*     */   private double[] meanColumn;
/*     */   private double[] stdColumn;
/*     */   private double[] singularValues;
/*     */   private double[][] eigenVectors;
/*     */   private double[] eigenValues;
/*     */   private double[][] result;
/*     */   private AnalysisMethod method;
/*     */   
/*     */   public enum AnalysisMethod {
/*  19 */     Standartize,
/*  20 */     Center;
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
/*     */   public double[][] getResult() {
/*  32 */     return this.result;
/*     */   }
/*     */   
/*     */   public double[][] getEigenVectors() {
/*  36 */     return this.eigenVectors;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PrincipalComponentAnalysis(double[][] matrix) {
/*  42 */     this(matrix, AnalysisMethod.Center);
/*     */   }
/*     */   
/*     */   public PrincipalComponentAnalysis(double[][] matrix, AnalysisMethod method) {
/*  46 */     this.matrix = matrix;
/*  47 */     this.method = method;
/*     */   }
/*     */ 
/*     */   
/*     */   public void Compute() {
/*  52 */     this.meanColumn = new double[(this.matrix[0]).length];
/*  53 */     this.stdColumn = new double[(this.matrix[0]).length];
/*     */ 
/*     */     
/*  56 */     int cols = (this.matrix[0]).length;
/*  57 */     for (int i = 0; i < cols; i++) {
/*  58 */       double[] col = Matrix.getColumn(this.matrix, i);
/*  59 */       this.meanColumn[i] = Tools.Mean(col);
/*  60 */       this.stdColumn[i] = Tools.StandartDeviation(col, this.meanColumn[i]);
/*     */     } 
/*     */ 
/*     */     
/*  64 */     double[][] m = Center(this.matrix, this.meanColumn);
/*     */     
/*  66 */     if (this.method == AnalysisMethod.Standartize) {
/*  67 */       for (int k = 0; k < m.length; k++) {
/*  68 */         for (int n = 0; n < (m[0]).length; n++) {
/*  69 */           m[k][n] = m[k][n] / this.stdColumn[n];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  75 */     SingularValueDecomposition svd = new SingularValueDecomposition(m);
/*  76 */     this.singularValues = svd.getSingularValues();
/*  77 */     this.eigenVectors = svd.getV();
/*     */ 
/*     */     
/*  80 */     this.result = Matrix.MultiplyByDiagonal(svd.getU(), this.singularValues);
/*     */ 
/*     */     
/*  83 */     this.eigenValues = new double[this.singularValues.length];
/*  84 */     for (int j = 0; j < this.singularValues.length; j++) {
/*  85 */       this.eigenValues[j] = this.singularValues[j] * this.singularValues[j] / (this.matrix.length - 1);
/*     */     }
/*  87 */     CreateComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void CreateComponents() {}
/*     */ 
/*     */   
/*     */   private double[][] Center(double[][] matrix, double[] means) {
/*  96 */     double[][] m = new double[matrix.length][(matrix[0]).length];
/*  97 */     for (int i = 0; i < m.length; i++) {
/*  98 */       for (int j = 0; j < (m[0]).length; j++) {
/*  99 */         m[i][j] = matrix[i][j] - means[j];
/*     */       }
/*     */     } 
/* 102 */     return m;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Analysis/PrincipalComponentAnalysis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */