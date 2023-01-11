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

package jalview.fts.api;

/**
 * This interface provides a model for the summary data;
 * 
 * @author tcnofoegbu
 *
 */
public interface FTSData
{

  /**
   * Return an array of Objects representing the retrieved FTS data
   * 
   * @return
   */
  public Object[] getSummaryData();

  /**
   * The primary key object for the retrieved FTS data
   * 
   * @return
   */
  public Object getPrimaryKey();
}
