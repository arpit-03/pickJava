/*     */ package org.jfree.base.modules;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PackageSorter
/*     */ {
/*     */   private static class SortModule
/*     */     implements Comparable
/*     */   {
/*     */     private int position;
/*     */     private final PackageState state;
/*     */     private ArrayList dependSubsystems;
/*     */     
/*     */     public SortModule(PackageState state) {
/*  93 */       this.position = -1;
/*  94 */       this.state = state;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayList getDependSubsystems() {
/* 105 */       return this.dependSubsystems;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDependSubsystems(ArrayList dependSubsystems) {
/* 116 */       this.dependSubsystems = dependSubsystems;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getPosition() {
/* 128 */       return this.position;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPosition(int position) {
/* 139 */       this.position = position;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PackageState getState() {
/* 149 */       return this.state;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 161 */       StringBuffer buffer = new StringBuffer();
/* 162 */       buffer.append("SortModule: ");
/* 163 */       buffer.append(this.position);
/* 164 */       buffer.append(" ");
/* 165 */       buffer.append(this.state.getModule().getName());
/* 166 */       buffer.append(" ");
/* 167 */       buffer.append(this.state.getModule().getModuleClass());
/* 168 */       return buffer.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Object o) {
/* 184 */       SortModule otherModule = (SortModule)o;
/* 185 */       if (this.position > otherModule.position)
/*     */       {
/* 187 */         return 1;
/*     */       }
/* 189 */       if (this.position < otherModule.position)
/*     */       {
/* 191 */         return -1;
/*     */       }
/* 193 */       return 0;
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
/*     */   public static void sort(List<PackageState> modules) {
/* 214 */     HashMap<Object, Object> moduleMap = new HashMap<Object, Object>();
/* 215 */     ArrayList<PackageState> errorModules = new ArrayList();
/* 216 */     ArrayList<SortModule> weightModules = new ArrayList();
/*     */     
/* 218 */     for (int i = 0; i < modules.size(); i++) {
/*     */       
/* 220 */       PackageState state = modules.get(i);
/* 221 */       if (state.getState() == -2) {
/*     */         
/* 223 */         errorModules.add(state);
/*     */       }
/*     */       else {
/*     */         
/* 227 */         SortModule mod = new SortModule(state);
/* 228 */         weightModules.add(mod);
/* 229 */         moduleMap.put(state.getModule().getModuleClass(), mod);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 234 */     SortModule[] weigths = weightModules.<SortModule>toArray(new SortModule[weightModules.size()]);
/*     */     
/* 236 */     for (int j = 0; j < weigths.length; j++) {
/*     */       
/* 238 */       SortModule sortMod = weigths[j];
/* 239 */       sortMod
/* 240 */         .setDependSubsystems(collectSubsystemModules(sortMod.getState().getModule(), moduleMap));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     boolean doneWork = true;
/* 251 */     while (doneWork) {
/*     */       
/* 253 */       doneWork = false;
/* 254 */       for (int m = 0; m < weigths.length; m++) {
/*     */         
/* 256 */         SortModule mod = weigths[m];
/* 257 */         int position = searchModulePosition(mod, moduleMap);
/* 258 */         if (position != mod.getPosition()) {
/*     */           
/* 260 */           mod.setPosition(position);
/* 261 */           doneWork = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     Arrays.sort((Object[])weigths);
/* 267 */     modules.clear(); int k;
/* 268 */     for (k = 0; k < weigths.length; k++)
/*     */     {
/* 270 */       modules.add(weigths[k].getState());
/*     */     }
/* 272 */     for (k = 0; k < errorModules.size(); k++)
/*     */     {
/* 274 */       modules.add(errorModules.get(k));
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
/*     */   private static int searchModulePosition(SortModule smodule, HashMap moduleMap) {
/* 290 */     Module module = smodule.getState().getModule();
/* 291 */     int position = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     ModuleInfo[] modInfo = module.getOptionalModules(); int modPos;
/* 297 */     for (modPos = 0; modPos < modInfo.length; modPos++) {
/*     */       
/* 299 */       String moduleName = modInfo[modPos].getModuleClass();
/* 300 */       SortModule reqMod = (SortModule)moduleMap.get(moduleName);
/* 301 */       if (reqMod != null)
/*     */       {
/*     */ 
/*     */         
/* 305 */         if (reqMod.getPosition() >= position)
/*     */         {
/* 307 */           position = reqMod.getPosition() + 1;
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     modInfo = module.getRequiredModules();
/* 316 */     for (modPos = 0; modPos < modInfo.length; modPos++) {
/*     */       
/* 318 */       String moduleName = modInfo[modPos].getModuleClass();
/* 319 */       SortModule reqMod = (SortModule)moduleMap.get(moduleName);
/* 320 */       if (reqMod == null) {
/*     */         
/* 322 */         Log.warn("Invalid state: Required dependency of '" + moduleName + "' had an error.");
/*     */       
/*     */       }
/* 325 */       else if (reqMod.getPosition() >= position) {
/*     */         
/* 327 */         position = reqMod.getPosition() + 1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     String subSystem = module.getSubSystem();
/* 335 */     Iterator<SortModule> it = moduleMap.values().iterator();
/* 336 */     while (it.hasNext()) {
/*     */       
/* 338 */       SortModule mod = it.next();
/*     */       
/* 340 */       if (mod.getState().getModule() == module) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 345 */       Module subSysMod = mod.getState().getModule();
/*     */ 
/*     */ 
/*     */       
/* 349 */       if (subSystem.equals(subSysMod.getSubSystem())) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 359 */       if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 364 */         if (!isBaseModule(subSysMod, module))
/*     */         {
/* 366 */           if (mod.getPosition() >= position)
/*     */           {
/* 368 */             position = mod.getPosition() + 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     } 
/* 373 */     return position;
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
/*     */   private static boolean isBaseModule(Module mod, ModuleInfo mi) {
/* 386 */     ModuleInfo[] info = mod.getRequiredModules(); int i;
/* 387 */     for (i = 0; i < info.length; i++) {
/*     */       
/* 389 */       if (info[i].getModuleClass().equals(mi.getModuleClass()))
/*     */       {
/* 391 */         return true;
/*     */       }
/*     */     } 
/* 394 */     info = mod.getOptionalModules();
/* 395 */     for (i = 0; i < info.length; i++) {
/*     */       
/* 397 */       if (info[i].getModuleClass().equals(mi.getModuleClass()))
/*     */       {
/* 399 */         return true;
/*     */       }
/*     */     } 
/* 402 */     return false;
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
/*     */   private static ArrayList collectSubsystemModules(Module childMod, HashMap moduleMap) {
/* 416 */     ArrayList<String> collector = new ArrayList();
/* 417 */     ModuleInfo[] info = childMod.getRequiredModules(); int i;
/* 418 */     for (i = 0; i < info.length; i++) {
/*     */ 
/*     */       
/* 421 */       SortModule dependentModule = (SortModule)moduleMap.get(info[i].getModuleClass());
/* 422 */       if (dependentModule == null) {
/*     */ 
/*     */         
/* 425 */         Log.warn(new Log.SimpleMessage("A dependent module was not found in the list of known modules.", info[i]
/*     */               
/* 427 */               .getModuleClass()));
/*     */       }
/*     */       else {
/*     */         
/* 431 */         collector.add(dependentModule.getState().getModule().getSubSystem());
/*     */       } 
/*     */     } 
/* 434 */     info = childMod.getOptionalModules();
/* 435 */     for (i = 0; i < info.length; i++) {
/*     */ 
/*     */       
/* 438 */       Module dependentModule = (Module)moduleMap.get(info[i].getModuleClass());
/* 439 */       if (dependentModule == null) {
/*     */         
/* 441 */         Log.warn("A dependent module was not found in the list of known modules.");
/*     */       } else {
/*     */         
/* 444 */         collector.add(dependentModule.getSubSystem());
/*     */       } 
/* 446 */     }  return collector;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/modules/PackageSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */