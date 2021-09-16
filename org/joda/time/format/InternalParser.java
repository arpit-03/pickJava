package org.joda.time.format;

interface InternalParser {
  int estimateParsedLength();
  
  int parseInto(DateTimeParserBucket paramDateTimeParserBucket, CharSequence paramCharSequence, int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/InternalParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */