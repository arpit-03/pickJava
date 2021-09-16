/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.AbstractButton;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FloatingButtonEnabler
/*     */   extends MouseAdapter
/*     */ {
/*     */   private static FloatingButtonEnabler singleton;
/*     */   
/*     */   public static FloatingButtonEnabler getInstance() {
/*  73 */     if (singleton == null) {
/*  74 */       singleton = new FloatingButtonEnabler();
/*     */     }
/*  76 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addButton(AbstractButton button) {
/*  85 */     button.addMouseListener(this);
/*  86 */     button.setBorderPainted(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeButton(AbstractButton button) {
/*  95 */     button.addMouseListener(this);
/*  96 */     button.setBorderPainted(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEvent e) {
/* 105 */     if (e.getSource() instanceof AbstractButton) {
/* 106 */       AbstractButton button = (AbstractButton)e.getSource();
/* 107 */       if (button.isEnabled()) {
/* 108 */         button.setBorderPainted(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseEvent e) {
/* 119 */     if (e.getSource() instanceof AbstractButton) {
/* 120 */       AbstractButton button = (AbstractButton)e.getSource();
/* 121 */       button.setBorderPainted(false);
/* 122 */       if (button.getParent() != null)
/*     */       {
/*     */ 
/*     */         
/* 126 */         button.getParent().repaint();
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/FloatingButtonEnabler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */