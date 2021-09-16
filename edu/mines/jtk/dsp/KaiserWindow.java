/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.MathPlus;
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
/*     */ public class KaiserWindow
/*     */ {
/*     */   private double _error;
/*     */   private double _width;
/*     */   private double _length;
/*     */   private double _alpha;
/*     */   private double _scale;
/*     */   private double _xxmax;
/*     */   
/*     */   public static KaiserWindow fromErrorAndWidth(double error, double width) {
/*  71 */     Check.argument((error > 0.0D), "error>0.0");
/*  72 */     Check.argument((error < 1.0D), "error<1.0");
/*  73 */     Check.argument((width > 0.0D), "width>0.0");
/*  74 */     double a = -20.0D * MathPlus.log10(error);
/*  75 */     double d = (a > 21.0D) ? ((a - 7.95D) / 14.36D) : 0.9222D;
/*  76 */     double length = d / width;
/*  77 */     return new KaiserWindow(error, width, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KaiserWindow fromErrorAndLength(double error, double length) {
/*  87 */     Check.argument((error > 0.0D), "error>0.0");
/*  88 */     Check.argument((error < 1.0D), "error<1.0");
/*  89 */     Check.argument((length > 0.0D), "length>0");
/*  90 */     double a = -20.0D * MathPlus.log10(error);
/*  91 */     double d = (a > 21.0D) ? ((a - 7.95D) / 14.36D) : 0.9222D;
/*  92 */     double width = d / length;
/*  93 */     return new KaiserWindow(error, width, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KaiserWindow fromWidthAndLength(double width, double length) {
/* 104 */     Check.argument((width > 0.0D), "width>0.0");
/* 105 */     Check.argument((length > 0.0D), "length>0");
/* 106 */     Check.argument((width * length >= 1.0D), "width*length>=1.0");
/* 107 */     double d = width * length;
/* 108 */     double a = 14.36D * d + 7.95D;
/* 109 */     double error = MathPlus.pow(10.0D, -a / 20.0D);
/* 110 */     return new KaiserWindow(error, width, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evaluate(double x) {
/* 119 */     double xx = x * x;
/* 120 */     return (xx <= this._xxmax) ? (this._scale * ino(this._alpha * MathPlus.sqrt(1.0D - xx / this._xxmax))) : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getError() {
/* 128 */     return this._error;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLength() {
/* 136 */     return this._length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWidth() {
/* 144 */     return this._width;
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
/*     */   private KaiserWindow(double error, double width, double length) {
/* 158 */     this._error = error;
/* 159 */     this._width = width;
/* 160 */     this._length = length;
/* 161 */     double a = -20.0D * MathPlus.log10(this._error);
/* 162 */     if (a <= 21.0D) {
/* 163 */       this._alpha = 0.0D;
/* 164 */     } else if (a <= 50.0D) {
/* 165 */       this._alpha = 0.5842D * MathPlus.pow(a - 21.0D, 0.4D) + 0.07886D * (a - 21.0D);
/*     */     } else {
/* 167 */       this._alpha = 0.1102D * (a - 8.7D);
/*     */     } 
/* 169 */     this._scale = 1.0D / ino(this._alpha);
/* 170 */     this._xxmax = 0.25D * this._length * this._length;
/*     */   }
/*     */   
/*     */   private double ino(double x) {
/* 174 */     double s = 1.0D;
/* 175 */     double ds = 1.0D;
/* 176 */     double d = 0.0D;
/*     */     while (true) {
/* 178 */       d += 2.0D;
/* 179 */       ds *= x * x / d * d;
/* 180 */       s += ds;
/* 181 */       if (ds <= s * 2.220446049250313E-16D)
/* 182 */         return s; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/KaiserWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */