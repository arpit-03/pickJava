/*     */ package edu.mines.jtk.opt;
/*     */ 
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
/*     */ public class BrentMinFinder
/*     */ {
/*     */   public BrentMinFinder(Function f) {
/*  74 */     this._f = f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double f(double x) {
/*  83 */     return this._f.evaluate(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double findMin(double a, double b, double tol) {
/*  94 */     double x = a + GSI * (b - a);
/*  95 */     double v = x;
/*  96 */     double w = x;
/*  97 */     double fx = f(x);
/*  98 */     double fv = fx;
/*  99 */     double fw = fx;
/* 100 */     double d = 0.0D;
/* 101 */     double e = 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 105 */     double xm = 0.5D * (a + b), tol1 = EPS * MathPlus.abs(x) + tol / 3.0D, tol2 = 2.0D * tol1;
/* 106 */     for (; MathPlus.abs(x - xm) > tol2 - 0.5D * (b - a); 
/* 107 */       xm = 0.5D * (a + b), tol1 = EPS * MathPlus.abs(x) + tol / 3.0D, tol2 = 2.0D * tol1) {
/*     */ 
/*     */       
/* 110 */       boolean gsstep = (MathPlus.abs(e) <= tol1);
/*     */ 
/*     */       
/* 113 */       if (!gsstep) {
/*     */ 
/*     */         
/* 116 */         double r = (x - w) * (fx - fv);
/* 117 */         double q = (x - v) * (fx - fw);
/* 118 */         double p = (x - v) * q - (x - w) * r;
/* 119 */         q = 2.0D * (q - r);
/* 120 */         if (q > 0.0D)
/* 121 */           p = -p; 
/* 122 */         q = MathPlus.abs(q);
/* 123 */         r = e;
/* 124 */         e = d;
/*     */ 
/*     */ 
/*     */         
/* 128 */         if (MathPlus.abs(p) < MathPlus.abs(0.5D * q * r) && p > q * (a - x) && p < q * (b - x)) {
/* 129 */           d = p / q;
/* 130 */           double d1 = x + d;
/* 131 */           if (d1 - a < tol2 || b - d1 < tol2) {
/* 132 */             d = (xm >= x) ? tol1 : -tol1;
/*     */           }
/*     */         } else {
/*     */           
/* 136 */           gsstep = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 141 */       if (gsstep) {
/* 142 */         e = (x >= xm) ? (a - x) : (b - x);
/* 143 */         d = GSI * e;
/*     */       } 
/*     */ 
/*     */       
/* 147 */       double u = x + ((MathPlus.abs(d) >= tol1) ? d : ((d >= 0.0D) ? tol1 : -tol1));
/* 148 */       double fu = f(u);
/*     */ 
/*     */       
/* 151 */       if (fu <= fx) {
/* 152 */         if (u >= x) {
/* 153 */           a = x;
/*     */         } else {
/* 155 */           b = x;
/* 156 */         }  v = w;
/* 157 */         w = x;
/* 158 */         x = u;
/* 159 */         fv = fw;
/* 160 */         fw = fx;
/* 161 */         fx = fu;
/*     */       } else {
/* 163 */         if (u < x) {
/* 164 */           a = u;
/*     */         } else {
/* 166 */           b = u;
/* 167 */         }  if (fu <= fw || w == x) {
/* 168 */           v = w;
/* 169 */           w = u;
/* 170 */           fv = fw;
/* 171 */           fw = fu;
/* 172 */         } else if (fu <= fv || v == x || v == w) {
/* 173 */           v = u;
/* 174 */           fv = fu;
/*     */         } 
/*     */       } 
/*     */     } 
/* 178 */     return x;
/*     */   }
/*     */ 
/*     */   
/* 182 */   private static final double GSI = 0.5D * (3.0D - MathPlus.sqrt(5.0D));
/*     */ 
/*     */   
/* 185 */   private static final double EPS = MathPlus.sqrt(2.220446049250313E-16D);
/*     */   private Function _f;
/*     */   
/*     */   public static interface Function {
/*     */     double evaluate(double param1Double);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/BrentMinFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */