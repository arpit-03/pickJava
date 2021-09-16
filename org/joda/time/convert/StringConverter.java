/*     */ package org.joda.time.convert;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.Period;
/*     */ import org.joda.time.ReadWritableInterval;
/*     */ import org.joda.time.ReadWritablePeriod;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import org.joda.time.format.ISODateTimeFormat;
/*     */ import org.joda.time.format.ISOPeriodFormat;
/*     */ import org.joda.time.format.PeriodFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StringConverter
/*     */   extends AbstractConverter
/*     */   implements InstantConverter, PartialConverter, DurationConverter, PeriodConverter, IntervalConverter
/*     */ {
/*  44 */   static final StringConverter INSTANCE = new StringConverter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getInstantMillis(Object paramObject, Chronology paramChronology) {
/*  63 */     String str = (String)paramObject;
/*  64 */     DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeParser();
/*  65 */     return dateTimeFormatter.withChronology(paramChronology).parseMillis(str);
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
/*     */   public int[] getPartialValues(ReadablePartial paramReadablePartial, Object paramObject, Chronology paramChronology, DateTimeFormatter paramDateTimeFormatter) {
/*  84 */     if (paramDateTimeFormatter.getZone() != null) {
/*  85 */       paramChronology = paramChronology.withZone(paramDateTimeFormatter.getZone());
/*     */     }
/*  87 */     long l = paramDateTimeFormatter.withChronology(paramChronology).parseMillis((String)paramObject);
/*  88 */     return paramChronology.get(paramReadablePartial, l);
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
/*     */   public long getDurationMillis(Object paramObject) {
/* 102 */     String str1 = (String)paramObject;
/* 103 */     String str2 = str1;
/* 104 */     int i = str2.length();
/* 105 */     if (i >= 4 && (str2.charAt(0) == 'P' || str2.charAt(0) == 'p') && (str2.charAt(1) == 'T' || str2.charAt(1) == 't') && (str2.charAt(i - 1) == 'S' || str2.charAt(i - 1) == 's')) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       str2 = str2.substring(2, i - 1);
/* 114 */       byte b = -1;
/* 115 */       boolean bool1 = false;
/* 116 */       for (byte b1 = 0; b1 < str2.length(); b1++) {
/* 117 */         if (str2.charAt(b1) < '0' || str2.charAt(b1) > '9')
/*     */         {
/* 119 */           if (b1 == 0 && str2.charAt(0) == '-') {
/*     */             
/* 121 */             bool1 = true;
/* 122 */           } else if (b1 > (bool1 ? 1 : 0) && str2.charAt(b1) == '.' && b == -1) {
/*     */             
/* 124 */             b = b1;
/*     */           } else {
/* 126 */             throw new IllegalArgumentException("Invalid format: \"" + str1 + '"');
/*     */           }  } 
/*     */       } 
/* 129 */       long l1 = 0L, l2 = 0L;
/* 130 */       boolean bool2 = bool1 ? true : false;
/* 131 */       if (b > 0) {
/* 132 */         l2 = Long.parseLong(str2.substring(bool2, b));
/* 133 */         str2 = str2.substring(b + 1);
/* 134 */         if (str2.length() != 3) {
/* 135 */           str2 = (str2 + "000").substring(0, 3);
/*     */         }
/* 137 */         l1 = Integer.parseInt(str2);
/* 138 */       } else if (bool1) {
/* 139 */         l2 = Long.parseLong(str2.substring(bool2, str2.length()));
/*     */       } else {
/* 141 */         l2 = Long.parseLong(str2);
/*     */       } 
/* 143 */       if (bool1) {
/* 144 */         return FieldUtils.safeAdd(FieldUtils.safeMultiply(-l2, 1000), -l1);
/*     */       }
/* 146 */       return FieldUtils.safeAdd(FieldUtils.safeMultiply(l2, 1000), l1);
/*     */     } 
/*     */     throw new IllegalArgumentException("Invalid format: \"" + str1 + '"');
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
/*     */   public void setInto(ReadWritablePeriod paramReadWritablePeriod, Object paramObject, Chronology paramChronology) {
/* 162 */     String str = (String)paramObject;
/* 163 */     PeriodFormatter periodFormatter = ISOPeriodFormat.standard();
/* 164 */     paramReadWritablePeriod.clear();
/* 165 */     int i = periodFormatter.parseInto(paramReadWritablePeriod, str, 0);
/* 166 */     if (i < str.length()) {
/* 167 */       if (i < 0)
/*     */       {
/* 169 */         periodFormatter.withParseType(paramReadWritablePeriod.getPeriodType()).parseMutablePeriod(str);
/*     */       }
/* 171 */       throw new IllegalArgumentException("Invalid format: \"" + str + '"');
/*     */     } 
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
/*     */   public void setInto(ReadWritableInterval paramReadWritableInterval, Object paramObject, Chronology paramChronology) {
/* 184 */     String str1 = (String)paramObject;
/*     */     
/* 186 */     int i = str1.indexOf('/');
/* 187 */     if (i < 0) {
/* 188 */       throw new IllegalArgumentException("Format requires a '/' separator: " + str1);
/*     */     }
/*     */     
/* 191 */     String str2 = str1.substring(0, i);
/* 192 */     if (str2.length() <= 0) {
/* 193 */       throw new IllegalArgumentException("Format invalid: " + str1);
/*     */     }
/* 195 */     String str3 = str1.substring(i + 1);
/* 196 */     if (str3.length() <= 0) {
/* 197 */       throw new IllegalArgumentException("Format invalid: " + str1);
/*     */     }
/*     */     
/* 200 */     DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeParser();
/* 201 */     dateTimeFormatter = dateTimeFormatter.withChronology(paramChronology);
/* 202 */     PeriodFormatter periodFormatter = ISOPeriodFormat.standard();
/* 203 */     long l1 = 0L, l2 = 0L;
/* 204 */     Period period = null;
/* 205 */     Chronology chronology = null;
/*     */ 
/*     */     
/* 208 */     char c = str2.charAt(0);
/* 209 */     if (c == 'P' || c == 'p') {
/* 210 */       period = periodFormatter.withParseType(getPeriodType(str2)).parsePeriod(str2);
/*     */     } else {
/* 212 */       DateTime dateTime = dateTimeFormatter.parseDateTime(str2);
/* 213 */       l1 = dateTime.getMillis();
/* 214 */       chronology = dateTime.getChronology();
/*     */     } 
/*     */ 
/*     */     
/* 218 */     c = str3.charAt(0);
/* 219 */     if (c == 'P' || c == 'p') {
/* 220 */       if (period != null) {
/* 221 */         throw new IllegalArgumentException("Interval composed of two durations: " + str1);
/*     */       }
/* 223 */       period = periodFormatter.withParseType(getPeriodType(str3)).parsePeriod(str3);
/* 224 */       paramChronology = (paramChronology != null) ? paramChronology : chronology;
/* 225 */       l2 = paramChronology.add((ReadablePeriod)period, l1, 1);
/*     */     } else {
/* 227 */       DateTime dateTime = dateTimeFormatter.parseDateTime(str3);
/* 228 */       l2 = dateTime.getMillis();
/* 229 */       chronology = (chronology != null) ? chronology : dateTime.getChronology();
/* 230 */       paramChronology = (paramChronology != null) ? paramChronology : chronology;
/* 231 */       if (period != null) {
/* 232 */         l1 = paramChronology.add((ReadablePeriod)period, l2, -1);
/*     */       }
/*     */     } 
/*     */     
/* 236 */     paramReadWritableInterval.setInterval(l1, l2);
/* 237 */     paramReadWritableInterval.setChronology(paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getSupportedType() {
/* 247 */     return String.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/StringConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */