package org.jfree.base.modules;

import org.jfree.util.Configuration;
import org.jfree.util.ExtendedConfiguration;

public interface SubSystem {
  Configuration getGlobalConfig();
  
  ExtendedConfiguration getExtendedConfig();
  
  PackageManager getPackageManager();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/modules/SubSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */