package test;
import jalview.gui.JvOptionPane;
import jalview.util.JSONUtils;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import org.json.simple.parser.ParseException;

/**
 * A class with a main method entry point for ad hoc tests of JalviewJS
 * behaviour. The J2S transpiler should generate an html entry point for this
 * class, allowing comparison between Java and Javascript execution.
 */
public class JalviewJSTest extends JPanel
{
  public static void main(String[] args)
  {
    new JalviewJSTest().doTest6();
  }
  
  void doTest6()
  {
    /*
     * check for transpiler fix associated with JSONParser yylex.java use of charAt()
     * instead of codePointAt(); moved here from PDBFTSRestClient
     */

    String s = "e";
    char c = 'c';
    char f = 'f';
    s += c | f;
    int x = c & f;
    int y = 2 & c;
    int z = c ^ 5;
    String result = s + x + y + z;
    System.out.println("Expected " + "e103982102, found " + result);
    try
    {
      Map<String, Object> jsonObj = (Map<String, Object>) JSONUtils
              .parse("{\"a\":3}");
      System.out.println(jsonObj);
    } catch (ParseException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Dialog message truncation
   */
  void doTest5()
  {
    JFrame main = new JFrame();
    main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    main.setContentPane(new JPanel());
    main.setMinimumSize(new Dimension(100,  100));
    main.pack();
    main.setVisible(true);
    
    /*
     * like AlignFrame.delete_actionPerformed()
     * except here it works fine, also in JS
     */
    JvOptionPane dialog = JvOptionPane.newOptionDialog(null);
    Object[] options = new Object[] { "OK", "Cancel" };
	String msg = "<html>Deleting all sequences will close the alignment window.<br>Confirm deletion or Cancel.";
	dialog.showDialog(msg, "", JvOptionPane.DEFAULT_OPTION, JvOptionPane.PLAIN_MESSAGE, null, options, options[0]);

  }

  void doTest4() 
  {
	  Float fl = new Float(0f);
	  System.out.println(fl.isInfinite());
	  System.out.println(fl.isNaN());

	  System.out.println(Float.isInfinite(0f));
	  System.out.println(Float.isFinite(0f));
	  System.out.println(Float.isNaN(0f));
  }

  void doTest3() 
  {
	    System.out.println("Mungo".toLowerCase(Locale.getDefault()));
	    System.out.println("Mungo".toLowerCase(Locale.ENGLISH));
    System.out.println("Mungo".toLowerCase(Locale.ROOT));
  }
  
  void doTest2() {
	  Map<File, String> map = new HashMap<>();
	  File f1 = new File("/var/folders/y/xyz");
	  File f2 = new File("/var/folders/y/xyz");
	  map.put(f1,  "hello world");
	  System.out.println("f1.equals(f2) = " + f1.equals(f2));
	  System.out.println("f1 hashCode = " + f1.hashCode());
	  System.out.println("f2 hashCode = " + f2.hashCode());
	  System.out.println(map.get(f2));
  }
  
  /**
   * Put some content in a JFrame and show it
   */
  void doTest1()
  {
	  System.out.println("ab;c;".split(";"));
    new DecimalFormat("###,###").format((Integer) 1);
    JFrame main = new JFrame();
    main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JMenu menu = new JMenu("testing");
    menu.setHorizontalAlignment(SwingConstants.RIGHT);
    main.setContentPane(getVisualPaneContent(menu));
    main.setJMenuBar(new JMenuBar());
    main.getJMenuBar().add(menu);
    main.pack();
    main.setVisible(true);
  }

  /**
   * Builds a cut-down 'Preferences Visual tab' for a minimal test of layout
   * problems
 * @param menu 
   */
  Container getVisualPaneContent(JMenu menu)
  {
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(400, 500));
    panel.setOpaque(true);
    panel.setLayout(new BorderLayout());

    JPanel firstColumn = new JPanel();
    firstColumn.setLayout(new GridLayout(10, 1));
    firstColumn.setBorder(new TitledBorder("column 1"));

    /*
     * bug 21/08/18:
     * - checkbox label and text extend outside the enclosing panel in JS
     */
    Font font = new Font("Verdana", Font.PLAIN, 11);

    JLabel l1 = new JLabel(getImage("test2.png"));
    l1.setText("trailing right");
    l1.setHorizontalTextPosition(SwingConstants.TRAILING);
    l1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    l1.setHorizontalAlignment(SwingConstants.RIGHT);

    JLabel l2 = new JLabel(getImage("test2.png"));
    l2.setText("leading left");
    l2.setFont(font);
    l2.setHorizontalTextPosition(SwingConstants.LEADING);
    l2.setHorizontalAlignment(SwingConstants.LEFT);

    JButton b1 = new JButton("right left");
    b1.setIcon(getImage("test2.png"));
    b1.setFont(font);
    b1.setHorizontalTextPosition(SwingConstants.RIGHT);
    b1.setHorizontalAlignment(SwingConstants.LEFT);

    firstColumn.add(l1);
    firstColumn.add(l2);
    firstColumn.add(b1);

    
    JCheckBox cb3 = new JCheckBox("leading,left-to-right,rt");
    cb3.setFont(font);
    cb3.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    cb3.setHorizontalTextPosition(SwingConstants.LEADING);
    cb3.setHorizontalAlignment(SwingConstants.TRAILING);

    JCheckBox cb4 = new JCheckBox("leading,right-to-left");
    cb4.setFont(font);
    cb4.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    cb4.setHorizontalTextPosition(SwingConstants.LEADING);

    JCheckBox cb5 = new JCheckBox("trailing,left-to-right");
    cb5.setFont(font);
    cb5.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    cb5.setHorizontalTextPosition(SwingConstants.TRAILING);

    JRadioButton rb1 = new JRadioButton("trailing,right-to-left");
    rb1.setFont(font);
    rb1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    rb1.setHorizontalTextPosition(SwingConstants.TRAILING);

    JRadioButton rb2 = new JRadioButton("right,left-to-right");
    rb2.setFont(font);
    rb2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    rb2.setHorizontalTextPosition(SwingConstants.RIGHT);

    JRadioButton rb3 = new JRadioButton("right,right-to-left");
    rb3.setFont(font);
    rb3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    rb3.setHorizontalTextPosition(SwingConstants.RIGHT);


    
    firstColumn.add(cb3);
    firstColumn.add(cb4);
    firstColumn.add(cb5);
    firstColumn.add(rb1);
    firstColumn.add(rb2);
    firstColumn.add(rb3);
    firstColumn.setBounds(200, 20, 200, 500);

    JCheckBoxMenuItem cb3m = new JCheckBoxMenuItem("leading,left-to-right");
    cb3m.setFont(font);
    cb3m.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    cb3m.setHorizontalTextPosition(SwingConstants.LEADING);

    JCheckBoxMenuItem cb4m = new JCheckBoxMenuItem("leading,right-to-left");
    cb4m.setFont(font);
    cb4m.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    cb4m.setHorizontalTextPosition(SwingConstants.LEADING);

    JCheckBoxMenuItem cb5m = new JCheckBoxMenuItem("trailing,left-to-right");
    cb5m.setFont(font);
    cb5m.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    cb5m.setHorizontalTextPosition(SwingConstants.TRAILING);

    JCheckBoxMenuItem cb6m = new JCheckBoxMenuItem("trailing,right-to-left");
    cb6m.setFont(font);
    cb6m.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    cb6m.setHorizontalTextPosition(SwingConstants.TRAILING);

    JRadioButtonMenuItem rb1m = new JRadioButtonMenuItem("trailing,right-to-left");
    rb1m.setFont(font);
    rb1m.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    rb1m.setHorizontalTextPosition(SwingConstants.TRAILING);

    JRadioButtonMenuItem rb2m = new JRadioButtonMenuItem("right,left-to-right");
    rb2m.setFont(font);
    rb2m.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    rb2m.setHorizontalTextPosition(SwingConstants.RIGHT);

    JRadioButtonMenuItem rb3m = new JRadioButtonMenuItem("right,right-to-left");
    rb3m.setFont(font);
    rb3m.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    rb3m.setHorizontalTextPosition(SwingConstants.RIGHT);

    JMenu m1 = new JMenu("left");
    JMenu m2 = new JMenu("right");
    menu.add(m1);
    menu.add(m2);
    m1.add(cb3m);
    m2.add(cb4m);
    m1.add(cb5m);
    m2.add(cb6m);
    m2.add(rb1m);
    m1.add(rb2m);
    m2.add(rb3m);
    
    JPanel theTab = new JPanel();
    
    theTab.setLayout(null);
    theTab.add(firstColumn);
    panel.add(theTab);

    return panel;
  }

private ImageIcon getImage(String name) {
    ImageIcon icon = new ImageIcon(getClass().getResource(name));

    while(icon.getImageLoadStatus() == MediaTracker.LOADING)
    {
      try {
      	Thread.sleep(10);
      } catch (InterruptedException e) {
      }
    }
    return icon;
}
}
