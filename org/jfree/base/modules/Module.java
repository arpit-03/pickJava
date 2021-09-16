package org.jfree.base.modules;

public interface Module extends ModuleInfo {
  ModuleInfo[] getRequiredModules();
  
  ModuleInfo[] getOptionalModules();
  
  void initialize(SubSystem paramSubSystem) throws ModuleInitializeException;
  
  void configure(SubSystem paramSubSystem);
  
  String getDescription();
  
  String getProducer();
  
  String getName();
  
  String getSubSystem();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/modules/Module.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */