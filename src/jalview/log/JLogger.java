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
package jalview.log;

import java.util.HashMap;
import java.util.Map;

import jalview.bin.Console;
import jalview.util.Platform;

public abstract class JLogger implements JLoggerI
{
  protected String name;

  protected LogLevel level;

  private Object logger = null;

  private static Map<String, JLogger> registry = new HashMap<>();

  // implement these abstract methods
  protected abstract void loggerSetup();

  public abstract boolean loggerExists();

  protected abstract void loggerSetLevel(LogLevel level);

  protected abstract void loggerLogMessage(LogLevel level, String message,
          Throwable t);

  public static LogLevel toLevel(String levelString)
  {
    try
    {
      return LogLevel.valueOf(levelString);
    } catch (IllegalArgumentException e)
    {
      Console.error("Could not parse LogLevel '" + levelString + "'", e);
      return LogLevel.INFO;
    }
  }

  public static JLogger getLogger(Class c)
  {
    return getLogger(c);
  }

  public static JLogger getLogger(Class c, LogLevel loglevel)
  {
    return getLogger(c.getCanonicalName(), loglevel);
  }

  public static JLogger getLogger(String name)
  {
    return getLogger(name, LogLevel.INFO);
  }

  public static JLogger getLogger(String name, LogLevel loglevel)
  {
    return registry.containsKey(name) ? (JLogger) registry.get(name) : null;
  }

  protected JLogger()
  {
  }

  protected JLogger(String name, LogLevel level)
  {
    this.name = name;
    this.level = level;
    this.loggerSetup();
    this.registryStore();
  }

  protected void registryStore()
  {
    registry.put(this.name, this);
  }

  protected static boolean registryContainsKey(String name)
  {
    return registry.containsKey(name);
  }

  protected static JLogger registryGet(String name)
  {
    return registry.get(name);
  }

  public LogLevel getLevel()
  {
    return this.level;
  }

  public void setLevel(LogLevel level)
  {
    this.level = level;
    if (loggerExists())
      loggerSetLevel(level);
  }

  private boolean println(LogLevel loglevel, String message, Throwable t)
  {
    if (loglevel.compareTo(this.level) < 0)
    {
      return false;
    }
    if (!loggerExists() || Platform.isJS())
    {
      String logLine = String.format("%s: %s", loglevel.toString(),
              message);
      System.out.println(logLine);
      if (t != null)
      {
        if (loglevel.compareTo(LogLevel.DEBUG) <= 0)
          t.printStackTrace(System.err);
        else
          System.err.println(t.getMessage());
      }
      return false;
    }
    else
    {
      loggerLogMessage(loglevel, message, t);
      return true;
    }
  }

  public void trace(String message)
  {
    trace(message, null);
  }

  public void trace(String message, Throwable t)
  {
    println(LogLevel.TRACE, message, t);
  }

  public void debug(String message)
  {
    debug(message, null);
  }

  public void debug(String message, Throwable t)
  {
    println(LogLevel.DEBUG, message, t);
  }

  public void info(String message)
  {
    info(message, null);
  }

  public void info(String message, Throwable t)
  {
    println(LogLevel.INFO, message, t);
  }

  public void warn(String message)
  {
    warn(message, null);
  }

  public void warn(String message, Throwable t)
  {
    println(LogLevel.WARN, message, t);
  }

  public void error(String message)
  {
    error(message, null);
  }

  public void error(String message, Throwable t)
  {
    println(LogLevel.ERROR, message, t);
  }

  public void fatal(String message)
  {
    fatal(message, null);
  }

  public void fatal(String message, Throwable t)
  {
    println(LogLevel.FATAL, message, t);
  }

  public boolean isDebugEnabled()
  {
    return level.compareTo(LogLevel.DEBUG) <= 0;
  }

  public boolean isTraceEnabled()
  {
    return level.compareTo(LogLevel.TRACE) <= 0;
  }
}
