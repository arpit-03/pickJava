/*     */ package Catalano.Math.Functions;
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
/*     */ public final class Gabor
/*     */ {
/*     */   public enum Config
/*     */   {
/*  39 */     Real,
/*     */ 
/*     */ 
/*     */     
/*  43 */     Imaginary,
/*     */ 
/*     */ 
/*     */     
/*  47 */     Magnitude,
/*     */ 
/*     */ 
/*     */     
/*  51 */     SquaredMagnitude;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Function1D(double x, double mean, double amplitude, double position, double width, double phase, double frequency) {
/*  74 */     double envelope = mean + amplitude * Math.exp(-Math.pow(x - position, 2.0D) / Math.pow(2.0D * width, 2.0D));
/*  75 */     double carry = Math.cos(6.283185307179586D * frequency * (x - position) + phase);
/*  76 */     return envelope * carry;
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
/*     */   public static ComplexNumber Function2D(int x, int y, double wavelength, double orientation, double phaseOffset, double gaussVariance, double aspectRatio) {
/*  92 */     double X = x * Math.cos(orientation) + y * Math.sin(orientation);
/*  93 */     double Y = -x * Math.sin(orientation) + y * Math.cos(orientation);
/*     */     
/*  95 */     double envelope = Math.exp(-((X * X + aspectRatio * aspectRatio * Y * Y) / 2.0D * gaussVariance * gaussVariance));
/*  96 */     double real = Math.cos(6.283185307179586D * X / wavelength + phaseOffset);
/*  97 */     double imaginary = Math.sin(6.283185307179586D * X / wavelength + phaseOffset);
/*     */     
/*  99 */     return new ComplexNumber(envelope * real, envelope * imaginary);
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
/*     */   public static double RealFunction2D(int x, int y, double wavelength, double orientation, double phaseOffset, double gaussVariance, double aspectRatio) {
/* 116 */     double X = x * Math.cos(orientation) + y * Math.sin(orientation);
/* 117 */     double Y = -x * Math.sin(orientation) + y * Math.cos(orientation);
/*     */     
/* 119 */     double envelope = Math.exp(-((X * X + aspectRatio * aspectRatio * Y * Y) / 2.0D * gaussVariance * gaussVariance));
/* 120 */     double carrier = Math.cos(6.283185307179586D * X / wavelength + phaseOffset);
/*     */     
/* 122 */     return envelope * carrier;
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
/*     */   public static double ImaginaryFunction2D(int x, int y, double wavelength, double orientation, double phaseOffset, double gaussVariance, double aspectRatio) {
/* 139 */     double X = x * Math.cos(orientation) + y * Math.sin(orientation);
/* 140 */     double Y = -x * Math.sin(orientation) + y * Math.cos(orientation);
/*     */     
/* 142 */     double envelope = Math.exp(-((X * X + aspectRatio * aspectRatio * Y * Y) / 2.0D * gaussVariance * gaussVariance));
/* 143 */     double carrier = Math.sin(6.283185307179586D * X / wavelength + phaseOffset);
/*     */     
/* 145 */     return envelope * carrier;
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
/*     */   public static double[][] Kernel2D(int size, double wavelength, double orientation, double phaseOffset, double gaussVariance, double aspectRatio) {
/* 159 */     double sigmaX = gaussVariance;
/* 160 */     double sigmaY = gaussVariance / aspectRatio;
/*     */     
/* 162 */     int xMax = (int)Math.ceil(Math.max(1.0D, Math.max(Math.abs(size * sigmaX * Math.cos(orientation)), Math.abs(size * sigmaY * Math.sin(orientation)))));
/* 163 */     int yMax = (int)Math.ceil(Math.max(1.0D, Math.max(Math.abs(size * sigmaX * Math.sin(orientation)), Math.abs(size * sigmaY * Math.cos(orientation)))));
/*     */     
/* 165 */     double[][] kernel = new double[2 * xMax + 1][2 * yMax + 1];
/*     */     
/* 167 */     double sum = 0.0D; int x;
/* 168 */     for (x = -xMax; x <= xMax; x++) {
/* 169 */       for (int y = -yMax; y <= yMax; y++) {
/* 170 */         kernel[x + xMax][y + yMax] = ImaginaryFunction2D(x, y, wavelength, orientation, phaseOffset, gaussVariance, aspectRatio);
/* 171 */         sum += kernel[x + xMax][y + yMax];
/*     */       } 
/*     */     } 
/* 174 */     for (x = -xMax; x <= xMax; x++) {
/* 175 */       for (int y = -yMax; y <= yMax; y++) {
/* 176 */         kernel[x + xMax][y + yMax] = kernel[x + xMax][y + yMax] / sum;
/*     */       }
/*     */     } 
/* 179 */     return kernel;
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
/*     */   public static double[][] Kernel2D(int size, double wavelength, double orientation, double phaseOffset, double gaussVariance, double aspectRatio, Config config) {
/* 194 */     double sigmaX = gaussVariance;
/* 195 */     double sigmaY = gaussVariance / aspectRatio;
/*     */     
/* 197 */     int xMax = (int)Math.ceil(Math.max(1.0D, Math.max(Math.abs(size * sigmaX * Math.cos(orientation)), Math.abs(size * sigmaY * Math.sin(orientation)))));
/* 198 */     int yMax = (int)Math.ceil(Math.max(1.0D, Math.max(Math.abs(size * sigmaX * Math.sin(orientation)), Math.abs(size * sigmaY * Math.cos(orientation)))));
/*     */     
/* 200 */     double[][] kernel = new double[2 * xMax + 1][2 * yMax + 1];
/*     */     
/* 202 */     double sum = 0.0D;
/*     */     
/* 204 */     switch (config) {
/*     */       case Real:
/* 206 */         for (x = -xMax; x <= xMax; x++) {
/* 207 */           for (int y = -yMax; y <= yMax; y++) {
/* 208 */             kernel[x + xMax][y + yMax] = RealFunction2D(x, y, wavelength, orientation, phaseOffset, gaussVariance, aspectRatio);
/* 209 */             sum += kernel[x + xMax][y + yMax];
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case null:
/* 214 */         for (x = -xMax; x <= xMax; x++) {
/* 215 */           for (int y = -yMax; y <= yMax; y++) {
/* 216 */             kernel[x + xMax][y + yMax] = ImaginaryFunction2D(x, y, wavelength, orientation, phaseOffset, gaussVariance, aspectRatio);
/* 217 */             sum += kernel[x + xMax][y + yMax];
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case Magnitude:
/* 222 */         for (x = -xMax; x <= xMax; x++) {
/* 223 */           for (int y = -yMax; y <= yMax; y++) {
/* 224 */             ComplexNumber c = Function2D(x, y, wavelength, orientation, phaseOffset, gaussVariance, aspectRatio);
/* 225 */             kernel[x + xMax][y + yMax] = c.getMagnitude();
/* 226 */             sum += kernel[x + xMax][y + yMax];
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case SquaredMagnitude:
/* 231 */         for (x = -xMax; x <= xMax; x++) {
/* 232 */           for (int y = -yMax; y <= yMax; y++) {
/* 233 */             ComplexNumber c = Function2D(x, y, wavelength, orientation, phaseOffset, gaussVariance, aspectRatio);
/* 234 */             kernel[x + xMax][y + yMax] = c.getSquaredMagnitude();
/* 235 */             sum += kernel[x + xMax][y + yMax];
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/* 241 */     for (int x = -xMax; x <= xMax; x++) {
/* 242 */       for (int y = -yMax; y <= yMax; y++) {
/* 243 */         kernel[x + xMax][y + yMax] = kernel[x + xMax][y + yMax] / sum;
/*     */       }
/*     */     } 
/* 246 */     return kernel;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Gabor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */