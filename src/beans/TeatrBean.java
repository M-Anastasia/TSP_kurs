package beans;

import tables.Teatr;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nastya on 16.04.2017.
 */
@ManagedBean(name = "teatr")
@SessionScoped
public class TeatrBean implements Serializable{
    public String pickedTeatr;

    public int t_id;
    public int c_id;
    public String name;

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickedTeatr() {
        return pickedTeatr;
    }

    public void setPickedTeatr(String pickedTeatr) {
        this.pickedTeatr = pickedTeatr;
    }

    public List<String> getTeatrList(int c_id)throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = null;
        if (c_id!=0){
            result=statmt.executeQuery("SELECT NAME FROM TEATR WHERE CID="+c_id);
            List<String> list = new ArrayList<String>();
            while (result.next()){
                String teatr;
                teatr = result.getString("NAME");
                list.add(teatr);
            }
            return list;
        }
        return null;
    }

    public int checkID(String teatr) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = null;
        if (teatr!=null){
            result=statmt.executeQuery("SELECT TID FROM TEATR WHERE NAME='"+teatr+"'");
            int t_id=0;
            while (result.next()){
                t_id=result.getInt("TID");
            }
            return t_id;
        }
        return 0;
    }

    public List<Teatr> getAllTeatrList()throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM TEATR");
        List<Teatr> list = new ArrayList<Teatr>();
        while (result.next()){
            Teatr teatr = new Teatr();
            teatr.setT_id(result.getInt("TID"));
            teatr.setC_id(result.getInt("CID"));
            teatr.setName(result.getString("NAME"));
            list.add(teatr);
        }
        return list;
    }

    public void add() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO TEATR(TID,CID,NAME) VALUES(?,?,?)");
        pstmt.setInt(1,this.getT_id());
        pstmt.setInt(2,this.getC_id());
        pstmt.setString(3,this.getName());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }
    }

    public void edit() throws SQLException, ClassNotFoundException {
        List<Teatr> teatrs = getAllTeatrList();
        FacesContext fc = FacesContext.getCurrentInstance();
        int id;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        id = Integer.parseInt(request.getParameter("id"));
        for (Teatr teatr : teatrs){
            if (teatr.getT_id() == id){
                this.setT_id(teatr.getT_id());
                this.setC_id(teatr.getC_id());
                this.setName(teatr.getName());
            }
        }
    }
    public  void update() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("UPDATE TEATR SET CID=?, NAME=? WHERE TID="+id);
        pstmt.setInt(1,this.getC_id());
        pstmt.setString(2,this.getName());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }

    }

    public void delete()throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM TEATR WHERE TID=?");
        pstmt.setInt(1,id);
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete SuccessFully");
        }
    }
}
