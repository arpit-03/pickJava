/*    */ package org.apache.commons.math3.ode;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
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
/*    */ public abstract class AbstractParameterizable
/*    */   implements Parameterizable
/*    */ {
/*    */   private final Collection<String> parametersNames;
/*    */   
/*    */   protected AbstractParameterizable(String... names) {
/* 36 */     this.parametersNames = new ArrayList<String>();
/* 37 */     for (String name : names) {
/* 38 */       this.parametersNames.add(name);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractParameterizable(Collection<String> names) {
/* 46 */     this.parametersNames = new ArrayList<String>();
/* 47 */     this.parametersNames.addAll(names);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getParametersNames() {
/* 52 */     return this.parametersNames;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupported(String name) {
/* 57 */     for (String supportedName : this.parametersNames) {
/* 58 */       if (supportedName.equals(name)) {
/* 59 */         return true;
/*    */       }
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void complainIfNotSupported(String name) throws UnknownParameterException {
/* 72 */     if (!isSupported(name))
/* 73 */       throw new UnknownParameterException(name); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/AbstractParameterizable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */