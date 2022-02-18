/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financeme;

import java.awt.Color;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author slavko
 */
public class DatabaseOperations extends FinanceCalculation {
    
    
   public void Create(){
      Connection c = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement prst = null;

      try{
        Class.forName("org.sqlite.JDBC");
    c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
    c.setAutoCommit(false);

     stmt = c.createStatement();
     String sql = "CREATE TABLE IF NOT EXISTS TableDatabase(ID INTEGER PRIMARY KEY AUTOINCREMENT,ARTIKAL TEXT NOT NULL,STANJE TEXT NOT NULL,KUPOVNA INT NOT NULL,PRODAJNA INT NOT NULL)";
     stmt.executeUpdate(sql);
     String sql_profit = "CREATE TABLE IF NOT EXISTS ProfitData(PROFIT INT PRIMARY KEY NOT NULL)";   
        stmt.executeUpdate(sql_profit);
        // ovo odavde dodajem posle par meseci programiranja mozda je kod rizican
        
     String sql_sold = "CREATE TABLE IF NOT EXISTS SoldDatabase(ID INTEGER PRIMARY KEY AUTOINCREMENT,ARTIKAL TEXT NOT NULL,STANJE TEXT NOT NULL,KUPOVNA INT NOT NULL,PRODAJNA INT NOT NULL)";
     stmt.executeUpdate(sql_sold);
     String sql_itemsforme = "CREATE TABLE IF NOT EXISTS MyitemsDatabase(ID INTEGER PRIMARY KEY AUTOINCREMENT,ARTIKAL TEXT NOT NULL,STANJE TEXT NOT NULL,KUPOVNA INT NOT NULL,Dobavljac TEXT NOT NULL)";
     stmt.executeUpdate(sql_itemsforme);

        
        stmt.close();
     c.commit();
     c.close();
   //    }
   
//pst.setString(1,getID);
}
catch(Exception e){
JOptionPane.showMessageDialog(null, e.getMessage());
}
     
   }

public void Insert(Object row0,Object row1,Object row2,Object row3,Object row4) throws ClassNotFoundException{
Connection c = null;
Statement s = null;

try{
Class.forName("org.sqlite.JDBC");
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
c.setAutoCommit(false);
output.setForeground(Color.YELLOW);
    output.setText("Database Opened");
    
    s = c.createStatement();
    String data1 = "INSERT INTO TableDatabase(ID,ARTIKAL,STANJE,KUPOVNA,PRODAJNA) VALUES (NULL,'"+row1+"','"+row2+"','"+row3+"','"+row4+"')";
    s.executeUpdate(data1);
   
    s.close();
    c.commit();
    c.close();
}
catch(SQLException e){
System.err.println(e.getClass().getName()+ ":" + e.getMessage());
System.exit(0);
}
}

public void Insert_3TABELA(Object row0,Object row1,Object row2,Object row3,Object row4) throws ClassNotFoundException{
Connection c = null;
Statement s = null;

try{
Class.forName("org.sqlite.JDBC");
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
c.setAutoCommit(false);
output.setForeground(Color.YELLOW);
    output.setText("Database Opened");
    
    s = c.createStatement();
    String data1 = "INSERT INTO MyitemsDatabase(ID,ARTIKAL,STANJE,KUPOVNA,Dobavljac) VALUES (NULL,'"+row1+"','"+row2+"','"+row3+"','"+row4+"')";
    s.executeUpdate(data1);
   
    s.close();
    c.commit();
    c.close();
}
catch(SQLException e){
System.err.println(e.getClass().getName()+ ":" + e.getMessage());
System.exit(0);
}
}

public void InsertToSold(Object soldRow0,Object soldRow1,Object soldRow2,Object soldRow3,Object soldRow4) throws ClassNotFoundException{
Connection c = null;
Statement stmt4 = null;

try{
Class.forName("org.sqlite.JDBC");
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
c.setAutoCommit(false);
output.setForeground(Color.YELLOW);
    output.setText("Database Opened");
    
    stmt4 = c.createStatement();
    String data1 = "INSERT INTO SoldDatabase(ID,ARTIKAL,STANJE,KUPOVNA,PRODAJNA) VALUES (NULL,'"+soldRow1+"','"+soldRow2+"','"+soldRow3+"','"+soldRow4+"')";
    stmt4.executeUpdate(data1);
   
    stmt4.close();
    c.commit();
    c.close();
}
catch(SQLException e){
System.err.println(e.getClass().getName()+ ":" + e.getMessage());
System.exit(0);
}
}


  public void Create1(){
      Connection c = null;
    Statement stmt = null;

      try{
          Class.forName("org.sqlite.JDBC");
    c = DriverManager.getConnection("jdbc:sqlite:resources\\ProfitDB.db");
    c.setAutoCommit(false);

     stmt = c.createStatement();
   String sql_profit = "CREATE TABLE IF NOT EXISTS ProfitData(PROFIT INT NOT NULL)";   
        stmt.executeUpdate(sql_profit);
     
        stmt.close();
     c.commit();
     c.close();
   
}
catch(Exception e){
JOptionPane.showMessageDialog(null, e.getMessage());
}
   }
  

public void CreateFirstEntry1(){
  
  Connection c = null;
Statement stmt3 = null;

try{
 // Class.forName("org.sqlite.JDBC");
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
c.setAutoCommit(false);
 // output.setForeground(Color.YELLOW);
  //  output.setText("Database Opened");
    
    stmt3 = c.createStatement();
    String sql_data = "INSERT OR IGNORE INTO ProfitData(PROFIT) VALUES ('"+0+"')";
   
//    String sql_data1 = "UPDATE ProfitData SET PROFIT=('"+total_int+"')";
    stmt3.executeUpdate(sql_data);
//    stmt3.executeUpdate(sql_data1);
    stmt3.close();
    c.commit();
    c.close();
}
catch(SQLException e){
System.err.println(e.getClass().getName()+ ":" + e.getMessage());
System.exit(0);
}
 
}
  
  public void InsertProfit(){
   Connection ca = null;
   Statement stmt2 = null;
    try{
ca = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
 ca.setAutoCommit(false);
  stmt2 = ca.createStatement();
  String sql_data2 = "INSERT INTO ProfitData(PROFIT) VALUES ('"+total_int+"')";
  String sql_data = "REPLACE INTO ProfitData(PROFIT) VALUES ('"+total_int+"')";
 String data2 = "UPDATE ProfitData SET PROFIT = ('"+total_int+"')";
 //stmt2.executeUpdate(sql_data);
    stmt2.executeUpdate(sql_data2);
    stmt2.close();
    
    ca.commit();
    ca.close();
    }
    catch(Exception e){
    output.setForeground(Color.red);
    output.setText("Error in Loading total profit");
        System.out.println(e);}
  }
  
  
   public void InsertProfit2(){
   Connection ca = null;
   Statement stmt2 = null;
    try{
ca = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
 ca.setAutoCommit(false);
  stmt2 = ca.createStatement();
  String sql_data2 = "INSERT OR IGNORE INTO ProfitData(PROFIT) VALUES ('"+total_int+"')";
  String sql_data = "REPLACE INTO ProfitData(PROFIT) VALUES ('"+total_int+"')";
 String data2 = "UPDATE ProfitData SET PROFIT = ('"+total_int+"') WHERE PROFIT=('"+0+"')" ;
 String sql_data3="DELETE FROM ProfitData WHERE MIN(PROFIT)";
 
 String sql_data4= "DELETE from ProfitData WHERE PROFIT NOT IN (SELECT PROFIT FROM ProfitData ORDER by PROFIT desc)";
 String sql_data5="DELETE FROM ProfitData WHERE PROFIT < (SELECT MAX(PROFIT) FROM ProfitData)";
 
 //stmt2.executeUpdate(sql_data);
    stmt2.executeUpdate(sql_data5);
    stmt2.close();
    
    ca.commit();
    ca.close();
    }
    catch(Exception e){
    output.setForeground(Color.red);
    output.setText("Error in Loading total profit");
        System.out.println(e);}
  }
  
  public void LoadProfit(){
      CreateFirstEntry1();
  Connection c = null;
   
    try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
 c.setAutoCommit(false);
 Statement stmt1 = c.createStatement();
ResultSet rs = stmt1.executeQuery("SELECT PROFIT FROM ProfitData");
    total_int = rs.getInt(1);
    
    rs.close();
    stmt1.close();
    c.commit();
    c.close();
    }
    catch(Exception e){
    output.setForeground(Color.red);
    output.setText("Error in loading profit data");
    }
  }
  // napravi da se jedan row samo azurira,a ne dodaje prilokom brisanja prodatih
  // napravi da pri ulasku u program ucitava taj row i prenosi u total profit
  
 }
