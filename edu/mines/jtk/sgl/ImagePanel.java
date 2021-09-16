/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.awt.ColorMapListener;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.ogl.GlTextureName;
/*     */ import edu.mines.jtk.util.Clips;
/*     */ import edu.mines.jtk.util.Direct;
/*     */ import edu.mines.jtk.util.Float3;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ import edu.mines.jtk.util.SimpleFloat3;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public class ImagePanel
/*     */   extends AxisAlignedPanel
/*     */ {
/*     */   private Axis _axis;
/*     */   private Sampling _sx;
/*     */   private Sampling _sy;
/*     */   private Sampling _sz;
/*     */   private Float3 _f;
/*     */   private double _xmin;
/*     */   private double _ymin;
/*     */   private double _zmin;
/*     */   private double _xmax;
/*     */   private double _ymax;
/*     */   private double _zmax;
/*     */   private Clips _clips;
/*     */   private float _clipMin;
/*     */   private float _clipMax;
/*     */   private ColorMap _colorMap;
/*     */   private int _ls;
/*     */   private int _lt;
/*     */   private int _ms;
/*     */   private int _mt;
/*     */   private int _ns;
/*     */   private int _nt;
/*     */   
/*     */   public ImagePanel(float[][][] f) {
/*  44 */     this(new Sampling((f[0][0]).length), new Sampling((f[0]).length), new Sampling(f.length), f);
/*     */   }
/*     */   private double _ds; private double _dt;
/*     */   private double _fs;
/*     */   private double _ft;
/*     */   private GlTextureName[][] _tn;
/*     */   private boolean _texturesDirty;
/*     */   private int _kxmin;
/*     */   private int _kymin;
/*     */   private int _kzmin;
/*     */   private int _kxmax;
/*     */   private int _kymax;
/*     */   
/*     */   public ImagePanel(Sampling s1, Sampling s2, Sampling s3, float[][][] f) {
/*  58 */     this(s1, s2, s3, (Float3)new SimpleFloat3(f));
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
/*     */   private int _kzmax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _ksmin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _ktmin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _ksmax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _ktmax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _jsmin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _jtmin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _jsmax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _jtmax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IntBuffer _pixels;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[][] _floats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*     */     this._texturesDirty = true;
/*     */     dirtyDraw();
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
/*     */   public BoxConstraint getBoxConstraint() {
/*     */     return new BoxConstraint(this._sx, this._sy, this._sz);
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
/*     */   public void setColorModel(IndexColorModel colorModel) {
/*     */     this._colorMap.setColorModel(colorModel);
/*     */     this._texturesDirty = true;
/*     */     dirtyDraw();
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
/*     */   public ImagePanel(Sampling s1, Sampling s2, Sampling s3, Float3 f) {
/* 217 */     this._clipMin = 0.0F;
/* 218 */     this._clipMax = 1.0F;
/*     */ 
/*     */     
/* 221 */     this._colorMap = new ColorMap(this._clipMin, this._clipMax, ColorMap.GRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     this._texturesDirty = true;
/*     */     this._sx = s3;
/*     */     this._sy = s2;
/*     */     this._sz = s1;
/*     */     this._f = f;
/*     */     this._clips = new Clips(this._f);
/*     */   }
/*     */   public IndexColorModel getColorModel() { return this._colorMap.getColorModel(); } public void setClips(double clipMin, double clipMax) { this._clips.setClips(clipMin, clipMax);
/*     */     this._texturesDirty = true;
/*     */     dirtyDraw(); } public float getClipMin() {
/*     */     return this._clips.getClipMin();
/*     */   } public float getClipMax() {
/*     */     return this._clips.getClipMax();
/*     */   } public void setPercentiles(double percMin, double percMax) {
/*     */     this._clips.setPercentiles(percMin, percMax);
/*     */     this._texturesDirty = true;
/*     */     dirtyDraw();
/*     */   } private void updateClipMinMax() {
/* 283 */     float clipMin = this._clips.getClipMin();
/* 284 */     float clipMax = this._clips.getClipMax();
/* 285 */     if (this._clipMin != clipMin || this._clipMax != clipMax) {
/* 286 */       this._clipMin = clipMin;
/* 287 */       this._clipMax = clipMax;
/* 288 */       this._colorMap.setValueRange(this._clipMin, this._clipMax);
/* 289 */       this._texturesDirty = true;
/*     */     } 
/*     */   }
/*     */   public float getPercentileMin() {
/*     */     return this._clips.getPercentileMin();
/*     */   }
/*     */   private void drawTextures() {
/* 296 */     AxisAlignedFrame frame = getFrame();
/* 297 */     if (frame == null) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     Axis axis = frame.getAxis();
/* 302 */     if (this._axis != axis) {
/* 303 */       updateSampling(axis, this._sx, this._sy, this._sz);
/*     */     }
/*     */     
/* 306 */     Point3 qmin = frame.getCornerMin();
/* 307 */     Point3 qmax = frame.getCornerMax();
/* 308 */     double xmin = qmin.x;
/* 309 */     double ymin = qmin.y;
/* 310 */     double zmin = qmin.z;
/* 311 */     double xmax = qmax.x;
/* 312 */     double ymax = qmax.y;
/* 313 */     double zmax = qmax.z;
/* 314 */     if (this._xmin != xmin || this._ymin != ymin || this._zmin != zmin || this._xmax != xmax || this._ymax != ymax || this._zmax != zmax)
/*     */     {
/* 316 */       updateBoundsAndTextures(xmin, ymin, zmin, xmax, ymax, zmax);
/*     */     }
/*     */     
/* 319 */     if (this._texturesDirty) {
/* 320 */       updateTextures();
/*     */     }
/*     */     
/* 323 */     Gl.glShadeModel(7424);
/* 324 */     Gl.glEnable(3553);
/* 325 */     Gl.glTexEnvf(8960, 8704, 7681.0F);
/*     */ 
/*     */     
/* 328 */     float[] v = { 0.0F };
/* 329 */     Gl.glGetFloatv(32824, v, 0); float factor = v[0];
/* 330 */     Gl.glGetFloatv(10752, v, 0); float units = v[0];
/* 331 */     Gl.glEnable(32823);
/* 332 */     Gl.glPolygonOffset(factor + 1.0F, units + 1.0F);
/*     */ 
/*     */     
/* 335 */     float sa = 0.5F / this._ls;
/* 336 */     float ta = 0.5F / this._lt;
/* 337 */     float sb = 1.0F / this._ls;
/* 338 */     float tb = 1.0F / this._lt;
/*     */ 
/*     */     
/* 341 */     double xa = 0.5D * (this._xmin + this._xmax);
/* 342 */     double ya = 0.5D * (this._ymin + this._ymax);
/* 343 */     double za = 0.5D * (this._zmin + this._zmax);
/*     */ 
/*     */     
/* 346 */     for (int jt = this._jtmin; jt <= this._jtmax; jt++) {
/* 347 */       for (int js = this._jsmin; js <= this._jsmax; js++) {
/*     */ 
/*     */         
/* 350 */         int ks0 = js * (this._ls - 1);
/* 351 */         int kt0 = jt * (this._lt - 1);
/* 352 */         int ks1 = ks0 + this._ls - 1;
/* 353 */         int kt1 = kt0 + this._lt - 1;
/* 354 */         ks0 = MathPlus.max(this._ksmin, ks0);
/* 355 */         kt0 = MathPlus.max(this._ktmin, kt0);
/* 356 */         ks1 = MathPlus.min(this._ksmax, ks1);
/* 357 */         kt1 = MathPlus.min(this._ktmax, kt1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 369 */         float s0 = sa + (ks0 % (this._ls - 1)) * sb;
/* 370 */         float t0 = ta + (kt0 % (this._lt - 1)) * tb;
/* 371 */         float s1 = s0 + (ks1 - ks0) * sb;
/* 372 */         float t1 = t0 + (kt1 - kt0) * tb;
/*     */ 
/*     */         
/* 375 */         GlTextureName tn = this._tn[jt][js];
/* 376 */         Gl.glBindTexture(3553, tn.name());
/* 377 */         Gl.glBegin(9);
/* 378 */         if (this._axis == Axis.X) {
/* 379 */           double y0 = this._fs + ks0 * this._ds;
/* 380 */           double z0 = this._ft + kt0 * this._dt;
/* 381 */           double y1 = this._fs + ks1 * this._ds;
/* 382 */           double z1 = this._ft + kt1 * this._dt;
/* 383 */           Gl.glTexCoord2f(s0, t0); Gl.glVertex3d(xa, y0, z0);
/* 384 */           Gl.glTexCoord2f(s1, t0); Gl.glVertex3d(xa, y1, z0);
/* 385 */           Gl.glTexCoord2f(s1, t1); Gl.glVertex3d(xa, y1, z1);
/* 386 */           Gl.glTexCoord2f(s0, t1); Gl.glVertex3d(xa, y0, z1);
/* 387 */         } else if (this._axis == Axis.Y) {
/* 388 */           double x0 = this._fs + ks0 * this._ds;
/* 389 */           double z0 = this._ft + kt0 * this._dt;
/* 390 */           double x1 = this._fs + ks1 * this._ds;
/* 391 */           double z1 = this._ft + kt1 * this._dt;
/* 392 */           Gl.glTexCoord2f(s0, t0); Gl.glVertex3d(x0, ya, z0);
/* 393 */           Gl.glTexCoord2f(s1, t0); Gl.glVertex3d(x1, ya, z0);
/* 394 */           Gl.glTexCoord2f(s1, t1); Gl.glVertex3d(x1, ya, z1);
/* 395 */           Gl.glTexCoord2f(s0, t1); Gl.glVertex3d(x0, ya, z1);
/*     */         } else {
/* 397 */           double x0 = this._fs + ks0 * this._ds;
/* 398 */           double y0 = this._ft + kt0 * this._dt;
/* 399 */           double x1 = this._fs + ks1 * this._ds;
/* 400 */           double y1 = this._ft + kt1 * this._dt;
/* 401 */           Gl.glTexCoord2f(s0, t0); Gl.glVertex3d(x0, y0, za);
/* 402 */           Gl.glTexCoord2f(s1, t0); Gl.glVertex3d(x1, y0, za);
/* 403 */           Gl.glTexCoord2f(s1, t1); Gl.glVertex3d(x1, y1, za);
/* 404 */           Gl.glTexCoord2f(s0, t1); Gl.glVertex3d(x0, y1, za);
/*     */         } 
/* 406 */         Gl.glEnd();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 411 */     Gl.glBindTexture(3553, 0);
/* 412 */     Gl.glDisable(3553);
/*     */   } public float getPercentileMax() {
/*     */     return this._clips.getPercentileMax();
/*     */   } public void addColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.addListener(cml);
/*     */   }
/* 418 */   private void updateSampling(Axis axis, Sampling sx, Sampling sy, Sampling sz) { disposeTextures();
/* 419 */     int nx = sx.getCount();
/* 420 */     int ny = sy.getCount();
/* 421 */     int nz = sz.getCount();
/* 422 */     double dx = sx.getDelta();
/* 423 */     double dy = sy.getDelta();
/* 424 */     double dz = sz.getDelta();
/* 425 */     double fx = sx.getFirst();
/* 426 */     double fy = sy.getFirst();
/* 427 */     double fz = sz.getFirst();
/* 428 */     this._axis = axis;
/* 429 */     this._sx = sx;
/* 430 */     this._sy = sy;
/* 431 */     this._sz = sz;
/* 432 */     this._ls = 64;
/* 433 */     this._lt = 64;
/* 434 */     if (this._axis == Axis.X) {
/* 435 */       this._ns = ny;
/* 436 */       this._ds = dy;
/* 437 */       this._fs = fy;
/* 438 */       this._nt = nz;
/* 439 */       this._dt = dz;
/* 440 */       this._ft = fz;
/* 441 */     } else if (this._axis == Axis.Y) {
/* 442 */       this._ns = nx;
/* 443 */       this._ds = dx;
/* 444 */       this._fs = fx;
/* 445 */       this._nt = nz;
/* 446 */       this._dt = dz;
/* 447 */       this._ft = fz;
/*     */     } else {
/* 449 */       this._ns = nx;
/* 450 */       this._ds = dx;
/* 451 */       this._fs = fx;
/* 452 */       this._nt = ny;
/* 453 */       this._dt = dy;
/* 454 */       this._ft = fy;
/*     */     } 
/* 456 */     this._ms = 1 + (this._ns - 2) / (this._ls - 1);
/* 457 */     this._mt = 1 + (this._nt - 2) / (this._lt - 1);
/* 458 */     this._tn = new GlTextureName[this._mt][this._ms];
/* 459 */     this._kxmin = 0; this._kxmax = -1;
/* 460 */     this._kymin = 0; this._kymax = -1;
/* 461 */     this._kzmin = 0; this._kzmax = -1;
/* 462 */     this._ksmin = 0; this._ksmax = -1;
/* 463 */     this._ktmin = 0; this._ktmax = -1;
/* 464 */     this._jsmin = 0; this._jsmax = -1;
/* 465 */     this._jtmin = 0; this._jtmax = -1;
/* 466 */     this._pixels = Direct.newIntBuffer(this._ls * this._lt);
/* 467 */     this._floats = new float[this._ls][this._lt]; } public void removeColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.removeListener(cml);
/*     */   } protected void draw(DrawContext dc) {
/*     */     updateClipMinMax();
/*     */     drawTextures();
/*     */   } private void updateBoundsAndTextures(double xmin, double ymin, double zmin, double xmax, double ymax, double zmax) {
/*     */     boolean stale;
/* 474 */     this._xmin = MathPlus.max(xmin, this._sx.getFirst());
/* 475 */     this._ymin = MathPlus.max(ymin, this._sy.getFirst());
/* 476 */     this._zmin = MathPlus.max(zmin, this._sz.getFirst());
/* 477 */     this._xmax = MathPlus.min(xmax, this._sx.getLast());
/* 478 */     this._ymax = MathPlus.min(ymax, this._sy.getLast());
/* 479 */     this._zmax = MathPlus.min(zmax, this._sz.getLast());
/* 480 */     int kxmin = this._sx.indexOfNearest(this._xmin);
/* 481 */     int kymin = this._sy.indexOfNearest(this._ymin);
/* 482 */     int kzmin = this._sz.indexOfNearest(this._zmin);
/* 483 */     int kxmax = this._sx.indexOfNearest(this._xmax);
/* 484 */     int kymax = this._sy.indexOfNearest(this._ymax);
/* 485 */     int kzmax = this._sz.indexOfNearest(this._zmax);
/*     */     
/* 487 */     if (this._axis == Axis.X) {
/* 488 */       stale = (this._kxmin != kxmin);
/* 489 */       this._kxmin = kxmin;
/* 490 */       this._ksmin = this._kymin = kymin;
/* 491 */       this._ktmin = this._kzmin = kzmin;
/* 492 */       this._kxmax = kxmax;
/* 493 */       this._ksmax = this._kymax = kymax;
/* 494 */       this._ktmax = this._kzmax = kzmax;
/* 495 */     } else if (this._axis == Axis.Y) {
/* 496 */       stale = (this._kymin != kymin);
/* 497 */       this._ksmin = this._kxmin = kxmin;
/* 498 */       this._kymin = kymin;
/* 499 */       this._ktmin = this._kzmin = kzmin;
/* 500 */       this._ksmax = this._kxmax = kxmax;
/* 501 */       this._kymax = kymax;
/* 502 */       this._ktmax = this._kzmax = kzmax;
/*     */     } else {
/* 504 */       stale = (this._kzmin != kzmin);
/* 505 */       this._ksmin = this._kxmin = kxmin;
/* 506 */       this._ktmin = this._kymin = kymin;
/* 507 */       this._kzmin = kzmin;
/* 508 */       this._ksmax = this._kxmax = kxmax;
/* 509 */       this._ktmax = this._kymax = kymax;
/* 510 */       this._kzmax = kzmax;
/*     */     } 
/*     */ 
/*     */     
/* 514 */     int jsmin = this._ksmin / (this._ls - 1);
/* 515 */     int jtmin = this._ktmin / (this._lt - 1);
/* 516 */     int jsmax = MathPlus.max(0, this._ksmax - 1) / (this._ls - 1);
/* 517 */     int jtmax = MathPlus.max(0, this._ktmax - 1) / (this._lt - 1);
/*     */ 
/*     */ 
/*     */     
/* 521 */     ArrayList<GlTextureName> staleList = new ArrayList<>();
/* 522 */     for (int jt = this._jtmin; jt <= this._jtmax; jt++) {
/* 523 */       for (int js = this._jsmin; js <= this._jsmax; js++) {
/* 524 */         if ((stale || js < jsmin || jt < jtmin || jsmax < js || jtmax < jt) && 
/* 525 */           this._tn[jt][js] != null) {
/* 526 */           staleList.add(this._tn[jt][js]);
/* 527 */           this._tn[jt][js] = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 532 */     int nstale = staleList.size();
/*     */ 
/*     */ 
/*     */     
/* 536 */     for (int i = jtmin; i <= jtmax; i++) {
/* 537 */       for (int js = jsmin; js <= jsmax; js++) {
/* 538 */         GlTextureName tn = this._tn[i][js];
/* 539 */         if (tn == null) {
/* 540 */           if (!staleList.isEmpty()) {
/* 541 */             tn = staleList.remove(--nstale);
/*     */           } else {
/* 543 */             tn = makeTexture();
/*     */           } 
/* 545 */           this._tn[i][js] = tn;
/* 546 */           loadTexture(js, i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 552 */     while (nstale > 0) {
/* 553 */       GlTextureName tn = staleList.remove(--nstale);
/* 554 */       tn.dispose();
/*     */     } 
/*     */ 
/*     */     
/* 558 */     this._jsmin = jsmin;
/* 559 */     this._jtmin = jtmin;
/* 560 */     this._jsmax = jsmax;
/* 561 */     this._jtmax = jtmax;
/*     */ 
/*     */     
/* 564 */     this._texturesDirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTextures() {
/* 570 */     for (int jt = this._jtmin; jt <= this._jtmax; jt++) {
/* 571 */       for (int js = this._jsmin; js <= this._jsmax; js++) {
/* 572 */         if (this._tn[jt][js] != null) {
/* 573 */           loadTexture(js, jt);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 578 */     this._texturesDirty = false;
/*     */   }
/*     */   
/*     */   private void disposeTextures() {
/* 582 */     if (this._tn != null) {
/* 583 */       for (int jt = 0; jt < this._mt; jt++) {
/* 584 */         for (int js = 0; js < this._ms; js++) {
/* 585 */           GlTextureName tn = this._tn[jt][js];
/* 586 */           if (tn != null)
/* 587 */             tn.dispose(); 
/*     */         } 
/*     */       } 
/* 590 */       this._tn = (GlTextureName[][])null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private GlTextureName makeTexture() {
/* 595 */     Gl.glPixelStorei(3317, 1);
/* 596 */     GlTextureName tn = new GlTextureName();
/* 597 */     Gl.glBindTexture(3553, tn.name());
/* 598 */     Gl.glTexParameteri(3553, 10242, 10497);
/* 599 */     Gl.glTexParameteri(3553, 10243, 10497);
/* 600 */     Gl.glTexParameteri(3553, 10240, 9729);
/* 601 */     Gl.glTexParameteri(3553, 10241, 9729);
/* 602 */     Gl.glTexImage2D(3553, 0, 6408, this._ls, this._lt, 0, 6408, 5121, this._pixels);
/*     */     
/* 604 */     Gl.glBindTexture(3553, 0);
/* 605 */     return tn;
/*     */   }
/*     */   
/*     */   private void loadTexture(int js, int jt) {
/* 609 */     int ks = js * (this._ls - 1);
/* 610 */     int kt = jt * (this._lt - 1);
/* 611 */     int ls = MathPlus.min(this._ls, this._ns - ks);
/* 612 */     int lt = MathPlus.min(this._lt, this._nt - kt);
/* 613 */     if (this._axis == Axis.X) {
/* 614 */       this._f.get12(lt, ls, kt, ks, this._kxmin, this._floats);
/* 615 */     } else if (this._axis == Axis.Y) {
/* 616 */       this._f.get13(lt, ls, kt, this._kymin, ks, this._floats);
/* 617 */     } else if (this._axis == Axis.Z) {
/* 618 */       this._f.get23(lt, ls, this._kzmin, kt, ks, this._floats);
/*     */     } 
/* 620 */     float fscale = 255.0F / (this._clipMax - this._clipMin);
/* 621 */     float fshift = this._clipMin;
/* 622 */     IndexColorModel icm = this._colorMap.getColorModel();
/* 623 */     for (int is = 0; is < ls; is++) {
/* 624 */       for (int it = 0; it < lt; it++) {
/* 625 */         float fi = (this._floats[is][it] - fshift) * fscale;
/* 626 */         if (fi < 0.0F)
/* 627 */           fi = 0.0F; 
/* 628 */         if (fi > 255.0F)
/* 629 */           fi = 255.0F; 
/* 630 */         int i = (int)(fi + 0.5F);
/* 631 */         int r = icm.getRed(i);
/* 632 */         int g = icm.getGreen(i);
/* 633 */         int b = icm.getBlue(i);
/* 634 */         int a = icm.getAlpha(i);
/* 635 */         int p = r & 0xFF | (g & 0xFF) << 8 | (b & 0xFF) << 16 | (a & 0xFF) << 24;
/* 636 */         this._pixels.put(is + it * this._ls, p);
/*     */       } 
/*     */     } 
/* 639 */     Gl.glPixelStorei(3317, 1);
/* 640 */     GlTextureName tn = this._tn[jt][js];
/* 641 */     Gl.glBindTexture(3553, tn.name());
/* 642 */     Gl.glTexSubImage2D(3553, 0, 0, 0, this._ls, this._lt, 6408, 5121, this._pixels);
/*     */     
/* 644 */     Gl.glBindTexture(3553, 0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/ImagePanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */