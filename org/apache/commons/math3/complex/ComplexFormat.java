/*     */ package org.apache.commons.math3.complex;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathParseException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class ComplexFormat
/*     */ {
/*     */   private static final String DEFAULT_IMAGINARY_CHARACTER = "i";
/*     */   private final String imaginaryCharacter;
/*     */   private final NumberFormat imaginaryFormat;
/*     */   private final NumberFormat realFormat;
/*     */   
/*     */   public ComplexFormat() {
/*  54 */     this.imaginaryCharacter = "i";
/*  55 */     this.imaginaryFormat = CompositeFormat.getDefaultNumberFormat();
/*  56 */     this.realFormat = this.imaginaryFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComplexFormat(NumberFormat format) throws NullArgumentException {
/*  66 */     if (format == null) {
/*  67 */       throw new NullArgumentException(LocalizedFormats.IMAGINARY_FORMAT, new Object[0]);
/*     */     }
/*  69 */     this.imaginaryCharacter = "i";
/*  70 */     this.imaginaryFormat = format;
/*  71 */     this.realFormat = format;
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
/*     */   public ComplexFormat(NumberFormat realFormat, NumberFormat imaginaryFormat) throws NullArgumentException {
/*  84 */     if (imaginaryFormat == null) {
/*  85 */       throw new NullArgumentException(LocalizedFormats.IMAGINARY_FORMAT, new Object[0]);
/*     */     }
/*  87 */     if (realFormat == null) {
/*  88 */       throw new NullArgumentException(LocalizedFormats.REAL_FORMAT, new Object[0]);
/*     */     }
/*     */     
/*  91 */     this.imaginaryCharacter = "i";
/*  92 */     this.imaginaryFormat = imaginaryFormat;
/*  93 */     this.realFormat = realFormat;
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
/*     */   public ComplexFormat(String imaginaryCharacter) throws NullArgumentException, NoDataException {
/* 107 */     this(imaginaryCharacter, CompositeFormat.getDefaultNumberFormat());
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
/*     */   public ComplexFormat(String imaginaryCharacter, NumberFormat format) throws NullArgumentException, NoDataException {
/* 123 */     this(imaginaryCharacter, format, format);
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
/*     */   public ComplexFormat(String imaginaryCharacter, NumberFormat realFormat, NumberFormat imaginaryFormat) throws NullArgumentException, NoDataException {
/* 145 */     if (imaginaryCharacter == null) {
/* 146 */       throw new NullArgumentException();
/*     */     }
/* 148 */     if (imaginaryCharacter.length() == 0) {
/* 149 */       throw new NoDataException();
/*     */     }
/* 151 */     if (imaginaryFormat == null) {
/* 152 */       throw new NullArgumentException(LocalizedFormats.IMAGINARY_FORMAT, new Object[0]);
/*     */     }
/* 154 */     if (realFormat == null) {
/* 155 */       throw new NullArgumentException(LocalizedFormats.REAL_FORMAT, new Object[0]);
/*     */     }
/*     */     
/* 158 */     this.imaginaryCharacter = imaginaryCharacter;
/* 159 */     this.imaginaryFormat = imaginaryFormat;
/* 160 */     this.realFormat = realFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale[] getAvailableLocales() {
/* 169 */     return NumberFormat.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(Complex c) {
/* 179 */     return format(c, new StringBuffer(), new FieldPosition(0)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(Double c) {
/* 189 */     return format(new Complex(c.doubleValue(), 0.0D), new StringBuffer(), new FieldPosition(0)).toString();
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
/*     */   public StringBuffer format(Complex complex, StringBuffer toAppendTo, FieldPosition pos) {
/* 203 */     pos.setBeginIndex(0);
/* 204 */     pos.setEndIndex(0);
/*     */ 
/*     */     
/* 207 */     double re = complex.getReal();
/* 208 */     CompositeFormat.formatDouble(re, getRealFormat(), toAppendTo, pos);
/*     */ 
/*     */     
/* 211 */     double im = complex.getImaginary();
/*     */     
/* 213 */     if (im < 0.0D) {
/* 214 */       toAppendTo.append(" - ");
/* 215 */       StringBuffer imAppendTo = formatImaginary(-im, new StringBuffer(), pos);
/* 216 */       toAppendTo.append(imAppendTo);
/* 217 */       toAppendTo.append(getImaginaryCharacter());
/* 218 */     } else if (im > 0.0D || Double.isNaN(im)) {
/* 219 */       toAppendTo.append(" + ");
/* 220 */       StringBuffer imAppendTo = formatImaginary(im, new StringBuffer(), pos);
/* 221 */       toAppendTo.append(imAppendTo);
/* 222 */       toAppendTo.append(getImaginaryCharacter());
/*     */     } 
/*     */     
/* 225 */     return toAppendTo;
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
/*     */   private StringBuffer formatImaginary(double absIm, StringBuffer toAppendTo, FieldPosition pos) {
/* 240 */     pos.setBeginIndex(0);
/* 241 */     pos.setEndIndex(0);
/*     */     
/* 243 */     CompositeFormat.formatDouble(absIm, getImaginaryFormat(), toAppendTo, pos);
/* 244 */     if (toAppendTo.toString().equals("1"))
/*     */     {
/* 246 */       toAppendTo.setLength(0);
/*     */     }
/*     */     
/* 249 */     return toAppendTo;
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
/*     */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) throws MathIllegalArgumentException {
/* 269 */     StringBuffer ret = null;
/*     */     
/* 271 */     if (obj instanceof Complex) {
/* 272 */       ret = format((Complex)obj, toAppendTo, pos);
/* 273 */     } else if (obj instanceof Number) {
/* 274 */       ret = format(new Complex(((Number)obj).doubleValue(), 0.0D), toAppendTo, pos);
/*     */     } else {
/*     */       
/* 277 */       throw new MathIllegalArgumentException(LocalizedFormats.CANNOT_FORMAT_INSTANCE_AS_COMPLEX, new Object[] { obj.getClass().getName() });
/*     */     } 
/*     */ 
/*     */     
/* 281 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImaginaryCharacter() {
/* 289 */     return this.imaginaryCharacter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getImaginaryFormat() {
/* 297 */     return this.imaginaryFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexFormat getInstance() {
/* 305 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComplexFormat getInstance(Locale locale) {
/* 314 */     NumberFormat f = CompositeFormat.getDefaultNumberFormat(locale);
/* 315 */     return new ComplexFormat(f);
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
/*     */   public static ComplexFormat getInstance(String imaginaryCharacter, Locale locale) throws NullArgumentException, NoDataException {
/* 330 */     NumberFormat f = CompositeFormat.getDefaultNumberFormat(locale);
/* 331 */     return new ComplexFormat(imaginaryCharacter, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getRealFormat() {
/* 339 */     return this.realFormat;
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
/*     */   public Complex parse(String source) throws MathParseException {
/* 351 */     ParsePosition parsePosition = new ParsePosition(0);
/* 352 */     Complex result = parse(source, parsePosition);
/* 353 */     if (parsePosition.getIndex() == 0) {
/* 354 */       throw new MathParseException(source, parsePosition.getErrorIndex(), Complex.class);
/*     */     }
/*     */ 
/*     */     
/* 358 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Complex parse(String source, ParsePosition pos) {
/* 369 */     int initialIndex = pos.getIndex();
/*     */ 
/*     */     
/* 372 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 375 */     Number re = CompositeFormat.parseNumber(source, getRealFormat(), pos);
/* 376 */     if (re == null) {
/*     */ 
/*     */       
/* 379 */       pos.setIndex(initialIndex);
/* 380 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 384 */     int startIndex = pos.getIndex();
/* 385 */     char c = CompositeFormat.parseNextCharacter(source, pos);
/* 386 */     int sign = 0;
/* 387 */     switch (c) {
/*     */ 
/*     */       
/*     */       case '\000':
/* 391 */         return new Complex(re.doubleValue(), 0.0D);
/*     */       case '-':
/* 393 */         sign = -1;
/*     */         break;
/*     */       case '+':
/* 396 */         sign = 1;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 402 */         pos.setIndex(initialIndex);
/* 403 */         pos.setErrorIndex(startIndex);
/* 404 */         return null;
/*     */     } 
/*     */ 
/*     */     
/* 408 */     CompositeFormat.parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 411 */     Number im = CompositeFormat.parseNumber(source, getRealFormat(), pos);
/* 412 */     if (im == null) {
/*     */ 
/*     */       
/* 415 */       pos.setIndex(initialIndex);
/* 416 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 420 */     if (!CompositeFormat.parseFixedstring(source, getImaginaryCharacter(), pos)) {
/* 421 */       return null;
/*     */     }
/*     */     
/* 424 */     return new Complex(re.doubleValue(), im.doubleValue() * sign);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/complex/ComplexFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */