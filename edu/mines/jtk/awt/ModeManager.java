/*     */ package edu.mines.jtk.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.util.HashSet;
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
/*     */ public class ModeManager
/*     */ {
/*     */   public void add(Component c) {
/*  45 */     this._cset.add(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Component c) {
/*  53 */     this._cset.remove(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(Mode m) {
/*  64 */     this._mset.add(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setActive(Mode mode, boolean active) {
/*  75 */     if (active == mode.isActive()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  81 */     Mode modeDeactivated = null;
/*  82 */     if (active && mode.isExclusive()) {
/*  83 */       for (Mode m : this._mset) {
/*  84 */         if (m != mode && m.isExclusive() && m.isActive()) {
/*  85 */           setActiveInternal(m, false);
/*  86 */           modeDeactivated = m;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  93 */     setActiveInternal(mode, active);
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (!active && mode.isExclusive() && this._modeDeactivated != null) {
/*  98 */       setActiveInternal(this._modeDeactivated, true);
/*     */     }
/*     */ 
/*     */     
/* 102 */     this._modeDeactivated = modeDeactivated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private Set<Mode> _mset = new HashSet<>();
/* 109 */   private Set<Component> _cset = new HashSet<>();
/*     */   private Mode _modeDeactivated;
/*     */   
/*     */   private void setActiveInternal(Mode mode, boolean active) {
/* 113 */     Cursor cursor = active ? mode.getCursor() : null;
/* 114 */     if (cursor == null)
/* 115 */       cursor = Cursor.getDefaultCursor(); 
/* 116 */     mode.setActiveInternal(active);
/* 117 */     for (Component c : this._cset) {
/* 118 */       c.setCursor(cursor);
/* 119 */       mode.setActive(c, active);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/awt/ModeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */