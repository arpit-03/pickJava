/*    */ package com.jmatio.io;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public class MatFileFilter
/*    */ {
/* 38 */   private Set<String> filter = new HashSet<String>();
/*    */ 
/*    */ 
/*    */   
/*    */   public MatFileFilter() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public MatFileFilter(String[] names) {
/* 47 */     this();
/*    */     
/* 49 */     for (String name : names)
/*    */     {
/* 51 */       addArrayName(name);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addArrayName(String name) {
/* 62 */     this.filter.add(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(String name) {
/* 72 */     if (this.filter.size() == 0)
/*    */     {
/* 74 */       return true;
/*    */     }
/* 76 */     return this.filter.contains(name);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatFileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */