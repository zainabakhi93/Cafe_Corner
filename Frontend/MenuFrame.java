package Frontend;

import Backend.MenuDAO;
import Backend.MenuItem;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class MenuFrame extends JFrame {


    JTable table;


    public MenuFrame(){


        setTitle("Cafe Menu");


        setSize(500,400);


        setLocationRelativeTo(null);



        String column[]={"Drink/Food","Price"};



        DefaultTableModel model=
                new DefaultTableModel(column,0);



         ArrayList<MenuItem> list = MenuDAO.getAllItems();



        for(MenuItem item:list){


            model.addRow(
                    new Object[]{
                            item.getName(),
                            item.getPrice()
                    }
            );


        }



        table=new JTable(model);


        add(new JScrollPane(table));



        setVisible(true);


    }


}