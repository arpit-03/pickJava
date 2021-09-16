/*      */ package edu.mines.jtk.mesh;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Geometry
/*      */ {
/*      */   private static final double EPSILON;
/*      */   private static final double SPLITTER;
/*      */   private static final double O2DERRBOUND;
/*      */   private static final double O3DERRBOUND;
/*      */   private static final double INCERRBOUND;
/*      */   private static final double INSERRBOUND;
/*      */   private static final double IOSERRBOUND;
/*      */   
/*      */   public static double leftOfLine(double xa, double ya, double xb, double yb, double xc, double yc) {
/*   64 */     double detsum, detleft = (xa - xc) * (yb - yc);
/*   65 */     double detright = (ya - yc) * (xb - xc);
/*   66 */     double det = detleft - detright;
/*      */     
/*   68 */     if (detleft > 0.0D) {
/*   69 */       if (detright <= 0.0D) {
/*   70 */         return det;
/*      */       }
/*   72 */       detsum = detleft + detright;
/*      */     }
/*   74 */     else if (detleft < 0.0D) {
/*   75 */       if (detright >= 0.0D) {
/*   76 */         return det;
/*      */       }
/*   78 */       detsum = -detleft - detright;
/*      */     } else {
/*      */       
/*   81 */       return det;
/*      */     } 
/*   83 */     double errbound = O2DERRBOUND * detsum;
/*   84 */     if (det >= errbound || -det >= errbound) {
/*   85 */       return det;
/*      */     }
/*   87 */     return leftOfLineExact(xa, ya, xb, yb, xc, yc);
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
/*      */   public static double leftOfLine(double[] pa, double[] pb, double[] pc) {
/*  104 */     return leftOfLine(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1]);
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
/*      */   public static double leftOfLine(float[] pa, float[] pb, float[] pc) {
/*  124 */     return leftOfLine(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1]);
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
/*      */   public static double leftOfLineFast(double xa, double ya, double xb, double yb, double xc, double yc) {
/*  151 */     double acx = xa - xc;
/*  152 */     double bcx = xb - xc;
/*  153 */     double acy = ya - yc;
/*  154 */     double bcy = yb - yc;
/*  155 */     return acx * bcy - acy * bcx;
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
/*      */   public static double leftOfLineFast(double[] pa, double[] pb, double[] pc) {
/*  174 */     return leftOfLineFast(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1]);
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
/*      */   public static double leftOfLineFast(float[] pa, float[] pb, float[] pc) {
/*  196 */     return leftOfLineFast(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1]);
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
/*      */   public static double leftOfPlane(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd) {
/*  228 */     double adx = xa - xd;
/*  229 */     double bdx = xb - xd;
/*  230 */     double cdx = xc - xd;
/*  231 */     double ady = ya - yd;
/*  232 */     double bdy = yb - yd;
/*  233 */     double cdy = yc - yd;
/*  234 */     double adz = za - zd;
/*  235 */     double bdz = zb - zd;
/*  236 */     double cdz = zc - zd;
/*      */     
/*  238 */     double bdxcdy = bdx * cdy;
/*  239 */     double cdxbdy = cdx * bdy;
/*      */     
/*  241 */     double cdxady = cdx * ady;
/*  242 */     double adxcdy = adx * cdy;
/*      */     
/*  244 */     double adxbdy = adx * bdy;
/*  245 */     double bdxady = bdx * ady;
/*      */     
/*  247 */     double det = adz * (bdxcdy - cdxbdy) + bdz * (cdxady - adxcdy) + cdz * (adxbdy - bdxady);
/*      */ 
/*      */ 
/*      */     
/*  251 */     if (adz < 0.0D) adz = -adz; 
/*  252 */     if (bdz < 0.0D) bdz = -bdz; 
/*  253 */     if (cdz < 0.0D) cdz = -cdz; 
/*  254 */     if (bdxcdy < 0.0D) bdxcdy = -bdxcdy; 
/*  255 */     if (cdxbdy < 0.0D) cdxbdy = -cdxbdy; 
/*  256 */     if (cdxady < 0.0D) cdxady = -cdxady; 
/*  257 */     if (adxcdy < 0.0D) adxcdy = -adxcdy; 
/*  258 */     if (adxbdy < 0.0D) adxbdy = -adxbdy; 
/*  259 */     if (bdxady < 0.0D) bdxady = -bdxady; 
/*  260 */     double permanent = (bdxcdy + cdxbdy) * adz + (cdxady + adxcdy) * bdz + (adxbdy + bdxady) * cdz;
/*      */ 
/*      */     
/*  263 */     double errbound = O3DERRBOUND * permanent;
/*  264 */     if (det > errbound || -det > errbound) {
/*  265 */       return det;
/*      */     }
/*      */     
/*  268 */     return leftOfPlaneExact(xa, ya, za, xb, yb, zb, xc, yc, zc, xd, yd, zd);
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
/*      */   public static double leftOfPlane(double[] pa, double[] pb, double[] pc, double[] pd) {
/*  286 */     return leftOfPlane(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2]);
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
/*      */   public static double leftOfPlane(float[] pa, float[] pb, float[] pc, float[] pd) {
/*  308 */     return leftOfPlane(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2]);
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
/*      */   public static double leftOfPlaneFast(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd) {
/*  343 */     double adx = xa - xd;
/*  344 */     double bdx = xb - xd;
/*  345 */     double cdx = xc - xd;
/*  346 */     double ady = ya - yd;
/*  347 */     double bdy = yb - yd;
/*  348 */     double cdy = yc - yd;
/*  349 */     double adz = za - zd;
/*  350 */     double bdz = zb - zd;
/*  351 */     double cdz = zc - zd;
/*      */     
/*  353 */     return adx * (bdy * cdz - bdz * cdy) + bdx * (cdy * adz - cdz * ady) + cdx * (ady * bdz - adz * bdy);
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
/*      */   public static double leftOfPlaneFast(double[] pa, double[] pb, double[] pc, double[] pd) {
/*  375 */     return leftOfPlaneFast(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2]);
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
/*      */   public static double leftOfPlaneFast(float[] pa, float[] pb, float[] pc, float[] pd) {
/*  399 */     return leftOfPlaneFast(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2]);
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
/*      */   public static double inCircle(double xa, double ya, double xb, double yb, double xc, double yc, double xd, double yd) {
/*  428 */     double adx = xa - xd;
/*  429 */     double bdx = xb - xd;
/*  430 */     double cdx = xc - xd;
/*  431 */     double ady = ya - yd;
/*  432 */     double bdy = yb - yd;
/*  433 */     double cdy = yc - yd;
/*      */     
/*  435 */     double bdxcdy = bdx * cdy;
/*  436 */     double cdxbdy = cdx * bdy;
/*  437 */     double alift = adx * adx + ady * ady;
/*      */     
/*  439 */     double cdxady = cdx * ady;
/*  440 */     double adxcdy = adx * cdy;
/*  441 */     double blift = bdx * bdx + bdy * bdy;
/*      */     
/*  443 */     double adxbdy = adx * bdy;
/*  444 */     double bdxady = bdx * ady;
/*  445 */     double clift = cdx * cdx + cdy * cdy;
/*      */     
/*  447 */     double det = alift * (bdxcdy - cdxbdy) + blift * (cdxady - adxcdy) + clift * (adxbdy - bdxady);
/*      */ 
/*      */ 
/*      */     
/*  451 */     if (bdxcdy < 0.0D) bdxcdy = -bdxcdy; 
/*  452 */     if (cdxbdy < 0.0D) cdxbdy = -cdxbdy; 
/*  453 */     if (adxcdy < 0.0D) adxcdy = -adxcdy; 
/*  454 */     if (cdxady < 0.0D) cdxady = -cdxady; 
/*  455 */     if (adxbdy < 0.0D) adxbdy = -adxbdy; 
/*  456 */     if (bdxady < 0.0D) bdxady = -bdxady;
/*      */     
/*  458 */     double permanent = alift * (bdxcdy + cdxbdy) + blift * (cdxady + adxcdy) + clift * (adxbdy + bdxady);
/*      */ 
/*      */     
/*  461 */     double errbound = INCERRBOUND * permanent;
/*  462 */     if (det > errbound || -det > errbound) {
/*  463 */       return det;
/*      */     }
/*  465 */     return inCircleExact(xa, ya, xb, yb, xc, yc, xd, yd);
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
/*      */   public static double inCircle(double[] pa, double[] pb, double[] pc, double[] pd) {
/*  483 */     return inCircle(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1], pd[0], pd[1]);
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
/*      */   public static double inCircle(float[] pa, float[] pb, float[] pc, float[] pd) {
/*  505 */     return inCircle(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1], pd[0], pd[1]);
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
/*      */   public static double inCircleFast(double xa, double ya, double xb, double yb, double xc, double yc, double xd, double yd) {
/*  536 */     double adx = xa - xd;
/*  537 */     double ady = ya - yd;
/*  538 */     double bdx = xb - xd;
/*  539 */     double bdy = yb - yd;
/*  540 */     double cdx = xc - xd;
/*  541 */     double cdy = yc - yd;
/*      */     
/*  543 */     double abdet = adx * bdy - bdx * ady;
/*  544 */     double bcdet = bdx * cdy - cdx * bdy;
/*  545 */     double cadet = cdx * ady - adx * cdy;
/*  546 */     double alift = adx * adx + ady * ady;
/*  547 */     double blift = bdx * bdx + bdy * bdy;
/*  548 */     double clift = cdx * cdx + cdy * cdy;
/*      */     
/*  550 */     return alift * bcdet + blift * cadet + clift * abdet;
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
/*      */   public static double inCircleFast(double[] pa, double[] pb, double[] pc, double[] pd) {
/*  570 */     return inCircleFast(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1], pd[0], pd[1]);
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
/*      */   public static double inCircleFast(float[] pa, float[] pb, float[] pc, float[] pd) {
/*  594 */     return inCircleFast(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1], pd[0], pd[1]);
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
/*      */   public static double inSphere(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd, double xe, double ye, double ze) {
/*  631 */     double aex = xa - xe;
/*  632 */     double bex = xb - xe;
/*  633 */     double cex = xc - xe;
/*  634 */     double dex = xd - xe;
/*  635 */     double aey = ya - ye;
/*  636 */     double bey = yb - ye;
/*  637 */     double cey = yc - ye;
/*  638 */     double dey = yd - ye;
/*  639 */     double aez = za - ze;
/*  640 */     double bez = zb - ze;
/*  641 */     double cez = zc - ze;
/*  642 */     double dez = zd - ze;
/*      */     
/*  644 */     double aexbey = aex * bey;
/*  645 */     double bexaey = bex * aey;
/*  646 */     double ab = aexbey - bexaey;
/*  647 */     double bexcey = bex * cey;
/*  648 */     double cexbey = cex * bey;
/*  649 */     double bc = bexcey - cexbey;
/*  650 */     double cexdey = cex * dey;
/*  651 */     double dexcey = dex * cey;
/*  652 */     double cd = cexdey - dexcey;
/*  653 */     double dexaey = dex * aey;
/*  654 */     double aexdey = aex * dey;
/*  655 */     double da = dexaey - aexdey;
/*      */     
/*  657 */     double aexcey = aex * cey;
/*  658 */     double cexaey = cex * aey;
/*  659 */     double ac = aexcey - cexaey;
/*  660 */     double bexdey = bex * dey;
/*  661 */     double dexbey = dex * bey;
/*  662 */     double bd = bexdey - dexbey;
/*      */     
/*  664 */     double abc = aez * bc - bez * ac + cez * ab;
/*  665 */     double bcd = bez * cd - cez * bd + dez * bc;
/*  666 */     double cda = cez * da + dez * ac + aez * cd;
/*  667 */     double dab = dez * ab + aez * bd + bez * da;
/*      */     
/*  669 */     double alift = aex * aex + aey * aey + aez * aez;
/*  670 */     double blift = bex * bex + bey * bey + bez * bez;
/*  671 */     double clift = cex * cex + cey * cey + cez * cez;
/*  672 */     double dlift = dex * dex + dey * dey + dez * dez;
/*      */     
/*  674 */     double det = dlift * abc - clift * dab + blift * cda - alift * bcd;
/*      */     
/*  676 */     if (aez < 0.0D) aez = -aez; 
/*  677 */     if (bez < 0.0D) bez = -bez; 
/*  678 */     if (cez < 0.0D) cez = -cez; 
/*  679 */     if (dez < 0.0D) dez = -dez; 
/*  680 */     if (aexbey < 0.0D) aexbey = -aexbey; 
/*  681 */     if (bexaey < 0.0D) bexaey = -bexaey; 
/*  682 */     if (bexcey < 0.0D) bexcey = -bexcey; 
/*  683 */     if (cexbey < 0.0D) cexbey = -cexbey; 
/*  684 */     if (cexdey < 0.0D) cexdey = -cexdey; 
/*  685 */     if (dexcey < 0.0D) dexcey = -dexcey; 
/*  686 */     if (dexaey < 0.0D) dexaey = -dexaey; 
/*  687 */     if (aexdey < 0.0D) aexdey = -aexdey; 
/*  688 */     if (aexcey < 0.0D) aexcey = -aexcey; 
/*  689 */     if (cexaey < 0.0D) cexaey = -cexaey; 
/*  690 */     if (bexdey < 0.0D) bexdey = -bexdey; 
/*  691 */     if (dexbey < 0.0D) dexbey = -dexbey; 
/*  692 */     double permanent = ((cexdey + dexcey) * bez + (dexbey + bexdey) * cez + (bexcey + cexbey) * dez) * alift + ((dexaey + aexdey) * cez + (aexcey + cexaey) * dez + (cexdey + dexcey) * aez) * blift + ((aexbey + bexaey) * dez + (bexdey + dexbey) * aez + (dexaey + aexdey) * bez) * clift + ((bexcey + cexbey) * aez + (cexaey + aexcey) * bez + (aexbey + bexaey) * cez) * dlift;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  708 */     double errbound = INSERRBOUND * permanent;
/*  709 */     if (det > errbound || -det > errbound) {
/*  710 */       return det;
/*      */     }
/*      */     
/*  713 */     return inSphereExact(xa, ya, za, xb, yb, zb, xc, yc, zc, xd, yd, zd, xe, ye, ze);
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
/*      */   public static double inSphere(double[] pa, double[] pb, double[] pc, double[] pd, double[] pe) {
/*  732 */     return inSphere(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2], pe[0], pe[1], pe[2]);
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
/*      */   public static double inSphere(float[] pa, float[] pb, float[] pc, float[] pd, float[] pe) {
/*  756 */     return inSphere(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2], pe[0], pe[1], pe[2]);
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
/*      */   public static double inSphereFast(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd, double xe, double ye, double ze) {
/*  796 */     double aex = xa - xe;
/*  797 */     double bex = xb - xe;
/*  798 */     double cex = xc - xe;
/*  799 */     double dex = xd - xe;
/*  800 */     double aey = ya - ye;
/*  801 */     double bey = yb - ye;
/*  802 */     double cey = yc - ye;
/*  803 */     double dey = yd - ye;
/*  804 */     double aez = za - ze;
/*  805 */     double bez = zb - ze;
/*  806 */     double cez = zc - ze;
/*  807 */     double dez = zd - ze;
/*      */     
/*  809 */     double ab = aex * bey - bex * aey;
/*  810 */     double bc = bex * cey - cex * bey;
/*  811 */     double cd = cex * dey - dex * cey;
/*  812 */     double da = dex * aey - aex * dey;
/*      */     
/*  814 */     double ac = aex * cey - cex * aey;
/*  815 */     double bd = bex * dey - dex * bey;
/*      */     
/*  817 */     double abc = aez * bc - bez * ac + cez * ab;
/*  818 */     double bcd = bez * cd - cez * bd + dez * bc;
/*  819 */     double cda = cez * da + dez * ac + aez * cd;
/*  820 */     double dab = dez * ab + aez * bd + bez * da;
/*      */     
/*  822 */     double alift = aex * aex + aey * aey + aez * aez;
/*  823 */     double blift = bex * bex + bey * bey + bez * bez;
/*  824 */     double clift = cex * cex + cey * cey + cez * cez;
/*  825 */     double dlift = dex * dex + dey * dey + dez * dez;
/*      */     
/*  827 */     return dlift * abc - clift * dab + blift * cda - alift * bcd;
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
/*      */   public static double inSphereFast(double[] pa, double[] pb, double[] pc, double[] pd, double[] pe) {
/*  848 */     return inSphereFast(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2], pe[0], pe[1], pe[2]);
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
/*      */   public static double inSphereFast(float[] pa, float[] pb, float[] pc, float[] pd, float[] pe) {
/*  874 */     return inSphereFast(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2], pe[0], pe[1], pe[2]);
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
/*      */   public static double inOrthoSphere(double xa, double ya, double za, double wa, double xb, double yb, double zb, double wb, double xc, double yc, double zc, double wc, double xd, double yd, double zd, double wd, double xe, double ye, double ze, double we) {
/*  924 */     double aex = xa - xe;
/*  925 */     double bex = xb - xe;
/*  926 */     double cex = xc - xe;
/*  927 */     double dex = xd - xe;
/*  928 */     double aey = ya - ye;
/*  929 */     double bey = yb - ye;
/*  930 */     double cey = yc - ye;
/*  931 */     double dey = yd - ye;
/*  932 */     double aez = za - ze;
/*  933 */     double bez = zb - ze;
/*  934 */     double cez = zc - ze;
/*  935 */     double dez = zd - ze;
/*  936 */     double aew = wa - we;
/*  937 */     double bew = wb - we;
/*  938 */     double cew = wc - we;
/*  939 */     double dew = wd - we;
/*      */     
/*  941 */     double aexbey = aex * bey;
/*  942 */     double bexaey = bex * aey;
/*  943 */     double ab = aexbey - bexaey;
/*  944 */     double bexcey = bex * cey;
/*  945 */     double cexbey = cex * bey;
/*  946 */     double bc = bexcey - cexbey;
/*  947 */     double cexdey = cex * dey;
/*  948 */     double dexcey = dex * cey;
/*  949 */     double cd = cexdey - dexcey;
/*  950 */     double dexaey = dex * aey;
/*  951 */     double aexdey = aex * dey;
/*  952 */     double da = dexaey - aexdey;
/*      */     
/*  954 */     double aexcey = aex * cey;
/*  955 */     double cexaey = cex * aey;
/*  956 */     double ac = aexcey - cexaey;
/*  957 */     double bexdey = bex * dey;
/*  958 */     double dexbey = dex * bey;
/*  959 */     double bd = bexdey - dexbey;
/*      */     
/*  961 */     double abc = aez * bc - bez * ac + cez * ab;
/*  962 */     double bcd = bez * cd - cez * bd + dez * bc;
/*  963 */     double cda = cez * da + dez * ac + aez * cd;
/*  964 */     double dab = dez * ab + aez * bd + bez * da;
/*      */     
/*  966 */     double alift = aex * aex + aey * aey + aez * aez;
/*  967 */     double blift = bex * bex + bey * bey + bez * bez;
/*  968 */     double clift = cex * cex + cey * cey + cez * cez;
/*  969 */     double dlift = dex * dex + dey * dey + dez * dez;
/*      */     
/*  971 */     double det = (dlift - dew) * abc - (clift - cew) * dab + (blift - bew) * cda - (alift - aew) * bcd;
/*      */ 
/*      */     
/*  974 */     if (aez < 0.0D) aez = -aez; 
/*  975 */     if (bez < 0.0D) bez = -bez; 
/*  976 */     if (cez < 0.0D) cez = -cez; 
/*  977 */     if (dez < 0.0D) dez = -dez; 
/*  978 */     if (aew < 0.0D) aew = -aew; 
/*  979 */     if (bew < 0.0D) bew = -bew; 
/*  980 */     if (cew < 0.0D) cew = -cew; 
/*  981 */     if (dew < 0.0D) dew = -dew; 
/*  982 */     if (aexbey < 0.0D) aexbey = -aexbey; 
/*  983 */     if (bexaey < 0.0D) bexaey = -bexaey; 
/*  984 */     if (bexcey < 0.0D) bexcey = -bexcey; 
/*  985 */     if (cexbey < 0.0D) cexbey = -cexbey; 
/*  986 */     if (cexdey < 0.0D) cexdey = -cexdey; 
/*  987 */     if (dexcey < 0.0D) dexcey = -dexcey; 
/*  988 */     if (dexaey < 0.0D) dexaey = -dexaey; 
/*  989 */     if (aexdey < 0.0D) aexdey = -aexdey; 
/*  990 */     if (aexcey < 0.0D) aexcey = -aexcey; 
/*  991 */     if (cexaey < 0.0D) cexaey = -cexaey; 
/*  992 */     if (bexdey < 0.0D) bexdey = -bexdey; 
/*  993 */     if (dexbey < 0.0D) dexbey = -dexbey; 
/*  994 */     double permanent = ((cexdey + dexcey) * bez + (dexbey + bexdey) * cez + (bexcey + cexbey) * dez) * (alift + aew) + ((dexaey + aexdey) * cez + (aexcey + cexaey) * dez + (cexdey + dexcey) * aez) * (blift + bew) + ((aexbey + bexaey) * dez + (bexdey + dexbey) * aez + (dexaey + aexdey) * bez) * (clift + cew) + ((bexcey + cexbey) * aez + (cexaey + aexcey) * bez + (aexbey + bexaey) * cez) * (dlift + dew);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1010 */     double errbound = IOSERRBOUND * permanent;
/* 1011 */     if (det > errbound || -det > errbound) {
/* 1012 */       return det;
/*      */     }
/*      */     
/* 1015 */     return inOrthoSphereExact(xa, ya, za, wa, xb, yb, zb, wb, xc, yc, zc, wc, xd, yd, zd, wd, xe, ye, ze, we);
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
/*      */   public static double inOrthoSphere(double[] pa, double[] pb, double[] pc, double[] pd, double[] pe) {
/* 1039 */     return inOrthoSphere(pa[0], pa[1], pa[2], pa[3], pb[0], pb[1], pb[2], pb[3], pc[0], pc[1], pc[2], pc[3], pd[0], pd[1], pd[2], pd[3], pe[0], pe[1], pe[2], pe[3]);
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
/*      */   public static double inOrthoSphere(float[] pa, float[] pb, float[] pc, float[] pd, float[] pe) {
/* 1062 */     return inOrthoSphere(pa[0], pa[1], pa[2], pa[3], pb[0], pb[1], pb[2], pb[3], pc[0], pc[1], pc[2], pc[3], pd[0], pd[1], pd[2], pd[3], pe[0], pe[1], pe[2], pe[3]);
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
/*      */   public static double inOrthoSphereFast(double xa, double ya, double za, double wa, double xb, double yb, double zb, double wb, double xc, double yc, double zc, double wc, double xd, double yd, double zd, double wd, double xe, double ye, double ze, double we) {
/* 1106 */     double aex = xa - xe;
/* 1107 */     double bex = xb - xe;
/* 1108 */     double cex = xc - xe;
/* 1109 */     double dex = xd - xe;
/* 1110 */     double aey = ya - ye;
/* 1111 */     double bey = yb - ye;
/* 1112 */     double cey = yc - ye;
/* 1113 */     double dey = yd - ye;
/* 1114 */     double aez = za - ze;
/* 1115 */     double bez = zb - ze;
/* 1116 */     double cez = zc - ze;
/* 1117 */     double dez = zd - ze;
/* 1118 */     double aew = wa - we;
/* 1119 */     double bew = wb - we;
/* 1120 */     double cew = wc - we;
/* 1121 */     double dew = wd - we;
/*      */     
/* 1123 */     double ab = aex * bey - bex * aey;
/* 1124 */     double bc = bex * cey - cex * bey;
/* 1125 */     double cd = cex * dey - dex * cey;
/* 1126 */     double da = dex * aey - aex * dey;
/*      */     
/* 1128 */     double ac = aex * cey - cex * aey;
/* 1129 */     double bd = bex * dey - dex * bey;
/*      */     
/* 1131 */     double abc = aez * bc - bez * ac + cez * ab;
/* 1132 */     double bcd = bez * cd - cez * bd + dez * bc;
/* 1133 */     double cda = cez * da + dez * ac + aez * cd;
/* 1134 */     double dab = dez * ab + aez * bd + bez * da;
/*      */     
/* 1136 */     double alift = aex * aex + aey * aey + aez * aez - aew;
/* 1137 */     double blift = bex * bex + bey * bey + bez * bez - bew;
/* 1138 */     double clift = cex * cex + cey * cey + cez * cez - cew;
/* 1139 */     double dlift = dex * dex + dey * dey + dez * dez - dew;
/*      */     
/* 1141 */     return dlift * abc - clift * dab + blift * cda - alift * bcd;
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
/*      */   public static double inOrthoSphereFast(double[] pa, double[] pb, double[] pc, double[] pd, double[] pe) {
/* 1161 */     return inOrthoSphereFast(pa[0], pa[1], pa[2], pa[3], pb[0], pb[1], pb[2], pb[3], pc[0], pc[1], pc[2], pc[3], pd[0], pd[1], pd[2], pd[3], pe[0], pe[1], pe[2], pe[3]);
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
/*      */   public static double inOrthoSphereFast(float[] pa, float[] pb, float[] pc, float[] pd, float[] pe) {
/* 1186 */     return inOrthoSphereFast(pa[0], pa[1], pa[2], pa[3], pb[0], pb[1], pb[2], pb[3], pc[0], pc[1], pc[2], pc[3], pd[0], pd[1], pd[2], pd[3], pe[0], pe[1], pe[2], pe[3]);
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
/*      */   public static void centerCircle(float xa, float ya, float xb, float yb, float xc, float yc, float[] po) {
/* 1212 */     double acx = (xa - xc);
/* 1213 */     double bcx = (xb - xc);
/* 1214 */     double acy = (ya - yc);
/* 1215 */     double bcy = (yb - yc);
/* 1216 */     double acs = acx * acx + acy * acy;
/* 1217 */     double bcs = bcx * bcx + bcy * bcy;
/* 1218 */     double scale = 0.5D / leftOfLine(xa, ya, xb, yb, xc, yc);
/* 1219 */     po[0] = (float)(xc + scale * (acs * bcy - bcs * acy));
/* 1220 */     po[1] = (float)(yc + scale * (bcs * acx - acs * bcx));
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
/*      */   public static void centerCircle(float[] pa, float[] pb, float[] pc, float[] po) {
/* 1235 */     centerCircle(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1], po);
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
/*      */   public static void centerCircle(double xa, double ya, double xb, double yb, double xc, double yc, double[] po) {
/* 1259 */     double acx = xa - xc;
/* 1260 */     double bcx = xb - xc;
/* 1261 */     double acy = ya - yc;
/* 1262 */     double bcy = yb - yc;
/* 1263 */     double acs = acx * acx + acy * acy;
/* 1264 */     double bcs = bcx * bcx + bcy * bcy;
/* 1265 */     double scale = 0.5D / leftOfLine(xa, ya, xb, yb, xc, yc);
/* 1266 */     po[0] = xc + scale * (acs * bcy - bcs * acy);
/* 1267 */     po[1] = yc + scale * (bcs * acx - acs * bcx);
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
/*      */   public static void centerCircle(double[] pa, double[] pb, double[] pc, double[] po) {
/* 1282 */     centerCircle(pa[0], pa[1], pb[0], pb[1], pc[0], pc[1], po);
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
/*      */   public static void centerSphere(float xa, float ya, float za, float xb, float yb, float zb, float xc, float yc, float zc, float xd, float yd, float zd, float[] po) {
/* 1313 */     double adx = (xa - xd);
/* 1314 */     double bdx = (xb - xd);
/* 1315 */     double cdx = (xc - xd);
/* 1316 */     double ady = (ya - yd);
/* 1317 */     double bdy = (yb - yd);
/* 1318 */     double cdy = (yc - yd);
/* 1319 */     double adz = (za - zd);
/* 1320 */     double bdz = (zb - zd);
/* 1321 */     double cdz = (zc - zd);
/* 1322 */     double ads = adx * adx + ady * ady + adz * adz;
/* 1323 */     double bds = bdx * bdx + bdy * bdy + bdz * bdz;
/* 1324 */     double cds = cdx * cdx + cdy * cdy + cdz * cdz;
/* 1325 */     double scale = 0.5D / leftOfPlane(xa, ya, za, xb, yb, zb, xc, yc, zc, xd, yd, zd);
/* 1326 */     po[0] = xd + (float)(scale * (ads * (bdy * cdz - cdy * bdz) + bds * (cdy * adz - ady * cdz) + cds * (ady * bdz - bdy * adz)));
/*      */ 
/*      */     
/* 1329 */     po[1] = yd + (float)(scale * (ads * (bdz * cdx - cdz * bdx) + bds * (cdz * adx - adz * cdx) + cds * (adz * bdx - bdz * adx)));
/*      */ 
/*      */     
/* 1332 */     po[2] = zd + (float)(scale * (ads * (bdx * cdy - cdx * bdy) + bds * (cdx * ady - adx * cdy) + cds * (adx * bdy - bdx * ady)));
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
/*      */   public static void centerSphere(float[] pa, float[] pb, float[] pc, float[] pd, float[] po) {
/* 1350 */     centerSphere(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2], po);
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
/*      */   public static void centerSphere(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd, double[] po) {
/* 1382 */     double adx = xa - xd;
/* 1383 */     double bdx = xb - xd;
/* 1384 */     double cdx = xc - xd;
/* 1385 */     double ady = ya - yd;
/* 1386 */     double bdy = yb - yd;
/* 1387 */     double cdy = yc - yd;
/* 1388 */     double adz = za - zd;
/* 1389 */     double bdz = zb - zd;
/* 1390 */     double cdz = zc - zd;
/* 1391 */     double ads = adx * adx + ady * ady + adz * adz;
/* 1392 */     double bds = bdx * bdx + bdy * bdy + bdz * bdz;
/* 1393 */     double cds = cdx * cdx + cdy * cdy + cdz * cdz;
/* 1394 */     double scale = 0.5D / leftOfPlane(xa, ya, za, xb, yb, zb, xc, yc, zc, xd, yd, zd);
/* 1395 */     po[0] = xd + scale * (ads * (bdy * cdz - cdy * bdz) + bds * (cdy * adz - ady * cdz) + cds * (ady * bdz - bdy * adz));
/*      */ 
/*      */     
/* 1398 */     po[1] = yd + scale * (ads * (bdz * cdx - cdz * bdx) + bds * (cdz * adx - adz * cdx) + cds * (adz * bdx - bdz * adx));
/*      */ 
/*      */     
/* 1401 */     po[2] = zd + scale * (ads * (bdx * cdy - cdx * bdy) + bds * (cdx * ady - adx * cdy) + cds * (adx * bdy - bdx * ady));
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
/*      */   public static void centerSphere(double[] pa, double[] pb, double[] pc, double[] pd, double[] po) {
/* 1419 */     centerSphere(pa[0], pa[1], pa[2], pb[0], pb[1], pb[2], pc[0], pc[1], pc[2], pd[0], pd[1], pd[2], po);
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
/*      */   public static void centerCircle3D(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double[] po) {
/* 1446 */     double acx = xa - xc;
/* 1447 */     double acy = ya - yc;
/* 1448 */     double acz = za - zc;
/* 1449 */     double bcx = xb - xc;
/* 1450 */     double bcy = yb - yc;
/* 1451 */     double bcz = zb - zc;
/* 1452 */     double acs = acx * acx + acy * acy + acz * acz;
/* 1453 */     double bcs = bcx * bcx + bcy * bcy + bcz * bcz;
/* 1454 */     double abx = leftOfLine(ya, za, yb, zb, yc, zc);
/* 1455 */     double aby = leftOfLine(za, xa, zb, xb, zc, xc);
/* 1456 */     double abz = leftOfLine(xa, ya, xb, yb, xc, yc);
/* 1457 */     double scale = 0.5D / (abx * abx + aby * aby + abz * abz);
/* 1458 */     po[0] = xc + scale * ((acs * bcy - bcs * acy) * abz - (acs * bcz - bcs * acz) * aby);
/* 1459 */     po[1] = yc + scale * ((acs * bcz - bcs * acz) * abx - (acs * bcx - bcs * acx) * abz);
/* 1460 */     po[2] = zc + scale * ((acs * bcx - bcs * acx) * aby - (acs * bcy - bcs * acy) * abx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Two
/*      */   {
/*      */     double x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     double y;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Two() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1496 */     double epsilon = 1.0D;
/* 1497 */     double splitter = 1.0D;
/* 1498 */     boolean everyOther = true;
/*      */     while (true) {
/* 1500 */       epsilon *= 0.5D;
/* 1501 */       if (everyOther)
/* 1502 */         splitter *= 2.0D; 
/* 1503 */       everyOther = !everyOther;
/* 1504 */       if (1.0D + epsilon == 1.0D) {
/* 1505 */         splitter++;
/* 1506 */         EPSILON = epsilon;
/* 1507 */         SPLITTER = splitter;
/* 1508 */         O2DERRBOUND = 4.0D * EPSILON;
/* 1509 */         O3DERRBOUND = 8.0D * EPSILON;
/* 1510 */         INCERRBOUND = 11.0D * EPSILON;
/* 1511 */         INSERRBOUND = 17.0D * EPSILON;
/* 1512 */         IOSERRBOUND = 19.0D * EPSILON;
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static strictfp void twoSum(double a, double b, Two t) {
/* 1520 */     double x = a + b;
/* 1521 */     double bvirt = x - a;
/* 1522 */     double avirt = x - bvirt;
/* 1523 */     double bround = b - bvirt;
/* 1524 */     double around = a - avirt;
/* 1525 */     t.x = x;
/* 1526 */     t.y = around + bround;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static strictfp void twoSumFast(double a, double b, Two t) {
/* 1534 */     double x = a + b;
/* 1535 */     double bvirt = x - a;
/* 1536 */     t.x = x;
/* 1537 */     t.y = b - bvirt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static strictfp void twoDiff(double a, double b, Two t) {
/* 1545 */     double x = a - b;
/* 1546 */     double bvirt = a - x;
/* 1547 */     double avirt = x + bvirt;
/* 1548 */     double bround = bvirt - b;
/* 1549 */     double around = a - avirt;
/* 1550 */     t.x = x;
/* 1551 */     t.y = around + bround;
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
/*      */   private static strictfp void split(double a, Two t) {
/* 1572 */     double c = SPLITTER * a;
/* 1573 */     double abig = c - a;
/* 1574 */     t.x = c - abig;
/* 1575 */     t.y = a - t.x;
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
/*      */   private static strictfp void twoProduct1Presplit(double a, double b, double bhi, double blo, Two t) {
/* 1606 */     split(a, t);
/* 1607 */     double ahi = t.x;
/* 1608 */     double alo = t.y;
/* 1609 */     t.x = a * b;
/* 1610 */     double err1 = t.x - ahi * bhi;
/* 1611 */     double err2 = err1 - alo * bhi;
/* 1612 */     double err3 = err2 - ahi * blo;
/* 1613 */     t.y = alo * blo - err3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static strictfp void twoProduct2Presplit(double a, double ahi, double alo, double b, double bhi, double blo, Two t) {
/* 1623 */     t.x = a * b;
/* 1624 */     double err1 = t.x - ahi * bhi;
/* 1625 */     double err2 = err1 - alo * bhi;
/* 1626 */     double err3 = err2 - ahi * blo;
/* 1627 */     t.y = alo * blo - err3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static strictfp void twoTwoProduct(double a1, double a0, double b1, double b0, double[] x) {
/* 1638 */     Two t = new Two();
/* 1639 */     split(a0, t);
/* 1640 */     double a0hi = t.x;
/* 1641 */     double a0lo = t.y;
/* 1642 */     split(b0, t);
/* 1643 */     double b0hi = t.x;
/* 1644 */     double b0lo = t.y;
/* 1645 */     twoProduct2Presplit(a0, a0hi, a0lo, b0, b0hi, b0lo, t);
/* 1646 */     double ui = t.x;
/* 1647 */     x[0] = t.y;
/* 1648 */     split(a1, t);
/* 1649 */     double a1hi = t.x;
/* 1650 */     double a1lo = t.y;
/* 1651 */     twoProduct2Presplit(a1, a1hi, a1lo, b0, b0hi, b0lo, t);
/* 1652 */     double uj = t.x;
/* 1653 */     double u0 = t.y;
/* 1654 */     twoSum(ui, u0, t);
/* 1655 */     double uk = t.x;
/* 1656 */     double u1 = t.y;
/* 1657 */     twoSumFast(uj, uk, t);
/* 1658 */     double ul = t.x;
/* 1659 */     double u2 = t.y;
/* 1660 */     split(b1, t);
/* 1661 */     double b1hi = t.x;
/* 1662 */     double b1lo = t.y;
/* 1663 */     twoProduct2Presplit(a0, a0hi, a0lo, b1, b1hi, b1lo, t);
/* 1664 */     ui = t.x;
/* 1665 */     u0 = t.y;
/* 1666 */     twoSum(u1, u0, t);
/* 1667 */     uk = t.x;
/* 1668 */     x[1] = t.y;
/* 1669 */     twoSum(u2, uk, t);
/* 1670 */     uj = t.x;
/* 1671 */     u1 = t.y;
/* 1672 */     twoSum(ul, uj, t);
/* 1673 */     double um = t.x;
/* 1674 */     u2 = t.y;
/* 1675 */     twoProduct2Presplit(a1, a1hi, a1lo, b1, b1hi, b1lo, t);
/* 1676 */     uj = t.x;
/* 1677 */     u0 = t.y;
/* 1678 */     twoSum(ui, u0, t);
/* 1679 */     double un = t.x;
/* 1680 */     u0 = t.y;
/* 1681 */     twoSum(u1, u0, t);
/* 1682 */     ui = t.x;
/* 1683 */     x[2] = t.y;
/* 1684 */     twoSum(u2, ui, t);
/* 1685 */     uk = t.x;
/* 1686 */     u1 = t.y;
/* 1687 */     twoSum(um, uk, t);
/* 1688 */     ul = t.x;
/* 1689 */     u2 = t.y;
/* 1690 */     twoSum(uj, un, t);
/* 1691 */     uk = t.x;
/* 1692 */     u0 = t.y;
/* 1693 */     twoSum(u1, u0, t);
/* 1694 */     uj = t.x;
/* 1695 */     x[3] = t.y;
/* 1696 */     twoSum(u2, uj, t);
/* 1697 */     ui = t.x;
/* 1698 */     u1 = t.y;
/* 1699 */     twoSum(ul, ui, t);
/* 1700 */     um = t.x;
/* 1701 */     u2 = t.y;
/* 1702 */     twoSum(u1, uk, t);
/* 1703 */     ui = t.x;
/* 1704 */     x[4] = t.y;
/* 1705 */     twoSum(u2, ui, t);
/* 1706 */     uk = t.x;
/* 1707 */     x[5] = t.y;
/* 1708 */     twoSum(um, uk, t);
/* 1709 */     x[7] = t.x;
/* 1710 */     x[6] = t.y;
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
/*      */   private static int expansionSumZeroElimFast(int elen, double[] e, int flen, double[] f, double[] h) {
/*      */     double q;
/* 1725 */     Two t = new Two();
/* 1726 */     double enow = e[0];
/* 1727 */     double fnow = f[0];
/* 1728 */     int eindex = 0;
/* 1729 */     int findex = 0;
/* 1730 */     if (((fnow > enow) ? true : false) == ((fnow > -enow) ? true : false)) {
/* 1731 */       q = enow;
/* 1732 */       eindex++;
/* 1733 */       if (eindex < elen)
/* 1734 */         enow = e[eindex]; 
/*      */     } else {
/* 1736 */       q = fnow;
/* 1737 */       findex++;
/* 1738 */       if (findex < flen)
/* 1739 */         fnow = f[findex]; 
/*      */     } 
/* 1741 */     int hindex = 0;
/* 1742 */     if (eindex < elen && findex < flen) {
/* 1743 */       double qnew, hh; if (((fnow > enow) ? true : false) == ((fnow > -enow) ? true : false)) {
/* 1744 */         twoSumFast(enow, q, t);
/* 1745 */         qnew = t.x;
/* 1746 */         hh = t.y;
/* 1747 */         eindex++;
/* 1748 */         if (eindex < elen)
/* 1749 */           enow = e[eindex]; 
/*      */       } else {
/* 1751 */         twoSumFast(fnow, q, t);
/* 1752 */         qnew = t.x;
/* 1753 */         hh = t.y;
/* 1754 */         findex++;
/* 1755 */         if (findex < flen)
/* 1756 */           fnow = f[findex]; 
/*      */       } 
/* 1758 */       q = qnew;
/* 1759 */       if (hh != 0.0D)
/* 1760 */         h[hindex++] = hh; 
/* 1761 */       while (eindex < elen && findex < flen) {
/* 1762 */         if (((fnow > enow) ? true : false) == ((fnow > -enow) ? true : false)) {
/* 1763 */           twoSum(q, enow, t);
/* 1764 */           qnew = t.x;
/* 1765 */           hh = t.y;
/* 1766 */           eindex++;
/* 1767 */           if (eindex < elen)
/* 1768 */             enow = e[eindex]; 
/*      */         } else {
/* 1770 */           twoSum(q, fnow, t);
/* 1771 */           qnew = t.x;
/* 1772 */           hh = t.y;
/* 1773 */           findex++;
/* 1774 */           if (findex < flen)
/* 1775 */             fnow = f[findex]; 
/*      */         } 
/* 1777 */         q = qnew;
/* 1778 */         if (hh != 0.0D)
/* 1779 */           h[hindex++] = hh; 
/*      */       } 
/*      */     } 
/* 1782 */     while (eindex < elen) {
/* 1783 */       twoSum(q, enow, t);
/* 1784 */       double qnew = t.x;
/* 1785 */       double hh = t.y;
/* 1786 */       eindex++;
/* 1787 */       if (eindex < elen)
/* 1788 */         enow = e[eindex]; 
/* 1789 */       q = qnew;
/* 1790 */       if (hh != 0.0D)
/* 1791 */         h[hindex++] = hh; 
/*      */     } 
/* 1793 */     while (findex < flen) {
/* 1794 */       twoSum(q, fnow, t);
/* 1795 */       double qnew = t.x;
/* 1796 */       double hh = t.y;
/* 1797 */       findex++;
/* 1798 */       if (findex < flen)
/* 1799 */         fnow = f[findex]; 
/* 1800 */       q = qnew;
/* 1801 */       if (hh != 0.0D)
/* 1802 */         h[hindex++] = hh; 
/*      */     } 
/* 1804 */     if (q != 0.0D || hindex == 0)
/* 1805 */       h[hindex++] = q; 
/* 1806 */     return hindex;
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
/*      */   private static int scaleExpansionZeroElim(int elen, double[] e, double b, double[] h) {
/* 1820 */     Two t = new Two();
/* 1821 */     split(b, t);
/* 1822 */     double bhi = t.x;
/* 1823 */     double blo = t.y;
/* 1824 */     twoProduct1Presplit(e[0], b, bhi, blo, t);
/* 1825 */     double q = t.x;
/* 1826 */     double hh = t.y;
/* 1827 */     int hindex = 0;
/* 1828 */     if (hh != 0.0D)
/* 1829 */       h[hindex++] = hh; 
/* 1830 */     for (int eindex = 1; eindex < elen; eindex++) {
/* 1831 */       double enow = e[eindex];
/* 1832 */       twoProduct1Presplit(enow, b, bhi, blo, t);
/* 1833 */       double product1 = t.x;
/* 1834 */       double product0 = t.y;
/* 1835 */       twoSum(q, product0, t);
/* 1836 */       double sum = t.x;
/* 1837 */       hh = t.y;
/* 1838 */       if (hh != 0.0D)
/* 1839 */         h[hindex++] = hh; 
/* 1840 */       twoSumFast(product1, sum, t);
/* 1841 */       q = t.x;
/* 1842 */       hh = t.y;
/* 1843 */       if (hh != 0.0D)
/* 1844 */         h[hindex++] = hh; 
/*      */     } 
/* 1846 */     if (q != 0.0D || hindex == 0)
/* 1847 */       h[hindex++] = q; 
/* 1848 */     return hindex;
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
/*      */   private static double leftOfLineExact(double xa, double ya, double xb, double yb, double xc, double yc) {
/* 1864 */     Two t = new Two();
/* 1865 */     twoDiff(xa, xc, t);
/* 1866 */     double acx = t.x;
/* 1867 */     double acxtail = t.y;
/* 1868 */     twoDiff(ya, yc, t);
/* 1869 */     double acy = t.x;
/* 1870 */     double acytail = t.y;
/* 1871 */     twoDiff(xb, xc, t);
/* 1872 */     double bcx = t.x;
/* 1873 */     double bcxtail = t.y;
/* 1874 */     twoDiff(yb, yc, t);
/* 1875 */     double bcy = t.x;
/* 1876 */     double bcytail = t.y;
/*      */     
/* 1878 */     double[] axby = new double[8];
/* 1879 */     double[] bxay = new double[8];
/* 1880 */     twoTwoProduct(acx, acxtail, bcy, bcytail, axby);
/* 1881 */     double negate = -acy;
/* 1882 */     double negatetail = -acytail;
/* 1883 */     twoTwoProduct(bcx, bcxtail, negate, negatetail, bxay);
/*      */     
/* 1885 */     double[] det = new double[16];
/* 1886 */     int detlen = expansionSumZeroElimFast(8, axby, 8, bxay, det);
/*      */     
/* 1888 */     return det[detlen - 1];
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
/*      */   private static double leftOfPlaneExact(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd) {
/* 1906 */     Two t = new Two();
/* 1907 */     twoDiff(xa, xd, t);
/* 1908 */     double adx = t.x;
/* 1909 */     double adxtail = t.y;
/* 1910 */     twoDiff(ya, yd, t);
/* 1911 */     double ady = t.x;
/* 1912 */     double adytail = t.y;
/* 1913 */     twoDiff(za, zd, t);
/* 1914 */     double adz = t.x;
/* 1915 */     double adztail = t.y;
/* 1916 */     twoDiff(xb, xd, t);
/* 1917 */     double bdx = t.x;
/* 1918 */     double bdxtail = t.y;
/* 1919 */     twoDiff(yb, yd, t);
/* 1920 */     double bdy = t.x;
/* 1921 */     double bdytail = t.y;
/* 1922 */     twoDiff(zb, zd, t);
/* 1923 */     double bdz = t.x;
/* 1924 */     double bdztail = t.y;
/* 1925 */     twoDiff(xc, xd, t);
/* 1926 */     double cdx = t.x;
/* 1927 */     double cdxtail = t.y;
/* 1928 */     twoDiff(yc, yd, t);
/* 1929 */     double cdy = t.x;
/* 1930 */     double cdytail = t.y;
/* 1931 */     twoDiff(zc, zd, t);
/* 1932 */     double cdz = t.x;
/* 1933 */     double cdztail = t.y;
/*      */     
/* 1935 */     double[] axby = new double[8];
/* 1936 */     twoTwoProduct(adx, adxtail, bdy, bdytail, axby);
/* 1937 */     double negate = -ady;
/* 1938 */     double negatetail = -adytail;
/* 1939 */     double[] bxay = new double[8];
/* 1940 */     twoTwoProduct(bdx, bdxtail, negate, negatetail, bxay);
/*      */     
/* 1942 */     double[] bxcy = new double[8];
/* 1943 */     twoTwoProduct(bdx, bdxtail, cdy, cdytail, bxcy);
/* 1944 */     negate = -bdy;
/* 1945 */     negatetail = -bdytail;
/* 1946 */     double[] cxby = new double[8];
/* 1947 */     twoTwoProduct(cdx, cdxtail, negate, negatetail, cxby);
/*      */     
/* 1949 */     double[] cxay = new double[8];
/* 1950 */     twoTwoProduct(cdx, cdxtail, ady, adytail, cxay);
/* 1951 */     negate = -cdy;
/* 1952 */     negatetail = -cdytail;
/* 1953 */     double[] axcy = new double[8];
/* 1954 */     twoTwoProduct(adx, adxtail, negate, negatetail, axcy);
/*      */     
/* 1956 */     double[] t16 = new double[16];
/* 1957 */     double[] t32 = new double[32];
/* 1958 */     double[] t32t = new double[32];
/*      */ 
/*      */     
/* 1961 */     int t16len = expansionSumZeroElimFast(8, bxcy, 8, cxby, t16);
/* 1962 */     int t32len = scaleExpansionZeroElim(t16len, t16, adz, t32);
/* 1963 */     int t32tlen = scaleExpansionZeroElim(t16len, t16, adztail, t32t);
/* 1964 */     double[] adet = new double[64];
/* 1965 */     int alen = expansionSumZeroElimFast(t32len, t32, t32tlen, t32t, adet);
/*      */     
/* 1967 */     t16len = expansionSumZeroElimFast(8, cxay, 8, axcy, t16);
/* 1968 */     t32len = scaleExpansionZeroElim(t16len, t16, bdz, t32);
/* 1969 */     t32tlen = scaleExpansionZeroElim(t16len, t16, bdztail, t32t);
/* 1970 */     double[] bdet = new double[64];
/* 1971 */     int blen = expansionSumZeroElimFast(t32len, t32, t32tlen, t32t, bdet);
/*      */     
/* 1973 */     t16len = expansionSumZeroElimFast(8, axby, 8, bxay, t16);
/* 1974 */     t32len = scaleExpansionZeroElim(t16len, t16, cdz, t32);
/* 1975 */     t32tlen = scaleExpansionZeroElim(t16len, t16, cdztail, t32t);
/* 1976 */     double[] cdet = new double[64];
/* 1977 */     int clen = expansionSumZeroElimFast(t32len, t32, t32tlen, t32t, cdet);
/*      */     
/* 1979 */     double[] abdet = new double[128];
/* 1980 */     int ablen = expansionSumZeroElimFast(alen, adet, blen, bdet, abdet);
/* 1981 */     double[] det = new double[192];
/* 1982 */     int detlen = expansionSumZeroElimFast(ablen, abdet, clen, cdet, det);
/*      */     
/* 1984 */     return det[detlen - 1];
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
/*      */   private static double inCircleExact(double xa, double ya, double xb, double yb, double xc, double yc, double xd, double yd) {
/* 2001 */     Two t = new Two();
/* 2002 */     twoDiff(xa, xd, t);
/* 2003 */     double adx = t.x;
/* 2004 */     double adxtail = t.y;
/* 2005 */     twoDiff(ya, yd, t);
/* 2006 */     double ady = t.x;
/* 2007 */     double adytail = t.y;
/* 2008 */     twoDiff(xb, xd, t);
/* 2009 */     double bdx = t.x;
/* 2010 */     double bdxtail = t.y;
/* 2011 */     twoDiff(yb, yd, t);
/* 2012 */     double bdy = t.x;
/* 2013 */     double bdytail = t.y;
/* 2014 */     twoDiff(xc, xd, t);
/* 2015 */     double cdx = t.x;
/* 2016 */     double cdxtail = t.y;
/* 2017 */     twoDiff(yc, yd, t);
/* 2018 */     double cdy = t.x;
/* 2019 */     double cdytail = t.y;
/*      */     
/* 2021 */     double[] axby = new double[8];
/* 2022 */     double[] bxay = new double[8];
/* 2023 */     twoTwoProduct(adx, adxtail, bdy, bdytail, axby);
/* 2024 */     double negate = -ady;
/* 2025 */     double negatetail = -adytail;
/* 2026 */     twoTwoProduct(bdx, bdxtail, negate, negatetail, bxay);
/*      */     
/* 2028 */     double[] bxcy = new double[8];
/* 2029 */     double[] cxby = new double[8];
/* 2030 */     twoTwoProduct(bdx, bdxtail, cdy, cdytail, bxcy);
/* 2031 */     negate = -bdy;
/* 2032 */     negatetail = -bdytail;
/* 2033 */     twoTwoProduct(cdx, cdxtail, negate, negatetail, cxby);
/*      */     
/* 2035 */     double[] cxay = new double[8];
/* 2036 */     double[] axcy = new double[8];
/* 2037 */     twoTwoProduct(cdx, cdxtail, ady, adytail, cxay);
/* 2038 */     negate = -cdy;
/* 2039 */     negatetail = -cdytail;
/* 2040 */     twoTwoProduct(adx, adxtail, negate, negatetail, axcy);
/*      */     
/* 2042 */     double[] t16 = new double[16];
/* 2043 */     int t16len = expansionSumZeroElimFast(8, bxcy, 8, cxby, t16);
/*      */     
/* 2045 */     double[] detx = new double[32];
/* 2046 */     double[] detxx = new double[64];
/* 2047 */     double[] detxt = new double[32];
/* 2048 */     double[] detxxt = new double[64];
/* 2049 */     double[] detxtxt = new double[64];
/* 2050 */     double[] x1 = new double[128];
/* 2051 */     double[] x2 = new double[192];
/* 2052 */     int xlen = scaleExpansionZeroElim(t16len, t16, adx, detx);
/* 2053 */     int xxlen = scaleExpansionZeroElim(xlen, detx, adx, detxx);
/* 2054 */     int xtlen = scaleExpansionZeroElim(t16len, t16, adxtail, detxt);
/* 2055 */     int xxtlen = scaleExpansionZeroElim(xtlen, detxt, adx, detxxt);
/* 2056 */     for (int i = 0; i < xxtlen; i++)
/* 2057 */       detxxt[i] = detxxt[i] * 2.0D; 
/* 2058 */     int xtxtlen = scaleExpansionZeroElim(xtlen, detxt, adxtail, detxtxt);
/* 2059 */     int x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2060 */     int x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/*      */     
/* 2062 */     double[] dety = new double[32];
/* 2063 */     double[] detyy = new double[64];
/* 2064 */     double[] detyt = new double[32];
/* 2065 */     double[] detyyt = new double[64];
/* 2066 */     double[] detytyt = new double[64];
/* 2067 */     double[] y1 = new double[128];
/* 2068 */     double[] y2 = new double[192];
/* 2069 */     int ylen = scaleExpansionZeroElim(t16len, t16, ady, dety);
/* 2070 */     int yylen = scaleExpansionZeroElim(ylen, dety, ady, detyy);
/* 2071 */     int ytlen = scaleExpansionZeroElim(t16len, t16, adytail, detyt);
/* 2072 */     int yytlen = scaleExpansionZeroElim(ytlen, detyt, ady, detyyt);
/* 2073 */     for (int j = 0; j < yytlen; j++)
/* 2074 */       detyyt[j] = detyyt[j] * 2.0D; 
/* 2075 */     int ytytlen = scaleExpansionZeroElim(ytlen, detyt, adytail, detytyt);
/* 2076 */     int y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2077 */     int y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/*      */     
/* 2079 */     double[] adet = new double[384];
/* 2080 */     double[] bdet = new double[384];
/* 2081 */     double[] cdet = new double[384];
/* 2082 */     int alen = expansionSumZeroElimFast(x2len, x2, y2len, y2, adet);
/*      */     
/* 2084 */     t16len = expansionSumZeroElimFast(8, cxay, 8, axcy, t16);
/* 2085 */     xlen = scaleExpansionZeroElim(t16len, t16, bdx, detx);
/* 2086 */     xxlen = scaleExpansionZeroElim(xlen, detx, bdx, detxx);
/* 2087 */     xtlen = scaleExpansionZeroElim(t16len, t16, bdxtail, detxt);
/* 2088 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, bdx, detxxt); int k;
/* 2089 */     for (k = 0; k < xxtlen; k++)
/* 2090 */       detxxt[k] = detxxt[k] * 2.0D; 
/* 2091 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, bdxtail, detxtxt);
/* 2092 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2093 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/*      */     
/* 2095 */     ylen = scaleExpansionZeroElim(t16len, t16, bdy, dety);
/* 2096 */     yylen = scaleExpansionZeroElim(ylen, dety, bdy, detyy);
/* 2097 */     ytlen = scaleExpansionZeroElim(t16len, t16, bdytail, detyt);
/* 2098 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, bdy, detyyt);
/* 2099 */     for (k = 0; k < yytlen; k++)
/* 2100 */       detyyt[k] = detyyt[k] * 2.0D; 
/* 2101 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, bdytail, detytyt);
/* 2102 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2103 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2104 */     int blen = expansionSumZeroElimFast(x2len, x2, y2len, y2, bdet);
/*      */     
/* 2106 */     t16len = expansionSumZeroElimFast(8, axby, 8, bxay, t16);
/* 2107 */     xlen = scaleExpansionZeroElim(t16len, t16, cdx, detx);
/* 2108 */     xxlen = scaleExpansionZeroElim(xlen, detx, cdx, detxx);
/* 2109 */     xtlen = scaleExpansionZeroElim(t16len, t16, cdxtail, detxt);
/* 2110 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, cdx, detxxt); int m;
/* 2111 */     for (m = 0; m < xxtlen; m++)
/* 2112 */       detxxt[m] = detxxt[m] * 2.0D; 
/* 2113 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, cdxtail, detxtxt);
/* 2114 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2115 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2116 */     ylen = scaleExpansionZeroElim(t16len, t16, cdy, dety);
/* 2117 */     yylen = scaleExpansionZeroElim(ylen, dety, cdy, detyy);
/* 2118 */     ytlen = scaleExpansionZeroElim(t16len, t16, cdytail, detyt);
/* 2119 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, cdy, detyyt);
/* 2120 */     for (m = 0; m < yytlen; m++)
/* 2121 */       detyyt[m] = detyyt[m] * 2.0D; 
/* 2122 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, cdytail, detytyt);
/* 2123 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2124 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2125 */     int clen = expansionSumZeroElimFast(x2len, x2, y2len, y2, cdet);
/*      */     
/* 2127 */     double[] abdet = new double[768];
/* 2128 */     double[] det = new double[1152];
/* 2129 */     int ablen = expansionSumZeroElimFast(alen, adet, blen, bdet, abdet);
/* 2130 */     int detlen = expansionSumZeroElimFast(ablen, abdet, clen, cdet, det);
/*      */     
/* 2132 */     return det[detlen - 1];
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
/*      */   private static double inSphereExact(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc, double xd, double yd, double zd, double xe, double ye, double ze) {
/* 2151 */     Two t = new Two();
/* 2152 */     twoDiff(xa, xe, t);
/* 2153 */     double aex = t.x;
/* 2154 */     double aextail = t.y;
/* 2155 */     twoDiff(ya, ye, t);
/* 2156 */     double aey = t.x;
/* 2157 */     double aeytail = t.y;
/* 2158 */     twoDiff(za, ze, t);
/* 2159 */     double aez = t.x;
/* 2160 */     double aeztail = t.y;
/* 2161 */     twoDiff(xb, xe, t);
/* 2162 */     double bex = t.x;
/* 2163 */     double bextail = t.y;
/* 2164 */     twoDiff(yb, ye, t);
/* 2165 */     double bey = t.x;
/* 2166 */     double beytail = t.y;
/* 2167 */     twoDiff(zb, ze, t);
/* 2168 */     double bez = t.x;
/* 2169 */     double beztail = t.y;
/* 2170 */     twoDiff(xc, xe, t);
/* 2171 */     double cex = t.x;
/* 2172 */     double cextail = t.y;
/* 2173 */     twoDiff(yc, ye, t);
/* 2174 */     double cey = t.x;
/* 2175 */     double ceytail = t.y;
/* 2176 */     twoDiff(zc, ze, t);
/* 2177 */     double cez = t.x;
/* 2178 */     double ceztail = t.y;
/* 2179 */     twoDiff(xd, xe, t);
/* 2180 */     double dex = t.x;
/* 2181 */     double dextail = t.y;
/* 2182 */     twoDiff(yd, ye, t);
/* 2183 */     double dey = t.x;
/* 2184 */     double deytail = t.y;
/* 2185 */     twoDiff(zd, ze, t);
/* 2186 */     double dez = t.x;
/* 2187 */     double deztail = t.y;
/*      */     
/* 2189 */     double[] axby = new double[8];
/* 2190 */     double[] bxay = new double[8];
/* 2191 */     double[] ab = new double[16];
/* 2192 */     twoTwoProduct(aex, aextail, bey, beytail, axby);
/* 2193 */     double negate = -aey;
/* 2194 */     double negatetail = -aeytail;
/* 2195 */     twoTwoProduct(bex, bextail, negate, negatetail, bxay);
/* 2196 */     int ablen = expansionSumZeroElimFast(8, axby, 8, bxay, ab);
/*      */     
/* 2198 */     double[] bxcy = new double[8];
/* 2199 */     double[] cxby = new double[8];
/* 2200 */     double[] bc = new double[16];
/* 2201 */     twoTwoProduct(bex, bextail, cey, ceytail, bxcy);
/* 2202 */     negate = -bey;
/* 2203 */     negatetail = -beytail;
/* 2204 */     twoTwoProduct(cex, cextail, negate, negatetail, cxby);
/* 2205 */     int bclen = expansionSumZeroElimFast(8, bxcy, 8, cxby, bc);
/*      */     
/* 2207 */     double[] cxdy = new double[8];
/* 2208 */     double[] dxcy = new double[8];
/* 2209 */     double[] cd = new double[16];
/* 2210 */     twoTwoProduct(cex, cextail, dey, deytail, cxdy);
/* 2211 */     negate = -cey;
/* 2212 */     negatetail = -ceytail;
/* 2213 */     twoTwoProduct(dex, dextail, negate, negatetail, dxcy);
/* 2214 */     int cdlen = expansionSumZeroElimFast(8, cxdy, 8, dxcy, cd);
/*      */     
/* 2216 */     double[] dxay = new double[8];
/* 2217 */     double[] axdy = new double[8];
/* 2218 */     double[] da = new double[16];
/* 2219 */     twoTwoProduct(dex, dextail, aey, aeytail, dxay);
/* 2220 */     negate = -dey;
/* 2221 */     negatetail = -deytail;
/* 2222 */     twoTwoProduct(aex, aextail, negate, negatetail, axdy);
/* 2223 */     int dalen = expansionSumZeroElimFast(8, dxay, 8, axdy, da);
/*      */     
/* 2225 */     double[] axcy = new double[8];
/* 2226 */     double[] cxay = new double[8];
/* 2227 */     double[] ac = new double[16];
/* 2228 */     twoTwoProduct(aex, aextail, cey, ceytail, axcy);
/* 2229 */     negate = -aey;
/* 2230 */     negatetail = -aeytail;
/* 2231 */     twoTwoProduct(cex, cextail, negate, negatetail, cxay);
/* 2232 */     int aclen = expansionSumZeroElimFast(8, axcy, 8, cxay, ac);
/*      */     
/* 2234 */     double[] bxdy = new double[8];
/* 2235 */     double[] dxby = new double[8];
/* 2236 */     double[] bd = new double[16];
/* 2237 */     twoTwoProduct(bex, bextail, dey, deytail, bxdy);
/* 2238 */     negate = -bey;
/* 2239 */     negatetail = -beytail;
/* 2240 */     twoTwoProduct(dex, dextail, negate, negatetail, dxby);
/* 2241 */     int bdlen = expansionSumZeroElimFast(8, bxdy, 8, dxby, bd);
/*      */     
/* 2243 */     double[] t32a = new double[32];
/* 2244 */     double[] t32b = new double[32];
/* 2245 */     double[] t64a = new double[64];
/* 2246 */     double[] t64b = new double[64];
/* 2247 */     double[] t64c = new double[64];
/* 2248 */     double[] t128 = new double[128];
/* 2249 */     double[] t192 = new double[192];
/*      */     
/* 2251 */     int t32alen = scaleExpansionZeroElim(cdlen, cd, -bez, t32a);
/* 2252 */     int t32blen = scaleExpansionZeroElim(cdlen, cd, -beztail, t32b);
/* 2253 */     int t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2254 */     t32alen = scaleExpansionZeroElim(bdlen, bd, cez, t32a);
/* 2255 */     t32blen = scaleExpansionZeroElim(bdlen, bd, ceztail, t32b);
/* 2256 */     int t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2257 */     t32alen = scaleExpansionZeroElim(bclen, bc, -dez, t32a);
/* 2258 */     t32blen = scaleExpansionZeroElim(bclen, bc, -deztail, t32b);
/* 2259 */     int t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2260 */     int t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2261 */     int t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/*      */     
/* 2263 */     double[] detx = new double[384];
/* 2264 */     double[] detxx = new double[768];
/* 2265 */     double[] detxt = new double[384];
/* 2266 */     double[] detxxt = new double[768];
/* 2267 */     double[] detxtxt = new double[768];
/* 2268 */     double[] x1 = new double[1536];
/* 2269 */     double[] x2 = new double[2304];
/* 2270 */     int xlen = scaleExpansionZeroElim(t192len, t192, aex, detx);
/* 2271 */     int xxlen = scaleExpansionZeroElim(xlen, detx, aex, detxx);
/* 2272 */     int xtlen = scaleExpansionZeroElim(t192len, t192, aextail, detxt);
/* 2273 */     int xxtlen = scaleExpansionZeroElim(xtlen, detxt, aex, detxxt);
/* 2274 */     for (int i = 0; i < xxtlen; i++)
/* 2275 */       detxxt[i] = detxxt[i] * 2.0D; 
/* 2276 */     int xtxtlen = scaleExpansionZeroElim(xtlen, detxt, aextail, detxtxt);
/* 2277 */     int x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2278 */     int x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/*      */     
/* 2280 */     double[] dety = new double[384];
/* 2281 */     double[] detyy = new double[768];
/* 2282 */     double[] detyt = new double[384];
/* 2283 */     double[] detyyt = new double[768];
/* 2284 */     double[] detytyt = new double[768];
/* 2285 */     double[] y1 = new double[1536];
/* 2286 */     double[] y2 = new double[2304];
/* 2287 */     int ylen = scaleExpansionZeroElim(t192len, t192, aey, dety);
/* 2288 */     int yylen = scaleExpansionZeroElim(ylen, dety, aey, detyy);
/* 2289 */     int ytlen = scaleExpansionZeroElim(t192len, t192, aeytail, detyt);
/* 2290 */     int yytlen = scaleExpansionZeroElim(ytlen, detyt, aey, detyyt);
/* 2291 */     for (int j = 0; j < yytlen; j++)
/* 2292 */       detyyt[j] = detyyt[j] * 2.0D; 
/* 2293 */     int ytytlen = scaleExpansionZeroElim(ytlen, detyt, aeytail, detytyt);
/* 2294 */     int y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2295 */     int y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/*      */     
/* 2297 */     double[] detz = new double[384];
/* 2298 */     double[] detzz = new double[768];
/* 2299 */     double[] detzt = new double[384];
/* 2300 */     double[] detzzt = new double[768];
/* 2301 */     double[] detztzt = new double[768];
/* 2302 */     double[] z1 = new double[1536];
/* 2303 */     double[] z2 = new double[2304];
/* 2304 */     int zlen = scaleExpansionZeroElim(t192len, t192, aez, detz);
/* 2305 */     int zzlen = scaleExpansionZeroElim(zlen, detz, aez, detzz);
/* 2306 */     int ztlen = scaleExpansionZeroElim(t192len, t192, aeztail, detzt);
/* 2307 */     int zztlen = scaleExpansionZeroElim(ztlen, detzt, aez, detzzt);
/* 2308 */     for (int k = 0; k < zztlen; k++)
/* 2309 */       detzzt[k] = detzzt[k] * 2.0D; 
/* 2310 */     int ztztlen = scaleExpansionZeroElim(ztlen, detzt, aeztail, detztzt);
/* 2311 */     int z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2312 */     int z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/*      */     
/* 2314 */     double[] detxy = new double[4608];
/* 2315 */     double[] adet = new double[6912];
/* 2316 */     double[] bdet = new double[6912];
/* 2317 */     double[] cdet = new double[6912];
/* 2318 */     double[] ddet = new double[6912];
/* 2319 */     int xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2320 */     int alen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, adet);
/*      */     
/* 2322 */     t32alen = scaleExpansionZeroElim(dalen, da, cez, t32a);
/* 2323 */     t32blen = scaleExpansionZeroElim(dalen, da, ceztail, t32b);
/* 2324 */     t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2325 */     t32alen = scaleExpansionZeroElim(aclen, ac, dez, t32a);
/* 2326 */     t32blen = scaleExpansionZeroElim(aclen, ac, deztail, t32b);
/* 2327 */     t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2328 */     t32alen = scaleExpansionZeroElim(cdlen, cd, aez, t32a);
/* 2329 */     t32blen = scaleExpansionZeroElim(cdlen, cd, aeztail, t32b);
/* 2330 */     t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2331 */     t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2332 */     t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/* 2333 */     xlen = scaleExpansionZeroElim(t192len, t192, bex, detx);
/* 2334 */     xxlen = scaleExpansionZeroElim(xlen, detx, bex, detxx);
/* 2335 */     xtlen = scaleExpansionZeroElim(t192len, t192, bextail, detxt);
/* 2336 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, bex, detxxt); int m;
/* 2337 */     for (m = 0; m < xxtlen; m++)
/* 2338 */       detxxt[m] = detxxt[m] * 2.0D; 
/* 2339 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, bextail, detxtxt);
/* 2340 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2341 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2342 */     ylen = scaleExpansionZeroElim(t192len, t192, bey, dety);
/* 2343 */     yylen = scaleExpansionZeroElim(ylen, dety, bey, detyy);
/* 2344 */     ytlen = scaleExpansionZeroElim(t192len, t192, beytail, detyt);
/* 2345 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, bey, detyyt);
/* 2346 */     for (m = 0; m < yytlen; m++)
/* 2347 */       detyyt[m] = detyyt[m] * 2.0D; 
/* 2348 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, beytail, detytyt);
/* 2349 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2350 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2351 */     zlen = scaleExpansionZeroElim(t192len, t192, bez, detz);
/* 2352 */     zzlen = scaleExpansionZeroElim(zlen, detz, bez, detzz);
/* 2353 */     ztlen = scaleExpansionZeroElim(t192len, t192, beztail, detzt);
/* 2354 */     zztlen = scaleExpansionZeroElim(ztlen, detzt, bez, detzzt);
/* 2355 */     for (m = 0; m < zztlen; m++)
/* 2356 */       detzzt[m] = detzzt[m] * 2.0D; 
/* 2357 */     ztztlen = scaleExpansionZeroElim(ztlen, detzt, beztail, detztzt);
/* 2358 */     z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2359 */     z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/* 2360 */     xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2361 */     int blen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, bdet);
/*      */     
/* 2363 */     t32alen = scaleExpansionZeroElim(ablen, ab, -dez, t32a);
/* 2364 */     t32blen = scaleExpansionZeroElim(ablen, ab, -deztail, t32b);
/* 2365 */     t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2366 */     t32alen = scaleExpansionZeroElim(bdlen, bd, -aez, t32a);
/* 2367 */     t32blen = scaleExpansionZeroElim(bdlen, bd, -aeztail, t32b);
/* 2368 */     t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2369 */     t32alen = scaleExpansionZeroElim(dalen, da, -bez, t32a);
/* 2370 */     t32blen = scaleExpansionZeroElim(dalen, da, -beztail, t32b);
/* 2371 */     t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2372 */     t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2373 */     t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/* 2374 */     xlen = scaleExpansionZeroElim(t192len, t192, cex, detx);
/* 2375 */     xxlen = scaleExpansionZeroElim(xlen, detx, cex, detxx);
/* 2376 */     xtlen = scaleExpansionZeroElim(t192len, t192, cextail, detxt);
/* 2377 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, cex, detxxt); int n;
/* 2378 */     for (n = 0; n < xxtlen; n++)
/* 2379 */       detxxt[n] = detxxt[n] * 2.0D; 
/* 2380 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, cextail, detxtxt);
/* 2381 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2382 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2383 */     ylen = scaleExpansionZeroElim(t192len, t192, cey, dety);
/* 2384 */     yylen = scaleExpansionZeroElim(ylen, dety, cey, detyy);
/* 2385 */     ytlen = scaleExpansionZeroElim(t192len, t192, ceytail, detyt);
/* 2386 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, cey, detyyt);
/* 2387 */     for (n = 0; n < yytlen; n++)
/* 2388 */       detyyt[n] = detyyt[n] * 2.0D; 
/* 2389 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, ceytail, detytyt);
/* 2390 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2391 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2392 */     zlen = scaleExpansionZeroElim(t192len, t192, cez, detz);
/* 2393 */     zzlen = scaleExpansionZeroElim(zlen, detz, cez, detzz);
/* 2394 */     ztlen = scaleExpansionZeroElim(t192len, t192, ceztail, detzt);
/* 2395 */     zztlen = scaleExpansionZeroElim(ztlen, detzt, cez, detzzt);
/* 2396 */     for (n = 0; n < zztlen; n++)
/* 2397 */       detzzt[n] = detzzt[n] * 2.0D; 
/* 2398 */     ztztlen = scaleExpansionZeroElim(ztlen, detzt, ceztail, detztzt);
/* 2399 */     z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2400 */     z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/* 2401 */     xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2402 */     int clen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, cdet);
/*      */     
/* 2404 */     t32alen = scaleExpansionZeroElim(bclen, bc, aez, t32a);
/* 2405 */     t32blen = scaleExpansionZeroElim(bclen, bc, aeztail, t32b);
/* 2406 */     t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2407 */     t32alen = scaleExpansionZeroElim(aclen, ac, -bez, t32a);
/* 2408 */     t32blen = scaleExpansionZeroElim(aclen, ac, -beztail, t32b);
/* 2409 */     t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2410 */     t32alen = scaleExpansionZeroElim(ablen, ab, cez, t32a);
/* 2411 */     t32blen = scaleExpansionZeroElim(ablen, ab, ceztail, t32b);
/* 2412 */     t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2413 */     t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2414 */     t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/* 2415 */     xlen = scaleExpansionZeroElim(t192len, t192, dex, detx);
/* 2416 */     xxlen = scaleExpansionZeroElim(xlen, detx, dex, detxx);
/* 2417 */     xtlen = scaleExpansionZeroElim(t192len, t192, dextail, detxt);
/* 2418 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, dex, detxxt); int i1;
/* 2419 */     for (i1 = 0; i1 < xxtlen; i1++)
/* 2420 */       detxxt[i1] = detxxt[i1] * 2.0D; 
/* 2421 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, dextail, detxtxt);
/* 2422 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2423 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2424 */     ylen = scaleExpansionZeroElim(t192len, t192, dey, dety);
/* 2425 */     yylen = scaleExpansionZeroElim(ylen, dety, dey, detyy);
/* 2426 */     ytlen = scaleExpansionZeroElim(t192len, t192, deytail, detyt);
/* 2427 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, dey, detyyt);
/* 2428 */     for (i1 = 0; i1 < yytlen; i1++)
/* 2429 */       detyyt[i1] = detyyt[i1] * 2.0D; 
/* 2430 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, deytail, detytyt);
/* 2431 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2432 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2433 */     zlen = scaleExpansionZeroElim(t192len, t192, dez, detz);
/* 2434 */     zzlen = scaleExpansionZeroElim(zlen, detz, dez, detzz);
/* 2435 */     ztlen = scaleExpansionZeroElim(t192len, t192, deztail, detzt);
/* 2436 */     zztlen = scaleExpansionZeroElim(ztlen, detzt, dez, detzzt);
/* 2437 */     for (i1 = 0; i1 < zztlen; i1++)
/* 2438 */       detzzt[i1] = detzzt[i1] * 2.0D; 
/* 2439 */     ztztlen = scaleExpansionZeroElim(ztlen, detzt, deztail, detztzt);
/* 2440 */     z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2441 */     z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/* 2442 */     xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2443 */     int dlen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, ddet);
/*      */     
/* 2445 */     double[] abdet = new double[13824];
/* 2446 */     double[] cddet = new double[13824];
/* 2447 */     double[] det = new double[27648];
/* 2448 */     ablen = expansionSumZeroElimFast(alen, adet, blen, bdet, abdet);
/* 2449 */     cdlen = expansionSumZeroElimFast(clen, cdet, dlen, ddet, cddet);
/* 2450 */     int detlen = expansionSumZeroElimFast(ablen, abdet, cdlen, cddet, det);
/*      */     
/* 2452 */     return det[detlen - 1];
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
/*      */   private static double inOrthoSphereExact(double xa, double ya, double za, double wa, double xb, double yb, double zb, double wb, double xc, double yc, double zc, double wc, double xd, double yd, double zd, double wd, double xe, double ye, double ze, double we) {
/* 2471 */     Two t = new Two();
/* 2472 */     twoDiff(xa, xe, t);
/* 2473 */     double aex = t.x;
/* 2474 */     double aextail = t.y;
/* 2475 */     twoDiff(ya, ye, t);
/* 2476 */     double aey = t.x;
/* 2477 */     double aeytail = t.y;
/* 2478 */     twoDiff(za, ze, t);
/* 2479 */     double aez = t.x;
/* 2480 */     double aeztail = t.y;
/* 2481 */     twoDiff(wa, we, t);
/*      */ 
/*      */     
/* 2484 */     twoDiff(xb, xe, t);
/* 2485 */     double bex = t.x;
/* 2486 */     double bextail = t.y;
/* 2487 */     twoDiff(yb, ye, t);
/* 2488 */     double bey = t.x;
/* 2489 */     double beytail = t.y;
/* 2490 */     twoDiff(zb, ze, t);
/* 2491 */     double bez = t.x;
/* 2492 */     double beztail = t.y;
/* 2493 */     twoDiff(wb, we, t);
/* 2494 */     double bew = t.x;
/* 2495 */     double bewtail = t.y;
/* 2496 */     twoDiff(xc, xe, t);
/* 2497 */     double cex = t.x;
/* 2498 */     double cextail = t.y;
/* 2499 */     twoDiff(yc, ye, t);
/* 2500 */     double cey = t.x;
/* 2501 */     double ceytail = t.y;
/* 2502 */     twoDiff(zc, ze, t);
/* 2503 */     double cez = t.x;
/* 2504 */     double ceztail = t.y;
/* 2505 */     twoDiff(wc, we, t);
/* 2506 */     double cew = t.x;
/* 2507 */     double cewtail = t.y;
/* 2508 */     twoDiff(xd, xe, t);
/* 2509 */     double dex = t.x;
/* 2510 */     double dextail = t.y;
/* 2511 */     twoDiff(yd, ye, t);
/* 2512 */     double dey = t.x;
/* 2513 */     double deytail = t.y;
/* 2514 */     twoDiff(zd, ze, t);
/* 2515 */     double dez = t.x;
/* 2516 */     double deztail = t.y;
/* 2517 */     twoDiff(wd, we, t);
/* 2518 */     double dew = t.x;
/* 2519 */     double dewtail = t.y;
/*      */     
/* 2521 */     double[] axby = new double[8];
/* 2522 */     double[] bxay = new double[8];
/* 2523 */     double[] ab = new double[16];
/* 2524 */     twoTwoProduct(aex, aextail, bey, beytail, axby);
/* 2525 */     double negate = -aey;
/* 2526 */     double negatetail = -aeytail;
/* 2527 */     twoTwoProduct(bex, bextail, negate, negatetail, bxay);
/* 2528 */     int ablen = expansionSumZeroElimFast(8, axby, 8, bxay, ab);
/*      */     
/* 2530 */     double[] bxcy = new double[8];
/* 2531 */     double[] cxby = new double[8];
/* 2532 */     double[] bc = new double[16];
/* 2533 */     twoTwoProduct(bex, bextail, cey, ceytail, bxcy);
/* 2534 */     negate = -bey;
/* 2535 */     negatetail = -beytail;
/* 2536 */     twoTwoProduct(cex, cextail, negate, negatetail, cxby);
/* 2537 */     int bclen = expansionSumZeroElimFast(8, bxcy, 8, cxby, bc);
/*      */     
/* 2539 */     double[] cxdy = new double[8];
/* 2540 */     double[] dxcy = new double[8];
/* 2541 */     double[] cd = new double[16];
/* 2542 */     twoTwoProduct(cex, cextail, dey, deytail, cxdy);
/* 2543 */     negate = -cey;
/* 2544 */     negatetail = -ceytail;
/* 2545 */     twoTwoProduct(dex, dextail, negate, negatetail, dxcy);
/* 2546 */     int cdlen = expansionSumZeroElimFast(8, cxdy, 8, dxcy, cd);
/*      */     
/* 2548 */     double[] dxay = new double[8];
/* 2549 */     double[] axdy = new double[8];
/* 2550 */     double[] da = new double[16];
/* 2551 */     twoTwoProduct(dex, dextail, aey, aeytail, dxay);
/* 2552 */     negate = -dey;
/* 2553 */     negatetail = -deytail;
/* 2554 */     twoTwoProduct(aex, aextail, negate, negatetail, axdy);
/* 2555 */     int dalen = expansionSumZeroElimFast(8, dxay, 8, axdy, da);
/*      */     
/* 2557 */     double[] axcy = new double[8];
/* 2558 */     double[] cxay = new double[8];
/* 2559 */     double[] ac = new double[16];
/* 2560 */     twoTwoProduct(aex, aextail, cey, ceytail, axcy);
/* 2561 */     negate = -aey;
/* 2562 */     negatetail = -aeytail;
/* 2563 */     twoTwoProduct(cex, cextail, negate, negatetail, cxay);
/* 2564 */     int aclen = expansionSumZeroElimFast(8, axcy, 8, cxay, ac);
/*      */     
/* 2566 */     double[] bxdy = new double[8];
/* 2567 */     double[] dxby = new double[8];
/* 2568 */     double[] bd = new double[16];
/* 2569 */     twoTwoProduct(bex, bextail, dey, deytail, bxdy);
/* 2570 */     negate = -bey;
/* 2571 */     negatetail = -beytail;
/* 2572 */     twoTwoProduct(dex, dextail, negate, negatetail, dxby);
/* 2573 */     int bdlen = expansionSumZeroElimFast(8, bxdy, 8, dxby, bd);
/*      */     
/* 2575 */     double[] t32a = new double[32];
/* 2576 */     double[] t32b = new double[32];
/* 2577 */     double[] t64a = new double[64];
/* 2578 */     double[] t64b = new double[64];
/* 2579 */     double[] t64c = new double[64];
/* 2580 */     double[] t128 = new double[128];
/* 2581 */     double[] t192 = new double[192];
/*      */     
/* 2583 */     int t32alen = scaleExpansionZeroElim(cdlen, cd, -bez, t32a);
/* 2584 */     int t32blen = scaleExpansionZeroElim(cdlen, cd, -beztail, t32b);
/* 2585 */     int t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2586 */     t32alen = scaleExpansionZeroElim(bdlen, bd, cez, t32a);
/* 2587 */     t32blen = scaleExpansionZeroElim(bdlen, bd, ceztail, t32b);
/* 2588 */     int t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2589 */     t32alen = scaleExpansionZeroElim(bclen, bc, -dez, t32a);
/* 2590 */     t32blen = scaleExpansionZeroElim(bclen, bc, -deztail, t32b);
/* 2591 */     int t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2592 */     int t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2593 */     int t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/*      */     
/* 2595 */     double[] detx = new double[384];
/* 2596 */     double[] detxx = new double[768];
/* 2597 */     double[] detxt = new double[384];
/* 2598 */     double[] detxxt = new double[768];
/* 2599 */     double[] detxtxt = new double[768];
/* 2600 */     double[] x1 = new double[1536];
/* 2601 */     double[] x2 = new double[2304];
/* 2602 */     int xlen = scaleExpansionZeroElim(t192len, t192, aex, detx);
/* 2603 */     int xxlen = scaleExpansionZeroElim(xlen, detx, aex, detxx);
/* 2604 */     int xtlen = scaleExpansionZeroElim(t192len, t192, aextail, detxt);
/* 2605 */     int xxtlen = scaleExpansionZeroElim(xtlen, detxt, aex, detxxt);
/* 2606 */     for (int i = 0; i < xxtlen; i++)
/* 2607 */       detxxt[i] = detxxt[i] * 2.0D; 
/* 2608 */     int xtxtlen = scaleExpansionZeroElim(xtlen, detxt, aextail, detxtxt);
/* 2609 */     int x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2610 */     int x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/*      */     
/* 2612 */     double[] dety = new double[384];
/* 2613 */     double[] detyy = new double[768];
/* 2614 */     double[] detyt = new double[384];
/* 2615 */     double[] detyyt = new double[768];
/* 2616 */     double[] detytyt = new double[768];
/* 2617 */     double[] y1 = new double[1536];
/* 2618 */     double[] y2 = new double[2304];
/* 2619 */     int ylen = scaleExpansionZeroElim(t192len, t192, aey, dety);
/* 2620 */     int yylen = scaleExpansionZeroElim(ylen, dety, aey, detyy);
/* 2621 */     int ytlen = scaleExpansionZeroElim(t192len, t192, aeytail, detyt);
/* 2622 */     int yytlen = scaleExpansionZeroElim(ytlen, detyt, aey, detyyt);
/* 2623 */     for (int j = 0; j < yytlen; j++)
/* 2624 */       detyyt[j] = detyyt[j] * 2.0D; 
/* 2625 */     int ytytlen = scaleExpansionZeroElim(ytlen, detyt, aeytail, detytyt);
/* 2626 */     int y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2627 */     int y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/*      */     
/* 2629 */     double[] detz = new double[384];
/* 2630 */     double[] detzz = new double[768];
/* 2631 */     double[] detzt = new double[384];
/* 2632 */     double[] detzzt = new double[768];
/* 2633 */     double[] detztzt = new double[768];
/* 2634 */     double[] z1 = new double[1536];
/* 2635 */     double[] z2 = new double[2304];
/* 2636 */     int zlen = scaleExpansionZeroElim(t192len, t192, aez, detz);
/* 2637 */     int zzlen = scaleExpansionZeroElim(zlen, detz, aez, detzz);
/* 2638 */     int ztlen = scaleExpansionZeroElim(t192len, t192, aeztail, detzt);
/* 2639 */     int zztlen = scaleExpansionZeroElim(ztlen, detzt, aez, detzzt);
/* 2640 */     for (int k = 0; k < zztlen; k++)
/* 2641 */       detzzt[k] = detzzt[k] * 2.0D; 
/* 2642 */     int ztztlen = scaleExpansionZeroElim(ztlen, detzt, aeztail, detztzt);
/* 2643 */     int z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2644 */     int z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/*      */     
/* 2646 */     double[] detw = new double[384];
/* 2647 */     double[] detwt = new double[384];
/* 2648 */     double[] w2 = new double[768];
/* 2649 */     int wlen = scaleExpansionZeroElim(t192len, t192, -bew, detw);
/* 2650 */     int wtlen = scaleExpansionZeroElim(t192len, t192, -bewtail, detwt);
/* 2651 */     int w2len = expansionSumZeroElimFast(wlen, detw, wtlen, detwt, w2);
/*      */     
/* 2653 */     double[] detxy = new double[4608];
/* 2654 */     double[] detxyz = new double[6912];
/* 2655 */     double[] adet = new double[7680];
/* 2656 */     int xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2657 */     int xyzlen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, detxyz);
/* 2658 */     int alen = expansionSumZeroElimFast(w2len, w2, xyzlen, detxyz, adet);
/*      */     
/* 2660 */     t32alen = scaleExpansionZeroElim(dalen, da, cez, t32a);
/* 2661 */     t32blen = scaleExpansionZeroElim(dalen, da, ceztail, t32b);
/* 2662 */     t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2663 */     t32alen = scaleExpansionZeroElim(aclen, ac, dez, t32a);
/* 2664 */     t32blen = scaleExpansionZeroElim(aclen, ac, deztail, t32b);
/* 2665 */     t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2666 */     t32alen = scaleExpansionZeroElim(cdlen, cd, aez, t32a);
/* 2667 */     t32blen = scaleExpansionZeroElim(cdlen, cd, aeztail, t32b);
/* 2668 */     t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2669 */     t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2670 */     t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/* 2671 */     xlen = scaleExpansionZeroElim(t192len, t192, bex, detx);
/* 2672 */     xxlen = scaleExpansionZeroElim(xlen, detx, bex, detxx);
/* 2673 */     xtlen = scaleExpansionZeroElim(t192len, t192, bextail, detxt);
/* 2674 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, bex, detxxt); int m;
/* 2675 */     for (m = 0; m < xxtlen; m++)
/* 2676 */       detxxt[m] = detxxt[m] * 2.0D; 
/* 2677 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, bextail, detxtxt);
/* 2678 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2679 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2680 */     ylen = scaleExpansionZeroElim(t192len, t192, bey, dety);
/* 2681 */     yylen = scaleExpansionZeroElim(ylen, dety, bey, detyy);
/* 2682 */     ytlen = scaleExpansionZeroElim(t192len, t192, beytail, detyt);
/* 2683 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, bey, detyyt);
/* 2684 */     for (m = 0; m < yytlen; m++)
/* 2685 */       detyyt[m] = detyyt[m] * 2.0D; 
/* 2686 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, beytail, detytyt);
/* 2687 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2688 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2689 */     zlen = scaleExpansionZeroElim(t192len, t192, bez, detz);
/* 2690 */     zzlen = scaleExpansionZeroElim(zlen, detz, bez, detzz);
/* 2691 */     ztlen = scaleExpansionZeroElim(t192len, t192, beztail, detzt);
/* 2692 */     zztlen = scaleExpansionZeroElim(ztlen, detzt, bez, detzzt);
/* 2693 */     for (m = 0; m < zztlen; m++)
/* 2694 */       detzzt[m] = detzzt[m] * 2.0D; 
/* 2695 */     ztztlen = scaleExpansionZeroElim(ztlen, detzt, beztail, detztzt);
/* 2696 */     z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2697 */     z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/* 2698 */     wlen = scaleExpansionZeroElim(t192len, t192, -bew, detw);
/* 2699 */     wtlen = scaleExpansionZeroElim(t192len, t192, -bewtail, detwt);
/* 2700 */     w2len = expansionSumZeroElimFast(wlen, detw, wtlen, detwt, w2);
/* 2701 */     xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2702 */     xyzlen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, detxyz);
/* 2703 */     double[] bdet = new double[7680];
/* 2704 */     int blen = expansionSumZeroElimFast(w2len, w2, xyzlen, detxyz, bdet);
/*      */     
/* 2706 */     t32alen = scaleExpansionZeroElim(ablen, ab, -dez, t32a);
/* 2707 */     t32blen = scaleExpansionZeroElim(ablen, ab, -deztail, t32b);
/* 2708 */     t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2709 */     t32alen = scaleExpansionZeroElim(bdlen, bd, -aez, t32a);
/* 2710 */     t32blen = scaleExpansionZeroElim(bdlen, bd, -aeztail, t32b);
/* 2711 */     t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2712 */     t32alen = scaleExpansionZeroElim(dalen, da, -bez, t32a);
/* 2713 */     t32blen = scaleExpansionZeroElim(dalen, da, -beztail, t32b);
/* 2714 */     t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2715 */     t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2716 */     t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/* 2717 */     xlen = scaleExpansionZeroElim(t192len, t192, cex, detx);
/* 2718 */     xxlen = scaleExpansionZeroElim(xlen, detx, cex, detxx);
/* 2719 */     xtlen = scaleExpansionZeroElim(t192len, t192, cextail, detxt);
/* 2720 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, cex, detxxt); int n;
/* 2721 */     for (n = 0; n < xxtlen; n++)
/* 2722 */       detxxt[n] = detxxt[n] * 2.0D; 
/* 2723 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, cextail, detxtxt);
/* 2724 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2725 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2726 */     ylen = scaleExpansionZeroElim(t192len, t192, cey, dety);
/* 2727 */     yylen = scaleExpansionZeroElim(ylen, dety, cey, detyy);
/* 2728 */     ytlen = scaleExpansionZeroElim(t192len, t192, ceytail, detyt);
/* 2729 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, cey, detyyt);
/* 2730 */     for (n = 0; n < yytlen; n++)
/* 2731 */       detyyt[n] = detyyt[n] * 2.0D; 
/* 2732 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, ceytail, detytyt);
/* 2733 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2734 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2735 */     zlen = scaleExpansionZeroElim(t192len, t192, cez, detz);
/* 2736 */     zzlen = scaleExpansionZeroElim(zlen, detz, cez, detzz);
/* 2737 */     ztlen = scaleExpansionZeroElim(t192len, t192, ceztail, detzt);
/* 2738 */     zztlen = scaleExpansionZeroElim(ztlen, detzt, cez, detzzt);
/* 2739 */     for (n = 0; n < zztlen; n++)
/* 2740 */       detzzt[n] = detzzt[n] * 2.0D; 
/* 2741 */     ztztlen = scaleExpansionZeroElim(ztlen, detzt, ceztail, detztzt);
/* 2742 */     z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2743 */     z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/* 2744 */     wlen = scaleExpansionZeroElim(t192len, t192, -cew, detw);
/* 2745 */     wtlen = scaleExpansionZeroElim(t192len, t192, -cewtail, detwt);
/* 2746 */     w2len = expansionSumZeroElimFast(wlen, detw, wtlen, detwt, w2);
/* 2747 */     xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2748 */     xyzlen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, detxyz);
/* 2749 */     double[] cdet = new double[7680];
/* 2750 */     int clen = expansionSumZeroElimFast(w2len, w2, xyzlen, detxyz, cdet);
/*      */     
/* 2752 */     t32alen = scaleExpansionZeroElim(bclen, bc, aez, t32a);
/* 2753 */     t32blen = scaleExpansionZeroElim(bclen, bc, aeztail, t32b);
/* 2754 */     t64alen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64a);
/* 2755 */     t32alen = scaleExpansionZeroElim(aclen, ac, -bez, t32a);
/* 2756 */     t32blen = scaleExpansionZeroElim(aclen, ac, -beztail, t32b);
/* 2757 */     t64blen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64b);
/* 2758 */     t32alen = scaleExpansionZeroElim(ablen, ab, cez, t32a);
/* 2759 */     t32blen = scaleExpansionZeroElim(ablen, ab, ceztail, t32b);
/* 2760 */     t64clen = expansionSumZeroElimFast(t32alen, t32a, t32blen, t32b, t64c);
/* 2761 */     t128len = expansionSumZeroElimFast(t64alen, t64a, t64blen, t64b, t128);
/* 2762 */     t192len = expansionSumZeroElimFast(t64clen, t64c, t128len, t128, t192);
/* 2763 */     xlen = scaleExpansionZeroElim(t192len, t192, dex, detx);
/* 2764 */     xxlen = scaleExpansionZeroElim(xlen, detx, dex, detxx);
/* 2765 */     xtlen = scaleExpansionZeroElim(t192len, t192, dextail, detxt);
/* 2766 */     xxtlen = scaleExpansionZeroElim(xtlen, detxt, dex, detxxt); int i1;
/* 2767 */     for (i1 = 0; i1 < xxtlen; i1++)
/* 2768 */       detxxt[i1] = detxxt[i1] * 2.0D; 
/* 2769 */     xtxtlen = scaleExpansionZeroElim(xtlen, detxt, dextail, detxtxt);
/* 2770 */     x1len = expansionSumZeroElimFast(xxlen, detxx, xxtlen, detxxt, x1);
/* 2771 */     x2len = expansionSumZeroElimFast(x1len, x1, xtxtlen, detxtxt, x2);
/* 2772 */     ylen = scaleExpansionZeroElim(t192len, t192, dey, dety);
/* 2773 */     yylen = scaleExpansionZeroElim(ylen, dety, dey, detyy);
/* 2774 */     ytlen = scaleExpansionZeroElim(t192len, t192, deytail, detyt);
/* 2775 */     yytlen = scaleExpansionZeroElim(ytlen, detyt, dey, detyyt);
/* 2776 */     for (i1 = 0; i1 < yytlen; i1++)
/* 2777 */       detyyt[i1] = detyyt[i1] * 2.0D; 
/* 2778 */     ytytlen = scaleExpansionZeroElim(ytlen, detyt, deytail, detytyt);
/* 2779 */     y1len = expansionSumZeroElimFast(yylen, detyy, yytlen, detyyt, y1);
/* 2780 */     y2len = expansionSumZeroElimFast(y1len, y1, ytytlen, detytyt, y2);
/* 2781 */     zlen = scaleExpansionZeroElim(t192len, t192, dez, detz);
/* 2782 */     zzlen = scaleExpansionZeroElim(zlen, detz, dez, detzz);
/* 2783 */     ztlen = scaleExpansionZeroElim(t192len, t192, deztail, detzt);
/* 2784 */     zztlen = scaleExpansionZeroElim(ztlen, detzt, dez, detzzt);
/* 2785 */     for (i1 = 0; i1 < zztlen; i1++)
/* 2786 */       detzzt[i1] = detzzt[i1] * 2.0D; 
/* 2787 */     ztztlen = scaleExpansionZeroElim(ztlen, detzt, deztail, detztzt);
/* 2788 */     z1len = expansionSumZeroElimFast(zzlen, detzz, zztlen, detzzt, z1);
/* 2789 */     z2len = expansionSumZeroElimFast(z1len, z1, ztztlen, detztzt, z2);
/* 2790 */     wlen = scaleExpansionZeroElim(t192len, t192, -dew, detw);
/* 2791 */     wtlen = scaleExpansionZeroElim(t192len, t192, -dewtail, detwt);
/* 2792 */     w2len = expansionSumZeroElimFast(wlen, detw, wtlen, detwt, w2);
/* 2793 */     xylen = expansionSumZeroElimFast(x2len, x2, y2len, y2, detxy);
/* 2794 */     xyzlen = expansionSumZeroElimFast(z2len, z2, xylen, detxy, detxyz);
/* 2795 */     double[] ddet = new double[7680];
/* 2796 */     int dlen = expansionSumZeroElimFast(w2len, w2, xyzlen, detxyz, ddet);
/*      */     
/* 2798 */     double[] abdet = new double[15360];
/* 2799 */     double[] cddet = new double[15360];
/* 2800 */     double[] det = new double[30720];
/* 2801 */     ablen = expansionSumZeroElimFast(alen, adet, blen, bdet, abdet);
/* 2802 */     cdlen = expansionSumZeroElimFast(clen, cdet, dlen, ddet, cddet);
/* 2803 */     int detlen = expansionSumZeroElimFast(ablen, abdet, cdlen, cddet, det);
/*      */     
/* 2805 */     return det[detlen - 1];
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mesh/Geometry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */