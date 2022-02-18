/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financeme;

//import static financeme.GenerateJTable.generateJTable;

import java.sql.ResultSet;


public class FinanceME {
    ResultSet rs = null;
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
        public void run() {
            DatabaseOperations dbo = new DatabaseOperations();
       
          
            
     FinanceCalculation fc = new FinanceCalculation();
     fc.FinanceCalculation();
     dbo.LoadProfit();
     fc.tableSelection();
      dbo.CreateFirstEntry1();
     fc.LoadData();
     fc.TableColumnSettings();
     fc.ProfitOutputReloadAfterUpdate();
        }
        });
        
    }}
     
