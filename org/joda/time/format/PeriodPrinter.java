package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.ReadablePeriod;

public interface PeriodPrinter {
  int calculatePrintedLength(ReadablePeriod paramReadablePeriod, Locale paramLocale);
  
  int countFieldsToPrint(ReadablePeriod paramReadablePeriod, int paramInt, Locale paramLocale);
  
  void printTo(StringBuffer paramStringBuffer, ReadablePeriod paramReadablePeriod, Locale paramLocale);
  
  void printTo(Writer paramWriter, ReadablePeriod paramReadablePeriod, Locale paramLocale) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/PeriodPrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */