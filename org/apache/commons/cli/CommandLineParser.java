package org.apache.commons.cli;

public interface CommandLineParser {
  CommandLine parse(Options paramOptions, String[] paramArrayOfString) throws ParseException;
  
  CommandLine parse(Options paramOptions, String[] paramArrayOfString, boolean paramBoolean) throws ParseException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/cli/CommandLineParser.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */