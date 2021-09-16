/*     */ package org.jfree.ui.action;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionButton
/*     */   extends JButton
/*     */ {
/*     */   private Action action;
/*     */   private ActionEnablePropertyChangeHandler propertyChangeHandler;
/*     */   
/*     */   private class ActionEnablePropertyChangeHandler
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent event) {
/*     */       try {
/*  94 */         if (event.getPropertyName().equals("enabled")) {
/*  95 */           ActionButton.this.setEnabled(ActionButton.this.getAction().isEnabled());
/*     */         }
/*  97 */         else if (event.getPropertyName().equals("SmallIcon")) {
/*  98 */           ActionButton.this.setIcon((Icon)ActionButton.this.getAction().getValue("SmallIcon"));
/*     */         }
/* 100 */         else if (event.getPropertyName().equals("Name")) {
/* 101 */           ActionButton.this.setText((String)ActionButton.this.getAction()
/* 102 */               .getValue("Name"));
/*     */         }
/* 104 */         else if (event.getPropertyName().equals("ShortDescription")) {
/* 105 */           ActionButton.this.setToolTipText((String)ActionButton.this
/* 106 */               .getAction().getValue("ShortDescription"));
/*     */         } 
/*     */         
/* 109 */         Action ac = ActionButton.this.getAction();
/* 110 */         if (event.getPropertyName().equals("AcceleratorKey")) {
/* 111 */           KeyStroke oldVal = (KeyStroke)event.getOldValue();
/* 112 */           if (oldVal != null) {
/* 113 */             ActionButton.this.unregisterKeyboardAction(oldVal);
/*     */           }
/* 115 */           Object o = ac.getValue("AcceleratorKey");
/* 116 */           if (o instanceof KeyStroke) {
/* 117 */             KeyStroke k = (KeyStroke)o;
/* 118 */             ActionButton.this.registerKeyboardAction(ac, k, 2);
/*     */           }
/*     */         
/* 121 */         } else if (event.getPropertyName().equals("MnemonicKey")) {
/* 122 */           Object o = ac.getValue("MnemonicKey");
/* 123 */           if (o != null) {
/* 124 */             if (o instanceof Character) {
/* 125 */               Character c = (Character)o;
/* 126 */               ActionButton.this.setMnemonic(c.charValue());
/*     */             }
/* 128 */             else if (o instanceof Integer) {
/* 129 */               Integer c = (Integer)o;
/* 130 */               ActionButton.this.setMnemonic(c.intValue());
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/* 135 */       } catch (Exception e) {
/* 136 */         Log.warn("Error on PropertyChange in ActionButton: ", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionButton() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionButton(String text) {
/* 154 */     super(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionButton(String text, Icon icon) {
/* 164 */     super(text, icon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionButton(Icon icon) {
/* 174 */     super(icon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionButton(Action action) {
/* 183 */     setAction(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/* 192 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ActionEnablePropertyChangeHandler getPropertyChangeHandler() {
/* 203 */     if (this.propertyChangeHandler == null) {
/* 204 */       this.propertyChangeHandler = new ActionEnablePropertyChangeHandler();
/*     */     }
/* 206 */     return this.propertyChangeHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean b) {
/* 216 */     super.setEnabled(b);
/* 217 */     if (getAction() != null) {
/* 218 */       getAction().setEnabled(b);
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
/*     */   public void setAction(Action newAction) {
/* 236 */     Action oldAction = getAction();
/* 237 */     if (oldAction != null) {
/* 238 */       removeActionListener(oldAction);
/* 239 */       oldAction.removePropertyChangeListener(getPropertyChangeHandler());
/*     */       
/* 241 */       Object o = oldAction.getValue("AcceleratorKey");
/* 242 */       if (o instanceof KeyStroke) {
/* 243 */         KeyStroke k = (KeyStroke)o;
/* 244 */         unregisterKeyboardAction(k);
/*     */       } 
/*     */     } 
/* 247 */     this.action = newAction;
/* 248 */     if (this.action != null) {
/* 249 */       addActionListener(newAction);
/* 250 */       newAction.addPropertyChangeListener(getPropertyChangeHandler());
/*     */       
/* 252 */       setText((String)newAction.getValue("Name"));
/* 253 */       setToolTipText((String)newAction.getValue("ShortDescription"));
/* 254 */       setIcon((Icon)newAction.getValue("SmallIcon"));
/* 255 */       setEnabled(this.action.isEnabled());
/*     */       
/* 257 */       Object o = newAction.getValue("MnemonicKey");
/* 258 */       if (o != null) {
/* 259 */         if (o instanceof Character) {
/* 260 */           Character c = (Character)o;
/* 261 */           setMnemonic(c.charValue());
/*     */         }
/* 263 */         else if (o instanceof Integer) {
/* 264 */           Integer c = (Integer)o;
/* 265 */           setMnemonic(c.intValue());
/*     */         } 
/*     */       }
/* 268 */       o = newAction.getValue("AcceleratorKey");
/* 269 */       if (o instanceof KeyStroke) {
/* 270 */         KeyStroke k = (KeyStroke)o;
/* 271 */         registerKeyboardAction(newAction, k, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/action/ActionButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */