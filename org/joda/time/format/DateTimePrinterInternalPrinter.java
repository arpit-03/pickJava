/*    */ package org.joda.time.format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Locale;
/*    */ import org.joda.time.Chronology;
/*    */ import org.joda.time.DateTimeZone;
/*    */ import org.joda.time.ReadablePartial;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DateTimePrinterInternalPrinter
/*    */   implements InternalPrinter
/*    */ {
/*    */   private final DateTimePrinter underlying;
/*    */   
/*    */   static InternalPrinter of(DateTimePrinter paramDateTimePrinter) {
/* 37 */     if (paramDateTimePrinter instanceof InternalPrinterDateTimePrinter) {
/* 38 */       return (InternalPrinter)paramDateTimePrinter;
/*    */     }
/* 40 */     if (paramDateTimePrinter == null) {
/* 41 */       return null;
/*    */     }
/* 43 */     return new DateTimePrinterInternalPrinter(paramDateTimePrinter);
/*    */   }
/*    */   
/*    */   private DateTimePrinterInternalPrinter(DateTimePrinter paramDateTimePrinter) {
/* 47 */     this.underlying = paramDateTimePrinter;
/*    */   }
/*    */ 
/*    */   
/*    */   DateTimePrinter getUnderlying() {
/* 52 */     return this.underlying;
/*    */   }
/*    */ 
/*    */   
/*    */   public int estimatePrintedLength() {
/* 57 */     return this.underlying.estimatePrintedLength();
/*    */   }
/*    */ 
/*    */   
/*    */   public void printTo(Appendable paramAppendable, long paramLong, Chronology paramChronology, int paramInt, DateTimeZone paramDateTimeZone, Locale paramLocale) throws IOException {
/* 62 */     if (paramAppendable instanceof StringBuffer) {
/* 63 */       StringBuffer stringBuffer1 = (StringBuffer)paramAppendable;
/* 64 */       this.underlying.printTo(stringBuffer1, paramLong, paramChronology, paramInt, paramDateTimeZone, paramLocale);
/*    */     } 
/* 66 */     if (paramAppendable instanceof Writer) {
/* 67 */       Writer writer = (Writer)paramAppendable;
/* 68 */       this.underlying.printTo(writer, paramLong, paramChronology, paramInt, paramDateTimeZone, paramLocale);
/*    */     } 
/* 70 */     StringBuffer stringBuffer = new StringBuffer(estimatePrintedLength());
/* 71 */     this.underlying.printTo(stringBuffer, paramLong, paramChronology, paramInt, paramDateTimeZone, paramLocale);
/* 72 */     paramAppendable.append(stringBuffer);
/*    */   }
/*    */   
/*    */   public void printTo(Appendable paramAppendable, ReadablePartial paramReadablePartial, Locale paramLocale) throws IOException {
/* 76 */     if (paramAppendable instanceof StringBuffer) {
/* 77 */       StringBuffer stringBuffer1 = (StringBuffer)paramAppendable;
/* 78 */       this.underlying.printTo(stringBuffer1, paramReadablePartial, paramLocale);
/*    */     } 
/* 80 */     if (paramAppendable instanceof Writer) {
/* 81 */       Writer writer = (Writer)paramAppendable;
/* 82 */       this.underlying.printTo(writer, paramReadablePartial, paramLocale);
/*    */     } 
/* 84 */     StringBuffer stringBuffer = new StringBuffer(estimatePrintedLength());
/* 85 */     this.underlying.printTo(stringBuffer, paramReadablePartial, paramLocale);
/* 86 */     paramAppendable.append(stringBuffer);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimePrinterInternalPrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */