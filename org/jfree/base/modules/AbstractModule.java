/*     */ package org.jfree.base.modules;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractModule
/*     */   extends DefaultModuleInfo
/*     */   implements Module
/*     */ {
/*     */   private ModuleInfo[] requiredModules;
/*     */   private ModuleInfo[] optionalModules;
/*     */   private String name;
/*     */   private String description;
/*     */   private String producer;
/*     */   private String subsystem;
/*     */   
/*     */   private static class ReaderHelper
/*     */   {
/*     */     private String buffer;
/*     */     private final BufferedReader reader;
/*     */     
/*     */     protected ReaderHelper(BufferedReader reader) {
/* 119 */       this.reader = reader;
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
/*     */     public boolean hasNext() throws IOException {
/* 131 */       if (this.buffer == null)
/*     */       {
/* 133 */         this.buffer = readLine();
/*     */       }
/* 135 */       return (this.buffer != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String next() {
/* 145 */       String line = this.buffer;
/* 146 */       this.buffer = null;
/* 147 */       return line;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void pushBack(String line) {
/* 158 */       this.buffer = line;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String readLine() throws IOException {
/* 169 */       String line = this.reader.readLine();
/* 170 */       while (line != null && (line.length() == 0 || line.startsWith("#")))
/*     */       {
/*     */         
/* 173 */         line = this.reader.readLine();
/*     */       }
/* 175 */       return line;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 185 */       this.reader.close();
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
/*     */   public AbstractModule() {
/* 208 */     setModuleClass(getClass().getName());
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
/*     */   protected void loadModuleInfo() throws ModuleInitializeException {
/* 220 */     InputStream in = ObjectUtilities.getResourceRelativeAsStream("module.properties", getClass());
/* 221 */     if (in == null)
/*     */     {
/* 223 */       throw new ModuleInitializeException("File 'module.properties' not found in module package.");
/*     */     }
/*     */ 
/*     */     
/* 227 */     loadModuleInfo(in);
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
/*     */   protected void loadModuleInfo(InputStream in) throws ModuleInitializeException {
/* 240 */     if (in == null)
/*     */     {
/* 242 */       throw new NullPointerException("Given InputStream is null.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 248 */       ArrayList<DefaultModuleInfo> optionalModules = new ArrayList();
/* 249 */       ArrayList<DefaultModuleInfo> dependendModules = new ArrayList();
/* 250 */       ReaderHelper rh = new ReaderHelper(new BufferedReader(new InputStreamReader(in, "ISO-8859-1")));
/*     */ 
/*     */       
/*     */       try {
/* 254 */         while (rh.hasNext())
/*     */         {
/* 256 */           String lastLineRead = rh.next();
/* 257 */           if (lastLineRead.startsWith("module-info:")) {
/*     */             
/* 259 */             readModuleInfo(rh); continue;
/*     */           } 
/* 261 */           if (lastLineRead.startsWith("depends:")) {
/*     */             
/* 263 */             dependendModules.add(readExternalModule(rh)); continue;
/*     */           } 
/* 265 */           if (lastLineRead.startsWith("optional:"))
/*     */           {
/* 267 */             optionalModules.add(readExternalModule(rh));
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 278 */         rh.close();
/*     */       } 
/*     */       
/* 281 */       this
/* 282 */         .optionalModules = optionalModules.<ModuleInfo>toArray(new ModuleInfo[optionalModules.size()]);
/*     */       
/* 284 */       this
/* 285 */         .requiredModules = dependendModules.<ModuleInfo>toArray(new ModuleInfo[dependendModules.size()]);
/*     */     }
/* 287 */     catch (IOException ioe) {
/*     */       
/* 289 */       throw new ModuleInitializeException("Failed to load properties", ioe);
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
/*     */   private String readValue(ReaderHelper reader, String firstLine) throws IOException {
/* 304 */     StringBuffer b = new StringBuffer(firstLine.trim());
/* 305 */     boolean newLine = true;
/* 306 */     while (isNextLineValueLine(reader)) {
/*     */       
/* 308 */       firstLine = reader.next();
/* 309 */       String trimedLine = firstLine.trim();
/* 310 */       if (trimedLine.length() == 0 && !newLine) {
/*     */         
/* 312 */         b.append("\n");
/* 313 */         newLine = true;
/*     */         
/*     */         continue;
/*     */       } 
/* 317 */       if (!newLine)
/*     */       {
/* 319 */         b.append(" ");
/*     */       }
/* 321 */       b.append(parseValue(trimedLine));
/* 322 */       newLine = false;
/*     */     } 
/*     */     
/* 325 */     return b.toString();
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
/*     */   private boolean isNextLineValueLine(ReaderHelper reader) throws IOException {
/* 337 */     if (!reader.hasNext())
/*     */     {
/* 339 */       return false;
/*     */     }
/* 341 */     String firstLine = reader.next();
/* 342 */     if (firstLine == null)
/*     */     {
/* 344 */       return false;
/*     */     }
/* 346 */     if (parseKey(firstLine) != null) {
/*     */       
/* 348 */       reader.pushBack(firstLine);
/* 349 */       return false;
/*     */     } 
/* 351 */     reader.pushBack(firstLine);
/* 352 */     return true;
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
/*     */   private void readModuleInfo(ReaderHelper reader) throws IOException {
/* 364 */     while (reader.hasNext()) {
/*     */       
/* 366 */       String lastLineRead = reader.next();
/*     */       
/* 368 */       if (!Character.isWhitespace(lastLineRead.charAt(0))) {
/*     */ 
/*     */         
/* 371 */         reader.pushBack(lastLineRead);
/*     */         
/*     */         return;
/*     */       } 
/* 375 */       String line = lastLineRead.trim();
/* 376 */       String key = parseKey(line);
/* 377 */       if (key != null) {
/*     */ 
/*     */         
/* 380 */         String b = readValue(reader, parseValue(line.trim()));
/*     */         
/* 382 */         if ("name".equals(key)) {
/*     */           
/* 384 */           setName(b); continue;
/*     */         } 
/* 386 */         if ("producer".equals(key)) {
/*     */           
/* 388 */           setProducer(b); continue;
/*     */         } 
/* 390 */         if ("description".equals(key)) {
/*     */           
/* 392 */           setDescription(b); continue;
/*     */         } 
/* 394 */         if ("subsystem".equals(key)) {
/*     */           
/* 396 */           setSubSystem(b); continue;
/*     */         } 
/* 398 */         if ("version.major".equals(key)) {
/*     */           
/* 400 */           setMajorVersion(b); continue;
/*     */         } 
/* 402 */         if ("version.minor".equals(key)) {
/*     */           
/* 404 */           setMinorVersion(b); continue;
/*     */         } 
/* 406 */         if ("version.patchlevel".equals(key))
/*     */         {
/* 408 */           setPatchLevel(b);
/*     */         }
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
/*     */   private String parseKey(String line) {
/* 423 */     int idx = line.indexOf(':');
/* 424 */     if (idx == -1)
/*     */     {
/* 426 */       return null;
/*     */     }
/* 428 */     return line.substring(0, idx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parseValue(String line) {
/* 439 */     int idx = line.indexOf(':');
/* 440 */     if (idx == -1)
/*     */     {
/* 442 */       return line;
/*     */     }
/* 444 */     if (idx + 1 == line.length())
/*     */     {
/* 446 */       return "";
/*     */     }
/* 448 */     return line.substring(idx + 1);
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
/*     */   private DefaultModuleInfo readExternalModule(ReaderHelper reader) throws IOException {
/* 462 */     DefaultModuleInfo mi = new DefaultModuleInfo();
/*     */     
/* 464 */     while (reader.hasNext()) {
/*     */       
/* 466 */       String lastLineRead = reader.next();
/*     */       
/* 468 */       if (!Character.isWhitespace(lastLineRead.charAt(0))) {
/*     */ 
/*     */         
/* 471 */         reader.pushBack(lastLineRead);
/* 472 */         return mi;
/*     */       } 
/*     */       
/* 475 */       String line = lastLineRead.trim();
/* 476 */       String key = parseKey(line);
/* 477 */       if (key != null) {
/*     */         
/* 479 */         String b = readValue(reader, parseValue(line));
/* 480 */         if ("module".equals(key)) {
/*     */           
/* 482 */           mi.setModuleClass(b); continue;
/*     */         } 
/* 484 */         if ("version.major".equals(key)) {
/*     */           
/* 486 */           mi.setMajorVersion(b); continue;
/*     */         } 
/* 488 */         if ("version.minor".equals(key)) {
/*     */           
/* 490 */           mi.setMinorVersion(b); continue;
/*     */         } 
/* 492 */         if ("version.patchlevel".equals(key))
/*     */         {
/* 494 */           mi.setPatchLevel(b);
/*     */         }
/*     */       } 
/*     */     } 
/* 498 */     return mi;
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
/*     */   public String getName() {
/* 510 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setName(String name) {
/* 520 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 531 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDescription(String description) {
/* 541 */     this.description = description;
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
/*     */   public String getProducer() {
/* 553 */     return this.producer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setProducer(String producer) {
/* 563 */     this.producer = producer;
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
/*     */   public ModuleInfo[] getRequiredModules() {
/* 575 */     ModuleInfo[] retval = new ModuleInfo[this.requiredModules.length];
/* 576 */     System.arraycopy(this.requiredModules, 0, retval, 0, this.requiredModules.length);
/* 577 */     return retval;
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
/*     */   public ModuleInfo[] getOptionalModules() {
/* 589 */     ModuleInfo[] retval = new ModuleInfo[this.optionalModules.length];
/* 590 */     System.arraycopy(this.optionalModules, 0, retval, 0, this.optionalModules.length);
/* 591 */     return retval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setRequiredModules(ModuleInfo[] requiredModules) {
/* 601 */     this.requiredModules = new ModuleInfo[requiredModules.length];
/* 602 */     System.arraycopy(requiredModules, 0, this.requiredModules, 0, requiredModules.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptionalModules(ModuleInfo[] optionalModules) {
/* 612 */     this.optionalModules = new ModuleInfo[optionalModules.length];
/* 613 */     System.arraycopy(optionalModules, 0, this.optionalModules, 0, optionalModules.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 624 */     StringBuffer buffer = new StringBuffer();
/* 625 */     buffer.append("Module : ");
/* 626 */     buffer.append(getName());
/* 627 */     buffer.append("\n");
/* 628 */     buffer.append("ModuleClass : ");
/* 629 */     buffer.append(getModuleClass());
/* 630 */     buffer.append("\n");
/* 631 */     buffer.append("Version: ");
/* 632 */     buffer.append(getMajorVersion());
/* 633 */     buffer.append(".");
/* 634 */     buffer.append(getMinorVersion());
/* 635 */     buffer.append(".");
/* 636 */     buffer.append(getPatchLevel());
/* 637 */     buffer.append("\n");
/* 638 */     buffer.append("Producer: ");
/* 639 */     buffer.append(getProducer());
/* 640 */     buffer.append("\n");
/* 641 */     buffer.append("Description: ");
/* 642 */     buffer.append(getDescription());
/* 643 */     buffer.append("\n");
/* 644 */     return buffer.toString();
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
/*     */   protected static boolean isClassLoadable(String name) {
/*     */     try {
/* 659 */       ClassLoader loader = ObjectUtilities.getClassLoader(AbstractModule.class);
/* 660 */       if (loader == null)
/*     */       {
/*     */         
/* 663 */         return false;
/*     */       }
/* 665 */       loader.loadClass(name);
/* 666 */       return true;
/*     */     }
/* 668 */     catch (Exception e) {
/*     */       
/* 670 */       return false;
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
/*     */   protected static boolean isClassLoadable(String name, Class context) {
/*     */     try {
/* 686 */       ObjectUtilities.getClassLoader(context).loadClass(name);
/* 687 */       return true;
/*     */     }
/* 689 */     catch (Exception e) {
/*     */       
/* 691 */       return false;
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
/*     */   public void configure(SubSystem subSystem) {
/* 704 */     InputStream in = ObjectUtilities.getResourceRelativeAsStream("configuration.properties", getClass());
/* 705 */     if (in == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 711 */       subSystem.getPackageManager().getPackageConfiguration().load(in);
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 717 */         in.close();
/*     */       }
/* 719 */       catch (IOException e) {}
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
/*     */   protected void performExternalInitialize(String classname) throws ModuleInitializeException {
/*     */     try {
/* 740 */       ModuleInitializer mi = (ModuleInitializer)ObjectUtilities.loadAndInstantiate(classname, AbstractModule.class, ModuleInitializer.class);
/* 741 */       if (mi == null)
/*     */       {
/* 743 */         throw new ModuleInitializeException("Failed to load specified initializer class.");
/*     */       }
/* 745 */       mi.performInit();
/*     */     }
/* 747 */     catch (ModuleInitializeException mie) {
/*     */       
/* 749 */       throw mie;
/*     */     }
/* 751 */     catch (Exception e) {
/*     */       
/* 753 */       throw new ModuleInitializeException("Failed to load specified initializer class.", e);
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
/*     */   protected void performExternalInitialize(String classname, Class context) throws ModuleInitializeException {
/*     */     try {
/* 770 */       ModuleInitializer mi = (ModuleInitializer)ObjectUtilities.loadAndInstantiate(classname, context, ModuleInitializer.class);
/* 771 */       if (mi == null)
/*     */       {
/* 773 */         throw new ModuleInitializeException("Failed to load specified initializer class.");
/*     */       }
/* 775 */       mi.performInit();
/*     */     }
/* 777 */     catch (ModuleInitializeException mie) {
/*     */       
/* 779 */       throw mie;
/*     */     }
/* 781 */     catch (Exception e) {
/*     */       
/* 783 */       throw new ModuleInitializeException("Failed to load specified initializer class.", e);
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
/*     */   public String getSubSystem() {
/* 795 */     if (this.subsystem == null)
/*     */     {
/* 797 */       return getName();
/*     */     }
/* 799 */     return this.subsystem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setSubSystem(String name) {
/* 809 */     this.subsystem = name;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/modules/AbstractModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */