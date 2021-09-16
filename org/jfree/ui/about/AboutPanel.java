/*     */ package org.jfree.ui.about;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Image;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.ui.RefineryUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AboutPanel
/*     */   extends JPanel
/*     */ {
/*     */   public AboutPanel(String application, String version, String copyright, String info) {
/*  79 */     this(application, version, copyright, info, (Image)null);
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
/*     */   public AboutPanel(String application, String version, String copyright, String info, Image logo) {
/*  98 */     setLayout(new BorderLayout());
/*     */     
/* 100 */     JPanel textPanel = new JPanel(new GridLayout(4, 1, 0, 4));
/*     */     
/* 102 */     JPanel appPanel = new JPanel();
/* 103 */     Font f1 = new Font("Dialog", 1, 14);
/* 104 */     JLabel appLabel = RefineryUtilities.createJLabel(application, f1, Color.black);
/* 105 */     appLabel.setHorizontalTextPosition(0);
/* 106 */     appPanel.add(appLabel);
/*     */     
/* 108 */     JPanel verPanel = new JPanel();
/* 109 */     Font f2 = new Font("Dialog", 0, 12);
/* 110 */     JLabel verLabel = RefineryUtilities.createJLabel(version, f2, Color.black);
/* 111 */     verLabel.setHorizontalTextPosition(0);
/* 112 */     verPanel.add(verLabel);
/*     */     
/* 114 */     JPanel copyrightPanel = new JPanel();
/* 115 */     JLabel copyrightLabel = RefineryUtilities.createJLabel(copyright, f2, Color.black);
/* 116 */     copyrightLabel.setHorizontalTextPosition(0);
/* 117 */     copyrightPanel.add(copyrightLabel);
/*     */     
/* 119 */     JPanel infoPanel = new JPanel();
/* 120 */     JLabel infoLabel = RefineryUtilities.createJLabel(info, f2, Color.black);
/* 121 */     infoLabel.setHorizontalTextPosition(0);
/* 122 */     infoPanel.add(infoLabel);
/*     */     
/* 124 */     textPanel.add(appPanel);
/* 125 */     textPanel.add(verPanel);
/* 126 */     textPanel.add(copyrightPanel);
/* 127 */     textPanel.add(infoPanel);
/*     */     
/* 129 */     add(textPanel);
/*     */     
/* 131 */     if (logo != null) {
/* 132 */       JPanel imagePanel = new JPanel(new BorderLayout());
/* 133 */       imagePanel.add(new JLabel(new ImageIcon(logo)));
/* 134 */       imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
/* 135 */       JPanel imageContainer = new JPanel(new BorderLayout());
/* 136 */       imageContainer.add(imagePanel, "North");
/* 137 */       add(imageContainer, "West");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/about/AboutPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */