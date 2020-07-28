package toDo;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;

import javax.swing.plaf.basic.BasicTreeUI;
import javax.xml.bind.ParseConversionEvent;

import com.sun.javafx.scene.control.skin.DatePickerContent;

public class FXMLDocumentController implements Initializable {
	
    String Task;
    String Owner;
    Statement statement;

    @FXML
    private Button SaveButton;
    @FXML
    private TextField TaskText;
    @FXML
    private TextField OwnerText;
    @FXML
    private TextField ExpText;
    @FXML
    private Label TaskLabel;
    @FXML
    private Label OwnerLabel;
    @FXML
    private Label DateLabel;
    @FXML
    private Label ExpLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Pane basePane;
    @FXML
    private Pane alertPane;
    @FXML
    private Label alertText;
    @FXML
    private Button alertButton;
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, Integer>tc1;
    @FXML
    private TableColumn<Data, LocalDate>tc2;
    @FXML
    private TableColumn<Data, String>tc3;
    @FXML
    private TableColumn<Data, String>tc4;
    @FXML
    private Button DelButton;
    @FXML
    private Button ExpButton;
    
    
    Connection cn;

    @FXML
    private ObservableList<Data> info = FXCollections.observableArrayList();
    
    public void Date(ActionEvent event) {
    LocalDate ld = datePicker.getValue();
    }

    @FXML
    private void addTask(ActionEvent event) throws SQLException {

        if (TaskText.getText().isEmpty() || OwnerText.getText().isEmpty()) {
            popUp();
            table.setItems(null);
        	table.setItems(info);
        } else {
        
        }
            
       	Data nd = new Data (3, datePicker.getValue(), OwnerText.getText(), TaskText.getText());
       	System.out.println(datePicker.getValue());
       	db.addTask(nd);
       	System.out.println("OK1");
       	System.out.println("OK2");
                   TaskText.clear();
            OwnerText.clear();
            
            info.clear();
              loadDataFromDatabase();
       
        }

    @FXML
    private void handleButtonAlert(ActionEvent event) {
        popDown();
    }

        @FXML
    private void expButtonAction(ActionEvent event) {
      String fileName = ExpText.getText();
      fileName = fileName.replaceAll("\\s+", "");
      if (fileName != null && !fileName.equals("")) {
          PDFGenerator pg = new PDFGenerator();
          pg.PDFGenerator(fileName, info);
          System.out.println("exportálva");
      } else {
          popUp();
      }
    }

    @FXML
    private void delButtonAction(ActionEvent event) {
      System.out.println("törlés");
        	
    	ObservableList<Data> delList = table.getSelectionModel().getSelectedItems();
        db.removeTask(delList);
        info.removeAll(delList);        
    }
    

    private void popUp() {
        basePane.setDisable(true);
        basePane.setOpacity(0.4);
        alertPane.setVisible(true);
        alertText.setText("Fill the gap please!");
    }

    private String popDown() {
        basePane.setDisable(false);
        basePane.setOpacity(1);
        alertPane.setVisible(false);

        return null;
    }


    private Db db;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

     
    	db = new Db();
    	loadDataFromDatabase();    	
    	
        }

	
    
    public void loadDataFromDatabase () {

    	
    	tc1.setCellValueFactory(new PropertyValueFactory<>("No"));
    	tc2.setCellValueFactory(new PropertyValueFactory<>("date"));

    	tc3.setCellValueFactory(new PropertyValueFactory<>("owner"));
    	tc3.setCellFactory(TextFieldTableCell.forTableColumn());
    	tc3.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data, String> t) {
                Data actualdata = t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualdata.setOwner(t.getNewValue());
                db.updateTask(actualdata);
            }
        });
    	tc4.setCellValueFactory(new PropertyValueFactory<>("task"));    	
    	tc4.setCellFactory(TextFieldTableCell.forTableColumn());
    	tc4.setOnEditCommit(
    	new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Data, String> t) {
                Data actualdata = t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualdata.setTask(t.getNewValue());
                db.updateTask(actualdata);
            }});
    	
    	table.setEditable(true);
    	
    	info.addAll(db.getAllTask());
        
        table.setItems(info);
        
    ObservableList<Data> autoDelList = FXCollections.observableArrayList();            
        for (int i = 0; i < info.size(); i++){     
        if ((tc2.getCellData(i)).isBefore(LocalDate.now())){
            autoDelList.add(info.get(i));
            info.remove(autoDelList);
            db.removeTask(autoDelList);   
        }
    }
        info.clear();
        info.addAll(db.getAllTask());

    	table.getSortOrder().setAll(tc2);
    	
    	tc1.setCellValueFactory(new Callback<CellDataFeatures<Data, Integer>, ObservableValue<Integer>>() {
    	  @Override public ObservableValue<Integer> call(CellDataFeatures<Data, Integer> p) {
    	    return new ReadOnlyObjectWrapper(table.getItems().indexOf(p.getValue())+1 + "");
    	  }
    	});
    	   
    	tc1.setSortable(false);
    	table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    
}
