package org.jfree.util;

public interface ExtendedConfiguration extends Configuration {
  boolean isPropertySet(String paramString);
  
  int getIntProperty(String paramString);
  
  int getIntProperty(String paramString, int paramInt);
  
  boolean getBoolProperty(String paramString);
  
  boolean getBoolProperty(String paramString, boolean paramBoolean);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ExtendedConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */