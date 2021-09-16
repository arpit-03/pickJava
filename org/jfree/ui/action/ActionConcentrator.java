/*     */ package org.jfree.ui.action;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionConcentrator
/*     */ {
/*  63 */   private final ArrayList actions = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAction(Action a) {
/*  72 */     if (a == null) {
/*  73 */       throw new NullPointerException();
/*     */     }
/*  75 */     this.actions.add(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAction(Action a) {
/*  84 */     if (a == null) {
/*  85 */       throw new NullPointerException();
/*     */     }
/*  87 */     this.actions.remove(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean b) {
/*  96 */     for (int i = 0; i < this.actions.size(); i++) {
/*  97 */       Action a = this.actions.get(i);
/*  98 */       a.setEnabled(b);
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
/*     */   public boolean isEnabled() {
/* 111 */     for (int i = 0; i < this.actions.size(); i++) {
/* 112 */       Action a = this.actions.get(i);
/* 113 */       if (a.isEnabled()) {
/* 114 */         return true;
/*     */       }
/*     */     } 
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/action/ActionConcentrator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */