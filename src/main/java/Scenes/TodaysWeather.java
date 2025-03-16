package Scenes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.text.Text;

public class TodaysWeather {
    private BorderPane borderPane;
    // Declare the navbar objects
//    private HBox navBar;
//    private Button threeDayWeatherBtn;
//    private Button todaysWeatherBtn;

    // Declare vbox object for vertical alignment of main content
    private VBox mainContent;

    // Declare hbox object for horizontal alignment of first row of main content
    // Title, Today's Weather image, switch units toggle button
    private GridPane mainContentLayout;
    private HBox titleLine;
    private Label todaysWeatherContentTitle;
    private Image iconImage;
    private ImageView weatherIconImageView;
    private ToggleButton switchUnitsBtn;

    // Declare first row of content objects (Today's Temperature)
    private HBox temperatureLine;
    private Label temperatureText;
    private Label fahrenheitText;
    private Label celsiusText;

    // Declare the second row of content objects (Today's wind speed)
    private HBox windSpeedLine;
    private Label windSpeedText;
    private Label mphWindSpeedText;
    private Label kphWindSpeedText;

    // Declare fourth row of content object (Today's Weather precipitation probability)
    private Label precipitationProbabilityTitle;
    private Label precipitationProbabilityText;

    // Declare third row of content objects (Today's Weather description)
    private Text weatherDescriptionTitle;
    private Text weatherDescriptionText;



    // Construct today's weather scene layout
    public TodaysWeather() {
//        todaysWeatherBtn = new Button("Today's weather");
//        threeDayWeatherBtn = new Button("Three Day weather");

        todaysWeatherContentTitle = new Label();
        switchUnitsBtn = new ToggleButton();

        temperatureText = new Label();
        fahrenheitText = new Label();
        celsiusText = new Label();

        windSpeedText = new Label();
        mphWindSpeedText = new Label();
        kphWindSpeedText = new Label();

        weatherDescriptionTitle = new Text();
        weatherDescriptionText = new Text();

        precipitationProbabilityTitle = new Label();
        precipitationProbabilityText = new Label();
    }

    // Retrieves the weather data from the WeatherAPI.
    // Calls the updateWeather function to update
    // the weather data objects.
    public void loadWeatherData() {
        ArrayList<Period> forecast = WeatherAPI.getForecast("LOT", 77, 70);
        if (forecast == null || forecast.isEmpty()) {
            throw new RuntimeException("Forecast did not load");
        }

        this.updateWeather(forecast.get(0).temperature, forecast.get(0).shortForecast, forecast.get(0).detailedForecast, forecast.get(0).windSpeed, String.valueOf(forecast.get(0).probabilityOfPrecipitation.value));
    }

    // Converts the mph wind speed range to kph wind speed range
    // Takes in the mph wind speed range (e.g "20 to 30 mph")
    // Returns an ArrayList of the kph range as integers (e.g [20, 30]);
    public ArrayList<Integer> convertMphToKphRange(String mphWindSpeedRange) {
        // match one or more digits (0-9)
        Pattern pattern = Pattern.compile("\\d+");
        // scans mphWindSpeedRange string for occurrences of pattern
        Matcher matcher = pattern.matcher(mphWindSpeedRange);

        // Store the converted km/h in an array list
        ArrayList<Integer> speedsInKph = new ArrayList<>();

        // Moves to the next match in the string (if any exists)
        while (matcher.find()) {
            // Extracts numbers and parses them as ints
            int mph = Integer.parseInt(matcher.group());
            // Converts to km/h
            int kph = (int) Math.round(mph * 1.60934);
            speedsInKph.add(kph);
        }

        return speedsInKph;
    }

    // Updates the weather data objects which represent the main content on the today's weather scene.
    public void updateWeather(Integer tempFahrenheit, String sortForecast, String detailedForecast, String mphWindSpeedRange, String precipitationProb) {

        todaysWeatherContentTitle.setText("Today's Weather");
        todaysWeatherContentTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        // Retrieves weather icon from IconLoader class
        weatherIconImageView = IconLoader.getWeatherIcon(sortForecast);

        // Create the toggle units button
        switchUnitsBtn = new ToggleButton("Toggle Units");
        switchUnitsBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Set the temperature text
        temperatureText.setText("Temperature: ");
        temperatureText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Initially disable showing the Celsius text
        Integer tempCelsius = (tempFahrenheit - 32) * 5 / 9;
        String tempCelsiusText = String.valueOf(tempCelsius);
        celsiusText.setText(tempCelsiusText + "°C");
        celsiusText.setDisable(true);

        // Initially show the Fahrenheit units
        String tempFahrenheitText = String.valueOf(tempFahrenheit);
        fahrenheitText.setText(tempFahrenheitText + "°F ");

        // Set the wind speed text
        windSpeedText.setText("Wind speed: ");
        windSpeedText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Since speed is passed in as range (e.g "20 to 30 mph")
        // we need to first parse the string in order to convert to kph
        ArrayList<Integer> convertedSpeedRange = convertMphToKphRange(mphWindSpeedRange);

        String kphWindSpeed = "";
        if (convertedSpeedRange.size() == 2) {
            kphWindSpeed = String.valueOf(convertedSpeedRange.get(0)) + " to " + String.valueOf(convertedSpeedRange.get(1));
        } else {
            kphWindSpeed = String.valueOf(convertedSpeedRange.get(0));
        }

        // Initially show the mph units
        mphWindSpeedText.setText(mphWindSpeedRange + " ");

        // Initially disable showing the km/h speed
        kphWindSpeedText.setText(kphWindSpeed + "km/h");
        kphWindSpeedText.setDisable(true);

        // Toggle the units when switch units button is clicked
        switchUnitsBtn.setOnAction(e -> {
            if (switchUnitsBtn.isSelected()) {
                // Disable fahrenheit
                fahrenheitText.setDisable(true);
                // Enable celsius
                celsiusText.setDisable(false);

                // Disable mph
                mphWindSpeedText.setDisable(true);
                // Enable kmp
                kphWindSpeedText.setDisable(false);
            } else {
                // Disable celsius
                celsiusText.setDisable(true);
                // Enable fahrenheit
                fahrenheitText.setDisable(false);

                // Disable kph
                kphWindSpeedText.setDisable(true);
                // Enable mph
                mphWindSpeedText.setDisable(false);
            }
        });

        // Set the precipitation for today's weather
        precipitationProbabilityTitle.setText("Precipitation: ");
        precipitationProbabilityTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        precipitationProbabilityText.setText(precipitationProb + "%");

        // Set the description for today's weather
        weatherDescriptionTitle.setText("Description: ");
        weatherDescriptionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        weatherDescriptionText.setText(detailedForecast);

    }

    public GridPane getLayout() {
//        borderPane = new BorderPane();
        loadWeatherData();

//        navBar = new HBox(20, todaysWeatherBtn, threeDayWeatherBtn);
//        navBar.setStyle("-fx-padding: 10px; -fx-alignment: center-right;");
//        borderPane.setTop(navBar);

        mainContentLayout = new GridPane();
        // Add vertical spacing between rows
        mainContentLayout.setVgap(10);

        // Title, Icon, switch units button row
        mainContentLayout.add(todaysWeatherContentTitle, 0, 0, 1, 1);
        // Horizontally align to the bottom left
        GridPane.setHalignment(todaysWeatherContentTitle, HPos.LEFT);
        // Vertically align with the bottom of the weather icon
        GridPane.setValignment(todaysWeatherContentTitle, VPos.BOTTOM);
        mainContentLayout.add(weatherIconImageView, 1, 0, 1, 1);
        mainContentLayout.add(switchUnitsBtn, 2, 0, 1, 1);
        // Vertically align with the bottom of the weather icon
        GridPane.setValignment(switchUnitsBtn, VPos.BOTTOM);

        // Set margin for each element to add space around them
        GridPane.setMargin(todaysWeatherContentTitle, new Insets(0, 10, 0, 0));  // Right margin 10px
        GridPane.setMargin(weatherIconImageView, new Insets(0, 50, 0, 0));      // Right margin 40px
        GridPane.setMargin(switchUnitsBtn, new Insets(0, 0, 0, 100));          // Left margin 20px

        // Create seperator between row
        Separator sep1 = new Separator();
        sep1.setStyle("-fx-background-color: #000000;");
        mainContentLayout.add(sep1, 0, 1, 3, 1);

        // Temperature text, fahrenheit text, celsius text
        mainContentLayout.add(temperatureText, 0, 2, 1, 1);
        mainContentLayout.add(fahrenheitText, 1, 2, 1, 1);
        mainContentLayout.add(celsiusText, 2, 2, 1, 1);

        Separator sep2 = new Separator();
        sep2.setStyle("-fx-background-color: #000000;");
        mainContentLayout.add(sep2, 0, 3, 3, 1);

        mainContentLayout.add(windSpeedText, 0, 4, 1, 1);
        mainContentLayout.add(mphWindSpeedText, 1, 4, 1, 1);
        mainContentLayout.add(kphWindSpeedText, 2, 4, 1, 1);

        Separator sep3 = new Separator();
        sep3.setStyle("-fx-background-color: #000000;");
        mainContentLayout.add(sep3, 0, 5, 3, 1);

        mainContentLayout.add(precipitationProbabilityTitle, 0, 6, 1, 1);
        mainContentLayout.add(precipitationProbabilityText, 1, 6, 1, 1);

        Separator sep4 = new Separator();
        sep4.setStyle("-fx-background-color: #000000;");
        mainContentLayout.add(sep4, 0, 7, 3, 1);

        mainContentLayout.add(weatherDescriptionTitle, 0, 8, 1, 1);

        weatherDescriptionText.setWrappingWidth(300);
        mainContentLayout.add(weatherDescriptionText, 1, 8, 3, 1);

        mainContentLayout.setStyle("-fx-padding: 20px; -fx-alignment: top-center;");

        //borderPane.setCenter(mainContentLayout);

        return mainContentLayout;
    }
}
