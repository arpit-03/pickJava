/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DifferenceFilter
/*     */ {
/*     */   private static final float AP1 = -0.999F;
/*     */   private static final float A0P0 = 1.7954845F;
/*     */   private static final float A0P1 = -0.64490664F;
/*     */   private static final float A0P2 = -0.03850411F;
/*     */   private static final float A0P3 = -0.01793403F;
/*     */   private static final float A0P4 = -0.00708972F;
/*     */   private static final float APM0 = -0.5565992F;
/*     */   private static final float APM1 = -0.20031442F;
/*     */   private static final float APM2 = -0.08457147F;
/*     */   private static final float APM3 = -0.04141619F;
/*     */   private static final float APM4 = -0.02290331F;
/*     */   private static final float AIP0 = 0.5569527F;
/*     */   private static final float A00P0 = 2.3110454F;
/*     */   private static final float A00P1 = -0.4805547F;
/*     */   private static final float A00P2 = -0.0143204F;
/*     */   private static final float A0PM2 = -0.0291793F;
/*     */   private static final float A0PM1 = -0.1057476F;
/*     */   private static final float A0PP0 = -0.4572746F;
/*     */   private static final float A0PP1 = -0.0115732F;
/*     */   private static final float A0PP2 = -0.0047283F;
/*     */   private static final float APMM2 = -0.0149963F;
/*     */   private static final float APMM1 = -0.0408317F;
/*     */   private static final float APMP0 = -0.0945958F;
/*     */   private static final float APMP1 = -0.0223166F;
/*     */   private static final float APMP2 = -0.0062781F;
/*     */   private static final float AP0M2 = -0.0213786F;
/*     */   private static final float AP0M1 = -0.0898909F;
/*     */   private static final float AP0P0 = -0.4322719F;
/*     */   private static final float AI0P0 = 0.4327046F;
/*     */   
/*     */   public void apply(float[] x, float[] y) {
/*  56 */     int n = y.length;
/*  57 */     y[0] = x[0];
/*  58 */     for (int i = 1; i < n; i++) {
/*  59 */       y[i] = x[i] + -0.999F * x[i - 1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[][] x, float[][] y) {
/*  70 */     int n1 = (x[0]).length;
/*  71 */     int n2 = x.length;
/*  72 */     float xm3 = 0.0F, xm2 = xm3, xm1 = xm2, xm0 = xm1;
/*  73 */     for (int i1 = 0; i1 < n1; i1++) {
/*  74 */       float xm4 = xm3; xm3 = xm2; xm2 = xm1; xm1 = xm0; xm0 = x[0][i1];
/*  75 */       y[0][i1] = 1.7954845F * xm0 + -0.64490664F * xm1 + -0.03850411F * xm2 + -0.01793403F * xm3 + -0.00708972F * xm4;
/*     */     } 
/*  77 */     for (int i2 = 1; i2 < n2; i2++) {
/*  78 */       xm0 = xm1 = xm2 = xm3 = 0.0F;
/*  79 */       float xp4 = 0.0F, xp3 = xp4, xp2 = xp3, xp1 = xp2;
/*  80 */       if (n1 >= 4)
/*  81 */         xp4 = x[i2 - 1][3]; 
/*  82 */       if (n1 >= 3)
/*  83 */         xp3 = x[i2 - 1][2]; 
/*  84 */       if (n1 >= 2)
/*  85 */         xp2 = x[i2 - 1][1]; 
/*  86 */       if (n1 >= 1)
/*  87 */         xp1 = x[i2 - 1][0]; 
/*  88 */       for (int i = 0; i < n1 - 4; i++) {
/*  89 */         float xm4 = xm3; xm3 = xm2; xm2 = xm1; xm1 = xm0; xm0 = x[i2][i];
/*  90 */         float xp0 = xp1; xp1 = xp2; xp2 = xp3; xp3 = xp4; xp4 = x[i2 - 1][i + 4];
/*  91 */         y[i2][i] = 1.7954845F * xm0 + -0.64490664F * xm1 + -0.03850411F * xm2 + -0.01793403F * xm3 + -0.00708972F * xm4 + -0.5565992F * xp0 + -0.20031442F * xp1 + -0.08457147F * xp2 + -0.04141619F * xp3 + -0.02290331F * xp4;
/*     */       } 
/*     */       
/*  94 */       if (n1 >= 4) {
/*  95 */         float xm4 = xm3; xm3 = xm2; xm2 = xm1; xm1 = xm0; xm0 = x[i2][n1 - 4];
/*  96 */         float xp0 = xp1; xp1 = xp2; xp2 = xp3; xp3 = xp4;
/*  97 */         y[i2][n1 - 4] = 1.7954845F * xm0 + -0.64490664F * xm1 + -0.03850411F * xm2 + -0.01793403F * xm3 + -0.00708972F * xm4 + -0.5565992F * xp0 + -0.20031442F * xp1 + -0.08457147F * xp2 + -0.04141619F * xp3;
/*     */       } 
/*     */       
/* 100 */       if (n1 >= 3) {
/* 101 */         float xm4 = xm3; xm3 = xm2; xm2 = xm1; xm1 = xm0; xm0 = x[i2][n1 - 3];
/* 102 */         float xp0 = xp1; xp1 = xp2; xp2 = xp3;
/* 103 */         y[i2][n1 - 3] = 1.7954845F * xm0 + -0.64490664F * xm1 + -0.03850411F * xm2 + -0.01793403F * xm3 + -0.00708972F * xm4 + -0.5565992F * xp0 + -0.20031442F * xp1 + -0.08457147F * xp2;
/*     */       } 
/*     */       
/* 106 */       if (n1 >= 2) {
/* 107 */         float xm4 = xm3; xm3 = xm2; xm2 = xm1; xm1 = xm0; xm0 = x[i2][n1 - 2];
/* 108 */         float xp0 = xp1; xp1 = xp2;
/* 109 */         y[i2][n1 - 2] = 1.7954845F * xm0 + -0.64490664F * xm1 + -0.03850411F * xm2 + -0.01793403F * xm3 + -0.00708972F * xm4 + -0.5565992F * xp0 + -0.20031442F * xp1;
/*     */       } 
/*     */       
/* 112 */       if (n1 >= 1) {
/* 113 */         float xm4 = xm3; xm3 = xm2; xm2 = xm1; xm1 = xm0; xm0 = x[i2][n1 - 1];
/* 114 */         float xp0 = xp1;
/* 115 */         y[i2][n1 - 1] = 1.7954845F * xm0 + -0.64490664F * xm1 + -0.03850411F * xm2 + -0.01793403F * xm3 + -0.00708972F * xm4 + -0.5565992F * xp0;
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
/*     */   public void apply(float[][][] x, float[][][] y) {
/* 127 */     int n1 = (x[0][0]).length;
/* 128 */     int n2 = (x[0]).length;
/* 129 */     int n3 = x.length;
/* 130 */     int n2m1 = n2 - 1;
/* 131 */     for (int i3 = 0; i3 < n3; i3++) {
/* 132 */       for (int i2 = 0; i2 < n2; i2++) {
/* 133 */         float x0mm2 = 0.0F, x0mm1 = 0.0F, x0mm0 = 0.0F, x0mp1 = 0.0F, x0mp2 = 0.0F;
/* 134 */         float x00m1 = 0.0F, x00m0 = 0.0F;
/* 135 */         float xm0m0 = 0.0F, xm0p1 = 0.0F, xm0p2 = 0.0F;
/* 136 */         float xmpm2 = 0.0F, xmpm1 = 0.0F, xmpm0 = 0.0F, xmpp1 = 0.0F, xmpp2 = 0.0F;
/* 137 */         if (n1 > 0) {
/* 138 */           if (i2 > 0)
/* 139 */             x0mp1 = x[i3][i2 - 1][0]; 
/* 140 */           if (i3 > 0) {
/* 141 */             xm0p1 = x[i3 - 1][i2][0];
/* 142 */             if (i2 < n2m1)
/* 143 */               xmpp1 = x[i3 - 1][i2 + 1][0]; 
/*     */           } 
/*     */         } 
/* 146 */         if (n1 > 1) {
/* 147 */           if (i2 > 0)
/* 148 */             x0mp2 = x[i3][i2 - 1][1]; 
/* 149 */           if (i3 > 0) {
/* 150 */             xm0p2 = x[i3 - 1][i2][1];
/* 151 */             if (i2 < n2m1)
/* 152 */               xmpp2 = x[i3 - 1][i2 + 1][1]; 
/*     */           } 
/*     */         } 
/* 155 */         for (int i1 = 0; i1 < n1 - 2; i1++) {
/* 156 */           float x00m2 = x00m1;
/* 157 */           x00m1 = x00m0;
/* 158 */           x00m0 = x[i3][i2][i1];
/* 159 */           if (i2 > 0) {
/* 160 */             x0mm2 = x0mm1;
/* 161 */             x0mm1 = x0mm0;
/* 162 */             x0mm0 = x0mp1;
/* 163 */             x0mp1 = x0mp2;
/* 164 */             x0mp2 = x[i3][i2 - 1][i1 + 2];
/*     */           } 
/* 166 */           if (i3 > 0) {
/* 167 */             if (i2 < n2m1) {
/* 168 */               xmpm2 = xmpm1;
/* 169 */               xmpm1 = xmpm0;
/* 170 */               xmpm0 = xmpp1;
/* 171 */               xmpp1 = xmpp2;
/* 172 */               xmpp2 = x[i3 - 1][i2 + 1][i1 + 2];
/*     */             } 
/* 174 */             xm0m0 = xm0p1;
/* 175 */             xm0p1 = xm0p2;
/* 176 */             xm0p2 = x[i3 - 1][i2][i1 + 2];
/*     */           } 
/* 178 */           y[i3][i2][i1] = 2.3110454F * x00m0 + -0.4805547F * x00m1 + -0.0143204F * x00m2 + -0.0291793F * x0mp2 + -0.1057476F * x0mp1 + -0.4572746F * x0mm0 + -0.0115732F * x0mm1 + -0.0047283F * x0mm2 + -0.0149963F * xmpp2 + -0.0408317F * xmpp1 + -0.0945958F * xmpm0 + -0.0223166F * xmpm1 + -0.0062781F * xmpm2 + -0.0213786F * xm0p2 + -0.0898909F * xm0p1 + -0.4322719F * xm0m0;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 183 */         if (n1 > 1) {
/* 184 */           float x00m2 = x00m1;
/* 185 */           x00m1 = x00m0;
/* 186 */           x00m0 = x[i3][i2][n1 - 2];
/* 187 */           x0mm2 = x0mm1;
/* 188 */           x0mm1 = x0mm0;
/* 189 */           x0mm0 = x0mp1;
/* 190 */           x0mp1 = x0mp2;
/* 191 */           xmpm2 = xmpm1;
/* 192 */           xmpm1 = xmpm0;
/* 193 */           xmpm0 = xmpp1;
/* 194 */           xmpp1 = xmpp2;
/* 195 */           xm0m0 = xm0p1;
/* 196 */           xm0p1 = xm0p2;
/* 197 */           y[i3][i2][n1 - 2] = 2.3110454F * x00m0 + -0.4805547F * x00m1 + -0.0143204F * x00m2 + -0.1057476F * x0mp1 + -0.4572746F * x0mm0 + -0.0115732F * x0mm1 + -0.0047283F * x0mm2 + -0.0408317F * xmpp1 + -0.0945958F * xmpm0 + -0.0223166F * xmpm1 + -0.0062781F * xmpm2 + -0.0898909F * xm0p1 + -0.4322719F * xm0m0;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 202 */         if (n1 > 0) {
/* 203 */           float x00m2 = x00m1;
/* 204 */           x00m1 = x00m0;
/* 205 */           x00m0 = x[i3][i2][n1 - 1];
/* 206 */           x0mm2 = x0mm1;
/* 207 */           x0mm1 = x0mm0;
/* 208 */           x0mm0 = x0mp1;
/* 209 */           xmpm2 = xmpm1;
/* 210 */           xmpm1 = xmpm0;
/* 211 */           xmpm0 = xmpp1;
/* 212 */           xm0m0 = xm0p1;
/* 213 */           y[i3][i2][n1 - 1] = 2.3110454F * x00m0 + -0.4805547F * x00m1 + -0.0143204F * x00m2 + -0.4572746F * x0mm0 + -0.0115732F * x0mm1 + -0.0047283F * x0mm2 + -0.0945958F * xmpm0 + -0.0223166F * xmpm1 + -0.0062781F * xmpm2 + -0.4322719F * xm0m0;
/*     */         } 
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
/*     */ 
/*     */   
/*     */   public void applyTranspose(float[] x, float[] y) {
/* 228 */     int n = y.length;
/* 229 */     y[n - 1] = x[n - 1];
/* 230 */     for (int i = n - 2; i >= 0; i--) {
/* 231 */       y[i] = x[i] + -0.999F * x[i + 1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyTranspose(float[][] x, float[][] y) {
/* 242 */     int n1 = (x[0]).length;
/* 243 */     int n2 = x.length;
/* 244 */     float xp3 = 0.0F, xp2 = xp3, xp1 = xp2, xp0 = xp1;
/* 245 */     for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 246 */       float xp4 = xp3; xp3 = xp2; xp2 = xp1; xp1 = xp0; xp0 = x[n2 - 1][i1];
/* 247 */       y[n2 - 1][i1] = 1.7954845F * xp0 + -0.64490664F * xp1 + -0.03850411F * xp2 + -0.01793403F * xp3 + -0.00708972F * xp4;
/*     */     } 
/* 249 */     for (int i2 = n2 - 2; i2 >= 0; i2--) {
/* 250 */       float xm4 = 0.0F, xm3 = xm4, xm2 = xm3, xm1 = xm2;
/* 251 */       xp0 = xp1 = xp2 = xp3 = 0.0F;
/* 252 */       if (n1 >= 4)
/* 253 */         xm4 = x[i2 + 1][n1 - 4]; 
/* 254 */       if (n1 >= 3)
/* 255 */         xm3 = x[i2 + 1][n1 - 3]; 
/* 256 */       if (n1 >= 2)
/* 257 */         xm2 = x[i2 + 1][n1 - 2]; 
/* 258 */       if (n1 >= 1)
/* 259 */         xm1 = x[i2 + 1][n1 - 1]; 
/* 260 */       for (int i = n1 - 1; i >= 4; i--) {
/* 261 */         float xp4 = xp3; xp3 = xp2; xp2 = xp1; xp1 = xp0; xp0 = x[i2][i];
/* 262 */         float xm0 = xm1; xm1 = xm2; xm2 = xm3; xm3 = xm4; xm4 = x[i2 + 1][i - 4];
/* 263 */         y[i2][i] = 1.7954845F * xp0 + -0.64490664F * xp1 + -0.03850411F * xp2 + -0.01793403F * xp3 + -0.00708972F * xp4 + -0.5565992F * xm0 + -0.20031442F * xm1 + -0.08457147F * xm2 + -0.04141619F * xm3 + -0.02290331F * xm4;
/*     */       } 
/*     */       
/* 266 */       if (n1 > 3) {
/* 267 */         float xp4 = xp3; xp3 = xp2; xp2 = xp1; xp1 = xp0; xp0 = x[i2][3];
/* 268 */         float xm0 = xm1; xm1 = xm2; xm2 = xm3; xm3 = xm4;
/* 269 */         y[i2][3] = 1.7954845F * xp0 + -0.64490664F * xp1 + -0.03850411F * xp2 + -0.01793403F * xp3 + -0.00708972F * xp4 + -0.5565992F * xm0 + -0.20031442F * xm1 + -0.08457147F * xm2 + -0.04141619F * xm3;
/*     */       } 
/*     */       
/* 272 */       if (n1 > 2) {
/* 273 */         float xp4 = xp3; xp3 = xp2; xp2 = xp1; xp1 = xp0; xp0 = x[i2][2];
/* 274 */         float xm0 = xm1; xm1 = xm2; xm2 = xm3;
/* 275 */         y[i2][2] = 1.7954845F * xp0 + -0.64490664F * xp1 + -0.03850411F * xp2 + -0.01793403F * xp3 + -0.00708972F * xp4 + -0.5565992F * xm0 + -0.20031442F * xm1 + -0.08457147F * xm2;
/*     */       } 
/*     */       
/* 278 */       if (n1 > 1) {
/* 279 */         float xp4 = xp3; xp3 = xp2; xp2 = xp1; xp1 = xp0; xp0 = x[i2][1];
/* 280 */         float xm0 = xm1; xm1 = xm2;
/* 281 */         y[i2][1] = 1.7954845F * xp0 + -0.64490664F * xp1 + -0.03850411F * xp2 + -0.01793403F * xp3 + -0.00708972F * xp4 + -0.5565992F * xm0 + -0.20031442F * xm1;
/*     */       } 
/*     */       
/* 284 */       if (n1 > 0) {
/* 285 */         float xp4 = xp3; xp3 = xp2; xp2 = xp1; xp1 = xp0; xp0 = x[i2][0];
/* 286 */         float xm0 = xm1;
/* 287 */         y[i2][0] = 1.7954845F * xp0 + -0.64490664F * xp1 + -0.03850411F * xp2 + -0.01793403F * xp3 + -0.00708972F * xp4 + -0.5565992F * xm0;
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
/*     */   public void applyTranspose(float[][][] x, float[][][] y) {
/* 299 */     int n1 = (x[0][0]).length;
/* 300 */     int n2 = (x[0]).length;
/* 301 */     int n3 = x.length;
/* 302 */     int n1m1 = n1 - 1;
/* 303 */     int n2m1 = n2 - 1;
/* 304 */     int n3m1 = n3 - 1;
/* 305 */     for (int i3 = n3m1; i3 >= 0; i3--) {
/* 306 */       for (int i2 = n2m1; i2 >= 0; i2--) {
/* 307 */         float x0mm2 = 0.0F, x0mm1 = 0.0F, x0mm0 = 0.0F, x0mp1 = 0.0F, x0mp2 = 0.0F;
/* 308 */         float x00m1 = 0.0F, x00m0 = 0.0F;
/* 309 */         float xm0m0 = 0.0F, xm0p1 = 0.0F, xm0p2 = 0.0F;
/* 310 */         float xmpm2 = 0.0F, xmpm1 = 0.0F, xmpm0 = 0.0F, xmpp1 = 0.0F, xmpp2 = 0.0F;
/* 311 */         if (n1 > 0) {
/* 312 */           if (i2 < n2m1)
/* 313 */             x0mp1 = x[i3][i2 + 1][n1 - 1]; 
/* 314 */           if (i3 < n3m1) {
/* 315 */             xm0p1 = x[i3 + 1][i2][n1 - 1];
/* 316 */             if (i2 > 0)
/* 317 */               xmpp1 = x[i3 + 1][i2 - 1][n1 - 1]; 
/*     */           } 
/*     */         } 
/* 320 */         if (n1 > 1) {
/* 321 */           if (i2 < n2m1)
/* 322 */             x0mp2 = x[i3][i2 + 1][n1 - 2]; 
/* 323 */           if (i3 < n3m1) {
/* 324 */             xm0p2 = x[i3 + 1][i2][n1 - 2];
/* 325 */             if (i2 > 0)
/* 326 */               xmpp2 = x[i3 + 1][i2 - 1][n1 - 2]; 
/*     */           } 
/*     */         } 
/* 329 */         for (int i1 = n1m1; i1 >= 2; i1--) {
/* 330 */           float x00m2 = x00m1;
/* 331 */           x00m1 = x00m0;
/* 332 */           x00m0 = x[i3][i2][i1];
/* 333 */           if (i2 < n2m1) {
/* 334 */             x0mm2 = x0mm1;
/* 335 */             x0mm1 = x0mm0;
/* 336 */             x0mm0 = x0mp1;
/* 337 */             x0mp1 = x0mp2;
/* 338 */             x0mp2 = x[i3][i2 + 1][i1 - 2];
/*     */           } 
/* 340 */           if (i3 < n3m1) {
/* 341 */             if (i2 > 0) {
/* 342 */               xmpm2 = xmpm1;
/* 343 */               xmpm1 = xmpm0;
/* 344 */               xmpm0 = xmpp1;
/* 345 */               xmpp1 = xmpp2;
/* 346 */               xmpp2 = x[i3 + 1][i2 - 1][i1 - 2];
/*     */             } 
/* 348 */             xm0m0 = xm0p1;
/* 349 */             xm0p1 = xm0p2;
/* 350 */             xm0p2 = x[i3 + 1][i2][i1 - 2];
/*     */           } 
/* 352 */           y[i3][i2][i1] = 2.3110454F * x00m0 + -0.4805547F * x00m1 + -0.0143204F * x00m2 + -0.0291793F * x0mp2 + -0.1057476F * x0mp1 + -0.4572746F * x0mm0 + -0.0115732F * x0mm1 + -0.0047283F * x0mm2 + -0.0149963F * xmpp2 + -0.0408317F * xmpp1 + -0.0945958F * xmpm0 + -0.0223166F * xmpm1 + -0.0062781F * xmpm2 + -0.0213786F * xm0p2 + -0.0898909F * xm0p1 + -0.4322719F * xm0m0;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 357 */         if (n1 > 1) {
/* 358 */           float x00m2 = x00m1;
/* 359 */           x00m1 = x00m0;
/* 360 */           x00m0 = x[i3][i2][1];
/* 361 */           x0mm2 = x0mm1;
/* 362 */           x0mm1 = x0mm0;
/* 363 */           x0mm0 = x0mp1;
/* 364 */           x0mp1 = x0mp2;
/* 365 */           xmpm2 = xmpm1;
/* 366 */           xmpm1 = xmpm0;
/* 367 */           xmpm0 = xmpp1;
/* 368 */           xmpp1 = xmpp2;
/* 369 */           xm0m0 = xm0p1;
/* 370 */           xm0p1 = xm0p2;
/* 371 */           y[i3][i2][1] = 2.3110454F * x00m0 + -0.4805547F * x00m1 + -0.0143204F * x00m2 + -0.1057476F * x0mp1 + -0.4572746F * x0mm0 + -0.0115732F * x0mm1 + -0.0047283F * x0mm2 + -0.0408317F * xmpp1 + -0.0945958F * xmpm0 + -0.0223166F * xmpm1 + -0.0062781F * xmpm2 + -0.0898909F * xm0p1 + -0.4322719F * xm0m0;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 376 */         if (n1 > 0) {
/* 377 */           float x00m2 = x00m1;
/* 378 */           x00m1 = x00m0;
/* 379 */           x00m0 = x[i3][i2][0];
/* 380 */           x0mm2 = x0mm1;
/* 381 */           x0mm1 = x0mm0;
/* 382 */           x0mm0 = x0mp1;
/* 383 */           xmpm2 = xmpm1;
/* 384 */           xmpm1 = xmpm0;
/* 385 */           xmpm0 = xmpp1;
/* 386 */           xm0m0 = xm0p1;
/* 387 */           y[i3][i2][0] = 2.3110454F * x00m0 + -0.4805547F * x00m1 + -0.0143204F * x00m2 + -0.4572746F * x0mm0 + -0.0115732F * x0mm1 + -0.0047283F * x0mm2 + -0.0945958F * xmpm0 + -0.0223166F * xmpm1 + -0.0062781F * xmpm2 + -0.4322719F * xm0m0;
/*     */         } 
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
/*     */ 
/*     */   
/*     */   public void applyInverse(float[] x, float[] y) {
/* 402 */     int n = y.length;
/* 403 */     y[0] = x[0];
/* 404 */     for (int i = 1; i < n; i++) {
/* 405 */       y[i] = x[i] - -0.999F * y[i - 1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse(float[][] x, float[][] y) {
/* 417 */     int n1 = (x[0]).length;
/* 418 */     int n2 = x.length;
/* 419 */     float ym3 = 0.0F, ym2 = ym3, ym1 = ym2, ym0 = ym1;
/* 420 */     for (int i1 = 0; i1 < n1; i1++) {
/* 421 */       float ym4 = ym3; ym3 = ym2; ym2 = ym1; ym1 = ym0; float xm0 = x[0][i1];
/* 422 */       y[0][i1] = ym0 = 0.5569527F * (xm0 - -0.64490664F * ym1 - -0.03850411F * ym2 - -0.01793403F * ym3 - -0.00708972F * ym4);
/*     */     } 
/* 424 */     for (int i2 = 1; i2 < n2; i2++) {
/* 425 */       ym0 = ym1 = ym2 = ym3 = 0.0F;
/* 426 */       float yp4 = 0.0F, yp3 = yp4, yp2 = yp3, yp1 = yp2;
/* 427 */       if (n1 >= 4)
/* 428 */         yp4 = y[i2 - 1][3]; 
/* 429 */       if (n1 >= 3)
/* 430 */         yp3 = y[i2 - 1][2]; 
/* 431 */       if (n1 >= 2)
/* 432 */         yp2 = y[i2 - 1][1]; 
/* 433 */       if (n1 >= 1)
/* 434 */         yp1 = y[i2 - 1][0]; 
/* 435 */       for (int i = 0; i < n1 - 4; i++) {
/* 436 */         float ym4 = ym3; ym3 = ym2; ym2 = ym1; ym1 = ym0; float xm0 = x[i2][i];
/* 437 */         float yp0 = yp1; yp1 = yp2; yp2 = yp3; yp3 = yp4; yp4 = y[i2 - 1][i + 4];
/* 438 */         y[i2][i] = ym0 = 0.5569527F * (xm0 - -0.64490664F * ym1 - -0.03850411F * ym2 - -0.01793403F * ym3 - -0.00708972F * ym4 - -0.5565992F * yp0 - -0.20031442F * yp1 - -0.08457147F * yp2 - -0.04141619F * yp3 - -0.02290331F * yp4);
/*     */       } 
/*     */       
/* 441 */       if (n1 >= 4) {
/* 442 */         float ym4 = ym3; ym3 = ym2; ym2 = ym1; ym1 = ym0; float xm0 = x[i2][n1 - 4];
/* 443 */         float yp0 = yp1; yp1 = yp2; yp2 = yp3; yp3 = yp4;
/* 444 */         y[i2][n1 - 4] = ym0 = 0.5569527F * (xm0 - -0.64490664F * ym1 - -0.03850411F * ym2 - -0.01793403F * ym3 - -0.00708972F * ym4 - -0.5565992F * yp0 - -0.20031442F * yp1 - -0.08457147F * yp2 - -0.04141619F * yp3);
/*     */       } 
/*     */       
/* 447 */       if (n1 >= 3) {
/* 448 */         float ym4 = ym3; ym3 = ym2; ym2 = ym1; ym1 = ym0; float xm0 = x[i2][n1 - 3];
/* 449 */         float yp0 = yp1; yp1 = yp2; yp2 = yp3;
/* 450 */         y[i2][n1 - 3] = ym0 = 0.5569527F * (xm0 - -0.64490664F * ym1 - -0.03850411F * ym2 - -0.01793403F * ym3 - -0.00708972F * ym4 - -0.5565992F * yp0 - -0.20031442F * yp1 - -0.08457147F * yp2);
/*     */       } 
/*     */       
/* 453 */       if (n1 >= 2) {
/* 454 */         float ym4 = ym3; ym3 = ym2; ym2 = ym1; ym1 = ym0; float xm0 = x[i2][n1 - 2];
/* 455 */         float yp0 = yp1; yp1 = yp2;
/* 456 */         y[i2][n1 - 2] = ym0 = 0.5569527F * (xm0 - -0.64490664F * ym1 - -0.03850411F * ym2 - -0.01793403F * ym3 - -0.00708972F * ym4 - -0.5565992F * yp0 - -0.20031442F * yp1);
/*     */       } 
/*     */       
/* 459 */       if (n1 >= 1) {
/* 460 */         float ym4 = ym3; ym3 = ym2; ym2 = ym1; ym1 = ym0; float xm0 = x[i2][n1 - 1];
/* 461 */         float yp0 = yp1;
/* 462 */         y[i2][n1 - 1] = 0.5569527F * (xm0 - -0.64490664F * ym1 - -0.03850411F * ym2 - -0.01793403F * ym3 - -0.00708972F * ym4 - -0.5565992F * yp0);
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
/*     */   public void applyInverse(float[][][] x, float[][][] y) {
/* 474 */     int n1 = (x[0][0]).length;
/* 475 */     int n2 = (x[0]).length;
/* 476 */     int n3 = x.length;
/* 477 */     int n2m1 = n2 - 1;
/* 478 */     for (int i3 = 0; i3 < n3; i3++) {
/* 479 */       for (int i2 = 0; i2 < n2; i2++) {
/*     */         
/* 481 */         float y0mm2 = 0.0F, y0mm1 = 0.0F, y0mm0 = 0.0F, y0mp1 = 0.0F, y0mp2 = 0.0F;
/* 482 */         float y00m1 = 0.0F, y00m0 = 0.0F;
/* 483 */         float ym0m0 = 0.0F, ym0p1 = 0.0F, ym0p2 = 0.0F;
/* 484 */         float ympm2 = 0.0F, ympm1 = 0.0F, ympm0 = 0.0F, ympp1 = 0.0F, ympp2 = 0.0F;
/* 485 */         if (n1 > 0) {
/* 486 */           if (i2 > 0)
/* 487 */             y0mp1 = y[i3][i2 - 1][0]; 
/* 488 */           if (i3 > 0) {
/* 489 */             ym0p1 = y[i3 - 1][i2][0];
/* 490 */             if (i2 < n2m1)
/* 491 */               ympp1 = y[i3 - 1][i2 + 1][0]; 
/*     */           } 
/*     */         } 
/* 494 */         if (n1 > 1) {
/* 495 */           if (i2 > 0)
/* 496 */             y0mp2 = y[i3][i2 - 1][1]; 
/* 497 */           if (i3 > 0) {
/* 498 */             ym0p2 = y[i3 - 1][i2][1];
/* 499 */             if (i2 < n2m1)
/* 500 */               ympp2 = y[i3 - 1][i2 + 1][1]; 
/*     */           } 
/*     */         } 
/* 503 */         for (int i1 = 0; i1 < n1 - 2; i1++) {
/* 504 */           float x00m0 = x[i3][i2][i1];
/* 505 */           float y00m2 = y00m1;
/* 506 */           y00m1 = y00m0;
/* 507 */           if (i2 > 0) {
/* 508 */             y0mm2 = y0mm1;
/* 509 */             y0mm1 = y0mm0;
/* 510 */             y0mm0 = y0mp1;
/* 511 */             y0mp1 = y0mp2;
/* 512 */             y0mp2 = y[i3][i2 - 1][i1 + 2];
/*     */           } 
/* 514 */           if (i3 > 0) {
/* 515 */             if (i2 < n2m1) {
/* 516 */               ympm2 = ympm1;
/* 517 */               ympm1 = ympm0;
/* 518 */               ympm0 = ympp1;
/* 519 */               ympp1 = ympp2;
/* 520 */               ympp2 = y[i3 - 1][i2 + 1][i1 + 2];
/*     */             } 
/* 522 */             ym0m0 = ym0p1;
/* 523 */             ym0p1 = ym0p2;
/* 524 */             ym0p2 = y[i3 - 1][i2][i1 + 2];
/*     */           } 
/* 526 */           y[i3][i2][i1] = y00m0 = 0.4327046F * (x00m0 - -0.4805547F * y00m1 - -0.0143204F * y00m2 - -0.0291793F * y0mp2 - -0.1057476F * y0mp1 - -0.4572746F * y0mm0 - -0.0115732F * y0mm1 - -0.0047283F * y0mm2 - -0.0149963F * ympp2 - -0.0408317F * ympp1 - -0.0945958F * ympm0 - -0.0223166F * ympm1 - -0.0062781F * ympm2 - -0.0213786F * ym0p2 - -0.0898909F * ym0p1 - -0.4322719F * ym0m0);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 531 */         if (n1 > 1) {
/* 532 */           float x00m0 = x[i3][i2][n1 - 2];
/* 533 */           float y00m2 = y00m1;
/* 534 */           y00m1 = y00m0;
/* 535 */           y0mm2 = y0mm1;
/* 536 */           y0mm1 = y0mm0;
/* 537 */           y0mm0 = y0mp1;
/* 538 */           y0mp1 = y0mp2;
/* 539 */           ympm2 = ympm1;
/* 540 */           ympm1 = ympm0;
/* 541 */           ympm0 = ympp1;
/* 542 */           ympp1 = ympp2;
/* 543 */           ym0m0 = ym0p1;
/* 544 */           ym0p1 = ym0p2;
/* 545 */           y[i3][i2][n1 - 2] = y00m0 = 0.4327046F * (x00m0 - -0.4805547F * y00m1 - -0.0143204F * y00m2 - -0.1057476F * y0mp1 - -0.4572746F * y0mm0 - -0.0115732F * y0mm1 - -0.0047283F * y0mm2 - -0.0408317F * ympp1 - -0.0945958F * ympm0 - -0.0223166F * ympm1 - -0.0062781F * ympm2 - -0.0898909F * ym0p1 - -0.4322719F * ym0m0);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 550 */         if (n1 > 0) {
/* 551 */           float x00m0 = x[i3][i2][n1 - 1];
/* 552 */           float y00m2 = y00m1;
/* 553 */           y00m1 = y00m0;
/* 554 */           y0mm2 = y0mm1;
/* 555 */           y0mm1 = y0mm0;
/* 556 */           y0mm0 = y0mp1;
/* 557 */           ympm2 = ympm1;
/* 558 */           ympm1 = ympm0;
/* 559 */           ympm0 = ympp1;
/* 560 */           ym0m0 = ym0p1;
/* 561 */           y[i3][i2][n1 - 1] = 0.4327046F * (x00m0 - -0.4805547F * y00m1 - -0.0143204F * y00m2 - -0.4572746F * y0mm0 - -0.0115732F * y0mm1 - -0.0047283F * y0mm2 - -0.0945958F * ympm0 - -0.0223166F * ympm1 - -0.0062781F * ympm2 - -0.4322719F * ym0m0);
/*     */         } 
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
/*     */ 
/*     */   
/*     */   public void applyInverseTranspose(float[] x, float[] y) {
/* 576 */     int n = y.length;
/* 577 */     y[n - 1] = x[n - 1];
/* 578 */     for (int i = n - 2; i >= 0; i--) {
/* 579 */       y[i] = x[i] - -0.999F * y[i + 1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverseTranspose(float[][] x, float[][] y) {
/* 591 */     int n1 = (x[0]).length;
/* 592 */     int n2 = x.length;
/* 593 */     float yp3 = 0.0F, yp2 = yp3, yp1 = yp2, yp0 = yp1;
/* 594 */     for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 595 */       float yp4 = yp3; yp3 = yp2; yp2 = yp1; yp1 = yp0; float xp0 = x[n2 - 1][i1];
/* 596 */       y[n2 - 1][i1] = yp0 = 0.5569527F * (xp0 - -0.64490664F * yp1 - -0.03850411F * yp2 - -0.01793403F * yp3 - -0.00708972F * yp4);
/*     */     } 
/* 598 */     for (int i2 = n2 - 2; i2 >= 0; i2--) {
/* 599 */       float ym4 = 0.0F, ym3 = ym4, ym2 = ym3, ym1 = ym2;
/* 600 */       yp0 = yp1 = yp2 = yp3 = 0.0F;
/* 601 */       if (n1 >= 4)
/* 602 */         ym4 = y[i2 + 1][n1 - 4]; 
/* 603 */       if (n1 >= 3)
/* 604 */         ym3 = y[i2 + 1][n1 - 3]; 
/* 605 */       if (n1 >= 2)
/* 606 */         ym2 = y[i2 + 1][n1 - 2]; 
/* 607 */       if (n1 >= 1)
/* 608 */         ym1 = y[i2 + 1][n1 - 1]; 
/* 609 */       for (int i = n1 - 1; i >= 4; i--) {
/* 610 */         float yp4 = yp3; yp3 = yp2; yp2 = yp1; yp1 = yp0; float xp0 = x[i2][i];
/* 611 */         float ym0 = ym1; ym1 = ym2; ym2 = ym3; ym3 = ym4; ym4 = y[i2 + 1][i - 4];
/* 612 */         y[i2][i] = yp0 = 0.5569527F * (xp0 - -0.64490664F * yp1 - -0.03850411F * yp2 - -0.01793403F * yp3 - -0.00708972F * yp4 - -0.5565992F * ym0 - -0.20031442F * ym1 - -0.08457147F * ym2 - -0.04141619F * ym3 - -0.02290331F * ym4);
/*     */       } 
/*     */       
/* 615 */       if (n1 > 3) {
/* 616 */         float yp4 = yp3; yp3 = yp2; yp2 = yp1; yp1 = yp0; float xp0 = x[i2][3];
/* 617 */         float ym0 = ym1; ym1 = ym2; ym2 = ym3; ym3 = ym4;
/* 618 */         y[i2][3] = yp0 = 0.5569527F * (xp0 - -0.64490664F * yp1 - -0.03850411F * yp2 - -0.01793403F * yp3 - -0.00708972F * yp4 - -0.5565992F * ym0 - -0.20031442F * ym1 - -0.08457147F * ym2 - -0.04141619F * ym3);
/*     */       } 
/*     */       
/* 621 */       if (n1 > 2) {
/* 622 */         float yp4 = yp3; yp3 = yp2; yp2 = yp1; yp1 = yp0; float xp0 = x[i2][2];
/* 623 */         float ym0 = ym1; ym1 = ym2; ym2 = ym3;
/* 624 */         y[i2][2] = yp0 = 0.5569527F * (xp0 - -0.64490664F * yp1 - -0.03850411F * yp2 - -0.01793403F * yp3 - -0.00708972F * yp4 - -0.5565992F * ym0 - -0.20031442F * ym1 - -0.08457147F * ym2);
/*     */       } 
/*     */       
/* 627 */       if (n1 > 1) {
/* 628 */         float yp4 = yp3; yp3 = yp2; yp2 = yp1; yp1 = yp0; float xp0 = x[i2][1];
/* 629 */         float ym0 = ym1; ym1 = ym2;
/* 630 */         y[i2][1] = yp0 = 0.5569527F * (xp0 - -0.64490664F * yp1 - -0.03850411F * yp2 - -0.01793403F * yp3 - -0.00708972F * yp4 - -0.5565992F * ym0 - -0.20031442F * ym1);
/*     */       } 
/*     */       
/* 633 */       if (n1 > 0) {
/* 634 */         float yp4 = yp3; yp3 = yp2; yp2 = yp1; yp1 = yp0; float xp0 = x[i2][0];
/* 635 */         float ym0 = ym1;
/* 636 */         y[i2][0] = 0.5569527F * (xp0 - -0.64490664F * yp1 - -0.03850411F * yp2 - -0.01793403F * yp3 - -0.00708972F * yp4 - -0.5565992F * ym0);
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
/*     */   public void applyInverseTranspose(float[][][] x, float[][][] y) {
/* 648 */     int n1 = (x[0][0]).length;
/* 649 */     int n2 = (x[0]).length;
/* 650 */     int n3 = x.length;
/* 651 */     int n1m1 = n1 - 1;
/* 652 */     int n2m1 = n2 - 1;
/* 653 */     int n3m1 = n3 - 1;
/* 654 */     for (int i3 = n3m1; i3 >= 0; i3--) {
/* 655 */       for (int i2 = n2m1; i2 >= 0; i2--) {
/*     */         
/* 657 */         float y0mm2 = 0.0F, y0mm1 = 0.0F, y0mm0 = 0.0F, y0mp1 = 0.0F, y0mp2 = 0.0F;
/* 658 */         float y00m1 = 0.0F, y00m0 = 0.0F;
/* 659 */         float ym0m0 = 0.0F, ym0p1 = 0.0F, ym0p2 = 0.0F;
/* 660 */         float ympm2 = 0.0F, ympm1 = 0.0F, ympm0 = 0.0F, ympp1 = 0.0F, ympp2 = 0.0F;
/* 661 */         if (n1 > 0) {
/* 662 */           if (i2 < n2m1)
/* 663 */             y0mp1 = y[i3][i2 + 1][n1 - 1]; 
/* 664 */           if (i3 < n3m1) {
/* 665 */             ym0p1 = y[i3 + 1][i2][n1 - 1];
/* 666 */             if (i2 > 0)
/* 667 */               ympp1 = y[i3 + 1][i2 - 1][n1 - 1]; 
/*     */           } 
/*     */         } 
/* 670 */         if (n1 > 1) {
/* 671 */           if (i2 < n2m1)
/* 672 */             y0mp2 = y[i3][i2 + 1][n1 - 2]; 
/* 673 */           if (i3 < n3m1) {
/* 674 */             ym0p2 = y[i3 + 1][i2][n1 - 2];
/* 675 */             if (i2 > 0)
/* 676 */               ympp2 = y[i3 + 1][i2 - 1][n1 - 2]; 
/*     */           } 
/*     */         } 
/* 679 */         for (int i1 = n1m1; i1 >= 2; i1--) {
/* 680 */           float x00m0 = x[i3][i2][i1];
/* 681 */           float y00m2 = y00m1;
/* 682 */           y00m1 = y00m0;
/* 683 */           if (i2 < n2m1) {
/* 684 */             y0mm2 = y0mm1;
/* 685 */             y0mm1 = y0mm0;
/* 686 */             y0mm0 = y0mp1;
/* 687 */             y0mp1 = y0mp2;
/* 688 */             y0mp2 = y[i3][i2 + 1][i1 - 2];
/*     */           } 
/* 690 */           if (i3 < n3m1) {
/* 691 */             if (i2 > 0) {
/* 692 */               ympm2 = ympm1;
/* 693 */               ympm1 = ympm0;
/* 694 */               ympm0 = ympp1;
/* 695 */               ympp1 = ympp2;
/* 696 */               ympp2 = y[i3 + 1][i2 - 1][i1 - 2];
/*     */             } 
/* 698 */             ym0m0 = ym0p1;
/* 699 */             ym0p1 = ym0p2;
/* 700 */             ym0p2 = y[i3 + 1][i2][i1 - 2];
/*     */           } 
/* 702 */           y[i3][i2][i1] = y00m0 = 0.4327046F * (x00m0 - -0.4805547F * y00m1 - -0.0143204F * y00m2 - -0.0291793F * y0mp2 - -0.1057476F * y0mp1 - -0.4572746F * y0mm0 - -0.0115732F * y0mm1 - -0.0047283F * y0mm2 - -0.0149963F * ympp2 - -0.0408317F * ympp1 - -0.0945958F * ympm0 - -0.0223166F * ympm1 - -0.0062781F * ympm2 - -0.0213786F * ym0p2 - -0.0898909F * ym0p1 - -0.4322719F * ym0m0);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 707 */         if (n1 > 1) {
/* 708 */           float x00m0 = x[i3][i2][1];
/* 709 */           float y00m2 = y00m1;
/* 710 */           y00m1 = y00m0;
/* 711 */           y0mm2 = y0mm1;
/* 712 */           y0mm1 = y0mm0;
/* 713 */           y0mm0 = y0mp1;
/* 714 */           y0mp1 = y0mp2;
/* 715 */           ympm2 = ympm1;
/* 716 */           ympm1 = ympm0;
/* 717 */           ympm0 = ympp1;
/* 718 */           ympp1 = ympp2;
/* 719 */           ym0m0 = ym0p1;
/* 720 */           ym0p1 = ym0p2;
/* 721 */           y[i3][i2][1] = y00m0 = 0.4327046F * (x00m0 - -0.4805547F * y00m1 - -0.0143204F * y00m2 - -0.1057476F * y0mp1 - -0.4572746F * y0mm0 - -0.0115732F * y0mm1 - -0.0047283F * y0mm2 - -0.0408317F * ympp1 - -0.0945958F * ympm0 - -0.0223166F * ympm1 - -0.0062781F * ympm2 - -0.0898909F * ym0p1 - -0.4322719F * ym0m0);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 726 */         if (n1 > 0) {
/* 727 */           float x00m0 = x[i3][i2][0];
/* 728 */           float y00m2 = y00m1;
/* 729 */           y00m1 = y00m0;
/* 730 */           y0mm2 = y0mm1;
/* 731 */           y0mm1 = y0mm0;
/* 732 */           y0mm0 = y0mp1;
/* 733 */           ympm2 = ympm1;
/* 734 */           ympm1 = ympm0;
/* 735 */           ympm0 = ympp1;
/* 736 */           ym0m0 = ym0p1;
/* 737 */           y[i3][i2][0] = 0.4327046F * (x00m0 - -0.4805547F * y00m1 - -0.0143204F * y00m2 - -0.4572746F * y0mm0 - -0.0115732F * y0mm1 - -0.0047283F * y0mm2 - -0.0945958F * ympm0 - -0.0223166F * ympm1 - -0.0062781F * ympm2 - -0.4322719F * ym0m0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/DifferenceFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */