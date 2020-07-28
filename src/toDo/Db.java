package toDo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.ObservableList;



public class Db {

	Connection con = connection();
	
	public Connection connection () {
		try {
			String url = "jdbc:mysql://localhost:3306/toDoList?seUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String user = "miki";
			String password = "miki";
			Connection con = DriverManager.getConnection(url, user, password);
			return con;
		}catch (SQLException ex){
			System.out.println("Nem jó a Db");
		}
		return null;
		
	}
	
	
    public void addTask(Data data) {
        try {
            String sql = "insert into toDoList (date, owner, task) values (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            LocalDate ld = data.getDate();
            System.out.println(ld);
            pst.setDate(1, Date.valueOf(ld));
            pst.setString(2, data.getOwner());
            pst.setString(3, data.getTask());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("addTask-nál hiba");
            System.out.println("" + ex);
        }
    }
	
		
    public void removeTask(ObservableList<Data> data) {
        for (int i = 0; i < data.size(); i++) {
            try {
                String sql = "delete from toDoList where id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.valueOf(data.get(i).getId()));
                preparedStatement.execute();
                System.out.println("Törlés OK1 data id " + data.get(i).getId());
            } catch (SQLException ex) {
                System.out.println("Valami baj van a task törlésekor");
                System.out.println("" + ex);
            }
        }
    }
    
    
    public void updateTask(Data data) {
        try {
            String sql = "update TODOLIST set Date = ?, Owner = ?, Task = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            LocalDate ld = data.getDate();
            
            preparedStatement.setDate(1, Date.valueOf(ld));
            preparedStatement.setString(2, data.getOwner());
            preparedStatement.setString(3, data.getTask());
            preparedStatement.setInt(4, data.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a feladat hozzáadásakor");
            System.out.println("" + ex);
        }
    }
    
	
	Statement st = null;
	ResultSet rs = null;
	Statement createStatement = null;
	
	
	public ArrayList<Data> getAllTask() {
        String sql = "select * from toDoList";
        ArrayList<Data> users = null;
		
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            users = new ArrayList<>();
            while (rs.next()) {
                Data actualdata = new Data(rs.getInt("id"), rs.getDate("date").toLocalDate(), rs.getString("Owner"), rs.getString("Task"));
                users.add(actualdata);                
            }    
        } catch (SQLException ex) {
            System.out.println("getAllTask-nál hiba");
            System.out.println("" + ex);
        }
        
        return users;
    }
    
	}
