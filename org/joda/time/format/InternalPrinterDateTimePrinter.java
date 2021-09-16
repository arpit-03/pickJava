/*     */ package org.joda.time.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadablePartial;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InternalPrinterDateTimePrinter
/*     */   implements DateTimePrinter, InternalPrinter
/*     */ {
/*     */   private final InternalPrinter underlying;
/*     */   
/*     */   static DateTimePrinter of(InternalPrinter paramInternalPrinter) {
/*  37 */     if (paramInternalPrinter instanceof DateTimePrinterInternalPrinter) {
/*  38 */       return ((DateTimePrinterInternalPrinter)paramInternalPrinter).getUnderlying();
/*     */     }
/*  40 */     if (paramInternalPrinter instanceof DateTimePrinter) {
/*  41 */       return (DateTimePrinter)paramInternalPrinter;
/*     */     }
/*  43 */     if (paramInternalPrinter == null) {
/*  44 */       return null;
/*     */     }
/*  46 */     return new InternalPrinterDateTimePrinter(paramInternalPrinter);
/*     */   }
/*     */   
/*     */   private InternalPrinterDateTimePrinter(InternalPrinter paramInternalPrinter) {
/*  50 */     this.underlying = paramInternalPrinter;
/*     */   }
/*     */ 
/*     */   
/*     */   public int estimatePrintedLength() {
/*  55 */     return this.underlying.estimatePrintedLength();
/*     */   }
/*     */ 
/*     */   
/*     */   public void printTo(StringBuffer paramStringBuffer, long paramLong, Chronology paramChronology, int paramInt, DateTimeZone paramDateTimeZone, Locale paramLocale) {
/*     */     try {
/*  61 */       this.underlying.printTo(paramStringBuffer, paramLong, paramChronology, paramInt, paramDateTimeZone, paramLocale);
/*  62 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printTo(Writer paramWriter, long paramLong, Chronology paramChronology, int paramInt, DateTimeZone paramDateTimeZone, Locale paramLocale) throws IOException {
/*  69 */     this.underlying.printTo(paramWriter, paramLong, paramChronology, paramInt, paramDateTimeZone, paramLocale);
/*     */   }
/*     */ 
/*     */   
/*     */   public void printTo(Appendable paramAppendable, long paramLong, Chronology paramChronology, int paramInt, DateTimeZone paramDateTimeZone, Locale paramLocale) throws IOException {
/*  74 */     this.underlying.printTo(paramAppendable, paramLong, paramChronology, paramInt, paramDateTimeZone, paramLocale);
/*     */   }
/*     */   
/*     */   public void printTo(StringBuffer paramStringBuffer, ReadablePartial paramReadablePartial, Locale paramLocale) {
/*     */     try {
/*  79 */       this.underlying.printTo(paramStringBuffer, paramReadablePartial, paramLocale);
/*  80 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void printTo(Writer paramWriter, ReadablePartial paramReadablePartial, Locale paramLocale) throws IOException {
/*  86 */     this.underlying.printTo(paramWriter, paramReadablePartial, paramLocale);
/*     */   }
/*     */   
/*     */   public void printTo(Appendable paramAppendable, ReadablePartial paramReadablePartial, Locale paramLocale) throws IOException {
/*  90 */     this.underlying.printTo(paramAppendable, paramReadablePartial, paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  96 */     if (paramObject == this) {
/*  97 */       return true;
/*     */     }
/*  99 */     if (paramObject instanceof InternalPrinterDateTimePrinter) {
/* 100 */       InternalPrinterDateTimePrinter internalPrinterDateTimePrinter = (InternalPrinterDateTimePrinter)paramObject;
/* 101 */       return this.underlying.equals(internalPrinterDateTimePrinter.underlying);
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/InternalPrinterDateTimePrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */