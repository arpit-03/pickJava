/*    */ package com.sun.jna.platform.win32;
/*    */ 
/*    */ import com.sun.jna.Native;
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.Structure;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Wdm
/*    */ {
/*    */   public static class KEY_BASIC_INFORMATION
/*    */     extends Structure
/*    */   {
/* 44 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "LastWriteTime", "TitleIndex", "NameLength", "Name" });
/*    */ 
/*    */ 
/*    */     
/*    */     public long LastWriteTime;
/*    */ 
/*    */ 
/*    */     
/*    */     public int TitleIndex;
/*    */ 
/*    */ 
/*    */     
/*    */     public int NameLength;
/*    */ 
/*    */ 
/*    */     
/*    */     public char[] Name;
/*    */ 
/*    */ 
/*    */     
/*    */     public KEY_BASIC_INFORMATION() {}
/*    */ 
/*    */ 
/*    */     
/*    */     public KEY_BASIC_INFORMATION(int size) {
/* 69 */       this.NameLength = size - 16;
/* 70 */       this.Name = new char[this.NameLength];
/* 71 */       allocateMemory();
/*    */     }
/*    */     
/*    */     public KEY_BASIC_INFORMATION(Pointer memory) {
/* 75 */       super(memory);
/* 76 */       read();
/*    */     }
/*    */ 
/*    */     
/*    */     protected List<String> getFieldOrder() {
/* 81 */       return FIELDS;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public String getName() {
/* 88 */       return Native.toString(this.Name);
/*    */     }
/*    */ 
/*    */     
/*    */     public void read() {
/* 93 */       super.read();
/* 94 */       this.Name = new char[this.NameLength / 2];
/* 95 */       readField("Name");
/*    */     }
/*    */   }
/*    */   
/*    */   public static abstract class KEY_INFORMATION_CLASS {
/*    */     public static final int KeyBasicInformation = 0;
/*    */     public static final int KeyNodeInformation = 1;
/*    */     public static final int KeyFullInformation = 2;
/*    */     public static final int KeyNameInformation = 3;
/*    */     public static final int KeyCachedInformation = 4;
/*    */     public static final int KeyVirtualizationInformation = 5;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Wdm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */