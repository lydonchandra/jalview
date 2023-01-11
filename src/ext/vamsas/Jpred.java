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

public interface Jpred extends java.rmi.Remote
{
  public java.lang.String predict(vamsas.objects.simple.Sequence seq)
          throws java.rmi.RemoteException;

  public java.lang.String predictOnMsa(
          vamsas.objects.simple.Msfalignment msf)
          throws java.rmi.RemoteException;

  public vamsas.objects.simple.Secstructpred getpredict(
          java.lang.String job_id) throws java.rmi.RemoteException;

  public vamsas.objects.simple.JpredResult getresult(
          java.lang.String job_id) throws java.rmi.RemoteException;
}
