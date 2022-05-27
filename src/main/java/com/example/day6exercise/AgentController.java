/**
 * Sample Skeleton for 'agent-view.fxml' Controller Class
 */

package com.example.day6exercise;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AgentController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="cbAgentId"
    private ComboBox<String> cbAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyId"
    private TextField tfAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentId"
    private TextField tfAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfBusPhone"
    private TextField tfBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfEmail"
    private TextField tfEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfFirstName"
    private TextField tfFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tfLastName"
    private TextField tfLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tfMiddleInitial"
    private TextField tfMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tfPosition"
    private TextField tfPosition; // Value injected by FXMLLoader

    ObservableList<Agent> data = FXCollections.observableArrayList();

    List<String> ID = new ArrayList<>();

    private int selectedIndex;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert cbAgentId != null : "fx:id=\"cbAgentId\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfBusPhone != null : "fx:id=\"tfBusPhone\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfFirstName != null : "fx:id=\"tfFirstName\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfLastName != null : "fx:id=\"tfLastName\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfMiddleInitial != null : "fx:id=\"tfMiddleInitial\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tfPosition != null : "fx:id=\"tfPosition\" was not injected: check your FXML file 'agent-view.fxml'.";

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSaveClicked(mouseEvent);
            }
        });

        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnEditClicked(mouseEvent);
            }
        });


        cbAgentId.setItems(FXCollections.observableArrayList(getAgentId()));

        cbAgentId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                displayAgent((Integer.parseInt(cbAgentId.getValue()))-1);
                btnSave.setDisable(true);
                btnEdit.setDisable(false);
            }
        });


    }

    private void btnEditClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(false);
        btnEdit.setDisable(true);

        tfAgentId.setDisable(false);
        tfFirstName.setDisable(false);
        tfMiddleInitial.setDisable(false);
        tfLastName.setDisable(false);
        tfBusPhone.setDisable(false);
        tfEmail.setDisable(false);
        tfPosition.setDisable(false);
        tfAgencyId.setDisable(false);
    }

    private void btnSaveClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnEdit.setDisable(false);

        tfAgentId.setDisable(true);
        tfFirstName.setDisable(true);
        tfMiddleInitial.setDisable(true);
        tfLastName.setDisable(true);
        tfBusPhone.setDisable(true);
        tfEmail.setDisable(true);
        tfPosition.setDisable(true);
        tfAgencyId.setDisable(true);

        String userName = "Becca";
        String password = "P@ssw0rd";


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", userName, password);

            String sql = "UPDATE `agents` SET `AgtFirstName`=?,`AgtMiddleInitial`=?," +
                    "`AgtLastName`=?,`AgtBusPhone`=?,`AgtEmail`=?,`AgtPosition`=?,`AgencyId`=? WHERE AgentId=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tfFirstName.getText());
            stmt.setString(2, tfMiddleInitial.getText());
            stmt.setString(3, tfLastName.getText());
            stmt.setString(4, tfBusPhone.getText());
            stmt.setString(5, tfEmail.getText());
            stmt.setString(6, tfPosition.getText());
            stmt.setInt(7, Integer.parseInt(tfAgencyId.getText()));
            stmt.setInt(8, Integer.parseInt(tfAgentId.getText()));
            int numRows = stmt.executeUpdate();
            if (numRows == 0)
            {
                System.out.println("update failed");
            }
            else
            {
                //create an agent object and set it into the selected index of the observable list
                data.set(selectedIndex, new Agent(Integer.parseInt(tfAgentId.getText()), tfFirstName.getText(),
                        tfMiddleInitial.getText(), tfLastName.getText(), tfBusPhone.getText(),
                        tfEmail.getText(), tfPosition.getText(), Integer.parseInt(tfAgencyId.getText())));
            }

            //get reference to stage and close it
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<String> getAgentId() {
        String userName = "Becca";
        String password = "P@ssw0rd";


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", userName, password);
            String query = "select * from agents";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                ID.add(String.valueOf((rs.getInt("AgentId"))));
                data.add(new Agent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
            }

            conn.close();
            return ID;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void displayAgent(int selectedIndex) {

        this.selectedIndex = selectedIndex;

        Agent a = data.get(selectedIndex);
        tfAgentId.setText(a.getAgentId() + "");
        tfFirstName.setText(a.getAgtFirstName());
        tfMiddleInitial.setText(a.getAgtMiddleInitial());
        tfLastName.setText(a.getAgtLastName());
        tfBusPhone.setText(a.getAgtBusPhone());
        tfEmail.setText(a.getAgtEmail());
        tfPosition.setText(a.getAgtPosition());
        tfAgencyId.setText(a.getAgencyId() + "");

        tfAgentId.setDisable(true);
        tfFirstName.setDisable(true);
        tfMiddleInitial.setDisable(true);
        tfLastName.setDisable(true);
        tfBusPhone.setDisable(true);
        tfEmail.setDisable(true);
        tfPosition.setDisable(true);
        tfAgencyId.setDisable(true);

    }

}
