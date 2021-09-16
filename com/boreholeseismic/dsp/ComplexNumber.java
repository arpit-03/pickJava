/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComplexNumber
/*     */ {
/*  12 */   public double real = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*  16 */   public double imaginary = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexNumber() {
/*  22 */     this(0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexNumber(double real, double imaginary) {
/*  31 */     this.real = real;
/*  32 */     this.imaginary = imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexNumber(ComplexNumber z1) {
/*  40 */     this.real = z1.real;
/*  41 */     this.imaginary = z1.imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMagnitude() {
/*  49 */     return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSquaredMagnitude() {
/*  57 */     return this.real * this.real + this.imaginary * this.imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPhase() {
/*  65 */     return Math.atan2(this.imaginary, this.real);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getReal(ComplexNumber[] cn) {
/*  74 */     double[] n = new double[cn.length];
/*  75 */     for (int i = 0; i < n.length; i++) {
/*  76 */       n[i] = (cn[i]).real;
/*     */     }
/*  78 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getImaginary(ComplexNumber[] cn) {
/*  87 */     double[] n = new double[cn.length];
/*  88 */     for (int i = 0; i < n.length; i++) {
/*  89 */       n[i] = (cn[i]).imaginary;
/*     */     }
/*  91 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] getReal(ComplexNumber[][] cn) {
/* 100 */     double[][] n = new double[cn.length][(cn[0]).length];
/* 101 */     for (int i = 0; i < n.length; i++) {
/* 102 */       for (int j = 0; j < (n[0]).length; j++) {
/* 103 */         n[i][j] = (cn[i][j]).real;
/*     */       }
/*     */     } 
/* 106 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] getImaginary(ComplexNumber[][] cn) {
/* 115 */     double[][] n = new double[cn.length][(cn[0]).length];
/* 116 */     for (int i = 0; i < n.length; i++) {
/* 117 */       for (int j = 0; j < (n[0]).length; j++) {
/* 118 */         n[i][j] = (cn[i][j]).imaginary;
/*     */       }
/*     */     } 
/* 121 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Swap(ComplexNumber z1) {
/* 129 */     double t = z1.real;
/* 130 */     z1.real = z1.imaginary;
/* 131 */     z1.imaginary = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Swap(ComplexNumber[] z) {
/* 139 */     for (int i = 0; i < z.length; i++) {
/* 140 */       z[i] = new ComplexNumber((z[i]).imaginary, (z[i]).real);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Swap(ComplexNumber[][] z) {
/* 149 */     for (int i = 0; i < z.length; i++) {
/* 150 */       for (int j = 0; j < (z[0]).length; j++) {
/* 151 */         z[i][j] = new ComplexNumber((z[i][j]).imaginary, (z[i][j]).real);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Abs(ComplexNumber z) {
/* 163 */     return Magnitude(z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] Abs(ComplexNumber[] z) {
/* 172 */     double[] values = new double[z.length];
/* 173 */     for (int i = 0; i < values.length; i++) {
/* 174 */       values[i] = z[i].getMagnitude();
/*     */     }
/* 176 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] Abs(ComplexNumber[][] z) {
/* 185 */     double[][] values = new double[z.length][(z[0]).length];
/* 186 */     for (int i = 0; i < values.length; i++) {
/* 187 */       for (int j = 0; j < (values[0]).length; j++) {
/* 188 */         values[i][j] = z[i][j].getMagnitude();
/*     */       }
/*     */     } 
/* 191 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Add(ComplexNumber z1, ComplexNumber z2) {
/* 201 */     return new ComplexNumber(z1.real + z2.real, z1.imaginary + z2.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Add(ComplexNumber z1, double scalar) {
/* 211 */     return new ComplexNumber(z1.real + scalar, z1.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(double scalar) {
/* 219 */     this.real += scalar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Subtract(ComplexNumber z1, ComplexNumber z2) {
/* 229 */     return new ComplexNumber(z1.real - z2.real, z1.imaginary - z2.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Subtract(ComplexNumber z1, double scalar) {
/* 239 */     return new ComplexNumber(z1.real - scalar, z1.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(double scalar) {
/* 247 */     this.real -= scalar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Magnitude(ComplexNumber z) {
/* 256 */     return Math.sqrt(z.real * z.real + z.imaginary * z.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Multiply(ComplexNumber z1, ComplexNumber z2) {
/* 266 */     double z1R = z1.real, z1I = z1.imaginary;
/* 267 */     double z2R = z2.real, z2I = z2.imaginary;
/*     */     
/* 269 */     return new ComplexNumber(z1R * z2R - z1I * z2I, z1R * z2I + z1I * z2R);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Multiply(ComplexNumber z1, double scalar) {
/* 279 */     return new ComplexNumber(z1.real * scalar, z1.imaginary * scalar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(double scalar) {
/* 287 */     this.real *= scalar;
/* 288 */     this.imaginary *= scalar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Divide(ComplexNumber z1, ComplexNumber z2) {
/* 299 */     ComplexNumber conj = Conjugate(z2);
/*     */     
/* 301 */     double a = z1.real * conj.real + z1.imaginary * conj.imaginary * -1.0D;
/* 302 */     double b = z1.real * conj.imaginary + z1.imaginary * conj.real;
/*     */     
/* 304 */     double c = z2.real * conj.real + z2.imaginary * conj.imaginary * -1.0D;
/*     */     
/* 306 */     return new ComplexNumber(a / c, b / c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(ComplexNumber z1) {
/* 314 */     ComplexNumber conj = Conjugate(z1);
/*     */     
/* 316 */     double a = this.real * conj.real + this.imaginary * conj.imaginary * -1.0D;
/* 317 */     double b = this.real * conj.imaginary + this.imaginary * conj.real;
/*     */     
/* 319 */     double c = z1.real * conj.real + z1.imaginary * conj.imaginary * -1.0D;
/*     */     
/* 321 */     this.real = a / c;
/* 322 */     this.imaginary = b / c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Divide(ComplexNumber z1, double scalar) {
/* 332 */     return new ComplexNumber(z1.real / scalar, z1.imaginary / scalar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(double scalar) {
/* 341 */     if (scalar == 0.0D) {
/*     */       try {
/* 343 */         throw new ArithmeticException("Can not divide by zero.");
/* 344 */       } catch (Exception e) {
/* 345 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 349 */     this.real /= scalar;
/* 350 */     this.imaginary /= scalar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Pow(ComplexNumber z1, double n) {
/* 361 */     double norm = Math.pow(z1.getMagnitude(), n);
/* 362 */     double angle = 360.0D - Math.abs(Math.toDegrees(Math.atan(z1.imaginary / z1.real)));
/*     */     
/* 364 */     double common = n * angle;
/*     */     
/* 366 */     double r = norm * Math.cos(Math.toRadians(common));
/* 367 */     double i = norm * Math.sin(Math.toRadians(common));
/*     */     
/* 369 */     return new ComplexNumber(r, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Pow(double n) {
/* 378 */     double norm = Math.pow(getMagnitude(), n);
/* 379 */     double angle = 360.0D - Math.abs(Math.toDegrees(Math.atan(this.imaginary / this.real)));
/*     */     
/* 381 */     double common = n * angle;
/*     */     
/* 383 */     this.real = norm * Math.cos(Math.toRadians(common));
/* 384 */     this.imaginary = norm * Math.sin(Math.toRadians(common));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Log(ComplexNumber z1) {
/* 393 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 395 */     if (z1.real > 0.0D && z1.imaginary == 0.0D) {
/* 396 */       result.real = Math.log(z1.real);
/* 397 */       result.imaginary = 0.0D;
/*     */     }
/* 399 */     else if (z1.real == 0.0D) {
/* 400 */       if (z1.imaginary > 0.0D) {
/* 401 */         result.real = Math.log(z1.imaginary);
/* 402 */         result.imaginary = 1.5707963267948966D;
/*     */       } else {
/*     */         
/* 405 */         result.real = Math.log(-z1.imaginary);
/* 406 */         result.imaginary = -1.5707963267948966D;
/*     */       } 
/*     */     } else {
/*     */       
/* 410 */       result.real = Math.log(z1.getMagnitude());
/* 411 */       result.imaginary = Math.atan2(z1.imaginary, z1.real);
/*     */     } 
/*     */     
/* 414 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Exp(ComplexNumber z1) {
/* 424 */     ComplexNumber x = new ComplexNumber(Math.exp(z1.real), 0.0D);
/* 425 */     ComplexNumber y = new ComplexNumber(Math.cos(z1.imaginary), Math.sin(z1.imaginary));
/*     */     
/* 427 */     return Multiply(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Sin(ComplexNumber z1) {
/* 436 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 438 */     if (z1.imaginary == 0.0D) {
/*     */       
/* 440 */       result.real = Math.sin(z1.real);
/* 441 */       result.imaginary = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 445 */       result.real = Math.sin(z1.real) * Math.cosh(z1.imaginary);
/* 446 */       result.imaginary = Math.cos(z1.real) * Math.sinh(z1.imaginary);
/*     */     } 
/*     */     
/* 449 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Cos(ComplexNumber z1) {
/* 458 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 460 */     if (z1.imaginary == 0.0D) {
/*     */       
/* 462 */       result.real = Math.cos(z1.real);
/* 463 */       result.imaginary = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 467 */       result.real = Math.cos(z1.real) * Math.cosh(z1.imaginary);
/* 468 */       result.imaginary = -Math.sin(z1.real) * Math.sinh(z1.imaginary);
/*     */     } 
/*     */     
/* 471 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Tan(ComplexNumber z1) {
/* 480 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 482 */     if (z1.imaginary == 0.0D) {
/*     */       
/* 484 */       result.real = Math.tan(z1.real);
/* 485 */       result.imaginary = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 489 */       double real2 = 2.0D * z1.real;
/* 490 */       double imag2 = 2.0D * z1.imaginary;
/* 491 */       double denom = Math.cos(real2) + Math.cosh(real2);
/*     */       
/* 493 */       result.real = Math.sin(real2) / denom;
/* 494 */       result.imaginary = Math.sinh(imag2) / denom;
/*     */     } 
/*     */     
/* 497 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Conjugate() {
/* 504 */     this.imaginary *= -1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Conjugate(ComplexNumber z1) {
/* 513 */     return new ComplexNumber(z1.real, z1.imaginary * -1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 518 */     if (this.imaginary >= 0.0D)
/* 519 */       return String.valueOf(this.real) + " +" + this.imaginary + "i"; 
/* 520 */     return String.valueOf(this.real) + " " + this.imaginary + "i";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/ComplexNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */