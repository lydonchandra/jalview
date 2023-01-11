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
package jalview.gui;

/**
 * Visual progress indicator interface.
 * 
 * @author JimP
 * 
 */
public interface IProgressIndicator
{
  /**
   * Visual indication of some operation taking place. On first call with a
   * particular ID an indicator with the given message is added. The indicator
   * is removed with a second call with same ID.
   * 
   * @param message
   *          - displayed message for operation. Please ensure message is
   *          internationalised.
   * @param id
   *          - unique handle for this indicator
   */
  public abstract void setProgressBar(String message, long id);

  /**
   * register a handler for the progress bar identified by id
   * 
   * @param id
   * @param handler
   */
  public abstract void registerHandler(long id,
          IProgressIndicatorHandler handler);

  /**
   * 
   * @return true if any progress bars are still active
   */
  boolean operationInProgress();

}
