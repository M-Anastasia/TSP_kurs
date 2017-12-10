package beans;

import tables.City;

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
@ManagedBean(name = "city")
@SessionScoped
public class CityBean implements Serializable {
    public String pickedCity;
    public int c_id;
    public String name;

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

    public String getPickedCity() {
        return pickedCity;
    }

    public void setPickedCity(String pickedCity) {
        this.pickedCity = pickedCity;
    }

    public List<String> getCityList()throws SQLException, ClassNotFoundException{
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
                if(con==null)
                    throw new SQLException("Can't get database connection");
                Statement statmt = con.createStatement();
                ResultSet result = statmt.executeQuery("SELECT NAME FROM CITY");
                List<String> list = new ArrayList<String>();
                while (result.next()){
                    String city;
                    city = result.getString("NAME");
                    list.add(city);
        }
        return list;
    }

    public int checkID(String city) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = null;
        if (city!=null){
            result=statmt.executeQuery("SELECT CID FROM CITY WHERE NAME='"+city+"'");
            int c_id=0;
            while (result.next()){
                c_id=result.getInt("CID");
            }
            return c_id;
        }
        return 0;
    }

    public List<City> getAllCityList() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        if(con==null)
            throw new SQLException("Can't get database connection");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM CITY");
        List<City> list = new ArrayList<City>();
        while (result.next()){
            City city = new City();
            city.setC_id(result.getInt("CID"));
            city.setName(result.getString("NAME"));
            list.add(city);
        }
        return list;
    }

    public void add() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO CITY(CID,NAME) VALUES(?,?)");
        pstmt.setInt(1,this.getC_id());
        pstmt.setString(2,this.getName());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }
    }

    public void edit() throws SQLException, ClassNotFoundException {
        List<City> citys = getAllCityList();
        FacesContext fc = FacesContext.getCurrentInstance();
        int id;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        id = Integer.parseInt(request.getParameter("id"));
        for (City city : citys){
            if (city.getC_id() == id){
                this.setC_id(city.getC_id());
                this.setName(city.getName());
            }
        }
    }
    public  void update() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("UPDATE CITY SET NAME=? WHERE CID="+id);
        pstmt.setString(1,this.getName());
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
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM CITY WHERE CID=?");
        pstmt.setInt(1,id);
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete SuccessFully");
        }
    }
}
