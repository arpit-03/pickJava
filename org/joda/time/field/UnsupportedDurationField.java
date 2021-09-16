/*     */ package org.joda.time.field;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnsupportedDurationField
/*     */   extends DurationField
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6390301302770925357L;
/*     */   private static HashMap<DurationFieldType, UnsupportedDurationField> cCache;
/*     */   private final DurationFieldType iType;
/*     */   
/*     */   public static synchronized UnsupportedDurationField getInstance(DurationFieldType paramDurationFieldType) {
/*     */     UnsupportedDurationField unsupportedDurationField;
/*  49 */     if (cCache == null) {
/*  50 */       cCache = new HashMap<DurationFieldType, UnsupportedDurationField>(7);
/*  51 */       unsupportedDurationField = null;
/*     */     } else {
/*  53 */       unsupportedDurationField = cCache.get(paramDurationFieldType);
/*     */     } 
/*  55 */     if (unsupportedDurationField == null) {
/*  56 */       unsupportedDurationField = new UnsupportedDurationField(paramDurationFieldType);
/*  57 */       cCache.put(paramDurationFieldType, unsupportedDurationField);
/*     */     } 
/*  59 */     return unsupportedDurationField;
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
/*     */   private UnsupportedDurationField(DurationFieldType paramDurationFieldType) {
/*  71 */     this.iType = paramDurationFieldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DurationFieldType getType() {
/*  79 */     return this.iType;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  83 */     return this.iType.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported() {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrecise() {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue(long paramLong) {
/* 110 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getValueAsLong(long paramLong) {
/* 119 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue(long paramLong1, long paramLong2) {
/* 128 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getValueAsLong(long paramLong1, long paramLong2) {
/* 137 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis(int paramInt) {
/* 146 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis(long paramLong) {
/* 155 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis(int paramInt, long paramLong) {
/* 164 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis(long paramLong1, long paramLong2) {
/* 173 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long add(long paramLong, int paramInt) {
/* 182 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long add(long paramLong1, long paramLong2) {
/* 191 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDifference(long paramLong1, long paramLong2) {
/* 200 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/* 209 */     throw unsupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getUnitMillis() {
/* 218 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(DurationField paramDurationField) {
/* 227 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 238 */     if (this == paramObject)
/* 239 */       return true; 
/* 240 */     if (paramObject instanceof UnsupportedDurationField) {
/* 241 */       UnsupportedDurationField unsupportedDurationField = (UnsupportedDurationField)paramObject;
/* 242 */       if (unsupportedDurationField.getName() == null) {
/* 243 */         return (getName() == null);
/*     */       }
/* 245 */       return unsupportedDurationField.getName().equals(getName());
/*     */     } 
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 265 */     return "UnsupportedDurationField[" + getName() + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 272 */     return getInstance(this.iType);
/*     */   }
/*     */   
/*     */   private UnsupportedOperationException unsupported() {
/* 276 */     return new UnsupportedOperationException(this.iType + " field is unsupported");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/UnsupportedDurationField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */