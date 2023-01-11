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
package jalview.io.gff;

import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.MappingType;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.util.MapList;
import jalview.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Base class with generic / common functionality for processing GFF3 data.
 * Override this as required for any specialisations resulting from
 * peculiarities of GFF3 generated by particular tools.
 */
public class Gff3Helper extends GffHelperBase
{
  public static final String ALLELES = "alleles";

  protected static final String TARGET = "Target";

  protected static final String ID = "ID";

  private static final String NAME = "Name";

  /**
   * GFF3 uses '=' to delimit name/value pairs in column 9, and comma to
   * separate multiple values for a name
   * 
   * @param text
   * @return
   */
  public static Map<String, List<String>> parseNameValuePairs(String text)
  {
    return parseNameValuePairs(text, ";", '=', ",");
  }

  /**
   * Process one GFF feature line (as modelled by SequenceFeature)
   * 
   * @param seq
   *          the sequence with which this feature is associated
   * @param sf
   *          the sequence feature with ATTRIBUTES property containing any
   *          additional attributes
   * @param align
   *          the alignment we are adding GFF to
   * @param newseqs
   *          any new sequences referenced by the GFF
   * @param relaxedIdMatching
   *          if true, match word tokens in sequence names
   * @return true if the sequence feature should be added to the sequence, else
   *         false (i.e. it has been processed in another way e.g. to generate a
   *         mapping)
   * @throws IOException
   */
  @Override
  public SequenceFeature processGff(SequenceI seq, String[] gff,
          AlignmentI align, List<SequenceI> newseqs,
          boolean relaxedIdMatching) throws IOException
  {
    SequenceFeature sf = null;

    if (gff.length == 9)
    {
      String soTerm = gff[TYPE_COL];
      String atts = gff[ATTRIBUTES_COL];
      Map<String, List<String>> attributes = parseNameValuePairs(atts);

      SequenceOntologyI so = SequenceOntologyFactory.getInstance();
      if (so.isA(soTerm, SequenceOntologyI.PROTEIN_MATCH))
      {
        sf = processProteinMatch(attributes, seq, gff, align, newseqs,
                relaxedIdMatching);
      }
      else if (so.isA(soTerm, SequenceOntologyI.NUCLEOTIDE_MATCH))
      {
        sf = processNucleotideMatch(attributes, seq, gff, align, newseqs,
                relaxedIdMatching);
      }
      else
      {
        sf = buildSequenceFeature(gff, attributes);
      }
    }
    else
    {
      /*
       * fall back on generating a sequence feature with no special processing
       */
      sf = buildSequenceFeature(gff, null);
    }

    return sf;
  }

  /**
   * Processes one GFF3 nucleotide (e.g. cDNA to genome) match.
   * 
   * @param attributes
   *          parsed GFF column 9 key/value(s)
   * @param seq
   *          the sequence the GFF feature is on
   * @param gffColumns
   *          the GFF column data
   * @param align
   *          the alignment the sequence belongs to, where any new mappings
   *          should be added
   * @param newseqs
   *          a list of new 'virtual sequences' generated while parsing GFF
   * @param relaxedIdMatching
   *          if true allow fuzzy search for a matching target sequence
   * @return a sequence feature, if one should be added to the sequence, else
   *         null
   * @throws IOException
   */
  protected SequenceFeature processNucleotideMatch(
          Map<String, List<String>> attributes, SequenceI seq,
          String[] gffColumns, AlignmentI align, List<SequenceI> newseqs,
          boolean relaxedIdMatching) throws IOException
  {
    String strand = gffColumns[STRAND_COL];

    /*
     * (For now) we don't process mappings from reverse complement ; to do
     * this would require (a) creating a virtual sequence placeholder for
     * the reverse complement (b) resolving the sequence by its id from some
     * source (GFF ##FASTA or other) (c) creating the reverse complement
     * sequence (d) updating the mapping to be to the reverse complement
     */
    if ("-".equals(strand))
    {
      System.err.println(
              "Skipping mapping from reverse complement as not yet supported");
      return null;
    }

    List<String> targets = attributes.get(TARGET);
    if (targets == null)
    {
      System.err.println("'Target' missing in GFF");
      return null;
    }

    /*
     * Typically we only expect one Target per GFF line, but this can handle
     * multiple matches, to the same or different sequences (e.g. dna variants)
     */
    for (String target : targets)
    {
      /*
       * Process "seqid start end [strand]"
       */
      String[] tokens = target.split(" ");
      if (tokens.length < 3)
      {
        System.err.println("Incomplete Target: " + target);
        continue;
      }

      /*
       * Locate the mapped sequence in the alignment, or as a 
       * (new or existing) virtual sequence in the newseqs list 
       */
      String targetId = findTargetId(tokens[0], attributes);
      SequenceI mappedSequence1 = findSequence(targetId, align, newseqs,
              relaxedIdMatching);
      SequenceI mappedSequence = mappedSequence1;
      if (mappedSequence == null)
      {
        continue;
      }

      /*
       * get any existing mapping for these sequences (or start one),
       * and add this mapped range
       */
      AlignedCodonFrame acf = getMapping(align, seq, mappedSequence);

      try
      {
        int toStart = Integer.parseInt(tokens[1]);
        int toEnd = Integer.parseInt(tokens[2]);
        if (tokens.length > 3 && "-".equals(tokens[3]))
        {
          // mapping to reverse strand - swap start/end
          int temp = toStart;
          toStart = toEnd;
          toEnd = temp;
        }

        int fromStart = Integer.parseInt(gffColumns[START_COL]);
        int fromEnd = Integer.parseInt(gffColumns[END_COL]);
        MapList mapping = constructMappingFromAlign(fromStart, fromEnd,
                toStart, toEnd, MappingType.NucleotideToNucleotide);

        if (mapping != null)
        {
          acf.addMap(seq, mappedSequence, mapping);
          align.addCodonFrame(acf);
        }
      } catch (NumberFormatException nfe)
      {
        System.err.println("Invalid start or end in Target " + target);
      }
    }

    SequenceFeature sf = buildSequenceFeature(gffColumns, attributes);
    return sf;
  }

  /**
   * Returns the target sequence id extracted from the GFF name/value pairs.
   * Default (standard behaviour) is the first token for "Target". This may be
   * overridden where tools report this in a non-standard way.
   * 
   * @param target
   *          first token of a "Target" value from GFF column 9, typically
   *          "seqid start end"
   * @param set
   *          a map with all parsed column 9 attributes
   * @return
   */
  @SuppressWarnings("unused")
  protected String findTargetId(String target,
          Map<String, List<String>> set)
  {
    return target;
  }

  /**
   * Processes one GFF 'protein_match'; fields of interest are
   * <ul>
   * <li>feature group - the database reporting a match e.g. Pfam</li>
   * <li>Name - the matched entry's accession id in the database</li>
   * <li>ID - a sequence identifier for the matched region (which may be
   * appended as FASTA in the GFF file)</li>
   * </ul>
   * 
   * @param set
   *          parsed GFF column 9 key/value(s)
   * @param seq
   *          the sequence the GFF feature is on
   * @param gffColumns
   *          the sequence feature holding GFF data
   * @param align
   *          the alignment the sequence belongs to, where any new mappings
   *          should be added
   * @param newseqs
   *          a list of new 'virtual sequences' generated while parsing GFF
   * @param relaxedIdMatching
   *          if true allow fuzzy search for a matching target sequence
   * @return the (real or virtual) sequence(s) mapped to by this match
   * @throws IOException
   */
  protected SequenceFeature processProteinMatch(
          Map<String, List<String>> set, SequenceI seq, String[] gffColumns,
          AlignmentI align, List<SequenceI> newseqs,
          boolean relaxedIdMatching)
  {
    // This is currently tailored to InterProScan GFF output:
    // ID holds the ID of the matched sequence, Target references the
    // query sequence; this looks wrong, as ID should just be the GFF internal
    // ID of the GFF feature, while Target would normally reference the matched
    // sequence.
    // TODO refactor as needed if other protein-protein GFF varies

    SequenceFeature sf = buildSequenceFeature(gffColumns, set);

    /*
     * locate the mapped sequence in the alignment, or as a 
     * (new or existing) virtual sequence in the newseqs list 
     */
    List<String> targets = set.get(TARGET);
    if (targets != null)
    {
      for (String target : targets)
      {

        SequenceI mappedSequence1 = findSequence(findTargetId(target, set),
                align, newseqs, relaxedIdMatching);
        SequenceI mappedSequence = mappedSequence1;
        if (mappedSequence == null)
        {
          continue;
        }

        /*
         * give the mapped sequence a copy of the sequence feature, with 
         * start/end range adjusted 
         */
        int sequenceFeatureLength = 1 + sf.getEnd() - sf.getBegin();
        SequenceFeature sf2 = new SequenceFeature(sf, 1,
                sequenceFeatureLength, sf.getFeatureGroup(), sf.getScore());
        mappedSequence.addSequenceFeature(sf2);

        /*
         * add a property to the mapped sequence so that it can eventually be
         * renamed with its qualified accession id; renaming has to wait until
         * all sequence reference resolution is complete
         */
        String accessionId = StringUtils
                .listToDelimitedString(set.get(NAME), ",");
        if (accessionId.length() > 0)
        {
          String database = sf.getType(); // TODO InterProScan only??
          String qualifiedAccId = database + "|" + accessionId;
          sf2.setValue(RENAME_TOKEN, qualifiedAccId);
        }

        /*
         * get any existing mapping for these sequences (or start one),
         * and add this mapped range
         */
        AlignedCodonFrame alco = getMapping(align, seq, mappedSequence);
        int[] from = new int[] { sf.getBegin(), sf.getEnd() };
        int[] to = new int[] { 1, sequenceFeatureLength };
        MapList mapping = new MapList(from, to, 1, 1);

        alco.addMap(seq, mappedSequence, mapping);
        align.addCodonFrame(alco);
      }
    }

    return sf;
  }

  /**
   * Modifies the default SequenceFeature in order to set the Target sequence id
   * as the description
   */
  @Override
  protected SequenceFeature buildSequenceFeature(String[] gff,
          int typeColumn, String group,
          Map<String, List<String>> attributes)
  {
    SequenceFeature sf = super.buildSequenceFeature(gff, typeColumn, group,
            attributes);
    String desc = getDescription(sf, attributes);
    if (desc != null)
    {
      sf.setDescription(desc);
    }
    return sf;
  }

  /**
   * Apply heuristic rules to try to get the most useful feature description
   * 
   * @param sf
   * @param attributes
   * @return
   */
  protected String getDescription(SequenceFeature sf,
          Map<String, List<String>> attributes)
  {
    String desc = null;
    String target = (String) sf.getValue(TARGET);
    if (target != null)
    {
      desc = target.split(" ")[0];
    }

    SequenceOntologyI so = SequenceOntologyFactory.getInstance();
    String type = sf.getType();
    if (so.isA(type, SequenceOntologyI.SEQUENCE_VARIANT))
    {
      /*
       * Ensembl returns dna variants as 'alleles'
       */
      desc = StringUtils.listToDelimitedString(attributes.get(ALLELES),
              ",");
    }

    /*
     * extract 'Name' for a transcript (to show gene name)
     * or an exon (so 'colour by label' shows exon boundaries) 
     */
    if (SequenceOntologyI.NMD_TRANSCRIPT_VARIANT.equals(type)
            || so.isA(type, SequenceOntologyI.TRANSCRIPT)
            || so.isA(type, SequenceOntologyI.EXON))
    {
      desc = StringUtils.listToDelimitedString(attributes.get("Name"), ",");
    }

    /*
     * if the above fails, try ID
     */
    if (desc == null)
    {
      desc = (String) sf.getValue(ID);
    }

    /*
     * and decode comma, equals, semi-colon as required by GFF3 spec
     */
    desc = StringUtils.urlDecode(desc, GFF_ENCODABLE);

    return desc;
  }
}
