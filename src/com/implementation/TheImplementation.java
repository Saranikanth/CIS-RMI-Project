package com.implementation;

import com.Interface.Database;
import com.Interface.TheInterface;
import com.rmi.server.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This class contains the server side methods that will be used by the server
 * 
 * @author T.Saranikanth - 2023016
 */
public class TheImplementation extends UnicastRemoteObject implements TheInterface {

    public TheImplementation() throws RemoteException {
        super();
        database.Connect();
    }
    

   boolean Sign_up, Sign_in, questionAdd, questionUpdate, questionDelete;
   
   
   /***
    * This is the instance for database class
    */
   Database database=new Database();
   
   

   /***
    * This method used to perform the SignUp
    * @param username
    * @param password
    * @return Sign_up
    * @throws RemoteException 
    * @exception SQLException
    */
    public boolean Sign_up(String username, String password) throws RemoteException {
        try {
           

            String sql = "INSERT INTO admin(username,password) VALUES (?,?)";
            database.pst = database.con.prepareStatement(sql);
            database.pst.setString(1, username);
            database.pst.setString(2, password);
            database.pst.executeUpdate();
            Sign_up = true;
        } catch (SQLException ex) {

            Sign_up = false;
        }

        return Sign_up;
    }

    /***
     * This method used to perform the SignIn
     * @param username
     * @param password
     * @return Sign_in
     * @throws RemoteException 
     * @exception SQLException
     */
    public boolean Sign_in(String username, String password) throws RemoteException {
        try {
            

            String sql1 = "select * from admin where username = ? and password = ?";
            database.pst = database.con.prepareStatement(sql1);
            database.pst.setString(1, username);
            database.pst.setString(2, password);
            database.rst = database.pst.executeQuery();

            if (database.rst.next()) {
                Sign_in = true;
            } else {
                Sign_in = false;
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return Sign_in;
    }

    /***
     * This method used to perform the questionAdd
     * @param qid
     * @param question
     * @param option1
     * @param option2
     * @param option3
     * @return questionAdd
     * @throws RemoteException 
     * @exception SQLException
     */
    public boolean questionAdd(String qid, String question, String option1, String option2, String option3) throws RemoteException {
        try {
            

            String sql1 = "INSERT INTO questionnaire(qid,question,option1,option2,option3) VALUES (?,?,?,?,?)";
            database.pst = database.con.prepareStatement(sql1);
            database.pst.setString(1, qid);
            database.pst.setString(2, question);
            database.pst.setString(3, option1);
            database.pst.setString(4, option2);
            database.pst.setString(5, option3);
            database.pst.executeUpdate();

            questionAdd = true;

        } catch (SQLException ex) {
            questionAdd = false;
            ex.printStackTrace();
        }

        return questionAdd;
    }
    
    /***
     * This method used to perform the questionUpdate
     * @param qno
     * @param question
     * @param opt1
     * @param opt2
     * @param opt3
     * @returnquestionUpdate
     * @throws RemoteException 
     * @exception SQLException
     */
    public boolean questionUpdate(String qno, String question, String opt1, String opt2, String opt3) throws RemoteException {

        try {
           

            String sql = "UPDATE questionnaire SET question=?,option1=?,option2=?,option3=? WHERE qid=?";
            database.pst = database.con.prepareStatement(sql);

            database.pst.setString(1, question);
            database.pst.setString(2, opt1);
            database.pst.setString(3, opt2);
            database.pst.setString(4, opt3);
            database.pst.setString(5, qno);
            database.pst.executeUpdate();
            questionUpdate = true;

        } catch (SQLException ex) {

            questionUpdate = false;
        }

        return questionUpdate;

    }
    
    /***
     * This method used to perform the questionDelete
     * @param qno
     * @return questionDelete
     * @throws RemoteException 
     * @exception SQLException
     */
    public boolean questionDelete(String qno) throws RemoteException {

        try {
           
            String sql = "DELETE FROM questionnaire WHERE qid=?";
            database.pst = database.con.prepareStatement(sql);
            database.pst.setString(1, qno);
            database.pst.executeUpdate();

            questionDelete = true;

        } catch (SQLException ex) {

            questionDelete = false;
        }

        return questionDelete;
    }
    
    /***
     * These methods used to perform chart analysis by using a web API called QUICKCHART.IO
     * These functions needs Internet connection
     * 
     * NOTE - This method will apply for all the 10 Questions
     * @throws RemoteException 
     */
    public void Question1_Analyse() throws RemoteException{
        
        try {
          
            String Advertisements = "", Word_by_mouth = "", Social_media = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=1 and answer=\"Advertisements\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Advertisements = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=1 and answer=\"Word by mouth\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Word_by_mouth = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=1 and answer=\"Social media\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Social_media = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'pie',data: { datasets: [ { data: [\'" + Advertisements + "\',\'" + Word_by_mouth + "\',\'" + Social_media + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'How-did-you-heard-about-our-KPT-restaurant-before?', }, ], labels: ['Advertisement', 'Word-By-Mouth','Social-Media'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("How did you heard about our KPT restaurant before?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(1000, 800);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
    }
    
    
    public void Question2_Analyse() throws RemoteException{
     try {
           
            String Yes = "", No = "", Not_prefered_to_say = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=2 and answer=\"Yes\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Yes = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=2 and answer=\"No\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    No = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=2 and answer=\"Not prefered to say\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Not_prefered_to_say = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'bar',data: { datasets: [ { data: [\'" + Yes + "\',\'" + No + "\',\'" + Not_prefered_to_say + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'Is-this-your-first-order?', }, ], labels: ['Yes', 'No','Not-Prefered-To-Say'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("Is this your first order?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(1000, 800);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
    }

    
    public void Question3_Analyse() throws RemoteException{
      try {
            
            String Vegetarian = "", Non_vegetarian = "", Vegan = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=3 and answer=\"Vegetarian\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Vegetarian = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=3 and answer=\"Non-vegetarian\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Non_vegetarian = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=3 and answer=\"Vegan\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Vegan = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'pie',data: { datasets: [ { data: [\'" + Vegetarian + "\',\'" + Non_vegetarian + "\',\'" + Vegan + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-is-your-preference-on-food?', }, ], labels: ['Vegetarian', 'Non-Vegetarian','Vegan'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What is your preference on food?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(900, 900);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
    }
    
    
    public void Question4_Analyse() throws RemoteException{
    try {

            String Indian = "", European = "", Chinese = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=4 and answer=\"Indian\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Indian = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=4 and answer=\"European\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    European = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=4 and answer=\"Chinese\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Chinese = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'bar',data: { datasets: [ { data: [\'" + Indian + "\',\'" + European + "\',\'" + Chinese + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-type-of-cuisine-you-prefer?', }, ], labels: ['Indian', 'European','Chinese'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What type of cuisine you prefer?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(1000, 800);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
    }
    
    
     public void Question5_Analyse() throws RemoteException{
     try {

            String Kids_meal = "", Normal_meal = "", Jumbo_meal = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=5 and answer=\"Kids meal\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Kids_meal = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=5 and answer=\"Normal meal\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Normal_meal = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=5 and answer=\"Jumbo meal\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Jumbo_meal = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'pie',data: { datasets: [ { data: [\'" + Kids_meal + "\',\'" + Normal_meal + "\',\'" + Jumbo_meal + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-type-of-meal-you-prefer-according-to-capacity?', }, ], labels: ['Kids-Meal', 'Normal-Meal','Jumbo-Meal'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What type of meal you prefer according to capacity?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(900, 900);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
     }
     
      public void Question6_Analyse() throws RemoteException{
       try {
            
            String Normal = "", Moderate = "", Salt_free = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=6 and answer=\"Normal\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Normal = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=6 and answer=\"Moderate\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Moderate = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=6 and answer=\"Salt free\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Salt_free = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'bar',data: { datasets: [ { data: [\'" + Normal + "\',\'" + Moderate + "\',\'" + Salt_free + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-is-your-preference-in-salt?', }, ], labels: ['Normal', 'Moderate','Salt-Free'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What is your preference in salt?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(1000, 800);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
      }
      
      
      public void Question7_Analyse() throws RemoteException{
       try {

            String Spicy = "", Moderate = "", Low = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=7 and answer=\"Spicy\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Spicy = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=7 and answer=\"Moderate\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Moderate = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=7 and answer=\"Low\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Low = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'pie',data: { datasets: [ { data: [\'" + Spicy + "\',\'" + Moderate + "\',\'" + Low + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-is-your-spicy-tolerance-level?', }, ], labels: ['Spicy', 'Moderate','Low'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What is your spicy tolerance level?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(900, 900);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
      }
      
      
      public void Question8_Analyse() throws RemoteException{
      try {

            String Natural_juices = "", Carbonated_drinks = "", Hot_drinks = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=8 and answer=\"Natural juices\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Natural_juices = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=8 and answer=\"Carbonated drinks\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Carbonated_drinks = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=8 and answer=\"Hot drinks\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Hot_drinks = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'bar',data: { datasets: [ { data: [\'" + Natural_juices + "\',\'" + Carbonated_drinks + "\',\'" + Hot_drinks + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-do-you prefer-to-drinks?', }, ], labels: ['Natural-juices', 'Carbonated-drinks','Hot-drinks'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What do you prefer to drinks?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(1000, 800);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
      }
      
      
      public void Question9_Analyse() throws RemoteException{
       try {
            
            String Dine_inn = "", Take_away = "", both = "", link = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=9 and answer=\"Dine inn\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Dine_inn = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=9 and answer=\"Take away\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Take_away = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=9 and answer=\"both\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    both = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link = "https://quickchart.io/chart?bkg=white&c={ type: 'pie',data: { datasets: [ { data: [\'" + Dine_inn + "\',\'" + Take_away + "\',\'" + both + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'Which-type-of-delivery-method-you-prefer?', }, ], labels: ['Dine-Inn', 'Take-Away','Both'],},}";

                link = link.replace(" ", "");

                URL url1 = new URL(link);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("Which type of delivery method you prefer?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(900, 900);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
      }
      
      
      public void Question10_Analyse() throws RemoteException{
      try {
            
            String Card = "", Cash = "", E_Wallets = "", link1 = "";
            BufferedImage image = null;

            String sql1 = "SELECT COUNT(user_id) FROM answers WHERE question_id=10 and answer=\"Card\" ";
            try {
                database.pst = database.con.prepareStatement(sql1);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Card = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }
            String sql2 = "SELECT COUNT(user_id) FROM answers WHERE question_id=10 and answer=\"Cash\" ";
            try {
                database.pst = database.con.prepareStatement(sql2);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    Cash = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            String sql3 = "SELECT COUNT(user_id) FROM answers WHERE question_id=10 and answer=\"E-Wallets\" ";
            try {
                database.pst = database.con.prepareStatement(sql3);
                ResultSet rs = database.pst.executeQuery();
                while (rs.next()) {
                    E_Wallets = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "ERROR:");

            }

            try {

                link1 = "https://quickchart.io/chart?bkg=white&c={ type: 'bar',data: { datasets: [ { data: [\'" + Card + "\',\'" + Cash + "\',\'" + E_Wallets + "\',], backgroundColor: [ 'rgb(204, 0, 0)', 'rgb(204, 204, 0)', 'rgb(0,204,204)'   ], label: 'What-is-your-payment-method?', }, ], labels: ['Card', 'Cash','E-Wallets'],},}";

                link1 = link1.replace(" ", "");

                URL url1 = new URL(link1);

                HttpURLConnection urlcon = (HttpURLConnection) url1.openConnection();

                urlcon.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                image = ImageIO.read(urlcon.getInputStream());

                JFrame frame = new JFrame();

                frame.setForeground(new Color(153, 255, 255));

                frame.setTitle("What is your payment method?");
                frame.setResizable(false);
                frame.setBackground(new Color(153, 255, 255));
                frame.setSize(1000, 800);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            } catch (MalformedURLException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Not Internet Connection", "ALERT", JOptionPane.WARNING_MESSAGE);

                e1.printStackTrace();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error...");
            e.printStackTrace();
        }
      }
}
