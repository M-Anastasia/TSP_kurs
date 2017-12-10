package tables;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nastya on 20.04.2017.
 */
@ManagedBean(name = "purchase")
@SessionScoped
public class Purchase {
    public int p_id;
    public String name;
    public int price;
    public static int k=0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static int getRowsCount()throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT PID FROM PURCHASE");
        while (result.next()){
            k++;
        }
        return k;
    }

    public List<Purchase> getAllPurchase()throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM PURCHASE");
        List<Purchase> list = new ArrayList<Purchase>();
        while (result.next()){
            Purchase purchase = new Purchase();
            purchase.setP_id(result.getInt("PID"));
            purchase.setName(result.getString("NAME"));
            purchase.setPrice(result.getInt("PRICE"));
            list.add(purchase);
        }
        return list;
    }
}
