
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

import jalview.api.FeatureSettingsModelI;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.DBRefSource;
import jalview.datamodel.PDBEntry;
import jalview.datamodel.PDBEntry.Type;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.datamodel.features.SequenceFeaturesI;
import jalview.io.DataSourceType;
import jalview.io.FileFormat;
import jalview.io.FileFormatI;
import jalview.io.FormatAdapter;
import jalview.io.PDBFeatureSettings;
import jalview.structure.StructureImportSettings;
import jalview.util.HttpUtils;
import jalview.util.MessageManager;
import jalview.ws.ebi.EBIFetchClient;
import jalview.ws.utils.UrlDownloadClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.stevesoft.pat.Regex;

/**
 * @author JimP
 * 
 */
public class EBIAlfaFold extends EbiFileRetrievedProxy
{
  private static final String SEPARATOR = "|";

  private static final String COLON = ":";

  private static final int PDB_ID_LENGTH = 4;

  public EBIAlfaFold()
  {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getAccessionSeparator()
   */
  @Override
  public String getAccessionSeparator()
  {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getAccessionValidator()
   */
  @Override
  public Regex getAccessionValidator()
  {
    Regex validator = new Regex("(AF-[A-Z]+[0-9]+[A-Z0-9]+-F1)");
    validator.setIgnoreCase(true);
    return validator;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbSource()
   */
  @Override
  public String getDbSource()
  {
    return "ALPHAFOLD";
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbVersion()
   */
  @Override
  public String getDbVersion()
  {
    return "1";
  }

  public static String getAlphaFoldCifDownloadUrl(String id)
  {
    return "https://alphafold.ebi.ac.uk/files/" + id + "-model_v1.cif";
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getSequenceRecords(java.lang.String[])
   */
  @Override
  public AlignmentI getSequenceRecords(String queries) throws Exception
  {
    return getSequenceRecords(queries, null);
  }

  public AlignmentI getSequenceRecords(String queries, String retrievalUrl)
          throws Exception
  {
    AlignmentI pdbAlignment = null;
    String chain = null;
    String id = null;
    if (queries.indexOf(COLON) > -1)
    {
      chain = queries.substring(queries.indexOf(COLON) + 1);
      id = queries.substring(0, queries.indexOf(COLON));
    }
    else
    {
      id = queries;
    }

    if (!isValidReference(id))
    {
      System.err.println(
              "(AFClient) Ignoring invalid pdb query: '" + id + "'");
      stopQuery();
      return null;
    }
    String alphaFoldCif = getAlphaFoldCifDownloadUrl(id);
    if (retrievalUrl != null)
    {
      alphaFoldCif = retrievalUrl;
    }

    try
    {
      File tmpFile = File.createTempFile(id, ".cif");
      UrlDownloadClient.download(alphaFoldCif, tmpFile);

      // may not need this check ?
      file = tmpFile.getAbsolutePath();
      if (file == null)
      {
        return null;
      }

      pdbAlignment = importDownloadedStructureFromUrl(alphaFoldCif, tmpFile,
              id, chain, getDbSource(), getDbVersion());

      if (pdbAlignment == null || pdbAlignment.getHeight() < 1)
      {
        throw new Exception(MessageManager.formatMessage(
                "exception.no_pdb_records_for_chain", new String[]
                { id, ((chain == null) ? "' '" : chain) }));
      }

    } catch (Exception ex) // Problem parsing PDB file
    {
      stopQuery();
      throw (ex);
    }
    return pdbAlignment;
  }

  /**
   * general purpose structure importer - designed to yield alignment useful for
   * transfer of annotation to associated sequences
   * 
   * @param alphaFoldCif
   * @param tmpFile
   * @param id
   * @param chain
   * @param dbSource
   * @param dbVersion
   * @return
   * @throws Exception
   */
  public static AlignmentI importDownloadedStructureFromUrl(
          String alphaFoldCif, File tmpFile, String id, String chain,
          String dbSource, String dbVersion) throws Exception
  {
    String file = tmpFile.getAbsolutePath();
    // todo get rid of Type and use FileFormatI instead?
    FileFormatI fileFormat = FileFormat.MMCif;
    AlignmentI pdbAlignment = new FormatAdapter().readFile(tmpFile,
            DataSourceType.FILE, fileFormat);
    if (pdbAlignment != null)
    {
      List<SequenceI> toremove = new ArrayList<SequenceI>();
      for (SequenceI pdbcs : pdbAlignment.getSequences())
      {
        String chid = null;
        // Mapping map=null;
        for (PDBEntry pid : pdbcs.getAllPDBEntries())
        {
          if (pid.getFile() == file)
          {
            chid = pid.getChainCode();

          }
        }
        if (chain == null || (chid != null && (chid.equals(chain)
                || chid.trim().equals(chain.trim())
                || (chain.trim().length() == 0 && chid.equals("_")))))
        {
          // FIXME seems to result in 'PDB|1QIP|1qip|A' - 1QIP is redundant.
          // TODO: suggest simplify naming to 1qip|A as default name defined
          pdbcs.setName(id + SEPARATOR + pdbcs.getName());
          // Might need to add more metadata to the PDBEntry object
          // like below
          /*
           * PDBEntry entry = new PDBEntry(); // Construct the PDBEntry
           * entry.setId(id); if (entry.getProperty() == null)
           * entry.setProperty(new Hashtable());
           * entry.getProperty().put("chains", pdbchain.id + "=" +
           * sq.getStart() + "-" + sq.getEnd());
           * sq.getDatasetSequence().addPDBId(entry);
           */
          // Add PDB DB Refs
          // We make a DBRefEtntry because we have obtained the PDB file from
          // a
          // verifiable source
          // JBPNote - PDB DBRefEntry should also carry the chain and mapping
          // information
          if (dbSource != null)
          {
            DBRefEntry dbentry = new DBRefEntry(dbSource,

                    dbVersion, (chid == null ? id : id + chid));
            // dbentry.setMap()
            pdbcs.addDBRef(dbentry);
            // update any feature groups
            List<SequenceFeature> allsf = pdbcs.getFeatures()
                    .getAllFeatures();
            List<SequenceFeature> newsf = new ArrayList<SequenceFeature>();
            if (allsf != null && allsf.size() > 0)
            {
              for (SequenceFeature f : allsf)
              {
                if (file.equals(f.getFeatureGroup()))
                {
                  f = new SequenceFeature(f, f.type, f.begin, f.end, id,
                          f.score);
                }
                newsf.add(f);
              }
              pdbcs.setSequenceFeatures(newsf);
            }
          }
        }
        else
        {
          // mark this sequence to be removed from the alignment
          // - since it's not from the right chain
          toremove.add(pdbcs);
        }
      }
      // now remove marked sequences
      for (SequenceI pdbcs : toremove)
      {
        pdbAlignment.deleteSequence(pdbcs);
        if (pdbcs.getAnnotation() != null)
        {
          for (AlignmentAnnotation aa : pdbcs.getAnnotation())
          {
            pdbAlignment.deleteAnnotation(aa);
          }
        }
      }
    }
    return pdbAlignment;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#isValidReference(java.lang.String)
   */
  @Override
  public boolean isValidReference(String accession)
  {
    Regex r = getAccessionValidator();
    return r.search(accession.trim());
  }

  /**
   * human glyoxalase
   */
  @Override
  public String getTestQuery()
  {
    return "1QIP";
  }

  @Override
  public String getDbName()
  {
    return "PDB"; // getDbSource();
  }

  @Override
  public int getTier()
  {
    return 0;
  }

  /**
   * Returns a descriptor for suitable feature display settings with
   * <ul>
   * <li>ResNums or insertions features visible</li>
   * <li>insertions features coloured red</li>
   * <li>ResNum features coloured by label</li>
   * <li>Insertions displayed above (on top of) ResNums</li>
   * </ul>
   */
  @Override
  public FeatureSettingsModelI getFeatureColourScheme()
  {
    return new PDBFeatureSettings();
  }

}
