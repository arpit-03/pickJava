/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.InstantConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateTimeComparator
/*     */   implements Comparator<Object>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6097339773320178364L;
/*  54 */   private static final DateTimeComparator ALL_INSTANCE = new DateTimeComparator(null, null);
/*     */   
/*  56 */   private static final DateTimeComparator DATE_INSTANCE = new DateTimeComparator(DateTimeFieldType.dayOfYear(), null);
/*     */   
/*  58 */   private static final DateTimeComparator TIME_INSTANCE = new DateTimeComparator(null, DateTimeFieldType.dayOfYear());
/*     */ 
/*     */ 
/*     */   
/*     */   private final DateTimeFieldType iLowerLimit;
/*     */ 
/*     */ 
/*     */   
/*     */   private final DateTimeFieldType iUpperLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeComparator getInstance() {
/*  72 */     return ALL_INSTANCE;
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
/*     */   public static DateTimeComparator getInstance(DateTimeFieldType paramDateTimeFieldType) {
/*  87 */     return getInstance(paramDateTimeFieldType, null);
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
/*     */   public static DateTimeComparator getInstance(DateTimeFieldType paramDateTimeFieldType1, DateTimeFieldType paramDateTimeFieldType2) {
/* 106 */     if (paramDateTimeFieldType1 == null && paramDateTimeFieldType2 == null) {
/* 107 */       return ALL_INSTANCE;
/*     */     }
/* 109 */     if (paramDateTimeFieldType1 == DateTimeFieldType.dayOfYear() && paramDateTimeFieldType2 == null) {
/* 110 */       return DATE_INSTANCE;
/*     */     }
/* 112 */     if (paramDateTimeFieldType1 == null && paramDateTimeFieldType2 == DateTimeFieldType.dayOfYear()) {
/* 113 */       return TIME_INSTANCE;
/*     */     }
/* 115 */     return new DateTimeComparator(paramDateTimeFieldType1, paramDateTimeFieldType2);
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
/*     */   public static DateTimeComparator getDateOnlyInstance() {
/* 130 */     return DATE_INSTANCE;
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
/*     */   public static DateTimeComparator getTimeOnlyInstance() {
/* 145 */     return TIME_INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DateTimeComparator(DateTimeFieldType paramDateTimeFieldType1, DateTimeFieldType paramDateTimeFieldType2) {
/* 156 */     this.iLowerLimit = paramDateTimeFieldType1;
/* 157 */     this.iUpperLimit = paramDateTimeFieldType2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeFieldType getLowerLimit() {
/* 167 */     return this.iLowerLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeFieldType getUpperLimit() {
/* 176 */     return this.iUpperLimit;
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
/*     */   public int compare(Object paramObject1, Object paramObject2) {
/* 192 */     InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(paramObject1);
/* 193 */     Chronology chronology1 = instantConverter.getChronology(paramObject1, (Chronology)null);
/* 194 */     long l1 = instantConverter.getInstantMillis(paramObject1, chronology1);
/*     */     
/* 196 */     instantConverter = ConverterManager.getInstance().getInstantConverter(paramObject2);
/* 197 */     Chronology chronology2 = instantConverter.getChronology(paramObject2, (Chronology)null);
/* 198 */     long l2 = instantConverter.getInstantMillis(paramObject2, chronology2);
/*     */     
/* 200 */     if (this.iLowerLimit != null) {
/* 201 */       l1 = this.iLowerLimit.getField(chronology1).roundFloor(l1);
/* 202 */       l2 = this.iLowerLimit.getField(chronology2).roundFloor(l2);
/*     */     } 
/*     */     
/* 205 */     if (this.iUpperLimit != null) {
/* 206 */       l1 = this.iUpperLimit.getField(chronology1).remainder(l1);
/* 207 */       l2 = this.iUpperLimit.getField(chronology2).remainder(l2);
/*     */     } 
/*     */     
/* 210 */     if (l1 < l2)
/* 211 */       return -1; 
/* 212 */     if (l1 > l2) {
/* 213 */       return 1;
/*     */     }
/* 215 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 226 */     return getInstance(this.iLowerLimit, this.iUpperLimit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 236 */     if (paramObject instanceof DateTimeComparator) {
/* 237 */       DateTimeComparator dateTimeComparator = (DateTimeComparator)paramObject;
/* 238 */       return ((this.iLowerLimit == dateTimeComparator.getLowerLimit() || (this.iLowerLimit != null && this.iLowerLimit.equals(dateTimeComparator.getLowerLimit()))) && (this.iUpperLimit == dateTimeComparator.getUpperLimit() || (this.iUpperLimit != null && this.iUpperLimit.equals(dateTimeComparator.getUpperLimit()))));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 243 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 252 */     return ((this.iLowerLimit == null) ? 0 : this.iLowerLimit.hashCode()) + 123 * ((this.iUpperLimit == null) ? 0 : this.iUpperLimit.hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 262 */     if (this.iLowerLimit == this.iUpperLimit) {
/* 263 */       return "DateTimeComparator[" + ((this.iLowerLimit == null) ? "" : this.iLowerLimit.getName()) + "]";
/*     */     }
/*     */ 
/*     */     
/* 267 */     return "DateTimeComparator[" + ((this.iLowerLimit == null) ? "" : this.iLowerLimit.getName()) + "-" + ((this.iUpperLimit == null) ? "" : this.iUpperLimit.getName()) + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/DateTimeComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */