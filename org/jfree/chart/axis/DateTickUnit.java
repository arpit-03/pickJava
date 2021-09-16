/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateTickUnit
/*     */   extends TickUnit
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7289292157229621901L;
/*     */   private DateTickUnitType unitType;
/*     */   private int count;
/*     */   private DateTickUnitType rollUnitType;
/*     */   private int rollCount;
/*     */   private DateFormat formatter;
/*     */   public static final int YEAR = 0;
/*     */   public static final int MONTH = 1;
/*     */   public static final int DAY = 2;
/*     */   public static final int HOUR = 3;
/*     */   public static final int MINUTE = 4;
/*     */   public static final int SECOND = 5;
/*     */   public static final int MILLISECOND = 6;
/*     */   private int unit;
/*     */   private int rollUnit;
/*     */   
/*     */   public DateTickUnit(DateTickUnitType unitType, int multiple) {
/* 109 */     this(unitType, multiple, DateFormat.getDateInstance(3));
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
/*     */   public DateTickUnit(DateTickUnitType unitType, int multiple, DateFormat formatter) {
/* 123 */     this(unitType, multiple, unitType, multiple, formatter);
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
/*     */   public DateTickUnit(DateTickUnitType unitType, int multiple, DateTickUnitType rollUnitType, int rollMultiple, DateFormat formatter) {
/* 140 */     super(getMillisecondCount(unitType, multiple));
/* 141 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 142 */     if (multiple <= 0) {
/* 143 */       throw new IllegalArgumentException("Requires 'multiple' > 0.");
/*     */     }
/* 145 */     if (rollMultiple <= 0) {
/* 146 */       throw new IllegalArgumentException("Requires 'rollMultiple' > 0.");
/*     */     }
/* 148 */     this.unitType = unitType;
/* 149 */     this.count = multiple;
/* 150 */     this.rollUnitType = rollUnitType;
/* 151 */     this.rollCount = rollMultiple;
/* 152 */     this.formatter = formatter;
/*     */ 
/*     */     
/* 155 */     this.unit = unitTypeToInt(unitType);
/* 156 */     this.rollUnit = unitTypeToInt(rollUnitType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTickUnitType getUnitType() {
/* 167 */     return this.unitType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultiple() {
/* 176 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTickUnitType getRollUnitType() {
/* 187 */     return this.rollUnitType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRollMultiple() {
/* 198 */     return this.rollCount;
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
/*     */   public String valueToString(double milliseconds) {
/* 210 */     return this.formatter.format(new Date((long)milliseconds));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String dateToString(Date date) {
/* 221 */     return this.formatter.format(date);
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
/*     */   public Date addToDate(Date base, TimeZone zone) {
/* 239 */     Calendar calendar = Calendar.getInstance(zone);
/* 240 */     calendar.setTime(base);
/* 241 */     calendar.add(this.unitType.getCalendarField(), this.count);
/* 242 */     return calendar.getTime();
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
/*     */   public Date rollDate(Date base) {
/* 256 */     return rollDate(base, TimeZone.getDefault());
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
/*     */   public Date rollDate(Date base, TimeZone zone) {
/* 275 */     Calendar calendar = Calendar.getInstance(zone);
/* 276 */     calendar.setTime(base);
/* 277 */     calendar.add(this.rollUnitType.getCalendarField(), this.rollCount);
/* 278 */     return calendar.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCalendarField() {
/* 288 */     return this.unitType.getCalendarField();
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
/*     */   private static long getMillisecondCount(DateTickUnitType unit, int count) {
/* 307 */     if (unit.equals(DateTickUnitType.YEAR)) {
/* 308 */       return 31536000000L * count;
/*     */     }
/* 310 */     if (unit.equals(DateTickUnitType.MONTH)) {
/* 311 */       return 2678400000L * count;
/*     */     }
/* 313 */     if (unit.equals(DateTickUnitType.DAY)) {
/* 314 */       return 86400000L * count;
/*     */     }
/* 316 */     if (unit.equals(DateTickUnitType.HOUR)) {
/* 317 */       return 3600000L * count;
/*     */     }
/* 319 */     if (unit.equals(DateTickUnitType.MINUTE)) {
/* 320 */       return 60000L * count;
/*     */     }
/* 322 */     if (unit.equals(DateTickUnitType.SECOND)) {
/* 323 */       return 1000L * count;
/*     */     }
/* 325 */     if (unit.equals(DateTickUnitType.MILLISECOND)) {
/* 326 */       return count;
/*     */     }
/*     */     
/* 329 */     throw new IllegalArgumentException("The 'unit' argument has a value that is not recognised.");
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
/*     */   private static DateTickUnitType intToUnitType(int unit) {
/* 346 */     switch (unit) { case 0:
/* 347 */         return DateTickUnitType.YEAR;
/* 348 */       case 1: return DateTickUnitType.MONTH;
/* 349 */       case 2: return DateTickUnitType.DAY;
/* 350 */       case 3: return DateTickUnitType.HOUR;
/* 351 */       case 4: return DateTickUnitType.MINUTE;
/* 352 */       case 5: return DateTickUnitType.SECOND;
/* 353 */       case 6: return DateTickUnitType.MILLISECOND; }
/* 354 */      throw new IllegalArgumentException("Unrecognised 'unit' value " + unit + ".");
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
/*     */   private static int unitTypeToInt(DateTickUnitType unitType) {
/* 369 */     ParamChecks.nullNotPermitted(unitType, "unitType");
/* 370 */     if (unitType.equals(DateTickUnitType.YEAR)) {
/* 371 */       return 0;
/*     */     }
/* 373 */     if (unitType.equals(DateTickUnitType.MONTH)) {
/* 374 */       return 1;
/*     */     }
/* 376 */     if (unitType.equals(DateTickUnitType.DAY)) {
/* 377 */       return 2;
/*     */     }
/* 379 */     if (unitType.equals(DateTickUnitType.HOUR)) {
/* 380 */       return 3;
/*     */     }
/* 382 */     if (unitType.equals(DateTickUnitType.MINUTE)) {
/* 383 */       return 4;
/*     */     }
/* 385 */     if (unitType.equals(DateTickUnitType.SECOND)) {
/* 386 */       return 5;
/*     */     }
/* 388 */     if (unitType.equals(DateTickUnitType.MILLISECOND)) {
/* 389 */       return 6;
/*     */     }
/*     */     
/* 392 */     throw new IllegalArgumentException("The 'unitType' is not recognised");
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
/*     */   private static DateFormat notNull(DateFormat formatter) {
/* 406 */     if (formatter == null) {
/* 407 */       return DateFormat.getDateInstance(3);
/*     */     }
/*     */     
/* 410 */     return formatter;
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
/* 423 */     if (obj == this) {
/* 424 */       return true;
/*     */     }
/* 426 */     if (!(obj instanceof DateTickUnit)) {
/* 427 */       return false;
/*     */     }
/* 429 */     if (!super.equals(obj)) {
/* 430 */       return false;
/*     */     }
/* 432 */     DateTickUnit that = (DateTickUnit)obj;
/* 433 */     if (!this.unitType.equals(that.unitType)) {
/* 434 */       return false;
/*     */     }
/* 436 */     if (this.count != that.count) {
/* 437 */       return false;
/*     */     }
/* 439 */     if (!ObjectUtilities.equal(this.formatter, that.formatter)) {
/* 440 */       return false;
/*     */     }
/* 442 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 452 */     int result = 19;
/* 453 */     result = 37 * result + this.unitType.hashCode();
/* 454 */     result = 37 * result + this.count;
/* 455 */     result = 37 * result + this.formatter.hashCode();
/* 456 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 467 */     return "DateTickUnit[" + this.unitType.toString() + ", " + this.count + "]";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTickUnit(int unit, int count, DateFormat formatter) {
/* 547 */     this(unit, count, unit, count, formatter);
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
/*     */   public DateTickUnit(int unit, int count) {
/* 561 */     this(unit, count, (DateFormat)null);
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
/*     */   public DateTickUnit(int unit, int count, int rollUnit, int rollCount, DateFormat formatter) {
/* 578 */     this(intToUnitType(unit), count, intToUnitType(rollUnit), rollCount, 
/* 579 */         notNull(formatter));
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
/*     */   public int getUnit() {
/* 595 */     return this.unit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 606 */     return this.count;
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
/*     */   public int getRollUnit() {
/* 620 */     return this.rollUnit;
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
/*     */   public int getRollCount() {
/* 632 */     return this.rollCount;
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
/*     */   public Date addToDate(Date base) {
/* 649 */     return addToDate(base, TimeZone.getDefault());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/DateTickUnit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */