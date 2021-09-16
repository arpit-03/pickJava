/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.axis.ColorBar;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.plot.ColorPalette;
/*     */ import org.jfree.chart.plot.GreyPalette;
/*     */ import org.jfree.chart.plot.RainbowPalette;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultColorBarEditor
/*     */   extends DefaultNumberAxisEditor
/*     */ {
/*     */   private JCheckBox invertPaletteCheckBox;
/*     */   private boolean invertPalette = false;
/*     */   private JCheckBox stepPaletteCheckBox;
/*     */   private boolean stepPalette = false;
/*     */   private PaletteSample currentPalette;
/*     */   private PaletteSample[] availablePaletteSamples;
/*  98 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultColorBarEditor(ColorBar colorBar) {
/* 107 */     super((NumberAxis)colorBar.getAxis());
/* 108 */     this.invertPalette = colorBar.getColorPalette().isInverse();
/* 109 */     this.stepPalette = colorBar.getColorPalette().isStepped();
/* 110 */     this.currentPalette = new PaletteSample(colorBar.getColorPalette());
/* 111 */     this.availablePaletteSamples = new PaletteSample[2];
/* 112 */     this.availablePaletteSamples[0] = new PaletteSample((ColorPalette)new RainbowPalette());
/*     */     
/* 114 */     this.availablePaletteSamples[1] = new PaletteSample((ColorPalette)new GreyPalette());
/*     */ 
/*     */     
/* 117 */     JTabbedPane other = getOtherTabs();
/*     */     
/* 119 */     JPanel palettePanel = new JPanel((LayoutManager)new LCBLayout(4));
/* 120 */     palettePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*     */     
/* 122 */     palettePanel.add(new JPanel());
/* 123 */     this
/* 124 */       .invertPaletteCheckBox = new JCheckBox(localizationResources.getString("Invert_Palette"), this.invertPalette);
/*     */ 
/*     */     
/* 127 */     this.invertPaletteCheckBox.setActionCommand("invertPalette");
/* 128 */     this.invertPaletteCheckBox.addActionListener(this);
/* 129 */     palettePanel.add(this.invertPaletteCheckBox);
/* 130 */     palettePanel.add(new JPanel());
/*     */     
/* 132 */     palettePanel.add(new JPanel());
/* 133 */     this
/* 134 */       .stepPaletteCheckBox = new JCheckBox(localizationResources.getString("Step_Palette"), this.stepPalette);
/*     */ 
/*     */     
/* 137 */     this.stepPaletteCheckBox.setActionCommand("stepPalette");
/* 138 */     this.stepPaletteCheckBox.addActionListener(this);
/* 139 */     palettePanel.add(this.stepPaletteCheckBox);
/* 140 */     palettePanel.add(new JPanel());
/*     */     
/* 142 */     palettePanel.add(new JLabel(localizationResources
/* 143 */           .getString("Palette")));
/*     */ 
/*     */     
/* 146 */     JButton button = new JButton(localizationResources.getString("Set_palette..."));
/* 147 */     button.setActionCommand("PaletteChoice");
/* 148 */     button.addActionListener(this);
/* 149 */     palettePanel.add(this.currentPalette);
/* 150 */     palettePanel.add(button);
/*     */     
/* 152 */     other.add(localizationResources.getString("Palette"), palettePanel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 163 */     String command = event.getActionCommand();
/* 164 */     if (command.equals("PaletteChoice")) {
/* 165 */       attemptPaletteSelection();
/*     */     }
/* 167 */     else if (command.equals("invertPalette")) {
/* 168 */       this.invertPalette = this.invertPaletteCheckBox.isSelected();
/*     */     }
/* 170 */     else if (command.equals("stepPalette")) {
/* 171 */       this.stepPalette = this.stepPaletteCheckBox.isSelected();
/*     */     } else {
/*     */       
/* 174 */       super.actionPerformed(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptPaletteSelection() {
/* 182 */     PaletteChooserPanel panel = new PaletteChooserPanel(null, this.availablePaletteSamples);
/*     */     
/* 184 */     int result = JOptionPane.showConfirmDialog(this, panel, localizationResources
/* 185 */         .getString("Palette_Selection"), 2, -1);
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (result == 0) {
/* 190 */       double zmin = this.currentPalette.getPalette().getMinZ();
/* 191 */       double zmax = this.currentPalette.getPalette().getMaxZ();
/* 192 */       this.currentPalette.setPalette(panel.getSelectedPalette());
/* 193 */       this.currentPalette.getPalette().setMinZ(zmin);
/* 194 */       this.currentPalette.getPalette().setMaxZ(zmax);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAxisProperties(ColorBar colorBar) {
/* 205 */     setAxisProperties((Axis)colorBar.getAxis());
/* 206 */     colorBar.setColorPalette(this.currentPalette.getPalette());
/* 207 */     colorBar.getColorPalette().setInverse(this.invertPalette);
/* 208 */     colorBar.getColorPalette().setStepped(this.stepPalette);
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
/*     */   public static DefaultColorBarEditor getInstance(ColorBar colorBar) {
/* 221 */     if (colorBar != null) {
/* 222 */       return new DefaultColorBarEditor(colorBar);
/*     */     }
/*     */     
/* 225 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultColorBarEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */