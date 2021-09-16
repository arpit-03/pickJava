/*     */ package org.jfree.base;
/*     */ 
/*     */ import org.jfree.JCommon;
/*     */ import org.jfree.base.config.ModifiableConfiguration;
/*     */ import org.jfree.base.log.DefaultLogModule;
/*     */ import org.jfree.util.Configuration;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseBoot
/*     */   extends AbstractBoot
/*     */ {
/*     */   private static BaseBoot singleton;
/*  73 */   private BootableProjectInfo bootableProjectInfo = (BootableProjectInfo)JCommon.INFO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ModifiableConfiguration getConfiguration() {
/*  82 */     return (ModifiableConfiguration)getInstance().getGlobalConfig();
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
/*     */ 
/*     */   
/*     */   protected synchronized Configuration loadConfiguration() {
/* 100 */     return createDefaultHierarchicalConfiguration("/org/jfree/base/jcommon.properties", "/jcommon.properties", true, BaseBoot.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized AbstractBoot getInstance() {
/* 110 */     if (singleton == null) {
/* 111 */       singleton = new BaseBoot();
/*     */     }
/* 113 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void performBoot() {
/* 122 */     ObjectUtilities.setClassLoaderSource(getConfiguration().getConfigProperty("org.jfree.ClassLoader"));
/*     */     
/* 124 */     getPackageManager().addModule(DefaultLogModule.class.getName());
/* 125 */     getPackageManager().load("org.jfree.jcommon.modules.");
/* 126 */     getPackageManager().initializeModules();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BootableProjectInfo getProjectInfo() {
/* 135 */     return this.bootableProjectInfo;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/BaseBoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */