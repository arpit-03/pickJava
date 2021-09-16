/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.Duration;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.MutablePeriod;
/*     */ import org.joda.time.PeriodType;
/*     */ import org.joda.time.ReadWritablePeriod;
/*     */ import org.joda.time.ReadableDuration;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.PeriodConverter;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BasePeriod
/*     */   extends AbstractPeriod
/*     */   implements ReadablePeriod, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2110953284060001145L;
/*     */   
/*  57 */   private static final ReadablePeriod DUMMY_PERIOD = new AbstractPeriod() {
/*     */       public int getValue(int param1Int) {
/*  59 */         return 0;
/*     */       }
/*     */       public PeriodType getPeriodType() {
/*  62 */         return PeriodType.time();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final PeriodType iType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] iValues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasePeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, PeriodType paramPeriodType) {
/*  91 */     paramPeriodType = checkPeriodType(paramPeriodType);
/*  92 */     this.iType = paramPeriodType;
/*  93 */     this.iValues = setPeriodInternal(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
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
/*     */   protected BasePeriod(long paramLong1, long paramLong2, PeriodType paramPeriodType, Chronology paramChronology) {
/* 107 */     paramPeriodType = checkPeriodType(paramPeriodType);
/* 108 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 109 */     this.iType = paramPeriodType;
/* 110 */     this.iValues = paramChronology.get(this, paramLong1, paramLong2);
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
/*     */   protected BasePeriod(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2, PeriodType paramPeriodType) {
/* 123 */     paramPeriodType = checkPeriodType(paramPeriodType);
/* 124 */     if (paramReadableInstant1 == null && paramReadableInstant2 == null) {
/* 125 */       this.iType = paramPeriodType;
/* 126 */       this.iValues = new int[size()];
/*     */     } else {
/* 128 */       long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant1);
/* 129 */       long l2 = DateTimeUtils.getInstantMillis(paramReadableInstant2);
/* 130 */       Chronology chronology = DateTimeUtils.getIntervalChronology(paramReadableInstant1, paramReadableInstant2);
/* 131 */       this.iType = paramPeriodType;
/* 132 */       this.iValues = chronology.get(this, l1, l2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasePeriod(ReadablePartial paramReadablePartial1, ReadablePartial paramReadablePartial2, PeriodType paramPeriodType) {
/* 156 */     if (paramReadablePartial1 == null || paramReadablePartial2 == null) {
/* 157 */       throw new IllegalArgumentException("ReadablePartial objects must not be null");
/*     */     }
/* 159 */     if (paramReadablePartial1 instanceof BaseLocal && paramReadablePartial2 instanceof BaseLocal && paramReadablePartial1.getClass() == paramReadablePartial2.getClass()) {
/*     */       
/* 161 */       paramPeriodType = checkPeriodType(paramPeriodType);
/* 162 */       long l1 = ((BaseLocal)paramReadablePartial1).getLocalMillis();
/* 163 */       long l2 = ((BaseLocal)paramReadablePartial2).getLocalMillis();
/* 164 */       Chronology chronology = paramReadablePartial1.getChronology();
/* 165 */       chronology = DateTimeUtils.getChronology(chronology);
/* 166 */       this.iType = paramPeriodType;
/* 167 */       this.iValues = chronology.get(this, l1, l2);
/*     */     } else {
/* 169 */       if (paramReadablePartial1.size() != paramReadablePartial2.size())
/* 170 */         throw new IllegalArgumentException("ReadablePartial objects must have the same set of fields");  byte b;
/*     */       int i;
/* 172 */       for (b = 0, i = paramReadablePartial1.size(); b < i; b++) {
/* 173 */         if (paramReadablePartial1.getFieldType(b) != paramReadablePartial2.getFieldType(b)) {
/* 174 */           throw new IllegalArgumentException("ReadablePartial objects must have the same set of fields");
/*     */         }
/*     */       } 
/* 177 */       if (!DateTimeUtils.isContiguous(paramReadablePartial1)) {
/* 178 */         throw new IllegalArgumentException("ReadablePartial objects must be contiguous");
/*     */       }
/* 180 */       this.iType = checkPeriodType(paramPeriodType);
/* 181 */       Chronology chronology = DateTimeUtils.getChronology(paramReadablePartial1.getChronology()).withUTC();
/* 182 */       this.iValues = chronology.get(this, chronology.set(paramReadablePartial1, 0L), chronology.set(paramReadablePartial2, 0L));
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
/*     */   protected BasePeriod(ReadableInstant paramReadableInstant, ReadableDuration paramReadableDuration, PeriodType paramPeriodType) {
/* 195 */     paramPeriodType = checkPeriodType(paramPeriodType);
/* 196 */     long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 197 */     long l2 = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 198 */     long l3 = FieldUtils.safeAdd(l1, l2);
/* 199 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 200 */     this.iType = paramPeriodType;
/* 201 */     this.iValues = chronology.get(this, l1, l3);
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
/*     */   protected BasePeriod(ReadableDuration paramReadableDuration, ReadableInstant paramReadableInstant, PeriodType paramPeriodType) {
/* 213 */     paramPeriodType = checkPeriodType(paramPeriodType);
/* 214 */     long l1 = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 215 */     long l2 = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 216 */     long l3 = FieldUtils.safeSubtract(l2, l1);
/* 217 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 218 */     this.iType = paramPeriodType;
/* 219 */     this.iValues = chronology.get(this, l3, l2);
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
/*     */   protected BasePeriod(long paramLong) {
/* 235 */     this.iType = PeriodType.standard();
/* 236 */     int[] arrayOfInt = ISOChronology.getInstanceUTC().get(DUMMY_PERIOD, paramLong);
/* 237 */     this.iValues = new int[8];
/* 238 */     System.arraycopy(arrayOfInt, 0, this.iValues, 4, 4);
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
/*     */   protected BasePeriod(long paramLong, PeriodType paramPeriodType, Chronology paramChronology) {
/* 255 */     paramPeriodType = checkPeriodType(paramPeriodType);
/* 256 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 257 */     this.iType = paramPeriodType;
/* 258 */     this.iValues = paramChronology.get(this, paramLong);
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
/*     */   protected BasePeriod(Object paramObject, PeriodType paramPeriodType, Chronology paramChronology) {
/* 272 */     PeriodConverter periodConverter = ConverterManager.getInstance().getPeriodConverter(paramObject);
/* 273 */     paramPeriodType = (paramPeriodType == null) ? periodConverter.getPeriodType(paramObject) : paramPeriodType;
/* 274 */     paramPeriodType = checkPeriodType(paramPeriodType);
/* 275 */     this.iType = paramPeriodType;
/* 276 */     if (this instanceof ReadWritablePeriod) {
/* 277 */       this.iValues = new int[size()];
/* 278 */       paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 279 */       periodConverter.setInto((ReadWritablePeriod)this, paramObject, paramChronology);
/*     */     } else {
/* 281 */       this.iValues = (new MutablePeriod(paramObject, paramPeriodType, paramChronology)).getValues();
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
/*     */   protected BasePeriod(int[] paramArrayOfint, PeriodType paramPeriodType) {
/* 294 */     this.iType = paramPeriodType;
/* 295 */     this.iValues = paramArrayOfint;
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
/*     */   protected PeriodType checkPeriodType(PeriodType paramPeriodType) {
/* 308 */     return DateTimeUtils.getPeriodType(paramPeriodType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType getPeriodType() {
/* 318 */     return this.iType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue(int paramInt) {
/* 329 */     return this.iValues[paramInt];
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
/*     */   public Duration toDurationFrom(ReadableInstant paramReadableInstant) {
/* 350 */     long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 351 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 352 */     long l2 = chronology.add(this, l1, 1);
/* 353 */     return new Duration(l1, l2);
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
/*     */   public Duration toDurationTo(ReadableInstant paramReadableInstant) {
/* 374 */     long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 375 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 376 */     long l2 = chronology.add(this, l1, -1);
/* 377 */     return new Duration(l2, l1);
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
/*     */   private void checkAndUpdate(DurationFieldType paramDurationFieldType, int[] paramArrayOfint, int paramInt) {
/* 390 */     int i = indexOf(paramDurationFieldType);
/* 391 */     if (i == -1) {
/* 392 */       if (paramInt != 0) {
/* 393 */         throw new IllegalArgumentException("Period does not support field '" + paramDurationFieldType.getName() + "'");
/*     */       }
/*     */     } else {
/*     */       
/* 397 */       paramArrayOfint[i] = paramInt;
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
/*     */   protected void setPeriod(ReadablePeriod paramReadablePeriod) {
/* 409 */     if (paramReadablePeriod == null) {
/* 410 */       setValues(new int[size()]);
/*     */     } else {
/* 412 */       setPeriodInternal(paramReadablePeriod);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPeriodInternal(ReadablePeriod paramReadablePeriod) {
/* 420 */     int[] arrayOfInt = new int[size()]; byte b; int i;
/* 421 */     for (b = 0, i = paramReadablePeriod.size(); b < i; b++) {
/* 422 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/* 423 */       int j = paramReadablePeriod.getValue(b);
/* 424 */       checkAndUpdate(durationFieldType, arrayOfInt, j);
/*     */     } 
/* 426 */     setValues(arrayOfInt);
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
/*     */   protected void setPeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 444 */     int[] arrayOfInt = setPeriodInternal(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
/* 445 */     setValues(arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] setPeriodInternal(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 453 */     int[] arrayOfInt = new int[size()];
/* 454 */     checkAndUpdate(DurationFieldType.years(), arrayOfInt, paramInt1);
/* 455 */     checkAndUpdate(DurationFieldType.months(), arrayOfInt, paramInt2);
/* 456 */     checkAndUpdate(DurationFieldType.weeks(), arrayOfInt, paramInt3);
/* 457 */     checkAndUpdate(DurationFieldType.days(), arrayOfInt, paramInt4);
/* 458 */     checkAndUpdate(DurationFieldType.hours(), arrayOfInt, paramInt5);
/* 459 */     checkAndUpdate(DurationFieldType.minutes(), arrayOfInt, paramInt6);
/* 460 */     checkAndUpdate(DurationFieldType.seconds(), arrayOfInt, paramInt7);
/* 461 */     checkAndUpdate(DurationFieldType.millis(), arrayOfInt, paramInt8);
/* 462 */     return arrayOfInt;
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
/*     */   protected void setField(DurationFieldType paramDurationFieldType, int paramInt) {
/* 474 */     setFieldInto(this.iValues, paramDurationFieldType, paramInt);
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
/*     */   protected void setFieldInto(int[] paramArrayOfint, DurationFieldType paramDurationFieldType, int paramInt) {
/* 486 */     int i = indexOf(paramDurationFieldType);
/* 487 */     if (i == -1) {
/* 488 */       if (paramInt != 0 || paramDurationFieldType == null) {
/* 489 */         throw new IllegalArgumentException("Period does not support field '" + paramDurationFieldType + "'");
/*     */       }
/*     */     } else {
/*     */       
/* 493 */       paramArrayOfint[i] = paramInt;
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
/*     */   protected void addField(DurationFieldType paramDurationFieldType, int paramInt) {
/* 505 */     addFieldInto(this.iValues, paramDurationFieldType, paramInt);
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
/*     */   protected void addFieldInto(int[] paramArrayOfint, DurationFieldType paramDurationFieldType, int paramInt) {
/* 517 */     int i = indexOf(paramDurationFieldType);
/* 518 */     if (i == -1) {
/* 519 */       if (paramInt != 0 || paramDurationFieldType == null) {
/* 520 */         throw new IllegalArgumentException("Period does not support field '" + paramDurationFieldType + "'");
/*     */       }
/*     */     } else {
/*     */       
/* 524 */       paramArrayOfint[i] = FieldUtils.safeAdd(paramArrayOfint[i], paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mergePeriod(ReadablePeriod paramReadablePeriod) {
/* 535 */     if (paramReadablePeriod != null) {
/* 536 */       setValues(mergePeriodInto(getValues(), paramReadablePeriod));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] mergePeriodInto(int[] paramArrayOfint, ReadablePeriod paramReadablePeriod) {
/*     */     byte b;
/*     */     int i;
/* 549 */     for (b = 0, i = paramReadablePeriod.size(); b < i; b++) {
/* 550 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/* 551 */       int j = paramReadablePeriod.getValue(b);
/* 552 */       checkAndUpdate(durationFieldType, paramArrayOfint, j);
/*     */     } 
/* 554 */     return paramArrayOfint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addPeriod(ReadablePeriod paramReadablePeriod) {
/* 564 */     if (paramReadablePeriod != null) {
/* 565 */       setValues(addPeriodInto(getValues(), paramReadablePeriod));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] addPeriodInto(int[] paramArrayOfint, ReadablePeriod paramReadablePeriod) {
/*     */     byte b;
/*     */     int i;
/* 578 */     for (b = 0, i = paramReadablePeriod.size(); b < i; b++) {
/* 579 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/* 580 */       int j = paramReadablePeriod.getValue(b);
/* 581 */       if (j != 0) {
/* 582 */         int k = indexOf(durationFieldType);
/* 583 */         if (k == -1) {
/* 584 */           throw new IllegalArgumentException("Period does not support field '" + durationFieldType.getName() + "'");
/*     */         }
/*     */         
/* 587 */         paramArrayOfint[k] = FieldUtils.safeAdd(getValue(k), j);
/*     */       } 
/*     */     } 
/*     */     
/* 591 */     return paramArrayOfint;
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
/*     */   protected void setValue(int paramInt1, int paramInt2) {
/* 603 */     this.iValues[paramInt1] = paramInt2;
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
/*     */   protected void setValues(int[] paramArrayOfint) {
/* 616 */     System.arraycopy(paramArrayOfint, 0, this.iValues, 0, this.iValues.length);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/BasePeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */