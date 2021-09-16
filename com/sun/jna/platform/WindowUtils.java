/*      */ package com.sun.jna.platform;
/*      */ 
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.NativeLong;
/*      */ import com.sun.jna.Platform;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.platform.unix.X11;
/*      */ import com.sun.jna.platform.win32.GDI32;
/*      */ import com.sun.jna.platform.win32.Kernel32;
/*      */ import com.sun.jna.platform.win32.Psapi;
/*      */ import com.sun.jna.platform.win32.User32;
/*      */ import com.sun.jna.platform.win32.Win32Exception;
/*      */ import com.sun.jna.platform.win32.WinDef;
/*      */ import com.sun.jna.platform.win32.WinGDI;
/*      */ import com.sun.jna.platform.win32.WinNT;
/*      */ import com.sun.jna.platform.win32.WinUser;
/*      */ import com.sun.jna.ptr.ByteByReference;
/*      */ import com.sun.jna.ptr.IntByReference;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.AWTEventListener;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.HierarchyEvent;
/*      */ import java.awt.event.HierarchyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.geom.Area;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.Raster;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JLayeredPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import javax.swing.SwingUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WindowUtils
/*      */ {
/*      */   private static final String TRANSPARENT_OLD_BG = "transparent-old-bg";
/*      */   private static final String TRANSPARENT_OLD_OPAQUE = "transparent-old-opaque";
/*      */   private static final String TRANSPARENT_ALPHA = "transparent-alpha";
/*  167 */   public static final Shape MASK_NONE = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class HeavyweightForcer
/*      */     extends Window
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean packed;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HeavyweightForcer(Window parent) {
/*  189 */       super(parent);
/*  190 */       pack();
/*  191 */       this.packed = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isVisible() {
/*  199 */       return this.packed;
/*      */     }
/*      */ 
/*      */     
/*      */     public Rectangle getBounds() {
/*  204 */       return getOwner().getBounds();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected static class RepaintTrigger
/*      */     extends JComponent
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */     
/*      */     protected class Listener
/*      */       extends WindowAdapter
/*      */       implements ComponentListener, HierarchyListener, AWTEventListener
/*      */     {
/*      */       public void windowOpened(WindowEvent e) {
/*  220 */         WindowUtils.RepaintTrigger.this.repaint();
/*      */       }
/*      */ 
/*      */       
/*      */       public void componentHidden(ComponentEvent e) {}
/*      */ 
/*      */       
/*      */       public void componentMoved(ComponentEvent e) {}
/*      */ 
/*      */       
/*      */       public void componentResized(ComponentEvent e) {
/*  231 */         WindowUtils.RepaintTrigger.this.setSize(WindowUtils.RepaintTrigger.this.getParent().getSize());
/*  232 */         WindowUtils.RepaintTrigger.this.repaint();
/*      */       }
/*      */ 
/*      */       
/*      */       public void componentShown(ComponentEvent e) {
/*  237 */         WindowUtils.RepaintTrigger.this.repaint();
/*      */       }
/*      */ 
/*      */       
/*      */       public void hierarchyChanged(HierarchyEvent e) {
/*  242 */         WindowUtils.RepaintTrigger.this.repaint();
/*      */       }
/*      */ 
/*      */       
/*      */       public void eventDispatched(AWTEvent e) {
/*  247 */         if (e instanceof MouseEvent) {
/*  248 */           Component src = ((MouseEvent)e).getComponent();
/*  249 */           if (src != null && 
/*  250 */             SwingUtilities.isDescendingFrom(src, WindowUtils.RepaintTrigger.this.content)) {
/*  251 */             MouseEvent me = SwingUtilities.convertMouseEvent(src, (MouseEvent)e, WindowUtils.RepaintTrigger.this.content);
/*  252 */             Component c = SwingUtilities.getDeepestComponentAt(WindowUtils.RepaintTrigger.this.content, me.getX(), me.getY());
/*  253 */             if (c != null) {
/*  254 */               WindowUtils.RepaintTrigger.this.setCursor(c.getCursor());
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*  261 */     private final Listener listener = createListener(); private final JComponent content;
/*      */     private Rectangle dirty;
/*      */     
/*      */     public RepaintTrigger(JComponent content) {
/*  265 */       this.content = content;
/*      */     }
/*      */ 
/*      */     
/*      */     public void addNotify() {
/*  270 */       super.addNotify();
/*  271 */       Window w = SwingUtilities.getWindowAncestor(this);
/*  272 */       setSize(getParent().getSize());
/*  273 */       w.addComponentListener(this.listener);
/*  274 */       w.addWindowListener(this.listener);
/*  275 */       Toolkit.getDefaultToolkit().addAWTEventListener(this.listener, 48L);
/*      */     }
/*      */ 
/*      */     
/*      */     public void removeNotify() {
/*  280 */       Toolkit.getDefaultToolkit().removeAWTEventListener(this.listener);
/*  281 */       Window w = SwingUtilities.getWindowAncestor(this);
/*  282 */       w.removeComponentListener(this.listener);
/*  283 */       w.removeWindowListener(this.listener);
/*  284 */       super.removeNotify();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void paintComponent(Graphics g) {
/*  290 */       Rectangle bounds = g.getClipBounds();
/*  291 */       if (this.dirty == null || !this.dirty.contains(bounds)) {
/*  292 */         if (this.dirty == null) {
/*  293 */           this.dirty = bounds;
/*      */         } else {
/*      */           
/*  296 */           this.dirty = this.dirty.union(bounds);
/*      */         } 
/*  298 */         this.content.repaint(this.dirty);
/*      */       } else {
/*      */         
/*  301 */         this.dirty = null;
/*      */       } 
/*      */     }
/*      */     
/*      */     protected Listener createListener() {
/*  306 */       return new Listener();
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract class NativeWindowUtils {
/*      */     protected abstract class TransparentContentPane
/*      */       extends JPanel implements AWTEventListener {
/*      */       private static final long serialVersionUID = 1L;
/*      */       private boolean transparent;
/*      */       
/*      */       public TransparentContentPane(Container oldContent) {
/*  317 */         super(new BorderLayout());
/*  318 */         add(oldContent, "Center");
/*  319 */         setTransparent(true);
/*  320 */         if (oldContent instanceof JPanel) {
/*  321 */           ((JComponent)oldContent).setOpaque(false);
/*      */         }
/*      */       }
/*      */       
/*      */       public void addNotify() {
/*  326 */         super.addNotify();
/*  327 */         Toolkit.getDefaultToolkit().addAWTEventListener(this, 2L);
/*      */       }
/*      */       
/*      */       public void removeNotify() {
/*  331 */         Toolkit.getDefaultToolkit().removeAWTEventListener(this);
/*  332 */         super.removeNotify();
/*      */       }
/*      */       public void setTransparent(boolean transparent) {
/*  335 */         this.transparent = transparent;
/*  336 */         setOpaque(!transparent);
/*  337 */         setDoubleBuffered(!transparent);
/*  338 */         repaint();
/*      */       }
/*      */       
/*      */       public void eventDispatched(AWTEvent e) {
/*  342 */         if (e.getID() == 300 && 
/*  343 */           SwingUtilities.isDescendingFrom(((ContainerEvent)e).getChild(), this)) {
/*  344 */           Component child = ((ContainerEvent)e).getChild();
/*  345 */           WindowUtils.NativeWindowUtils.this.setDoubleBuffered(child, false);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void paint(Graphics gr) {
/*  350 */         if (this.transparent) {
/*  351 */           Rectangle r = gr.getClipBounds();
/*  352 */           int w = r.width;
/*  353 */           int h = r.height;
/*  354 */           if (getWidth() > 0 && getHeight() > 0) {
/*  355 */             BufferedImage buf = new BufferedImage(w, h, 3);
/*      */ 
/*      */             
/*  358 */             Graphics2D g = buf.createGraphics();
/*  359 */             g.setComposite(AlphaComposite.Clear);
/*  360 */             g.fillRect(0, 0, w, h);
/*  361 */             g.dispose();
/*      */             
/*  363 */             g = buf.createGraphics();
/*  364 */             g.translate(-r.x, -r.y);
/*  365 */             super.paint(g);
/*  366 */             g.dispose();
/*      */             
/*  368 */             paintDirect(buf, r);
/*      */           } 
/*      */         } else {
/*      */           
/*  372 */           super.paint(gr);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       protected abstract void paintDirect(BufferedImage param2BufferedImage, Rectangle param2Rectangle);
/*      */     }
/*      */ 
/*      */     
/*      */     protected Window getWindow(Component c) {
/*  382 */       return (c instanceof Window) ? (Window)c : 
/*  383 */         SwingUtilities.getWindowAncestor(c);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void whenDisplayable(Component w, final Runnable action) {
/*  390 */       if (w.isDisplayable() && (!WindowUtils.Holder.requiresVisible || w.isVisible())) {
/*  391 */         action.run();
/*      */       }
/*  393 */       else if (WindowUtils.Holder.requiresVisible) {
/*  394 */         getWindow(w).addWindowListener(new WindowAdapter()
/*      */             {
/*      */               public void windowOpened(WindowEvent e) {
/*  397 */                 e.getWindow().removeWindowListener(this);
/*  398 */                 action.run();
/*      */               }
/*      */               
/*      */               public void windowClosed(WindowEvent e) {
/*  402 */                 e.getWindow().removeWindowListener(this);
/*      */               }
/*      */             });
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  409 */         w.addHierarchyListener(new HierarchyListener()
/*      */             {
/*      */               public void hierarchyChanged(HierarchyEvent e) {
/*  412 */                 if ((e.getChangeFlags() & 0x2L) != 0L && e
/*  413 */                   .getComponent().isDisplayable()) {
/*  414 */                   e.getComponent().removeHierarchyListener(this);
/*  415 */                   action.run();
/*      */                 } 
/*      */               }
/*      */             });
/*      */       } 
/*      */     }
/*      */     
/*      */     protected Raster toRaster(Shape mask) {
/*  423 */       Raster raster = null;
/*  424 */       if (mask != WindowUtils.MASK_NONE) {
/*  425 */         Rectangle bounds = mask.getBounds();
/*  426 */         if (bounds.width > 0 && bounds.height > 0) {
/*  427 */           BufferedImage clip = new BufferedImage(bounds.x + bounds.width, bounds.y + bounds.height, 12);
/*      */ 
/*      */ 
/*      */           
/*  431 */           Graphics2D g = clip.createGraphics();
/*  432 */           g.setColor(Color.black);
/*  433 */           g.fillRect(0, 0, bounds.x + bounds.width, bounds.y + bounds.height);
/*  434 */           g.setColor(Color.white);
/*  435 */           g.fill(mask);
/*  436 */           raster = clip.getRaster();
/*      */         } 
/*      */       } 
/*  439 */       return raster;
/*      */     }
/*      */     
/*      */     protected Raster toRaster(Component c, Icon mask) {
/*  443 */       Raster raster = null;
/*  444 */       if (mask != null) {
/*      */         
/*  446 */         Rectangle bounds = new Rectangle(0, 0, mask.getIconWidth(), mask.getIconHeight());
/*  447 */         BufferedImage clip = new BufferedImage(bounds.width, bounds.height, 2);
/*      */ 
/*      */         
/*  450 */         Graphics2D g = clip.createGraphics();
/*  451 */         g.setComposite(AlphaComposite.Clear);
/*  452 */         g.fillRect(0, 0, bounds.width, bounds.height);
/*  453 */         g.setComposite(AlphaComposite.SrcOver);
/*  454 */         mask.paintIcon(c, g, 0, 0);
/*  455 */         raster = clip.getAlphaRaster();
/*      */       } 
/*  457 */       return raster;
/*      */     }
/*      */     
/*      */     protected Shape toShape(Raster raster) {
/*  461 */       final Area area = new Area(new Rectangle(0, 0, 0, 0));
/*  462 */       RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput()
/*      */           {
/*      */             public boolean outputRange(int x, int y, int w, int h) {
/*  465 */               area.add(new Area(new Rectangle(x, y, w, h)));
/*  466 */               return true;
/*      */             }
/*      */           });
/*  469 */       return area;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowAlpha(Window w, float alpha) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isWindowAlphaSupported() {
/*  482 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
/*  488 */       GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  489 */       GraphicsDevice dev = env.getDefaultScreenDevice();
/*  490 */       return dev.getDefaultConfiguration();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowTransparent(Window w, boolean transparent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setDoubleBuffered(Component root, boolean buffered) {
/*  503 */       if (root instanceof JComponent) {
/*  504 */         ((JComponent)root).setDoubleBuffered(buffered);
/*      */       }
/*  506 */       if (root instanceof JRootPane && buffered) {
/*  507 */         ((JRootPane)root).setDoubleBuffered(true);
/*      */       }
/*  509 */       else if (root instanceof Container) {
/*  510 */         Component[] kids = ((Container)root).getComponents();
/*  511 */         for (int i = 0; i < kids.length; i++) {
/*  512 */           setDoubleBuffered(kids[i], buffered);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void setLayersTransparent(Window w, boolean transparent) {
/*  519 */       Color bg = transparent ? new Color(0, 0, 0, 0) : null;
/*  520 */       if (w instanceof RootPaneContainer) {
/*  521 */         RootPaneContainer rpc = (RootPaneContainer)w;
/*  522 */         JRootPane root = rpc.getRootPane();
/*  523 */         JLayeredPane lp = root.getLayeredPane();
/*  524 */         Container c = root.getContentPane();
/*  525 */         JComponent content = (c instanceof JComponent) ? (JComponent)c : null;
/*      */         
/*  527 */         if (transparent) {
/*  528 */           lp.putClientProperty("transparent-old-opaque", Boolean.valueOf(lp.isOpaque()));
/*  529 */           lp.setOpaque(false);
/*  530 */           root.putClientProperty("transparent-old-opaque", Boolean.valueOf(root.isOpaque()));
/*  531 */           root.setOpaque(false);
/*  532 */           if (content != null) {
/*  533 */             content.putClientProperty("transparent-old-opaque", Boolean.valueOf(content.isOpaque()));
/*  534 */             content.setOpaque(false);
/*      */           } 
/*  536 */           root.putClientProperty("transparent-old-bg", root
/*  537 */               .getParent().getBackground());
/*      */         } else {
/*      */           
/*  540 */           lp.setOpaque(Boolean.TRUE.equals(lp.getClientProperty("transparent-old-opaque")));
/*  541 */           lp.putClientProperty("transparent-old-opaque", (Object)null);
/*  542 */           root.setOpaque(Boolean.TRUE.equals(root.getClientProperty("transparent-old-opaque")));
/*  543 */           root.putClientProperty("transparent-old-opaque", (Object)null);
/*  544 */           if (content != null) {
/*  545 */             content.setOpaque(Boolean.TRUE.equals(content.getClientProperty("transparent-old-opaque")));
/*  546 */             content.putClientProperty("transparent-old-opaque", (Object)null);
/*      */           } 
/*  548 */           bg = (Color)root.getClientProperty("transparent-old-bg");
/*  549 */           root.putClientProperty("transparent-old-bg", (Object)null);
/*      */         } 
/*      */       } 
/*  552 */       w.setBackground(bg);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setMask(Component c, Raster raster) {
/*  559 */       throw new UnsupportedOperationException("Window masking is not available");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setWindowMask(Component w, Raster raster) {
/*  568 */       if (w.isLightweight())
/*  569 */         throw new IllegalArgumentException("Component must be heavyweight: " + w); 
/*  570 */       setMask(w, raster);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setWindowMask(Component w, Shape mask) {
/*  575 */       setWindowMask(w, toRaster(mask));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowMask(Component w, Icon mask) {
/*  583 */       setWindowMask(w, toRaster(w, mask));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setForceHeavyweightPopups(Window w, boolean force) {
/*  592 */       if (!(w instanceof WindowUtils.HeavyweightForcer)) {
/*  593 */         Window[] owned = w.getOwnedWindows();
/*  594 */         for (int i = 0; i < owned.length; i++) {
/*  595 */           if (owned[i] instanceof WindowUtils.HeavyweightForcer) {
/*  596 */             if (force)
/*      */               return; 
/*  598 */             owned[i].dispose();
/*      */           } 
/*      */         } 
/*  601 */         Boolean b = Boolean.valueOf(System.getProperty("jna.force_hw_popups", "true"));
/*  602 */         if (force && b.booleanValue()) {
/*  603 */           new WindowUtils.HeavyweightForcer(w);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected BufferedImage getWindowIcon(WinDef.HWND hwnd) {
/*  622 */       throw new UnsupportedOperationException("This platform is not supported, yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Dimension getIconSize(WinDef.HICON hIcon) {
/*  638 */       throw new UnsupportedOperationException("This platform is not supported, yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<DesktopWindow> getAllWindows(boolean onlyVisibleWindows) {
/*  659 */       throw new UnsupportedOperationException("This platform is not supported, yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected String getWindowTitle(WinDef.HWND hwnd) {
/*  675 */       throw new UnsupportedOperationException("This platform is not supported, yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected String getProcessFilePath(WinDef.HWND hwnd) {
/*  692 */       throw new UnsupportedOperationException("This platform is not supported, yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Rectangle getWindowLocationAndSize(WinDef.HWND hwnd) {
/*  707 */       throw new UnsupportedOperationException("This platform is not supported, yet.");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Holder
/*      */   {
/*      */     public static boolean requiresVisible;
/*      */     
/*      */     public static final WindowUtils.NativeWindowUtils INSTANCE;
/*      */ 
/*      */     
/*      */     static {
/*  720 */       if (Platform.isWindows()) {
/*  721 */         INSTANCE = new WindowUtils.W32WindowUtils();
/*      */       }
/*  723 */       else if (Platform.isMac()) {
/*  724 */         INSTANCE = new WindowUtils.MacWindowUtils();
/*      */       }
/*  726 */       else if (Platform.isX11()) {
/*  727 */         INSTANCE = new WindowUtils.X11WindowUtils();
/*      */         
/*  729 */         requiresVisible = System.getProperty("java.version").matches("^1\\.4\\..*");
/*      */       } else {
/*      */         
/*  732 */         String os = System.getProperty("os.name");
/*  733 */         throw new UnsupportedOperationException("No support for " + os);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static NativeWindowUtils getInstance() {
/*  739 */     return Holder.INSTANCE;
/*      */   }
/*      */   private static class W32WindowUtils extends NativeWindowUtils { private W32WindowUtils() {}
/*      */     
/*      */     private WinDef.HWND getHWnd(Component w) {
/*  744 */       WinDef.HWND hwnd = new WinDef.HWND();
/*  745 */       hwnd.setPointer(Native.getComponentPointer(w));
/*  746 */       return hwnd;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isWindowAlphaSupported() {
/*  755 */       return Boolean.getBoolean("sun.java2d.noddraw");
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean usingUpdateLayeredWindow(Window w) {
/*  760 */       if (w instanceof RootPaneContainer) {
/*  761 */         JRootPane root = ((RootPaneContainer)w).getRootPane();
/*  762 */         return (root.getClientProperty("transparent-old-bg") != null);
/*      */       } 
/*  764 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void storeAlpha(Window w, byte alpha) {
/*  771 */       if (w instanceof RootPaneContainer) {
/*  772 */         JRootPane root = ((RootPaneContainer)w).getRootPane();
/*  773 */         Byte b = (alpha == -1) ? null : Byte.valueOf(alpha);
/*  774 */         root.putClientProperty("transparent-alpha", b);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private byte getAlpha(Window w) {
/*  780 */       if (w instanceof RootPaneContainer) {
/*  781 */         JRootPane root = ((RootPaneContainer)w).getRootPane();
/*  782 */         Byte b = (Byte)root.getClientProperty("transparent-alpha");
/*  783 */         if (b != null) {
/*  784 */           return b.byteValue();
/*      */         }
/*      */       } 
/*  787 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setWindowAlpha(final Window w, final float alpha) {
/*  792 */       if (!isWindowAlphaSupported()) {
/*  793 */         throw new UnsupportedOperationException("Set sun.java2d.noddraw=true to enable transparent windows");
/*      */       }
/*  795 */       whenDisplayable(w, new Runnable()
/*      */           {
/*      */             public void run() {
/*  798 */               WinDef.HWND hWnd = WindowUtils.W32WindowUtils.this.getHWnd(w);
/*  799 */               User32 user = User32.INSTANCE;
/*  800 */               int flags = user.GetWindowLong(hWnd, -20);
/*  801 */               byte level = (byte)((int)(255.0F * alpha) & 0xFF);
/*  802 */               if (WindowUtils.W32WindowUtils.this.usingUpdateLayeredWindow(w)) {
/*      */ 
/*      */                 
/*  805 */                 WinUser.BLENDFUNCTION blend = new WinUser.BLENDFUNCTION();
/*  806 */                 blend.SourceConstantAlpha = level;
/*  807 */                 blend.AlphaFormat = 1;
/*  808 */                 user.UpdateLayeredWindow(hWnd, null, null, null, null, null, 0, blend, 2);
/*      */ 
/*      */               
/*      */               }
/*  812 */               else if (alpha == 1.0F) {
/*  813 */                 flags &= 0xFFF7FFFF;
/*  814 */                 user.SetWindowLong(hWnd, -20, flags);
/*      */               } else {
/*      */                 
/*  817 */                 flags |= 0x80000;
/*  818 */                 user.SetWindowLong(hWnd, -20, flags);
/*  819 */                 user.SetLayeredWindowAttributes(hWnd, 0, level, 2);
/*      */               } 
/*      */               
/*  822 */               WindowUtils.W32WindowUtils.this.setForceHeavyweightPopups(w, (alpha != 1.0F));
/*  823 */               WindowUtils.W32WindowUtils.this.storeAlpha(w, level);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     private class W32TransparentContentPane
/*      */       extends WindowUtils.NativeWindowUtils.TransparentContentPane
/*      */     {
/*      */       private static final long serialVersionUID = 1L;
/*      */       private WinDef.HDC memDC;
/*      */       private WinDef.HBITMAP hBitmap;
/*      */       private Pointer pbits;
/*      */       private Dimension bitmapSize;
/*      */       
/*      */       public W32TransparentContentPane(Container content) {
/*  839 */         super(content);
/*      */       }
/*      */       private void disposeBackingStore() {
/*  842 */         GDI32 gdi = GDI32.INSTANCE;
/*  843 */         if (this.hBitmap != null) {
/*  844 */           gdi.DeleteObject((WinNT.HANDLE)this.hBitmap);
/*  845 */           this.hBitmap = null;
/*      */         } 
/*  847 */         if (this.memDC != null) {
/*  848 */           gdi.DeleteDC(this.memDC);
/*  849 */           this.memDC = null;
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removeNotify() {
/*  854 */         super.removeNotify();
/*  855 */         disposeBackingStore();
/*      */       }
/*      */       
/*      */       public void setTransparent(boolean transparent) {
/*  859 */         super.setTransparent(transparent);
/*  860 */         if (!transparent) {
/*  861 */           disposeBackingStore();
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       protected void paintDirect(BufferedImage buf, Rectangle bounds) {
/*  867 */         Window win = SwingUtilities.getWindowAncestor(this);
/*  868 */         GDI32 gdi = GDI32.INSTANCE;
/*  869 */         User32 user = User32.INSTANCE;
/*  870 */         int x = bounds.x;
/*  871 */         int y = bounds.y;
/*  872 */         Point origin = SwingUtilities.convertPoint(this, x, y, win);
/*  873 */         int w = bounds.width;
/*  874 */         int h = bounds.height;
/*  875 */         int ww = win.getWidth();
/*  876 */         int wh = win.getHeight();
/*  877 */         WinDef.HDC screenDC = user.GetDC(null);
/*  878 */         WinNT.HANDLE oldBitmap = null;
/*      */         try {
/*  880 */           if (this.memDC == null) {
/*  881 */             this.memDC = gdi.CreateCompatibleDC(screenDC);
/*      */           }
/*  883 */           if (this.hBitmap == null || !win.getSize().equals(this.bitmapSize)) {
/*  884 */             if (this.hBitmap != null) {
/*  885 */               gdi.DeleteObject((WinNT.HANDLE)this.hBitmap);
/*  886 */               this.hBitmap = null;
/*      */             } 
/*  888 */             WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
/*  889 */             bmi.bmiHeader.biWidth = ww;
/*  890 */             bmi.bmiHeader.biHeight = wh;
/*  891 */             bmi.bmiHeader.biPlanes = 1;
/*  892 */             bmi.bmiHeader.biBitCount = 32;
/*  893 */             bmi.bmiHeader.biCompression = 0;
/*  894 */             bmi.bmiHeader.biSizeImage = ww * wh * 4;
/*  895 */             PointerByReference ppbits = new PointerByReference();
/*  896 */             this.hBitmap = gdi.CreateDIBSection(this.memDC, bmi, 0, ppbits, null, 0);
/*      */ 
/*      */             
/*  899 */             this.pbits = ppbits.getValue();
/*  900 */             this.bitmapSize = new Dimension(ww, wh);
/*      */           } 
/*  902 */           oldBitmap = gdi.SelectObject(this.memDC, (WinNT.HANDLE)this.hBitmap);
/*  903 */           Raster raster = buf.getData();
/*  904 */           int[] pixel = new int[4];
/*  905 */           int[] bits = new int[w];
/*  906 */           for (int row = 0; row < h; row++) {
/*  907 */             for (int col = 0; col < w; col++) {
/*  908 */               raster.getPixel(col, row, pixel);
/*  909 */               int alpha = (pixel[3] & 0xFF) << 24;
/*  910 */               int red = pixel[2] & 0xFF;
/*  911 */               int green = (pixel[1] & 0xFF) << 8;
/*  912 */               int blue = (pixel[0] & 0xFF) << 16;
/*  913 */               bits[col] = alpha | red | green | blue;
/*      */             } 
/*  915 */             int v = wh - origin.y + row - 1;
/*  916 */             this.pbits.write(((v * ww + origin.x) * 4), bits, 0, bits.length);
/*      */           } 
/*  918 */           WinUser.SIZE winSize = new WinUser.SIZE();
/*  919 */           winSize.cx = win.getWidth();
/*  920 */           winSize.cy = win.getHeight();
/*  921 */           WinDef.POINT winLoc = new WinDef.POINT();
/*  922 */           winLoc.x = win.getX();
/*  923 */           winLoc.y = win.getY();
/*  924 */           WinDef.POINT srcLoc = new WinDef.POINT();
/*  925 */           WinUser.BLENDFUNCTION blend = new WinUser.BLENDFUNCTION();
/*  926 */           WinDef.HWND hWnd = WindowUtils.W32WindowUtils.this.getHWnd(win);
/*      */           
/*  928 */           ByteByReference bref = new ByteByReference();
/*  929 */           IntByReference iref = new IntByReference();
/*  930 */           byte level = WindowUtils.W32WindowUtils.this.getAlpha(win);
/*      */           
/*      */           try {
/*  933 */             if (user.GetLayeredWindowAttributes(hWnd, null, bref, iref) && (iref
/*  934 */               .getValue() & 0x2) != 0) {
/*  935 */               level = bref.getValue();
/*      */             }
/*      */           }
/*  938 */           catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
/*      */           
/*  940 */           blend.SourceConstantAlpha = level;
/*  941 */           blend.AlphaFormat = 1;
/*  942 */           user.UpdateLayeredWindow(hWnd, screenDC, winLoc, winSize, this.memDC, srcLoc, 0, blend, 2);
/*      */         } finally {
/*      */           
/*  945 */           user.ReleaseDC(null, screenDC);
/*  946 */           if (this.memDC != null && oldBitmap != null) {
/*  947 */             gdi.SelectObject(this.memDC, oldBitmap);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowTransparent(final Window w, final boolean transparent) {
/*  959 */       if (!(w instanceof RootPaneContainer)) {
/*  960 */         throw new IllegalArgumentException("Window must be a RootPaneContainer");
/*      */       }
/*  962 */       if (!isWindowAlphaSupported()) {
/*  963 */         throw new UnsupportedOperationException("Set sun.java2d.noddraw=true to enable transparent windows");
/*      */       }
/*      */       
/*  966 */       boolean isTransparent = (w.getBackground() != null && w.getBackground().getAlpha() == 0);
/*  967 */       if (transparent == isTransparent)
/*      */         return; 
/*  969 */       whenDisplayable(w, new Runnable()
/*      */           {
/*      */             public void run() {
/*  972 */               User32 user = User32.INSTANCE;
/*  973 */               WinDef.HWND hWnd = WindowUtils.W32WindowUtils.this.getHWnd(w);
/*  974 */               int flags = user.GetWindowLong(hWnd, -20);
/*  975 */               JRootPane root = ((RootPaneContainer)w).getRootPane();
/*  976 */               JLayeredPane lp = root.getLayeredPane();
/*  977 */               Container content = root.getContentPane();
/*  978 */               if (content instanceof WindowUtils.W32WindowUtils.W32TransparentContentPane) {
/*  979 */                 ((WindowUtils.W32WindowUtils.W32TransparentContentPane)content).setTransparent(transparent);
/*      */               }
/*  981 */               else if (transparent) {
/*  982 */                 WindowUtils.W32WindowUtils.W32TransparentContentPane w32content = new WindowUtils.W32WindowUtils.W32TransparentContentPane(content);
/*      */                 
/*  984 */                 root.setContentPane(w32content);
/*  985 */                 lp.add(new WindowUtils.RepaintTrigger(w32content), JLayeredPane.DRAG_LAYER);
/*      */               } 
/*      */               
/*  988 */               if (transparent && !WindowUtils.W32WindowUtils.this.usingUpdateLayeredWindow(w)) {
/*  989 */                 flags |= 0x80000;
/*  990 */                 user.SetWindowLong(hWnd, -20, flags);
/*      */               }
/*  992 */               else if (!transparent && WindowUtils.W32WindowUtils.this.usingUpdateLayeredWindow(w)) {
/*  993 */                 flags &= 0xFFF7FFFF;
/*  994 */                 user.SetWindowLong(hWnd, -20, flags);
/*      */               } 
/*  996 */               WindowUtils.W32WindowUtils.this.setLayersTransparent(w, transparent);
/*  997 */               WindowUtils.W32WindowUtils.this.setForceHeavyweightPopups(w, transparent);
/*  998 */               WindowUtils.W32WindowUtils.this.setDoubleBuffered(w, !transparent);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public void setWindowMask(Component w, Shape mask) {
/* 1005 */       if (mask instanceof Area && ((Area)mask).isPolygonal()) {
/* 1006 */         setMask(w, (Area)mask);
/*      */       } else {
/*      */         
/* 1009 */         super.setWindowMask(w, mask);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void setWindowRegion(final Component w, final WinDef.HRGN hrgn) {
/* 1015 */       whenDisplayable(w, new Runnable()
/*      */           {
/*      */             public void run() {
/* 1018 */               GDI32 gdi = GDI32.INSTANCE;
/* 1019 */               User32 user = User32.INSTANCE;
/* 1020 */               WinDef.HWND hWnd = WindowUtils.W32WindowUtils.this.getHWnd(w);
/*      */               try {
/* 1022 */                 user.SetWindowRgn(hWnd, hrgn, true);
/* 1023 */                 WindowUtils.W32WindowUtils.this.setForceHeavyweightPopups(WindowUtils.W32WindowUtils.this.getWindow(w), (hrgn != null));
/*      */               } finally {
/*      */                 
/* 1026 */                 gdi.DeleteObject((WinNT.HANDLE)hrgn);
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     private void setMask(Component w, Area area) {
/* 1034 */       GDI32 gdi = GDI32.INSTANCE;
/* 1035 */       PathIterator pi = area.getPathIterator(null);
/* 1036 */       int mode = (pi.getWindingRule() == 1) ? 2 : 1;
/*      */       
/* 1038 */       float[] coords = new float[6];
/* 1039 */       List<WinDef.POINT> points = new ArrayList<WinDef.POINT>();
/* 1040 */       int size = 0;
/* 1041 */       List<Integer> sizes = new ArrayList<Integer>();
/* 1042 */       while (!pi.isDone()) {
/* 1043 */         int type = pi.currentSegment(coords);
/* 1044 */         if (type == 0) {
/* 1045 */           size = 1;
/* 1046 */           points.add(new WinDef.POINT((int)coords[0], (int)coords[1]));
/*      */         }
/* 1048 */         else if (type == 1) {
/* 1049 */           size++;
/* 1050 */           points.add(new WinDef.POINT((int)coords[0], (int)coords[1]));
/*      */         }
/* 1052 */         else if (type == 4) {
/* 1053 */           sizes.add(Integer.valueOf(size));
/*      */         } else {
/*      */           
/* 1056 */           throw new RuntimeException("Area is not polygonal: " + area);
/*      */         } 
/* 1058 */         pi.next();
/*      */       } 
/* 1060 */       WinDef.POINT[] lppt = (WinDef.POINT[])(new WinDef.POINT()).toArray(points.size());
/* 1061 */       WinDef.POINT[] pts = points.<WinDef.POINT>toArray(new WinDef.POINT[points.size()]);
/* 1062 */       for (int i = 0; i < lppt.length; i++) {
/* 1063 */         (lppt[i]).x = (pts[i]).x;
/* 1064 */         (lppt[i]).y = (pts[i]).y;
/*      */       } 
/* 1066 */       int[] counts = new int[sizes.size()];
/* 1067 */       for (int j = 0; j < counts.length; j++) {
/* 1068 */         counts[j] = ((Integer)sizes.get(j)).intValue();
/*      */       }
/* 1070 */       WinDef.HRGN hrgn = gdi.CreatePolyPolygonRgn(lppt, counts, counts.length, mode);
/* 1071 */       setWindowRegion(w, hrgn);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void setMask(Component w, Raster raster) {
/* 1076 */       GDI32 gdi = GDI32.INSTANCE;
/*      */       
/* 1078 */       final WinDef.HRGN region = (raster != null) ? gdi.CreateRectRgn(0, 0, 0, 0) : null;
/* 1079 */       if (region != null) {
/* 1080 */         final WinDef.HRGN tempRgn = gdi.CreateRectRgn(0, 0, 0, 0);
/*      */         try {
/* 1082 */           RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput()
/*      */               {
/*      */                 public boolean outputRange(int x, int y, int w, int h) {
/* 1085 */                   GDI32 gdi = GDI32.INSTANCE;
/* 1086 */                   gdi.SetRectRgn(tempRgn, x, y, x + w, y + h);
/* 1087 */                   return (gdi.CombineRgn(region, region, tempRgn, 2) != 0);
/*      */                 }
/*      */               });
/*      */         } finally {
/*      */           
/* 1092 */           gdi.DeleteObject((WinNT.HANDLE)tempRgn);
/*      */         } 
/*      */       } 
/* 1095 */       setWindowRegion(w, region);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BufferedImage getWindowIcon(WinDef.HWND hwnd) {
/* 1101 */       WinDef.DWORDByReference hIconNumber = new WinDef.DWORDByReference();
/*      */       
/* 1103 */       WinDef.LRESULT result = User32.INSTANCE.SendMessageTimeout(hwnd, 127, new WinDef.WPARAM(1L), new WinDef.LPARAM(0L), 2, 500, hIconNumber);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1108 */       if (result.intValue() == 0)
/*      */       {
/* 1110 */         result = User32.INSTANCE.SendMessageTimeout(hwnd, 127, new WinDef.WPARAM(0L), new WinDef.LPARAM(0L), 2, 500, hIconNumber);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1115 */       if (result.intValue() == 0)
/*      */       {
/* 1117 */         result = User32.INSTANCE.SendMessageTimeout(hwnd, 127, new WinDef.WPARAM(2L), new WinDef.LPARAM(0L), 2, 500, hIconNumber);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1122 */       if (result.intValue() == 0) {
/*      */ 
/*      */         
/* 1125 */         result = new WinDef.LRESULT(User32.INSTANCE.GetClassLongPtr(hwnd, -14).intValue());
/* 1126 */         hIconNumber.getValue().setValue(result.intValue());
/*      */       } 
/* 1128 */       if (result.intValue() == 0) {
/*      */ 
/*      */         
/* 1131 */         result = new WinDef.LRESULT(User32.INSTANCE.GetClassLongPtr(hwnd, -34).intValue());
/* 1132 */         hIconNumber.getValue().setValue(result.intValue());
/*      */       } 
/* 1134 */       if (result.intValue() == 0) {
/* 1135 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1139 */       WinDef.HICON hIcon = new WinDef.HICON(new Pointer(hIconNumber.getValue().longValue()));
/* 1140 */       Dimension iconSize = getIconSize(hIcon);
/* 1141 */       if (iconSize.width == 0 || iconSize.height == 0) {
/* 1142 */         return null;
/*      */       }
/* 1144 */       int width = iconSize.width;
/* 1145 */       int height = iconSize.height;
/* 1146 */       short depth = 24;
/*      */       
/* 1148 */       byte[] lpBitsColor = new byte[width * height * 24 / 8];
/* 1149 */       Memory memory1 = new Memory(lpBitsColor.length);
/* 1150 */       byte[] lpBitsMask = new byte[width * height * 24 / 8];
/* 1151 */       Memory memory2 = new Memory(lpBitsMask.length);
/* 1152 */       WinGDI.BITMAPINFO bitmapInfo = new WinGDI.BITMAPINFO();
/* 1153 */       WinGDI.BITMAPINFOHEADER hdr = new WinGDI.BITMAPINFOHEADER();
/*      */       
/* 1155 */       bitmapInfo.bmiHeader = hdr;
/* 1156 */       hdr.biWidth = width;
/* 1157 */       hdr.biHeight = height;
/* 1158 */       hdr.biPlanes = 1;
/* 1159 */       hdr.biBitCount = 24;
/* 1160 */       hdr.biCompression = 0;
/* 1161 */       hdr.write();
/* 1162 */       bitmapInfo.write();
/*      */       
/* 1164 */       WinDef.HDC hDC = User32.INSTANCE.GetDC(null);
/* 1165 */       WinGDI.ICONINFO iconInfo = new WinGDI.ICONINFO();
/* 1166 */       User32.INSTANCE.GetIconInfo(hIcon, iconInfo);
/* 1167 */       iconInfo.read();
/* 1168 */       GDI32.INSTANCE.GetDIBits(hDC, iconInfo.hbmColor, 0, height, (Pointer)memory1, bitmapInfo, 0);
/*      */       
/* 1170 */       memory1.read(0L, lpBitsColor, 0, lpBitsColor.length);
/* 1171 */       GDI32.INSTANCE.GetDIBits(hDC, iconInfo.hbmMask, 0, height, (Pointer)memory2, bitmapInfo, 0);
/*      */       
/* 1173 */       memory2.read(0L, lpBitsMask, 0, lpBitsMask.length);
/* 1174 */       BufferedImage image = new BufferedImage(width, height, 2);
/*      */ 
/*      */ 
/*      */       
/* 1178 */       int x = 0, y = height - 1; int i;
/* 1179 */       for (i = 0; i < lpBitsColor.length; i += 3) {
/* 1180 */         int b = lpBitsColor[i] & 0xFF;
/* 1181 */         int g = lpBitsColor[i + 1] & 0xFF;
/* 1182 */         int r = lpBitsColor[i + 2] & 0xFF;
/* 1183 */         int a = 255 - lpBitsMask[i] & 0xFF;
/* 1184 */         int argb = a << 24 | r << 16 | g << 8 | b;
/* 1185 */         image.setRGB(x, y, argb);
/* 1186 */         x = (x + 1) % width;
/* 1187 */         if (x == 0) {
/* 1188 */           y--;
/*      */         }
/*      */       } 
/* 1191 */       User32.INSTANCE.ReleaseDC(null, hDC);
/*      */       
/* 1193 */       return image;
/*      */     }
/*      */ 
/*      */     
/*      */     public Dimension getIconSize(WinDef.HICON hIcon) {
/* 1198 */       WinGDI.ICONINFO iconInfo = new WinGDI.ICONINFO();
/*      */       try {
/* 1200 */         if (!User32.INSTANCE.GetIconInfo(hIcon, iconInfo))
/* 1201 */           return new Dimension(); 
/* 1202 */         iconInfo.read();
/*      */         
/* 1204 */         WinGDI.BITMAP bmp = new WinGDI.BITMAP();
/* 1205 */         if (iconInfo.hbmColor != null && iconInfo.hbmColor
/* 1206 */           .getPointer() != Pointer.NULL) {
/* 1207 */           int nWrittenBytes = GDI32.INSTANCE.GetObject((WinNT.HANDLE)iconInfo.hbmColor, bmp
/* 1208 */               .size(), bmp.getPointer());
/* 1209 */           bmp.read();
/* 1210 */           if (nWrittenBytes > 0)
/* 1211 */             return new Dimension(bmp.bmWidth.intValue(), bmp.bmHeight
/* 1212 */                 .intValue()); 
/* 1213 */         } else if (iconInfo.hbmMask != null && iconInfo.hbmMask
/* 1214 */           .getPointer() != Pointer.NULL) {
/* 1215 */           int nWrittenBytes = GDI32.INSTANCE.GetObject((WinNT.HANDLE)iconInfo.hbmMask, bmp
/* 1216 */               .size(), bmp.getPointer());
/* 1217 */           bmp.read();
/* 1218 */           if (nWrittenBytes > 0)
/* 1219 */             return new Dimension(bmp.bmWidth.intValue(), bmp.bmHeight.intValue() / 2); 
/*      */         } 
/*      */       } finally {
/* 1222 */         if (iconInfo.hbmColor != null && iconInfo.hbmColor
/* 1223 */           .getPointer() != Pointer.NULL)
/* 1224 */           GDI32.INSTANCE.DeleteObject((WinNT.HANDLE)iconInfo.hbmColor); 
/* 1225 */         if (iconInfo.hbmMask != null && iconInfo.hbmMask
/* 1226 */           .getPointer() != Pointer.NULL) {
/* 1227 */           GDI32.INSTANCE.DeleteObject((WinNT.HANDLE)iconInfo.hbmMask);
/*      */         }
/*      */       } 
/* 1230 */       return new Dimension();
/*      */     }
/*      */ 
/*      */     
/*      */     public List<DesktopWindow> getAllWindows(final boolean onlyVisibleWindows) {
/* 1235 */       final List<DesktopWindow> result = new LinkedList<DesktopWindow>();
/*      */       
/* 1237 */       WinUser.WNDENUMPROC lpEnumFunc = new WinUser.WNDENUMPROC()
/*      */         {
/*      */           public boolean callback(WinDef.HWND hwnd, Pointer arg1)
/*      */           {
/*      */             try {
/* 1242 */               boolean visible = (!onlyVisibleWindows || User32.INSTANCE.IsWindowVisible(hwnd));
/* 1243 */               if (visible) {
/* 1244 */                 String title = WindowUtils.W32WindowUtils.this.getWindowTitle(hwnd);
/* 1245 */                 String filePath = WindowUtils.W32WindowUtils.this.getProcessFilePath(hwnd);
/* 1246 */                 Rectangle locAndSize = WindowUtils.W32WindowUtils.this.getWindowLocationAndSize(hwnd);
/* 1247 */                 result.add(new DesktopWindow(hwnd, title, filePath, locAndSize));
/*      */               }
/*      */             
/* 1250 */             } catch (Exception e) {
/*      */               
/* 1252 */               e.printStackTrace();
/*      */             } 
/*      */             
/* 1255 */             return true;
/*      */           }
/*      */         };
/*      */       
/* 1259 */       if (!User32.INSTANCE.EnumWindows(lpEnumFunc, null)) {
/* 1260 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1262 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getWindowTitle(WinDef.HWND hwnd) {
/* 1268 */       int requiredLength = User32.INSTANCE.GetWindowTextLength(hwnd) + 1;
/* 1269 */       char[] title = new char[requiredLength];
/* 1270 */       int length = User32.INSTANCE.GetWindowText(hwnd, title, title.length);
/*      */ 
/*      */       
/* 1273 */       return Native.toString(Arrays.copyOfRange(title, 0, length));
/*      */     }
/*      */ 
/*      */     
/*      */     public String getProcessFilePath(WinDef.HWND hwnd) {
/* 1278 */       char[] filePath = new char[2048];
/* 1279 */       IntByReference pid = new IntByReference();
/* 1280 */       User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);
/*      */       
/* 1282 */       WinNT.HANDLE process = Kernel32.INSTANCE.OpenProcess(1040, false, pid
/* 1283 */           .getValue());
/* 1284 */       if (process == null && Kernel32.INSTANCE
/* 1285 */         .GetLastError() != 5) {
/* 1286 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1288 */       int length = Psapi.INSTANCE.GetModuleFileNameExW(process, null, filePath, filePath.length);
/*      */       
/* 1290 */       if (length == 0 && Kernel32.INSTANCE
/* 1291 */         .GetLastError() != 6) {
/* 1292 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1294 */       return Native.toString(filePath).trim();
/*      */     }
/*      */ 
/*      */     
/*      */     public Rectangle getWindowLocationAndSize(WinDef.HWND hwnd) {
/* 1299 */       WinDef.RECT lpRect = new WinDef.RECT();
/* 1300 */       if (!User32.INSTANCE.GetWindowRect(hwnd, lpRect)) {
/* 1301 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1303 */       return new Rectangle(lpRect.left, lpRect.top, Math.abs(lpRect.right - lpRect.left), 
/* 1304 */           Math.abs(lpRect.bottom - lpRect.top));
/*      */     } }
/*      */   
/*      */   private static class MacWindowUtils extends NativeWindowUtils {
/*      */     private static final String WDRAG = "apple.awt.draggableWindowBackground";
/*      */     
/*      */     public boolean isWindowAlphaSupported() {
/* 1311 */       return true;
/*      */     }
/*      */     private MacWindowUtils() {}
/*      */     private OSXMaskingContentPane installMaskingPane(Window w) {
/*      */       OSXMaskingContentPane content;
/* 1316 */       if (w instanceof RootPaneContainer) {
/*      */         
/* 1318 */         RootPaneContainer rpc = (RootPaneContainer)w;
/* 1319 */         Container oldContent = rpc.getContentPane();
/* 1320 */         if (oldContent instanceof OSXMaskingContentPane) {
/* 1321 */           content = (OSXMaskingContentPane)oldContent;
/*      */         } else {
/*      */           
/* 1324 */           content = new OSXMaskingContentPane(oldContent);
/*      */           
/* 1326 */           rpc.setContentPane(content);
/*      */         } 
/*      */       } else {
/*      */         
/* 1330 */         Component oldContent = (w.getComponentCount() > 0) ? w.getComponent(0) : null;
/* 1331 */         if (oldContent instanceof OSXMaskingContentPane) {
/* 1332 */           content = (OSXMaskingContentPane)oldContent;
/*      */         } else {
/*      */           
/* 1335 */           content = new OSXMaskingContentPane(oldContent);
/* 1336 */           w.add(content);
/*      */         } 
/*      */       } 
/* 1339 */       return content;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowTransparent(Window w, boolean transparent) {
/* 1353 */       boolean isTransparent = (w.getBackground() != null && w.getBackground().getAlpha() == 0);
/* 1354 */       if (transparent != isTransparent) {
/* 1355 */         setBackgroundTransparent(w, transparent, "setWindowTransparent");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void fixWindowDragging(Window w, String context) {
/* 1362 */       if (w instanceof RootPaneContainer) {
/* 1363 */         JRootPane p = ((RootPaneContainer)w).getRootPane();
/* 1364 */         Boolean oldDraggable = (Boolean)p.getClientProperty("apple.awt.draggableWindowBackground");
/* 1365 */         if (oldDraggable == null) {
/* 1366 */           p.putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
/* 1367 */           if (w.isDisplayable()) {
/* 1368 */             System.err.println(context + "(): To avoid content dragging, " + context + "() must be called before the window is realized, or " + "apple.awt.draggableWindowBackground" + " must be set to Boolean.FALSE before the window is realized.  If you really want content dragging, set " + "apple.awt.draggableWindowBackground" + " on the window's root pane to Boolean.TRUE before calling " + context + "() to hide this message.");
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowAlpha(final Window w, final float alpha) {
/* 1384 */       if (w instanceof RootPaneContainer) {
/* 1385 */         JRootPane p = ((RootPaneContainer)w).getRootPane();
/* 1386 */         p.putClientProperty("Window.alpha", Float.valueOf(alpha));
/* 1387 */         fixWindowDragging(w, "setWindowAlpha");
/*      */       } 
/* 1389 */       whenDisplayable(w, new Runnable()
/*      */           {
/*      */             public void run() {
/* 1392 */               Object peer = w.getPeer();
/*      */               try {
/* 1394 */                 Class<?> cls = peer.getClass();
/* 1395 */                 Method m = cls.getMethod("setAlpha", new Class[] { float.class });
/* 1396 */                 m.invoke(peer, new Object[] { Float.valueOf(this.val$alpha) });
/*      */               }
/* 1398 */               catch (Exception exception) {}
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setWindowMask(Component w, Raster raster) {
/* 1406 */       if (raster != null) {
/* 1407 */         setWindowMask(w, toShape(raster));
/*      */       } else {
/*      */         
/* 1410 */         setWindowMask(w, new Rectangle(0, 0, w.getWidth(), w
/* 1411 */               .getHeight()));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setWindowMask(Component c, Shape shape) {
/* 1417 */       if (c instanceof Window) {
/* 1418 */         Window w = (Window)c;
/* 1419 */         OSXMaskingContentPane content = installMaskingPane(w);
/* 1420 */         content.setMask(shape);
/* 1421 */         setBackgroundTransparent(w, (shape != WindowUtils.MASK_NONE), "setWindowMask");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static class OSXMaskingContentPane
/*      */       extends JPanel
/*      */     {
/*      */       private static final long serialVersionUID = 1L;
/*      */       
/*      */       private Shape shape;
/*      */ 
/*      */       
/*      */       public OSXMaskingContentPane(Component oldContent) {
/* 1436 */         super(new BorderLayout());
/* 1437 */         if (oldContent != null) {
/* 1438 */           add(oldContent, "Center");
/*      */         }
/*      */       }
/*      */       
/*      */       public void setMask(Shape shape) {
/* 1443 */         this.shape = shape;
/* 1444 */         repaint();
/*      */       }
/*      */ 
/*      */       
/*      */       public void paint(Graphics graphics) {
/* 1449 */         Graphics2D g = (Graphics2D)graphics.create();
/* 1450 */         g.setComposite(AlphaComposite.Clear);
/* 1451 */         g.fillRect(0, 0, getWidth(), getHeight());
/* 1452 */         g.dispose();
/* 1453 */         if (this.shape != null) {
/* 1454 */           g = (Graphics2D)graphics.create();
/* 1455 */           g.setClip(this.shape);
/* 1456 */           super.paint(g);
/* 1457 */           g.dispose();
/*      */         } else {
/*      */           
/* 1460 */           super.paint(graphics);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void setBackgroundTransparent(Window w, boolean transparent, String context) {
/* 1467 */       JRootPane rp = (w instanceof RootPaneContainer) ? ((RootPaneContainer)w).getRootPane() : null;
/* 1468 */       if (transparent) {
/* 1469 */         if (rp != null) {
/* 1470 */           rp.putClientProperty("transparent-old-bg", w.getBackground());
/*      */         }
/* 1472 */         w.setBackground(new Color(0, 0, 0, 0));
/*      */       
/*      */       }
/* 1475 */       else if (rp != null) {
/* 1476 */         Color bg = (Color)rp.getClientProperty("transparent-old-bg");
/*      */ 
/*      */ 
/*      */         
/* 1480 */         if (bg != null) {
/* 1481 */           bg = new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), bg.getAlpha());
/*      */         }
/* 1483 */         w.setBackground(bg);
/* 1484 */         rp.putClientProperty("transparent-old-bg", (Object)null);
/*      */       } else {
/*      */         
/* 1487 */         w.setBackground((Color)null);
/*      */       } 
/*      */       
/* 1490 */       fixWindowDragging(w, context);
/*      */     } }
/*      */   
/*      */   private static class X11WindowUtils extends NativeWindowUtils {
/*      */     private boolean didCheck;
/*      */     
/*      */     private static X11.Pixmap createBitmap(X11.Display dpy, X11.Window win, Raster raster) {
/* 1497 */       X11 x11 = X11.INSTANCE;
/* 1498 */       Rectangle bounds = raster.getBounds();
/* 1499 */       int width = bounds.x + bounds.width;
/* 1500 */       int height = bounds.y + bounds.height;
/* 1501 */       X11.Pixmap pm = x11.XCreatePixmap(dpy, (X11.Drawable)win, width, height, 1);
/* 1502 */       X11.GC gc = x11.XCreateGC(dpy, (X11.Drawable)pm, new NativeLong(0L), null);
/* 1503 */       if (gc == null) {
/* 1504 */         return null;
/*      */       }
/* 1506 */       x11.XSetForeground(dpy, gc, new NativeLong(0L));
/* 1507 */       x11.XFillRectangle(dpy, (X11.Drawable)pm, gc, 0, 0, width, height);
/* 1508 */       final List<Rectangle> rlist = new ArrayList<Rectangle>();
/*      */       try {
/* 1510 */         RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput()
/*      */             {
/*      */               public boolean outputRange(int x, int y, int w, int h) {
/* 1513 */                 rlist.add(new Rectangle(x, y, w, h));
/* 1514 */                 return true;
/*      */               }
/*      */             });
/*      */         
/* 1518 */         X11.XRectangle[] rects = (X11.XRectangle[])(new X11.XRectangle()).toArray(rlist.size());
/* 1519 */         for (int i = 0; i < rects.length; i++) {
/* 1520 */           Rectangle r = rlist.get(i);
/* 1521 */           (rects[i]).x = (short)r.x;
/* 1522 */           (rects[i]).y = (short)r.y;
/* 1523 */           (rects[i]).width = (short)r.width;
/* 1524 */           (rects[i]).height = (short)r.height;
/*      */           
/* 1526 */           Pointer p = rects[i].getPointer();
/* 1527 */           p.setShort(0L, (short)r.x);
/* 1528 */           p.setShort(2L, (short)r.y);
/* 1529 */           p.setShort(4L, (short)r.width);
/* 1530 */           p.setShort(6L, (short)r.height);
/* 1531 */           rects[i].setAutoSynch(false);
/*      */         } 
/*      */         
/* 1534 */         int UNMASKED = 1;
/* 1535 */         x11.XSetForeground(dpy, gc, new NativeLong(1L));
/* 1536 */         x11.XFillRectangles(dpy, (X11.Drawable)pm, gc, rects, rects.length);
/*      */       } finally {
/*      */         
/* 1539 */         x11.XFreeGC(dpy, gc);
/*      */       } 
/* 1541 */       return pm;
/*      */     }
/*      */ 
/*      */     
/* 1545 */     private long[] alphaVisualIDs = new long[0]; private static final long OPAQUE = 4294967295L;
/*      */     private static final String OPACITY = "_NET_WM_WINDOW_OPACITY";
/*      */     
/*      */     public boolean isWindowAlphaSupported() {
/* 1549 */       return ((getAlphaVisualIDs()).length > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static long getVisualID(GraphicsConfiguration config) {
/*      */       try {
/* 1558 */         Object o = config.getClass().getMethod("getVisual", (Class[])null).invoke(config, (Object[])null);
/* 1559 */         return ((Number)o).longValue();
/*      */       }
/* 1561 */       catch (Exception e) {
/*      */         
/* 1563 */         e.printStackTrace();
/* 1564 */         return -1L;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
/* 1571 */       if (isWindowAlphaSupported()) {
/*      */         
/* 1573 */         GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 1574 */         GraphicsDevice[] devices = env.getScreenDevices();
/* 1575 */         for (int i = 0; i < devices.length; i++) {
/*      */           
/* 1577 */           GraphicsConfiguration[] configs = devices[i].getConfigurations();
/* 1578 */           for (int j = 0; j < configs.length; j++) {
/* 1579 */             long visualID = getVisualID(configs[j]);
/* 1580 */             long[] ids = getAlphaVisualIDs();
/* 1581 */             for (int k = 0; k < ids.length; k++) {
/* 1582 */               if (visualID == ids[k]) {
/* 1583 */                 return configs[j];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1589 */       return super.getAlphaCompatibleGraphicsConfiguration();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private synchronized long[] getAlphaVisualIDs() {
/* 1597 */       if (this.didCheck) {
/* 1598 */         return this.alphaVisualIDs;
/*      */       }
/* 1600 */       this.didCheck = true;
/* 1601 */       X11 x11 = X11.INSTANCE;
/* 1602 */       X11.Display dpy = x11.XOpenDisplay(null);
/* 1603 */       if (dpy == null)
/* 1604 */         return this.alphaVisualIDs; 
/* 1605 */       X11.XVisualInfo info = null;
/*      */       try {
/* 1607 */         int screen = x11.XDefaultScreen(dpy);
/* 1608 */         X11.XVisualInfo template = new X11.XVisualInfo();
/* 1609 */         template.screen = screen;
/* 1610 */         template.depth = 32;
/* 1611 */         template.c_class = 4;
/* 1612 */         NativeLong mask = new NativeLong(14L);
/*      */ 
/*      */         
/* 1615 */         IntByReference pcount = new IntByReference();
/* 1616 */         info = x11.XGetVisualInfo(dpy, mask, template, pcount);
/* 1617 */         if (info != null) {
/* 1618 */           List<X11.VisualID> list = new ArrayList<X11.VisualID>();
/*      */           
/* 1620 */           X11.XVisualInfo[] infos = (X11.XVisualInfo[])info.toArray(pcount.getValue()); int i;
/* 1621 */           for (i = 0; i < infos.length; i++) {
/*      */             
/* 1623 */             X11.Xrender.XRenderPictFormat format = X11.Xrender.INSTANCE.XRenderFindVisualFormat(dpy, (infos[i]).visual);
/*      */             
/* 1625 */             if (format.type == 1 && format.direct.alphaMask != 0)
/*      */             {
/* 1627 */               list.add((infos[i]).visualid);
/*      */             }
/*      */           } 
/* 1630 */           this.alphaVisualIDs = new long[list.size()];
/* 1631 */           for (i = 0; i < this.alphaVisualIDs.length; i++) {
/* 1632 */             this.alphaVisualIDs[i] = ((Number)list.get(i)).longValue();
/*      */           }
/* 1634 */           return this.alphaVisualIDs;
/*      */         } 
/*      */       } finally {
/*      */         
/* 1638 */         if (info != null) {
/* 1639 */           x11.XFree(info.getPointer());
/*      */         }
/* 1641 */         x11.XCloseDisplay(dpy);
/*      */       } 
/* 1643 */       return this.alphaVisualIDs;
/*      */     }
/*      */ 
/*      */     
/*      */     private static X11.Window getContentWindow(Window w, X11.Display dpy, X11.Window win, Point offset) {
/* 1648 */       if ((w instanceof Frame && !((Frame)w).isUndecorated()) || (w instanceof Dialog && 
/* 1649 */         !((Dialog)w).isUndecorated())) {
/* 1650 */         X11 x11 = X11.INSTANCE;
/* 1651 */         X11.WindowByReference rootp = new X11.WindowByReference();
/* 1652 */         X11.WindowByReference parentp = new X11.WindowByReference();
/* 1653 */         PointerByReference childrenp = new PointerByReference();
/* 1654 */         IntByReference countp = new IntByReference();
/* 1655 */         x11.XQueryTree(dpy, win, rootp, parentp, childrenp, countp);
/* 1656 */         Pointer p = childrenp.getValue();
/* 1657 */         int[] ids = p.getIntArray(0L, countp.getValue());
/* 1658 */         int arrayOfInt1[] = ids, i = arrayOfInt1.length; byte b = 0; if (b < i) { int id = arrayOfInt1[b];
/*      */           
/* 1660 */           X11.Window child = new X11.Window(id);
/* 1661 */           X11.XWindowAttributes xwa = new X11.XWindowAttributes();
/* 1662 */           x11.XGetWindowAttributes(dpy, child, xwa);
/* 1663 */           offset.x = -xwa.x;
/* 1664 */           offset.y = -xwa.y;
/* 1665 */           win = child; }
/*      */ 
/*      */         
/* 1668 */         if (p != null) {
/* 1669 */           x11.XFree(p);
/*      */         }
/*      */       } 
/* 1672 */       return win;
/*      */     }
/*      */     
/*      */     private static X11.Window getDrawable(Component w) {
/* 1676 */       int id = (int)Native.getComponentID(w);
/* 1677 */       if (id == 0)
/* 1678 */         return null; 
/* 1679 */       return new X11.Window(id);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowAlpha(final Window w, final float alpha) {
/* 1687 */       if (!isWindowAlphaSupported()) {
/* 1688 */         throw new UnsupportedOperationException("This X11 display does not provide a 32-bit visual");
/*      */       }
/* 1690 */       Runnable action = new Runnable()
/*      */         {
/*      */           public void run() {
/* 1693 */             X11 x11 = X11.INSTANCE;
/* 1694 */             X11.Display dpy = x11.XOpenDisplay(null);
/* 1695 */             if (dpy == null)
/*      */               return; 
/*      */             try {
/* 1698 */               X11.Window win = WindowUtils.X11WindowUtils.getDrawable(w);
/* 1699 */               if (alpha == 1.0F) {
/* 1700 */                 x11.XDeleteProperty(dpy, win, x11
/* 1701 */                     .XInternAtom(dpy, "_NET_WM_WINDOW_OPACITY", false));
/*      */               }
/*      */               else {
/*      */                 
/* 1705 */                 int opacity = (int)((long)(alpha * 4.2949673E9F) & 0xFFFFFFFFFFFFFFFFL);
/* 1706 */                 IntByReference patom = new IntByReference(opacity);
/* 1707 */                 x11.XChangeProperty(dpy, win, x11
/* 1708 */                     .XInternAtom(dpy, "_NET_WM_WINDOW_OPACITY", false), X11.XA_CARDINAL, 32, 0, patom
/*      */ 
/*      */ 
/*      */                     
/* 1712 */                     .getPointer(), 1);
/*      */               } 
/*      */             } finally {
/*      */               
/* 1716 */               x11.XCloseDisplay(dpy);
/*      */             } 
/*      */           }
/*      */         };
/* 1720 */       whenDisplayable(w, action);
/*      */     }
/*      */     private class X11TransparentContentPane extends WindowUtils.NativeWindowUtils.TransparentContentPane { private static final long serialVersionUID = 1L; private Memory buffer;
/*      */       private int[] pixels;
/*      */       private final int[] pixel;
/*      */       
/*      */       public X11TransparentContentPane(Container oldContent) {
/* 1727 */         super(oldContent);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1732 */         this.pixel = new int[4];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected void paintDirect(BufferedImage buf, Rectangle bounds) {
/* 1738 */         Window window = SwingUtilities.getWindowAncestor(this);
/* 1739 */         X11 x11 = X11.INSTANCE;
/* 1740 */         X11.Display dpy = x11.XOpenDisplay(null);
/* 1741 */         X11.Window win = WindowUtils.X11WindowUtils.getDrawable(window);
/* 1742 */         Point offset = new Point();
/* 1743 */         win = WindowUtils.X11WindowUtils.getContentWindow(window, dpy, win, offset);
/* 1744 */         X11.GC gc = x11.XCreateGC(dpy, (X11.Drawable)win, new NativeLong(0L), null);
/*      */         
/* 1746 */         Raster raster = buf.getData();
/* 1747 */         int w = bounds.width;
/* 1748 */         int h = bounds.height;
/* 1749 */         if (this.buffer == null || this.buffer.size() != (w * h * 4)) {
/* 1750 */           this.buffer = new Memory((w * h * 4));
/* 1751 */           this.pixels = new int[w * h];
/*      */         } 
/* 1753 */         for (int y = 0; y < h; y++) {
/* 1754 */           for (int x = 0; x < w; x++) {
/* 1755 */             raster.getPixel(x, y, this.pixel);
/* 1756 */             int alpha = this.pixel[3] & 0xFF;
/* 1757 */             int red = this.pixel[2] & 0xFF;
/* 1758 */             int green = this.pixel[1] & 0xFF;
/* 1759 */             int blue = this.pixel[0] & 0xFF;
/*      */ 
/*      */             
/* 1762 */             this.pixels[y * w + x] = alpha << 24 | blue << 16 | green << 8 | red;
/*      */           } 
/*      */         } 
/* 1765 */         X11.XWindowAttributes xwa = new X11.XWindowAttributes();
/* 1766 */         x11.XGetWindowAttributes(dpy, win, xwa);
/*      */         
/* 1768 */         X11.XImage image = x11.XCreateImage(dpy, xwa.visual, 32, 2, 0, (Pointer)this.buffer, w, h, 32, w * 4);
/*      */         
/* 1770 */         this.buffer.write(0L, this.pixels, 0, this.pixels.length);
/* 1771 */         offset.x += bounds.x;
/* 1772 */         offset.y += bounds.y;
/* 1773 */         x11.XPutImage(dpy, (X11.Drawable)win, gc, image, 0, 0, offset.x, offset.y, w, h);
/*      */         
/* 1775 */         x11.XFree(image.getPointer());
/* 1776 */         x11.XFreeGC(dpy, gc);
/* 1777 */         x11.XCloseDisplay(dpy);
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setWindowTransparent(final Window w, final boolean transparent) {
/* 1784 */       if (!(w instanceof RootPaneContainer)) {
/* 1785 */         throw new IllegalArgumentException("Window must be a RootPaneContainer");
/*      */       }
/* 1787 */       if (!isWindowAlphaSupported()) {
/* 1788 */         throw new UnsupportedOperationException("This X11 display does not provide a 32-bit visual");
/*      */       }
/*      */       
/* 1791 */       if (!w.getGraphicsConfiguration().equals(getAlphaCompatibleGraphicsConfiguration())) {
/* 1792 */         throw new IllegalArgumentException("Window GraphicsConfiguration '" + w.getGraphicsConfiguration() + "' does not support transparency");
/*      */       }
/*      */       
/* 1795 */       boolean isTransparent = (w.getBackground() != null && w.getBackground().getAlpha() == 0);
/* 1796 */       if (transparent == isTransparent)
/*      */         return; 
/* 1798 */       whenDisplayable(w, new Runnable()
/*      */           {
/*      */             public void run() {
/* 1801 */               JRootPane root = ((RootPaneContainer)w).getRootPane();
/* 1802 */               JLayeredPane lp = root.getLayeredPane();
/* 1803 */               Container content = root.getContentPane();
/* 1804 */               if (content instanceof WindowUtils.X11WindowUtils.X11TransparentContentPane) {
/* 1805 */                 ((WindowUtils.X11WindowUtils.X11TransparentContentPane)content).setTransparent(transparent);
/*      */               }
/* 1807 */               else if (transparent) {
/* 1808 */                 WindowUtils.X11WindowUtils.X11TransparentContentPane x11content = new WindowUtils.X11WindowUtils.X11TransparentContentPane(content);
/*      */                 
/* 1810 */                 root.setContentPane(x11content);
/* 1811 */                 lp.add(new WindowUtils.RepaintTrigger(x11content), JLayeredPane.DRAG_LAYER);
/*      */               } 
/*      */               
/* 1814 */               WindowUtils.X11WindowUtils.this.setLayersTransparent(w, transparent);
/* 1815 */               WindowUtils.X11WindowUtils.this.setForceHeavyweightPopups(w, transparent);
/* 1816 */               WindowUtils.X11WindowUtils.this.setDoubleBuffered(w, !transparent);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setWindowShape(final Window w, final PixmapSource src) {
/* 1826 */       Runnable action = new Runnable()
/*      */         {
/*      */           public void run() {
/* 1829 */             X11 x11 = X11.INSTANCE;
/* 1830 */             X11.Display dpy = x11.XOpenDisplay(null);
/* 1831 */             if (dpy == null) {
/*      */               return;
/*      */             }
/* 1834 */             X11.Pixmap pm = null;
/*      */             try {
/* 1836 */               X11.Window win = WindowUtils.X11WindowUtils.getDrawable(w);
/* 1837 */               pm = src.getPixmap(dpy, win);
/* 1838 */               X11.Xext ext = X11.Xext.INSTANCE;
/* 1839 */               ext.XShapeCombineMask(dpy, win, 0, 0, 0, (pm == null) ? X11.Pixmap.None : pm, 0);
/*      */             
/*      */             }
/*      */             finally {
/*      */               
/* 1844 */               if (pm != null) {
/* 1845 */                 x11.XFreePixmap(dpy, pm);
/*      */               }
/* 1847 */               x11.XCloseDisplay(dpy);
/*      */             } 
/* 1849 */             WindowUtils.X11WindowUtils.this.setForceHeavyweightPopups(WindowUtils.X11WindowUtils.this.getWindow(w), (pm != null));
/*      */           }
/*      */         };
/* 1852 */       whenDisplayable(w, action);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void setMask(Component w, final Raster raster) {
/* 1857 */       setWindowShape(getWindow(w), new PixmapSource()
/*      */           {
/*      */             public X11.Pixmap getPixmap(X11.Display dpy, X11.Window win) {
/* 1860 */               return (raster != null) ? WindowUtils.X11WindowUtils.createBitmap(dpy, win, raster) : null;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     private X11WindowUtils() {}
/*      */     
/*      */     private static interface PixmapSource {
/*      */       X11.Pixmap getPixmap(X11.Display param2Display, X11.Window param2Window); }
/*      */   }
/*      */   
/*      */   public static void setWindowMask(Window w, Shape mask) {
/* 1872 */     getInstance().setWindowMask(w, mask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setComponentMask(Component c, Shape mask) {
/* 1881 */     getInstance().setWindowMask(c, mask);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setWindowMask(Window w, Icon mask) {
/* 1890 */     getInstance().setWindowMask(w, mask);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isWindowAlphaSupported() {
/* 1895 */     return getInstance().isWindowAlphaSupported();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
/* 1903 */     return getInstance().getAlphaCompatibleGraphicsConfiguration();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setWindowAlpha(Window w, float alpha) {
/* 1921 */     getInstance().setWindowAlpha(w, Math.max(0.0F, Math.min(alpha, 1.0F)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setWindowTransparent(Window w, boolean transparent) {
/* 1937 */     getInstance().setWindowTransparent(w, transparent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedImage getWindowIcon(WinDef.HWND hwnd) {
/* 1950 */     return getInstance().getWindowIcon(hwnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Dimension getIconSize(WinDef.HICON hIcon) {
/* 1962 */     return getInstance().getIconSize(hIcon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<DesktopWindow> getAllWindows(boolean onlyVisibleWindows) {
/* 1980 */     return getInstance().getAllWindows(onlyVisibleWindows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getWindowTitle(WinDef.HWND hwnd) {
/* 1993 */     return getInstance().getWindowTitle(hwnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getProcessFilePath(WinDef.HWND hwnd) {
/* 2007 */     return getInstance().getProcessFilePath(hwnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Rectangle getWindowLocationAndSize(WinDef.HWND hwnd) {
/* 2019 */     return getInstance().getWindowLocationAndSize(hwnd);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/WindowUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */