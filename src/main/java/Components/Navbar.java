package Components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import weather.Period;

public class Navbar {
    private Label logo;
    private Label logoSpace;
    private Button today;
    private Button threeDay;

    public Navbar() {
        // Logo Text
        logo = new Label("MyWeather");
        logo.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        logo.setMinSize(150, 30);
        logoSpace = new Label();
        logoSpace.setPrefWidth(400);

        // Scene Buttons
        today = new Button("Today's Weather");
        today.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        threeDay = new Button("3-Day Forecast");
        threeDay.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        today.setMinSize(150, 30);
        threeDay.setMinSize(150, 30);
    }

    public HBox getNavbar(Period Today) {
        HBox Top = new HBox(20, logo, logoSpace, today, threeDay);
        Top.setPadding(new Insets(20, 20, 20, 20));
        if (Today.isDaytime) {
            Top.setStyle("-fx-background-color: linear-gradient(to right, white, orange)");
        } else {
            Top.setStyle("-fx-background-color: linear-gradient(to right, white, #2e169c)");
        }
        return Top;
    }

    public Button getTodayButton() {
        return today;
    }

    public Button getThreeDayButton() {
        return threeDay;
    }
}
