package toDo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateStringConverter;

public class Data {

	private final IntegerProperty id;
	private LocalDate date;
    private final SimpleStringProperty owner;
    private final StringProperty task;
    private CheckBox checkBox;
    
	
/**    public Data(){
        this(LocalDate.now(), "", "");
    }
    public Data(LocalDate  dateC, String taskC, String ownerC) {
        this("", dateC, taskC, ownerC);
    }
    public Data(int id, LocalDate  dateC, String taskC, String ownerC) {
        this(id, dateC, taskC, ownerC, new Boolean(true));
    }
    public Data(int id, LocalDate  dateC, String taskC, String ownerC, Boolean checkC) {
        this.DateColoumn = dateC;
        this.TaskColoumn = new SimpleStringProperty (taskC);
        this.OwnerColoumn = new SimpleStringProperty (ownerC);
        this.CheckColoumn = new Boolean(true);
        this.id = new SimpleIntegerProperty(id);
    }
   */ 
    public Data () {
    	this.date = LocalDate.now();
    	this.id = new SimpleIntegerProperty();
    	this.owner = new SimpleStringProperty();
    	this.task = new SimpleStringProperty();
    	this.checkBox = new CheckBox();
    }
    
    public Data(int id, LocalDate date, String owner, String task) {
    	this.date = date;
    	this.id = new SimpleIntegerProperty(id);
    	this.owner = new SimpleStringProperty(owner);
    	this.task = new SimpleStringProperty(task);
    	this.checkBox = new CheckBox();
    }
    
    
    public String getOwner() {
        return owner.get();
    }
    
    public void setOwner(String ownerC){
        owner.set(ownerC);
    }

    public String getTask() {
        return task.get();
    }
    
    public void setTask(String taskC){
        task.set(taskC);
    }
    
    public LocalDate getDate(){
           return date;
       }
    
        public void setDate(LocalDate dateC){
       date = dateC;
    }

    public int getId() {
        return id.get();
    }
    public void setId(int Id){
        id.set(Id);
    }
    
    public CheckBox getCheckBox(){
        return checkBox;
    }
    public void setCheckBox(CheckBox checkBox){
        this.checkBox = checkBox;
    }
}