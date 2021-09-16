/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.IntegerType;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.PointerType;
/*      */ import com.sun.jna.Structure;
/*      */ import java.awt.Rectangle;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface WinDef
/*      */ {
/*      */   public static final int MAX_PATH = 260;
/*      */   
/*      */   public static class WORD
/*      */     extends IntegerType
/*      */     implements Comparable<WORD>
/*      */   {
/*      */     public static final int SIZE = 2;
/*      */     
/*      */     public WORD() {
/*   62 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WORD(long value) {
/*   72 */       super(2, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(WORD other) {
/*   77 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class WORDByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public WORDByReference() {
/*   90 */       this(new WinDef.WORD(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WORDByReference(WinDef.WORD value) {
/*   99 */       super(2);
/*  100 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.WORD value) {
/*  109 */       getPointer().setShort(0L, value.shortValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD getValue() {
/*  118 */       return new WinDef.WORD(getPointer().getShort(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DWORD
/*      */     extends IntegerType
/*      */     implements Comparable<DWORD>
/*      */   {
/*      */     public static final int SIZE = 4;
/*      */ 
/*      */ 
/*      */     
/*      */     public DWORD() {
/*  134 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DWORD(long value) {
/*  144 */       super(4, value, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD getLow() {
/*  153 */       return new WinDef.WORD(longValue() & 0xFFFFL);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD getHigh() {
/*  162 */       return new WinDef.WORD(longValue() >> 16L & 0xFFFFL);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(DWORD other) {
/*  167 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DWORDByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public DWORDByReference() {
/*  180 */       this(new WinDef.DWORD(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DWORDByReference(WinDef.DWORD value) {
/*  189 */       super(4);
/*  190 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.DWORD value) {
/*  199 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD getValue() {
/*  208 */       return new WinDef.DWORD(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LONG
/*      */     extends IntegerType
/*      */     implements Comparable<LONG>
/*      */   {
/*  218 */     public static final int SIZE = Native.LONG_SIZE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LONG() {
/*  224 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LONG(long value) {
/*  233 */       super(SIZE, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(LONG other) {
/*  238 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LONGByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public LONGByReference() {
/*  251 */       this(new WinDef.LONG(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LONGByReference(WinDef.LONG value) {
/*  260 */       super(WinDef.LONG.SIZE);
/*  261 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.LONG value) {
/*  270 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.LONG getValue() {
/*  279 */       return new WinDef.LONG(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LONGLONG
/*      */     extends IntegerType
/*      */     implements Comparable<LONGLONG>
/*      */   {
/*  289 */     public static final int SIZE = Native.LONG_SIZE * 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LONGLONG() {
/*  295 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LONGLONG(long value) {
/*  304 */       super(8, value, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(LONGLONG other) {
/*  309 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LONGLONGByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public LONGLONGByReference() {
/*  322 */       this(new WinDef.LONGLONG(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LONGLONGByReference(WinDef.LONGLONG value) {
/*  331 */       super(WinDef.LONGLONG.SIZE);
/*  332 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.LONGLONG value) {
/*  341 */       getPointer().setLong(0L, value.longValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.LONGLONG getValue() {
/*  350 */       return new WinDef.LONGLONG(getPointer().getLong(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HDC
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HDC() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HDC(Pointer p) {
/*  373 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HICON
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HICON() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HICON(WinNT.HANDLE handle) {
/*  398 */       this(handle.getPointer());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HICON(Pointer p) {
/*  408 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HCURSOR
/*      */     extends HICON
/*      */   {
/*      */     public HCURSOR() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HCURSOR(Pointer p) {
/*  431 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HMENU
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HMENU() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HMENU(Pointer p) {
/*  454 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HPEN
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HPEN() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HPEN(Pointer p) {
/*  477 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HRSRC
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HRSRC() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HRSRC(Pointer p) {
/*  500 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HPALETTE
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HPALETTE() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HPALETTE(Pointer p) {
/*  523 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HBITMAP
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HBITMAP() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HBITMAP(Pointer p) {
/*  546 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HRGN
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HRGN() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HRGN(Pointer p) {
/*  569 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HWND
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HWND() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HWND(Pointer p) {
/*  592 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HINSTANCE
/*      */     extends WinNT.HANDLE {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HMODULE
/*      */     extends HINSTANCE {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HFONT
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HFONT() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HFONT(Pointer p) {
/*  629 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LPARAM
/*      */     extends BaseTSD.LONG_PTR
/*      */   {
/*      */     public LPARAM() {
/*  642 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LPARAM(long value) {
/*  652 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LRESULT
/*      */     extends BaseTSD.LONG_PTR
/*      */   {
/*      */     public LRESULT() {
/*  665 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LRESULT(long value) {
/*  675 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class INT_PTR
/*      */     extends IntegerType
/*      */   {
/*      */     public INT_PTR() {
/*  686 */       super(Pointer.SIZE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public INT_PTR(long value) {
/*  696 */       super(Pointer.SIZE, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer toPointer() {
/*  705 */       return Pointer.createConstant(longValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class UINT_PTR
/*      */     extends IntegerType
/*      */   {
/*      */     public UINT_PTR() {
/*  718 */       super(Pointer.SIZE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UINT_PTR(long value) {
/*  728 */       super(Pointer.SIZE, value, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer toPointer() {
/*  737 */       return Pointer.createConstant(longValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class WPARAM
/*      */     extends UINT_PTR
/*      */   {
/*      */     public WPARAM() {
/*  750 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WPARAM(long value) {
/*  760 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class RECT
/*      */     extends Structure
/*      */   {
/*  768 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "left", "top", "right", "bottom" });
/*      */ 
/*      */     
/*      */     public int left;
/*      */ 
/*      */     
/*      */     public int top;
/*      */ 
/*      */     
/*      */     public int right;
/*      */     
/*      */     public int bottom;
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  783 */       return FIELDS;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle toRectangle() {
/*  792 */       return new Rectangle(this.left, this.top, this.right - this.left, this.bottom - this.top);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  797 */       return "[(" + this.left + "," + this.top + ")(" + this.right + "," + this.bottom + ")]";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ULONG
/*      */     extends IntegerType
/*      */     implements Comparable<ULONG>
/*      */   {
/*  807 */     public static final int SIZE = Native.LONG_SIZE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ULONG() {
/*  813 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ULONG(long value) {
/*  823 */       super(SIZE, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(ULONG other) {
/*  828 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ULONGByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public ULONGByReference() {
/*  841 */       this(new WinDef.ULONG(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ULONGByReference(WinDef.ULONG value) {
/*  850 */       super(WinDef.ULONG.SIZE);
/*  851 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.ULONG value) {
/*  860 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.ULONG getValue() {
/*  869 */       return new WinDef.ULONG(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ULONGLONG
/*      */     extends IntegerType
/*      */     implements Comparable<ULONGLONG>
/*      */   {
/*  879 */     public static final int SIZE = Native.LONG_SIZE * 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ULONGLONG() {
/*  885 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ULONGLONG(long value) {
/*  894 */       super(SIZE, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(ULONGLONG other) {
/*  899 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ULONGLONGByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public ULONGLONGByReference() {
/*  912 */       this(new WinDef.ULONGLONG(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ULONGLONGByReference(WinDef.ULONGLONG value) {
/*  921 */       super(WinDef.ULONGLONG.SIZE);
/*  922 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.ULONGLONG value) {
/*  931 */       getPointer().setLong(0L, value.longValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.ULONGLONG getValue() {
/*  940 */       return new WinDef.ULONGLONG(getPointer().getLong(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DWORDLONG
/*      */     extends IntegerType
/*      */     implements Comparable<DWORDLONG>
/*      */   {
/*      */     public static final int SIZE = 8;
/*      */ 
/*      */ 
/*      */     
/*      */     public DWORDLONG() {
/*  956 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DWORDLONG(long value) {
/*  966 */       super(8, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(DWORDLONG other) {
/*  971 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HBRUSH
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HBRUSH() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HBRUSH(Pointer p) {
/*  994 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ATOM
/*      */     extends WORD
/*      */   {
/*      */     public ATOM() {
/* 1007 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ATOM(long value) {
/* 1017 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PVOID
/*      */     extends PointerType
/*      */   {
/*      */     public PVOID() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PVOID(Pointer pointer) {
/* 1036 */       super(pointer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LPVOID
/*      */     extends PointerType
/*      */   {
/*      */     public LPVOID() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LPVOID(Pointer p) {
/* 1057 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class POINT
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends POINT
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public ByReference(Pointer memory) {
/* 1075 */         super(memory);
/*      */       }
/*      */       
/*      */       public ByReference(int x, int y) {
/* 1079 */         super(x, y);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByValue
/*      */       extends POINT
/*      */       implements Structure.ByValue
/*      */     {
/*      */       public ByValue() {}
/*      */ 
/*      */       
/*      */       public ByValue(Pointer memory) {
/* 1093 */         super(memory);
/*      */       }
/*      */       
/*      */       public ByValue(int x, int y) {
/* 1097 */         super(x, y);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1102 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "x", "y" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int x;
/*      */ 
/*      */ 
/*      */     
/*      */     public int y;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public POINT() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public POINT(Pointer memory) {
/* 1122 */       super(memory);
/* 1123 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public POINT(int x, int y) {
/* 1135 */       this.x = x;
/* 1136 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1141 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class USHORT
/*      */     extends IntegerType
/*      */     implements Comparable<USHORT>
/*      */   {
/*      */     public static final int SIZE = 2;
/*      */ 
/*      */ 
/*      */     
/*      */     public USHORT() {
/* 1157 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public USHORT(long value) {
/* 1167 */       super(2, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(USHORT other) {
/* 1172 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class USHORTByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public USHORTByReference() {
/* 1185 */       this(new WinDef.USHORT(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public USHORTByReference(WinDef.USHORT value) {
/* 1194 */       super(2);
/* 1195 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public USHORTByReference(short value) {
/* 1204 */       super(2);
/* 1205 */       setValue(new WinDef.USHORT(value));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.USHORT value) {
/* 1214 */       getPointer().setShort(0L, value.shortValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.USHORT getValue() {
/* 1223 */       return new WinDef.USHORT(getPointer().getShort(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SHORT
/*      */     extends IntegerType
/*      */     implements Comparable<SHORT>
/*      */   {
/*      */     public static final int SIZE = 2;
/*      */ 
/*      */ 
/*      */     
/*      */     public SHORT() {
/* 1239 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SHORT(long value) {
/* 1249 */       super(2, value, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(SHORT other) {
/* 1254 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class UINT
/*      */     extends IntegerType
/*      */     implements Comparable<UINT>
/*      */   {
/*      */     public static final int SIZE = 4;
/*      */ 
/*      */ 
/*      */     
/*      */     public UINT() {
/* 1270 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UINT(long value) {
/* 1280 */       super(4, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(UINT other) {
/* 1285 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class UINTByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public UINTByReference() {
/* 1298 */       this(new WinDef.UINT(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UINTByReference(WinDef.UINT value) {
/* 1307 */       super(4);
/* 1308 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.UINT value) {
/* 1317 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.UINT getValue() {
/* 1326 */       return new WinDef.UINT(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SCODE
/*      */     extends ULONG
/*      */   {
/*      */     public SCODE() {
/* 1339 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SCODE(long value) {
/* 1349 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SCODEByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public SCODEByReference() {
/* 1362 */       this(new WinDef.SCODE(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SCODEByReference(WinDef.SCODE value) {
/* 1371 */       super(WinDef.SCODE.SIZE);
/* 1372 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.SCODE value) {
/* 1381 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.SCODE getValue() {
/* 1390 */       return new WinDef.SCODE(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LCID
/*      */     extends DWORD
/*      */   {
/*      */     public LCID() {
/* 1403 */       super(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LCID(long value) {
/* 1412 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BOOL
/*      */     extends IntegerType
/*      */     implements Comparable<BOOL>
/*      */   {
/*      */     public static final int SIZE = 4;
/*      */ 
/*      */ 
/*      */     
/*      */     public BOOL() {
/* 1428 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BOOL(boolean value) {
/* 1437 */       this(value ? 1L : 0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BOOL(long value) {
/* 1446 */       super(4, value, false);
/* 1447 */       assert value == 0L || value == 1L;
/*      */     }
/*      */     
/*      */     public boolean booleanValue() {
/* 1451 */       if (intValue() > 0) {
/* 1452 */         return true;
/*      */       }
/* 1454 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1460 */       return Boolean.toString(booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BOOL other) {
/* 1465 */       return compare(this, other);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int compare(BOOL v1, BOOL v2) {
/* 1482 */       if (v1 == v2)
/* 1483 */         return 0; 
/* 1484 */       if (v1 == null)
/* 1485 */         return 1; 
/* 1486 */       if (v2 == null) {
/* 1487 */         return -1;
/*      */       }
/* 1489 */       return compare(v1.booleanValue(), v2.booleanValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int compare(BOOL v1, boolean v2) {
/* 1505 */       if (v1 == null) {
/* 1506 */         return 1;
/*      */       }
/* 1508 */       return compare(v1.booleanValue(), v2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static int compare(boolean v1, boolean v2) {
/* 1514 */       if (v1 == v2)
/* 1515 */         return 0; 
/* 1516 */       if (v1) {
/* 1517 */         return 1;
/*      */       }
/* 1519 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BOOLByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public BOOLByReference() {
/* 1533 */       this(new WinDef.BOOL(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BOOLByReference(WinDef.BOOL value) {
/* 1542 */       super(4);
/* 1543 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.BOOL value) {
/* 1552 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.BOOL getValue() {
/* 1561 */       return new WinDef.BOOL(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class UCHAR
/*      */     extends IntegerType
/*      */     implements Comparable<UCHAR>
/*      */   {
/*      */     public static final int SIZE = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     public UCHAR() {
/* 1577 */       this(0L);
/*      */     }
/*      */     
/*      */     public UCHAR(char ch) {
/* 1581 */       this((ch & 0xFF));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UCHAR(long value) {
/* 1590 */       super(1, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(UCHAR other) {
/* 1595 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BYTE
/*      */     extends UCHAR
/*      */   {
/*      */     public BYTE() {
/* 1608 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BYTE(long value) {
/* 1617 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CHAR
/*      */     extends IntegerType
/*      */     implements Comparable<CHAR>
/*      */   {
/*      */     public static final int SIZE = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     public CHAR() {
/* 1633 */       this(0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CHAR(byte ch) {
/* 1642 */       this((ch & 0xFF));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CHAR(long value) {
/* 1651 */       super(1, value, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(CHAR other) {
/* 1656 */       return compare(this, other);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CHARByReference
/*      */     extends com.sun.jna.ptr.ByReference
/*      */   {
/*      */     public CHARByReference() {
/* 1669 */       this(new WinDef.CHAR(0L));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CHARByReference(WinDef.CHAR value) {
/* 1678 */       super(1);
/* 1679 */       setValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(WinDef.CHAR value) {
/* 1688 */       getPointer().setByte(0L, value.byteValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.CHAR getValue() {
/* 1697 */       return new WinDef.CHAR(getPointer().getChar(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HGLRC
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HGLRC() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HGLRC(Pointer p) {
/* 1720 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HGLRCByReference
/*      */     extends WinNT.HANDLEByReference
/*      */   {
/*      */     public HGLRCByReference() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HGLRCByReference(WinDef.HGLRC h) {
/* 1743 */       super(h);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinDef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */