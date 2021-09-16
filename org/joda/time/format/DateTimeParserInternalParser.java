/*    */ package org.joda.time.format;
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
/*    */ class DateTimeParserInternalParser
/*    */   implements InternalParser
/*    */ {
/*    */   private final DateTimeParser underlying;
/*    */   
/*    */   static InternalParser of(DateTimeParser paramDateTimeParser) {
/* 29 */     if (paramDateTimeParser instanceof InternalParserDateTimeParser) {
/* 30 */       return (InternalParser)paramDateTimeParser;
/*    */     }
/* 32 */     if (paramDateTimeParser == null) {
/* 33 */       return null;
/*    */     }
/* 35 */     return new DateTimeParserInternalParser(paramDateTimeParser);
/*    */   }
/*    */   
/*    */   private DateTimeParserInternalParser(DateTimeParser paramDateTimeParser) {
/* 39 */     this.underlying = paramDateTimeParser;
/*    */   }
/*    */ 
/*    */   
/*    */   DateTimeParser getUnderlying() {
/* 44 */     return this.underlying;
/*    */   }
/*    */   
/*    */   public int estimateParsedLength() {
/* 48 */     return this.underlying.estimateParsedLength();
/*    */   }
/*    */   
/*    */   public int parseInto(DateTimeParserBucket paramDateTimeParserBucket, CharSequence paramCharSequence, int paramInt) {
/* 52 */     return this.underlying.parseInto(paramDateTimeParserBucket, paramCharSequence.toString(), paramInt);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimeParserInternalParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */