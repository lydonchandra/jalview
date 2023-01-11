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
import java.awt.Dialog.ModalityType;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import jalview.util.Platform;
import jalview.util.dialogrunner.DialogRunnerI;

public class JvOptionPane extends JOptionPane
        implements DialogRunnerI, PropertyChangeListener
{
  private static final long serialVersionUID = -3019167117756785229L;

  private static Object mockResponse = JvOptionPane.CANCEL_OPTION;

  private static boolean interactiveMode = true;

  private Component parentComponent;

  private Map<Object, Runnable> callbacks = new HashMap<>();

  /*
   * JalviewJS reports user choice in the dialog as the selected
   * option (text); this list allows conversion to index (int)
   */
  List<Object> ourOptions;

  public JvOptionPane(final Component parent)
  {
    this.parentComponent = Platform.isJS() ? this : parent;
  }

  public static int showConfirmDialog(Component parentComponent,
          Object message) throws HeadlessException
  {
    // only called by test
    return isInteractiveMode()
            ? JOptionPane.showConfirmDialog(parentComponent, message)
            : (int) getMockResponse();
  }

  /**
   * Message, title, optionType
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @return
   * @throws HeadlessException
   */
  public static int showConfirmDialog(Component parentComponent,
          Object message, String title, int optionType)
          throws HeadlessException
  {
    if (!isInteractiveMode())
    {
      return (int) getMockResponse();
    }
    switch (optionType)
    {
    case JvOptionPane.YES_NO_CANCEL_OPTION:
      // FeatureRenderer amendFeatures ?? TODO ??
      // Chimera close
      // PromptUserConfig
      // $FALL-THROUGH$
    default:
    case JvOptionPane.YES_NO_OPTION:
      // PromptUserConfig usage stats
      // for now treated as "OK CANCEL"
      // $FALL-THROUGH$
    case JvOptionPane.OK_CANCEL_OPTION:
      // will fall back to simple HTML
      return JOptionPane.showConfirmDialog(parentComponent, message, title,
              optionType);
    }
  }

  /**
   * Adds a message type. Fallback is to just add it in the beginning.
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @param messageType
   * @return
   * @throws HeadlessException
   */
  public static int showConfirmDialog(Component parentComponent,
          Object message, String title, int optionType, int messageType)
          throws HeadlessException
  {
    // JalviewServicesChanged
    // PromptUserConfig raiseDialog
    return isInteractiveMode()
            ? JOptionPane.showConfirmDialog(parentComponent, message, title,
                    optionType, messageType)
            : (int) getMockResponse();
  }

  /**
   * Adds an icon
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @param messageType
   * @param icon
   * @return
   * @throws HeadlessException
   */
  public static int showConfirmDialog(Component parentComponent,
          Object message, String title, int optionType, int messageType,
          Icon icon) throws HeadlessException
  {
    // JvOptionPaneTest only
    return isInteractiveMode()
            ? JOptionPane.showConfirmDialog(parentComponent, message, title,
                    optionType, messageType, icon)
            : (int) getMockResponse();
  }

  /**
   * Internal version "OK"
   * 
   * @param parentComponent
   * @param message
   * @return
   */
  public static int showInternalConfirmDialog(Component parentComponent,
          Object message)
  {
    // JvOptionPaneTest only;
    return isInteractiveMode()
            ? JOptionPane.showInternalConfirmDialog(parentComponent,
                    message)
            : (int) getMockResponse();
  }

  /**
   * Internal version -- changed to standard version for now
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @return
   */
  public static int showInternalConfirmDialog(Component parentComponent,
          String message, String title, int optionType)
  {
    if (!isInteractiveMode())
    {
      return (int) getMockResponse();
    }
    switch (optionType)
    {
    case JvOptionPane.YES_NO_CANCEL_OPTION:
      // ColourMenuHelper.addMenuItmers.offerRemoval TODO
    case JvOptionPane.YES_NO_OPTION:
      // UserDefinedColoursSave -- relevant? TODO
      // $FALL-THROUGH$
    default:
    case JvOptionPane.OK_CANCEL_OPTION:

      // EditNameDialog --- uses panel for messsage TODO

      // Desktop.inputURLMenuItem
      // WsPreferenses
      return JOptionPane.showConfirmDialog(parentComponent, message, title,
              optionType);
    }
  }

  /**
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @param messageType
   * @return
   */
  public static int showInternalConfirmDialog(Component parentComponent,
          Object message, String title, int optionType, int messageType)
  {
    if (!isInteractiveMode())
    {
      return (int) getMockResponse();
    }
    switch (optionType)
    {
    case JvOptionPane.YES_NO_CANCEL_OPTION:
    case JvOptionPane.YES_NO_OPTION:
      // UserQuestionanaireCheck
      // VamsasApplication
      // $FALL-THROUGH$
    default:
    case JvOptionPane.OK_CANCEL_OPTION:
      // will fall back to simple HTML
      return JOptionPane.showConfirmDialog(parentComponent, message, title,
              optionType, messageType);
    }
  }

  /**
   * adds icon; no longer internal
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @param messageType
   * @param icon
   * @return
   */
  public static int showInternalConfirmDialog(Component parentComponent,
          Object message, String title, int optionType, int messageType,
          Icon icon)
  {
    if (!isInteractiveMode())
    {
      return (int) getMockResponse();
    }
    switch (optionType)
    {
    case JvOptionPane.YES_NO_CANCEL_OPTION:
    case JvOptionPane.YES_NO_OPTION:
      //$FALL-THROUGH$
    default:
    case JvOptionPane.OK_CANCEL_OPTION:
      // Preferences editLink/newLink
      return JOptionPane.showConfirmDialog(parentComponent, message, title,
              optionType, messageType, icon);
    }

  }

  /**
   * custom options full-featured
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param optionType
   * @param messageType
   * @param icon
   * @param options
   * @param initialValue
   * @return
   * @throws HeadlessException
   */
  public static int showOptionDialog(Component parentComponent,
          String message, String title, int optionType, int messageType,
          Icon icon, Object[] options, Object initialValue)
          throws HeadlessException
  {
    if (!isInteractiveMode())
    {
      return (int) getMockResponse();
    }
    // two uses:
    //
    // TODO
    //
    // 1) AlignViewport for openLinkedAlignment
    //
    // Show a dialog with the option to open and link (cDNA <-> protein) as a
    // new
    // alignment, either as a standalone alignment or in a split frame. Returns
    // true if the new alignment was opened, false if not, because the user
    // declined the offer.
    //
    // 2) UserDefinedColors warning about saving over a name already defined
    //
    return JOptionPane.showOptionDialog(parentComponent, message, title,
            optionType, messageType, icon, options, initialValue);
  }

  /**
   * Just an OK message
   * 
   * @param message
   * @throws HeadlessException
   */
  public static void showMessageDialog(Component parentComponent,
          String message) throws HeadlessException
  {
    if (!isInteractiveMode())
    {
      outputMessage(message);
      return;
    }

    // test class only

    JOptionPane.showMessageDialog(parentComponent, message);
  }

  /**
   * OK with message, title, and type
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @throws HeadlessException
   */
  public static void showMessageDialog(Component parentComponent,
          String message, String title, int messageType)
          throws HeadlessException
  {
    // 30 implementations -- all just fine.

    if (!isInteractiveMode())
    {
      outputMessage(message);
      return;
    }

    JOptionPane.showMessageDialog(parentComponent,
            getPrefix(messageType) + message, title, messageType);
  }

  /**
   * adds title and icon
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @param icon
   * @throws HeadlessException
   */
  public static void showMessageDialog(Component parentComponent,
          String message, String title, int messageType, Icon icon)
          throws HeadlessException
  {

    // test only

    if (!isInteractiveMode())
    {
      outputMessage(message);
      return;
    }

    JOptionPane.showMessageDialog(parentComponent, message, title,
            messageType, icon);
  }

  /**
   * was internal
   * 
   */
  public static void showInternalMessageDialog(Component parentComponent,
          Object message)
  {

    // WsPreferences only

    if (!isInteractiveMode())
    {
      outputMessage(message);
      return;
    }

    JOptionPane.showMessageDialog(parentComponent, message);
  }

  /**
   * Adds title and messageType
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   */
  public static void showInternalMessageDialog(Component parentComponent,
          String message, String title, int messageType)
  {

    // 41 references

    if (!isInteractiveMode())
    {
      outputMessage(message);
      return;
    }

    JOptionPane.showMessageDialog(parentComponent,
            getPrefix(messageType) + message, title, messageType);
  }

  /**
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @param icon
   */
  public static void showInternalMessageDialog(Component parentComponent,
          Object message, String title, int messageType, Icon icon)
  {

    // test only

    if (!isInteractiveMode())
    {
      outputMessage(message);
      return;
    }

    JOptionPane.showMessageDialog(parentComponent, message, title,
            messageType, icon);
  }

  /**
   * 
   * @param message
   * @return
   * @throws HeadlessException
   */
  public static String showInputDialog(Object message)
          throws HeadlessException
  {
    // test only

    if (!isInteractiveMode())
    {
      return getMockResponse().toString();
    }

    return JOptionPane.showInputDialog(message);
  }

  /**
   * adds inital selection value
   * 
   * @param message
   * @param initialSelectionValue
   * @return
   */
  public static String showInputDialog(String message,
          String initialSelectionValue)
  {
    if (!isInteractiveMode())
    {
      return getMockResponse().toString();
    }

    // AnnotationPanel character option

    return JOptionPane.showInputDialog(message, initialSelectionValue);
  }

  /**
   * adds inital selection value
   * 
   * @param message
   * @param initialSelectionValue
   * @return
   */
  public static String showInputDialog(Object message,
          Object initialSelectionValue)
  {
    if (!isInteractiveMode())
    {
      return getMockResponse().toString();
    }

    // AnnotationPanel character option

    return JOptionPane.showInputDialog(message, initialSelectionValue);
  }

  /**
   * centered on parent
   * 
   * @param parentComponent
   * @param message
   * @return
   * @throws HeadlessException
   */
  public static String showInputDialog(Component parentComponent,
          String message) throws HeadlessException
  {
    // test only

    return isInteractiveMode()
            ? JOptionPane.showInputDialog(parentComponent, message)
            : getMockResponse().toString();
  }

  /**
   * input with initial selection
   * 
   * @param parentComponent
   * @param message
   * @param initialSelectionValue
   * @return
   */
  public static String showInputDialog(Component parentComponent,
          String message, String initialSelectionValue)
  {

    // AnnotationPanel

    return isInteractiveMode()
            ? JOptionPane.showInputDialog(parentComponent, message,
                    initialSelectionValue)
            : getMockResponse().toString();
  }

  /**
   * input with initial selection
   * 
   * @param parentComponent
   * @param message
   * @param initialSelectionValue
   * @return
   */
  public static String showInputDialog(Component parentComponent,
          Object message, Object initialSelectionValue)
  {

    // AnnotationPanel

    return isInteractiveMode()
            ? JOptionPane.showInputDialog(parentComponent, message,
                    initialSelectionValue)
            : getMockResponse().toString();
  }

  /**
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @return
   * @throws HeadlessException
   */
  public static String showInputDialog(Component parentComponent,
          String message, String title, int messageType)
          throws HeadlessException
  {

    // test only

    return isInteractiveMode()
            ? JOptionPane.showInputDialog(parentComponent, message, title,
                    messageType)
            : getMockResponse().toString();
  }

  /**
   * Customized input option
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @param icon
   * @param selectionValues
   * @param initialSelectionValue
   * @return
   * @throws HeadlessException
   */
  public static Object showInputDialog(Component parentComponent,
          Object message, String title, int messageType, Icon icon,
          Object[] selectionValues, Object initialSelectionValue)
          throws HeadlessException
  {

    // test only

    return isInteractiveMode()
            ? JOptionPane.showInputDialog(parentComponent, message, title,
                    messageType, icon, selectionValues,
                    initialSelectionValue)
            : getMockResponse().toString();
  }

  /**
   * internal version
   * 
   * @param parentComponent
   * @param message
   * @return
   */
  public static String showInternalInputDialog(Component parentComponent,
          String message)
  {
    // test only

    return isInteractiveMode()
            ? JOptionPane.showInternalInputDialog(parentComponent, message)
            : getMockResponse().toString();
  }

  /**
   * internal with title and messageType
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @return
   */
  public static String showInternalInputDialog(Component parentComponent,
          String message, String title, int messageType)
  {

    // AlignFrame tabbedPane_mousePressed

    return isInteractiveMode()
            ? JOptionPane.showInternalInputDialog(parentComponent,
                    getPrefix(messageType) + message, title, messageType)
            : getMockResponse().toString();
  }

  /**
   * customized internal
   * 
   * @param parentComponent
   * @param message
   * @param title
   * @param messageType
   * @param icon
   * @param selectionValues
   * @param initialSelectionValue
   * @return
   */
  public static Object showInternalInputDialog(Component parentComponent,
          String message, String title, int messageType, Icon icon,
          Object[] selectionValues, Object initialSelectionValue)
  {
    // test only

    return isInteractiveMode()
            ? JOptionPane.showInternalInputDialog(parentComponent, message,
                    title, messageType, icon, selectionValues,
                    initialSelectionValue)
            : getMockResponse().toString();
  }

  ///////////// end of options ///////////////

  private static void outputMessage(Object message)
  {
    System.out.println(">>> JOption Message : " + message.toString());
  }

  public static Object getMockResponse()
  {
    return mockResponse;
  }

  public static void setMockResponse(Object mockOption)
  {
    JvOptionPane.mockResponse = mockOption;
  }

  public static void resetMock()
  {
    setMockResponse(JvOptionPane.CANCEL_OPTION);
    setInteractiveMode(true);
  }

  public static boolean isInteractiveMode()
  {
    return interactiveMode;
  }

  public static void setInteractiveMode(boolean interactive)
  {
    JvOptionPane.interactiveMode = interactive;
  }

  private static String getPrefix(int messageType)
  {
    String prefix = "";

    // JavaScript only
    if (Platform.isJS())
    {
      switch (messageType)
      {
      case JvOptionPane.WARNING_MESSAGE:
        prefix = "WARNING! ";
        break;
      case JvOptionPane.ERROR_MESSAGE:
        prefix = "ERROR! ";
        break;
      default:
        prefix = "Note: ";
      }
    }
    return prefix;
  }

  /**
   * create a new option dialog that can be used to register responses - along
   * lines of showOptionDialog
   * 
   * @param desktop
   * @param question
   * @param string
   * @param defaultOption
   * @param plainMessage
   * @param object
   * @param options
   * @param string2
   * @return
   */
  public static JvOptionPane newOptionDialog(Component parentComponent)
  {
    return new JvOptionPane(parentComponent);
  }

  public void showDialog(String message, String title, int optionType,
          int messageType, Icon icon, Object[] options, Object initialValue)
  {
    showDialog(message, title, optionType, messageType, icon, options,
            initialValue, true);
  }

  public void showDialog(String message, String title, int optionType,
          int messageType, Icon icon, Object[] options, Object initialValue,
          boolean modal)
  {
    if (!isInteractiveMode())
    {
      handleResponse(getMockResponse());
    }
    // two uses:
    //
    // TODO
    //
    // 1) AlignViewport for openLinkedAlignment
    //
    // Show a dialog with the option to open and link (cDNA <-> protein) as a
    // new
    // alignment, either as a standalone alignment or in a split frame. Returns
    // true if the new alignment was opened, false if not, because the user
    // declined the offer.
    //
    // 2) UserDefinedColors warning about saving over a name already defined
    //

    ourOptions = Arrays.asList(options);

    if (modal)
    {
      // use a JOptionPane as usual
      int response = JOptionPane.showOptionDialog(parentComponent, message,
              title, optionType, messageType, icon, options, initialValue);

      /*
       * In Java, the response is returned to this thread and handled here;
       * (for Javascript, see propertyChange)
       */
      if (!Platform.isJS())
      /**
       * Java only
       * 
       * @j2sIgnore
       */
      {
        handleResponse(response);
      }
    }
    else
    {
      /*
       * This is java similar to the swingjs handling, with the callbacks
       * attached to the button press of the dialog.  This means we can use
       * a non-modal JDialog for the confirmation without blocking the GUI.
       */
      JOptionPane joptionpane = new JOptionPane();
      // Make button options
      int[] buttonActions = { JvOptionPane.YES_OPTION,
          JvOptionPane.NO_OPTION, JvOptionPane.CANCEL_OPTION };

      // we need the strings to make the buttons with actionEventListener
      if (options == null)
      {
        ArrayList<String> options_default = new ArrayList<>();
        options_default
                .add(UIManager.getString("OptionPane.yesButtonText"));
        if (optionType == JvOptionPane.YES_NO_OPTION
                || optionType == JvOptionPane.YES_NO_CANCEL_OPTION)
        {
          options_default
                  .add(UIManager.getString("OptionPane.noButtonText"));
        }
        if (optionType == JvOptionPane.YES_NO_CANCEL_OPTION)
        {
          options_default
                  .add(UIManager.getString("OptionPane.cancelButtonText"));
        }
        options = options_default.toArray();
      }

      ArrayList<JButton> options_btns = new ArrayList<>();
      Object initialValue_btn = null;
      if (!Platform.isJS()) // JalviewJS already uses callback, don't need to add them here
      {
        for (int i = 0; i < options.length && i < 3; i++)
        {
          Object o = options[i];
          int buttonAction = buttonActions[i];
          Runnable action = callbacks.get(buttonAction);
          JButton jb = new JButton();
          jb.setText((String) o);
          jb.addActionListener(new ActionListener()
          {
            @Override
            public void actionPerformed(ActionEvent e)
            {
              joptionpane.setValue(buttonAction);
              if (action != null)
                Executors.defaultThreadFactory().newThread(action).start();
              // joptionpane.transferFocusBackward();
              joptionpane.transferFocusBackward();
              joptionpane.setVisible(false);
              // put focus and raise parent window if possible, unless cancel
              // button pressed
              boolean raiseParent = (parentComponent != null);
              if (buttonAction == JvOptionPane.CANCEL_OPTION)
                raiseParent = false;
              if (optionType == JvOptionPane.YES_NO_OPTION
                      && buttonAction == JvOptionPane.NO_OPTION)
                raiseParent = false;
              if (raiseParent)
              {
                parentComponent.requestFocus();
                if (parentComponent instanceof JInternalFrame)
                {
                  JInternalFrame jif = (JInternalFrame) parentComponent;
                  jif.show();
                  jif.moveToFront();
                  jif.grabFocus();
                }
                else if (parentComponent instanceof Window)
                {
                  Window w = (Window) parentComponent;
                  w.toFront();
                  w.requestFocus();
                }
              }
              joptionpane.setVisible(false);
            }
          });
          options_btns.add(jb);
          if (o.equals(initialValue))
            initialValue_btn = jb;
        }
      }
      joptionpane.setMessage(message);
      joptionpane.setMessageType(messageType);
      joptionpane.setOptionType(optionType);
      joptionpane.setIcon(icon);
      joptionpane.setOptions(
              Platform.isJS() ? options : options_btns.toArray());
      joptionpane.setInitialValue(
              Platform.isJS() ? initialValue : initialValue_btn);

      JDialog dialog = joptionpane.createDialog(parentComponent, title);
      dialog.setModalityType(modal ? ModalityType.APPLICATION_MODAL
              : ModalityType.MODELESS);
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setVisible(true);
    }
  }

  public void showInternalDialog(JPanel mainPanel, String title,
          int yesNoCancelOption, int questionMessage, Icon icon,
          Object[] options, String initresponse)
  {
    if (!isInteractiveMode())
    {
      handleResponse(getMockResponse());
    }

    ourOptions = Arrays.asList(options);
    int response;
    if (parentComponent != this)
    {
      response = JOptionPane.showInternalOptionDialog(parentComponent,
              mainPanel, title, yesNoCancelOption, questionMessage, icon,
              options, initresponse);
    }
    else
    {
      response = JOptionPane.showOptionDialog(parentComponent, mainPanel,
              title, yesNoCancelOption, questionMessage, icon, options,
              initresponse);
    }
    if (!Platform.isJS())
    /**
     * Java only
     * 
     * @j2sIgnore
     */
    {
      handleResponse(response);
    }
  }

  @Override
  public JvOptionPane setResponseHandler(Object response, Runnable action)
  {
    callbacks.put(response, action);
    return this;
  }

  /**
   * JalviewJS signals option selection by a property change event for the
   * option e.g. "OK". This methods responds to that by running the response
   * action that corresponds to that option.
   * 
   * @param evt
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt)
  {
    Object newValue = evt.getNewValue();
    int ourOption = ourOptions.indexOf(newValue);
    if (ourOption >= 0)
    {
      handleResponse(ourOption);
    }
    else
    {
      // try our luck..
      handleResponse(newValue);
    }
  }

  @Override
  public void handleResponse(Object response)
  {
    /*
    * this test is for NaN in Chrome
    */
    if (response != null && !response.equals(response))
    {
      return;
    }
    Runnable action = callbacks.get(response);
    if (action != null)
    {
      action.run();
      parentComponent.requestFocus();
    }
  }
}
