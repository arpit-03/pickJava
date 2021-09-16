/*     */ package org.joda.time.tz;
/*     */ 
/*     */ import java.util.SimpleTimeZone;
/*     */ import java.util.TimeZone;
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
/*     */ public final class FixedDateTimeZone
/*     */   extends DateTimeZone
/*     */ {
/*     */   private static final long serialVersionUID = -3513011772763289092L;
/*     */   private final String iNameKey;
/*     */   private final int iWallOffset;
/*     */   private final int iStandardOffset;
/*     */   
/*     */   public FixedDateTimeZone(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/*  38 */     super(paramString1);
/*  39 */     this.iNameKey = paramString2;
/*  40 */     this.iWallOffset = paramInt1;
/*  41 */     this.iStandardOffset = paramInt2;
/*     */   }
/*     */   
/*     */   public String getNameKey(long paramLong) {
/*  45 */     return this.iNameKey;
/*     */   }
/*     */   
/*     */   public int getOffset(long paramLong) {
/*  49 */     return this.iWallOffset;
/*     */   }
/*     */   
/*     */   public int getStandardOffset(long paramLong) {
/*  53 */     return this.iStandardOffset;
/*     */   }
/*     */   
/*     */   public int getOffsetFromLocal(long paramLong) {
/*  57 */     return this.iWallOffset;
/*     */   }
/*     */   
/*     */   public boolean isFixed() {
/*  61 */     return true;
/*     */   }
/*     */   
/*     */   public long nextTransition(long paramLong) {
/*  65 */     return paramLong;
/*     */   }
/*     */   
/*     */   public long previousTransition(long paramLong) {
/*  69 */     return paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeZone toTimeZone() {
/*  77 */     String str = getID();
/*  78 */     if (str.length() == 6 && (str.startsWith("+") || str.startsWith("-")))
/*     */     {
/*     */       
/*  81 */       return TimeZone.getTimeZone("GMT" + getID());
/*     */     }
/*     */     
/*  84 */     return new SimpleTimeZone(this.iWallOffset, getID());
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  88 */     if (this == paramObject) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (paramObject instanceof FixedDateTimeZone) {
/*  92 */       FixedDateTimeZone fixedDateTimeZone = (FixedDateTimeZone)paramObject;
/*  93 */       return (getID().equals(fixedDateTimeZone.getID()) && this.iStandardOffset == fixedDateTimeZone.iStandardOffset && this.iWallOffset == fixedDateTimeZone.iWallOffset);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 102 */     return getID().hashCode() + 37 * this.iStandardOffset + 31 * this.iWallOffset;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/FixedDateTimeZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */