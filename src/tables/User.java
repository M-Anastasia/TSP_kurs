package tables;

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
@ManagedBean(name = "user")
@SessionScoped
public class User {
    public int u_id;
    public String message = "Enter username and password";
    public String name;
    public String password;
    public int k=0;

    public String login() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM USERS WHERE UID=0");
        List<User> list = new ArrayList<User>();
        while (result.next()){
            User user = new User();
            user.setU_id(result.getInt("UID"));
            user.setName(result.getString("NAME"));
            user.setPassword(result.getString("PASWORD"));
            list.add(user);
        }
        if ((list.get(0).getName().equalsIgnoreCase(name))&&(list.get(0).getPassword()).equalsIgnoreCase(password)){
            message="successfully log in";
            return "success";
        }
        else {
            message ="Wrong credentials.";
            return "login";
        }
    }

    public String reg()throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
//        Statement statmt = con.createStatement();
        k++;
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO USERS(UID,NAME,PASWORD) VALUES(?,?,?)");
        pstmt.setInt(1,getRowsCount());
        pstmt.setString(2,this.getName());
        pstmt.setString(3,this.getPassword());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Added SuccessFully");
        }
        message="successfully sign up";
        return "homeAfterReg";
    }

    public String loginAsUser() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM USERS");
        List<User> list = new ArrayList<User>();
        while (result.next()){
            User user = new User();
            user.setU_id(result.getInt("UID"));
            user.setName(result.getString("NAME"));
            user.setPassword(result.getString("PASWORD"));
            list.add(user);
        }
        for (int i=0; i<list.size(); i++){
            if ((list.get(i).getName().equalsIgnoreCase(name))&&(list.get(i).getPassword()).equalsIgnoreCase(password)){
                message="successfully log in";
                return "homeAfterReg";
            }
        }
        message ="Wrong credentials.";
        return "loginAsUser";
    }

    public int getRowsCount()throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT UID FROM USERS");
        while (result.next()){
            k++;
        }
        return k;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getAllUser() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM USERS");
        List<User> list = new ArrayList<User>();
        while (result.next()){
            User user = new User();
            user.setU_id(result.getInt("UID"));
            user.setName(result.getString("NAME"));
            user.setPassword(result.getString("PASWORD"));
            list.add(user);
        }
        return list;
    }



    public void add() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO USERS(UID,NAME,PASWORD) VALUES(?,?,?)");
        pstmt.setInt(1,this.getU_id());
        pstmt.setString(2,this.getName());
        pstmt.setString(3,this.getPassword());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }
    }

    public void edit() throws SQLException, ClassNotFoundException {
        List<User> users = getAllUser();
        FacesContext fc = FacesContext.getCurrentInstance();
        int id;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        id = Integer.parseInt(request.getParameter("id"));
        for (User user : users){
            if (user.getU_id() == id){
                this.setU_id(user.getU_id());
                this.setName(user.getName());
                this.setPassword(user.getPassword());
            }
        }
    }
    public  void update() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("UPDATE USERS SET NAME=?, PASWORD=? WHERE UID="+id);
        pstmt.setString(1,this.getName());
        pstmt.setString(2,this.getPassword());
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
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM USERS WHERE UID=?");
        pstmt.setInt(1,id);
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete SuccessFully");
        }
    }
}
