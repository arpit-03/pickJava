/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.date.SerialDate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialDateChooserPanel
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*  76 */   public static final Color DEFAULT_DATE_BUTTON_COLOR = Color.red;
/*     */ 
/*     */   
/*  79 */   public static final Color DEFAULT_MONTH_BUTTON_COLOR = Color.lightGray;
/*     */ 
/*     */   
/*     */   private SerialDate date;
/*     */ 
/*     */   
/*     */   private Color dateButtonColor;
/*     */ 
/*     */   
/*     */   private Color monthButtonColor;
/*     */ 
/*     */   
/*  91 */   private Color chosenOtherButtonColor = Color.darkGray;
/*     */ 
/*     */   
/*  94 */   private int firstDayOfWeek = 1;
/*     */ 
/*     */   
/*  97 */   private int yearSelectionRange = 20;
/*     */ 
/*     */   
/* 100 */   private Font dateFont = new Font("SansSerif", 0, 10);
/*     */ 
/*     */   
/* 103 */   private JComboBox monthSelector = null;
/*     */ 
/*     */   
/* 106 */   private JComboBox yearSelector = null;
/*     */ 
/*     */   
/* 109 */   private JButton todayButton = null;
/*     */ 
/*     */   
/* 112 */   private JButton[] buttons = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean refreshing = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialDateChooserPanel() {
/* 122 */     this(SerialDate.createInstance(new Date()), false, DEFAULT_DATE_BUTTON_COLOR, DEFAULT_MONTH_BUTTON_COLOR);
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
/*     */   public SerialDateChooserPanel(SerialDate date, boolean controlPanel) {
/* 137 */     this(date, controlPanel, DEFAULT_DATE_BUTTON_COLOR, DEFAULT_MONTH_BUTTON_COLOR);
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
/*     */   public SerialDateChooserPanel(SerialDate date, boolean controlPanel, Color dateButtonColor, Color monthButtonColor) {
/* 154 */     super(new BorderLayout());
/*     */     
/* 156 */     this.date = date;
/* 157 */     this.dateButtonColor = dateButtonColor;
/* 158 */     this.monthButtonColor = monthButtonColor;
/*     */     
/* 160 */     add(constructSelectionPanel(), "North");
/* 161 */     add(getCalendarPanel(), "Center");
/* 162 */     if (controlPanel) {
/* 163 */       add(constructControlPanel(), "South");
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
/*     */   public void setDate(SerialDate date) {
/* 175 */     this.date = date;
/* 176 */     this.monthSelector.setSelectedIndex(date.getMonth() - 1);
/* 177 */     refreshYearSelector();
/* 178 */     refreshButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialDate getDate() {
/* 188 */     return this.date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 198 */     if (e.getActionCommand().equals("monthSelectionChanged")) {
/* 199 */       JComboBox c = (JComboBox)e.getSource();
/* 200 */       this.date = SerialDate.createInstance(this.date
/* 201 */           .getDayOfMonth(), c.getSelectedIndex() + 1, this.date.getYYYY());
/*     */       
/* 203 */       refreshButtons();
/*     */     }
/* 205 */     else if (e.getActionCommand().equals("yearSelectionChanged")) {
/* 206 */       if (!this.refreshing) {
/* 207 */         JComboBox c = (JComboBox)e.getSource();
/* 208 */         Integer y = (Integer)c.getSelectedItem();
/* 209 */         this.date = SerialDate.createInstance(this.date
/* 210 */             .getDayOfMonth(), this.date.getMonth(), y.intValue());
/*     */         
/* 212 */         refreshYearSelector();
/* 213 */         refreshButtons();
/*     */       }
/*     */     
/* 216 */     } else if (e.getActionCommand().equals("todayButtonClicked")) {
/* 217 */       setDate(SerialDate.createInstance(new Date()));
/*     */     }
/* 219 */     else if (e.getActionCommand().equals("dateButtonClicked")) {
/* 220 */       JButton b = (JButton)e.getSource();
/* 221 */       int i = Integer.parseInt(b.getName());
/* 222 */       SerialDate first = getFirstVisibleDate();
/* 223 */       SerialDate selected = SerialDate.addDays(i, first);
/* 224 */       setDate(selected);
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
/*     */   private JPanel getCalendarPanel() {
/* 237 */     JPanel panel = new JPanel(new GridLayout(7, 7));
/* 238 */     panel.add(new JLabel("Sun", 0));
/* 239 */     panel.add(new JLabel("Mon", 0));
/* 240 */     panel.add(new JLabel("Tue", 0));
/* 241 */     panel.add(new JLabel("Wed", 0));
/* 242 */     panel.add(new JLabel("Thu", 0));
/* 243 */     panel.add(new JLabel("Fri", 0));
/* 244 */     panel.add(new JLabel("Sat", 0));
/*     */     
/* 246 */     this.buttons = new JButton[42];
/* 247 */     for (int i = 0; i < 42; i++) {
/* 248 */       JButton button = new JButton("");
/* 249 */       button.setMargin(new Insets(1, 1, 1, 1));
/* 250 */       button.setName(Integer.toString(i));
/* 251 */       button.setFont(this.dateFont);
/* 252 */       button.setFocusPainted(false);
/* 253 */       button.setActionCommand("dateButtonClicked");
/* 254 */       button.addActionListener(this);
/* 255 */       this.buttons[i] = button;
/* 256 */       panel.add(button);
/*     */     } 
/* 258 */     return panel;
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
/*     */   protected Color getButtonColor(SerialDate targetDate) {
/* 271 */     if (this.date.equals(this.date)) {
/* 272 */       return this.dateButtonColor;
/*     */     }
/* 274 */     if (targetDate.getMonth() == this.date.getMonth()) {
/* 275 */       return this.monthButtonColor;
/*     */     }
/*     */     
/* 278 */     return this.chosenOtherButtonColor;
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
/*     */   protected SerialDate getFirstVisibleDate() {
/* 291 */     SerialDate result = SerialDate.createInstance(1, this.date.getMonth(), this.date.getYYYY());
/* 292 */     result = SerialDate.addDays(-1, result);
/* 293 */     while (result.getDayOfWeek() != getFirstDayOfWeek()) {
/* 294 */       result = SerialDate.addDays(-1, result);
/*     */     }
/* 296 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFirstDayOfWeek() {
/* 306 */     return this.firstDayOfWeek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void refreshButtons() {
/* 314 */     SerialDate current = getFirstVisibleDate();
/* 315 */     for (int i = 0; i < 42; i++) {
/* 316 */       JButton button = this.buttons[i];
/* 317 */       button.setText(String.valueOf(current.getDayOfWeek()));
/* 318 */       button.setBackground(getButtonColor(current));
/* 319 */       current = SerialDate.addDays(1, current);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshYearSelector() {
/* 329 */     if (!this.refreshing) {
/* 330 */       this.refreshing = true;
/* 331 */       this.yearSelector.removeAllItems();
/* 332 */       Vector v = getYears(this.date.getYYYY());
/* 333 */       for (Enumeration e = v.elements(); e.hasMoreElements();) {
/* 334 */         this.yearSelector.addItem(e.nextElement());
/*     */       }
/* 336 */       this.yearSelector.setSelectedItem(new Integer(this.date.getYYYY()));
/* 337 */       this.refreshing = false;
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
/*     */   private Vector getYears(int chosenYear) {
/* 350 */     Vector<Integer> v = new Vector();
/* 351 */     int i = chosenYear - this.yearSelectionRange;
/* 352 */     for (; i <= chosenYear + this.yearSelectionRange; i++) {
/* 353 */       v.addElement(new Integer(i));
/*     */     }
/* 355 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel constructSelectionPanel() {
/* 365 */     JPanel p = new JPanel();
/* 366 */     this.monthSelector = new JComboBox<String>(SerialDate.getMonths());
/* 367 */     this.monthSelector.addActionListener(this);
/* 368 */     this.monthSelector.setActionCommand("monthSelectionChanged");
/* 369 */     p.add(this.monthSelector);
/*     */     
/* 371 */     this.yearSelector = new JComboBox(getYears(0));
/* 372 */     this.yearSelector.addActionListener(this);
/* 373 */     this.yearSelector.setActionCommand("yearSelectionChanged");
/* 374 */     p.add(this.yearSelector);
/*     */     
/* 376 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel constructControlPanel() {
/* 387 */     JPanel p = new JPanel();
/* 388 */     p.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
/* 389 */     this.todayButton = new JButton("Today");
/* 390 */     this.todayButton.addActionListener(this);
/* 391 */     this.todayButton.setActionCommand("todayButtonClicked");
/* 392 */     p.add(this.todayButton);
/* 393 */     return p;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/SerialDateChooserPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */