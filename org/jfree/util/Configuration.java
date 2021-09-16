package org.jfree.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

public interface Configuration extends Serializable, Cloneable {
  String getConfigProperty(String paramString);
  
  String getConfigProperty(String paramString1, String paramString2);
  
  Iterator findPropertyKeys(String paramString);
  
  Enumeration getConfigProperties();
  
  Object clone() throws CloneNotSupportedException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */