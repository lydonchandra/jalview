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
package jalview.ext.ensembl;

import java.util.Locale;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceDummy;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.gui.JvOptionPane;
import jalview.io.gff.SequenceOntologyFactory;
import jalview.io.gff.SequenceOntologyLite;
import jalview.util.MapList;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EnsemblCdnaTest
{

  @BeforeClass(alwaysRun = true)
  public void setUpJvOptionPane()
  {
    JvOptionPane.setInteractiveMode(false);
    JvOptionPane.setMockResponse(JvOptionPane.CANCEL_OPTION);
  }

  @BeforeClass(alwaysRun = true)
  public void setUp()
  {
    SequenceOntologyFactory.setInstance(new SequenceOntologyLite());
  }

  @AfterClass(alwaysRun = true)
  public void tearDown()
  {
    SequenceOntologyFactory.setInstance(null);
  }

  /**
   * Test that the cdna part of genomic sequence is correctly identified by
   * 'exon' features (or subtypes) - reverse strand case.
   */
  @Test(groups = "Functional")
  public void testGetGenomicRangesFromFeatures_reverseStrand()
  {
    EnsemblCdna testee = new EnsemblCdna();
    SequenceI genomic = new SequenceDummy("chr7");
    genomic.setStart(10000);
    genomic.setEnd(50000);
    String transcriptId = "ABC123";

    // exon at (start+10000) length 501
    SequenceFeature sf = new SequenceFeature("exon", "", 20000, 20500, 0f,
            null);
    sf.setValue("Parent", transcriptId);
    sf.setStrand("-");
    genomic.addSequenceFeature(sf);

    // exon (sub-type) at (start + exon_variant) length 101
    sf = new SequenceFeature("coding_exon", "", 10500, 10600, 0f, null);
    sf.setValue("Parent", transcriptId);
    sf.setStrand("-");
    genomic.addSequenceFeature(sf);

    // exon belonging to a different transcript doesn't count
    sf = new SequenceFeature("exon", "", 11500, 12600, 0f, null);
    sf.setValue("Parent", "anotherOne");
    genomic.addSequenceFeature(sf);

    // transcript feature doesn't count
    sf = new SequenceFeature("transcript", "", 10000, 50000, 0f, null);
    genomic.addSequenceFeature(sf);

    MapList ranges = testee.getGenomicRangesFromFeatures(genomic,
            transcriptId, 23);
    List<int[]> fromRanges = ranges.getFromRanges();
    assertEquals(2, fromRanges.size());

    /*
     * from ranges should be sorted by start order descending
     * and hold mappings from reverse strand sense
     */
    assertEquals(20500, fromRanges.get(0)[0]);
    assertEquals(20000, fromRanges.get(0)[1]);
    assertEquals(10600, fromRanges.get(1)[0]);
    assertEquals(10500, fromRanges.get(1)[1]);
    // to range should start from given start numbering
    List<int[]> toRanges = ranges.getToRanges();
    assertEquals(1, toRanges.size());
    assertEquals(23, toRanges.get(0)[0]);
    assertEquals(624, toRanges.get(0)[1]);
  }

  /**
   * Test that the cdna part of genomic sequence is correctly identified by
   * 'exon' features (or subtypes) with the desired transcript as parent
   */
  @Test(groups = "Functional")
  public void testGetGenomicRangesFromFeatures()
  {
    EnsemblCdna testee = new EnsemblCdna();
    SequenceI genomic = new SequenceDummy("chr7");
    genomic.setStart(10000);
    genomic.setEnd(50000);
    String transcriptId = "ABC123";

    // exon at (start+10000) length 501
    SequenceFeature sf = new SequenceFeature("exon", "", 20000, 20500, 0f,
            null);
    sf.setValue("Parent", transcriptId);
    sf.setStrand("+");
    genomic.addSequenceFeature(sf);

    // exon (sub-type) at (start + exon_variant) length 101
    sf = new SequenceFeature("coding_exon", "", 10500, 10600, 0f, null);
    sf.setValue("Parent", transcriptId);
    sf.setStrand("+");
    genomic.addSequenceFeature(sf);

    // exon belonging to a different transcript doesn't count
    sf = new SequenceFeature("exon", "", 11500, 12600, 0f, null);
    sf.setValue("Parent", "anotherOne");
    genomic.addSequenceFeature(sf);

    // transcript feature doesn't count
    sf = new SequenceFeature("transcript", "", 10000, 50000, 0f, null);
    sf.setStrand("-"); // weird but ignored
    genomic.addSequenceFeature(sf);

    MapList ranges = testee.getGenomicRangesFromFeatures(genomic,
            transcriptId, 23);
    List<int[]> fromRanges = ranges.getFromRanges();
    assertEquals(2, fromRanges.size());
    // from ranges should be sorted by start order
    assertEquals(10500, fromRanges.get(0)[0]);
    assertEquals(10600, fromRanges.get(0)[1]);
    assertEquals(20000, fromRanges.get(1)[0]);
    assertEquals(20500, fromRanges.get(1)[1]);
    // to range should start from given start numbering
    List<int[]> toRanges = ranges.getToRanges();
    assertEquals(1, toRanges.size());
    assertEquals(23, toRanges.get(0)[0]);
    assertEquals(624, toRanges.get(0)[1]);
  }

  /**
   * The method under test should give up and return null if both forward and
   * reverse strands are present in the features of interest
   */
  @Test(groups = "Functional")
  public void testGetGenomicRangesFromFeatures_mixedStrand()
  {
    EnsemblCdna testee = new EnsemblCdna();
    SequenceI genomic = new SequenceDummy("chr7");
    genomic.setStart(10000);
    genomic.setEnd(50000);
    String transcriptId = "ABC123";

    SequenceFeature sf = new SequenceFeature("exon", "", 20000, 20500, 0f,
            null);
    sf.setValue("Parent", "transcript:" + transcriptId);
    sf.setStrand("-");
    genomic.addSequenceFeature(sf);

    sf = new SequenceFeature("coding_exon", "", 10500, 10600, 0f, null);
    sf.setValue("Parent", "transcript:" + transcriptId);
    sf.setStrand("+");
    genomic.addSequenceFeature(sf);

    MapList ranges = testee.getGenomicRangesFromFeatures(genomic,
            transcriptId, 23);
    assertNull(ranges);
  }

  /**
   * Test the method that retains features except for 'transcript' (or
   * subtypes), or features with parent other than the given id
   */
  @Test(groups = "Functional")
  public void testRetainFeature()
  {
    String accId = "ABC123";
    EnsemblCdna testee = new EnsemblCdna();

    SequenceFeature sf = new SequenceFeature("transcript", "", 20000, 20500,
            0f, null);
    assertFalse(testee.retainFeature(sf, accId));

    sf = new SequenceFeature("aberrant_processed_transcript", "", 20000,
            20500, 0f, null);
    assertFalse(testee.retainFeature(sf, accId));

    sf = new SequenceFeature("NMD_transcript_variant", "", 20000, 20500, 0f,
            null);
    assertFalse(testee.retainFeature(sf, accId));

    // other feature with no parent is retained
    sf = new SequenceFeature("sequence_variant", "", 20000, 20500, 0f,
            null);
    assertTrue(testee.retainFeature(sf, accId));

    // other feature with desired parent is retained
    sf.setValue("Parent", accId);
    assertTrue(testee.retainFeature(sf, accId));

    // test is not case-sensitive
    assertTrue(testee.retainFeature(sf, accId.toLowerCase(Locale.ROOT)));

    // feature with wrong parent is not retained
    sf.setValue("Parent", "XYZ");
    assertFalse(testee.retainFeature(sf, accId));
  }

  /**
   * Test the method that picks out 'exon' (or subtype) features with the
   * accession id as parent
   */
  @Test(groups = "Functional")
  public void testGetIdentifyingFeatures()
  {
    String accId = "ABC123";
    SequenceI seq = new Sequence(accId, "MKLNFRQIE");

    // exon with no parent: not valid
    SequenceFeature sf1 = new SequenceFeature("exon", "", 1, 2, 0f, null);
    seq.addSequenceFeature(sf1);

    // exon with wrong parent: not valid
    SequenceFeature sf2 = new SequenceFeature("exon", "", 1, 2, 0f, null);
    sf2.setValue("Parent", "XYZ");
    seq.addSequenceFeature(sf2);

    // exon with right parent is valid
    SequenceFeature sf3 = new SequenceFeature("exon", "", 1, 2, 0f, null);
    sf3.setValue("Parent", accId);
    seq.addSequenceFeature(sf3);

    // exon sub-type with right parent is valid
    SequenceFeature sf4 = new SequenceFeature("coding_exon", "", 1, 2, 0f,
            null);
    sf4.setValue("Parent", accId);
    seq.addSequenceFeature(sf4);

    // transcript not valid:
    SequenceFeature sf5 = new SequenceFeature("transcript", "", 1, 2, 0f,
            null);
    sf5.setValue("Parent", accId);
    seq.addSequenceFeature(sf5);

    // CDS not valid:
    SequenceFeature sf6 = new SequenceFeature("transcript", "", 1, 2, 0f,
            null);
    sf6.setValue("Parent", accId);
    seq.addSequenceFeature(sf6);

    List<SequenceFeature> sfs = new EnsemblCdna()
            .getIdentifyingFeatures(seq, accId);
    assertFalse(sfs.contains(sf1));
    assertFalse(sfs.contains(sf2));
    assertTrue(sfs.contains(sf3));
    assertTrue(sfs.contains(sf4));
    assertFalse(sfs.contains(sf5));
    assertFalse(sfs.contains(sf6));
  }

  @Test(groups = "Functional")
  public void testIsValidReference() throws Exception
  {
    EnsemblSequenceFetcher esq = new EnsemblCdna();
    Assert.assertTrue(esq.isValidReference("CCDS5863.1"));
    Assert.assertTrue(esq.isValidReference("ENST00000288602"));
    Assert.assertTrue(esq.isValidReference("ENSG00000288602"));
    Assert.assertFalse(esq.isValidReference("ENSP00000288602"));
    Assert.assertFalse(esq.isValidReference("ENST0000288602"));
    // non-human species having a 3 character identifier included:
    Assert.assertTrue(esq.isValidReference("ENSMUSG00000099398"));
  }
}
