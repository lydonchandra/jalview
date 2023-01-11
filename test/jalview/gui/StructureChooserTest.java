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

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Collection;
import java.util.Vector;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jalview.datamodel.DBRefEntry;
import jalview.datamodel.PDBEntry;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceI;
import jalview.fts.api.FTSData;
import jalview.fts.core.FTSRestClient;
import jalview.fts.service.pdb.PDBFTSRestClient;
import jalview.fts.service.pdb.PDBFTSRestClientTest;
import jalview.fts.service.threedbeacons.TDBeaconsFTSRestClient;
import jalview.fts.threedbeacons.TDBeaconsFTSRestClientTest;
import jalview.gui.structurechooser.PDBStructureChooserQuerySource;
import jalview.jbgui.FilterOption;
import junit.extensions.PA;

@Test(singleThreaded = true)
public class StructureChooserTest
{

  @BeforeClass(alwaysRun = true)
  public void setUpJvOptionPane()
  {
    JvOptionPane.setInteractiveMode(false);
    JvOptionPane.setMockResponse(JvOptionPane.CANCEL_OPTION);
  }

  Sequence seq, upSeq, upSeq_nocanonical;

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception
  {
    seq = new Sequence("PDB|4kqy|4KQY|A", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 1,
            26);
    seq.createDatasetSequence();
    for (int x = 1; x < 5; x++)
    {
      DBRefEntry dbRef = new DBRefEntry();
      dbRef.setAccessionId("XYZ_" + x);
      seq.addDBRef(dbRef);
    }

    PDBEntry dbRef = new PDBEntry();
    dbRef.setId("1tim");

    Vector<PDBEntry> pdbIds = new Vector<>();
    pdbIds.add(dbRef);

    seq.setPDBId(pdbIds);

    // Uniprot sequence for 3D-Beacons mocks
    upSeq = new Sequence("P38398",
            "MDLSALRVEEVQNVINAMQKILECPICLELIKEPVSTKCDHIFCKFCMLKLLNQKKGPSQCPLCKNDITKRS\n"
                    + "LQESTRFSQLVEELLKIICAFQLDTGLEYANSYNFAKKENNSPEHLKDEVSIIQSMGYRNRAKRLLQSEPEN\n"
                    + "PSLQETSLSVQLSNLGTVRTLRTKQRIQPQKTSVYIELGSDSSEDTVNKATYCSVGDQELLQITPQGTRDEI\n"
                    + "SLDSAKKAACEFSETDVTNTEHHQPSNNDLNTTEKRAAERHPEKYQGSSVSNLHVEPCGTNTHASSLQHENS\n"
                    + "SLLLTKDRMNVEKAEFCNKSKQPGLARSQHNRWAGSKETCNDRRTPSTEKKVDLNADPLCERKEWNKQKLPC\n"
                    + "SENPRDTEDVPWITLNSSIQKVNEWFSRSDELLGSDDSHDGESESNAKVADVLDVLNEVDEYSGSSEKIDLL\n"
                    + "ASDPHEALICKSERVHSKSVESNIEDKIFGKTYRKKASLPNLSHVTENLIIGAFVTEPQIIQERPLTNKLKR\n"
                    + "KRRPTSGLHPEDFIKKADLAVQKTPEMINQGTNQTEQNGQVMNITNSGHENKTKGDSIQNEKNPNPIESLEK\n"
                    + "ESAFKTKAEPISSSISNMELELNIHNSKAPKKNRLRRKSSTRHIHALELVVSRNLSPPNCTELQIDSCSSSE\n"
                    + "EIKKKKYNQMPVRHSRNLQLMEGKEPATGAKKSNKPNEQTSKRHDSDTFPELKLTNAPGSFTKCSNTSELKE\n"
                    + "FVNPSLPREEKEEKLETVKVSNNAEDPKDLMLSGERVLQTERSVESSSISLVPGTDYGTQESISLLEVSTLG\n"
                    + "KAKTEPNKCVSQCAAFENPKGLIHGCSKDNRNDTEGFKYPLGHEVNHSRETSIEMEESELDAQYLQNTFKVS\n"
                    + "KRQSFAPFSNPGNAEEECATFSAHSGSLKKQSPKVTFECEQKEENQGKNESNIKPVQTVNITAGFPVVGQKD\n"
                    + "KPVDNAKCSIKGGSRFCLSSQFRGNETGLITPNKHGLLQNPYRIPPLFPIKSFVKTKCKKNLLEENFEEHSM\n"
                    + "SPEREMGNENIPSTVSTISRNNIRENVFKEASSSNINEVGSSTNEVGSSINEIGSSDENIQAELGRNRGPKL\n"
                    + "NAMLRLGVLQPEVYKQSLPGSNCKHPEIKKQEYEEVVQTVNTDFSPYLISDNLEQPMGSSHASQVCSETPDD\n"
                    + "LLDDGEIKEDTSFAENDIKESSAVFSKSVQKGELSRSPSPFTHTHLAQGYRRGAKKLESSEENLSSEDEELP\n"
                    + "CFQHLLFGKVNNIPSQSTRHSTVATECLSKNTEENLLSLKNSLNDCSNQVILAKASQEHHLSEETKCSASLF\n"
                    + "SSQCSELEDLTANTNTQDPFLIGSSKQMRHQSESQGVGLSDKELVSDDEERGTGLEENNQEEQSMDSNLGEA\n"
                    + "ASGCESETSVSEDCSGLSSQSDILTTQQRDTMQHNLIKLQQEMAELEAVLEQHGSQPSNSYPSIISDSSALE\n"
                    + "DLRNPEQSTSEKAVLTSQKSSEYPISQNPEGLSADKFEVSADSSTSKNKEPGVERSSPSKCPSLDDRWYMHS\n"
                    + "CSGSLQNRNYPSQEELIKVVDVEEQQLEESGPHDLTETSYLPRQDLEGTPYLESGISLFSDDPESDPSEDRA\n"
                    + "PESARVGNIPSSTSALKVPQLKVAESAQSPAAAHTTDTAGYNAMEESVSREKPELTASTERVNKRMSMVVSG\n"
                    + "LTPEEFMLVYKFARKHHITLTNLITEETTHVVMKTDAEFVCERTLKYFLGIAGGKWVVSYFWVTQSIKERKM\n"
                    + "LNEHDFEVRGDVVNGRNHQGPKRARESQDRKIFRGLEICCYGPFTNMPTDQLEWMVQLCGASVVKELSSFTL\n"
                    + "GTGVHPIVVVQPDAWTEDNGFHAIGQMCEAPVVTREWVLDSVALYQCQELDTYLIPQIPHSHY\n"
                    + "",
            1, 1863);
    upSeq.setDescription("Breast cancer type 1 susceptibility protein");
    upSeq_nocanonical = new Sequence(upSeq);
    upSeq.createDatasetSequence();
    upSeq.addDBRef(new DBRefEntry("UNIPROT", "0", "P38398", null, true));

    upSeq_nocanonical.createDatasetSequence();
    // not a canonical reference
    upSeq_nocanonical.addDBRef(
            new DBRefEntry("UNIPROT", "0", "P38398", null, false));

  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception
  {
    seq = null;
    upSeq = null;
    upSeq_nocanonical = null;
  }

  @Test(groups = { "Functional" })
  public void populateFilterComboBoxTest() throws InterruptedException
  {
    TDBeaconsFTSRestClientTest.setMock();
    PDBFTSRestClientTest.setMock();

    SequenceI[] selectedSeqs = new SequenceI[] { seq };
    StructureChooser sc = new StructureChooser(selectedSeqs, seq, null);
    ThreadwaitFor(200, sc);

    // if structures are not discovered then don't
    // populate filter options
    sc.populateFilterComboBox(false, false);
    int optionsSize = sc.getCmbFilterOption().getItemCount();
    System.out.println("Items (no data, no cache): ");
    StringBuilder items = new StringBuilder();
    for (int p = 0; p < optionsSize; p++)
    {
      items.append("- ")
              .append(sc.getCmbFilterOption().getItemAt(p).getName())
              .append("\n");

    }
    // report items when this fails - seems to be a race condition
    Assert.assertEquals(items.toString(), optionsSize, 2);

    sc.populateFilterComboBox(true, false);
    optionsSize = sc.getCmbFilterOption().getItemCount();
    assertTrue(optionsSize > 3); // if structures are found, filter options
                                 // should be populated

    sc.populateFilterComboBox(true, true);
    assertTrue(sc.getCmbFilterOption().getSelectedItem() != null);
    FilterOption filterOpt = (FilterOption) sc.getCmbFilterOption()
            .getSelectedItem();
    assertEquals("Cached Structures", filterOpt.getName());
    FTSRestClient
            .unMock((FTSRestClient) TDBeaconsFTSRestClient.getInstance());
    FTSRestClient.unMock((FTSRestClient) PDBFTSRestClient.getInstance());

  }

  @Test(groups = { "Functional" })
  public void displayTDBQueryTest() throws InterruptedException
  {
    TDBeaconsFTSRestClientTest.setMock();
    PDBFTSRestClientTest.setMock();

    SequenceI[] selectedSeqs = new SequenceI[] { upSeq_nocanonical };
    StructureChooser sc = new StructureChooser(selectedSeqs,
            upSeq_nocanonical, null);
    // mock so should be quick. Exceptions from mocked PDBFTS are expected too
    ThreadwaitFor(500, sc);

    assertTrue(sc.isCanQueryTDB() && sc.isNotQueriedTDBYet());
  }

  @Test(groups = { "Network" })
  public void fetchStructuresInfoTest()
  {
    FTSRestClient
            .unMock((FTSRestClient) TDBeaconsFTSRestClient.getInstance());
    PDBFTSRestClient.unMock((FTSRestClient) PDBFTSRestClient.getInstance());
    SequenceI[] selectedSeqs = new SequenceI[] { seq };
    StructureChooser sc = new StructureChooser(selectedSeqs, seq, null);
    // not mocked, wait for 2s
    ThreadwaitFor(2000, sc);

    sc.fetchStructuresMetaData();
    Collection<FTSData> ss = (Collection<FTSData>) PA.getValue(sc,
            "discoveredStructuresSet");
    assertNotNull(ss);
    assertTrue(ss.size() > 0);
  }

  @Test(groups = { "Functional" })
  public void fetchStructuresInfoMockedTest()
  {
    TDBeaconsFTSRestClientTest.setMock();
    PDBFTSRestClientTest.setMock();
    SequenceI[] selectedSeqs = new SequenceI[] { upSeq };
    StructureChooser sc = new StructureChooser(selectedSeqs, seq, null);
    ThreadwaitFor(500, sc);

    sc.fetchStructuresMetaData();
    Collection<FTSData> ss = (Collection<FTSData>) PA.getValue(sc,
            "discoveredStructuresSet");
    assertNotNull(ss);
    assertTrue(ss.size() > 0);
  }

  private void ThreadwaitFor(int i, StructureChooser sc)
  {
    long timeout = i + System.currentTimeMillis();
    while (!sc.isDialogVisible() && timeout > System.currentTimeMillis())
    {
      try
      {
        Thread.sleep(50);
      } catch (InterruptedException x)
      {

      }
    }

  }

  @Test(groups = { "Functional" })
  public void sanitizeSeqNameTest()
  {
    String name = "ab_cdEF|fwxyz012349";
    assertEquals(name,
            PDBStructureChooserQuerySource.sanitizeSeqName(name));

    // remove a [nn] substring
    name = "abcde12[345]fg";
    assertEquals("abcde12fg",
            PDBStructureChooserQuerySource.sanitizeSeqName(name));

    // remove characters other than a-zA-Z0-9 | or _
    name = "ab[cd],.\t£$*!- \\\"@:e";
    assertEquals("abcde",
            PDBStructureChooserQuerySource.sanitizeSeqName(name));

    name = "abcde12[345a]fg";
    assertEquals("abcde12345afg",
            PDBStructureChooserQuerySource.sanitizeSeqName(name));
  }
}
