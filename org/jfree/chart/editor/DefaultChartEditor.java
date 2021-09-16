/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PolarPlot;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.title.Title;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ import org.jfree.ui.PaintSample;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultChartEditor
/*     */   extends JPanel
/*     */   implements ActionListener, ChartEditor
/*     */ {
/*     */   private DefaultTitleEditor titleEditor;
/*     */   private DefaultPlotEditor plotEditor;
/*     */   private JCheckBox antialias;
/*     */   private PaintSample background;
/*  93 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultChartEditor(JFreeChart chart) {
/* 103 */     setLayout(new BorderLayout());
/*     */     
/* 105 */     JPanel other = new JPanel(new BorderLayout());
/* 106 */     other.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
/*     */     
/* 108 */     JPanel general = new JPanel(new BorderLayout());
/* 109 */     general.setBorder(BorderFactory.createTitledBorder(
/* 110 */           BorderFactory.createEtchedBorder(), localizationResources
/* 111 */           .getString("General")));
/*     */     
/* 113 */     JPanel interior = new JPanel((LayoutManager)new LCBLayout(6));
/* 114 */     interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
/*     */     
/* 116 */     this.antialias = new JCheckBox(localizationResources.getString("Draw_anti-aliased"));
/*     */     
/* 118 */     this.antialias.setSelected(chart.getAntiAlias());
/* 119 */     interior.add(this.antialias);
/* 120 */     interior.add(new JLabel(""));
/* 121 */     interior.add(new JLabel(""));
/* 122 */     interior.add(new JLabel(localizationResources.getString("Background_paint")));
/*     */     
/* 124 */     this.background = new PaintSample(chart.getBackgroundPaint());
/* 125 */     interior.add((Component)this.background);
/* 126 */     JButton button = new JButton(localizationResources.getString("Select..."));
/*     */     
/* 128 */     button.setActionCommand("BackgroundPaint");
/* 129 */     button.addActionListener(this);
/* 130 */     interior.add(button);
/*     */     
/* 132 */     interior.add(new JLabel(localizationResources.getString("Series_Paint")));
/*     */     
/* 134 */     JTextField info = new JTextField(localizationResources.getString("No_editor_implemented"));
/*     */     
/* 136 */     info.setEnabled(false);
/* 137 */     interior.add(info);
/* 138 */     button = new JButton(localizationResources.getString("Edit..."));
/* 139 */     button.setEnabled(false);
/* 140 */     interior.add(button);
/*     */     
/* 142 */     interior.add(new JLabel(localizationResources.getString("Series_Stroke")));
/*     */     
/* 144 */     info = new JTextField(localizationResources.getString("No_editor_implemented"));
/*     */     
/* 146 */     info.setEnabled(false);
/* 147 */     interior.add(info);
/* 148 */     button = new JButton(localizationResources.getString("Edit..."));
/* 149 */     button.setEnabled(false);
/* 150 */     interior.add(button);
/*     */     
/* 152 */     interior.add(new JLabel(localizationResources.getString("Series_Outline_Paint")));
/*     */     
/* 154 */     info = new JTextField(localizationResources.getString("No_editor_implemented"));
/*     */     
/* 156 */     info.setEnabled(false);
/* 157 */     interior.add(info);
/* 158 */     button = new JButton(localizationResources.getString("Edit..."));
/* 159 */     button.setEnabled(false);
/* 160 */     interior.add(button);
/*     */     
/* 162 */     interior.add(new JLabel(localizationResources.getString("Series_Outline_Stroke")));
/*     */     
/* 164 */     info = new JTextField(localizationResources.getString("No_editor_implemented"));
/*     */     
/* 166 */     info.setEnabled(false);
/* 167 */     interior.add(info);
/* 168 */     button = new JButton(localizationResources.getString("Edit..."));
/* 169 */     button.setEnabled(false);
/* 170 */     interior.add(button);
/*     */     
/* 172 */     general.add(interior, "North");
/* 173 */     other.add(general, "North");
/*     */     
/* 175 */     JPanel parts = new JPanel(new BorderLayout());
/*     */     
/* 177 */     TextTitle textTitle = chart.getTitle();
/* 178 */     Plot plot = chart.getPlot();
/*     */     
/* 180 */     JTabbedPane tabs = new JTabbedPane();
/*     */     
/* 182 */     this.titleEditor = new DefaultTitleEditor((Title)textTitle);
/* 183 */     this.titleEditor.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 184 */     tabs.addTab(localizationResources.getString("Title"), this.titleEditor);
/*     */     
/* 186 */     if (plot instanceof PolarPlot) {
/* 187 */       this.plotEditor = new DefaultPolarPlotEditor((PolarPlot)plot);
/*     */     } else {
/*     */       
/* 190 */       this.plotEditor = new DefaultPlotEditor(plot);
/*     */     } 
/* 192 */     this.plotEditor.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 193 */     tabs.addTab(localizationResources.getString("Plot"), this.plotEditor);
/*     */     
/* 195 */     tabs.add(localizationResources.getString("Other"), other);
/* 196 */     parts.add(tabs, "North");
/* 197 */     add(parts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultTitleEditor getTitleEditor() {
/* 206 */     return this.titleEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPlotEditor getPlotEditor() {
/* 215 */     return this.plotEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAntiAlias() {
/* 224 */     return this.antialias.isSelected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 233 */     return this.background.getPaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 243 */     String command = event.getActionCommand();
/* 244 */     if (command.equals("BackgroundPaint")) {
/* 245 */       attemptModifyBackgroundPaint();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptModifyBackgroundPaint() {
/* 256 */     Color c = JColorChooser.showDialog(this, localizationResources.getString("Background_Color"), Color.blue);
/*     */     
/* 258 */     if (c != null) {
/* 259 */       this.background.setPaint(c);
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
/*     */   public void updateChart(JFreeChart chart) {
/* 271 */     this.titleEditor.setTitleProperties(chart);
/* 272 */     this.plotEditor.updatePlotProperties(chart.getPlot());
/* 273 */     chart.setAntiAlias(getAntiAlias());
/* 274 */     chart.setBackgroundPaint(getBackgroundPaint());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultChartEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */