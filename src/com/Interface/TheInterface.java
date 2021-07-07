/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Interface;

import com.rmi.server.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author HP
 */
public interface TheInterface extends Remote {
    
    public boolean Sign_up(String username, String password) throws RemoteException;
    public boolean Sign_in(String username, String password) throws RemoteException;
    public boolean questionAdd(String qid, String question, String option1, String option2, String option3) throws RemoteException;
    public boolean questionUpdate(String qno, String question, String opt1, String opt2, String opt3) throws RemoteException;
    public boolean questionDelete(String qno) throws RemoteException;
    public void Question1_Analyse() throws RemoteException;
    public void Question2_Analyse() throws RemoteException;
    public void Question3_Analyse() throws RemoteException;
    public void Question4_Analyse() throws RemoteException;
    public void Question5_Analyse() throws RemoteException;
    public void Question6_Analyse() throws RemoteException;
    public void Question7_Analyse() throws RemoteException;
    public void Question8_Analyse() throws RemoteException;
    public void Question9_Analyse() throws RemoteException;
    public void Question10_Analyse() throws RemoteException;
}


