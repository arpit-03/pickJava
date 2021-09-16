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
/*     */ public class ComplexNumber
/*     */ {
/*  36 */   public double real = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*  40 */   public double imaginary = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexNumber() {
/*  46 */     this(0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexNumber(double real, double imaginary) {
/*  55 */     this.real = real;
/*  56 */     this.imaginary = imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexNumber(ComplexNumber z1) {
/*  64 */     this.real = z1.real;
/*  65 */     this.imaginary = z1.imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMagnitude() {
/*  73 */     return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSquaredMagnitude() {
/*  81 */     return this.real * this.real + this.imaginary * this.imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPhase() {
/*  89 */     return Math.atan2(this.imaginary, this.real);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getReal(ComplexNumber[] cn) {
/*  98 */     double[] n = new double[cn.length];
/*  99 */     for (int i = 0; i < n.length; i++) {
/* 100 */       n[i] = (cn[i]).real;
/*     */     }
/* 102 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getImaginary(ComplexNumber[] cn) {
/* 111 */     double[] n = new double[cn.length];
/* 112 */     for (int i = 0; i < n.length; i++) {
/* 113 */       n[i] = (cn[i]).imaginary;
/*     */     }
/* 115 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] getReal(ComplexNumber[][] cn) {
/* 124 */     double[][] n = new double[cn.length][(cn[0]).length];
/* 125 */     for (int i = 0; i < n.length; i++) {
/* 126 */       for (int j = 0; j < (n[0]).length; j++) {
/* 127 */         n[i][j] = (cn[i][j]).real;
/*     */       }
/*     */     } 
/* 130 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] getImaginary(ComplexNumber[][] cn) {
/* 139 */     double[][] n = new double[cn.length][(cn[0]).length];
/* 140 */     for (int i = 0; i < n.length; i++) {
/* 141 */       for (int j = 0; j < (n[0]).length; j++) {
/* 142 */         n[i][j] = (cn[i][j]).imaginary;
/*     */       }
/*     */     } 
/* 145 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Swap(ComplexNumber z1) {
/* 153 */     double t = z1.real;
/* 154 */     z1.real = z1.imaginary;
/* 155 */     z1.imaginary = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Swap(ComplexNumber[] z) {
/* 163 */     for (int i = 0; i < z.length; i++) {
/* 164 */       z[i] = new ComplexNumber((z[i]).imaginary, (z[i]).real);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Swap(ComplexNumber[][] z) {
/* 173 */     for (int i = 0; i < z.length; i++) {
/* 174 */       for (int j = 0; j < (z[0]).length; j++) {
/* 175 */         z[i][j] = new ComplexNumber((z[i][j]).imaginary, (z[i][j]).real);
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
/* 187 */     return Magnitude(z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] Abs(ComplexNumber[] z) {
/* 196 */     double[] values = new double[z.length];
/* 197 */     for (int i = 0; i < values.length; i++) {
/* 198 */       values[i] = z[i].getMagnitude();
/*     */     }
/* 200 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[][] Abs(ComplexNumber[][] z) {
/* 209 */     double[][] values = new double[z.length][(z[0]).length];
/* 210 */     for (int i = 0; i < values.length; i++) {
/* 211 */       for (int j = 0; j < (values[0]).length; j++) {
/* 212 */         values[i][j] = z[i][j].getMagnitude();
/*     */       }
/*     */     } 
/* 215 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Add(ComplexNumber z1, ComplexNumber z2) {
/* 225 */     return new ComplexNumber(z1.real + z2.real, z1.imaginary + z2.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Add(ComplexNumber z1, double scalar) {
/* 235 */     return new ComplexNumber(z1.real + scalar, z1.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(double scalar) {
/* 243 */     this.real += scalar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Subtract(ComplexNumber z1, ComplexNumber z2) {
/* 253 */     return new ComplexNumber(z1.real - z2.real, z1.imaginary - z2.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Subtract(ComplexNumber z1, double scalar) {
/* 263 */     return new ComplexNumber(z1.real - scalar, z1.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(double scalar) {
/* 271 */     this.real -= scalar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Magnitude(ComplexNumber z) {
/* 280 */     return Math.sqrt(z.real * z.real + z.imaginary * z.imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Multiply(ComplexNumber z1, ComplexNumber z2) {
/* 290 */     double z1R = z1.real, z1I = z1.imaginary;
/* 291 */     double z2R = z2.real, z2I = z2.imaginary;
/*     */     
/* 293 */     return new ComplexNumber(z1R * z2R - z1I * z2I, z1R * z2I + z1I * z2R);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Multiply(ComplexNumber z1, double scalar) {
/* 303 */     return new ComplexNumber(z1.real * scalar, z1.imaginary * scalar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(double scalar) {
/* 311 */     this.real *= scalar;
/* 312 */     this.imaginary *= scalar;
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
/* 323 */     ComplexNumber conj = Conjugate(z2);
/*     */     
/* 325 */     double a = z1.real * conj.real + z1.imaginary * conj.imaginary * -1.0D;
/* 326 */     double b = z1.real * conj.imaginary + z1.imaginary * conj.real;
/*     */     
/* 328 */     double c = z2.real * conj.real + z2.imaginary * conj.imaginary * -1.0D;
/*     */     
/* 330 */     return new ComplexNumber(a / c, b / c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(ComplexNumber z1) {
/* 338 */     ComplexNumber conj = Conjugate(z1);
/*     */     
/* 340 */     double a = this.real * conj.real + this.imaginary * conj.imaginary * -1.0D;
/* 341 */     double b = this.real * conj.imaginary + this.imaginary * conj.real;
/*     */     
/* 343 */     double c = z1.real * conj.real + z1.imaginary * conj.imaginary * -1.0D;
/*     */     
/* 345 */     this.real = a / c;
/* 346 */     this.imaginary = b / c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Divide(ComplexNumber z1, double scalar) {
/* 356 */     return new ComplexNumber(z1.real / scalar, z1.imaginary / scalar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(double scalar) {
/* 365 */     if (scalar == 0.0D) {
/*     */       try {
/* 367 */         throw new ArithmeticException("Can not divide by zero.");
/* 368 */       } catch (Exception e) {
/* 369 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 373 */     this.real /= scalar;
/* 374 */     this.imaginary /= scalar;
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
/* 385 */     double norm = Math.pow(z1.getMagnitude(), n);
/* 386 */     double angle = 360.0D - Math.abs(Math.toDegrees(Math.atan(z1.imaginary / z1.real)));
/*     */     
/* 388 */     double common = n * angle;
/*     */     
/* 390 */     double r = norm * Math.cos(Math.toRadians(common));
/* 391 */     double i = norm * Math.sin(Math.toRadians(common));
/*     */     
/* 393 */     return new ComplexNumber(r, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Pow(double n) {
/* 402 */     double norm = Math.pow(getMagnitude(), n);
/* 403 */     double angle = 360.0D - Math.abs(Math.toDegrees(Math.atan(this.imaginary / this.real)));
/*     */     
/* 405 */     double common = n * angle;
/*     */     
/* 407 */     this.real = norm * Math.cos(Math.toRadians(common));
/* 408 */     this.imaginary = norm * Math.sin(Math.toRadians(common));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Log(ComplexNumber z1) {
/* 417 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 419 */     if (z1.real > 0.0D && z1.imaginary == 0.0D) {
/* 420 */       result.real = Math.log(z1.real);
/* 421 */       result.imaginary = 0.0D;
/*     */     }
/* 423 */     else if (z1.real == 0.0D) {
/* 424 */       if (z1.imaginary > 0.0D) {
/* 425 */         result.real = Math.log(z1.imaginary);
/* 426 */         result.imaginary = 1.5707963267948966D;
/*     */       } else {
/*     */         
/* 429 */         result.real = Math.log(-z1.imaginary);
/* 430 */         result.imaginary = -1.5707963267948966D;
/*     */       } 
/*     */     } else {
/*     */       
/* 434 */       result.real = Math.log(z1.getMagnitude());
/* 435 */       result.imaginary = Math.atan2(z1.imaginary, z1.real);
/*     */     } 
/*     */     
/* 438 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Exp(ComplexNumber z1) {
/* 448 */     ComplexNumber x = new ComplexNumber(Math.exp(z1.real), 0.0D);
/* 449 */     ComplexNumber y = new ComplexNumber(Math.cos(z1.imaginary), Math.sin(z1.imaginary));
/*     */     
/* 451 */     return Multiply(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Sin(ComplexNumber z1) {
/* 460 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 462 */     if (z1.imaginary == 0.0D) {
/*     */       
/* 464 */       result.real = Math.sin(z1.real);
/* 465 */       result.imaginary = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 469 */       result.real = Math.sin(z1.real) * Math.cosh(z1.imaginary);
/* 470 */       result.imaginary = Math.cos(z1.real) * Math.sinh(z1.imaginary);
/*     */     } 
/*     */     
/* 473 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Cos(ComplexNumber z1) {
/* 482 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 484 */     if (z1.imaginary == 0.0D) {
/*     */       
/* 486 */       result.real = Math.cos(z1.real);
/* 487 */       result.imaginary = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 491 */       result.real = Math.cos(z1.real) * Math.cosh(z1.imaginary);
/* 492 */       result.imaginary = -Math.sin(z1.real) * Math.sinh(z1.imaginary);
/*     */     } 
/*     */     
/* 495 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Tan(ComplexNumber z1) {
/* 504 */     ComplexNumber result = new ComplexNumber();
/*     */     
/* 506 */     if (z1.imaginary == 0.0D) {
/*     */       
/* 508 */       result.real = Math.tan(z1.real);
/* 509 */       result.imaginary = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 513 */       double real2 = 2.0D * z1.real;
/* 514 */       double imag2 = 2.0D * z1.imaginary;
/* 515 */       double denom = Math.cos(real2) + Math.cosh(real2);
/*     */       
/* 517 */       result.real = Math.sin(real2) / denom;
/* 518 */       result.imaginary = Math.sinh(imag2) / denom;
/*     */     } 
/*     */     
/* 521 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Conjugate() {
/* 528 */     this.imaginary *= -1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexNumber Conjugate(ComplexNumber z1) {
/* 537 */     return new ComplexNumber(z1.real, z1.imaginary * -1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 542 */     if (this.imaginary >= 0.0D)
/* 543 */       return String.valueOf(this.real) + " +" + this.imaginary + "i"; 
/* 544 */     return String.valueOf(this.real) + " " + this.imaginary + "i";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/ComplexNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */