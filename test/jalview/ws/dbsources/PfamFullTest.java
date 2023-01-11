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
package jalview.ws.dbsources;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jalview.bin.Cache;

public class PfamFullTest
{
  @BeforeClass(alwaysRun = true)
  public void setUp()
  {
    Cache.loadProperties("test/jalview/io/testProps.jvprops");
  }

  @Test(groups = "Functional")
  public void testGetURL()
  {
    String path = "www.ebi.ac.uk/interpro/api/entry/pfam/ABC/?annotation=alignment:full";

    // with default value for domain
    String url = new PfamFull().getURL(" abc ");
    assertEquals(url, "https://" + path);

    // with override in properties
    Cache.setProperty(Pfam.PFAM_BASEURL_KEY,
            "http://www.ebi.ac.uk/interpro/api/entry/pfam/$PFAMID$/?annotation=alignment:$ALTYPE$");
    url = new PfamFull().getURL(" abc ");
    assertEquals(url, "http://" + path);
  }
}
