/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*     */ import org.apache.commons.math3.geometry.partitioning.Embedding;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Line
/*     */   implements Embedding<Euclidean3D, Euclidean1D>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   private Vector3D direction;
/*     */   private Vector3D zero;
/*     */   private final double tolerance;
/*     */   
/*     */   public Line(Vector3D p1, Vector3D p2, double tolerance) throws MathIllegalArgumentException {
/*  64 */     reset(p1, p2);
/*  65 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line(Line line) {
/*  74 */     this.direction = line.direction;
/*  75 */     this.zero = line.zero;
/*  76 */     this.tolerance = line.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Line(Vector3D p1, Vector3D p2) throws MathIllegalArgumentException {
/*  87 */     this(p1, p2, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(Vector3D p1, Vector3D p2) throws MathIllegalArgumentException {
/*  96 */     Vector3D delta = p2.subtract(p1);
/*  97 */     double norm2 = delta.getNormSq();
/*  98 */     if (norm2 == 0.0D) {
/*  99 */       throw new MathIllegalArgumentException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/* 101 */     this.direction = new Vector3D(1.0D / FastMath.sqrt(norm2), delta);
/* 102 */     this.zero = new Vector3D(1.0D, p1, -p1.dotProduct(delta) / norm2, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 110 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line revert() {
/* 117 */     Line reverted = new Line(this);
/* 118 */     reverted.direction = reverted.direction.negate();
/* 119 */     return reverted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getDirection() {
/* 126 */     return this.direction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getOrigin() {
/* 133 */     return this.zero;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAbscissa(Vector3D point) {
/* 144 */     return point.subtract(this.zero).dotProduct(this.direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D pointAt(double abscissa) {
/* 152 */     return new Vector3D(1.0D, this.zero, abscissa, this.direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D toSubSpace(Vector<Euclidean3D> vector) {
/* 161 */     return toSubSpace((Point<Euclidean3D>)vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D toSpace(Vector<Euclidean1D> vector) {
/* 170 */     return toSpace((Point<Euclidean1D>)vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D toSubSpace(Point<Euclidean3D> point) {
/* 177 */     return new Vector1D(getAbscissa((Vector3D)point));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D toSpace(Point<Euclidean1D> point) {
/* 184 */     return pointAt(((Vector1D)point).getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSimilarTo(Line line) {
/* 195 */     double angle = Vector3D.angle(this.direction, line.direction);
/* 196 */     return ((angle < this.tolerance || angle > Math.PI - this.tolerance) && contains(line.zero));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Vector3D p) {
/* 204 */     return (distance(p) < this.tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance(Vector3D p) {
/* 212 */     Vector3D d = p.subtract(this.zero);
/* 213 */     Vector3D n = new Vector3D(1.0D, d, -d.dotProduct(this.direction), this.direction);
/* 214 */     return n.getNorm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance(Line line) {
/* 223 */     Vector3D normal = Vector3D.crossProduct(this.direction, line.direction);
/* 224 */     double n = normal.getNorm();
/* 225 */     if (n < Precision.SAFE_MIN)
/*     */     {
/* 227 */       return distance(line.zero);
/*     */     }
/*     */ 
/*     */     
/* 231 */     double offset = line.zero.subtract(this.zero).dotProduct(normal) / n;
/*     */     
/* 233 */     return FastMath.abs(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D closestPoint(Line line) {
/* 243 */     double cos = this.direction.dotProduct(line.direction);
/* 244 */     double n = 1.0D - cos * cos;
/* 245 */     if (n < Precision.EPSILON)
/*     */     {
/* 247 */       return this.zero;
/*     */     }
/*     */     
/* 250 */     Vector3D delta0 = line.zero.subtract(this.zero);
/* 251 */     double a = delta0.dotProduct(this.direction);
/* 252 */     double b = delta0.dotProduct(line.direction);
/*     */     
/* 254 */     return new Vector3D(1.0D, this.zero, (a - b * cos) / n, this.direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D intersection(Line line) {
/* 264 */     Vector3D closest = closestPoint(line);
/* 265 */     return line.contains(closest) ? closest : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubLine wholeLine() {
/* 272 */     return new SubLine(this, new IntervalsSet(this.tolerance));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/Line.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */