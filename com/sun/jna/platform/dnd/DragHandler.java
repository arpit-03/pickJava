/*     */ package com.sun.jna.platform.dnd;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.DragGestureEvent;
/*     */ import java.awt.dnd.DragGestureListener;
/*     */ import java.awt.dnd.DragSource;
/*     */ import java.awt.dnd.DragSourceDragEvent;
/*     */ import java.awt.dnd.DragSourceDropEvent;
/*     */ import java.awt.dnd.DragSourceEvent;
/*     */ import java.awt.dnd.DragSourceListener;
/*     */ import java.awt.dnd.DragSourceMotionListener;
/*     */ import java.awt.dnd.DropTargetDropEvent;
/*     */ import java.awt.dnd.DropTargetEvent;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DragHandler
/*     */   implements DragSourceListener, DragSourceMotionListener, DragGestureListener
/*     */ {
/* 114 */   public static final Dimension MAX_GHOST_SIZE = new Dimension(250, 250);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float DEFAULT_GHOST_ALPHA = 0.5F;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int UNKNOWN_MODIFIERS = -1;
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final Transferable UNKNOWN_TRANSFERABLE = null;
/*     */ 
/*     */   
/*     */   protected static final int MOVE = 2;
/*     */ 
/*     */   
/*     */   protected static final int COPY = 1;
/*     */ 
/*     */   
/*     */   protected static final int LINK = 1073741824;
/*     */   
/*     */   protected static final int NONE = 0;
/*     */   
/*     */   static final int MOVE_MASK = 64;
/*     */   
/* 142 */   static final boolean OSX = (System.getProperty("os.name").toLowerCase().indexOf("mac") != -1);
/*     */   
/* 144 */   static final int COPY_MASK = OSX ? 512 : 128;
/*     */ 
/*     */   
/* 147 */   static final int LINK_MASK = OSX ? 768 : 192;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int KEY_MASK = 9152;
/*     */ 
/*     */ 
/*     */   
/* 156 */   private static int modifiers = -1;
/* 157 */   private static Transferable transferable = UNKNOWN_TRANSFERABLE;
/*     */ 
/*     */ 
/*     */   
/*     */   private int supportedActions;
/*     */ 
/*     */ 
/*     */   
/*     */   static int getModifiers() {
/* 166 */     return modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transferable getTransferable(DropTargetEvent e) {
/* 176 */     if (e instanceof java.awt.dnd.DropTargetDragEvent) {
/*     */       
/*     */       try {
/* 179 */         return (Transferable)e
/* 180 */           .getClass().getMethod("getTransferable", (Class[])null).invoke(e, (Object[])null);
/*     */       }
/* 182 */       catch (Exception exception) {}
/*     */ 
/*     */     
/*     */     }
/* 186 */     else if (e instanceof DropTargetDropEvent) {
/* 187 */       return ((DropTargetDropEvent)e).getTransferable();
/*     */     } 
/* 189 */     return transferable;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean fixCursor = true;
/*     */   private Component dragSource;
/*     */   private GhostedDragImage ghost;
/*     */   private Point imageOffset;
/* 197 */   private Dimension maxGhostSize = MAX_GHOST_SIZE;
/* 198 */   private float ghostAlpha = 0.5F;
/*     */   
/*     */   private String lastAction;
/*     */   
/*     */   private boolean moved;
/*     */ 
/*     */   
/*     */   protected DragHandler(Component dragSource, int actions) {
/* 206 */     this.dragSource = dragSource;
/* 207 */     this.supportedActions = actions;
/*     */     try {
/* 209 */       String alpha = System.getProperty("DragHandler.alpha");
/* 210 */       if (alpha != null) {
/*     */         try {
/* 212 */           this.ghostAlpha = Float.parseFloat(alpha);
/*     */         }
/* 214 */         catch (NumberFormatException numberFormatException) {}
/*     */       }
/* 216 */       String max = System.getProperty("DragHandler.maxDragImageSize");
/* 217 */       if (max != null) {
/* 218 */         String[] size = max.split("x");
/* 219 */         if (size.length == 2) {
/*     */           try {
/* 221 */             this
/* 222 */               .maxGhostSize = new Dimension(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
/*     */           }
/* 224 */           catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */       }
/*     */     
/* 228 */     } catch (SecurityException securityException) {}
/*     */     
/* 230 */     disableSwingDragSupport(dragSource);
/* 231 */     DragSource src = DragSource.getDefaultDragSource();
/* 232 */     src.createDefaultDragGestureRecognizer(dragSource, this.supportedActions, this);
/*     */   }
/*     */   
/*     */   private void disableSwingDragSupport(Component comp) {
/* 236 */     if (comp instanceof JTree) {
/* 237 */       ((JTree)comp).setDragEnabled(false);
/*     */     }
/* 239 */     else if (comp instanceof JList) {
/* 240 */       ((JList)comp).setDragEnabled(false);
/*     */     }
/* 242 */     else if (comp instanceof JTable) {
/* 243 */       ((JTable)comp).setDragEnabled(false);
/*     */     }
/* 245 */     else if (comp instanceof JTextComponent) {
/* 246 */       ((JTextComponent)comp).setDragEnabled(false);
/*     */     }
/* 248 */     else if (comp instanceof JColorChooser) {
/* 249 */       ((JColorChooser)comp).setDragEnabled(false);
/*     */     }
/* 251 */     else if (comp instanceof JFileChooser) {
/* 252 */       ((JFileChooser)comp).setDragEnabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDrag(DragGestureEvent e) {
/* 263 */     int mods = e.getTriggerEvent().getModifiersEx() & 0x23C0;
/* 264 */     if (mods == 64)
/* 265 */       return ((this.supportedActions & 0x2) != 0); 
/* 266 */     if (mods == COPY_MASK)
/* 267 */       return ((this.supportedActions & 0x1) != 0); 
/* 268 */     if (mods == LINK_MASK)
/* 269 */       return ((this.supportedActions & 0x40000000) != 0); 
/* 270 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setModifiers(int mods) {
/* 277 */     modifiers = mods;
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
/*     */   protected abstract Transferable getTransferable(DragGestureEvent paramDragGestureEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon getDragIcon(DragGestureEvent e, Point srcOffset) {
/* 299 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dragStarted(DragGestureEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragGestureRecognized(DragGestureEvent e) {
/* 313 */     if ((e.getDragAction() & this.supportedActions) != 0 && 
/* 314 */       canDrag(e)) {
/* 315 */       setModifiers(e.getTriggerEvent().getModifiersEx() & 0x23C0);
/* 316 */       Transferable transferable = getTransferable(e);
/* 317 */       if (transferable == null)
/*     */         return; 
/*     */       try {
/* 320 */         Point srcOffset = new Point(0, 0);
/* 321 */         Icon icon = getDragIcon(e, srcOffset);
/* 322 */         Point origin = e.getDragOrigin();
/*     */         
/* 324 */         this.imageOffset = new Point(srcOffset.x - origin.x, srcOffset.y - origin.y);
/*     */         
/* 326 */         Icon dragIcon = scaleDragIcon(icon, this.imageOffset);
/* 327 */         Cursor cursor = null;
/* 328 */         if (dragIcon != null && DragSource.isDragImageSupported()) {
/* 329 */           GraphicsConfiguration gc = e.getComponent().getGraphicsConfiguration();
/* 330 */           e.startDrag(cursor, createDragImage(gc, dragIcon), this.imageOffset, transferable, this);
/*     */         }
/*     */         else {
/*     */           
/* 334 */           if (dragIcon != null) {
/* 335 */             Point screen = this.dragSource.getLocationOnScreen();
/* 336 */             screen.translate(origin.x, origin.y);
/* 337 */             Point cursorOffset = new Point(-this.imageOffset.x, -this.imageOffset.y);
/* 338 */             this
/* 339 */               .ghost = new GhostedDragImage(this.dragSource, dragIcon, getImageLocation(screen), cursorOffset);
/* 340 */             this.ghost.setAlpha(this.ghostAlpha);
/*     */           } 
/* 342 */           e.startDrag(cursor, transferable, this);
/*     */         } 
/* 344 */         dragStarted(e);
/* 345 */         this.moved = false;
/* 346 */         e.getDragSource().addDragSourceMotionListener(this);
/* 347 */         DragHandler.transferable = transferable;
/*     */       }
/* 349 */       catch (InvalidDnDOperationException ex) {
/* 350 */         if (this.ghost != null) {
/* 351 */           this.ghost.dispose();
/* 352 */           this.ghost = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon scaleDragIcon(Icon icon, Point imageOffset) {
/* 378 */     return icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Image createDragImage(GraphicsConfiguration gc, Icon icon) {
/* 388 */     int w = icon.getIconWidth();
/* 389 */     int h = icon.getIconHeight();
/* 390 */     BufferedImage image = gc.createCompatibleImage(w, h, 3);
/* 391 */     Graphics2D g = (Graphics2D)image.getGraphics();
/* 392 */     g.setComposite(AlphaComposite.Clear);
/* 393 */     g.fillRect(0, 0, w, h);
/*     */     
/* 395 */     g.setComposite(AlphaComposite.getInstance(2, this.ghostAlpha));
/* 396 */     icon.paintIcon(this.dragSource, g, 0, 0);
/* 397 */     g.dispose();
/* 398 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   private int reduce(int actions) {
/* 403 */     if ((actions & 0x2) != 0 && actions != 2) {
/* 404 */       return 2;
/*     */     }
/* 406 */     if ((actions & 0x1) != 0 && actions != 1) {
/* 407 */       return 1;
/*     */     }
/* 409 */     return actions;
/*     */   }
/*     */   
/*     */   protected Cursor getCursorForAction(int actualAction) {
/* 413 */     switch (actualAction) {
/*     */       case 2:
/* 415 */         return DragSource.DefaultMoveDrop;
/*     */       case 1:
/* 417 */         return DragSource.DefaultCopyDrop;
/*     */       case 1073741824:
/* 419 */         return DragSource.DefaultLinkDrop;
/*     */     } 
/* 421 */     return DragSource.DefaultMoveNoDrop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAcceptableDropAction(int targetActions) {
/* 430 */     return reduce(this.supportedActions & targetActions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getDropAction(DragSourceEvent ev) {
/* 438 */     if (ev instanceof DragSourceDragEvent) {
/* 439 */       DragSourceDragEvent e = (DragSourceDragEvent)ev;
/* 440 */       return e.getDropAction();
/*     */     } 
/* 442 */     if (ev instanceof DragSourceDropEvent) {
/* 443 */       return ((DragSourceDropEvent)ev).getDropAction();
/*     */     }
/* 445 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int adjustDropAction(DragSourceEvent ev) {
/* 454 */     int action = getDropAction(ev);
/* 455 */     if (ev instanceof DragSourceDragEvent) {
/* 456 */       DragSourceDragEvent e = (DragSourceDragEvent)ev;
/* 457 */       if (action == 0) {
/* 458 */         int mods = e.getGestureModifiersEx() & 0x23C0;
/* 459 */         if (mods == 0) {
/* 460 */           action = getAcceptableDropAction(e.getTargetActions());
/*     */         }
/*     */       } 
/*     */     } 
/* 464 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateCursor(DragSourceEvent ev) {
/* 472 */     if (!this.fixCursor)
/*     */       return; 
/* 474 */     Cursor cursor = getCursorForAction(adjustDropAction(ev));
/* 475 */     ev.getDragSourceContext().setCursor(cursor);
/*     */   }
/*     */   
/*     */   static String actionString(int action) {
/* 479 */     switch (action) { case 2:
/* 480 */         return "MOVE";
/* 481 */       case 3: return "MOVE|COPY";
/* 482 */       case 1073741826: return "MOVE|LINK";
/* 483 */       case 1073741827: return "MOVE|COPY|LINK";
/* 484 */       case 1: return "COPY";
/* 485 */       case 1073741825: return "COPY|LINK";
/* 486 */       case 1073741824: return "LINK"; }
/* 487 */      return "NONE";
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
/*     */   private void describe(String type, DragSourceEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragDropEnd(DragSourceDropEvent e) {
/* 513 */     describe("end", e);
/* 514 */     setModifiers(-1);
/* 515 */     transferable = UNKNOWN_TRANSFERABLE;
/* 516 */     if (this.ghost != null) {
/* 517 */       if (e.getDropSuccess()) {
/* 518 */         this.ghost.dispose();
/*     */       } else {
/*     */         
/* 521 */         this.ghost.returnToOrigin();
/*     */       } 
/* 523 */       this.ghost = null;
/*     */     } 
/* 525 */     DragSource src = e.getDragSourceContext().getDragSource();
/* 526 */     src.removeDragSourceMotionListener(this);
/* 527 */     this.moved = false;
/*     */   }
/*     */   
/*     */   private Point getImageLocation(Point where) {
/* 531 */     where.translate(this.imageOffset.x, this.imageOffset.y);
/* 532 */     return where;
/*     */   }
/*     */   
/*     */   public void dragEnter(DragSourceDragEvent e) {
/* 536 */     describe("enter", e);
/* 537 */     if (this.ghost != null) {
/* 538 */       this.ghost.move(getImageLocation(e.getLocation()));
/*     */     }
/* 540 */     updateCursor(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dragMouseMoved(DragSourceDragEvent e) {
/* 548 */     describe("move", e);
/* 549 */     if (this.ghost != null) {
/* 550 */       this.ghost.move(getImageLocation(e.getLocation()));
/*     */     }
/* 552 */     if (this.moved)
/* 553 */       updateCursor(e); 
/* 554 */     this.moved = true;
/*     */   }
/*     */   
/*     */   public void dragOver(DragSourceDragEvent e) {
/* 558 */     describe("over", e);
/* 559 */     if (this.ghost != null) {
/* 560 */       this.ghost.move(getImageLocation(e.getLocation()));
/*     */     }
/* 562 */     updateCursor(e);
/*     */   }
/*     */   
/*     */   public void dragExit(DragSourceEvent e) {
/* 566 */     describe("exit", e);
/*     */   }
/*     */   
/*     */   public void dropActionChanged(DragSourceDragEvent e) {
/* 570 */     describe("change", e);
/* 571 */     setModifiers(e.getGestureModifiersEx() & 0x23C0);
/* 572 */     if (this.ghost != null) {
/* 573 */       this.ghost.move(getImageLocation(e.getLocation()));
/*     */     }
/* 575 */     updateCursor(e);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/dnd/DragHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */