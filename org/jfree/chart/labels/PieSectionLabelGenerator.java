package org.jfree.chart.labels;

import java.text.AttributedString;
import org.jfree.data.general.PieDataset;

public interface PieSectionLabelGenerator {
  String generateSectionLabel(PieDataset paramPieDataset, Comparable paramComparable);
  
  AttributedString generateAttributedSectionLabel(PieDataset paramPieDataset, Comparable paramComparable);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/PieSectionLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */