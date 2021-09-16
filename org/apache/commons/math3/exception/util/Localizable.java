package org.apache.commons.math3.exception.util;

import java.io.Serializable;
import java.util.Locale;

public interface Localizable extends Serializable {
  String getSourceString();
  
  String getLocalizedString(Locale paramLocale);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/util/Localizable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */