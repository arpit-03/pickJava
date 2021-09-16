/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.awt.ColorMapListener;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.ogl.Gl;
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
/*     */ public class ImagePanelGroup
/*     */   extends Group
/*     */ {
/*     */   private Sampling _s1;
/*     */   private Sampling _s2;
/*     */   private Sampling _s3;
/*     */   private ArrayList<ImagePanel> _ipList;
/*     */   private Clips _clips;
/*     */   private ColorMap _colorMap;
/*     */   
/*     */   public ImagePanelGroup(float[][][] f) {
/*  50 */     this(new Sampling((f[0][0]).length), new Sampling((f[0]).length), new Sampling(f.length), f);
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
/*     */   public ImagePanelGroup(Sampling s1, Sampling s2, Sampling s3, float[][][] f) {
/*  64 */     this(s1, s2, s3, (Float3)new SimpleFloat3(f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePanelGroup(Sampling s1, Sampling s2, Sampling s3, Float3 f) {
/*  75 */     this(s1, s2, s3, f, new Axis[] { Axis.X, Axis.Y, Axis.Z });
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
/*     */   public ImagePanelGroup(Sampling s1, Sampling s2, Sampling s3, float[][][] f, Axis[] axes) {
/*  89 */     this(s1, s2, s3, (Float3)new SimpleFloat3(f), axes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePanel getImagePanel(Axis axis) {
/*     */     for (ImagePanel ip : this._ipList) {
/*     */       if (axis == ip.getFrame().getAxis()) {
/*     */         return ip;
/*     */       }
/*     */     } 
/*     */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ImagePanel> getImagePanels() {
/*     */     return this._ipList.iterator();
/*     */   }
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
/*     */     for (ImagePanel ip : this._ipList) {
/*     */       ip.setColorModel(colorModel);
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
/*     */   public IndexColorModel getColorModel() {
/*     */     return this._colorMap.getColorModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClips(double clipMin, double clipMax) {
/*     */     this._clips.setClips(clipMin, clipMax);
/*     */     clipMin = this._clips.getClipMin();
/*     */     clipMax = this._clips.getClipMax();
/*     */     for (ImagePanel ip : this._ipList) {
/*     */       ip.setClips(clipMin, clipMax);
/*     */     }
/*     */     this._colorMap.setValueRange(clipMin, clipMax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getClipMin() {
/*     */     return this._clips.getClipMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getClipMax() {
/*     */     return this._clips.getClipMax();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPercentiles(double percMin, double percMax) {
/*     */     this._clips.setPercentiles(percMin, percMax);
/*     */     double clipMin = this._clips.getClipMin();
/*     */     double clipMax = this._clips.getClipMax();
/*     */     for (ImagePanel ip : this._ipList) {
/*     */       ip.setClips(clipMin, clipMax);
/*     */     }
/*     */     this._colorMap.setValueRange(clipMin, clipMax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPercentileMin() {
/*     */     return this._clips.getPercentileMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPercentileMax() {
/*     */     return this._clips.getPercentileMax();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.addListener(cml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.removeListener(cml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlices(int k1, int k2, int k3) {
/*     */     int nx = this._s3.getCount();
/*     */     int ny = this._s2.getCount();
/*     */     int nz = this._s1.getCount();
/*     */     double dx = this._s3.getDelta();
/*     */     double dy = this._s2.getDelta();
/*     */     double dz = this._s1.getDelta();
/*     */     double fx = this._s3.getFirst();
/*     */     double fy = this._s2.getFirst();
/*     */     double fz = this._s1.getFirst();
/*     */     double lx = fx + (nx - 1) * dx;
/*     */     double ly = fy + (ny - 1) * dy;
/*     */     double lz = fz + (nz - 1) * dz;
/*     */     int kx = Math.max(0, Math.min(nx - 1, k3));
/*     */     int ky = Math.max(0, Math.min(ny - 1, k2));
/*     */     int kz = Math.max(0, Math.min(nz - 1, k1));
/*     */     double xk = fx + kx * dx;
/*     */     double yk = fy + ky * dy;
/*     */     double zk = fz + kz * dz;
/*     */     for (ImagePanel ip : this._ipList) {
/*     */       AxisAlignedFrame frame = ip.getFrame();
/*     */       Axis axis = frame.getAxis();
/*     */       if (axis == Axis.X) {
/*     */         frame.setCorners(new Point3(xk, fy, fz), new Point3(xk, ly, lz));
/*     */         continue;
/*     */       } 
/*     */       if (axis == Axis.Y) {
/*     */         frame.setCorners(new Point3(fx, yk, fz), new Point3(lx, yk, lz));
/*     */         continue;
/*     */       } 
/*     */       frame.setCorners(new Point3(fx, fy, zk), new Point3(lx, ly, zk));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePanelGroup(Sampling s1, Sampling s2, Sampling s3, Float3 f, Axis[] axes) {
/* 283 */     this._colorMap = new ColorMap(0.0D, 1.0D, ColorMap.GRAY);
/*     */     this._clips = new Clips(f);
/*     */     addPanels(s1, s2, s3, f, axes);
/*     */     addChild(new Wires(this._ipList));
/*     */   } private void addPanels(Sampling s1, Sampling s2, Sampling s3, Float3 f3, Axis[] axes) {
/* 288 */     this._s1 = s1;
/* 289 */     this._s2 = s2;
/* 290 */     this._s3 = s3;
/* 291 */     int nx = s3.getCount();
/* 292 */     int ny = s2.getCount();
/* 293 */     int nz = s1.getCount();
/* 294 */     double dx = s3.getDelta();
/* 295 */     double dy = s2.getDelta();
/* 296 */     double dz = s1.getDelta();
/* 297 */     double fx = s3.getFirst();
/* 298 */     double fy = s2.getFirst();
/* 299 */     double fz = s1.getFirst();
/* 300 */     double lx = fx + (nx - 1) * dx;
/* 301 */     double ly = fy + (ny - 1) * dy;
/* 302 */     double lz = fz + (nz - 1) * dz;
/* 303 */     Point3 qmin = new Point3(fx, fy, fz);
/* 304 */     Point3 qmax = new Point3(lx, ly, lz);
/* 305 */     int np = axes.length;
/* 306 */     this._ipList = new ArrayList<>(np);
/* 307 */     for (int jp = 0; jp < np; jp++) {
/* 308 */       AxisAlignedQuad aaq = new AxisAlignedQuad(axes[jp], qmin, qmax);
/* 309 */       ImagePanel ip = new ImagePanel(s1, s2, s3, f3);
/* 310 */       ip.setColorModel(getColorModel());
/* 311 */       aaq.getFrame().addChild(ip);
/* 312 */       addChild(aaq);
/* 313 */       this._ipList.add(ip);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class Wires extends Node { private ArrayList<ImagePanel> _ipList;
/*     */     
/*     */     Wires(ArrayList<ImagePanel> ipList) {
/* 320 */       this._ipList = ipList;
/*     */     }
/*     */     protected void draw(DrawContext dc) {
/* 323 */       Gl.glColor3f(1.0F, 1.0F, 1.0F);
/* 324 */       Gl.glLineWidth(1.5F);
/* 325 */       for (ImagePanel ip : this._ipList) {
/* 326 */         AxisAlignedFrame fi = ip.getFrame();
/* 327 */         Axis ai = fi.getAxis();
/* 328 */         for (ImagePanel jp : this._ipList) {
/* 329 */           AxisAlignedFrame fj = jp.getFrame();
/* 330 */           Axis aj = fj.getAxis();
/* 331 */           if (ai != aj) {
/* 332 */             Point3 pmini = fi.getCornerMin();
/* 333 */             Point3 pmaxi = fi.getCornerMax();
/* 334 */             Point3 pminj = fj.getCornerMin();
/* 335 */             Point3 pmaxj = fj.getCornerMax();
/* 336 */             double xmin = Math.max(pmini.x, pminj.x);
/* 337 */             double xmax = Math.min(pmaxi.x, pmaxj.x);
/* 338 */             double ymin = Math.max(pmini.y, pminj.y);
/* 339 */             double ymax = Math.min(pmaxi.y, pmaxj.y);
/* 340 */             double zmin = Math.max(pmini.z, pminj.z);
/* 341 */             double zmax = Math.min(pmaxi.z, pmaxj.z);
/* 342 */             if (xmin <= xmax && ymin <= ymax && zmin <= zmax) {
/* 343 */               Gl.glBegin(1);
/* 344 */               Gl.glVertex3d(xmin, ymin, zmin);
/* 345 */               Gl.glVertex3d(xmax, ymax, zmax);
/* 346 */               Gl.glEnd();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/ImagePanelGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */