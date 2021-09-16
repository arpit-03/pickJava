/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BasicGJChronology
/*     */   extends BasicChronology
/*     */ {
/*     */   private static final long serialVersionUID = 538276888268L;
/*  42 */   private static final int[] MIN_DAYS_PER_MONTH_ARRAY = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*     */ 
/*     */   
/*  45 */   private static final int[] MAX_DAYS_PER_MONTH_ARRAY = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final long[] MIN_TOTAL_MILLIS_BY_MONTH_ARRAY = new long[12];
/*  54 */   private static final long[] MAX_TOTAL_MILLIS_BY_MONTH_ARRAY = new long[12];
/*     */   static {
/*  56 */     long l1 = 0L;
/*  57 */     long l2 = 0L;
/*  58 */     for (byte b = 0; b < 11; b++) {
/*  59 */       long l = MIN_DAYS_PER_MONTH_ARRAY[b] * 86400000L;
/*     */       
/*  61 */       l1 += l;
/*  62 */       MIN_TOTAL_MILLIS_BY_MONTH_ARRAY[b + 1] = l1;
/*     */       
/*  64 */       l = MAX_DAYS_PER_MONTH_ARRAY[b] * 86400000L;
/*     */       
/*  66 */       l2 += l;
/*  67 */       MAX_TOTAL_MILLIS_BY_MONTH_ARRAY[b + 1] = l2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final long FEB_29 = 5097600000L;
/*     */   
/*     */   BasicGJChronology(Chronology paramChronology, Object paramObject, int paramInt) {
/*  75 */     super(paramChronology, paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLeapDay(long paramLong) {
/*  81 */     return (dayOfMonth().get(paramLong) == 29 && monthOfYear().isLeap(paramLong));
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
/*     */   int getMonthOfYear(long paramLong, int paramInt) {
/*  93 */     int i = (int)(paramLong - getYearMillis(paramInt) >> 10L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return isLeapYear(paramInt) ? ((i < 15356250) ? ((i < 7678125) ? ((i < 2615625) ? 1 : ((i < 5062500) ? 2 : 3)) : ((i < 10209375) ? 4 : ((i < 12825000) ? 5 : 6))) : ((i < 23118750) ? ((i < 17971875) ? 7 : ((i < 20587500) ? 8 : 9)) : ((i < 25734375) ? 10 : ((i < 28265625) ? 11 : 12)))) : ((i < 15271875) ? ((i < 7593750) ? ((i < 2615625) ? 1 : ((i < 4978125) ? 2 : 3)) : ((i < 10125000) ? 4 : ((i < 12740625) ? 5 : 6))) : ((i < 23034375) ? ((i < 17887500) ? 7 : ((i < 20503125) ? 8 : 9)) : ((i < 25650000) ? 10 : ((i < 28181250) ? 11 : 12))));
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
/*     */   int getDaysInYearMonth(int paramInt1, int paramInt2) {
/* 125 */     if (isLeapYear(paramInt1)) {
/* 126 */       return MAX_DAYS_PER_MONTH_ARRAY[paramInt2 - 1];
/*     */     }
/* 128 */     return MIN_DAYS_PER_MONTH_ARRAY[paramInt2 - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getDaysInMonthMax(int paramInt) {
/* 134 */     return MAX_DAYS_PER_MONTH_ARRAY[paramInt - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   int getDaysInMonthMaxForSet(long paramLong, int paramInt) {
/* 139 */     return (paramInt > 28 || paramInt < 1) ? getDaysInMonthMax(paramLong) : 28;
/*     */   }
/*     */ 
/*     */   
/*     */   long getTotalMillisByYearMonth(int paramInt1, int paramInt2) {
/* 144 */     if (isLeapYear(paramInt1)) {
/* 145 */       return MAX_TOTAL_MILLIS_BY_MONTH_ARRAY[paramInt2 - 1];
/*     */     }
/* 147 */     return MIN_TOTAL_MILLIS_BY_MONTH_ARRAY[paramInt2 - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getYearDifference(long paramLong1, long paramLong2) {
/* 153 */     int i = getYear(paramLong1);
/* 154 */     int j = getYear(paramLong2);
/*     */ 
/*     */     
/* 157 */     long l1 = paramLong1 - getYearMillis(i);
/* 158 */     long l2 = paramLong2 - getYearMillis(j);
/*     */ 
/*     */     
/* 161 */     if (l2 >= 5097600000L) {
/* 162 */       if (isLeapYear(j)) {
/* 163 */         if (!isLeapYear(i)) {
/* 164 */           l2 -= 86400000L;
/*     */         }
/* 166 */       } else if (l1 >= 5097600000L && isLeapYear(i)) {
/* 167 */         l1 -= 86400000L;
/*     */       } 
/*     */     }
/*     */     
/* 171 */     int k = i - j;
/* 172 */     if (l1 < l2) {
/* 173 */       k--;
/*     */     }
/* 175 */     return k;
/*     */   }
/*     */ 
/*     */   
/*     */   long setYear(long paramLong, int paramInt) {
/* 180 */     int i = getYear(paramLong);
/* 181 */     int j = getDayOfYear(paramLong, i);
/* 182 */     int k = getMillisOfDay(paramLong);
/*     */     
/* 184 */     if (j > 59) {
/* 185 */       if (isLeapYear(i)) {
/*     */         
/* 187 */         if (!isLeapYear(paramInt))
/*     */         {
/* 189 */           j--;
/*     */         
/*     */         }
/*     */       }
/* 193 */       else if (isLeapYear(paramInt)) {
/*     */         
/* 195 */         j++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 200 */     paramLong = getYearMonthDayMillis(paramInt, 1, j);
/* 201 */     paramLong += k;
/*     */     
/* 203 */     return paramLong;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicGJChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */