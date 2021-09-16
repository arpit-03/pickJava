/*     */ package org.joda.time.base;
/*     */ 
/*     */ import org.joda.convert.ToString;
/*     */ import org.joda.time.Duration;
/*     */ import org.joda.time.Period;
/*     */ import org.joda.time.ReadableDuration;
/*     */ import org.joda.time.format.FormatUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDuration
/*     */   implements ReadableDuration
/*     */ {
/*     */   public Duration toDuration() {
/*  54 */     return new Duration(getMillis());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Period toPeriod() {
/*  80 */     return new Period(getMillis());
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
/*     */   public int compareTo(ReadableDuration paramReadableDuration) {
/*  93 */     long l1 = getMillis();
/*  94 */     long l2 = paramReadableDuration.getMillis();
/*     */ 
/*     */     
/*  97 */     if (l1 < l2) {
/*  98 */       return -1;
/*     */     }
/* 100 */     if (l1 > l2) {
/* 101 */       return 1;
/*     */     }
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqual(ReadableDuration paramReadableDuration) {
/*     */     Duration duration;
/* 113 */     if (paramReadableDuration == null) {
/* 114 */       duration = Duration.ZERO;
/*     */     }
/* 116 */     return (compareTo((ReadableDuration)duration) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLongerThan(ReadableDuration paramReadableDuration) {
/*     */     Duration duration;
/* 126 */     if (paramReadableDuration == null) {
/* 127 */       duration = Duration.ZERO;
/*     */     }
/* 129 */     return (compareTo((ReadableDuration)duration) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShorterThan(ReadableDuration paramReadableDuration) {
/*     */     Duration duration;
/* 139 */     if (paramReadableDuration == null) {
/* 140 */       duration = Duration.ZERO;
/*     */     }
/* 142 */     return (compareTo((ReadableDuration)duration) < 0);
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
/*     */   public boolean equals(Object paramObject) {
/* 154 */     if (this == paramObject) {
/* 155 */       return true;
/*     */     }
/* 157 */     if (!(paramObject instanceof ReadableDuration)) {
/* 158 */       return false;
/*     */     }
/* 160 */     ReadableDuration readableDuration = (ReadableDuration)paramObject;
/* 161 */     return (getMillis() == readableDuration.getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 171 */     long l = getMillis();
/* 172 */     return (int)(l ^ l >>> 32L);
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
/*     */   @ToString
/*     */   public String toString() {
/* 189 */     long l = getMillis();
/* 190 */     StringBuffer stringBuffer = new StringBuffer();
/* 191 */     stringBuffer.append("PT");
/* 192 */     boolean bool = (l < 0L) ? true : false;
/* 193 */     FormatUtils.appendUnpaddedInteger(stringBuffer, l);
/* 194 */     while (stringBuffer.length() < (bool ? 7 : 6)) {
/* 195 */       stringBuffer.insert(bool ? 3 : 2, "0");
/*     */     }
/* 197 */     if (l / 1000L * 1000L == l) {
/* 198 */       stringBuffer.setLength(stringBuffer.length() - 3);
/*     */     } else {
/* 200 */       stringBuffer.insert(stringBuffer.length() - 3, ".");
/*     */     } 
/* 202 */     stringBuffer.append('S');
/* 203 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/AbstractDuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */