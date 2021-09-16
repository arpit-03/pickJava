package us.hebi.matlab.mat.format;

import java.io.IOException;
import us.hebi.matlab.mat.types.Sink;

public interface Mat5Serializable {
  int getMat5Size(String paramString);
  
  void writeMat5(String paramString, boolean paramBoolean, Sink paramSink) throws IOException;
  
  public static interface Mat5Attributes {
    boolean isLogical();
    
    boolean isComplex();
    
    int getNzMax();
  }
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5Serializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */