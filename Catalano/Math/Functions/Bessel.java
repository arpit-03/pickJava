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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Bessel
/*     */ {
/*     */   public static double J0(double x) {
/*     */     double ax;
/*  66 */     if ((ax = Math.abs(x)) < 8.0D) {
/*  67 */       double d1 = x * x;
/*  68 */       double d2 = 5.7568490574E10D + d1 * (-1.3362590354E10D + d1 * (6.516196407E8D + 
/*  69 */         d1 * (-1.121442418E7D + d1 * (77392.33017D + d1 * -184.9052456D))));
/*  70 */       double d3 = 5.7568490411E10D + d1 * (1.029532985E9D + d1 * (9494680.718D + 
/*  71 */         d1 * (59272.64853D + d1 * (267.8532712D + d1 * 1.0D))));
/*     */       
/*  73 */       return d2 / d3;
/*     */     } 
/*     */     
/*  76 */     double z = 8.0D / ax;
/*  77 */     double y = z * z;
/*  78 */     double xx = ax - 0.785398164D;
/*  79 */     double ans1 = 1.0D + y * (-0.001098628627D + y * (2.734510407E-5D + 
/*  80 */       y * (-2.073370639E-6D + y * 2.093887211E-7D)));
/*  81 */     double ans2 = -0.01562499995D + y * (1.430488765E-4D + 
/*  82 */       y * (-6.911147651E-6D + y * (7.621095161E-7D - 
/*  83 */       y * 9.34935152E-8D)));
/*     */     
/*  85 */     return Math.sqrt(0.636619772D / ax) * (
/*  86 */       Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
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
/*     */   public static double J(double x) {
/*     */     double ax;
/* 100 */     if ((ax = Math.abs(x)) < 8.0D) {
/* 101 */       double d1 = x * x;
/* 102 */       double d2 = x * (7.2362614232E10D + d1 * (-7.895059235E9D + d1 * (2.423968531E8D + 
/* 103 */         d1 * (-2972611.439D + d1 * (15704.4826D + d1 * -30.16036606D)))));
/* 104 */       double d3 = 1.44725228442E11D + d1 * (2.300535178E9D + d1 * (1.858330474E7D + 
/* 105 */         d1 * (99447.43394D + d1 * (376.9991397D + d1 * 1.0D))));
/* 106 */       return d2 / d3;
/*     */     } 
/*     */     
/* 109 */     double z = 8.0D / ax;
/* 110 */     double xx = ax - 2.356194491D;
/* 111 */     double y = z * z;
/*     */     
/* 113 */     double ans1 = 1.0D + y * (0.00183105D + y * (-3.516396496E-5D + 
/* 114 */       y * (2.457520174E-6D + y * -2.40337019E-7D)));
/* 115 */     double ans2 = 0.04687499995D + y * (-2.002690873E-4D + 
/* 116 */       y * (8.449199096E-6D + y * (-8.8228987E-7D + 
/* 117 */       y * 1.05787412E-7D)));
/* 118 */     double ans = Math.sqrt(0.636619772D / ax) * (
/* 119 */       Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
/* 120 */     if (x < 0.0D) ans = -ans; 
/* 121 */     return ans;
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
/*     */   public static double J(int n, double x) {
/* 136 */     double ans, ACC = 40.0D;
/* 137 */     double BIGNO = 1.0E10D;
/* 138 */     double BIGNI = 1.0E-10D;
/*     */     
/* 140 */     if (n == 0) return J0(x); 
/* 141 */     if (n == 1) return J(x);
/*     */     
/* 143 */     double ax = Math.abs(x);
/* 144 */     if (ax == 0.0D) return 0.0D; 
/* 145 */     if (ax > n) {
/*     */       
/* 147 */       double tox = 2.0D / ax;
/* 148 */       double bjm = J0(ax);
/* 149 */       double bj = J(ax);
/* 150 */       for (int j = 1; j < n; j++) {
/*     */         
/* 152 */         double bjp = j * tox * bj - bjm;
/* 153 */         bjm = bj;
/* 154 */         bj = bjp;
/*     */       } 
/* 156 */       ans = bj;
/*     */     }
/*     */     else {
/*     */       
/* 160 */       double tox = 2.0D / ax;
/* 161 */       int m = 2 * (n + (int)Math.sqrt(ACC * n)) / 2;
/* 162 */       boolean jsum = false;
/* 163 */       double sum = 0.0D, bjp = ans = sum = 0.0D;
/* 164 */       double bj = 1.0D;
/* 165 */       int j = m;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     return (x < 0.0D && n % 2 == 1) ? -ans : ans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Y0(double x) {
/* 194 */     if (x < 8.0D) {
/* 195 */       double d1 = x * x;
/*     */       
/* 197 */       double d2 = -2.957821389E9D + d1 * (7.062834065E9D + d1 * (-5.123598036E8D + 
/* 198 */         d1 * (1.087988129E7D + d1 * (-86327.92757D + d1 * 228.4622733D))));
/* 199 */       double d3 = 4.0076544269E10D + d1 * (7.452499648E8D + d1 * (7189466.438D + 
/* 200 */         d1 * (47447.2647D + d1 * (226.1030244D + d1 * 1.0D))));
/*     */       
/* 202 */       return d2 / d3 + 0.636619772D * J0(x) * Math.log(x);
/*     */     } 
/*     */     
/* 205 */     double z = 8.0D / x;
/* 206 */     double y = z * z;
/* 207 */     double xx = x - 0.785398164D;
/*     */     
/* 209 */     double ans1 = 1.0D + y * (-0.001098628627D + y * (2.734510407E-5D + 
/* 210 */       y * (-2.073370639E-6D + y * 2.093887211E-7D)));
/* 211 */     double ans2 = -0.01562499995D + y * (1.430488765E-4D + 
/* 212 */       y * (-6.911147651E-6D + y * (7.621095161E-7D + 
/* 213 */       y * -9.34945152E-8D)));
/* 214 */     return Math.sqrt(0.636619772D / x) * (
/* 215 */       Math.sin(xx) * ans1 + z * Math.cos(xx) * ans2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Y(double x) {
/* 225 */     if (x < 8.0D) {
/*     */       
/* 227 */       double d1 = x * x;
/* 228 */       double d2 = x * (-4.900604943E12D + d1 * (1.27527439E12D + 
/* 229 */         d1 * (-5.153438139E10D + d1 * (7.349264551E8D + 
/* 230 */         d1 * (-4237922.726D + d1 * 8511.937935D)))));
/* 231 */       double d3 = 2.49958057E13D + d1 * (4.244419664E11D + 
/* 232 */         d1 * (3.733650367E9D + d1 * (2.245904002E7D + 
/* 233 */         d1 * (102042.605D + d1 * (354.9632885D + d1)))));
/* 234 */       return d2 / d3 + 0.636619772D * (J(x) * Math.log(x) - 1.0D / x);
/*     */     } 
/*     */ 
/*     */     
/* 238 */     double z = 8.0D / x;
/* 239 */     double y = z * z;
/* 240 */     double xx = x - 2.356194491D;
/* 241 */     double ans1 = 1.0D + y * (0.00183105D + y * (-3.516396496E-5D + 
/* 242 */       y * (2.457520174E-6D + y * -2.40337019E-7D)));
/* 243 */     double ans2 = 0.04687499995D + y * (-2.002690873E-4D + 
/* 244 */       y * (8.449199096E-6D + y * (-8.8228987E-7D + 
/* 245 */       y * 1.05787412E-7D)));
/* 246 */     return Math.sqrt(0.636619772D / x) * (
/* 247 */       Math.sin(xx) * ans1 + z * Math.cos(xx) * ans2);
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
/*     */   public static double Y(int n, double x) {
/* 260 */     if (n == 0) return Y0(x); 
/* 261 */     if (n == 1) return Y(x);
/*     */     
/* 263 */     double tox = 2.0D / x;
/* 264 */     double by = Y(x);
/* 265 */     double bym = Y0(x);
/* 266 */     for (int j = 1; j < n; j++) {
/* 267 */       double byp = j * tox * by - bym;
/* 268 */       bym = by;
/* 269 */       by = byp;
/*     */     } 
/* 271 */     return by;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double I0(double x) {
/* 281 */     double ans, ax = Math.abs(x);
/*     */     
/* 283 */     if (ax < 3.75D) {
/* 284 */       double y = x / 3.75D;
/* 285 */       y *= y;
/* 286 */       ans = 1.0D + y * (3.5156229D + y * (3.0899424D + y * (1.2067492D + 
/* 287 */         y * (0.2659732D + y * (0.0360768D + y * 0.0045813D)))));
/*     */     } else {
/*     */       
/* 290 */       double y = 3.75D / ax;
/* 291 */       ans = Math.exp(ax) / Math.sqrt(ax) * (0.39894228D + y * (0.01328592D + 
/* 292 */         y * (0.00225319D + y * (-0.00157565D + y * (0.00916281D + 
/* 293 */         y * (-0.02057706D + y * (0.02635537D + y * (-0.01647633D + 
/* 294 */         y * 0.00392377D))))))));
/*     */     } 
/*     */     
/* 297 */     return ans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double I(double x) {
/* 308 */     double ans, ax = Math.abs(x);
/*     */     
/* 310 */     if (ax < 3.75D) {
/* 311 */       double y = x / 3.75D;
/* 312 */       y *= y;
/* 313 */       ans = ax * (0.5D + y * (0.87890594D + y * (0.51498869D + y * (0.15084934D + 
/* 314 */         y * (0.02658733D + y * (0.00301532D + y * 3.2411E-4D))))));
/*     */     } else {
/*     */       
/* 317 */       double y = 3.75D / ax;
/* 318 */       ans = 0.02282967D + y * (-0.02895312D + y * (0.01787654D - y * 0.00420059D));
/* 319 */       ans = 0.39894228D + y * (-0.03988024D + y * (-0.00362018D + y * (0.00163801D + y * (-0.01031555D + y * ans))));
/* 320 */       ans *= Math.exp(ax) / Math.sqrt(ax);
/*     */     } 
/* 322 */     return (x < 0.0D) ? -ans : ans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double I(int n, double x) {
/* 332 */     if (n < 0)
/* 333 */       throw new IllegalArgumentException("the variable n out of range."); 
/* 334 */     if (n == 0)
/* 335 */       return I0(x); 
/* 336 */     if (n == 1) {
/* 337 */       return I(x);
/*     */     }
/* 339 */     if (x == 0.0D) {
/* 340 */       return 0.0D;
/*     */     }
/* 342 */     double ACC = 40.0D;
/* 343 */     double BIGNO = 1.0E10D;
/* 344 */     double BIGNI = 1.0E-10D;
/*     */     
/* 346 */     double tox = 2.0D / Math.abs(x);
/* 347 */     double bip = 0.0D, ans = 0.0D;
/* 348 */     double bi = 1.0D;
/*     */     
/* 350 */     for (int j = 2 * (n + (int)Math.sqrt(ACC * n)); j > 0; j--) {
/*     */       
/* 352 */       double bim = bip + j * tox * bi;
/* 353 */       bip = bi;
/* 354 */       bi = bim;
/*     */       
/* 356 */       if (Math.abs(bi) > BIGNO) {
/*     */         
/* 358 */         ans *= BIGNI;
/* 359 */         bi *= BIGNI;
/* 360 */         bip *= BIGNI;
/*     */       } 
/*     */       
/* 363 */       if (j == n) {
/* 364 */         ans = bip;
/*     */       }
/*     */     } 
/* 367 */     ans *= I0(x) / bi;
/* 368 */     return (x < 0.0D && n % 2 == 1) ? -ans : ans;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Bessel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */