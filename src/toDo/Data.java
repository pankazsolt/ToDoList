package toDo;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Data {

	private final IntegerProperty id;
	private LocalDate date;
	private final SimpleStringProperty owner;
	private final StringProperty task;

	public Data() {
		this.date = LocalDate.now();
		this.id = new SimpleIntegerProperty();
		this.owner = new SimpleStringProperty();
		this.task = new SimpleStringProperty();
	}

	public Data(LocalDate date, String owner, String task) {
		this.date = date;
		this.id = new SimpleIntegerProperty();
		this.owner = new SimpleStringProperty(owner);
		this.task = new SimpleStringProperty(task);
	}

	public Data(int id, LocalDate date, String owner, String task) {
		this.date = date;
		this.id = new SimpleIntegerProperty(id);
		this.owner = new SimpleStringProperty(owner);
		this.task = new SimpleStringProperty(task);
	}

	public String getOwner() {
		return owner.get();
	}

	public void setOwner(String ownerC) {
		owner.set(ownerC);
	}

	public String getTask() {
		return task.get();
	}

	public void setTask(String taskC) {
		task.set(taskC);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate dateC) {
		date = dateC;
	}

	public int getId() {
		return id.get();
	}

	public void setId(int Id) {
		id.set(Id);
	}
}