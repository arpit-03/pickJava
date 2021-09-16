/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Conv
/*     */ {
/*     */   public static void conv(int lx, int kx, float[] x, int ly, int ky, float[] y, int lz, int kz, float[] z) {
/*  95 */     convFast(lx, kx, x, ly, ky, y, lz, kz, z);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void conv(int lx1, int lx2, int kx1, int kx2, float[][] x, int ly1, int ly2, int ky1, int ky2, float[][] y, int lz1, int lz2, int kz1, int kz2, float[][] z) {
/* 121 */     zero(lz1, lz2, z);
/* 122 */     int ilo2 = kz2 - kx2 - ky2;
/* 123 */     int ihi2 = ilo2 + lz2 - 1;
/* 124 */     for (int i2 = ilo2; i2 <= ihi2; i2++) {
/* 125 */       int jlo2 = ArrayMath.max(0, i2 - ly2 + 1);
/* 126 */       int jhi2 = ArrayMath.min(lx2 - 1, i2);
/* 127 */       for (int j2 = jlo2; j2 <= jhi2; j2++) {
/* 128 */         convSum(lx1, kx1, x[j2], ly1, ky1, y[i2 - j2], lz1, kz1, z[i2 - ilo2]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void conv(int lx1, int lx2, int lx3, int kx1, int kx2, int kx3, float[][][] x, int ly1, int ly2, int ly3, int ky1, int ky2, int ky3, float[][][] y, int lz1, int lz2, int lz3, int kz1, int kz2, int kz3, float[][][] z) {
/* 162 */     zero(lz1, lz2, lz3, z);
/* 163 */     int ilo2 = kz2 - kx2 - ky2;
/* 164 */     int ilo3 = kz3 - kx3 - ky3;
/* 165 */     int ihi2 = ilo2 + lz2 - 1;
/* 166 */     int ihi3 = ilo3 + lz3 - 1;
/* 167 */     for (int i3 = ilo3; i3 <= ihi3; i3++) {
/* 168 */       int jlo3 = ArrayMath.max(0, i3 - ly3 + 1);
/* 169 */       int jhi3 = ArrayMath.min(lx3 - 1, i3);
/* 170 */       for (int j3 = jlo3; j3 <= jhi3; j3++) {
/* 171 */         for (int i2 = ilo2; i2 <= ihi2; i2++) {
/* 172 */           int jlo2 = ArrayMath.max(0, i2 - ly2 + 1);
/* 173 */           int jhi2 = ArrayMath.min(lx2 - 1, i2);
/* 174 */           for (int j2 = jlo2; j2 <= jhi2; j2++) {
/* 175 */             convSum(lx1, kx1, x[j3][j2], ly1, ky1, y[i3 - j3][i2 - j2], lz1, kz1, z[i3 - ilo3][i2 - ilo2]);
/*     */           }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void xcor(int lx, int kx, float[] x, int ly, int ky, float[] y, int lz, int kz, float[] z) {
/* 201 */     boolean copy = (x == y);
/* 202 */     x = reverse(lx, x, copy);
/* 203 */     kx = 1 - kx - lx;
/* 204 */     conv(lx, kx, x, ly, ky, y, lz, kz, z);
/* 205 */     if (!copy) {
/* 206 */       reverse(lx, x, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void xcor(int lx1, int lx2, int kx1, int kx2, float[][] x, int ly1, int ly2, int ky1, int ky2, float[][] y, int lz1, int lz2, int kz1, int kz2, float[][] z) {
/* 232 */     boolean copy = (x == y);
/* 233 */     x = reverse(lx1, lx2, x, copy);
/* 234 */     kx1 = 1 - kx1 - lx1;
/* 235 */     kx2 = 1 - kx2 - lx2;
/* 236 */     conv(lx1, lx2, kx1, kx2, x, ly1, ly2, ky1, ky2, y, lz1, lz2, kz1, kz2, z);
/* 237 */     if (!copy) {
/* 238 */       reverse(lx1, lx2, x, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void xcor(int lx1, int lx2, int lx3, int kx1, int kx2, int kx3, float[][][] x, int ly1, int ly2, int ly3, int ky1, int ky2, int ky3, float[][][] y, int lz1, int lz2, int lz3, int kz1, int kz2, int kz3, float[][][] z) {
/* 270 */     boolean copy = (x == y);
/* 271 */     x = reverse(lx1, lx2, lx3, x, copy);
/* 272 */     kx1 = 1 - kx1 - lx1;
/* 273 */     kx2 = 1 - kx2 - lx2;
/* 274 */     kx3 = 1 - kx3 - lx3;
/* 275 */     conv(lx1, lx2, lx3, kx1, kx2, kx3, x, ly1, ly2, ly3, ky1, ky2, ky3, y, lz1, lz2, lz3, kz1, kz2, kz3, z);
/*     */ 
/*     */     
/* 278 */     if (!copy) {
/* 279 */       reverse(lx1, lx2, lx3, x, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convFast(int lx, int kx, float[] x, int ly, int ky, float[] y, int lz, int kz, float[] z) {
/* 359 */     if (lx > ly) {
/* 360 */       int lt = lx; lx = ly; ly = lt;
/* 361 */       int kt = kx; kx = ky; ky = kt;
/* 362 */       float[] t = x; x = y; y = t;
/*     */     } 
/*     */ 
/*     */     
/* 366 */     int imin = kz - kx - ky;
/* 367 */     int imax = imin + lz - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 374 */     int ilo = imin;
/* 375 */     int ihi = ArrayMath.min(-1, imax); int i, iz;
/* 376 */     for (i = ilo, iz = i - imin; i <= ihi; i++, iz++) {
/* 377 */       z[iz] = 0.0F;
/*     */     }
/*     */     
/* 380 */     ilo = ArrayMath.max(0, imin);
/* 381 */     ihi = ArrayMath.min(lx - 2, imax);
/* 382 */     int jlo = 0;
/* 383 */     int jhi = ilo;
/* 384 */     for (i = ilo, iz = i - imin; i < ihi; i += 2, iz += 2, jhi += 2) {
/* 385 */       float sa = 0.0F;
/* 386 */       float sb = 0.0F;
/* 387 */       float yb = y[i - jlo + 1]; int j;
/* 388 */       for (j = jlo; j < jhi; j += 2) {
/* 389 */         float f1 = x[j];
/* 390 */         sb += f1 * yb;
/* 391 */         float ya = y[i - j];
/* 392 */         sa += f1 * ya;
/* 393 */         float xb = x[j + 1];
/* 394 */         sb += xb * ya;
/* 395 */         yb = y[i - j - 1];
/* 396 */         sa += xb * yb;
/*     */       } 
/* 398 */       float xa = x[j];
/* 399 */       sb += xa * yb;
/* 400 */       if (j == jhi) {
/* 401 */         float ya = y[i - j];
/* 402 */         sa += xa * ya;
/* 403 */         float xb = x[j + 1];
/* 404 */         sb += xb * ya;
/*     */       } 
/* 406 */       z[iz] = sa;
/* 407 */       z[iz + 1] = sb;
/*     */     } 
/* 409 */     if (i == ihi) {
/* 410 */       jlo = 0;
/* 411 */       jhi = i;
/* 412 */       float sa = 0.0F;
/* 413 */       for (int j = jlo; j <= jhi; j++)
/* 414 */         sa += x[j] * y[i - j]; 
/* 415 */       z[iz] = sa;
/*     */     } 
/*     */ 
/*     */     
/* 419 */     ilo = ArrayMath.max(lx - 1, imin);
/* 420 */     ihi = ArrayMath.min(ly - 1, imax);
/* 421 */     jlo = 0;
/* 422 */     jhi = lx - 1;
/* 423 */     for (i = ilo, iz = i - imin; i < ihi; i += 2, iz += 2) {
/* 424 */       float sa = 0.0F;
/* 425 */       float sb = 0.0F;
/* 426 */       float yb = y[i - jlo + 1]; int j;
/* 427 */       for (j = jlo; j < jhi; j += 2) {
/* 428 */         float xa = x[j];
/* 429 */         sb += xa * yb;
/* 430 */         float ya = y[i - j];
/* 431 */         sa += xa * ya;
/* 432 */         float xb = x[j + 1];
/* 433 */         sb += xb * ya;
/* 434 */         yb = y[i - j - 1];
/* 435 */         sa += xb * yb;
/*     */       } 
/* 437 */       if (j == jhi) {
/* 438 */         float xa = x[j];
/* 439 */         sb += xa * yb;
/* 440 */         float ya = y[i - j];
/* 441 */         sa += xa * ya;
/*     */       } 
/* 443 */       z[iz] = sa;
/* 444 */       z[iz + 1] = sb;
/*     */     } 
/* 446 */     if (i == ihi) {
/* 447 */       float sa = 0.0F;
/* 448 */       for (int j = jlo; j <= jhi; j++)
/* 449 */         sa += x[j] * y[i - j]; 
/* 450 */       z[iz] = sa;
/*     */     } 
/*     */ 
/*     */     
/* 454 */     ilo = ArrayMath.max(ly, imin);
/* 455 */     ihi = ArrayMath.min(lx + ly - 2, imax);
/* 456 */     jlo = ihi - ly + 1;
/* 457 */     jhi = lx - 1;
/* 458 */     for (i = ihi, iz = i - imin; i > ilo; i -= 2, iz -= 2, jlo -= 2) {
/* 459 */       float sa = 0.0F;
/* 460 */       float sb = 0.0F;
/* 461 */       float yb = y[i - jhi - 1]; int j;
/* 462 */       for (j = jhi; j > jlo; j -= 2) {
/* 463 */         float f1 = x[j];
/* 464 */         sb += f1 * yb;
/* 465 */         float ya = y[i - j];
/* 466 */         sa += f1 * ya;
/* 467 */         float xb = x[j - 1];
/* 468 */         sb += xb * ya;
/* 469 */         yb = y[i - j + 1];
/* 470 */         sa += xb * yb;
/*     */       } 
/* 472 */       float xa = x[j];
/* 473 */       sb += xa * yb;
/* 474 */       if (j == jlo) {
/* 475 */         float ya = y[i - j];
/* 476 */         sa += xa * ya;
/* 477 */         float xb = x[j - 1];
/* 478 */         sb += xb * ya;
/*     */       } 
/* 480 */       z[iz] = sa;
/* 481 */       z[iz - 1] = sb;
/*     */     } 
/* 483 */     if (i == ilo) {
/* 484 */       jlo = i - ly + 1;
/* 485 */       jhi = lx - 1;
/* 486 */       float sa = 0.0F;
/* 487 */       for (int j = jhi; j >= jlo; j--)
/* 488 */         sa += x[j] * y[i - j]; 
/* 489 */       z[iz] = sa;
/*     */     } 
/*     */ 
/*     */     
/* 493 */     ilo = ArrayMath.max(lx + ly - 1, imin);
/* 494 */     ihi = imax;
/* 495 */     for (i = ilo, iz = i - imin; i <= ihi; i++, iz++) {
/* 496 */       z[iz] = 0.0F;
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
/*     */   private static void convSum(int lx, int kx, float[] x, int ly, int ky, float[] y, int lz, int kz, float[] z) {
/* 509 */     if (lx > ly) {
/* 510 */       int lt = lx; lx = ly; ly = lt;
/* 511 */       int kt = kx; kx = ky; ky = kt;
/* 512 */       float[] t = x; x = y; y = t;
/*     */     } 
/*     */ 
/*     */     
/* 516 */     int imin = kz - kx - ky;
/* 517 */     int imax = imin + lz - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 524 */     int ilo = ArrayMath.max(0, imin);
/* 525 */     int ihi = ArrayMath.min(lx - 2, imax);
/* 526 */     int jlo = 0;
/* 527 */     int jhi = ilo; int i, iz;
/* 528 */     for (i = ilo, iz = i - imin; i < ihi; i += 2, iz += 2, jhi += 2) {
/* 529 */       float sa = z[iz];
/* 530 */       float sb = z[iz + 1];
/* 531 */       float yb = y[i - jlo + 1]; int j;
/* 532 */       for (j = jlo; j < jhi; j += 2) {
/* 533 */         float f1 = x[j];
/* 534 */         sb += f1 * yb;
/* 535 */         float ya = y[i - j];
/* 536 */         sa += f1 * ya;
/* 537 */         float xb = x[j + 1];
/* 538 */         sb += xb * ya;
/* 539 */         yb = y[i - j - 1];
/* 540 */         sa += xb * yb;
/*     */       } 
/* 542 */       float xa = x[j];
/* 543 */       sb += xa * yb;
/* 544 */       if (j == jhi) {
/* 545 */         float ya = y[i - j];
/* 546 */         sa += xa * ya;
/* 547 */         float xb = x[j + 1];
/* 548 */         sb += xb * ya;
/*     */       } 
/* 550 */       z[iz] = sa;
/* 551 */       z[iz + 1] = sb;
/*     */     } 
/* 553 */     if (i == ihi) {
/* 554 */       jlo = 0;
/* 555 */       jhi = i;
/* 556 */       float sa = z[iz];
/* 557 */       for (int j = jlo; j <= jhi; j++)
/* 558 */         sa += x[j] * y[i - j]; 
/* 559 */       z[iz] = sa;
/*     */     } 
/*     */ 
/*     */     
/* 563 */     ilo = ArrayMath.max(lx - 1, imin);
/* 564 */     ihi = ArrayMath.min(ly - 1, imax);
/* 565 */     jlo = 0;
/* 566 */     jhi = lx - 1;
/* 567 */     for (i = ilo, iz = i - imin; i < ihi; i += 2, iz += 2) {
/* 568 */       float sa = z[iz];
/* 569 */       float sb = z[iz + 1];
/* 570 */       float yb = y[i - jlo + 1]; int j;
/* 571 */       for (j = jlo; j < jhi; j += 2) {
/* 572 */         float xa = x[j];
/* 573 */         sb += xa * yb;
/* 574 */         float ya = y[i - j];
/* 575 */         sa += xa * ya;
/* 576 */         float xb = x[j + 1];
/* 577 */         sb += xb * ya;
/* 578 */         yb = y[i - j - 1];
/* 579 */         sa += xb * yb;
/*     */       } 
/* 581 */       if (j == jhi) {
/* 582 */         float xa = x[j];
/* 583 */         sb += xa * yb;
/* 584 */         float ya = y[i - j];
/* 585 */         sa += xa * ya;
/*     */       } 
/* 587 */       z[iz] = sa;
/* 588 */       z[iz + 1] = sb;
/*     */     } 
/* 590 */     if (i == ihi) {
/* 591 */       float sa = z[iz];
/* 592 */       for (int j = jlo; j <= jhi; j++)
/* 593 */         sa += x[j] * y[i - j]; 
/* 594 */       z[iz] = sa;
/*     */     } 
/*     */ 
/*     */     
/* 598 */     ilo = ArrayMath.max(ly, imin);
/* 599 */     ihi = ArrayMath.min(lx + ly - 2, imax);
/* 600 */     jlo = ihi - ly + 1;
/* 601 */     jhi = lx - 1;
/* 602 */     for (i = ihi, iz = i - imin; i > ilo; i -= 2, iz -= 2, jlo -= 2) {
/* 603 */       float sa = z[iz];
/* 604 */       float sb = z[iz - 1];
/* 605 */       float yb = y[i - jhi - 1]; int j;
/* 606 */       for (j = jhi; j > jlo; j -= 2) {
/* 607 */         float f1 = x[j];
/* 608 */         sb += f1 * yb;
/* 609 */         float ya = y[i - j];
/* 610 */         sa += f1 * ya;
/* 611 */         float xb = x[j - 1];
/* 612 */         sb += xb * ya;
/* 613 */         yb = y[i - j + 1];
/* 614 */         sa += xb * yb;
/*     */       } 
/* 616 */       float xa = x[j];
/* 617 */       sb += xa * yb;
/* 618 */       if (j == jlo) {
/* 619 */         float ya = y[i - j];
/* 620 */         sa += xa * ya;
/* 621 */         float xb = x[j - 1];
/* 622 */         sb += xb * ya;
/*     */       } 
/* 624 */       z[iz] = sa;
/* 625 */       z[iz - 1] = sb;
/*     */     } 
/* 627 */     if (i == ilo) {
/* 628 */       jlo = i - ly + 1;
/* 629 */       jhi = lx - 1;
/* 630 */       float sa = z[iz];
/* 631 */       for (int j = jhi; j >= jlo; j--)
/* 632 */         sa += x[j] * y[i - j]; 
/* 633 */       z[iz] = sa;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void zero(int n1, float[] z) {
/* 638 */     for (int i1 = 0; i1 < n1; i1++)
/* 639 */       z[i1] = 0.0F; 
/*     */   }
/*     */   
/*     */   private static void zero(int n1, int n2, float[][] z) {
/* 643 */     for (int i2 = 0; i2 < n2; i2++)
/* 644 */       zero(n1, z[i2]); 
/*     */   }
/*     */   
/*     */   private static void zero(int n1, int n2, int n3, float[][][] z) {
/* 648 */     for (int i3 = 0; i3 < n3; i3++)
/* 649 */       zero(n1, n2, z[i3]); 
/*     */   }
/*     */   
/*     */   private static float[] reverse(int n1, float[] z, boolean copy) {
/* 653 */     if (copy)
/* 654 */       z = ArrayMath.copy(n1, z); 
/* 655 */     for (int i1 = 0, j1 = n1 - 1; i1 < j1; i1++, j1--) {
/* 656 */       float zt = z[i1];
/* 657 */       z[i1] = z[j1];
/* 658 */       z[j1] = zt;
/*     */     } 
/* 660 */     return z;
/*     */   }
/*     */   
/*     */   private static float[][] reverse(int n1, int n2, float[][] z, boolean copy) {
/* 664 */     if (copy)
/* 665 */       z = ArrayMath.copy(n1, n2, z);  int i2; int j2;
/* 666 */     for (i2 = 0, j2 = n2 - 1; i2 < j2; i2++, j2--) {
/* 667 */       float[] zt = z[i2];
/* 668 */       z[i2] = z[j2];
/* 669 */       z[j2] = zt;
/*     */     } 
/* 671 */     for (i2 = 0; i2 < n2; i2++)
/* 672 */       reverse(n1, z[i2], false); 
/* 673 */     return z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[][][] reverse(int n1, int n2, int n3, float[][][] z, boolean copy) {
/* 679 */     if (copy)
/* 680 */       z = ArrayMath.copy(n1, n2, n3, z);  int i3; int j3;
/* 681 */     for (i3 = 0, j3 = n3 - 1; i3 < j3; i3++, j3--) {
/* 682 */       float[][] zt = z[i3];
/* 683 */       z[i3] = z[j3];
/* 684 */       z[j3] = zt;
/*     */     } 
/* 686 */     for (i3 = 0; i3 < n3; i3++)
/* 687 */       reverse(n1, n2, z[i3], false); 
/* 688 */     return z;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Conv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */