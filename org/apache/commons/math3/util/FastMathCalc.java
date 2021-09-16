/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FastMathCalc
/*     */ {
/*     */   private static final long HEX_40000000 = 1073741824L;
/*  35 */   private static final double[] FACT = new double[] { 1.0D, 1.0D, 2.0D, 6.0D, 24.0D, 120.0D, 720.0D, 5040.0D, 40320.0D, 362880.0D, 3628800.0D, 3.99168E7D, 4.790016E8D, 6.2270208E9D, 8.71782912E10D, 1.307674368E12D, 2.0922789888E13D, 3.55687428096E14D, 6.402373705728E15D, 1.21645100408832E17D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final double[][] LN_SPLIT_COEF = new double[][] { { 2.0D, 0.0D }, { 0.6666666269302368D, 3.9736429850260626E-8D }, { 0.3999999761581421D, 2.3841857910019882E-8D }, { 0.2857142686843872D, 1.7029898543501842E-8D }, { 0.2222222089767456D, 1.3245471311735498E-8D }, { 0.1818181574344635D, 2.4384203044354907E-8D }, { 0.1538461446762085D, 9.140260083262505E-9D }, { 0.13333332538604736D, 9.220590270857665E-9D }, { 0.11764700710773468D, 1.2393345855018391E-8D }, { 0.10526403784751892D, 8.251545029714408E-9D }, { 0.0952233225107193D, 1.2675934823758863E-8D }, { 0.08713622391223907D, 1.1430250008909141E-8D }, { 0.07842259109020233D, 2.404307984052299E-9D }, { 0.08371849358081818D, 1.176342548272881E-8D }, { 0.030589580535888672D, 1.2958646899018938E-9D }, { 0.14982303977012634D, 1.225743062930824E-8D } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String TABLE_START_DECL = "    {";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String TABLE_END_DECL = "    };";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void buildSinCosTables(double[] SINE_TABLE_A, double[] SINE_TABLE_B, double[] COSINE_TABLE_A, double[] COSINE_TABLE_B, int SINE_TABLE_LEN, double[] TANGENT_TABLE_A, double[] TANGENT_TABLE_B) {
/* 104 */     double[] result = new double[2];
/*     */     
/*     */     int i;
/* 107 */     for (i = 0; i < 7; i++) {
/* 108 */       double x = i / 8.0D;
/*     */       
/* 110 */       slowSin(x, result);
/* 111 */       SINE_TABLE_A[i] = result[0];
/* 112 */       SINE_TABLE_B[i] = result[1];
/*     */       
/* 114 */       slowCos(x, result);
/* 115 */       COSINE_TABLE_A[i] = result[0];
/* 116 */       COSINE_TABLE_B[i] = result[1];
/*     */     } 
/*     */ 
/*     */     
/* 120 */     for (i = 7; i < SINE_TABLE_LEN; i++) {
/* 121 */       double[] xs = new double[2];
/* 122 */       double[] ys = new double[2];
/* 123 */       double[] as = new double[2];
/* 124 */       double[] bs = new double[2];
/* 125 */       double[] temps = new double[2];
/*     */       
/* 127 */       if ((i & 0x1) == 0) {
/*     */         
/* 129 */         xs[0] = SINE_TABLE_A[i / 2];
/* 130 */         xs[1] = SINE_TABLE_B[i / 2];
/* 131 */         ys[0] = COSINE_TABLE_A[i / 2];
/* 132 */         ys[1] = COSINE_TABLE_B[i / 2];
/*     */ 
/*     */         
/* 135 */         splitMult(xs, ys, result);
/* 136 */         SINE_TABLE_A[i] = result[0] * 2.0D;
/* 137 */         SINE_TABLE_B[i] = result[1] * 2.0D;
/*     */ 
/*     */         
/* 140 */         splitMult(ys, ys, as);
/* 141 */         splitMult(xs, xs, temps);
/* 142 */         temps[0] = -temps[0];
/* 143 */         temps[1] = -temps[1];
/* 144 */         splitAdd(as, temps, result);
/* 145 */         COSINE_TABLE_A[i] = result[0];
/* 146 */         COSINE_TABLE_B[i] = result[1];
/*     */       } else {
/* 148 */         xs[0] = SINE_TABLE_A[i / 2];
/* 149 */         xs[1] = SINE_TABLE_B[i / 2];
/* 150 */         ys[0] = COSINE_TABLE_A[i / 2];
/* 151 */         ys[1] = COSINE_TABLE_B[i / 2];
/* 152 */         as[0] = SINE_TABLE_A[i / 2 + 1];
/* 153 */         as[1] = SINE_TABLE_B[i / 2 + 1];
/* 154 */         bs[0] = COSINE_TABLE_A[i / 2 + 1];
/* 155 */         bs[1] = COSINE_TABLE_B[i / 2 + 1];
/*     */ 
/*     */         
/* 158 */         splitMult(xs, bs, temps);
/* 159 */         splitMult(ys, as, result);
/* 160 */         splitAdd(result, temps, result);
/* 161 */         SINE_TABLE_A[i] = result[0];
/* 162 */         SINE_TABLE_B[i] = result[1];
/*     */ 
/*     */         
/* 165 */         splitMult(ys, bs, result);
/* 166 */         splitMult(xs, as, temps);
/* 167 */         temps[0] = -temps[0];
/* 168 */         temps[1] = -temps[1];
/* 169 */         splitAdd(result, temps, result);
/* 170 */         COSINE_TABLE_A[i] = result[0];
/* 171 */         COSINE_TABLE_B[i] = result[1];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 176 */     for (i = 0; i < SINE_TABLE_LEN; i++) {
/* 177 */       double[] xs = new double[2];
/* 178 */       double[] ys = new double[2];
/* 179 */       double[] as = new double[2];
/*     */       
/* 181 */       as[0] = COSINE_TABLE_A[i];
/* 182 */       as[1] = COSINE_TABLE_B[i];
/*     */       
/* 184 */       splitReciprocal(as, ys);
/*     */       
/* 186 */       xs[0] = SINE_TABLE_A[i];
/* 187 */       xs[1] = SINE_TABLE_B[i];
/*     */       
/* 189 */       splitMult(xs, ys, as);
/*     */       
/* 191 */       TANGENT_TABLE_A[i] = as[0];
/* 192 */       TANGENT_TABLE_B[i] = as[1];
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
/*     */ 
/*     */   
/*     */   static double slowCos(double x, double[] result) {
/* 207 */     double[] xs = new double[2];
/* 208 */     double[] ys = new double[2];
/* 209 */     double[] facts = new double[2];
/* 210 */     double[] as = new double[2];
/* 211 */     split(x, xs);
/* 212 */     ys[1] = 0.0D; ys[0] = 0.0D;
/*     */     
/* 214 */     for (int i = FACT.length - 1; i >= 0; i--) {
/* 215 */       splitMult(xs, ys, as);
/* 216 */       ys[0] = as[0]; ys[1] = as[1];
/*     */       
/* 218 */       if ((i & 0x1) == 0) {
/*     */ 
/*     */ 
/*     */         
/* 222 */         split(FACT[i], as);
/* 223 */         splitReciprocal(as, facts);
/*     */         
/* 225 */         if ((i & 0x2) != 0) {
/* 226 */           facts[0] = -facts[0];
/* 227 */           facts[1] = -facts[1];
/*     */         } 
/*     */         
/* 230 */         splitAdd(ys, facts, as);
/* 231 */         ys[0] = as[0]; ys[1] = as[1];
/*     */       } 
/*     */     } 
/* 234 */     if (result != null) {
/* 235 */       result[0] = ys[0];
/* 236 */       result[1] = ys[1];
/*     */     } 
/*     */     
/* 239 */     return ys[0] + ys[1];
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
/*     */   static double slowSin(double x, double[] result) {
/* 251 */     double[] xs = new double[2];
/* 252 */     double[] ys = new double[2];
/* 253 */     double[] facts = new double[2];
/* 254 */     double[] as = new double[2];
/* 255 */     split(x, xs);
/* 256 */     ys[1] = 0.0D; ys[0] = 0.0D;
/*     */     
/* 258 */     for (int i = FACT.length - 1; i >= 0; i--) {
/* 259 */       splitMult(xs, ys, as);
/* 260 */       ys[0] = as[0]; ys[1] = as[1];
/*     */       
/* 262 */       if ((i & 0x1) != 0) {
/*     */ 
/*     */ 
/*     */         
/* 266 */         split(FACT[i], as);
/* 267 */         splitReciprocal(as, facts);
/*     */         
/* 269 */         if ((i & 0x2) != 0) {
/* 270 */           facts[0] = -facts[0];
/* 271 */           facts[1] = -facts[1];
/*     */         } 
/*     */         
/* 274 */         splitAdd(ys, facts, as);
/* 275 */         ys[0] = as[0]; ys[1] = as[1];
/*     */       } 
/*     */     } 
/* 278 */     if (result != null) {
/* 279 */       result[0] = ys[0];
/* 280 */       result[1] = ys[1];
/*     */     } 
/*     */     
/* 283 */     return ys[0] + ys[1];
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
/*     */   static double slowexp(double x, double[] result) {
/* 295 */     double[] xs = new double[2];
/* 296 */     double[] ys = new double[2];
/* 297 */     double[] facts = new double[2];
/* 298 */     double[] as = new double[2];
/* 299 */     split(x, xs);
/* 300 */     ys[1] = 0.0D; ys[0] = 0.0D;
/*     */     
/* 302 */     for (int i = FACT.length - 1; i >= 0; i--) {
/* 303 */       splitMult(xs, ys, as);
/* 304 */       ys[0] = as[0];
/* 305 */       ys[1] = as[1];
/*     */       
/* 307 */       split(FACT[i], as);
/* 308 */       splitReciprocal(as, facts);
/*     */       
/* 310 */       splitAdd(ys, facts, as);
/* 311 */       ys[0] = as[0];
/* 312 */       ys[1] = as[1];
/*     */     } 
/*     */     
/* 315 */     if (result != null) {
/* 316 */       result[0] = ys[0];
/* 317 */       result[1] = ys[1];
/*     */     } 
/*     */     
/* 320 */     return ys[0] + ys[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void split(double d, double[] split) {
/* 329 */     if (d < 8.0E298D && d > -8.0E298D) {
/* 330 */       double a = d * 1.073741824E9D;
/* 331 */       split[0] = d + a - a;
/* 332 */       split[1] = d - split[0];
/*     */     } else {
/* 334 */       double a = d * 9.313225746154785E-10D;
/* 335 */       split[0] = (d + a - d) * 1.073741824E9D;
/* 336 */       split[1] = d - split[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void resplit(double[] a) {
/* 345 */     double c = a[0] + a[1];
/* 346 */     double d = -(c - a[0] - a[1]);
/*     */     
/* 348 */     if (c < 8.0E298D && c > -8.0E298D) {
/* 349 */       double z = c * 1.073741824E9D;
/* 350 */       a[0] = c + z - z;
/* 351 */       a[1] = c - a[0] + d;
/*     */     } else {
/* 353 */       double z = c * 9.313225746154785E-10D;
/* 354 */       a[0] = (c + z - c) * 1.073741824E9D;
/* 355 */       a[1] = c - a[0] + d;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void splitMult(double[] a, double[] b, double[] ans) {
/* 365 */     ans[0] = a[0] * b[0];
/* 366 */     ans[1] = a[0] * b[1] + a[1] * b[0] + a[1] * b[1];
/*     */ 
/*     */     
/* 369 */     resplit(ans);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void splitAdd(double[] a, double[] b, double[] ans) {
/* 378 */     ans[0] = a[0] + b[0];
/* 379 */     ans[1] = a[1] + b[1];
/*     */     
/* 381 */     resplit(ans);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void splitReciprocal(double[] in, double[] result) {
/* 403 */     double b = 2.384185791015625E-7D;
/* 404 */     double a = 0.9999997615814209D;
/*     */     
/* 406 */     if (in[0] == 0.0D) {
/* 407 */       in[0] = in[1];
/* 408 */       in[1] = 0.0D;
/*     */     } 
/*     */     
/* 411 */     result[0] = 0.9999997615814209D / in[0];
/* 412 */     result[1] = (2.384185791015625E-7D * in[0] - 0.9999997615814209D * in[1]) / (in[0] * in[0] + in[0] * in[1]);
/*     */     
/* 414 */     if (result[1] != result[1]) {
/* 415 */       result[1] = 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 419 */     resplit(result);
/*     */     
/* 421 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 423 */       double err = 1.0D - result[0] * in[0] - result[0] * in[1] - result[1] * in[0] - result[1] * in[1];
/*     */ 
/*     */       
/* 426 */       err *= result[0] + result[1];
/*     */       
/* 428 */       result[1] = result[1] + err;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void quadMult(double[] a, double[] b, double[] result) {
/* 438 */     double[] xs = new double[2];
/* 439 */     double[] ys = new double[2];
/* 440 */     double[] zs = new double[2];
/*     */ 
/*     */     
/* 443 */     split(a[0], xs);
/* 444 */     split(b[0], ys);
/* 445 */     splitMult(xs, ys, zs);
/*     */     
/* 447 */     result[0] = zs[0];
/* 448 */     result[1] = zs[1];
/*     */ 
/*     */     
/* 451 */     split(b[1], ys);
/* 452 */     splitMult(xs, ys, zs);
/*     */     
/* 454 */     double tmp = result[0] + zs[0];
/* 455 */     result[1] = result[1] - tmp - result[0] - zs[0];
/* 456 */     result[0] = tmp;
/* 457 */     tmp = result[0] + zs[1];
/* 458 */     result[1] = result[1] - tmp - result[0] - zs[1];
/* 459 */     result[0] = tmp;
/*     */ 
/*     */     
/* 462 */     split(a[1], xs);
/* 463 */     split(b[0], ys);
/* 464 */     splitMult(xs, ys, zs);
/*     */     
/* 466 */     tmp = result[0] + zs[0];
/* 467 */     result[1] = result[1] - tmp - result[0] - zs[0];
/* 468 */     result[0] = tmp;
/* 469 */     tmp = result[0] + zs[1];
/* 470 */     result[1] = result[1] - tmp - result[0] - zs[1];
/* 471 */     result[0] = tmp;
/*     */ 
/*     */     
/* 474 */     split(a[1], xs);
/* 475 */     split(b[1], ys);
/* 476 */     splitMult(xs, ys, zs);
/*     */     
/* 478 */     tmp = result[0] + zs[0];
/* 479 */     result[1] = result[1] - tmp - result[0] - zs[0];
/* 480 */     result[0] = tmp;
/* 481 */     tmp = result[0] + zs[1];
/* 482 */     result[1] = result[1] - tmp - result[0] - zs[1];
/* 483 */     result[0] = tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double expint(int p, double[] result) {
/* 493 */     double[] xs = new double[2];
/* 494 */     double[] as = new double[2];
/* 495 */     double[] ys = new double[2];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 504 */     xs[0] = Math.E;
/* 505 */     xs[1] = 1.4456468917292502E-16D;
/*     */     
/* 507 */     split(1.0D, ys);
/*     */     
/* 509 */     while (p > 0) {
/* 510 */       if ((p & 0x1) != 0) {
/* 511 */         quadMult(ys, xs, as);
/* 512 */         ys[0] = as[0]; ys[1] = as[1];
/*     */       } 
/*     */       
/* 515 */       quadMult(xs, xs, as);
/* 516 */       xs[0] = as[0]; xs[1] = as[1];
/*     */       
/* 518 */       p >>= 1;
/*     */     } 
/*     */     
/* 521 */     if (result != null) {
/* 522 */       result[0] = ys[0];
/* 523 */       result[1] = ys[1];
/*     */       
/* 525 */       resplit(result);
/*     */     } 
/*     */     
/* 528 */     return ys[0] + ys[1];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double[] slowLog(double xi) {
/* 550 */     double[] x = new double[2];
/* 551 */     double[] x2 = new double[2];
/* 552 */     double[] y = new double[2];
/* 553 */     double[] a = new double[2];
/*     */     
/* 555 */     split(xi, x);
/*     */ 
/*     */     
/* 558 */     x[0] = x[0] + 1.0D;
/* 559 */     resplit(x);
/* 560 */     splitReciprocal(x, a);
/* 561 */     x[0] = x[0] - 2.0D;
/* 562 */     resplit(x);
/* 563 */     splitMult(x, a, y);
/* 564 */     x[0] = y[0];
/* 565 */     x[1] = y[1];
/*     */ 
/*     */     
/* 568 */     splitMult(x, x, x2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 574 */     y[0] = LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][0];
/* 575 */     y[1] = LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][1];
/*     */     
/* 577 */     for (int i = LN_SPLIT_COEF.length - 2; i >= 0; i--) {
/* 578 */       splitMult(y, x2, a);
/* 579 */       y[0] = a[0];
/* 580 */       y[1] = a[1];
/* 581 */       splitAdd(y, LN_SPLIT_COEF[i], a);
/* 582 */       y[0] = a[0];
/* 583 */       y[1] = a[1];
/*     */     } 
/*     */     
/* 586 */     splitMult(y, x, a);
/* 587 */     y[0] = a[0];
/* 588 */     y[1] = a[1];
/*     */     
/* 590 */     return y;
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
/*     */   static void printarray(PrintStream out, String name, int expectedLen, double[][] array2d) {
/* 602 */     out.println(name);
/* 603 */     checkLen(expectedLen, array2d.length);
/* 604 */     out.println("    { ");
/* 605 */     int i = 0;
/* 606 */     for (double[] array : array2d) {
/* 607 */       out.print("        {");
/* 608 */       for (double d : array) {
/* 609 */         out.printf("%-25.25s", new Object[] { format(d) });
/*     */       } 
/* 611 */       out.println("}, // " + i++);
/*     */     } 
/* 613 */     out.println("    };");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void printarray(PrintStream out, String name, int expectedLen, double[] array) {
/* 624 */     out.println(name + "=");
/* 625 */     checkLen(expectedLen, array.length);
/* 626 */     out.println("    {");
/* 627 */     for (double d : array) {
/* 628 */       out.printf("        %s%n", new Object[] { format(d) });
/*     */     } 
/* 630 */     out.println("    };");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String format(double d) {
/* 638 */     if (d != d) {
/* 639 */       return "Double.NaN,";
/*     */     }
/* 641 */     return ((d >= 0.0D) ? "+" : "") + Double.toString(d) + "d,";
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
/*     */   private static void checkLen(int expectedLen, int actual) throws DimensionMismatchException {
/* 653 */     if (expectedLen != actual)
/* 654 */       throw new DimensionMismatchException(actual, expectedLen); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/FastMathCalc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */