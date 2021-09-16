/*    */ package edu.mines.jtk.awt;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.JToggleButton;
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
/*    */ public class ModeToggleButton
/*    */   extends JToggleButton
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ModeToggleButton(Mode mode) {
/* 41 */     super(mode);
/* 42 */     setText(null);
/* 43 */     setMnemonic(0);
/* 44 */     mode.addPropertyChangeListener(new PropertyChangeListener() {
/*    */           public void propertyChange(PropertyChangeEvent e) {
/* 46 */             if (e.getPropertyName().equals("active"))
/* 47 */               ModeToggleButton.this.setSelected(((Boolean)e.getNewValue()).booleanValue()); 
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/awt/ModeToggleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */