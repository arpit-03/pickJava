/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Arrays;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapeUtilities
/*     */ {
/*     */   public static Shape clone(Shape shape) {
/* 108 */     if (shape instanceof Cloneable) {
/*     */       try {
/* 110 */         return (Shape)ObjectUtilities.clone(shape);
/*     */       }
/* 112 */       catch (CloneNotSupportedException cnse) {}
/*     */     }
/*     */     
/* 115 */     Shape result = null;
/* 116 */     return result;
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
/*     */   public static boolean equal(Shape s1, Shape s2) {
/* 133 */     if (s1 instanceof Line2D && s2 instanceof Line2D) {
/* 134 */       return equal((Line2D)s1, (Line2D)s2);
/*     */     }
/* 136 */     if (s1 instanceof Ellipse2D && s2 instanceof Ellipse2D) {
/* 137 */       return equal((Ellipse2D)s1, (Ellipse2D)s2);
/*     */     }
/* 139 */     if (s1 instanceof Arc2D && s2 instanceof Arc2D) {
/* 140 */       return equal((Arc2D)s1, (Arc2D)s2);
/*     */     }
/* 142 */     if (s1 instanceof Polygon && s2 instanceof Polygon) {
/* 143 */       return equal((Polygon)s1, (Polygon)s2);
/*     */     }
/* 145 */     if (s1 instanceof GeneralPath && s2 instanceof GeneralPath) {
/* 146 */       return equal((GeneralPath)s1, (GeneralPath)s2);
/*     */     }
/*     */ 
/*     */     
/* 150 */     return ObjectUtilities.equal(s1, s2);
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
/*     */   public static boolean equal(Line2D l1, Line2D l2) {
/* 164 */     if (l1 == null) {
/* 165 */       return (l2 == null);
/*     */     }
/* 167 */     if (l2 == null) {
/* 168 */       return false;
/*     */     }
/* 170 */     if (!l1.getP1().equals(l2.getP1())) {
/* 171 */       return false;
/*     */     }
/* 173 */     if (!l1.getP2().equals(l2.getP2())) {
/* 174 */       return false;
/*     */     }
/* 176 */     return true;
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
/*     */   public static boolean equal(Ellipse2D e1, Ellipse2D e2) {
/* 189 */     if (e1 == null) {
/* 190 */       return (e2 == null);
/*     */     }
/* 192 */     if (e2 == null) {
/* 193 */       return false;
/*     */     }
/* 195 */     if (!e1.getFrame().equals(e2.getFrame())) {
/* 196 */       return false;
/*     */     }
/* 198 */     return true;
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
/*     */   public static boolean equal(Arc2D a1, Arc2D a2) {
/* 211 */     if (a1 == null) {
/* 212 */       return (a2 == null);
/*     */     }
/* 214 */     if (a2 == null) {
/* 215 */       return false;
/*     */     }
/* 217 */     if (!a1.getFrame().equals(a2.getFrame())) {
/* 218 */       return false;
/*     */     }
/* 220 */     if (a1.getAngleStart() != a2.getAngleStart()) {
/* 221 */       return false;
/*     */     }
/* 223 */     if (a1.getAngleExtent() != a2.getAngleExtent()) {
/* 224 */       return false;
/*     */     }
/* 226 */     if (a1.getArcType() != a2.getArcType()) {
/* 227 */       return false;
/*     */     }
/* 229 */     return true;
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
/*     */   public static boolean equal(Polygon p1, Polygon p2) {
/* 242 */     if (p1 == null) {
/* 243 */       return (p2 == null);
/*     */     }
/* 245 */     if (p2 == null) {
/* 246 */       return false;
/*     */     }
/* 248 */     if (p1.npoints != p2.npoints) {
/* 249 */       return false;
/*     */     }
/* 251 */     if (!Arrays.equals(p1.xpoints, p2.xpoints)) {
/* 252 */       return false;
/*     */     }
/* 254 */     if (!Arrays.equals(p1.ypoints, p2.ypoints)) {
/* 255 */       return false;
/*     */     }
/* 257 */     return true;
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
/*     */   public static boolean equal(GeneralPath p1, GeneralPath p2) {
/* 270 */     if (p1 == null) {
/* 271 */       return (p2 == null);
/*     */     }
/* 273 */     if (p2 == null) {
/* 274 */       return false;
/*     */     }
/* 276 */     if (p1.getWindingRule() != p2.getWindingRule()) {
/* 277 */       return false;
/*     */     }
/* 279 */     PathIterator iterator1 = p1.getPathIterator((AffineTransform)null);
/* 280 */     PathIterator iterator2 = p2.getPathIterator((AffineTransform)null);
/* 281 */     double[] d1 = new double[6];
/* 282 */     double[] d2 = new double[6];
/* 283 */     boolean done = (iterator1.isDone() && iterator2.isDone());
/* 284 */     while (!done) {
/* 285 */       if (iterator1.isDone() != iterator2.isDone()) {
/* 286 */         return false;
/*     */       }
/* 288 */       int seg1 = iterator1.currentSegment(d1);
/* 289 */       int seg2 = iterator2.currentSegment(d2);
/* 290 */       if (seg1 != seg2) {
/* 291 */         return false;
/*     */       }
/* 293 */       if (!Arrays.equals(d1, d2)) {
/* 294 */         return false;
/*     */       }
/* 296 */       iterator1.next();
/* 297 */       iterator2.next();
/* 298 */       done = (iterator1.isDone() && iterator2.isDone());
/*     */     } 
/* 300 */     return true;
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
/*     */   public static Shape createTranslatedShape(Shape shape, double transX, double transY) {
/* 315 */     if (shape == null) {
/* 316 */       throw new IllegalArgumentException("Null 'shape' argument.");
/*     */     }
/* 318 */     AffineTransform transform = AffineTransform.getTranslateInstance(transX, transY);
/*     */     
/* 320 */     return transform.createTransformedShape(shape);
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
/*     */   public static Shape createTranslatedShape(Shape shape, RectangleAnchor anchor, double locationX, double locationY) {
/* 339 */     if (shape == null) {
/* 340 */       throw new IllegalArgumentException("Null 'shape' argument.");
/*     */     }
/* 342 */     if (anchor == null) {
/* 343 */       throw new IllegalArgumentException("Null 'anchor' argument.");
/*     */     }
/* 345 */     Point2D anchorPoint = RectangleAnchor.coordinates(shape
/* 346 */         .getBounds2D(), anchor);
/* 347 */     AffineTransform transform = AffineTransform.getTranslateInstance(locationX - anchorPoint
/* 348 */         .getX(), locationY - anchorPoint.getY());
/* 349 */     return transform.createTransformedShape(shape);
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
/*     */   public static Shape rotateShape(Shape base, double angle, float x, float y) {
/* 365 */     if (base == null) {
/* 366 */       return null;
/*     */     }
/* 368 */     AffineTransform rotate = AffineTransform.getRotateInstance(angle, x, y);
/*     */     
/* 370 */     Shape result = rotate.createTransformedShape(base);
/* 371 */     return result;
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
/*     */   public static void drawRotatedShape(Graphics2D g2, Shape shape, double angle, float x, float y) {
/* 387 */     AffineTransform saved = g2.getTransform();
/* 388 */     AffineTransform rotate = AffineTransform.getRotateInstance(angle, x, y);
/*     */     
/* 390 */     g2.transform(rotate);
/* 391 */     g2.draw(shape);
/* 392 */     g2.setTransform(saved);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 397 */   private static final float SQRT2 = (float)Math.pow(2.0D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape createDiagonalCross(float l, float t) {
/* 408 */     GeneralPath p0 = new GeneralPath();
/* 409 */     p0.moveTo(-l - t, -l + t);
/* 410 */     p0.lineTo(-l + t, -l - t);
/* 411 */     p0.lineTo(0.0F, -t * SQRT2);
/* 412 */     p0.lineTo(l - t, -l - t);
/* 413 */     p0.lineTo(l + t, -l + t);
/* 414 */     p0.lineTo(t * SQRT2, 0.0F);
/* 415 */     p0.lineTo(l + t, l - t);
/* 416 */     p0.lineTo(l - t, l + t);
/* 417 */     p0.lineTo(0.0F, t * SQRT2);
/* 418 */     p0.lineTo(-l + t, l + t);
/* 419 */     p0.lineTo(-l - t, l - t);
/* 420 */     p0.lineTo(-t * SQRT2, 0.0F);
/* 421 */     p0.closePath();
/* 422 */     return p0;
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
/*     */   public static Shape createRegularCross(float l, float t) {
/* 434 */     GeneralPath p0 = new GeneralPath();
/* 435 */     p0.moveTo(-l, t);
/* 436 */     p0.lineTo(-t, t);
/* 437 */     p0.lineTo(-t, l);
/* 438 */     p0.lineTo(t, l);
/* 439 */     p0.lineTo(t, t);
/* 440 */     p0.lineTo(l, t);
/* 441 */     p0.lineTo(l, -t);
/* 442 */     p0.lineTo(t, -t);
/* 443 */     p0.lineTo(t, -l);
/* 444 */     p0.lineTo(-t, -l);
/* 445 */     p0.lineTo(-t, -t);
/* 446 */     p0.lineTo(-l, -t);
/* 447 */     p0.closePath();
/* 448 */     return p0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape createDiamond(float s) {
/* 459 */     GeneralPath p0 = new GeneralPath();
/* 460 */     p0.moveTo(0.0F, -s);
/* 461 */     p0.lineTo(s, 0.0F);
/* 462 */     p0.lineTo(0.0F, s);
/* 463 */     p0.lineTo(-s, 0.0F);
/* 464 */     p0.closePath();
/* 465 */     return p0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape createUpTriangle(float s) {
/* 476 */     GeneralPath p0 = new GeneralPath();
/* 477 */     p0.moveTo(0.0F, -s);
/* 478 */     p0.lineTo(s, s);
/* 479 */     p0.lineTo(-s, s);
/* 480 */     p0.closePath();
/* 481 */     return p0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape createDownTriangle(float s) {
/* 492 */     GeneralPath p0 = new GeneralPath();
/* 493 */     p0.moveTo(0.0F, s);
/* 494 */     p0.lineTo(s, -s);
/* 495 */     p0.lineTo(-s, -s);
/* 496 */     p0.closePath();
/* 497 */     return p0;
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
/*     */   public static Shape createLineRegion(Line2D line, float width) {
/* 511 */     GeneralPath result = new GeneralPath();
/* 512 */     float x1 = (float)line.getX1();
/* 513 */     float x2 = (float)line.getX2();
/* 514 */     float y1 = (float)line.getY1();
/* 515 */     float y2 = (float)line.getY2();
/* 516 */     if ((x2 - x1) != 0.0D) {
/* 517 */       double theta = Math.atan(((y2 - y1) / (x2 - x1)));
/* 518 */       float dx = (float)Math.sin(theta) * width;
/* 519 */       float dy = (float)Math.cos(theta) * width;
/* 520 */       result.moveTo(x1 - dx, y1 + dy);
/* 521 */       result.lineTo(x1 + dx, y1 - dy);
/* 522 */       result.lineTo(x2 + dx, y2 - dy);
/* 523 */       result.lineTo(x2 - dx, y2 + dy);
/* 524 */       result.closePath();
/*     */     }
/*     */     else {
/*     */       
/* 528 */       result.moveTo(x1 - width / 2.0F, y1);
/* 529 */       result.lineTo(x1 + width / 2.0F, y1);
/* 530 */       result.lineTo(x2 + width / 2.0F, y2);
/* 531 */       result.lineTo(x2 - width / 2.0F, y2);
/* 532 */       result.closePath();
/*     */     } 
/* 534 */     return result;
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
/*     */   public static Point2D getPointInRectangle(double x, double y, Rectangle2D area) {
/* 553 */     x = Math.max(area.getMinX(), Math.min(x, area.getMaxX()));
/* 554 */     y = Math.max(area.getMinY(), Math.min(y, area.getMaxY()));
/* 555 */     return new Point2D.Double(x, y);
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
/*     */   public static boolean contains(Rectangle2D rect1, Rectangle2D rect2) {
/* 571 */     double x0 = rect1.getX();
/* 572 */     double y0 = rect1.getY();
/* 573 */     double x = rect2.getX();
/* 574 */     double y = rect2.getY();
/* 575 */     double w = rect2.getWidth();
/* 576 */     double h = rect2.getHeight();
/*     */     
/* 578 */     return (x >= x0 && y >= y0 && x + w <= x0 + rect1
/* 579 */       .getWidth() && y + h <= y0 + rect1
/* 580 */       .getHeight());
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
/*     */   public static boolean intersects(Rectangle2D rect1, Rectangle2D rect2) {
/* 597 */     double x0 = rect1.getX();
/* 598 */     double y0 = rect1.getY();
/*     */     
/* 600 */     double x = rect2.getX();
/* 601 */     double width = rect2.getWidth();
/* 602 */     double y = rect2.getY();
/* 603 */     double height = rect2.getHeight();
/* 604 */     return (x + width >= x0 && y + height >= y0 && x <= x0 + rect1.getWidth() && y <= y0 + rect1
/* 605 */       .getHeight());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ShapeUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */