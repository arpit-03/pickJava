/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.awt.ColorMapListener;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.Clips;
/*     */ import edu.mines.jtk.util.Float3;
/*     */ import edu.mines.jtk.util.SimpleFloat3;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImagePanelGroup2
/*     */   extends Group
/*     */ {
/*     */   private Sampling _s1;
/*     */   private Sampling _s2;
/*     */   private Sampling _s3;
/*     */   private ArrayList<ImagePanel> _ip1List;
/*     */   private ArrayList<ImagePanel> _ip2List;
/*     */   private Clips _clips1;
/*     */   private Clips _clips2;
/*     */   private ColorMap _colorMap1;
/*     */   private ColorMap _colorMap2;
/*     */   
/*     */   public ImagePanelGroup2(float[][][] f1, float[][][] f2) {
/*  56 */     this(new Sampling((f1[0][0]).length), new Sampling((f1[0]).length), new Sampling(f1.length), f1, f2);
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
/*     */   public ImagePanelGroup2(Sampling s1, Sampling s2, Sampling s3, float[][][] f1, float[][][] f2) {
/*  75 */     this(s1, s2, s3, (Float3)new SimpleFloat3(f1), (Float3)new SimpleFloat3(f2));
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
/*     */   public ImagePanelGroup2(Sampling s1, Sampling s2, Sampling s3, Float3 f1, Float3 f2) {
/*  91 */     this(s1, s2, s3, f1, f2, new Axis[] { Axis.X, Axis.Y, Axis.Z });
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
/*     */   public ImagePanelGroup2(Sampling s1, Sampling s2, Sampling s3, float[][][] f1, float[][][] f2, Axis[] axes) {
/* 108 */     this(s1, s2, s3, (Float3)new SimpleFloat3(f1), (Float3)new SimpleFloat3(f2), axes);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePanelGroup2(Sampling s1, Sampling s2, Sampling s3, Float3 f1, Float3 f2, Axis[] axes) {
/* 455 */     this._colorMap1 = new ColorMap(0.0D, 1.0D, ColorMap.GRAY);
/* 456 */     this._colorMap2 = new ColorMap(0.0D, 1.0D, ColorMap.getJet(0.5D)); this._clips1 = new Clips(f1); this._clips2 = new Clips(f2); addPanels(s1, s2, s3, f1, f2, axes); addChild(new ImagePanelGroup.Wires(this._ip1List));
/*     */   } public void update1() { for (ImagePanel ip : this._ip1List) ip.update();  } public void update2() { for (ImagePanel ip : this._ip2List) ip.update();  } public ImagePanel getImagePanel1(Axis axis) { for (ImagePanel ip : this._ip1List) { if (axis == ip.getFrame().getAxis()) return ip;  }  return null; } public ImagePanel getImagePanel2(Axis axis) { for (ImagePanel ip : this._ip2List) { if (axis == ip.getFrame().getAxis()) return ip;  }  return null; } public Iterator<ImagePanel> getImagePanels1() { return this._ip1List.iterator(); } public Iterator<ImagePanel> getImagePanels2() { return this._ip2List.iterator(); } public void setColorModel1(IndexColorModel colorModel) { this._colorMap1.setColorModel(colorModel); for (ImagePanel ip : this._ip1List) ip.setColorModel(colorModel);  } public void setColorModel2(IndexColorModel colorModel) { this._colorMap2.setColorModel(colorModel); for (ImagePanel ip : this._ip2List) ip.setColorModel(colorModel);  } public IndexColorModel getColorModel1() { return this._colorMap1.getColorModel(); } public IndexColorModel getColorModel2() { return this._colorMap2.getColorModel(); }
/*     */   public void setClips1(double clipMin, double clipMax) { this._clips1.setClips(clipMin, clipMax); clipMin = this._clips1.getClipMin(); clipMax = this._clips1.getClipMax(); for (ImagePanel ip : this._ip1List) ip.setClips(clipMin, clipMax);  this._colorMap1.setValueRange(clipMin, clipMax); }
/*     */   public void setClips2(double clipMin, double clipMax) { this._clips2.setClips(clipMin, clipMax); clipMin = this._clips2.getClipMin(); clipMax = this._clips2.getClipMax(); for (ImagePanel ip : this._ip2List) ip.setClips(clipMin, clipMax);  this._colorMap2.setValueRange(clipMin, clipMax); }
/*     */   public float getClip1Min() { return this._clips1.getClipMin(); }
/* 461 */   private static void checkSampling(Sampling s1, Sampling s2, Sampling s3, Float3 f1, Float3 f2) { Check.argument((f1.getN1() == s1.getCount()), "f1.getN1()==s1.getCount()");
/*     */     
/* 463 */     Check.argument((f1.getN2() == s2.getCount()), "f1.getN2()==s2.getCount()");
/*     */     
/* 465 */     Check.argument((f1.getN3() == s3.getCount()), "f1.getN3()==s3.getCount()");
/*     */     
/* 467 */     Check.argument((f2.getN1() == s1.getCount()), "f2.getN1()==s1.getCount()");
/*     */     
/* 469 */     Check.argument((f2.getN2() == s2.getCount()), "f2.getN2()==s2.getCount()");
/*     */     
/* 471 */     Check.argument((f2.getN3() == s3.getCount()), "f2.getN3()==s3.getCount()"); }
/*     */   public float getClip2Min() { return this._clips2.getClipMin(); }
/*     */   public float getClip1Max() { return this._clips1.getClipMax(); }
/*     */   public float getClip2Max() { return this._clips2.getClipMax(); }
/*     */   public void setPercentiles1(double percMin, double percMax) { this._clips1.setPercentiles(percMin, percMax); double clipMin = this._clips1.getClipMin(); double clipMax = this._clips1.getClipMax(); for (ImagePanel ip : this._ip1List) ip.setClips(clipMin, clipMax);  this._colorMap1.setValueRange(clipMin, clipMax); }
/*     */   public void setPercentiles2(double percMin, double percMax) { this._clips2.setPercentiles(percMin, percMax); double clipMin = this._clips2.getClipMin(); double clipMax = this._clips2.getClipMax(); for (ImagePanel ip : this._ip2List) ip.setClips(clipMin, clipMax);  this._colorMap2.setValueRange(clipMin, clipMax); }
/*     */   public float getPercentile1Min() { return this._clips1.getPercentileMin(); }
/* 478 */   public float getPercentile2Min() { return this._clips2.getPercentileMin(); } private void addPanels(Sampling s1, Sampling s2, Sampling s3, Float3 f1, Float3 f2, Axis[] axes) { checkSampling(s1, s2, s3, f1, f2);
/* 479 */     this._s1 = s1;
/* 480 */     this._s2 = s2;
/* 481 */     this._s3 = s3;
/* 482 */     int nx = s3.getCount();
/* 483 */     int ny = s2.getCount();
/* 484 */     int nz = s1.getCount();
/* 485 */     double dx = s3.getDelta();
/* 486 */     double dy = s2.getDelta();
/* 487 */     double dz = s1.getDelta();
/* 488 */     double fx = s3.getFirst();
/* 489 */     double fy = s2.getFirst();
/* 490 */     double fz = s1.getFirst();
/* 491 */     double lx = fx + (nx - 1) * dx;
/* 492 */     double ly = fy + (ny - 1) * dy;
/* 493 */     double lz = fz + (nz - 1) * dz;
/* 494 */     Point3 qmin = new Point3(fx, fy, fz);
/* 495 */     Point3 qmax = new Point3(lx, ly, lz);
/* 496 */     int np = axes.length;
/* 497 */     this._ip1List = new ArrayList<>(np);
/* 498 */     this._ip2List = new ArrayList<>(np);
/* 499 */     PolygonState ps1 = new PolygonState();
/* 500 */     ps1.setPolygonOffset(1.0F, 1.0F);
/* 501 */     ps1.setPolygonOffsetFill(true);
/* 502 */     StateSet ss1 = new StateSet();
/* 503 */     ss1.add(ps1);
/* 504 */     BlendState bs2 = new BlendState();
/* 505 */     bs2.setFunction(770, 771);
/* 506 */     StateSet ss2 = new StateSet();
/* 507 */     ss2.add(bs2);
/* 508 */     for (int jp = 0; jp < np; jp++)
/* 509 */     { AxisAlignedQuad aaq = new AxisAlignedQuad(axes[jp], qmin, qmax);
/* 510 */       AxisAlignedFrame aaf = aaq.getFrame();
/* 511 */       ImagePanel ip1 = new ImagePanel(s1, s2, s3, f1);
/* 512 */       ImagePanel ip2 = new ImagePanel(s1, s2, s3, f2);
/* 513 */       ip1.setStates(ss1);
/* 514 */       ip2.setStates(ss2);
/* 515 */       ip1.setColorModel(getColorModel1());
/* 516 */       ip2.setColorModel(getColorModel2());
/* 517 */       aaf.addChild(ip1);
/* 518 */       aaf.addChild(ip2);
/* 519 */       addChild(aaq);
/* 520 */       this._ip1List.add(ip1);
/* 521 */       this._ip2List.add(ip2); }  }
/*     */   public float getPercentile1Max() { return this._clips1.getPercentileMax(); }
/*     */   public float getPercentile2Max() { return this._clips2.getPercentileMax(); }
/*     */   public void addColorMap1Listener(ColorMapListener cml) { this._colorMap1.addListener(cml); }
/*     */   public void addColorMap2Listener(ColorMapListener cml) { this._colorMap2.addListener(cml); }
/*     */   public void removeColorMap1Listener(ColorMapListener cml) { this._colorMap1.removeListener(cml); } public void removeColorMap2Listener(ColorMapListener cml) { this._colorMap2.removeListener(cml); } public void setSlices(int k1, int k2, int k3) { int nx = this._s3.getCount(); int ny = this._s2.getCount(); int nz = this._s1.getCount(); double dx = this._s3.getDelta(); double dy = this._s2.getDelta(); double dz = this._s1.getDelta(); double fx = this._s3.getFirst(); double fy = this._s2.getFirst(); double fz = this._s1.getFirst(); double lx = fx + (nx - 1) * dx; double ly = fy + (ny - 1) * dy; double lz = fz + (nz - 1) * dz; int kx = Math.max(0, Math.min(nx - 1, k3)); int ky = Math.max(0, Math.min(ny - 1, k2)); int kz = Math.max(0, Math.min(nz - 1, k1)); double xk = fx + kx * dx; double yk = fy + ky * dy; double zk = fz + kz * dz; for (ImagePanel ip : this._ip1List) { AxisAlignedFrame frame = ip.getFrame(); Axis axis = frame.getAxis(); if (axis == Axis.X) { frame.setCorners(new Point3(xk, fy, fz), new Point3(xk, ly, lz)); continue; }  if (axis == Axis.Y) { frame.setCorners(new Point3(fx, yk, fz), new Point3(lx, yk, lz)); continue; }  frame.setCorners(new Point3(fx, fy, zk), new Point3(lx, ly, zk)); }  } private class XWires extends Node
/*     */   {
/* 528 */     protected void draw(DrawContext dc) { Gl.glColor3f(1.0F, 1.0F, 1.0F);
/* 529 */       Gl.glLineWidth(1.5F);
/* 530 */       for (ImagePanel ip : ImagePanelGroup2.this._ip1List) {
/* 531 */         AxisAlignedFrame fi = ip.getFrame();
/* 532 */         Axis ai = fi.getAxis();
/* 533 */         for (ImagePanel jp : ImagePanelGroup2.this._ip1List) {
/* 534 */           AxisAlignedFrame fj = jp.getFrame();
/* 535 */           Axis aj = fj.getAxis();
/* 536 */           if (ai != aj) {
/* 537 */             Point3 pmini = fi.getCornerMin();
/* 538 */             Point3 pmaxi = fi.getCornerMax();
/* 539 */             Point3 pminj = fj.getCornerMin();
/* 540 */             Point3 pmaxj = fj.getCornerMax();
/* 541 */             double xmin = Math.max(pmini.x, pminj.x);
/* 542 */             double xmax = Math.min(pmaxi.x, pmaxj.x);
/* 543 */             double ymin = Math.max(pmini.y, pminj.y);
/* 544 */             double ymax = Math.min(pmaxi.y, pmaxj.y);
/* 545 */             double zmin = Math.max(pmini.z, pminj.z);
/* 546 */             double zmax = Math.min(pmaxi.z, pmaxj.z);
/* 547 */             if (xmin <= xmax && ymin <= ymax && zmin <= zmax) {
/* 548 */               Gl.glBegin(1);
/* 549 */               Gl.glVertex3d(xmin, ymin, zmin);
/* 550 */               Gl.glVertex3d(xmax, ymax, zmax);
/* 551 */               Gl.glEnd();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/*     */   
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/ImagePanelGroup2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */