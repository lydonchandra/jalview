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
package jalview.io.cache;

import java.util.LinkedHashSet;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JvCacheableInputBoxTest
{

  private AppCache appCache;

  private static final String TEST_CACHE_KEY = "CACHE.UNIT_TEST";

  private JvCacheableInputBox<String> cacheBox = new JvCacheableInputBox<>(
          TEST_CACHE_KEY, 20);

  @BeforeClass(alwaysRun = true)
  private void setUpCache()
  {
    appCache = AppCache.getInstance();
  }

  @Test(groups = { "Functional" })
  public void getUserInputTest()
  {
    String userInput = cacheBox.getUserInput();
    Assert.assertEquals("", userInput);

    String testInput = "TestInput";
    cacheBox.addItem(testInput);
    cacheBox.setSelectedItem(testInput);

    try
    {
      // This delay is essential to prevent the
      // assertion below from executing before
      // swing thread finishes updating the combo-box
      Thread.sleep(100);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    userInput = cacheBox.getUserInput();
    Assert.assertEquals(testInput, userInput);
  }

  @Test(groups = { "Functional" })
  public void updateCacheTest()
  {
    String testInput = "TestInput";
    cacheBox.addItem(testInput);
    cacheBox.setSelectedItem(testInput);
    cacheBox.updateCache();
    try
    {
      // This delay is to let
      // cacheBox.updateCache() finish updating the cache
      Thread.sleep(200);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    LinkedHashSet<String> foundCache = appCache
            .getAllCachedItemsFor(TEST_CACHE_KEY);
    Assert.assertTrue(foundCache.contains(testInput));
  }
}
