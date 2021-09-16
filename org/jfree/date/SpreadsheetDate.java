/*     */ package org.jfree.date;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpreadsheetDate
/*     */   extends SerialDate
/*     */ {
/*     */   private static final long serialVersionUID = -2039586705374454461L;
/*     */   private final int serial;
/*     */   private final int day;
/*     */   private final int month;
/*     */   private final int year;
/*     */   
/*     */   public SpreadsheetDate(int day, int month, int year) {
/* 110 */     if (year >= 1900 && year <= 9999) {
/* 111 */       this.year = year;
/*     */     } else {
/*     */       
/* 114 */       throw new IllegalArgumentException("The 'year' argument must be in range 1900 to 9999.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (month >= 1 && month <= 12) {
/*     */       
/* 121 */       this.month = month;
/*     */     } else {
/*     */       
/* 124 */       throw new IllegalArgumentException("The 'month' argument must be in the range 1 to 12.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (day >= 1 && day <= SerialDate.lastDayOfMonth(month, year)) {
/* 130 */       this.day = day;
/*     */     } else {
/*     */       
/* 133 */       throw new IllegalArgumentException("Invalid 'day' argument.");
/*     */     } 
/*     */ 
/*     */     
/* 137 */     this.serial = calcSerial(day, month, year);
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
/*     */   public SpreadsheetDate(int serial) {
/* 149 */     if (serial >= 2 && serial <= 2958465) {
/* 150 */       this.serial = serial;
/*     */     } else {
/*     */       
/* 153 */       throw new IllegalArgumentException("SpreadsheetDate: Serial must be in range 2 to 2958465.");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     int days = this.serial - 2;
/*     */     
/* 161 */     int overestimatedYYYY = 1900 + days / 365;
/* 162 */     int leaps = SerialDate.leapYearCount(overestimatedYYYY);
/* 163 */     int nonleapdays = days - leaps;
/*     */     
/* 165 */     int underestimatedYYYY = 1900 + nonleapdays / 365;
/*     */     
/* 167 */     if (underestimatedYYYY == overestimatedYYYY) {
/* 168 */       this.year = underestimatedYYYY;
/*     */     } else {
/*     */       
/* 171 */       int ss1 = calcSerial(1, 1, underestimatedYYYY);
/* 172 */       while (ss1 <= this.serial) {
/* 173 */         underestimatedYYYY++;
/* 174 */         ss1 = calcSerial(1, 1, underestimatedYYYY);
/*     */       } 
/* 176 */       this.year = underestimatedYYYY - 1;
/*     */     } 
/*     */     
/* 179 */     int ss2 = calcSerial(1, 1, this.year);
/*     */     
/* 181 */     int[] daysToEndOfPrecedingMonth = AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH;
/*     */ 
/*     */     
/* 184 */     if (isLeapYear(this.year)) {
/* 185 */       daysToEndOfPrecedingMonth = LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     int mm = 1;
/* 191 */     int sss = ss2 + daysToEndOfPrecedingMonth[mm] - 1;
/* 192 */     while (sss < this.serial) {
/* 193 */       mm++;
/* 194 */       sss = ss2 + daysToEndOfPrecedingMonth[mm] - 1;
/*     */     } 
/* 196 */     this.month = mm - 1;
/*     */ 
/*     */     
/* 199 */     this.day = this.serial - ss2 - daysToEndOfPrecedingMonth[this.month] + 1;
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
/*     */   public int toSerial() {
/* 212 */     return this.serial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date toDate() {
/* 221 */     Calendar calendar = Calendar.getInstance();
/* 222 */     calendar.set(getYYYY(), getMonth() - 1, getDayOfMonth(), 0, 0, 0);
/* 223 */     return calendar.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYYYY() {
/* 232 */     return this.year;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonth() {
/* 241 */     return this.month;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfMonth() {
/* 250 */     return this.day;
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
/*     */   public int getDayOfWeek() {
/* 264 */     return (this.serial + 6) % 7 + 1;
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
/*     */   public boolean equals(Object object) {
/* 280 */     if (object instanceof SerialDate) {
/* 281 */       SerialDate s = (SerialDate)object;
/* 282 */       return (s.toSerial() == toSerial());
/*     */     } 
/*     */     
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 296 */     return toSerial();
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
/*     */   public int compare(SerialDate other) {
/* 309 */     return this.serial - other.toSerial();
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
/*     */   public int compareTo(Object other) {
/* 321 */     return compare((SerialDate)other);
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
/*     */   public boolean isOn(SerialDate other) {
/* 334 */     return (this.serial == other.toSerial());
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
/*     */   public boolean isBefore(SerialDate other) {
/* 347 */     return (this.serial < other.toSerial());
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
/*     */   public boolean isOnOrBefore(SerialDate other) {
/* 360 */     return (this.serial <= other.toSerial());
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
/*     */   public boolean isAfter(SerialDate other) {
/* 373 */     return (this.serial > other.toSerial());
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
/*     */   public boolean isOnOrAfter(SerialDate other) {
/* 386 */     return (this.serial >= other.toSerial());
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
/*     */   public boolean isInRange(SerialDate d1, SerialDate d2) {
/* 400 */     return isInRange(d1, d2, 3);
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
/*     */   public boolean isInRange(SerialDate d1, SerialDate d2, int include) {
/* 418 */     int s1 = d1.toSerial();
/* 419 */     int s2 = d2.toSerial();
/* 420 */     int start = Math.min(s1, s2);
/* 421 */     int end = Math.max(s1, s2);
/*     */     
/* 423 */     int s = toSerial();
/* 424 */     if (include == 3) {
/* 425 */       return (s >= start && s <= end);
/*     */     }
/* 427 */     if (include == 1) {
/* 428 */       return (s >= start && s < end);
/*     */     }
/* 430 */     if (include == 2) {
/* 431 */       return (s > start && s <= end);
/*     */     }
/*     */     
/* 434 */     return (s > start && s < end);
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
/*     */   private int calcSerial(int d, int m, int y) {
/* 450 */     int yy = (y - 1900) * 365 + SerialDate.leapYearCount(y - 1);
/* 451 */     int mm = SerialDate.AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[m];
/* 452 */     if (m > 2 && 
/* 453 */       SerialDate.isLeapYear(y)) {
/* 454 */       mm++;
/*     */     }
/*     */     
/* 457 */     int dd = d;
/* 458 */     return yy + mm + dd + 1;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/date/SpreadsheetDate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */