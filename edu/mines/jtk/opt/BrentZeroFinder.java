/*     */ package edu.mines.jtk.opt;
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
/*     */ public class BrentZeroFinder
/*     */ {
/*     */   private Function _f;
/*     */   private static final double EPS = 2.220446049250313E-16D;
/*     */   
/*     */   public BrentZeroFinder(Function f) {
/*  66 */     this._f = f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double f(double x) {
/*  75 */     return this._f.evaluate(x);
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
/*     */   public double findZero(double a, double b, double tol) {
/*  87 */     double fa = this._f.evaluate(a);
/*  88 */     double fb = this._f.evaluate(b);
/*  89 */     return findZero(a, fa, b, fb, tol);
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
/*     */   public double findZero(double a, double fa, double b, double fb, double tol) {
/* 106 */     Check.argument((a < b), "Invalid Search Interval");
/* 107 */     Check.argument(((fa <= 0.0D && fb >= 0.0D) || (fa >= 0.0D && fb <= 0.0D)), "Function values must not have same sign");
/*     */     
/* 109 */     Check.argument((tol > 0.0D), "Accuracy must be greater than zero");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     double c = a;
/* 117 */     double fc = fa;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 123 */       double e = b - a;
/*     */ 
/*     */       
/* 126 */       if (MathPlus.abs(fc) < MathPlus.abs(fb)) {
/* 127 */         a = b; fa = fb;
/* 128 */         b = c; fb = fc;
/* 129 */         c = a; fc = fa;
/*     */       } 
/*     */ 
/*     */       
/* 133 */       double d = 0.5D * (c - b);
/*     */ 
/*     */       
/* 136 */       double dtol = 4.440892098500626E-16D * MathPlus.abs(b) + 0.5D * tol;
/* 137 */       if (MathPlus.abs(d) <= dtol || fb == 0.0D) {
/* 138 */         return b;
/*     */       }
/*     */       
/* 141 */       if (MathPlus.abs(e) >= dtol && MathPlus.abs(fa) > MathPlus.abs(fb)) {
/*     */         double p;
/*     */ 
/*     */         
/*     */         double q;
/*     */ 
/*     */         
/* 148 */         if (a == c) {
/* 149 */           double s = fb / fa;
/* 150 */           p = 2.0D * d * s;
/* 151 */           q = 1.0D - s;
/*     */         } else {
/* 153 */           double r = fb / fc;
/* 154 */           double s = fb / fa;
/* 155 */           q = fa / fc;
/* 156 */           p = s * (2.0D * d * q * (q - r) - (b - a) * (r - 1.0D));
/* 157 */           q = (q - 1.0D) * (r - 1.0D) * (s - 1.0D);
/*     */         } 
/* 159 */         if (p > 0.0D) {
/* 160 */           q = -q;
/*     */         } else {
/* 162 */           p = -p;
/*     */         } 
/*     */ 
/*     */         
/* 166 */         if (2.0D * p < 3.0D * d * q - MathPlus.abs(dtol * q) && p < MathPlus.abs(0.5D * e * q)) {
/* 167 */           d = p / q;
/*     */         }
/*     */       } 
/*     */       
/* 171 */       if (MathPlus.abs(d) < dtol) {
/* 172 */         d = (d > 0.0D) ? dtol : -dtol;
/*     */       }
/*     */       
/* 175 */       a = b; fa = fb;
/* 176 */       b += d; fb = this._f.evaluate(b);
/*     */ 
/*     */       
/* 179 */       if ((fb > 0.0D && fc > 0.0D) || (fb < 0.0D && fc < 0.0D)) {
/* 180 */         c = a; fc = fa;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Function {
/*     */     double evaluate(double param1Double);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/BrentZeroFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */