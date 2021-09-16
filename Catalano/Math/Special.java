/*     */ package Catalano.Math;
/*     */ 
/*     */ import Catalano.Math.Functions.Gamma;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Special
/*     */ {
/*     */   private static int ftop;
/*     */   private static double[] fcache;
/*     */   private static double[] lnfcache;
/*     */   
/*     */   public static double Erfc(double value) {
/*  48 */     double x, p, q, P[] = {
/*     */         
/*  50 */         2.461969814735305E-10D, 
/*  51 */         0.5641895648310689D, 
/*  52 */         7.463210564422699D, 
/*  53 */         48.63719709856814D, 
/*  54 */         196.5208329560771D, 
/*  55 */         526.4451949954773D, 
/*  56 */         934.5285271719576D, 
/*  57 */         1027.5518868951572D, 
/*  58 */         557.5353353693994D
/*     */       };
/*  60 */     double[] Q = {
/*     */         
/*  62 */         13.228195115474499D, 
/*  63 */         86.70721408859897D, 
/*  64 */         354.9377788878199D, 
/*  65 */         975.7085017432055D, 
/*  66 */         1823.9091668790973D, 
/*  67 */         2246.3376081871097D, 
/*  68 */         1656.6630919416134D, 
/*  69 */         557.5353408177277D
/*     */       };
/*     */     
/*  72 */     double[] R = {
/*     */         
/*  74 */         0.5641895835477551D, 
/*  75 */         1.275366707599781D, 
/*  76 */         5.019050422511805D, 
/*  77 */         6.160210979930536D, 
/*  78 */         7.4097426995044895D, 
/*  79 */         2.9788666537210022D
/*     */       };
/*  81 */     double[] S = {
/*     */         
/*  83 */         2.2605286322011726D, 
/*  84 */         9.396035249380015D, 
/*  85 */         12.048953980809666D, 
/*  86 */         17.08144507475659D, 
/*  87 */         9.608968090632859D, 
/*  88 */         3.369076451000815D
/*     */       };
/*     */     
/*  91 */     if (value < 0.0D) { x = -value; }
/*  92 */     else { x = value; }
/*     */     
/*  94 */     if (x < 1.0D) return 1.0D - Erf(value);
/*     */     
/*  96 */     double z = -value * value;
/*     */     
/*  98 */     if (z < -709.782712893384D) {
/*     */       
/* 100 */       if (value < 0.0D) return 2.0D; 
/* 101 */       return 0.0D;
/*     */     } 
/*     */     
/* 104 */     z = Math.exp(z);
/*     */     
/* 106 */     if (x < 8.0D) {
/*     */       
/* 108 */       p = Polevl(x, P, 8);
/* 109 */       q = P1evl(x, Q, 8);
/*     */     }
/*     */     else {
/*     */       
/* 113 */       p = Polevl(x, R, 5);
/* 114 */       q = P1evl(x, S, 6);
/*     */     } 
/*     */     
/* 117 */     double y = z * p / q;
/*     */     
/* 119 */     if (value < 0.0D) y = 2.0D - y;
/*     */     
/* 121 */     if (y == 0.0D) {
/*     */       
/* 123 */       if (value < 0.0D) return 2.0D; 
/* 124 */       return 0.0D;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Erf(double x) {
/* 138 */     double[] T = {
/*     */         
/* 140 */         9.604973739870516D, 
/* 141 */         90.02601972038427D, 
/* 142 */         2232.005345946843D, 
/* 143 */         7003.325141128051D, 
/* 144 */         55592.30130103949D
/*     */       };
/* 146 */     double[] U = {
/*     */         
/* 148 */         33.56171416475031D, 
/* 149 */         521.3579497801527D, 
/* 150 */         4594.323829709801D, 
/* 151 */         22629.000061389095D, 
/* 152 */         49267.39426086359D
/*     */       };
/*     */     
/* 155 */     if (Math.abs(x) > 1.0D) {
/* 156 */       return 1.0D - Erfc(x);
/*     */     }
/* 158 */     double z = x * x;
/* 159 */     double y = x * Polevl(z, T, 4) / P1evl(z, U, 5);
/*     */     
/* 161 */     return y;
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
/*     */   public static double Polevl(double x, double[] coef, int n) {
/* 174 */     double ans = coef[0];
/*     */     
/* 176 */     for (int i = 1; i <= n; i++) {
/* 177 */       ans = ans * x + coef[i];
/*     */     }
/* 179 */     return ans;
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
/*     */   public static double P1evl(double x, double[] coef, int n) {
/* 192 */     double ans = x + coef[0];
/*     */     
/* 194 */     for (int i = 1; i < n; i++) {
/* 195 */       ans = ans * x + coef[i];
/*     */     }
/* 197 */     return ans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double BSpline(int n, double x) {
/* 207 */     if (n == Integer.MAX_VALUE) {
/* 208 */       throw new IllegalArgumentException("n");
/*     */     }
/*     */     
/* 211 */     double a = 1.0D / Factorial(n);
/*     */ 
/*     */     
/* 214 */     boolean positive = true;
/* 215 */     for (int k = 0; k <= n + 1; k++) {
/*     */       
/* 217 */       double c = Binomial(n + 1, k) * Tools.TruncatedPower(x + (n + 1.0D) / 2.0D - k, n);
/* 218 */       a += positive ? c : -c;
/* 219 */       positive = !positive;
/*     */     } 
/*     */     
/* 222 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Binomial(int n, int k) {
/* 232 */     return Math.round(Math.exp(LogFactorial(n) - LogFactorial(k) - LogFactorial(n - k)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double LogBinomial(int n, int k) {
/* 242 */     return LogFactorial(n) - LogFactorial(k) - LogFactorial(n - k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double LogFactorial(int n) {
/* 251 */     if (lnfcache == null) {
/* 252 */       lnfcache = new double[101];
/*     */     }
/* 254 */     if (n < 0) {
/*     */       
/*     */       try {
/*     */         
/* 258 */         throw new ArithmeticException("Argument cannot be negative.");
/* 259 */       } catch (Exception e) {
/* 260 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 263 */     if (n <= 1)
/*     */     {
/*     */       
/* 266 */       return 0.0D;
/*     */     }
/* 268 */     if (n <= 100)
/*     */     {
/*     */ 
/*     */       
/* 272 */       return (lnfcache[n] > 0.0D) ? lnfcache[n] : (lnfcache[n] = Gamma.Log(n + 1.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 277 */     return Gamma.Log(n + 1.0D);
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
/*     */   public static double Factorial(int n) {
/* 292 */     if (fcache == null) {
/*     */       
/* 294 */       fcache = new double[33];
/* 295 */       fcache[0] = 1.0D; fcache[1] = 1.0D;
/* 296 */       fcache[2] = 2.0D; fcache[3] = 6.0D;
/* 297 */       fcache[4] = 24.0D; ftop = 4;
/*     */     } 
/*     */     
/* 300 */     if (n < 0) {
/*     */       try {
/* 302 */         throw new ArithmeticException("Argument cannot be negative.");
/* 303 */       } catch (Exception e) {
/* 304 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 307 */     if (n > 32)
/*     */     {
/*     */       
/* 310 */       return Math.exp(Gamma.Log(n + 1.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 315 */     while (ftop < n) {
/* 316 */       int j = ftop++;
/* 317 */       fcache[ftop] = fcache[j] * ftop;
/*     */     } 
/* 319 */     return fcache[n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Log1m(double x) {
/* 329 */     if (x >= 1.0D) {
/* 330 */       return Double.NaN;
/*     */     }
/* 332 */     if (Math.abs(x) > 1.0E-4D) {
/* 333 */       return Math.log(1.0D - x);
/*     */     }
/*     */ 
/*     */     
/* 337 */     return -(0.5D * x + 1.0D) * x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Log1p(double x) {
/* 346 */     if (x <= -1.0D) {
/* 347 */       return Double.NaN;
/*     */     }
/* 349 */     if (Math.abs(x) > 1.0E-4D) {
/* 350 */       return Math.log(1.0D + x);
/*     */     }
/*     */ 
/*     */     
/* 354 */     return (-0.5D * x + 1.0D) * x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Expm1(double x) {
/* 363 */     if (Math.abs(x) < 1.0E-5D) {
/* 364 */       return x + 0.5D * x * x;
/*     */     }
/* 366 */     return Math.exp(x) - 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Epslon(double x) {
/* 375 */     double eps = 0.0D;
/*     */     
/* 377 */     double a = 1.3333333333333333D;
/*     */     
/* 379 */     while (eps == 0.0D) {
/* 380 */       double b = a - 1.0D;
/* 381 */       double c = b + b + b;
/* 382 */       eps = Math.abs(c - 1.0D);
/*     */     } 
/*     */     
/* 385 */     return eps * Math.abs(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Sign(double a, double b) {
/* 395 */     double x = (a >= 0.0D) ? a : -a;
/* 396 */     return (b >= 0.0D) ? x : -x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double LogDiff(double lna, double lnc) {
/* 406 */     if (lna > lnc) {
/* 407 */       return lna + Math.exp(1.0D - Math.exp(lnc - lna));
/*     */     }
/* 409 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double LogSum(double lna, double lnc) {
/* 419 */     if (lna == Double.NEGATIVE_INFINITY)
/* 420 */       return lnc; 
/* 421 */     if (lnc == Double.NEGATIVE_INFINITY) {
/* 422 */       return lna;
/*     */     }
/* 424 */     if (lna > lnc) {
/* 425 */       return lna + Log1p(Math.exp(lnc - lna));
/*     */     }
/* 427 */     return lnc + Log1p(Math.exp(lna - lnc));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double LogSum(float lna, float lnc) {
/* 437 */     if (lna == Float.NEGATIVE_INFINITY)
/* 438 */       return lnc; 
/* 439 */     if (lnc == Float.NEGATIVE_INFINITY) {
/* 440 */       return lna;
/*     */     }
/* 442 */     if (lna > lnc) {
/* 443 */       return lna + Log1p(Math.exp((lnc - lna)));
/*     */     }
/* 445 */     return lnc + Log1p(Math.exp((lna - lnc)));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Special.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */