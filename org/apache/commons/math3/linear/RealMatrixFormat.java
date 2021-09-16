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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RealMatrixFormat
/*     */ {
/*     */   private static final String DEFAULT_PREFIX = "{";
/*     */   private static final String DEFAULT_SUFFIX = "}";
/*     */   private static final String DEFAULT_ROW_PREFIX = "{";
/*     */   private static final String DEFAULT_ROW_SUFFIX = "}";
/*     */   private static final String DEFAULT_ROW_SEPARATOR = ",";
/*     */   private static final String DEFAULT_COLUMN_SEPARATOR = ",";
/*     */   private final String prefix;
/*     */   private final String suffix;
/*     */   private final String rowPrefix;
/*     */   private final String rowSuffix;
/*     */   private final String rowSeparator;
/*     */   private final String columnSeparator;
/*     */   private final NumberFormat format;
/*     */   
/*     */   public RealMatrixFormat() {
/*  89 */     this("{", "}", "{", "}", ",", ",", CompositeFormat.getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrixFormat(NumberFormat format) {
/*  98 */     this("{", "}", "{", "}", ",", ",", format);
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
/*     */   public RealMatrixFormat(String prefix, String suffix, String rowPrefix, String rowSuffix, String rowSeparator, String columnSeparator) {
/* 114 */     this(prefix, suffix, rowPrefix, rowSuffix, rowSeparator, columnSeparator, CompositeFormat.getDefaultNumberFormat());
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
/*     */   public RealMatrixFormat(String prefix, String suffix, String rowPrefix, String rowSuffix, String rowSeparator, String columnSeparator, NumberFormat format) {
/* 133 */     this.prefix = prefix;
/* 134 */     this.suffix = suffix;
/* 135 */     this.rowPrefix = rowPrefix;
/* 136 */     this.rowSuffix = rowSuffix;
/* 137 */     this.rowSeparator = rowSeparator;
/* 138 */     this.columnSeparator = columnSeparator;
/* 139 */     this.format = format;
/*     */     
/* 141 */     this.format.setGroupingUsed(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale[] getAvailableLocales() {
/* 150 */     return NumberFormat.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 158 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/* 166 */     return this.suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRowPrefix() {
/* 174 */     return this.rowPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRowSuffix() {
/* 182 */     return this.rowSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRowSeparator() {
/* 190 */     return this.rowSeparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnSeparator() {
/* 198 */     return this.columnSeparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getFormat() {
/* 206 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RealMatrixFormat getInstance() {
/* 214 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RealMatrixFormat getInstance(Locale locale) {
/* 223 */     return new RealMatrixFormat(CompositeFormat.getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(RealMatrix m) {
/* 233 */     return format(m, new StringBuffer(), new FieldPosition(0)).toString();
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
/*     */   public StringBuffer format(RealMatrix matrix, StringBuffer toAppendTo, FieldPosition pos) {
/* 247 */     pos.setBeginIndex(0);
/* 248 */     pos.setEndIndex(0);
/*     */ 
/*     */     
/* 251 */     toAppendTo.append(this.prefix);
/*     */ 
/*     */     
/* 254 */     int rows = matrix.getRowDimension();
/* 255 */     for (int i = 0; i < rows; i++) {
/* 256 */       toAppendTo.append(this.rowPrefix);
/* 257 */       for (int j = 0; j < matrix.getColumnDimension(); j++) {
/* 258 */         if (j > 0) {
/* 259 */           toAppendTo.append(this.columnSeparator);
/*     */         }
/* 261 */         CompositeFormat.formatDouble(matrix.getEntry(i, j), this.format, toAppendTo, pos);
/*     */       } 
/* 263 */       toAppendTo.append(this.rowSuffix);
/* 264 */       if (i < rows - 1) {
/* 265 */         toAppendTo.append(this.rowSeparator);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 270 */     toAppendTo.append(this.suffix);
/*     */     
/* 272 */     return toAppendTo;
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
/*     */   public RealMatrix parse(String source) {
/* 284 */     ParsePosition parsePosition = new ParsePosition(0);
/* 285 */     RealMatrix result = parse(source, parsePosition);
/* 286 */     if (parsePosition.getIndex() == 0) {
/* 287 */       throw new MathParseException(source, parsePosition.getErrorIndex(), Array2DRowRealMatrix.class);
/*     */     }
/*     */ 
/*     */     
/* 291 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix parse(String source, ParsePosition pos) {
/* 302 */     int initialIndex = pos.getIndex();
/*     */     
/* 304 */     String trimmedPrefix = this.prefix.trim();
/* 305 */     String trimmedSuffix = this.suffix.trim();
/* 306 */     String trimmedRowPrefix = this.rowPrefix.trim();
/* 307 */     String trimmedRowSuffix = this.rowSuffix.trim();
/* 308 */     String trimmedColumnSeparator = this.columnSeparator.trim();
/* 309 */     String trimmedRowSeparator = this.rowSeparator.trim();
/*     */ 
/*     */     
/* 312 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 313 */     if (!CompositeFormat.parseFixedstring(source, trimmedPrefix, pos)) {
/* 314 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 318 */     List<List<Number>> matrix = new ArrayList<List<Number>>();
/* 319 */     List<Number> rowComponents = new ArrayList<Number>();
/* 320 */     for (boolean loop = true; loop; ) {
/*     */       
/* 322 */       if (!rowComponents.isEmpty()) {
/* 323 */         CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 324 */         if (!CompositeFormat.parseFixedstring(source, trimmedColumnSeparator, pos)) {
/* 325 */           if (trimmedRowSuffix.length() != 0 && !CompositeFormat.parseFixedstring(source, trimmedRowSuffix, pos))
/*     */           {
/* 327 */             return null;
/*     */           }
/* 329 */           CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 330 */           if (CompositeFormat.parseFixedstring(source, trimmedRowSeparator, pos)) {
/* 331 */             matrix.add(rowComponents);
/* 332 */             rowComponents = new ArrayList<Number>();
/*     */             continue;
/*     */           } 
/* 335 */           loop = false;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 340 */         CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 341 */         if (trimmedRowPrefix.length() != 0 && !CompositeFormat.parseFixedstring(source, trimmedRowPrefix, pos))
/*     */         {
/* 343 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 347 */       if (loop) {
/* 348 */         CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 349 */         Number component = CompositeFormat.parseNumber(source, this.format, pos);
/* 350 */         if (component != null) {
/* 351 */           rowComponents.add(component); continue;
/*     */         } 
/* 353 */         if (rowComponents.isEmpty()) {
/* 354 */           loop = false;
/*     */           
/*     */           continue;
/*     */         } 
/* 358 */         pos.setIndex(initialIndex);
/* 359 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     if (!rowComponents.isEmpty()) {
/* 367 */       matrix.add(rowComponents);
/*     */     }
/*     */ 
/*     */     
/* 371 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/* 372 */     if (!CompositeFormat.parseFixedstring(source, trimmedSuffix, pos)) {
/* 373 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 377 */     if (matrix.isEmpty()) {
/* 378 */       pos.setIndex(initialIndex);
/* 379 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 383 */     double[][] data = new double[matrix.size()][];
/* 384 */     int row = 0;
/* 385 */     for (List<Number> rowList : matrix) {
/* 386 */       data[row] = new double[rowList.size()];
/* 387 */       for (int i = 0; i < rowList.size(); i++) {
/* 388 */         data[row][i] = ((Number)rowList.get(i)).doubleValue();
/*     */       }
/* 390 */       row++;
/*     */     } 
/* 392 */     return MatrixUtils.createRealMatrix(data);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RealMatrixFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */