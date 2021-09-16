/*      */ package edu.mines.jtk.dsp;class Pfacc { private static final float P120 = 0.12053668F; private static final float P142 = 0.14231484F;
/*      */   private static final float P173 = 0.17364818F;
/*      */   private static final float P222 = 0.22252093F;
/*      */   private static final float P239 = 0.23931566F;
/*      */   private static final float P281 = 0.28173256F;
/*      */   private static final float P342 = 0.34202015F;
/*      */   private static final float P354 = 0.3546049F;
/*      */   private static final float P382 = 0.38268343F;
/*      */   private static final float P415 = 0.41541502F;
/*      */   private static final float P433 = 0.43388373F;
/*      */   private static final float P464 = 0.46472317F;
/*      */   private static final float P540 = 0.54064083F;
/*      */   private static final float P559 = 0.559017F;
/*      */   private static final float P568 = 0.56806475F;
/*      */   private static final float P587 = 0.58778524F;
/*      */   private static final float P623 = 0.6234898F;
/*      */   private static final float P642 = 0.64278764F;
/*      */   private static final float P654 = 0.65486073F;
/*      */   private static final float P663 = 0.66312265F;
/*      */   private static final float P707 = 0.70710677F;
/*      */   private static final float P748 = 0.7485107F;
/*      */   private static final float P755 = 0.7557496F;
/*      */   private static final float P766 = 0.76604444F;
/*      */   private static final float P781 = 0.7818315F;
/*      */   private static final float P822 = 0.82298386F;
/*      */   private static final float P841 = 0.8412535F;
/*      */   private static final float P866 = 0.8660254F;
/*      */   private static final float P885 = 0.885456F;
/*      */   private static final float P900 = 0.90096885F;
/*      */   private static final float P909 = 0.90963197F;
/*      */   private static final float P923 = 0.9238795F;
/*      */   private static final float P935 = 0.9350162F;
/*      */   private static final float P939 = 0.9396926F;
/*      */   private static final float P951 = 0.95105654F;
/*      */   private static final float P959 = 0.959493F;
/*      */   private static final float P970 = 0.97094184F;
/*      */   private static final float P974 = 0.9749279F;
/*      */   private static final float P984 = 0.9848077F;
/*      */   private static final float P989 = 0.98982143F;
/*      */   private static final float P992 = 0.99270886F;
/*      */   private static final float PONE = 1.0F;
/*      */   private static final int NFAC = 10;
/*      */   
/*      */   static boolean nfftValid(int nfft) {
/*   45 */     return (ArrayMath.binarySearch(_ntable, nfft) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int nfftSmall(int n) {
/*   58 */     Check.argument((n <= 720720), "n does not exceed 720720");
/*   59 */     int itable = ArrayMath.binarySearch(_ntable, n);
/*   60 */     if (itable < 0) itable = -(itable + 1); 
/*   61 */     return _ntable[itable];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int nfftFast(int n) {
/*   74 */     Check.argument((n <= 720720), "n does not exceed 720720");
/*   75 */     int ifast = ArrayMath.binarySearch(_ntable, n);
/*   76 */     if (ifast < 0) ifast = -(ifast + 1); 
/*   77 */     int nfast = _ntable[ifast];
/*   78 */     int nstop = 2 * nfast;
/*   79 */     double cfast = _ctable[ifast];
/*   80 */     for (int i = ifast + 1; i < 240 && _ntable[i] < nstop; i++) {
/*   81 */       if (_ctable[i] < cfast) {
/*   82 */         cfast = _ctable[i];
/*   83 */         nfast = _ntable[i];
/*      */       } 
/*      */     } 
/*   86 */     return nfast;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void transform(int sign, int nfft, float[] z) {
/*   98 */     int nleft = nfft;
/*      */ 
/*      */     
/*  101 */     for (int jfac = 0; jfac < 10; jfac++) {
/*      */ 
/*      */       
/*  104 */       int ifac = _kfac[jfac];
/*  105 */       int ndiv = nleft / ifac;
/*  106 */       if (ndiv * ifac == nleft) {
/*      */ 
/*      */ 
/*      */         
/*  110 */         nleft = ndiv;
/*  111 */         int m = nfft / ifac;
/*      */ 
/*      */         
/*  114 */         int mu = 0;
/*  115 */         int mm = 0;
/*  116 */         for (int kfac = 1; kfac <= ifac && mm % ifac != 1; kfac++) {
/*  117 */           mu = kfac;
/*  118 */           mm = kfac * m;
/*      */         } 
/*  120 */         if (sign < 0) {
/*  121 */           mu = ifac - mu;
/*      */         }
/*      */         
/*  124 */         int jinc = 2 * mm;
/*  125 */         int jmax = 2 * nfft;
/*  126 */         int j0 = 0;
/*  127 */         int j1 = j0 + jinc;
/*      */ 
/*      */         
/*  130 */         if (ifac == 2) {
/*  131 */           pfa2(z, m, j0, j1);
/*      */         } else {
/*      */           
/*  134 */           int j2 = (j1 + jinc) % jmax;
/*      */ 
/*      */           
/*  137 */           if (ifac == 3) {
/*  138 */             pfa3(z, mu, m, j0, j1, j2);
/*      */           } else {
/*      */             
/*  141 */             int j3 = (j2 + jinc) % jmax;
/*      */ 
/*      */             
/*  144 */             if (ifac == 4) {
/*  145 */               pfa4(z, mu, m, j0, j1, j2, j3);
/*      */             } else {
/*      */               
/*  148 */               int j4 = (j3 + jinc) % jmax;
/*      */ 
/*      */               
/*  151 */               if (ifac == 5) {
/*  152 */                 pfa5(z, mu, m, j0, j1, j2, j3, j4);
/*      */               } else {
/*      */                 
/*  155 */                 int j5 = (j4 + jinc) % jmax;
/*  156 */                 int j6 = (j5 + jinc) % jmax;
/*      */ 
/*      */                 
/*  159 */                 if (ifac == 7)
/*  160 */                 { pfa7(z, mu, m, j0, j1, j2, j3, j4, j5, j6); }
/*      */                 else
/*      */                 
/*  163 */                 { int j7 = (j6 + jinc) % jmax;
/*      */ 
/*      */                   
/*  166 */                   if (ifac == 8)
/*  167 */                   { pfa8(z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7); }
/*      */                   else
/*      */                   
/*  170 */                   { int j8 = (j7 + jinc) % jmax;
/*      */ 
/*      */                     
/*  173 */                     if (ifac == 9)
/*  174 */                     { pfa9(z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8); }
/*      */                     else
/*      */                     
/*  177 */                     { int j9 = (j8 + jinc) % jmax;
/*  178 */                       int j10 = (j9 + jinc) % jmax;
/*      */ 
/*      */                       
/*  181 */                       if (ifac == 11)
/*  182 */                       { pfa11(z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10); }
/*      */                       else
/*      */                       
/*  185 */                       { int j11 = (j10 + jinc) % jmax;
/*  186 */                         int j12 = (j11 + jinc) % jmax;
/*      */ 
/*      */                         
/*  189 */                         if (ifac == 13)
/*  190 */                         { pfa13(z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12); }
/*      */                         else
/*      */                         
/*  193 */                         { int j13 = (j12 + jinc) % jmax;
/*  194 */                           int j14 = (j13 + jinc) % jmax;
/*  195 */                           int j15 = (j14 + jinc) % jmax;
/*      */ 
/*      */                           
/*  198 */                           if (ifac == 16)
/*  199 */                             pfa16(z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15);  }  }  }  }  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  205 */     }  } private static void pfa2(float[] z, int m, int j0, int j1) { for (int i = 0; i < m; i++) {
/*  206 */       float t1r = z[j0] - z[j1];
/*  207 */       float t1i = z[j0 + 1] - z[j1 + 1];
/*  208 */       z[j0] = z[j0] + z[j1];
/*  209 */       z[j0 + 1] = z[j0 + 1] + z[j1 + 1];
/*  210 */       z[j1] = t1r;
/*  211 */       z[j1 + 1] = t1i;
/*  212 */       int jt = j1 + 2;
/*  213 */       j1 = j0 + 2;
/*  214 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pfa3(float[] z, int mu, int m, int j0, int j1, int j2) {
/*      */     float c1;
/*  221 */     if (mu == 1) {
/*  222 */       c1 = 0.8660254F;
/*      */     } else {
/*  224 */       c1 = -0.8660254F;
/*      */     } 
/*  226 */     for (int i = 0; i < m; i++) {
/*  227 */       float t1r = z[j1] + z[j2];
/*  228 */       float t1i = z[j1 + 1] + z[j2 + 1];
/*  229 */       float y1r = z[j0] - 0.5F * t1r;
/*  230 */       float y1i = z[j0 + 1] - 0.5F * t1i;
/*  231 */       float y2r = c1 * (z[j1] - z[j2]);
/*  232 */       float y2i = c1 * (z[j1 + 1] - z[j2 + 1]);
/*  233 */       z[j0] = z[j0] + t1r;
/*  234 */       z[j0 + 1] = z[j0 + 1] + t1i;
/*  235 */       z[j1] = y1r - y2i;
/*  236 */       z[j1 + 1] = y1i + y2r;
/*  237 */       z[j2] = y1r + y2i;
/*  238 */       z[j2 + 1] = y1i - y2r;
/*  239 */       int jt = j2 + 2;
/*  240 */       j2 = j1 + 2;
/*  241 */       j1 = j0 + 2;
/*  242 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa4(float[] z, int mu, int m, int j0, int j1, int j2, int j3) {
/*      */     float c1;
/*  249 */     if (mu == 1) {
/*  250 */       c1 = 1.0F;
/*      */     } else {
/*  252 */       c1 = -1.0F;
/*      */     } 
/*  254 */     for (int i = 0; i < m; i++) {
/*  255 */       float t1r = z[j0] + z[j2];
/*  256 */       float t1i = z[j0 + 1] + z[j2 + 1];
/*  257 */       float t2r = z[j1] + z[j3];
/*  258 */       float t2i = z[j1 + 1] + z[j3 + 1];
/*  259 */       float y1r = z[j0] - z[j2];
/*  260 */       float y1i = z[j0 + 1] - z[j2 + 1];
/*  261 */       float y3r = c1 * (z[j1] - z[j3]);
/*  262 */       float y3i = c1 * (z[j1 + 1] - z[j3 + 1]);
/*  263 */       z[j0] = t1r + t2r;
/*  264 */       z[j0 + 1] = t1i + t2i;
/*  265 */       z[j1] = y1r - y3i;
/*  266 */       z[j1 + 1] = y1i + y3r;
/*  267 */       z[j2] = t1r - t2r;
/*  268 */       z[j2 + 1] = t1i - t2i;
/*  269 */       z[j3] = y1r + y3i;
/*  270 */       z[j3 + 1] = y1i - y3r;
/*  271 */       int jt = j3 + 2;
/*  272 */       j3 = j2 + 2;
/*  273 */       j2 = j1 + 2;
/*  274 */       j1 = j0 + 2;
/*  275 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa5(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4) { float c1;
/*      */     float c2;
/*      */     float c3;
/*  282 */     if (mu == 1) {
/*  283 */       c1 = 0.559017F;
/*  284 */       c2 = 0.95105654F;
/*  285 */       c3 = 0.58778524F;
/*  286 */     } else if (mu == 2) {
/*  287 */       c1 = -0.559017F;
/*  288 */       c2 = 0.58778524F;
/*  289 */       c3 = -0.95105654F;
/*  290 */     } else if (mu == 3) {
/*  291 */       c1 = -0.559017F;
/*  292 */       c2 = -0.58778524F;
/*  293 */       c3 = 0.95105654F;
/*      */     } else {
/*  295 */       c1 = 0.559017F;
/*  296 */       c2 = -0.95105654F;
/*  297 */       c3 = -0.58778524F;
/*      */     } 
/*  299 */     for (int i = 0; i < m; i++) {
/*  300 */       float t1r = z[j1] + z[j4];
/*  301 */       float t1i = z[j1 + 1] + z[j4 + 1];
/*  302 */       float t2r = z[j2] + z[j3];
/*  303 */       float t2i = z[j2 + 1] + z[j3 + 1];
/*  304 */       float t3r = z[j1] - z[j4];
/*  305 */       float t3i = z[j1 + 1] - z[j4 + 1];
/*  306 */       float t4r = z[j2] - z[j3];
/*  307 */       float t4i = z[j2 + 1] - z[j3 + 1];
/*  308 */       float t5r = t1r + t2r;
/*  309 */       float t5i = t1i + t2i;
/*  310 */       float t6r = c1 * (t1r - t2r);
/*  311 */       float t6i = c1 * (t1i - t2i);
/*  312 */       float t7r = z[j0] - 0.25F * t5r;
/*  313 */       float t7i = z[j0 + 1] - 0.25F * t5i;
/*  314 */       float y1r = t7r + t6r;
/*  315 */       float y1i = t7i + t6i;
/*  316 */       float y2r = t7r - t6r;
/*  317 */       float y2i = t7i - t6i;
/*  318 */       float y3r = c3 * t3r - c2 * t4r;
/*  319 */       float y3i = c3 * t3i - c2 * t4i;
/*  320 */       float y4r = c2 * t3r + c3 * t4r;
/*  321 */       float y4i = c2 * t3i + c3 * t4i;
/*  322 */       z[j0] = z[j0] + t5r;
/*  323 */       z[j0 + 1] = z[j0 + 1] + t5i;
/*  324 */       z[j1] = y1r - y4i;
/*  325 */       z[j1 + 1] = y1i + y4r;
/*  326 */       z[j2] = y2r - y3i;
/*  327 */       z[j2 + 1] = y2i + y3r;
/*  328 */       z[j3] = y2r + y3i;
/*  329 */       z[j3 + 1] = y2i - y3r;
/*  330 */       z[j4] = y1r + y4i;
/*  331 */       z[j4 + 1] = y1i - y4r;
/*  332 */       int jt = j4 + 2;
/*  333 */       j4 = j3 + 2;
/*  334 */       j3 = j2 + 2;
/*  335 */       j2 = j1 + 2;
/*  336 */       j1 = j0 + 2;
/*  337 */       j0 = jt;
/*      */     }  } private static void pfa7(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6) { float c1;
/*      */     float c2;
/*      */     float c3;
/*      */     float c4;
/*      */     float c5;
/*      */     float c6;
/*  344 */     if (mu == 1) {
/*  345 */       c1 = 0.6234898F;
/*  346 */       c2 = -0.22252093F;
/*  347 */       c3 = -0.90096885F;
/*  348 */       c4 = 0.7818315F;
/*  349 */       c5 = 0.9749279F;
/*  350 */       c6 = 0.43388373F;
/*  351 */     } else if (mu == 2) {
/*  352 */       c1 = -0.22252093F;
/*  353 */       c2 = -0.90096885F;
/*  354 */       c3 = 0.6234898F;
/*  355 */       c4 = 0.9749279F;
/*  356 */       c5 = -0.43388373F;
/*  357 */       c6 = -0.7818315F;
/*  358 */     } else if (mu == 3) {
/*  359 */       c1 = -0.90096885F;
/*  360 */       c2 = 0.6234898F;
/*  361 */       c3 = -0.22252093F;
/*  362 */       c4 = 0.43388373F;
/*  363 */       c5 = -0.7818315F;
/*  364 */       c6 = 0.9749279F;
/*  365 */     } else if (mu == 4) {
/*  366 */       c1 = -0.90096885F;
/*  367 */       c2 = 0.6234898F;
/*  368 */       c3 = -0.22252093F;
/*  369 */       c4 = -0.43388373F;
/*  370 */       c5 = 0.7818315F;
/*  371 */       c6 = -0.9749279F;
/*  372 */     } else if (mu == 5) {
/*  373 */       c1 = -0.22252093F;
/*  374 */       c2 = -0.90096885F;
/*  375 */       c3 = 0.6234898F;
/*  376 */       c4 = -0.9749279F;
/*  377 */       c5 = 0.43388373F;
/*  378 */       c6 = 0.7818315F;
/*      */     } else {
/*  380 */       c1 = 0.6234898F;
/*  381 */       c2 = -0.22252093F;
/*  382 */       c3 = -0.90096885F;
/*  383 */       c4 = -0.7818315F;
/*  384 */       c5 = -0.9749279F;
/*  385 */       c6 = -0.43388373F;
/*      */     } 
/*  387 */     for (int i = 0; i < m; i++) {
/*  388 */       float t1r = z[j1] + z[j6];
/*  389 */       float t1i = z[j1 + 1] + z[j6 + 1];
/*  390 */       float t2r = z[j2] + z[j5];
/*  391 */       float t2i = z[j2 + 1] + z[j5 + 1];
/*  392 */       float t3r = z[j3] + z[j4];
/*  393 */       float t3i = z[j3 + 1] + z[j4 + 1];
/*  394 */       float t4r = z[j1] - z[j6];
/*  395 */       float t4i = z[j1 + 1] - z[j6 + 1];
/*  396 */       float t5r = z[j2] - z[j5];
/*  397 */       float t5i = z[j2 + 1] - z[j5 + 1];
/*  398 */       float t6r = z[j3] - z[j4];
/*  399 */       float t6i = z[j3 + 1] - z[j4 + 1];
/*  400 */       float t7r = z[j0] - 0.5F * t3r;
/*  401 */       float t7i = z[j0 + 1] - 0.5F * t3i;
/*  402 */       float t8r = t1r - t3r;
/*  403 */       float t8i = t1i - t3i;
/*  404 */       float t9r = t2r - t3r;
/*  405 */       float t9i = t2i - t3i;
/*  406 */       float y1r = t7r + c1 * t8r + c2 * t9r;
/*  407 */       float y1i = t7i + c1 * t8i + c2 * t9i;
/*  408 */       float y2r = t7r + c2 * t8r + c3 * t9r;
/*  409 */       float y2i = t7i + c2 * t8i + c3 * t9i;
/*  410 */       float y3r = t7r + c3 * t8r + c1 * t9r;
/*  411 */       float y3i = t7i + c3 * t8i + c1 * t9i;
/*  412 */       float y4r = c6 * t4r - c4 * t5r + c5 * t6r;
/*  413 */       float y4i = c6 * t4i - c4 * t5i + c5 * t6i;
/*  414 */       float y5r = c5 * t4r - c6 * t5r - c4 * t6r;
/*  415 */       float y5i = c5 * t4i - c6 * t5i - c4 * t6i;
/*  416 */       float y6r = c4 * t4r + c5 * t5r + c6 * t6r;
/*  417 */       float y6i = c4 * t4i + c5 * t5i + c6 * t6i;
/*  418 */       z[j0] = z[j0] + t1r + t2r + t3r;
/*  419 */       z[j0 + 1] = z[j0 + 1] + t1i + t2i + t3i;
/*  420 */       z[j1] = y1r - y6i;
/*  421 */       z[j1 + 1] = y1i + y6r;
/*  422 */       z[j2] = y2r - y5i;
/*  423 */       z[j2 + 1] = y2i + y5r;
/*  424 */       z[j3] = y3r - y4i;
/*  425 */       z[j3 + 1] = y3i + y4r;
/*  426 */       z[j4] = y3r + y4i;
/*  427 */       z[j4 + 1] = y3i - y4r;
/*  428 */       z[j5] = y2r + y5i;
/*  429 */       z[j5 + 1] = y2i - y5r;
/*  430 */       z[j6] = y1r + y6i;
/*  431 */       z[j6 + 1] = y1i - y6r;
/*  432 */       int jt = j6 + 2;
/*  433 */       j6 = j5 + 2;
/*  434 */       j5 = j4 + 2;
/*  435 */       j4 = j3 + 2;
/*  436 */       j3 = j2 + 2;
/*  437 */       j2 = j1 + 2;
/*  438 */       j1 = j0 + 2;
/*  439 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pfa8(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7) {
/*      */     float c1, c2;
/*  446 */     if (mu == 1) {
/*  447 */       c1 = 1.0F;
/*  448 */       c2 = 0.70710677F;
/*  449 */     } else if (mu == 3) {
/*  450 */       c1 = -1.0F;
/*  451 */       c2 = -0.70710677F;
/*  452 */     } else if (mu == 5) {
/*  453 */       c1 = 1.0F;
/*  454 */       c2 = -0.70710677F;
/*      */     } else {
/*  456 */       c1 = -1.0F;
/*  457 */       c2 = 0.70710677F;
/*      */     } 
/*  459 */     float c3 = c1 * c2;
/*  460 */     for (int i = 0; i < m; i++) {
/*  461 */       float t1r = z[j0] + z[j4];
/*  462 */       float t1i = z[j0 + 1] + z[j4 + 1];
/*  463 */       float t2r = z[j0] - z[j4];
/*  464 */       float t2i = z[j0 + 1] - z[j4 + 1];
/*  465 */       float t3r = z[j1] + z[j5];
/*  466 */       float t3i = z[j1 + 1] + z[j5 + 1];
/*  467 */       float t4r = z[j1] - z[j5];
/*  468 */       float t4i = z[j1 + 1] - z[j5 + 1];
/*  469 */       float t5r = z[j2] + z[j6];
/*  470 */       float t5i = z[j2 + 1] + z[j6 + 1];
/*  471 */       float t6r = c1 * (z[j2] - z[j6]);
/*  472 */       float t6i = c1 * (z[j2 + 1] - z[j6 + 1]);
/*  473 */       float t7r = z[j3] + z[j7];
/*  474 */       float t7i = z[j3 + 1] + z[j7 + 1];
/*  475 */       float t8r = z[j3] - z[j7];
/*  476 */       float t8i = z[j3 + 1] - z[j7 + 1];
/*  477 */       float t9r = t1r + t5r;
/*  478 */       float t9i = t1i + t5i;
/*  479 */       float t10r = t3r + t7r;
/*  480 */       float t10i = t3i + t7i;
/*  481 */       float t11r = c2 * (t4r - t8r);
/*  482 */       float t11i = c2 * (t4i - t8i);
/*  483 */       float t12r = c3 * (t4r + t8r);
/*  484 */       float t12i = c3 * (t4i + t8i);
/*  485 */       float y1r = t2r + t11r;
/*  486 */       float y1i = t2i + t11i;
/*  487 */       float y2r = t1r - t5r;
/*  488 */       float y2i = t1i - t5i;
/*  489 */       float y3r = t2r - t11r;
/*  490 */       float y3i = t2i - t11i;
/*  491 */       float y5r = t12r - t6r;
/*  492 */       float y5i = t12i - t6i;
/*  493 */       float y6r = c1 * (t3r - t7r);
/*  494 */       float y6i = c1 * (t3i - t7i);
/*  495 */       float y7r = t12r + t6r;
/*  496 */       float y7i = t12i + t6i;
/*  497 */       z[j0] = t9r + t10r;
/*  498 */       z[j0 + 1] = t9i + t10i;
/*  499 */       z[j1] = y1r - y7i;
/*  500 */       z[j1 + 1] = y1i + y7r;
/*  501 */       z[j2] = y2r - y6i;
/*  502 */       z[j2 + 1] = y2i + y6r;
/*  503 */       z[j3] = y3r - y5i;
/*  504 */       z[j3 + 1] = y3i + y5r;
/*  505 */       z[j4] = t9r - t10r;
/*  506 */       z[j4 + 1] = t9i - t10i;
/*  507 */       z[j5] = y3r + y5i;
/*  508 */       z[j5 + 1] = y3i - y5r;
/*  509 */       z[j6] = y2r + y6i;
/*  510 */       z[j6 + 1] = y2i - y6r;
/*  511 */       z[j7] = y1r + y7i;
/*  512 */       z[j7 + 1] = y1i - y7r;
/*  513 */       int jt = j7 + 2;
/*  514 */       j7 = j6 + 2;
/*  515 */       j6 = j5 + 2;
/*  516 */       j5 = j4 + 2;
/*  517 */       j4 = j3 + 2;
/*  518 */       j3 = j2 + 2;
/*  519 */       j2 = j1 + 2;
/*  520 */       j1 = j0 + 2;
/*  521 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa9(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8) {
/*      */     float c1, c2, c3, c4, c5;
/*  528 */     if (mu == 1) {
/*  529 */       c1 = 0.8660254F;
/*  530 */       c2 = 0.76604444F;
/*  531 */       c3 = 0.64278764F;
/*  532 */       c4 = 0.17364818F;
/*  533 */       c5 = 0.9848077F;
/*  534 */     } else if (mu == 2) {
/*  535 */       c1 = -0.8660254F;
/*  536 */       c2 = 0.17364818F;
/*  537 */       c3 = 0.9848077F;
/*  538 */       c4 = -0.9396926F;
/*  539 */       c5 = 0.34202015F;
/*  540 */     } else if (mu == 4) {
/*  541 */       c1 = 0.8660254F;
/*  542 */       c2 = -0.9396926F;
/*  543 */       c3 = 0.34202015F;
/*  544 */       c4 = 0.76604444F;
/*  545 */       c5 = -0.64278764F;
/*  546 */     } else if (mu == 5) {
/*  547 */       c1 = -0.8660254F;
/*  548 */       c2 = -0.9396926F;
/*  549 */       c3 = -0.34202015F;
/*  550 */       c4 = 0.76604444F;
/*  551 */       c5 = 0.64278764F;
/*  552 */     } else if (mu == 7) {
/*  553 */       c1 = 0.8660254F;
/*  554 */       c2 = 0.17364818F;
/*  555 */       c3 = -0.9848077F;
/*  556 */       c4 = -0.9396926F;
/*  557 */       c5 = -0.34202015F;
/*      */     } else {
/*  559 */       c1 = -0.8660254F;
/*  560 */       c2 = 0.76604444F;
/*  561 */       c3 = -0.64278764F;
/*  562 */       c4 = 0.17364818F;
/*  563 */       c5 = -0.9848077F;
/*      */     } 
/*  565 */     float c6 = c1 * c2;
/*  566 */     float c7 = c1 * c3;
/*  567 */     float c8 = c1 * c4;
/*  568 */     float c9 = c1 * c5;
/*  569 */     for (int i = 0; i < m; i++) {
/*  570 */       float t1r = z[j3] + z[j6];
/*  571 */       float t1i = z[j3 + 1] + z[j6 + 1];
/*  572 */       float t2r = z[j0] - 0.5F * t1r;
/*  573 */       float t2i = z[j0 + 1] - 0.5F * t1i;
/*  574 */       float t3r = c1 * (z[j3] - z[j6]);
/*  575 */       float t3i = c1 * (z[j3 + 1] - z[j6 + 1]);
/*  576 */       float t4r = z[j0] + t1r;
/*  577 */       float t4i = z[j0 + 1] + t1i;
/*  578 */       float t5r = z[j4] + z[j7];
/*  579 */       float t5i = z[j4 + 1] + z[j7 + 1];
/*  580 */       float t6r = z[j1] - 0.5F * t5r;
/*  581 */       float t6i = z[j1 + 1] - 0.5F * t5i;
/*  582 */       float t7r = z[j4] - z[j7];
/*  583 */       float t7i = z[j4 + 1] - z[j7 + 1];
/*  584 */       float t8r = z[j1] + t5r;
/*  585 */       float t8i = z[j1 + 1] + t5i;
/*  586 */       float t9r = z[j2] + z[j5];
/*  587 */       float t9i = z[j2 + 1] + z[j5 + 1];
/*  588 */       float t10r = z[j8] - 0.5F * t9r;
/*  589 */       float t10i = z[j8 + 1] - 0.5F * t9i;
/*  590 */       float t11r = z[j2] - z[j5];
/*  591 */       float t11i = z[j2 + 1] - z[j5 + 1];
/*  592 */       float t12r = z[j8] + t9r;
/*  593 */       float t12i = z[j8 + 1] + t9i;
/*  594 */       float t13r = t8r + t12r;
/*  595 */       float t13i = t8i + t12i;
/*  596 */       float t14r = t6r + t10r;
/*  597 */       float t14i = t6i + t10i;
/*  598 */       float t15r = t6r - t10r;
/*  599 */       float t15i = t6i - t10i;
/*  600 */       float t16r = t7r + t11r;
/*  601 */       float t16i = t7i + t11i;
/*  602 */       float t17r = t7r - t11r;
/*  603 */       float t17i = t7i - t11i;
/*  604 */       float t18r = c2 * t14r - c7 * t17r;
/*  605 */       float t18i = c2 * t14i - c7 * t17i;
/*  606 */       float t19r = c4 * t14r + c9 * t17r;
/*  607 */       float t19i = c4 * t14i + c9 * t17i;
/*  608 */       float t20r = c3 * t15r + c6 * t16r;
/*  609 */       float t20i = c3 * t15i + c6 * t16i;
/*  610 */       float t21r = c5 * t15r - c8 * t16r;
/*  611 */       float t21i = c5 * t15i - c8 * t16i;
/*  612 */       float t22r = t18r + t19r;
/*  613 */       float t22i = t18i + t19i;
/*  614 */       float t23r = t20r - t21r;
/*  615 */       float t23i = t20i - t21i;
/*  616 */       float y1r = t2r + t18r;
/*  617 */       float y1i = t2i + t18i;
/*  618 */       float y2r = t2r + t19r;
/*  619 */       float y2i = t2i + t19i;
/*  620 */       float y3r = t4r - 0.5F * t13r;
/*  621 */       float y3i = t4i - 0.5F * t13i;
/*  622 */       float y4r = t2r - t22r;
/*  623 */       float y4i = t2i - t22i;
/*  624 */       float y5r = t3r - t23r;
/*  625 */       float y5i = t3i - t23i;
/*  626 */       float y6r = c1 * (t8r - t12r);
/*  627 */       float y6i = c1 * (t8i - t12i);
/*  628 */       float y7r = t21r - t3r;
/*  629 */       float y7i = t21i - t3i;
/*  630 */       float y8r = t3r + t20r;
/*  631 */       float y8i = t3i + t20i;
/*  632 */       z[j0] = t4r + t13r;
/*  633 */       z[j0 + 1] = t4i + t13i;
/*  634 */       z[j1] = y1r - y8i;
/*  635 */       z[j1 + 1] = y1i + y8r;
/*  636 */       z[j2] = y2r - y7i;
/*  637 */       z[j2 + 1] = y2i + y7r;
/*  638 */       z[j3] = y3r - y6i;
/*  639 */       z[j3 + 1] = y3i + y6r;
/*  640 */       z[j4] = y4r - y5i;
/*  641 */       z[j4 + 1] = y4i + y5r;
/*  642 */       z[j5] = y4r + y5i;
/*  643 */       z[j5 + 1] = y4i - y5r;
/*  644 */       z[j6] = y3r + y6i;
/*  645 */       z[j6 + 1] = y3i - y6r;
/*  646 */       z[j7] = y2r + y7i;
/*  647 */       z[j7 + 1] = y2i - y7r;
/*  648 */       z[j8] = y1r + y8i;
/*  649 */       z[j8 + 1] = y1i - y8r;
/*  650 */       int jt = j8 + 2;
/*  651 */       j8 = j7 + 2;
/*  652 */       j7 = j6 + 2;
/*  653 */       j6 = j5 + 2;
/*  654 */       j5 = j4 + 2;
/*  655 */       j4 = j3 + 2;
/*  656 */       j3 = j2 + 2;
/*  657 */       j2 = j1 + 2;
/*  658 */       j1 = j0 + 2;
/*  659 */       j0 = jt;
/*      */     }  } private static void pfa11(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10) { float c1; float c2; float c3; float c4;
/*      */     float c5;
/*      */     float c6;
/*      */     float c7;
/*      */     float c8;
/*      */     float c9;
/*      */     float c10;
/*  667 */     if (mu == 1) {
/*  668 */       c1 = 0.8412535F;
/*  669 */       c2 = 0.41541502F;
/*  670 */       c3 = -0.14231484F;
/*  671 */       c4 = -0.65486073F;
/*  672 */       c5 = -0.959493F;
/*  673 */       c6 = 0.54064083F;
/*  674 */       c7 = 0.90963197F;
/*  675 */       c8 = 0.98982143F;
/*  676 */       c9 = 0.7557496F;
/*  677 */       c10 = 0.28173256F;
/*  678 */     } else if (mu == 2) {
/*  679 */       c1 = 0.41541502F;
/*  680 */       c2 = -0.65486073F;
/*  681 */       c3 = -0.959493F;
/*  682 */       c4 = -0.14231484F;
/*  683 */       c5 = 0.8412535F;
/*  684 */       c6 = 0.90963197F;
/*  685 */       c7 = 0.7557496F;
/*  686 */       c8 = -0.28173256F;
/*  687 */       c9 = -0.98982143F;
/*  688 */       c10 = -0.54064083F;
/*  689 */     } else if (mu == 3) {
/*  690 */       c1 = -0.14231484F;
/*  691 */       c2 = -0.959493F;
/*  692 */       c3 = 0.41541502F;
/*  693 */       c4 = 0.8412535F;
/*  694 */       c5 = -0.65486073F;
/*  695 */       c6 = 0.98982143F;
/*  696 */       c7 = -0.28173256F;
/*  697 */       c8 = -0.90963197F;
/*  698 */       c9 = 0.54064083F;
/*  699 */       c10 = 0.7557496F;
/*  700 */     } else if (mu == 4) {
/*  701 */       c1 = -0.65486073F;
/*  702 */       c2 = -0.14231484F;
/*  703 */       c3 = 0.8412535F;
/*  704 */       c4 = -0.959493F;
/*  705 */       c5 = 0.41541502F;
/*  706 */       c6 = 0.7557496F;
/*  707 */       c7 = -0.98982143F;
/*  708 */       c8 = 0.54064083F;
/*  709 */       c9 = 0.28173256F;
/*  710 */       c10 = -0.90963197F;
/*  711 */     } else if (mu == 5) {
/*  712 */       c1 = -0.959493F;
/*  713 */       c2 = 0.8412535F;
/*  714 */       c3 = -0.65486073F;
/*  715 */       c4 = 0.41541502F;
/*  716 */       c5 = -0.14231484F;
/*  717 */       c6 = 0.28173256F;
/*  718 */       c7 = -0.54064083F;
/*  719 */       c8 = 0.7557496F;
/*  720 */       c9 = -0.90963197F;
/*  721 */       c10 = 0.98982143F;
/*  722 */     } else if (mu == 6) {
/*  723 */       c1 = -0.959493F;
/*  724 */       c2 = 0.8412535F;
/*  725 */       c3 = -0.65486073F;
/*  726 */       c4 = 0.41541502F;
/*  727 */       c5 = -0.14231484F;
/*  728 */       c6 = -0.28173256F;
/*  729 */       c7 = 0.54064083F;
/*  730 */       c8 = -0.7557496F;
/*  731 */       c9 = 0.90963197F;
/*  732 */       c10 = -0.98982143F;
/*  733 */     } else if (mu == 7) {
/*  734 */       c1 = -0.65486073F;
/*  735 */       c2 = -0.14231484F;
/*  736 */       c3 = 0.8412535F;
/*  737 */       c4 = -0.959493F;
/*  738 */       c5 = 0.41541502F;
/*  739 */       c6 = -0.7557496F;
/*  740 */       c7 = 0.98982143F;
/*  741 */       c8 = -0.54064083F;
/*  742 */       c9 = -0.28173256F;
/*  743 */       c10 = 0.90963197F;
/*  744 */     } else if (mu == 8) {
/*  745 */       c1 = -0.14231484F;
/*  746 */       c2 = -0.959493F;
/*  747 */       c3 = 0.41541502F;
/*  748 */       c4 = 0.8412535F;
/*  749 */       c5 = -0.65486073F;
/*  750 */       c6 = -0.98982143F;
/*  751 */       c7 = 0.28173256F;
/*  752 */       c8 = 0.90963197F;
/*  753 */       c9 = -0.54064083F;
/*  754 */       c10 = -0.7557496F;
/*  755 */     } else if (mu == 9) {
/*  756 */       c1 = 0.41541502F;
/*  757 */       c2 = -0.65486073F;
/*  758 */       c3 = -0.959493F;
/*  759 */       c4 = -0.14231484F;
/*  760 */       c5 = 0.8412535F;
/*  761 */       c6 = -0.90963197F;
/*  762 */       c7 = -0.7557496F;
/*  763 */       c8 = 0.28173256F;
/*  764 */       c9 = 0.98982143F;
/*  765 */       c10 = 0.54064083F;
/*      */     } else {
/*  767 */       c1 = 0.8412535F;
/*  768 */       c2 = 0.41541502F;
/*  769 */       c3 = -0.14231484F;
/*  770 */       c4 = -0.65486073F;
/*  771 */       c5 = -0.959493F;
/*  772 */       c6 = -0.54064083F;
/*  773 */       c7 = -0.90963197F;
/*  774 */       c8 = -0.98982143F;
/*  775 */       c9 = -0.7557496F;
/*  776 */       c10 = -0.28173256F;
/*      */     } 
/*  778 */     for (int i = 0; i < m; i++) {
/*  779 */       float t1r = z[j1] + z[j10];
/*  780 */       float t1i = z[j1 + 1] + z[j10 + 1];
/*  781 */       float t2r = z[j2] + z[j9];
/*  782 */       float t2i = z[j2 + 1] + z[j9 + 1];
/*  783 */       float t3r = z[j3] + z[j8];
/*  784 */       float t3i = z[j3 + 1] + z[j8 + 1];
/*  785 */       float t4r = z[j4] + z[j7];
/*  786 */       float t4i = z[j4 + 1] + z[j7 + 1];
/*  787 */       float t5r = z[j5] + z[j6];
/*  788 */       float t5i = z[j5 + 1] + z[j6 + 1];
/*  789 */       float t6r = z[j1] - z[j10];
/*  790 */       float t6i = z[j1 + 1] - z[j10 + 1];
/*  791 */       float t7r = z[j2] - z[j9];
/*  792 */       float t7i = z[j2 + 1] - z[j9 + 1];
/*  793 */       float t8r = z[j3] - z[j8];
/*  794 */       float t8i = z[j3 + 1] - z[j8 + 1];
/*  795 */       float t9r = z[j4] - z[j7];
/*  796 */       float t9i = z[j4 + 1] - z[j7 + 1];
/*  797 */       float t10r = z[j5] - z[j6];
/*  798 */       float t10i = z[j5 + 1] - z[j6 + 1];
/*  799 */       float t11r = z[j0] - 0.5F * t5r;
/*  800 */       float t11i = z[j0 + 1] - 0.5F * t5i;
/*  801 */       float t12r = t1r - t5r;
/*  802 */       float t12i = t1i - t5i;
/*  803 */       float t13r = t2r - t5r;
/*  804 */       float t13i = t2i - t5i;
/*  805 */       float t14r = t3r - t5r;
/*  806 */       float t14i = t3i - t5i;
/*  807 */       float t15r = t4r - t5r;
/*  808 */       float t15i = t4i - t5i;
/*  809 */       float y1r = t11r + c1 * t12r + c2 * t13r + c3 * t14r + c4 * t15r;
/*  810 */       float y1i = t11i + c1 * t12i + c2 * t13i + c3 * t14i + c4 * t15i;
/*  811 */       float y2r = t11r + c2 * t12r + c4 * t13r + c5 * t14r + c3 * t15r;
/*  812 */       float y2i = t11i + c2 * t12i + c4 * t13i + c5 * t14i + c3 * t15i;
/*  813 */       float y3r = t11r + c3 * t12r + c5 * t13r + c2 * t14r + c1 * t15r;
/*  814 */       float y3i = t11i + c3 * t12i + c5 * t13i + c2 * t14i + c1 * t15i;
/*  815 */       float y4r = t11r + c4 * t12r + c3 * t13r + c1 * t14r + c5 * t15r;
/*  816 */       float y4i = t11i + c4 * t12i + c3 * t13i + c1 * t14i + c5 * t15i;
/*  817 */       float y5r = t11r + c5 * t12r + c1 * t13r + c4 * t14r + c2 * t15r;
/*  818 */       float y5i = t11i + c5 * t12i + c1 * t13i + c4 * t14i + c2 * t15i;
/*  819 */       float y6r = c10 * t6r - c6 * t7r + c9 * t8r - c7 * t9r + c8 * t10r;
/*  820 */       float y6i = c10 * t6i - c6 * t7i + c9 * t8i - c7 * t9i + c8 * t10i;
/*  821 */       float y7r = c9 * t6r - c8 * t7r + c6 * t8r + c10 * t9r - c7 * t10r;
/*  822 */       float y7i = c9 * t6i - c8 * t7i + c6 * t8i + c10 * t9i - c7 * t10i;
/*  823 */       float y8r = c8 * t6r - c10 * t7r - c7 * t8r + c6 * t9r + c9 * t10r;
/*  824 */       float y8i = c8 * t6i - c10 * t7i - c7 * t8i + c6 * t9i + c9 * t10i;
/*  825 */       float y9r = c7 * t6r + c9 * t7r - c10 * t8r - c8 * t9r - c6 * t10r;
/*  826 */       float y9i = c7 * t6i + c9 * t7i - c10 * t8i - c8 * t9i - c6 * t10i;
/*  827 */       float y10r = c6 * t6r + c7 * t7r + c8 * t8r + c9 * t9r + c10 * t10r;
/*  828 */       float y10i = c6 * t6i + c7 * t7i + c8 * t8i + c9 * t9i + c10 * t10i;
/*  829 */       z[j0] = z[j0] + t1r + t2r + t3r + t4r + t5r;
/*  830 */       z[j0 + 1] = z[j0 + 1] + t1i + t2i + t3i + t4i + t5i;
/*  831 */       z[j1] = y1r - y10i;
/*  832 */       z[j1 + 1] = y1i + y10r;
/*  833 */       z[j2] = y2r - y9i;
/*  834 */       z[j2 + 1] = y2i + y9r;
/*  835 */       z[j3] = y3r - y8i;
/*  836 */       z[j3 + 1] = y3i + y8r;
/*  837 */       z[j4] = y4r - y7i;
/*  838 */       z[j4 + 1] = y4i + y7r;
/*  839 */       z[j5] = y5r - y6i;
/*  840 */       z[j5 + 1] = y5i + y6r;
/*  841 */       z[j6] = y5r + y6i;
/*  842 */       z[j6 + 1] = y5i - y6r;
/*  843 */       z[j7] = y4r + y7i;
/*  844 */       z[j7 + 1] = y4i - y7r;
/*  845 */       z[j8] = y3r + y8i;
/*  846 */       z[j8 + 1] = y3i - y8r;
/*  847 */       z[j9] = y2r + y9i;
/*  848 */       z[j9 + 1] = y2i - y9r;
/*  849 */       z[j10] = y1r + y10i;
/*  850 */       z[j10 + 1] = y1i - y10r;
/*  851 */       int jt = j10 + 2;
/*  852 */       j10 = j9 + 2;
/*  853 */       j9 = j8 + 2;
/*  854 */       j8 = j7 + 2;
/*  855 */       j7 = j6 + 2;
/*  856 */       j6 = j5 + 2;
/*  857 */       j5 = j4 + 2;
/*  858 */       j4 = j3 + 2;
/*  859 */       j3 = j2 + 2;
/*  860 */       j2 = j1 + 2;
/*  861 */       j1 = j0 + 2;
/*  862 */       j0 = jt;
/*      */     }  } private static void pfa13(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10, int j11, int j12) { float c1; float c2; float c3; float c4; float c5; float c6;
/*      */     float c7;
/*      */     float c8;
/*      */     float c9;
/*      */     float c10;
/*      */     float c11;
/*      */     float c12;
/*  870 */     if (mu == 1) {
/*  871 */       c1 = 0.885456F;
/*  872 */       c2 = 0.56806475F;
/*  873 */       c3 = 0.12053668F;
/*  874 */       c4 = -0.3546049F;
/*  875 */       c5 = -0.7485107F;
/*  876 */       c6 = -0.97094184F;
/*  877 */       c7 = 0.46472317F;
/*  878 */       c8 = 0.82298386F;
/*  879 */       c9 = 0.99270886F;
/*  880 */       c10 = 0.9350162F;
/*  881 */       c11 = 0.66312265F;
/*  882 */       c12 = 0.23931566F;
/*  883 */     } else if (mu == 2) {
/*  884 */       c1 = 0.56806475F;
/*  885 */       c2 = -0.3546049F;
/*  886 */       c3 = -0.97094184F;
/*  887 */       c4 = -0.7485107F;
/*  888 */       c5 = 0.12053668F;
/*  889 */       c6 = 0.885456F;
/*  890 */       c7 = 0.82298386F;
/*  891 */       c8 = 0.9350162F;
/*  892 */       c9 = 0.23931566F;
/*  893 */       c10 = -0.66312265F;
/*  894 */       c11 = -0.99270886F;
/*  895 */       c12 = -0.46472317F;
/*  896 */     } else if (mu == 3) {
/*  897 */       c1 = 0.12053668F;
/*  898 */       c2 = -0.97094184F;
/*  899 */       c3 = -0.3546049F;
/*  900 */       c4 = 0.885456F;
/*  901 */       c5 = 0.56806475F;
/*  902 */       c6 = -0.7485107F;
/*  903 */       c7 = 0.99270886F;
/*  904 */       c8 = 0.23931566F;
/*  905 */       c9 = -0.9350162F;
/*  906 */       c10 = -0.46472317F;
/*  907 */       c11 = 0.82298386F;
/*  908 */       c12 = 0.66312265F;
/*  909 */     } else if (mu == 4) {
/*  910 */       c1 = -0.3546049F;
/*  911 */       c2 = -0.7485107F;
/*  912 */       c3 = 0.885456F;
/*  913 */       c4 = 0.12053668F;
/*  914 */       c5 = -0.97094184F;
/*  915 */       c6 = 0.56806475F;
/*  916 */       c7 = 0.9350162F;
/*  917 */       c8 = -0.66312265F;
/*  918 */       c9 = -0.46472317F;
/*  919 */       c10 = 0.99270886F;
/*  920 */       c11 = -0.23931566F;
/*  921 */       c12 = -0.82298386F;
/*  922 */     } else if (mu == 5) {
/*  923 */       c1 = -0.7485107F;
/*  924 */       c2 = 0.12053668F;
/*  925 */       c3 = 0.56806475F;
/*  926 */       c4 = -0.97094184F;
/*  927 */       c5 = 0.885456F;
/*  928 */       c6 = -0.3546049F;
/*  929 */       c7 = 0.66312265F;
/*  930 */       c8 = -0.99270886F;
/*  931 */       c9 = 0.82298386F;
/*  932 */       c10 = -0.23931566F;
/*  933 */       c11 = -0.46472317F;
/*  934 */       c12 = 0.9350162F;
/*  935 */     } else if (mu == 6) {
/*  936 */       c1 = -0.97094184F;
/*  937 */       c2 = 0.885456F;
/*  938 */       c3 = -0.7485107F;
/*  939 */       c4 = 0.56806475F;
/*  940 */       c5 = -0.3546049F;
/*  941 */       c6 = 0.12053668F;
/*  942 */       c7 = 0.23931566F;
/*  943 */       c8 = -0.46472317F;
/*  944 */       c9 = 0.66312265F;
/*  945 */       c10 = -0.82298386F;
/*  946 */       c11 = 0.9350162F;
/*  947 */       c12 = -0.99270886F;
/*  948 */     } else if (mu == 7) {
/*  949 */       c1 = -0.97094184F;
/*  950 */       c2 = 0.885456F;
/*  951 */       c3 = -0.7485107F;
/*  952 */       c4 = 0.56806475F;
/*  953 */       c5 = -0.3546049F;
/*  954 */       c6 = 0.12053668F;
/*  955 */       c7 = -0.23931566F;
/*  956 */       c8 = 0.46472317F;
/*  957 */       c9 = -0.66312265F;
/*  958 */       c10 = 0.82298386F;
/*  959 */       c11 = -0.9350162F;
/*  960 */       c12 = 0.99270886F;
/*  961 */     } else if (mu == 8) {
/*  962 */       c1 = -0.7485107F;
/*  963 */       c2 = 0.12053668F;
/*  964 */       c3 = 0.56806475F;
/*  965 */       c4 = -0.97094184F;
/*  966 */       c5 = 0.885456F;
/*  967 */       c6 = -0.3546049F;
/*  968 */       c7 = -0.66312265F;
/*  969 */       c8 = 0.99270886F;
/*  970 */       c9 = -0.82298386F;
/*  971 */       c10 = 0.23931566F;
/*  972 */       c11 = 0.46472317F;
/*  973 */       c12 = -0.9350162F;
/*  974 */     } else if (mu == 9) {
/*  975 */       c1 = -0.3546049F;
/*  976 */       c2 = -0.7485107F;
/*  977 */       c3 = 0.885456F;
/*  978 */       c4 = 0.12053668F;
/*  979 */       c5 = -0.97094184F;
/*  980 */       c6 = 0.56806475F;
/*  981 */       c7 = -0.9350162F;
/*  982 */       c8 = 0.66312265F;
/*  983 */       c9 = 0.46472317F;
/*  984 */       c10 = -0.99270886F;
/*  985 */       c11 = 0.23931566F;
/*  986 */       c12 = 0.82298386F;
/*  987 */     } else if (mu == 10) {
/*  988 */       c1 = 0.12053668F;
/*  989 */       c2 = -0.97094184F;
/*  990 */       c3 = -0.3546049F;
/*  991 */       c4 = 0.885456F;
/*  992 */       c5 = 0.56806475F;
/*  993 */       c6 = -0.7485107F;
/*  994 */       c7 = -0.99270886F;
/*  995 */       c8 = -0.23931566F;
/*  996 */       c9 = 0.9350162F;
/*  997 */       c10 = 0.46472317F;
/*  998 */       c11 = -0.82298386F;
/*  999 */       c12 = -0.66312265F;
/* 1000 */     } else if (mu == 11) {
/* 1001 */       c1 = 0.56806475F;
/* 1002 */       c2 = -0.3546049F;
/* 1003 */       c3 = -0.97094184F;
/* 1004 */       c4 = -0.7485107F;
/* 1005 */       c5 = 0.12053668F;
/* 1006 */       c6 = 0.885456F;
/* 1007 */       c7 = -0.82298386F;
/* 1008 */       c8 = -0.9350162F;
/* 1009 */       c9 = -0.23931566F;
/* 1010 */       c10 = 0.66312265F;
/* 1011 */       c11 = 0.99270886F;
/* 1012 */       c12 = 0.46472317F;
/*      */     } else {
/* 1014 */       c1 = 0.885456F;
/* 1015 */       c2 = 0.56806475F;
/* 1016 */       c3 = 0.12053668F;
/* 1017 */       c4 = -0.3546049F;
/* 1018 */       c5 = -0.7485107F;
/* 1019 */       c6 = -0.97094184F;
/* 1020 */       c7 = -0.46472317F;
/* 1021 */       c8 = -0.82298386F;
/* 1022 */       c9 = -0.99270886F;
/* 1023 */       c10 = -0.9350162F;
/* 1024 */       c11 = -0.66312265F;
/* 1025 */       c12 = -0.23931566F;
/*      */     } 
/* 1027 */     for (int i = 0; i < m; i++) {
/* 1028 */       float t1r = z[j1] + z[j12];
/* 1029 */       float t1i = z[j1 + 1] + z[j12 + 1];
/* 1030 */       float t2r = z[j2] + z[j11];
/* 1031 */       float t2i = z[j2 + 1] + z[j11 + 1];
/* 1032 */       float t3r = z[j3] + z[j10];
/* 1033 */       float t3i = z[j3 + 1] + z[j10 + 1];
/* 1034 */       float t4r = z[j4] + z[j9];
/* 1035 */       float t4i = z[j4 + 1] + z[j9 + 1];
/* 1036 */       float t5r = z[j5] + z[j8];
/* 1037 */       float t5i = z[j5 + 1] + z[j8 + 1];
/* 1038 */       float t6r = z[j6] + z[j7];
/* 1039 */       float t6i = z[j6 + 1] + z[j7 + 1];
/* 1040 */       float t7r = z[j1] - z[j12];
/* 1041 */       float t7i = z[j1 + 1] - z[j12 + 1];
/* 1042 */       float t8r = z[j2] - z[j11];
/* 1043 */       float t8i = z[j2 + 1] - z[j11 + 1];
/* 1044 */       float t9r = z[j3] - z[j10];
/* 1045 */       float t9i = z[j3 + 1] - z[j10 + 1];
/* 1046 */       float t10r = z[j4] - z[j9];
/* 1047 */       float t10i = z[j4 + 1] - z[j9 + 1];
/* 1048 */       float t11r = z[j5] - z[j8];
/* 1049 */       float t11i = z[j5 + 1] - z[j8 + 1];
/* 1050 */       float t12r = z[j6] - z[j7];
/* 1051 */       float t12i = z[j6 + 1] - z[j7 + 1];
/* 1052 */       float t13r = z[j0] - 0.5F * t6r;
/* 1053 */       float t13i = z[j0 + 1] - 0.5F * t6i;
/* 1054 */       float t14r = t1r - t6r;
/* 1055 */       float t14i = t1i - t6i;
/* 1056 */       float t15r = t2r - t6r;
/* 1057 */       float t15i = t2i - t6i;
/* 1058 */       float t16r = t3r - t6r;
/* 1059 */       float t16i = t3i - t6i;
/* 1060 */       float t17r = t4r - t6r;
/* 1061 */       float t17i = t4i - t6i;
/* 1062 */       float t18r = t5r - t6r;
/* 1063 */       float t18i = t5i - t6i;
/* 1064 */       float y1r = t13r + c1 * t14r + c2 * t15r + c3 * t16r + c4 * t17r + c5 * t18r;
/* 1065 */       float y1i = t13i + c1 * t14i + c2 * t15i + c3 * t16i + c4 * t17i + c5 * t18i;
/* 1066 */       float y2r = t13r + c2 * t14r + c4 * t15r + c6 * t16r + c5 * t17r + c3 * t18r;
/* 1067 */       float y2i = t13i + c2 * t14i + c4 * t15i + c6 * t16i + c5 * t17i + c3 * t18i;
/* 1068 */       float y3r = t13r + c3 * t14r + c6 * t15r + c4 * t16r + c1 * t17r + c2 * t18r;
/* 1069 */       float y3i = t13i + c3 * t14i + c6 * t15i + c4 * t16i + c1 * t17i + c2 * t18i;
/* 1070 */       float y4r = t13r + c4 * t14r + c5 * t15r + c1 * t16r + c3 * t17r + c6 * t18r;
/* 1071 */       float y4i = t13i + c4 * t14i + c5 * t15i + c1 * t16i + c3 * t17i + c6 * t18i;
/* 1072 */       float y5r = t13r + c5 * t14r + c3 * t15r + c2 * t16r + c6 * t17r + c1 * t18r;
/* 1073 */       float y5i = t13i + c5 * t14i + c3 * t15i + c2 * t16i + c6 * t17i + c1 * t18i;
/* 1074 */       float y6r = t13r + c6 * t14r + c1 * t15r + c5 * t16r + c2 * t17r + c4 * t18r;
/* 1075 */       float y6i = t13i + c6 * t14i + c1 * t15i + c5 * t16i + c2 * t17i + c4 * t18i;
/* 1076 */       float y7r = c12 * t7r - c7 * t8r + c11 * t9r - c8 * t10r + c10 * t11r - c9 * t12r;
/* 1077 */       float y7i = c12 * t7i - c7 * t8i + c11 * t9i - c8 * t10i + c10 * t11i - c9 * t12i;
/* 1078 */       float y8r = c11 * t7r - c9 * t8r + c8 * t9r - c12 * t10r - c7 * t11r + c10 * t12r;
/* 1079 */       float y8i = c11 * t7i - c9 * t8i + c8 * t9i - c12 * t10i - c7 * t11i + c10 * t12i;
/* 1080 */       float y9r = c10 * t7r - c11 * t8r - c7 * t9r + c9 * t10r - c12 * t11r - c8 * t12r;
/* 1081 */       float y9i = c10 * t7i - c11 * t8i - c7 * t9i + c9 * t10i - c12 * t11i - c8 * t12i;
/* 1082 */       float y10r = c9 * t7r + c12 * t8r - c10 * t9r - c7 * t10r + c8 * t11r + c11 * t12r;
/* 1083 */       float y10i = c9 * t7i + c12 * t8i - c10 * t9i - c7 * t10i + c8 * t11i + c11 * t12i;
/* 1084 */       float y11r = c8 * t7r + c10 * t8r + c12 * t9r - c11 * t10r - c9 * t11r - c7 * t12r;
/* 1085 */       float y11i = c8 * t7i + c10 * t8i + c12 * t9i - c11 * t10i - c9 * t11i - c7 * t12i;
/* 1086 */       float y12r = c7 * t7r + c8 * t8r + c9 * t9r + c10 * t10r + c11 * t11r + c12 * t12r;
/* 1087 */       float y12i = c7 * t7i + c8 * t8i + c9 * t9i + c10 * t10i + c11 * t11i + c12 * t12i;
/* 1088 */       z[j0] = z[j0] + t1r + t2r + t3r + t4r + t5r + t6r;
/* 1089 */       z[j0 + 1] = z[j0 + 1] + t1i + t2i + t3i + t4i + t5i + t6i;
/* 1090 */       z[j1] = y1r - y12i;
/* 1091 */       z[j1 + 1] = y1i + y12r;
/* 1092 */       z[j2] = y2r - y11i;
/* 1093 */       z[j2 + 1] = y2i + y11r;
/* 1094 */       z[j3] = y3r - y10i;
/* 1095 */       z[j3 + 1] = y3i + y10r;
/* 1096 */       z[j4] = y4r - y9i;
/* 1097 */       z[j4 + 1] = y4i + y9r;
/* 1098 */       z[j5] = y5r - y8i;
/* 1099 */       z[j5 + 1] = y5i + y8r;
/* 1100 */       z[j6] = y6r - y7i;
/* 1101 */       z[j6 + 1] = y6i + y7r;
/* 1102 */       z[j7] = y6r + y7i;
/* 1103 */       z[j7 + 1] = y6i - y7r;
/* 1104 */       z[j8] = y5r + y8i;
/* 1105 */       z[j8 + 1] = y5i - y8r;
/* 1106 */       z[j9] = y4r + y9i;
/* 1107 */       z[j9 + 1] = y4i - y9r;
/* 1108 */       z[j10] = y3r + y10i;
/* 1109 */       z[j10 + 1] = y3i - y10r;
/* 1110 */       z[j11] = y2r + y11i;
/* 1111 */       z[j11 + 1] = y2i - y11r;
/* 1112 */       z[j12] = y1r + y12i;
/* 1113 */       z[j12 + 1] = y1i - y12r;
/* 1114 */       int jt = j12 + 2;
/* 1115 */       j12 = j11 + 2;
/* 1116 */       j11 = j10 + 2;
/* 1117 */       j10 = j9 + 2;
/* 1118 */       j9 = j8 + 2;
/* 1119 */       j8 = j7 + 2;
/* 1120 */       j7 = j6 + 2;
/* 1121 */       j6 = j5 + 2;
/* 1122 */       j5 = j4 + 2;
/* 1123 */       j4 = j3 + 2;
/* 1124 */       j3 = j2 + 2;
/* 1125 */       j2 = j1 + 2;
/* 1126 */       j1 = j0 + 2;
/* 1127 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pfa16(float[] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10, int j11, int j12, int j13, int j14, int j15) {
/*      */     float c1, c2, c3, c4;
/* 1135 */     if (mu == 1) {
/* 1136 */       c1 = 1.0F;
/* 1137 */       c2 = 0.9238795F;
/* 1138 */       c3 = 0.38268343F;
/* 1139 */       c4 = 0.70710677F;
/* 1140 */     } else if (mu == 3) {
/* 1141 */       c1 = -1.0F;
/* 1142 */       c2 = 0.38268343F;
/* 1143 */       c3 = 0.9238795F;
/* 1144 */       c4 = -0.70710677F;
/* 1145 */     } else if (mu == 5) {
/* 1146 */       c1 = 1.0F;
/* 1147 */       c2 = -0.38268343F;
/* 1148 */       c3 = 0.9238795F;
/* 1149 */       c4 = -0.70710677F;
/* 1150 */     } else if (mu == 7) {
/* 1151 */       c1 = -1.0F;
/* 1152 */       c2 = -0.9238795F;
/* 1153 */       c3 = 0.38268343F;
/* 1154 */       c4 = 0.70710677F;
/* 1155 */     } else if (mu == 9) {
/* 1156 */       c1 = 1.0F;
/* 1157 */       c2 = -0.9238795F;
/* 1158 */       c3 = -0.38268343F;
/* 1159 */       c4 = 0.70710677F;
/* 1160 */     } else if (mu == 11) {
/* 1161 */       c1 = -1.0F;
/* 1162 */       c2 = -0.38268343F;
/* 1163 */       c3 = -0.9238795F;
/* 1164 */       c4 = -0.70710677F;
/* 1165 */     } else if (mu == 13) {
/* 1166 */       c1 = 1.0F;
/* 1167 */       c2 = 0.38268343F;
/* 1168 */       c3 = -0.9238795F;
/* 1169 */       c4 = -0.70710677F;
/*      */     } else {
/* 1171 */       c1 = -1.0F;
/* 1172 */       c2 = 0.9238795F;
/* 1173 */       c3 = -0.38268343F;
/* 1174 */       c4 = 0.70710677F;
/*      */     } 
/* 1176 */     float c5 = c1 * c4;
/* 1177 */     float c6 = c1 * c3;
/* 1178 */     float c7 = c1 * c2;
/* 1179 */     for (int i = 0; i < m; i++) {
/* 1180 */       float t1r = z[j0] + z[j8];
/* 1181 */       float t1i = z[j0 + 1] + z[j8 + 1];
/* 1182 */       float t2r = z[j4] + z[j12];
/* 1183 */       float t2i = z[j4 + 1] + z[j12 + 1];
/* 1184 */       float t3r = z[j0] - z[j8];
/* 1185 */       float t3i = z[j0 + 1] - z[j8 + 1];
/* 1186 */       float t4r = c1 * (z[j4] - z[j12]);
/* 1187 */       float t4i = c1 * (z[j4 + 1] - z[j12 + 1]);
/* 1188 */       float t5r = t1r + t2r;
/* 1189 */       float t5i = t1i + t2i;
/* 1190 */       float t6r = t1r - t2r;
/* 1191 */       float t6i = t1i - t2i;
/* 1192 */       float t7r = z[j1] + z[j9];
/* 1193 */       float t7i = z[j1 + 1] + z[j9 + 1];
/* 1194 */       float t8r = z[j5] + z[j13];
/* 1195 */       float t8i = z[j5 + 1] + z[j13 + 1];
/* 1196 */       float t9r = z[j1] - z[j9];
/* 1197 */       float t9i = z[j1 + 1] - z[j9 + 1];
/* 1198 */       float t10r = z[j5] - z[j13];
/* 1199 */       float t10i = z[j5 + 1] - z[j13 + 1];
/* 1200 */       float t11r = t7r + t8r;
/* 1201 */       float t11i = t7i + t8i;
/* 1202 */       float t12r = t7r - t8r;
/* 1203 */       float t12i = t7i - t8i;
/* 1204 */       float t13r = z[j2] + z[j10];
/* 1205 */       float t13i = z[j2 + 1] + z[j10 + 1];
/* 1206 */       float t14r = z[j6] + z[j14];
/* 1207 */       float t14i = z[j6 + 1] + z[j14 + 1];
/* 1208 */       float t15r = z[j2] - z[j10];
/* 1209 */       float t15i = z[j2 + 1] - z[j10 + 1];
/* 1210 */       float t16r = z[j6] - z[j14];
/* 1211 */       float t16i = z[j6 + 1] - z[j14 + 1];
/* 1212 */       float t17r = t13r + t14r;
/* 1213 */       float t17i = t13i + t14i;
/* 1214 */       float t18r = c4 * (t15r - t16r);
/* 1215 */       float t18i = c4 * (t15i - t16i);
/* 1216 */       float t19r = c5 * (t15r + t16r);
/* 1217 */       float t19i = c5 * (t15i + t16i);
/* 1218 */       float t20r = c1 * (t13r - t14r);
/* 1219 */       float t20i = c1 * (t13i - t14i);
/* 1220 */       float t21r = z[j3] + z[j11];
/* 1221 */       float t21i = z[j3 + 1] + z[j11 + 1];
/* 1222 */       float t22r = z[j7] + z[j15];
/* 1223 */       float t22i = z[j7 + 1] + z[j15 + 1];
/* 1224 */       float t23r = z[j3] - z[j11];
/* 1225 */       float t23i = z[j3 + 1] - z[j11 + 1];
/* 1226 */       float t24r = z[j7] - z[j15];
/* 1227 */       float t24i = z[j7 + 1] - z[j15 + 1];
/* 1228 */       float t25r = t21r + t22r;
/* 1229 */       float t25i = t21i + t22i;
/* 1230 */       float t26r = t21r - t22r;
/* 1231 */       float t26i = t21i - t22i;
/* 1232 */       float t27r = t9r + t24r;
/* 1233 */       float t27i = t9i + t24i;
/* 1234 */       float t28r = t10r + t23r;
/* 1235 */       float t28i = t10i + t23i;
/* 1236 */       float t29r = t9r - t24r;
/* 1237 */       float t29i = t9i - t24i;
/* 1238 */       float t30r = t10r - t23r;
/* 1239 */       float t30i = t10i - t23i;
/* 1240 */       float t31r = t5r + t17r;
/* 1241 */       float t31i = t5i + t17i;
/* 1242 */       float t32r = t11r + t25r;
/* 1243 */       float t32i = t11i + t25i;
/* 1244 */       float t33r = t3r + t18r;
/* 1245 */       float t33i = t3i + t18i;
/* 1246 */       float t34r = c2 * t29r - c6 * t30r;
/* 1247 */       float t34i = c2 * t29i - c6 * t30i;
/* 1248 */       float t35r = t3r - t18r;
/* 1249 */       float t35i = t3i - t18i;
/* 1250 */       float t36r = c7 * t27r - c3 * t28r;
/* 1251 */       float t36i = c7 * t27i - c3 * t28i;
/* 1252 */       float t37r = t4r + t19r;
/* 1253 */       float t37i = t4i + t19i;
/* 1254 */       float t38r = c3 * t27r + c7 * t28r;
/* 1255 */       float t38i = c3 * t27i + c7 * t28i;
/* 1256 */       float t39r = t4r - t19r;
/* 1257 */       float t39i = t4i - t19i;
/* 1258 */       float t40r = c6 * t29r + c2 * t30r;
/* 1259 */       float t40i = c6 * t29i + c2 * t30i;
/* 1260 */       float t41r = c4 * (t12r - t26r);
/* 1261 */       float t41i = c4 * (t12i - t26i);
/* 1262 */       float t42r = c5 * (t12r + t26r);
/* 1263 */       float t42i = c5 * (t12i + t26i);
/* 1264 */       float y1r = t33r + t34r;
/* 1265 */       float y1i = t33i + t34i;
/* 1266 */       float y2r = t6r + t41r;
/* 1267 */       float y2i = t6i + t41i;
/* 1268 */       float y3r = t35r + t40r;
/* 1269 */       float y3i = t35i + t40i;
/* 1270 */       float y4r = t5r - t17r;
/* 1271 */       float y4i = t5i - t17i;
/* 1272 */       float y5r = t35r - t40r;
/* 1273 */       float y5i = t35i - t40i;
/* 1274 */       float y6r = t6r - t41r;
/* 1275 */       float y6i = t6i - t41i;
/* 1276 */       float y7r = t33r - t34r;
/* 1277 */       float y7i = t33i - t34i;
/* 1278 */       float y9r = t38r - t37r;
/* 1279 */       float y9i = t38i - t37i;
/* 1280 */       float y10r = t42r - t20r;
/* 1281 */       float y10i = t42i - t20i;
/* 1282 */       float y11r = t36r + t39r;
/* 1283 */       float y11i = t36i + t39i;
/* 1284 */       float y12r = c1 * (t11r - t25r);
/* 1285 */       float y12i = c1 * (t11i - t25i);
/* 1286 */       float y13r = t36r - t39r;
/* 1287 */       float y13i = t36i - t39i;
/* 1288 */       float y14r = t42r + t20r;
/* 1289 */       float y14i = t42i + t20i;
/* 1290 */       float y15r = t38r + t37r;
/* 1291 */       float y15i = t38i + t37i;
/* 1292 */       z[j0] = t31r + t32r;
/* 1293 */       z[j0 + 1] = t31i + t32i;
/* 1294 */       z[j1] = y1r - y15i;
/* 1295 */       z[j1 + 1] = y1i + y15r;
/* 1296 */       z[j2] = y2r - y14i;
/* 1297 */       z[j2 + 1] = y2i + y14r;
/* 1298 */       z[j3] = y3r - y13i;
/* 1299 */       z[j3 + 1] = y3i + y13r;
/* 1300 */       z[j4] = y4r - y12i;
/* 1301 */       z[j4 + 1] = y4i + y12r;
/* 1302 */       z[j5] = y5r - y11i;
/* 1303 */       z[j5 + 1] = y5i + y11r;
/* 1304 */       z[j6] = y6r - y10i;
/* 1305 */       z[j6 + 1] = y6i + y10r;
/* 1306 */       z[j7] = y7r - y9i;
/* 1307 */       z[j7 + 1] = y7i + y9r;
/* 1308 */       z[j8] = t31r - t32r;
/* 1309 */       z[j8 + 1] = t31i - t32i;
/* 1310 */       z[j9] = y7r + y9i;
/* 1311 */       z[j9 + 1] = y7i - y9r;
/* 1312 */       z[j10] = y6r + y10i;
/* 1313 */       z[j10 + 1] = y6i - y10r;
/* 1314 */       z[j11] = y5r + y11i;
/* 1315 */       z[j11 + 1] = y5i - y11r;
/* 1316 */       z[j12] = y4r + y12i;
/* 1317 */       z[j12 + 1] = y4i - y12r;
/* 1318 */       z[j13] = y3r + y13i;
/* 1319 */       z[j13 + 1] = y3i - y13r;
/* 1320 */       z[j14] = y2r + y14i;
/* 1321 */       z[j14 + 1] = y2i - y14r;
/* 1322 */       z[j15] = y1r + y15i;
/* 1323 */       z[j15 + 1] = y1i - y15r;
/* 1324 */       int jt = j15 + 2;
/* 1325 */       j15 = j14 + 2;
/* 1326 */       j14 = j13 + 2;
/* 1327 */       j13 = j12 + 2;
/* 1328 */       j12 = j11 + 2;
/* 1329 */       j11 = j10 + 2;
/* 1330 */       j10 = j9 + 2;
/* 1331 */       j9 = j8 + 2;
/* 1332 */       j8 = j7 + 2;
/* 1333 */       j7 = j6 + 2;
/* 1334 */       j6 = j5 + 2;
/* 1335 */       j5 = j4 + 2;
/* 1336 */       j4 = j3 + 2;
/* 1337 */       j3 = j2 + 2;
/* 1338 */       j2 = j1 + 2;
/* 1339 */       j1 = j0 + 2;
/* 1340 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void transform2a(int sign, int n1, int nfft, float[][] z) {
/* 1357 */     int nleft = nfft;
/*      */ 
/*      */     
/* 1360 */     for (int jfac = 0; jfac < 10; jfac++) {
/*      */ 
/*      */       
/* 1363 */       int ifac = _kfac[jfac];
/* 1364 */       int ndiv = nleft / ifac;
/* 1365 */       if (ndiv * ifac == nleft) {
/*      */ 
/*      */ 
/*      */         
/* 1369 */         nleft = ndiv;
/* 1370 */         int m = nfft / ifac;
/*      */ 
/*      */         
/* 1373 */         int mu = 0;
/* 1374 */         int mm = 0;
/* 1375 */         for (int kfac = 1; kfac <= ifac && mm % ifac != 1; kfac++) {
/* 1376 */           mu = kfac;
/* 1377 */           mm = kfac * m;
/*      */         } 
/* 1379 */         if (sign < 0) {
/* 1380 */           mu = ifac - mu;
/*      */         }
/*      */         
/* 1383 */         int jinc = mm;
/* 1384 */         int jmax = nfft;
/* 1385 */         int j0 = 0;
/* 1386 */         int j1 = j0 + jinc;
/*      */ 
/*      */         
/* 1389 */         if (ifac == 2) {
/* 1390 */           pfa2a(n1, z, m, j0, j1);
/*      */         } else {
/*      */           
/* 1393 */           int j2 = (j1 + jinc) % jmax;
/*      */ 
/*      */           
/* 1396 */           if (ifac == 3) {
/* 1397 */             pfa3a(n1, z, mu, m, j0, j1, j2);
/*      */           } else {
/*      */             
/* 1400 */             int j3 = (j2 + jinc) % jmax;
/*      */ 
/*      */             
/* 1403 */             if (ifac == 4) {
/* 1404 */               pfa4a(n1, z, mu, m, j0, j1, j2, j3);
/*      */             } else {
/*      */               
/* 1407 */               int j4 = (j3 + jinc) % jmax;
/*      */ 
/*      */               
/* 1410 */               if (ifac == 5) {
/* 1411 */                 pfa5a(n1, z, mu, m, j0, j1, j2, j3, j4);
/*      */               } else {
/*      */                 
/* 1414 */                 int j5 = (j4 + jinc) % jmax;
/* 1415 */                 int j6 = (j5 + jinc) % jmax;
/*      */ 
/*      */                 
/* 1418 */                 if (ifac == 7)
/* 1419 */                 { pfa7a(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6); }
/*      */                 else
/*      */                 
/* 1422 */                 { int j7 = (j6 + jinc) % jmax;
/*      */ 
/*      */                   
/* 1425 */                   if (ifac == 8)
/* 1426 */                   { pfa8a(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7); }
/*      */                   else
/*      */                   
/* 1429 */                   { int j8 = (j7 + jinc) % jmax;
/*      */ 
/*      */                     
/* 1432 */                     if (ifac == 9)
/* 1433 */                     { pfa9a(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8); }
/*      */                     else
/*      */                     
/* 1436 */                     { int j9 = (j8 + jinc) % jmax;
/* 1437 */                       int j10 = (j9 + jinc) % jmax;
/*      */ 
/*      */                       
/* 1440 */                       if (ifac == 11)
/* 1441 */                       { pfa11a(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10); }
/*      */                       else
/*      */                       
/* 1444 */                       { int j11 = (j10 + jinc) % jmax;
/* 1445 */                         int j12 = (j11 + jinc) % jmax;
/*      */ 
/*      */                         
/* 1448 */                         if (ifac == 13)
/* 1449 */                         { pfa13a(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12); }
/*      */                         else
/*      */                         
/* 1452 */                         { int j13 = (j12 + jinc) % jmax;
/* 1453 */                           int j14 = (j13 + jinc) % jmax;
/* 1454 */                           int j15 = (j14 + jinc) % jmax;
/*      */ 
/*      */                           
/* 1457 */                           if (ifac == 16)
/* 1458 */                             pfa16a(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15);  }  }  }  }  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1464 */     }  } private static void pfa2a(int n1, float[][] z, int m, int j0, int j1) { int m1 = 2 * n1;
/* 1465 */     for (int i = 0; i < m; i++) {
/* 1466 */       float[] zj0 = z[j0];
/* 1467 */       float[] zj1 = z[j1];
/* 1468 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1469 */         float t1r = zj0[i1] - zj1[i1];
/* 1470 */         float t1i = zj0[i1 + 1] - zj1[i1 + 1];
/* 1471 */         zj0[i1] = zj0[i1] + zj1[i1];
/* 1472 */         zj0[i1 + 1] = zj0[i1 + 1] + zj1[i1 + 1];
/* 1473 */         zj1[i1] = t1r;
/* 1474 */         zj1[i1 + 1] = t1i;
/*      */       } 
/* 1476 */       int jt = j1 + 1;
/* 1477 */       j1 = j0 + 1;
/* 1478 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */   
/*      */   private static void pfa3a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2) {
/*      */     float c1;
/* 1484 */     int m1 = 2 * n1;
/*      */     
/* 1486 */     if (mu == 1) {
/* 1487 */       c1 = 0.8660254F;
/*      */     } else {
/* 1489 */       c1 = -0.8660254F;
/*      */     } 
/* 1491 */     for (int i = 0; i < m; i++) {
/* 1492 */       float[] zj0 = z[j0];
/* 1493 */       float[] zj1 = z[j1];
/* 1494 */       float[] zj2 = z[j2];
/* 1495 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1496 */         float t1r = zj1[i1] + zj2[i1];
/* 1497 */         float t1i = zj1[i1 + 1] + zj2[i1 + 1];
/* 1498 */         float y1r = zj0[i1] - 0.5F * t1r;
/* 1499 */         float y1i = zj0[i1 + 1] - 0.5F * t1i;
/* 1500 */         float y2r = c1 * (zj1[i1] - zj2[i1]);
/* 1501 */         float y2i = c1 * (zj1[i1 + 1] - zj2[i1 + 1]);
/* 1502 */         zj0[i1] = zj0[i1] + t1r;
/* 1503 */         zj0[i1 + 1] = zj0[i1 + 1] + t1i;
/* 1504 */         zj1[i1] = y1r - y2i;
/* 1505 */         zj1[i1 + 1] = y1i + y2r;
/* 1506 */         zj2[i1] = y1r + y2i;
/* 1507 */         zj2[i1 + 1] = y1i - y2r;
/*      */       } 
/* 1509 */       int jt = j2 + 1;
/* 1510 */       j2 = j1 + 1;
/* 1511 */       j1 = j0 + 1;
/* 1512 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa4a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3) {
/*      */     float c1;
/* 1518 */     int m1 = 2 * n1;
/*      */     
/* 1520 */     if (mu == 1) {
/* 1521 */       c1 = 1.0F;
/*      */     } else {
/* 1523 */       c1 = -1.0F;
/*      */     } 
/* 1525 */     for (int i = 0; i < m; i++) {
/* 1526 */       float[] zj0 = z[j0];
/* 1527 */       float[] zj1 = z[j1];
/* 1528 */       float[] zj2 = z[j2];
/* 1529 */       float[] zj3 = z[j3];
/* 1530 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1531 */         float t1r = zj0[i1] + zj2[i1];
/* 1532 */         float t1i = zj0[i1 + 1] + zj2[i1 + 1];
/* 1533 */         float t2r = zj1[i1] + zj3[i1];
/* 1534 */         float t2i = zj1[i1 + 1] + zj3[i1 + 1];
/* 1535 */         float y1r = zj0[i1] - zj2[i1];
/* 1536 */         float y1i = zj0[i1 + 1] - zj2[i1 + 1];
/* 1537 */         float y3r = c1 * (zj1[i1] - zj3[i1]);
/* 1538 */         float y3i = c1 * (zj1[i1 + 1] - zj3[i1 + 1]);
/* 1539 */         zj0[i1] = t1r + t2r;
/* 1540 */         zj0[i1 + 1] = t1i + t2i;
/* 1541 */         zj1[i1] = y1r - y3i;
/* 1542 */         zj1[i1 + 1] = y1i + y3r;
/* 1543 */         zj2[i1] = t1r - t2r;
/* 1544 */         zj2[i1 + 1] = t1i - t2i;
/* 1545 */         zj3[i1] = y1r + y3i;
/* 1546 */         zj3[i1 + 1] = y1i - y3r;
/*      */       } 
/* 1548 */       int jt = j3 + 1;
/* 1549 */       j3 = j2 + 1;
/* 1550 */       j2 = j1 + 1;
/* 1551 */       j1 = j0 + 1;
/* 1552 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa5a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4) {
/*      */     float c1, c2, c3;
/* 1558 */     int m1 = 2 * n1;
/*      */     
/* 1560 */     if (mu == 1) {
/* 1561 */       c1 = 0.559017F;
/* 1562 */       c2 = 0.95105654F;
/* 1563 */       c3 = 0.58778524F;
/* 1564 */     } else if (mu == 2) {
/* 1565 */       c1 = -0.559017F;
/* 1566 */       c2 = 0.58778524F;
/* 1567 */       c3 = -0.95105654F;
/* 1568 */     } else if (mu == 3) {
/* 1569 */       c1 = -0.559017F;
/* 1570 */       c2 = -0.58778524F;
/* 1571 */       c3 = 0.95105654F;
/*      */     } else {
/* 1573 */       c1 = 0.559017F;
/* 1574 */       c2 = -0.95105654F;
/* 1575 */       c3 = -0.58778524F;
/*      */     } 
/* 1577 */     for (int i = 0; i < m; i++) {
/* 1578 */       float[] zj0 = z[j0];
/* 1579 */       float[] zj1 = z[j1];
/* 1580 */       float[] zj2 = z[j2];
/* 1581 */       float[] zj3 = z[j3];
/* 1582 */       float[] zj4 = z[j4];
/* 1583 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1584 */         float t1r = zj1[i1] + zj4[i1];
/* 1585 */         float t1i = zj1[i1 + 1] + zj4[i1 + 1];
/* 1586 */         float t2r = zj2[i1] + zj3[i1];
/* 1587 */         float t2i = zj2[i1 + 1] + zj3[i1 + 1];
/* 1588 */         float t3r = zj1[i1] - zj4[i1];
/* 1589 */         float t3i = zj1[i1 + 1] - zj4[i1 + 1];
/* 1590 */         float t4r = zj2[i1] - zj3[i1];
/* 1591 */         float t4i = zj2[i1 + 1] - zj3[i1 + 1];
/* 1592 */         float t5r = t1r + t2r;
/* 1593 */         float t5i = t1i + t2i;
/* 1594 */         float t6r = c1 * (t1r - t2r);
/* 1595 */         float t6i = c1 * (t1i - t2i);
/* 1596 */         float t7r = zj0[i1] - 0.25F * t5r;
/* 1597 */         float t7i = zj0[i1 + 1] - 0.25F * t5i;
/* 1598 */         float y1r = t7r + t6r;
/* 1599 */         float y1i = t7i + t6i;
/* 1600 */         float y2r = t7r - t6r;
/* 1601 */         float y2i = t7i - t6i;
/* 1602 */         float y3r = c3 * t3r - c2 * t4r;
/* 1603 */         float y3i = c3 * t3i - c2 * t4i;
/* 1604 */         float y4r = c2 * t3r + c3 * t4r;
/* 1605 */         float y4i = c2 * t3i + c3 * t4i;
/* 1606 */         zj0[i1] = zj0[i1] + t5r;
/* 1607 */         zj0[i1 + 1] = zj0[i1 + 1] + t5i;
/* 1608 */         zj1[i1] = y1r - y4i;
/* 1609 */         zj1[i1 + 1] = y1i + y4r;
/* 1610 */         zj2[i1] = y2r - y3i;
/* 1611 */         zj2[i1 + 1] = y2i + y3r;
/* 1612 */         zj3[i1] = y2r + y3i;
/* 1613 */         zj3[i1 + 1] = y2i - y3r;
/* 1614 */         zj4[i1] = y1r + y4i;
/* 1615 */         zj4[i1 + 1] = y1i - y4r;
/*      */       } 
/* 1617 */       int jt = j4 + 1;
/* 1618 */       j4 = j3 + 1;
/* 1619 */       j3 = j2 + 1;
/* 1620 */       j2 = j1 + 1;
/* 1621 */       j1 = j0 + 1;
/* 1622 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa7a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6) {
/*      */     float c1, c2, c3, c4, c5, c6;
/* 1628 */     int m1 = 2 * n1;
/*      */     
/* 1630 */     if (mu == 1) {
/* 1631 */       c1 = 0.6234898F;
/* 1632 */       c2 = -0.22252093F;
/* 1633 */       c3 = -0.90096885F;
/* 1634 */       c4 = 0.7818315F;
/* 1635 */       c5 = 0.9749279F;
/* 1636 */       c6 = 0.43388373F;
/* 1637 */     } else if (mu == 2) {
/* 1638 */       c1 = -0.22252093F;
/* 1639 */       c2 = -0.90096885F;
/* 1640 */       c3 = 0.6234898F;
/* 1641 */       c4 = 0.9749279F;
/* 1642 */       c5 = -0.43388373F;
/* 1643 */       c6 = -0.7818315F;
/* 1644 */     } else if (mu == 3) {
/* 1645 */       c1 = -0.90096885F;
/* 1646 */       c2 = 0.6234898F;
/* 1647 */       c3 = -0.22252093F;
/* 1648 */       c4 = 0.43388373F;
/* 1649 */       c5 = -0.7818315F;
/* 1650 */       c6 = 0.9749279F;
/* 1651 */     } else if (mu == 4) {
/* 1652 */       c1 = -0.90096885F;
/* 1653 */       c2 = 0.6234898F;
/* 1654 */       c3 = -0.22252093F;
/* 1655 */       c4 = -0.43388373F;
/* 1656 */       c5 = 0.7818315F;
/* 1657 */       c6 = -0.9749279F;
/* 1658 */     } else if (mu == 5) {
/* 1659 */       c1 = -0.22252093F;
/* 1660 */       c2 = -0.90096885F;
/* 1661 */       c3 = 0.6234898F;
/* 1662 */       c4 = -0.9749279F;
/* 1663 */       c5 = 0.43388373F;
/* 1664 */       c6 = 0.7818315F;
/*      */     } else {
/* 1666 */       c1 = 0.6234898F;
/* 1667 */       c2 = -0.22252093F;
/* 1668 */       c3 = -0.90096885F;
/* 1669 */       c4 = -0.7818315F;
/* 1670 */       c5 = -0.9749279F;
/* 1671 */       c6 = -0.43388373F;
/*      */     } 
/* 1673 */     for (int i = 0; i < m; i++) {
/* 1674 */       float[] zj0 = z[j0];
/* 1675 */       float[] zj1 = z[j1];
/* 1676 */       float[] zj2 = z[j2];
/* 1677 */       float[] zj3 = z[j3];
/* 1678 */       float[] zj4 = z[j4];
/* 1679 */       float[] zj5 = z[j5];
/* 1680 */       float[] zj6 = z[j6];
/* 1681 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1682 */         float t1r = zj1[i1] + zj6[i1];
/* 1683 */         float t1i = zj1[i1 + 1] + zj6[i1 + 1];
/* 1684 */         float t2r = zj2[i1] + zj5[i1];
/* 1685 */         float t2i = zj2[i1 + 1] + zj5[i1 + 1];
/* 1686 */         float t3r = zj3[i1] + zj4[i1];
/* 1687 */         float t3i = zj3[i1 + 1] + zj4[i1 + 1];
/* 1688 */         float t4r = zj1[i1] - zj6[i1];
/* 1689 */         float t4i = zj1[i1 + 1] - zj6[i1 + 1];
/* 1690 */         float t5r = zj2[i1] - zj5[i1];
/* 1691 */         float t5i = zj2[i1 + 1] - zj5[i1 + 1];
/* 1692 */         float t6r = zj3[i1] - zj4[i1];
/* 1693 */         float t6i = zj3[i1 + 1] - zj4[i1 + 1];
/* 1694 */         float t7r = zj0[i1] - 0.5F * t3r;
/* 1695 */         float t7i = zj0[i1 + 1] - 0.5F * t3i;
/* 1696 */         float t8r = t1r - t3r;
/* 1697 */         float t8i = t1i - t3i;
/* 1698 */         float t9r = t2r - t3r;
/* 1699 */         float t9i = t2i - t3i;
/* 1700 */         float y1r = t7r + c1 * t8r + c2 * t9r;
/* 1701 */         float y1i = t7i + c1 * t8i + c2 * t9i;
/* 1702 */         float y2r = t7r + c2 * t8r + c3 * t9r;
/* 1703 */         float y2i = t7i + c2 * t8i + c3 * t9i;
/* 1704 */         float y3r = t7r + c3 * t8r + c1 * t9r;
/* 1705 */         float y3i = t7i + c3 * t8i + c1 * t9i;
/* 1706 */         float y4r = c6 * t4r - c4 * t5r + c5 * t6r;
/* 1707 */         float y4i = c6 * t4i - c4 * t5i + c5 * t6i;
/* 1708 */         float y5r = c5 * t4r - c6 * t5r - c4 * t6r;
/* 1709 */         float y5i = c5 * t4i - c6 * t5i - c4 * t6i;
/* 1710 */         float y6r = c4 * t4r + c5 * t5r + c6 * t6r;
/* 1711 */         float y6i = c4 * t4i + c5 * t5i + c6 * t6i;
/* 1712 */         zj0[i1] = zj0[i1] + t1r + t2r + t3r;
/* 1713 */         zj0[i1 + 1] = zj0[i1 + 1] + t1i + t2i + t3i;
/* 1714 */         zj1[i1] = y1r - y6i;
/* 1715 */         zj1[i1 + 1] = y1i + y6r;
/* 1716 */         zj2[i1] = y2r - y5i;
/* 1717 */         zj2[i1 + 1] = y2i + y5r;
/* 1718 */         zj3[i1] = y3r - y4i;
/* 1719 */         zj3[i1 + 1] = y3i + y4r;
/* 1720 */         zj4[i1] = y3r + y4i;
/* 1721 */         zj4[i1 + 1] = y3i - y4r;
/* 1722 */         zj5[i1] = y2r + y5i;
/* 1723 */         zj5[i1 + 1] = y2i - y5r;
/* 1724 */         zj6[i1] = y1r + y6i;
/* 1725 */         zj6[i1 + 1] = y1i - y6r;
/*      */       } 
/* 1727 */       int jt = j6 + 1;
/* 1728 */       j6 = j5 + 1;
/* 1729 */       j5 = j4 + 1;
/* 1730 */       j4 = j3 + 1;
/* 1731 */       j3 = j2 + 1;
/* 1732 */       j2 = j1 + 1;
/* 1733 */       j1 = j0 + 1;
/* 1734 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa8a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7) {
/*      */     float c1, c2;
/* 1740 */     int m1 = 2 * n1;
/*      */     
/* 1742 */     if (mu == 1) {
/* 1743 */       c1 = 1.0F;
/* 1744 */       c2 = 0.70710677F;
/* 1745 */     } else if (mu == 3) {
/* 1746 */       c1 = -1.0F;
/* 1747 */       c2 = -0.70710677F;
/* 1748 */     } else if (mu == 5) {
/* 1749 */       c1 = 1.0F;
/* 1750 */       c2 = -0.70710677F;
/*      */     } else {
/* 1752 */       c1 = -1.0F;
/* 1753 */       c2 = 0.70710677F;
/*      */     } 
/* 1755 */     float c3 = c1 * c2;
/* 1756 */     for (int i = 0; i < m; i++) {
/* 1757 */       float[] zj0 = z[j0];
/* 1758 */       float[] zj1 = z[j1];
/* 1759 */       float[] zj2 = z[j2];
/* 1760 */       float[] zj3 = z[j3];
/* 1761 */       float[] zj4 = z[j4];
/* 1762 */       float[] zj5 = z[j5];
/* 1763 */       float[] zj6 = z[j6];
/* 1764 */       float[] zj7 = z[j7];
/* 1765 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1766 */         float t1r = zj0[i1] + zj4[i1];
/* 1767 */         float t1i = zj0[i1 + 1] + zj4[i1 + 1];
/* 1768 */         float t2r = zj0[i1] - zj4[i1];
/* 1769 */         float t2i = zj0[i1 + 1] - zj4[i1 + 1];
/* 1770 */         float t3r = zj1[i1] + zj5[i1];
/* 1771 */         float t3i = zj1[i1 + 1] + zj5[i1 + 1];
/* 1772 */         float t4r = zj1[i1] - zj5[i1];
/* 1773 */         float t4i = zj1[i1 + 1] - zj5[i1 + 1];
/* 1774 */         float t5r = zj2[i1] + zj6[i1];
/* 1775 */         float t5i = zj2[i1 + 1] + zj6[i1 + 1];
/* 1776 */         float t6r = c1 * (zj2[i1] - zj6[i1]);
/* 1777 */         float t6i = c1 * (zj2[i1 + 1] - zj6[i1 + 1]);
/* 1778 */         float t7r = zj3[i1] + zj7[i1];
/* 1779 */         float t7i = zj3[i1 + 1] + zj7[i1 + 1];
/* 1780 */         float t8r = zj3[i1] - zj7[i1];
/* 1781 */         float t8i = zj3[i1 + 1] - zj7[i1 + 1];
/* 1782 */         float t9r = t1r + t5r;
/* 1783 */         float t9i = t1i + t5i;
/* 1784 */         float t10r = t3r + t7r;
/* 1785 */         float t10i = t3i + t7i;
/* 1786 */         float t11r = c2 * (t4r - t8r);
/* 1787 */         float t11i = c2 * (t4i - t8i);
/* 1788 */         float t12r = c3 * (t4r + t8r);
/* 1789 */         float t12i = c3 * (t4i + t8i);
/* 1790 */         float y1r = t2r + t11r;
/* 1791 */         float y1i = t2i + t11i;
/* 1792 */         float y2r = t1r - t5r;
/* 1793 */         float y2i = t1i - t5i;
/* 1794 */         float y3r = t2r - t11r;
/* 1795 */         float y3i = t2i - t11i;
/* 1796 */         float y5r = t12r - t6r;
/* 1797 */         float y5i = t12i - t6i;
/* 1798 */         float y6r = c1 * (t3r - t7r);
/* 1799 */         float y6i = c1 * (t3i - t7i);
/* 1800 */         float y7r = t12r + t6r;
/* 1801 */         float y7i = t12i + t6i;
/* 1802 */         zj0[i1] = t9r + t10r;
/* 1803 */         zj0[i1 + 1] = t9i + t10i;
/* 1804 */         zj1[i1] = y1r - y7i;
/* 1805 */         zj1[i1 + 1] = y1i + y7r;
/* 1806 */         zj2[i1] = y2r - y6i;
/* 1807 */         zj2[i1 + 1] = y2i + y6r;
/* 1808 */         zj3[i1] = y3r - y5i;
/* 1809 */         zj3[i1 + 1] = y3i + y5r;
/* 1810 */         zj4[i1] = t9r - t10r;
/* 1811 */         zj4[i1 + 1] = t9i - t10i;
/* 1812 */         zj5[i1] = y3r + y5i;
/* 1813 */         zj5[i1 + 1] = y3i - y5r;
/* 1814 */         zj6[i1] = y2r + y6i;
/* 1815 */         zj6[i1 + 1] = y2i - y6r;
/* 1816 */         zj7[i1] = y1r + y7i;
/* 1817 */         zj7[i1 + 1] = y1i - y7r;
/*      */       } 
/* 1819 */       int jt = j7 + 1;
/* 1820 */       j7 = j6 + 1;
/* 1821 */       j6 = j5 + 1;
/* 1822 */       j5 = j4 + 1;
/* 1823 */       j4 = j3 + 1;
/* 1824 */       j3 = j2 + 1;
/* 1825 */       j2 = j1 + 1;
/* 1826 */       j1 = j0 + 1;
/* 1827 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa9a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8) {
/*      */     float c1, c2, c3, c4, c5;
/* 1833 */     int m1 = 2 * n1;
/*      */     
/* 1835 */     if (mu == 1) {
/* 1836 */       c1 = 0.8660254F;
/* 1837 */       c2 = 0.76604444F;
/* 1838 */       c3 = 0.64278764F;
/* 1839 */       c4 = 0.17364818F;
/* 1840 */       c5 = 0.9848077F;
/* 1841 */     } else if (mu == 2) {
/* 1842 */       c1 = -0.8660254F;
/* 1843 */       c2 = 0.17364818F;
/* 1844 */       c3 = 0.9848077F;
/* 1845 */       c4 = -0.9396926F;
/* 1846 */       c5 = 0.34202015F;
/* 1847 */     } else if (mu == 4) {
/* 1848 */       c1 = 0.8660254F;
/* 1849 */       c2 = -0.9396926F;
/* 1850 */       c3 = 0.34202015F;
/* 1851 */       c4 = 0.76604444F;
/* 1852 */       c5 = -0.64278764F;
/* 1853 */     } else if (mu == 5) {
/* 1854 */       c1 = -0.8660254F;
/* 1855 */       c2 = -0.9396926F;
/* 1856 */       c3 = -0.34202015F;
/* 1857 */       c4 = 0.76604444F;
/* 1858 */       c5 = 0.64278764F;
/* 1859 */     } else if (mu == 7) {
/* 1860 */       c1 = 0.8660254F;
/* 1861 */       c2 = 0.17364818F;
/* 1862 */       c3 = -0.9848077F;
/* 1863 */       c4 = -0.9396926F;
/* 1864 */       c5 = -0.34202015F;
/*      */     } else {
/* 1866 */       c1 = -0.8660254F;
/* 1867 */       c2 = 0.76604444F;
/* 1868 */       c3 = -0.64278764F;
/* 1869 */       c4 = 0.17364818F;
/* 1870 */       c5 = -0.9848077F;
/*      */     } 
/* 1872 */     float c6 = c1 * c2;
/* 1873 */     float c7 = c1 * c3;
/* 1874 */     float c8 = c1 * c4;
/* 1875 */     float c9 = c1 * c5;
/* 1876 */     for (int i = 0; i < m; i++) {
/* 1877 */       float[] zj0 = z[j0];
/* 1878 */       float[] zj1 = z[j1];
/* 1879 */       float[] zj2 = z[j2];
/* 1880 */       float[] zj3 = z[j3];
/* 1881 */       float[] zj4 = z[j4];
/* 1882 */       float[] zj5 = z[j5];
/* 1883 */       float[] zj6 = z[j6];
/* 1884 */       float[] zj7 = z[j7];
/* 1885 */       float[] zj8 = z[j8];
/* 1886 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 1887 */         float t1r = zj3[i1] + zj6[i1];
/* 1888 */         float t1i = zj3[i1 + 1] + zj6[i1 + 1];
/* 1889 */         float t2r = zj0[i1] - 0.5F * t1r;
/* 1890 */         float t2i = zj0[i1 + 1] - 0.5F * t1i;
/* 1891 */         float t3r = c1 * (zj3[i1] - zj6[i1]);
/* 1892 */         float t3i = c1 * (zj3[i1 + 1] - zj6[i1 + 1]);
/* 1893 */         float t4r = zj0[i1] + t1r;
/* 1894 */         float t4i = zj0[i1 + 1] + t1i;
/* 1895 */         float t5r = zj4[i1] + zj7[i1];
/* 1896 */         float t5i = zj4[i1 + 1] + zj7[i1 + 1];
/* 1897 */         float t6r = zj1[i1] - 0.5F * t5r;
/* 1898 */         float t6i = zj1[i1 + 1] - 0.5F * t5i;
/* 1899 */         float t7r = zj4[i1] - zj7[i1];
/* 1900 */         float t7i = zj4[i1 + 1] - zj7[i1 + 1];
/* 1901 */         float t8r = zj1[i1] + t5r;
/* 1902 */         float t8i = zj1[i1 + 1] + t5i;
/* 1903 */         float t9r = zj2[i1] + zj5[i1];
/* 1904 */         float t9i = zj2[i1 + 1] + zj5[i1 + 1];
/* 1905 */         float t10r = zj8[i1] - 0.5F * t9r;
/* 1906 */         float t10i = zj8[i1 + 1] - 0.5F * t9i;
/* 1907 */         float t11r = zj2[i1] - zj5[i1];
/* 1908 */         float t11i = zj2[i1 + 1] - zj5[i1 + 1];
/* 1909 */         float t12r = zj8[i1] + t9r;
/* 1910 */         float t12i = zj8[i1 + 1] + t9i;
/* 1911 */         float t13r = t8r + t12r;
/* 1912 */         float t13i = t8i + t12i;
/* 1913 */         float t14r = t6r + t10r;
/* 1914 */         float t14i = t6i + t10i;
/* 1915 */         float t15r = t6r - t10r;
/* 1916 */         float t15i = t6i - t10i;
/* 1917 */         float t16r = t7r + t11r;
/* 1918 */         float t16i = t7i + t11i;
/* 1919 */         float t17r = t7r - t11r;
/* 1920 */         float t17i = t7i - t11i;
/* 1921 */         float t18r = c2 * t14r - c7 * t17r;
/* 1922 */         float t18i = c2 * t14i - c7 * t17i;
/* 1923 */         float t19r = c4 * t14r + c9 * t17r;
/* 1924 */         float t19i = c4 * t14i + c9 * t17i;
/* 1925 */         float t20r = c3 * t15r + c6 * t16r;
/* 1926 */         float t20i = c3 * t15i + c6 * t16i;
/* 1927 */         float t21r = c5 * t15r - c8 * t16r;
/* 1928 */         float t21i = c5 * t15i - c8 * t16i;
/* 1929 */         float t22r = t18r + t19r;
/* 1930 */         float t22i = t18i + t19i;
/* 1931 */         float t23r = t20r - t21r;
/* 1932 */         float t23i = t20i - t21i;
/* 1933 */         float y1r = t2r + t18r;
/* 1934 */         float y1i = t2i + t18i;
/* 1935 */         float y2r = t2r + t19r;
/* 1936 */         float y2i = t2i + t19i;
/* 1937 */         float y3r = t4r - 0.5F * t13r;
/* 1938 */         float y3i = t4i - 0.5F * t13i;
/* 1939 */         float y4r = t2r - t22r;
/* 1940 */         float y4i = t2i - t22i;
/* 1941 */         float y5r = t3r - t23r;
/* 1942 */         float y5i = t3i - t23i;
/* 1943 */         float y6r = c1 * (t8r - t12r);
/* 1944 */         float y6i = c1 * (t8i - t12i);
/* 1945 */         float y7r = t21r - t3r;
/* 1946 */         float y7i = t21i - t3i;
/* 1947 */         float y8r = t3r + t20r;
/* 1948 */         float y8i = t3i + t20i;
/* 1949 */         zj0[i1] = t4r + t13r;
/* 1950 */         zj0[i1 + 1] = t4i + t13i;
/* 1951 */         zj1[i1] = y1r - y8i;
/* 1952 */         zj1[i1 + 1] = y1i + y8r;
/* 1953 */         zj2[i1] = y2r - y7i;
/* 1954 */         zj2[i1 + 1] = y2i + y7r;
/* 1955 */         zj3[i1] = y3r - y6i;
/* 1956 */         zj3[i1 + 1] = y3i + y6r;
/* 1957 */         zj4[i1] = y4r - y5i;
/* 1958 */         zj4[i1 + 1] = y4i + y5r;
/* 1959 */         zj5[i1] = y4r + y5i;
/* 1960 */         zj5[i1 + 1] = y4i - y5r;
/* 1961 */         zj6[i1] = y3r + y6i;
/* 1962 */         zj6[i1 + 1] = y3i - y6r;
/* 1963 */         zj7[i1] = y2r + y7i;
/* 1964 */         zj7[i1 + 1] = y2i - y7r;
/* 1965 */         zj8[i1] = y1r + y8i;
/* 1966 */         zj8[i1 + 1] = y1i - y8r;
/*      */       } 
/* 1968 */       int jt = j8 + 1;
/* 1969 */       j8 = j7 + 1;
/* 1970 */       j7 = j6 + 1;
/* 1971 */       j6 = j5 + 1;
/* 1972 */       j5 = j4 + 1;
/* 1973 */       j4 = j3 + 1;
/* 1974 */       j3 = j2 + 1;
/* 1975 */       j2 = j1 + 1;
/* 1976 */       j1 = j0 + 1;
/* 1977 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa11a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10) {
/*      */     float c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
/* 1984 */     int m1 = 2 * n1;
/*      */     
/* 1986 */     if (mu == 1) {
/* 1987 */       c1 = 0.8412535F;
/* 1988 */       c2 = 0.41541502F;
/* 1989 */       c3 = -0.14231484F;
/* 1990 */       c4 = -0.65486073F;
/* 1991 */       c5 = -0.959493F;
/* 1992 */       c6 = 0.54064083F;
/* 1993 */       c7 = 0.90963197F;
/* 1994 */       c8 = 0.98982143F;
/* 1995 */       c9 = 0.7557496F;
/* 1996 */       c10 = 0.28173256F;
/* 1997 */     } else if (mu == 2) {
/* 1998 */       c1 = 0.41541502F;
/* 1999 */       c2 = -0.65486073F;
/* 2000 */       c3 = -0.959493F;
/* 2001 */       c4 = -0.14231484F;
/* 2002 */       c5 = 0.8412535F;
/* 2003 */       c6 = 0.90963197F;
/* 2004 */       c7 = 0.7557496F;
/* 2005 */       c8 = -0.28173256F;
/* 2006 */       c9 = -0.98982143F;
/* 2007 */       c10 = -0.54064083F;
/* 2008 */     } else if (mu == 3) {
/* 2009 */       c1 = -0.14231484F;
/* 2010 */       c2 = -0.959493F;
/* 2011 */       c3 = 0.41541502F;
/* 2012 */       c4 = 0.8412535F;
/* 2013 */       c5 = -0.65486073F;
/* 2014 */       c6 = 0.98982143F;
/* 2015 */       c7 = -0.28173256F;
/* 2016 */       c8 = -0.90963197F;
/* 2017 */       c9 = 0.54064083F;
/* 2018 */       c10 = 0.7557496F;
/* 2019 */     } else if (mu == 4) {
/* 2020 */       c1 = -0.65486073F;
/* 2021 */       c2 = -0.14231484F;
/* 2022 */       c3 = 0.8412535F;
/* 2023 */       c4 = -0.959493F;
/* 2024 */       c5 = 0.41541502F;
/* 2025 */       c6 = 0.7557496F;
/* 2026 */       c7 = -0.98982143F;
/* 2027 */       c8 = 0.54064083F;
/* 2028 */       c9 = 0.28173256F;
/* 2029 */       c10 = -0.90963197F;
/* 2030 */     } else if (mu == 5) {
/* 2031 */       c1 = -0.959493F;
/* 2032 */       c2 = 0.8412535F;
/* 2033 */       c3 = -0.65486073F;
/* 2034 */       c4 = 0.41541502F;
/* 2035 */       c5 = -0.14231484F;
/* 2036 */       c6 = 0.28173256F;
/* 2037 */       c7 = -0.54064083F;
/* 2038 */       c8 = 0.7557496F;
/* 2039 */       c9 = -0.90963197F;
/* 2040 */       c10 = 0.98982143F;
/* 2041 */     } else if (mu == 6) {
/* 2042 */       c1 = -0.959493F;
/* 2043 */       c2 = 0.8412535F;
/* 2044 */       c3 = -0.65486073F;
/* 2045 */       c4 = 0.41541502F;
/* 2046 */       c5 = -0.14231484F;
/* 2047 */       c6 = -0.28173256F;
/* 2048 */       c7 = 0.54064083F;
/* 2049 */       c8 = -0.7557496F;
/* 2050 */       c9 = 0.90963197F;
/* 2051 */       c10 = -0.98982143F;
/* 2052 */     } else if (mu == 7) {
/* 2053 */       c1 = -0.65486073F;
/* 2054 */       c2 = -0.14231484F;
/* 2055 */       c3 = 0.8412535F;
/* 2056 */       c4 = -0.959493F;
/* 2057 */       c5 = 0.41541502F;
/* 2058 */       c6 = -0.7557496F;
/* 2059 */       c7 = 0.98982143F;
/* 2060 */       c8 = -0.54064083F;
/* 2061 */       c9 = -0.28173256F;
/* 2062 */       c10 = 0.90963197F;
/* 2063 */     } else if (mu == 8) {
/* 2064 */       c1 = -0.14231484F;
/* 2065 */       c2 = -0.959493F;
/* 2066 */       c3 = 0.41541502F;
/* 2067 */       c4 = 0.8412535F;
/* 2068 */       c5 = -0.65486073F;
/* 2069 */       c6 = -0.98982143F;
/* 2070 */       c7 = 0.28173256F;
/* 2071 */       c8 = 0.90963197F;
/* 2072 */       c9 = -0.54064083F;
/* 2073 */       c10 = -0.7557496F;
/* 2074 */     } else if (mu == 9) {
/* 2075 */       c1 = 0.41541502F;
/* 2076 */       c2 = -0.65486073F;
/* 2077 */       c3 = -0.959493F;
/* 2078 */       c4 = -0.14231484F;
/* 2079 */       c5 = 0.8412535F;
/* 2080 */       c6 = -0.90963197F;
/* 2081 */       c7 = -0.7557496F;
/* 2082 */       c8 = 0.28173256F;
/* 2083 */       c9 = 0.98982143F;
/* 2084 */       c10 = 0.54064083F;
/*      */     } else {
/* 2086 */       c1 = 0.8412535F;
/* 2087 */       c2 = 0.41541502F;
/* 2088 */       c3 = -0.14231484F;
/* 2089 */       c4 = -0.65486073F;
/* 2090 */       c5 = -0.959493F;
/* 2091 */       c6 = -0.54064083F;
/* 2092 */       c7 = -0.90963197F;
/* 2093 */       c8 = -0.98982143F;
/* 2094 */       c9 = -0.7557496F;
/* 2095 */       c10 = -0.28173256F;
/*      */     } 
/* 2097 */     for (int i = 0; i < m; i++) {
/* 2098 */       float[] zj0 = z[j0];
/* 2099 */       float[] zj1 = z[j1];
/* 2100 */       float[] zj2 = z[j2];
/* 2101 */       float[] zj3 = z[j3];
/* 2102 */       float[] zj4 = z[j4];
/* 2103 */       float[] zj5 = z[j5];
/* 2104 */       float[] zj6 = z[j6];
/* 2105 */       float[] zj7 = z[j7];
/* 2106 */       float[] zj8 = z[j8];
/* 2107 */       float[] zj9 = z[j9];
/* 2108 */       float[] zj10 = z[j10];
/* 2109 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 2110 */         float t1r = zj1[i1] + zj10[i1];
/* 2111 */         float t1i = zj1[i1 + 1] + zj10[i1 + 1];
/* 2112 */         float t2r = zj2[i1] + zj9[i1];
/* 2113 */         float t2i = zj2[i1 + 1] + zj9[i1 + 1];
/* 2114 */         float t3r = zj3[i1] + zj8[i1];
/* 2115 */         float t3i = zj3[i1 + 1] + zj8[i1 + 1];
/* 2116 */         float t4r = zj4[i1] + zj7[i1];
/* 2117 */         float t4i = zj4[i1 + 1] + zj7[i1 + 1];
/* 2118 */         float t5r = zj5[i1] + zj6[i1];
/* 2119 */         float t5i = zj5[i1 + 1] + zj6[i1 + 1];
/* 2120 */         float t6r = zj1[i1] - zj10[i1];
/* 2121 */         float t6i = zj1[i1 + 1] - zj10[i1 + 1];
/* 2122 */         float t7r = zj2[i1] - zj9[i1];
/* 2123 */         float t7i = zj2[i1 + 1] - zj9[i1 + 1];
/* 2124 */         float t8r = zj3[i1] - zj8[i1];
/* 2125 */         float t8i = zj3[i1 + 1] - zj8[i1 + 1];
/* 2126 */         float t9r = zj4[i1] - zj7[i1];
/* 2127 */         float t9i = zj4[i1 + 1] - zj7[i1 + 1];
/* 2128 */         float t10r = zj5[i1] - zj6[i1];
/* 2129 */         float t10i = zj5[i1 + 1] - zj6[i1 + 1];
/* 2130 */         float t11r = zj0[i1] - 0.5F * t5r;
/* 2131 */         float t11i = zj0[i1 + 1] - 0.5F * t5i;
/* 2132 */         float t12r = t1r - t5r;
/* 2133 */         float t12i = t1i - t5i;
/* 2134 */         float t13r = t2r - t5r;
/* 2135 */         float t13i = t2i - t5i;
/* 2136 */         float t14r = t3r - t5r;
/* 2137 */         float t14i = t3i - t5i;
/* 2138 */         float t15r = t4r - t5r;
/* 2139 */         float t15i = t4i - t5i;
/* 2140 */         float y1r = t11r + c1 * t12r + c2 * t13r + c3 * t14r + c4 * t15r;
/* 2141 */         float y1i = t11i + c1 * t12i + c2 * t13i + c3 * t14i + c4 * t15i;
/* 2142 */         float y2r = t11r + c2 * t12r + c4 * t13r + c5 * t14r + c3 * t15r;
/* 2143 */         float y2i = t11i + c2 * t12i + c4 * t13i + c5 * t14i + c3 * t15i;
/* 2144 */         float y3r = t11r + c3 * t12r + c5 * t13r + c2 * t14r + c1 * t15r;
/* 2145 */         float y3i = t11i + c3 * t12i + c5 * t13i + c2 * t14i + c1 * t15i;
/* 2146 */         float y4r = t11r + c4 * t12r + c3 * t13r + c1 * t14r + c5 * t15r;
/* 2147 */         float y4i = t11i + c4 * t12i + c3 * t13i + c1 * t14i + c5 * t15i;
/* 2148 */         float y5r = t11r + c5 * t12r + c1 * t13r + c4 * t14r + c2 * t15r;
/* 2149 */         float y5i = t11i + c5 * t12i + c1 * t13i + c4 * t14i + c2 * t15i;
/* 2150 */         float y6r = c10 * t6r - c6 * t7r + c9 * t8r - c7 * t9r + c8 * t10r;
/* 2151 */         float y6i = c10 * t6i - c6 * t7i + c9 * t8i - c7 * t9i + c8 * t10i;
/* 2152 */         float y7r = c9 * t6r - c8 * t7r + c6 * t8r + c10 * t9r - c7 * t10r;
/* 2153 */         float y7i = c9 * t6i - c8 * t7i + c6 * t8i + c10 * t9i - c7 * t10i;
/* 2154 */         float y8r = c8 * t6r - c10 * t7r - c7 * t8r + c6 * t9r + c9 * t10r;
/* 2155 */         float y8i = c8 * t6i - c10 * t7i - c7 * t8i + c6 * t9i + c9 * t10i;
/* 2156 */         float y9r = c7 * t6r + c9 * t7r - c10 * t8r - c8 * t9r - c6 * t10r;
/* 2157 */         float y9i = c7 * t6i + c9 * t7i - c10 * t8i - c8 * t9i - c6 * t10i;
/* 2158 */         float y10r = c6 * t6r + c7 * t7r + c8 * t8r + c9 * t9r + c10 * t10r;
/* 2159 */         float y10i = c6 * t6i + c7 * t7i + c8 * t8i + c9 * t9i + c10 * t10i;
/* 2160 */         zj0[i1] = zj0[i1] + t1r + t2r + t3r + t4r + t5r;
/* 2161 */         zj0[i1 + 1] = zj0[i1 + 1] + t1i + t2i + t3i + t4i + t5i;
/* 2162 */         zj1[i1] = y1r - y10i;
/* 2163 */         zj1[i1 + 1] = y1i + y10r;
/* 2164 */         zj2[i1] = y2r - y9i;
/* 2165 */         zj2[i1 + 1] = y2i + y9r;
/* 2166 */         zj3[i1] = y3r - y8i;
/* 2167 */         zj3[i1 + 1] = y3i + y8r;
/* 2168 */         zj4[i1] = y4r - y7i;
/* 2169 */         zj4[i1 + 1] = y4i + y7r;
/* 2170 */         zj5[i1] = y5r - y6i;
/* 2171 */         zj5[i1 + 1] = y5i + y6r;
/* 2172 */         zj6[i1] = y5r + y6i;
/* 2173 */         zj6[i1 + 1] = y5i - y6r;
/* 2174 */         zj7[i1] = y4r + y7i;
/* 2175 */         zj7[i1 + 1] = y4i - y7r;
/* 2176 */         zj8[i1] = y3r + y8i;
/* 2177 */         zj8[i1 + 1] = y3i - y8r;
/* 2178 */         zj9[i1] = y2r + y9i;
/* 2179 */         zj9[i1 + 1] = y2i - y9r;
/* 2180 */         zj10[i1] = y1r + y10i;
/* 2181 */         zj10[i1 + 1] = y1i - y10r;
/*      */       } 
/* 2183 */       int jt = j10 + 1;
/* 2184 */       j10 = j9 + 1;
/* 2185 */       j9 = j8 + 1;
/* 2186 */       j8 = j7 + 1;
/* 2187 */       j7 = j6 + 1;
/* 2188 */       j6 = j5 + 1;
/* 2189 */       j5 = j4 + 1;
/* 2190 */       j4 = j3 + 1;
/* 2191 */       j3 = j2 + 1;
/* 2192 */       j2 = j1 + 1;
/* 2193 */       j1 = j0 + 1;
/* 2194 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa13a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10, int j11, int j12) {
/*      */     float c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
/* 2201 */     int m1 = 2 * n1;
/*      */     
/* 2203 */     if (mu == 1) {
/* 2204 */       c1 = 0.885456F;
/* 2205 */       c2 = 0.56806475F;
/* 2206 */       c3 = 0.12053668F;
/* 2207 */       c4 = -0.3546049F;
/* 2208 */       c5 = -0.7485107F;
/* 2209 */       c6 = -0.97094184F;
/* 2210 */       c7 = 0.46472317F;
/* 2211 */       c8 = 0.82298386F;
/* 2212 */       c9 = 0.99270886F;
/* 2213 */       c10 = 0.9350162F;
/* 2214 */       c11 = 0.66312265F;
/* 2215 */       c12 = 0.23931566F;
/* 2216 */     } else if (mu == 2) {
/* 2217 */       c1 = 0.56806475F;
/* 2218 */       c2 = -0.3546049F;
/* 2219 */       c3 = -0.97094184F;
/* 2220 */       c4 = -0.7485107F;
/* 2221 */       c5 = 0.12053668F;
/* 2222 */       c6 = 0.885456F;
/* 2223 */       c7 = 0.82298386F;
/* 2224 */       c8 = 0.9350162F;
/* 2225 */       c9 = 0.23931566F;
/* 2226 */       c10 = -0.66312265F;
/* 2227 */       c11 = -0.99270886F;
/* 2228 */       c12 = -0.46472317F;
/* 2229 */     } else if (mu == 3) {
/* 2230 */       c1 = 0.12053668F;
/* 2231 */       c2 = -0.97094184F;
/* 2232 */       c3 = -0.3546049F;
/* 2233 */       c4 = 0.885456F;
/* 2234 */       c5 = 0.56806475F;
/* 2235 */       c6 = -0.7485107F;
/* 2236 */       c7 = 0.99270886F;
/* 2237 */       c8 = 0.23931566F;
/* 2238 */       c9 = -0.9350162F;
/* 2239 */       c10 = -0.46472317F;
/* 2240 */       c11 = 0.82298386F;
/* 2241 */       c12 = 0.66312265F;
/* 2242 */     } else if (mu == 4) {
/* 2243 */       c1 = -0.3546049F;
/* 2244 */       c2 = -0.7485107F;
/* 2245 */       c3 = 0.885456F;
/* 2246 */       c4 = 0.12053668F;
/* 2247 */       c5 = -0.97094184F;
/* 2248 */       c6 = 0.56806475F;
/* 2249 */       c7 = 0.9350162F;
/* 2250 */       c8 = -0.66312265F;
/* 2251 */       c9 = -0.46472317F;
/* 2252 */       c10 = 0.99270886F;
/* 2253 */       c11 = -0.23931566F;
/* 2254 */       c12 = -0.82298386F;
/* 2255 */     } else if (mu == 5) {
/* 2256 */       c1 = -0.7485107F;
/* 2257 */       c2 = 0.12053668F;
/* 2258 */       c3 = 0.56806475F;
/* 2259 */       c4 = -0.97094184F;
/* 2260 */       c5 = 0.885456F;
/* 2261 */       c6 = -0.3546049F;
/* 2262 */       c7 = 0.66312265F;
/* 2263 */       c8 = -0.99270886F;
/* 2264 */       c9 = 0.82298386F;
/* 2265 */       c10 = -0.23931566F;
/* 2266 */       c11 = -0.46472317F;
/* 2267 */       c12 = 0.9350162F;
/* 2268 */     } else if (mu == 6) {
/* 2269 */       c1 = -0.97094184F;
/* 2270 */       c2 = 0.885456F;
/* 2271 */       c3 = -0.7485107F;
/* 2272 */       c4 = 0.56806475F;
/* 2273 */       c5 = -0.3546049F;
/* 2274 */       c6 = 0.12053668F;
/* 2275 */       c7 = 0.23931566F;
/* 2276 */       c8 = -0.46472317F;
/* 2277 */       c9 = 0.66312265F;
/* 2278 */       c10 = -0.82298386F;
/* 2279 */       c11 = 0.9350162F;
/* 2280 */       c12 = -0.99270886F;
/* 2281 */     } else if (mu == 7) {
/* 2282 */       c1 = -0.97094184F;
/* 2283 */       c2 = 0.885456F;
/* 2284 */       c3 = -0.7485107F;
/* 2285 */       c4 = 0.56806475F;
/* 2286 */       c5 = -0.3546049F;
/* 2287 */       c6 = 0.12053668F;
/* 2288 */       c7 = -0.23931566F;
/* 2289 */       c8 = 0.46472317F;
/* 2290 */       c9 = -0.66312265F;
/* 2291 */       c10 = 0.82298386F;
/* 2292 */       c11 = -0.9350162F;
/* 2293 */       c12 = 0.99270886F;
/* 2294 */     } else if (mu == 8) {
/* 2295 */       c1 = -0.7485107F;
/* 2296 */       c2 = 0.12053668F;
/* 2297 */       c3 = 0.56806475F;
/* 2298 */       c4 = -0.97094184F;
/* 2299 */       c5 = 0.885456F;
/* 2300 */       c6 = -0.3546049F;
/* 2301 */       c7 = -0.66312265F;
/* 2302 */       c8 = 0.99270886F;
/* 2303 */       c9 = -0.82298386F;
/* 2304 */       c10 = 0.23931566F;
/* 2305 */       c11 = 0.46472317F;
/* 2306 */       c12 = -0.9350162F;
/* 2307 */     } else if (mu == 9) {
/* 2308 */       c1 = -0.3546049F;
/* 2309 */       c2 = -0.7485107F;
/* 2310 */       c3 = 0.885456F;
/* 2311 */       c4 = 0.12053668F;
/* 2312 */       c5 = -0.97094184F;
/* 2313 */       c6 = 0.56806475F;
/* 2314 */       c7 = -0.9350162F;
/* 2315 */       c8 = 0.66312265F;
/* 2316 */       c9 = 0.46472317F;
/* 2317 */       c10 = -0.99270886F;
/* 2318 */       c11 = 0.23931566F;
/* 2319 */       c12 = 0.82298386F;
/* 2320 */     } else if (mu == 10) {
/* 2321 */       c1 = 0.12053668F;
/* 2322 */       c2 = -0.97094184F;
/* 2323 */       c3 = -0.3546049F;
/* 2324 */       c4 = 0.885456F;
/* 2325 */       c5 = 0.56806475F;
/* 2326 */       c6 = -0.7485107F;
/* 2327 */       c7 = -0.99270886F;
/* 2328 */       c8 = -0.23931566F;
/* 2329 */       c9 = 0.9350162F;
/* 2330 */       c10 = 0.46472317F;
/* 2331 */       c11 = -0.82298386F;
/* 2332 */       c12 = -0.66312265F;
/* 2333 */     } else if (mu == 11) {
/* 2334 */       c1 = 0.56806475F;
/* 2335 */       c2 = -0.3546049F;
/* 2336 */       c3 = -0.97094184F;
/* 2337 */       c4 = -0.7485107F;
/* 2338 */       c5 = 0.12053668F;
/* 2339 */       c6 = 0.885456F;
/* 2340 */       c7 = -0.82298386F;
/* 2341 */       c8 = -0.9350162F;
/* 2342 */       c9 = -0.23931566F;
/* 2343 */       c10 = 0.66312265F;
/* 2344 */       c11 = 0.99270886F;
/* 2345 */       c12 = 0.46472317F;
/*      */     } else {
/* 2347 */       c1 = 0.885456F;
/* 2348 */       c2 = 0.56806475F;
/* 2349 */       c3 = 0.12053668F;
/* 2350 */       c4 = -0.3546049F;
/* 2351 */       c5 = -0.7485107F;
/* 2352 */       c6 = -0.97094184F;
/* 2353 */       c7 = -0.46472317F;
/* 2354 */       c8 = -0.82298386F;
/* 2355 */       c9 = -0.99270886F;
/* 2356 */       c10 = -0.9350162F;
/* 2357 */       c11 = -0.66312265F;
/* 2358 */       c12 = -0.23931566F;
/*      */     } 
/* 2360 */     for (int i = 0; i < m; i++) {
/* 2361 */       float[] zj0 = z[j0];
/* 2362 */       float[] zj1 = z[j1];
/* 2363 */       float[] zj2 = z[j2];
/* 2364 */       float[] zj3 = z[j3];
/* 2365 */       float[] zj4 = z[j4];
/* 2366 */       float[] zj5 = z[j5];
/* 2367 */       float[] zj6 = z[j6];
/* 2368 */       float[] zj7 = z[j7];
/* 2369 */       float[] zj8 = z[j8];
/* 2370 */       float[] zj9 = z[j9];
/* 2371 */       float[] zj10 = z[j10];
/* 2372 */       float[] zj11 = z[j11];
/* 2373 */       float[] zj12 = z[j12];
/* 2374 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 2375 */         float t1r = zj1[i1] + zj12[i1];
/* 2376 */         float t1i = zj1[i1 + 1] + zj12[i1 + 1];
/* 2377 */         float t2r = zj2[i1] + zj11[i1];
/* 2378 */         float t2i = zj2[i1 + 1] + zj11[i1 + 1];
/* 2379 */         float t3r = zj3[i1] + zj10[i1];
/* 2380 */         float t3i = zj3[i1 + 1] + zj10[i1 + 1];
/* 2381 */         float t4r = zj4[i1] + zj9[i1];
/* 2382 */         float t4i = zj4[i1 + 1] + zj9[i1 + 1];
/* 2383 */         float t5r = zj5[i1] + zj8[i1];
/* 2384 */         float t5i = zj5[i1 + 1] + zj8[i1 + 1];
/* 2385 */         float t6r = zj6[i1] + zj7[i1];
/* 2386 */         float t6i = zj6[i1 + 1] + zj7[i1 + 1];
/* 2387 */         float t7r = zj1[i1] - zj12[i1];
/* 2388 */         float t7i = zj1[i1 + 1] - zj12[i1 + 1];
/* 2389 */         float t8r = zj2[i1] - zj11[i1];
/* 2390 */         float t8i = zj2[i1 + 1] - zj11[i1 + 1];
/* 2391 */         float t9r = zj3[i1] - zj10[i1];
/* 2392 */         float t9i = zj3[i1 + 1] - zj10[i1 + 1];
/* 2393 */         float t10r = zj4[i1] - zj9[i1];
/* 2394 */         float t10i = zj4[i1 + 1] - zj9[i1 + 1];
/* 2395 */         float t11r = zj5[i1] - zj8[i1];
/* 2396 */         float t11i = zj5[i1 + 1] - zj8[i1 + 1];
/* 2397 */         float t12r = zj6[i1] - zj7[i1];
/* 2398 */         float t12i = zj6[i1 + 1] - zj7[i1 + 1];
/* 2399 */         float t13r = zj0[i1] - 0.5F * t6r;
/* 2400 */         float t13i = zj0[i1 + 1] - 0.5F * t6i;
/* 2401 */         float t14r = t1r - t6r;
/* 2402 */         float t14i = t1i - t6i;
/* 2403 */         float t15r = t2r - t6r;
/* 2404 */         float t15i = t2i - t6i;
/* 2405 */         float t16r = t3r - t6r;
/* 2406 */         float t16i = t3i - t6i;
/* 2407 */         float t17r = t4r - t6r;
/* 2408 */         float t17i = t4i - t6i;
/* 2409 */         float t18r = t5r - t6r;
/* 2410 */         float t18i = t5i - t6i;
/* 2411 */         float y1r = t13r + c1 * t14r + c2 * t15r + c3 * t16r + c4 * t17r + c5 * t18r;
/* 2412 */         float y1i = t13i + c1 * t14i + c2 * t15i + c3 * t16i + c4 * t17i + c5 * t18i;
/* 2413 */         float y2r = t13r + c2 * t14r + c4 * t15r + c6 * t16r + c5 * t17r + c3 * t18r;
/* 2414 */         float y2i = t13i + c2 * t14i + c4 * t15i + c6 * t16i + c5 * t17i + c3 * t18i;
/* 2415 */         float y3r = t13r + c3 * t14r + c6 * t15r + c4 * t16r + c1 * t17r + c2 * t18r;
/* 2416 */         float y3i = t13i + c3 * t14i + c6 * t15i + c4 * t16i + c1 * t17i + c2 * t18i;
/* 2417 */         float y4r = t13r + c4 * t14r + c5 * t15r + c1 * t16r + c3 * t17r + c6 * t18r;
/* 2418 */         float y4i = t13i + c4 * t14i + c5 * t15i + c1 * t16i + c3 * t17i + c6 * t18i;
/* 2419 */         float y5r = t13r + c5 * t14r + c3 * t15r + c2 * t16r + c6 * t17r + c1 * t18r;
/* 2420 */         float y5i = t13i + c5 * t14i + c3 * t15i + c2 * t16i + c6 * t17i + c1 * t18i;
/* 2421 */         float y6r = t13r + c6 * t14r + c1 * t15r + c5 * t16r + c2 * t17r + c4 * t18r;
/* 2422 */         float y6i = t13i + c6 * t14i + c1 * t15i + c5 * t16i + c2 * t17i + c4 * t18i;
/* 2423 */         float y7r = c12 * t7r - c7 * t8r + c11 * t9r - c8 * t10r + c10 * t11r - c9 * t12r;
/* 2424 */         float y7i = c12 * t7i - c7 * t8i + c11 * t9i - c8 * t10i + c10 * t11i - c9 * t12i;
/* 2425 */         float y8r = c11 * t7r - c9 * t8r + c8 * t9r - c12 * t10r - c7 * t11r + c10 * t12r;
/* 2426 */         float y8i = c11 * t7i - c9 * t8i + c8 * t9i - c12 * t10i - c7 * t11i + c10 * t12i;
/* 2427 */         float y9r = c10 * t7r - c11 * t8r - c7 * t9r + c9 * t10r - c12 * t11r - c8 * t12r;
/* 2428 */         float y9i = c10 * t7i - c11 * t8i - c7 * t9i + c9 * t10i - c12 * t11i - c8 * t12i;
/* 2429 */         float y10r = c9 * t7r + c12 * t8r - c10 * t9r - c7 * t10r + c8 * t11r + c11 * t12r;
/* 2430 */         float y10i = c9 * t7i + c12 * t8i - c10 * t9i - c7 * t10i + c8 * t11i + c11 * t12i;
/* 2431 */         float y11r = c8 * t7r + c10 * t8r + c12 * t9r - c11 * t10r - c9 * t11r - c7 * t12r;
/* 2432 */         float y11i = c8 * t7i + c10 * t8i + c12 * t9i - c11 * t10i - c9 * t11i - c7 * t12i;
/* 2433 */         float y12r = c7 * t7r + c8 * t8r + c9 * t9r + c10 * t10r + c11 * t11r + c12 * t12r;
/* 2434 */         float y12i = c7 * t7i + c8 * t8i + c9 * t9i + c10 * t10i + c11 * t11i + c12 * t12i;
/* 2435 */         zj0[i1] = zj0[i1] + t1r + t2r + t3r + t4r + t5r + t6r;
/* 2436 */         zj0[i1 + 1] = zj0[i1 + 1] + t1i + t2i + t3i + t4i + t5i + t6i;
/* 2437 */         zj1[i1] = y1r - y12i;
/* 2438 */         zj1[i1 + 1] = y1i + y12r;
/* 2439 */         zj2[i1] = y2r - y11i;
/* 2440 */         zj2[i1 + 1] = y2i + y11r;
/* 2441 */         zj3[i1] = y3r - y10i;
/* 2442 */         zj3[i1 + 1] = y3i + y10r;
/* 2443 */         zj4[i1] = y4r - y9i;
/* 2444 */         zj4[i1 + 1] = y4i + y9r;
/* 2445 */         zj5[i1] = y5r - y8i;
/* 2446 */         zj5[i1 + 1] = y5i + y8r;
/* 2447 */         zj6[i1] = y6r - y7i;
/* 2448 */         zj6[i1 + 1] = y6i + y7r;
/* 2449 */         zj7[i1] = y6r + y7i;
/* 2450 */         zj7[i1 + 1] = y6i - y7r;
/* 2451 */         zj8[i1] = y5r + y8i;
/* 2452 */         zj8[i1 + 1] = y5i - y8r;
/* 2453 */         zj9[i1] = y4r + y9i;
/* 2454 */         zj9[i1 + 1] = y4i - y9r;
/* 2455 */         zj10[i1] = y3r + y10i;
/* 2456 */         zj10[i1 + 1] = y3i - y10r;
/* 2457 */         zj11[i1] = y2r + y11i;
/* 2458 */         zj11[i1 + 1] = y2i - y11r;
/* 2459 */         zj12[i1] = y1r + y12i;
/* 2460 */         zj12[i1 + 1] = y1i - y12r;
/*      */       } 
/* 2462 */       int jt = j12 + 1;
/* 2463 */       j12 = j11 + 1;
/* 2464 */       j11 = j10 + 1;
/* 2465 */       j10 = j9 + 1;
/* 2466 */       j9 = j8 + 1;
/* 2467 */       j8 = j7 + 1;
/* 2468 */       j7 = j6 + 1;
/* 2469 */       j6 = j5 + 1;
/* 2470 */       j5 = j4 + 1;
/* 2471 */       j4 = j3 + 1;
/* 2472 */       j3 = j2 + 1;
/* 2473 */       j2 = j1 + 1;
/* 2474 */       j1 = j0 + 1;
/* 2475 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa16a(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10, int j11, int j12, int j13, int j14, int j15) {
/*      */     float c1, c2, c3, c4;
/* 2482 */     int m1 = 2 * n1;
/*      */     
/* 2484 */     if (mu == 1) {
/* 2485 */       c1 = 1.0F;
/* 2486 */       c2 = 0.9238795F;
/* 2487 */       c3 = 0.38268343F;
/* 2488 */       c4 = 0.70710677F;
/* 2489 */     } else if (mu == 3) {
/* 2490 */       c1 = -1.0F;
/* 2491 */       c2 = 0.38268343F;
/* 2492 */       c3 = 0.9238795F;
/* 2493 */       c4 = -0.70710677F;
/* 2494 */     } else if (mu == 5) {
/* 2495 */       c1 = 1.0F;
/* 2496 */       c2 = -0.38268343F;
/* 2497 */       c3 = 0.9238795F;
/* 2498 */       c4 = -0.70710677F;
/* 2499 */     } else if (mu == 7) {
/* 2500 */       c1 = -1.0F;
/* 2501 */       c2 = -0.9238795F;
/* 2502 */       c3 = 0.38268343F;
/* 2503 */       c4 = 0.70710677F;
/* 2504 */     } else if (mu == 9) {
/* 2505 */       c1 = 1.0F;
/* 2506 */       c2 = -0.9238795F;
/* 2507 */       c3 = -0.38268343F;
/* 2508 */       c4 = 0.70710677F;
/* 2509 */     } else if (mu == 11) {
/* 2510 */       c1 = -1.0F;
/* 2511 */       c2 = -0.38268343F;
/* 2512 */       c3 = -0.9238795F;
/* 2513 */       c4 = -0.70710677F;
/* 2514 */     } else if (mu == 13) {
/* 2515 */       c1 = 1.0F;
/* 2516 */       c2 = 0.38268343F;
/* 2517 */       c3 = -0.9238795F;
/* 2518 */       c4 = -0.70710677F;
/*      */     } else {
/* 2520 */       c1 = -1.0F;
/* 2521 */       c2 = 0.9238795F;
/* 2522 */       c3 = -0.38268343F;
/* 2523 */       c4 = 0.70710677F;
/*      */     } 
/* 2525 */     float c5 = c1 * c4;
/* 2526 */     float c6 = c1 * c3;
/* 2527 */     float c7 = c1 * c2;
/* 2528 */     for (int i = 0; i < m; i++) {
/* 2529 */       float[] zj0 = z[j0];
/* 2530 */       float[] zj1 = z[j1];
/* 2531 */       float[] zj2 = z[j2];
/* 2532 */       float[] zj3 = z[j3];
/* 2533 */       float[] zj4 = z[j4];
/* 2534 */       float[] zj5 = z[j5];
/* 2535 */       float[] zj6 = z[j6];
/* 2536 */       float[] zj7 = z[j7];
/* 2537 */       float[] zj8 = z[j8];
/* 2538 */       float[] zj9 = z[j9];
/* 2539 */       float[] zj10 = z[j10];
/* 2540 */       float[] zj11 = z[j11];
/* 2541 */       float[] zj12 = z[j12];
/* 2542 */       float[] zj13 = z[j13];
/* 2543 */       float[] zj14 = z[j14];
/* 2544 */       float[] zj15 = z[j15];
/* 2545 */       for (int i1 = 0; i1 < m1; i1 += 2) {
/* 2546 */         float t1r = zj0[i1] + zj8[i1];
/* 2547 */         float t1i = zj0[i1 + 1] + zj8[i1 + 1];
/* 2548 */         float t2r = zj4[i1] + zj12[i1];
/* 2549 */         float t2i = zj4[i1 + 1] + zj12[i1 + 1];
/* 2550 */         float t3r = zj0[i1] - zj8[i1];
/* 2551 */         float t3i = zj0[i1 + 1] - zj8[i1 + 1];
/* 2552 */         float t4r = c1 * (zj4[i1] - zj12[i1]);
/* 2553 */         float t4i = c1 * (zj4[i1 + 1] - zj12[i1 + 1]);
/* 2554 */         float t5r = t1r + t2r;
/* 2555 */         float t5i = t1i + t2i;
/* 2556 */         float t6r = t1r - t2r;
/* 2557 */         float t6i = t1i - t2i;
/* 2558 */         float t7r = zj1[i1] + zj9[i1];
/* 2559 */         float t7i = zj1[i1 + 1] + zj9[i1 + 1];
/* 2560 */         float t8r = zj5[i1] + zj13[i1];
/* 2561 */         float t8i = zj5[i1 + 1] + zj13[i1 + 1];
/* 2562 */         float t9r = zj1[i1] - zj9[i1];
/* 2563 */         float t9i = zj1[i1 + 1] - zj9[i1 + 1];
/* 2564 */         float t10r = zj5[i1] - zj13[i1];
/* 2565 */         float t10i = zj5[i1 + 1] - zj13[i1 + 1];
/* 2566 */         float t11r = t7r + t8r;
/* 2567 */         float t11i = t7i + t8i;
/* 2568 */         float t12r = t7r - t8r;
/* 2569 */         float t12i = t7i - t8i;
/* 2570 */         float t13r = zj2[i1] + zj10[i1];
/* 2571 */         float t13i = zj2[i1 + 1] + zj10[i1 + 1];
/* 2572 */         float t14r = zj6[i1] + zj14[i1];
/* 2573 */         float t14i = zj6[i1 + 1] + zj14[i1 + 1];
/* 2574 */         float t15r = zj2[i1] - zj10[i1];
/* 2575 */         float t15i = zj2[i1 + 1] - zj10[i1 + 1];
/* 2576 */         float t16r = zj6[i1] - zj14[i1];
/* 2577 */         float t16i = zj6[i1 + 1] - zj14[i1 + 1];
/* 2578 */         float t17r = t13r + t14r;
/* 2579 */         float t17i = t13i + t14i;
/* 2580 */         float t18r = c4 * (t15r - t16r);
/* 2581 */         float t18i = c4 * (t15i - t16i);
/* 2582 */         float t19r = c5 * (t15r + t16r);
/* 2583 */         float t19i = c5 * (t15i + t16i);
/* 2584 */         float t20r = c1 * (t13r - t14r);
/* 2585 */         float t20i = c1 * (t13i - t14i);
/* 2586 */         float t21r = zj3[i1] + zj11[i1];
/* 2587 */         float t21i = zj3[i1 + 1] + zj11[i1 + 1];
/* 2588 */         float t22r = zj7[i1] + zj15[i1];
/* 2589 */         float t22i = zj7[i1 + 1] + zj15[i1 + 1];
/* 2590 */         float t23r = zj3[i1] - zj11[i1];
/* 2591 */         float t23i = zj3[i1 + 1] - zj11[i1 + 1];
/* 2592 */         float t24r = zj7[i1] - zj15[i1];
/* 2593 */         float t24i = zj7[i1 + 1] - zj15[i1 + 1];
/* 2594 */         float t25r = t21r + t22r;
/* 2595 */         float t25i = t21i + t22i;
/* 2596 */         float t26r = t21r - t22r;
/* 2597 */         float t26i = t21i - t22i;
/* 2598 */         float t27r = t9r + t24r;
/* 2599 */         float t27i = t9i + t24i;
/* 2600 */         float t28r = t10r + t23r;
/* 2601 */         float t28i = t10i + t23i;
/* 2602 */         float t29r = t9r - t24r;
/* 2603 */         float t29i = t9i - t24i;
/* 2604 */         float t30r = t10r - t23r;
/* 2605 */         float t30i = t10i - t23i;
/* 2606 */         float t31r = t5r + t17r;
/* 2607 */         float t31i = t5i + t17i;
/* 2608 */         float t32r = t11r + t25r;
/* 2609 */         float t32i = t11i + t25i;
/* 2610 */         float t33r = t3r + t18r;
/* 2611 */         float t33i = t3i + t18i;
/* 2612 */         float t34r = c2 * t29r - c6 * t30r;
/* 2613 */         float t34i = c2 * t29i - c6 * t30i;
/* 2614 */         float t35r = t3r - t18r;
/* 2615 */         float t35i = t3i - t18i;
/* 2616 */         float t36r = c7 * t27r - c3 * t28r;
/* 2617 */         float t36i = c7 * t27i - c3 * t28i;
/* 2618 */         float t37r = t4r + t19r;
/* 2619 */         float t37i = t4i + t19i;
/* 2620 */         float t38r = c3 * t27r + c7 * t28r;
/* 2621 */         float t38i = c3 * t27i + c7 * t28i;
/* 2622 */         float t39r = t4r - t19r;
/* 2623 */         float t39i = t4i - t19i;
/* 2624 */         float t40r = c6 * t29r + c2 * t30r;
/* 2625 */         float t40i = c6 * t29i + c2 * t30i;
/* 2626 */         float t41r = c4 * (t12r - t26r);
/* 2627 */         float t41i = c4 * (t12i - t26i);
/* 2628 */         float t42r = c5 * (t12r + t26r);
/* 2629 */         float t42i = c5 * (t12i + t26i);
/* 2630 */         float y1r = t33r + t34r;
/* 2631 */         float y1i = t33i + t34i;
/* 2632 */         float y2r = t6r + t41r;
/* 2633 */         float y2i = t6i + t41i;
/* 2634 */         float y3r = t35r + t40r;
/* 2635 */         float y3i = t35i + t40i;
/* 2636 */         float y4r = t5r - t17r;
/* 2637 */         float y4i = t5i - t17i;
/* 2638 */         float y5r = t35r - t40r;
/* 2639 */         float y5i = t35i - t40i;
/* 2640 */         float y6r = t6r - t41r;
/* 2641 */         float y6i = t6i - t41i;
/* 2642 */         float y7r = t33r - t34r;
/* 2643 */         float y7i = t33i - t34i;
/* 2644 */         float y9r = t38r - t37r;
/* 2645 */         float y9i = t38i - t37i;
/* 2646 */         float y10r = t42r - t20r;
/* 2647 */         float y10i = t42i - t20i;
/* 2648 */         float y11r = t36r + t39r;
/* 2649 */         float y11i = t36i + t39i;
/* 2650 */         float y12r = c1 * (t11r - t25r);
/* 2651 */         float y12i = c1 * (t11i - t25i);
/* 2652 */         float y13r = t36r - t39r;
/* 2653 */         float y13i = t36i - t39i;
/* 2654 */         float y14r = t42r + t20r;
/* 2655 */         float y14i = t42i + t20i;
/* 2656 */         float y15r = t38r + t37r;
/* 2657 */         float y15i = t38i + t37i;
/* 2658 */         zj0[i1] = t31r + t32r;
/* 2659 */         zj0[i1 + 1] = t31i + t32i;
/* 2660 */         zj1[i1] = y1r - y15i;
/* 2661 */         zj1[i1 + 1] = y1i + y15r;
/* 2662 */         zj2[i1] = y2r - y14i;
/* 2663 */         zj2[i1 + 1] = y2i + y14r;
/* 2664 */         zj3[i1] = y3r - y13i;
/* 2665 */         zj3[i1 + 1] = y3i + y13r;
/* 2666 */         zj4[i1] = y4r - y12i;
/* 2667 */         zj4[i1 + 1] = y4i + y12r;
/* 2668 */         zj5[i1] = y5r - y11i;
/* 2669 */         zj5[i1 + 1] = y5i + y11r;
/* 2670 */         zj6[i1] = y6r - y10i;
/* 2671 */         zj6[i1 + 1] = y6i + y10r;
/* 2672 */         zj7[i1] = y7r - y9i;
/* 2673 */         zj7[i1 + 1] = y7i + y9r;
/* 2674 */         zj8[i1] = t31r - t32r;
/* 2675 */         zj8[i1 + 1] = t31i - t32i;
/* 2676 */         zj9[i1] = y7r + y9i;
/* 2677 */         zj9[i1 + 1] = y7i - y9r;
/* 2678 */         zj10[i1] = y6r + y10i;
/* 2679 */         zj10[i1 + 1] = y6i - y10r;
/* 2680 */         zj11[i1] = y5r + y11i;
/* 2681 */         zj11[i1 + 1] = y5i - y11r;
/* 2682 */         zj12[i1] = y4r + y12i;
/* 2683 */         zj12[i1 + 1] = y4i - y12r;
/* 2684 */         zj13[i1] = y3r + y13i;
/* 2685 */         zj13[i1 + 1] = y3i - y13r;
/* 2686 */         zj14[i1] = y2r + y14i;
/* 2687 */         zj14[i1 + 1] = y2i - y14r;
/* 2688 */         zj15[i1] = y1r + y15i;
/* 2689 */         zj15[i1 + 1] = y1i - y15r;
/*      */       } 
/* 2691 */       int jt = j15 + 1;
/* 2692 */       j15 = j14 + 1;
/* 2693 */       j14 = j13 + 1;
/* 2694 */       j13 = j12 + 1;
/* 2695 */       j12 = j11 + 1;
/* 2696 */       j11 = j10 + 1;
/* 2697 */       j10 = j9 + 1;
/* 2698 */       j9 = j8 + 1;
/* 2699 */       j8 = j7 + 1;
/* 2700 */       j7 = j6 + 1;
/* 2701 */       j6 = j5 + 1;
/* 2702 */       j5 = j4 + 1;
/* 2703 */       j4 = j3 + 1;
/* 2704 */       j3 = j2 + 1;
/* 2705 */       j2 = j1 + 1;
/* 2706 */       j1 = j0 + 1;
/* 2707 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void transform2b(int sign, int n1, int nfft, float[][] z) {
/* 2724 */     int nleft = nfft;
/*      */ 
/*      */     
/* 2727 */     for (int jfac = 0; jfac < 10; jfac++) {
/*      */ 
/*      */       
/* 2730 */       int ifac = _kfac[jfac];
/* 2731 */       int ndiv = nleft / ifac;
/* 2732 */       if (ndiv * ifac == nleft) {
/*      */ 
/*      */ 
/*      */         
/* 2736 */         nleft = ndiv;
/* 2737 */         int m = nfft / ifac;
/*      */ 
/*      */         
/* 2740 */         int mu = 0;
/* 2741 */         int mm = 0;
/* 2742 */         for (int kfac = 1; kfac <= ifac && mm % ifac != 1; kfac++) {
/* 2743 */           mu = kfac;
/* 2744 */           mm = kfac * m;
/*      */         } 
/* 2746 */         if (sign < 0) {
/* 2747 */           mu = ifac - mu;
/*      */         }
/*      */         
/* 2750 */         int jinc = 2 * mm;
/* 2751 */         int jmax = 2 * nfft;
/* 2752 */         int j0 = 0;
/* 2753 */         int j1 = j0 + jinc;
/*      */ 
/*      */         
/* 2756 */         if (ifac == 2) {
/* 2757 */           pfa2b(n1, z, m, j0, j1);
/*      */         } else {
/*      */           
/* 2760 */           int j2 = (j1 + jinc) % jmax;
/*      */ 
/*      */           
/* 2763 */           if (ifac == 3) {
/* 2764 */             pfa3b(n1, z, mu, m, j0, j1, j2);
/*      */           } else {
/*      */             
/* 2767 */             int j3 = (j2 + jinc) % jmax;
/*      */ 
/*      */             
/* 2770 */             if (ifac == 4) {
/* 2771 */               pfa4b(n1, z, mu, m, j0, j1, j2, j3);
/*      */             } else {
/*      */               
/* 2774 */               int j4 = (j3 + jinc) % jmax;
/*      */ 
/*      */               
/* 2777 */               if (ifac == 5) {
/* 2778 */                 pfa5b(n1, z, mu, m, j0, j1, j2, j3, j4);
/*      */               } else {
/*      */                 
/* 2781 */                 int j5 = (j4 + jinc) % jmax;
/* 2782 */                 int j6 = (j5 + jinc) % jmax;
/*      */ 
/*      */                 
/* 2785 */                 if (ifac == 7)
/* 2786 */                 { pfa7b(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6); }
/*      */                 else
/*      */                 
/* 2789 */                 { int j7 = (j6 + jinc) % jmax;
/*      */ 
/*      */                   
/* 2792 */                   if (ifac == 8)
/* 2793 */                   { pfa8b(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7); }
/*      */                   else
/*      */                   
/* 2796 */                   { int j8 = (j7 + jinc) % jmax;
/*      */ 
/*      */                     
/* 2799 */                     if (ifac == 9)
/* 2800 */                     { pfa9b(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8); }
/*      */                     else
/*      */                     
/* 2803 */                     { int j9 = (j8 + jinc) % jmax;
/* 2804 */                       int j10 = (j9 + jinc) % jmax;
/*      */ 
/*      */                       
/* 2807 */                       if (ifac == 11)
/* 2808 */                       { pfa11b(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10); }
/*      */                       else
/*      */                       
/* 2811 */                       { int j11 = (j10 + jinc) % jmax;
/* 2812 */                         int j12 = (j11 + jinc) % jmax;
/*      */ 
/*      */                         
/* 2815 */                         if (ifac == 13)
/* 2816 */                         { pfa13b(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12); }
/*      */                         else
/*      */                         
/* 2819 */                         { int j13 = (j12 + jinc) % jmax;
/* 2820 */                           int j14 = (j13 + jinc) % jmax;
/* 2821 */                           int j15 = (j14 + jinc) % jmax;
/*      */ 
/*      */                           
/* 2824 */                           if (ifac == 16)
/* 2825 */                             pfa16b(n1, z, mu, m, j0, j1, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15);  }  }  }  }  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2831 */     }  } private static void pfa2b(int n1, float[][] z, int m, int j0, int j1) { for (int i = 0; i < m; i++) {
/* 2832 */       float[] zj0r = z[j0];
/* 2833 */       float[] zj0i = z[j0 + 1];
/* 2834 */       float[] zj1r = z[j1];
/* 2835 */       float[] zj1i = z[j1 + 1];
/* 2836 */       for (int i1 = 0; i1 < n1; i1++) {
/* 2837 */         float t1r = zj0r[i1] - zj1r[i1];
/* 2838 */         float t1i = zj0i[i1] - zj1i[i1];
/* 2839 */         zj0r[i1] = zj0r[i1] + zj1r[i1];
/* 2840 */         zj0i[i1] = zj0i[i1] + zj1i[i1];
/* 2841 */         zj1r[i1] = t1r;
/* 2842 */         zj1i[i1] = t1i;
/*      */       } 
/* 2844 */       int jt = j1 + 2;
/* 2845 */       j1 = j0 + 2;
/* 2846 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pfa3b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2) {
/*      */     float c1;
/* 2853 */     if (mu == 1) {
/* 2854 */       c1 = 0.8660254F;
/*      */     } else {
/* 2856 */       c1 = -0.8660254F;
/*      */     } 
/* 2858 */     for (int i = 0; i < m; i++) {
/* 2859 */       float[] zj0r = z[j0];
/* 2860 */       float[] zj0i = z[j0 + 1];
/* 2861 */       float[] zj1r = z[j1];
/* 2862 */       float[] zj1i = z[j1 + 1];
/* 2863 */       float[] zj2r = z[j2];
/* 2864 */       float[] zj2i = z[j2 + 1];
/* 2865 */       for (int i1 = 0; i1 < n1; i1++) {
/* 2866 */         float t1r = zj1r[i1] + zj2r[i1];
/* 2867 */         float t1i = zj1i[i1] + zj2i[i1];
/* 2868 */         float y1r = zj0r[i1] - 0.5F * t1r;
/* 2869 */         float y1i = zj0i[i1] - 0.5F * t1i;
/* 2870 */         float y2r = c1 * (zj1r[i1] - zj2r[i1]);
/* 2871 */         float y2i = c1 * (zj1i[i1] - zj2i[i1]);
/* 2872 */         zj0r[i1] = zj0r[i1] + t1r;
/* 2873 */         zj0i[i1] = zj0i[i1] + t1i;
/* 2874 */         zj1r[i1] = y1r - y2i;
/* 2875 */         zj1i[i1] = y1i + y2r;
/* 2876 */         zj2r[i1] = y1r + y2i;
/* 2877 */         zj2i[i1] = y1i - y2r;
/*      */       } 
/* 2879 */       int jt = j2 + 2;
/* 2880 */       j2 = j1 + 2;
/* 2881 */       j1 = j0 + 2;
/* 2882 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa4b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3) {
/*      */     float c1;
/* 2889 */     if (mu == 1) {
/* 2890 */       c1 = 1.0F;
/*      */     } else {
/* 2892 */       c1 = -1.0F;
/*      */     } 
/* 2894 */     for (int i = 0; i < m; i++) {
/* 2895 */       float[] zj0r = z[j0];
/* 2896 */       float[] zj0i = z[j0 + 1];
/* 2897 */       float[] zj1r = z[j1];
/* 2898 */       float[] zj1i = z[j1 + 1];
/* 2899 */       float[] zj2r = z[j2];
/* 2900 */       float[] zj2i = z[j2 + 1];
/* 2901 */       float[] zj3r = z[j3];
/* 2902 */       float[] zj3i = z[j3 + 1];
/* 2903 */       for (int i1 = 0; i1 < n1; i1++) {
/* 2904 */         float t1r = zj0r[i1] + zj2r[i1];
/* 2905 */         float t1i = zj0i[i1] + zj2i[i1];
/* 2906 */         float t2r = zj1r[i1] + zj3r[i1];
/* 2907 */         float t2i = zj1i[i1] + zj3i[i1];
/* 2908 */         float y1r = zj0r[i1] - zj2r[i1];
/* 2909 */         float y1i = zj0i[i1] - zj2i[i1];
/* 2910 */         float y3r = c1 * (zj1r[i1] - zj3r[i1]);
/* 2911 */         float y3i = c1 * (zj1i[i1] - zj3i[i1]);
/* 2912 */         zj0r[i1] = t1r + t2r;
/* 2913 */         zj0i[i1] = t1i + t2i;
/* 2914 */         zj1r[i1] = y1r - y3i;
/* 2915 */         zj1i[i1] = y1i + y3r;
/* 2916 */         zj2r[i1] = t1r - t2r;
/* 2917 */         zj2i[i1] = t1i - t2i;
/* 2918 */         zj3r[i1] = y1r + y3i;
/* 2919 */         zj3i[i1] = y1i - y3r;
/*      */       } 
/* 2921 */       int jt = j3 + 2;
/* 2922 */       j3 = j2 + 2;
/* 2923 */       j2 = j1 + 2;
/* 2924 */       j1 = j0 + 2;
/* 2925 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void pfa5b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4) { float c1;
/*      */     float c2;
/*      */     float c3;
/* 2932 */     if (mu == 1) {
/* 2933 */       c1 = 0.559017F;
/* 2934 */       c2 = 0.95105654F;
/* 2935 */       c3 = 0.58778524F;
/* 2936 */     } else if (mu == 2) {
/* 2937 */       c1 = -0.559017F;
/* 2938 */       c2 = 0.58778524F;
/* 2939 */       c3 = -0.95105654F;
/* 2940 */     } else if (mu == 3) {
/* 2941 */       c1 = -0.559017F;
/* 2942 */       c2 = -0.58778524F;
/* 2943 */       c3 = 0.95105654F;
/*      */     } else {
/* 2945 */       c1 = 0.559017F;
/* 2946 */       c2 = -0.95105654F;
/* 2947 */       c3 = -0.58778524F;
/*      */     } 
/* 2949 */     for (int i = 0; i < m; i++) {
/* 2950 */       float[] zj0r = z[j0];
/* 2951 */       float[] zj0i = z[j0 + 1];
/* 2952 */       float[] zj1r = z[j1];
/* 2953 */       float[] zj1i = z[j1 + 1];
/* 2954 */       float[] zj2r = z[j2];
/* 2955 */       float[] zj2i = z[j2 + 1];
/* 2956 */       float[] zj3r = z[j3];
/* 2957 */       float[] zj3i = z[j3 + 1];
/* 2958 */       float[] zj4r = z[j4];
/* 2959 */       float[] zj4i = z[j4 + 1];
/* 2960 */       for (int i1 = 0; i1 < n1; i1++) {
/* 2961 */         float t1r = zj1r[i1] + zj4r[i1];
/* 2962 */         float t1i = zj1i[i1] + zj4i[i1];
/* 2963 */         float t2r = zj2r[i1] + zj3r[i1];
/* 2964 */         float t2i = zj2i[i1] + zj3i[i1];
/* 2965 */         float t3r = zj1r[i1] - zj4r[i1];
/* 2966 */         float t3i = zj1i[i1] - zj4i[i1];
/* 2967 */         float t4r = zj2r[i1] - zj3r[i1];
/* 2968 */         float t4i = zj2i[i1] - zj3i[i1];
/* 2969 */         float t5r = t1r + t2r;
/* 2970 */         float t5i = t1i + t2i;
/* 2971 */         float t6r = c1 * (t1r - t2r);
/* 2972 */         float t6i = c1 * (t1i - t2i);
/* 2973 */         float t7r = zj0r[i1] - 0.25F * t5r;
/* 2974 */         float t7i = zj0i[i1] - 0.25F * t5i;
/* 2975 */         float y1r = t7r + t6r;
/* 2976 */         float y1i = t7i + t6i;
/* 2977 */         float y2r = t7r - t6r;
/* 2978 */         float y2i = t7i - t6i;
/* 2979 */         float y3r = c3 * t3r - c2 * t4r;
/* 2980 */         float y3i = c3 * t3i - c2 * t4i;
/* 2981 */         float y4r = c2 * t3r + c3 * t4r;
/* 2982 */         float y4i = c2 * t3i + c3 * t4i;
/* 2983 */         zj0r[i1] = zj0r[i1] + t5r;
/* 2984 */         zj0i[i1] = zj0i[i1] + t5i;
/* 2985 */         zj1r[i1] = y1r - y4i;
/* 2986 */         zj1i[i1] = y1i + y4r;
/* 2987 */         zj2r[i1] = y2r - y3i;
/* 2988 */         zj2i[i1] = y2i + y3r;
/* 2989 */         zj3r[i1] = y2r + y3i;
/* 2990 */         zj3i[i1] = y2i - y3r;
/* 2991 */         zj4r[i1] = y1r + y4i;
/* 2992 */         zj4i[i1] = y1i - y4r;
/*      */       } 
/* 2994 */       int jt = j4 + 2;
/* 2995 */       j4 = j3 + 2;
/* 2996 */       j3 = j2 + 2;
/* 2997 */       j2 = j1 + 2;
/* 2998 */       j1 = j0 + 2;
/* 2999 */       j0 = jt;
/*      */     }  } private static void pfa7b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6) { float c1;
/*      */     float c2;
/*      */     float c3;
/*      */     float c4;
/*      */     float c5;
/*      */     float c6;
/* 3006 */     if (mu == 1) {
/* 3007 */       c1 = 0.6234898F;
/* 3008 */       c2 = -0.22252093F;
/* 3009 */       c3 = -0.90096885F;
/* 3010 */       c4 = 0.7818315F;
/* 3011 */       c5 = 0.9749279F;
/* 3012 */       c6 = 0.43388373F;
/* 3013 */     } else if (mu == 2) {
/* 3014 */       c1 = -0.22252093F;
/* 3015 */       c2 = -0.90096885F;
/* 3016 */       c3 = 0.6234898F;
/* 3017 */       c4 = 0.9749279F;
/* 3018 */       c5 = -0.43388373F;
/* 3019 */       c6 = -0.7818315F;
/* 3020 */     } else if (mu == 3) {
/* 3021 */       c1 = -0.90096885F;
/* 3022 */       c2 = 0.6234898F;
/* 3023 */       c3 = -0.22252093F;
/* 3024 */       c4 = 0.43388373F;
/* 3025 */       c5 = -0.7818315F;
/* 3026 */       c6 = 0.9749279F;
/* 3027 */     } else if (mu == 4) {
/* 3028 */       c1 = -0.90096885F;
/* 3029 */       c2 = 0.6234898F;
/* 3030 */       c3 = -0.22252093F;
/* 3031 */       c4 = -0.43388373F;
/* 3032 */       c5 = 0.7818315F;
/* 3033 */       c6 = -0.9749279F;
/* 3034 */     } else if (mu == 5) {
/* 3035 */       c1 = -0.22252093F;
/* 3036 */       c2 = -0.90096885F;
/* 3037 */       c3 = 0.6234898F;
/* 3038 */       c4 = -0.9749279F;
/* 3039 */       c5 = 0.43388373F;
/* 3040 */       c6 = 0.7818315F;
/*      */     } else {
/* 3042 */       c1 = 0.6234898F;
/* 3043 */       c2 = -0.22252093F;
/* 3044 */       c3 = -0.90096885F;
/* 3045 */       c4 = -0.7818315F;
/* 3046 */       c5 = -0.9749279F;
/* 3047 */       c6 = -0.43388373F;
/*      */     } 
/* 3049 */     for (int i = 0; i < m; i++) {
/* 3050 */       float[] zj0r = z[j0];
/* 3051 */       float[] zj0i = z[j0 + 1];
/* 3052 */       float[] zj1r = z[j1];
/* 3053 */       float[] zj1i = z[j1 + 1];
/* 3054 */       float[] zj2r = z[j2];
/* 3055 */       float[] zj2i = z[j2 + 1];
/* 3056 */       float[] zj3r = z[j3];
/* 3057 */       float[] zj3i = z[j3 + 1];
/* 3058 */       float[] zj4r = z[j4];
/* 3059 */       float[] zj4i = z[j4 + 1];
/* 3060 */       float[] zj5r = z[j5];
/* 3061 */       float[] zj5i = z[j5 + 1];
/* 3062 */       float[] zj6r = z[j6];
/* 3063 */       float[] zj6i = z[j6 + 1];
/* 3064 */       for (int i1 = 0; i1 < n1; i1++) {
/* 3065 */         float t1r = zj1r[i1] + zj6r[i1];
/* 3066 */         float t1i = zj1i[i1] + zj6i[i1];
/* 3067 */         float t2r = zj2r[i1] + zj5r[i1];
/* 3068 */         float t2i = zj2i[i1] + zj5i[i1];
/* 3069 */         float t3r = zj3r[i1] + zj4r[i1];
/* 3070 */         float t3i = zj3i[i1] + zj4i[i1];
/* 3071 */         float t4r = zj1r[i1] - zj6r[i1];
/* 3072 */         float t4i = zj1i[i1] - zj6i[i1];
/* 3073 */         float t5r = zj2r[i1] - zj5r[i1];
/* 3074 */         float t5i = zj2i[i1] - zj5i[i1];
/* 3075 */         float t6r = zj3r[i1] - zj4r[i1];
/* 3076 */         float t6i = zj3i[i1] - zj4i[i1];
/* 3077 */         float t7r = zj0r[i1] - 0.5F * t3r;
/* 3078 */         float t7i = zj0i[i1] - 0.5F * t3i;
/* 3079 */         float t8r = t1r - t3r;
/* 3080 */         float t8i = t1i - t3i;
/* 3081 */         float t9r = t2r - t3r;
/* 3082 */         float t9i = t2i - t3i;
/* 3083 */         float y1r = t7r + c1 * t8r + c2 * t9r;
/* 3084 */         float y1i = t7i + c1 * t8i + c2 * t9i;
/* 3085 */         float y2r = t7r + c2 * t8r + c3 * t9r;
/* 3086 */         float y2i = t7i + c2 * t8i + c3 * t9i;
/* 3087 */         float y3r = t7r + c3 * t8r + c1 * t9r;
/* 3088 */         float y3i = t7i + c3 * t8i + c1 * t9i;
/* 3089 */         float y4r = c6 * t4r - c4 * t5r + c5 * t6r;
/* 3090 */         float y4i = c6 * t4i - c4 * t5i + c5 * t6i;
/* 3091 */         float y5r = c5 * t4r - c6 * t5r - c4 * t6r;
/* 3092 */         float y5i = c5 * t4i - c6 * t5i - c4 * t6i;
/* 3093 */         float y6r = c4 * t4r + c5 * t5r + c6 * t6r;
/* 3094 */         float y6i = c4 * t4i + c5 * t5i + c6 * t6i;
/* 3095 */         zj0r[i1] = zj0r[i1] + t1r + t2r + t3r;
/* 3096 */         zj0i[i1] = zj0i[i1] + t1i + t2i + t3i;
/* 3097 */         zj1r[i1] = y1r - y6i;
/* 3098 */         zj1i[i1] = y1i + y6r;
/* 3099 */         zj2r[i1] = y2r - y5i;
/* 3100 */         zj2i[i1] = y2i + y5r;
/* 3101 */         zj3r[i1] = y3r - y4i;
/* 3102 */         zj3i[i1] = y3i + y4r;
/* 3103 */         zj4r[i1] = y3r + y4i;
/* 3104 */         zj4i[i1] = y3i - y4r;
/* 3105 */         zj5r[i1] = y2r + y5i;
/* 3106 */         zj5i[i1] = y2i - y5r;
/* 3107 */         zj6r[i1] = y1r + y6i;
/* 3108 */         zj6i[i1] = y1i - y6r;
/*      */       } 
/* 3110 */       int jt = j6 + 2;
/* 3111 */       j6 = j5 + 2;
/* 3112 */       j5 = j4 + 2;
/* 3113 */       j4 = j3 + 2;
/* 3114 */       j3 = j2 + 2;
/* 3115 */       j2 = j1 + 2;
/* 3116 */       j1 = j0 + 2;
/* 3117 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pfa8b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7) {
/*      */     float c1, c2;
/* 3124 */     if (mu == 1) {
/* 3125 */       c1 = 1.0F;
/* 3126 */       c2 = 0.70710677F;
/* 3127 */     } else if (mu == 3) {
/* 3128 */       c1 = -1.0F;
/* 3129 */       c2 = -0.70710677F;
/* 3130 */     } else if (mu == 5) {
/* 3131 */       c1 = 1.0F;
/* 3132 */       c2 = -0.70710677F;
/*      */     } else {
/* 3134 */       c1 = -1.0F;
/* 3135 */       c2 = 0.70710677F;
/*      */     } 
/* 3137 */     float c3 = c1 * c2;
/* 3138 */     for (int i = 0; i < m; i++) {
/* 3139 */       float[] zj0r = z[j0];
/* 3140 */       float[] zj0i = z[j0 + 1];
/* 3141 */       float[] zj1r = z[j1];
/* 3142 */       float[] zj1i = z[j1 + 1];
/* 3143 */       float[] zj2r = z[j2];
/* 3144 */       float[] zj2i = z[j2 + 1];
/* 3145 */       float[] zj3r = z[j3];
/* 3146 */       float[] zj3i = z[j3 + 1];
/* 3147 */       float[] zj4r = z[j4];
/* 3148 */       float[] zj4i = z[j4 + 1];
/* 3149 */       float[] zj5r = z[j5];
/* 3150 */       float[] zj5i = z[j5 + 1];
/* 3151 */       float[] zj6r = z[j6];
/* 3152 */       float[] zj6i = z[j6 + 1];
/* 3153 */       float[] zj7r = z[j7];
/* 3154 */       float[] zj7i = z[j7 + 1];
/* 3155 */       for (int i1 = 0; i1 < n1; i1++) {
/* 3156 */         float t1r = zj0r[i1] + zj4r[i1];
/* 3157 */         float t1i = zj0i[i1] + zj4i[i1];
/* 3158 */         float t2r = zj0r[i1] - zj4r[i1];
/* 3159 */         float t2i = zj0i[i1] - zj4i[i1];
/* 3160 */         float t3r = zj1r[i1] + zj5r[i1];
/* 3161 */         float t3i = zj1i[i1] + zj5i[i1];
/* 3162 */         float t4r = zj1r[i1] - zj5r[i1];
/* 3163 */         float t4i = zj1i[i1] - zj5i[i1];
/* 3164 */         float t5r = zj2r[i1] + zj6r[i1];
/* 3165 */         float t5i = zj2i[i1] + zj6i[i1];
/* 3166 */         float t6r = c1 * (zj2r[i1] - zj6r[i1]);
/* 3167 */         float t6i = c1 * (zj2i[i1] - zj6i[i1]);
/* 3168 */         float t7r = zj3r[i1] + zj7r[i1];
/* 3169 */         float t7i = zj3i[i1] + zj7i[i1];
/* 3170 */         float t8r = zj3r[i1] - zj7r[i1];
/* 3171 */         float t8i = zj3i[i1] - zj7i[i1];
/* 3172 */         float t9r = t1r + t5r;
/* 3173 */         float t9i = t1i + t5i;
/* 3174 */         float t10r = t3r + t7r;
/* 3175 */         float t10i = t3i + t7i;
/* 3176 */         float t11r = c2 * (t4r - t8r);
/* 3177 */         float t11i = c2 * (t4i - t8i);
/* 3178 */         float t12r = c3 * (t4r + t8r);
/* 3179 */         float t12i = c3 * (t4i + t8i);
/* 3180 */         float y1r = t2r + t11r;
/* 3181 */         float y1i = t2i + t11i;
/* 3182 */         float y2r = t1r - t5r;
/* 3183 */         float y2i = t1i - t5i;
/* 3184 */         float y3r = t2r - t11r;
/* 3185 */         float y3i = t2i - t11i;
/* 3186 */         float y5r = t12r - t6r;
/* 3187 */         float y5i = t12i - t6i;
/* 3188 */         float y6r = c1 * (t3r - t7r);
/* 3189 */         float y6i = c1 * (t3i - t7i);
/* 3190 */         float y7r = t12r + t6r;
/* 3191 */         float y7i = t12i + t6i;
/* 3192 */         zj0r[i1] = t9r + t10r;
/* 3193 */         zj0i[i1] = t9i + t10i;
/* 3194 */         zj1r[i1] = y1r - y7i;
/* 3195 */         zj1i[i1] = y1i + y7r;
/* 3196 */         zj2r[i1] = y2r - y6i;
/* 3197 */         zj2i[i1] = y2i + y6r;
/* 3198 */         zj3r[i1] = y3r - y5i;
/* 3199 */         zj3i[i1] = y3i + y5r;
/* 3200 */         zj4r[i1] = t9r - t10r;
/* 3201 */         zj4i[i1] = t9i - t10i;
/* 3202 */         zj5r[i1] = y3r + y5i;
/* 3203 */         zj5i[i1] = y3i - y5r;
/* 3204 */         zj6r[i1] = y2r + y6i;
/* 3205 */         zj6i[i1] = y2i - y6r;
/* 3206 */         zj7r[i1] = y1r + y7i;
/* 3207 */         zj7i[i1] = y1i - y7r;
/*      */       } 
/* 3209 */       int jt = j7 + 2;
/* 3210 */       j7 = j6 + 2;
/* 3211 */       j6 = j5 + 2;
/* 3212 */       j5 = j4 + 2;
/* 3213 */       j4 = j3 + 2;
/* 3214 */       j3 = j2 + 2;
/* 3215 */       j2 = j1 + 2;
/* 3216 */       j1 = j0 + 2;
/* 3217 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void pfa9b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8) {
/*      */     float c1, c2, c3, c4, c5;
/* 3224 */     if (mu == 1) {
/* 3225 */       c1 = 0.8660254F;
/* 3226 */       c2 = 0.76604444F;
/* 3227 */       c3 = 0.64278764F;
/* 3228 */       c4 = 0.17364818F;
/* 3229 */       c5 = 0.9848077F;
/* 3230 */     } else if (mu == 2) {
/* 3231 */       c1 = -0.8660254F;
/* 3232 */       c2 = 0.17364818F;
/* 3233 */       c3 = 0.9848077F;
/* 3234 */       c4 = -0.9396926F;
/* 3235 */       c5 = 0.34202015F;
/* 3236 */     } else if (mu == 4) {
/* 3237 */       c1 = 0.8660254F;
/* 3238 */       c2 = -0.9396926F;
/* 3239 */       c3 = 0.34202015F;
/* 3240 */       c4 = 0.76604444F;
/* 3241 */       c5 = -0.64278764F;
/* 3242 */     } else if (mu == 5) {
/* 3243 */       c1 = -0.8660254F;
/* 3244 */       c2 = -0.9396926F;
/* 3245 */       c3 = -0.34202015F;
/* 3246 */       c4 = 0.76604444F;
/* 3247 */       c5 = 0.64278764F;
/* 3248 */     } else if (mu == 7) {
/* 3249 */       c1 = 0.8660254F;
/* 3250 */       c2 = 0.17364818F;
/* 3251 */       c3 = -0.9848077F;
/* 3252 */       c4 = -0.9396926F;
/* 3253 */       c5 = -0.34202015F;
/*      */     } else {
/* 3255 */       c1 = -0.8660254F;
/* 3256 */       c2 = 0.76604444F;
/* 3257 */       c3 = -0.64278764F;
/* 3258 */       c4 = 0.17364818F;
/* 3259 */       c5 = -0.9848077F;
/*      */     } 
/* 3261 */     float c6 = c1 * c2;
/* 3262 */     float c7 = c1 * c3;
/* 3263 */     float c8 = c1 * c4;
/* 3264 */     float c9 = c1 * c5;
/* 3265 */     for (int i = 0; i < m; i++) {
/* 3266 */       float[] zj0r = z[j0];
/* 3267 */       float[] zj0i = z[j0 + 1];
/* 3268 */       float[] zj1r = z[j1];
/* 3269 */       float[] zj1i = z[j1 + 1];
/* 3270 */       float[] zj2r = z[j2];
/* 3271 */       float[] zj2i = z[j2 + 1];
/* 3272 */       float[] zj3r = z[j3];
/* 3273 */       float[] zj3i = z[j3 + 1];
/* 3274 */       float[] zj4r = z[j4];
/* 3275 */       float[] zj4i = z[j4 + 1];
/* 3276 */       float[] zj5r = z[j5];
/* 3277 */       float[] zj5i = z[j5 + 1];
/* 3278 */       float[] zj6r = z[j6];
/* 3279 */       float[] zj6i = z[j6 + 1];
/* 3280 */       float[] zj7r = z[j7];
/* 3281 */       float[] zj7i = z[j7 + 1];
/* 3282 */       float[] zj8r = z[j8];
/* 3283 */       float[] zj8i = z[j8 + 1];
/* 3284 */       for (int i1 = 0; i1 < n1; i1++) {
/* 3285 */         float t1r = zj3r[i1] + zj6r[i1];
/* 3286 */         float t1i = zj3i[i1] + zj6i[i1];
/* 3287 */         float t2r = zj0r[i1] - 0.5F * t1r;
/* 3288 */         float t2i = zj0i[i1] - 0.5F * t1i;
/* 3289 */         float t3r = c1 * (zj3r[i1] - zj6r[i1]);
/* 3290 */         float t3i = c1 * (zj3i[i1] - zj6i[i1]);
/* 3291 */         float t4r = zj0r[i1] + t1r;
/* 3292 */         float t4i = zj0i[i1] + t1i;
/* 3293 */         float t5r = zj4r[i1] + zj7r[i1];
/* 3294 */         float t5i = zj4i[i1] + zj7i[i1];
/* 3295 */         float t6r = zj1r[i1] - 0.5F * t5r;
/* 3296 */         float t6i = zj1i[i1] - 0.5F * t5i;
/* 3297 */         float t7r = zj4r[i1] - zj7r[i1];
/* 3298 */         float t7i = zj4i[i1] - zj7i[i1];
/* 3299 */         float t8r = zj1r[i1] + t5r;
/* 3300 */         float t8i = zj1i[i1] + t5i;
/* 3301 */         float t9r = zj2r[i1] + zj5r[i1];
/* 3302 */         float t9i = zj2i[i1] + zj5i[i1];
/* 3303 */         float t10r = zj8r[i1] - 0.5F * t9r;
/* 3304 */         float t10i = zj8i[i1] - 0.5F * t9i;
/* 3305 */         float t11r = zj2r[i1] - zj5r[i1];
/* 3306 */         float t11i = zj2i[i1] - zj5i[i1];
/* 3307 */         float t12r = zj8r[i1] + t9r;
/* 3308 */         float t12i = zj8i[i1] + t9i;
/* 3309 */         float t13r = t8r + t12r;
/* 3310 */         float t13i = t8i + t12i;
/* 3311 */         float t14r = t6r + t10r;
/* 3312 */         float t14i = t6i + t10i;
/* 3313 */         float t15r = t6r - t10r;
/* 3314 */         float t15i = t6i - t10i;
/* 3315 */         float t16r = t7r + t11r;
/* 3316 */         float t16i = t7i + t11i;
/* 3317 */         float t17r = t7r - t11r;
/* 3318 */         float t17i = t7i - t11i;
/* 3319 */         float t18r = c2 * t14r - c7 * t17r;
/* 3320 */         float t18i = c2 * t14i - c7 * t17i;
/* 3321 */         float t19r = c4 * t14r + c9 * t17r;
/* 3322 */         float t19i = c4 * t14i + c9 * t17i;
/* 3323 */         float t20r = c3 * t15r + c6 * t16r;
/* 3324 */         float t20i = c3 * t15i + c6 * t16i;
/* 3325 */         float t21r = c5 * t15r - c8 * t16r;
/* 3326 */         float t21i = c5 * t15i - c8 * t16i;
/* 3327 */         float t22r = t18r + t19r;
/* 3328 */         float t22i = t18i + t19i;
/* 3329 */         float t23r = t20r - t21r;
/* 3330 */         float t23i = t20i - t21i;
/* 3331 */         float y1r = t2r + t18r;
/* 3332 */         float y1i = t2i + t18i;
/* 3333 */         float y2r = t2r + t19r;
/* 3334 */         float y2i = t2i + t19i;
/* 3335 */         float y3r = t4r - 0.5F * t13r;
/* 3336 */         float y3i = t4i - 0.5F * t13i;
/* 3337 */         float y4r = t2r - t22r;
/* 3338 */         float y4i = t2i - t22i;
/* 3339 */         float y5r = t3r - t23r;
/* 3340 */         float y5i = t3i - t23i;
/* 3341 */         float y6r = c1 * (t8r - t12r);
/* 3342 */         float y6i = c1 * (t8i - t12i);
/* 3343 */         float y7r = t21r - t3r;
/* 3344 */         float y7i = t21i - t3i;
/* 3345 */         float y8r = t3r + t20r;
/* 3346 */         float y8i = t3i + t20i;
/* 3347 */         zj0r[i1] = t4r + t13r;
/* 3348 */         zj0i[i1] = t4i + t13i;
/* 3349 */         zj1r[i1] = y1r - y8i;
/* 3350 */         zj1i[i1] = y1i + y8r;
/* 3351 */         zj2r[i1] = y2r - y7i;
/* 3352 */         zj2i[i1] = y2i + y7r;
/* 3353 */         zj3r[i1] = y3r - y6i;
/* 3354 */         zj3i[i1] = y3i + y6r;
/* 3355 */         zj4r[i1] = y4r - y5i;
/* 3356 */         zj4i[i1] = y4i + y5r;
/* 3357 */         zj5r[i1] = y4r + y5i;
/* 3358 */         zj5i[i1] = y4i - y5r;
/* 3359 */         zj6r[i1] = y3r + y6i;
/* 3360 */         zj6i[i1] = y3i - y6r;
/* 3361 */         zj7r[i1] = y2r + y7i;
/* 3362 */         zj7i[i1] = y2i - y7r;
/* 3363 */         zj8r[i1] = y1r + y8i;
/* 3364 */         zj8i[i1] = y1i - y8r;
/*      */       } 
/* 3366 */       int jt = j8 + 2;
/* 3367 */       j8 = j7 + 2;
/* 3368 */       j7 = j6 + 2;
/* 3369 */       j6 = j5 + 2;
/* 3370 */       j5 = j4 + 2;
/* 3371 */       j4 = j3 + 2;
/* 3372 */       j3 = j2 + 2;
/* 3373 */       j2 = j1 + 2;
/* 3374 */       j1 = j0 + 2;
/* 3375 */       j0 = jt;
/*      */     }  } private static void pfa11b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10) { float c1; float c2; float c3; float c4;
/*      */     float c5;
/*      */     float c6;
/*      */     float c7;
/*      */     float c8;
/*      */     float c9;
/*      */     float c10;
/* 3383 */     if (mu == 1) {
/* 3384 */       c1 = 0.8412535F;
/* 3385 */       c2 = 0.41541502F;
/* 3386 */       c3 = -0.14231484F;
/* 3387 */       c4 = -0.65486073F;
/* 3388 */       c5 = -0.959493F;
/* 3389 */       c6 = 0.54064083F;
/* 3390 */       c7 = 0.90963197F;
/* 3391 */       c8 = 0.98982143F;
/* 3392 */       c9 = 0.7557496F;
/* 3393 */       c10 = 0.28173256F;
/* 3394 */     } else if (mu == 2) {
/* 3395 */       c1 = 0.41541502F;
/* 3396 */       c2 = -0.65486073F;
/* 3397 */       c3 = -0.959493F;
/* 3398 */       c4 = -0.14231484F;
/* 3399 */       c5 = 0.8412535F;
/* 3400 */       c6 = 0.90963197F;
/* 3401 */       c7 = 0.7557496F;
/* 3402 */       c8 = -0.28173256F;
/* 3403 */       c9 = -0.98982143F;
/* 3404 */       c10 = -0.54064083F;
/* 3405 */     } else if (mu == 3) {
/* 3406 */       c1 = -0.14231484F;
/* 3407 */       c2 = -0.959493F;
/* 3408 */       c3 = 0.41541502F;
/* 3409 */       c4 = 0.8412535F;
/* 3410 */       c5 = -0.65486073F;
/* 3411 */       c6 = 0.98982143F;
/* 3412 */       c7 = -0.28173256F;
/* 3413 */       c8 = -0.90963197F;
/* 3414 */       c9 = 0.54064083F;
/* 3415 */       c10 = 0.7557496F;
/* 3416 */     } else if (mu == 4) {
/* 3417 */       c1 = -0.65486073F;
/* 3418 */       c2 = -0.14231484F;
/* 3419 */       c3 = 0.8412535F;
/* 3420 */       c4 = -0.959493F;
/* 3421 */       c5 = 0.41541502F;
/* 3422 */       c6 = 0.7557496F;
/* 3423 */       c7 = -0.98982143F;
/* 3424 */       c8 = 0.54064083F;
/* 3425 */       c9 = 0.28173256F;
/* 3426 */       c10 = -0.90963197F;
/* 3427 */     } else if (mu == 5) {
/* 3428 */       c1 = -0.959493F;
/* 3429 */       c2 = 0.8412535F;
/* 3430 */       c3 = -0.65486073F;
/* 3431 */       c4 = 0.41541502F;
/* 3432 */       c5 = -0.14231484F;
/* 3433 */       c6 = 0.28173256F;
/* 3434 */       c7 = -0.54064083F;
/* 3435 */       c8 = 0.7557496F;
/* 3436 */       c9 = -0.90963197F;
/* 3437 */       c10 = 0.98982143F;
/* 3438 */     } else if (mu == 6) {
/* 3439 */       c1 = -0.959493F;
/* 3440 */       c2 = 0.8412535F;
/* 3441 */       c3 = -0.65486073F;
/* 3442 */       c4 = 0.41541502F;
/* 3443 */       c5 = -0.14231484F;
/* 3444 */       c6 = -0.28173256F;
/* 3445 */       c7 = 0.54064083F;
/* 3446 */       c8 = -0.7557496F;
/* 3447 */       c9 = 0.90963197F;
/* 3448 */       c10 = -0.98982143F;
/* 3449 */     } else if (mu == 7) {
/* 3450 */       c1 = -0.65486073F;
/* 3451 */       c2 = -0.14231484F;
/* 3452 */       c3 = 0.8412535F;
/* 3453 */       c4 = -0.959493F;
/* 3454 */       c5 = 0.41541502F;
/* 3455 */       c6 = -0.7557496F;
/* 3456 */       c7 = 0.98982143F;
/* 3457 */       c8 = -0.54064083F;
/* 3458 */       c9 = -0.28173256F;
/* 3459 */       c10 = 0.90963197F;
/* 3460 */     } else if (mu == 8) {
/* 3461 */       c1 = -0.14231484F;
/* 3462 */       c2 = -0.959493F;
/* 3463 */       c3 = 0.41541502F;
/* 3464 */       c4 = 0.8412535F;
/* 3465 */       c5 = -0.65486073F;
/* 3466 */       c6 = -0.98982143F;
/* 3467 */       c7 = 0.28173256F;
/* 3468 */       c8 = 0.90963197F;
/* 3469 */       c9 = -0.54064083F;
/* 3470 */       c10 = -0.7557496F;
/* 3471 */     } else if (mu == 9) {
/* 3472 */       c1 = 0.41541502F;
/* 3473 */       c2 = -0.65486073F;
/* 3474 */       c3 = -0.959493F;
/* 3475 */       c4 = -0.14231484F;
/* 3476 */       c5 = 0.8412535F;
/* 3477 */       c6 = -0.90963197F;
/* 3478 */       c7 = -0.7557496F;
/* 3479 */       c8 = 0.28173256F;
/* 3480 */       c9 = 0.98982143F;
/* 3481 */       c10 = 0.54064083F;
/*      */     } else {
/* 3483 */       c1 = 0.8412535F;
/* 3484 */       c2 = 0.41541502F;
/* 3485 */       c3 = -0.14231484F;
/* 3486 */       c4 = -0.65486073F;
/* 3487 */       c5 = -0.959493F;
/* 3488 */       c6 = -0.54064083F;
/* 3489 */       c7 = -0.90963197F;
/* 3490 */       c8 = -0.98982143F;
/* 3491 */       c9 = -0.7557496F;
/* 3492 */       c10 = -0.28173256F;
/*      */     } 
/* 3494 */     for (int i = 0; i < m; i++) {
/* 3495 */       float[] zj0r = z[j0];
/* 3496 */       float[] zj0i = z[j0 + 1];
/* 3497 */       float[] zj1r = z[j1];
/* 3498 */       float[] zj1i = z[j1 + 1];
/* 3499 */       float[] zj2r = z[j2];
/* 3500 */       float[] zj2i = z[j2 + 1];
/* 3501 */       float[] zj3r = z[j3];
/* 3502 */       float[] zj3i = z[j3 + 1];
/* 3503 */       float[] zj4r = z[j4];
/* 3504 */       float[] zj4i = z[j4 + 1];
/* 3505 */       float[] zj5r = z[j5];
/* 3506 */       float[] zj5i = z[j5 + 1];
/* 3507 */       float[] zj6r = z[j6];
/* 3508 */       float[] zj6i = z[j6 + 1];
/* 3509 */       float[] zj7r = z[j7];
/* 3510 */       float[] zj7i = z[j7 + 1];
/* 3511 */       float[] zj8r = z[j8];
/* 3512 */       float[] zj8i = z[j8 + 1];
/* 3513 */       float[] zj9r = z[j9];
/* 3514 */       float[] zj9i = z[j9 + 1];
/* 3515 */       float[] zj10r = z[j10];
/* 3516 */       float[] zj10i = z[j10 + 1];
/* 3517 */       for (int i1 = 0; i1 < n1; i1++) {
/* 3518 */         float t1r = zj1r[i1] + zj10r[i1];
/* 3519 */         float t1i = zj1i[i1] + zj10i[i1];
/* 3520 */         float t2r = zj2r[i1] + zj9r[i1];
/* 3521 */         float t2i = zj2i[i1] + zj9i[i1];
/* 3522 */         float t3r = zj3r[i1] + zj8r[i1];
/* 3523 */         float t3i = zj3i[i1] + zj8i[i1];
/* 3524 */         float t4r = zj4r[i1] + zj7r[i1];
/* 3525 */         float t4i = zj4i[i1] + zj7i[i1];
/* 3526 */         float t5r = zj5r[i1] + zj6r[i1];
/* 3527 */         float t5i = zj5i[i1] + zj6i[i1];
/* 3528 */         float t6r = zj1r[i1] - zj10r[i1];
/* 3529 */         float t6i = zj1i[i1] - zj10i[i1];
/* 3530 */         float t7r = zj2r[i1] - zj9r[i1];
/* 3531 */         float t7i = zj2i[i1] - zj9i[i1];
/* 3532 */         float t8r = zj3r[i1] - zj8r[i1];
/* 3533 */         float t8i = zj3i[i1] - zj8i[i1];
/* 3534 */         float t9r = zj4r[i1] - zj7r[i1];
/* 3535 */         float t9i = zj4i[i1] - zj7i[i1];
/* 3536 */         float t10r = zj5r[i1] - zj6r[i1];
/* 3537 */         float t10i = zj5i[i1] - zj6i[i1];
/* 3538 */         float t11r = zj0r[i1] - 0.5F * t5r;
/* 3539 */         float t11i = zj0i[i1] - 0.5F * t5i;
/* 3540 */         float t12r = t1r - t5r;
/* 3541 */         float t12i = t1i - t5i;
/* 3542 */         float t13r = t2r - t5r;
/* 3543 */         float t13i = t2i - t5i;
/* 3544 */         float t14r = t3r - t5r;
/* 3545 */         float t14i = t3i - t5i;
/* 3546 */         float t15r = t4r - t5r;
/* 3547 */         float t15i = t4i - t5i;
/* 3548 */         float y1r = t11r + c1 * t12r + c2 * t13r + c3 * t14r + c4 * t15r;
/* 3549 */         float y1i = t11i + c1 * t12i + c2 * t13i + c3 * t14i + c4 * t15i;
/* 3550 */         float y2r = t11r + c2 * t12r + c4 * t13r + c5 * t14r + c3 * t15r;
/* 3551 */         float y2i = t11i + c2 * t12i + c4 * t13i + c5 * t14i + c3 * t15i;
/* 3552 */         float y3r = t11r + c3 * t12r + c5 * t13r + c2 * t14r + c1 * t15r;
/* 3553 */         float y3i = t11i + c3 * t12i + c5 * t13i + c2 * t14i + c1 * t15i;
/* 3554 */         float y4r = t11r + c4 * t12r + c3 * t13r + c1 * t14r + c5 * t15r;
/* 3555 */         float y4i = t11i + c4 * t12i + c3 * t13i + c1 * t14i + c5 * t15i;
/* 3556 */         float y5r = t11r + c5 * t12r + c1 * t13r + c4 * t14r + c2 * t15r;
/* 3557 */         float y5i = t11i + c5 * t12i + c1 * t13i + c4 * t14i + c2 * t15i;
/* 3558 */         float y6r = c10 * t6r - c6 * t7r + c9 * t8r - c7 * t9r + c8 * t10r;
/* 3559 */         float y6i = c10 * t6i - c6 * t7i + c9 * t8i - c7 * t9i + c8 * t10i;
/* 3560 */         float y7r = c9 * t6r - c8 * t7r + c6 * t8r + c10 * t9r - c7 * t10r;
/* 3561 */         float y7i = c9 * t6i - c8 * t7i + c6 * t8i + c10 * t9i - c7 * t10i;
/* 3562 */         float y8r = c8 * t6r - c10 * t7r - c7 * t8r + c6 * t9r + c9 * t10r;
/* 3563 */         float y8i = c8 * t6i - c10 * t7i - c7 * t8i + c6 * t9i + c9 * t10i;
/* 3564 */         float y9r = c7 * t6r + c9 * t7r - c10 * t8r - c8 * t9r - c6 * t10r;
/* 3565 */         float y9i = c7 * t6i + c9 * t7i - c10 * t8i - c8 * t9i - c6 * t10i;
/* 3566 */         float y10r = c6 * t6r + c7 * t7r + c8 * t8r + c9 * t9r + c10 * t10r;
/* 3567 */         float y10i = c6 * t6i + c7 * t7i + c8 * t8i + c9 * t9i + c10 * t10i;
/* 3568 */         zj0r[i1] = zj0r[i1] + t1r + t2r + t3r + t4r + t5r;
/* 3569 */         zj0i[i1] = zj0i[i1] + t1i + t2i + t3i + t4i + t5i;
/* 3570 */         zj1r[i1] = y1r - y10i;
/* 3571 */         zj1i[i1] = y1i + y10r;
/* 3572 */         zj2r[i1] = y2r - y9i;
/* 3573 */         zj2i[i1] = y2i + y9r;
/* 3574 */         zj3r[i1] = y3r - y8i;
/* 3575 */         zj3i[i1] = y3i + y8r;
/* 3576 */         zj4r[i1] = y4r - y7i;
/* 3577 */         zj4i[i1] = y4i + y7r;
/* 3578 */         zj5r[i1] = y5r - y6i;
/* 3579 */         zj5i[i1] = y5i + y6r;
/* 3580 */         zj6r[i1] = y5r + y6i;
/* 3581 */         zj6i[i1] = y5i - y6r;
/* 3582 */         zj7r[i1] = y4r + y7i;
/* 3583 */         zj7i[i1] = y4i - y7r;
/* 3584 */         zj8r[i1] = y3r + y8i;
/* 3585 */         zj8i[i1] = y3i - y8r;
/* 3586 */         zj9r[i1] = y2r + y9i;
/* 3587 */         zj9i[i1] = y2i - y9r;
/* 3588 */         zj10r[i1] = y1r + y10i;
/* 3589 */         zj10i[i1] = y1i - y10r;
/*      */       } 
/* 3591 */       int jt = j10 + 2;
/* 3592 */       j10 = j9 + 2;
/* 3593 */       j9 = j8 + 2;
/* 3594 */       j8 = j7 + 2;
/* 3595 */       j7 = j6 + 2;
/* 3596 */       j6 = j5 + 2;
/* 3597 */       j5 = j4 + 2;
/* 3598 */       j4 = j3 + 2;
/* 3599 */       j3 = j2 + 2;
/* 3600 */       j2 = j1 + 2;
/* 3601 */       j1 = j0 + 2;
/* 3602 */       j0 = jt;
/*      */     }  } private static void pfa13b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10, int j11, int j12) { float c1; float c2; float c3; float c4; float c5; float c6;
/*      */     float c7;
/*      */     float c8;
/*      */     float c9;
/*      */     float c10;
/*      */     float c11;
/*      */     float c12;
/* 3610 */     if (mu == 1) {
/* 3611 */       c1 = 0.885456F;
/* 3612 */       c2 = 0.56806475F;
/* 3613 */       c3 = 0.12053668F;
/* 3614 */       c4 = -0.3546049F;
/* 3615 */       c5 = -0.7485107F;
/* 3616 */       c6 = -0.97094184F;
/* 3617 */       c7 = 0.46472317F;
/* 3618 */       c8 = 0.82298386F;
/* 3619 */       c9 = 0.99270886F;
/* 3620 */       c10 = 0.9350162F;
/* 3621 */       c11 = 0.66312265F;
/* 3622 */       c12 = 0.23931566F;
/* 3623 */     } else if (mu == 2) {
/* 3624 */       c1 = 0.56806475F;
/* 3625 */       c2 = -0.3546049F;
/* 3626 */       c3 = -0.97094184F;
/* 3627 */       c4 = -0.7485107F;
/* 3628 */       c5 = 0.12053668F;
/* 3629 */       c6 = 0.885456F;
/* 3630 */       c7 = 0.82298386F;
/* 3631 */       c8 = 0.9350162F;
/* 3632 */       c9 = 0.23931566F;
/* 3633 */       c10 = -0.66312265F;
/* 3634 */       c11 = -0.99270886F;
/* 3635 */       c12 = -0.46472317F;
/* 3636 */     } else if (mu == 3) {
/* 3637 */       c1 = 0.12053668F;
/* 3638 */       c2 = -0.97094184F;
/* 3639 */       c3 = -0.3546049F;
/* 3640 */       c4 = 0.885456F;
/* 3641 */       c5 = 0.56806475F;
/* 3642 */       c6 = -0.7485107F;
/* 3643 */       c7 = 0.99270886F;
/* 3644 */       c8 = 0.23931566F;
/* 3645 */       c9 = -0.9350162F;
/* 3646 */       c10 = -0.46472317F;
/* 3647 */       c11 = 0.82298386F;
/* 3648 */       c12 = 0.66312265F;
/* 3649 */     } else if (mu == 4) {
/* 3650 */       c1 = -0.3546049F;
/* 3651 */       c2 = -0.7485107F;
/* 3652 */       c3 = 0.885456F;
/* 3653 */       c4 = 0.12053668F;
/* 3654 */       c5 = -0.97094184F;
/* 3655 */       c6 = 0.56806475F;
/* 3656 */       c7 = 0.9350162F;
/* 3657 */       c8 = -0.66312265F;
/* 3658 */       c9 = -0.46472317F;
/* 3659 */       c10 = 0.99270886F;
/* 3660 */       c11 = -0.23931566F;
/* 3661 */       c12 = -0.82298386F;
/* 3662 */     } else if (mu == 5) {
/* 3663 */       c1 = -0.7485107F;
/* 3664 */       c2 = 0.12053668F;
/* 3665 */       c3 = 0.56806475F;
/* 3666 */       c4 = -0.97094184F;
/* 3667 */       c5 = 0.885456F;
/* 3668 */       c6 = -0.3546049F;
/* 3669 */       c7 = 0.66312265F;
/* 3670 */       c8 = -0.99270886F;
/* 3671 */       c9 = 0.82298386F;
/* 3672 */       c10 = -0.23931566F;
/* 3673 */       c11 = -0.46472317F;
/* 3674 */       c12 = 0.9350162F;
/* 3675 */     } else if (mu == 6) {
/* 3676 */       c1 = -0.97094184F;
/* 3677 */       c2 = 0.885456F;
/* 3678 */       c3 = -0.7485107F;
/* 3679 */       c4 = 0.56806475F;
/* 3680 */       c5 = -0.3546049F;
/* 3681 */       c6 = 0.12053668F;
/* 3682 */       c7 = 0.23931566F;
/* 3683 */       c8 = -0.46472317F;
/* 3684 */       c9 = 0.66312265F;
/* 3685 */       c10 = -0.82298386F;
/* 3686 */       c11 = 0.9350162F;
/* 3687 */       c12 = -0.99270886F;
/* 3688 */     } else if (mu == 7) {
/* 3689 */       c1 = -0.97094184F;
/* 3690 */       c2 = 0.885456F;
/* 3691 */       c3 = -0.7485107F;
/* 3692 */       c4 = 0.56806475F;
/* 3693 */       c5 = -0.3546049F;
/* 3694 */       c6 = 0.12053668F;
/* 3695 */       c7 = -0.23931566F;
/* 3696 */       c8 = 0.46472317F;
/* 3697 */       c9 = -0.66312265F;
/* 3698 */       c10 = 0.82298386F;
/* 3699 */       c11 = -0.9350162F;
/* 3700 */       c12 = 0.99270886F;
/* 3701 */     } else if (mu == 8) {
/* 3702 */       c1 = -0.7485107F;
/* 3703 */       c2 = 0.12053668F;
/* 3704 */       c3 = 0.56806475F;
/* 3705 */       c4 = -0.97094184F;
/* 3706 */       c5 = 0.885456F;
/* 3707 */       c6 = -0.3546049F;
/* 3708 */       c7 = -0.66312265F;
/* 3709 */       c8 = 0.99270886F;
/* 3710 */       c9 = -0.82298386F;
/* 3711 */       c10 = 0.23931566F;
/* 3712 */       c11 = 0.46472317F;
/* 3713 */       c12 = -0.9350162F;
/* 3714 */     } else if (mu == 9) {
/* 3715 */       c1 = -0.3546049F;
/* 3716 */       c2 = -0.7485107F;
/* 3717 */       c3 = 0.885456F;
/* 3718 */       c4 = 0.12053668F;
/* 3719 */       c5 = -0.97094184F;
/* 3720 */       c6 = 0.56806475F;
/* 3721 */       c7 = -0.9350162F;
/* 3722 */       c8 = 0.66312265F;
/* 3723 */       c9 = 0.46472317F;
/* 3724 */       c10 = -0.99270886F;
/* 3725 */       c11 = 0.23931566F;
/* 3726 */       c12 = 0.82298386F;
/* 3727 */     } else if (mu == 10) {
/* 3728 */       c1 = 0.12053668F;
/* 3729 */       c2 = -0.97094184F;
/* 3730 */       c3 = -0.3546049F;
/* 3731 */       c4 = 0.885456F;
/* 3732 */       c5 = 0.56806475F;
/* 3733 */       c6 = -0.7485107F;
/* 3734 */       c7 = -0.99270886F;
/* 3735 */       c8 = -0.23931566F;
/* 3736 */       c9 = 0.9350162F;
/* 3737 */       c10 = 0.46472317F;
/* 3738 */       c11 = -0.82298386F;
/* 3739 */       c12 = -0.66312265F;
/* 3740 */     } else if (mu == 11) {
/* 3741 */       c1 = 0.56806475F;
/* 3742 */       c2 = -0.3546049F;
/* 3743 */       c3 = -0.97094184F;
/* 3744 */       c4 = -0.7485107F;
/* 3745 */       c5 = 0.12053668F;
/* 3746 */       c6 = 0.885456F;
/* 3747 */       c7 = -0.82298386F;
/* 3748 */       c8 = -0.9350162F;
/* 3749 */       c9 = -0.23931566F;
/* 3750 */       c10 = 0.66312265F;
/* 3751 */       c11 = 0.99270886F;
/* 3752 */       c12 = 0.46472317F;
/*      */     } else {
/* 3754 */       c1 = 0.885456F;
/* 3755 */       c2 = 0.56806475F;
/* 3756 */       c3 = 0.12053668F;
/* 3757 */       c4 = -0.3546049F;
/* 3758 */       c5 = -0.7485107F;
/* 3759 */       c6 = -0.97094184F;
/* 3760 */       c7 = -0.46472317F;
/* 3761 */       c8 = -0.82298386F;
/* 3762 */       c9 = -0.99270886F;
/* 3763 */       c10 = -0.9350162F;
/* 3764 */       c11 = -0.66312265F;
/* 3765 */       c12 = -0.23931566F;
/*      */     } 
/* 3767 */     for (int i = 0; i < m; i++) {
/* 3768 */       float[] zj0r = z[j0];
/* 3769 */       float[] zj0i = z[j0 + 1];
/* 3770 */       float[] zj1r = z[j1];
/* 3771 */       float[] zj1i = z[j1 + 1];
/* 3772 */       float[] zj2r = z[j2];
/* 3773 */       float[] zj2i = z[j2 + 1];
/* 3774 */       float[] zj3r = z[j3];
/* 3775 */       float[] zj3i = z[j3 + 1];
/* 3776 */       float[] zj4r = z[j4];
/* 3777 */       float[] zj4i = z[j4 + 1];
/* 3778 */       float[] zj5r = z[j5];
/* 3779 */       float[] zj5i = z[j5 + 1];
/* 3780 */       float[] zj6r = z[j6];
/* 3781 */       float[] zj6i = z[j6 + 1];
/* 3782 */       float[] zj7r = z[j7];
/* 3783 */       float[] zj7i = z[j7 + 1];
/* 3784 */       float[] zj8r = z[j8];
/* 3785 */       float[] zj8i = z[j8 + 1];
/* 3786 */       float[] zj9r = z[j9];
/* 3787 */       float[] zj9i = z[j9 + 1];
/* 3788 */       float[] zj10r = z[j10];
/* 3789 */       float[] zj10i = z[j10 + 1];
/* 3790 */       float[] zj11r = z[j11];
/* 3791 */       float[] zj11i = z[j11 + 1];
/* 3792 */       float[] zj12r = z[j12];
/* 3793 */       float[] zj12i = z[j12 + 1];
/* 3794 */       for (int i1 = 0; i1 < n1; i1++) {
/* 3795 */         float t1r = zj1r[i1] + zj12r[i1];
/* 3796 */         float t1i = zj1i[i1] + zj12i[i1];
/* 3797 */         float t2r = zj2r[i1] + zj11r[i1];
/* 3798 */         float t2i = zj2i[i1] + zj11i[i1];
/* 3799 */         float t3r = zj3r[i1] + zj10r[i1];
/* 3800 */         float t3i = zj3i[i1] + zj10i[i1];
/* 3801 */         float t4r = zj4r[i1] + zj9r[i1];
/* 3802 */         float t4i = zj4i[i1] + zj9i[i1];
/* 3803 */         float t5r = zj5r[i1] + zj8r[i1];
/* 3804 */         float t5i = zj5i[i1] + zj8i[i1];
/* 3805 */         float t6r = zj6r[i1] + zj7r[i1];
/* 3806 */         float t6i = zj6i[i1] + zj7i[i1];
/* 3807 */         float t7r = zj1r[i1] - zj12r[i1];
/* 3808 */         float t7i = zj1i[i1] - zj12i[i1];
/* 3809 */         float t8r = zj2r[i1] - zj11r[i1];
/* 3810 */         float t8i = zj2i[i1] - zj11i[i1];
/* 3811 */         float t9r = zj3r[i1] - zj10r[i1];
/* 3812 */         float t9i = zj3i[i1] - zj10i[i1];
/* 3813 */         float t10r = zj4r[i1] - zj9r[i1];
/* 3814 */         float t10i = zj4i[i1] - zj9i[i1];
/* 3815 */         float t11r = zj5r[i1] - zj8r[i1];
/* 3816 */         float t11i = zj5i[i1] - zj8i[i1];
/* 3817 */         float t12r = zj6r[i1] - zj7r[i1];
/* 3818 */         float t12i = zj6i[i1] - zj7i[i1];
/* 3819 */         float t13r = zj0r[i1] - 0.5F * t6r;
/* 3820 */         float t13i = zj0i[i1] - 0.5F * t6i;
/* 3821 */         float t14r = t1r - t6r;
/* 3822 */         float t14i = t1i - t6i;
/* 3823 */         float t15r = t2r - t6r;
/* 3824 */         float t15i = t2i - t6i;
/* 3825 */         float t16r = t3r - t6r;
/* 3826 */         float t16i = t3i - t6i;
/* 3827 */         float t17r = t4r - t6r;
/* 3828 */         float t17i = t4i - t6i;
/* 3829 */         float t18r = t5r - t6r;
/* 3830 */         float t18i = t5i - t6i;
/* 3831 */         float y1r = t13r + c1 * t14r + c2 * t15r + c3 * t16r + c4 * t17r + c5 * t18r;
/* 3832 */         float y1i = t13i + c1 * t14i + c2 * t15i + c3 * t16i + c4 * t17i + c5 * t18i;
/* 3833 */         float y2r = t13r + c2 * t14r + c4 * t15r + c6 * t16r + c5 * t17r + c3 * t18r;
/* 3834 */         float y2i = t13i + c2 * t14i + c4 * t15i + c6 * t16i + c5 * t17i + c3 * t18i;
/* 3835 */         float y3r = t13r + c3 * t14r + c6 * t15r + c4 * t16r + c1 * t17r + c2 * t18r;
/* 3836 */         float y3i = t13i + c3 * t14i + c6 * t15i + c4 * t16i + c1 * t17i + c2 * t18i;
/* 3837 */         float y4r = t13r + c4 * t14r + c5 * t15r + c1 * t16r + c3 * t17r + c6 * t18r;
/* 3838 */         float y4i = t13i + c4 * t14i + c5 * t15i + c1 * t16i + c3 * t17i + c6 * t18i;
/* 3839 */         float y5r = t13r + c5 * t14r + c3 * t15r + c2 * t16r + c6 * t17r + c1 * t18r;
/* 3840 */         float y5i = t13i + c5 * t14i + c3 * t15i + c2 * t16i + c6 * t17i + c1 * t18i;
/* 3841 */         float y6r = t13r + c6 * t14r + c1 * t15r + c5 * t16r + c2 * t17r + c4 * t18r;
/* 3842 */         float y6i = t13i + c6 * t14i + c1 * t15i + c5 * t16i + c2 * t17i + c4 * t18i;
/* 3843 */         float y7r = c12 * t7r - c7 * t8r + c11 * t9r - c8 * t10r + c10 * t11r - c9 * t12r;
/* 3844 */         float y7i = c12 * t7i - c7 * t8i + c11 * t9i - c8 * t10i + c10 * t11i - c9 * t12i;
/* 3845 */         float y8r = c11 * t7r - c9 * t8r + c8 * t9r - c12 * t10r - c7 * t11r + c10 * t12r;
/* 3846 */         float y8i = c11 * t7i - c9 * t8i + c8 * t9i - c12 * t10i - c7 * t11i + c10 * t12i;
/* 3847 */         float y9r = c10 * t7r - c11 * t8r - c7 * t9r + c9 * t10r - c12 * t11r - c8 * t12r;
/* 3848 */         float y9i = c10 * t7i - c11 * t8i - c7 * t9i + c9 * t10i - c12 * t11i - c8 * t12i;
/* 3849 */         float y10r = c9 * t7r + c12 * t8r - c10 * t9r - c7 * t10r + c8 * t11r + c11 * t12r;
/* 3850 */         float y10i = c9 * t7i + c12 * t8i - c10 * t9i - c7 * t10i + c8 * t11i + c11 * t12i;
/* 3851 */         float y11r = c8 * t7r + c10 * t8r + c12 * t9r - c11 * t10r - c9 * t11r - c7 * t12r;
/* 3852 */         float y11i = c8 * t7i + c10 * t8i + c12 * t9i - c11 * t10i - c9 * t11i - c7 * t12i;
/* 3853 */         float y12r = c7 * t7r + c8 * t8r + c9 * t9r + c10 * t10r + c11 * t11r + c12 * t12r;
/* 3854 */         float y12i = c7 * t7i + c8 * t8i + c9 * t9i + c10 * t10i + c11 * t11i + c12 * t12i;
/* 3855 */         zj0r[i1] = zj0r[i1] + t1r + t2r + t3r + t4r + t5r + t6r;
/* 3856 */         zj0i[i1] = zj0i[i1] + t1i + t2i + t3i + t4i + t5i + t6i;
/* 3857 */         zj1r[i1] = y1r - y12i;
/* 3858 */         zj1i[i1] = y1i + y12r;
/* 3859 */         zj2r[i1] = y2r - y11i;
/* 3860 */         zj2i[i1] = y2i + y11r;
/* 3861 */         zj3r[i1] = y3r - y10i;
/* 3862 */         zj3i[i1] = y3i + y10r;
/* 3863 */         zj4r[i1] = y4r - y9i;
/* 3864 */         zj4i[i1] = y4i + y9r;
/* 3865 */         zj5r[i1] = y5r - y8i;
/* 3866 */         zj5i[i1] = y5i + y8r;
/* 3867 */         zj6r[i1] = y6r - y7i;
/* 3868 */         zj6i[i1] = y6i + y7r;
/* 3869 */         zj7r[i1] = y6r + y7i;
/* 3870 */         zj7i[i1] = y6i - y7r;
/* 3871 */         zj8r[i1] = y5r + y8i;
/* 3872 */         zj8i[i1] = y5i - y8r;
/* 3873 */         zj9r[i1] = y4r + y9i;
/* 3874 */         zj9i[i1] = y4i - y9r;
/* 3875 */         zj10r[i1] = y3r + y10i;
/* 3876 */         zj10i[i1] = y3i - y10r;
/* 3877 */         zj11r[i1] = y2r + y11i;
/* 3878 */         zj11i[i1] = y2i - y11r;
/* 3879 */         zj12r[i1] = y1r + y12i;
/* 3880 */         zj12i[i1] = y1i - y12r;
/*      */       } 
/* 3882 */       int jt = j12 + 2;
/* 3883 */       j12 = j11 + 2;
/* 3884 */       j11 = j10 + 2;
/* 3885 */       j10 = j9 + 2;
/* 3886 */       j9 = j8 + 2;
/* 3887 */       j8 = j7 + 2;
/* 3888 */       j7 = j6 + 2;
/* 3889 */       j6 = j5 + 2;
/* 3890 */       j5 = j4 + 2;
/* 3891 */       j4 = j3 + 2;
/* 3892 */       j3 = j2 + 2;
/* 3893 */       j2 = j1 + 2;
/* 3894 */       j1 = j0 + 2;
/* 3895 */       j0 = jt;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pfa16b(int n1, float[][] z, int mu, int m, int j0, int j1, int j2, int j3, int j4, int j5, int j6, int j7, int j8, int j9, int j10, int j11, int j12, int j13, int j14, int j15) {
/*      */     float c1, c2, c3, c4;
/* 3903 */     if (mu == 1) {
/* 3904 */       c1 = 1.0F;
/* 3905 */       c2 = 0.9238795F;
/* 3906 */       c3 = 0.38268343F;
/* 3907 */       c4 = 0.70710677F;
/* 3908 */     } else if (mu == 3) {
/* 3909 */       c1 = -1.0F;
/* 3910 */       c2 = 0.38268343F;
/* 3911 */       c3 = 0.9238795F;
/* 3912 */       c4 = -0.70710677F;
/* 3913 */     } else if (mu == 5) {
/* 3914 */       c1 = 1.0F;
/* 3915 */       c2 = -0.38268343F;
/* 3916 */       c3 = 0.9238795F;
/* 3917 */       c4 = -0.70710677F;
/* 3918 */     } else if (mu == 7) {
/* 3919 */       c1 = -1.0F;
/* 3920 */       c2 = -0.9238795F;
/* 3921 */       c3 = 0.38268343F;
/* 3922 */       c4 = 0.70710677F;
/* 3923 */     } else if (mu == 9) {
/* 3924 */       c1 = 1.0F;
/* 3925 */       c2 = -0.9238795F;
/* 3926 */       c3 = -0.38268343F;
/* 3927 */       c4 = 0.70710677F;
/* 3928 */     } else if (mu == 11) {
/* 3929 */       c1 = -1.0F;
/* 3930 */       c2 = -0.38268343F;
/* 3931 */       c3 = -0.9238795F;
/* 3932 */       c4 = -0.70710677F;
/* 3933 */     } else if (mu == 13) {
/* 3934 */       c1 = 1.0F;
/* 3935 */       c2 = 0.38268343F;
/* 3936 */       c3 = -0.9238795F;
/* 3937 */       c4 = -0.70710677F;
/*      */     } else {
/* 3939 */       c1 = -1.0F;
/* 3940 */       c2 = 0.9238795F;
/* 3941 */       c3 = -0.38268343F;
/* 3942 */       c4 = 0.70710677F;
/*      */     } 
/* 3944 */     float c5 = c1 * c4;
/* 3945 */     float c6 = c1 * c3;
/* 3946 */     float c7 = c1 * c2;
/* 3947 */     for (int i = 0; i < m; i++) {
/* 3948 */       float[] zj0r = z[j0];
/* 3949 */       float[] zj0i = z[j0 + 1];
/* 3950 */       float[] zj1r = z[j1];
/* 3951 */       float[] zj1i = z[j1 + 1];
/* 3952 */       float[] zj2r = z[j2];
/* 3953 */       float[] zj2i = z[j2 + 1];
/* 3954 */       float[] zj3r = z[j3];
/* 3955 */       float[] zj3i = z[j3 + 1];
/* 3956 */       float[] zj4r = z[j4];
/* 3957 */       float[] zj4i = z[j4 + 1];
/* 3958 */       float[] zj5r = z[j5];
/* 3959 */       float[] zj5i = z[j5 + 1];
/* 3960 */       float[] zj6r = z[j6];
/* 3961 */       float[] zj6i = z[j6 + 1];
/* 3962 */       float[] zj7r = z[j7];
/* 3963 */       float[] zj7i = z[j7 + 1];
/* 3964 */       float[] zj8r = z[j8];
/* 3965 */       float[] zj8i = z[j8 + 1];
/* 3966 */       float[] zj9r = z[j9];
/* 3967 */       float[] zj9i = z[j9 + 1];
/* 3968 */       float[] zj10r = z[j10];
/* 3969 */       float[] zj10i = z[j10 + 1];
/* 3970 */       float[] zj11r = z[j11];
/* 3971 */       float[] zj11i = z[j11 + 1];
/* 3972 */       float[] zj12r = z[j12];
/* 3973 */       float[] zj12i = z[j12 + 1];
/* 3974 */       float[] zj13r = z[j13];
/* 3975 */       float[] zj13i = z[j13 + 1];
/* 3976 */       float[] zj14r = z[j14];
/* 3977 */       float[] zj14i = z[j14 + 1];
/* 3978 */       float[] zj15r = z[j15];
/* 3979 */       float[] zj15i = z[j15 + 1];
/* 3980 */       for (int i1 = 0; i1 < n1; i1++) {
/* 3981 */         float t1r = zj0r[i1] + zj8r[i1];
/* 3982 */         float t1i = zj0i[i1] + zj8i[i1];
/* 3983 */         float t2r = zj4r[i1] + zj12r[i1];
/* 3984 */         float t2i = zj4i[i1] + zj12i[i1];
/* 3985 */         float t3r = zj0r[i1] - zj8r[i1];
/* 3986 */         float t3i = zj0i[i1] - zj8i[i1];
/* 3987 */         float t4r = c1 * (zj4r[i1] - zj12r[i1]);
/* 3988 */         float t4i = c1 * (zj4i[i1] - zj12i[i1]);
/* 3989 */         float t5r = t1r + t2r;
/* 3990 */         float t5i = t1i + t2i;
/* 3991 */         float t6r = t1r - t2r;
/* 3992 */         float t6i = t1i - t2i;
/* 3993 */         float t7r = zj1r[i1] + zj9r[i1];
/* 3994 */         float t7i = zj1i[i1] + zj9i[i1];
/* 3995 */         float t8r = zj5r[i1] + zj13r[i1];
/* 3996 */         float t8i = zj5i[i1] + zj13i[i1];
/* 3997 */         float t9r = zj1r[i1] - zj9r[i1];
/* 3998 */         float t9i = zj1i[i1] - zj9i[i1];
/* 3999 */         float t10r = zj5r[i1] - zj13r[i1];
/* 4000 */         float t10i = zj5i[i1] - zj13i[i1];
/* 4001 */         float t11r = t7r + t8r;
/* 4002 */         float t11i = t7i + t8i;
/* 4003 */         float t12r = t7r - t8r;
/* 4004 */         float t12i = t7i - t8i;
/* 4005 */         float t13r = zj2r[i1] + zj10r[i1];
/* 4006 */         float t13i = zj2i[i1] + zj10i[i1];
/* 4007 */         float t14r = zj6r[i1] + zj14r[i1];
/* 4008 */         float t14i = zj6i[i1] + zj14i[i1];
/* 4009 */         float t15r = zj2r[i1] - zj10r[i1];
/* 4010 */         float t15i = zj2i[i1] - zj10i[i1];
/* 4011 */         float t16r = zj6r[i1] - zj14r[i1];
/* 4012 */         float t16i = zj6i[i1] - zj14i[i1];
/* 4013 */         float t17r = t13r + t14r;
/* 4014 */         float t17i = t13i + t14i;
/* 4015 */         float t18r = c4 * (t15r - t16r);
/* 4016 */         float t18i = c4 * (t15i - t16i);
/* 4017 */         float t19r = c5 * (t15r + t16r);
/* 4018 */         float t19i = c5 * (t15i + t16i);
/* 4019 */         float t20r = c1 * (t13r - t14r);
/* 4020 */         float t20i = c1 * (t13i - t14i);
/* 4021 */         float t21r = zj3r[i1] + zj11r[i1];
/* 4022 */         float t21i = zj3i[i1] + zj11i[i1];
/* 4023 */         float t22r = zj7r[i1] + zj15r[i1];
/* 4024 */         float t22i = zj7i[i1] + zj15i[i1];
/* 4025 */         float t23r = zj3r[i1] - zj11r[i1];
/* 4026 */         float t23i = zj3i[i1] - zj11i[i1];
/* 4027 */         float t24r = zj7r[i1] - zj15r[i1];
/* 4028 */         float t24i = zj7i[i1] - zj15i[i1];
/* 4029 */         float t25r = t21r + t22r;
/* 4030 */         float t25i = t21i + t22i;
/* 4031 */         float t26r = t21r - t22r;
/* 4032 */         float t26i = t21i - t22i;
/* 4033 */         float t27r = t9r + t24r;
/* 4034 */         float t27i = t9i + t24i;
/* 4035 */         float t28r = t10r + t23r;
/* 4036 */         float t28i = t10i + t23i;
/* 4037 */         float t29r = t9r - t24r;
/* 4038 */         float t29i = t9i - t24i;
/* 4039 */         float t30r = t10r - t23r;
/* 4040 */         float t30i = t10i - t23i;
/* 4041 */         float t31r = t5r + t17r;
/* 4042 */         float t31i = t5i + t17i;
/* 4043 */         float t32r = t11r + t25r;
/* 4044 */         float t32i = t11i + t25i;
/* 4045 */         float t33r = t3r + t18r;
/* 4046 */         float t33i = t3i + t18i;
/* 4047 */         float t34r = c2 * t29r - c6 * t30r;
/* 4048 */         float t34i = c2 * t29i - c6 * t30i;
/* 4049 */         float t35r = t3r - t18r;
/* 4050 */         float t35i = t3i - t18i;
/* 4051 */         float t36r = c7 * t27r - c3 * t28r;
/* 4052 */         float t36i = c7 * t27i - c3 * t28i;
/* 4053 */         float t37r = t4r + t19r;
/* 4054 */         float t37i = t4i + t19i;
/* 4055 */         float t38r = c3 * t27r + c7 * t28r;
/* 4056 */         float t38i = c3 * t27i + c7 * t28i;
/* 4057 */         float t39r = t4r - t19r;
/* 4058 */         float t39i = t4i - t19i;
/* 4059 */         float t40r = c6 * t29r + c2 * t30r;
/* 4060 */         float t40i = c6 * t29i + c2 * t30i;
/* 4061 */         float t41r = c4 * (t12r - t26r);
/* 4062 */         float t41i = c4 * (t12i - t26i);
/* 4063 */         float t42r = c5 * (t12r + t26r);
/* 4064 */         float t42i = c5 * (t12i + t26i);
/* 4065 */         float y1r = t33r + t34r;
/* 4066 */         float y1i = t33i + t34i;
/* 4067 */         float y2r = t6r + t41r;
/* 4068 */         float y2i = t6i + t41i;
/* 4069 */         float y3r = t35r + t40r;
/* 4070 */         float y3i = t35i + t40i;
/* 4071 */         float y4r = t5r - t17r;
/* 4072 */         float y4i = t5i - t17i;
/* 4073 */         float y5r = t35r - t40r;
/* 4074 */         float y5i = t35i - t40i;
/* 4075 */         float y6r = t6r - t41r;
/* 4076 */         float y6i = t6i - t41i;
/* 4077 */         float y7r = t33r - t34r;
/* 4078 */         float y7i = t33i - t34i;
/* 4079 */         float y9r = t38r - t37r;
/* 4080 */         float y9i = t38i - t37i;
/* 4081 */         float y10r = t42r - t20r;
/* 4082 */         float y10i = t42i - t20i;
/* 4083 */         float y11r = t36r + t39r;
/* 4084 */         float y11i = t36i + t39i;
/* 4085 */         float y12r = c1 * (t11r - t25r);
/* 4086 */         float y12i = c1 * (t11i - t25i);
/* 4087 */         float y13r = t36r - t39r;
/* 4088 */         float y13i = t36i - t39i;
/* 4089 */         float y14r = t42r + t20r;
/* 4090 */         float y14i = t42i + t20i;
/* 4091 */         float y15r = t38r + t37r;
/* 4092 */         float y15i = t38i + t37i;
/* 4093 */         zj0r[i1] = t31r + t32r;
/* 4094 */         zj0i[i1] = t31i + t32i;
/* 4095 */         zj1r[i1] = y1r - y15i;
/* 4096 */         zj1i[i1] = y1i + y15r;
/* 4097 */         zj2r[i1] = y2r - y14i;
/* 4098 */         zj2i[i1] = y2i + y14r;
/* 4099 */         zj3r[i1] = y3r - y13i;
/* 4100 */         zj3i[i1] = y3i + y13r;
/* 4101 */         zj4r[i1] = y4r - y12i;
/* 4102 */         zj4i[i1] = y4i + y12r;
/* 4103 */         zj5r[i1] = y5r - y11i;
/* 4104 */         zj5i[i1] = y5i + y11r;
/* 4105 */         zj6r[i1] = y6r - y10i;
/* 4106 */         zj6i[i1] = y6i + y10r;
/* 4107 */         zj7r[i1] = y7r - y9i;
/* 4108 */         zj7i[i1] = y7i + y9r;
/* 4109 */         zj8r[i1] = t31r - t32r;
/* 4110 */         zj8i[i1] = t31i - t32i;
/* 4111 */         zj9r[i1] = y7r + y9i;
/* 4112 */         zj9i[i1] = y7i - y9r;
/* 4113 */         zj10r[i1] = y6r + y10i;
/* 4114 */         zj10i[i1] = y6i - y10r;
/* 4115 */         zj11r[i1] = y5r + y11i;
/* 4116 */         zj11i[i1] = y5i - y11r;
/* 4117 */         zj12r[i1] = y4r + y12i;
/* 4118 */         zj12i[i1] = y4i - y12r;
/* 4119 */         zj13r[i1] = y3r + y13i;
/* 4120 */         zj13i[i1] = y3i - y13r;
/* 4121 */         zj14r[i1] = y2r + y14i;
/* 4122 */         zj14i[i1] = y2i - y14r;
/* 4123 */         zj15r[i1] = y1r + y15i;
/* 4124 */         zj15i[i1] = y1i - y15r;
/*      */       } 
/* 4126 */       int jt = j15 + 2;
/* 4127 */       j15 = j14 + 2;
/* 4128 */       j14 = j13 + 2;
/* 4129 */       j13 = j12 + 2;
/* 4130 */       j12 = j11 + 2;
/* 4131 */       j11 = j10 + 2;
/* 4132 */       j10 = j9 + 2;
/* 4133 */       j9 = j8 + 2;
/* 4134 */       j8 = j7 + 2;
/* 4135 */       j7 = j6 + 2;
/* 4136 */       j6 = j5 + 2;
/* 4137 */       j5 = j4 + 2;
/* 4138 */       j4 = j3 + 2;
/* 4139 */       j3 = j2 + 2;
/* 4140 */       j2 = j1 + 2;
/* 4141 */       j1 = j0 + 2;
/* 4142 */       j0 = jt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4193 */   private static final int[] _kfac = new int[] { 16, 13, 11, 9, 8, 7, 5, 4, 3, 2 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NTABLE = 240;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4203 */   private static final int[] _ntable = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 21, 22, 24, 26, 28, 30, 33, 35, 36, 39, 40, 42, 44, 45, 48, 52, 55, 56, 60, 63, 65, 66, 70, 72, 77, 78, 80, 84, 88, 90, 91, 99, 104, 105, 110, 112, 117, 120, 126, 130, 132, 140, 143, 144, 154, 156, 165, 168, 176, 180, 182, 195, 198, 208, 210, 220, 231, 234, 240, 252, 260, 264, 273, 280, 286, 308, 312, 315, 330, 336, 360, 364, 385, 390, 396, 420, 429, 440, 455, 462, 468, 495, 504, 520, 528, 546, 560, 572, 585, 616, 624, 630, 660, 693, 715, 720, 728, 770, 780, 792, 819, 840, 858, 880, 910, 924, 936, 990, 1001, 1008, 1040, 1092, 1144, 1155, 1170, 1232, 1260, 1287, 1320, 1365, 1386, 1430, 1456, 1540, 1560, 1584, 1638, 1680, 1716, 1820, 1848, 1872, 1980, 2002, 2145, 2184, 2288, 2310, 2340, 2520, 2574, 2640, 2730, 2772, 2860, 3003, 3080, 3120, 3276, 3432, 3465, 3640, 3696, 3960, 4004, 4095, 4290, 4368, 4620, 4680, 5005, 5040, 5148, 5460, 5544, 5720, 6006, 6160, 6435, 6552, 6864, 6930, 7280, 7920, 8008, 8190, 8580, 9009, 9240, 9360, 10010, 10296, 10920, 11088, 11440, 12012, 12870, 13104, 13860, 15015, 16016, 16380, 17160, 18018, 18480, 20020, 20592, 21840, 24024, 25740, 27720, 30030, 32760, 34320, 36036, 40040, 45045, 48048, 51480, 55440, 60060, 65520, 72072, 80080, 90090, 102960, 120120, 144144, 180180, 240240, 360360, 720720 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4232 */   private static final double[] _ctable = new double[] { 1.54844595E-6D, 1.60858985E-6D, 1.73777398E-6D, 1.78300246E-6D, 1.86692603E-6D, 2.02796424E-6D, 2.05593203E-6D, 2.03027471E-6D, 2.13199871E-6D, 2.23464061E-6D, 2.45504197E-6D, 2.24507775E-6D, 2.77484785E-6D, 2.71335681E-6D, 2.60084271E-6D, 2.66712117E-6D, 2.77849063E-6D, 2.84002694E-6D, 3.17837121E-6D, 3.73373597E-6D, 3.15791133E-6D, 4.24124687E-6D, 3.58681599E-6D, 3.74904075E-6D, 4.74708669E-6D, 4.38838644E-6D, 4.01250829E-6D, 5.62735292E-6D, 4.3411639E-6D, 5.17084182E-6D, 5.52020262E-6D, 4.98530294E-6D, 5.2024893E-6D, 6.81986102E-6D, 7.10553295E-6D, 5.93798174E-6D, 5.87481339E-6D, 7.01743322E-6D, 8.61901953E-6D, 8.32813604E-6D, 7.91730898E-6D, 6.8840368E-6D, 1.002803645E-5D, 1.02678457E-5D, 8.19893573E-6D, 8.28260941E-6D, 1.014724144E-5D, 9.34954606E-6D, 1.232645727E-5D, 1.214189591E-5D, 1.269497208E-5D, 1.102202755E-5D, 1.388176589E-5D, 1.172641106E-5D, 1.503375461E-5D, 1.113972204E-5D, 1.364655225E-5D, 1.703912278E-5D, 1.477596306E-5D, 1.424973677E-5D, 2.127456187E-5D, 1.398938399E-5D, 2.00864429E-5D, 1.869909587E-5D, 1.986433148E-5D, 1.651781665E-5D, 2.092824006E-5D, 1.702478496E-5D, 2.499906394E-5D, 2.500343282E-5D, 2.465807389E-5D, 2.632305568E-5D, 2.308853872E-5D, 2.566435179E-5D, 2.996843066E-5D, 3.179869821E-5D, 2.372351388E-5D, 2.578195392E-5D, 3.236648622E-5D, 3.062192175E-5D, 3.688562326E-5D, 2.903319322E-5D, 4.464405117E-5D, 3.835181037E-5D, 3.890755813E-5D, 3.489790229E-5D, 4.193095941E-5D, 3.661590772E-5D, 3.585050946E-5D, 4.948000296E-5D, 5.19463679E-5D, 5.316664012E-5D, 4.831628715E-5D, 4.470982143E-5D, 6.71179171E-5D, 5.416880764E-5D, 6.503589644E-5D, 6.376341005E-5D, 6.060697752E-5D, 6.481361636E-5D, 5.49474666E-5D, 6.84295036E-5D, 6.725764749E-5D, 7.9550419E-5D, 6.51735139E-5D, 8.783546746E-5D, 8.145918907E-5D, 8.208007212E-5D, 8.453260181E-5D, 7.714824943E-5D, 8.332986646E-5D, 9.686623465E-5D, 1.1742291007E-4D, 8.016979016E-5D, 1.0372863801E-4D, 1.1169975463E-4D, 1.0527145635E-4D, 1.0259693695E-4D, 1.2171112596E-4D, 9.645574497E-5D, 1.4179527113E-4D, 1.1871442125E-4D, 1.4121545403E-4D, 1.2558781115E-4D, 1.2962723272E-4D, 1.4012872534E-4D, 1.7282139776E-4D, 1.2333743842E-4D, 1.5017243965E-4D, 1.5933147632E-4D, 1.8467637839E-4D, 1.6783978549E-4D, 1.7760241178E-4D, 1.8111945022E-4D, 1.5790303508E-4D, 2.1759913091E-4D, 1.7785473273E-4D, 2.1290391156E-4D, 2.1022786937E-4D, 2.5198138131E-4D, 2.2553766468E-4D, 2.2349921892E-4D, 2.2576645627E-4D, 2.2422478451E-4D, 2.6572035023E-4D, 2.1417878529E-4D, 2.8409252164E-4D, 2.8290960452E-4D, 2.6774495388E-4D, 2.8504340401E-4D, 2.8006152125E-4D, 3.7010347376E-4D, 3.7949981053E-4D, 3.3613022319E-4D, 4.0431974162E-4D, 3.6459661264E-4D, 3.535121779E-4D, 3.3107438017E-4D, 4.668997669E-4D, 3.9452432539E-4D, 4.564721969E-4D, 4.2150673401E-4D, 5.0466112371E-4D, 5.5797101449E-4D, 4.7941598851E-4D, 4.9189587426E-4D, 5.2933403805E-4D, 6.056849108E-4D, 5.6200897868E-4D, 6.0222489477E-4D, 5.884253819E-4D, 6.0084033613E-4D, 7.4850523169E-4D, 7.0305370305E-4D, 8.1422764228E-4D, 7.3423753666E-4D, 7.3504587156E-4D, 7.5785092698E-4D, 9.8138167565E-4D, 7.3504587156E-4D, 9.3946503989E-4D, 9.1880733945E-4D, 9.0674513354E-4D, 0.00105810882198D, 0.0012059000602D, 0.00104322916667D, 0.00124255583127D, 0.00113291855204D, 0.00130338541667D, 0.00122432762836D, 0.00130234070221D, 0.0013344437042D, 0.00158214849921D, 0.00153958493467D, 0.00166694421316D, 0.00183424908425D, 0.00157344854674D, 0.00164180327869D, 0.00210620399579D, 0.00198316831683D, 0.00195414634146D, 0.00197145669291D, 0.00228132118451D, 0.00241495778046D, 0.00264248021108D, 0.00243970767357D, 0.00246068796069D, 0.00314937106918D, 0.00341226575809D, 0.00304407294833D, 0.00342979452055D, 0.00391780821918D, 0.00339491525424D, 0.00423467230444D, 0.00427991452991D, 0.00422573839662D, 0.0051358974359D, 0.00532712765957D, 0.00522976501305D, 0.00671812080537D, 0.00644051446945D, 0.00747388059701D, 0.00785490196078D, 0.00890222222222D, 0.01032474226804D, 0.01088586956522D, 0.01131638418079D, 0.01125280898876D, 0.01371232876712D, 0.01390972222222D, 0.01663636363636D, 0.01917142857143D, 0.02201098901099D, 0.02425301204819D, 0.02849295774648D, 0.03531578947368D, 0.04575D, 0.06190909090909D, 0.10542105263158D, 0.24033333333333D }; }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Pfacc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */