/*     */ package com.boreholeseismic.io;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import us.hebi.matlab.mat.format.Mat5;
/*     */ import us.hebi.matlab.mat.format.Mat5File;
/*     */ import us.hebi.matlab.mat.types.MatFile;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ import us.hebi.matlab.mat.types.Sources;
/*     */ import us.hebi.matlab.mat.types.Struct;
/*     */ 
/*     */ public class readMAT {
/*     */   private double[][] traceData;
/*     */   private double[][] auxData;
/*     */   private double[][] traceDataOnly;
/*     */   public static String FileName;
/*     */   public static String timeUnits;
/*     */   public double[][] pickOutP;
/*     */   public double[][] computeP;
/*     */   public double[][] ampP;
/*     */   public double[][] sampP;
/*     */   public double[][] pickOutS;
/*     */   public double[][] computeS;
/*     */   public double[][] ampS;
/*     */   public double[][] sampS;
/*  28 */   public double misfit = 0.0D; public double[][] pickOutSS; public double[][] computeSS; public double[][] pickOutR1; public double[][] computeR1; public double[][] pickOutR2; public double[][] computeR2; public double[][] pickOutR3; public double[][] computeR3; public double[][] edgesOut; public double noRec; public double noTrace; public double timeSample; public double noSample; public double depth = 0.0D; public double Easting = 0.0D; public double Northing = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean errorFree = true;
/*     */ 
/*     */   
/*     */   public boolean amplitudePicked = true;
/*     */ 
/*     */   
/*     */   public boolean auxDataPresent = true;
/*     */ 
/*     */   
/*     */   public boolean inverted = false;
/*     */ 
/*     */   
/*     */   public Struct[] FileArray;
/*     */ 
/*     */ 
/*     */   
/*     */   public readMAT(String FileLocation) throws FileNotFoundException, IOException {
/*  49 */     MatFile mfr = null;
/*  50 */     Source source = Sources.openFile(FileLocation);
/*  51 */     Mat5File mat5File = Mat5.newReader(source).readMat();
/*  52 */     source.close();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  60 */       this.traceData = getDoubleArray(mat5File.getMatrix("traceData"));
/*  61 */       Struct headerData = mat5File.getStruct("headerData");
/*     */ 
/*     */ 
/*     */       
/*  65 */       FileName = headerData.getChar("fileName").toString();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       timeUnits = headerData.getChar("timeUnits").toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       this.timeSample = headerData.getMatrix("timeSample").getDouble(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  84 */       this.noRec = Math.ceil(((this.traceData[0]).length / 3));
/*  85 */       this.noTrace = (this.traceData[0]).length;
/*  86 */       this.noSample = this.traceData.length;
/*     */ 
/*     */       
/*  89 */       Struct headerDataML = mat5File.getStruct("headerData");
/*  90 */       this.FileArray = new Struct[1];
/*  91 */       this.FileArray[0] = headerDataML;
/*     */ 
/*     */       
/*     */       try {
/*  95 */         Struct invDataML = mat5File.getStruct("invBHS");
/*  96 */         if (invDataML != null)
/*     */         {
/*  98 */           this.FileArray = new Struct[2];
/*  99 */           this.FileArray[0] = headerDataML;
/* 100 */           this.FileArray[1] = invDataML;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 105 */           this.inverted = true;
/*     */         }
/*     */       
/*     */       }
/* 109 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 116 */         this.ampP = getDoubleArray(mat5File.getMatrix("ampP"));
/*     */         
/* 118 */         if ((this.ampP[0]).length < 2) {
/* 119 */           throw new Exception();
/*     */         }
/* 121 */       } catch (Exception e) {
/*     */         
/* 123 */         this.amplitudePicked = false;
/* 124 */         this.ampP = new double[(int)this.noRec][3];
/* 125 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 127 */           this.ampP[i][0] = Double.NaN;
/* 128 */           this.ampP[i][1] = Double.NaN;
/* 129 */           this.ampP[i][2] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 137 */         this.ampS = getDoubleArray(mat5File.getMatrix("ampS"));
/* 138 */         if ((this.ampS[0]).length < 2) {
/* 139 */           throw new Exception();
/*     */         }
/* 141 */       } catch (Exception e) {
/*     */         
/* 143 */         this.amplitudePicked = false;
/* 144 */         this.ampS = new double[(int)this.noRec][3];
/* 145 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 147 */           this.ampS[i][0] = Double.NaN;
/* 148 */           this.ampS[i][1] = Double.NaN;
/* 149 */           this.ampS[i][2] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 156 */         this.sampP = getDoubleArray(mat5File.getMatrix("sampP"));
/* 157 */         if ((this.sampP[0]).length < 1) {
/* 158 */           throw new Exception();
/*     */         }
/* 160 */       } catch (Exception e) {
/*     */         
/* 162 */         this.amplitudePicked = false;
/* 163 */         this.sampP = new double[(int)this.noRec][1];
/* 164 */         for (int i = 0; i < this.noRec; i++)
/*     */         {
/* 166 */           this.sampP[i][0] = Double.NaN;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 175 */         this.sampS = getDoubleArray(mat5File.getMatrix("sampS"));
/* 176 */         if ((this.sampS[0]).length < 1) {
/* 177 */           throw new Exception();
/*     */         }
/* 179 */       } catch (Exception e) {
/*     */         
/* 181 */         this.amplitudePicked = false;
/* 182 */         this.sampS = new double[(int)this.noRec][1];
/* 183 */         for (int i = 0; i < this.noRec; i++)
/*     */         {
/* 185 */           this.sampS[i][0] = Double.NaN;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 193 */         this.computeP = getDoubleArray(mat5File.getMatrix("computeP"));
/*     */         
/* 195 */         if ((this.computeP[0]).length < 2) {
/* 196 */           throw new Exception();
/*     */         }
/* 198 */       } catch (Exception e) {
/*     */         
/* 200 */         this.computeP = new double[(int)this.noRec][2];
/* 201 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 203 */           this.computeP[i][0] = (i + 1);
/* 204 */           this.computeP[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 212 */         this.computeS = getDoubleArray(mat5File.getMatrix("computeS"));
/* 213 */         if ((this.computeS[0]).length < 2) {
/* 214 */           throw new Exception();
/*     */         }
/* 216 */       } catch (Exception e) {
/*     */         
/* 218 */         this.computeS = new double[(int)this.noRec][2];
/* 219 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 221 */           this.computeS[i][0] = (i + 1);
/* 222 */           this.computeS[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 230 */         this.computeSS = getDoubleArray(mat5File.getMatrix("computeSS"));
/* 231 */         if ((this.computeSS[0]).length < 2) {
/* 232 */           throw new Exception();
/*     */         }
/* 234 */       } catch (Exception e) {
/*     */         
/* 236 */         this.computeSS = new double[(int)this.noRec][2];
/* 237 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 239 */           this.computeSS[i][0] = (i + 1);
/* 240 */           this.computeSS[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 249 */         this.pickOutP = getDoubleArray(mat5File.getMatrix("pickOutP"));
/*     */       }
/* 251 */       catch (Exception e) {
/*     */         
/* 253 */         this.pickOutP = new double[(int)this.noRec][2];
/* 254 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 256 */           this.pickOutP[i][0] = (i + 1);
/* 257 */           this.pickOutP[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 264 */         this.pickOutS = getDoubleArray(mat5File.getMatrix("pickOutS"));
/*     */       }
/* 266 */       catch (Exception e) {
/*     */         
/* 268 */         this.pickOutS = new double[(int)this.noRec][2];
/* 269 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 271 */           this.pickOutS[i][0] = (i + 1);
/* 272 */           this.pickOutS[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 280 */         this.pickOutSS = getDoubleArray(mat5File.getMatrix("pickOutSS"));
/*     */       }
/* 282 */       catch (Exception e) {
/*     */         
/* 284 */         this.pickOutSS = new double[(int)this.noRec][2];
/* 285 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 287 */           this.pickOutSS[i][0] = (i + 1);
/* 288 */           this.pickOutSS[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 296 */         this.pickOutR1 = getDoubleArray(mat5File.getMatrix("pickOutR1"));
/*     */       }
/* 298 */       catch (Exception e) {
/*     */         
/* 300 */         this.pickOutR1 = new double[(int)this.noRec][2];
/* 301 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 303 */           this.pickOutR1[i][0] = (i + 1);
/* 304 */           this.pickOutR1[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 312 */         this.pickOutR2 = getDoubleArray(mat5File.getMatrix("pickOutR2"));
/*     */       }
/* 314 */       catch (Exception e) {
/*     */         
/* 316 */         this.pickOutR2 = new double[(int)this.noRec][2];
/* 317 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 319 */           this.pickOutR2[i][0] = (i + 1);
/* 320 */           this.pickOutR2[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 328 */         this.pickOutR3 = getDoubleArray(mat5File.getMatrix("pickOutR3"));
/*     */       }
/* 330 */       catch (Exception e) {
/*     */         
/* 332 */         this.pickOutR3 = new double[(int)this.noRec][2];
/* 333 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 335 */           this.pickOutR3[i][0] = (i + 1);
/* 336 */           this.pickOutR3[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 343 */         this.computeR1 = getDoubleArray(mat5File.getMatrix("computeR1"));
/*     */       }
/* 345 */       catch (Exception e) {
/*     */         
/* 347 */         this.computeR1 = new double[(int)this.noRec][2];
/* 348 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 350 */           this.computeR1[i][0] = (i + 1);
/* 351 */           this.computeR1[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 357 */         this.computeR2 = getDoubleArray(mat5File.getMatrix("computeR2"));
/*     */       }
/* 359 */       catch (Exception e) {
/*     */         
/* 361 */         this.computeR2 = new double[(int)this.noRec][2];
/* 362 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 364 */           this.computeR2[i][0] = (i + 1);
/* 365 */           this.computeR2[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 371 */         this.computeR3 = getDoubleArray(mat5File.getMatrix("computeR3"));
/*     */       }
/* 373 */       catch (Exception e) {
/*     */         
/* 375 */         this.computeR3 = new double[(int)this.noRec][2];
/* 376 */         for (int i = 0; i < this.noRec; i++) {
/*     */           
/* 378 */           this.computeR3[i][0] = (i + 1);
/* 379 */           this.computeR3[i][1] = Double.NaN;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 386 */         Struct autopicks = mat5File.getStruct("autoPicks");
/* 387 */         this.edgesOut = getDoubleArray(autopicks.getMatrix("edgesOut"));
/*     */       }
/* 389 */       catch (Exception e) {
/*     */         
/* 391 */         this.edgesOut = new double[0][0];
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 399 */       this.traceDataOnly = this.traceData;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 405 */         this.auxData = getDoubleArray(mat5File.getMatrix("auxData"));
/*     */         
/* 407 */         if ((this.auxData[0]).length < 1) {
/* 408 */           throw new Exception();
/*     */         }
/* 410 */         double[][] fixAuxData = new double[this.traceData.length][3];
/*     */         int i;
/* 412 */         for (i = 0; i < fixAuxData.length; i++) {
/* 413 */           for (int k = 0; k < (fixAuxData[0]).length; k++) {
/* 414 */             fixAuxData[i][k] = Double.NaN;
/*     */           }
/*     */         } 
/*     */         
/* 418 */         for (i = 0; i < this.auxData.length; i++) {
/* 419 */           for (int k = 0; k < (this.auxData[0]).length; k++) {
/* 420 */             fixAuxData[i][k] = this.auxData[i][k];
/*     */           }
/*     */         } 
/*     */         
/* 424 */         this.auxData = fixAuxData;
/*     */         
/* 426 */         double[][] fixTraceData = new double[this.traceData.length][(this.traceData[0]).length + 3];
/*     */         
/* 428 */         for (int j = 0; j < fixTraceData.length; j++) {
/*     */           int k;
/* 430 */           for (k = 0; k < (this.traceData[0]).length; k++) {
/* 431 */             fixTraceData[j][k] = this.traceData[j][k];
/*     */           }
/*     */           
/* 434 */           for (k = (this.traceData[0]).length; k < (this.traceData[0]).length + 3; k++) {
/* 435 */             fixTraceData[j][k] = this.auxData[j][k - (this.traceData[0]).length];
/*     */           }
/*     */         } 
/*     */         
/* 439 */         this.traceData = fixTraceData;
/*     */       
/*     */       }
/* 442 */       catch (Exception e) {
/*     */         
/* 444 */         this.auxDataPresent = false;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 451 */     catch (Exception e) {
/*     */       
/* 453 */       e.printStackTrace();
/* 454 */       JOptionPane.showMessageDialog(new JFrame(), "Error reading this File. Check MAT File for BHS Event Formatting");
/* 455 */       this.errorFree = false;
/*     */       return;
/*     */     } 
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
/*     */   
/*     */   public boolean checkSize(double[][] picks) {
/* 474 */     if ((picks[0]).length < 2) {
/* 475 */       return false;
/*     */     }
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getTraceData() {
/* 483 */     return this.traceData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTraceData(double[][] trace) {
/* 488 */     this.traceData = trace;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getAuxData() {
/* 494 */     return this.auxData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAuxData(double[][] trace) {
/* 499 */     this.auxData = trace;
/*     */   }
/*     */   
/*     */   public double[][] getTraceDataOnly() {
/* 503 */     return this.traceDataOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] getDoubleArray(Matrix matrix) {
/* 508 */     double[][] output = new double[matrix.getNumRows()][matrix.getNumCols()];
/*     */     
/* 510 */     for (int i = 0; i < output.length; i++) {
/* 511 */       for (int j = 0; j < (output[0]).length; j++) {
/* 512 */         output[i][j] = matrix.getDouble(i, j);
/*     */       }
/*     */     } 
/*     */     
/* 516 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Matrix getDoubleMatrix(double[][] matrix) {
/* 522 */     Matrix output = Mat5.newMatrix(matrix.length, (matrix[0]).length);
/*     */     
/* 524 */     for (int i = 0; i < matrix.length; i++) {
/* 525 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 526 */         output.setDouble(i, j, matrix[i][j]);
/*     */       }
/*     */     } 
/*     */     
/* 530 */     return output;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/io/readMAT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */