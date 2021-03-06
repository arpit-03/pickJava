/*     */ package org.jfree.base.log;
/*     */ 
/*     */ import org.jfree.util.Log;
/*     */ import org.jfree.util.LogTarget;
/*     */ import org.jfree.util.PrintStreamLogTarget;
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
/*     */ public class DefaultLog
/*     */   extends Log
/*     */ {
/*  58 */   private static final PrintStreamLogTarget DEFAULT_LOG_TARGET = new PrintStreamLogTarget();
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
/*  72 */   private static final DefaultLog defaultLogInstance = new DefaultLog(); static {
/*  73 */     defaultLogInstance.addTarget((LogTarget)DEFAULT_LOG_TARGET);
/*     */ 
/*     */     
/*     */     try {
/*  77 */       String property = System.getProperty("org.jfree.DebugDefault", "false");
/*  78 */       if (Boolean.valueOf(property).booleanValue()) {
/*  79 */         defaultLogInstance.setDebuglevel(3);
/*     */       } else {
/*     */         
/*  82 */         defaultLogInstance.setDebuglevel(1);
/*     */       }
/*     */     
/*  85 */     } catch (SecurityException se) {
/*  86 */       defaultLogInstance.setDebuglevel(1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  95 */     removeTarget((LogTarget)DEFAULT_LOG_TARGET);
/*  96 */     String logLevel = LogConfiguration.getLogLevel();
/*  97 */     if (logLevel.equalsIgnoreCase("error")) {
/*  98 */       setDebuglevel(0);
/*     */     }
/* 100 */     else if (logLevel.equalsIgnoreCase("warn")) {
/* 101 */       setDebuglevel(1);
/*     */     }
/* 103 */     else if (logLevel.equalsIgnoreCase("info")) {
/* 104 */       setDebuglevel(2);
/*     */     }
/* 106 */     else if (logLevel.equalsIgnoreCase("debug")) {
/* 107 */       setDebuglevel(3);
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
/*     */   public synchronized void addTarget(LogTarget target) {
/* 119 */     super.addTarget(target);
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (target != DEFAULT_LOG_TARGET) {
/* 124 */       removeTarget((LogTarget)DEFAULT_LOG_TARGET);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultLog getDefaultLog() {
/* 134 */     return defaultLogInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void installDefaultLog() {
/* 141 */     Log.defineLog(defaultLogInstance);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/log/DefaultLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */