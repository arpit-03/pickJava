/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.OrientedPoint;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*     */ import org.apache.commons.math3.geometry.partitioning.Embedding;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Transform;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements Hyperplane<Euclidean2D>, Embedding<Euclidean2D, Euclidean1D>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   private double angle;
/*     */   private double cos;
/*     */   private double sin;
/*     */   private double originOffset;
/*     */   private final double tolerance;
/*     */   private Line reverse;
/*     */   
/*     */   public Line(Vector2D p1, Vector2D p2, double tolerance) {
/*  94 */     reset(p1, p2);
/*  95 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line(Vector2D p, double angle, double tolerance) {
/* 105 */     reset(p, angle);
/* 106 */     this.tolerance = tolerance;
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
/*     */   private Line(double angle, double cos, double sin, double originOffset, double tolerance) {
/* 119 */     this.angle = angle;
/* 120 */     this.cos = cos;
/* 121 */     this.sin = sin;
/* 122 */     this.originOffset = originOffset;
/* 123 */     this.tolerance = tolerance;
/* 124 */     this.reverse = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Line(Vector2D p1, Vector2D p2) {
/* 135 */     this(p1, p2, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Line(Vector2D p, double angle) {
/* 145 */     this(p, angle, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line(Line line) {
/* 154 */     this.angle = MathUtils.normalizeAngle(line.angle, Math.PI);
/* 155 */     this.cos = line.cos;
/* 156 */     this.sin = line.sin;
/* 157 */     this.originOffset = line.originOffset;
/* 158 */     this.tolerance = line.tolerance;
/* 159 */     this.reverse = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Line copySelf() {
/* 164 */     return new Line(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(Vector2D p1, Vector2D p2) {
/* 173 */     unlinkReverse();
/* 174 */     double dx = p2.getX() - p1.getX();
/* 175 */     double dy = p2.getY() - p1.getY();
/* 176 */     double d = FastMath.hypot(dx, dy);
/* 177 */     if (d == 0.0D) {
/* 178 */       this.angle = 0.0D;
/* 179 */       this.cos = 1.0D;
/* 180 */       this.sin = 0.0D;
/* 181 */       this.originOffset = p1.getY();
/*     */     } else {
/* 183 */       this.angle = Math.PI + FastMath.atan2(-dy, -dx);
/* 184 */       this.cos = dx / d;
/* 185 */       this.sin = dy / d;
/* 186 */       this.originOffset = MathArrays.linearCombination(p2.getX(), p1.getY(), -p1.getX(), p2.getY()) / d;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(Vector2D p, double alpha) {
/* 195 */     unlinkReverse();
/* 196 */     this.angle = MathUtils.normalizeAngle(alpha, Math.PI);
/* 197 */     this.cos = FastMath.cos(this.angle);
/* 198 */     this.sin = FastMath.sin(this.angle);
/* 199 */     this.originOffset = MathArrays.linearCombination(this.cos, p.getY(), -this.sin, p.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void revertSelf() {
/* 205 */     unlinkReverse();
/* 206 */     if (this.angle < Math.PI) {
/* 207 */       this.angle += Math.PI;
/*     */     } else {
/* 209 */       this.angle -= Math.PI;
/*     */     } 
/* 211 */     this.cos = -this.cos;
/* 212 */     this.sin = -this.sin;
/* 213 */     this.originOffset = -this.originOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unlinkReverse() {
/* 219 */     if (this.reverse != null) {
/* 220 */       this.reverse.reverse = null;
/*     */     }
/* 222 */     this.reverse = null;
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
/*     */   public Line getReverse() {
/* 241 */     if (this.reverse == null) {
/* 242 */       this.reverse = new Line((this.angle < Math.PI) ? (this.angle + Math.PI) : (this.angle - Math.PI), -this.cos, -this.sin, -this.originOffset, this.tolerance);
/*     */       
/* 244 */       this.reverse.reverse = this;
/*     */     } 
/* 246 */     return this.reverse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D toSubSpace(Vector<Euclidean2D> vector) {
/* 255 */     return toSubSpace((Point<Euclidean2D>)vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D toSpace(Vector<Euclidean1D> vector) {
/* 264 */     return toSpace((Point<Euclidean1D>)vector);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D toSubSpace(Point<Euclidean2D> point) {
/* 269 */     Vector2D p2 = (Vector2D)point;
/* 270 */     return new Vector1D(MathArrays.linearCombination(this.cos, p2.getX(), this.sin, p2.getY()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D toSpace(Point<Euclidean1D> point) {
/* 275 */     double abscissa = ((Vector1D)point).getX();
/* 276 */     return new Vector2D(MathArrays.linearCombination(abscissa, this.cos, -this.originOffset, this.sin), MathArrays.linearCombination(abscissa, this.sin, this.originOffset, this.cos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D intersection(Line other) {
/* 286 */     double d = MathArrays.linearCombination(this.sin, other.cos, -other.sin, this.cos);
/* 287 */     if (FastMath.abs(d) < this.tolerance) {
/* 288 */       return null;
/*     */     }
/* 290 */     return new Vector2D(MathArrays.linearCombination(this.cos, other.originOffset, -other.cos, this.originOffset) / d, MathArrays.linearCombination(this.sin, other.originOffset, -other.sin, this.originOffset) / d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point<Euclidean2D> project(Point<Euclidean2D> point) {
/* 298 */     return (Point<Euclidean2D>)toSpace((Vector<Euclidean1D>)toSubSpace(point));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 305 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubLine wholeHyperplane() {
/* 310 */     return new SubLine(this, (Region<Euclidean1D>)new IntervalsSet(this.tolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolygonsSet wholeSpace() {
/* 318 */     return new PolygonsSet(this.tolerance);
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
/*     */   public double getOffset(Line line) {
/* 332 */     return this.originOffset + ((MathArrays.linearCombination(this.cos, line.cos, this.sin, line.sin) > 0.0D) ? -line.originOffset : line.originOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOffset(Vector<Euclidean2D> vector) {
/* 341 */     return getOffset((Point<Euclidean2D>)vector);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOffset(Point<Euclidean2D> point) {
/* 346 */     Vector2D p2 = (Vector2D)point;
/* 347 */     return MathArrays.linearCombination(this.sin, p2.getX(), -this.cos, p2.getY(), 1.0D, this.originOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sameOrientationAs(Hyperplane<Euclidean2D> other) {
/* 352 */     Line otherL = (Line)other;
/* 353 */     return (MathArrays.linearCombination(this.sin, otherL.sin, this.cos, otherL.cos) >= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D getPointAt(Vector1D abscissa, double offset) {
/* 363 */     double x = abscissa.getX();
/* 364 */     double dOffset = offset - this.originOffset;
/* 365 */     return new Vector2D(MathArrays.linearCombination(x, this.cos, dOffset, this.sin), MathArrays.linearCombination(x, this.sin, -dOffset, this.cos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Vector2D p) {
/* 374 */     return (FastMath.abs(getOffset(p)) < this.tolerance);
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
/*     */   public double distance(Vector2D p) {
/* 387 */     return FastMath.abs(getOffset(p));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParallelTo(Line line) {
/* 396 */     return (FastMath.abs(MathArrays.linearCombination(this.sin, line.cos, -this.cos, line.sin)) < this.tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateToPoint(Vector2D p) {
/* 403 */     this.originOffset = MathArrays.linearCombination(this.cos, p.getY(), -this.sin, p.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 410 */     return MathUtils.normalizeAngle(this.angle, Math.PI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAngle(double angle) {
/* 417 */     unlinkReverse();
/* 418 */     this.angle = MathUtils.normalizeAngle(angle, Math.PI);
/* 419 */     this.cos = FastMath.cos(this.angle);
/* 420 */     this.sin = FastMath.sin(this.angle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOriginOffset() {
/* 427 */     return this.originOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOriginOffset(double offset) {
/* 434 */     unlinkReverse();
/* 435 */     this.originOffset = offset;
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
/*     */   @Deprecated
/*     */   public static Transform<Euclidean2D, Euclidean1D> getTransform(AffineTransform transform) throws MathIllegalArgumentException {
/* 455 */     double[] m = new double[6];
/* 456 */     transform.getMatrix(m);
/* 457 */     return new LineTransform(m[0], m[1], m[2], m[3], m[4], m[5]);
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
/*     */   public static Transform<Euclidean2D, Euclidean1D> getTransform(double cXX, double cYX, double cXY, double cYY, double cX1, double cY1) throws MathIllegalArgumentException {
/* 482 */     return new LineTransform(cXX, cYX, cXY, cYY, cX1, cY1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LineTransform
/*     */     implements Transform<Euclidean2D, Euclidean1D>
/*     */   {
/*     */     private double cXX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double cYX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double cXY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double cYY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double cX1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double cY1;
/*     */ 
/*     */ 
/*     */     
/*     */     private double c1Y;
/*     */ 
/*     */ 
/*     */     
/*     */     private double c1X;
/*     */ 
/*     */ 
/*     */     
/*     */     private double c11;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LineTransform(double cXX, double cYX, double cXY, double cYY, double cX1, double cY1) throws MathIllegalArgumentException {
/* 535 */       this.cXX = cXX;
/* 536 */       this.cYX = cYX;
/* 537 */       this.cXY = cXY;
/* 538 */       this.cYY = cYY;
/* 539 */       this.cX1 = cX1;
/* 540 */       this.cY1 = cY1;
/*     */       
/* 542 */       this.c1Y = MathArrays.linearCombination(cXY, cY1, -cYY, cX1);
/* 543 */       this.c1X = MathArrays.linearCombination(cXX, cY1, -cYX, cX1);
/* 544 */       this.c11 = MathArrays.linearCombination(cXX, cYY, -cYX, cXY);
/*     */       
/* 546 */       if (FastMath.abs(this.c11) < 1.0E-20D) {
/* 547 */         throw new MathIllegalArgumentException(LocalizedFormats.NON_INVERTIBLE_TRANSFORM, new Object[0]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Vector2D apply(Point<Euclidean2D> point) {
/* 554 */       Vector2D p2D = (Vector2D)point;
/* 555 */       double x = p2D.getX();
/* 556 */       double y = p2D.getY();
/* 557 */       return new Vector2D(MathArrays.linearCombination(this.cXX, x, this.cXY, y, this.cX1, 1.0D), MathArrays.linearCombination(this.cYX, x, this.cYY, y, this.cY1, 1.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Line apply(Hyperplane<Euclidean2D> hyperplane) {
/* 563 */       Line line = (Line)hyperplane;
/* 564 */       double rOffset = MathArrays.linearCombination(this.c1X, line.cos, this.c1Y, line.sin, this.c11, line.originOffset);
/* 565 */       double rCos = MathArrays.linearCombination(this.cXX, line.cos, this.cXY, line.sin);
/* 566 */       double rSin = MathArrays.linearCombination(this.cYX, line.cos, this.cYY, line.sin);
/* 567 */       double inv = 1.0D / FastMath.sqrt(rSin * rSin + rCos * rCos);
/* 568 */       return new Line(Math.PI + FastMath.atan2(-rSin, -rCos), inv * rCos, inv * rSin, inv * rOffset, line.tolerance);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubHyperplane<Euclidean1D> apply(SubHyperplane<Euclidean1D> sub, Hyperplane<Euclidean2D> original, Hyperplane<Euclidean2D> transformed) {
/* 577 */       OrientedPoint op = (OrientedPoint)sub.getHyperplane();
/* 578 */       Line originalLine = (Line)original;
/* 579 */       Line transformedLine = (Line)transformed;
/* 580 */       Vector1D newLoc = transformedLine.toSubSpace(apply((Point<Euclidean2D>)originalLine.toSpace((Vector<Euclidean1D>)op.getLocation())));
/*     */       
/* 582 */       return (SubHyperplane<Euclidean1D>)(new OrientedPoint(newLoc, op.isDirect(), originalLine.tolerance)).wholeHyperplane();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/Line.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */