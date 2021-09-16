/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnitSphereSampling
/*     */ {
/*     */   public UnitSphereSampling() {
/*  51 */     this(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitSphereSampling(int nbits) {
/*  61 */     initialize(nbits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countSamples() {
/*  69 */     return this._npoint;
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
/*     */   public int getMaxIndex() {
/*  82 */     return this._mindex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getPoint(int index) {
/*  93 */     Check.argument((index != 0), "index!=0");
/*  94 */     return (index >= 0) ? this._pu[index] : this._pl[index + this._nindex];
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
/*     */   public int getIndex(float x, float y, float z) {
/* 109 */     double ax = (x >= 0.0F) ? x : -x;
/* 110 */     double ay = (y >= 0.0F) ? y : -y;
/* 111 */     double az = (z >= 0.0F) ? z : -z;
/* 112 */     double scale = ALMOST_ONE / (ax + ay + az);
/* 113 */     double r = x * scale;
/* 114 */     double s = y * scale;
/* 115 */     int ir = (int)(0.5D + (r + 1.0D) * this._od);
/* 116 */     int is = (int)(0.5D + (s + 1.0D) * this._od);
/* 117 */     int index = this._ip[is][ir];
/* 118 */     assert index > 0 : "index>0";
/* 119 */     return (z >= 0.0F) ? index : (index - this._nindex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(float[] xyz) {
/* 129 */     return getIndex(xyz[0], xyz[1], xyz[2]);
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
/*     */   public int[] getTriangle(float x, float y, float z) {
/*     */     int ia, ib, ic;
/* 145 */     double ax = (x >= 0.0F) ? x : -x;
/* 146 */     double ay = (y >= 0.0F) ? y : -y;
/* 147 */     double az = (z >= 0.0F) ? z : -z;
/* 148 */     double scale = ALMOST_ONE / (ax + ay + az);
/* 149 */     double r = x * scale;
/* 150 */     double s = y * scale;
/*     */ 
/*     */ 
/*     */     
/* 154 */     double rn = (r + 1.0D) * this._od;
/* 155 */     double sn = (s + 1.0D) * this._od;
/* 156 */     int ir = (int)rn;
/* 157 */     int is = (int)sn;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     int jr = ir - this._m;
/* 163 */     int js = is - this._m;
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (jr + js == this._m) {
/* 168 */       if (jr > 0) {
/* 169 */         jr--;
/* 170 */         ir--;
/*     */       } else {
/* 172 */         js--;
/* 173 */         is--;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     double fr = rn - ir;
/* 183 */     double fs = sn - is;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (jr >= 0 && js >= 0) {
/* 190 */       if (jr + js + 2 > this._m || fr + fs <= 1.0D) {
/* 191 */         ia = this._ip[is][ir];
/* 192 */         ib = this._ip[is][ir + 1];
/* 193 */         ic = this._ip[is + 1][ir];
/*     */       } else {
/* 195 */         ia = this._ip[is + 1][ir + 1];
/* 196 */         ib = this._ip[is + 1][ir];
/* 197 */         ic = this._ip[is][ir + 1];
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 202 */     else if (jr < 0 && js >= 0) {
/* 203 */       if (-jr + js + 1 > this._m || fr >= fs) {
/* 204 */         ia = this._ip[is][ir + 1];
/* 205 */         ib = this._ip[is + 1][ir + 1];
/* 206 */         ic = this._ip[is][ir];
/*     */       } else {
/* 208 */         ia = this._ip[is + 1][ir];
/* 209 */         ib = this._ip[is][ir];
/* 210 */         ic = this._ip[is + 1][ir + 1];
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 215 */     else if (jr < 0 && js < 0) {
/* 216 */       if (-jr - js > this._m || fr + fs >= 1.0D) {
/* 217 */         ia = this._ip[is + 1][ir + 1];
/* 218 */         ib = this._ip[is + 1][ir];
/* 219 */         ic = this._ip[is][ir + 1];
/*     */       } else {
/* 221 */         ia = this._ip[is][ir];
/* 222 */         ib = this._ip[is][ir + 1];
/* 223 */         ic = this._ip[is + 1][ir];
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 229 */     else if (jr + 1 - js > this._m || fr <= fs) {
/* 230 */       ia = this._ip[is + 1][ir];
/* 231 */       ib = this._ip[is][ir];
/* 232 */       ic = this._ip[is + 1][ir + 1];
/*     */     } else {
/* 234 */       ia = this._ip[is][ir + 1];
/* 235 */       ib = this._ip[is + 1][ir + 1];
/* 236 */       ic = this._ip[is][ir];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 241 */     if (ia == 0 || ib == 0 || ic == 0) {
/* 242 */       trace("ia=" + ia + " ib=" + ib + " ic=" + ic);
/* 243 */       trace("x=" + x + " y=" + y + " z=" + z);
/* 244 */       trace("r=" + r + " s=" + s);
/* 245 */       trace("ir=" + ir + " is=" + is);
/* 246 */       trace("jr=" + jr + " js=" + js);
/* 247 */       trace("fr=" + fr + " fs=" + fs);
/* 248 */       trace("rn=" + rn + " sn=" + sn);
/* 249 */       assert false : "valid ia,ib,ic";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     (new int[3])[0] = ia; (new int[3])[1] = ib; (new int[3])[2] = ic; (new int[3])[0] = ia - this._nindex; (new int[3])[1] = ic - this._nindex; (new int[3])[2] = ib - this._nindex; return (z >= 0.0F) ? new int[3] : new int[3];
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
/*     */   public int[] getTriangle(float[] xyz) {
/* 269 */     return getTriangle(xyz[0], xyz[1], xyz[2]);
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
/*     */   public float[] getWeights(float x, float y, float z, int[] iabc) {
/* 289 */     float[] pa = getPoint(iabc[0]);
/* 290 */     float[] pb = getPoint(iabc[1]);
/* 291 */     float[] pc = getPoint(iabc[2]);
/* 292 */     double xa = pa[0], ya = pa[1], za = pa[2];
/* 293 */     double xb = pb[0], yb = pb[1], zb = pb[2];
/* 294 */     double xc = pc[0], yc = pc[1], zc = pc[2];
/* 295 */     double wa = x * (yb * zc - yc * zb) + y * (zb * xc - zc * xb) + z * (xb * yc - xc * yb);
/* 296 */     double wb = x * (yc * za - ya * zc) + y * (zc * xa - za * xc) + z * (xc * ya - xa * yc);
/* 297 */     double wc = x * (ya * zb - yb * za) + y * (za * xb - zb * xa) + z * (xa * yb - xb * ya);
/* 298 */     if (wa < 0.0D) wa = 0.0D; 
/* 299 */     if (wb < 0.0D) wb = 0.0D; 
/* 300 */     if (wc < 0.0D) wc = 0.0D; 
/* 301 */     double ws = 1.0D / (wa + wb + wc);
/* 302 */     float fa = (float)(wa * ws);
/* 303 */     float fb = (float)(wb * ws);
/* 304 */     float fc = (float)(wc * ws);
/* 305 */     return new float[] { fa, fb, fc };
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
/*     */   public float[] getWeights(float[] xyz, int[] iabc) {
/* 323 */     return getWeights(xyz[0], xyz[1], xyz[2], iabc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short[] encode16(float[] x, float[] y, float[] z) {
/* 334 */     UnitSphereSampling uss = getUnitSphereSampling16();
/* 335 */     int n = x.length;
/* 336 */     short[] s = new short[n];
/* 337 */     for (int j = 0; j < n; j++)
/* 338 */       s[j] = (short)uss.getIndex(x[j], y[j], z[j]); 
/* 339 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short[][] encode16(float[][] x, float[][] y, float[][] z) {
/* 350 */     int n = x.length;
/* 351 */     short[][] s = new short[n][];
/* 352 */     for (int j = 0; j < n; j++)
/* 353 */       s[j] = encode16(x[j], y[j], z[j]); 
/* 354 */     return s;
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
/*     */   public static short[][][] encode16(float[][][] x, float[][][] y, float[][][] z) {
/* 367 */     int n = x.length;
/* 368 */     short[][][] s = new short[n][][];
/* 369 */     for (int j = 0; j < n; j++)
/* 370 */       s[j] = encode16(x[j], y[j], z[j]); 
/* 371 */     return s;
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
/* 430 */   private static final double ALMOST_ONE = 1.0D - 5.0D * ArrayMath.ulp(1.0D);
/*     */   private int _m;
/*     */   private int _n;
/*     */   private int _mindex;
/*     */   private int _nindex;
/*     */   private int _npoint;
/*     */   private double _d;
/*     */   private double _od;
/*     */   private float[][] _pu;
/*     */   private float[][] _pl;
/*     */   private int[][] _ip;
/*     */   private static UnitSphereSampling _uss16;
/*     */   private static final boolean TRACE = true;
/*     */   
/*     */   private static UnitSphereSampling getUnitSphereSampling16() {
/* 445 */     if (_uss16 == null)
/* 446 */       _uss16 = new UnitSphereSampling(16); 
/* 447 */     return _uss16;
/*     */   }
/*     */   
/*     */   private void initialize(int nbits) {
/* 451 */     Check.argument((nbits >= 4), "nbits>=4");
/* 452 */     Check.argument((nbits <= 32), "nbits<=32");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     int indexLimit = (1 << nbits - 1) - 1;
/* 462 */     int m = 1;
/* 463 */     while (1 + 2 * m * (1 + m) <= indexLimit)
/* 464 */       m++; 
/* 465 */     this._m = --m;
/* 466 */     this._n = 2 * this._m + 1;
/*     */ 
/*     */     
/* 469 */     this._d = 1.0D / this._m;
/* 470 */     this._od = this._m;
/*     */ 
/*     */     
/* 473 */     this._mindex = 1 + 2 * this._m * (1 + this._m);
/*     */ 
/*     */ 
/*     */     
/* 477 */     this._nindex = this._mindex + 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     this._npoint = 2 * this._mindex - 4 * this._m;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     this._pu = new float[this._nindex][];
/* 489 */     this._pl = new float[this._nindex][];
/*     */ 
/*     */     
/* 492 */     this._ip = new int[this._n][this._n];
/*     */ 
/*     */     
/* 495 */     for (int is = 0, js = -this._m, index = 0; is < this._n; is++, js++) {
/*     */ 
/*     */       
/* 498 */       double s = js * this._d;
/* 499 */       double as = (s >= 0.0D) ? s : -s;
/*     */ 
/*     */       
/* 502 */       for (int ir = 0, jr = -this._m; ir < this._n; ir++, jr++) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 507 */         int jrs = ArrayMath.abs(jr) + ArrayMath.abs(js);
/* 508 */         if (jrs <= this._m) {
/*     */ 
/*     */           
/* 511 */           this._ip[is][ir] = ++index;
/*     */ 
/*     */ 
/*     */           
/* 515 */           double r = jr * this._d;
/* 516 */           double ar = (r >= 0.0D) ? r : -r;
/*     */ 
/*     */           
/* 519 */           double t = ArrayMath.max(0.0D, 1.0D - ar - as);
/* 520 */           if (jrs == this._m) t = 0.0D;
/*     */ 
/*     */           
/* 523 */           double scale = 1.0D / ArrayMath.sqrt(s * s + r * r + t * t);
/* 524 */           float x = (float)(r * scale);
/* 525 */           float y = (float)(s * scale);
/* 526 */           float z = (float)(t * scale);
/*     */ 
/*     */           
/* 529 */           float[] pu = this._pu[index] = new float[3];
/* 530 */           float[] pl = this._pl[this._nindex - index] = new float[3];
/* 531 */           pu[0] = x; pu[1] = y; pu[2] = z;
/* 532 */           pl[0] = -x; pl[1] = -y; pl[2] = -z;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static float distanceOnSphere(float[] p, float[] q) {
/* 539 */     double x = (p[0] + q[0]);
/* 540 */     double y = (p[1] + q[1]);
/* 541 */     double z = (p[2] + q[2]);
/* 542 */     double d = x * x + y * y + z * z;
/* 543 */     if (d == 0.0D) {
/* 544 */       d = Math.PI;
/* 545 */     } else if (d == 4.0D) {
/* 546 */       d = 0.0D;
/*     */     } else {
/* 548 */       d = 2.0D * ArrayMath.atan(ArrayMath.sqrt((4.0D - d) / d));
/*     */     } 
/* 550 */     return (float)d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 557 */     System.out.println(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 564 */     UnitSphereSampling uss = new UnitSphereSampling(8);
/* 565 */     testSymmetry(uss);
/* 566 */     testInterpolation(uss);
/* 567 */     testWeights(uss);
/* 568 */     testTriangle(uss);
/* 569 */     testMaxError(uss);
/*     */   }
/*     */   
/*     */   private static void testSymmetry(UnitSphereSampling uss) {
/* 573 */     int mi = uss.getMaxIndex();
/* 574 */     for (int i = 1, j = -i; i <= mi; j = -++i) {
/* 575 */       float[] p = uss.getPoint(i);
/* 576 */       float[] q = uss.getPoint(j);
/* 577 */       assert p[0] == -q[0];
/* 578 */       assert p[1] == -q[1];
/* 579 */       assert p[2] == -q[2];
/*     */     } 
/* 581 */     int npoint = 10000;
/* 582 */     for (int ipoint = 0; ipoint < npoint; ipoint++) {
/* 583 */       float[] p = randomPoint();
/* 584 */       float[] q = { -p[0], -p[1], -p[2] };
/* 585 */       int k = uss.getIndex(p);
/* 586 */       int m = uss.getIndex(q);
/* 587 */       if (p[2] == 0.0F) {
/* 588 */         assert k + m == mi + 1;
/*     */       } else {
/* 590 */         assert -k == m;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void testInterpolation(UnitSphereSampling uss) {
/* 596 */     int mi = uss.getMaxIndex();
/*     */ 
/*     */     
/* 599 */     int nf = 1 + 2 * mi;
/* 600 */     float[] fi = new float[nf];
/* 601 */     for (int i = 1; i <= mi; i++) {
/* 602 */       float[] p = uss.getPoint(i);
/* 603 */       float[] q = uss.getPoint(-i);
/* 604 */       fi[i] = func(p[0], p[1], p[2]);
/* 605 */       fi[nf - i] = func(q[0], q[1], q[2]);
/*     */     } 
/*     */ 
/*     */     
/* 609 */     float emax = 0.0F;
/* 610 */     float[] pmax = null;
/* 611 */     int npoint = 10000;
/* 612 */     for (int ipoint = 0; ipoint < npoint; ipoint++) {
/* 613 */       float[] p = randomPoint();
/* 614 */       int[] iabc = uss.getTriangle(p);
/* 615 */       float[] wabc = uss.getWeights(p, iabc);
/* 616 */       int ia = iabc[0], ib = iabc[1], ic = iabc[2];
/* 617 */       float wa = wabc[0], wb = wabc[1], wc = wabc[2];
/* 618 */       if (ia < 0) {
/* 619 */         ia = nf + ia;
/* 620 */         ib = nf + ib;
/* 621 */         ic = nf + ic;
/*     */       } 
/* 623 */       float fa = fi[ia], fb = fi[ib], fc = fi[ic];
/* 624 */       float f = func(p[0], p[1], p[2]);
/* 625 */       float g = wa * fa + wb * fb + wc * fc;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 630 */       float e = ArrayMath.abs(g - f);
/* 631 */       if (e > emax) {
/* 632 */         emax = e;
/* 633 */         pmax = p;
/*     */       } 
/*     */     } 
/* 636 */     trace("emax=" + emax);
/* 637 */     ArrayMath.dump(pmax);
/*     */   }
/*     */   private static float func(float x, float y, float z) {
/* 640 */     return 0.1F * (9.0F * x * x * x - 2.0F * x * x * y + 3.0F * x * y * y - 4.0F * y * y * y + 2.0F * z * z * z - x * y * z);
/*     */   }
/*     */   
/*     */   private static void testTriangle(UnitSphereSampling uss) {
/* 644 */     int npoint = 1000000;
/* 645 */     for (int ipoint = 0; ipoint < npoint; ipoint++) {
/* 646 */       float[] p = randomPoint();
/* 647 */       int i = uss.getIndex(p);
/* 648 */       int[] abc = uss.getTriangle(p);
/* 649 */       int ia = abc[0], ib = abc[1], ic = abc[2];
/*     */       
/* 651 */       float[] q = uss.getPoint(i);
/* 652 */       float[] qa = uss.getPoint(ia);
/* 653 */       float[] qb = uss.getPoint(ib);
/* 654 */       float[] qc = uss.getPoint(ic);
/* 655 */       float d = distanceOnSphere(p, q);
/* 656 */       float da = distanceOnSphere(p, qa);
/* 657 */       float db = distanceOnSphere(p, qb);
/* 658 */       float dc = distanceOnSphere(p, qc);
/* 659 */       if (i != ia && i != ib && i != ic) {
/* 660 */         trace("d=" + d + " da=" + da + " db=" + db + " dc=" + dc);
/* 661 */         ArrayMath.dump(p);
/* 662 */         ArrayMath.dump(q);
/* 663 */         ArrayMath.dump(qa);
/* 664 */         ArrayMath.dump(qb);
/* 665 */         ArrayMath.dump(qc);
/* 666 */         assert false : "i equals ia or ib or ic";
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void testWeights(UnitSphereSampling uss) {
/* 672 */     int npoint = 10;
/* 673 */     for (int ipoint = 0; ipoint < npoint; ipoint++) {
/* 674 */       float[] p = randomPoint();
/* 675 */       int[] iabc = uss.getTriangle(p);
/* 676 */       int ia = iabc[0], ib = iabc[1], ic = iabc[2];
/* 677 */       float[] qa = uss.getPoint(ia);
/* 678 */       float[] qb = uss.getPoint(ib);
/* 679 */       float[] qc = uss.getPoint(ic);
/* 680 */       float[] wabc = uss.getWeights(p, iabc);
/* 681 */       float wa = wabc[0], wb = wabc[1], wc = wabc[2];
/* 682 */       trace("wa=" + wa + " wb=" + wb + " wc=" + wc);
/* 683 */       ArrayMath.dump(p);
/* 684 */       ArrayMath.dump(qa);
/* 685 */       ArrayMath.dump(qb);
/* 686 */       ArrayMath.dump(qc);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void testMaxError(UnitSphereSampling uss) {
/* 691 */     estimateMaxError(uss);
/*     */   }
/*     */   
/*     */   private static float estimateMaxError(UnitSphereSampling uss) {
/* 695 */     int npoint = 1000000;
/* 696 */     float dmax = 0.0F;
/* 697 */     float[] pmax = null;
/* 698 */     float[] qmax = null;
/* 699 */     for (int ipoint = 0; ipoint < npoint; ipoint++) {
/* 700 */       float[] p = randomPoint();
/* 701 */       int i = uss.getIndex(p);
/* 702 */       float[] q = uss.getPoint(i);
/* 703 */       float d = distanceOnSphere(p, q);
/* 704 */       if (d > dmax) {
/* 705 */         dmax = d;
/* 706 */         pmax = p;
/* 707 */         qmax = q;
/*     */       } 
/*     */     } 
/* 710 */     float dmaxDegrees = (float)(dmax * 180.0D / Math.PI);
/* 711 */     trace("npoint=" + npoint + " dmax=" + dmax + " degrees=" + dmaxDegrees);
/* 712 */     trace("pmax=");
/* 713 */     ArrayMath.dump(pmax);
/* 714 */     trace("qmax=");
/* 715 */     ArrayMath.dump(qmax);
/* 716 */     return dmax;
/*     */   }
/*     */   
/* 719 */   private static Random _random = new Random();
/*     */   private static float[] randomPoint() {
/* 721 */     float x = -1.0F + 2.0F * _random.nextFloat();
/* 722 */     float y = -1.0F + 2.0F * _random.nextFloat();
/* 723 */     float z = -1.0F + 2.0F * _random.nextFloat();
/* 724 */     float f = _random.nextFloat();
/* 725 */     if (f < 0.1F) x = 0.0F; 
/* 726 */     if (0.1F <= f && f < 0.2F) y = 0.0F; 
/* 727 */     if (0.2F <= f && f < 0.3F) z = 0.0F; 
/* 728 */     float s = 1.0F / ArrayMath.sqrt(x * x + y * y + z * z);
/* 729 */     return new float[] { x * s, y * s, z * s };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/UnitSphereSampling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */