/*     */ package edu.mines.jtk.sgl;
/*     */ 
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
/*     */ public class BoundingBox
/*     */ {
/*     */   private double _xmin;
/*     */   private double _ymin;
/*     */   private double _zmin;
/*     */   private double _xmax;
/*     */   private double _ymax;
/*     */   private double _zmax;
/*     */   
/*     */   public BoundingBox() {
/*  41 */     setEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox(Point3 p) {
/*  49 */     this._xmin = this._xmax = p.x;
/*  50 */     this._ymin = this._ymax = p.y;
/*  51 */     this._zmin = this._zmax = p.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox(Point3 p, Point3 q) {
/*  61 */     this._xmin = Math.min(p.x, q.x);
/*  62 */     this._ymin = Math.min(p.y, q.y);
/*  63 */     this._zmin = Math.min(p.z, q.z);
/*  64 */     this._xmax = Math.max(p.x, q.x);
/*  65 */     this._ymax = Math.max(p.y, q.y);
/*  66 */     this._zmax = Math.max(p.z, q.z);
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
/*     */   public BoundingBox(double xmin, double ymin, double zmin, double xmax, double ymax, double zmax) {
/*  82 */     Check.argument((xmin <= xmax), "xmin<=xmax");
/*  83 */     Check.argument((ymin <= ymax), "ymin<=ymax");
/*  84 */     Check.argument((zmin <= zmax), "zmin<=zmax");
/*  85 */     this._xmin = xmin;
/*  86 */     this._ymin = ymin;
/*  87 */     this._zmin = zmin;
/*  88 */     this._xmax = xmax;
/*  89 */     this._ymax = ymax;
/*  90 */     this._zmax = zmax;
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
/*     */   public BoundingBox(float[] xyz) {
/* 102 */     this();
/* 103 */     expandBy(xyz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox(float[] x, float[] y, float[] z) {
/* 113 */     this();
/* 114 */     expandBy(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox(BoundingBox bb) {
/* 122 */     this._xmin = bb._xmin;
/* 123 */     this._ymin = bb._ymin;
/* 124 */     this._zmin = bb._zmin;
/* 125 */     this._xmax = bb._xmax;
/* 126 */     this._ymax = bb._ymax;
/* 127 */     this._zmax = bb._zmax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 135 */     return (this._xmin > this._xmax || this._ymin > this._ymax || this._zmin > this._zmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 143 */     return (this._xmin == Double.NEGATIVE_INFINITY && this._ymin == Double.NEGATIVE_INFINITY && this._zmin == Double.NEGATIVE_INFINITY && this._xmax == Double.POSITIVE_INFINITY && this._ymax == Double.POSITIVE_INFINITY && this._zmax == Double.POSITIVE_INFINITY);
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
/*     */   public Point3 getMin() {
/* 156 */     Check.state(!isEmpty(), "bounding box is not empty");
/* 157 */     return new Point3(this._xmin, this._ymin, this._zmin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getMax() {
/* 165 */     Check.state(!isEmpty(), "bounding box is not empty");
/* 166 */     return new Point3(this._xmax, this._ymax, this._zmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getCenter() {
/* 174 */     Check.state(!isEmpty(), "bounding box is not empty");
/* 175 */     Check.state(!isInfinite(), "bounding box is not infinite");
/* 176 */     return isInfinite() ? new Point3(0.0D, 0.0D, 0.0D) : new Point3(0.5D * (this._xmin + this._xmax), 0.5D * (this._ymin + this._ymax), 0.5D * (this._zmin + this._zmax));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 186 */     return Math.sqrt(getRadiusSquared());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadiusSquared() {
/* 194 */     Check.state(!isEmpty(), "bounding box is not empty");
/* 195 */     double dx = this._xmax - this._xmin;
/* 196 */     double dy = this._ymax - this._ymin;
/* 197 */     double dz = this._zmax - this._zmin;
/* 198 */     return 0.25D * (dx * dx + dy * dy + dz * dz);
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
/*     */   public Point3 getCorner(int index) {
/* 211 */     Check.state(!isEmpty(), "bounding box is not empty");
/* 212 */     double x = ((index & 0x1) == 0) ? this._xmin : this._xmax;
/* 213 */     double y = ((index & 0x2) == 0) ? this._ymin : this._ymax;
/* 214 */     double z = ((index & 0x4) == 0) ? this._zmin : this._zmax;
/* 215 */     return new Point3(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandBy(Point3 p) {
/* 223 */     expandBy(p.x, p.y, p.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandBy(double x, double y, double z) {
/* 233 */     if (this._xmin > x) this._xmin = x; 
/* 234 */     if (this._ymin > y) this._ymin = y; 
/* 235 */     if (this._zmin > z) this._zmin = z; 
/* 236 */     if (this._xmax < x) this._xmax = x; 
/* 237 */     if (this._ymax < y) this._ymax = y; 
/* 238 */     if (this._zmax < z) this._zmax = z;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandBy(float[] xyz) {
/* 250 */     int n = xyz.length;
/* 251 */     for (int i = 0; i < n; i += 3) {
/* 252 */       expandBy(xyz[i], xyz[i + 1], xyz[i + 2]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandBy(float[] x, float[] y, float[] z) {
/* 262 */     int n = x.length;
/* 263 */     for (int i = 0; i < n; i++) {
/* 264 */       expandBy(x[i], y[i], z[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandBy(BoundingBox bb) {
/* 272 */     if (this._xmin > bb._xmin) this._xmin = bb._xmin; 
/* 273 */     if (this._ymin > bb._ymin) this._ymin = bb._ymin; 
/* 274 */     if (this._zmin > bb._zmin) this._zmin = bb._zmin; 
/* 275 */     if (this._xmax < bb._xmax) this._xmax = bb._xmax; 
/* 276 */     if (this._ymax < bb._ymax) this._ymax = bb._ymax; 
/* 277 */     if (this._zmax < bb._zmax) this._zmax = bb._zmax;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandBy(BoundingSphere bs) {
/* 285 */     if (!bs.isInfinite()) {
/* 286 */       if (!bs.isEmpty()) {
/* 287 */         double r = bs.getRadius();
/* 288 */         Point3 c = bs.getCenter();
/* 289 */         double x = c.x;
/* 290 */         double y = c.y;
/* 291 */         double z = c.z;
/* 292 */         if (this._xmin > x - r) this._xmin = x - r; 
/* 293 */         if (this._ymin > y - r) this._ymin = y - r; 
/* 294 */         if (this._zmin > z - r) this._zmin = z - r; 
/* 295 */         if (this._xmax < x + r) this._xmax = x + r; 
/* 296 */         if (this._ymax < y + r) this._ymax = y + r; 
/* 297 */         if (this._zmax < z + r) this._zmax = z + r; 
/*     */       } 
/*     */     } else {
/* 300 */       setInfinite();
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
/*     */   public boolean contains(double x, double y, double z) {
/* 312 */     return (this._xmin <= x && x <= this._xmax && this._ymin <= y && y <= this._ymax && this._zmin <= z && z <= this._zmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Point3 p) {
/* 323 */     return contains(p.x, p.y, p.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(BoundingBox bb) {
/* 332 */     return (contains(bb._xmin, bb._ymin, bb._zmin) && 
/* 333 */       contains(bb._xmax, bb._ymax, bb._zmax));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(BoundingBox bb) {
/* 342 */     return (Math.max(this._xmin, bb._xmin) <= Math.min(this._xmax, bb._xmax) && 
/* 343 */       Math.max(this._ymin, bb._ymin) <= Math.min(this._ymax, bb._ymax) && 
/* 344 */       Math.max(this._zmin, bb._zmin) <= Math.min(this._zmax, bb._zmax));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoundingBox empty() {
/* 352 */     return new BoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoundingBox infinite() {
/* 360 */     BoundingBox bb = new BoundingBox();
/* 361 */     bb.setInfinite();
/* 362 */     return bb;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 366 */     return "{" + getMin() + ":" + getMax() + "}";
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
/*     */   private void setEmpty() {
/* 383 */     this._xmin = Double.POSITIVE_INFINITY;
/* 384 */     this._ymin = Double.POSITIVE_INFINITY;
/* 385 */     this._zmin = Double.POSITIVE_INFINITY;
/* 386 */     this._xmax = Double.NEGATIVE_INFINITY;
/* 387 */     this._ymax = Double.NEGATIVE_INFINITY;
/* 388 */     this._zmax = Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInfinite() {
/* 395 */     this._xmin = Double.NEGATIVE_INFINITY;
/* 396 */     this._ymin = Double.NEGATIVE_INFINITY;
/* 397 */     this._zmin = Double.NEGATIVE_INFINITY;
/* 398 */     this._xmax = Double.POSITIVE_INFINITY;
/* 399 */     this._ymax = Double.POSITIVE_INFINITY;
/* 400 */     this._zmax = Double.POSITIVE_INFINITY;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/BoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */