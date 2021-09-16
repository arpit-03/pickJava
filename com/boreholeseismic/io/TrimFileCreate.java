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
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.format.DateTimeFormat;
/*     */ import org.joda.time.format.DateTimeFormatter;
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
/*     */ public class TrimFileCreate
/*     */ {
/*     */   public TrimFileCreate(String inputFilePath, int inputLowerSampleNo, int inputUpperSampleNo, boolean skipFolder) {
/*     */     double pickP[][], pickS[][], pickSS[][], pickR1[][], pickR2[][], pickR3[][], noRec, timeSample;
/*     */     MLStructure headerData;
/*  50 */     boolean auxPresent = false;
/*  51 */     double offset = 0.0D;
/*     */     
/*  53 */     String filePath = inputFilePath;
/*  54 */     int lowerSample = inputLowerSampleNo;
/*  55 */     int upperSample = inputUpperSampleNo;
/*     */     
/*  57 */     File inputFile = new File(filePath);
/*     */ 
/*     */     
/*  60 */     MatFileFilter filter = new MatFileFilter();
/*  61 */     filter.addArrayName("traceData");
/*  62 */     filter.addArrayName("headerData");
/*  63 */     filter.addArrayName("pickOutP");
/*  64 */     filter.addArrayName("pickOutS");
/*  65 */     filter.addArrayName("pickOutSS");
/*  66 */     filter.addArrayName("pickOutR1");
/*  67 */     filter.addArrayName("pickOutR2");
/*  68 */     filter.addArrayName("pickOutR3");
/*  69 */     filter.addArrayName("auxData");
/*  70 */     filter.addArrayName("autoPicks");
/*     */     
/*  72 */     MatFileReader mfr = null;
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
/*     */     try {
/*  94 */       mfr = new MatFileReader(filePath, filter);
/*  95 */       traceData = ((MLDouble)mfr.getMLArray("traceData")).getArray();
/*  96 */       headerData = (MLStructure)mfr.getMLArray("headerData");
/*  97 */       acqDate = ((MLChar)((MLStructure)((MLStructure)mfr.getMLArray("headerData")).getField("acquisition")).getField("date")).getString(0);
/*  98 */       acqTime = ((MLChar)((MLStructure)((MLStructure)mfr.getMLArray("headerData")).getField("acquisition")).getField("time")).getString(0);
/*  99 */       acqMSec = ((MLChar)((MLStructure)((MLStructure)mfr.getMLArray("headerData")).getField("acquisition")).getField("secondFraction")).getString(0);
/* 100 */       timeSample = ((MLDouble)((MLStructure)mfr.getMLArray("headerData")).getField("timeSample")).getArray()[0][0];
/* 101 */       noRec = Math.ceil(((traceData[0]).length / 3));
/* 102 */       double noTrace = (traceData[0]).length;
/* 103 */       double noSample = traceData.length;
/*     */ 
/*     */     
/*     */     }
/* 107 */     catch (Exception e) {
/*     */       
/* 109 */       JOptionPane.showMessageDialog(new JFrame(), "Error reading this File. Check MAT File for BHS Event Formatting");
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 118 */       pickP = ((MLDouble)mfr.getMLArray("pickOutP")).getArray();
/*     */ 
/*     */     
/*     */     }
/* 122 */     catch (Exception e) {
/*     */       
/* 124 */       pickP = new double[(int)noRec][2];
/* 125 */       for (int k = 0; k < noRec; k++) {
/*     */         
/* 127 */         pickP[k][0] = (k + 1);
/* 128 */         pickP[k][1] = Double.NaN;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 134 */       pickS = ((MLDouble)mfr.getMLArray("pickOutS")).getArray();
/*     */     
/*     */     }
/* 137 */     catch (Exception e) {
/*     */       
/* 139 */       pickS = new double[(int)noRec][2];
/* 140 */       for (int k = 0; k < noRec; k++) {
/*     */         
/* 142 */         pickS[k][0] = (k + 1);
/* 143 */         pickS[k][1] = Double.NaN;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 149 */       pickSS = ((MLDouble)mfr.getMLArray("pickOutSS")).getArray();
/*     */     
/*     */     }
/* 152 */     catch (Exception e) {
/*     */       
/* 154 */       pickSS = new double[(int)noRec][2];
/* 155 */       for (int k = 0; k < noRec; k++) {
/*     */         
/* 157 */         pickSS[k][0] = (k + 1);
/* 158 */         pickSS[k][1] = Double.NaN;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 164 */       pickR1 = ((MLDouble)mfr.getMLArray("pickOutR1")).getArray();
/*     */     
/*     */     }
/* 167 */     catch (Exception e) {
/*     */       
/* 169 */       pickR1 = new double[(int)noRec][2];
/* 170 */       for (int k = 0; k < noRec; k++) {
/*     */         
/* 172 */         pickR1[k][0] = (k + 1);
/* 173 */         pickR1[k][1] = Double.NaN;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 179 */       pickR2 = ((MLDouble)mfr.getMLArray("pickOutR2")).getArray();
/*     */     
/*     */     }
/* 182 */     catch (Exception e) {
/*     */       
/* 184 */       pickR2 = new double[(int)noRec][2];
/* 185 */       for (int k = 0; k < noRec; k++) {
/*     */         
/* 187 */         pickR2[k][0] = (k + 1);
/* 188 */         pickR2[k][1] = Double.NaN;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 194 */       pickR3 = ((MLDouble)mfr.getMLArray("pickOutR3")).getArray();
/*     */     
/*     */     }
/* 197 */     catch (Exception e) {
/*     */       
/* 199 */       pickR3 = new double[(int)noRec][2];
/* 200 */       for (int k = 0; k < noRec; k++) {
/*     */         
/* 202 */         pickR3[k][0] = (k + 1);
/* 203 */         pickR3[k][1] = Double.NaN;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     double[][] newTraceData = new double[upperSample - lowerSample + 1][(traceData[0]).length];
/*     */     
/* 212 */     for (int i = 0; i < upperSample - lowerSample + 1; i++) {
/* 213 */       for (int k = 0; k < (traceData[0]).length; k++)
/* 214 */         newTraceData[i][k] = traceData[lowerSample + i][k]; 
/*     */     } 
/* 216 */     double[][] traceData = newTraceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     String dateTimeString = String.valueOf(acqDate) + acqTime + acqMSec;
/*     */     
/* 225 */     DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyyHH:mm:ssSSS");
/* 226 */     DateTimeFormatter milliSecondFormatter = DateTimeFormat.forPattern("SSS");
/*     */     
/* 228 */     DateTime originalDate = formatter.parseDateTime(dateTimeString);
/* 229 */     DateTime newDate = originalDate.plusMillis((int)Math.round(lowerSample * timeSample));
/*     */ 
/*     */ 
/*     */     
/* 233 */     DateTimeFormatter fileHeaderDateFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
/* 234 */     DateTimeFormatter fileHeaderTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss");
/* 235 */     DateTimeFormatter fileHeaderMSecFormatter = DateTimeFormat.forPattern("SSS");
/*     */     
/* 237 */     String acqDate = newDate.toString(fileHeaderDateFormatter);
/* 238 */     String acqTime = newDate.toString(fileHeaderTimeFormatter);
/* 239 */     String acqMSec = newDate.toString(fileHeaderMSecFormatter);
/*     */ 
/*     */ 
/*     */     
/* 243 */     DateTimeFormatter fileNameDateFormatter = DateTimeFormat.forPattern("yyyyMMdd_");
/* 244 */     DateTimeFormatter fileNameTimeFormatter = DateTimeFormat.forPattern("_HHmmss_");
/* 245 */     DateTimeFormatter fileNameMSecFormatter = DateTimeFormat.forPattern("_SSS");
/* 246 */     DateTimeFormatter fileNameFormatter = DateTimeFormat.forPattern("yyyyMMdd_HHmmss_SSS");
/*     */ 
/*     */     
/* 249 */     String fileName = inputFile.getName();
/*     */     
/* 251 */     if (fileName.contains(originalDate.toString(fileNameDateFormatter)) && 
/* 252 */       fileName.contains(originalDate.toString(fileNameTimeFormatter)) && 
/* 253 */       fileName.contains(originalDate.toString(fileNameMSecFormatter))) {
/*     */       
/* 255 */       fileName = fileName.replaceAll(originalDate.toString(fileNameFormatter), newDate.toString(fileNameFormatter));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 260 */       fileName = String.valueOf(newDate.toString(DateTimeFormat.forPattern("yyyyMMdd_HHmmss_SSS"))) + ".mat";
/*     */     } 
/* 262 */     File outputFile = null;
/*     */     
/* 264 */     if (skipFolder) {
/*     */       
/* 266 */       String outputFileString = fileName;
/* 267 */       File outputFolder = new File(inputFile.getParentFile().getAbsolutePath(), "03_Cut Files");
/* 268 */       if (!outputFolder.exists()) {
/* 269 */         outputFolder.mkdir();
/*     */       }
/* 271 */       outputFile = new File(outputFolder.getAbsolutePath(), fileName);
/*     */       
/* 273 */       if (outputFile.exists())
/*     */       {
/* 275 */         outputFile.canWrite();
/* 276 */         outputFile.delete();
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 282 */       String outputFileString = fileName;
/* 283 */       outputFile = new File(inputFile.getParentFile().getAbsolutePath(), fileName);
/*     */     } 
/*     */ 
/*     */     
/* 287 */     int[] temp1 = { 1, 1 };
/*     */ 
/*     */ 
/*     */     
/* 291 */     MLStructure acqStructure = new MLStructure("acquisition", temp1);
/* 292 */     acqStructure.setField("date", (MLArray)new MLChar("date", acqDate));
/* 293 */     acqStructure.setField("time", (MLArray)new MLChar("time", acqTime));
/* 294 */     acqStructure.setField("secondFraction", (MLArray)new MLChar("secondFraction", acqMSec));
/* 295 */     headerData.setField("acquisition", (MLArray)acqStructure);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     double[] temp2 = { (upperSample - lowerSample + 1) };
/* 305 */     headerData.setField("fileName", (MLArray)new MLChar("fileName", fileName));
/*     */ 
/*     */     
/* 308 */     headerData.setField("noSample", (MLArray)new MLDouble("noSample", temp2, 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     for (int j = 0; j < pickP.length; j++) {
/*     */       
/* 315 */       pickP[j][1] = pickP[j][1] - lowerSample * timeSample;
/* 316 */       if ((((pickP[j][1] < 0.0D) ? 1 : 0) | ((pickP[j][1] > timeSample * (upperSample - lowerSample + 1)) ? 1 : 0)) != 0) {
/* 317 */         pickP[j][1] = Double.NaN;
/*     */       }
/* 319 */       pickS[j][1] = pickS[j][1] - lowerSample * timeSample;
/* 320 */       if ((((pickS[j][1] < 0.0D) ? 1 : 0) | ((pickS[j][1] > timeSample * (upperSample - lowerSample + 1)) ? 1 : 0)) != 0) {
/* 321 */         pickS[j][1] = Double.NaN;
/*     */       }
/* 323 */       pickSS[j][1] = pickSS[j][1] - lowerSample * timeSample;
/* 324 */       if ((((pickSS[j][1] < 0.0D) ? 1 : 0) | ((pickSS[j][1] > timeSample * (upperSample - lowerSample + 1)) ? 1 : 0)) != 0) {
/* 325 */         pickSS[j][1] = Double.NaN;
/*     */       }
/*     */       
/* 328 */       pickR1[j][1] = pickR1[j][1] - lowerSample * timeSample;
/* 329 */       if ((((pickR1[j][1] < 0.0D) ? 1 : 0) | ((pickR1[j][1] > timeSample * (upperSample - lowerSample + 1)) ? 1 : 0)) != 0) {
/* 330 */         pickR1[j][1] = Double.NaN;
/*     */       }
/* 332 */       pickR2[j][1] = pickR2[j][1] - lowerSample * timeSample;
/* 333 */       if ((((pickR2[j][1] < 0.0D) ? 1 : 0) | ((pickR2[j][1] > timeSample * (upperSample - lowerSample + 1)) ? 1 : 0)) != 0) {
/* 334 */         pickR2[j][1] = Double.NaN;
/*     */       }
/* 336 */       pickR3[j][1] = pickR3[j][1] - lowerSample * timeSample;
/* 337 */       if ((((pickR3[j][1] < 0.0D) ? 1 : 0) | ((pickR3[j][1] > timeSample * (upperSample - lowerSample + 1)) ? 1 : 0)) != 0) {
/* 338 */         pickR3[j][1] = Double.NaN;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 346 */       Map<String, MLArray> mlArrayRetrived = mfr.getContent();
/* 347 */       MLDouble pickOutP = new MLDouble("pickOutP", pickP);
/* 348 */       MLDouble pickOutS = new MLDouble("pickOutS", pickS);
/* 349 */       MLDouble pickOutSS = new MLDouble("pickOutSS", pickSS);
/* 350 */       MLDouble pickOutR1 = new MLDouble("pickOutR1", pickR1);
/* 351 */       MLDouble pickOutR2 = new MLDouble("pickOutR2", pickR2);
/* 352 */       MLDouble pickOutR3 = new MLDouble("pickOutR3", pickR3);
/* 353 */       MLDouble traceData1 = new MLDouble("traceData", traceData);
/*     */ 
/*     */ 
/*     */       
/* 357 */       mlArrayRetrived.put("pickOutP", pickOutP);
/* 358 */       mlArrayRetrived.put("pickOutS", pickOutS);
/* 359 */       mlArrayRetrived.put("pickOutSS", pickOutSS);
/* 360 */       mlArrayRetrived.put("pickOutR1", pickOutR1);
/* 361 */       mlArrayRetrived.put("pickOutR2", pickOutR2);
/* 362 */       mlArrayRetrived.put("pickOutR3", pickOutR3);
/* 363 */       mlArrayRetrived.put("traceData", traceData1);
/* 364 */       mlArrayRetrived.put("headerData", headerData);
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
/*     */       try {
/* 377 */         double[][] auxData = ((MLDouble)mfr.getMLArray("auxData")).getArray();
/*     */         
/* 379 */         auxPresent = true;
/*     */         
/* 381 */         if (auxPresent)
/*     */         {
/*     */           
/* 384 */           double[][] newAuxData = new double[upperSample - lowerSample + 1][(auxData[0]).length];
/*     */           
/* 386 */           for (int k = 0; k < upperSample - lowerSample + 1; k++) {
/* 387 */             for (int m = 0; m < (auxData[0]).length; m++)
/* 388 */               newAuxData[k][m] = auxData[lowerSample + k][m]; 
/*     */           } 
/* 390 */           auxData = newAuxData;
/*     */           
/* 392 */           MLDouble auxData1 = new MLDouble("auxData", auxData);
/* 393 */           mlArrayRetrived.put("auxData", auxData1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 399 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 408 */         MLStructure autoPickStructure = (MLStructure)mfr.getMLArray("autoPicks");
/* 409 */         double[][] autoPicks = ((MLDouble)((MLStructure)mfr.getMLArray("autoPicks")).getField("edgesOut")).getArray();
/*     */ 
/*     */         
/* 412 */         if (autoPicks.length > 0 && (
/* 413 */           autoPicks[0]).length > 0)
/*     */         {
/* 415 */           for (int k = 0; k < autoPicks.length; k++) {
/* 416 */             for (int m = 0; m < (autoPicks[0]).length; m++) {
/* 417 */               if (autoPicks[k][m] > upperSample * timeSample || autoPicks[k][m] < lowerSample * timeSample) {
/* 418 */                 autoPicks[k][m] = Double.NaN;
/*     */               }
/*     */               
/* 421 */               autoPicks[k][m] = -(lowerSample * timeSample) + autoPicks[k][m];
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 430 */         autoPickStructure.setField("edgesOut", (MLArray)new MLDouble("edgesOut", autoPicks));
/*     */         
/* 432 */         mlArrayRetrived.put("autoPicks", autoPickStructure);
/*     */ 
/*     */       
/*     */       }
/* 436 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 445 */       ArrayList list = new ArrayList();
/*     */       
/* 447 */       for (String key : mlArrayRetrived.keySet()) {
/* 448 */         list.add(mlArrayRetrived.get(key));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 457 */     catch (Exception e2) {
/*     */       
/* 459 */       JOptionPane.showMessageDialog(new JFrame(), "Error Saving Trimmed File.");
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[][] getDoubleArray(Matrix matrix) {
/* 468 */     double[][] output = new double[matrix.getNumRows()][matrix.getNumCols()];
/*     */     
/* 470 */     for (int i = 0; i < output.length; i++) {
/* 471 */       for (int j = 0; j < (output[0]).length; j++) {
/* 472 */         output[i][j] = matrix.getDouble(i, j);
/*     */       }
/*     */     } 
/*     */     
/* 476 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Matrix getDoubleMatrix(double[][] matrix) {
/* 482 */     Matrix output = Mat5.newMatrix(matrix.length, (matrix[0]).length);
/*     */     
/* 484 */     for (int i = 0; i < matrix.length; i++) {
/* 485 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 486 */         output.setDouble(i, j, matrix[i][j]);
/*     */       }
/*     */     } 
/*     */     
/* 490 */     return output;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/io/TrimFileCreate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */