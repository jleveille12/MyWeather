import Components.Navbar;
import Scenes.TodaysWeather;
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

	private final Navbar navbar = new Navbar();
	Button todayButton = navbar.getTodayButton();
	Button threeDayButton = navbar.getThreeDayButton();

	public static void main(String[] args) {
		launch(args);
	}

	private BorderPane firstScene(Period Today) {
		BorderPane mainLayout = new BorderPane();
		TodaysWeather todaysWeatherScene = new TodaysWeather(Today);
		mainLayout.setTop(navbar.getNavbar(Today));
		mainLayout.setCenter(todaysWeatherScene.getLayout());

		return mainLayout;
	}

	private boolean isMetricLeft = false;
	private boolean isMetricMiddle = false;
	private boolean isMetricRight = false;

	private BorderPane secondScene(ArrayList<Period> forecast) {
		Period Today = forecast.getFirst();

		int i = 1;
		if (Today.isDaytime) {
			i++;
		}

		Period Day1 = forecast.get(i);
		Period Night1 = forecast.get(i+1);

		Period Day2 = forecast.get(i+2);
		Period Night2 = forecast.get(i+3);

		Period Day3 = forecast.get(i+4);
		Period Night3 = forecast.get(i+5);

		//############ Load Images
		Image sun = new Image(getClass().getResource("/images/Sun.png").toExternalForm());
		Image moon = new Image(getClass().getResource("/images/Moon.png").toExternalForm());
		Image rain = new Image(getClass().getResource("/images/Rain.png").toExternalForm());
		Image wind = new Image(getClass().getResource("/images/Wind.png").toExternalForm());

		//############################################### Top Pane
		HBox Top = navbar.getNavbar(Today);

		//############################################### Center Pane

		//############################### Left Panel

		//################# rainLeft

		// rainTextDayLeft
		Label rainTextDayLeft = new Label(String.valueOf(Day1.probabilityOfPrecipitation.value));
		rainTextDayLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// rainImageDayLeft
		ImageView rainImageDayLeft = new ImageView(rain);
		rainImageDayLeft.setFitHeight(25);
		rainImageDayLeft.setFitWidth(25);
		rainImageDayLeft.setPreserveRatio(true);
		// rainSpaceLeft
		Label rainSpaceLeft = new Label("%");
		rainSpaceLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		rainSpaceLeft.setMinHeight(40);
		// rainImageNightLeft
		ImageView rainImageNightLeft = new ImageView(rain);
		rainImageNightLeft.setFitHeight(25);
		rainImageNightLeft.setFitWidth(25);
		rainImageNightLeft.setPreserveRatio(true);
		// rainTextNightLeft
		Label rainTextNightLeft = new Label(String.valueOf(Night1.probabilityOfPrecipitation.value));
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
		Label windTextDayLeft = new Label(Day1.windSpeed.substring(0,2));
		windTextDayLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// windImageDayLeft
		ImageView windImageDayLeft = new ImageView(wind);
		windImageDayLeft.setFitHeight(25);
		windImageDayLeft.setFitWidth(25);
		windImageDayLeft.setPreserveRatio(true);
		// windSpaceLeft
		Label windSpaceLeft = new Label("mph");
		windSpaceLeft.setStyle("-fx-font-size: 12;");
		windSpaceLeft.setMinHeight(40);
		// windImageNightLeft
		ImageView windImageNightLeft = new ImageView(wind);
		windImageNightLeft.setFitHeight(25);
		windImageNightLeft.setFitWidth(25);
		windImageNightLeft.setPreserveRatio(true);
		// windTextNightLeft
		Label windTextNightLeft = new Label(Night1.windSpeed.substring(0,2));
		windTextNightLeft.setStyle("-fx-font-size: 12; -fx-font-weight: bold");

		VBox windLeft = new VBox(5, windTextDayLeft, windImageDayLeft, windSpaceLeft, windImageNightLeft, windTextNightLeft);
		windLeft.setAlignment(Pos.CENTER);

		//################# Temps Left
		Label tempDayLeft = new Label(String.valueOf(Day1.temperature) + "°F");
		Label tempNightLeft = new Label(String.valueOf(Night1.temperature) + "°F");
		tempDayLeft.setAlignment(Pos.CENTER);
		tempNightLeft.setAlignment(Pos.CENTER);
		tempDayLeft.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
		tempNightLeft.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");

		HBox detailsLeft = new HBox(10, rainLeft, imagesLeft, windLeft);
		detailsLeft.setPadding(new Insets(0, 10, 0, 10));

		VBox panelLeft = new VBox(tempDayLeft, detailsLeft, tempNightLeft);
		panelLeft.setAlignment(Pos.CENTER);
		panelLeft.setStyle("-fx-background-color: linear-gradient(to bottom, white, gray); -fx-background-radius: 25px;");

		// Actions Left

		unitsButtonLeft.setOnAction(e -> {
			if (isMetricLeft) {
				tempDayLeft.setText(String.valueOf(Day1.temperature) + "°F");
				tempNightLeft.setText(String.valueOf(Night1.temperature) + "°F");
				windTextDayLeft.setText(Day1.windSpeed.substring(0,2));
				windTextNightLeft.setText(Night1.windSpeed.substring(0,2));
				windSpaceLeft.setText("mph");
				isMetricLeft = false;
			} else {
				double dayCelsius = (Day1.temperature - 32) * 5.0 / 9.0;
				double dayKMH = (Integer.valueOf(Day1.windSpeed.substring(0,2)) * 1.60934);
				double nightCelsius = (Night1.temperature - 32) * 5.0 / 9.0;
				double nightKMH = (Integer.valueOf(Night1.windSpeed.substring(0,2)) * 1.60934);
				tempDayLeft.setText(String.valueOf((int)dayCelsius) + "°C");
				tempNightLeft.setText(String.valueOf((int)nightCelsius) + "°C");
				windTextDayLeft.setText(String.valueOf((int)dayKMH));
				windTextNightLeft.setText(String.valueOf((int)nightKMH));
				windSpaceLeft.setText("kmh");
				isMetricLeft = true;
			}
		});

		//############################### Middle Panel

		//################# rainMiddle

		// rainTextDayMiddle
		Label rainTextDayMiddle = new Label(String.valueOf(Day2.probabilityOfPrecipitation.value));
		rainTextDayMiddle.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// rainImageDayMiddle
		ImageView rainImageDayMiddle = new ImageView(rain);
		rainImageDayMiddle.setFitHeight(25);
		rainImageDayMiddle.setFitWidth(25);
		rainImageDayMiddle.setPreserveRatio(true);
		// rainSpaceMiddle
		Label rainSpaceMiddle = new Label("%");
		rainSpaceMiddle.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		rainSpaceMiddle.setMinHeight(40);
		// rainImageNightMiddle
		ImageView rainImageNightMiddle = new ImageView(rain);
		rainImageNightMiddle.setFitHeight(25);
		rainImageNightMiddle.setFitWidth(25);
		rainImageNightMiddle.setPreserveRatio(true);
		// rainTextNightMiddle
		Label rainTextNightMiddle = new Label(String.valueOf(Night2.probabilityOfPrecipitation.value));
		rainTextNightMiddle.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

		VBox rainMiddle = new VBox(5, rainTextDayMiddle, rainImageDayMiddle, rainSpaceMiddle, rainImageNightMiddle, rainTextNightMiddle);
		rainMiddle.setAlignment(Pos.CENTER);

		//################# imagesMiddle

		// sunViewMiddle
		ImageView sunViewMiddle = new ImageView(sun);
		sunViewMiddle.setFitHeight(50);
		sunViewMiddle.setFitWidth(50);
		sunViewMiddle.setPreserveRatio(true);
		// sunSpaceMiddle
		Label sunSpaceMiddle = new Label();
		// unitsButtonMiddle
		Button unitsButtonMiddle = new Button("Switch Units");
		// moonSpaceMiddle
		Label moonSpaceMiddle = new Label();
		// moonViewMiddle
		ImageView moonViewMiddle = new ImageView(moon);
		moonViewMiddle.setFitHeight(50);
		moonViewMiddle.setFitWidth(50);
		moonViewMiddle.setPreserveRatio(true);

		VBox imagesMiddle = new VBox(10, sunViewMiddle, sunSpaceMiddle, unitsButtonMiddle, moonSpaceMiddle, moonViewMiddle);
		imagesMiddle.setAlignment(Pos.CENTER);

		//################# windMiddle

		// windTextDayMiddle
		Label windTextDayMiddle = new Label(Day2.windSpeed.substring(0,2));
		windTextDayMiddle.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// windImageDayMiddle
		ImageView windImageDayMiddle = new ImageView(wind);
		windImageDayMiddle.setFitHeight(25);
		windImageDayMiddle.setFitWidth(25);
		windImageDayMiddle.setPreserveRatio(true);
		// windSpaceMiddle
		Label windSpaceMiddle = new Label("mph");
		windSpaceMiddle.setStyle("-fx-font-size: 12;");
		windSpaceMiddle.setMinHeight(40);
		// windImageNightMiddle
		ImageView windImageNightMiddle = new ImageView(wind);
		windImageNightMiddle.setFitHeight(25);
		windImageNightMiddle.setFitWidth(25);
		windImageNightMiddle.setPreserveRatio(true);
		// windTextNightMiddle
		Label windTextNightMiddle = new Label(Night2.windSpeed.substring(0,2));
		windTextNightMiddle.setStyle("-fx-font-size: 12; -fx-font-weight: bold");

		VBox windMiddle = new VBox(5, windTextDayMiddle, windImageDayMiddle, windSpaceMiddle, windImageNightMiddle, windTextNightMiddle);
		windMiddle.setAlignment(Pos.CENTER);

		//################# Temps Middle
		Label tempDayMiddle = new Label(String.valueOf(Day2.temperature) + "°F");
		Label tempNightMiddle = new Label(String.valueOf(Night2.temperature) + "°F");
		tempDayMiddle.setAlignment(Pos.CENTER);
		tempNightMiddle.setAlignment(Pos.CENTER);
		tempDayMiddle.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
		tempNightMiddle.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");

		HBox detailsMiddle = new HBox(10, rainMiddle, imagesMiddle, windMiddle);
		detailsMiddle.setPadding(new Insets(0, 10, 0, 10));

		VBox panelMiddle = new VBox(tempDayMiddle, detailsMiddle, tempNightMiddle);
		panelMiddle.setAlignment(Pos.CENTER);
		panelMiddle.setStyle("-fx-background-color: linear-gradient(to bottom, white, gray); -fx-background-radius: 25px;");

		// Actions Middle

		unitsButtonMiddle.setOnAction(e -> {
			if (isMetricMiddle) {
				tempDayMiddle.setText(String.valueOf(Day2.temperature) + "°F");
				tempNightMiddle.setText(String.valueOf(Night2.temperature) + "°F");
				windTextDayMiddle.setText(Day2.windSpeed.substring(0,2));
				windTextNightMiddle.setText(Night2.windSpeed.substring(0,2));
				windSpaceMiddle.setText("mph");
				isMetricMiddle = false;
			} else {
				double dayCelsius = (Day2.temperature - 32) * 5.0 / 9.0;
				double dayKMH = (Integer.valueOf(Day2.windSpeed.substring(0,2)) * 1.60934);
				double nightCelsius = (Night2.temperature - 32) * 5.0 / 9.0;
				double nightKMH = (Integer.valueOf(Night2.windSpeed.substring(0,2)) * 1.60934);
				tempDayMiddle.setText(String.valueOf((int)dayCelsius) + "°C");
				tempNightMiddle.setText(String.valueOf((int)nightCelsius) + "°C");
				windTextDayMiddle.setText(String.valueOf((int)dayKMH));
				windTextNightMiddle.setText(String.valueOf((int)nightKMH));
				windSpaceMiddle.setText("kmh");
				isMetricMiddle = true;
			}
		});

		//############################### Right Panel

		//################# rainRight

		// rainTextDayRight
		Label rainTextDayRight = new Label(String.valueOf(Day3.probabilityOfPrecipitation.value));
		rainTextDayRight.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// rainImageDayRight
		ImageView rainImageDayRight = new ImageView(rain);
		rainImageDayRight.setFitHeight(25);
		rainImageDayRight.setFitWidth(25);
		rainImageDayRight.setPreserveRatio(true);
		// rainSpaceRight
		Label rainSpaceRight = new Label("%");
		rainSpaceRight.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		rainSpaceRight.setMinHeight(40);
		// rainImageNightRight
		ImageView rainImageNightRight = new ImageView(rain);
		rainImageNightRight.setFitHeight(25);
		rainImageNightRight.setFitWidth(25);
		rainImageNightRight.setPreserveRatio(true);
		// rainTextNightRight
		Label rainTextNightRight = new Label(String.valueOf(Night3.probabilityOfPrecipitation.value));
		rainTextNightRight.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

		VBox rainRight = new VBox(5, rainTextDayRight, rainImageDayRight, rainSpaceRight, rainImageNightRight, rainTextNightRight);
		rainRight.setAlignment(Pos.CENTER);

		//################# imagesRight

		// sunViewRight
		ImageView sunViewRight = new ImageView(sun);
		sunViewRight.setFitHeight(50);
		sunViewRight.setFitWidth(50);
		sunViewRight.setPreserveRatio(true);
		// sunSpaceRight
		Label sunSpaceRight = new Label();
		// unitsButtonRight
		Button unitsButtonRight = new Button("Switch Units");
		// moonSpaceRight
		Label moonSpaceRight = new Label();
		// moonViewRight
		ImageView moonViewRight = new ImageView(moon);
		moonViewRight.setFitHeight(50);
		moonViewRight.setFitWidth(50);
		moonViewRight.setPreserveRatio(true);

		VBox imagesRight = new VBox(10, sunViewRight, sunSpaceRight, unitsButtonRight, moonSpaceRight, moonViewRight);
		imagesRight.setAlignment(Pos.CENTER);

		//################# windRight

		// windTextDayRight
		Label windTextDayRight = new Label(Day3.windSpeed.substring(0,2));
		windTextDayRight.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
		// windImageDayRight
		ImageView windImageDayRight = new ImageView(wind);
		windImageDayRight.setFitHeight(25);
		windImageDayRight.setFitWidth(25);
		windImageDayRight.setPreserveRatio(true);
		// windSpaceRight
		Label windSpaceRight = new Label("mph");
		windSpaceRight.setStyle("-fx-font-size: 12;");
		windSpaceRight.setMinHeight(40);
		// windImageNightRight
		ImageView windImageNightRight = new ImageView(wind);
		windImageNightRight.setFitHeight(25);
		windImageNightRight.setFitWidth(25);
		windImageNightRight.setPreserveRatio(true);
		// windTextNightRight
		Label windTextNightRight = new Label(Night3.windSpeed.substring(0,2));
		windTextNightRight.setStyle("-fx-font-size: 12; -fx-font-weight: bold");

		VBox windRight = new VBox(5, windTextDayRight, windImageDayRight, windSpaceRight, windImageNightRight, windTextNightRight);
		windRight.setAlignment(Pos.CENTER);

		//################# Temps Right
		Label tempDayRight = new Label(String.valueOf(Day3.temperature) + "°F");
		Label tempNightRight = new Label(String.valueOf(Night3.temperature) + "°F");
		tempDayRight.setAlignment(Pos.CENTER);
		tempNightRight.setAlignment(Pos.CENTER);
		tempDayRight.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");
		tempNightRight.setStyle("-fx-font-size: 48; -fx-font-weight: bold;");

		HBox detailsRight = new HBox(10, rainRight, imagesRight, windRight);
		detailsRight.setPadding(new Insets(0, 10, 0, 10));

		VBox panelRight = new VBox(tempDayRight, detailsRight, tempNightRight);
		panelRight.setAlignment(Pos.CENTER);
		panelRight.setStyle("-fx-background-color: linear-gradient(to bottom, white, gray); -fx-background-radius: 25px;");

		// Actions Right

		unitsButtonRight.setOnAction(e -> {
			if (isMetricRight) {
				tempDayRight.setText(String.valueOf(Day3.temperature) + "°F");
				tempNightRight.setText(String.valueOf(Night3.temperature) + "°F");
				windTextDayRight.setText(Day3.windSpeed.substring(0,2));
				windTextNightRight.setText(Night3.windSpeed.substring(0,2));
				windSpaceRight.setText("mph");
				isMetricRight = false;
			} else {
				double dayCelsius = (Day3.temperature - 32) * 5.0 / 9.0;
				double dayKMH = (Integer.valueOf(Day3.windSpeed.substring(0,2)) * 1.60934);
				double nightCelsius = (Night3.temperature - 32) * 5.0 / 9.0;
				double nightKMH = (Integer.valueOf(Night3.windSpeed.substring(0,2)) * 1.60934);
				tempDayRight.setText(String.valueOf((int)dayCelsius) + "°C");
				tempNightRight.setText(String.valueOf((int)nightCelsius) + "°C");
				windTextDayRight.setText(String.valueOf((int)dayKMH));
				windTextNightRight.setText(String.valueOf((int)nightKMH));
				windSpaceRight.setText("kmh");
				isMetricRight = true;
			}
		});

		//############################################### Bottom Pane

		Label weekdayLeft = new Label(Day1.name);
		Label dateLeft = new Label(Day1.startTime.toString().substring(4,10));
		weekdayLeft.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		dateLeft.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		VBox dateInfoLeft = new VBox(weekdayLeft, dateLeft);
		dateInfoLeft.setAlignment(Pos.CENTER);

		Label spaceLeft = new Label();
		spaceLeft.setPrefWidth(135);

		Label weekdayCenter = new Label(Day2.name);
		Label dateCenter = new Label(Day2.startTime.toString().substring(4,10));
		weekdayCenter.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		dateCenter.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		VBox dateInfoCenter = new VBox(weekdayCenter, dateCenter);
		dateInfoCenter.setAlignment(Pos.CENTER);

		Label spaceRight = new Label();
		spaceRight.setPrefWidth(135);

		Label weekdayRight = new Label(Day3.name);
		Label dateRight = new Label(Day3.startTime.toString().substring(4,10));
		weekdayRight.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		dateRight.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		VBox dateInfoRight = new VBox(weekdayRight, dateRight);
		dateInfoRight.setAlignment(Pos.CENTER);

		HBox Bottom = new HBox(dateInfoLeft, spaceLeft, dateInfoCenter, spaceRight, dateInfoRight);
		Bottom.setAlignment(Pos.CENTER);
		Bottom.setPadding(new Insets(0, 0, 20, 0));
		Bottom.setStyle("-fx-background-color: #f5f5f5");

		// ##########################################################

		HBox Center = new HBox(80, panelLeft, panelMiddle, panelRight);
		Center.setPadding(new Insets(20, 0, 10, 0));
		Center.setAlignment(Pos.CENTER);
		Center.setPrefWidth(Bottom.getPrefWidth());
		Center.setStyle("-fx-background-color: #f5f5f5");

		BorderPane root = new BorderPane();
		root.setTop(Top);
		root.setCenter(Center);
		root.setBottom(Bottom);

		return root;
	}



	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("MyWeather");

		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null || forecast.size() < 8){
			throw new RuntimeException("Forecast did not load properly");
		}

		BorderPane mainLayoutInitialScene = this.firstScene(forecast.get(0));
		Scene initialScene = new Scene(mainLayoutInitialScene, 800, 600);

		primaryStage.setScene(initialScene);
		primaryStage.setTitle("Weather App");
		primaryStage.show();

		// Switch scene logic
		todayButton.setOnAction(e -> {
			BorderPane mainLayoutFirstScene = this.firstScene(forecast.get(0));
			Scene scene1 = new Scene(mainLayoutFirstScene, 800, 600);
			primaryStage.setScene(scene1);
		});

		threeDayButton.setOnAction(e -> {
			// Set up second scene (3-day forecast)
			BorderPane mainLayoutSecondScene = this.secondScene(forecast);
			Scene scene2 = new Scene(mainLayoutSecondScene, 800, 600);
			primaryStage.setScene(scene2);
		});
	}

}
