/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DateTimeFieldType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -42615285973990L;
/*     */   static final byte ERA = 1;
/*     */   static final byte YEAR_OF_ERA = 2;
/*     */   static final byte CENTURY_OF_ERA = 3;
/*     */   static final byte YEAR_OF_CENTURY = 4;
/*     */   static final byte YEAR = 5;
/*     */   static final byte DAY_OF_YEAR = 6;
/*     */   static final byte MONTH_OF_YEAR = 7;
/*     */   static final byte DAY_OF_MONTH = 8;
/*     */   static final byte WEEKYEAR_OF_CENTURY = 9;
/*     */   static final byte WEEKYEAR = 10;
/*     */   static final byte WEEK_OF_WEEKYEAR = 11;
/*     */   static final byte DAY_OF_WEEK = 12;
/*     */   static final byte HALFDAY_OF_DAY = 13;
/*     */   static final byte HOUR_OF_HALFDAY = 14;
/*     */   static final byte CLOCKHOUR_OF_HALFDAY = 15;
/*     */   static final byte CLOCKHOUR_OF_DAY = 16;
/*     */   static final byte HOUR_OF_DAY = 17;
/*     */   static final byte MINUTE_OF_DAY = 18;
/*     */   static final byte MINUTE_OF_HOUR = 19;
/*     */   static final byte SECOND_OF_DAY = 20;
/*     */   static final byte SECOND_OF_MINUTE = 21;
/*     */   static final byte MILLIS_OF_DAY = 22;
/*     */   static final byte MILLIS_OF_SECOND = 23;
/*  73 */   private static final DateTimeFieldType ERA_TYPE = new StandardDateTimeFieldType("era", (byte)1, DurationFieldType.eras(), null);
/*     */ 
/*     */   
/*  76 */   private static final DateTimeFieldType YEAR_OF_ERA_TYPE = new StandardDateTimeFieldType("yearOfEra", (byte)2, DurationFieldType.years(), DurationFieldType.eras());
/*     */ 
/*     */   
/*  79 */   private static final DateTimeFieldType CENTURY_OF_ERA_TYPE = new StandardDateTimeFieldType("centuryOfEra", (byte)3, DurationFieldType.centuries(), DurationFieldType.eras());
/*     */ 
/*     */   
/*  82 */   private static final DateTimeFieldType YEAR_OF_CENTURY_TYPE = new StandardDateTimeFieldType("yearOfCentury", (byte)4, DurationFieldType.years(), DurationFieldType.centuries());
/*     */ 
/*     */   
/*  85 */   private static final DateTimeFieldType YEAR_TYPE = new StandardDateTimeFieldType("year", (byte)5, DurationFieldType.years(), null);
/*     */ 
/*     */   
/*  88 */   private static final DateTimeFieldType DAY_OF_YEAR_TYPE = new StandardDateTimeFieldType("dayOfYear", (byte)6, DurationFieldType.days(), DurationFieldType.years());
/*     */ 
/*     */   
/*  91 */   private static final DateTimeFieldType MONTH_OF_YEAR_TYPE = new StandardDateTimeFieldType("monthOfYear", (byte)7, DurationFieldType.months(), DurationFieldType.years());
/*     */ 
/*     */   
/*  94 */   private static final DateTimeFieldType DAY_OF_MONTH_TYPE = new StandardDateTimeFieldType("dayOfMonth", (byte)8, DurationFieldType.days(), DurationFieldType.months());
/*     */ 
/*     */   
/*  97 */   private static final DateTimeFieldType WEEKYEAR_OF_CENTURY_TYPE = new StandardDateTimeFieldType("weekyearOfCentury", (byte)9, DurationFieldType.weekyears(), DurationFieldType.centuries());
/*     */ 
/*     */   
/* 100 */   private static final DateTimeFieldType WEEKYEAR_TYPE = new StandardDateTimeFieldType("weekyear", (byte)10, DurationFieldType.weekyears(), null);
/*     */ 
/*     */   
/* 103 */   private static final DateTimeFieldType WEEK_OF_WEEKYEAR_TYPE = new StandardDateTimeFieldType("weekOfWeekyear", (byte)11, DurationFieldType.weeks(), DurationFieldType.weekyears());
/*     */ 
/*     */   
/* 106 */   private static final DateTimeFieldType DAY_OF_WEEK_TYPE = new StandardDateTimeFieldType("dayOfWeek", (byte)12, DurationFieldType.days(), DurationFieldType.weeks());
/*     */ 
/*     */ 
/*     */   
/* 110 */   private static final DateTimeFieldType HALFDAY_OF_DAY_TYPE = new StandardDateTimeFieldType("halfdayOfDay", (byte)13, DurationFieldType.halfdays(), DurationFieldType.days());
/*     */ 
/*     */   
/* 113 */   private static final DateTimeFieldType HOUR_OF_HALFDAY_TYPE = new StandardDateTimeFieldType("hourOfHalfday", (byte)14, DurationFieldType.hours(), DurationFieldType.halfdays());
/*     */ 
/*     */   
/* 116 */   private static final DateTimeFieldType CLOCKHOUR_OF_HALFDAY_TYPE = new StandardDateTimeFieldType("clockhourOfHalfday", (byte)15, DurationFieldType.hours(), DurationFieldType.halfdays());
/*     */ 
/*     */   
/* 119 */   private static final DateTimeFieldType CLOCKHOUR_OF_DAY_TYPE = new StandardDateTimeFieldType("clockhourOfDay", (byte)16, DurationFieldType.hours(), DurationFieldType.days());
/*     */ 
/*     */   
/* 122 */   private static final DateTimeFieldType HOUR_OF_DAY_TYPE = new StandardDateTimeFieldType("hourOfDay", (byte)17, DurationFieldType.hours(), DurationFieldType.days());
/*     */ 
/*     */   
/* 125 */   private static final DateTimeFieldType MINUTE_OF_DAY_TYPE = new StandardDateTimeFieldType("minuteOfDay", (byte)18, DurationFieldType.minutes(), DurationFieldType.days());
/*     */ 
/*     */   
/* 128 */   private static final DateTimeFieldType MINUTE_OF_HOUR_TYPE = new StandardDateTimeFieldType("minuteOfHour", (byte)19, DurationFieldType.minutes(), DurationFieldType.hours());
/*     */ 
/*     */   
/* 131 */   private static final DateTimeFieldType SECOND_OF_DAY_TYPE = new StandardDateTimeFieldType("secondOfDay", (byte)20, DurationFieldType.seconds(), DurationFieldType.days());
/*     */ 
/*     */   
/* 134 */   private static final DateTimeFieldType SECOND_OF_MINUTE_TYPE = new StandardDateTimeFieldType("secondOfMinute", (byte)21, DurationFieldType.seconds(), DurationFieldType.minutes());
/*     */ 
/*     */   
/* 137 */   private static final DateTimeFieldType MILLIS_OF_DAY_TYPE = new StandardDateTimeFieldType("millisOfDay", (byte)22, DurationFieldType.millis(), DurationFieldType.days());
/*     */ 
/*     */   
/* 140 */   private static final DateTimeFieldType MILLIS_OF_SECOND_TYPE = new StandardDateTimeFieldType("millisOfSecond", (byte)23, DurationFieldType.millis(), DurationFieldType.seconds());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String iName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DateTimeFieldType(String paramString) {
/* 154 */     this.iName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType millisOfSecond() {
/* 164 */     return MILLIS_OF_SECOND_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType millisOfDay() {
/* 173 */     return MILLIS_OF_DAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType secondOfMinute() {
/* 182 */     return SECOND_OF_MINUTE_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType secondOfDay() {
/* 191 */     return SECOND_OF_DAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType minuteOfHour() {
/* 200 */     return MINUTE_OF_HOUR_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType minuteOfDay() {
/* 209 */     return MINUTE_OF_DAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType hourOfDay() {
/* 218 */     return HOUR_OF_DAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType clockhourOfDay() {
/* 227 */     return CLOCKHOUR_OF_DAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType hourOfHalfday() {
/* 236 */     return HOUR_OF_HALFDAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType clockhourOfHalfday() {
/* 245 */     return CLOCKHOUR_OF_HALFDAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType halfdayOfDay() {
/* 254 */     return HALFDAY_OF_DAY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType dayOfWeek() {
/* 264 */     return DAY_OF_WEEK_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType dayOfMonth() {
/* 273 */     return DAY_OF_MONTH_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType dayOfYear() {
/* 282 */     return DAY_OF_YEAR_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType weekOfWeekyear() {
/* 291 */     return WEEK_OF_WEEKYEAR_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType weekyear() {
/* 300 */     return WEEKYEAR_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType weekyearOfCentury() {
/* 309 */     return WEEKYEAR_OF_CENTURY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType monthOfYear() {
/* 318 */     return MONTH_OF_YEAR_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType year() {
/* 327 */     return YEAR_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType yearOfEra() {
/* 336 */     return YEAR_OF_ERA_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType yearOfCentury() {
/* 345 */     return YEAR_OF_CENTURY_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType centuryOfEra() {
/* 354 */     return CENTURY_OF_ERA_TYPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFieldType era() {
/* 363 */     return ERA_TYPE;
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
/*     */   public String getName() {
/* 378 */     return this.iName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(Chronology paramChronology) {
/* 410 */     return getField(paramChronology).isSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 419 */     return getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract DurationFieldType getDurationType();
/*     */ 
/*     */   
/*     */   public abstract DurationFieldType getRangeDurationType();
/*     */ 
/*     */   
/*     */   public abstract DateTimeField getField(Chronology paramChronology);
/*     */ 
/*     */   
/*     */   private static class StandardDateTimeFieldType
/*     */     extends DateTimeFieldType
/*     */   {
/*     */     private static final long serialVersionUID = -9937958251642L;
/*     */     
/*     */     private final byte iOrdinal;
/*     */     
/*     */     private final transient DurationFieldType iUnitType;
/*     */     
/*     */     private final transient DurationFieldType iRangeType;
/*     */     
/*     */     StandardDateTimeFieldType(String param1String, byte param1Byte, DurationFieldType param1DurationFieldType1, DurationFieldType param1DurationFieldType2) {
/* 444 */       super(param1String);
/* 445 */       this.iOrdinal = param1Byte;
/* 446 */       this.iUnitType = param1DurationFieldType1;
/* 447 */       this.iRangeType = param1DurationFieldType2;
/*     */     }
/*     */ 
/*     */     
/*     */     public DurationFieldType getDurationType() {
/* 452 */       return this.iUnitType;
/*     */     }
/*     */ 
/*     */     
/*     */     public DurationFieldType getRangeDurationType() {
/* 457 */       return this.iRangeType;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 463 */       if (this == param1Object) {
/* 464 */         return true;
/*     */       }
/* 466 */       if (param1Object instanceof StandardDateTimeFieldType) {
/* 467 */         return (this.iOrdinal == ((StandardDateTimeFieldType)param1Object).iOrdinal);
/*     */       }
/* 469 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 475 */       return 1 << this.iOrdinal;
/*     */     }
/*     */ 
/*     */     
/*     */     public DateTimeField getField(Chronology param1Chronology) {
/* 480 */       param1Chronology = DateTimeUtils.getChronology(param1Chronology);
/*     */       
/* 482 */       switch (this.iOrdinal) {
/*     */         case 1:
/* 484 */           return param1Chronology.era();
/*     */         case 2:
/* 486 */           return param1Chronology.yearOfEra();
/*     */         case 3:
/* 488 */           return param1Chronology.centuryOfEra();
/*     */         case 4:
/* 490 */           return param1Chronology.yearOfCentury();
/*     */         case 5:
/* 492 */           return param1Chronology.year();
/*     */         case 6:
/* 494 */           return param1Chronology.dayOfYear();
/*     */         case 7:
/* 496 */           return param1Chronology.monthOfYear();
/*     */         case 8:
/* 498 */           return param1Chronology.dayOfMonth();
/*     */         case 9:
/* 500 */           return param1Chronology.weekyearOfCentury();
/*     */         case 10:
/* 502 */           return param1Chronology.weekyear();
/*     */         case 11:
/* 504 */           return param1Chronology.weekOfWeekyear();
/*     */         case 12:
/* 506 */           return param1Chronology.dayOfWeek();
/*     */         case 13:
/* 508 */           return param1Chronology.halfdayOfDay();
/*     */         case 14:
/* 510 */           return param1Chronology.hourOfHalfday();
/*     */         case 15:
/* 512 */           return param1Chronology.clockhourOfHalfday();
/*     */         case 16:
/* 514 */           return param1Chronology.clockhourOfDay();
/*     */         case 17:
/* 516 */           return param1Chronology.hourOfDay();
/*     */         case 18:
/* 518 */           return param1Chronology.minuteOfDay();
/*     */         case 19:
/* 520 */           return param1Chronology.minuteOfHour();
/*     */         case 20:
/* 522 */           return param1Chronology.secondOfDay();
/*     */         case 21:
/* 524 */           return param1Chronology.secondOfMinute();
/*     */         case 22:
/* 526 */           return param1Chronology.millisOfDay();
/*     */         case 23:
/* 528 */           return param1Chronology.millisOfSecond();
/*     */       } 
/*     */       
/* 531 */       throw new InternalError();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 541 */       switch (this.iOrdinal) {
/*     */         case 1:
/* 543 */           return DateTimeFieldType.ERA_TYPE;
/*     */         case 2:
/* 545 */           return DateTimeFieldType.YEAR_OF_ERA_TYPE;
/*     */         case 3:
/* 547 */           return DateTimeFieldType.CENTURY_OF_ERA_TYPE;
/*     */         case 4:
/* 549 */           return DateTimeFieldType.YEAR_OF_CENTURY_TYPE;
/*     */         case 5:
/* 551 */           return DateTimeFieldType.YEAR_TYPE;
/*     */         case 6:
/* 553 */           return DateTimeFieldType.DAY_OF_YEAR_TYPE;
/*     */         case 7:
/* 555 */           return DateTimeFieldType.MONTH_OF_YEAR_TYPE;
/*     */         case 8:
/* 557 */           return DateTimeFieldType.DAY_OF_MONTH_TYPE;
/*     */         case 9:
/* 559 */           return DateTimeFieldType.WEEKYEAR_OF_CENTURY_TYPE;
/*     */         case 10:
/* 561 */           return DateTimeFieldType.WEEKYEAR_TYPE;
/*     */         case 11:
/* 563 */           return DateTimeFieldType.WEEK_OF_WEEKYEAR_TYPE;
/*     */         case 12:
/* 565 */           return DateTimeFieldType.DAY_OF_WEEK_TYPE;
/*     */         case 13:
/* 567 */           return DateTimeFieldType.HALFDAY_OF_DAY_TYPE;
/*     */         case 14:
/* 569 */           return DateTimeFieldType.HOUR_OF_HALFDAY_TYPE;
/*     */         case 15:
/* 571 */           return DateTimeFieldType.CLOCKHOUR_OF_HALFDAY_TYPE;
/*     */         case 16:
/* 573 */           return DateTimeFieldType.CLOCKHOUR_OF_DAY_TYPE;
/*     */         case 17:
/* 575 */           return DateTimeFieldType.HOUR_OF_DAY_TYPE;
/*     */         case 18:
/* 577 */           return DateTimeFieldType.MINUTE_OF_DAY_TYPE;
/*     */         case 19:
/* 579 */           return DateTimeFieldType.MINUTE_OF_HOUR_TYPE;
/*     */         case 20:
/* 581 */           return DateTimeFieldType.SECOND_OF_DAY_TYPE;
/*     */         case 21:
/* 583 */           return DateTimeFieldType.SECOND_OF_MINUTE_TYPE;
/*     */         case 22:
/* 585 */           return DateTimeFieldType.MILLIS_OF_DAY_TYPE;
/*     */         case 23:
/* 587 */           return DateTimeFieldType.MILLIS_OF_SECOND_TYPE;
/*     */       } 
/*     */       
/* 590 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/DateTimeFieldType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */