/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyedComboBoxModel
/*     */   implements ComboBoxModel
/*     */ {
/*     */   private int selectedItemIndex;
/*     */   private Object selectedItemValue;
/*     */   private ArrayList data;
/*     */   private ArrayList listdatalistener;
/*     */   private transient ListDataListener[] tempListeners;
/*     */   private boolean allowOtherValue;
/*     */   
/*     */   private static class ComboBoxItemPair
/*     */   {
/*     */     private Object key;
/*     */     private Object value;
/*     */     
/*     */     public ComboBoxItemPair(Object key, Object value) {
/*  87 */       this.key = key;
/*  88 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getKey() {
/*  98 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getValue() {
/* 108 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(Object value) {
/* 118 */       this.value = value;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyedComboBoxModel() {
/* 146 */     this.data = new ArrayList();
/* 147 */     this.listdatalistener = new ArrayList();
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
/*     */   public KeyedComboBoxModel(Object[] keys, Object[] values) {
/* 159 */     this();
/* 160 */     setData(keys, values);
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
/*     */   public void setData(Object[] keys, Object[] values) {
/* 172 */     if (values.length != keys.length)
/*     */     {
/* 174 */       throw new IllegalArgumentException("Values and text must have the same length.");
/*     */     }
/*     */     
/* 177 */     this.data.clear();
/* 178 */     this.data.ensureCapacity(keys.length);
/*     */     
/* 180 */     for (int i = 0; i < values.length; i++)
/*     */     {
/* 182 */       add(keys[i], values[i]);
/*     */     }
/*     */     
/* 185 */     this.selectedItemIndex = -1;
/*     */     
/* 187 */     ListDataEvent evt = new ListDataEvent(this, 0, 0, this.data.size() - 1);
/* 188 */     fireListDataEvent(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void fireListDataEvent(ListDataEvent evt) {
/* 198 */     if (this.tempListeners == null)
/*     */     {
/* 200 */       this
/* 201 */         .tempListeners = (ListDataListener[])this.listdatalistener.toArray((Object[])new ListDataListener[this.listdatalistener.size()]);
/*     */     }
/*     */     
/* 204 */     ListDataListener[] listeners = this.tempListeners;
/* 205 */     for (int i = 0; i < listeners.length; i++) {
/*     */       
/* 207 */       ListDataListener l = listeners[i];
/* 208 */       l.contentsChanged(evt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSelectedItem() {
/* 219 */     return this.selectedItemValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedKey(Object anItem) {
/* 230 */     if (anItem == null) {
/*     */       
/* 232 */       this.selectedItemIndex = -1;
/* 233 */       this.selectedItemValue = null;
/*     */     }
/*     */     else {
/*     */       
/* 237 */       int newSelectedItem = findDataElementIndex(anItem);
/* 238 */       if (newSelectedItem == -1) {
/*     */         
/* 240 */         this.selectedItemIndex = -1;
/* 241 */         this.selectedItemValue = null;
/*     */       }
/*     */       else {
/*     */         
/* 245 */         this.selectedItemIndex = newSelectedItem;
/* 246 */         this.selectedItemValue = getElementAt(this.selectedItemIndex);
/*     */       } 
/*     */     } 
/* 249 */     fireListDataEvent(new ListDataEvent(this, 0, -1, -1));
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
/*     */   public void setSelectedItem(Object anItem) {
/* 262 */     if (anItem == null) {
/*     */       
/* 264 */       this.selectedItemIndex = -1;
/* 265 */       this.selectedItemValue = null;
/*     */     }
/*     */     else {
/*     */       
/* 269 */       int newSelectedItem = findElementIndex(anItem);
/* 270 */       if (newSelectedItem == -1) {
/*     */         
/* 272 */         if (isAllowOtherValue())
/*     */         {
/* 274 */           this.selectedItemIndex = -1;
/* 275 */           this.selectedItemValue = anItem;
/*     */         }
/*     */         else
/*     */         {
/* 279 */           this.selectedItemIndex = -1;
/* 280 */           this.selectedItemValue = null;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 285 */         this.selectedItemIndex = newSelectedItem;
/* 286 */         this.selectedItemValue = getElementAt(this.selectedItemIndex);
/*     */       } 
/*     */     } 
/* 289 */     fireListDataEvent(new ListDataEvent(this, 0, -1, -1));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAllowOtherValue() {
/* 294 */     return this.allowOtherValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowOtherValue(boolean allowOtherValue) {
/* 302 */     this.allowOtherValue = allowOtherValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addListDataListener(ListDataListener l) {
/* 313 */     if (l == null)
/*     */     {
/* 315 */       throw new NullPointerException();
/*     */     }
/* 317 */     this.listdatalistener.add(l);
/* 318 */     this.tempListeners = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getElementAt(int index) {
/* 329 */     if (index >= this.data.size())
/*     */     {
/* 331 */       return null;
/*     */     }
/*     */     
/* 334 */     ComboBoxItemPair datacon = this.data.get(index);
/* 335 */     if (datacon == null)
/*     */     {
/* 337 */       return null;
/*     */     }
/* 339 */     return datacon.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getKeyAt(int index) {
/* 350 */     if (index >= this.data.size())
/*     */     {
/* 352 */       return null;
/*     */     }
/*     */     
/* 355 */     if (index < 0)
/*     */     {
/* 357 */       return null;
/*     */     }
/*     */     
/* 360 */     ComboBoxItemPair datacon = this.data.get(index);
/* 361 */     if (datacon == null)
/*     */     {
/* 363 */       return null;
/*     */     }
/* 365 */     return datacon.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSelectedKey() {
/* 375 */     return getKeyAt(this.selectedItemIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 385 */     return this.data.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListDataListener(ListDataListener l) {
/* 396 */     this.listdatalistener.remove(l);
/* 397 */     this.tempListeners = null;
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
/*     */   private int findDataElementIndex(Object anItem) {
/* 409 */     if (anItem == null)
/*     */     {
/* 411 */       throw new NullPointerException("Item to find must not be null");
/*     */     }
/*     */     
/* 414 */     for (int i = 0; i < this.data.size(); i++) {
/*     */       
/* 416 */       ComboBoxItemPair datacon = this.data.get(i);
/* 417 */       if (anItem.equals(datacon.getKey()))
/*     */       {
/* 419 */         return i;
/*     */       }
/*     */     } 
/* 422 */     return -1;
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
/*     */   public int findElementIndex(Object key) {
/* 434 */     if (key == null)
/*     */     {
/* 436 */       throw new NullPointerException("Item to find must not be null");
/*     */     }
/*     */     
/* 439 */     for (int i = 0; i < this.data.size(); i++) {
/*     */       
/* 441 */       ComboBoxItemPair datacon = this.data.get(i);
/* 442 */       if (key.equals(datacon.getValue()))
/*     */       {
/* 444 */         return i;
/*     */       }
/*     */     } 
/* 447 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDataElement(Object key) {
/* 457 */     int idx = findDataElementIndex(key);
/* 458 */     if (idx == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 463 */     this.data.remove(idx);
/* 464 */     ListDataEvent evt = new ListDataEvent(this, 2, idx, idx);
/*     */     
/* 466 */     fireListDataEvent(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Object key, Object cbitem) {
/* 477 */     ComboBoxItemPair con = new ComboBoxItemPair(key, cbitem);
/* 478 */     this.data.add(con);
/*     */     
/* 480 */     ListDataEvent evt = new ListDataEvent(this, 1, this.data.size() - 2, this.data.size() - 2);
/* 481 */     fireListDataEvent(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 489 */     int size = getSize();
/* 490 */     this.data.clear();
/* 491 */     ListDataEvent evt = new ListDataEvent(this, 2, 0, size - 1);
/* 492 */     fireListDataEvent(evt);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/KeyedComboBoxModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */