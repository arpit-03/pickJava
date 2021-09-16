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
/*    */ 
/*    */ class InternalParserDateTimeParser
/*    */   implements DateTimeParser, InternalParser
/*    */ {
/*    */   private final InternalParser underlying;
/*    */   
/*    */   static DateTimeParser of(InternalParser paramInternalParser) {
/* 30 */     if (paramInternalParser instanceof DateTimeParserInternalParser) {
/* 31 */       return ((DateTimeParserInternalParser)paramInternalParser).getUnderlying();
/*    */     }
/* 33 */     if (paramInternalParser instanceof DateTimeParser) {
/* 34 */       return (DateTimeParser)paramInternalParser;
/*    */     }
/* 36 */     if (paramInternalParser == null) {
/* 37 */       return null;
/*    */     }
/* 39 */     return new InternalParserDateTimeParser(paramInternalParser);
/*    */   }
/*    */   
/*    */   private InternalParserDateTimeParser(InternalParser paramInternalParser) {
/* 43 */     this.underlying = paramInternalParser;
/*    */   }
/*    */ 
/*    */   
/*    */   public int estimateParsedLength() {
/* 48 */     return this.underlying.estimateParsedLength();
/*    */   }
/*    */   
/*    */   public int parseInto(DateTimeParserBucket paramDateTimeParserBucket, CharSequence paramCharSequence, int paramInt) {
/* 52 */     return this.underlying.parseInto(paramDateTimeParserBucket, paramCharSequence, paramInt);
/*    */   }
/*    */   
/*    */   public int parseInto(DateTimeParserBucket paramDateTimeParserBucket, String paramString, int paramInt) {
/* 56 */     return this.underlying.parseInto(paramDateTimeParserBucket, paramString, paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 62 */     if (paramObject == this) {
/* 63 */       return true;
/*    */     }
/* 65 */     if (paramObject instanceof InternalParserDateTimeParser) {
/* 66 */       InternalParserDateTimeParser internalParserDateTimeParser = (InternalParserDateTimeParser)paramObject;
/* 67 */       return this.underlying.equals(internalParserDateTimeParser.underlying);
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/InternalParserDateTimeParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */