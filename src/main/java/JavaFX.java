import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;

public class JavaFX extends Application {
	TextField temperature,weather;

	public static void main(String[] args) {

		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
//		primaryStage.setTitle("I'm a professional Weather App!");
//		//int temp = WeatherAPI.getTodaysTemperature(77,70);
//		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
//		if (forecast == null){
//			throw new RuntimeException("Forecast did not load");
//		}
//		temperature = new TextField();
//		weather = new TextField();
//		temperature.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature));
//		weather.setText(forecast.get(0).shortForecast);
//
//
//
//
//		Scene scene = new Scene(new VBox(temperature,weather), 700,700);
//		primaryStage.setScene(scene);
//		primaryStage.show();

		primaryStage.setTitle("MyWeather");

		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		//############ Load Images
		Image sun = new Image(getClass().getResource("/images/Sun.png").toExternalForm());
		Image moon = new Image(getClass().getResource("/images/Moon.png").toExternalForm());
		Image rain = new Image(getClass().getResource("/images/Rain.png").toExternalForm());
		Image wind = new Image(getClass().getResource("/images/Wind.png").toExternalForm());


		//############################################### Top Pane

		// Logo Text
		Label logo = new Label("MyWeather");
		logo.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		logo.setMinSize(150, 30);
		Label logoSpace = new Label();
		logoSpace.setPrefWidth(400);

		// Scene Buttons
		Button today = new Button("Today's Weather");
		today.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		Button threeDay = new Button("3-Day Forecast");
		threeDay.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		today.setMinSize(150, 30);
		threeDay.setMinSize(150, 30);


		HBox Top = new HBox(20, logo, logoSpace, today, threeDay);
		Top.setPadding(new Insets(20, 20, 20, 20));
		Top.setStyle("-fx-background-color: linear-gradient(to right, white, orange)");

		//############################################### Center Pane

		//############################### Left Panel

		//################# rainLeft

		// rainTextDayLeft
		Label rainTextDayLeft = new Label("T");
		rainTextDayLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// rainImageDayLeft
		ImageView rainImageDayLeft = new ImageView(rain);
		rainImageDayLeft.setFitHeight(25);
		rainImageDayLeft.setFitWidth(25);
		rainImageDayLeft.setPreserveRatio(true);
		// rainSpaceLeft
		Label rainSpaceLeft = new Label("%");
		rainSpaceLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// rainImageNightLeft
		ImageView rainImageNightLeft = new ImageView(rain);
		rainImageNightLeft.setFitHeight(25);
		rainImageNightLeft.setFitWidth(25);
		rainImageNightLeft.setPreserveRatio(true);
		// rainTextNightLeft
		Label rainTextNightLeft = new Label("T");
		rainTextNightLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

		VBox rainLeft = new VBox(5, rainTextDayLeft, rainImageDayLeft, rainSpaceLeft, rainImageNightLeft, rainTextNightLeft);
		rainLeft.setAlignment(Pos.CENTER);

		//################# imagesLeft

		// sunViewLeft
		ImageView sunViewLeft = new ImageView(sun);
		sunViewLeft.setFitHeight(50);
		sunViewLeft.setFitWidth(50);
		sunViewLeft.setPreserveRatio(true);
		// sunSpaceLeft
		Label sunSpaceLeft = new Label();
		// unitsButtonLeft
		Button unitsButtonLeft = new Button("Switch Units");
		// moonSpaceLeft
		Label moonSpaceLeft = new Label();
		// moonViewLeft
		ImageView moonViewLeft = new ImageView(moon);
		moonViewLeft.setFitHeight(50);
		moonViewLeft.setFitWidth(50);
		moonViewLeft.setPreserveRatio(true);

		VBox imagesLeft = new VBox(10, sunViewLeft, sunSpaceLeft, unitsButtonLeft, moonSpaceLeft, moonViewLeft);
		imagesLeft.setAlignment(Pos.CENTER);

		//################# windLeft

		// windTextDayLeft
		Label windTextDayLeft = new Label("TT");
		windTextDayLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// windImageDayLeft
		ImageView windImageDayLeft = new ImageView(wind);
		windImageDayLeft.setFitHeight(25);
		windImageDayLeft.setFitWidth(25);
		windImageDayLeft.setPreserveRatio(true);
		// windSpaceLeft
		Label windSpaceLeft = new Label("mph");
		windSpaceLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// windImageNightLeft
		ImageView windImageNightLeft = new ImageView(wind);
		windImageNightLeft.setFitHeight(25);
		windImageNightLeft.setFitWidth(25);
		windImageNightLeft.setPreserveRatio(true);
		// windTextNightLeft
		Label windTextNightLeft = new Label("TT");
		windTextNightLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold");

		VBox windLeft = new VBox(5, windTextDayLeft, windImageDayLeft, windSpaceLeft, windImageNightLeft, windTextNightLeft);
		windLeft.setAlignment(Pos.CENTER);

		//################# Temps Left
		Label tempDayLeft = new Label("T°");
		Label tempNightLeft = new Label("T°");
		tempDayLeft.setAlignment(Pos.CENTER);
		tempNightLeft.setAlignment(Pos.CENTER);
		tempDayLeft.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
		tempNightLeft.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");

		HBox detailsLeft = new HBox(10, rainLeft, imagesLeft, windLeft);
		detailsLeft.setPadding(new Insets(0, 10, 0, 10));

		VBox panelLeft = new VBox(tempDayLeft, detailsLeft, tempNightLeft);
		panelLeft.setAlignment(Pos.CENTER);
		panelLeft.setStyle("-fx-background-color: linear-gradient(to bottom, white, gray); -fx-background-radius: 25px;");

		//############################### Middle Panel

		//################# rainMiddle

		// rainTextDayMiddle
		Label rainTextDayMiddle = new Label("T%");
		// rainImageDayMiddle
		ImageView rainImageDayMiddle = new ImageView(rain);
		rainImageDayMiddle.setFitHeight(25);
		rainImageDayMiddle.setFitWidth(25);
		rainImageDayMiddle.setPreserveRatio(true);
		// rainImageNightMiddle
		ImageView rainImageNightMiddle = new ImageView(rain);
		rainImageNightMiddle.setFitHeight(25);
		rainImageNightMiddle.setFitWidth(25);
		rainImageNightMiddle.setPreserveRatio(true);
		// rainTextNightMiddle
		Label rainTextNightMiddle = new Label("T%");

		VBox rainMiddle = new VBox(rainTextDayMiddle, rainImageDayMiddle, rainImageNightMiddle, rainTextNightMiddle);

		//################# imagesMiddle

		// sunViewMiddle
		ImageView sunViewMiddle = new ImageView(sun);
		sunViewMiddle.setFitHeight(50);
		sunViewMiddle.setFitWidth(50);
		sunViewMiddle.setPreserveRatio(true);
		// unitsButtonMiddle
		Button unitsButtonMiddle = new Button("Switch Units");
		// moonViewMiddle
		ImageView moonViewMiddle = new ImageView(moon);
		moonViewMiddle.setFitHeight(50);
		moonViewMiddle.setFitWidth(50);
		moonViewMiddle.setPreserveRatio(true);

		VBox imagesMiddle = new VBox(sunViewMiddle, unitsButtonMiddle, moonViewMiddle);

		//################# windMiddle

		// windTextDayMiddle
		Label windTextDayMiddle = new Label("T mph");
		// windImageDayMiddle
		ImageView windImageDayMiddle = new ImageView(wind);
		windImageDayMiddle.setFitHeight(25);
		windImageDayMiddle.setFitWidth(25);
		windImageDayMiddle.setPreserveRatio(true);
		// windImageNightMiddle
		ImageView windImageNightMiddle = new ImageView(wind);
		windImageNightMiddle.setFitHeight(25);
		windImageNightMiddle.setFitWidth(25);
		windImageNightMiddle.setPreserveRatio(true);
		// windTextNightMiddle
		Label windTextNightMiddle = new Label("T mph");

		VBox windMiddle = new VBox(windTextDayMiddle, windImageDayMiddle, windImageNightMiddle, windTextNightMiddle);

		//################# Temps Middle
		Label tempDayMiddle = new Label("T°");
		Label tempNightMiddle = new Label("T°");

		HBox detailsMiddle = new HBox(rainMiddle, imagesMiddle, windMiddle);

		VBox panelMiddle = new VBox(tempDayMiddle, detailsMiddle, tempNightMiddle);

		//############################### Right Panel

		//################# rainRight

		// rainTextDayRight
		Label rainTextDayRight = new Label("T%");
		// rainImageDayRight
		ImageView rainImageDayRight = new ImageView(rain);
		rainImageDayRight.setFitHeight(25);
		rainImageDayRight.setFitWidth(25);
		rainImageDayRight.setPreserveRatio(true);
		// rainImageNightRight
		ImageView rainImageNightRight = new ImageView(rain);
		rainImageNightRight.setFitHeight(25);
		rainImageNightRight.setFitWidth(25);
		rainImageNightRight.setPreserveRatio(true);
		// rainTextNightRight
		Label rainTextNightRight = new Label("T%");

		VBox rainRight = new VBox(rainTextDayRight, rainImageDayRight, rainImageNightRight, rainTextNightRight);

		//################# imagesRight

		// sunViewRight
		ImageView sunViewRight = new ImageView(sun);
		sunViewRight.setFitHeight(50);
		sunViewRight.setFitWidth(50);
		sunViewRight.setPreserveRatio(true);
		// unitsButtonRight
		Button unitsButtonRight = new Button("Switch Units");
		// moonViewRight
		ImageView moonViewRight = new ImageView(moon);
		moonViewRight.setFitHeight(50);
		moonViewRight.setFitWidth(50);
		moonViewRight.setPreserveRatio(true);

		VBox imagesRight = new VBox(sunViewRight, unitsButtonRight, moonViewRight);

		//################# windRight

		// windTextDayRight
		Label windTextDayRight = new Label("T mph");
		// windImageDayRight
		ImageView windImageDayRight = new ImageView(wind);
		windImageDayRight.setFitHeight(25);
		windImageDayRight.setFitWidth(25);
		windImageDayRight.setPreserveRatio(true);
		// windImageNightRight
		ImageView windImageNightRight = new ImageView(wind);
		windImageNightRight.setFitHeight(25);
		windImageNightRight.setFitWidth(25);
		windImageNightRight.setPreserveRatio(true);
		// windTextNightRight
		Label windTextNightRight = new Label("T mph");

		VBox windRight = new VBox(windTextDayRight, windImageDayRight, windImageNightRight, windTextNightRight);

		//################# Right Temps
		Label tempDayRight = new Label("T°");
		Label tempNightRight = new Label("T°");

		HBox detailsRight = new HBox(rainRight, imagesRight, windRight);

		VBox panelRight = new VBox(tempDayRight, detailsRight, tempNightRight);

		//############################################### Bottom Pane

		Label weekdayLeft = new Label("Testday");
		Label dateLeft = new Label("T/TT");
		weekdayLeft.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		dateLeft.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		VBox dateInfoLeft = new VBox(weekdayLeft, dateLeft);
		dateInfoLeft.setAlignment(Pos.CENTER);

		Label weekdayCenter = new Label("Testday");
		Label dateCenter = new Label("T/TT");
		weekdayCenter.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		dateCenter.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		VBox dateInfoCenter = new VBox(weekdayCenter, dateCenter);
		dateInfoCenter.setAlignment(Pos.CENTER);

		Label weekdayRight = new Label("Testday");
		Label dateRight = new Label("T/TT");
		weekdayRight.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		dateRight.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		VBox dateInfoRight = new VBox(weekdayRight, dateRight);
		dateInfoRight.setAlignment(Pos.CENTER);

		HBox Bottom = new HBox(135, dateInfoLeft, dateInfoCenter, dateInfoRight);
		Bottom.setAlignment(Pos.CENTER);
		Bottom.setPadding(new Insets(0, 0, 20, 0));
		Bottom.setStyle("-fx-background-color: #f7f7f7");

		// ##########################################################

		HBox Center = new HBox(80, panelLeft, panelMiddle, panelRight);
		Center.setPadding(new Insets(20, 0, 10, 0));
		Center.setAlignment(Pos.CENTER);
		Center.setPrefWidth(Bottom.getPrefWidth());
		Center.setStyle("-fx-background-color: white");

		BorderPane root = new BorderPane();
		root.setTop(Top);
		root.setCenter(Center);
		root.setBottom(Bottom);

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
