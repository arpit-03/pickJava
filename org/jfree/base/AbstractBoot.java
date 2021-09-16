/*     */ package org.jfree.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import org.jfree.base.config.HierarchicalConfiguration;
/*     */ import org.jfree.base.config.PropertyFileConfiguration;
/*     */ import org.jfree.base.config.SystemPropertyConfiguration;
/*     */ import org.jfree.base.modules.PackageManager;
/*     */ import org.jfree.base.modules.SubSystem;
/*     */ import org.jfree.util.Configuration;
/*     */ import org.jfree.util.ExtendedConfiguration;
/*     */ import org.jfree.util.ExtendedConfigurationWrapper;
/*     */ import org.jfree.util.Log;
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
/*     */ public abstract class AbstractBoot
/*     */   implements SubSystem
/*     */ {
/*     */   private ExtendedConfigurationWrapper extWrapper;
/*     */   private PackageManager packageManager;
/*     */   private Configuration globalConfig;
/*     */   private boolean bootInProgress;
/*     */   private boolean bootDone;
/*     */   
/*     */   public synchronized PackageManager getPackageManager() {
/* 108 */     if (this.packageManager == null) {
/* 109 */       this.packageManager = PackageManager.createInstance(this);
/*     */     }
/* 111 */     return this.packageManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Configuration getGlobalConfig() {
/* 120 */     if (this.globalConfig == null) {
/* 121 */       this.globalConfig = loadConfiguration();
/*     */     }
/* 123 */     return this.globalConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized boolean isBootInProgress() {
/* 132 */     return this.bootInProgress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized boolean isBootDone() {
/* 141 */     return this.bootDone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Configuration loadConfiguration();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void start() {
/* 156 */     synchronized (this) {
/* 157 */       if (isBootDone()) {
/*     */         return;
/*     */       }
/* 160 */       while (isBootInProgress()) {
/*     */         try {
/* 162 */           wait();
/*     */         }
/* 164 */         catch (InterruptedException e) {}
/*     */       } 
/*     */ 
/*     */       
/* 168 */       if (isBootDone()) {
/*     */         return;
/*     */       }
/* 171 */       this.bootInProgress = true;
/*     */     } 
/*     */ 
/*     */     
/* 175 */     BootableProjectInfo info = getProjectInfo();
/* 176 */     if (info != null) {
/* 177 */       BootableProjectInfo[] childs = info.getDependencies();
/* 178 */       for (int i = 0; i < childs.length; i++) {
/* 179 */         AbstractBoot boot = loadBooter(childs[i].getBootClass());
/* 180 */         if (boot != null)
/*     */         {
/* 182 */           synchronized (boot) {
/* 183 */             boot.start();
/* 184 */             while (!boot.isBootDone()) {
/*     */               try {
/* 186 */                 boot.wait();
/*     */               }
/* 188 */               catch (InterruptedException e) {}
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     performBoot();
/* 198 */     if (info != null) {
/*     */       
/* 200 */       Log.info(info.getName() + " " + info.getVersion() + " started.");
/*     */     }
/*     */     else {
/*     */       
/* 204 */       Log.info(getClass() + " started.");
/*     */     } 
/*     */     
/* 207 */     synchronized (this) {
/* 208 */       this.bootInProgress = false;
/* 209 */       this.bootDone = true;
/* 210 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void performBoot();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BootableProjectInfo getProjectInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractBoot loadBooter(String classname) {
/* 234 */     if (classname == null) {
/* 235 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 239 */       Class<?> c = ObjectUtilities.getClassLoader(getClass()).loadClass(classname);
/* 240 */       Method m = c.getMethod("getInstance", (Class[])null);
/* 241 */       return (AbstractBoot)m.invoke(null, (Object[])null);
/*     */     }
/* 243 */     catch (Exception e) {
/* 244 */       Log.info("Unable to boot dependent class: " + classname);
/* 245 */       return null;
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
/*     */   protected Configuration createDefaultHierarchicalConfiguration(String staticConfig, String userConfig, boolean addSysProps) {
/* 270 */     return createDefaultHierarchicalConfiguration(staticConfig, userConfig, addSysProps, PropertyFileConfiguration.class);
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
/*     */   protected Configuration createDefaultHierarchicalConfiguration(String staticConfig, String userConfig, boolean addSysProps, Class source) {
/* 287 */     HierarchicalConfiguration globalConfig = new HierarchicalConfiguration();
/*     */ 
/*     */     
/* 290 */     if (staticConfig != null) {
/* 291 */       PropertyFileConfiguration rootProperty = new PropertyFileConfiguration();
/*     */       
/* 293 */       rootProperty.load(staticConfig, getClass());
/* 294 */       globalConfig.insertConfiguration((HierarchicalConfiguration)rootProperty);
/* 295 */       globalConfig.insertConfiguration((HierarchicalConfiguration)
/* 296 */           getPackageManager().getPackageConfiguration());
/*     */     } 
/* 298 */     if (userConfig != null) {
/*     */       String userConfigStripped;
/* 300 */       if (userConfig.startsWith("/")) {
/* 301 */         userConfigStripped = userConfig.substring(1);
/*     */       } else {
/*     */         
/* 304 */         userConfigStripped = userConfig;
/*     */       } 
/*     */       
/*     */       try {
/* 308 */         Enumeration<URL> userConfigs = ObjectUtilities.getClassLoader(getClass()).getResources(userConfigStripped);
/* 309 */         ArrayList<PropertyFileConfiguration> configs = new ArrayList();
/* 310 */         while (userConfigs.hasMoreElements()) {
/* 311 */           URL url = userConfigs.nextElement();
/*     */           try {
/* 313 */             PropertyFileConfiguration baseProperty = new PropertyFileConfiguration();
/*     */             
/* 315 */             InputStream in = url.openStream();
/* 316 */             baseProperty.load(in);
/* 317 */             in.close();
/* 318 */             configs.add(baseProperty);
/*     */           }
/* 320 */           catch (IOException ioe) {
/* 321 */             Log.warn("Failed to load the user configuration at " + url, ioe);
/*     */           } 
/*     */         } 
/*     */         
/* 325 */         for (int i = configs.size() - 1; i >= 0; i--)
/*     */         {
/* 327 */           PropertyFileConfiguration baseProperty = configs.get(i);
/* 328 */           globalConfig.insertConfiguration((HierarchicalConfiguration)baseProperty);
/*     */         }
/*     */       
/* 331 */       } catch (IOException e) {
/* 332 */         Log.warn("Failed to lookup the user configurations.", e);
/*     */       } 
/*     */     } 
/* 335 */     if (addSysProps) {
/* 336 */       SystemPropertyConfiguration systemConfig = new SystemPropertyConfiguration();
/*     */       
/* 338 */       globalConfig.insertConfiguration((HierarchicalConfiguration)systemConfig);
/*     */     } 
/* 340 */     return (Configuration)globalConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ExtendedConfiguration getExtendedConfig() {
/* 350 */     if (this.extWrapper == null) {
/* 351 */       this.extWrapper = new ExtendedConfigurationWrapper(getGlobalConfig());
/*     */     }
/* 353 */     return (ExtendedConfiguration)this.extWrapper;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/AbstractBoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */