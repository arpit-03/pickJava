/*     */ package org.jfree.ui.tabbedui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
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
/*     */ public abstract class AbstractTabbedUI
/*     */   extends JComponent
/*     */ {
/*     */   public static final String JMENUBAR_PROPERTY = "jMenuBar";
/*     */   public static final String GLOBAL_MENU_PROPERTY = "globalMenu";
/*     */   private ArrayList rootEditors;
/*     */   private JTabbedPane tabbedPane;
/*     */   private int selectedRootEditor;
/*     */   private JComponent currentToolbar;
/*     */   private JPanel toolbarContainer;
/*     */   private Action closeAction;
/*     */   private JMenuBar jMenuBar;
/*     */   private boolean globalMenu;
/*     */   
/*     */   protected class ExitAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public ExitAction() {
/*  89 */       putValue("Name", "Exit");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/*  98 */       AbstractTabbedUI.this.attempExit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class TabChangeHandler
/*     */     implements ChangeListener
/*     */   {
/*     */     private final JTabbedPane pane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TabChangeHandler(JTabbedPane pane) {
/* 117 */       this.pane = pane;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void stateChanged(ChangeEvent e) {
/* 126 */       AbstractTabbedUI.this.setSelectedEditor(this.pane.getSelectedIndex());
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
/*     */   private class TabEnableChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/* 148 */       if (!evt.getPropertyName().equals("enabled")) {
/* 149 */         Log.debug("PropertyName");
/*     */         return;
/*     */       } 
/* 152 */       if (!(evt.getSource() instanceof RootEditor)) {
/* 153 */         Log.debug("Source");
/*     */         return;
/*     */       } 
/* 156 */       RootEditor editor = (RootEditor)evt.getSource();
/* 157 */       AbstractTabbedUI.this.updateRootEditorEnabled(editor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTabbedUI() {
/* 182 */     this.selectedRootEditor = -1;
/*     */     
/* 184 */     this.toolbarContainer = new JPanel();
/* 185 */     this.toolbarContainer.setLayout(new BorderLayout());
/*     */     
/* 187 */     this.tabbedPane = new JTabbedPane(3);
/* 188 */     this.tabbedPane.addChangeListener(new TabChangeHandler(this.tabbedPane));
/*     */     
/* 190 */     this.rootEditors = new ArrayList();
/*     */     
/* 192 */     setLayout(new BorderLayout());
/* 193 */     add(this.toolbarContainer, "North");
/* 194 */     add(this.tabbedPane, "Center");
/*     */     
/* 196 */     this.closeAction = createCloseAction();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JTabbedPane getTabbedPane() {
/* 205 */     return this.tabbedPane;
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
/*     */   public boolean isGlobalMenu() {
/* 219 */     return this.globalMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalMenu(boolean globalMenu) {
/* 228 */     this.globalMenu = globalMenu;
/* 229 */     if (isGlobalMenu()) {
/* 230 */       setJMenuBar(updateGlobalMenubar());
/*     */     
/*     */     }
/* 233 */     else if (getRootEditorCount() > 0) {
/* 234 */       setJMenuBar(createEditorMenubar(getRootEditor(getSelectedEditor())));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuBar getJMenuBar() {
/* 245 */     return this.jMenuBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setJMenuBar(JMenuBar menuBar) {
/* 254 */     JMenuBar oldMenuBar = this.jMenuBar;
/* 255 */     this.jMenuBar = menuBar;
/* 256 */     firePropertyChange("jMenuBar", oldMenuBar, menuBar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Action createCloseAction() {
/* 265 */     return new ExitAction();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getCloseAction() {
/* 274 */     return this.closeAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract JMenu[] getPrefixMenus();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract JMenu[] getPostfixMenus();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMenus(JMenuBar menuBar, JMenu[] customMenus) {
/* 298 */     for (int i = 0; i < customMenus.length; i++) {
/* 299 */       menuBar.add(customMenus[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JMenuBar updateGlobalMenubar() {
/* 308 */     JMenuBar menuBar = getJMenuBar();
/* 309 */     if (menuBar == null) {
/* 310 */       menuBar = new JMenuBar();
/*     */     } else {
/*     */       
/* 313 */       menuBar.removeAll();
/*     */     } 
/*     */     
/* 316 */     addMenus(menuBar, getPrefixMenus());
/* 317 */     for (int i = 0; i < this.rootEditors.size(); i++) {
/*     */       
/* 319 */       RootEditor editor = this.rootEditors.get(i);
/* 320 */       addMenus(menuBar, editor.getMenus());
/*     */     } 
/* 322 */     addMenus(menuBar, getPostfixMenus());
/* 323 */     return menuBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JMenuBar createEditorMenubar(RootEditor root) {
/* 334 */     JMenuBar menuBar = getJMenuBar();
/* 335 */     if (menuBar == null) {
/* 336 */       menuBar = new JMenuBar();
/*     */     } else {
/*     */       
/* 339 */       menuBar.removeAll();
/*     */     } 
/*     */     
/* 342 */     addMenus(menuBar, getPrefixMenus());
/* 343 */     if (isGlobalMenu()) {
/*     */       
/* 345 */       for (int i = 0; i < this.rootEditors.size(); i++)
/*     */       {
/* 347 */         RootEditor editor = this.rootEditors.get(i);
/* 348 */         addMenus(menuBar, editor.getMenus());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 353 */       addMenus(menuBar, root.getMenus());
/*     */     } 
/* 355 */     addMenus(menuBar, getPostfixMenus());
/* 356 */     return menuBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRootEditor(RootEditor rootPanel) {
/* 365 */     this.rootEditors.add(rootPanel);
/* 366 */     this.tabbedPane.add(rootPanel.getEditorName(), rootPanel.getMainPanel());
/* 367 */     rootPanel.addPropertyChangeListener("enabled", new TabEnableChangeListener());
/* 368 */     updateRootEditorEnabled(rootPanel);
/* 369 */     if (getRootEditorCount() == 1) {
/* 370 */       setSelectedEditor(0);
/*     */     }
/* 372 */     else if (isGlobalMenu()) {
/* 373 */       setJMenuBar(updateGlobalMenubar());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRootEditorCount() {
/* 383 */     return this.rootEditors.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RootEditor getRootEditor(int pos) {
/* 394 */     return this.rootEditors.get(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectedEditor() {
/* 403 */     return this.selectedRootEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedEditor(int selectedEditor) {
/* 412 */     int oldEditor = this.selectedRootEditor;
/* 413 */     if (oldEditor == selectedEditor) {
/*     */       return;
/*     */     }
/*     */     
/* 417 */     this.selectedRootEditor = selectedEditor;
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/* 422 */     for (i = 0; i < this.rootEditors.size(); i++) {
/* 423 */       boolean shouldBeActive = (i == selectedEditor);
/*     */       
/* 425 */       RootEditor container = this.rootEditors.get(i);
/* 426 */       if (container.isActive() && !shouldBeActive) {
/* 427 */         container.setActive(false);
/*     */       }
/*     */     } 
/*     */     
/* 431 */     if (this.currentToolbar != null) {
/* 432 */       closeToolbar();
/* 433 */       this.toolbarContainer.removeAll();
/* 434 */       this.currentToolbar = null;
/*     */     } 
/*     */     
/* 437 */     for (i = 0; i < this.rootEditors.size(); i++) {
/* 438 */       boolean shouldBeActive = (i == selectedEditor);
/*     */       
/* 440 */       RootEditor container = this.rootEditors.get(i);
/* 441 */       if (!container.isActive() && shouldBeActive == true) {
/* 442 */         container.setActive(true);
/* 443 */         setJMenuBar(createEditorMenubar(container));
/* 444 */         this.currentToolbar = container.getToolbar();
/* 445 */         if (this.currentToolbar != null) {
/* 446 */           this.toolbarContainer
/* 447 */             .add(this.currentToolbar, "Center");
/* 448 */           this.toolbarContainer.setVisible(true);
/* 449 */           this.currentToolbar.setVisible(true);
/*     */         } else {
/*     */           
/* 452 */           this.toolbarContainer.setVisible(false);
/*     */         } 
/*     */         
/* 455 */         getJMenuBar().repaint();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeToolbar() {
/* 464 */     if (this.currentToolbar != null) {
/* 465 */       if (this.currentToolbar.getParent() != this.toolbarContainer) {
/*     */ 
/*     */         
/* 468 */         Window w = SwingUtilities.windowForComponent(this.currentToolbar);
/* 469 */         if (w != null) {
/* 470 */           w.setVisible(false);
/* 471 */           w.dispose();
/*     */         } 
/*     */       } 
/* 474 */       this.currentToolbar.setVisible(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void attempExit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateRootEditorEnabled(RootEditor editor) {
/* 490 */     boolean enabled = editor.isEnabled();
/* 491 */     for (int i = 0; i < this.tabbedPane.getTabCount(); i++) {
/* 492 */       Component tab = this.tabbedPane.getComponentAt(i);
/* 493 */       if (tab == editor.getMainPanel()) {
/* 494 */         this.tabbedPane.setEnabledAt(i, enabled);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/tabbedui/AbstractTabbedUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */