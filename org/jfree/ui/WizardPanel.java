/*    */ package org.jfree.ui;
/*    */ 
/*    */ import java.awt.LayoutManager;
/*    */ import javax.swing.JPanel;
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
/*    */ public abstract class WizardPanel
/*    */   extends JPanel
/*    */ {
/*    */   private WizardDialog owner;
/*    */   
/*    */   protected WizardPanel(LayoutManager layout) {
/* 65 */     super(layout);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WizardDialog getOwner() {
/* 74 */     return this.owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOwner(WizardDialog owner) {
/* 84 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getResult() {
/* 93 */     return null;
/*    */   }
/*    */   
/*    */   public abstract void returnFromLaterStep();
/*    */   
/*    */   public abstract boolean canRedisplayNextPanel();
/*    */   
/*    */   public abstract boolean hasNextPanel();
/*    */   
/*    */   public abstract boolean canFinish();
/*    */   
/*    */   public abstract WizardPanel getNextPanel();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/WizardPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */