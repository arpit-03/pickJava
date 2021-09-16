/*     */ package org.joda.time.base;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractPartial
/*     */   implements ReadablePartial, Comparable<ReadablePartial>
/*     */ {
/*     */   public DateTimeFieldType getFieldType(int paramInt) {
/*  79 */     return getField(paramInt, getChronology()).getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeFieldType[] getFieldTypes() {
/*  90 */     DateTimeFieldType[] arrayOfDateTimeFieldType = new DateTimeFieldType[size()];
/*  91 */     for (byte b = 0; b < arrayOfDateTimeFieldType.length; b++) {
/*  92 */       arrayOfDateTimeFieldType[b] = getFieldType(b);
/*     */     }
/*  94 */     return arrayOfDateTimeFieldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField getField(int paramInt) {
/* 105 */     return getField(paramInt, getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField[] getFields() {
/* 116 */     DateTimeField[] arrayOfDateTimeField = new DateTimeField[size()];
/* 117 */     for (byte b = 0; b < arrayOfDateTimeField.length; b++) {
/* 118 */       arrayOfDateTimeField[b] = getField(b);
/*     */     }
/* 120 */     return arrayOfDateTimeField;
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
/* 132 */     int[] arrayOfInt = new int[size()];
/* 133 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 134 */       arrayOfInt[b] = getValue(b);
/*     */     }
/* 136 */     return arrayOfInt;
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
/*     */   public int get(DateTimeFieldType paramDateTimeFieldType) {
/* 150 */     return getValue(indexOfSupported(paramDateTimeFieldType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(DateTimeFieldType paramDateTimeFieldType) {
/* 160 */     return (indexOf(paramDateTimeFieldType) != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(DateTimeFieldType paramDateTimeFieldType) {
/*     */     byte b;
/*     */     int i;
/* 170 */     for (b = 0, i = size(); b < i; b++) {
/* 171 */       if (getFieldType(b) == paramDateTimeFieldType) {
/* 172 */         return b;
/*     */       }
/*     */     } 
/* 175 */     return -1;
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
/*     */   protected int indexOfSupported(DateTimeFieldType paramDateTimeFieldType) {
/* 187 */     int i = indexOf(paramDateTimeFieldType);
/* 188 */     if (i == -1) {
/* 189 */       throw new IllegalArgumentException("Field '" + paramDateTimeFieldType + "' is not supported");
/*     */     }
/* 191 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int indexOf(DurationFieldType paramDurationFieldType) {
/*     */     byte b;
/*     */     int i;
/* 202 */     for (b = 0, i = size(); b < i; b++) {
/* 203 */       if (getFieldType(b).getDurationType() == paramDurationFieldType) {
/* 204 */         return b;
/*     */       }
/*     */     } 
/* 207 */     return -1;
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
/*     */   protected int indexOfSupported(DurationFieldType paramDurationFieldType) {
/* 219 */     int i = indexOf(paramDurationFieldType);
/* 220 */     if (i == -1) {
/* 221 */       throw new IllegalArgumentException("Field '" + paramDurationFieldType + "' is not supported");
/*     */     }
/* 223 */     return i;
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
/*     */   public DateTime toDateTime(ReadableInstant paramReadableInstant) {
/* 240 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 241 */     long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 242 */     long l2 = chronology.set(this, l1);
/* 243 */     return new DateTime(l2, chronology);
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
/* 255 */     if (this == paramObject) {
/* 256 */       return true;
/*     */     }
/* 258 */     if (!(paramObject instanceof ReadablePartial)) {
/* 259 */       return false;
/*     */     }
/* 261 */     ReadablePartial readablePartial = (ReadablePartial)paramObject;
/* 262 */     if (size() != readablePartial.size())
/* 263 */       return false;  byte b;
/*     */     int i;
/* 265 */     for (b = 0, i = size(); b < i; b++) {
/* 266 */       if (getValue(b) != readablePartial.getValue(b) || getFieldType(b) != readablePartial.getFieldType(b)) {
/* 267 */         return false;
/*     */       }
/*     */     } 
/* 270 */     return FieldUtils.equals(getChronology(), readablePartial.getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 280 */     int i = 157; byte b; int j;
/* 281 */     for (b = 0, j = size(); b < j; b++) {
/* 282 */       i = 23 * i + getValue(b);
/* 283 */       i = 23 * i + getFieldType(b).hashCode();
/*     */     } 
/* 285 */     i += getChronology().hashCode();
/* 286 */     return i;
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
/*     */   public int compareTo(ReadablePartial paramReadablePartial) {
/* 311 */     if (this == paramReadablePartial) {
/* 312 */       return 0;
/*     */     }
/* 314 */     if (size() != paramReadablePartial.size())
/* 315 */       throw new ClassCastException("ReadablePartial objects must have matching field types");  byte b;
/*     */     int i;
/* 317 */     for (b = 0, i = size(); b < i; b++) {
/* 318 */       if (getFieldType(b) != paramReadablePartial.getFieldType(b)) {
/* 319 */         throw new ClassCastException("ReadablePartial objects must have matching field types");
/*     */       }
/*     */     } 
/*     */     
/* 323 */     for (b = 0, i = size(); b < i; b++) {
/* 324 */       if (getValue(b) > paramReadablePartial.getValue(b)) {
/* 325 */         return 1;
/*     */       }
/* 327 */       if (getValue(b) < paramReadablePartial.getValue(b)) {
/* 328 */         return -1;
/*     */       }
/*     */     } 
/* 331 */     return 0;
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
/*     */   public boolean isAfter(ReadablePartial paramReadablePartial) {
/* 350 */     if (paramReadablePartial == null) {
/* 351 */       throw new IllegalArgumentException("Partial cannot be null");
/*     */     }
/* 353 */     return (compareTo(paramReadablePartial) > 0);
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
/*     */   public boolean isBefore(ReadablePartial paramReadablePartial) {
/* 372 */     if (paramReadablePartial == null) {
/* 373 */       throw new IllegalArgumentException("Partial cannot be null");
/*     */     }
/* 375 */     return (compareTo(paramReadablePartial) < 0);
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
/*     */   public boolean isEqual(ReadablePartial paramReadablePartial) {
/* 394 */     if (paramReadablePartial == null) {
/* 395 */       throw new IllegalArgumentException("Partial cannot be null");
/*     */     }
/* 397 */     return (compareTo(paramReadablePartial) == 0);
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
/*     */   public String toString(DateTimeFormatter paramDateTimeFormatter) {
/* 409 */     if (paramDateTimeFormatter == null) {
/* 410 */       return toString();
/*     */     }
/* 412 */     return paramDateTimeFormatter.print(this);
/*     */   }
/*     */   
/*     */   protected abstract DateTimeField getField(int paramInt, Chronology paramChronology);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/AbstractPartial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */