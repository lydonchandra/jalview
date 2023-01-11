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
package ext.vamsas;

public interface MuscleWS extends java.rmi.Remote
{
  public vamsas.objects.simple.WsJobId align(
          vamsas.objects.simple.SequenceSet seqSet)
          throws java.rmi.RemoteException;

  public vamsas.objects.simple.Alignment getalign(java.lang.String job_id)
          throws java.rmi.RemoteException;

  public vamsas.objects.simple.MsaResult getResult(java.lang.String job_id)
          throws java.rmi.RemoteException;

  public vamsas.objects.simple.WsJobId cancel(java.lang.String jobId)
          throws java.rmi.RemoteException;
}
