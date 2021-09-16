/*     */ package org.jfree.ui.about;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.util.ResourceBundleWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemPropertiesFrame
/*     */   extends JFrame
/*     */   implements ActionListener
/*     */ {
/*     */   private static final String COPY_COMMAND = "COPY";
/*     */   private static final String CLOSE_COMMAND = "CLOSE";
/*     */   private SystemPropertiesPanel panel;
/*     */   
/*     */   public SystemPropertiesFrame(boolean menu) {
/*  99 */     String baseName = "org.jfree.ui.about.resources.AboutResources";
/* 100 */     ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
/*     */ 
/*     */     
/* 103 */     String title = resources.getString("system-frame.title");
/* 104 */     setTitle(title);
/*     */     
/* 106 */     setDefaultCloseOperation(2);
/*     */     
/* 108 */     if (menu) {
/* 109 */       setJMenuBar(createMenuBar(resources));
/*     */     }
/*     */     
/* 112 */     JPanel content = new JPanel(new BorderLayout());
/* 113 */     this.panel = new SystemPropertiesPanel();
/* 114 */     this.panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*     */     
/* 116 */     content.add(this.panel, "Center");
/*     */     
/* 118 */     JPanel buttonPanel = new JPanel(new BorderLayout());
/* 119 */     buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
/*     */     
/* 121 */     String label = resources.getString("system-frame.button.close");
/* 122 */     Character mnemonic = (Character)resources.getObject("system-frame.button.close.mnemonic");
/*     */     
/* 124 */     JButton closeButton = new JButton(label);
/* 125 */     closeButton.setMnemonic(mnemonic.charValue());
/*     */     
/* 127 */     closeButton.setActionCommand("CLOSE");
/* 128 */     closeButton.addActionListener(this);
/*     */     
/* 130 */     buttonPanel.add(closeButton, "East");
/* 131 */     content.add(buttonPanel, "South");
/*     */     
/* 133 */     setContentPane(content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 144 */     String command = e.getActionCommand();
/* 145 */     if (command.equals("CLOSE")) {
/* 146 */       dispose();
/*     */     }
/* 148 */     else if (command.equals("COPY")) {
/* 149 */       this.panel.copySystemPropertiesToClipboard();
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
/*     */   private JMenuBar createMenuBar(ResourceBundle resources) {
/* 164 */     JMenuBar menuBar = new JMenuBar();
/*     */     
/* 166 */     String label = resources.getString("system-frame.menu.file");
/* 167 */     Character mnemonic = (Character)resources.getObject("system-frame.menu.file.mnemonic");
/*     */     
/* 169 */     JMenu fileMenu = new JMenu(label, true);
/* 170 */     fileMenu.setMnemonic(mnemonic.charValue());
/*     */     
/* 172 */     label = resources.getString("system-frame.menu.file.close");
/* 173 */     mnemonic = (Character)resources.getObject("system-frame.menu.file.close.mnemonic");
/*     */     
/* 175 */     JMenuItem closeItem = new JMenuItem(label, mnemonic.charValue());
/* 176 */     closeItem.setActionCommand("CLOSE");
/*     */     
/* 178 */     closeItem.addActionListener(this);
/* 179 */     fileMenu.add(closeItem);
/*     */     
/* 181 */     label = resources.getString("system-frame.menu.edit");
/* 182 */     mnemonic = (Character)resources.getObject("system-frame.menu.edit.mnemonic");
/*     */     
/* 184 */     JMenu editMenu = new JMenu(label);
/* 185 */     editMenu.setMnemonic(mnemonic.charValue());
/*     */     
/* 187 */     label = resources.getString("system-frame.menu.edit.copy");
/* 188 */     mnemonic = (Character)resources.getObject("system-frame.menu.edit.copy.mnemonic");
/*     */     
/* 190 */     JMenuItem copyItem = new JMenuItem(label, mnemonic.charValue());
/* 191 */     copyItem.setActionCommand("COPY");
/* 192 */     copyItem.addActionListener(this);
/* 193 */     editMenu.add(copyItem);
/*     */     
/* 195 */     menuBar.add(fileMenu);
/* 196 */     menuBar.add(editMenu);
/* 197 */     return menuBar;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/about/SystemPropertiesFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */