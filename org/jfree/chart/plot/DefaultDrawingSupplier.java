/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.jfree.chart.ChartColor;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ import org.jfree.util.ShapeUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultDrawingSupplier
/*     */   implements DrawingSupplier, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7339847061039422538L;
/*  83 */   public static final Paint[] DEFAULT_PAINT_SEQUENCE = ChartColor.createDefaultPaintArray();
/*     */ 
/*     */   
/*  86 */   public static final Paint[] DEFAULT_OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.lightGray };
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final Paint[] DEFAULT_FILL_PAINT_SEQUENCE = new Paint[] { Color.white };
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final Stroke[] DEFAULT_STROKE_SEQUENCE = new Stroke[] { new BasicStroke(1.0F, 2, 2) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final Stroke[] DEFAULT_OUTLINE_STROKE_SEQUENCE = new Stroke[] { new BasicStroke(1.0F, 2, 2) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final Shape[] DEFAULT_SHAPE_SEQUENCE = createStandardSeriesShapes();
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint[] paintSequence;
/*     */ 
/*     */ 
/*     */   
/*     */   private int paintIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint[] outlinePaintSequence;
/*     */ 
/*     */   
/*     */   private int outlinePaintIndex;
/*     */ 
/*     */   
/*     */   private transient Paint[] fillPaintSequence;
/*     */ 
/*     */   
/*     */   private int fillPaintIndex;
/*     */ 
/*     */   
/*     */   private transient Stroke[] strokeSequence;
/*     */ 
/*     */   
/*     */   private int strokeIndex;
/*     */ 
/*     */   
/*     */   private transient Stroke[] outlineStrokeSequence;
/*     */ 
/*     */   
/*     */   private int outlineStrokeIndex;
/*     */ 
/*     */   
/*     */   private transient Shape[] shapeSequence;
/*     */ 
/*     */   
/*     */   private int shapeIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDrawingSupplier() {
/* 149 */     this(DEFAULT_PAINT_SEQUENCE, DEFAULT_FILL_PAINT_SEQUENCE, DEFAULT_OUTLINE_PAINT_SEQUENCE, DEFAULT_STROKE_SEQUENCE, DEFAULT_OUTLINE_STROKE_SEQUENCE, DEFAULT_SHAPE_SEQUENCE);
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
/*     */   public DefaultDrawingSupplier(Paint[] paintSequence, Paint[] outlinePaintSequence, Stroke[] strokeSequence, Stroke[] outlineStrokeSequence, Shape[] shapeSequence) {
/* 172 */     this.paintSequence = paintSequence;
/* 173 */     this.fillPaintSequence = DEFAULT_FILL_PAINT_SEQUENCE;
/* 174 */     this.outlinePaintSequence = outlinePaintSequence;
/* 175 */     this.strokeSequence = strokeSequence;
/* 176 */     this.outlineStrokeSequence = outlineStrokeSequence;
/* 177 */     this.shapeSequence = shapeSequence;
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
/*     */   public DefaultDrawingSupplier(Paint[] paintSequence, Paint[] fillPaintSequence, Paint[] outlinePaintSequence, Stroke[] strokeSequence, Stroke[] outlineStrokeSequence, Shape[] shapeSequence) {
/* 198 */     this.paintSequence = paintSequence;
/* 199 */     this.fillPaintSequence = fillPaintSequence;
/* 200 */     this.outlinePaintSequence = outlinePaintSequence;
/* 201 */     this.strokeSequence = strokeSequence;
/* 202 */     this.outlineStrokeSequence = outlineStrokeSequence;
/* 203 */     this.shapeSequence = shapeSequence;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getNextPaint() {
/* 213 */     Paint result = this.paintSequence[this.paintIndex % this.paintSequence.length];
/*     */     
/* 215 */     this.paintIndex++;
/* 216 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getNextOutlinePaint() {
/* 226 */     Paint result = this.outlinePaintSequence[this.outlinePaintIndex % this.outlinePaintSequence.length];
/*     */     
/* 228 */     this.outlinePaintIndex++;
/* 229 */     return result;
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
/*     */   public Paint getNextFillPaint() {
/* 241 */     Paint result = this.fillPaintSequence[this.fillPaintIndex % this.fillPaintSequence.length];
/*     */     
/* 243 */     this.fillPaintIndex++;
/* 244 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getNextStroke() {
/* 254 */     Stroke result = this.strokeSequence[this.strokeIndex % this.strokeSequence.length];
/*     */     
/* 256 */     this.strokeIndex++;
/* 257 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getNextOutlineStroke() {
/* 267 */     Stroke result = this.outlineStrokeSequence[this.outlineStrokeIndex % this.outlineStrokeSequence.length];
/*     */     
/* 269 */     this.outlineStrokeIndex++;
/* 270 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape getNextShape() {
/* 280 */     Shape result = this.shapeSequence[this.shapeIndex % this.shapeSequence.length];
/*     */     
/* 282 */     this.shapeIndex++;
/* 283 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Shape[] createStandardSeriesShapes() {
/* 294 */     Shape[] result = new Shape[10];
/*     */     
/* 296 */     double size = 6.0D;
/* 297 */     double delta = size / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     result[0] = new Rectangle2D.Double(-delta, -delta, size, size);
/*     */     
/* 304 */     result[1] = new Ellipse2D.Double(-delta, -delta, size, size);
/*     */ 
/*     */     
/* 307 */     int[] xpoints = intArray(0.0D, delta, -delta);
/* 308 */     int[] ypoints = intArray(-delta, delta, delta);
/* 309 */     result[2] = new Polygon(xpoints, ypoints, 3);
/*     */ 
/*     */     
/* 312 */     xpoints = intArray(0.0D, delta, 0.0D, -delta);
/* 313 */     ypoints = intArray(-delta, 0.0D, delta, 0.0D);
/* 314 */     result[3] = new Polygon(xpoints, ypoints, 4);
/*     */ 
/*     */     
/* 317 */     result[4] = new Rectangle2D.Double(-delta, -delta / 2.0D, size, size / 2.0D);
/*     */ 
/*     */     
/* 320 */     xpoints = intArray(-delta, delta, 0.0D);
/* 321 */     ypoints = intArray(-delta, -delta, delta);
/* 322 */     result[5] = new Polygon(xpoints, ypoints, 3);
/*     */ 
/*     */     
/* 325 */     result[6] = new Ellipse2D.Double(-delta, -delta / 2.0D, size, size / 2.0D);
/*     */ 
/*     */     
/* 328 */     xpoints = intArray(-delta, delta, -delta);
/* 329 */     ypoints = intArray(-delta, 0.0D, delta);
/* 330 */     result[7] = new Polygon(xpoints, ypoints, 3);
/*     */ 
/*     */     
/* 333 */     result[8] = new Rectangle2D.Double(-delta / 2.0D, -delta, size / 2.0D, size);
/*     */ 
/*     */     
/* 336 */     xpoints = intArray(-delta, delta, delta);
/* 337 */     ypoints = intArray(0.0D, -delta, delta);
/* 338 */     result[9] = new Polygon(xpoints, ypoints, 3);
/*     */     
/* 340 */     return result;
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
/*     */   public boolean equals(Object obj) {
/* 353 */     if (obj == this) {
/* 354 */       return true;
/*     */     }
/* 356 */     if (!(obj instanceof DefaultDrawingSupplier)) {
/* 357 */       return false;
/*     */     }
/* 359 */     DefaultDrawingSupplier that = (DefaultDrawingSupplier)obj;
/* 360 */     if (!Arrays.equals((Object[])this.paintSequence, (Object[])that.paintSequence)) {
/* 361 */       return false;
/*     */     }
/* 363 */     if (this.paintIndex != that.paintIndex) {
/* 364 */       return false;
/*     */     }
/* 366 */     if (!Arrays.equals((Object[])this.outlinePaintSequence, (Object[])that.outlinePaintSequence))
/*     */     {
/* 368 */       return false;
/*     */     }
/* 370 */     if (this.outlinePaintIndex != that.outlinePaintIndex) {
/* 371 */       return false;
/*     */     }
/* 373 */     if (!Arrays.equals((Object[])this.strokeSequence, (Object[])that.strokeSequence)) {
/* 374 */       return false;
/*     */     }
/* 376 */     if (this.strokeIndex != that.strokeIndex) {
/* 377 */       return false;
/*     */     }
/* 379 */     if (!Arrays.equals((Object[])this.outlineStrokeSequence, (Object[])that.outlineStrokeSequence))
/*     */     {
/* 381 */       return false;
/*     */     }
/* 383 */     if (this.outlineStrokeIndex != that.outlineStrokeIndex) {
/* 384 */       return false;
/*     */     }
/* 386 */     if (!equalShapes(this.shapeSequence, that.shapeSequence)) {
/* 387 */       return false;
/*     */     }
/* 389 */     if (this.shapeIndex != that.shapeIndex) {
/* 390 */       return false;
/*     */     }
/* 392 */     return true;
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
/*     */   private boolean equalShapes(Shape[] s1, Shape[] s2) {
/* 404 */     if (s1 == null) {
/* 405 */       return (s2 == null);
/*     */     }
/* 407 */     if (s2 == null) {
/* 408 */       return false;
/*     */     }
/* 410 */     if (s1.length != s2.length) {
/* 411 */       return false;
/*     */     }
/* 413 */     for (int i = 0; i < s1.length; i++) {
/* 414 */       if (!ShapeUtilities.equal(s1[i], s2[i])) {
/* 415 */         return false;
/*     */       }
/*     */     } 
/* 418 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 429 */     stream.defaultWriteObject();
/*     */     
/* 431 */     int paintCount = this.paintSequence.length;
/* 432 */     stream.writeInt(paintCount);
/* 433 */     for (int i = 0; i < paintCount; i++) {
/* 434 */       SerialUtilities.writePaint(this.paintSequence[i], stream);
/*     */     }
/*     */     
/* 437 */     int outlinePaintCount = this.outlinePaintSequence.length;
/* 438 */     stream.writeInt(outlinePaintCount);
/* 439 */     for (int j = 0; j < outlinePaintCount; j++) {
/* 440 */       SerialUtilities.writePaint(this.outlinePaintSequence[j], stream);
/*     */     }
/*     */     
/* 443 */     int strokeCount = this.strokeSequence.length;
/* 444 */     stream.writeInt(strokeCount);
/* 445 */     for (int k = 0; k < strokeCount; k++) {
/* 446 */       SerialUtilities.writeStroke(this.strokeSequence[k], stream);
/*     */     }
/*     */     
/* 449 */     int outlineStrokeCount = this.outlineStrokeSequence.length;
/* 450 */     stream.writeInt(outlineStrokeCount);
/* 451 */     for (int m = 0; m < outlineStrokeCount; m++) {
/* 452 */       SerialUtilities.writeStroke(this.outlineStrokeSequence[m], stream);
/*     */     }
/*     */     
/* 455 */     int shapeCount = this.shapeSequence.length;
/* 456 */     stream.writeInt(shapeCount);
/* 457 */     for (int n = 0; n < shapeCount; n++) {
/* 458 */       SerialUtilities.writeShape(this.shapeSequence[n], stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 473 */     stream.defaultReadObject();
/*     */     
/* 475 */     int paintCount = stream.readInt();
/* 476 */     this.paintSequence = new Paint[paintCount];
/* 477 */     for (int i = 0; i < paintCount; i++) {
/* 478 */       this.paintSequence[i] = SerialUtilities.readPaint(stream);
/*     */     }
/*     */     
/* 481 */     int outlinePaintCount = stream.readInt();
/* 482 */     this.outlinePaintSequence = new Paint[outlinePaintCount];
/* 483 */     for (int j = 0; j < outlinePaintCount; j++) {
/* 484 */       this.outlinePaintSequence[j] = SerialUtilities.readPaint(stream);
/*     */     }
/*     */     
/* 487 */     int strokeCount = stream.readInt();
/* 488 */     this.strokeSequence = new Stroke[strokeCount];
/* 489 */     for (int k = 0; k < strokeCount; k++) {
/* 490 */       this.strokeSequence[k] = SerialUtilities.readStroke(stream);
/*     */     }
/*     */     
/* 493 */     int outlineStrokeCount = stream.readInt();
/* 494 */     this.outlineStrokeSequence = new Stroke[outlineStrokeCount];
/* 495 */     for (int m = 0; m < outlineStrokeCount; m++) {
/* 496 */       this.outlineStrokeSequence[m] = SerialUtilities.readStroke(stream);
/*     */     }
/*     */     
/* 499 */     int shapeCount = stream.readInt();
/* 500 */     this.shapeSequence = new Shape[shapeCount];
/* 501 */     for (int n = 0; n < shapeCount; n++) {
/* 502 */       this.shapeSequence[n] = SerialUtilities.readShape(stream);
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
/*     */   private static int[] intArray(double a, double b, double c) {
/* 518 */     return new int[] { (int)a, (int)b, (int)c };
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
/*     */   private static int[] intArray(double a, double b, double c, double d) {
/* 533 */     return new int[] { (int)a, (int)b, (int)c, (int)d };
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 546 */     DefaultDrawingSupplier clone = (DefaultDrawingSupplier)super.clone();
/* 547 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/DefaultDrawingSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */