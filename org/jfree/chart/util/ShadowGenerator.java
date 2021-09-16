package org.jfree.chart.util;

import java.awt.image.BufferedImage;

public interface ShadowGenerator {
  BufferedImage createDropShadow(BufferedImage paramBufferedImage);
  
  int calculateOffsetX();
  
  int calculateOffsetY();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/ShadowGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */