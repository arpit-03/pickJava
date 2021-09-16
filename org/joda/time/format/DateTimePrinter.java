package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

public interface DateTimePrinter {
  int estimatePrintedLength();
  
  void printTo(StringBuffer paramStringBuffer, long paramLong, Chronology paramChronology, int paramInt, DateTimeZone paramDateTimeZone, Locale paramLocale);
  
  void printTo(Writer paramWriter, long paramLong, Chronology paramChronology, int paramInt, DateTimeZone paramDateTimeZone, Locale paramLocale) throws IOException;
  
  void printTo(StringBuffer paramStringBuffer, ReadablePartial paramReadablePartial, Locale paramLocale);
  
  void printTo(Writer paramWriter, ReadablePartial paramReadablePartial, Locale paramLocale) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimePrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */