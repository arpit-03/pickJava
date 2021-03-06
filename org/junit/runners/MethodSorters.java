/*    */ package org.junit.runners;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Comparator;
/*    */ import org.junit.internal.MethodSorter;
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
/*    */ public enum MethodSorters
/*    */ {
/* 19 */   NAME_ASCENDING(MethodSorter.NAME_ASCENDING),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   JVM(null),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   DEFAULT(MethodSorter.DEFAULT);
/*    */   
/*    */   private final Comparator<Method> comparator;
/*    */   
/*    */   MethodSorters(Comparator<Method> comparator) {
/* 35 */     this.comparator = comparator;
/*    */   }
/*    */   
/*    */   public Comparator<Method> getComparator() {
/* 39 */     return this.comparator;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/runners/MethodSorters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */