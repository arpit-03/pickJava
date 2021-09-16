/*     */ package org.apache.commons.math3.geometry;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.math3.exception.MathParseException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VectorFormat<S extends Space>
/*     */ {
/*     */   public static final String DEFAULT_PREFIX = "{";
/*     */   public static final String DEFAULT_SUFFIX = "}";
/*     */   public static final String DEFAULT_SEPARATOR = "; ";
/*     */   private final String prefix;
/*     */   private final String suffix;
/*     */   private final String separator;
/*     */   private final String trimmedPrefix;
/*     */   private final String trimmedSuffix;
/*     */   private final String trimmedSeparator;
/*     */   private final NumberFormat format;
/*     */   
/*     */   protected VectorFormat() {
/*  83 */     this("{", "}", "; ", CompositeFormat.getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VectorFormat(NumberFormat format) {
/*  92 */     this("{", "}", "; ", format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VectorFormat(String prefix, String suffix, String separator) {
/* 103 */     this(prefix, suffix, separator, CompositeFormat.getDefaultNumberFormat());
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
/*     */   protected VectorFormat(String prefix, String suffix, String separator, NumberFormat format) {
/* 116 */     this.prefix = prefix;
/* 117 */     this.suffix = suffix;
/* 118 */     this.separator = separator;
/* 119 */     this.trimmedPrefix = prefix.trim();
/* 120 */     this.trimmedSuffix = suffix.trim();
/* 121 */     this.trimmedSeparator = separator.trim();
/* 122 */     this.format = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale[] getAvailableLocales() {
/* 131 */     return NumberFormat.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 139 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/* 147 */     return this.suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeparator() {
/* 155 */     return this.separator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getFormat() {
/* 163 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(Vector<S> vector) {
/* 172 */     return format(vector, new StringBuffer(), new FieldPosition(0)).toString();
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
/*     */   public abstract StringBuffer format(Vector<S> paramVector, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringBuffer format(StringBuffer toAppendTo, FieldPosition pos, double... coordinates) {
/* 197 */     pos.setBeginIndex(0);
/* 198 */     pos.setEndIndex(0);
/*     */ 
/*     */     
/* 201 */     toAppendTo.append(this.prefix);
/*     */ 
/*     */     
/* 204 */     for (int i = 0; i < coordinates.length; i++) {
/* 205 */       if (i > 0) {
/* 206 */         toAppendTo.append(this.separator);
/*     */       }
/* 208 */       CompositeFormat.formatDouble(coordinates[i], this.format, toAppendTo, pos);
/*     */     } 
/*     */ 
/*     */     
/* 212 */     toAppendTo.append(this.suffix);
/*     */     
/* 214 */     return toAppendTo;
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
/*     */   public abstract Vector<S> parse(String paramString) throws MathParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Vector<S> parse(String paramString, ParsePosition paramParsePosition);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double[] parseCoordinates(int dimension, String source, ParsePosition pos) {
/* 244 */     int initialIndex = pos.getIndex();
/* 245 */     double[] coordinates = new double[dimension];
/*     */ 
/*     */     
/* 248 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 249 */     if (!CompositeFormat.parseFixedstring(source, this.trimmedPrefix, pos)) {
/* 250 */       return null;
/*     */     }
/*     */     
/* 253 */     for (int i = 0; i < dimension; i++) {
/*     */ 
/*     */       
/* 256 */       CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */       
/* 259 */       if (i > 0 && !CompositeFormat.parseFixedstring(source, this.trimmedSeparator, pos)) {
/* 260 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 264 */       CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */       
/* 267 */       Number c = CompositeFormat.parseNumber(source, this.format, pos);
/* 268 */       if (c == null) {
/*     */ 
/*     */         
/* 271 */         pos.setIndex(initialIndex);
/* 272 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 276 */       coordinates[i] = c.doubleValue();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 281 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 282 */     if (!CompositeFormat.parseFixedstring(source, this.trimmedSuffix, pos)) {
/* 283 */       return null;
/*     */     }
/*     */     
/* 286 */     return coordinates;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/VectorFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */