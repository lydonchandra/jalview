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
package jalview.ws.params;

/**
 * Interface implemented by classes for maintaining user's parameters in a
 * Jalview session
 * 
 * @author JimP
 * 
 */
public interface ParamManager
{
  /**
   * 
   * @param name
   *          (may be null) select parameter sets with given name
   * @param serviceUrl
   *          (may be null) select parameter sets that are applicable for the
   *          given URL
   * @param modifiable
   *          - if true, return modifiable parameter sets
   * @param unmodifiable
   *          - if true, return server presets
   * @return null if no parameters found, or one or more parameter sets
   */
  public WsParamSetI[] getParameterSet(String name, String serviceUrl,
          boolean modifiable, boolean unmodifiable);

  /**
   * save the given parameter set in the user's parameter set database. Note:
   * this may result in a modal dialog box being raised.
   * 
   * @param parameterSet
   */
  public void storeParameterSet(WsParamSetI parameterSet);

  /**
   * delete the specified parameter set from the database. Note: this may result
   * in a modal dialog box being raised.
   * 
   * @param parameterSet
   */
  public void deleteParameterSet(WsParamSetI parameterSet);

  /**
   * register a parser for the given host url
   * 
   * @param hosturl
   * @param jabaParamStore
   */
  public void registerParser(String hosturl,
          ParamDatastoreI paramdataStore);

}
