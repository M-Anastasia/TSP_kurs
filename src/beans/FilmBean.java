package beans;

import tables.Film;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nastya on 15.04.2017.
 */
@ManagedBean(name = "film")
@SessionScoped
public class FilmBean implements Serializable{

    public int f_id;
    public String name;
    public int length;
    public String country;
    public int year;
    public String genre;
    public double reiting;

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getReiting() {
        return reiting;
    }

    public void setReiting(double reiting) {
        this.reiting = reiting;
    }

    public List<Film> getFilmList(List<Integer> f_ids,int t_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        Statement statmt = con.createStatement();
        ResultSet result = null;
        if (f_ids!=null){
            List<Film> list = new ArrayList<Film>();
            for (int i=0; i<f_ids.size(); i++){
                result = statmt.executeQuery("SELECT NAME, LENGTH, COUNTRY, YEAR, GENRE, REITING, FREESPOTS, PRICE FROM FILM, SEANCE WHERE FILM.FID="+f_ids.get(i)+" AND SEANCE.FID="+f_ids.get(i)+" AND SEANCE.TID="+t_id);
                while (result.next()){
                    Film film = new Film();
                    film.setName(result.getString("NAME"));
                    film.setLength(result.getInt("LENGTH"));
                    film.setCountry(result.getString("COUNTRY"));
                    film.setYear(result.getInt("YEAR"));
                    film.setGenre(result.getString("GENRE"));
                    film.setReiting(result.getDouble("REITING"));
                    film.setFreeSpots(result.getInt("FREESPOTS"));
                    film.setPrice(result.getInt("PRICE"));
                    list.add(film);
                }
            }
            return list;
        }
        return null;
    }

    public List<Film> getAllFilmList() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        if(con==null)
            throw new SQLException("Can't get database connection");
        Statement statmt = con.createStatement();
        ResultSet result = statmt.executeQuery("SELECT * FROM FILM");
        List<Film> list = new ArrayList<Film>();
        while (result.next()){
            Film film = new Film();
            film.setF_id(result.getInt("FID"));
            film.setName(result.getString("NAME"));
            film.setLength(result.getInt("LENGTH"));
            film.setCountry(result.getString("COUNTRY"));
            film.setYear(result.getInt("YEAR"));
            film.setGenre(result.getString("GENRE"));
            film.setReiting(result.getDouble("REITING"));
            list.add(film);
        }
        return list;
    }

    public void add() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO FILM(FID,NAME,LENGTH,COUNTRY,YEAR,GENRE,REITING) VALUES(?,?,?,?,?,?,?)");
        pstmt.setInt(1,this.getF_id());
        pstmt.setString(2,this.getName());
        pstmt.setInt(3,this.getLength());
        pstmt.setString(4,this.getCountry());
        pstmt.setInt(5,this.getYear());
        pstmt.setString(6,this.getGenre());
        pstmt.setDouble(7,this.getReiting());
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update SuccessFully");
        }
    }

    public void edit() throws SQLException, ClassNotFoundException {
        List<Film> films = getAllFilmList();
        FacesContext fc = FacesContext.getCurrentInstance();
        int id;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        id = Integer.parseInt(request.getParameter("id"));
        for (Film film : films){
            if (film.getF_id() == id){
                this.setF_id(film.getF_id());
                this.setName(film.getName());
                this.setLength(film.getLength());
                this.setCountry(film.getCountry());
                this.setYear(film.getYear());
                this.setGenre(film.getGenre());
                this.setReiting(film.getReiting());
            }
        }
    }
    public  void update() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Nastya/IdeaProjects/TSP_kurs/tspDB.db");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        PreparedStatement pstmt = con.prepareStatement("UPDATE FILM SET NAME=?, LENGTH=?, COUNTRY=?, YEAR=?, GENRE=?, REITING=? WHERE FID="+id);
        pstmt.setString(1,this.getName());
        pstmt.setInt(2,this.getLength());
        pstmt.setString(3,this.getCountry());
        pstmt.setInt(4,this.getYear());
        pstmt.setString(5,this.getGenre());
        pstmt.setDouble(6,this.getReiting());
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
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM FILM WHERE FID=?");
        pstmt.setInt(1,id);
        int executeUpdate = pstmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete SuccessFully");
        }
    }
}
