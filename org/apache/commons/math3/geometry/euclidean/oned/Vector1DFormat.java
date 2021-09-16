/*     */ package org.apache.commons.math3.geometry.euclidean.oned;
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
/*     */ public class Vector1DFormat
/*     */   extends VectorFormat<Euclidean1D>
/*     */ {
/*     */   public Vector1DFormat() {
/*  54 */     super("{", "}", "; ", CompositeFormat.getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1DFormat(NumberFormat format) {
/*  63 */     super("{", "}", "; ", format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1DFormat(String prefix, String suffix) {
/*  72 */     super(prefix, suffix, "; ", CompositeFormat.getDefaultNumberFormat());
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
/*     */   public Vector1DFormat(String prefix, String suffix, NumberFormat format) {
/*  84 */     super(prefix, suffix, "; ", format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector1DFormat getInstance() {
/*  92 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector1DFormat getInstance(Locale locale) {
/* 101 */     return new Vector1DFormat(CompositeFormat.getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer format(Vector<Euclidean1D> vector, StringBuffer toAppendTo, FieldPosition pos) {
/* 108 */     Vector1D p1 = (Vector1D)vector;
/* 109 */     return format(toAppendTo, pos, new double[] { p1.getX() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D parse(String source) throws MathParseException {
/* 115 */     ParsePosition parsePosition = new ParsePosition(0);
/* 116 */     Vector1D result = parse(source, parsePosition);
/* 117 */     if (parsePosition.getIndex() == 0) {
/* 118 */       throw new MathParseException(source, parsePosition.getErrorIndex(), Vector1D.class);
/*     */     }
/*     */ 
/*     */     
/* 122 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D parse(String source, ParsePosition pos) {
/* 128 */     double[] coordinates = parseCoordinates(1, source, pos);
/* 129 */     if (coordinates == null) {
/* 130 */       return null;
/*     */     }
/* 132 */     return new Vector1D(coordinates[0]);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/Vector1DFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */