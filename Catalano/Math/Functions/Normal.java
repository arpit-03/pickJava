/*     */ package Catalano.Math.Functions;
/*     */ 
/*     */ import Catalano.Math.Special;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Normal
/*     */ {
/*     */   public static double Function(double value) {
/*  47 */     return 0.5D * Special.Erfc(-value / 1.4142135623730951D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Inverse(double y0) {
/*     */     double x1;
/*  56 */     if (y0 <= 0.0D) {
/*     */       
/*  58 */       if (y0 == 0.0D) return Double.NEGATIVE_INFINITY; 
/*     */       try {
/*  60 */         throw new IllegalArgumentException("y0");
/*  61 */       } catch (Exception e) {
/*  62 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*  65 */     if (y0 >= 1.0D) {
/*     */       
/*  67 */       if (y0 == 1.0D) return Double.POSITIVE_INFINITY; 
/*     */       try {
/*  69 */         throw new IllegalArgumentException("y0");
/*  70 */       } catch (Exception e) {
/*  71 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  76 */     double s2pi = Math.sqrt(6.283185307179586D);
/*  77 */     int code = 1;
/*  78 */     double y = y0;
/*     */ 
/*     */     
/*  81 */     double[] P0 = {
/*     */         
/*  83 */         -59.96335010141079D, 
/*  84 */         98.00107541859997D, 
/*  85 */         -56.67628574690703D, 
/*  86 */         13.931260938727968D, 
/*  87 */         -1.2391658386738125D
/*     */       };
/*     */     
/*  90 */     double[] Q0 = {
/*     */         
/*  92 */         1.9544885833814176D, 
/*  93 */         4.676279128988815D, 
/*  94 */         86.36024213908905D, 
/*  95 */         -225.46268785411937D, 
/*  96 */         200.26021238006066D, 
/*  97 */         -82.03722561683334D, 
/*  98 */         15.90562251262117D, 
/*  99 */         -1.1833162112133D
/*     */       };
/*     */     
/* 102 */     double[] P1 = {
/*     */         
/* 104 */         4.0554489230596245D, 
/* 105 */         31.525109459989388D, 
/* 106 */         57.16281922464213D, 
/* 107 */         44.08050738932008D, 
/* 108 */         14.684956192885803D, 
/* 109 */         2.1866330685079025D, 
/* 110 */         -0.1402560791713545D, 
/* 111 */         -0.03504246268278482D, 
/* 112 */         -8.574567851546854E-4D
/*     */       };
/*     */     
/* 115 */     double[] Q1 = {
/*     */         
/* 117 */         15.779988325646675D, 
/* 118 */         45.39076351288792D, 
/* 119 */         41.3172038254672D, 
/* 120 */         15.04253856929075D, 
/* 121 */         2.504649462083094D, 
/* 122 */         -0.14218292285478779D, 
/* 123 */         -0.03808064076915783D, 
/* 124 */         -9.332594808954574E-4D
/*     */       };
/*     */     
/* 127 */     double[] P2 = {
/*     */         
/* 129 */         3.2377489177694603D, 
/* 130 */         6.915228890689842D, 
/* 131 */         3.9388102529247444D, 
/* 132 */         1.3330346081580755D, 
/* 133 */         0.20148538954917908D, 
/* 134 */         0.012371663481782003D, 
/* 135 */         3.0158155350823543E-4D, 
/* 136 */         2.6580697468673755E-6D, 
/* 137 */         6.239745391849833E-9D
/*     */       };
/*     */     
/* 140 */     double[] Q2 = {
/*     */         
/* 142 */         6.02427039364742D, 
/* 143 */         3.6798356385616087D, 
/* 144 */         1.3770209948908132D, 
/* 145 */         0.21623699359449663D, 
/* 146 */         0.013420400608854318D, 
/* 147 */         3.2801446468212774E-4D, 
/* 148 */         2.8924786474538068E-6D, 
/* 149 */         6.790194080099813E-9D
/*     */       };
/*     */     
/* 152 */     if (y > 0.8646647167633873D) {
/*     */       
/* 154 */       y = 1.0D - y;
/* 155 */       code = 0;
/*     */     } 
/*     */     
/* 158 */     if (y > 0.1353352832366127D) {
/*     */       
/* 160 */       y -= 0.5D;
/* 161 */       double y2 = y * y;
/* 162 */       double d1 = y + y * y2 * Special.Polevl(y2, P0, 4) / Special.P1evl(y2, Q0, 8);
/* 163 */       d1 *= s2pi;
/* 164 */       return d1;
/*     */     } 
/*     */     
/* 167 */     double x = Math.sqrt(-2.0D * Math.log(y));
/* 168 */     double x0 = x - Math.log(x) / x;
/* 169 */     double z = 1.0D / x;
/*     */ 
/*     */     
/* 172 */     if (x < 8.0D) {
/*     */       
/* 174 */       x1 = z * Special.Polevl(z, P1, 8) / Special.P1evl(z, Q1, 8);
/*     */     }
/*     */     else {
/*     */       
/* 178 */       x1 = z * Special.Polevl(z, P2, 8) / Special.P1evl(z, Q2, 8);
/*     */     } 
/*     */     
/* 181 */     x = x0 - x1;
/*     */     
/* 183 */     if (code != 0) {
/* 184 */       x = -x;
/*     */     }
/* 186 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double HighAccuracyFunction(double x) {
/* 195 */     if (x < -8.0D || x > 8.0D) {
/* 196 */       return 0.0D;
/*     */     }
/* 198 */     double sum = x;
/* 199 */     double term = 0.0D;
/*     */     
/* 201 */     double nextTerm = x;
/* 202 */     double pwr = x * x;
/* 203 */     double i = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     while (sum != term) {
/*     */       
/* 210 */       term = sum;
/*     */ 
/*     */       
/* 213 */       nextTerm *= pwr / (i += 2.0D);
/*     */       
/* 215 */       sum += nextTerm;
/*     */     } 
/*     */     
/* 218 */     return 0.5D + sum * Math.exp(-0.5D * pwr - 0.9189385332046728D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double HighAccuracyComplemented(double x) {
/* 227 */     double[] R = {
/*     */         
/* 229 */         1.2533141373155003D, 0.4213692292880545D, 0.23665238291356067D, 
/* 230 */         0.16237766089686745D, 0.1231319632579323D, 0.09902859647173193D, 
/* 231 */         0.08276628650136918D, 0.07106958053885211D, 0.0622586659950262D
/*     */       };
/*     */     
/* 234 */     int j = (int)(0.5D * (Math.abs(x) + 1.0D));
/*     */     
/* 236 */     double a = R[j];
/* 237 */     double z = (2 * j);
/* 238 */     double b = a * z - 1.0D;
/*     */     
/* 240 */     double h = Math.abs(x) - z;
/* 241 */     double q = h * h;
/* 242 */     double pwr = 1.0D;
/*     */     
/* 244 */     double sum = a + h * b;
/* 245 */     double term = a;
/*     */ 
/*     */     
/* 248 */     for (int i = 2; sum != term; i += 2) {
/*     */       
/* 250 */       term = sum;
/*     */       
/* 252 */       a = (a + z * b) / i;
/* 253 */       b = (b + z * a) / (i + 1);
/* 254 */       pwr *= q;
/*     */       
/* 256 */       sum = term + pwr * (a + h * b);
/*     */     } 
/*     */     
/* 259 */     sum *= Math.exp(-0.5D * x * x - 0.9189385332046728D);
/*     */     
/* 261 */     return (x >= 0.0D) ? sum : (1.0D - sum);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Normal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */