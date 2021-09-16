/*     */ package org.jfree.ui.action;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.KeyStroke;
/*     */ import org.jfree.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionRadioButton
/*     */   extends JRadioButton
/*     */ {
/*     */   private Action action;
/*     */   private ActionEnablePropertyChangeHandler propertyChangeHandler;
/*     */   
/*     */   private class ActionEnablePropertyChangeHandler
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private ActionEnablePropertyChangeHandler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent event) {
/*     */       try {
/*  86 */         if (event.getPropertyName().equals("enabled")) {
/*     */           
/*  88 */           ActionRadioButton.this.setEnabled(ActionRadioButton.this.getAction().isEnabled());
/*     */         }
/*  90 */         else if (event.getPropertyName().equals("SmallIcon")) {
/*     */           
/*  92 */           ActionRadioButton.this.setIcon((Icon)ActionRadioButton.this.getAction().getValue("SmallIcon"));
/*     */         }
/*  94 */         else if (event.getPropertyName().equals("Name")) {
/*     */           
/*  96 */           ActionRadioButton.this.setText((String)ActionRadioButton.this.getAction()
/*  97 */               .getValue("Name"));
/*     */         }
/*  99 */         else if (event.getPropertyName().equals("ShortDescription")) {
/*     */           
/* 101 */           ActionRadioButton.this.setToolTipText((String)ActionRadioButton.this
/* 102 */               .getAction().getValue("ShortDescription"));
/*     */         } 
/*     */         
/* 105 */         Action ac = ActionRadioButton.this.getAction();
/* 106 */         if (event.getPropertyName().equals("AcceleratorKey")) {
/*     */           
/* 108 */           KeyStroke oldVal = (KeyStroke)event.getOldValue();
/* 109 */           if (oldVal != null)
/*     */           {
/* 111 */             ActionRadioButton.this
/* 112 */               .unregisterKeyboardAction(oldVal);
/*     */           }
/* 114 */           Object o = ac.getValue("AcceleratorKey");
/* 115 */           if (o instanceof KeyStroke && o != null)
/*     */           {
/* 117 */             KeyStroke k = (KeyStroke)o;
/* 118 */             ActionRadioButton.this.registerKeyboardAction(ac, k, 2);
/*     */           }
/*     */         
/* 121 */         } else if (event.getPropertyName().equals("MnemonicKey")) {
/*     */           
/* 123 */           Object o = ac.getValue("MnemonicKey");
/* 124 */           if (o != null)
/*     */           {
/* 126 */             if (o instanceof Character)
/*     */             {
/* 128 */               Character c = (Character)o;
/* 129 */               ActionRadioButton.this.setMnemonic(c.charValue());
/*     */             }
/* 131 */             else if (o instanceof Integer)
/*     */             {
/* 133 */               Integer c = (Integer)o;
/* 134 */               ActionRadioButton.this.setMnemonic(c.intValue());
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/* 139 */       } catch (Exception e) {
/*     */         
/* 141 */         Log.warn("Error on PropertyChange in ActionButton: ", e);
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
/*     */   public ActionRadioButton() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionRadioButton(String text) {
/* 161 */     super(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionRadioButton(String text, Icon icon) {
/* 172 */     super(text, icon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionRadioButton(Icon icon) {
/* 183 */     super(icon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionRadioButton(Action action) {
/* 193 */     setAction(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/* 203 */     return this.action;
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
/*     */   private ActionEnablePropertyChangeHandler getPropertyChangeHandler() {
/* 215 */     if (this.propertyChangeHandler == null)
/*     */     {
/* 217 */       this.propertyChangeHandler = new ActionEnablePropertyChangeHandler();
/*     */     }
/* 219 */     return this.propertyChangeHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean b) {
/* 230 */     super.setEnabled(b);
/* 231 */     if (getAction() != null)
/*     */     {
/* 233 */       getAction().setEnabled(b);
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
/*     */   public void setAction(Action newAction) {
/* 252 */     Action oldAction = getAction();
/* 253 */     if (oldAction != null) {
/*     */       
/* 255 */       removeActionListener(oldAction);
/* 256 */       oldAction.removePropertyChangeListener(getPropertyChangeHandler());
/*     */       
/* 258 */       Object o = oldAction.getValue("AcceleratorKey");
/* 259 */       if (o instanceof KeyStroke && o != null) {
/*     */         
/* 261 */         KeyStroke k = (KeyStroke)o;
/* 262 */         unregisterKeyboardAction(k);
/*     */       } 
/*     */     } 
/* 265 */     this.action = newAction;
/* 266 */     if (this.action != null) {
/*     */       
/* 268 */       addActionListener(newAction);
/* 269 */       newAction.addPropertyChangeListener(getPropertyChangeHandler());
/*     */       
/* 271 */       setText((String)newAction.getValue("Name"));
/* 272 */       setToolTipText((String)newAction.getValue("ShortDescription"));
/* 273 */       setIcon((Icon)newAction.getValue("SmallIcon"));
/* 274 */       setEnabled(this.action.isEnabled());
/*     */       
/* 276 */       Object o = newAction.getValue("MnemonicKey");
/* 277 */       if (o != null)
/*     */       {
/* 279 */         if (o instanceof Character) {
/*     */           
/* 281 */           Character c = (Character)o;
/* 282 */           setMnemonic(c.charValue());
/*     */         }
/* 284 */         else if (o instanceof Integer) {
/*     */           
/* 286 */           Integer c = (Integer)o;
/* 287 */           setMnemonic(c.intValue());
/*     */         } 
/*     */       }
/* 290 */       o = newAction.getValue("AcceleratorKey");
/* 291 */       if (o instanceof KeyStroke && o != null) {
/*     */         
/* 293 */         KeyStroke k = (KeyStroke)o;
/* 294 */         registerKeyboardAction(newAction, k, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/action/ActionRadioButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */