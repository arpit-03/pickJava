/*     */ package Catalano.Signal;
/*     */ 
/*     */ import Catalano.Math.ComplexNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Convolution
/*     */ {
/*     */   public enum Mode
/*     */   {
/*  17 */     Same, Valid; }
/*  18 */   private Mode mode = Mode.Same;
/*     */ 
/*     */ 
/*     */   
/*     */   public Convolution(Mode mode) {
/*  23 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public double[][] Process(double[][] signal, double[][] kernel) {
/*     */     int width, height, lineI, lineJ, i;
/*  28 */     double[][] result = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     switch (this.mode) {
/*     */       case null:
/*  37 */         width = (signal[0]).length;
/*  38 */         height = (signal[1]).length;
/*     */         
/*  40 */         lineI = (kernel.length - 1) / 2;
/*  41 */         lineJ = ((kernel[0]).length - 1) / 2;
/*  42 */         result = new double[height][width];
/*  43 */         for (i = 0; i < height; i++) {
/*     */           
/*  45 */           double conv = 0.0D;
/*  46 */           for (int j = 0; j < width; j++) {
/*     */             
/*  48 */             for (int k = 0; k < kernel.length; k++) {
/*     */               
/*  50 */               int Xline = i + k - lineI;
/*  51 */               for (int l = 0; l < (kernel[0]).length; l++) {
/*     */                 
/*  53 */                 int Yline = j + l - lineJ;
/*  54 */                 if (Xline >= 0 && Xline < height && Yline >= 0 && Yline < width)
/*     */                 {
/*  56 */                   conv += result[Xline][Yline] * kernel[kernel.length - k - 1][(kernel[0]).length - l - 1];
/*     */                 }
/*     */               } 
/*     */             } 
/*  60 */             result[i][j] = conv;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case Valid:
/*  65 */         width = (signal[0]).length - (kernel[0]).length + 1;
/*  66 */         height = signal.length - kernel.length + 1;
/*     */         
/*  68 */         result = new double[height][width];
/*  69 */         for (i = 0; i < height; i++) {
/*     */           
/*  71 */           for (int j = 0; j < width; j++) {
/*     */ 
/*     */             
/*  74 */             double conv = 0.0D;
/*  75 */             for (int k = 0; k < kernel.length; k++) {
/*     */               
/*  77 */               for (int l = 0; l < (kernel[0]).length; l++)
/*     */               {
/*  79 */                 conv += signal[i + k][j + l] * kernel[kernel.length - k - 1][(kernel[0]).length - l - 1];
/*     */               }
/*     */             } 
/*  82 */             result[i][j] = conv;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*  87 */     return result;
/*     */   }
/*     */   
/*     */   public ComplexNumber[][] Process(ComplexNumber[][] signal, ComplexNumber[][] kernel) {
/*     */     int width, height, lineI, lineJ, i;
/*  92 */     ComplexNumber[][] result = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     switch (this.mode) {
/*     */       case null:
/* 101 */         width = (signal[0]).length;
/* 102 */         height = (signal[1]).length;
/*     */         
/* 104 */         lineI = (kernel.length - 1) / 2;
/* 105 */         lineJ = ((kernel[0]).length - 1) / 2;
/* 106 */         result = new ComplexNumber[height][width];
/* 107 */         for (i = 0; i < height; i++) {
/*     */           
/* 109 */           ComplexNumber conv = new ComplexNumber(0.0D, 0.0D);
/* 110 */           for (int j = 0; j < width; j++) {
/*     */             
/* 112 */             for (int k = 0; k < kernel.length; k++) {
/*     */               
/* 114 */               int Xline = i + k - lineI;
/* 115 */               for (int l = 0; l < (kernel[0]).length; l++) {
/*     */                 
/* 117 */                 int Yline = j + l - lineJ;
/* 118 */                 if (Xline >= 0 && Xline < height && Yline >= 0 && Yline < width)
/*     */                 {
/* 120 */                   conv = ComplexNumber.Add(conv, 
/* 121 */                       ComplexNumber.Multiply(result[Xline][Yline], kernel[kernel.length - k - 1][(kernel[0]).length - l - 1]));
/*     */                 }
/*     */               } 
/*     */             } 
/* 125 */             result[i][j] = conv;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case Valid:
/* 130 */         width = (signal[0]).length - (kernel[0]).length + 1;
/* 131 */         height = signal.length - kernel.length + 1;
/*     */         
/* 133 */         result = new ComplexNumber[height][width];
/* 134 */         for (i = 0; i < height; i++) {
/*     */           
/* 136 */           for (int j = 0; j < width; j++) {
/*     */ 
/*     */             
/* 139 */             ComplexNumber conv = new ComplexNumber(0.0D, 0.0D);
/* 140 */             for (int k = 0; k < kernel.length; k++) {
/*     */               
/* 142 */               for (int l = 0; l < (kernel[0]).length; l++)
/*     */               {
/* 144 */                 conv = ComplexNumber.Add(conv, 
/* 145 */                     ComplexNumber.Multiply(result[i + k][j + l], kernel[kernel.length - k - 1][(kernel[0]).length - l - 1]));
/*     */               }
/*     */             } 
/* 148 */             result[i][j] = conv;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/* 153 */     return result;
/*     */   }
/*     */   
/*     */   public Convolution() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Signal/Convolution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */