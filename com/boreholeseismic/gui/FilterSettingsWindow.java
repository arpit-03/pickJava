/*     */ package com.boreholeseismic.gui;
/*     */ 
/*     */ import com.boreholeseismic.dsp.FilterSettings;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.text.NumberFormat;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterSettingsWindow
/*     */ {
/*     */   private JPanel contentPane;
/*     */   
/*     */   public FilterSettingsWindow(final FilterSettings filterSettings) {
/*  31 */     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
/*  32 */     final JFrame J = new JFrame("Trace Filter Settings");
/*  33 */     J.setDefaultCloseOperation(2);
/*  34 */     J.setSize(new Dimension(280, 540));
/*  35 */     J.setLocation(dim.width / 2 - J.getWidth() / 2, dim.height / 2 - J.getHeight() / 2);
/*     */     
/*  37 */     this.contentPane = new JPanel();
/*  38 */     this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
/*  39 */     J.setContentPane(this.contentPane);
/*  40 */     this.contentPane.setLayout(new GridBagLayout());
/*     */     
/*  42 */     JPanel BPControlsPane = new JPanel();
/*  43 */     BPControlsPane.setBorder(
/*  44 */         BorderFactory.createCompoundBorder(
/*  45 */           BorderFactory.createTitledBorder("Bandpass Settings"), 
/*  46 */           BorderFactory.createEmptyBorder(10, 10, 10, 10)));
/*  47 */     GridBagConstraints c = new GridBagConstraints();
/*  48 */     c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 5;
/*     */     
/*  50 */     this.contentPane.add(BPControlsPane, c);
/*     */     
/*  52 */     BPControlsPane.setLayout(new GridLayout(4, 2));
/*  53 */     final JCheckBox BPFlag = new JCheckBox();
/*  54 */     BPFlag.setSelected(filterSettings.BPFlag);
/*  55 */     BPFlag.setHorizontalAlignment(0);
/*  56 */     JLabel textFieldBP = new JLabel("Bandpass Filter");
/*  57 */     textFieldBP.setLabelFor(BPFlag);
/*  58 */     textFieldBP.setHorizontalAlignment(0);
/*  59 */     BPControlsPane.add(textFieldBP);
/*  60 */     BPControlsPane.add(BPFlag);
/*     */     
/*  62 */     NumberFormat fieldFormat = NumberFormat.getIntegerInstance();
/*     */     
/*  64 */     final JFormattedTextField BPLowInp = new JFormattedTextField(fieldFormat);
/*  65 */     BPLowInp.setHorizontalAlignment(0);
/*  66 */     BPLowInp.setValue(Integer.valueOf(filterSettings.BPLow));
/*  67 */     JLabel textFieldBPLow = new JLabel("Low Freq.");
/*  68 */     textFieldBPLow.setHorizontalAlignment(0);
/*  69 */     textFieldBPLow.setLabelFor(BPLowInp);
/*  70 */     BPControlsPane.add(textFieldBPLow);
/*  71 */     BPControlsPane.add(BPLowInp);
/*     */     
/*  73 */     final JFormattedTextField BPEdgeInp = new JFormattedTextField(fieldFormat);
/*  74 */     BPEdgeInp.setHorizontalAlignment(0);
/*  75 */     BPEdgeInp.setValue(Integer.valueOf(filterSettings.BPEdge));
/*  76 */     JLabel textFieldBPEdge = new JLabel("Edge Freq.");
/*  77 */     textFieldBPEdge.setHorizontalAlignment(0);
/*  78 */     textFieldBPEdge.setLabelFor(BPEdgeInp);
/*  79 */     BPControlsPane.add(textFieldBPEdge);
/*  80 */     BPControlsPane.add(BPEdgeInp);
/*     */     
/*  82 */     final JFormattedTextField BPHighInp = new JFormattedTextField(fieldFormat);
/*  83 */     BPHighInp.setHorizontalAlignment(0);
/*  84 */     BPHighInp.setValue(Integer.valueOf(filterSettings.BPHigh));
/*  85 */     JLabel textFieldBPHigh = new JLabel("High Freq.");
/*  86 */     textFieldBPHigh.setHorizontalAlignment(0);
/*  87 */     textFieldBPHigh.setLabelFor(BPHighInp);
/*  88 */     BPControlsPane.add(textFieldBPHigh);
/*  89 */     BPControlsPane.add(BPHighInp);
/*     */ 
/*     */     
/*  92 */     JPanel LPControlsPane = new JPanel();
/*  93 */     LPControlsPane.setBorder(
/*  94 */         BorderFactory.createCompoundBorder(
/*  95 */           BorderFactory.createTitledBorder("Polarisation Settings"), 
/*  96 */           BorderFactory.createEmptyBorder(10, 10, 10, 10)));
/*  97 */     c = new GridBagConstraints();
/*  98 */     c.gridx = 0; c.gridy = 7; c.gridwidth = 1; c.gridheight = 5;
/*     */     
/* 100 */     this.contentPane.add(LPControlsPane, c);
/*     */     
/* 102 */     LPControlsPane.setLayout(new GridLayout(4, 2));
/* 103 */     final JCheckBox LPFlag = new JCheckBox();
/* 104 */     LPFlag.setSelected(filterSettings.LPFlag);
/* 105 */     LPFlag.setHorizontalAlignment(0);
/* 106 */     JLabel textFieldLP = new JLabel("Polarization Filter");
/* 107 */     textFieldLP.setLabelFor(LPFlag);
/* 108 */     textFieldLP.setHorizontalAlignment(0);
/* 109 */     LPControlsPane.add(textFieldLP);
/* 110 */     LPControlsPane.add(LPFlag);
/*     */     
/* 112 */     fieldFormat = NumberFormat.getNumberInstance();
/*     */     
/* 114 */     final JFormattedTextField LPLowInp = new JFormattedTextField(fieldFormat);
/* 115 */     LPLowInp.setHorizontalAlignment(0);
/* 116 */     LPLowInp.setValue(Double.valueOf(filterSettings.LPWindow));
/* 117 */     JLabel textFieldLPLow = new JLabel("Window");
/* 118 */     textFieldLPLow.setHorizontalAlignment(0);
/* 119 */     textFieldLPLow.setLabelFor(LPLowInp);
/* 120 */     LPControlsPane.add(textFieldLPLow);
/* 121 */     LPControlsPane.add(LPLowInp);
/*     */     
/* 123 */     final JFormattedTextField LPEdgeInp = new JFormattedTextField(fieldFormat);
/* 124 */     LPEdgeInp.setHorizontalAlignment(0);
/* 125 */     LPEdgeInp.setValue(Double.valueOf(filterSettings.LPExp));
/* 126 */     JLabel textFieldLPEdge = new JLabel("Exponential");
/* 127 */     textFieldLPEdge.setHorizontalAlignment(0);
/* 128 */     textFieldLPEdge.setLabelFor(LPEdgeInp);
/* 129 */     LPControlsPane.add(textFieldLPEdge);
/* 130 */     LPControlsPane.add(LPEdgeInp);
/*     */     
/* 132 */     final JFormattedTextField LPHighInp = new JFormattedTextField(fieldFormat);
/* 133 */     LPHighInp.setHorizontalAlignment(0);
/* 134 */     LPHighInp.setValue(Double.valueOf(filterSettings.LPThresh));
/* 135 */     JLabel textFieldLPHigh = new JLabel("Threshold");
/* 136 */     textFieldLPHigh.setHorizontalAlignment(0);
/* 137 */     textFieldLPHigh.setLabelFor(LPHighInp);
/* 138 */     LPControlsPane.add(textFieldLPHigh);
/* 139 */     LPControlsPane.add(LPHighInp);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     JPanel AdvancedFilters = new JPanel();
/* 147 */     AdvancedFilters.setLayout(new GridLayout(2, 2));
/*     */     
/* 149 */     AdvancedFilters.setBorder(
/* 150 */         BorderFactory.createCompoundBorder(
/* 151 */           BorderFactory.createTitledBorder("Advanced Settings"), 
/* 152 */           BorderFactory.createEmptyBorder(5, 5, 5, 5)));
/*     */     
/* 154 */     final JCheckBox IntegrateFlag = new JCheckBox();
/* 155 */     JLabel textFieldInt = new JLabel("Integrate Traces");
/* 156 */     IntegrateFlag.setSelected(filterSettings.IntFlag);
/* 157 */     textFieldInt.setHorizontalAlignment(0);
/* 158 */     IntegrateFlag.setHorizontalAlignment(0);
/* 159 */     textFieldInt.setLabelFor(IntegrateFlag);
/* 160 */     AdvancedFilters.add(textFieldInt);
/* 161 */     AdvancedFilters.add(IntegrateFlag);
/*     */     
/* 163 */     final JCheckBox DetrendFlag = new JCheckBox();
/* 164 */     JLabel textFieldDetrend = new JLabel("Detrend Traces");
/* 165 */     DetrendFlag.setSelected(filterSettings.DetrendFlag);
/* 166 */     textFieldDetrend.setHorizontalAlignment(0);
/* 167 */     DetrendFlag.setHorizontalAlignment(0);
/* 168 */     textFieldDetrend.setLabelFor(DetrendFlag);
/* 169 */     AdvancedFilters.add(textFieldDetrend);
/* 170 */     AdvancedFilters.add(DetrendFlag);
/*     */     
/* 172 */     c = new GridBagConstraints();
/* 173 */     c.gridx = 0; c.gridy = 14; c.gridwidth = 1; c.gridheight = 5;
/*     */     
/* 175 */     this.contentPane.add(AdvancedFilters, c);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     JPanel WellInfo = new JPanel();
/* 181 */     WellInfo.setLayout(new GridLayout(3, 2));
/*     */     
/* 183 */     WellInfo.setBorder(
/* 184 */         BorderFactory.createCompoundBorder(
/* 185 */           BorderFactory.createTitledBorder("Well Info"), 
/* 186 */           BorderFactory.createEmptyBorder(5, 5, 5, 5)));
/*     */     
/* 188 */     final JFormattedTextField noOfWells = new JFormattedTextField(fieldFormat);
/* 189 */     JLabel textFieldNoOfWells = new JLabel("No of Wells");
/* 190 */     noOfWells.setText(filterSettings.noOfWellsString);
/* 191 */     noOfWells.setHorizontalAlignment(0);
/* 192 */     textFieldNoOfWells.setHorizontalAlignment(0);
/* 193 */     textFieldNoOfWells.setLabelFor(noOfWells);
/* 194 */     WellInfo.add(textFieldNoOfWells);
/* 195 */     WellInfo.add(noOfWells);
/*     */ 
/*     */     
/* 198 */     final JFormattedTextField noOfRecs = new JFormattedTextField();
/* 199 */     JLabel textFieldNoOfRecs = new JLabel("No of Recs");
/* 200 */     noOfRecs.setText(filterSettings.noOfRecsString);
/*     */     
/* 202 */     noOfRecs.setHorizontalAlignment(0);
/* 203 */     textFieldNoOfRecs.setHorizontalAlignment(0);
/* 204 */     textFieldNoOfRecs.setLabelFor(noOfRecs);
/* 205 */     WellInfo.add(textFieldNoOfRecs);
/* 206 */     WellInfo.add(noOfRecs);
/*     */ 
/*     */     
/* 209 */     final JFormattedTextField toolstringNames = new JFormattedTextField();
/* 210 */     JLabel textFieldToolstringNames = new JLabel("Toolstring Names");
/* 211 */     toolstringNames.setText(filterSettings.toolstringNames);
/*     */     
/* 213 */     toolstringNames.setHorizontalAlignment(0);
/* 214 */     textFieldToolstringNames.setHorizontalAlignment(0);
/* 215 */     textFieldToolstringNames.setLabelFor(toolstringNames);
/* 216 */     WellInfo.add(textFieldToolstringNames);
/* 217 */     WellInfo.add(toolstringNames);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     c = new GridBagConstraints();
/* 223 */     c.gridx = 0; c.gridy = 19; c.gridwidth = 1; c.gridheight = 5;
/* 224 */     this.contentPane.add(WellInfo, c);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     JPanel ButtonPanel = new JPanel();
/* 231 */     ButtonPanel.setLayout(new GridLayout(1, 2));
/*     */     
/* 233 */     JButton OkButton = new JButton("OK");
/* 234 */     ButtonPanel.add(OkButton);
/* 235 */     OkButton.addActionListener(new ActionListener()
/*     */         {
/*     */ 
/*     */           
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 241 */             if (Integer.parseInt(BPHighInp.getValue().toString()) <= Integer.parseInt(BPLowInp.getValue().toString())) {
/*     */               
/* 243 */               JOptionPane.showMessageDialog(new JFrame("Error!!"), "Check Bandpass Low, High values.");
/*     */               
/*     */               return;
/*     */             } 
/* 247 */             if ((((Integer.parseInt(BPEdgeInp.getValue().toString()) > Integer.parseInt(BPLowInp.getValue().toString())) ? 1 : 0) | (
/* 248 */               (Integer.parseInt(BPEdgeInp.getValue().toString()) > Integer.parseInt(BPHighInp.getValue().toString())) ? 1 : 0)) != 0) {
/*     */               
/* 250 */               JOptionPane.showMessageDialog(new JFrame("Error!!"), "Check Bandpass Edge.");
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 255 */             if ((((Double.parseDouble(LPHighInp.getValue().toString()) > 1.0D) ? 1 : 0) | ((Double.parseDouble(LPHighInp.getValue().toString()) < 0.0D) ? 1 : 0)) != 0) {
/*     */               
/* 257 */               JOptionPane.showMessageDialog(new JFrame("Error!!"), "Threshold values should be between 0 and 1.");
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 263 */             filterSettings.setFilterSettings(
/* 264 */                 BPFlag.isSelected(), Integer.parseInt(BPLowInp.getValue().toString()), 
/* 265 */                 Integer.parseInt(BPEdgeInp.getValue().toString()), Integer.parseInt(BPHighInp.getValue().toString()), 
/* 266 */                 LPFlag.isSelected(), Double.parseDouble(LPLowInp.getValue().toString()), 
/* 267 */                 Double.parseDouble(LPEdgeInp.getValue().toString()), Double.parseDouble(LPHighInp.getValue().toString()), 
/* 268 */                 IntegrateFlag.isSelected(), DetrendFlag.isSelected(), 
/* 269 */                 noOfWells.getText(), noOfRecs.getText(), toolstringNames.getText());
/*     */ 
/*     */             
/* 272 */             J.dispose();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 278 */     JButton CancelButton = new JButton("Cancel");
/* 279 */     ButtonPanel.add(CancelButton);
/* 280 */     CancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 285 */             J.dispose();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 291 */     c = new GridBagConstraints();
/* 292 */     c.gridx = 0; c.gridy = 26; c.gridwidth = 1; c.gridheight = 2;
/* 293 */     this.contentPane.add(ButtonPanel, c);
/*     */     
/* 295 */     J.setFocusable(true);
/* 296 */     J.setVisible(true);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/FilterSettingsWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */