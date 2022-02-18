/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financeme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;


public class FinanceCalculation extends JFrame{
    
    public static void Frame2(){
   
    JEditorPane epane = new JEditorPane();
    epane.setBounds(20, 0, 323, 280);
    JTextArea tarea = new JTextArea(epane.getText());
    tarea.setBounds(20, 0, 323, 280);
    
    JFrame okvir2 = new JFrame("Opis stanja telefona");
    okvir2.setVisible(true);
    okvir2.setSize(350,300);
    okvir2.setLocationRelativeTo(null);
    okvir2.setLayout(new BorderLayout());
    okvir2.setResizable(false);
    okvir2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    
    JPanel panel1 = new JPanel();  // ovo je kod za novi frame gde pisem
    Panel panel2 = new Panel();  // stanje neispravnog telefona
    panel1.setBounds(0, 0, 350, 280);
    panel1.setLayout(new BorderLayout());
    
    JScrollPane scpane = new JScrollPane(epane);
    
    
    panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
    panel2.setBackground(Color.BLUE);
    JButton b1 = new JButton("Sacuvaj");
    JButton b2 = new JButton("Ponisti");
    panel2.add(b1);
    panel2.add(b2);
    panel1.add(tarea);
    panel1.add(scpane,BorderLayout.EAST);
    okvir2.add(panel1,BorderLayout.CENTER);
    okvir2.add(panel2,BorderLayout.SOUTH);
    
    b1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
    
    b2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          
        }
    });
    }
    // nedovrsen kod koji bi trebalo da izbacuje polje za detaljni opis stanja telefona
    // funkcija nije pozvana jos tako da je program ignorise
    
    
// finance calculation
    
    int errorIndicator=0;
    int saveErrorIndicator=0;
    int tableWidthindicator=0;
    int num_final1;
    int num_final2;
    
   final JTextField tf1 = new JTextField();
   final JTextField tf2 = new JTextField();
   final JTextField tf_sell_price = new JTextField();
   
   String[] valuta = new String[] {"Dinari","Evri"};
    JComboBox<String> cbox1 = new JComboBox<>(valuta);
    JComboBox<String> cbox2 = new JComboBox<>(valuta);
    
   public static int num_finall1,num_finall2;
 
    final Object[] row = new Object[5];
    final Object[] soldRow = new Object[5];
    
    final JTable table = new JTable();
    
    Object[] columns = {"ID","Artikal", "Stanje", "Kupovna Cena",
    "Prodaja" };   
    String statusrow;
    
    int rowcount = table.getRowCount();
    DefaultTableModel model = new DefaultTableModel();
    ResultSet rs = null;
    Statement s = null;
    
    JLabel outputtext = new JLabel("Output:");
    JLabel output = new JLabel("");
    
    JCheckBox deleted_cbox = new JCheckBox("Prodato?");
    
    JLabel totalprofit = new JLabel("Total profit:");
    public static int total_int = 0;
    public static String total_string = Integer.toString(total_int);
   
    public static JLabel profit_value = new JLabel("");
    
    String[] tabele = new String[] {"Na prodaju","Prodato","Za moje potrebe"};
    JComboBox<String> cbox3 = new JComboBox<>(tabele);
    
     JLabel artikal,kupovna_cena,stanje,prodajna_cena,supplier;
    JTextField tf_myitem_supplier;
    JButton save_btn_tabelatri,delete_btn_tabelatri,update_btn_tabelatri,save_btn,btnUpdate,btnDelete;
     JRadioButton rb1 = new JRadioButton("Novo");
    JRadioButton rb2 = new JRadioButton("Polovno");
    JRadioButton rb3 = new JRadioButton("Ispravno");  // tasteri za selektovnje stanja
    JRadioButton rb4 = new JRadioButton("Neispravno");
     JButton refresh = new JButton();
    JButton refresh1 = new JButton();
    JButton refresh2 = new JButton();
   //  Object supplier_value = tf_myitem_supplier.getText();
     
     
     
    public void FinanceCalculation(){
   

        tf_sell_price.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            String value = tf_sell_price.getText();
            int l = value.length();
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE) {  
               tf_sell_price.setEditable(true);
            } else {
               tf_sell_price.setEditable(false);
            }
         }
      });
        
        tf2.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            String value = tf2.getText();
            int l = value.length();
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9'|| ke.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE) {
               tf2.setEditable(true);
            } else {
               tf2.setEditable(false);
            }
         }
      });
        
         DatabaseOperations db = new DatabaseOperations();
        db.Create();
        
    JComponent components = new JComponent() {};
     // odavde pocnije kod za tabelu
     
     outputtext.setBounds(10,160,50,15);
     output.setBounds(60,160,150,15);
    
     profit_value.setText(total_string);
     profit_value.setBounds(350,105,100,20);
     totalprofit.setBounds(280,105,70,20);
     
     deleted_cbox.setBounds(62,140,80,20);
     deleted_cbox.setToolTipText("Ako je selektovan pri brisanju,dodaje vrednost profita u totalni profit");
     deleted_cbox.setForeground(Color.red);
     
    JPanel tpanel = new JPanel();
  //  tpanel.setBackground(Color.lightGray);
    tpanel.setBounds(0,180,545,360);
    tpanel.setLayout(new BorderLayout());
    tpanel.setVisible(true);
 
    

    
model.setColumnIdentifiers(columns);
table.setBackground(Color.darkGray.brighter());
table.setForeground(Color.ORANGE);
table.setSelectionBackground(Color.gray.brighter());

Font font = new Font("", 1, 18);
Font font2 = new Font("",1, 15);
table.setFont(font);
   
    tpanel.add(table);
    table.setModel(model);
  //  tpanel.add(sb,BorderLayout.EAST);
  JScrollPane scpane = new JScrollPane(table);
    scpane.setOpaque(true);
     tpanel.add(scpane);
    

      // do ovde radi kod za tabelu
    
    tf1.setBounds(65, 10, 100, 20);
    tf2.setBounds(105, 40, 100, 20);
    tf1.setBackground(Color.ORANGE.darker());
    tf1.setForeground(Color.WHITE);
    tf2.setBackground(Color.ORANGE.darker());
    tf2.setForeground(Color.WHITE);
    artikal = new JLabel("Artikal:");
    artikal.setBounds(10, 10, 100, 20);
    tf1.setToolTipText("Ukucaj naziv artikla");
    
    kupovna_cena = new JLabel("Kupovna cena:");
    kupovna_cena.setBounds(10, 40,100, 20);
    
    tf2.setToolTipText("Ukucaj kupovnu cenu");
    
    prodajna_cena = new JLabel("Prodajna cena:");
    prodajna_cena.setBounds(270, 80, 100, 20);
    
    stanje = new JLabel("Stanje:");  
    stanje.setBounds(10, 70, 100, 20); 
   //za tabelu ovo ispod 
   
     save_btn_tabelatri = new JButton("Sacuvaj");
     update_btn_tabelatri = new JButton("Azuriraj");
     delete_btn_tabelatri = new JButton("Obrisi");
     save_btn = new JButton("Sacuvaj");
     btnDelete = new JButton("Obrisi");
     btnUpdate = new JButton("Azuriraj");
  //  Icon refreshIcon = new ImageIcon("refreshIcon.png");
    
   
    ImageIcon icon = new ImageIcon("resources\\refreshicon2.png");
    Image imag = icon.getImage();
    
    BufferedImage bi = new BufferedImage(imag.getWidth(null),imag.getHeight(null),BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.createGraphics();
    g.drawImage(imag,25,25,50,50,null);
    ImageIcon newIcon = new ImageIcon(bi);
    
    refresh.setIcon(newIcon);
    refresh1.setIcon(newIcon);
    refresh2.setIcon(newIcon);
   
    tf_sell_price.setBounds(360, 80, 100, 20);
    tf_sell_price.setBackground(Color.ORANGE.darker());
    tf_sell_price.setForeground(Color.WHITE);
    tf_sell_price.setToolTipText("Ukucaj prodajnu cenu");
    // ovo ispod je za novi tf i za jlabel za trecu tabelu ya unos
    tf_myitem_supplier = new JTextField();
    supplier = new JLabel("Dobavljac:");
    tf_myitem_supplier.setVisible(false);
    supplier.setVisible(false);
    supplier.setBounds(270, 80, 100, 20);
    tf_myitem_supplier.setBounds(330, 80, 200, 20);
    tf_myitem_supplier.setForeground(Color.WHITE);
    tf_myitem_supplier.setBackground(Color.ORANGE.darker());
    
    
    save_btn_tabelatri.setBounds(270, 10, 80, 60);
    update_btn_tabelatri.setBounds(360, 10, 80, 60);
    delete_btn_tabelatri.setBounds(450, 10, 80, 60);
    save_btn_tabelatri.setBackground(Color.RED.darker());
    update_btn_tabelatri.setBackground(Color.RED);
    delete_btn_tabelatri.setBackground(Color.RED.brighter());
    save_btn_tabelatri.setVisible(false);
    update_btn_tabelatri.setVisible(false);
    delete_btn_tabelatri.setVisible(false);
    
    
    save_btn.setBounds(270, 10, 80, 60);
    btnUpdate.setBounds(360, 10, 80, 60);
    btnDelete.setBounds(450, 10, 80, 60);
    refresh.setBounds(10, 100, 50, 50);
    refresh1.setBounds(10, 100, 50, 50);
    refresh2.setBounds(10, 100, 50, 50);
    save_btn.setBackground(Color.RED.darker());
    btnUpdate.setBackground(Color.RED);
    btnDelete.setBackground(Color.RED.brighter());
    cbox1.setBounds(210, 40, 58, 20);
    cbox2.setBounds(470, 80, 58, 20);
    cbox3.setBounds(410,155,120,20);
    cbox1.setBackground(Color.ORANGE.darker());
    cbox1.setForeground(Color.WHITE);
    cbox2.setBackground(Color.ORANGE.darker());
    cbox2.setForeground(Color.WHITE);
    cbox3.setBackground(Color.darkGray.brighter());
    cbox3.setForeground(Color.ORANGE);
    JLabel selected_table = new JLabel("Tabela:");  
    selected_table.setBounds(365,155,70,20);
    
    
    JPanel stanje_panel = new JPanel();
    JPanel stanje_panel2 = new JPanel();
    stanje_panel.setBounds(65, 70, 195, 35);  // ovo je panel za izbor valute
    stanje_panel.setBackground(Color.YELLOW);
    stanje_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    stanje_panel.setVisible(true);
    stanje_panel2.setBounds(65,105,195,35);
    stanje_panel2.setBackground(Color.YELLOW);
    stanje_panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
    stanje_panel2.setVisible(false);
    ButtonGroup grp1 = new ButtonGroup();
    ButtonGroup grp2 = new ButtonGroup();

   
    rb1.setBackground(Color.YELLOW);
    rb2.setBackground(Color.YELLOW);
    rb3.setBackground(Color.YELLOW);
    rb4.setBackground(Color.YELLOW);

    grp1.add(rb1);
    grp1.add(rb2);
    grp2.add(rb3);
    grp2.add(rb4);
    stanje_panel.add(rb1);
    stanje_panel.add(rb2);
    stanje_panel2.add(rb3);
    stanje_panel2.add(rb4);
    // layout celog frame
  // setLayout(new FlowLayout());
        scpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scpane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        
  refresh.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoadData();
            db.LoadProfit();
            ProfitOutputReloadAfterUpdate();
            TableColumnSettings();
            
        }
    });
  refresh1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoadDataSOLD();
            db.LoadProfit();
            ProfitOutputReloadAfterUpdate();
            TableColumnSettings();
            
        }
    });
  refresh2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoadData3TABELA();
            db.LoadProfit();
            ProfitOutputReloadAfterUpdate();
            TableColumnSettings();
        }
    });
    rb1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if(rb1.isSelected())
    {
       stanje_panel2.setVisible(false);
       row[2] = "Novo"; 
    }  
}});
    
    rb2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if(rb2.isSelected())
    {
       stanje_panel2.setVisible(true);
       row[2] = "Polovno";
    }  
}});
    
    rb3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if(rb3.isSelected())
    {
       row[2] = "Ispravno";
    }  
}});
    
    rb4.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(rb4.isSelected())
    {
      row[2] = "Neispravno";
     //   Frame2();
    }  
}});
rb1.doClick();
    
    
save_btn.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {
String tf2value = tf2.getText().toString();
String tfsellpricevalue = tf_sell_price.getText().toString();
Connection c = null;
try{
int tfsellpriceint = Integer.parseInt(tfsellpricevalue);
int tf2int = Integer.parseInt(tf2value);
}
catch(NumberFormatException es){
    saveErrorIndicator=1;
output.setForeground(Color.blue);
    output.setText("Save Error: fill price");
}
if(saveErrorIndicator == 0){
    int tfsellpriceint = Integer.parseInt(tfsellpricevalue);
int tf2int = Integer.parseInt(tf2value);
int rowcount = table.getRowCount() + 1;
try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
Statement stmt1 = c.createStatement();
ResultSet rs = stmt1.executeQuery("SELECT ID FROM TableDatabase ORDER BY ID DESC LIMIT 1");
row[0] = rs.getInt(1)+1;

rs.close();
    stmt1.close();
    c.close();
}
catch(Exception ex){
    row[0] = rowcount;
    System.out.println("u kurac krenuo pogresno kod");
}
if(tf1.getText().isEmpty())
    row[1]="Neodređeni artikal";
else
   row[1] = tf1.getText(); 

cboxDefaultSet();

DatabaseOperations db = new DatabaseOperations();
    try{ 
    db.Insert(row[0], row[1], row[2], row[3], row[4]);
output.setText("Insert operation OK");
    }
    catch(Exception exe){System.out.println("Error in Insert Method Call");}
    model.addRow(row);
    table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);

new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                table.setSelectionBackground(Color.gray.brighter());
            }
        }, 
        200 
);
 table.setSelectionBackground(Color.green);
}
else{
saveErrorIndicator=0;
output.setForeground(Color.red);
output.setText("Save Error: fill price");
}
TableColumnSettings();
  }});

save_btn_tabelatri.addActionListener(new ActionListener() {
Connection c;
@Override
public void actionPerformed(ActionEvent e) {
String tf2value = tf2.getText().toString();

try{
int tf2int = Integer.parseInt(tf2value);
}
catch(NumberFormatException es){
    saveErrorIndicator=1;
output.setForeground(Color.blue);
    output.setText("Save Error: fill price");
}
if(saveErrorIndicator == 0){
  
int tf2int = Integer.parseInt(tf2value);
int rowcount = table.getRowCount() + 1;
try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
Statement stmt1 = c.createStatement();
ResultSet rs = stmt1.executeQuery("SELECT ID FROM MyItemsDatabase ORDER BY ID DESC LIMIT 1");
row[0] = rs.getInt(1)+1;

rs.close();
    stmt1.close();
    c.close();
}
catch(Exception ex){
    row[0] = rowcount;
    System.out.println("u kurac krenuo pogresno kod");
}
if(tf1.getText().isEmpty())
    row[1]="Neodređeni artikal";
else
   row[1] = tf1.getText(); 

  cboxDefaultSet();
  row[4] = tf_myitem_supplier.getText();
//  row[3] = tfsellpriceint;
//  row[2] = num_finall1;
//  row[3] = num_finall2;


DatabaseOperations db = new DatabaseOperations();
    try{ 
    db.Insert_3TABELA(row[0], row[1], row[2], row[3], row[4]);
output.setText("Insert operation OK");
    }
    catch(Exception exe){System.out.println("Error in Insert Method Call");}
    model.addRow(row);
    table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                table.setSelectionBackground(Color.gray.brighter());
            }
        }, 
        200 
);
 table.setSelectionBackground(Color.green);
}
else{
saveErrorIndicator=0;
output.setForeground(Color.red);
output.setText("Save Error: fill price");
}
TableColumnSettings();
  }     });

//save_btn_tabelatri.addActionListener(new ActionListener() {
//
//@Override
//public void actionPerformed(ActionEvent e) {
//table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
//table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
//String tf2value = tf2.getText().toString();
//
//try{
//int tf2int = Integer.parseInt(tf2value);
//
//
//if(tf2int>=Integer.MIN_VALUE&&tf2int<=Integer.MAX_VALUE)   {
//    cboxDefaultSet();
//   // ovde dodaj kod koji brise poslednji row u tabeli ako njie unet int u polje ya cenu
// // int i = table.getRowCount();
// //  deleteSQL_3TABELA();
// // model.removeRow(i);
//} 
//else{output.setForeground(Color.ORANGE);
//    output.setText("Enter numeric values");}
//}
//catch(NumberFormatException es){
//output.setForeground(Color.ORANGE);
//    output.setText("Enter numeric values");
//}
//int rowcount = table.getRowCount() + 1;
//row[0] = rowcount;
//row[1] = tf1.getText();
//row[4] = tf_myitem_supplier.getText();
//
////  row[2] = num_finall1;
////  row[3] = num_finall2;
//
//
//DatabaseOperations db = new DatabaseOperations();
//    try{ 
//    db.Insert_3TABELA(row[0], row[1], row[2], row[3], row[4]);
//output.setText("Insert operation OK");
//    }
//    catch(Exception exe){System.out.println("Error in Insert Method Call");}
//    model.addRow(row);
//    
//  }     });
//    
// button remove row - Clicked on Delete Button


btnDelete.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {

int i = table.getSelectedRow();
if (i >= 0) {
if(deleted_cbox.isSelected()){
String val1 = model.getValueAt(i,3).toString();
String val2 = model.getValueAt(i,4).toString();
int val1_ = Integer.parseInt(val1);
int val2_ = Integer.parseInt(val2);

int profit_int = val2_ - val1_;

total_int = total_int + profit_int;

soldRow[0] = model.getValueAt(i,0).toString();
soldRow[1] = model.getValueAt(i,1).toString();
soldRow[2] = model.getValueAt(i,2).toString();
soldRow[3] = model.getValueAt(i,3).toString();
soldRow[4] = model.getValueAt(i,4).toString();

    try {Thread t1 = new Thread(new Runnable() {
    @Override
    public void run() {
        
    }
});  
t1.start();
        
       
        db.InsertToSold(soldRow[0], soldRow[1], soldRow[2], soldRow[3], soldRow[4]);
    } catch (Exception ex) {
        Logger.getLogger(FinanceCalculation.class.getName()).log(Level.SEVERE, null, ex);
    }

db.InsertProfit();
  db.InsertProfit2();
ProfitOutputReloadAfterUpdate();
 // String total_string = Integer.toString(total_int);
    // profit_value.setText(total_string);
new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                profit_value.setForeground(Color.black);
                
                // your code here
            }
        }, 
        500 
);
profit_value.setForeground(Color.green);
}
deleteSQL();
model.removeRow(i);


output.setForeground(Color.red);
output.setText("Delete Operation OK");
}
else {
    output.setForeground(Color.red.darker());
output.setText("Delete Operation Fail");
}
TableColumnSettings();
}
});

delete_btn_tabelatri.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {

int i = table.getSelectedRow();
if (i >= 0) {
deleteSQL_3TABELA();
model.removeRow(i);
output.setForeground(Color.red);
output.setText("Delete Operation OK");
TableColumnSettings();
}
else {
    output.setForeground(Color.red.darker());
output.setText("Delete Operation Fail");
}
}
});

table.addMouseListener(new MouseAdapter() {

@Override
public void mouseClicked(MouseEvent e) {

int i = table.getSelectedRow();

tf1.setText(model.getValueAt(i, 1).toString());
tf2.setText(model.getValueAt(i, 3).toString());

String y = model.getValueAt(i, 4).toString();
try{
int x = Integer.parseInt(model.getValueAt(i, 4).toString());
String xx= String.valueOf(x);
tf_sell_price.setText(xx);
}
catch(NumberFormatException ex){
tf_myitem_supplier.setText(y);
}


switch(model.getValueAt(i, 2).toString()){
    case "Novo" : grp2.clearSelection(); rb1.doClick();
break;
    case "Polovno" : grp2.clearSelection(); rb2.doClick();
break;
    case "Ispravno" : rb2.doClick(); rb3.doClick();
break;
    case "Neispravno" : rb2.doClick(); rb4.doClick();
break;
};
}
});

        
btnUpdate.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {

// i = the index of the selected row
int i = table.getSelectedRow();

if (i >= 0) {
try{
cboxDefaultSet();    


model.setValueAt(tf1.getText(), i, 1);
model.setValueAt(row[2], i, 2);
model.setValueAt(tf2.getText(), i, 3);
model.setValueAt(tf_sell_price.getText(), i, 4);

UpdateSQLTable(1);}
catch(NumberFormatException ex){
output.setForeground(Color.red);
output.setText("Update Error: Invalid price");
}
}
TableColumnSettings();
}
});

update_btn_tabelatri.addActionListener(new ActionListener() {

@Override
public void actionPerformed(ActionEvent e) {

int i = table.getSelectedRow();

if (i >= 0) {
try{
cboxDefaultSet();    
model.setValueAt(tf1.getText(), i, 1);
model.setValueAt(row[2], i, 2);
model.setValueAt(tf2.getText(), i, 3);
model.setValueAt(tf_myitem_supplier.getText(), i, 4);

UpdateSQLTable();
}
catch(NumberFormatException ex){
output.setForeground(Color.red);
output.setText("Update Error: Invalid price");
}
}
}
});

// table kod gore
    setSize(550, 580);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("FinanceMe");
    setResizable(false);
    components.add(artikal);
    components.add(kupovna_cena);
    components.add(prodajna_cena);
    components.add(stanje);
    components.add(tf1);
    components.add(tf2);
    components.add(save_btn);
    components.add(btnUpdate);
    components.add(btnDelete);
    components.add(save_btn_tabelatri);
    components.add(update_btn_tabelatri);
    components.add(delete_btn_tabelatri);
    components.add(refresh);
    components.add(refresh1);
    components.add(refresh2);
    components.add(tf_sell_price);
    components.add(cbox1);
    components.add(cbox2);
    components.add(cbox3);
    components.add(tf_myitem_supplier);
    components.add(supplier);
    components.add(selected_table);
    components.add(stanje_panel);
    components.add(stanje_panel2);
    components.add(outputtext);
    components.add(output);
    components.add(deleted_cbox);
    components.add(totalprofit);
    components.add(profit_value);
    components.add(tpanel);
    add(components);
    this.setIconImage(new ImageIcon("resources\\icons8-duration-finance-100.png").getImage());
    setVisible(true);
    
    }
   
    public void tableSelection(){
  
   cbox3.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent event) {
   cbox3 = (JComboBox) event.getSource();
   String selectedObject = cbox3.getSelectedItem().toString();
   if(selectedObject.equals(tabele[0])){
       tableWidthindicator=0;
   LoadData();
   TableColumnSettings();
   tf_sell_price.setVisible(true);
   cbox2.setVisible(true);
   prodajna_cena.setVisible(true);
    tf_myitem_supplier.setVisible(false);
   supplier.setVisible(false);
       rb1.setEnabled(true);
       rb2.setEnabled(true);
       rb3.setEnabled(true);
       rb4.setEnabled(true);
    save_btn_tabelatri.setVisible(false);
    update_btn_tabelatri.setVisible(false);
    delete_btn_tabelatri.setVisible(false);
    save_btn.setVisible(true);
    btnUpdate.setVisible(true);
    btnDelete.setVisible(true);
    deleted_cbox.setEnabled(true);
    save_btn.setEnabled(true);
    btnUpdate.setEnabled(true);
    btnDelete.setEnabled(true);
    refresh.setVisible(true);
    refresh1.setVisible(false);
    refresh2.setVisible(false);
   }
   if(selectedObject.equals(tabele[1])){
       tableWidthindicator=1;
   LoadDataSOLD();
   TableColumnSettings();
       rb1.setEnabled(false);
       rb2.setEnabled(false);
       rb3.setEnabled(false);
       rb4.setEnabled(false);
    tf_sell_price.setVisible(true);
   cbox2.setVisible(true);
   prodajna_cena.setVisible(true);
    tf_myitem_supplier.setVisible(false);
   supplier.setVisible(false);
   
    save_btn_tabelatri.setVisible(false);
    update_btn_tabelatri.setVisible(false);
    delete_btn_tabelatri.setVisible(false);
    save_btn.setVisible(true);
    btnUpdate.setVisible(true);
    btnDelete.setVisible(true);
    save_btn.setEnabled(false);
    btnUpdate.setEnabled(false);
    btnDelete.setEnabled(false);
    refresh.setVisible(false);
    refresh1.setVisible(true);
    refresh2.setVisible(false);
    
    Boolean bool1 = deleted_cbox.isSelected();
    if(bool1 == true){
        deleted_cbox.doClick();
    }
    deleted_cbox.setEnabled(false);
   }
   if(selectedObject.equals(tabele[2])){
       tableWidthindicator=2;
   LoadData3TABELA();
   TableColumnSettings();
   tf_sell_price.setVisible(false);
   cbox2.setVisible(false);
   prodajna_cena.setVisible(false);
   tf_myitem_supplier.setVisible(true);
   supplier.setVisible(true);
    rb1.setEnabled(true);
       rb2.setEnabled(true);
       rb3.setEnabled(true);
       rb4.setEnabled(true);
    save_btn.setVisible(false);
    btnUpdate.setVisible(false);
    btnDelete.setVisible(false);
    save_btn_tabelatri.setVisible(true);
    update_btn_tabelatri.setVisible(true);
    delete_btn_tabelatri.setVisible(true);
    refresh.setVisible(false);
    refresh1.setVisible(false);
    refresh2.setVisible(true);
    Boolean bool1 = deleted_cbox.isSelected();
    if(bool1 == true){
        deleted_cbox.doClick();
    }
    deleted_cbox.setEnabled(false);
    deleted_cbox.setEnabled(false);
    
   }
   }
   });
   // ovo gore je kod za implementaciju cbox3 koji regulise tabele

   
    }
   static int br_tabele;
    public void cboxDefaultSet(){
        
         String str1 =cbox1.getSelectedItem().toString();
   if(str1.equals(valuta[1])){
    String x = tf2.getText();
            int z = Integer.parseInt(x);         //  za combo box 1
            num_final1 = z*117;
            row[3] = num_final1;
            
   }
   else{String str2 =cbox1.getSelectedItem().toString();

   if(str2.equals(valuta[0])){
    String x = tf2.getText();
     
            int z = Integer.parseInt(x);         //  za combo box 1
            row[3] = z;}}
  
    String str3 =cbox2.getSelectedItem().toString();
   if(str3.equals(valuta[1])){
    String x = tf_sell_price.getText();
            int z = Integer.parseInt(x);         //  za combo box 1
            num_final2 = z*117;
            row[4] = num_final2;
            
   }
   else{String str4 =cbox2.getSelectedItem().toString();
   String selectedObject1 = cbox3.getSelectedItem().toString();
   
   if(str4.equals(valuta[0])){
    String x = tf_sell_price.getText();
    if(selectedObject1.equals(tabele[2])){
    }
    else{
   int z = Integer.parseInt(x);//  za combo box 1
            row[4] = z;}
   }
   }
   }
    
    public void TableColumnSettings(){
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
table.setRowHeight(30); 
Connection c;
int temp_int;
try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
Statement stmt1 = c.createStatement();
String query = "";
if(tableWidthindicator == 0)
    query ="SELECT ID FROM TableDatabase ORDER BY ID DESC LIMIT 1";
if(tableWidthindicator == 1)
    query ="SELECT ID FROM SoldDatabase ORDER BY ID DESC LIMIT 1";
if(tableWidthindicator == 2)
    query ="SELECT ID FROM MyItemsDatabase ORDER BY ID DESC LIMIT 1";
ResultSet rs = stmt1.executeQuery(query);
temp_int = rs.getInt(1);
if(temp_int >=0 && temp_int <=9){
    table.getColumnModel().getColumn(0).setPreferredWidth(15);
}
if(temp_int >=10 && temp_int <=99){
    table.getColumnModel().getColumn(0).setPreferredWidth(25);
}
if(temp_int >=100 && temp_int <=999){
    table.getColumnModel().getColumn(0).setPreferredWidth(35);
}
if(temp_int >=1000 && temp_int <=9999){
    table.getColumnModel().getColumn(0).setPreferredWidth(45);
}
rs.close();
    stmt1.close();
    c.close();
}
catch(Exception ex){
   
}

table.getColumnModel().getColumn(1).setPreferredWidth(220);
table.getColumnModel().getColumn(2).setPreferredWidth(130);
table.getColumnModel().getColumn(3).setPreferredWidth(75);
table.getColumnModel().getColumn(4).setPreferredWidth(90);
        

    }
    public void LoadData(){
   //  LOG.info("start loadData method");
   Connection c = null;
 try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
// c.setAutoCommit(false);
Statement stmt = c.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM TableDatabase");
ResultSetMetaData metaData = rs.getMetaData();

Vector<String> columnNames = new Vector<String>();
int columnCount = metaData.getColumnCount();
for(int i = 1;i<= columnCount;i++){
columnNames.add(metaData.getColumnName(i));
}
Vector<Vector<Object>> data = new Vector<Vector<Object>>();
while(rs.next()){
Vector<Object> vector = new Vector<Object>();
for(int i = 1;i<= columnCount;i++){
vector.add(rs.getObject(i));
}
data.add(vector);
}

model.setDataVector(data, columnNames);
     output.setForeground(Color.BLUE);
     
 DatabaseOperations db = new DatabaseOperations();
   db.LoadProfit();
     stmt.close();
rs.close();
c.close();
     output.setText("Database load complete");
 }
 
 catch(Exception e){
     output.setForeground(Color.red);
     output.setText("Error in loading data prva tabela");
 }
 }
 
    public void LoadDataSOLD(){
   
   Connection c = null;
 try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
// c.setAutoCommit(false);
Statement stmt = c.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM SoldDatabase");
ResultSetMetaData metaData = rs.getMetaData();

Vector<String> columnNames = new Vector<String>();
int columnCount = metaData.getColumnCount();
for(int i = 1;i<= columnCount;i++){
columnNames.add(metaData.getColumnName(i));
}
Vector<Vector<Object>> data = new Vector<Vector<Object>>();
while(rs.next()){
Vector<Object> vector = new Vector<Object>();
for(int i = 1;i<= columnCount;i++){
vector.add(rs.getObject(i));
}
data.add(vector);
}

model.setDataVector(data, columnNames);
     output.setForeground(Color.BLUE);
     stmt.close();
rs.close();
c.close();
 DatabaseOperations db = new DatabaseOperations();
   db.LoadProfit();
     
     output.setText("Database load complete");
 }
 catch(Exception e){
     output.setForeground(Color.red);
     output.setText("Error in loading data sold");
 }
 }
    
    public void LoadData3TABELA(){
 
   Connection c = null;
 try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
// c.setAutoCommit(false);
Statement stmt = c.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM MyitemsDatabase");
ResultSetMetaData metaData = rs.getMetaData();

Vector<String> columnNames = new Vector<String>();
int columnCount = metaData.getColumnCount();
for(int i = 1;i<= columnCount;i++){
columnNames.add(metaData.getColumnName(i));
}
Vector<Vector<Object>> data = new Vector<Vector<Object>>();
while(rs.next()){
Vector<Object> vector = new Vector<Object>();
for(int i = 1;i<= columnCount;i++){
vector.add(rs.getObject(i));
}
data.add(vector);
}

model.setDataVector(data, columnNames);
     output.setForeground(Color.BLUE);
     stmt.close();
rs.close();

c.close();
 DatabaseOperations db = new DatabaseOperations();
   db.LoadProfit();
     output.setText("Database load complete");
 }
 catch(Exception e){
     output.setForeground(Color.red);
     output.setText("Error in loading data 3 tabela");
 }
 }
    
private void UpdateSQLTable(int br_tabele){
    if(errorIndicator == 0){
     Connection c = null;
     int getrow = table.getSelectedRow();
     int[] getrows = table.getSelectedRows();

       Object getID = this.model.getValueAt(getrow, 0);
     Object getARTIKAL = this.model.getValueAt(getrow, 1);
     Object getSTANJE = this.model.getValueAt(getrow, 1);
     Object getKUPOVNA = this.model.getValueAt(getrow, 3);
    Object getPRODAJNA = this.model.getValueAt(getrow, 4);
     
 try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
// c.setAutoCommit(false);
String query = "UPDATE TableDatabase SET ID = ?,ARTIKAL = ?,STANJE = ?,KUPOVNA = ?,PRODAJNA = ? WHERE ID = ?";

    statusrow = row[2].toString();
PreparedStatement pst = c.prepareStatement(query);
pst.setObject(1, getID);
       pst.setObject(2, getARTIKAL);
       pst.setObject(3, statusrow);
       pst.setObject(4, getKUPOVNA);
     pst.setObject(5, getPRODAJNA);
     
         
     pst.setObject(6, getID);

pst.executeUpdate();
 pst.close();
    c.close();
    output.setForeground(Color.GREEN);
    output.setText("Update row complete");
 }
 catch(Exception e){
     output.setForeground(Color.GREEN);
     output.setText("Operation Update: ERROR");
 }
        
    }
    else{
    output.setForeground(Color.GREEN);
     output.setText("Invalid price value");
     errorIndicator=0;
    }
}
    
private void UpdateSQLTable(){
     Connection c = null;
     int getrow = table.getSelectedRow();
     
     int[] getrows = table.getSelectedRows();

       Object getID = this.model.getValueAt(getrow, 0);
     Object getARTIKAL = this.model.getValueAt(getrow, 1);
     Object getSTANJE = this.model.getValueAt(getrow, 1);
     Object getKUPOVNA = this.model.getValueAt(getrow, 3);
    Object getPRODAJNA = this.model.getValueAt(getrow, 4);
     
 try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
// c.setAutoCommit(false);

  String query="UPDATE MyitemsDatabase SET ID = ?,ARTIKAL = ?,STANJE = ?,KUPOVNA = ?,Dobavljac = ? WHERE ID = ?";

    statusrow = row[2].toString();
PreparedStatement pst = c.prepareStatement(query);
pst.setObject(1, getID);
       pst.setObject(2, getARTIKAL);
       pst.setObject(3, statusrow);
       pst.setObject(4, getKUPOVNA);
     pst.setObject(5, getPRODAJNA);
     
         
     pst.setObject(6, getID);

pst.executeUpdate();
 pst.close();
    c.close();
    output.setForeground(Color.GREEN);
    output.setText("Update row complete");
 }
 catch(Exception e){
     output.setForeground(Color.GREEN);
     output.setText("Operation Update: ERROR");
 }
    }

private void deleteSQL(){
Connection c = null;
int getrow = table.getSelectedRow();
Object getID = table.getValueAt(getrow,0);
Object getArtikal = table.getValueAt(getrow,1);

try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
c.setAutoCommit(false);
String query = "DELETE FROM TableDatabase WHERE ID = ?";
PreparedStatement pst = c.prepareStatement(query);
pst.setObject(1, getID);
//pst.setObject(2, getArtikal);

pst.executeUpdate();
 pst.close();
    c.commit();
    c.close();
    
}
catch(Exception e){
     System.out.println("Error in Deleting Row");
 }
}    
    
private void deleteSQL_3TABELA(){
Connection c = null;
int getrow = table.getSelectedRow();
Object getID = table.getValueAt(getrow,0);
Object getArtikal = table.getValueAt(getrow,1);

try{
c = DriverManager.getConnection("jdbc:sqlite:resources\\TableDB.db");
c.setAutoCommit(false);
String query = "DELETE FROM MyitemsDatabase WHERE ID = ? AND ARTIKAL = ?";
PreparedStatement pst = c.prepareStatement(query);
pst.setObject(1, getID);
pst.setObject(2, getArtikal);

pst.executeUpdate();
 pst.close();
    c.commit();
    c.close();
    
}
catch(Exception e){
     System.out.println("Error in Deleting Row");
 }
}    

 public void ProfitOutputReloadAfterUpdate(){
     total_string = Integer.toString(total_int);
      profit_value.setText(total_string);
    }
public void errorwindow1(){
String message = "Error in price input field. Enter numeric data in text field";
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
        JOptionPane.ERROR_MESSAGE);
    // nisam jo[ upotrebio ovu funkciju
}
public void extendIdWidth(){

} 
        }    