/*     */ package com.boreholeseismic.io;
/*     */ 
/*     */ import com.jmatio.io.MatFileFilter;
/*     */ import com.jmatio.io.MatFileReader;
/*     */ import com.jmatio.types.MLArray;
/*     */ import com.jmatio.types.MLChar;
/*     */ import com.jmatio.types.MLDouble;
/*     */ import com.jmatio.types.MLStructure;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import us.hebi.matlab.mat.format.Mat5;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class createMergedFile
/*     */ {
/*     */   private double[][] getDoubleArray(Matrix matrix) {
/*  35 */     double[][] output = new double[matrix.getNumRows()][matrix.getNumCols()];
/*     */     
/*  37 */     for (int i = 0; i < output.length; i++) {
/*  38 */       for (int j = 0; j < (output[0]).length; j++) {
/*  39 */         output[i][j] = matrix.getDouble(i, j);
/*     */       }
/*     */     } 
/*     */     
/*  43 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   private Matrix getDoubleMatrix(double[][] matrix) {
/*  48 */     Matrix output = Mat5.newMatrix(matrix.length, (matrix[0]).length);
/*     */     
/*  50 */     for (int i = 0; i < matrix.length; i++) {
/*  51 */       for (int j = 0; j < (matrix[0]).length; j++) {
/*  52 */         output.setDouble(i, j, matrix[i][j]);
/*     */       }
/*     */     } 
/*     */     
/*  56 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public createMergedFile(String[] filePaths) {
/*     */     MLStructure autoPickStructure;
/*  67 */     String outputFileName = "";
/*  68 */     double[][] traceData = new double[0][0];
/*  69 */     double[][] auxData = new double[0][0];
/*  70 */     double[][] autoPicks = new double[0][0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     MatFileReader[] mfr = new MatFileReader[filePaths.length];
/*     */ 
/*     */     
/*  78 */     MatFileFilter filter = new MatFileFilter();
/*  79 */     filter.addArrayName("traceData");
/*  80 */     filter.addArrayName("headerData");
/*  81 */     filter.addArrayName("auxData");
/*  82 */     filter.addArrayName("autoPicks");
/*     */     
/*  84 */     double offset = 0.0D;
/*     */ 
/*     */     
/*  87 */     for (int i = 0; i < filePaths.length; i++) {
/*     */ 
/*     */       
/*     */       try {
/*  91 */         mfr[i] = new MatFileReader(filePaths[i], filter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         double[][] tempTraceData = ((MLDouble)mfr[i].getMLArray("traceData")).getArray();
/*     */         
/*  99 */         double[][] bkTraceData = new double[tempTraceData.length + traceData.length][(tempTraceData[0]).length];
/*     */ 
/*     */         
/* 102 */         for (int j = 0; j < (tempTraceData[0]).length; j++) {
/*     */           int k;
/* 104 */           for (k = 0; k < traceData.length; k++)
/*     */           {
/* 106 */             bkTraceData[k][j] = traceData[k][j];
/*     */           }
/*     */           
/* 109 */           for (k = 0; k < tempTraceData.length; k++)
/*     */           {
/* 111 */             bkTraceData[k + traceData.length][j] = tempTraceData[k][j];
/*     */           }
/*     */         } 
/*     */         
/* 115 */         traceData = new double[bkTraceData.length][(bkTraceData[0]).length];
/*     */         
/* 117 */         for (int x = 0; x < bkTraceData.length; x++) {
/* 118 */           for (int y = 0; y < (bkTraceData[0]).length; y++) {
/* 119 */             traceData[x][y] = bkTraceData[x][y];
/*     */           }
/*     */         } 
/*     */         try {
/* 123 */           double[][] tempAuxData = ((MLDouble)mfr[i].getMLArray("auxData")).getArray();
/*     */ 
/*     */           
/* 126 */           double[][] bkAuxData = new double[tempAuxData.length + auxData.length][(tempAuxData[0]).length];
/*     */           
/* 128 */           for (int k = 0; k < (tempAuxData[0]).length; k++) {
/*     */             int m;
/* 130 */             for (m = 0; m < auxData.length; m++)
/*     */             {
/* 132 */               bkAuxData[m][k] = auxData[m][k];
/*     */             }
/*     */             
/* 135 */             for (m = 0; m < tempAuxData.length; m++)
/*     */             {
/* 137 */               bkAuxData[m + auxData.length][k] = tempAuxData[m][k];
/*     */             }
/*     */           } 
/*     */           
/* 141 */           auxData = bkAuxData;
/*     */         }
/* 143 */         catch (Exception e) {
/*     */           
/* 145 */           auxData = null;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 154 */           double[][] tempAutoPicks = null;
/* 155 */           boolean autoPickExists = true;
/*     */           
/*     */           try {
/* 158 */             tempAutoPicks = ((MLDouble)((MLStructure)mfr[i].getMLArray("autoPicks")).getField("edgesOut")).getArray();
/*     */           
/*     */           }
/* 161 */           catch (Exception e) {
/* 162 */             autoPickExists = false;
/*     */           } 
/*     */           
/* 165 */           if (autoPickExists && tempAutoPicks != null && tempAutoPicks.length > 0)
/*     */           {
/* 167 */             if (autoPicks.length > 0) {
/*     */               
/* 169 */               double[][] bkAutoPicks = new double[tempAutoPicks.length][(tempAutoPicks[0]).length + (autoPicks[0]).length];
/*     */               
/* 171 */               for (int k = 0; k < tempAutoPicks.length; k++) {
/* 172 */                 int m; for (m = 0; m < (autoPicks[0]).length; m++) {
/* 173 */                   bkAutoPicks[k][m] = autoPicks[k][m];
/*     */                 }
/*     */                 
/* 176 */                 for (m = 0; m < (tempAutoPicks[0]).length; m++) {
/* 177 */                   bkAutoPicks[k][m + (autoPicks[0]).length] = offset + tempAutoPicks[k][m];
/*     */                 }
/*     */               } 
/*     */               
/* 181 */               autoPicks = bkAutoPicks;
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 186 */               autoPicks = tempAutoPicks;
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 194 */           offset += (
/* 195 */             (MLDouble)((MLStructure)mfr[i].getMLArray("headerData")).getField("noSample")).getArray()[0][0] * (
/* 196 */             (MLDouble)((MLStructure)mfr[i].getMLArray("headerData")).getField("timeSample")).getArray()[0][0];
/*     */         
/*     */         }
/* 199 */         catch (Exception e) {
/*     */           
/* 201 */           System.out.println("Error merging Auto Picks!");
/* 202 */           e.printStackTrace();
/* 203 */           autoPicks = null;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 208 */       catch (Exception e) {
/*     */         
/* 210 */         e.printStackTrace();
/* 211 */         JOptionPane.showMessageDialog(new JFrame("Error"), "Error reading MAT file, " + filePaths[i]);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     File f = new File(filePaths[0]);
/* 219 */     double[] temp = new double[1];
/* 220 */     temp[0] = traceData.length;
/* 221 */     MLStructure headerData = (MLStructure)mfr[0].getMLArray("headerData");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 227 */       autoPickStructure = (MLStructure)mfr[0].getMLArray("autoPicks");
/* 228 */       autoPickStructure.setField("edgesOut", (MLArray)new MLDouble("edgesOut", autoPicks));
/*     */     }
/* 230 */     catch (Exception e) {
/* 231 */       autoPickStructure = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     headerData.setField("fileName", (MLArray)new MLChar("fileName", f.getName().replace(".mat", "_Merged.mat")));
/* 238 */     headerData.setField("noSample", (MLArray)new MLDouble("noSample", temp, 1));
/*     */ 
/*     */     
/* 241 */     MLDouble traceData1 = new MLDouble("traceData", traceData);
/*     */     
/* 243 */     outputFileName = filePaths[0].replace(".mat", "_Merged.mat");
/*     */     
/* 245 */     Map<String, MLArray> mlArrayRetrived = mfr[0].getContent();
/* 246 */     mlArrayRetrived.clear();
/* 247 */     mlArrayRetrived.put("headerData", headerData);
/* 248 */     mlArrayRetrived.put("traceData", traceData1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     if (auxData != null) {
/* 255 */       MLDouble auxData1 = new MLDouble("auxData", auxData);
/* 256 */       mlArrayRetrived.put("auxData", auxData1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (autoPickStructure != null && autoPicks != null) {
/* 262 */       mlArrayRetrived.put("autoPicks", autoPickStructure);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 267 */     ArrayList list = new ArrayList();
/*     */     
/* 269 */     for (String key : mlArrayRetrived.keySet()) {
/* 270 */       list.add(mlArrayRetrived.get(key));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */     
/* 278 */     } catch (Exception e) {
/* 279 */       JOptionPane.showMessageDialog(new JFrame("Error"), "Cannot Write Merged File;\n" + outputFileName);
/* 280 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/io/createMergedFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */