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

/**
 * An immutable data bean that models a start-end range
 */
public class Range implements ContiguousI
{
  public final int start;

  public final int end;

  @Override
  public int getBegin()
  {
    return start;
  }

  @Override
  public int getEnd()
  {
    return end;
  }

  public Range(int i, int j)
  {
    start = i;
    end = j;
  }

  @Override
  public String toString()
  {
    return String.valueOf(start) + "-" + String.valueOf(end);
  }

  @Override
  public int hashCode()
  {
    return start * 31 + end;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Range)
    {
      Range r = (Range) obj;
      return (start == r.start && end == r.end);
    }
    return false;
  }
}
