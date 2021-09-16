/*     */ package Catalano.Math.Functions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Beta
/*     */ {
/*     */   public static double Function(double a, double b) {
/*  47 */     return Math.exp(Log(a, b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Log(double a, double b) {
/*  57 */     return Gamma.Log(a) + Gamma.Log(b) - Gamma.Log(a + b);
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
/*     */   public static double Incomplete(double a, double b, double x) {
/*     */     double aa, bb, xx, xc;
/*  71 */     if (a <= 0.0D) {
/*     */       try {
/*  73 */         throw new IllegalArgumentException(" 'a' Lower limit must be greater than zero.");
/*  74 */       } catch (Exception e) {
/*  75 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  78 */     if (b <= 0.0D) {
/*     */       try {
/*  80 */         throw new IllegalArgumentException(" 'b' Upper limit must be greater than zero.");
/*  81 */       } catch (Exception e) {
/*  82 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  86 */     if (x <= 0.0D || x >= 1.0D) {
/*     */       
/*  88 */       if (x == 0.0D) return 0.0D; 
/*  89 */       if (x == 1.0D) return 1.0D; 
/*     */       try {
/*  91 */         throw new IllegalArgumentException(" 'x' Value must be between 0 and 1.");
/*  92 */       } catch (Exception e) {
/*  93 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     boolean flag = false;
/*  98 */     if (b * x <= 1.0D && x <= 0.95D) {
/*     */       
/* 100 */       double d = PowerSeries(a, b, x);
/* 101 */       return d;
/*     */     } 
/*     */     
/* 104 */     double w = 1.0D - x;
/*     */     
/* 106 */     if (x > a / (a + b)) {
/*     */       
/* 108 */       flag = true;
/* 109 */       aa = b;
/* 110 */       bb = a;
/* 111 */       xc = x;
/* 112 */       xx = w;
/*     */     }
/*     */     else {
/*     */       
/* 116 */       aa = a;
/* 117 */       bb = b;
/* 118 */       xc = w;
/* 119 */       xx = x;
/*     */     } 
/*     */     
/* 122 */     if (flag && bb * xx <= 1.0D && xx <= 0.95D) {
/*     */       
/* 124 */       double d = PowerSeries(aa, bb, xx);
/* 125 */       if (d <= 1.1102230246251565E-16D) { d = 0.9999999999999999D; }
/* 126 */       else { d = 1.0D - d; }
/* 127 */        return d;
/*     */     } 
/*     */     
/* 130 */     double y = xx * (aa + bb - 2.0D) - aa - 1.0D;
/* 131 */     if (y < 0.0D) {
/* 132 */       w = Incbcf(aa, bb, xx);
/*     */     } else {
/* 134 */       w = Incbd(aa, bb, xx) / xc;
/*     */     } 
/*     */     
/* 137 */     y = aa * Math.log(xx);
/* 138 */     double t = bb * Math.log(xc);
/* 139 */     if (aa + bb < 171.6243769563027D && Math.abs(y) < 709.782712893384D && Math.abs(t) < 709.782712893384D) {
/*     */       
/* 141 */       t = Math.pow(xc, bb);
/* 142 */       t *= Math.pow(xx, aa);
/* 143 */       t /= aa;
/* 144 */       t *= w;
/* 145 */       t *= Gamma.Function(aa + bb) / Gamma.Function(aa) * Gamma.Function(bb);
/* 146 */       if (flag)
/*     */       {
/* 148 */         if (t <= 1.1102230246251565E-16D) { t = 0.9999999999999999D; }
/* 149 */         else { t = 1.0D - t; }
/*     */          } 
/* 151 */       return t;
/*     */     } 
/*     */     
/* 154 */     y += t + Gamma.Log(aa + bb) - Gamma.Log(aa) - Gamma.Log(bb);
/* 155 */     y += Math.log(w / aa);
/* 156 */     if (y < -745.1332191019412D) {
/* 157 */       t = 0.0D;
/*     */     } else {
/* 159 */       t = Math.exp(y);
/*     */     } 
/* 161 */     if (flag)
/*     */     {
/* 163 */       if (t <= 1.1102230246251565E-16D) { t = 0.9999999999999999D; }
/* 164 */       else { t = 1.0D - t; }
/*     */        } 
/* 166 */     return t;
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
/*     */   public static double Incbcf(double a, double b, double x) {
/* 181 */     double big = 4.503599627370496E15D;
/* 182 */     double biginv = 2.220446049250313E-16D;
/*     */     
/* 184 */     double k1 = a;
/* 185 */     double k2 = a + b;
/* 186 */     double k3 = a;
/* 187 */     double k4 = a + 1.0D;
/* 188 */     double k5 = 1.0D;
/* 189 */     double k6 = b - 1.0D;
/* 190 */     double k7 = k4;
/* 191 */     double k8 = a + 2.0D;
/*     */     
/* 193 */     double pkm2 = 0.0D;
/* 194 */     double qkm2 = 1.0D;
/* 195 */     double pkm1 = 1.0D;
/* 196 */     double qkm1 = 1.0D;
/* 197 */     double ans = 1.0D;
/* 198 */     double r = 1.0D;
/* 199 */     int n = 0;
/* 200 */     double thresh = 3.3306690738754696E-16D;
/*     */ 
/*     */     
/*     */     do {
/* 204 */       double t, xk = -(x * k1 * k2) / k3 * k4;
/* 205 */       double pk = pkm1 + pkm2 * xk;
/* 206 */       double qk = qkm1 + qkm2 * xk;
/* 207 */       pkm2 = pkm1;
/* 208 */       pkm1 = pk;
/* 209 */       qkm2 = qkm1;
/* 210 */       qkm1 = qk;
/*     */       
/* 212 */       xk = x * k5 * k6 / k7 * k8;
/* 213 */       pk = pkm1 + pkm2 * xk;
/* 214 */       qk = qkm1 + qkm2 * xk;
/* 215 */       pkm2 = pkm1;
/* 216 */       pkm1 = pk;
/* 217 */       qkm2 = qkm1;
/* 218 */       qkm1 = qk;
/*     */       
/* 220 */       if (qk != 0.0D) r = pk / qk; 
/* 221 */       if (r != 0.0D) {
/*     */         
/* 223 */         t = Math.abs((ans - r) / r);
/* 224 */         ans = r;
/*     */       } else {
/*     */         
/* 227 */         t = 1.0D;
/*     */       } 
/* 229 */       if (t < thresh) return ans;
/*     */       
/* 231 */       k1++;
/* 232 */       k2++;
/* 233 */       k3 += 2.0D;
/* 234 */       k4 += 2.0D;
/* 235 */       k5++;
/* 236 */       k6--;
/* 237 */       k7 += 2.0D;
/* 238 */       k8 += 2.0D;
/*     */       
/* 240 */       if (Math.abs(qk) + Math.abs(pk) > big) {
/*     */         
/* 242 */         pkm2 *= biginv;
/* 243 */         pkm1 *= biginv;
/* 244 */         qkm2 *= biginv;
/* 245 */         qkm1 *= biginv;
/*     */       } 
/* 247 */       if (Math.abs(qk) >= biginv && Math.abs(pk) >= biginv)
/*     */         continue; 
/* 249 */       pkm2 *= big;
/* 250 */       pkm1 *= big;
/* 251 */       qkm2 *= big;
/* 252 */       qkm1 *= big;
/*     */     }
/* 254 */     while (++n < 300);
/*     */     
/* 256 */     return ans;
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
/*     */   public static double Incbd(double a, double b, double x) {
/* 271 */     double big = 4.503599627370496E15D;
/* 272 */     double biginv = 2.220446049250313E-16D;
/*     */     
/* 274 */     double k1 = a;
/* 275 */     double k2 = b - 1.0D;
/* 276 */     double k3 = a;
/* 277 */     double k4 = a + 1.0D;
/* 278 */     double k5 = 1.0D;
/* 279 */     double k6 = a + b;
/* 280 */     double k7 = a + 1.0D;
/* 281 */     double k8 = a + 2.0D;
/*     */     
/* 283 */     double pkm2 = 0.0D;
/* 284 */     double qkm2 = 1.0D;
/* 285 */     double pkm1 = 1.0D;
/* 286 */     double qkm1 = 1.0D;
/* 287 */     double z = x / (1.0D - x);
/* 288 */     double ans = 1.0D;
/* 289 */     double r = 1.0D;
/* 290 */     int n = 0;
/* 291 */     double thresh = 3.3306690738754696E-16D;
/*     */     
/*     */     do {
/* 294 */       double t, xk = -(z * k1 * k2) / k3 * k4;
/* 295 */       double pk = pkm1 + pkm2 * xk;
/* 296 */       double qk = qkm1 + qkm2 * xk;
/* 297 */       pkm2 = pkm1;
/* 298 */       pkm1 = pk;
/* 299 */       qkm2 = qkm1;
/* 300 */       qkm1 = qk;
/*     */       
/* 302 */       xk = z * k5 * k6 / k7 * k8;
/* 303 */       pk = pkm1 + pkm2 * xk;
/* 304 */       qk = qkm1 + qkm2 * xk;
/* 305 */       pkm2 = pkm1;
/* 306 */       pkm1 = pk;
/* 307 */       qkm2 = qkm1;
/* 308 */       qkm1 = qk;
/*     */       
/* 310 */       if (qk != 0.0D) r = pk / qk; 
/* 311 */       if (r != 0.0D) {
/*     */         
/* 313 */         t = Math.abs((ans - r) / r);
/* 314 */         ans = r;
/*     */       } else {
/*     */         
/* 317 */         t = 1.0D;
/*     */       } 
/* 319 */       if (t < thresh) return ans;
/*     */       
/* 321 */       k1++;
/* 322 */       k2--;
/* 323 */       k3 += 2.0D;
/* 324 */       k4 += 2.0D;
/* 325 */       k5++;
/* 326 */       k6++;
/* 327 */       k7 += 2.0D;
/* 328 */       k8 += 2.0D;
/*     */       
/* 330 */       if (Math.abs(qk) + Math.abs(pk) > big) {
/*     */         
/* 332 */         pkm2 *= biginv;
/* 333 */         pkm1 *= biginv;
/* 334 */         qkm2 *= biginv;
/* 335 */         qkm1 *= biginv;
/*     */       } 
/* 337 */       if (Math.abs(qk) >= biginv && Math.abs(pk) >= biginv)
/*     */         continue; 
/* 339 */       pkm2 *= big;
/* 340 */       pkm1 *= big;
/* 341 */       qkm2 *= big;
/* 342 */       qkm1 *= big;
/*     */     }
/* 344 */     while (++n < 300);
/*     */     
/* 346 */     return ans;
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
/*     */   public static double PowerSeries(double a, double b, double x) {
/* 359 */     double ai = 1.0D / a;
/* 360 */     double u = (1.0D - b) * x;
/* 361 */     double v = u / (a + 1.0D);
/* 362 */     double t1 = v;
/* 363 */     double t = u;
/* 364 */     double n = 2.0D;
/* 365 */     double s = 0.0D;
/* 366 */     double z = 1.1102230246251565E-16D * ai;
/* 367 */     while (Math.abs(v) > z) {
/*     */       
/* 369 */       u = (n - b) * x / n;
/* 370 */       t *= u;
/* 371 */       v = t / (a + n);
/* 372 */       s += v;
/* 373 */       n++;
/*     */     } 
/* 375 */     s += t1;
/* 376 */     s += ai;
/*     */     
/* 378 */     u = a * Math.log(x);
/* 379 */     if (a + b < 171.6243769563027D && Math.abs(u) < 709.782712893384D) {
/*     */       
/* 381 */       t = Gamma.Function(a + b) / Gamma.Function(a) * Gamma.Function(b);
/* 382 */       s = s * t * Math.pow(x, a);
/*     */     }
/*     */     else {
/*     */       
/* 386 */       t = Gamma.Log(a + b) - Gamma.Log(a) - Gamma.Log(b) + u + Math.log(s);
/* 387 */       if (t < -745.1332191019412D) { s = 0.0D; }
/* 388 */       else { s = Math.exp(t); }
/*     */     
/* 390 */     }  return s;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Beta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */