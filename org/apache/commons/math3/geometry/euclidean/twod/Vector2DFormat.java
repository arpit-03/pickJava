/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.math3.exception.MathParseException;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.VectorFormat;
/*     */ import org.apache.commons.math3.util.CompositeFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector2DFormat
/*     */   extends VectorFormat<Euclidean2D>
/*     */ {
/*     */   public Vector2DFormat() {
/*  54 */     super("{", "}", "; ", CompositeFormat.getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2DFormat(NumberFormat format) {
/*  63 */     super("{", "}", "; ", format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2DFormat(String prefix, String suffix, String separator) {
/*  74 */     super(prefix, suffix, separator, CompositeFormat.getDefaultNumberFormat());
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
/*     */   public Vector2DFormat(String prefix, String suffix, String separator, NumberFormat format) {
/*  87 */     super(prefix, suffix, separator, format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector2DFormat getInstance() {
/*  95 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector2DFormat getInstance(Locale locale) {
/* 104 */     return new Vector2DFormat(CompositeFormat.getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer format(Vector<Euclidean2D> vector, StringBuffer toAppendTo, FieldPosition pos) {
/* 111 */     Vector2D p2 = (Vector2D)vector;
/* 112 */     return format(toAppendTo, pos, new double[] { p2.getX(), p2.getY() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D parse(String source) throws MathParseException {
/* 118 */     ParsePosition parsePosition = new ParsePosition(0);
/* 119 */     Vector2D result = parse(source, parsePosition);
/* 120 */     if (parsePosition.getIndex() == 0) {
/* 121 */       throw new MathParseException(source, parsePosition.getErrorIndex(), Vector2D.class);
/*     */     }
/*     */ 
/*     */     
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D parse(String source, ParsePosition pos) {
/* 131 */     double[] coordinates = parseCoordinates(2, source, pos);
/* 132 */     if (coordinates == null) {
/* 133 */       return null;
/*     */     }
/* 135 */     return new Vector2D(coordinates[0], coordinates[1]);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/Vector2DFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */