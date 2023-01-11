/*
 * Jalview - A Sequence Alignment Editor and Viewer (2.11.2.5)
 * Copyright (C) 2022 The Jalview Authors
 * 
 * This file is part of Jalview.
 * 
 * Jalview is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *  
 * Jalview is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Jalview.  If not, see <http://www.gnu.org/licenses/>.
 * The Jalview Authors are detailed in the 'AUTHORS' file.
 */
package jalview.io;

import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.SequenceI;

import java.io.IOException;

public class AMSAFile extends jalview.io.FastaFile
{

  AlignmentI al;

  /**
   * Creates a new AMSAFile object for output.
   */
  public AMSAFile(AlignmentI al)
  {
    this.al = al;
  }

  public AMSAFile(String inFile, DataSourceType sourceType)
          throws IOException
  {
    super(inFile, sourceType);
  }

  public AMSAFile(FileParse source) throws IOException
  {
    super(source);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  @Override
  public String print(SequenceI[] sqs, boolean jvsuffix)
  {
    super.print(sqs, jvsuffix);

    AlignmentAnnotation aa;
    if (al.getAlignmentAnnotation() != null)
    {

      for (int i = 0; i < al.getAlignmentAnnotation().length; i++)
      {
        aa = al.getAlignmentAnnotation()[i];

        if (aa.autoCalculated || !aa.visible)
        {
          continue;
        }

        out.append(">#_" + aa.label);
        if (aa.description != null)
        {
          out.append(" " + aa.description);
        }

        out.append(newline);

        int nochunks = Math.min(aa.annotations.length, al.getWidth()) / len
                + 1;

        for (int j = 0; j < nochunks; j++)
        {
          int start = j * len;
          int end = start + len;
          if (end > aa.annotations.length)
          {
            end = aa.annotations.length;
          }

          String ch;
          for (int k = start; k < end; k++)
          {
            if (aa.annotations[k] == null)
            {
              ch = " ";
            }
            else
            {
              ch = aa.annotations[k].displayCharacter;
            }
            if (ch.length() > 1)
            {
              this.warningMessage = "Truncated column annotation to first letter.";
              ch = ch.substring(0, 1);
            }
            out.append(ch);

          }
          out.append(newline);
        }
      }
    }
    return out.toString();
  }
}
