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
package jalview.commands;

import jalview.analysis.AlignmentSorter;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.SequenceI;

/**
 * An undoable command to reorder the sequences in an alignment.
 * 
 * @author gmcarstairs
 *
 */
public class OrderCommand implements CommandI
{
  String description;

  /*
   * The sequence order before sorting (target order for an undo)
   */
  SequenceI[] seqs;

  /*
   * The sequence order specified by this command
   */
  SequenceI[] seqs2;

  /*
   * The alignment the command acts on
   */
  AlignmentI al;

  /**
   * Constructor given the 'undo' sequence order, and the (already) sorted
   * alignment.
   * 
   * @param description
   *          a text label for the 'undo' menu option
   * @param seqs
   *          the sequence order for undo
   * @param al
   *          the alignment as ordered by this command
   */
  public OrderCommand(String description, SequenceI[] seqs, AlignmentI al)
  {
    this.description = description;
    this.seqs = seqs;
    this.seqs2 = al.getSequencesArray();
    this.al = al;
    doCommand(null);
  }

  public String getDescription()
  {
    return description;
  }

  public int getSize()
  {
    return 1;
  }

  public void doCommand(AlignmentI[] views)
  {
    AlignmentSorter.setOrder(al, seqs2);
  }

  public void undoCommand(AlignmentI[] views)
  {
    AlignmentSorter.setOrder(al, seqs);
  }

  /**
   * Returns the sequence order used to sort, or before sorting if undo=true.
   * 
   * @param undo
   * @return
   */
  public SequenceI[] getSequenceOrder(boolean undo)
  {
    return undo ? seqs : seqs2;
  }
}
