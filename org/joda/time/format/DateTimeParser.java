package org.joda.time.format;

public interface DateTimeParser {
  int estimateParsedLength();
  
  int parseInto(DateTimeParserBucket paramDateTimeParserBucket, String paramString, int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimeParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */