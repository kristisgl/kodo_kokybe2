
package com.example.coursemanagementsystem.fxControllers;

import com.example.coursemanagementsystem.HelloApplication;
import com.example.coursemanagementsystem.ds.Course;
import com.example.coursemanagementsystem.ds.User;
import com.example.coursemanagementsystem.hibernateControllers.CourseHibernateController;
import com.example.coursemanagementsystem.hibernateControllers.UserHibernateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;


public class CourseForm {
    public TextField courseTitle;
    public TextField courseDescription;
    public DatePicker courseStartDate;
    public DatePicker courseEndDate;
    private int userId;
    

    private int courseId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSystem");
    CourseHibernateController courseHibernateController = new CourseHibernateController(entityManagerFactory);

    public void setCourseFormData(int id, boolean edit, int courseId) {
        this.userId = id;
        this.courseId = courseId;

        if (edit) {
            Course course = courseHibernateController.getCourseById(courseId);
            courseTitle.setText(course.getTitle());
            courseDescription.setText(course.getDescription());
            courseStartDate.setValue(course.getStartDate());
            courseEndDate.setValue(course.getEndDate());
        }
    }
    public void saveCourse(ActionEvent actionEvent) throws IOException {
        Course course = courseHibernateController.getCourseById(courseId);
        course.setTitle(courseTitle.getText());
        course.setDescription(courseDescription.getText());
        course.setStartDate(courseStartDate.getValue());
        course.setEndDate(courseEndDate.getValue());
        courseHibernateController.editCourse(course);
        returnToMain();

    }
    public void returnToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-courses-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) courseTitle.getScene().getWindow();
        stage.setScene(scene);
        stage.close();
    }
}
