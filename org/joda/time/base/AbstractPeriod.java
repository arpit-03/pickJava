/*     */ package org.joda.time.base;
/*     */ 
/*     */ import org.joda.convert.ToString;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.MutablePeriod;
/*     */ import org.joda.time.Period;
/*     */ import org.joda.time.ReadablePeriod;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractPeriod
/*     */   implements ReadablePeriod
/*     */ {
/*     */   public int size() {
/*  56 */     return getPeriodType().size();
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
/*     */   public DurationFieldType getFieldType(int paramInt) {
/*  68 */     return getPeriodType().getFieldType(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationFieldType[] getFieldTypes() {
/*  79 */     DurationFieldType[] arrayOfDurationFieldType = new DurationFieldType[size()];
/*  80 */     for (byte b = 0; b < arrayOfDurationFieldType.length; b++) {
/*  81 */       arrayOfDurationFieldType[b] = getFieldType(b);
/*     */     }
/*  83 */     return arrayOfDurationFieldType;
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
/*     */   public int[] getValues() {
/*  95 */     int[] arrayOfInt = new int[size()];
/*  96 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  97 */       arrayOfInt[b] = getValue(b);
/*     */     }
/*  99 */     return arrayOfInt;
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
/*     */   public int get(DurationFieldType paramDurationFieldType) {
/* 113 */     int i = indexOf(paramDurationFieldType);
/* 114 */     if (i == -1) {
/* 115 */       return 0;
/*     */     }
/* 117 */     return getValue(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(DurationFieldType paramDurationFieldType) {
/* 127 */     return getPeriodType().isSupported(paramDurationFieldType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(DurationFieldType paramDurationFieldType) {
/* 137 */     return getPeriodType().indexOf(paramDurationFieldType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Period toPeriod() {
/* 147 */     return new Period(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutablePeriod toMutablePeriod() {
/* 158 */     return new MutablePeriod(this);
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
/*     */   public boolean equals(Object paramObject) {
/* 183 */     if (this == paramObject) {
/* 184 */       return true;
/*     */     }
/* 186 */     if (!(paramObject instanceof ReadablePeriod)) {
/* 187 */       return false;
/*     */     }
/* 189 */     ReadablePeriod readablePeriod = (ReadablePeriod)paramObject;
/* 190 */     if (size() != readablePeriod.size())
/* 191 */       return false;  byte b;
/*     */     int i;
/* 193 */     for (b = 0, i = size(); b < i; b++) {
/* 194 */       if (getValue(b) != readablePeriod.getValue(b) || getFieldType(b) != readablePeriod.getFieldType(b)) {
/* 195 */         return false;
/*     */       }
/*     */     } 
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 207 */     int i = 17; byte b; int j;
/* 208 */     for (b = 0, j = size(); b < j; b++) {
/* 209 */       i = 27 * i + getValue(b);
/* 210 */       i = 27 * i + getFieldType(b).hashCode();
/*     */     } 
/* 212 */     return i;
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
/*     */   @ToString
/*     */   public String toString() {
/* 228 */     return ISOPeriodFormat.standard().print(this);
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
/*     */   public String toString(PeriodFormatter paramPeriodFormatter) {
/* 240 */     if (paramPeriodFormatter == null) {
/* 241 */       return toString();
/*     */     }
/* 243 */     return paramPeriodFormatter.print(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/AbstractPeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */