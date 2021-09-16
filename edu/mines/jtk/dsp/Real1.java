/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Real1
/*     */ {
/*     */   Sampling _s;
/*     */   float[] _v;
/*     */   
/*     */   public Real1(Sampling s) {
/*  42 */     this._s = s;
/*  43 */     this._v = new float[s.getCount()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1(float[] v) {
/*  53 */     this._s = new Sampling(v.length, 1.0D, 0.0D);
/*  54 */     this._v = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1(Sampling s, float[] v) {
/*  64 */     Check.argument((s.getCount() == v.length), "v.length equals the number of samples in s");
/*     */     
/*  66 */     this._s = s;
/*  67 */     this._v = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1(int n, double d, double f) {
/*  77 */     this(new Sampling(n, d, f));
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
/*     */   public Real1(int n, double d, double f, float[] v) {
/*  89 */     this(new Sampling(n, d, f), v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1(Real1 r) {
/*  99 */     this(r._s, ArrayMath.copy(r._v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sampling getSampling() {
/* 107 */     return this._s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getValues() {
/* 115 */     return this._v;
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
/*     */   public Real1 resample(Sampling s) {
/* 135 */     Sampling t = this._s;
/*     */ 
/*     */     
/* 138 */     int[] overlap = t.overlapWith(s);
/*     */ 
/*     */     
/* 141 */     if (overlap != null) {
/* 142 */       int ni = overlap[0];
/* 143 */       int it = overlap[1];
/* 144 */       int is = overlap[2];
/* 145 */       Real1 rt = this;
/* 146 */       Real1 rs = new Real1(s);
/* 147 */       float[] xt = rt.getValues();
/* 148 */       float[] xs = rs.getValues();
/* 149 */       while (--ni >= 0)
/* 150 */         xs[is++] = xt[it++]; 
/* 151 */       return rs;
/*     */     } 
/*     */ 
/*     */     
/* 155 */     throw new UnsupportedOperationException("no interpolation, yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1 plus(Real1 ra) {
/* 165 */     return add(this, ra);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1 plus(float ar) {
/* 174 */     return add(this, ar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Real1 convolve(Real1 ra) {
/* 184 */     Real1 rx = this;
/* 185 */     Real1 ry = ra;
/* 186 */     Sampling sx = rx.getSampling();
/* 187 */     Sampling sy = ry.getSampling();
/* 188 */     double dx = sx.getDelta();
/* 189 */     double dy = sy.getDelta();
/* 190 */     Check.state(sx.isUniform(), "sampling is uniform");
/* 191 */     Check.argument(sy.isUniform(), "sampling is uniform");
/* 192 */     Check.argument((dx == dy), "sampling intervals are equal");
/* 193 */     int lx = sx.getCount();
/* 194 */     int ly = sy.getCount();
/* 195 */     double fx = sx.getFirst();
/* 196 */     double fy = sy.getFirst();
/* 197 */     float[] x = rx.getValues();
/* 198 */     float[] y = ry.getValues();
/* 199 */     int lz = lx + ly - 1;
/* 200 */     double dz = dx;
/* 201 */     double fz = fx + fy;
/* 202 */     float[] z = new float[lz];
/* 203 */     Conv.conv(lx, 0, x, ly, 0, y, lz, 0, z);
/* 204 */     return new Real1(lz, dz, fz, z);
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
/*     */   public Sampling getFourierSampling(int nmin) {
/* 220 */     int nt = this._s.getCount();
/* 221 */     double dt = this._s.getDelta();
/* 222 */     int nfft = FftReal.nfftSmall(ArrayMath.max(nmin, nt));
/* 223 */     int nf = nfft / 2 + 1;
/* 224 */     double df = 1.0D / nfft * dt;
/* 225 */     double ff = 0.0D;
/* 226 */     return new Sampling(nf, df, ff);
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
/*     */   public static Real1 zero(int n) {
/* 245 */     return new Real1(new Sampling(n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 zero(Sampling s) {
/* 254 */     return new Real1(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 fill(double ar, int n) {
/* 264 */     return fill(ar, new Sampling(n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 fill(double ar, Sampling s) {
/* 274 */     int n = s.getCount();
/* 275 */     return new Real1(s, ArrayMath.fillfloat((float)ar, n));
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
/*     */   public static Real1 ramp(double fv, double dv, int nv) {
/* 287 */     return ramp(fv, dv, new Sampling(nv));
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
/*     */   public static Real1 ramp(double fv, double dv, Sampling s) {
/* 300 */     int n = s.getCount();
/* 301 */     double d = s.getDelta();
/* 302 */     double f = s.getFirst();
/* 303 */     return new Real1(s, ArrayMath.rampfloat((float)(fv - f * dv), (float)(d * dv), n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 add(Real1 ra, Real1 rb) {
/* 314 */     return binaryOp(ra, rb, _add);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 add(float ar, Real1 rb) {
/* 324 */     return binaryOp(ar, rb, _add);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 add(Real1 ra, float br) {
/* 334 */     return binaryOp(ra, br, _add);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 sub(Real1 ra, Real1 rb) {
/* 345 */     return binaryOp(ra, rb, _sub);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 sub(float ar, Real1 rb) {
/* 355 */     return binaryOp(ar, rb, _sub);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 sub(Real1 ra, float br) {
/* 365 */     return binaryOp(ra, br, _sub);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 mul(Real1 ra, Real1 rb) {
/* 376 */     return binaryOp(ra, rb, _mul);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 mul(float ar, Real1 rb) {
/* 386 */     return binaryOp(ar, rb, _mul);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 mul(Real1 ra, float br) {
/* 396 */     return binaryOp(ra, br, _mul);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 div(Real1 ra, Real1 rb) {
/* 407 */     return binaryOp(ra, rb, _div);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 div(float ar, Real1 rb) {
/* 417 */     return binaryOp(ar, rb, _div);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Real1 div(Real1 ra, float br) {
/* 427 */     return binaryOp(ra, br, _div);
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
/*     */   private static Real1 binaryOp(Real1 ra, Real1 rb, Binary ab) {
/* 450 */     Sampling sa = ra.getSampling();
/* 451 */     Sampling sb = rb.getSampling();
/* 452 */     Check.argument(sa.isEquivalentTo(sb), "samplings equivalent");
/* 453 */     Sampling sc = sa;
/* 454 */     Real1 rc = new Real1(sc);
/* 455 */     float[] va = ra.getValues();
/* 456 */     float[] vb = rb.getValues();
/* 457 */     float[] vc = rc.getValues();
/* 458 */     ab.apply(vc.length, va, 0, vb, 0, vc, 0);
/* 459 */     return rc;
/*     */   }
/*     */   private static Real1 binaryOp(float ar, Real1 rb, Binary ab) {
/* 462 */     Sampling sb = rb.getSampling();
/* 463 */     Sampling sc = sb;
/* 464 */     Real1 rc = new Real1(sc);
/* 465 */     float[] vb = rb.getValues();
/* 466 */     float[] vc = rc.getValues();
/* 467 */     ab.apply(vc.length, ar, vb, 0, vc, 0);
/* 468 */     return rc;
/*     */   }
/*     */   private static Real1 binaryOp(Real1 ra, float br, Binary ab) {
/* 471 */     Sampling sa = ra.getSampling();
/* 472 */     Sampling sc = sa;
/* 473 */     Real1 rc = new Real1(sc);
/* 474 */     float[] va = ra.getValues();
/* 475 */     float[] vc = rc.getValues();
/* 476 */     ab.apply(vc.length, va, 0, br, vc, 0);
/* 477 */     return rc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 487 */   private static Binary _add = new Binary()
/*     */     {
/*     */       public void apply(int n, float[] a, int ia, float[] b, int ib, float[] c, int ic)
/*     */       {
/* 491 */         while (--n >= 0)
/* 492 */           c[ic++] = a[ia++] + b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float a, float[] b, int ib, float[] c, int ic) {
/* 495 */         while (--n >= 0)
/* 496 */           c[ic++] = a + b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float[] a, int ia, float b, float[] c, int ic) {
/* 499 */         while (--n >= 0)
/* 500 */           c[ic++] = a[ia++] + b; 
/*     */       }
/*     */     };
/* 503 */   private static Binary _sub = new Binary()
/*     */     {
/*     */       public void apply(int n, float[] a, int ia, float[] b, int ib, float[] c, int ic)
/*     */       {
/* 507 */         while (--n >= 0)
/* 508 */           c[ic++] = a[ia++] - b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float a, float[] b, int ib, float[] c, int ic) {
/* 511 */         while (--n >= 0)
/* 512 */           c[ic++] = a - b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float[] a, int ia, float b, float[] c, int ic) {
/* 515 */         while (--n >= 0)
/* 516 */           c[ic++] = a[ia++] - b; 
/*     */       }
/*     */     };
/* 519 */   private static Binary _mul = new Binary()
/*     */     {
/*     */       public void apply(int n, float[] a, int ia, float[] b, int ib, float[] c, int ic)
/*     */       {
/* 523 */         while (--n >= 0)
/* 524 */           c[ic++] = a[ia++] * b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float a, float[] b, int ib, float[] c, int ic) {
/* 527 */         while (--n >= 0)
/* 528 */           c[ic++] = a * b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float[] a, int ia, float b, float[] c, int ic) {
/* 531 */         while (--n >= 0)
/* 532 */           c[ic++] = a[ia++] * b; 
/*     */       }
/*     */     };
/* 535 */   private static Binary _div = new Binary()
/*     */     {
/*     */       public void apply(int n, float[] a, int ia, float[] b, int ib, float[] c, int ic)
/*     */       {
/* 539 */         while (--n >= 0)
/* 540 */           c[ic++] = a[ia++] / b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float a, float[] b, int ib, float[] c, int ic) {
/* 543 */         while (--n >= 0)
/* 544 */           c[ic++] = a / b[ib++]; 
/*     */       }
/*     */       public void apply(int n, float[] a, int ia, float b, float[] c, int ic) {
/* 547 */         while (--n >= 0)
/* 548 */           c[ic++] = a[ia++] / b; 
/*     */       }
/*     */     };
/*     */   
/*     */   private static interface Binary {
/*     */     void apply(int param1Int1, float[] param1ArrayOffloat1, int param1Int2, float[] param1ArrayOffloat2, int param1Int3, float[] param1ArrayOffloat3, int param1Int4);
/*     */     
/*     */     void apply(int param1Int1, float param1Float, float[] param1ArrayOffloat1, int param1Int2, float[] param1ArrayOffloat2, int param1Int3);
/*     */     
/*     */     void apply(int param1Int1, float[] param1ArrayOffloat1, int param1Int2, float param1Float, float[] param1ArrayOffloat2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Real1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */