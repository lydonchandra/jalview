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

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
* MouseEventDemo.java
*/

import jalview.util.Platform;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Sourced from Oracle and adapted
 * 
 * @see https
 *      ://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
 */
public class MouseEventDemo extends JPanel implements MouseListener
{
  private class BlankArea extends JLabel
  {
    Dimension minSize = new Dimension(200, 100);

    public BlankArea(Color color)
    {
      setBackground(color);
      setOpaque(true);
      setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public Dimension getMinimumSize()
    {
      return minSize;
    }

    @Override
    public Dimension getPreferredSize()
    {
      return minSize;
    }
  }

  static int counter = 0;

  BlankArea blankArea;

  JTextArea textArea;

  static final String NEWLINE = System.getProperty("line.separator");

  /**
   * @j2sIgnore
   */
  public static void main(String[] args)
  {
    // Schedule a job for the event dispatch thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        createAndShowGUI();
      }
    });
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event dispatch thread.
   */
  private static void createAndShowGUI()
  {
    // Create and set up the window.
    JFrame frame = new JFrame("MouseEventDemo (C to clear)");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Create and set up the content pane.
    JComponent newContentPane = new MouseEventDemo();
    newContentPane.setOpaque(true); // content panes must be opaque
    frame.setContentPane(newContentPane);

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public MouseEventDemo()
  {
    super(new GridLayout(0, 1));

    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(400, 75));

    blankArea = new BlankArea(Color.YELLOW);
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            blankArea, scrollPane);
    splitPane.setVisible(true);
    splitPane.setDividerLocation(0.2d);
    splitPane.setResizeWeight(0.5d);
    add(splitPane);

    addKeyBinding();

    blankArea.addMouseListener(this);
    addMouseListener(this);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
  }

  private void addKeyBinding()
  {
    addKeyBinding(KeyStroke.getKeyStroke('C'));
    addKeyBinding(KeyStroke.getKeyStroke('c'));
  }

  /**
   * @param ks
   */
  void addKeyBinding(final KeyStroke ks)
  {
    InputMap inputMap = this.getInputMap(JComponent.WHEN_FOCUSED);
    inputMap.put(ks, ks);
    this.getActionMap().put(ks, new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        textArea.setText("");
        log("");
      }
    });
  }

  void logEvent(String eventDescription, MouseEvent e)
  {
    log("------- " + counter++ + ": " + eventDescription);
    log("e.isPopupTrigger: " + e.isPopupTrigger());
    log("SwingUtilities.isRightMouseButton: "
            + SwingUtilities.isRightMouseButton(e));
    log("SwingUtilities.isLeftMouseButton: "
            + SwingUtilities.isLeftMouseButton(e));
    log("Platform.isControlDown: " + Platform.isControlDown(e));
    log("e.isControlDown: " + e.isControlDown());
    log("e.isAltDown: " + e.isAltDown());
    log("e.isMetaDown: " + e.isMetaDown());
    log("e.isShiftDown: " + e.isShiftDown());
    log("e.getClickCount: " + e.getClickCount());
  }

  /**
   * @param msg
   */
  void log(String msg)
  {
    textArea.append(msg + NEWLINE);
    textArea.setCaretPosition(textArea.getDocument().getLength());
  }

  @Override
  public void mousePressed(MouseEvent e)
  {
    logEvent("Mouse pressed", e);
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    logEvent("Mouse released", e);
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
  }

  @Override
  public void mouseExited(MouseEvent e)
  {
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
    logEvent("Mouse clicked", e);
  }
}
