package org.jfree.ui.tabbedui;

import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JMenu;

public interface RootEditor {
  void setActive(boolean paramBoolean);
  
  boolean isActive();
  
  String getEditorName();
  
  JMenu[] getMenus();
  
  JComponent getToolbar();
  
  JComponent getMainPanel();
  
  boolean isEnabled();
  
  void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/tabbedui/RootEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */