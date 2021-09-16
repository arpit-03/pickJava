/*    */ package com.boreholeseismic.io;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ 
/*    */ public class ExtensionFileFilter
/*    */   implements FilenameFilter
/*    */ {
/*    */   private String[] m_extensions;
/*    */   
/*    */   public ExtensionFileFilter(String[] extensions) {
/* 12 */     this.m_extensions = extensions;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExtensionFileFilter(String extension) {
/* 17 */     this.m_extensions = new String[] { extension };
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accept(File file, String name) {
/* 22 */     for (int i = 0; i < this.m_extensions.length; i++) {
/*    */       
/* 24 */       if (name.endsWith(this.m_extensions[i]))
/*    */       {
/* 26 */         return true;
/*    */       }
/*    */     } 
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/io/ExtensionFileFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */