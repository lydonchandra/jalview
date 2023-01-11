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

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

/**
 * A helper class to render a combobox with tooltips
 * 
 * @see http
 *      ://stackoverflow.com/questions/480261/java-swing-mouseover-text-on-jcombobox
 *      -items
 */
public class ComboBoxTooltipRenderer extends DefaultListCellRenderer
{
  private static final long serialVersionUID = 1L;

  List<String> tooltips;

  @Override
  public Component getListCellRendererComponent(JList list, Object value,
          int index, boolean isSelected, boolean cellHasFocus)
  {

    JComponent comp = (JComponent) super.getListCellRendererComponent(list,
            value, index, isSelected, cellHasFocus);

    if (-1 < index && null != value && null != tooltips)
    {
      list.setToolTipText(tooltips.get(index));
    }
    return comp;
  }

  public void setTooltips(List<String> tips)
  {
    this.tooltips = tips;
  }
}
