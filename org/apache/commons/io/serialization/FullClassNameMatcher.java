/*    */ package org.apache.commons.io.serialization;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
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
/*    */ final class FullClassNameMatcher
/*    */   implements ClassNameMatcher
/*    */ {
/*    */   private final Set<String> classesSet;
/*    */   
/*    */   public FullClassNameMatcher(String... classes) {
/* 42 */     this.classesSet = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(classes)));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(String className) {
/* 47 */     return this.classesSet.contains(className);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/io/serialization/FullClassNameMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */