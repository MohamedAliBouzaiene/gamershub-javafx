/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamershub.Gui;

import gamershub.Entities.User;
import gamershub.Gamershub;
import gamershub.Services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author MAB
 */
public class ProfilePageController implements Initializable {

    @FXML
    private ImageView userImage;
    @FXML
    private Label username;
    @FXML
    private Pane verified;
    @FXML
    private Label name;
    @FXML
    private Label secondName;
    @FXML
    private Label coins;
    @FXML
    private Button updateProfile;
    @FXML
    private Button updatePass;
    @FXML
    private Button deleteAcc;

    public void setInfos() {
        this.username.setText(Gamershub.loggedUser.getUsername());
        this.userImage.setImage(new Image("https://avatars.dicebear.com/api/bottts/" + Gamershub.loggedUser.getUsername() + ".png"));
        this.coins.setText(Integer.toString(Gamershub.loggedUser.getCoins()));
        this.name.setText(Gamershub.loggedUser.getName());
        this.secondName.setText(Gamershub.loggedUser.getSecondName());
        this.verified.setVisible(Gamershub.loggedUser.getIsVerified() == 1);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setInfos();
    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == updateProfile) {
            try {
                FXMLLoader nodeLoader = new FXMLLoader(getClass().getResource("ProfileUpdate.fxml"));
                Parent node = nodeLoader.load();
                FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("HomeFront.fxml"));
                Parent homeRoot = homeLoader.load();
                HomeFrontController homeCtrl = homeLoader.getController();
                homeCtrl.changePage("UPDATE MY PROFILE", node);
                username.getScene().setRoot(homeRoot);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if(event.getSource() == updatePass){
            try {
                FXMLLoader nodeLoader = new FXMLLoader(getClass().getResource("PasswordUpdateForm.fxml"));
                Parent node = nodeLoader.load();
                FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("HomeFront.fxml"));
                Parent homeRoot = homeLoader.load();
                HomeFrontController homeCtrl = homeLoader.getController();
                homeCtrl.changePage("UPDATE PASSWORD", node);
                username.getScene().setRoot(homeRoot);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if(event.getSource() == deleteAcc){
            UserService us = new UserService();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
                Parent root = loader.load();
                us.deleteByUsername(Gamershub.loggedUser.getUsername());
                Gamershub.loggedUser = new User();
                this.username.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProfilePageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
