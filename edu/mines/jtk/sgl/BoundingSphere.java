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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoundingSphere
/*     */ {
/*     */   private double _x;
/*     */   private double _y;
/*     */   private double _z;
/*     */   private double _r;
/*     */   
/*     */   public BoundingSphere() {
/* 426 */     this._x = 0.0D;
/* 427 */     this._y = 0.0D;
/* 428 */     this._z = 0.0D;
/* 429 */     this._r = -1.0D; setEmpty(); } public BoundingSphere(double x, double y, double z, double r) { this._x = 0.0D; this._y = 0.0D; this._z = 0.0D; this._r = -1.0D; Check.argument((r >= 0.0D), "r>=0.0"); this._x = x; this._y = y; this._z = z; this._r = r; } public BoundingSphere(Point3 c, double r) { this(c.x, c.y, c.z, r); } public BoundingSphere(BoundingBox bb) { this(); expandBy(bb); } public boolean isEmpty() { return (this._r < 0.0D); } public boolean isInfinite() { return (this._r == Double.POSITIVE_INFINITY); } public Point3 getCenter() { Check.state(!isEmpty(), "bounding sphere is not empty"); Check.state(!isInfinite(), "bounding sphere is not infinite"); return new Point3(this._x, this._y, this._z); } public double getRadius() { Check.state(!isEmpty(), "bounding sphere is not empty"); return this._r; } public double getRadiusSquared() { Check.state(!isEmpty(), "bounding sphere is not empty"); return this._r * this._r; } public BoundingSphere(BoundingSphere bs) { this._x = 0.0D; this._y = 0.0D; this._z = 0.0D; this._r = -1.0D; this._x = bs._x; this._y = bs._y; this._z = bs._z; this._r = bs._r; }
/*     */   public void expandBy(double x, double y, double z) { if (!isInfinite()) if (!isEmpty()) { double dx = x - this._x; double dy = y - this._y; double dz = z - this._z; double d = Math.sqrt(dx * dx + dy * dy + dz * dz); if (d > this._r) { double dr = 0.5D * (d - this._r); double ds = dr / d; this._x += dx * ds; this._y += dy * ds; this._z += dz * ds; this._r += dr; }  } else { this._x = x; this._y = y; this._z = z; this._r = 0.0D; }   }
/*     */   public void expandRadiusBy(double x, double y, double z) { if (!isInfinite())
/*     */       if (!isEmpty()) { double dx = x - this._x; double dy = y - this._y; double dz = z - this._z; double d = Math.sqrt(dx * dx + dy * dy + dz * dz); if (d > this._r)
/*     */           this._r = d;  } else { this._x = x; this._y = y; this._z = z; this._r = 0.0D; }   }
/*     */   public void expandBy(Point3 p) { expandBy(p.x, p.y, p.z); }
/* 435 */   public void expandRadiusBy(Point3 p) { expandRadiusBy(p.x, p.y, p.z); } private void setEmpty() { this._x = 0.0D;
/* 436 */     this._y = 0.0D;
/* 437 */     this._z = 0.0D;
/* 438 */     this._r = -1.0D; } public void expandBy(BoundingSphere bs) { if (!isInfinite())
/*     */       if (!bs.isInfinite()) { if (!bs.isEmpty())
/*     */           if (!isEmpty()) { double dx = bs._x - this._x; double dy = bs._y - this._y; double dz = bs._z - this._z; double d = Math.sqrt(dx * dx + dy * dy + dz * dz); if (d == 0.0D && bs._r > this._r) { this._r = bs._r; } else if (d + bs._r > this._r) { double da = this._r / d; double xa = this._x - dx * da; double ya = this._y - dy * da; double za = this._z - dz * da; double db = bs._r / d; double xb = bs._x + dx * db; double yb = bs._y + dy * db; double zb = bs._z + dz * db; dx = xb - this._x; dy = yb - this._y; dz = zb - this._z; this._r = Math.sqrt(dx * dx + dy * dy + dz * dz); this._x = 0.5D * (xa + xb); this._y = 0.5D * (ya + yb); this._z = 0.5D * (za + zb); }  } else { this._r = bs._r; this._x = bs._x; this._y = bs._y; this._z = bs._z; }   } else { setInfinite(); }   }
/*     */   public void expandRadiusBy(BoundingSphere bs) { if (!isInfinite())
/*     */       if (!bs.isInfinite()) { if (!bs.isEmpty())
/*     */           if (!isEmpty()) { double dx = bs._x - this._x; double dy = bs._y - this._y; double dz = bs._z - this._z; double d = Math.sqrt(dx * dx + dy * dy + dz * dz); double r = d + bs._r; if (r > this._r)
/*     */               this._r = r;  } else { this._r = bs._r; this._x = bs._x; this._y = bs._y; this._z = bs._z; }   } else { setInfinite(); }   }
/* 445 */   private void setInfinite() { this._x = 0.0D;
/* 446 */     this._y = 0.0D;
/* 447 */     this._z = 0.0D;
/* 448 */     this._r = Double.POSITIVE_INFINITY; }
/*     */ 
/*     */   
/*     */   public void expandBy(BoundingBox bb) {
/*     */     if (!isInfinite())
/*     */       if (!bb.isInfinite()) {
/*     */         if (!bb.isEmpty()) {
/*     */           Point3 pmin = bb.getMin();
/*     */           Point3 pmax = bb.getMax();
/*     */           double xmin = pmin.x;
/*     */           double ymin = pmin.y;
/*     */           double zmin = pmin.z;
/*     */           double xmax = pmax.x;
/*     */           double ymax = pmax.y;
/*     */           double zmax = pmax.z;
/*     */           if (!isEmpty())
/*     */             for (int i = 0; i < 8; i++) {
/*     */               double x = ((i & 0x1) == 0) ? xmin : xmax;
/*     */               double y = ((i & 0x2) == 0) ? ymin : ymax;
/*     */               double z = ((i & 0x4) == 0) ? zmin : zmax;
/*     */               double d1 = x - this._x;
/*     */               double d2 = y - this._y;
/*     */               double d3 = z - this._z;
/*     */               double d = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */               double ds = (d > 0.0D) ? (this._r / d) : this._r;
/*     */               x = this._x - d1 * ds;
/*     */               y = this._y - d2 * ds;
/*     */               z = this._z - d3 * ds;
/*     */               if (x < xmin)
/*     */                 xmin = x; 
/*     */               if (y < ymin)
/*     */                 ymin = y; 
/*     */               if (z < zmin)
/*     */                 zmin = z; 
/*     */               if (x > xmax)
/*     */                 xmax = x; 
/*     */               if (y > ymax)
/*     */                 ymax = y; 
/*     */               if (z > zmax)
/*     */                 zmax = z; 
/*     */             }  
/*     */           double dx = xmax - xmin;
/*     */           double dy = ymax - ymin;
/*     */           double dz = zmax - zmin;
/*     */           this._r = 0.5D * Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */           this._x = 0.5D * (xmin + xmax);
/*     */           this._y = 0.5D * (ymin + ymax);
/*     */           this._z = 0.5D * (zmin + zmax);
/*     */         } 
/*     */       } else {
/*     */         setInfinite();
/*     */       }  
/*     */   }
/*     */   
/*     */   public void expandRadiusBy(BoundingBox bb) {
/*     */     if (!isInfinite())
/*     */       if (!bb.isInfinite()) {
/*     */         if (!bb.isEmpty()) {
/*     */           Point3 pmin = bb.getMin();
/*     */           Point3 pmax = bb.getMax();
/*     */           double xmin = pmin.x;
/*     */           double ymin = pmin.y;
/*     */           double zmin = pmin.z;
/*     */           double xmax = pmax.x;
/*     */           double ymax = pmax.y;
/*     */           double zmax = pmax.z;
/*     */           if (!isEmpty()) {
/*     */             for (int i = 0; i < 8; i++) {
/*     */               double x = ((i & 0x1) == 0) ? xmin : xmax;
/*     */               double y = ((i & 0x2) == 0) ? ymin : ymax;
/*     */               double z = ((i & 0x4) == 0) ? zmin : zmax;
/*     */               expandRadiusBy(x, y, z);
/*     */             } 
/*     */           } else {
/*     */             double dx = xmax - xmin;
/*     */             double dy = ymax - ymin;
/*     */             double dz = zmax - zmin;
/*     */             this._r = 0.5D * Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */             this._x = 0.5D * (xmin + xmax);
/*     */             this._y = 0.5D * (ymin + ymax);
/*     */             this._z = 0.5D * (zmin + zmax);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         setInfinite();
/*     */       }  
/*     */   }
/*     */   
/*     */   public boolean contains(double x, double y, double z) {
/*     */     if (isEmpty())
/*     */       return false; 
/*     */     if (isInfinite())
/*     */       return true; 
/*     */     double dx = this._x - x;
/*     */     double dy = this._y - y;
/*     */     double dz = this._z - z;
/*     */     double rs = this._r * this._r;
/*     */     return (dx * dx + dy * dy + dz * dz <= rs);
/*     */   }
/*     */   
/*     */   public boolean contains(Point3 p) {
/*     */     return contains(p.x, p.y, p.z);
/*     */   }
/*     */   
/*     */   public static BoundingSphere empty() {
/*     */     return new BoundingSphere();
/*     */   }
/*     */   
/*     */   public static BoundingSphere infinite() {
/*     */     BoundingSphere bs = new BoundingSphere();
/*     */     bs.setInfinite();
/*     */     return bs;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return "{" + getCenter() + ":" + getRadius() + "}";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/BoundingSphere.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */