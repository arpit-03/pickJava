/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.dsp.EigenTensors3;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TensorsPanel
/*     */   extends AxisAlignedPanel
/*     */ {
/*     */   private Sampling _sx;
/*     */   private Sampling _sy;
/*     */   private Sampling _sz;
/*     */   private EigenTensors3 _et;
/*     */   private float _emax;
/*     */   private int _ellipsoidSize;
/*     */   private EllipsoidGlyph _eg;
/*     */   
/*     */   public TensorsPanel(EigenTensors3 et) {
/*  37 */     this(new Sampling(et.getN1()), new Sampling(et
/*  38 */           .getN2()), new Sampling(et
/*  39 */           .getN3()), et);
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
/*     */   public void update() {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TensorsPanel(Sampling s1, Sampling s2, Sampling s3, EigenTensors3 et) {
/*  97 */     this._ellipsoidSize = 10;
/*  98 */     this._eg = new EllipsoidGlyph();
/*     */     this._sx = s3;
/*     */     this._sy = s2;
/*     */     this._sz = s1;
/*     */     this._et = et;
/*     */     this._emax = findMaxEigenvalue();
/*     */     setStates(StateSet.forTwoSidedShinySurface(Color.CYAN));
/*     */   } private void drawEllipsoids(Axis axis) {
/* 106 */     int nx = this._sx.getCount();
/* 107 */     int ny = this._sy.getCount();
/* 108 */     int nz = this._sz.getCount();
/* 109 */     double dx = this._sx.getDelta();
/* 110 */     double dy = this._sy.getDelta();
/* 111 */     double dz = this._sz.getDelta();
/* 112 */     double fx = this._sx.getFirst();
/* 113 */     double fy = this._sy.getFirst();
/* 114 */     double fz = this._sz.getFirst();
/*     */ 
/*     */     
/* 117 */     double xmin = this._sx.getFirst();
/* 118 */     double xmax = this._sx.getLast();
/* 119 */     double ymin = this._sy.getFirst();
/* 120 */     double ymax = this._sy.getLast();
/* 121 */     double zmin = this._sz.getFirst();
/* 122 */     double zmax = this._sz.getLast();
/*     */ 
/*     */     
/* 125 */     float dmax = 0.5F * this._ellipsoidSize;
/* 126 */     float dxmax = (float)dx * dmax;
/* 127 */     float dymax = (float)dy * dmax;
/* 128 */     float dzmax = (float)dz * dmax;
/*     */ 
/*     */     
/* 131 */     int kec = (int)(2.0D * dmax);
/*     */ 
/*     */     
/* 134 */     float scale = dmax / ArrayMath.sqrt(this._emax);
/*     */ 
/*     */     
/* 137 */     float etiny = 1.0E-4F * this._emax;
/*     */ 
/*     */     
/* 140 */     AxisAlignedFrame aaf = getFrame();
/*     */     
/* 142 */     if (axis == Axis.X) {
/*     */ 
/*     */       
/* 145 */       int nyc = (int)((ymax - ymin) / (2.0F * dymax));
/* 146 */       double dyc = kec * dy;
/* 147 */       double fyc = 0.5D * (ymax - ymin - (nyc - 1) * dyc);
/* 148 */       int jyc = (int)(fyc / dy);
/*     */ 
/*     */       
/* 151 */       int nzc = (int)((zmax - zmin) / (2.0F * dzmax));
/* 152 */       double dzc = kec * dz;
/* 153 */       double fzc = 0.5D * (zmax - zmin - (nzc - 1) * dzc);
/* 154 */       int jzc = (int)(fzc / dz);
/*     */       
/* 156 */       xmin = (aaf.getCornerMin()).x;
/* 157 */       xmax = (aaf.getCornerMax()).x;
/* 158 */       ymin = (aaf.getCornerMin()).y;
/* 159 */       ymax = (aaf.getCornerMax()).y;
/* 160 */       zmin = (aaf.getCornerMin()).z;
/* 161 */       zmax = (aaf.getCornerMax()).z;
/*     */ 
/*     */       
/* 164 */       float xc = 0.5F * (float)(xmax + xmin);
/* 165 */       int ix = this._sx.indexOfNearest(xc);
/*     */       int iy;
/* 167 */       for (iy = jyc; iy < ny; iy += kec) {
/* 168 */         float yc = (float)(fy + iy * dy);
/* 169 */         if (ymin < (yc - dymax) && (yc + dymax) < ymax) {
/* 170 */           int iz; for (iz = jzc; iz < nz; iz += kec) {
/* 171 */             float zc = (float)(fz + iz * dz);
/* 172 */             if (zmin < (zc - dzmax) && (zc + dzmax) < zmax) {
/* 173 */               float[] e = this._et.getEigenvalues(iz, iy, ix);
/* 174 */               float[] u = this._et.getEigenvectorU(iz, iy, ix);
/* 175 */               float[] v = this._et.getEigenvectorV(iz, iy, ix);
/* 176 */               float[] w = this._et.getEigenvectorW(iz, iy, ix);
/* 177 */               float eu = e[0], ev = e[1], ew = e[2];
/* 178 */               if (eu <= etiny) eu = etiny; 
/* 179 */               if (ev <= etiny) ev = etiny; 
/* 180 */               if (ew <= etiny) ew = etiny; 
/* 181 */               float uz = u[0], uy = u[1], ux = u[2];
/* 182 */               float vz = v[0], vy = v[1], vx = v[2];
/* 183 */               float wz = w[0], wy = w[1], wx = w[2];
/* 184 */               float su = scale * ArrayMath.sqrt(eu);
/* 185 */               float sv = scale * ArrayMath.sqrt(ev);
/* 186 */               float sw = scale * ArrayMath.sqrt(ew);
/* 187 */               ux = (float)(ux * su * dx); uy = (float)(uy * su * dy); uz = (float)(uz * su * dz);
/* 188 */               vx = (float)(vx * sv * dx); vy = (float)(vy * sv * dy); vz = (float)(vz * sv * dz);
/* 189 */               wx = (float)(wx * sw * dx); wy = (float)(wy * sw * dy); wz = (float)(wz * sw * dz);
/* 190 */               this._eg.draw(xc, yc, zc, ux, uy, uz, vx, vy, vz, wx, wy, wz);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 195 */     } else if (axis == Axis.Y) {
/*     */ 
/*     */       
/* 198 */       int nxc = (int)((xmax - xmin) / (2.0F * dxmax));
/* 199 */       double dxc = kec * dx;
/* 200 */       double fxc = 0.5D * (xmax - xmin - (nxc - 1) * dxc);
/* 201 */       int jxc = (int)(fxc / dx);
/*     */ 
/*     */       
/* 204 */       int nzc = (int)((zmax - zmin) / (2.0F * dzmax));
/* 205 */       double dzc = kec * dz;
/* 206 */       double fzc = 0.5D * (zmax - zmin - (nzc - 1) * dzc);
/* 207 */       int jzc = (int)(fzc / dz);
/*     */       
/* 209 */       xmin = (aaf.getCornerMin()).x;
/* 210 */       xmax = (aaf.getCornerMax()).x;
/* 211 */       ymin = (aaf.getCornerMin()).y;
/* 212 */       ymax = (aaf.getCornerMax()).y;
/* 213 */       zmin = (aaf.getCornerMin()).z;
/* 214 */       zmax = (aaf.getCornerMax()).z;
/*     */ 
/*     */       
/* 217 */       float yc = 0.5F * (float)(ymax + ymin);
/* 218 */       int iy = this._sy.indexOfNearest(yc);
/*     */       int ix;
/* 220 */       for (ix = jxc; ix < nx; ix += kec) {
/* 221 */         float xc = (float)(fx + ix * dx);
/* 222 */         if (xmin < (xc - dxmax) && (xc + dxmax) < xmax) {
/* 223 */           int iz; for (iz = jzc; iz < nz; iz += kec) {
/* 224 */             float zc = (float)(fz + iz * dz);
/* 225 */             if (zmin < (zc - dzmax) && (zc + dzmax) < zmax) {
/* 226 */               float[] e = this._et.getEigenvalues(iz, iy, ix);
/* 227 */               float[] u = this._et.getEigenvectorU(iz, iy, ix);
/* 228 */               float[] v = this._et.getEigenvectorV(iz, iy, ix);
/* 229 */               float[] w = this._et.getEigenvectorW(iz, iy, ix);
/* 230 */               float eu = e[0], ev = e[1], ew = e[2];
/* 231 */               if (eu <= etiny) eu = etiny; 
/* 232 */               if (ev <= etiny) ev = etiny; 
/* 233 */               if (ew <= etiny) ew = etiny; 
/* 234 */               float uz = u[0], uy = u[1], ux = u[2];
/* 235 */               float vz = v[0], vy = v[1], vx = v[2];
/* 236 */               float wz = w[0], wy = w[1], wx = w[2];
/* 237 */               float su = scale * ArrayMath.sqrt(eu);
/* 238 */               float sv = scale * ArrayMath.sqrt(ev);
/* 239 */               float sw = scale * ArrayMath.sqrt(ew);
/* 240 */               ux = (float)(ux * su * dx); uy = (float)(uy * su * dy); uz = (float)(uz * su * dz);
/* 241 */               vx = (float)(vx * sv * dx); vy = (float)(vy * sv * dy); vz = (float)(vz * sv * dz);
/* 242 */               wx = (float)(wx * sw * dx); wy = (float)(wy * sw * dy); wz = (float)(wz * sw * dz);
/* 243 */               this._eg.draw(xc, yc, zc, ux, uy, uz, vx, vy, vz, wx, wy, wz);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 248 */     } else if (axis == Axis.Z) {
/*     */ 
/*     */       
/* 251 */       int nxc = (int)((xmax - xmin) / (2.0F * dxmax));
/* 252 */       double dxc = kec * dx;
/* 253 */       double fxc = 0.5D * (xmax - xmin - (nxc - 1) * dxc);
/* 254 */       int jxc = (int)(fxc / dx);
/*     */ 
/*     */       
/* 257 */       int nyc = (int)((ymax - ymin) / (2.0F * dymax));
/* 258 */       double dyc = kec * dy;
/* 259 */       double fyc = 0.5D * (ymax - ymin - (nyc - 1) * dyc);
/* 260 */       int jyc = (int)(fyc / dy);
/*     */       
/* 262 */       xmin = (aaf.getCornerMin()).x;
/* 263 */       xmax = (aaf.getCornerMax()).x;
/* 264 */       ymin = (aaf.getCornerMin()).y;
/* 265 */       ymax = (aaf.getCornerMax()).y;
/* 266 */       zmin = (aaf.getCornerMin()).z;
/* 267 */       zmax = (aaf.getCornerMax()).z;
/*     */ 
/*     */       
/* 270 */       float zc = 0.5F * (float)(zmax + zmin);
/* 271 */       int iz = this._sz.indexOfNearest(zc);
/*     */       int ix;
/* 273 */       for (ix = jxc; ix < nx; ix += kec) {
/* 274 */         float xc = (float)(fx + ix * dx);
/* 275 */         if (xmin < (xc - dxmax) && (xc + dxmax) < xmax) {
/* 276 */           int iy; for (iy = jyc; iy < ny; iy += kec) {
/* 277 */             float yc = (float)(fy + iy * dy);
/* 278 */             if (ymin < (yc - dymax) && (yc + dymax) < ymax) {
/* 279 */               float[] e = this._et.getEigenvalues(iz, iy, ix);
/* 280 */               float[] u = this._et.getEigenvectorU(iz, iy, ix);
/* 281 */               float[] v = this._et.getEigenvectorV(iz, iy, ix);
/* 282 */               float[] w = this._et.getEigenvectorW(iz, iy, ix);
/* 283 */               float eu = e[0], ev = e[1], ew = e[2];
/* 284 */               if (eu <= etiny) eu = etiny; 
/* 285 */               if (ev <= etiny) ev = etiny; 
/* 286 */               if (ew <= etiny) ew = etiny; 
/* 287 */               float uz = u[0], uy = u[1], ux = u[2];
/* 288 */               float vz = v[0], vy = v[1], vx = v[2];
/* 289 */               float wz = w[0], wy = w[1], wx = w[2];
/* 290 */               float su = scale * ArrayMath.sqrt(eu);
/* 291 */               float sv = scale * ArrayMath.sqrt(ev);
/* 292 */               float sw = scale * ArrayMath.sqrt(ew);
/* 293 */               ux = (float)(ux * su * dx); uy = (float)(uy * su * dy); uz = (float)(uz * su * dz);
/* 294 */               vx = (float)(vx * sv * dx); vy = (float)(vy * sv * dy); vz = (float)(vz * sv * dz);
/* 295 */               wx = (float)(wx * sw * dx); wy = (float)(wy * sw * dy); wz = (float)(wz * sw * dz);
/* 296 */               this._eg.draw(xc, yc, zc, ux, uy, uz, vx, vy, vz, wx, wy, wz);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void setEllipsoidSize(int size) {
/*     */     this._ellipsoidSize = size;
/*     */     dirtyDraw();
/*     */   }
/*     */   private float findMaxEigenvalue() {
/* 308 */     int n1 = this._et.getN1();
/* 309 */     int n2 = this._et.getN2();
/* 310 */     int n3 = this._et.getN3();
/* 311 */     float[] e = new float[3];
/* 312 */     float emax = 0.0F;
/* 313 */     for (int i3 = 0; i3 < n3; i3++) {
/* 314 */       for (int i2 = 0; i2 < n2; i2++) {
/* 315 */         for (int i1 = 0; i1 < n1; i1++) {
/* 316 */           this._et.getEigenvalues(i1, i2, i3, e);
/* 317 */           float emaxi = ArrayMath.max(e[0], e[1], e[2]);
/* 318 */           if (emax < emaxi)
/* 319 */             emax = emaxi; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 323 */     return emax;
/*     */   }
/*     */   
/*     */   protected void draw(DrawContext dc) {
/*     */     AxisAlignedFrame aaf = getFrame();
/*     */     if (aaf == null)
/*     */       return; 
/*     */     Axis axis = aaf.getAxis();
/*     */     drawEllipsoids(axis);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/TensorsPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */