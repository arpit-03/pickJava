/*     */ package edu.mines.jtk.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.net.URL;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
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
/*     */ public abstract class Mode
/*     */   extends AbstractAction
/*     */ {
/*     */   private ModeManager _manager;
/*     */   private boolean _active;
/*     */   private Cursor _cursor;
/*     */   
/*     */   public void setActive(boolean active) {
/*  51 */     if (isEnabled() && this._active != active) {
/*  52 */       this._manager.setActive(this, active);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  60 */     return this._active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExclusive() {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/*  79 */     setActive(!this._active);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  85 */     if (!enabled && isActive())
/*  86 */       setActive(false); 
/*  87 */     super.setEnabled(enabled);
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
/*     */   public void setName(String name) {
/*  99 */     putValue("Name", name);
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
/*     */   public void setIcon(Icon icon) {
/* 111 */     putValue("SmallIcon", icon);
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
/*     */   public void setMnemonicKey(int mk) {
/* 123 */     putValue("MnemonicKey", new Integer(mk));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAcceleratorKey(KeyStroke ak) {
/* 134 */     putValue("AcceleratorKey", ak);
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
/*     */   public void setShortDescription(String sd) {
/* 146 */     putValue("ShortDescription", sd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLongDescription(String ld) {
/* 157 */     putValue("LongDescription", ld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursor(Cursor cursor) {
/* 166 */     this._cursor = cursor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cursor getCursor() {
/* 174 */     return this._cursor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Mode(ModeManager manager) {
/* 254 */     this._active = false;
/* 255 */     this._cursor = null; manager.add(this); this._manager = manager;
/*     */   }
/*     */   protected static Icon loadIcon(Class<?> cls, String res) { URL url = cls.getResource(res); return (url != null) ? new ImageIcon(url) : null; } protected static Cursor loadCursor(Class<?> cls, String res, int x, int y) { URL url = cls.getResource(res); if (url == null) return null;  Toolkit toolkit = Toolkit.getDefaultToolkit(); Image image = toolkit.getImage(url); if (image == null)
/* 258 */       return null;  image = resizeCursorImage(image); Point point = new Point(x, y); return toolkit.createCustomCursor(image, point, res); } private static Image resizeCursorImage(Image image) { Dimension size; BufferedImage bimage; image = (new ImageIcon(image)).getImage();
/* 259 */     int w = image.getWidth(null);
/* 260 */     int h = image.getHeight(null);
/*     */     
/*     */     try {
/* 263 */       size = Toolkit.getDefaultToolkit().getBestCursorSize(w, h);
/* 264 */     } catch (HeadlessException e) {
/* 265 */       return image;
/*     */     } 
/* 267 */     if (w == size.width && h == size.height)
/* 268 */       return image; 
/* 269 */     w = size.width;
/* 270 */     h = size.height;
/* 271 */     boolean hasAlpha = hasAlpha(image);
/*     */     
/* 273 */     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */     try {
/* 275 */       int transparency = hasAlpha ? 2 : 1;
/* 276 */       GraphicsDevice gs = ge.getDefaultScreenDevice();
/* 277 */       GraphicsConfiguration gc = gs.getDefaultConfiguration();
/* 278 */       bimage = gc.createCompatibleImage(w, h, transparency);
/* 279 */     } catch (HeadlessException e) {
/* 280 */       int type = hasAlpha ? 2 : 1;
/*     */ 
/*     */       
/* 283 */       bimage = new BufferedImage(w, h, type);
/*     */     } 
/* 285 */     Graphics g = bimage.createGraphics();
/* 286 */     g.drawImage(image, 0, 0, null);
/* 287 */     g.dispose();
/* 288 */     return bimage; } protected abstract void setActive(Component paramComponent, boolean paramBoolean); void setActiveInternal(boolean active) {
/*     */     firePropertyChange("active", Boolean.valueOf(this._active), Boolean.valueOf(active));
/*     */     this._active = active;
/*     */   } private static boolean hasAlpha(Image image) {
/* 292 */     if (image instanceof BufferedImage) {
/* 293 */       BufferedImage bimage = (BufferedImage)image;
/* 294 */       return bimage.getColorModel().hasAlpha();
/*     */     } 
/* 296 */     PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
/*     */     try {
/* 298 */       pg.grabPixels();
/* 299 */     } catch (InterruptedException e) {
/* 300 */       return true;
/*     */     } 
/* 302 */     ColorModel cm = pg.getColorModel();
/* 303 */     return cm.hasAlpha();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/awt/Mode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */