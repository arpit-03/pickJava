/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
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
/*      */ public class SteerablePyramid
/*      */ {
/*      */   private static final double THETA0 = 0.0D;
/*      */   private static final double THETA1 = 1.0471975511965976D;
/*      */   private static final double THETA2 = 2.0943951023931953D;
/*      */   private static final double ONE_THIRD = 0.3333333333333333D;
/*      */   private static final float TWO_THIRDS = 0.6666667F;
/*      */   
/*      */   public SteerablePyramid() {
/*   92 */     this.ka = 0.6D;
/*   93 */     this.kb = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SteerablePyramid(double ka, double kb) {
/*  103 */     this.ka = ka;
/*  104 */     this.kb = kb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][][][] makePyramid(float[][] x) {
/*  114 */     this.nx2 = x.length;
/*  115 */     this.nx1 = (x[0]).length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  121 */     this.nlev = 1;
/*  122 */     int nlev2 = 1;
/*  123 */     this.n1 = 9;
/*  124 */     this.n2 = 9;
/*  125 */     while (this.nx1 > this.n1) {
/*  126 */       this.n1 = (this.n1 - 1) * 2 + 1;
/*  127 */       this.nlev++;
/*      */     } 
/*  129 */     while (this.nx2 > this.n2) {
/*  130 */       this.n2 = (this.n2 - 1) * 2 + 1;
/*  131 */       nlev2++;
/*      */     } 
/*  133 */     if (this.nlev > nlev2) {
/*  134 */       this.nlev = nlev2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  142 */     float[][][][] spyr = new float[this.nlev + 1][1][1][1];
/*  143 */     for (int lev = 0; lev < this.nlev; lev++) {
/*  144 */       int j = (int)ArrayMath.pow(2.0D, lev);
/*  145 */       int k = (this.n2 - 1) / j + 1;
/*  146 */       int m = (this.n1 - 1) / j + 1;
/*  147 */       spyr[lev] = ArrayMath.zerofloat(m, k, 3);
/*      */     } 
/*  149 */     int lfactor = (int)ArrayMath.pow(2.0D, this.nlev);
/*  150 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/*  151 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/*  152 */     spyr[this.nlev] = ArrayMath.zerofloat(nl1, nl2, 1);
/*  153 */     float[][] cf = ftForward(0, x);
/*  154 */     applyRadial(this.ka, this.kb, cf);
/*  155 */     for (int i = 0; i < this.nlev; i++) {
/*  156 */       if (i > 0) {
/*  157 */         cf = ftForward(i, spyr[i][0]);
/*      */       }
/*  159 */       makePyramidLevel(i, cf, spyr);
/*      */     } 
/*  161 */     return spyr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][][][][] makePyramid(float[][][] x) {
/*  171 */     this.nx3 = x.length;
/*  172 */     this.nx2 = (x[0]).length;
/*  173 */     this.nx1 = (x[0][0]).length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  179 */     this.nlev = 1;
/*  180 */     int nlev2 = 1;
/*  181 */     int nlev3 = 1;
/*  182 */     this.n1 = 9;
/*  183 */     this.n2 = 9;
/*  184 */     this.n3 = 9;
/*  185 */     while (this.nx1 > this.n1) {
/*  186 */       this.n1 = (this.n1 - 1) * 2 + 1;
/*  187 */       this.nlev++;
/*      */     } 
/*  189 */     while (this.nx2 > this.n2) {
/*  190 */       this.n2 = (this.n2 - 1) * 2 + 1;
/*  191 */       nlev2++;
/*      */     } 
/*  193 */     while (this.nx3 > this.n3) {
/*  194 */       this.n3 = (this.n3 - 1) * 2 + 1;
/*  195 */       nlev3++;
/*      */     } 
/*  197 */     if (this.nlev > nlev2) {
/*  198 */       this.nlev = nlev2;
/*      */     }
/*  200 */     if (this.nlev > nlev3) {
/*  201 */       this.nlev = nlev3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  209 */     float[][][][][] spyr = new float[this.nlev + 1][1][1][1][1];
/*  210 */     for (int lev = 0; lev < this.nlev; lev++) {
/*  211 */       int j = (int)ArrayMath.pow(2.0D, lev);
/*  212 */       int k = (this.n3 - 1) / j + 1;
/*  213 */       int m = (this.n2 - 1) / j + 1;
/*  214 */       int n = (this.n1 - 1) / j + 1;
/*  215 */       spyr[lev] = new float[6][1][1][1];
/*  216 */       for (int dir = 0; dir < 6; dir++) {
/*  217 */         spyr[lev][dir] = ArrayMath.zerofloat(n, m, k);
/*      */       }
/*      */     } 
/*  220 */     int lfactor = (int)ArrayMath.pow(2.0D, this.nlev);
/*  221 */     int nl3 = (this.n3 - 1) / lfactor + 1;
/*  222 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/*  223 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/*  224 */     spyr[this.nlev][0] = ArrayMath.zerofloat(nl1, nl2, nl3);
/*  225 */     float[][][] cf = ftForward(0, x);
/*  226 */     applyRadial(this.ka, this.kb, cf);
/*  227 */     for (int i = 0; i < this.nlev; i++) {
/*  228 */       if (i > 0) {
/*  229 */         cf = ftForward(i, spyr[i][0]);
/*      */       }
/*  231 */       makePyramidLevel(i, cf, spyr);
/*      */     } 
/*  233 */     return spyr;
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
/*      */   public float[][] sumPyramid(boolean keeplow, float[][][][] spyr) {
/*  247 */     if (!keeplow) {
/*  248 */       ArrayMath.zero(spyr[this.nlev][0]);
/*      */     }
/*      */ 
/*      */     
/*  252 */     int lfactor = (int)ArrayMath.pow(2.0D, (this.nlev - 1));
/*  253 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/*  254 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/*  255 */     for (int i = 0; i < this.nlev; i++) {
/*  256 */       int lev = this.nlev - i - 1;
/*  257 */       for (int dir = 1; dir < 3; dir++) {
/*  258 */         ArrayMath.add(spyr[lev][0], spyr[lev][dir], spyr[lev][0]);
/*      */       }
/*  260 */       int m2 = (nl2 - 1) / 2 + 1;
/*  261 */       int m1 = (nl1 - 1) / 2 + 1;
/*  262 */       SincInterpolator si = new SincInterpolator();
/*  263 */       si.setExtrapolation(SincInterpolator.Extrapolation.CONSTANT);
/*  264 */       for (int i2 = 0; i2 < nl2; i2++) {
/*  265 */         for (int i1 = 0; i1 < nl1; i1++) {
/*  266 */           spyr[lev][0][i2][i1] = spyr[lev][0][i2][i1] + si.interpolate(m1, 2.0D, 0.0D, m2, 2.0D, 0.0D, spyr[lev + 1][0], i1, i2);
/*      */         }
/*      */       } 
/*      */       
/*  270 */       nl2 = (nl2 - 1) * 2 + 1;
/*  271 */       nl1 = (nl1 - 1) * 2 + 1;
/*      */     } 
/*  273 */     float[][] y = ArrayMath.zerofloat(this.nx1, this.nx2);
/*  274 */     ArrayMath.copy(this.nx1, this.nx2, spyr[0][0], y);
/*  275 */     return y;
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
/*      */   public float[][][] sumPyramid(boolean keeplow, float[][][][][] spyr) {
/*  289 */     if (!keeplow) {
/*  290 */       ArrayMath.zero(spyr[this.nlev][0]);
/*      */     }
/*      */ 
/*      */     
/*  294 */     int lfactor = (int)ArrayMath.pow(2.0D, (this.nlev - 1));
/*  295 */     int nl3 = (this.n3 - 1) / lfactor + 1;
/*  296 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/*  297 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/*  298 */     for (int i = 0; i < this.nlev; i++) {
/*  299 */       int lev = this.nlev - i - 1;
/*  300 */       for (int dir = 1; dir < 6; dir++) {
/*  301 */         ArrayMath.add(spyr[lev][0], spyr[lev][dir], spyr[lev][0]);
/*      */       }
/*  303 */       int m3 = (nl3 - 1) / 2 + 1;
/*  304 */       int m2 = (nl2 - 1) / 2 + 1;
/*  305 */       int m1 = (nl1 - 1) / 2 + 1;
/*      */       
/*  307 */       float[] lo11 = ArrayMath.zerofloat(m1);
/*  308 */       float[] lo12 = ArrayMath.zerofloat(m2);
/*  309 */       float[] lo13 = ArrayMath.zerofloat(m3);
/*  310 */       SincInterpolator si = SincInterpolator.fromErrorAndFrequency(0.001D, 0.4D);
/*  311 */       si.setExtrapolation(SincInterpolator.Extrapolation.CONSTANT); int i3;
/*  312 */       for (i3 = 0; i3 < nl3; i3 += 2) {
/*  313 */         int j3 = i3 / 2; int j;
/*  314 */         for (j = 0; j < nl2; j += 2) {
/*  315 */           int j2 = j / 2; int i1;
/*  316 */           for (i1 = 0; i1 < nl1; i1 += 2) {
/*  317 */             int j1 = i1 / 2;
/*  318 */             lo11[j1] = spyr[lev + 1][0][j3][j2][j1];
/*  319 */             spyr[lev][1][i3][j][i1] = lo11[j1];
/*      */           } 
/*  321 */           for (i1 = 1; i1 < nl1; i1 += 2) {
/*  322 */             spyr[lev][1][i3][j][i1] = si.interpolate(m1, 2.0D, 0.0D, lo11, i1);
/*      */           }
/*      */         } 
/*      */       } 
/*  326 */       for (i3 = 0; i3 < nl3; i3 += 2) {
/*  327 */         for (int i1 = 0; i1 < nl1; i1++) {
/*  328 */           int j; for (j = 0; j < nl2; j += 2) {
/*  329 */             int j2 = j / 2;
/*  330 */             lo12[j2] = spyr[lev][1][i3][j][i1];
/*      */           } 
/*  332 */           for (j = 1; j < nl2; j += 2) {
/*  333 */             spyr[lev][1][i3][j][i1] = si.interpolate(m2, 2.0D, 0.0D, lo12, j);
/*      */           }
/*      */         } 
/*      */       } 
/*  337 */       for (int i2 = 0; i2 < nl2; i2++) {
/*  338 */         for (int i1 = 0; i1 < nl1; i1++) {
/*  339 */           int j; for (j = 0; j < nl3; j += 2) {
/*  340 */             int j3 = j / 2;
/*  341 */             lo13[j3] = spyr[lev][1][j][i2][i1];
/*      */           } 
/*  343 */           for (j = 1; j < nl3; j += 2) {
/*  344 */             spyr[lev][1][j][i2][i1] = si.interpolate(m3, 2.0D, 0.0D, lo13, j);
/*      */           }
/*      */         } 
/*      */       } 
/*  348 */       ArrayMath.add(spyr[lev][0], spyr[lev][1], spyr[lev][0]);
/*  349 */       nl3 = (nl3 - 1) * 2 + 1;
/*  350 */       nl2 = (nl2 - 1) * 2 + 1;
/*  351 */       nl1 = (nl1 - 1) * 2 + 1;
/*      */     } 
/*  353 */     float[][][] y = ArrayMath.zerofloat(this.nx1, this.nx2, this.nx3);
/*  354 */     ArrayMath.copy(this.nx1, this.nx2, this.nx3, spyr[0][0], y);
/*  355 */     return y;
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
/*      */   public float[][][][] estimateAttributes(double sigma, float[][][][] spyr) {
/*  394 */     double sigmaa = 2.0D * sigma;
/*  395 */     double sigmac = 0.5D * sigma;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  401 */     float[][][][] attr = new float[this.nlev][2][1][1];
/*  402 */     for (int lev = 0; lev < this.nlev; lev++) {
/*  403 */       int lfactor = (int)ArrayMath.pow(2.0D, lev);
/*  404 */       int nl2 = (this.n2 - 1) / lfactor + 1;
/*  405 */       int nl1 = (this.n1 - 1) / lfactor + 1;
/*  406 */       for (int j = 0; j < 2; j++) {
/*  407 */         attr[lev][j] = ArrayMath.zerofloat(nl1, nl2);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  413 */     for (int levb = 0; levb < this.nlev; levb++) {
/*  414 */       int leva = levb - 1;
/*  415 */       int levc = levb + 1;
/*  416 */       float[][][] pqjb = pqjShiftSmooth(sigma, levb, spyr);
/*  417 */       int nl2 = (pqjb[0]).length;
/*  418 */       int nl1 = (pqjb[0][0]).length;
/*  419 */       if (leva >= 0) {
/*  420 */         float[][][] pqja = pqjShiftSmooth(sigmaa, leva, spyr);
/*  421 */         for (int i = 0; i < nl2; i++) {
/*  422 */           for (int i1b = 0; i1b < nl1; i1b++) {
/*  423 */             int i1a = 2 * i1b;
/*  424 */             int i2a = 2 * i;
/*  425 */             for (int j = 0; j < 3; j++) {
/*  426 */               pqjb[j][i][i1b] = pqjb[j][i][i1b] + pqja[j][i2a][i1a];
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  431 */       if (levc < this.nlev) {
/*  432 */         float[][][] pqja = pqjShiftSmooth(sigmac, levc, spyr);
/*  433 */         for (int i = 0; i < nl2; i++) {
/*  434 */           for (int i1b = 0; i1b < nl1; i1b++) {
/*  435 */             int i1c = (int)(ArrayMath.round(i1b) * 0.5D);
/*  436 */             int i2c = (int)(ArrayMath.round(i) * 0.5D);
/*  437 */             for (int j = 0; j < 3; j++) {
/*  438 */               pqjb[j][i][i1b] = pqjb[j][i][i1b] + pqja[j][i2c][i1c];
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  444 */       for (int i2b = 0; i2b < nl2; i2b++) {
/*  445 */         for (int i1b = 0; i1b < nl1; i1b++) {
/*  446 */           double a0 = pqjb[0][i2b][i1b];
/*  447 */           double a1 = pqjb[1][i2b][i1b];
/*  448 */           double a2 = pqjb[2][i2b][i1b];
/*  449 */           double[][] feout = findExtrema(a0, a1, a2);
/*  450 */           attr[levb][0][i2b][i1b] = (float)feout[0][0];
/*  451 */           double e0 = eval0(a0, a1, a2, feout[0][0]);
/*  452 */           double e1 = eval0(a0, a1, a2, feout[1][0]);
/*  453 */           attr[levb][1][i2b][i1b] = (float)((e0 - e1) / e0);
/*      */         } 
/*      */       } 
/*      */     } 
/*  457 */     return attr;
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
/*      */   public float[][][][][] estimateAttributes(boolean forlinear, double sigma, float[][][][][] spyr) {
/*  503 */     double sigmaa = 2.0D * sigma;
/*  504 */     double sigmac = 0.5D * sigma;
/*  505 */     double[] f = ArrayMath.zerodouble(6);
/*  506 */     double[][] abcf = ArrayMath.zerodouble(4, 3);
/*      */ 
/*      */     
/*  509 */     int abcindx = 2;
/*  510 */     int e0indx = 2;
/*  511 */     int e1indx = 1;
/*  512 */     this.statelinear = forlinear;
/*  513 */     if (forlinear) {
/*  514 */       abcindx = 0;
/*  515 */       e0indx = 1;
/*  516 */       e1indx = 0;
/*      */     } 
/*      */     
/*  519 */     float[][][][][] attr = new float[this.nlev][4][1][1][1];
/*  520 */     for (int lev = 0; lev < this.nlev; lev++) {
/*  521 */       int lfactor = (int)ArrayMath.pow(2.0D, lev);
/*  522 */       int nl3 = (this.n3 - 1) / lfactor + 1;
/*  523 */       int nl2 = (this.n2 - 1) / lfactor + 1;
/*  524 */       int nl1 = (this.n1 - 1) / lfactor + 1;
/*  525 */       for (int j = 0; j < 4; j++) {
/*  526 */         attr[lev][j] = ArrayMath.zerofloat(nl1, nl2, nl3);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  532 */     for (int levb = 0; levb < this.nlev; levb++) {
/*  533 */       int leva = levb - 1;
/*  534 */       int levc = levb + 1;
/*  535 */       float[][][][] pqjb = pqjShiftSmooth(sigma, levb, spyr);
/*  536 */       int nl3 = (pqjb[0]).length;
/*  537 */       int nl2 = (pqjb[0][0]).length;
/*  538 */       int nl1 = (pqjb[0][0][0]).length;
/*  539 */       if (leva >= 0) {
/*  540 */         float[][][][] pqja = pqjShiftSmooth(sigmaa, leva, spyr);
/*  541 */         for (int i = 0; i < nl3; i++) {
/*  542 */           for (int i2b = 0; i2b < nl2; i2b++) {
/*  543 */             for (int i1b = 0; i1b < nl1; i1b++) {
/*  544 */               int i3a = 2 * i;
/*  545 */               int i2a = 2 * i2b;
/*  546 */               int i1a = 2 * i1b;
/*  547 */               for (int j = 0; j < 6; j++) {
/*  548 */                 pqjb[j][i][i2b][i1b] = pqjb[j][i][i2b][i1b] + pqja[j][i3a][i2a][i1a];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  554 */       if (levc < this.nlev) {
/*  555 */         float[][][][] pqja = pqjShiftSmooth(sigmac, levc, spyr);
/*  556 */         for (int i = 0; i < nl3; i++) {
/*  557 */           for (int i2b = 0; i2b < nl2; i2b++) {
/*  558 */             for (int i1b = 0; i1b < nl1; i1b++) {
/*  559 */               int i3c = (int)(ArrayMath.round(i) * 0.5D);
/*  560 */               int i2c = (int)(ArrayMath.round(i2b) * 0.5D);
/*  561 */               int i1c = (int)(ArrayMath.round(i1b) * 0.5D);
/*  562 */               for (int j = 0; j < 6; j++) {
/*  563 */                 pqjb[j][i][i2b][i1b] = pqjb[j][i][i2b][i1b] + pqja[j][i3c][i2c][i1c];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  570 */       for (int i3b = 0; i3b < nl3; i3b++) {
/*  571 */         for (int i2b = 0; i2b < nl2; i2b++) {
/*  572 */           for (int i1b = 0; i1b < nl1; i1b++) {
/*  573 */             for (int j = 0; j < 6; j++) {
/*  574 */               f[j] = pqjb[j][i3b][i2b][i1b];
/*      */             }
/*  576 */             findCriticalPoints(f, abcf);
/*  577 */             attr[levb][0][i3b][i2b][i1b] = (float)abcf[abcindx][0];
/*  578 */             attr[levb][1][i3b][i2b][i1b] = (float)abcf[abcindx][1];
/*  579 */             attr[levb][2][i3b][i2b][i1b] = (float)abcf[abcindx][2];
/*  580 */             attr[levb][3][i3b][i2b][i1b] = (float)((abcf[e0indx][3] - abcf[e1indx][3]) / abcf[2][3]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  586 */     return attr;
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
/*      */   public void steerScale(boolean forlinear, int linpowr, float k, float thresh, float[][][][][] attr, float[][][][][] spyr) {
/*  611 */     float scal = 0.0F;
/*  612 */     int j0 = 0;
/*  613 */     int j1 = 0;
/*  614 */     int j2 = 0;
/*  615 */     float signb = 1.0F;
/*  616 */     for (int lev = 0; lev < this.nlev; lev++) {
/*  617 */       int lfactor = (int)ArrayMath.pow(2.0D, lev);
/*  618 */       int nl3 = (this.n3 - 1) / lfactor + 1;
/*  619 */       int nl2 = (this.n2 - 1) / lfactor + 1;
/*  620 */       int nl1 = (this.n1 - 1) / lfactor + 1;
/*      */ 
/*      */       
/*  623 */       if (this.statelinear) {
/*  624 */         float[][][] p = ArrayMath.add(spyr[lev][0], spyr[lev][1]); int i;
/*  625 */         for (i = 2; i < 6; i++) {
/*  626 */           ArrayMath.add(p, spyr[lev][i], p);
/*      */         }
/*  628 */         ArrayMath.mul(p, 0.5F, p);
/*  629 */         for (i = 0; i < 6; i++) {
/*  630 */           ArrayMath.sub(p, spyr[lev][i], spyr[lev][i]);
/*      */         }
/*      */       } 
/*      */       
/*  634 */       for (int dir = 0; dir < 6; dir++) {
/*  635 */         if (dir == 0) { j0 = 0; j1 = 1; j2 = 2; signb = 1.0F; }
/*  636 */         else if (dir == 1) { j0 = 0; j1 = 1; j2 = 2; signb = -1.0F; }
/*  637 */         else if (dir == 2) { j0 = 2; j1 = 0; j2 = 1; signb = 1.0F; }
/*  638 */         else if (dir == 3) { j0 = 2; j1 = 0; j2 = 1; signb = -1.0F; }
/*  639 */         else if (dir == 4) { j0 = 1; j1 = 2; j2 = 0; signb = 1.0F; }
/*  640 */         else if (dir == 5) { j0 = 1; j1 = 2; j2 = 0; signb = -1.0F; }
/*  641 */          for (int i3 = 0; i3 < nl3; i3++) {
/*  642 */           for (int i2 = 0; i2 < nl2; i2++) {
/*  643 */             for (int i1 = 0; i1 < nl1; i1++) {
/*  644 */               float ai = attr[lev][j0][i3][i2][i1];
/*  645 */               float bi = attr[lev][j1][i3][i2][i1] * signb;
/*  646 */               float ci = attr[lev][j2][i3][i2][i1];
/*  647 */               float wi = (ai + bi) * (ai + bi) - ci * ci;
/*  648 */               if (linpowr == 0) {
/*  649 */                 scal = 1.0F;
/*      */               }
/*  651 */               else if (linpowr == 1) {
/*  652 */                 scal = attr[lev][3][i3][i2][i1];
/*      */               }
/*  654 */               else if (linpowr > 1 && linpowr < 99) {
/*  655 */                 scal = ArrayMath.pow(attr[lev][3][i3][i2][i1], linpowr);
/*      */               }
/*  657 */               else if (linpowr == 99) {
/*  658 */                 scal = 1.0F / (1.0F + ArrayMath.exp(k * (thresh - attr[lev][3][i3][i2][i1])));
/*      */               } 
/*  660 */               spyr[lev][dir][i3][i2][i1] = spyr[lev][dir][i3][i2][i1] * scal * wi;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
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
/*      */   public void steerScale(int linpowr, float k, float thresh, float[][][][] attr, float[][][][] spyr) {
/*  685 */     float scal = 0.0F;
/*      */ 
/*      */     
/*  688 */     for (int lev = 0; lev < this.nlev; lev++) {
/*  689 */       int nl2 = (spyr[lev][0]).length;
/*  690 */       int nl1 = (spyr[lev][0][0]).length;
/*  691 */       for (int i2 = 0; i2 < nl2; i2++) {
/*  692 */         for (int i1 = 0; i1 < nl1; i1++) {
/*  693 */           double theta = attr[lev][0][i2][i1];
/*  694 */           float w0 = 0.5F * (float)(1.0D + 2.0D * ArrayMath.cos(2.0D * (theta - 0.0D)));
/*  695 */           float w1 = 0.5F * (float)(1.0D + 2.0D * ArrayMath.cos(2.0D * (theta - 1.0471975511965976D)));
/*  696 */           float w2 = 0.5F * (float)(1.0D + 2.0D * ArrayMath.cos(2.0D * (theta - 2.0943951023931953D)));
/*  697 */           if (linpowr == 0) {
/*  698 */             scal = 1.0F;
/*      */           }
/*  700 */           else if (linpowr == 1) {
/*  701 */             scal = attr[lev][1][i2][i1];
/*      */           }
/*  703 */           else if (linpowr > 1 && linpowr < 99) {
/*  704 */             scal = ArrayMath.pow(attr[lev][1][i2][i1], linpowr);
/*      */           }
/*  706 */           else if (linpowr == 99) {
/*  707 */             scal = 1.0F / (1.0F + ArrayMath.exp(k * (thresh - attr[lev][1][i2][i1])));
/*      */           } 
/*  709 */           spyr[lev][0][i2][i1] = spyr[lev][0][i2][i1] * scal * w0;
/*  710 */           spyr[lev][1][i2][i1] = spyr[lev][1][i2][i1] * scal * w1;
/*  711 */           spyr[lev][2][i2][i1] = spyr[lev][2][i2][i1] * scal * w2;
/*      */         } 
/*      */       } 
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
/*  724 */   private static final double SQRT3 = ArrayMath.sqrt(3.0D);
/*      */   
/*      */   private static final int NDIR2 = 3;
/*      */   
/*      */   private static final int NDIR3 = 6;
/*      */   
/*      */   private static final double ABC_SMALL = 0.01D;
/*      */   private static final double DET_SMALL = 1.0E-40D;
/*  732 */   private static final double COS_PIO3 = ArrayMath.cos(1.0471975511965976D);
/*  733 */   private static final double SIN_PIO3 = ArrayMath.sin(1.0471975511965976D);
/*      */   
/*      */   private int nlev;
/*      */   
/*      */   private int nx1;
/*      */   private int nx2;
/*      */   private int nx3;
/*      */   private int n1;
/*      */   private int n2;
/*      */   private int n3;
/*      */   private boolean statelinear;
/*      */   double ka;
/*      */   double kb;
/*      */   
/*      */   private void makePyramidLevel(int lev, float[][] cf, float[][][][] spyr) {
/*  748 */     int lfactor = (int)ArrayMath.pow(2.0D, lev);
/*  749 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/*  750 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/*  751 */     float[][] clo1 = ArrayMath.copy(cf);
/*  752 */     applyRadial(this.ka / 2.0D, this.kb / 2.0D, clo1);
/*  753 */     ArrayMath.sub(cf, clo1, cf);
/*  754 */     ftInverse(lev, 0, clo1, spyr);
/*  755 */     int ml2 = (nl2 - 1) / 2 + 1;
/*  756 */     int ml1 = (nl1 - 1) / 2 + 1;
/*  757 */     ArrayMath.copy(ml1, ml2, 0, 0, 2, 2, spyr[lev][0], 0, 0, 1, 1, spyr[lev + 1][0]);
/*  758 */     for (int dir = 0; dir < 3; dir++) {
/*  759 */       applySteerableFilter(dir, cf, clo1);
/*  760 */       ftInverse(lev, dir, clo1, spyr);
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
/*      */   private void makePyramidLevel(int lev, float[][][] cf, float[][][][][] spyr) {
/*  774 */     int lfactor = (int)ArrayMath.pow(2.0D, lev);
/*  775 */     int nl3 = (this.n3 - 1) / lfactor + 1;
/*  776 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/*  777 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/*  778 */     float[][][] clo1 = ArrayMath.copy(cf);
/*  779 */     applyRadial(this.ka / 2.0D, this.kb / 2.0D, clo1);
/*  780 */     ArrayMath.sub(cf, clo1, cf);
/*  781 */     ftInverse(lev, 0, clo1, spyr);
/*  782 */     int ml3 = (nl3 - 1) / 2 + 1;
/*  783 */     int ml2 = (nl2 - 1) / 2 + 1;
/*  784 */     int ml1 = (nl1 - 1) / 2 + 1;
/*  785 */     ArrayMath.copy(ml1, ml2, ml3, 0, 0, 0, 2, 2, 2, spyr[lev][0], 0, 0, 0, 1, 1, 1, spyr[lev + 1][0]);
/*      */     
/*  787 */     for (int dir = 0; dir < 6; dir++) {
/*  788 */       applySteerableFilter(dir, cf, clo1);
/*  789 */       ftInverse(lev, dir, clo1, spyr);
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
/*      */   private void applyRadial(double k1, double k2, float[][] cf) {
/*  801 */     int nf2 = cf.length;
/*  802 */     int nf1 = (cf[0]).length / 2;
/*      */     
/*  804 */     double m1 = (nf1 - 1);
/*  805 */     double m2 = (nf2 - 1) / 2.0D;
/*      */     
/*  807 */     double mf1 = 1.0D / m1;
/*  808 */     double mf2 = 1.0D / m2;
/*      */     
/*  810 */     double denom = 1.0D / (k2 - k1);
/*      */     
/*  812 */     for (int i2 = 0; i2 < nf2; i2++) {
/*  813 */       for (int i1 = 0; i1 < nf1; i1++) {
/*  814 */         double w1 = i1 * mf1;
/*  815 */         double w2 = (i2 - m2) * mf2;
/*  816 */         double wd = ArrayMath.sqrt(w1 * w1 + w2 * w2);
/*  817 */         int ir = 2 * i1;
/*  818 */         int ii = ir + 1;
/*  819 */         if (wd > k2) {
/*  820 */           cf[i2][ir] = 0.0F;
/*  821 */           cf[i2][ii] = 0.0F;
/*      */         }
/*  823 */         else if (wd > k1 && wd < k2) {
/*  824 */           double a = (wd - k1) * denom * Math.PI;
/*  825 */           float b = (float)(0.5D * (1.0D + ArrayMath.cos(a)));
/*  826 */           cf[i2][ir] = cf[i2][ir] * b;
/*  827 */           cf[i2][ii] = cf[i2][ii] * b;
/*      */         } 
/*      */       } 
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
/*      */   private void applyRadial(double k1, double k2, float[][][] cf) {
/*  841 */     int nf3 = cf.length;
/*  842 */     int nf2 = (cf[0]).length;
/*  843 */     int nf1 = (cf[0][0]).length / 2;
/*      */     
/*  845 */     double m1 = (nf1 - 1);
/*  846 */     double m2 = (nf2 - 1) / 2.0D;
/*  847 */     double m3 = (nf3 - 1) / 2.0D;
/*      */     
/*  849 */     double mf1 = 1.0D / m1;
/*  850 */     double mf2 = 1.0D / m2;
/*  851 */     double mf3 = 1.0D / m3;
/*      */     
/*  853 */     double denom = 1.0D / (k2 - k1);
/*      */     
/*  855 */     for (int i3 = 0; i3 < nf3; i3++) {
/*  856 */       for (int i2 = 0; i2 < nf2; i2++) {
/*  857 */         for (int i1 = 0; i1 < nf1; i1++) {
/*  858 */           double w1 = i1 * mf1;
/*  859 */           double w2 = (i2 - m2) * mf2;
/*  860 */           double w3 = (i3 - m3) * mf3;
/*  861 */           double wd = Math.sqrt(w1 * w1 + w2 * w2 + w3 * w3);
/*  862 */           int ir = 2 * i1;
/*  863 */           int ii = ir + 1;
/*  864 */           if (wd >= k2) {
/*  865 */             cf[i3][i2][ir] = 0.0F;
/*  866 */             cf[i3][i2][ii] = 0.0F;
/*      */           }
/*  868 */           else if (wd > k1 && wd < k2) {
/*  869 */             double a = (wd - k1) * denom * Math.PI;
/*  870 */             float b = (float)(0.5D * (1.0D + ArrayMath.cos(a)));
/*  871 */             cf[i3][i2][ir] = cf[i3][i2][ir] * b;
/*  872 */             cf[i3][i2][ii] = cf[i3][i2][ii] * b;
/*      */           } 
/*      */         } 
/*      */       } 
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
/*      */   private void applySteerableFilter(int dir, float[][] cfin, float[][] cfout) {
/*  890 */     int nf2 = cfin.length;
/*  891 */     int nf1 = (cfin[0]).length / 2;
/*  892 */     int m1 = nf1 - 1;
/*  893 */     int m2 = (nf2 - 1) / 2;
/*  894 */     double mf1 = 1.0D / m1;
/*  895 */     double mf2 = 1.0D / m2;
/*      */ 
/*      */ 
/*      */     
/*  899 */     double thetan = dir * 0.3333333333333333D * Math.PI;
/*  900 */     for (int i2 = 0; i2 < nf2; i2++) {
/*  901 */       for (int i1 = 0; i1 < nf1; i1++) {
/*  902 */         int ir = 2 * i1;
/*  903 */         int ii = ir + 1;
/*  904 */         double w1 = i1 * mf1;
/*  905 */         double w2 = (i2 - m2) * mf2;
/*  906 */         double theta = ArrayMath.atan2(w1, w2);
/*  907 */         float c = (float)ArrayMath.cos(theta - thetan);
/*  908 */         c = 0.6666667F * c * c;
/*  909 */         cfout[i2][ir] = c * cfin[i2][ir];
/*  910 */         cfout[i2][ii] = c * cfin[i2][ii];
/*      */       } 
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
/*      */   private void applySteerableFilter(int dir, float[][][] cfin, float[][][] cfout) {
/*  925 */     int nf3 = cfin.length;
/*  926 */     int nf2 = (cfin[0]).length;
/*  927 */     int nf1 = (cfin[0][0]).length / 2;
/*      */ 
/*      */     
/*  930 */     double m2 = (nf2 - 1) / 2.0D;
/*  931 */     double m3 = (nf3 - 1) / 2.0D;
/*  932 */     double v1 = 0.0D, v2 = 0.0D, v3 = 0.0D;
/*      */ 
/*      */     
/*  935 */     double s = 1.0D;
/*  936 */     double s2 = 1.0D + s * s;
/*  937 */     if (dir == 0) { v1 = 0.0D; v2 = 1.0D; v3 = s; }
/*  938 */     else if (dir == 1) { v1 = 0.0D; v2 = 1.0D; v3 = -s; }
/*  939 */     else if (dir == 2) { v1 = 1.0D; v2 = s; v3 = 0.0D; }
/*  940 */     else if (dir == 3) { v1 = 1.0D; v2 = -s; v3 = 0.0D; }
/*  941 */     else if (dir == 4) { v1 = s; v2 = 0.0D; v3 = 1.0D; }
/*  942 */     else if (dir == 5) { v1 = -s; v2 = 0.0D; v3 = 1.0D; }
/*  943 */      for (int i3 = 0; i3 < nf3; i3++) {
/*  944 */       for (int i2 = 0; i2 < nf2; i2++) {
/*  945 */         for (int i1 = 0; i1 < nf1; i1++) {
/*  946 */           double w1 = i1;
/*  947 */           double w2 = i2 - m2;
/*  948 */           double w3 = i3 - m3;
/*  949 */           int ir = 2 * i1;
/*  950 */           int ii = ir + 1;
/*  951 */           double flt1 = w1 * v1 + w2 * v2 + w3 * v3;
/*  952 */           flt1 *= flt1;
/*  953 */           double flt2 = (w1 * w1 + w2 * w2 + w3 * w3) * 2.0D * s2;
/*  954 */           flt1 /= flt2;
/*  955 */           cfout[i3][i2][ir] = cfin[i3][i2][ir] * (float)flt1;
/*  956 */           cfout[i3][i2][ii] = cfin[i3][i2][ii] * (float)flt1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  964 */     if ((int)m2 * 2 == nf2 && (int)m3 * 2 == nf3) {
/*  965 */       int ir = 0;
/*  966 */       int ii = ir + 1;
/*  967 */       cfout[(int)m3][(int)m2][ir] = 0.0F;
/*  968 */       cfout[(int)m3][(int)m2][ii] = 0.0F;
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
/*      */   private float[][][] pqjShiftSmooth(double sigma, int lev, float[][][][] spyr) {
/*  988 */     RecursiveGaussianFilter rcg = new RecursiveGaussianFilter(sigma);
/*  989 */     float[] test = ArrayMath.zerofloat(3);
/*      */ 
/*      */     
/*  992 */     float[][][] pq = ArrayMath.zerofloat(1, 1, 3);
/*      */     
/*  994 */     pq[0] = ArrayMath.add(spyr[lev][0], spyr[lev][1]);
/*  995 */     ArrayMath.add(pq[0], spyr[lev][2], pq[0]);
/*  996 */     pq[1] = ArrayMath.mul(pq[0], spyr[lev][1]);
/*  997 */     pq[2] = ArrayMath.mul(pq[0], spyr[lev][2]);
/*  998 */     ArrayMath.mul(pq[0], spyr[lev][0], pq[0]);
/*      */     
/* 1000 */     int n2 = (pq[0]).length;
/* 1001 */     int n1 = (pq[0][0]).length;
/* 1002 */     for (int i2 = 0; i2 < n2; i2++) {
/* 1003 */       for (int i1 = 0; i1 < n1; i1++) {
/* 1004 */         test[0] = pq[0][i2][i1];
/* 1005 */         test[1] = pq[1][i2][i1];
/* 1006 */         test[2] = pq[2][i2][i1];
/* 1007 */         float testmin = ArrayMath.min(test);
/* 1008 */         if (testmin < 0.0F) {
/* 1009 */           pq[0][i2][i1] = pq[0][i2][i1] - testmin;
/* 1010 */           pq[1][i2][i1] = pq[1][i2][i1] - testmin;
/* 1011 */           pq[2][i2][i1] = pq[2][i2][i1] - testmin;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1016 */     rcg.apply00(pq[0], pq[0]);
/* 1017 */     rcg.apply00(pq[1], pq[1]);
/* 1018 */     rcg.apply00(pq[2], pq[2]);
/* 1019 */     return pq;
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
/*      */   private float[][][][] pqjShiftSmooth(double sigma, int lev, float[][][][][] spyr) {
/* 1038 */     RecursiveGaussianFilter rcg = new RecursiveGaussianFilter(sigma);
/* 1039 */     float[] test = ArrayMath.zerofloat(6);
/*      */ 
/*      */     
/* 1042 */     float[][][][] pq = new float[6][1][1][1];
/*      */     
/* 1044 */     pq[0] = ArrayMath.add(spyr[lev][0], spyr[lev][1]); int dir;
/* 1045 */     for (dir = 2; dir < 6; dir++) {
/* 1046 */       ArrayMath.add(pq[0], spyr[lev][dir], pq[0]);
/*      */     }
/* 1048 */     for (dir = 1; dir < 6; dir++) {
/* 1049 */       pq[dir] = ArrayMath.mul(pq[0], spyr[lev][dir]);
/*      */     }
/* 1051 */     ArrayMath.mul(pq[0], spyr[lev][0], pq[0]);
/*      */     
/* 1053 */     int nl3 = (pq[0]).length;
/* 1054 */     int nl2 = (pq[0][0]).length;
/* 1055 */     int nl1 = (pq[0][0][0]).length;
/* 1056 */     for (int i3 = 0; i3 < nl3; i3++) {
/* 1057 */       for (int i2 = 0; i2 < nl2; i2++) {
/* 1058 */         for (int i1 = 0; i1 < nl1; i1++) {
/* 1059 */           int j; for (j = 0; j < 6; j++) {
/* 1060 */             test[j] = pq[j][i3][i2][i1];
/*      */           }
/* 1062 */           float testmin = ArrayMath.min(test);
/* 1063 */           if (testmin < 0.0F) {
/* 1064 */             for (j = 0; j < 6; j++) {
/* 1065 */               pq[j][i3][i2][i1] = pq[j][i3][i2][i1] - testmin;
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1072 */     for (int i = 0; i < 6; i++) {
/* 1073 */       rcg.apply000(pq[i], pq[i]);
/*      */     }
/* 1075 */     return pq;
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
/*      */   private static double[][] findExtrema(double f0, double f1, double f2) {
/* 1097 */     double theta1 = 0.5D * (Math.PI + ArrayMath.atan2(SQRT3 * (f1 - f2), 2.0D * f0 - f1 - f2));
/* 1098 */     double value1 = eval0(f0, f1, f2, theta1);
/* 1099 */     double theta2 = modPi(theta1 + 1.5707963267948966D);
/* 1100 */     double value2 = eval0(f0, f1, f2, theta2);
/* 1101 */     if (ArrayMath.abs(value1) >= ArrayMath.abs(value2)) {
/* 1102 */       return new double[][] { { theta1, value1 }, { theta2, value2 } };
/*      */     }
/* 1104 */     return new double[][] { { theta2, value2 }, { theta1, value1 } };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double modPi(double theta) {
/* 1111 */     return theta - ArrayMath.floor(theta / Math.PI) * Math.PI;
/*      */   }
/*      */ 
/*      */   
/*      */   private static double eval0(double f0, double f1, double f2, double theta) {
/* 1116 */     double k0 = 0.3333333333333333D * (1.0D + 2.0D * ArrayMath.cos(2.0D * (theta - 0.0D)));
/* 1117 */     double k1 = 0.3333333333333333D * (1.0D + 2.0D * ArrayMath.cos(2.0D * (theta - 1.0471975511965976D)));
/* 1118 */     double k2 = 0.3333333333333333D * (1.0D + 2.0D * ArrayMath.cos(2.0D * (theta - 2.0943951023931953D)));
/* 1119 */     return k0 * f0 + k1 * f1 + k2 * f2;
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
/*      */   private static double[][] findCriticalPoints(double[] f, double[][] abcf) {
/* 1143 */     double a, b, c, ar, br, cr, fsum = f[0] + f[1] + f[2] + f[3] + f[4] + f[5];
/* 1144 */     double fab = f[0] - f[1];
/* 1145 */     double fac = f[2] - f[3];
/* 1146 */     double fbc = f[4] - f[5];
/* 1147 */     double faa = f[4] + f[5];
/* 1148 */     double fbb = f[2] + f[3];
/* 1149 */     double fcc = f[0] + f[1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     double hbb = 2.0D * (fbb - faa);
/* 1158 */     double hcc = 2.0D * (fcc - faa);
/* 1159 */     double hbc = -fbc;
/* 1160 */     double da = hbb * hcc - hbc * hbc;
/* 1161 */     double dda = da * da;
/*      */ 
/*      */     
/* 1164 */     double haa = 2.0D * (faa - fbb);
/* 1165 */     hcc = 2.0D * (fcc - fbb);
/* 1166 */     double hac = -fac;
/* 1167 */     double db = haa * hcc - hac * hac;
/* 1168 */     double ddb = db * db;
/*      */ 
/*      */     
/* 1171 */     haa = 2.0D * (faa - fcc);
/* 1172 */     hbb = 2.0D * (fbb - fcc);
/* 1173 */     double hab = -fab;
/* 1174 */     double dc = haa * hbb - hab * hab;
/* 1175 */     double ddc = dc * dc;
/*      */ 
/*      */     
/* 1178 */     if (dda >= ddb && dda >= ddc) {
/* 1179 */       a = 1.0D; b = 0.0D; c = 0.0D;
/* 1180 */     } else if (ddb >= ddc) {
/* 1181 */       a = 0.0D; b = 1.0D; c = 0.0D;
/*      */     } else {
/* 1183 */       a = 0.0D; b = 0.0D; c = 1.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     for (int niter = 0; niter < 50; niter++) {
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
/* 1200 */       double d1, d2, d3, d4 = a * a;
/* 1201 */       double d5 = b * b;
/* 1202 */       double d6 = c * c;
/*      */ 
/*      */       
/* 1205 */       if (d4 <= d6 && d5 <= d6) {
/* 1206 */         double aoc = a / c;
/* 1207 */         double boc = b / c;
/* 1208 */         double aocs = aoc * aoc;
/* 1209 */         double bocs = boc * boc;
/* 1210 */         double faamcc2 = (faa - fcc) * 2.0D;
/* 1211 */         double fbbmcc2 = (fbb - fcc) * 2.0D;
/* 1212 */         double aocsp1 = aocs + 1.0D;
/* 1213 */         double bocsp1 = bocs + 1.0D;
/* 1214 */         double ga = a * (faamcc2 + boc * fbc) - c * (1.0D - aocs) * fac - b * fab;
/* 1215 */         double gb = b * (fbbmcc2 + aoc * fac) - c * (1.0D - bocs) * fbc - a * fab;
/* 1216 */         double d7 = faamcc2 + boc * aocsp1 * fbc + aoc * (3.0D + aocs) * fac;
/* 1217 */         double d8 = fbbmcc2 + aoc * bocsp1 * fac + boc * (3.0D + bocs) * fbc;
/* 1218 */         double d9 = boc * aocsp1 * fac + aoc * bocsp1 * fbc - fab;
/* 1219 */         double det = d7 * d8 - d9 * d9;
/* 1220 */         if (det <= 1.0E-40D && -det <= 1.0E-40D) det = 1.0E-40D; 
/* 1221 */         double odet = 1.0D / det;
/* 1222 */         d1 = odet * (d8 * ga - d9 * gb);
/* 1223 */         d2 = odet * (d7 * gb - d9 * ga);
/* 1224 */         d3 = 0.0D; double bt;
/* 1225 */         for (double at = a - d1; at * at + bt * bt >= 1.0D; at = a - d1, bt = b - d2) {
/* 1226 */           d1 *= 0.5D;
/* 1227 */           d2 *= 0.5D;
/*      */         } 
/* 1229 */         a -= d1;
/* 1230 */         b -= d2;
/* 1231 */         c = (c >= 0.0D) ? ArrayMath.sqrt(1.0D - a * a - b * b) : -ArrayMath.sqrt(1.0D - a * a - b * b);
/*      */ 
/*      */       
/*      */       }
/* 1235 */       else if (d4 <= d5) {
/* 1236 */         double aob = a / b;
/* 1237 */         double cob = c / b;
/* 1238 */         double aobs = aob * aob;
/* 1239 */         double cobs = cob * cob;
/* 1240 */         double faambb2 = (faa - fbb) * 2.0D;
/* 1241 */         double fccmbb2 = (fcc - fbb) * 2.0D;
/* 1242 */         double aobsp1 = aobs + 1.0D;
/* 1243 */         double cobsp1 = cobs + 1.0D;
/* 1244 */         double ga = a * (faambb2 + cob * fbc) - b * (1.0D - aobs) * fab - c * fac;
/* 1245 */         double gc = c * (fccmbb2 + aob * fab) - b * (1.0D - cobs) * fbc - a * fac;
/* 1246 */         double d7 = faambb2 + cob * aobsp1 * fbc + aob * (3.0D + aobs) * fab;
/* 1247 */         double d8 = fccmbb2 + aob * cobsp1 * fab + cob * (3.0D + cobs) * fbc;
/* 1248 */         double d9 = cob * aobsp1 * fab + aob * cobsp1 * fbc - fac;
/* 1249 */         double det = d7 * d8 - d9 * d9;
/* 1250 */         if (det <= 1.0E-40D && -det <= 1.0E-40D) det = 1.0E-40D; 
/* 1251 */         double odet = 1.0D / det;
/* 1252 */         d1 = odet * (d8 * ga - d9 * gc);
/* 1253 */         d2 = 0.0D;
/* 1254 */         d3 = odet * (d7 * gc - d9 * ga); double ct;
/* 1255 */         for (double at = a - d1; at * at + ct * ct >= 1.0D; at = a - d1, ct = c - d3) {
/* 1256 */           d1 *= 0.5D;
/* 1257 */           d3 *= 0.5D;
/*      */         } 
/* 1259 */         a -= d1;
/* 1260 */         c -= d3;
/* 1261 */         b = (b >= 0.0D) ? ArrayMath.sqrt(1.0D - a * a - c * c) : -ArrayMath.sqrt(1.0D - a * a - c * c);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1266 */         double boa = b / a;
/* 1267 */         double coa = c / a;
/* 1268 */         double boas = boa * boa;
/* 1269 */         double coas = coa * coa;
/* 1270 */         double fbbmaa2 = (fbb - faa) * 2.0D;
/* 1271 */         double fccmaa2 = (fcc - faa) * 2.0D;
/* 1272 */         double boasp1 = boas + 1.0D;
/* 1273 */         double coasp1 = coas + 1.0D;
/* 1274 */         double gb = b * (fbbmaa2 + coa * fac) - a * (1.0D - boas) * fab - c * fbc;
/* 1275 */         double gc = c * (fccmaa2 + boa * fab) - a * (1.0D - coas) * fac - b * fbc;
/* 1276 */         double d7 = fbbmaa2 + coa * boasp1 * fac + boa * (3.0D + boas) * fab;
/* 1277 */         double d8 = fccmaa2 + boa * coasp1 * fab + coa * (3.0D + coas) * fac;
/* 1278 */         double d9 = coa * boasp1 * fab + boa * coasp1 * fac - fbc;
/* 1279 */         double det = d7 * d8 - d9 * d9;
/* 1280 */         if (det <= 1.0E-40D && -det <= 1.0E-40D) det = 1.0E-40D; 
/* 1281 */         double odet = 1.0D / det;
/* 1282 */         d1 = 0.0D;
/* 1283 */         d2 = odet * (d8 * gb - d9 * gc);
/* 1284 */         d3 = odet * (d7 * gc - d9 * gb); double ct;
/* 1285 */         for (double bt = b - d2; bt * bt + ct * ct >= 1.0D; bt = b - d2, ct = c - d3) {
/* 1286 */           d2 *= 0.5D;
/* 1287 */           d3 *= 0.5D;
/*      */         } 
/* 1289 */         b -= d2;
/* 1290 */         c -= d3;
/* 1291 */         a = (a >= 0.0D) ? ArrayMath.sqrt(1.0D - b * b - c * c) : -ArrayMath.sqrt(1.0D - b * b - c * c);
/*      */       } 
/*      */ 
/*      */       
/* 1295 */       if (d1 < 0.01D && -d1 < 0.01D && d2 < 0.01D && -d2 < 0.01D && d3 < 0.01D && -d3 < 0.01D) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1304 */     double a0 = a, b0 = b, c0 = c;
/*      */ 
/*      */ 
/*      */     
/* 1308 */     double aa = a * a, bb = b * b, cc = c * c;
/* 1309 */     if (aa <= bb && aa <= cc) {
/* 1310 */       ar = 0.0D; br = c0; cr = -b0;
/* 1311 */     } else if (bb <= cc) {
/* 1312 */       ar = c0; br = 0.0D; cr = -a0;
/*      */     } else {
/* 1314 */       ar = b0; br = -a0; cr = 0.0D;
/*      */     } 
/* 1316 */     double sr0 = ar * a0 + br * b0 + cr * c0;
/* 1317 */     ar -= sr0 * a0; br -= sr0 * b0; cr -= sr0 * c0;
/* 1318 */     double sr = 1.0D / ArrayMath.sqrt(ar * ar + br * br + cr * cr);
/* 1319 */     ar *= sr; br *= sr; cr *= sr;
/*      */ 
/*      */     
/* 1322 */     double as = br * c0 - b0 * cr;
/* 1323 */     double bs = cr * a0 - c0 * ar;
/* 1324 */     double cs = ar * b0 - a0 * br;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1329 */     double a1 = ar;
/* 1330 */     double b1 = br;
/* 1331 */     double c1 = cr;
/* 1332 */     double a2 = COS_PIO3 * ar + SIN_PIO3 * as;
/* 1333 */     double b2 = COS_PIO3 * br + SIN_PIO3 * bs;
/* 1334 */     double c2 = COS_PIO3 * cr + SIN_PIO3 * cs;
/* 1335 */     double a3 = COS_PIO3 * ar - SIN_PIO3 * as;
/* 1336 */     double b3 = COS_PIO3 * br - SIN_PIO3 * bs;
/* 1337 */     double c3 = COS_PIO3 * cr - SIN_PIO3 * cs;
/*      */ 
/*      */     
/* 1340 */     double f0 = fab * a0 * b0 + fac * a0 * c0 + fbc * b0 * c0 - faa * a0 * a0 - fbb * b0 * b0 - fcc * c0 * c0;
/* 1341 */     double f1 = fab * a1 * b1 + fac * a1 * c1 + fbc * b1 * c1 - faa * a1 * a1 - fbb * b1 * b1 - fcc * c1 * c1;
/* 1342 */     double f2 = fab * a2 * b2 + fac * a2 * c2 + fbc * b2 * c2 - faa * a2 * a2 - fbb * b2 * b2 - fcc * c2 * c2;
/* 1343 */     double f3 = fab * a3 * b3 + fac * a3 * c3 + fbc * b3 * c3 - faa * a3 * a3 - fbb * b3 * b3 - fcc * c3 * c3;
/*      */ 
/*      */ 
/*      */     
/* 1347 */     double denom = 2.0D * f1 - f2 - f3;
/* 1348 */     if (denom < 1.0E-40D && -denom < 1.0E-40D)
/* 1349 */       denom = 1.0E-40D; 
/* 1350 */     double theta = 0.5D * ArrayMath.atan(SQRT3 * (f2 - f3) / denom);
/* 1351 */     double ctheta = ArrayMath.cos(theta);
/* 1352 */     double stheta = ArrayMath.sin(theta);
/* 1353 */     a1 = ctheta * ar + stheta * as;
/* 1354 */     b1 = ctheta * br + stheta * bs;
/* 1355 */     c1 = ctheta * cr + stheta * cs;
/* 1356 */     a2 = stheta * ar - ctheta * as;
/* 1357 */     b2 = stheta * br - ctheta * bs;
/* 1358 */     c2 = stheta * cr - ctheta * cs;
/*      */ 
/*      */     
/* 1361 */     f1 = fab * a1 * b1 + fac * a1 * c1 + fbc * b1 * c1 - faa * a1 * a1 - fbb * b1 * b1 - fcc * c1 * c1;
/* 1362 */     f2 = fab * a2 * b2 + fac * a2 * c2 + fbc * b2 * c2 - faa * a2 * a2 - fbb * b2 * b2 - fcc * c2 * c2;
/*      */ 
/*      */     
/* 1365 */     f0 = fsum + 2.0D * f0;
/* 1366 */     f1 = fsum + 2.0D * f1;
/* 1367 */     f2 = fsum + 2.0D * f2;
/*      */ 
/*      */     
/* 1370 */     if (f0 > f1) {
/* 1371 */       double at = a0; a0 = a1; a1 = at;
/* 1372 */       double bt = b0; b0 = b1; b1 = bt;
/* 1373 */       double ct = c0; c0 = c1; c1 = ct;
/* 1374 */       double ft = f0; f0 = f1; f1 = ft;
/*      */     } 
/* 1376 */     if (f1 > f2) {
/* 1377 */       double at = a1; a1 = a2; a2 = at;
/* 1378 */       double bt = b1; b1 = b2; b2 = bt;
/* 1379 */       double ct = c1; c1 = c2; c2 = ct;
/* 1380 */       double ft = f1; f1 = f2; f2 = ft;
/*      */     } 
/* 1382 */     if (f0 > f1) {
/* 1383 */       double at = a0; a0 = a1; a1 = at;
/* 1384 */       double bt = b0; b0 = b1; b1 = bt;
/* 1385 */       double ct = c0; c0 = c1; c1 = ct;
/* 1386 */       double ft = f0; f0 = f1; f1 = ft;
/*      */     } 
/*      */ 
/*      */     
/* 1390 */     if (abcf == null) abcf = new double[3][4]; 
/* 1391 */     abcf[0][0] = a0; abcf[0][1] = b0; abcf[0][2] = c0; abcf[0][3] = f0;
/* 1392 */     abcf[1][0] = a1; abcf[1][1] = b1; abcf[1][2] = c1; abcf[1][3] = f1;
/* 1393 */     abcf[2][0] = a2; abcf[2][1] = b2; abcf[2][2] = c2; abcf[2][3] = f2;
/* 1394 */     return abcf;
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
/*      */   private float[][] ftForward(int level, float[][] x) {
/* 1407 */     int ny2 = x.length;
/* 1408 */     int ny1 = (x[0]).length;
/* 1409 */     int mpad = ArrayMath.round(20.0F / (1.0F + level));
/* 1410 */     int lfactor = (int)ArrayMath.pow(2.0D, level);
/* 1411 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/* 1412 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/* 1413 */     int nf1 = FftReal.nfftSmall(nl1 + mpad * 2);
/* 1414 */     int nf1c = nf1 / 2 + 1;
/* 1415 */     int nf2 = FftComplex.nfftSmall(nl2 + mpad * 2);
/* 1416 */     float[][] xr = ArrayMath.zerofloat(nf1, nf2);
/* 1417 */     ArrayMath.copy(ny1, ny2, 0, 0, x, mpad, mpad, xr);
/* 1418 */     float[][] cx = ArrayMath.czerofloat(nf1c, nf2);
/* 1419 */     FftReal fft1 = new FftReal(nf1);
/* 1420 */     FftComplex fft2 = new FftComplex(nf2);
/* 1421 */     fft1.realToComplex1(1, nf2, xr, cx);
/* 1422 */     flipSign(2, cx);
/* 1423 */     fft2.complexToComplex2(1, nf1c, cx, cx);
/* 1424 */     return cx;
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
/*      */   private float[][][] ftForward(int level, float[][][] x) {
/* 1438 */     int ny3 = x.length;
/* 1439 */     int ny2 = (x[0]).length;
/* 1440 */     int ny1 = (x[0][0]).length;
/* 1441 */     int mpad = ArrayMath.round(20.0F / (1.0F + level));
/* 1442 */     int lfactor = (int)ArrayMath.pow(2.0D, level);
/* 1443 */     int nl3 = (this.n3 - 1) / lfactor + 1;
/* 1444 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/* 1445 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/* 1446 */     int nf3 = FftComplex.nfftSmall(nl3 + mpad * 2);
/* 1447 */     int nf2 = FftComplex.nfftSmall(nl2 + mpad * 2);
/* 1448 */     int nf1 = FftReal.nfftSmall(nl1 + mpad * 2);
/* 1449 */     int nf1c = nf1 / 2 + 1;
/* 1450 */     float[][][] xr = ArrayMath.zerofloat(nf1, nf2, nf3);
/* 1451 */     ArrayMath.copy(ny1, ny2, ny3, 0, 0, 0, x, mpad, mpad, mpad, xr);
/* 1452 */     float[][][] cx = ArrayMath.czerofloat(nf1c, nf2, nf3);
/* 1453 */     FftReal fft1 = new FftReal(nf1);
/* 1454 */     FftComplex fft2 = new FftComplex(nf2);
/* 1455 */     FftComplex fft3 = new FftComplex(nf3);
/* 1456 */     fft1.realToComplex1(1, nf2, nf3, xr, cx);
/* 1457 */     flipSign(2, cx);
/* 1458 */     fft2.complexToComplex2(1, nf1c, nf3, cx, cx);
/* 1459 */     flipSign(3, cx);
/* 1460 */     fft3.complexToComplex3(1, nf1c, nf2, cx, cx);
/* 1461 */     return cx;
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
/*      */   private void ftInverse(int lev, int dir, float[][] cf, float[][][][] spyr) {
/* 1477 */     int nf2 = cf.length;
/* 1478 */     int nf1c = (cf[0]).length / 2;
/* 1479 */     int nf1 = (nf1c - 1) * 2;
/* 1480 */     int mpad = ArrayMath.round(20.0F / (1.0F + lev));
/* 1481 */     int lfactor = (int)ArrayMath.pow(2.0D, lev);
/* 1482 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/* 1483 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/* 1484 */     FftReal fft1 = new FftReal(nf1);
/* 1485 */     FftComplex fft2 = new FftComplex(nf2);
/* 1486 */     fft2.complexToComplex2(-1, nf1c, cf, cf);
/* 1487 */     flipSign(2, cf);
/* 1488 */     fft2.scale(nf1c, nf2, cf);
/* 1489 */     fft1.complexToReal1(-1, nf2, cf, cf);
/* 1490 */     fft1.scale(nf1, nf2, cf);
/* 1491 */     ArrayMath.copy(nl1, nl2, mpad, mpad, 1, 1, cf, 0, 0, 1, 1, spyr[lev][dir]);
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
/*      */   private void ftInverse(int lev, int dir, float[][][] cf, float[][][][][] spyr) {
/* 1508 */     int nf3 = cf.length;
/* 1509 */     int nf2 = (cf[0]).length;
/* 1510 */     int nf1c = (cf[0][0]).length / 2;
/* 1511 */     int nf1 = (nf1c - 1) * 2;
/* 1512 */     int mpad = ArrayMath.round(20.0F / (1.0F + lev));
/* 1513 */     int lfactor = (int)ArrayMath.pow(2.0D, lev);
/* 1514 */     int nl3 = (this.n3 - 1) / lfactor + 1;
/* 1515 */     int nl2 = (this.n2 - 1) / lfactor + 1;
/* 1516 */     int nl1 = (this.n1 - 1) / lfactor + 1;
/* 1517 */     FftReal fft1 = new FftReal(nf1);
/* 1518 */     FftComplex fft2 = new FftComplex(nf2);
/* 1519 */     FftComplex fft3 = new FftComplex(nf3);
/* 1520 */     fft3.complexToComplex3(-1, nf1c, nf2, cf, cf);
/* 1521 */     flipSign(3, cf);
/* 1522 */     fft3.scale(nf1c, nf2, nf3, cf);
/* 1523 */     fft2.complexToComplex2(-1, nf1c, nf3, cf, cf);
/* 1524 */     flipSign(2, cf);
/* 1525 */     fft2.scale(nf1c, nf2, nf3, cf);
/* 1526 */     fft1.complexToReal1(-1, nf2, nf3, cf, cf);
/* 1527 */     fft1.scale(nf1, nf2, nf3, cf);
/* 1528 */     ArrayMath.copy(nl1, nl2, nl3, mpad, mpad, mpad, 1, 1, 1, cf, 0, 0, 0, 1, 1, 1, spyr[lev][dir]);
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
/*      */   private void flipSign(int a, float[][] x) {
/* 1543 */     int nx2 = x.length;
/* 1544 */     int nx1 = (x[0]).length;
/* 1545 */     if (a == 1) {
/* 1546 */       for (int i2 = 0; i2 < nx2; i2++) {
/* 1547 */         for (int i1 = 0; i1 < nx1 / 4; i1++) {
/* 1548 */           int ir = 4 * i1;
/* 1549 */           int ii = ir + 1;
/* 1550 */           x[i2][ir] = x[i2][ir] * -1.0F;
/* 1551 */           x[i2][ii] = x[i2][ii] * -1.0F;
/*      */         }
/*      */       
/*      */       } 
/* 1555 */     } else if (a == 2) {
/* 1556 */       for (int i2 = 0; i2 < nx2 / 2; i2++) {
/* 1557 */         for (int i1 = 0; i1 < nx1 / 2; i1++) {
/* 1558 */           int ir = 2 * i1;
/* 1559 */           int ii = ir + 1;
/* 1560 */           x[2 * i2][ir] = x[2 * i2][ir] * -1.0F;
/* 1561 */           x[2 * i2][ii] = x[2 * i2][ii] * -1.0F;
/*      */         } 
/*      */       } 
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
/*      */   private void flipSign(int a, float[][][] x) {
/* 1577 */     int l3 = x.length;
/* 1578 */     int l2 = (x[0]).length;
/* 1579 */     int l1 = (x[0][0]).length / 2;
/*      */     
/* 1581 */     if (a == 1) {
/* 1582 */       for (int i1 = 0; i1 < (int)Math.floor(l1 / 2.0D); i1++) {
/* 1583 */         for (int i3 = 0; i3 < l3; i3++) {
/* 1584 */           for (int i2 = 0; i2 < l2; i2++) {
/* 1585 */             int ir = 4 * i1;
/* 1586 */             int ii = ir + 1;
/* 1587 */             x[i3][i2][ir] = x[i3][i2][ir] * -1.0F;
/* 1588 */             x[i3][i2][ii] = x[i3][i2][ii] * -1.0F;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1593 */     } else if (a == 2) {
/* 1594 */       for (int i2 = 0; i2 < (int)Math.floor(l2 / 2.0D); i2++) {
/* 1595 */         for (int i3 = 0; i3 < l3; i3++) {
/* 1596 */           for (int i1 = 0; i1 < l1; i1++) {
/* 1597 */             int ir = 2 * i1;
/* 1598 */             int ii = ir + 1;
/* 1599 */             int b = 2 * i2;
/* 1600 */             x[i3][b][ir] = x[i3][b][ir] * -1.0F;
/* 1601 */             x[i3][b][ii] = x[i3][b][ii] * -1.0F;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1606 */     } else if (a == 3) {
/* 1607 */       for (int i3 = 0; i3 < (int)Math.floor(l3 / 2.0D); i3++) {
/* 1608 */         for (int i2 = 0; i2 < l2; i2++) {
/* 1609 */           for (int i1 = 0; i1 < l1; i1++) {
/* 1610 */             int ir = 2 * i1;
/* 1611 */             int ii = ir + 1;
/* 1612 */             int b = 2 * i3;
/* 1613 */             x[b][i2][ir] = x[b][i2][ir] * -1.0F;
/* 1614 */             x[b][i2][ii] = x[b][i2][ii] * -1.0F;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/SteerablePyramid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */