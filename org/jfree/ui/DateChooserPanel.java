/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateChooserPanel
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*     */   private Calendar chosenDate;
/*     */   private Color chosenDateButtonColor;
/*     */   private Color chosenMonthButtonColor;
/*     */   private Color chosenOtherButtonColor;
/*     */   private int firstDayOfWeek;
/* 105 */   private int yearSelectionRange = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private Font dateFont = new Font("SansSerif", 0, 10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JComboBox monthSelector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JComboBox yearSelector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton todayButton;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton[] buttons;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean refreshing = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] WEEK_DAYS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateChooserPanel() {
/* 149 */     this(Calendar.getInstance(), false);
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
/*     */   public DateChooserPanel(Calendar calendar, boolean controlPanel) {
/* 162 */     super(new BorderLayout());
/*     */     
/* 164 */     this.chosenDateButtonColor = UIManager.getColor("textHighlight");
/* 165 */     this.chosenMonthButtonColor = UIManager.getColor("control");
/* 166 */     this.chosenOtherButtonColor = UIManager.getColor("controlShadow");
/*     */ 
/*     */     
/* 169 */     this.chosenDate = calendar;
/* 170 */     this.firstDayOfWeek = calendar.getFirstDayOfWeek();
/* 171 */     this.WEEK_DAYS = new int[7];
/* 172 */     for (int i = 0; i < 7; i++) {
/* 173 */       this.WEEK_DAYS[i] = (this.firstDayOfWeek + i - 1) % 7 + 1;
/*     */     }
/*     */     
/* 176 */     add(constructSelectionPanel(), "North");
/* 177 */     add(getCalendarPanel(), "Center");
/* 178 */     if (controlPanel) {
/* 179 */       add(constructControlPanel(), "South");
/*     */     }
/* 181 */     setDate(calendar.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(Date theDate) {
/* 191 */     this.chosenDate.setTime(theDate);
/* 192 */     this.monthSelector.setSelectedIndex(this.chosenDate.get(2));
/*     */     
/* 194 */     refreshYearSelector();
/* 195 */     refreshButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 205 */     return this.chosenDate.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 215 */     if (e.getActionCommand().equals("monthSelectionChanged")) {
/* 216 */       JComboBox c = (JComboBox)e.getSource();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       int dayOfMonth = this.chosenDate.get(5);
/* 223 */       this.chosenDate.set(5, 1);
/* 224 */       this.chosenDate.set(2, c.getSelectedIndex());
/* 225 */       int maxDayOfMonth = this.chosenDate.getActualMaximum(5);
/*     */       
/* 227 */       this.chosenDate.set(5, Math.min(dayOfMonth, maxDayOfMonth));
/*     */       
/* 229 */       refreshButtons();
/*     */     }
/* 231 */     else if (e.getActionCommand().equals("yearSelectionChanged")) {
/* 232 */       if (!this.refreshing) {
/* 233 */         JComboBox c = (JComboBox)e.getSource();
/* 234 */         Integer y = (Integer)c.getSelectedItem();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 240 */         int dayOfMonth = this.chosenDate.get(5);
/* 241 */         this.chosenDate.set(5, 1);
/* 242 */         this.chosenDate.set(1, y.intValue());
/* 243 */         int maxDayOfMonth = this.chosenDate.getActualMaximum(5);
/*     */         
/* 245 */         this.chosenDate.set(5, Math.min(dayOfMonth, maxDayOfMonth));
/*     */         
/* 247 */         refreshYearSelector();
/* 248 */         refreshButtons();
/*     */       }
/*     */     
/* 251 */     } else if (e.getActionCommand().equals("todayButtonClicked")) {
/* 252 */       setDate(new Date());
/*     */     }
/* 254 */     else if (e.getActionCommand().equals("dateButtonClicked")) {
/* 255 */       JButton b = (JButton)e.getSource();
/* 256 */       int i = Integer.parseInt(b.getName());
/* 257 */       Calendar cal = getFirstVisibleDate();
/* 258 */       cal.add(5, i);
/* 259 */       setDate(cal.getTime());
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
/*     */   private JPanel getCalendarPanel() {
/* 271 */     JPanel p = new JPanel(new GridLayout(7, 7));
/* 272 */     DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
/* 273 */     String[] weekDays = dateFormatSymbols.getShortWeekdays();
/*     */     int i;
/* 275 */     for (i = 0; i < this.WEEK_DAYS.length; i++) {
/* 276 */       p.add(new JLabel(weekDays[this.WEEK_DAYS[i]], 0));
/*     */     }
/*     */ 
/*     */     
/* 280 */     this.buttons = new JButton[42];
/* 281 */     for (i = 0; i < 42; i++) {
/* 282 */       JButton b = new JButton("");
/* 283 */       b.setMargin(new Insets(1, 1, 1, 1));
/* 284 */       b.setName(Integer.toString(i));
/* 285 */       b.setFont(this.dateFont);
/* 286 */       b.setFocusPainted(false);
/* 287 */       b.setActionCommand("dateButtonClicked");
/* 288 */       b.addActionListener(this);
/* 289 */       this.buttons[i] = b;
/* 290 */       p.add(b);
/*     */     } 
/* 292 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Color getButtonColor(Calendar theDate) {
/* 303 */     if (equalDates(theDate, this.chosenDate)) {
/* 304 */       return this.chosenDateButtonColor;
/*     */     }
/* 306 */     if (theDate.get(2) == this.chosenDate.get(2))
/*     */     {
/* 308 */       return this.chosenMonthButtonColor;
/*     */     }
/*     */     
/* 311 */     return this.chosenOtherButtonColor;
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
/*     */   private boolean equalDates(Calendar c1, Calendar c2) {
/* 323 */     if (c1.get(5) == c2.get(5) && c1
/* 324 */       .get(2) == c2.get(2) && c1
/* 325 */       .get(1) == c2.get(1)) {
/* 326 */       return true;
/*     */     }
/*     */     
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Calendar getFirstVisibleDate() {
/* 340 */     Calendar c = Calendar.getInstance();
/* 341 */     c.set(this.chosenDate.get(1), this.chosenDate.get(2), 1);
/*     */     
/* 343 */     c.add(5, -1);
/* 344 */     while (c.get(7) != getFirstDayOfWeek()) {
/* 345 */       c.add(5, -1);
/*     */     }
/* 347 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFirstDayOfWeek() {
/* 357 */     return this.firstDayOfWeek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshButtons() {
/* 364 */     Calendar c = getFirstVisibleDate();
/* 365 */     for (int i = 0; i < 42; i++) {
/* 366 */       JButton b = this.buttons[i];
/* 367 */       b.setText(Integer.toString(c.get(5)));
/* 368 */       b.setBackground(getButtonColor(c));
/* 369 */       c.add(5, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshYearSelector() {
/* 378 */     if (!this.refreshing) {
/* 379 */       this.refreshing = true;
/* 380 */       this.yearSelector.removeAllItems();
/* 381 */       Integer[] years = getYears(this.chosenDate.get(1));
/*     */       
/* 383 */       for (int i = 0; i < years.length; i++) {
/* 384 */         this.yearSelector.addItem(years[i]);
/*     */       }
/* 386 */       this.yearSelector.setSelectedItem(new Integer(this.chosenDate.get(1)));
/*     */       
/* 388 */       this.refreshing = false;
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
/*     */   private Integer[] getYears(int chosenYear) {
/* 401 */     int size = this.yearSelectionRange * 2 + 1;
/* 402 */     int start = chosenYear - this.yearSelectionRange;
/*     */     
/* 404 */     Integer[] years = new Integer[size];
/* 405 */     for (int i = 0; i < size; i++) {
/* 406 */       years[i] = new Integer(i + start);
/*     */     }
/* 408 */     return years;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel constructSelectionPanel() {
/* 418 */     JPanel p = new JPanel();
/*     */     
/* 420 */     int minMonth = this.chosenDate.getMinimum(2);
/* 421 */     int maxMonth = this.chosenDate.getMaximum(2);
/* 422 */     String[] months = new String[maxMonth - minMonth + 1];
/* 423 */     System.arraycopy(SerialDate.getMonths(), minMonth, months, 0, months.length);
/*     */ 
/*     */     
/* 426 */     this.monthSelector = new JComboBox<String>(months);
/* 427 */     this.monthSelector.addActionListener(this);
/* 428 */     this.monthSelector.setActionCommand("monthSelectionChanged");
/* 429 */     p.add(this.monthSelector);
/*     */     
/* 431 */     this.yearSelector = new JComboBox<Integer>(getYears(0));
/* 432 */     this.yearSelector.addActionListener(this);
/* 433 */     this.yearSelector.setActionCommand("yearSelectionChanged");
/* 434 */     p.add(this.yearSelector);
/*     */     
/* 436 */     return p;
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
/* 447 */     JPanel p = new JPanel();
/* 448 */     p.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
/* 449 */     this.todayButton = new JButton("Today");
/* 450 */     this.todayButton.addActionListener(this);
/* 451 */     this.todayButton.setActionCommand("todayButtonClicked");
/* 452 */     p.add(this.todayButton);
/* 453 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getChosenDateButtonColor() {
/* 463 */     return this.chosenDateButtonColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChosenDateButtonColor(Color chosenDateButtonColor) {
/* 472 */     if (chosenDateButtonColor == null) {
/* 473 */       throw new NullPointerException("UIColor must not be null.");
/*     */     }
/* 475 */     Color oldValue = this.chosenDateButtonColor;
/* 476 */     this.chosenDateButtonColor = chosenDateButtonColor;
/* 477 */     refreshButtons();
/* 478 */     firePropertyChange("chosenDateButtonColor", oldValue, chosenDateButtonColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getChosenMonthButtonColor() {
/* 488 */     return this.chosenMonthButtonColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChosenMonthButtonColor(Color chosenMonthButtonColor) {
/* 497 */     if (chosenMonthButtonColor == null) {
/* 498 */       throw new NullPointerException("UIColor must not be null.");
/*     */     }
/* 500 */     Color oldValue = this.chosenMonthButtonColor;
/* 501 */     this.chosenMonthButtonColor = chosenMonthButtonColor;
/* 502 */     refreshButtons();
/* 503 */     firePropertyChange("chosenMonthButtonColor", oldValue, chosenMonthButtonColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getChosenOtherButtonColor() {
/* 513 */     return this.chosenOtherButtonColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChosenOtherButtonColor(Color chosenOtherButtonColor) {
/* 522 */     if (chosenOtherButtonColor == null) {
/* 523 */       throw new NullPointerException("UIColor must not be null.");
/*     */     }
/* 525 */     Color oldValue = this.chosenOtherButtonColor;
/* 526 */     this.chosenOtherButtonColor = chosenOtherButtonColor;
/* 527 */     refreshButtons();
/* 528 */     firePropertyChange("chosenOtherButtonColor", oldValue, chosenOtherButtonColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYearSelectionRange() {
/* 538 */     return this.yearSelectionRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYearSelectionRange(int yearSelectionRange) {
/* 547 */     int oldYearSelectionRange = this.yearSelectionRange;
/* 548 */     this.yearSelectionRange = yearSelectionRange;
/* 549 */     refreshYearSelector();
/* 550 */     firePropertyChange("yearSelectionRange", oldYearSelectionRange, yearSelectionRange);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/DateChooserPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */