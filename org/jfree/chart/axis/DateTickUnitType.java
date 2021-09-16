/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.ObjectStreamException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateTickUnitType
/*     */   implements Serializable
/*     */ {
/*  55 */   public static final DateTickUnitType YEAR = new DateTickUnitType("DateTickUnitType.YEAR", 1);
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final DateTickUnitType MONTH = new DateTickUnitType("DateTickUnitType.MONTH", 2);
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final DateTickUnitType DAY = new DateTickUnitType("DateTickUnitType.DAY", 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final DateTickUnitType HOUR = new DateTickUnitType("DateTickUnitType.HOUR", 11);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final DateTickUnitType MINUTE = new DateTickUnitType("DateTickUnitType.MINUTE", 12);
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final DateTickUnitType SECOND = new DateTickUnitType("DateTickUnitType.SECOND", 13);
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final DateTickUnitType MILLISECOND = new DateTickUnitType("DateTickUnitType.MILLISECOND", 14);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calendarField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DateTickUnitType(String name, int calendarField) {
/*  98 */     this.name = name;
/*  99 */     this.calendarField = calendarField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCalendarField() {
/* 108 */     return this.calendarField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return this.name;
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
/*     */   public boolean equals(Object obj) {
/* 131 */     if (this == obj) {
/* 132 */       return true;
/*     */     }
/* 134 */     if (!(obj instanceof DateTickUnitType)) {
/* 135 */       return false;
/*     */     }
/* 137 */     DateTickUnitType t = (DateTickUnitType)obj;
/* 138 */     if (!this.name.equals(t.toString())) {
/* 139 */       return false;
/*     */     }
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 152 */     if (equals(YEAR)) {
/* 153 */       return YEAR;
/*     */     }
/* 155 */     if (equals(MONTH)) {
/* 156 */       return MONTH;
/*     */     }
/* 158 */     if (equals(DAY)) {
/* 159 */       return DAY;
/*     */     }
/* 161 */     if (equals(HOUR)) {
/* 162 */       return HOUR;
/*     */     }
/* 164 */     if (equals(MINUTE)) {
/* 165 */       return MINUTE;
/*     */     }
/* 167 */     if (equals(SECOND)) {
/* 168 */       return SECOND;
/*     */     }
/* 170 */     if (equals(MILLISECOND)) {
/* 171 */       return MILLISECOND;
/*     */     }
/* 173 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/DateTickUnitType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */