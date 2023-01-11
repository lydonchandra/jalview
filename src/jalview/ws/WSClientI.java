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
package jalview.ws;

public interface WSClientI
{
  /**
   * basic interface supported by web service clients used by
   * jalview.gui.WebserviceInfo to discover GUI properties and pass events back
   * to the client.
   * 
   */
  /**
   * TODO: change this to be a WS Job Panel GUI 'attribute'
   * 
   * @return boolean true if a job cancel button should be shown
   */
  boolean isCancellable();

  /**
   * TODO: change this to be a WS Job Panel GUI 'attribute'
   * 
   * @return boolean true if results can be merged into the source of input data
   */
  boolean canMergeResults();

  /**
   * instruct client to cancel the job. This is also used by the GUI to
   */
  void cancelJob();
}
