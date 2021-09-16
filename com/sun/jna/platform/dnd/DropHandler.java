/*     */ package com.sun.jna.platform.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.dnd.DropTargetDragEvent;
/*     */ import java.awt.dnd.DropTargetDropEvent;
/*     */ import java.awt.dnd.DropTargetEvent;
/*     */ import java.awt.dnd.DropTargetListener;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DropHandler
/*     */   implements DropTargetListener
/*     */ {
/*     */   private int acceptedActions;
/*     */   private List<DataFlavor> acceptedFlavors;
/*     */   private DropTarget dropTarget;
/*     */   private boolean active = true;
/*     */   private DropTargetPainter painter;
/*     */   private String lastAction;
/*     */   
/*     */   public DropHandler(Component c, int acceptedActions) {
/* 113 */     this(c, acceptedActions, new DataFlavor[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropHandler(Component c, int acceptedActions, DataFlavor[] acceptedFlavors) {
/* 124 */     this(c, acceptedActions, acceptedFlavors, null);
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
/*     */   public DropHandler(Component c, int acceptedActions, DataFlavor[] acceptedFlavors, DropTargetPainter painter) {
/* 137 */     this.acceptedActions = acceptedActions;
/* 138 */     this.acceptedFlavors = Arrays.asList(acceptedFlavors);
/* 139 */     this.painter = painter;
/* 140 */     this.dropTarget = new DropTarget(c, acceptedActions, this, this.active);
/*     */   }
/*     */   
/*     */   protected DropTarget getDropTarget() {
/* 144 */     return this.dropTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 150 */     return this.active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 157 */     this.active = active;
/* 158 */     if (this.dropTarget != null) {
/* 159 */       this.dropTarget.setActive(active);
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
/*     */   protected int getDropActionsForFlavors(DataFlavor[] dataFlavors) {
/* 173 */     return this.acceptedActions;
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
/*     */   protected int getDropAction(DropTargetEvent e) {
/* 192 */     int currentAction = 0;
/* 193 */     int sourceActions = 0;
/* 194 */     Point location = null;
/* 195 */     DataFlavor[] flavors = new DataFlavor[0];
/* 196 */     if (e instanceof DropTargetDragEvent) {
/* 197 */       DropTargetDragEvent ev = (DropTargetDragEvent)e;
/* 198 */       currentAction = ev.getDropAction();
/* 199 */       sourceActions = ev.getSourceActions();
/* 200 */       flavors = ev.getCurrentDataFlavors();
/* 201 */       location = ev.getLocation();
/*     */     }
/* 203 */     else if (e instanceof DropTargetDropEvent) {
/* 204 */       DropTargetDropEvent ev = (DropTargetDropEvent)e;
/* 205 */       currentAction = ev.getDropAction();
/* 206 */       sourceActions = ev.getSourceActions();
/* 207 */       flavors = ev.getCurrentDataFlavors();
/* 208 */       location = ev.getLocation();
/*     */     } 
/* 210 */     if (isSupported(flavors)) {
/* 211 */       int availableActions = getDropActionsForFlavors(flavors);
/* 212 */       currentAction = getDropAction(e, currentAction, sourceActions, availableActions);
/* 213 */       if (currentAction != 0 && 
/* 214 */         canDrop(e, currentAction, location)) {
/* 215 */         return currentAction;
/*     */       }
/*     */     } 
/*     */     
/* 219 */     return 0;
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
/*     */   protected int getDropAction(DropTargetEvent e, int currentAction, int sourceActions, int acceptedActions) {
/* 240 */     boolean modifiersActive = modifiersActive(currentAction);
/* 241 */     if ((currentAction & acceptedActions) == 0 && !modifiersActive) {
/*     */       
/* 243 */       int action = acceptedActions & sourceActions;
/* 244 */       currentAction = action;
/*     */     }
/* 246 */     else if (modifiersActive) {
/* 247 */       int action = currentAction & acceptedActions & sourceActions;
/* 248 */       if (action != currentAction) {
/* 249 */         currentAction = action;
/*     */       }
/*     */     } 
/* 252 */     return currentAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean modifiersActive(int dropAction) {
/* 263 */     int mods = DragHandler.getModifiers();
/* 264 */     if (mods == -1) {
/* 265 */       if (dropAction == 1073741824 || dropAction == 1)
/*     */       {
/* 267 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 271 */       return false;
/*     */     } 
/* 273 */     return (mods != 0);
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
/*     */   private void describe(String type, DropTargetEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int acceptOrReject(DropTargetDragEvent e) {
/* 308 */     int action = getDropAction(e);
/* 309 */     if (action != 0) {
/*     */ 
/*     */       
/* 312 */       e.acceptDrag(action);
/*     */     } else {
/*     */       
/* 315 */       e.rejectDrag();
/*     */     } 
/* 317 */     return action;
/*     */   }
/*     */   
/*     */   public void dragEnter(DropTargetDragEvent e) {
/* 321 */     describe("enter(tgt)", e);
/* 322 */     int action = acceptOrReject(e);
/* 323 */     paintDropTarget(e, action, e.getLocation());
/*     */   }
/*     */   
/*     */   public void dragOver(DropTargetDragEvent e) {
/* 327 */     describe("over(tgt)", e);
/* 328 */     int action = acceptOrReject(e);
/* 329 */     paintDropTarget(e, action, e.getLocation());
/*     */   }
/*     */   
/*     */   public void dragExit(DropTargetEvent e) {
/* 333 */     describe("exit(tgt)", e);
/* 334 */     paintDropTarget(e, 0, null);
/*     */   }
/*     */   
/*     */   public void dropActionChanged(DropTargetDragEvent e) {
/* 338 */     describe("change(tgt)", e);
/* 339 */     int action = acceptOrReject(e);
/* 340 */     paintDropTarget(e, action, e.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drop(DropTargetDropEvent e) {
/* 348 */     describe("drop(tgt)", e);
/* 349 */     int action = getDropAction(e);
/* 350 */     if (action != 0) {
/* 351 */       e.acceptDrop(action);
/*     */       try {
/* 353 */         drop(e, action);
/*     */         
/* 355 */         e.dropComplete(true);
/*     */       }
/* 357 */       catch (Exception ex) {
/* 358 */         e.dropComplete(false);
/*     */       } 
/*     */     } else {
/*     */       
/* 362 */       e.rejectDrop();
/*     */     } 
/* 364 */     paintDropTarget(e, 0, e.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSupported(DataFlavor[] flavors) {
/* 374 */     Set<DataFlavor> set = new HashSet<DataFlavor>(Arrays.asList(flavors));
/* 375 */     set.retainAll(this.acceptedFlavors);
/* 376 */     return !set.isEmpty();
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
/*     */   protected void paintDropTarget(DropTargetEvent e, int action, Point location) {
/* 393 */     if (this.painter != null) {
/* 394 */       this.painter.paintDropTarget(e, action, location);
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
/*     */   protected boolean canDrop(DropTargetEvent e, int action, Point location) {
/* 409 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract void drop(DropTargetDropEvent paramDropTargetDropEvent, int paramInt) throws UnsupportedFlavorException, IOException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/dnd/DropHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */