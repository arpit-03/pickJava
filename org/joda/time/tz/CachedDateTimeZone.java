/*     */ package org.joda.time.tz;
/*     */ 
/*     */ import org.joda.time.DateTimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedDateTimeZone
/*     */   extends DateTimeZone
/*     */ {
/*     */   private static final long serialVersionUID = 5472298452022250685L;
/*     */   private static final int cInfoCacheMask;
/*     */   private final DateTimeZone iZone;
/*     */   
/*     */   static {
/*     */     Integer integer;
/*     */     int i;
/*     */     try {
/*  39 */       integer = Integer.getInteger("org.joda.time.tz.CachedDateTimeZone.size");
/*  40 */     } catch (SecurityException securityException) {
/*  41 */       integer = null;
/*     */     } 
/*     */ 
/*     */     
/*  45 */     if (integer == null) {
/*     */ 
/*     */       
/*  48 */       i = 512;
/*     */     } else {
/*  50 */       i = integer.intValue();
/*     */       
/*  52 */       i--;
/*  53 */       byte b = 0;
/*  54 */       while (i > 0) {
/*  55 */         b++;
/*  56 */         i >>= 1;
/*     */       } 
/*  58 */       i = 1 << b;
/*     */     } 
/*     */     
/*  61 */     cInfoCacheMask = i - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CachedDateTimeZone forZone(DateTimeZone paramDateTimeZone) {
/*  68 */     if (paramDateTimeZone instanceof CachedDateTimeZone) {
/*  69 */       return (CachedDateTimeZone)paramDateTimeZone;
/*     */     }
/*  71 */     return new CachedDateTimeZone(paramDateTimeZone);
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
/*  84 */   private final transient Info[] iInfoCache = new Info[cInfoCacheMask + 1];
/*     */   
/*     */   private CachedDateTimeZone(DateTimeZone paramDateTimeZone) {
/*  87 */     super(paramDateTimeZone.getID());
/*  88 */     this.iZone = paramDateTimeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeZone getUncachedZone() {
/*  95 */     return this.iZone;
/*     */   }
/*     */   
/*     */   public String getNameKey(long paramLong) {
/*  99 */     return getInfo(paramLong).getNameKey(paramLong);
/*     */   }
/*     */   
/*     */   public int getOffset(long paramLong) {
/* 103 */     return getInfo(paramLong).getOffset(paramLong);
/*     */   }
/*     */   
/*     */   public int getStandardOffset(long paramLong) {
/* 107 */     return getInfo(paramLong).getStandardOffset(paramLong);
/*     */   }
/*     */   
/*     */   public boolean isFixed() {
/* 111 */     return this.iZone.isFixed();
/*     */   }
/*     */   
/*     */   public long nextTransition(long paramLong) {
/* 115 */     return this.iZone.nextTransition(paramLong);
/*     */   }
/*     */   
/*     */   public long previousTransition(long paramLong) {
/* 119 */     return this.iZone.previousTransition(paramLong);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 123 */     return this.iZone.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 127 */     if (this == paramObject) {
/* 128 */       return true;
/*     */     }
/* 130 */     if (paramObject instanceof CachedDateTimeZone) {
/* 131 */       return this.iZone.equals(((CachedDateTimeZone)paramObject).iZone);
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Info getInfo(long paramLong) {
/* 140 */     int i = (int)(paramLong >> 32L);
/* 141 */     Info[] arrayOfInfo = this.iInfoCache;
/* 142 */     int j = i & cInfoCacheMask;
/* 143 */     Info info = arrayOfInfo[j];
/* 144 */     if (info == null || (int)(info.iPeriodStart >> 32L) != i) {
/* 145 */       info = createInfo(paramLong);
/* 146 */       arrayOfInfo[j] = info;
/*     */     } 
/* 148 */     return info;
/*     */   }
/*     */   
/*     */   private Info createInfo(long paramLong) {
/* 152 */     long l1 = paramLong & 0xFFFFFFFF00000000L;
/* 153 */     Info info1 = new Info(this.iZone, l1);
/*     */     
/* 155 */     long l2 = l1 | 0xFFFFFFFFL;
/* 156 */     Info info2 = info1;
/*     */     while (true) {
/* 158 */       long l = this.iZone.nextTransition(l1);
/* 159 */       if (l == l1 || l > l2) {
/*     */         break;
/*     */       }
/* 162 */       l1 = l;
/* 163 */       info2 = info2.iNextInfo = new Info(this.iZone, l1);
/*     */     } 
/*     */     
/* 166 */     return info1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Info
/*     */   {
/*     */     public final long iPeriodStart;
/*     */     
/*     */     public final DateTimeZone iZoneRef;
/*     */     Info iNextInfo;
/*     */     private String iNameKey;
/* 177 */     private int iOffset = Integer.MIN_VALUE;
/* 178 */     private int iStandardOffset = Integer.MIN_VALUE;
/*     */     
/*     */     Info(DateTimeZone param1DateTimeZone, long param1Long) {
/* 181 */       this.iPeriodStart = param1Long;
/* 182 */       this.iZoneRef = param1DateTimeZone;
/*     */     }
/*     */     
/*     */     public String getNameKey(long param1Long) {
/* 186 */       if (this.iNextInfo == null || param1Long < this.iNextInfo.iPeriodStart) {
/* 187 */         if (this.iNameKey == null) {
/* 188 */           this.iNameKey = this.iZoneRef.getNameKey(this.iPeriodStart);
/*     */         }
/* 190 */         return this.iNameKey;
/*     */       } 
/* 192 */       return this.iNextInfo.getNameKey(param1Long);
/*     */     }
/*     */     
/*     */     public int getOffset(long param1Long) {
/* 196 */       if (this.iNextInfo == null || param1Long < this.iNextInfo.iPeriodStart) {
/* 197 */         if (this.iOffset == Integer.MIN_VALUE) {
/* 198 */           this.iOffset = this.iZoneRef.getOffset(this.iPeriodStart);
/*     */         }
/* 200 */         return this.iOffset;
/*     */       } 
/* 202 */       return this.iNextInfo.getOffset(param1Long);
/*     */     }
/*     */     
/*     */     public int getStandardOffset(long param1Long) {
/* 206 */       if (this.iNextInfo == null || param1Long < this.iNextInfo.iPeriodStart) {
/* 207 */         if (this.iStandardOffset == Integer.MIN_VALUE) {
/* 208 */           this.iStandardOffset = this.iZoneRef.getStandardOffset(this.iPeriodStart);
/*     */         }
/* 210 */         return this.iStandardOffset;
/*     */       } 
/* 212 */       return this.iNextInfo.getStandardOffset(param1Long);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/CachedDateTimeZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */