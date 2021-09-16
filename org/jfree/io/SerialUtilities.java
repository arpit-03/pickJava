/*     */ package org.jfree.io;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.AttributedString;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialUtilities
/*     */ {
/*     */   public static boolean isSerializable(Class<?> c) {
/* 109 */     return Serializable.class.isAssignableFrom(c);
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
/*     */   public static Paint readPaint(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 126 */     if (stream == null) {
/* 127 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 129 */     Paint result = null;
/* 130 */     boolean isNull = stream.readBoolean();
/* 131 */     if (!isNull) {
/* 132 */       Class c = (Class)stream.readObject();
/* 133 */       if (isSerializable(c)) {
/* 134 */         result = (Paint)stream.readObject();
/*     */       }
/* 136 */       else if (c.equals(GradientPaint.class)) {
/* 137 */         float x1 = stream.readFloat();
/* 138 */         float y1 = stream.readFloat();
/* 139 */         Color c1 = (Color)stream.readObject();
/* 140 */         float x2 = stream.readFloat();
/* 141 */         float y2 = stream.readFloat();
/* 142 */         Color c2 = (Color)stream.readObject();
/* 143 */         boolean isCyclic = stream.readBoolean();
/* 144 */         result = new GradientPaint(x1, y1, c1, x2, y2, c2, isCyclic);
/*     */       } 
/*     */     } 
/* 147 */     return result;
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
/*     */   public static void writePaint(Paint paint, ObjectOutputStream stream) throws IOException {
/* 163 */     if (stream == null) {
/* 164 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 166 */     if (paint != null) {
/* 167 */       stream.writeBoolean(false);
/* 168 */       stream.writeObject(paint.getClass());
/* 169 */       if (paint instanceof Serializable) {
/* 170 */         stream.writeObject(paint);
/*     */       }
/* 172 */       else if (paint instanceof GradientPaint) {
/* 173 */         GradientPaint gp = (GradientPaint)paint;
/* 174 */         stream.writeFloat((float)gp.getPoint1().getX());
/* 175 */         stream.writeFloat((float)gp.getPoint1().getY());
/* 176 */         stream.writeObject(gp.getColor1());
/* 177 */         stream.writeFloat((float)gp.getPoint2().getX());
/* 178 */         stream.writeFloat((float)gp.getPoint2().getY());
/* 179 */         stream.writeObject(gp.getColor2());
/* 180 */         stream.writeBoolean(gp.isCyclic());
/*     */       } 
/*     */     } else {
/*     */       
/* 184 */       stream.writeBoolean(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Stroke readStroke(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 203 */     if (stream == null) {
/* 204 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 206 */     Stroke result = null;
/* 207 */     boolean isNull = stream.readBoolean();
/* 208 */     if (!isNull) {
/* 209 */       Class c = (Class)stream.readObject();
/* 210 */       if (c.equals(BasicStroke.class)) {
/* 211 */         float width = stream.readFloat();
/* 212 */         int cap = stream.readInt();
/* 213 */         int join = stream.readInt();
/* 214 */         float miterLimit = stream.readFloat();
/* 215 */         float[] dash = (float[])stream.readObject();
/* 216 */         float dashPhase = stream.readFloat();
/* 217 */         result = new BasicStroke(width, cap, join, miterLimit, dash, dashPhase);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 222 */         result = (Stroke)stream.readObject();
/*     */       } 
/*     */     } 
/* 225 */     return result;
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
/*     */   public static void writeStroke(Stroke stroke, ObjectOutputStream stream) throws IOException {
/* 244 */     if (stream == null) {
/* 245 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 247 */     if (stroke != null) {
/* 248 */       stream.writeBoolean(false);
/* 249 */       if (stroke instanceof BasicStroke) {
/* 250 */         BasicStroke s = (BasicStroke)stroke;
/* 251 */         stream.writeObject(BasicStroke.class);
/* 252 */         stream.writeFloat(s.getLineWidth());
/* 253 */         stream.writeInt(s.getEndCap());
/* 254 */         stream.writeInt(s.getLineJoin());
/* 255 */         stream.writeFloat(s.getMiterLimit());
/* 256 */         stream.writeObject(s.getDashArray());
/* 257 */         stream.writeFloat(s.getDashPhase());
/*     */       } else {
/*     */         
/* 260 */         stream.writeObject(stroke.getClass());
/* 261 */         stream.writeObject(stroke);
/*     */       } 
/*     */     } else {
/*     */       
/* 265 */       stream.writeBoolean(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Composite readComposite(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 286 */     if (stream == null) {
/* 287 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 289 */     Composite result = null;
/* 290 */     boolean isNull = stream.readBoolean();
/* 291 */     if (!isNull) {
/* 292 */       Class c = (Class)stream.readObject();
/* 293 */       if (isSerializable(c)) {
/* 294 */         result = (Composite)stream.readObject();
/*     */       }
/* 296 */       else if (c.equals(AlphaComposite.class)) {
/* 297 */         int rule = stream.readInt();
/* 298 */         float alpha = stream.readFloat();
/* 299 */         result = AlphaComposite.getInstance(rule, alpha);
/*     */       } 
/*     */     } 
/* 302 */     return result;
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
/*     */   public static void writeComposite(Composite composite, ObjectOutputStream stream) throws IOException {
/* 320 */     if (stream == null) {
/* 321 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 323 */     if (composite != null) {
/* 324 */       stream.writeBoolean(false);
/* 325 */       stream.writeObject(composite.getClass());
/* 326 */       if (composite instanceof Serializable) {
/* 327 */         stream.writeObject(composite);
/*     */       }
/* 329 */       else if (composite instanceof AlphaComposite) {
/* 330 */         AlphaComposite ac = (AlphaComposite)composite;
/* 331 */         stream.writeInt(ac.getRule());
/* 332 */         stream.writeFloat(ac.getAlpha());
/*     */       } 
/*     */     } else {
/*     */       
/* 336 */       stream.writeBoolean(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape readShape(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 354 */     if (stream == null) {
/* 355 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 357 */     Shape result = null;
/* 358 */     boolean isNull = stream.readBoolean();
/* 359 */     if (!isNull) {
/* 360 */       Class c = (Class)stream.readObject();
/* 361 */       if (c.equals(Line2D.class)) {
/* 362 */         double x1 = stream.readDouble();
/* 363 */         double y1 = stream.readDouble();
/* 364 */         double x2 = stream.readDouble();
/* 365 */         double y2 = stream.readDouble();
/* 366 */         result = new Line2D.Double(x1, y1, x2, y2);
/*     */       }
/* 368 */       else if (c.equals(Rectangle2D.class)) {
/* 369 */         double x = stream.readDouble();
/* 370 */         double y = stream.readDouble();
/* 371 */         double w = stream.readDouble();
/* 372 */         double h = stream.readDouble();
/* 373 */         result = new Rectangle2D.Double(x, y, w, h);
/*     */       }
/* 375 */       else if (c.equals(Ellipse2D.class)) {
/* 376 */         double x = stream.readDouble();
/* 377 */         double y = stream.readDouble();
/* 378 */         double w = stream.readDouble();
/* 379 */         double h = stream.readDouble();
/* 380 */         result = new Ellipse2D.Double(x, y, w, h);
/*     */       }
/* 382 */       else if (c.equals(Arc2D.class)) {
/* 383 */         double x = stream.readDouble();
/* 384 */         double y = stream.readDouble();
/* 385 */         double w = stream.readDouble();
/* 386 */         double h = stream.readDouble();
/* 387 */         double as = stream.readDouble();
/* 388 */         double ae = stream.readDouble();
/* 389 */         int at = stream.readInt();
/* 390 */         result = new Arc2D.Double(x, y, w, h, as, ae, at);
/*     */       }
/* 392 */       else if (c.equals(GeneralPath.class)) {
/* 393 */         GeneralPath gp = new GeneralPath();
/* 394 */         float[] args = new float[6];
/* 395 */         boolean hasNext = stream.readBoolean();
/* 396 */         while (!hasNext) {
/* 397 */           int type = stream.readInt();
/* 398 */           for (int i = 0; i < 6; i++) {
/* 399 */             args[i] = stream.readFloat();
/*     */           }
/* 401 */           switch (type) {
/*     */             case 0:
/* 403 */               gp.moveTo(args[0], args[1]);
/*     */               break;
/*     */             case 1:
/* 406 */               gp.lineTo(args[0], args[1]);
/*     */               break;
/*     */             case 3:
/* 409 */               gp.curveTo(args[0], args[1], args[2], args[3], args[4], args[5]);
/*     */               break;
/*     */             
/*     */             case 2:
/* 413 */               gp.quadTo(args[0], args[1], args[2], args[3]);
/*     */               break;
/*     */             case 4:
/* 416 */               gp.closePath();
/*     */               break;
/*     */             default:
/* 419 */               throw new RuntimeException("JFreeChart - No path exists");
/*     */           } 
/*     */           
/* 422 */           gp.setWindingRule(stream.readInt());
/* 423 */           hasNext = stream.readBoolean();
/*     */         } 
/* 425 */         result = gp;
/*     */       } else {
/*     */         
/* 428 */         result = (Shape)stream.readObject();
/*     */       } 
/*     */     } 
/* 431 */     return result;
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
/*     */   public static void writeShape(Shape shape, ObjectOutputStream stream) throws IOException {
/* 447 */     if (stream == null) {
/* 448 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 450 */     if (shape != null) {
/* 451 */       stream.writeBoolean(false);
/* 452 */       if (shape instanceof Line2D) {
/* 453 */         Line2D line = (Line2D)shape;
/* 454 */         stream.writeObject(Line2D.class);
/* 455 */         stream.writeDouble(line.getX1());
/* 456 */         stream.writeDouble(line.getY1());
/* 457 */         stream.writeDouble(line.getX2());
/* 458 */         stream.writeDouble(line.getY2());
/*     */       }
/* 460 */       else if (shape instanceof Rectangle2D) {
/* 461 */         Rectangle2D rectangle = (Rectangle2D)shape;
/* 462 */         stream.writeObject(Rectangle2D.class);
/* 463 */         stream.writeDouble(rectangle.getX());
/* 464 */         stream.writeDouble(rectangle.getY());
/* 465 */         stream.writeDouble(rectangle.getWidth());
/* 466 */         stream.writeDouble(rectangle.getHeight());
/*     */       }
/* 468 */       else if (shape instanceof Ellipse2D) {
/* 469 */         Ellipse2D ellipse = (Ellipse2D)shape;
/* 470 */         stream.writeObject(Ellipse2D.class);
/* 471 */         stream.writeDouble(ellipse.getX());
/* 472 */         stream.writeDouble(ellipse.getY());
/* 473 */         stream.writeDouble(ellipse.getWidth());
/* 474 */         stream.writeDouble(ellipse.getHeight());
/*     */       }
/* 476 */       else if (shape instanceof Arc2D) {
/* 477 */         Arc2D arc = (Arc2D)shape;
/* 478 */         stream.writeObject(Arc2D.class);
/* 479 */         stream.writeDouble(arc.getX());
/* 480 */         stream.writeDouble(arc.getY());
/* 481 */         stream.writeDouble(arc.getWidth());
/* 482 */         stream.writeDouble(arc.getHeight());
/* 483 */         stream.writeDouble(arc.getAngleStart());
/* 484 */         stream.writeDouble(arc.getAngleExtent());
/* 485 */         stream.writeInt(arc.getArcType());
/*     */       }
/* 487 */       else if (shape instanceof GeneralPath) {
/* 488 */         stream.writeObject(GeneralPath.class);
/* 489 */         PathIterator pi = shape.getPathIterator(null);
/* 490 */         float[] args = new float[6];
/* 491 */         stream.writeBoolean(pi.isDone());
/* 492 */         while (!pi.isDone()) {
/* 493 */           int type = pi.currentSegment(args);
/* 494 */           stream.writeInt(type);
/*     */ 
/*     */           
/* 497 */           for (int i = 0; i < 6; i++) {
/* 498 */             stream.writeFloat(args[i]);
/*     */           }
/* 500 */           stream.writeInt(pi.getWindingRule());
/* 501 */           pi.next();
/* 502 */           stream.writeBoolean(pi.isDone());
/*     */         } 
/*     */       } else {
/*     */         
/* 506 */         stream.writeObject(shape.getClass());
/* 507 */         stream.writeObject(shape);
/*     */       } 
/*     */     } else {
/*     */       
/* 511 */       stream.writeBoolean(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Point2D readPoint2D(ObjectInputStream stream) throws IOException {
/* 528 */     if (stream == null) {
/* 529 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 531 */     Point2D result = null;
/* 532 */     boolean isNull = stream.readBoolean();
/* 533 */     if (!isNull) {
/* 534 */       double x = stream.readDouble();
/* 535 */       double y = stream.readDouble();
/* 536 */       result = new Point2D.Double(x, y);
/*     */     } 
/* 538 */     return result;
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
/*     */   public static void writePoint2D(Point2D p, ObjectOutputStream stream) throws IOException {
/* 554 */     if (stream == null) {
/* 555 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 557 */     if (p != null) {
/* 558 */       stream.writeBoolean(false);
/* 559 */       stream.writeDouble(p.getX());
/* 560 */       stream.writeDouble(p.getY());
/*     */     } else {
/*     */       
/* 563 */       stream.writeBoolean(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributedString readAttributedString(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 583 */     if (stream == null) {
/* 584 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 586 */     AttributedString result = null;
/* 587 */     boolean isNull = stream.readBoolean();
/* 588 */     if (!isNull) {
/*     */       
/* 590 */       String plainStr = (String)stream.readObject();
/* 591 */       result = new AttributedString(plainStr);
/* 592 */       char c = stream.readChar();
/* 593 */       int start = 0;
/* 594 */       while (c != Character.MAX_VALUE) {
/* 595 */         int limit = stream.readInt();
/* 596 */         Map<? extends AttributedCharacterIterator.Attribute, ?> atts = (Map)stream.readObject();
/* 597 */         result.addAttributes(atts, start, limit);
/* 598 */         start = limit;
/* 599 */         c = stream.readChar();
/*     */       } 
/*     */     } 
/* 602 */     return result;
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
/*     */   public static void writeAttributedString(AttributedString as, ObjectOutputStream stream) throws IOException {
/* 616 */     if (stream == null) {
/* 617 */       throw new IllegalArgumentException("Null 'stream' argument.");
/*     */     }
/* 619 */     if (as != null) {
/* 620 */       stream.writeBoolean(false);
/* 621 */       AttributedCharacterIterator aci = as.getIterator();
/*     */ 
/*     */       
/* 624 */       StringBuffer plainStr = new StringBuffer();
/* 625 */       char current = aci.first();
/* 626 */       while (current != Character.MAX_VALUE) {
/* 627 */         plainStr = plainStr.append(current);
/* 628 */         current = aci.next();
/*     */       } 
/* 630 */       stream.writeObject(plainStr.toString());
/*     */ 
/*     */       
/* 633 */       current = aci.first();
/* 634 */       int begin = aci.getBeginIndex();
/* 635 */       while (current != Character.MAX_VALUE) {
/*     */ 
/*     */ 
/*     */         
/* 639 */         stream.writeChar(current);
/*     */ 
/*     */         
/* 642 */         int limit = aci.getRunLimit();
/* 643 */         stream.writeInt(limit - begin);
/*     */ 
/*     */         
/* 646 */         Map<AttributedCharacterIterator.Attribute, Object> atts = new HashMap<AttributedCharacterIterator.Attribute, Object>(aci.getAttributes());
/* 647 */         stream.writeObject(atts);
/* 648 */         current = aci.setIndex(limit);
/*     */       } 
/*     */ 
/*     */       
/* 652 */       stream.writeChar(65535);
/*     */     }
/*     */     else {
/*     */       
/* 656 */       stream.writeBoolean(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/io/SerialUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */