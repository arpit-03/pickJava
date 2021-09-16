/*     */ package org.jfree.base.modules;
/*     */ 
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
/*     */ public class PackageState
/*     */ {
/*     */   public static final int STATE_NEW = 0;
/*     */   public static final int STATE_CONFIGURED = 1;
/*     */   public static final int STATE_INITIALIZED = 2;
/*     */   public static final int STATE_ERROR = -2;
/*     */   private final Module module;
/*     */   private int state;
/*     */   
/*     */   public PackageState(Module module) {
/*  81 */     this(module, 0);
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
/*     */   public PackageState(Module module, int state) {
/*  93 */     if (module == null)
/*     */     {
/*  95 */       throw new NullPointerException("Module must not be null.");
/*     */     }
/*  97 */     if (state != 1 && state != -2 && state != 2 && state != 0)
/*     */     {
/*     */       
/* 100 */       throw new IllegalArgumentException("State is not valid");
/*     */     }
/* 102 */     this.module = module;
/* 103 */     this.state = state;
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
/*     */   public boolean configure(SubSystem subSystem) {
/* 116 */     if (this.state == 0) {
/*     */       
/*     */       try {
/*     */         
/* 120 */         this.module.configure(subSystem);
/* 121 */         this.state = 1;
/* 122 */         return true;
/*     */       }
/* 124 */       catch (NoClassDefFoundError noClassDef) {
/*     */         
/* 126 */         Log.warn(new Log.SimpleMessage("Unable to load module classes for ", this.module
/* 127 */               .getName(), ":", noClassDef.getMessage()));
/* 128 */         this.state = -2;
/*     */       }
/* 130 */       catch (Exception e) {
/*     */         
/* 132 */         if (Log.isDebugEnabled()) {
/*     */ 
/*     */           
/* 135 */           Log.warn("Unable to configure the module " + this.module.getName(), e);
/*     */         }
/* 137 */         else if (Log.isWarningEnabled()) {
/*     */           
/* 139 */           Log.warn("Unable to configure the module " + this.module.getName());
/*     */         } 
/* 141 */         this.state = -2;
/*     */       } 
/*     */     }
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Module getModule() {
/* 154 */     return this.module;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 165 */     return this.state;
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
/*     */   public boolean initialize(SubSystem subSystem) {
/* 180 */     if (this.state == 1) {
/*     */       
/*     */       try {
/*     */         
/* 184 */         this.module.initialize(subSystem);
/* 185 */         this.state = 2;
/* 186 */         return true;
/*     */       }
/* 188 */       catch (NoClassDefFoundError noClassDef) {
/*     */         
/* 190 */         Log.warn(new Log.SimpleMessage("Unable to load module classes for ", this.module
/* 191 */               .getName(), ":", noClassDef.getMessage()));
/* 192 */         this.state = -2;
/*     */       }
/* 194 */       catch (ModuleInitializeException me) {
/*     */         
/* 196 */         if (Log.isDebugEnabled()) {
/*     */ 
/*     */           
/* 199 */           Log.warn("Unable to initialize the module " + this.module.getName(), (Exception)me);
/*     */         }
/* 201 */         else if (Log.isWarningEnabled()) {
/*     */           
/* 203 */           Log.warn("Unable to initialize the module " + this.module.getName());
/*     */         } 
/* 205 */         this.state = -2;
/*     */       }
/* 207 */       catch (Exception e) {
/*     */         
/* 209 */         if (Log.isDebugEnabled()) {
/*     */ 
/*     */           
/* 212 */           Log.warn("Unable to initialize the module " + this.module.getName(), e);
/*     */         }
/* 214 */         else if (Log.isWarningEnabled()) {
/*     */           
/* 216 */           Log.warn("Unable to initialize the module " + this.module.getName());
/*     */         } 
/* 218 */         this.state = -2;
/*     */       } 
/*     */     }
/* 221 */     return false;
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
/*     */   public boolean equals(Object o) {
/* 234 */     if (this == o)
/*     */     {
/* 236 */       return true;
/*     */     }
/* 238 */     if (!(o instanceof PackageState))
/*     */     {
/* 240 */       return false;
/*     */     }
/*     */     
/* 243 */     PackageState packageState = (PackageState)o;
/*     */     
/* 245 */     if (!this.module.getModuleClass().equals(packageState.module.getModuleClass()))
/*     */     {
/* 247 */       return false;
/*     */     }
/*     */     
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 261 */     return this.module.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/modules/PackageState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */