package beans;

import tables.Purchase;
import tables.Seance;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nastya on 16.04.2017.
 */
@ManagedBean(name = "seance")
@SessionScoped
public class SeanceBean {
    public List<Integer> f_ids;

    public int s_id;
    public int f_id;
    public int t_id;
    public int freeSpots;
    public int price;

    public int totalPrice = 0;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }

    public List<Integer> getF_ids() {
        return f_ids;
    }

    public void setF_ids(List<Integer> f_ids) {
        this.f_ids = f_ids;
    }

    public List<Integer> getFIDs(int t_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = null;
        if (t_id != 0) {
            result = statmt.executeQuery("SELECT FID FROM SEANCE WHERE TID=" + t_id);
            f_ids = new ArrayList<Integer>();
//            freeSpots = new ArrayList<Seance>();
            while (result.next()) {
                Integer f_id;
                Seance seance = new Seance();
                f_id = result.getInt("FID");
                System.out.println(f_id);
//                seance.setFreeSpots(result.getInt("FREESPOTS"));
                f_ids.add(f_id);
//                freeSpots.add(seance);
            }
            return f_ids;
        }
        return null;
    }

    public List<Seance> getAllSeanceList() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT SID, NAME ,FREESPOTS,PRICE FROM SEANCE, FILM WHERE SEANCE.FID=FILM.FID");
        List<Seance> list = new ArrayList<Seance>();
        while (result.next()) {
            Seance seance = new Seance();
            seance.setS_id(result.getInt("SID"));
            seance.setF_name(result.getString("NAME"));
            seance.setFreeSpots(result.getInt("FREESPOTS"));
            seance.setPrice(result.getInt("PRICE"));
            list.add(seance);
        }
        return list;
    }

    public void add() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO SEANCE(SID,FID,TID,FREESPOTS,PRICE) VALUES(?,?,?,?,?)");
        pstmt.setInt(1, this.getS_id());
        pstmt.setInt(2, this.getF_id());
        pstmt.setInt(3, this.getT_id());
        pstmt.setInt(4, this.getFreeSpots());
        pstmt.setInt(5, this.getPrice());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }
    }

    public void edit() throws SQLException, ClassNotFoundException {
        List<Seance> seances = getAllSeanceList();
        FacesContext fc = FacesContext.getCurrentInstance();
        int id;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        id = Integer.parseInt(request.getParameter("id"));
        for (Seance seance : seances) {
            if (seance.getS_id() == id) {
                this.setS_id(seance.getS_id());
                this.setF_id(seance.getF_id());
                this.setT_id(seance.getT_id());
                this.setFreeSpots(seance.getFreeSpots());
                this.setPrice(seance.getPrice());
            }
        }
    }

    public void update() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("UPDATE SEANCE SET FID=?, TID=?, FREESPOTS=?, PRICE=? WHERE SID=" + id);
        pstmt.setInt(1, this.getF_id());
        pstmt.setInt(2, this.getT_id());
        pstmt.setInt(3, this.getFreeSpots());
        pstmt.setInt(4, this.getPrice());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }

    }

    public void delete() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM SEANCE WHERE SID=?");
        pstmt.setInt(1, id);
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete SuccessFully");
        }
    }

    public void buy(String name) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int price = Integer.parseInt(request.getParameter("price"));
        totalPrice += price;
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT FREESPOTS FROM SEANCE WHERE PRICE=" + price);
        int frSp = 0;
        while (result.next()) {
            frSp = result.getInt("FREESPOTS");
        }
//        ResultSet result1 = statmt.executeQuery("SELECT UID FROM USERS WHERE NAME=" + "'"+name+"'");
//        int u_id = 0;
//        while (result1.next()) {
//            u_id = result1.getInt("UID");
//        }
        PreparedStatement pstmt = con.prepareStatement("UPDATE SEANCE SET FREESPOTS=? WHERE PRICE=" + price);
        pstmt.setInt(1, frSp - 1);
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }
        PreparedStatement pstmtA = con.prepareStatement("INSERT INTO PURCHASE(PID, NAME, PRICE) VALUES (?,?,?)");
        pstmtA.setInt(1, Purchase.getRowsCount());
        pstmtA.setString(2, name);
        pstmtA.setInt(3,price);
        int executeUpdate1 = pstmtA.executeUpdate();
        if (executeUpdate1 > 0) {
            System.out.println("Update SuccessFully");
        }
    }
}
