/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WizardDialog
/*     */   extends JDialog
/*     */   implements ActionListener
/*     */ {
/*     */   private Object result;
/*     */   private int step;
/*     */   private WizardPanel currentPanel;
/*     */   private List panels;
/*     */   private JButton previousButton;
/*     */   private JButton nextButton;
/*     */   private JButton finishButton;
/*     */   private JButton helpButton;
/*     */   
/*     */   public WizardDialog(JDialog owner, boolean modal, String title, WizardPanel firstPanel) {
/* 111 */     super(owner, title + " : step 1", modal);
/* 112 */     this.result = null;
/* 113 */     this.currentPanel = firstPanel;
/* 114 */     this.step = 0;
/* 115 */     this.panels = new ArrayList();
/* 116 */     this.panels.add(firstPanel);
/* 117 */     setContentPane(createContent());
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
/*     */   public WizardDialog(JFrame owner, boolean modal, String title, WizardPanel firstPanel) {
/* 132 */     super(owner, title + " : step 1", modal);
/* 133 */     this.result = null;
/* 134 */     this.currentPanel = firstPanel;
/* 135 */     this.step = 0;
/* 136 */     this.panels = new ArrayList();
/* 137 */     this.panels.add(firstPanel);
/* 138 */     setContentPane(createContent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getResult() {
/* 147 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStepCount() {
/* 158 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDoPreviousPanel() {
/* 167 */     return (this.step > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDoNextPanel() {
/* 176 */     return this.currentPanel.hasNextPanel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFinish() {
/* 186 */     return this.currentPanel.canFinish();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WizardPanel getWizardPanel(int step) {
/* 197 */     if (step < this.panels.size()) {
/* 198 */       return this.panels.get(step);
/*     */     }
/*     */     
/* 201 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 211 */     String command = event.getActionCommand();
/* 212 */     if (command.equals("nextButton")) {
/* 213 */       next();
/*     */     }
/* 215 */     else if (command.equals("previousButton")) {
/* 216 */       previous();
/*     */     }
/* 218 */     else if (command.equals("finishButton")) {
/* 219 */       finish();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void previous() {
/* 227 */     if (this.step > 0) {
/* 228 */       WizardPanel previousPanel = getWizardPanel(this.step - 1);
/*     */       
/* 230 */       previousPanel.returnFromLaterStep();
/* 231 */       Container content = getContentPane();
/* 232 */       content.remove(this.currentPanel);
/* 233 */       content.add(previousPanel);
/* 234 */       this.step--;
/* 235 */       this.currentPanel = previousPanel;
/* 236 */       setTitle("Step " + (this.step + 1));
/* 237 */       enableButtons();
/* 238 */       pack();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/* 247 */     WizardPanel nextPanel = getWizardPanel(this.step + 1);
/* 248 */     if (nextPanel != null) {
/* 249 */       if (!this.currentPanel.canRedisplayNextPanel()) {
/* 250 */         nextPanel = this.currentPanel.getNextPanel();
/*     */       }
/*     */     } else {
/*     */       
/* 254 */       nextPanel = this.currentPanel.getNextPanel();
/*     */     } 
/*     */     
/* 257 */     this.step++;
/* 258 */     if (this.step < this.panels.size()) {
/* 259 */       this.panels.set(this.step, nextPanel);
/*     */     } else {
/*     */       
/* 262 */       this.panels.add(nextPanel);
/*     */     } 
/*     */     
/* 265 */     Container content = getContentPane();
/* 266 */     content.remove(this.currentPanel);
/* 267 */     content.add(nextPanel);
/*     */     
/* 269 */     this.currentPanel = nextPanel;
/* 270 */     setTitle("Step " + (this.step + 1));
/* 271 */     enableButtons();
/* 272 */     pack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() {
/* 280 */     this.result = this.currentPanel.getResult();
/* 281 */     setVisible(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enableButtons() {
/* 289 */     this.previousButton.setEnabled((this.step > 0));
/* 290 */     this.nextButton.setEnabled(canDoNextPanel());
/* 291 */     this.finishButton.setEnabled(canFinish());
/* 292 */     this.helpButton.setEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JPanel createContent() {
/* 311 */     JPanel content = new JPanel(new BorderLayout());
/* 312 */     content.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 313 */     content.add(this.panels.get(0));
/* 314 */     L1R3ButtonPanel buttons = new L1R3ButtonPanel("Help", "Previous", "Next", "Finish");
/*     */     
/* 316 */     this.helpButton = buttons.getLeftButton();
/* 317 */     this.helpButton.setEnabled(false);
/*     */     
/* 319 */     this.previousButton = buttons.getRightButton1();
/* 320 */     this.previousButton.setActionCommand("previousButton");
/* 321 */     this.previousButton.addActionListener(this);
/* 322 */     this.previousButton.setEnabled(false);
/*     */     
/* 324 */     this.nextButton = buttons.getRightButton2();
/* 325 */     this.nextButton.setActionCommand("nextButton");
/* 326 */     this.nextButton.addActionListener(this);
/* 327 */     this.nextButton.setEnabled(true);
/*     */     
/* 329 */     this.finishButton = buttons.getRightButton3();
/* 330 */     this.finishButton.setActionCommand("finishButton");
/* 331 */     this.finishButton.addActionListener(this);
/* 332 */     this.finishButton.setEnabled(false);
/*     */     
/* 334 */     buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
/* 335 */     content.add(buttons, "South");
/*     */     
/* 337 */     return content;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/WizardDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */