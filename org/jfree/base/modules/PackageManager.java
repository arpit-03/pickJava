/*     */ package org.jfree.base.modules;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.jfree.base.AbstractBoot;
/*     */ import org.jfree.base.config.HierarchicalConfiguration;
/*     */ import org.jfree.base.config.PropertyFileConfiguration;
/*     */ import org.jfree.base.log.PadMessage;
/*     */ import org.jfree.util.Configuration;
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
/*     */ public final class PackageManager
/*     */ {
/*     */   private static final int RETURN_MODULE_LOADED = 0;
/*     */   private static final int RETURN_MODULE_UNKNOWN = 1;
/*     */   private static final int RETURN_MODULE_ERROR = 2;
/*     */   private final PackageConfiguration packageConfiguration;
/*     */   private final ArrayList modules;
/*     */   private final ArrayList initSections;
/*     */   private AbstractBoot booter;
/*     */   private static HashMap instances;
/*     */   
/*     */   public static class PackageConfiguration
/*     */     extends PropertyFileConfiguration
/*     */   {
/*     */     public void insertConfiguration(HierarchicalConfiguration config) {
/*  95 */       super.insertConfiguration(config);
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
/*     */   public static PackageManager createInstance(AbstractBoot booter) {
/* 144 */     if (instances == null) {
/* 145 */       instances = new HashMap<Object, Object>();
/* 146 */       PackageManager packageManager = new PackageManager(booter);
/* 147 */       instances.put(booter, packageManager);
/* 148 */       return packageManager;
/*     */     } 
/* 150 */     PackageManager manager = (PackageManager)instances.get(booter);
/* 151 */     if (manager == null) {
/* 152 */       manager = new PackageManager(booter);
/* 153 */       instances.put(booter, manager);
/*     */     } 
/* 155 */     return manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PackageManager(AbstractBoot booter) {
/* 164 */     if (booter == null) {
/* 165 */       throw new NullPointerException();
/*     */     }
/* 167 */     this.booter = booter;
/* 168 */     this.packageConfiguration = new PackageConfiguration();
/* 169 */     this.modules = new ArrayList();
/* 170 */     this.initSections = new ArrayList();
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
/*     */   public boolean isModuleAvailable(ModuleInfo moduleDescription) {
/* 182 */     PackageState[] packageStates = (PackageState[])this.modules.toArray((Object[])new PackageState[this.modules.size()]);
/* 183 */     for (int i = 0; i < packageStates.length; i++) {
/* 184 */       PackageState state = packageStates[i];
/* 185 */       if (state.getModule().getModuleClass().equals(moduleDescription.getModuleClass())) {
/* 186 */         return (state.getState() == 2);
/*     */       }
/*     */     } 
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String modulePrefix) {
/* 200 */     if (this.initSections.contains(modulePrefix)) {
/*     */       return;
/*     */     }
/* 203 */     this.initSections.add(modulePrefix);
/*     */     
/* 205 */     Configuration config = this.booter.getGlobalConfig();
/* 206 */     Iterator<String> it = config.findPropertyKeys(modulePrefix);
/* 207 */     int count = 0;
/* 208 */     while (it.hasNext()) {
/* 209 */       String key = it.next();
/* 210 */       if (key.endsWith(".Module")) {
/* 211 */         String moduleClass = config.getConfigProperty(key);
/* 212 */         if (moduleClass != null && moduleClass.length() > 0) {
/* 213 */           addModule(moduleClass);
/* 214 */           count++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 218 */     Log.debug("Loaded a total of " + count + " modules under prefix: " + modulePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void initializeModules() {
/* 227 */     PackageSorter.sort(this.modules);
/*     */     int i;
/* 229 */     for (i = 0; i < this.modules.size(); i++) {
/* 230 */       PackageState mod = this.modules.get(i);
/* 231 */       if (mod.configure((SubSystem)this.booter)) {
/* 232 */         Log.debug(new Log.SimpleMessage("Conf: ", new PadMessage(mod
/* 233 */                 .getModule().getModuleClass(), 70), " [", mod
/* 234 */               .getModule().getSubSystem(), "]"));
/*     */       }
/*     */     } 
/*     */     
/* 238 */     for (i = 0; i < this.modules.size(); i++) {
/* 239 */       PackageState mod = this.modules.get(i);
/* 240 */       if (mod.initialize((SubSystem)this.booter)) {
/* 241 */         Log.debug(new Log.SimpleMessage("Init: ", new PadMessage(mod
/* 242 */                 .getModule().getModuleClass(), 70), " [", mod
/* 243 */               .getModule().getSubSystem(), "]"));
/*     */       }
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
/*     */   public synchronized void addModule(String modClass) {
/* 256 */     ArrayList<Module> loadModules = new ArrayList();
/* 257 */     ModuleInfo modInfo = new DefaultModuleInfo(modClass, null, null, null);
/*     */     
/* 259 */     if (loadModule(modInfo, new ArrayList(), loadModules, false)) {
/* 260 */       for (int i = 0; i < loadModules.size(); i++) {
/* 261 */         Module mod = loadModules.get(i);
/* 262 */         this.modules.add(new PackageState(mod));
/*     */       } 
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
/*     */   private int containsModule(ArrayList tempModules, ModuleInfo module) {
/* 277 */     if (tempModules != null) {
/*     */       
/* 279 */       ModuleInfo[] mods = (ModuleInfo[])tempModules.toArray((Object[])new ModuleInfo[tempModules.size()]);
/* 280 */       for (int j = 0; j < mods.length; j++) {
/* 281 */         if (mods[j].getModuleClass().equals(module.getModuleClass())) {
/* 282 */           return 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 288 */     PackageState[] packageStates = (PackageState[])this.modules.toArray((Object[])new PackageState[this.modules.size()]);
/* 289 */     for (int i = 0; i < packageStates.length; i++) {
/* 290 */       if (packageStates[i].getModule().getModuleClass().equals(module.getModuleClass())) {
/* 291 */         if (packageStates[i].getState() == -2) {
/* 292 */           return 2;
/*     */         }
/*     */         
/* 295 */         return 0;
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dropFailedModule(PackageState state) {
/* 310 */     if (!this.modules.contains(state)) {
/* 311 */       this.modules.add(state);
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
/*     */   private boolean loadModule(ModuleInfo moduleInfo, ArrayList<Module> incompleteModules, ArrayList<Module> modules, boolean fatal) {
/*     */     try {
/* 334 */       Class<?> c = ObjectUtilities.getClassLoader(getClass()).loadClass(moduleInfo.getModuleClass());
/* 335 */       Module module = (Module)c.newInstance();
/*     */       
/* 337 */       if (!acceptVersion(moduleInfo, module)) {
/*     */         
/* 339 */         Log.warn("Module " + module.getName() + ": required version: " + moduleInfo + ", but found Version: \n" + module);
/*     */         
/* 341 */         PackageState state = new PackageState(module, -2);
/* 342 */         dropFailedModule(state);
/* 343 */         return false;
/*     */       } 
/*     */       
/* 346 */       int moduleContained = containsModule(modules, module);
/* 347 */       if (moduleContained == 2) {
/*     */         
/* 349 */         Log.debug("Indicated failure for module: " + module.getModuleClass());
/* 350 */         PackageState state = new PackageState(module, -2);
/* 351 */         dropFailedModule(state);
/* 352 */         return false;
/*     */       } 
/* 354 */       if (moduleContained == 1) {
/* 355 */         if (incompleteModules.contains(module)) {
/*     */           
/* 357 */           Log.error(new Log.SimpleMessage("Circular module reference: This module definition is invalid: ", module
/*     */                 
/* 359 */                 .getClass()));
/* 360 */           PackageState state = new PackageState(module, -2);
/* 361 */           dropFailedModule(state);
/* 362 */           return false;
/*     */         } 
/* 364 */         incompleteModules.add(module);
/* 365 */         ModuleInfo[] required = module.getRequiredModules();
/* 366 */         for (int i = 0; i < required.length; i++) {
/* 367 */           if (!loadModule(required[i], incompleteModules, modules, true)) {
/* 368 */             Log.debug("Indicated failure for module: " + module.getModuleClass());
/* 369 */             PackageState state = new PackageState(module, -2);
/* 370 */             dropFailedModule(state);
/* 371 */             return false;
/*     */           } 
/*     */         } 
/*     */         
/* 375 */         ModuleInfo[] optional = module.getOptionalModules();
/* 376 */         for (int j = 0; j < optional.length; j++) {
/* 377 */           if (!loadModule(optional[j], incompleteModules, modules, true)) {
/* 378 */             Log.debug(new Log.SimpleMessage("Optional module: ", optional[j]
/* 379 */                   .getModuleClass(), " was not loaded."));
/*     */           }
/*     */         } 
/*     */         
/* 383 */         if (containsModule(modules, module) == 1) {
/* 384 */           modules.add(module);
/*     */         }
/* 386 */         incompleteModules.remove(module);
/*     */       } 
/* 388 */       return true;
/*     */     }
/* 390 */     catch (ClassNotFoundException cnfe) {
/* 391 */       if (fatal) {
/* 392 */         Log.warn(new Log.SimpleMessage("Unresolved dependency for package: ", moduleInfo
/* 393 */               .getModuleClass()));
/*     */       }
/* 395 */       Log.debug(new Log.SimpleMessage("ClassNotFound: ", cnfe.getMessage()));
/* 396 */       return false;
/*     */     }
/* 398 */     catch (Exception e) {
/* 399 */       Log.warn(new Log.SimpleMessage("Exception while loading module: ", moduleInfo), e);
/* 400 */       return false;
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
/*     */   private boolean acceptVersion(ModuleInfo moduleRequirement, Module module) {
/* 413 */     if (moduleRequirement.getMajorVersion() == null) {
/* 414 */       return true;
/*     */     }
/* 416 */     if (module.getMajorVersion() == null) {
/* 417 */       Log.warn("Module " + module.getName() + " does not define a major version.");
/*     */     } else {
/*     */       
/* 420 */       int compare = acceptVersion(moduleRequirement.getMajorVersion(), module
/* 421 */           .getMajorVersion());
/* 422 */       if (compare > 0) {
/* 423 */         return false;
/*     */       }
/* 425 */       if (compare < 0) {
/* 426 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 430 */     if (moduleRequirement.getMinorVersion() == null) {
/* 431 */       return true;
/*     */     }
/* 433 */     if (module.getMinorVersion() == null) {
/* 434 */       Log.warn("Module " + module.getName() + " does not define a minor version.");
/*     */     } else {
/*     */       
/* 437 */       int compare = acceptVersion(moduleRequirement.getMinorVersion(), module
/* 438 */           .getMinorVersion());
/* 439 */       if (compare > 0) {
/* 440 */         return false;
/*     */       }
/* 442 */       if (compare < 0) {
/* 443 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 447 */     if (moduleRequirement.getPatchLevel() == null) {
/* 448 */       return true;
/*     */     }
/* 450 */     if (module.getPatchLevel() == null) {
/* 451 */       Log.debug("Module " + module.getName() + " does not define a patch level.");
/*     */     
/*     */     }
/* 454 */     else if (acceptVersion(moduleRequirement.getPatchLevel(), module
/* 455 */         .getPatchLevel()) > 0) {
/* 456 */       Log.debug("Did not accept patchlevel: " + moduleRequirement
/* 457 */           .getPatchLevel() + " - " + module
/* 458 */           .getPatchLevel());
/* 459 */       return false;
/*     */     } 
/*     */     
/* 462 */     return true;
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
/*     */   private int acceptVersion(String modVer, String depModVer) {
/*     */     char[] modVerArray, depVerArray;
/* 477 */     int mLength = Math.max(modVer.length(), depModVer.length());
/*     */ 
/*     */     
/* 480 */     if (modVer.length() > depModVer.length()) {
/* 481 */       modVerArray = modVer.toCharArray();
/* 482 */       depVerArray = new char[mLength];
/* 483 */       int delta = modVer.length() - depModVer.length();
/* 484 */       Arrays.fill(depVerArray, 0, delta, ' ');
/* 485 */       System.arraycopy(depVerArray, delta, depModVer.toCharArray(), 0, depModVer.length());
/*     */     }
/* 487 */     else if (modVer.length() < depModVer.length()) {
/* 488 */       depVerArray = depModVer.toCharArray();
/* 489 */       modVerArray = new char[mLength];
/* 490 */       char[] b1 = new char[mLength];
/* 491 */       int delta = depModVer.length() - modVer.length();
/* 492 */       Arrays.fill(b1, 0, delta, ' ');
/* 493 */       System.arraycopy(b1, delta, modVer.toCharArray(), 0, modVer.length());
/*     */     } else {
/*     */       
/* 496 */       depVerArray = depModVer.toCharArray();
/* 497 */       modVerArray = modVer.toCharArray();
/*     */     } 
/* 499 */     return (new String(modVerArray)).compareTo(new String(depVerArray));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackageConfiguration getPackageConfiguration() {
/* 510 */     return this.packageConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Module[] getAllModules() {
/* 521 */     Module[] mods = new Module[this.modules.size()];
/* 522 */     for (int i = 0; i < this.modules.size(); i++) {
/* 523 */       PackageState state = this.modules.get(i);
/* 524 */       mods[i] = state.getModule();
/*     */     } 
/* 526 */     return mods;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Module[] getActiveModules() {
/* 536 */     ArrayList<Module> mods = new ArrayList();
/* 537 */     for (int i = 0; i < this.modules.size(); i++) {
/* 538 */       PackageState state = this.modules.get(i);
/* 539 */       if (state.getState() == 2) {
/* 540 */         mods.add(state.getModule());
/*     */       }
/*     */     } 
/* 543 */     return mods.<Module>toArray(new Module[mods.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printUsedModules(PrintStream p) {
/* 552 */     Module[] allMods = getAllModules();
/* 553 */     ArrayList<Module> activeModules = new ArrayList();
/* 554 */     ArrayList<Module> failedModules = new ArrayList();
/*     */     int i;
/* 556 */     for (i = 0; i < allMods.length; i++) {
/* 557 */       if (isModuleAvailable(allMods[i])) {
/* 558 */         activeModules.add(allMods[i]);
/*     */       } else {
/*     */         
/* 561 */         failedModules.add(allMods[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 565 */     p.print("Active modules: ");
/* 566 */     p.println(activeModules.size());
/* 567 */     p.println("----------------------------------------------------------");
/* 568 */     for (i = 0; i < activeModules.size(); i++) {
/* 569 */       Module mod = activeModules.get(i);
/* 570 */       p.print(new PadMessage(mod.getModuleClass(), 70));
/* 571 */       p.print(" [");
/* 572 */       p.print(mod.getSubSystem());
/* 573 */       p.println("]");
/* 574 */       p.print("  Version: ");
/* 575 */       p.print(mod.getMajorVersion());
/* 576 */       p.print("-");
/* 577 */       p.print(mod.getMinorVersion());
/* 578 */       p.print("-");
/* 579 */       p.print(mod.getPatchLevel());
/* 580 */       p.print(" Producer: ");
/* 581 */       p.println(mod.getProducer());
/* 582 */       p.print("  Description: ");
/* 583 */       p.println(mod.getDescription());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/modules/PackageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */