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
import java.awt.Color
import jalview.schemes.ColourSchemeI
import jalview.schemes.ColourSchemes
import jalview.datamodel.AnnotatedCollectionI
import jalview.datamodel.SequenceI
import jalview.datamodel.SequenceCollectionI
import jalview.api.AlignViewportI
import jalview.util.Comparison

/*
 * Closure that defines a colour scheme where non-consensus residues are pink,
 * other residues (and gaps) are white
 */
def unconserved
unconserved = { ->
  [
    /*
     * name shown in the colour menu
     */
    getSchemeName: { -> 'Unconserved' },
    
    /*
     * to make a new instance for each alignment view
     */
    getInstance: { view, coll -> unconserved() },
    
    /*
     * method only needed if colour scheme has to recalculate
     * values when an alignment is modified
     */
    alignmentChanged: { AnnotatedCollectionI coll, Map<SequenceI, SequenceCollectionI> map -> },
    
    /*
     * determine colour for a residue at an aligned position of a
     * sequence, given consensus residue(s) for the column and the
     * consensus percentage identity score for the column
     */
    findColour: { char res, int col, SequenceI seq, String consensus, float pid -> 
        if ('a' <= res && res <= 'z')
        {
            res -= ('a' - 'A');
        }
        if (Comparison.isGap(res) || consensus.contains(String.valueOf(res)))
        {
            Color.white
        } else 
        {
            Color.pink
        }
    },
    
    /*
     * true means applicable to nucleotide or peptide data
     */
    isApplicableTo: {AnnotatedCollectionI coll -> true},
    
    /*
     * simple colour schemes are those that depend on the residue
     * only (these are also available to colour structure viewers)
     */
    isSimple: { false }
 ] as ColourSchemeI
}

ColourSchemes.instance.registerColourScheme(unconserved())
