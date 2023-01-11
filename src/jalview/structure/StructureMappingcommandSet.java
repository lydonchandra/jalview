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
package jalview.structure;

/**
 * holder for script commands generated for a particular structure mapping
 * 
 * @author jimp
 * 
 */
public class StructureMappingcommandSet
{
  /**
   * structure file for which these commands were generated
   */
  public String mapping;

  /**
   * set of commands
   */
  public String[] commands;

  /**
   * some object that indicates what the commands can be parsed by (eg
   * JmolCommands.class implies these are Jmol commands)
   */
  public Object handledBy;

  /**
   * record the originating command generator, the structure mapping involved,
   * and the set of commands to be passed.
   * 
   * @param handledBy
   * @param files
   * @param commands
   */
  public StructureMappingcommandSet(Object handledBy, String files,
          String[] commands)
  {
    this.mapping = files;
    this.handledBy = handledBy;
    this.commands = commands;
  }
}
