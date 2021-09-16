/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoxConstraint
/*     */ {
/*     */   private double _xmin;
/*     */   private double _ymin;
/*     */   private double _zmin;
/*     */   private double _xmax;
/*     */   private double _ymax;
/*     */   private double _zmax;
/*     */   private double _dxmin;
/*     */   private double _dymin;
/*     */   private double _dzmin;
/*     */   private Sampling _sx;
/*     */   private Sampling _sy;
/*     */   private Sampling _sz;
/*     */   private boolean _sampled;
/*     */   
/*     */   public BoxConstraint(BoundingBox box) {
/*  35 */     this(box.getMin(), box.getMax(), 0.0D, 0.0D, 0.0D);
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
/*     */   public BoxConstraint(BoundingBox box, double dxmin, double dymin, double dzmin) {
/*  49 */     this(box.getMin(), box.getMax(), dxmin, dymin, dzmin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoxConstraint(Point3 p, Point3 q) {
/*  59 */     this(p, q, 0.0D, 0.0D, 0.0D);
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
/*     */   public BoxConstraint(Point3 p, Point3 q, double dxmin, double dymin, double dzmin) {
/*  74 */     this._xmin = Math.min(p.x, q.x);
/*  75 */     this._ymin = Math.min(p.y, q.y);
/*  76 */     this._zmin = Math.min(p.z, q.z);
/*  77 */     this._xmax = Math.max(p.x, q.x);
/*  78 */     this._ymax = Math.max(p.y, q.y);
/*  79 */     this._zmax = Math.max(p.z, q.z);
/*  80 */     this._dxmin = dxmin;
/*  81 */     this._dymin = dymin;
/*  82 */     this._dzmin = dzmin;
/*  83 */     this._sampled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoxConstraint(Sampling sx, Sampling sy, Sampling sz) {
/*  94 */     this(sx, sy, sz, 0.0D, 0.0D, 0.0D);
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
/*     */   public BoxConstraint(Sampling sx, Sampling sy, Sampling sz, double dxmin, double dymin, double dzmin) {
/* 111 */     this._xmin = sx.getFirst();
/* 112 */     this._ymin = sy.getFirst();
/* 113 */     this._zmin = sz.getFirst();
/* 114 */     this._xmax = sx.getLast();
/* 115 */     this._ymax = sy.getLast();
/* 116 */     this._zmax = sz.getLast();
/* 117 */     this._dxmin = dxmin;
/* 118 */     this._dymin = dymin;
/* 119 */     this._dzmin = dzmin;
/* 120 */     this._sx = sx;
/* 121 */     this._sy = sy;
/* 122 */     this._sz = sz;
/* 123 */     this._sampled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox getBoundingBox() {
/* 131 */     return new BoundingBox(this._xmin, this._ymin, this._zmin, this._xmax, this._ymax, this._zmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingSphere getBoundingSphere() {
/* 139 */     BoundingSphere bs = new BoundingSphere();
/* 140 */     bs.expandBy(getBoundingBox());
/* 141 */     return bs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPoint(Point3 p) {
/* 150 */     return (this._xmin <= p.x && p.x <= this._xmax && this._ymin <= p.y && p.y <= this._ymax && this._zmin <= p.z && p.z <= this._zmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrainPoint(Point3 p) {
/* 160 */     p.x = Math.max(this._xmin, Math.min(this._xmax, p.x));
/* 161 */     p.y = Math.max(this._ymin, Math.min(this._ymax, p.y));
/* 162 */     p.z = Math.max(this._zmin, Math.min(this._zmax, p.z));
/* 163 */     if (this._sampled) {
/* 164 */       p.x = this._sx.valueOfNearest(p.x);
/* 165 */       p.y = this._sy.valueOfNearest(p.y);
/* 166 */       p.z = this._sz.valueOfNearest(p.z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrainBox(Point3 p, Point3 q) {
/* 177 */     double ax = Math.min(p.x, q.x);
/* 178 */     double bx = Math.max(p.x, q.x);
/* 179 */     double dx = Math.max(bx - ax, this._dxmin);
/* 180 */     if (p.x <= q.x) {
/* 181 */       ax = Math.max(this._xmin, Math.min(this._xmax, ax));
/* 182 */       bx = Math.min(this._xmax, ax + dx);
/* 183 */       ax = Math.max(this._xmin, bx - dx);
/*     */     } else {
/* 185 */       bx = Math.min(this._xmax, Math.max(this._xmin, bx));
/* 186 */       ax = Math.max(this._xmin, bx - dx);
/* 187 */       bx = Math.min(this._xmax, ax + dx);
/*     */     } 
/* 189 */     if (this._sampled) {
/* 190 */       ax = this._sx.valueOfNearest(ax);
/* 191 */       bx = this._sx.valueOfNearest(bx);
/*     */     } 
/* 193 */     if (p.x <= q.x) {
/* 194 */       p.x = ax;
/* 195 */       q.x = bx;
/*     */     } else {
/* 197 */       p.x = bx;
/* 198 */       q.x = ax;
/*     */     } 
/* 200 */     double ay = Math.min(p.y, q.y);
/* 201 */     double by = Math.max(p.y, q.y);
/* 202 */     double dy = Math.max(by - ay, this._dymin);
/* 203 */     if (p.y <= q.y) {
/* 204 */       ay = Math.max(this._ymin, Math.min(this._ymax, ay));
/* 205 */       by = Math.min(this._ymax, ay + dy);
/* 206 */       ay = Math.max(this._ymin, by - dy);
/*     */     } else {
/* 208 */       by = Math.min(this._ymax, Math.max(this._ymin, by));
/* 209 */       ay = Math.max(this._ymin, by - dy);
/* 210 */       by = Math.min(this._ymax, ay + dy);
/*     */     } 
/* 212 */     if (this._sampled) {
/* 213 */       ay = this._sy.valueOfNearest(ay);
/* 214 */       by = this._sy.valueOfNearest(by);
/*     */     } 
/* 216 */     if (p.y <= q.y) {
/* 217 */       p.y = ay;
/* 218 */       q.y = by;
/*     */     } else {
/* 220 */       p.y = by;
/* 221 */       q.y = ay;
/*     */     } 
/* 223 */     double az = Math.min(p.z, q.z);
/* 224 */     double bz = Math.max(p.z, q.z);
/* 225 */     double dz = Math.max(bz - az, this._dzmin);
/* 226 */     if (p.z <= q.z) {
/* 227 */       az = Math.max(this._zmin, Math.min(this._zmax, az));
/* 228 */       bz = Math.min(this._zmax, az + dz);
/* 229 */       az = Math.max(this._zmin, bz - dz);
/*     */     } else {
/* 231 */       bz = Math.min(this._zmax, Math.max(this._zmin, bz));
/* 232 */       az = Math.max(this._zmin, bz - dz);
/* 233 */       bz = Math.min(this._zmax, az + dz);
/*     */     } 
/* 235 */     if (this._sampled) {
/* 236 */       az = this._sz.valueOfNearest(az);
/* 237 */       bz = this._sz.valueOfNearest(bz);
/*     */     } 
/* 239 */     if (p.z <= q.z) {
/* 240 */       p.z = az;
/* 241 */       q.z = bz;
/*     */     } else {
/* 243 */       p.z = bz;
/* 244 */       q.z = az;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/BoxConstraint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */