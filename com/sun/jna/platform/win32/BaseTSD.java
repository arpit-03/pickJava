/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.IntegerType;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.ptr.ByReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface BaseTSD
/*     */ {
/*     */   public static class LONG_PTR
/*     */     extends IntegerType
/*     */   {
/*     */     public LONG_PTR() {
/*  43 */       this(0L);
/*     */     }
/*     */     
/*     */     public LONG_PTR(long value) {
/*  47 */       super(Pointer.SIZE, value);
/*     */     }
/*     */     
/*     */     public Pointer toPointer() {
/*  51 */       return Pointer.createConstant(longValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SSIZE_T
/*     */     extends LONG_PTR
/*     */   {
/*     */     public SSIZE_T() {
/*  60 */       this(0L);
/*     */     }
/*     */     
/*     */     public SSIZE_T(long value) {
/*  64 */       super(value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ULONG_PTR
/*     */     extends IntegerType
/*     */   {
/*     */     public ULONG_PTR() {
/*  73 */       this(0L);
/*     */     }
/*     */     
/*     */     public ULONG_PTR(long value) {
/*  77 */       super(Pointer.SIZE, value, true);
/*     */     }
/*     */     
/*     */     public Pointer toPointer() {
/*  81 */       return Pointer.createConstant(longValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ULONG_PTRByReference
/*     */     extends ByReference
/*     */   {
/*     */     public ULONG_PTRByReference() {
/*  90 */       this(new BaseTSD.ULONG_PTR(0L));
/*     */     }
/*     */     public ULONG_PTRByReference(BaseTSD.ULONG_PTR value) {
/*  93 */       super(Pointer.SIZE);
/*  94 */       setValue(value);
/*     */     }
/*     */     public void setValue(BaseTSD.ULONG_PTR value) {
/*  97 */       if (Pointer.SIZE == 4) {
/*  98 */         getPointer().setInt(0L, value.intValue());
/*     */       } else {
/*     */         
/* 101 */         getPointer().setLong(0L, value.longValue());
/*     */       } 
/*     */     }
/*     */     public BaseTSD.ULONG_PTR getValue() {
/* 105 */       return new BaseTSD.ULONG_PTR((Pointer.SIZE == 4) ? 
/* 106 */           getPointer().getInt(0L) : 
/* 107 */           getPointer().getLong(0L));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DWORD_PTR
/*     */     extends IntegerType
/*     */   {
/*     */     public DWORD_PTR() {
/* 117 */       this(0L);
/*     */     }
/*     */     
/*     */     public DWORD_PTR(long value) {
/* 121 */       super(Pointer.SIZE, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SIZE_T
/*     */     extends ULONG_PTR
/*     */   {
/*     */     public SIZE_T() {
/* 131 */       this(0L);
/*     */     }
/*     */     
/*     */     public SIZE_T(long value) {
/* 135 */       super(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/BaseTSD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */