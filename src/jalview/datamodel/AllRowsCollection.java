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
package jalview.datamodel;

import jalview.api.AlignmentRowsCollectionI;

import java.util.Iterator;

public class AllRowsCollection implements AlignmentRowsCollectionI
{
  int start;

  int end;

  AlignmentI alignment;

  HiddenSequences hidden;

  public AllRowsCollection(int s, int e, AlignmentI al)
  {
    start = s;
    end = e;
    alignment = al;
    hidden = al.getHiddenSequences();
  }

  @Override
  public Iterator<Integer> iterator()
  {
    return new AllRowsIterator(start, end, alignment);
  }

  @Override
  public boolean isHidden(int seq)
  {
    return hidden.isHidden(seq);
  }

  @Override
  public SequenceI getSequence(int seq)
  {
    return alignment.getSequenceAtAbsoluteIndex(seq);
  }

  @Override
  public boolean hasHidden()
  {
    return (hidden.getSize() > 0);
  }
}
