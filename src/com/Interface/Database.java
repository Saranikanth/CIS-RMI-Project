/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Interface;

import com.implementation.TheImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *This class contains main database connection method 
 * @author T.Saranikanth - 2023016
 */
public final class Database {

    public Database() throws RemoteException {
        Connect();
    }

    //Declaring Connection, Prepared statement and resultset variables
    public Connection con;
    public PreparedStatement pst;
    public ResultSet rst;

    
    /***
     * 
     * This is for Database method using MYSQL
     * Database name is kpt_rmi
     * username - 'root' , password - "" for the XAMPP Server
     */
    public void Connect() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/kpt_rmi", "root", "");
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TheImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
