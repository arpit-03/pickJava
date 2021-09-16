/*     */ package org.joda.time.format;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.IllegalFieldValueException;
/*     */ import org.joda.time.IllegalInstantException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateTimeParserBucket
/*     */ {
/*     */   private final Chronology iChrono;
/*     */   private final long iMillis;
/*     */   private final Locale iLocale;
/*     */   private final int iDefaultYear;
/*     */   private final DateTimeZone iDefaultZone;
/*     */   private final Integer iDefaultPivotYear;
/*     */   private DateTimeZone iZone;
/*     */   private Integer iOffset;
/*     */   private Integer iPivotYear;
/*     */   private SavedField[] iSavedFields;
/*     */   private int iSavedFieldsCount;
/*     */   private boolean iSavedFieldsShared;
/*     */   private Object iSavedState;
/*     */   
/*     */   @Deprecated
/*     */   public DateTimeParserBucket(long paramLong, Chronology paramChronology, Locale paramLocale) {
/*  94 */     this(paramLong, paramChronology, paramLocale, null, 2000);
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
/*     */   @Deprecated
/*     */   public DateTimeParserBucket(long paramLong, Chronology paramChronology, Locale paramLocale, Integer paramInteger) {
/* 110 */     this(paramLong, paramChronology, paramLocale, paramInteger, 2000);
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
/*     */   public DateTimeParserBucket(long paramLong, Chronology paramChronology, Locale paramLocale, Integer paramInteger, int paramInt) {
/* 127 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 128 */     this.iMillis = paramLong;
/* 129 */     this.iDefaultZone = paramChronology.getZone();
/* 130 */     this.iChrono = paramChronology.withUTC();
/* 131 */     this.iLocale = (paramLocale == null) ? Locale.getDefault() : paramLocale;
/* 132 */     this.iDefaultYear = paramInt;
/* 133 */     this.iDefaultPivotYear = paramInteger;
/*     */     
/* 135 */     this.iZone = this.iDefaultZone;
/* 136 */     this.iPivotYear = this.iDefaultPivotYear;
/* 137 */     this.iSavedFields = new SavedField[8];
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
/*     */   public void reset() {
/* 150 */     this.iZone = this.iDefaultZone;
/* 151 */     this.iOffset = null;
/* 152 */     this.iPivotYear = this.iDefaultPivotYear;
/* 153 */     this.iSavedFieldsCount = 0;
/* 154 */     this.iSavedFieldsShared = false;
/* 155 */     this.iSavedState = null;
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
/*     */   public long parseMillis(DateTimeParser paramDateTimeParser, CharSequence paramCharSequence) {
/* 174 */     reset();
/* 175 */     return doParseMillis(DateTimeParserInternalParser.of(paramDateTimeParser), paramCharSequence);
/*     */   }
/*     */   
/*     */   long doParseMillis(InternalParser paramInternalParser, CharSequence paramCharSequence) {
/* 179 */     int i = paramInternalParser.parseInto(this, paramCharSequence, 0);
/* 180 */     if (i >= 0) {
/* 181 */       if (i >= paramCharSequence.length()) {
/* 182 */         return computeMillis(true, paramCharSequence);
/*     */       }
/*     */     } else {
/* 185 */       i ^= 0xFFFFFFFF;
/*     */     } 
/* 187 */     throw new IllegalArgumentException(FormatUtils.createErrorMessage(paramCharSequence.toString(), i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology getChronology() {
/* 195 */     return this.iChrono;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 205 */     return this.iLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeZone getZone() {
/* 213 */     return this.iZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZone(DateTimeZone paramDateTimeZone) {
/* 220 */     this.iSavedState = null;
/* 221 */     this.iZone = paramDateTimeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getOffset() {
/* 231 */     return (this.iOffset != null) ? this.iOffset.intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getOffsetInteger() {
/* 238 */     return this.iOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setOffset(int paramInt) {
/* 247 */     this.iSavedState = null;
/* 248 */     this.iOffset = Integer.valueOf(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOffset(Integer paramInteger) {
/* 255 */     this.iSavedState = null;
/* 256 */     this.iOffset = paramInteger;
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
/*     */   public Integer getPivotYear() {
/* 273 */     return this.iPivotYear;
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
/*     */   @Deprecated
/*     */   public void setPivotYear(Integer paramInteger) {
/* 288 */     this.iPivotYear = paramInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveField(DateTimeField paramDateTimeField, int paramInt) {
/* 299 */     obtainSaveField().init(paramDateTimeField, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveField(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/* 309 */     obtainSaveField().init(paramDateTimeFieldType.getField(this.iChrono), paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveField(DateTimeFieldType paramDateTimeFieldType, String paramString, Locale paramLocale) {
/* 320 */     obtainSaveField().init(paramDateTimeFieldType.getField(this.iChrono), paramString, paramLocale);
/*     */   }
/*     */   
/*     */   private SavedField obtainSaveField() {
/* 324 */     SavedField[] arrayOfSavedField = this.iSavedFields;
/* 325 */     int i = this.iSavedFieldsCount;
/*     */     
/* 327 */     if (i == arrayOfSavedField.length || this.iSavedFieldsShared) {
/*     */       
/* 329 */       SavedField[] arrayOfSavedField1 = new SavedField[(i == arrayOfSavedField.length) ? (i * 2) : arrayOfSavedField.length];
/*     */       
/* 331 */       System.arraycopy(arrayOfSavedField, 0, arrayOfSavedField1, 0, i);
/* 332 */       this.iSavedFields = arrayOfSavedField = arrayOfSavedField1;
/* 333 */       this.iSavedFieldsShared = false;
/*     */     } 
/*     */     
/* 336 */     this.iSavedState = null;
/* 337 */     SavedField savedField = arrayOfSavedField[i];
/* 338 */     if (savedField == null) {
/* 339 */       savedField = arrayOfSavedField[i] = new SavedField();
/*     */     }
/* 341 */     this.iSavedFieldsCount = i + 1;
/* 342 */     return savedField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object saveState() {
/* 353 */     if (this.iSavedState == null) {
/* 354 */       this.iSavedState = new SavedState();
/*     */     }
/* 356 */     return this.iSavedState;
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
/*     */   public boolean restoreState(Object paramObject) {
/* 368 */     if (paramObject instanceof SavedState && (
/* 369 */       (SavedState)paramObject).restoreState(this)) {
/* 370 */       this.iSavedState = paramObject;
/* 371 */       return true;
/*     */     } 
/*     */     
/* 374 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long computeMillis() {
/* 385 */     return computeMillis(false, (CharSequence)null);
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
/*     */   public long computeMillis(boolean paramBoolean) {
/* 397 */     return computeMillis(paramBoolean, (CharSequence)null);
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
/*     */   public long computeMillis(boolean paramBoolean, String paramString) {
/* 411 */     return computeMillis(paramBoolean, paramString);
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
/*     */   public long computeMillis(boolean paramBoolean, CharSequence paramCharSequence) {
/* 425 */     SavedField[] arrayOfSavedField = this.iSavedFields;
/* 426 */     int i = this.iSavedFieldsCount;
/* 427 */     if (this.iSavedFieldsShared) {
/*     */       
/* 429 */       this.iSavedFields = arrayOfSavedField = (SavedField[])this.iSavedFields.clone();
/* 430 */       this.iSavedFieldsShared = false;
/*     */     } 
/* 432 */     sort(arrayOfSavedField, i);
/* 433 */     if (i > 0) {
/*     */       
/* 435 */       DurationField durationField1 = DurationFieldType.months().getField(this.iChrono);
/* 436 */       DurationField durationField2 = DurationFieldType.days().getField(this.iChrono);
/* 437 */       DurationField durationField3 = (arrayOfSavedField[0]).iField.getDurationField();
/* 438 */       if (compareReverse(durationField3, durationField1) >= 0 && compareReverse(durationField3, durationField2) <= 0) {
/* 439 */         saveField(DateTimeFieldType.year(), this.iDefaultYear);
/* 440 */         return computeMillis(paramBoolean, paramCharSequence);
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     long l = this.iMillis; try {
/*     */       byte b;
/* 446 */       for (b = 0; b < i; b++) {
/* 447 */         l = arrayOfSavedField[b].set(l, paramBoolean);
/*     */       }
/* 449 */       if (paramBoolean) {
/* 450 */         for (b = 0; b < i; b++) {
/* 451 */           l = arrayOfSavedField[b].set(l, (b == i - 1));
/*     */         }
/*     */       }
/* 454 */     } catch (IllegalFieldValueException illegalFieldValueException) {
/* 455 */       if (paramCharSequence != null) {
/* 456 */         illegalFieldValueException.prependMessage("Cannot parse \"" + paramCharSequence + '"');
/*     */       }
/* 458 */       throw illegalFieldValueException;
/*     */     } 
/*     */     
/* 461 */     if (this.iOffset != null) {
/* 462 */       l -= this.iOffset.intValue();
/* 463 */     } else if (this.iZone != null) {
/* 464 */       int j = this.iZone.getOffsetFromLocal(l);
/* 465 */       l -= j;
/* 466 */       if (j != this.iZone.getOffset(l)) {
/* 467 */         String str = "Illegal instant due to time zone offset transition (" + this.iZone + ')';
/* 468 */         if (paramCharSequence != null) {
/* 469 */           str = "Cannot parse \"" + paramCharSequence + "\": " + str;
/*     */         }
/* 471 */         throw new IllegalInstantException(str);
/*     */       } 
/*     */     } 
/*     */     
/* 475 */     return l;
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
/*     */   private static void sort(SavedField[] paramArrayOfSavedField, int paramInt) {
/* 497 */     if (paramInt > 10) {
/* 498 */       Arrays.sort((Object[])paramArrayOfSavedField, 0, paramInt);
/*     */     } else {
/* 500 */       for (byte b = 0; b < paramInt; b++) {
/* 501 */         for (byte b1 = b; b1 > 0 && paramArrayOfSavedField[b1 - 1].compareTo(paramArrayOfSavedField[b1]) > 0; b1--) {
/* 502 */           SavedField savedField = paramArrayOfSavedField[b1];
/* 503 */           paramArrayOfSavedField[b1] = paramArrayOfSavedField[b1 - 1];
/* 504 */           paramArrayOfSavedField[b1 - 1] = savedField;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class SavedState
/*     */   {
/* 517 */     final DateTimeZone iZone = DateTimeParserBucket.this.iZone;
/* 518 */     final Integer iOffset = DateTimeParserBucket.this.iOffset;
/* 519 */     final DateTimeParserBucket.SavedField[] iSavedFields = DateTimeParserBucket.this.iSavedFields;
/* 520 */     final int iSavedFieldsCount = DateTimeParserBucket.this.iSavedFieldsCount;
/*     */ 
/*     */     
/*     */     boolean restoreState(DateTimeParserBucket param1DateTimeParserBucket) {
/* 524 */       if (param1DateTimeParserBucket != DateTimeParserBucket.this)
/*     */       {
/* 526 */         return false;
/*     */       }
/* 528 */       param1DateTimeParserBucket.iZone = this.iZone;
/* 529 */       param1DateTimeParserBucket.iOffset = this.iOffset;
/* 530 */       param1DateTimeParserBucket.iSavedFields = this.iSavedFields;
/* 531 */       if (this.iSavedFieldsCount < param1DateTimeParserBucket.iSavedFieldsCount)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 536 */         param1DateTimeParserBucket.iSavedFieldsShared = true;
/*     */       }
/* 538 */       param1DateTimeParserBucket.iSavedFieldsCount = this.iSavedFieldsCount;
/* 539 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class SavedField
/*     */     implements Comparable<SavedField>
/*     */   {
/*     */     DateTimeField iField;
/*     */     int iValue;
/*     */     String iText;
/*     */     Locale iLocale;
/*     */     
/*     */     void init(DateTimeField param1DateTimeField, int param1Int) {
/* 553 */       this.iField = param1DateTimeField;
/* 554 */       this.iValue = param1Int;
/* 555 */       this.iText = null;
/* 556 */       this.iLocale = null;
/*     */     }
/*     */     
/*     */     void init(DateTimeField param1DateTimeField, String param1String, Locale param1Locale) {
/* 560 */       this.iField = param1DateTimeField;
/* 561 */       this.iValue = 0;
/* 562 */       this.iText = param1String;
/* 563 */       this.iLocale = param1Locale;
/*     */     }
/*     */     
/*     */     long set(long param1Long, boolean param1Boolean) {
/* 567 */       if (this.iText == null) {
/* 568 */         param1Long = this.iField.setExtended(param1Long, this.iValue);
/*     */       } else {
/* 570 */         param1Long = this.iField.set(param1Long, this.iText, this.iLocale);
/*     */       } 
/* 572 */       if (param1Boolean) {
/* 573 */         param1Long = this.iField.roundFloor(param1Long);
/*     */       }
/* 575 */       return param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(SavedField param1SavedField) {
/* 584 */       DateTimeField dateTimeField = param1SavedField.iField;
/* 585 */       int i = DateTimeParserBucket.compareReverse(this.iField.getRangeDurationField(), dateTimeField.getRangeDurationField());
/*     */       
/* 587 */       if (i != 0) {
/* 588 */         return i;
/*     */       }
/* 590 */       return DateTimeParserBucket.compareReverse(this.iField.getDurationField(), dateTimeField.getDurationField());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static int compareReverse(DurationField paramDurationField1, DurationField paramDurationField2) {
/* 596 */     if (paramDurationField1 == null || !paramDurationField1.isSupported()) {
/* 597 */       if (paramDurationField2 == null || !paramDurationField2.isSupported()) {
/* 598 */         return 0;
/*     */       }
/* 600 */       return -1;
/*     */     } 
/* 602 */     if (paramDurationField2 == null || !paramDurationField2.isSupported()) {
/* 603 */       return 1;
/*     */     }
/* 605 */     return -paramDurationField1.compareTo(paramDurationField2);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimeParserBucket.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */