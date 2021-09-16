/*    */ package edu.mines.jtk.awt;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.JRadioButtonMenuItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModeMenuItem
/*    */   extends JRadioButtonMenuItem
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ModeMenuItem(Mode mode) {
/* 41 */     super(mode);
/* 42 */     setIcon(null);
/* 43 */     mode.addPropertyChangeListener(new PropertyChangeListener() {
/*    */           public void propertyChange(PropertyChangeEvent e) {
/* 45 */             if (e.getPropertyName().equals("active"))
/* 46 */               ModeMenuItem.this.setSelected(((Boolean)e.getNewValue()).booleanValue()); 
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/awt/ModeMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */