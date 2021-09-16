/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotTraces
/*     */ {
/*     */   private double[][] traceDataOut;
/*     */   
/*     */   public RotTraces() {}
/*     */   
/*     */   public RotTraces(double[][] traceData, double[][][] rotMatrix, double noRec) {
/*  19 */     if ((rotMatrix[0][0]).length != noRec) {
/*     */       
/*  21 */       System.out.println("rotMatrix[0][0].length");
/*  22 */       System.out.println((rotMatrix[0][0]).length);
/*  23 */       System.out.println((rotMatrix[0]).length);
/*  24 */       System.out.println(rotMatrix.length);
/*     */       
/*  26 */       System.out.println("traceData[0].length/3");
/*  27 */       System.out.println((traceData[0]).length / 3);
/*     */       
/*  29 */       JOptionPane.showMessageDialog(new JFrame("Rot Mat Error"), "No of Receivers in rotation matrix is not equal no of receivers in trace! Please QC");
/*     */     } 
/*     */     
/*  32 */     double[][] rotTraceData = new double[traceData.length][3];
/*     */     
/*  34 */     for (int z = 0; z < (rotMatrix[0][0]).length; z++) {
/*     */       
/*  36 */       double[][] rotTrace = new double[(rotMatrix[0]).length][rotMatrix.length];
/*     */       
/*  38 */       for (int x = 0; x < rotMatrix.length; x++) {
/*  39 */         for (int i = 0; i < (rotMatrix[0]).length; i++)
/*  40 */           rotTrace[x][i] = rotMatrix[x][i][z]; 
/*     */       } 
/*  42 */       for (int y = 0; y < traceData.length; y++) {
/*     */         
/*  44 */         double[][] temp1MAT = new double[3][1];
/*  45 */         temp1MAT[0][0] = traceData[y][3 * z];
/*  46 */         temp1MAT[1][0] = traceData[y][3 * z + 1];
/*  47 */         temp1MAT[2][0] = traceData[y][3 * z + 2];
/*     */         
/*  49 */         double[][] tempMat = new double[1][3];
/*  50 */         tempMat = transposeMatrix(multiplyMatrices(rotTrace, temp1MAT));
/*  51 */         traceData[y][3 * z] = tempMat[0][0];
/*  52 */         traceData[y][3 * z + 1] = tempMat[0][1];
/*  53 */         traceData[y][3 * z + 2] = tempMat[0][2];
/*     */       } 
/*     */     } 
/*  56 */     this.traceDataOut = traceData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] multiplyMatrices(double[][] m, double[][] n) {
/*  62 */     int mRows = m.length;
/*  63 */     int mCol = (m[0]).length;
/*  64 */     int nRows = n.length;
/*  65 */     int nCol = (n[0]).length;
/*     */     
/*  67 */     if (!canBeMultiplied(m, n))
/*     */     {
/*  69 */       JOptionPane.showMessageDialog(new JFrame("Rot Mat Error"), "Rotation Matrix Dimension Error!");
/*     */     }
/*     */ 
/*     */     
/*  73 */     double[][] answer = new double[mRows][nCol];
/*  74 */     for (int i = 0; i < mRows; i++) {
/*  75 */       for (int j = 0; j < nCol; j++) {
/*  76 */         for (int k = 0; k < mCol; k++) {
/*  77 */           answer[i][j] = answer[i][j] + m[i][k] * n[k][j];
/*     */         }
/*     */       } 
/*     */     } 
/*  81 */     return answer;
/*     */   }
/*     */   
/*     */   public double[][] getTraceData() {
/*  85 */     return this.traceDataOut;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBeMultiplied(double[][] m, double[][] n) {
/*  90 */     int mCol = (m[0]).length;
/*  91 */     int nRows = n.length;
/*     */     
/*  93 */     if (nRows == mCol)
/*     */     {
/*  95 */       return true;
/*     */     }
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] transposeMatrix(double[][] varName) {
/* 102 */     double[][] returnVar = new double[(varName[0]).length][varName.length];
/*     */     
/* 104 */     for (int i = 0; i < (varName[0]).length; i++) {
/* 105 */       for (int j = 0; j < varName.length; j++) {
/* 106 */         returnVar[i][j] = varName[j][i];
/*     */       }
/*     */     } 
/* 109 */     return returnVar;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/RotTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */