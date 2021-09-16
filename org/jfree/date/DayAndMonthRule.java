/*     */ package org.jfree.date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DayAndMonthRule
/*     */   extends AnnualDateRule
/*     */ {
/*     */   private int dayOfMonth;
/*     */   private int month;
/*     */   
/*     */   public DayAndMonthRule() {
/*  69 */     this(1, 1);
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
/*     */   public DayAndMonthRule(int dayOfMonth, int month) {
/*  86 */     setMonth(month);
/*  87 */     setDayOfMonth(dayOfMonth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfMonth() {
/*  97 */     return this.dayOfMonth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayOfMonth(int dayOfMonth) {
/* 108 */     if (dayOfMonth < 1 || dayOfMonth > SerialDate.LAST_DAY_OF_MONTH[this.month]) {
/* 109 */       throw new IllegalArgumentException("DayAndMonthRule(): dayOfMonth outside valid range.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 114 */     this.dayOfMonth = dayOfMonth;
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
/*     */   public int getMonth() {
/* 128 */     return this.month;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMonth(int month) {
/* 139 */     if (!SerialDate.isValidMonthCode(month)) {
/* 140 */       throw new IllegalArgumentException("DayAndMonthRule(): month code not valid.");
/*     */     }
/*     */ 
/*     */     
/* 144 */     this.month = month;
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
/*     */   public SerialDate getDate(int yyyy) {
/* 156 */     return SerialDate.createInstance(this.dayOfMonth, this.month, yyyy);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/date/DayAndMonthRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */