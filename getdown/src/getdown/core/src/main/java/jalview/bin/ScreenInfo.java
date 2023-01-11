package jalview.bin;

import java.awt.Toolkit;

public class ScreenInfo
{
  public int getScreenResolution()
  {
    return Toolkit.getDefaultToolkit().getScreenResolution();
  }

  public int getScreenHeight()
  {
    return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
  }

  public int getScreenWidth()
  {
    return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
  }
}
