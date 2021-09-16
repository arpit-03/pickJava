/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
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
/*     */ public final class JTextObserver
/*     */   implements FocusListener
/*     */ {
/*     */   private static JTextObserver singleton;
/*     */   
/*     */   public static JTextObserver getInstance() {
/*  72 */     if (singleton == null) {
/*  73 */       singleton = new JTextObserver();
/*     */     }
/*  75 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusGained(FocusEvent e) {
/*  84 */     if (e.getSource() instanceof JTextComponent) {
/*  85 */       JTextComponent tex = (JTextComponent)e.getSource();
/*  86 */       tex.selectAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent e) {
/*  96 */     if (e.getSource() instanceof JTextComponent) {
/*  97 */       JTextComponent tex = (JTextComponent)e.getSource();
/*  98 */       tex.select(0, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addTextComponent(JTextComponent t) {
/* 108 */     if (singleton == null) {
/* 109 */       singleton = new JTextObserver();
/*     */     }
/* 111 */     t.addFocusListener(singleton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeTextComponent(JTextComponent t) {
/* 120 */     if (singleton == null) {
/* 121 */       singleton = new JTextObserver();
/*     */     }
/* 123 */     t.removeFocusListener(singleton);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/JTextObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */