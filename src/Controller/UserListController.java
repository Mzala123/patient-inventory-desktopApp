/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.json.JSONException;

/**
 * FXML Controller class
 *
 * @author agile systems
 */
public class UserListController implements Initializable {

    @FXML
    private Pagination pagination;
    
    
    public static ObservableList<User> userList = FXCollections.observableArrayList();
   // FilteredList<User> filteredList = new FilteredList<>(positionList, e -> true);

    private TableView<User> tableUser = createUserTable();

    public static int index = 0;
    private final int rowsPerPage = 50;

    User user = new User();
    @FXML
    private StackPane stackpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    @FXML
    private void get_userList(ActionEvent event) throws JSONException {
      initializeData();
    }
    
    private void initializeData() throws JSONException {
        
        Region veil = new Region();
        JFXSpinner spinner = new JFXSpinner();
        Label fetching = new Label("Fetching data...");
        HBox box = new HBox();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                veil.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
                veil.setPrefSize(1117, 566);
                fetching.setStyle("-fx-text-fill:white; -fx-font-weight:bold;");
                spinner.setMaxSize(40, 40);
                box.setPadding(new Insets(0, 2, 0, 2));
                System.out.println("Mwafika panopa to table");
                user.user_list();
                System.out.println(userList);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
                        pagination.setPageFactory(new Callback<Integer, Node>() {
                            @Override
                            public Node call(Integer pageIndex) {
                                try {
                                    int fromIndex = pageIndex * rowsPerPage;
                                    int toIndex = Math.min(fromIndex + rowsPerPage, userList.size());
                                    tableUser.setItems(FXCollections.observableArrayList(userList.subList(fromIndex, toIndex)));
                                    return tableUser;
                                } catch (IllegalArgumentException ex) {
                                    return new Label("No such user Data\nNote: add user");
                                }

                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
                return null;
            }
        };
        spinner.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        spinner.visibleProperty().bind(task.runningProperty());
        box.visibleProperty().bind(task.runningProperty());
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(spinner, fetching);
        stackpane.getChildren().addAll(veil, box);
        Thread thread = new Thread(task);
        thread.start();

    }
    
     private Node createPage(int pageIndex) {
        try {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage,userList.size());
            tableUser.setItems(FXCollections.observableArrayList(userList.subList(fromIndex, toIndex)));
            return tableUser;
        } catch (IllegalArgumentException ex) {
            return new Label("No such user Data\nNote: add user");
        }

    }
    
    
    
    private TableView<User> createUserTable(){
        TableView<User> table = new TableView<>();
        TableColumn<User, String> col_primary_id = new TableColumn<>("ID");
        col_primary_id.setCellValueFactory(new PropertyValueFactory<>("primary_id"));
        col_primary_id.setMinWidth(10);
        col_primary_id.setPrefWidth(100);
        col_primary_id.setMaxWidth(70);
        
        TableColumn<User, String> col_useremail = new TableColumn<>("User Email");
        col_useremail.setCellValueFactory(new PropertyValueFactory<>("useremail"));
        col_useremail.setMinWidth(10);
        col_useremail.setPrefWidth(100);
        col_useremail.setCellFactory(WRAPPING_CELL_FACTORY);
        
        TableColumn<User, String> col_username = new TableColumn<>("User Name");
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_username.setMinWidth(10);
        col_username.setPrefWidth(100);
  
        
        TableColumn<User, String> col_phone = new TableColumn<>("Phonenumber");
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        col_phone.setMinWidth(10);
        col_phone.setPrefWidth(100);
      
        
        TableColumn<User, String> col_role = new TableColumn<>("Role");
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_role.setMinWidth(10);
        col_role.setPrefWidth(100);
   
        
        TableColumn<User, String> col_avatar = new TableColumn<>("Avatar");
        col_avatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        col_avatar.setMinWidth(10);
        col_avatar.setPrefWidth(100);
        col_avatar.setCellFactory(WRAPPING_CELL_FACTORY);
        
        table.getColumns().addAll(col_primary_id, col_useremail, col_username, col_phone, col_role, col_avatar);
          for (TableColumn column : table.getColumns()) {
            column.setMinWidth(100);
        }

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        return table;
    }
    
     //start here
    public static final Callback<TableColumn<User, String>, TableCell<User, String>> WRAPPING_CELL_FACTORY
            = new Callback<TableColumn<User, String>, TableCell<User, String>>() {
        @Override
        public TableCell<User, String> call(TableColumn<User, String> param) {
            TableCell<User, String> tableCell = new TableCell<User, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (item == getItem()) {
                        return;
                    }

                    super.updateItem(item, empty);

                    if (item == null) {
                        super.setText(null);
                        super.setGraphic(null);
                    } else {
                        super.setText(null);
                        Label l = new Label(item);
                        l.setWrapText(true);
                        VBox box = new VBox(l);
                        l.heightProperty().addListener((observable, oldValue, newValue) -> {
                            box.setPrefHeight(newValue.doubleValue() + 7);
                            Platform.runLater(() -> this.getTableRow().requestLayout());
                        });
                        super.setGraphic(box);
                    }
                }

            };

            return tableCell;
        }

    };

    // end here 

    
    
}
