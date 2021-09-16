/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
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
/*     */ public class ExtendedConfigurationWrapper
/*     */   implements ExtendedConfiguration
/*     */ {
/*     */   private Configuration parent;
/*     */   
/*     */   public ExtendedConfigurationWrapper(Configuration parent) {
/*  66 */     if (parent == null)
/*     */     {
/*  68 */       throw new NullPointerException("Parent given must not be null");
/*     */     }
/*  70 */     this.parent = parent;
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
/*     */   public boolean getBoolProperty(String name) {
/*  82 */     return getBoolProperty(name, false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolProperty(String name, boolean defaultValue) {
/*  97 */     return "true".equals(this.parent.getConfigProperty(name, String.valueOf(defaultValue)));
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
/*     */   public int getIntProperty(String name) {
/* 109 */     return getIntProperty(name, 0);
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
/*     */ 
/*     */   
/*     */   public int getIntProperty(String name, int defaultValue) {
/* 123 */     String retval = this.parent.getConfigProperty(name);
/* 124 */     if (retval == null)
/*     */     {
/* 126 */       return defaultValue;
/*     */     }
/*     */     
/*     */     try {
/* 130 */       return Integer.parseInt(retval);
/*     */     }
/* 132 */     catch (Exception e) {
/*     */       
/* 134 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPropertySet(String name) {
/* 146 */     return (this.parent.getConfigProperty(name) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator findPropertyKeys(String prefix) {
/* 157 */     return this.parent.findPropertyKeys(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getConfigProperty(String key) {
/* 168 */     return this.parent.getConfigProperty(key);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getConfigProperty(String key, String defaultValue) {
/* 184 */     return this.parent.getConfigProperty(key, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration getConfigProperties() {
/* 194 */     return this.parent.getConfigProperties();
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 206 */     ExtendedConfigurationWrapper wrapper = (ExtendedConfigurationWrapper)super.clone();
/* 207 */     wrapper.parent = (Configuration)this.parent.clone();
/* 208 */     return this.parent;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ExtendedConfigurationWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */