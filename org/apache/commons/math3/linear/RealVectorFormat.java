/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class RealVectorFormat
/*     */ {
/*     */   private static final String DEFAULT_PREFIX = "{";
/*     */   private static final String DEFAULT_SUFFIX = "}";
/*     */   private static final String DEFAULT_SEPARATOR = "; ";
/*     */   private final String prefix;
/*     */   private final String suffix;
/*     */   private final String separator;
/*     */   private final String trimmedPrefix;
/*     */   private final String trimmedSuffix;
/*     */   private final String trimmedSeparator;
/*     */   private final NumberFormat format;
/*     */   
/*     */   public RealVectorFormat() {
/*  72 */     this("{", "}", "; ", CompositeFormat.getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVectorFormat(NumberFormat format) {
/*  81 */     this("{", "}", "; ", format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVectorFormat(String prefix, String suffix, String separator) {
/*  92 */     this(prefix, suffix, separator, CompositeFormat.getDefaultNumberFormat());
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
/*     */   public RealVectorFormat(String prefix, String suffix, String separator, NumberFormat format) {
/* 106 */     this.prefix = prefix;
/* 107 */     this.suffix = suffix;
/* 108 */     this.separator = separator;
/* 109 */     this.trimmedPrefix = prefix.trim();
/* 110 */     this.trimmedSuffix = suffix.trim();
/* 111 */     this.trimmedSeparator = separator.trim();
/* 112 */     this.format = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale[] getAvailableLocales() {
/* 121 */     return NumberFormat.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 129 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/* 137 */     return this.suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeparator() {
/* 145 */     return this.separator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getFormat() {
/* 153 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RealVectorFormat getInstance() {
/* 161 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RealVectorFormat getInstance(Locale locale) {
/* 170 */     return new RealVectorFormat(CompositeFormat.getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(RealVector v) {
/* 180 */     return format(v, new StringBuffer(), new FieldPosition(0)).toString();
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
/*     */   public StringBuffer format(RealVector vector, StringBuffer toAppendTo, FieldPosition pos) {
/* 194 */     pos.setBeginIndex(0);
/* 195 */     pos.setEndIndex(0);
/*     */ 
/*     */     
/* 198 */     toAppendTo.append(this.prefix);
/*     */ 
/*     */     
/* 201 */     for (int i = 0; i < vector.getDimension(); i++) {
/* 202 */       if (i > 0) {
/* 203 */         toAppendTo.append(this.separator);
/*     */       }
/* 205 */       CompositeFormat.formatDouble(vector.getEntry(i), this.format, toAppendTo, pos);
/*     */     } 
/*     */ 
/*     */     
/* 209 */     toAppendTo.append(this.suffix);
/*     */     
/* 211 */     return toAppendTo;
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
/*     */   public ArrayRealVector parse(String source) {
/* 223 */     ParsePosition parsePosition = new ParsePosition(0);
/* 224 */     ArrayRealVector result = parse(source, parsePosition);
/* 225 */     if (parsePosition.getIndex() == 0) {
/* 226 */       throw new MathParseException(source, parsePosition.getErrorIndex(), ArrayRealVector.class);
/*     */     }
/*     */ 
/*     */     
/* 230 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector parse(String source, ParsePosition pos) {
/* 241 */     int initialIndex = pos.getIndex();
/*     */ 
/*     */     
/* 244 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 245 */     if (!CompositeFormat.parseFixedstring(source, this.trimmedPrefix, pos)) {
/* 246 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 250 */     List<Number> components = new ArrayList<Number>();
/* 251 */     for (boolean loop = true; loop; ) {
/*     */       
/* 253 */       if (!components.isEmpty()) {
/* 254 */         CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 255 */         if (!CompositeFormat.parseFixedstring(source, this.trimmedSeparator, pos)) {
/* 256 */           loop = false;
/*     */         }
/*     */       } 
/*     */       
/* 260 */       if (loop) {
/* 261 */         CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 262 */         Number component = CompositeFormat.parseNumber(source, this.format, pos);
/* 263 */         if (component != null) {
/* 264 */           components.add(component);
/*     */           
/*     */           continue;
/*     */         } 
/* 268 */         pos.setIndex(initialIndex);
/* 269 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 277 */     if (!CompositeFormat.parseFixedstring(source, this.trimmedSuffix, pos)) {
/* 278 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 282 */     double[] data = new double[components.size()];
/* 283 */     for (int i = 0; i < data.length; i++) {
/* 284 */       data[i] = ((Number)components.get(i)).doubleValue();
/*     */     }
/* 286 */     return new ArrayRealVector(data, false);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RealVectorFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */