package com.example.coursemanagementsystem.fxControllers;

import com.example.coursemanagementsystem.HelloApplication;
import com.example.coursemanagementsystem.control.DbUtils;
import com.example.coursemanagementsystem.control.RW;
import com.example.coursemanagementsystem.ds.Course;
import com.example.coursemanagementsystem.ds.CourseManagementSystem;
import com.example.coursemanagementsystem.ds.User;
import com.example.coursemanagementsystem.hibernateControllers.UserHibernateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.coursemanagementsystem.control.Constants.OUT_FILE;

public class LoginWindow implements Initializable {
    @FXML
    public TextField usernameF;
    @FXML
    public PasswordField passwordF;


    private CourseManagementSystem courseManagementSystem;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSystem");
    UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);

    public void setCourseManagementSystem(CourseManagementSystem courseManagementSystem) {
        this.courseManagementSystem = courseManagementSystem;
    }

    public void LoginF(ActionEvent actionEvent) throws IOException, SQLException {

        /*connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String sql = "SELECT count(*) FROM user AS u WHERE u.login = ? AND u.password = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, usernameF.getText());
        preparedStatement.setString(2, passwordF.getText());
        ResultSet rs = preparedStatement.executeQuery();
        String userName = null;*/
        User user = userHibernateController.getUserByLoginData(usernameF.getText(), passwordF.getText());

            if(user!=null)
            {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-courses-window.fxml"));
                Parent root=fxmlLoader.load();

                MainCoursesWindow mainCoursesWindow = fxmlLoader.getController();
                mainCoursesWindow.setLoginData( usernameF.getText(), passwordF.getText(), user.getId());



                Scene scene = new Scene(root);

                Stage stage = (Stage) usernameF.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                alertMessageError("Wrong input data, no such user found");

            }


        //DbUtils.disconnectFromDb(connection, statement);

    }
    private static void alertMessageError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Message text:");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }


    public void SignUpF(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-up-form.fxml"));
        Parent root=fxmlLoader.load();
        SignUpForm signUpForm = fxmlLoader.getController();
        signUpForm.setCourseManagementSystem(courseManagementSystem);

        Scene scene = new Scene(root);

        Stage stage = (Stage) usernameF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
