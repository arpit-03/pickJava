/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceBundleSupport
/*     */ {
/*     */   private ResourceBundle resources;
/*     */   private TreeMap cache;
/*     */   private TreeSet lookupPath;
/*     */   private String resourceBase;
/*     */   private Locale locale;
/*     */   
/*     */   public ResourceBundleSupport(Locale locale, String baseName) {
/* 119 */     this(locale, ResourceBundleWrapper.getBundle(baseName, locale), baseName);
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
/*     */   protected ResourceBundleSupport(Locale locale, ResourceBundle resourceBundle, String baseName) {
/* 135 */     if (locale == null)
/*     */     {
/* 137 */       throw new NullPointerException("Locale must not be null");
/*     */     }
/* 139 */     if (resourceBundle == null)
/*     */     {
/* 141 */       throw new NullPointerException("Resources must not be null");
/*     */     }
/* 143 */     if (baseName == null)
/*     */     {
/* 145 */       throw new NullPointerException("BaseName must not be null");
/*     */     }
/* 147 */     this.locale = locale;
/* 148 */     this.resources = resourceBundle;
/* 149 */     this.resourceBase = baseName;
/* 150 */     this.cache = new TreeMap<Object, Object>();
/* 151 */     this.lookupPath = new TreeSet();
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
/*     */   public ResourceBundleSupport(Locale locale, ResourceBundle resourceBundle) {
/* 164 */     this(locale, resourceBundle, resourceBundle.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundleSupport(String baseName) {
/* 175 */     this(Locale.getDefault(), ResourceBundleWrapper.getBundle(baseName), baseName);
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
/*     */   protected ResourceBundleSupport(ResourceBundle resourceBundle, String baseName) {
/* 189 */     this(Locale.getDefault(), resourceBundle, baseName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundleSupport(ResourceBundle resourceBundle) {
/* 199 */     this(Locale.getDefault(), resourceBundle, resourceBundle.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String getResourceBase() {
/* 209 */     return this.resourceBase;
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
/*     */   public synchronized String getString(String key) {
/* 227 */     String retval = (String)this.cache.get(key);
/* 228 */     if (retval != null)
/*     */     {
/* 230 */       return retval;
/*     */     }
/* 232 */     this.lookupPath.clear();
/* 233 */     return internalGetString(key);
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
/*     */   protected String internalGetString(String key) {
/* 245 */     if (this.lookupPath.contains(key))
/*     */     {
/* 247 */       throw new MissingResourceException("InfiniteLoop in resource lookup", 
/*     */           
/* 249 */           getResourceBase(), this.lookupPath.toString());
/*     */     }
/* 251 */     String fromResBundle = this.resources.getString(key);
/* 252 */     if (fromResBundle.startsWith("@@")) {
/*     */ 
/*     */       
/* 255 */       int idx = fromResBundle.indexOf('@', 2);
/* 256 */       if (idx == -1)
/*     */       {
/* 258 */         throw new MissingResourceException("Invalid format for global lookup key.", 
/* 259 */             getResourceBase(), key);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 264 */         ResourceBundle res = ResourceBundleWrapper.getBundle(fromResBundle.substring(2, idx));
/* 265 */         return res.getString(fromResBundle.substring(idx + 1));
/*     */       }
/* 267 */       catch (Exception e) {
/*     */         
/* 269 */         Log.error("Error during global lookup", e);
/* 270 */         throw new MissingResourceException("Error during global lookup", 
/* 271 */             getResourceBase(), key);
/*     */       } 
/*     */     } 
/* 274 */     if (fromResBundle.startsWith("@")) {
/*     */ 
/*     */       
/* 277 */       String newKey = fromResBundle.substring(1);
/* 278 */       this.lookupPath.add(key);
/* 279 */       String retval = internalGetString(newKey);
/*     */       
/* 281 */       this.cache.put(key, retval);
/* 282 */       return retval;
/*     */     } 
/*     */ 
/*     */     
/* 286 */     this.cache.put(key, fromResBundle);
/* 287 */     return fromResBundle;
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
/*     */   public Icon getIcon(String key, boolean large) {
/* 301 */     String name = getString(key);
/* 302 */     return createIcon(name, true, large);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon(String key) {
/* 313 */     String name = getString(key);
/* 314 */     return createIcon(name, false, false);
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
/*     */   public Integer getMnemonic(String key) {
/* 334 */     String name = getString(key);
/* 335 */     return createMnemonic(name);
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
/*     */   public Integer getOptionalMnemonic(String key) {
/* 347 */     String name = getString(key);
/* 348 */     if (name != null && name.length() > 0)
/*     */     {
/* 350 */       return createMnemonic(name);
/*     */     }
/* 352 */     return null;
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
/*     */   public KeyStroke getKeyStroke(String key) {
/* 377 */     return getKeyStroke(key, getMenuKeyMask());
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
/*     */   public KeyStroke getOptionalKeyStroke(String key) {
/* 389 */     return getOptionalKeyStroke(key, getMenuKeyMask());
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
/*     */   public KeyStroke getKeyStroke(String key, int mask) {
/* 416 */     String name = getString(key);
/* 417 */     return KeyStroke.getKeyStroke(createMnemonic(name).intValue(), mask);
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
/*     */   public KeyStroke getOptionalKeyStroke(String key, int mask) {
/* 430 */     String name = getString(key);
/*     */     
/* 432 */     if (name != null && name.length() > 0)
/*     */     {
/* 434 */       return KeyStroke.getKeyStroke(createMnemonic(name).intValue(), mask);
/*     */     }
/* 436 */     return null;
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
/*     */   public JMenu createMenu(String keyPrefix) {
/* 460 */     JMenu retval = new JMenu();
/* 461 */     retval.setText(getString(keyPrefix + ".name"));
/* 462 */     retval.setMnemonic(getMnemonic(keyPrefix + ".mnemonic").intValue());
/* 463 */     return retval;
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
/*     */   public URL getResourceURL(String key) {
/* 482 */     String name = getString(key);
/* 483 */     URL in = ObjectUtilities.getResource(name, ResourceBundleSupport.class);
/* 484 */     if (in == null)
/*     */     {
/* 486 */       Log.warn("Unable to find file in the class path: " + name + "; key=" + key);
/*     */     }
/* 488 */     return in;
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
/*     */   private ImageIcon createIcon(String resourceName, boolean scale, boolean large) {
/* 506 */     URL in = ObjectUtilities.getResource(resourceName, ResourceBundleSupport.class);
/*     */     
/* 508 */     if (in == null) {
/*     */       
/* 510 */       Log.warn("Unable to find file in the class path: " + resourceName);
/* 511 */       return new ImageIcon(createTransparentImage(1, 1));
/*     */     } 
/* 513 */     Image img = Toolkit.getDefaultToolkit().createImage(in);
/* 514 */     if (img == null) {
/*     */       
/* 516 */       Log.warn("Unable to instantiate the image: " + resourceName);
/* 517 */       return new ImageIcon(createTransparentImage(1, 1));
/*     */     } 
/* 519 */     if (scale) {
/*     */       
/* 521 */       if (large)
/*     */       {
/* 523 */         return new ImageIcon(img.getScaledInstance(24, 24, 4));
/*     */       }
/* 525 */       return new ImageIcon(img.getScaledInstance(16, 16, 4));
/*     */     } 
/* 527 */     return new ImageIcon(img);
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
/*     */   private Integer createMnemonic(String keyString) {
/* 539 */     if (keyString == null)
/*     */     {
/* 541 */       throw new NullPointerException("Key is null.");
/*     */     }
/* 543 */     if (keyString.length() == 0)
/*     */     {
/* 545 */       throw new IllegalArgumentException("Key is empty.");
/*     */     }
/* 547 */     int character = keyString.charAt(0);
/* 548 */     if (keyString.startsWith("VK_")) {
/*     */       
/*     */       try {
/*     */         
/* 552 */         Field f = KeyEvent.class.getField(keyString);
/* 553 */         Integer keyCode = (Integer)f.get(null);
/* 554 */         character = keyCode.intValue();
/*     */       }
/* 556 */       catch (Exception nsfe) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 561 */     return new Integer(character);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getMenuKeyMask() {
/*     */     try {
/* 573 */       return Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/*     */     }
/* 575 */     catch (UnsupportedOperationException he) {
/*     */ 
/*     */ 
/*     */       
/* 579 */       return 2;
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
/*     */   private BufferedImage createTransparentImage(int width, int height) {
/* 593 */     BufferedImage img = new BufferedImage(width, height, 2);
/* 594 */     int[] data = img.getRGB(0, 0, width, height, null, 0, width);
/* 595 */     Arrays.fill(data, 0);
/* 596 */     img.setRGB(0, 0, width, height, data, 0, width);
/* 597 */     return img;
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
/*     */   public Icon createTransparentIcon(int width, int height) {
/* 610 */     return new ImageIcon(createTransparentImage(width, height));
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
/*     */   public String formatMessage(String key, Object parameter) {
/* 623 */     return formatMessage(key, new Object[] { parameter });
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
/*     */   public String formatMessage(String key, Object par1, Object par2) {
/* 639 */     return formatMessage(key, new Object[] { par1, par2 });
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
/*     */   public String formatMessage(String key, Object[] parameters) {
/* 652 */     MessageFormat format = new MessageFormat(getString(key));
/* 653 */     format.setLocale(getLocale());
/* 654 */     return format.format(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 664 */     return this.locale;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ResourceBundleSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */