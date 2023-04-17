import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Product {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product");
        frame.setContentPane(new Product().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/mystore", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }



    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from product");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }




    public Product() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String pname,price,qty;
                pname = txtName.getText();
                price = txtSalary.getText();
                qty = txtMobile.getText();

                try {
                    pst = con.prepareStatement("insert into product(pname,price,qty)values(?,?,?)");
                    pst.setString(1, pname);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Addedddd!");
                    //table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }







            }





            });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    String pid = txtid.getText();//

                    pst = con.prepareStatement("select pname,price,qty from product where id = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String ppname = rs.getString(1);//
                        String pprice  = rs.getString(2);//
                        String pqty = rs.getString(3);//

                        txtName.setText(ppname);//
                        txtSalary.setText(pprice );//
                        txtMobile.setText(pqty);//

                    }
                    else
                    {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }











            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




                String pid,pname,price,qty;
                pname = txtName.getText();
                price = txtSalary.getText();
                qty = txtMobile.getText();
                pid = txtid.getText();

                try {
                    pst = con.prepareStatement("update product set pname = ?,price = ?,qty = ? where id = ?");
                    pst.setString(1, pname);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Updateee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }






        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String pid;
                pid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from product  where id = ?");

                    pst.setString(1, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Deleteeeeee!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }









        });
    }
}
