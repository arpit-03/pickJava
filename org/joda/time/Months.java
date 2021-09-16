/*     */ package org.joda.time;
/*     */ 
/*     */ import org.joda.convert.FromString;
/*     */ import org.joda.convert.ToString;
/*     */ import org.joda.time.base.BaseSingleFieldPeriod;
/*     */ import org.joda.time.field.FieldUtils;
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
/*     */ public final class Months
/*     */   extends BaseSingleFieldPeriod
/*     */ {
/*  45 */   public static final Months ZERO = new Months(0);
/*     */   
/*  47 */   public static final Months ONE = new Months(1);
/*     */   
/*  49 */   public static final Months TWO = new Months(2);
/*     */   
/*  51 */   public static final Months THREE = new Months(3);
/*     */   
/*  53 */   public static final Months FOUR = new Months(4);
/*     */   
/*  55 */   public static final Months FIVE = new Months(5);
/*     */   
/*  57 */   public static final Months SIX = new Months(6);
/*     */   
/*  59 */   public static final Months SEVEN = new Months(7);
/*     */   
/*  61 */   public static final Months EIGHT = new Months(8);
/*     */   
/*  63 */   public static final Months NINE = new Months(9);
/*     */   
/*  65 */   public static final Months TEN = new Months(10);
/*     */   
/*  67 */   public static final Months ELEVEN = new Months(11);
/*     */   
/*  69 */   public static final Months TWELVE = new Months(12);
/*     */   
/*  71 */   public static final Months MAX_VALUE = new Months(2147483647);
/*     */   
/*  73 */   public static final Months MIN_VALUE = new Months(-2147483648);
/*     */ 
/*     */   
/*  76 */   private static final PeriodFormatter PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.months());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 87525275727380867L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Months months(int paramInt) {
/*  90 */     switch (paramInt) {
/*     */       case 0:
/*  92 */         return ZERO;
/*     */       case 1:
/*  94 */         return ONE;
/*     */       case 2:
/*  96 */         return TWO;
/*     */       case 3:
/*  98 */         return THREE;
/*     */       case 4:
/* 100 */         return FOUR;
/*     */       case 5:
/* 102 */         return FIVE;
/*     */       case 6:
/* 104 */         return SIX;
/*     */       case 7:
/* 106 */         return SEVEN;
/*     */       case 8:
/* 108 */         return EIGHT;
/*     */       case 9:
/* 110 */         return NINE;
/*     */       case 10:
/* 112 */         return TEN;
/*     */       case 11:
/* 114 */         return ELEVEN;
/*     */       case 12:
/* 116 */         return TWELVE;
/*     */       case 2147483647:
/* 118 */         return MAX_VALUE;
/*     */       case -2147483648:
/* 120 */         return MIN_VALUE;
/*     */     } 
/* 122 */     return new Months(paramInt);
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
/*     */   public static Months monthsBetween(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/* 142 */     int i = BaseSingleFieldPeriod.between(paramReadableInstant1, paramReadableInstant2, DurationFieldType.months());
/* 143 */     return months(i);
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
/*     */   public static Months monthsBetween(ReadablePartial paramReadablePartial1, ReadablePartial paramReadablePartial2) {
/* 163 */     if (paramReadablePartial1 instanceof LocalDate && paramReadablePartial2 instanceof LocalDate) {
/* 164 */       Chronology chronology = DateTimeUtils.getChronology(paramReadablePartial1.getChronology());
/* 165 */       int j = chronology.months().getDifference(((LocalDate)paramReadablePartial2).getLocalMillis(), ((LocalDate)paramReadablePartial1).getLocalMillis());
/*     */       
/* 167 */       return months(j);
/*     */     } 
/* 169 */     int i = BaseSingleFieldPeriod.between(paramReadablePartial1, paramReadablePartial2, (ReadablePeriod)ZERO);
/* 170 */     return months(i);
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
/*     */   public static Months monthsIn(ReadableInterval paramReadableInterval) {
/* 183 */     if (paramReadableInterval == null) {
/* 184 */       return ZERO;
/*     */     }
/* 186 */     int i = BaseSingleFieldPeriod.between(paramReadableInterval.getStart(), paramReadableInterval.getEnd(), DurationFieldType.months());
/* 187 */     return months(i);
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
/*     */   @FromString
/*     */   public static Months parseMonths(String paramString) {
/* 203 */     if (paramString == null) {
/* 204 */       return ZERO;
/*     */     }
/* 206 */     Period period = PARSER.parsePeriod(paramString);
/* 207 */     return months(period.getMonths());
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
/*     */   private Months(int paramInt) {
/* 219 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 228 */     return months(getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationFieldType getFieldType() {
/* 238 */     return DurationFieldType.months();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType getPeriodType() {
/* 247 */     return PeriodType.months();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonths() {
/* 257 */     return getValue();
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
/*     */   public Months plus(int paramInt) {
/* 271 */     if (paramInt == 0) {
/* 272 */       return this;
/*     */     }
/* 274 */     return months(FieldUtils.safeAdd(getValue(), paramInt));
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
/*     */   public Months plus(Months paramMonths) {
/* 287 */     if (paramMonths == null) {
/* 288 */       return this;
/*     */     }
/* 290 */     return plus(paramMonths.getValue());
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
/*     */   public Months minus(int paramInt) {
/* 304 */     return plus(FieldUtils.safeNegate(paramInt));
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
/*     */   public Months minus(Months paramMonths) {
/* 317 */     if (paramMonths == null) {
/* 318 */       return this;
/*     */     }
/* 320 */     return minus(paramMonths.getValue());
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
/*     */   public Months multipliedBy(int paramInt) {
/* 334 */     return months(FieldUtils.safeMultiply(getValue(), paramInt));
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
/*     */   public Months dividedBy(int paramInt) {
/* 348 */     if (paramInt == 1) {
/* 349 */       return this;
/*     */     }
/* 351 */     return months(getValue() / paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Months negated() {
/* 362 */     return months(FieldUtils.safeNegate(getValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGreaterThan(Months paramMonths) {
/* 373 */     if (paramMonths == null) {
/* 374 */       return (getValue() > 0);
/*     */     }
/* 376 */     return (getValue() > paramMonths.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLessThan(Months paramMonths) {
/* 386 */     if (paramMonths == null) {
/* 387 */       return (getValue() < 0);
/*     */     }
/* 389 */     return (getValue() < paramMonths.getValue());
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
/*     */   @ToString
/*     */   public String toString() {
/* 402 */     return "P" + String.valueOf(getValue()) + "M";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/Months.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */