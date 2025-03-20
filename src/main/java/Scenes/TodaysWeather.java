package Scenes;

import ExtendedMyWeatherAPI.MyWeatherAPI;
import ExtendedMyWeatherAPI.PeriodHourly;
import Utils.IconLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import weather.Period;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.text.Text;

import java.text.SimpleDateFormat;

public class TodaysWeather {
    // Stores all of today's weather data retrieved from API
    private Period todaysWeather;

    private final Label todaysWeatherContentTitle;
    private ImageView weatherIconImageView;
    private ToggleButton switchUnitsBtn;

    // Declare first row of content objects (Today's Temperature)
    private final Label temperatureText;
    private final Label fahrenheitText;
    private final Label celsiusText;

    // Declare the second row of content objects (Today's wind speed)
    private final Label windSpeedText;
    private final Label mphWindSpeedText;
    private final Label kphWindSpeedText;

    // Declare fourth row of content object (Today's Weather precipitation probability)
    private final Label precipitationProbabilityTitle;
    private final Label precipitationProbabilityText;

    // Declare third row of content objects (Today's Weather description)
    private final Text weatherDescriptionTitle;
    private final Text weatherDescriptionText;

    private ArrayList<PeriodHourly> hourlyWeather;

    private ArrayList<ImageView> hourlyIconList;
    private ArrayList<Text> hourlyTempList;
    private ArrayList<Text> hourlyTimeList;
    // Hourly forecast period
    private final Integer TIME_INTERVAL = 12;

    // Construct today's weather scene layout
    public TodaysWeather(Period todaysForecast) {
        ArrayList<PeriodHourly> hourlyForecast = MyWeatherAPI.getHourlyForecast("LOT",77,70);
        if (hourlyForecast == null || hourlyForecast.size() < 10){
            throw new RuntimeException("Hourly forecast did not load properly");
        }

        hourlyWeather = hourlyForecast;

        todaysWeather = todaysForecast;

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

        hourlyIconList = new ArrayList<>();
        hourlyTempList = new ArrayList<>();
        hourlyTimeList = new ArrayList<>();
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
    public void setWeatherData() {
        todaysWeatherContentTitle.setText("Chicago, IL");
        todaysWeatherContentTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Retrieves weather icon from IconLoader class
        weatherIconImageView = IconLoader.getWeatherIcon(todaysWeather.shortForecast);

        // Create the toggle units button
        switchUnitsBtn = new ToggleButton("Toggle Units");
        switchUnitsBtn.setStyle("-fx-background-color: gray; -fx-text-fill: white;");

        // Set the temperature text
        temperatureText.setText("Temperature: ");
        temperatureText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Initially disable showing the Celsius text
        Integer tempCelsius = (todaysWeather.temperature - 32) * 5 / 9;
        String tempCelsiusText = String.valueOf(tempCelsius);
        celsiusText.setText(tempCelsiusText + "°C");
        celsiusText.setDisable(true);

        // Initially show Fahrenheit
        String tempFahrenheitText = String.valueOf(todaysWeather.temperature);
        fahrenheitText.setText(tempFahrenheitText + "°F ");

        // Set the wind speed text
        windSpeedText.setText("Wind speed: ");
        windSpeedText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Initially show mph
        mphWindSpeedText.setText(todaysWeather.windSpeed + " ");
        ArrayList<Integer> convertedSpeedRange = convertMphToKphRange(todaysWeather.windSpeed);
        String kphWindSpeed = "";
        if (convertedSpeedRange.size() == 2) {
            kphWindSpeed = String.valueOf(convertedSpeedRange.get(0)) + " to " + String.valueOf(convertedSpeedRange.get(1));
        } else {
            kphWindSpeed = String.valueOf(convertedSpeedRange.get(0));
        }
        // Initially disable showing the kph speed
        kphWindSpeedText.setText(kphWindSpeed + " km/h");
        kphWindSpeedText.setDisable(true);

        // Set the precipitation for today's weather
        precipitationProbabilityTitle.setText("Precipitation: ");
        precipitationProbabilityTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        precipitationProbabilityText.setText(String.valueOf(todaysWeather.probabilityOfPrecipitation.value) + "%");

        // Set the description for today's weather
        weatherDescriptionTitle.setText("Description: ");
        weatherDescriptionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        weatherDescriptionText.setText(todaysWeather.detailedForecast);

        // Set up the hourly forecast for 12 hour period
        for (int i = 0; i < TIME_INTERVAL; i++) {
            ImageView hourlyIcon = IconLoader.getWeatherIcon(hourlyWeather.get(i).shortForecast);
            hourlyIcon.setFitHeight(20);
            hourlyIcon.setFitWidth(20);
            hourlyIcon.setPreserveRatio(true);

            SimpleDateFormat formatter = new SimpleDateFormat("HH a");
            String timeOnly = formatter.format(hourlyWeather.get(i).startTime);
            Text hourlyTime = new Text();

            if (timeOnly.equals("00 AM")) {
                hourlyTime.setText("12 AM");
            } else if (timeOnly.startsWith("0"))  {
                hourlyTime.setText(timeOnly.substring(1));
            } else if (Integer.parseInt(timeOnly.substring(0, 2)) > 12){
                String pmTime = String.valueOf(Integer.parseInt(timeOnly.substring(0, 2)) - 12);
                hourlyTime.setText(pmTime + " PM");
            } else {
                hourlyTime.setText(timeOnly);
            }

            Text hourlyTemp = new Text(String.valueOf(hourlyWeather.get(i).temperature) + "°F");

            hourlyIconList.add(hourlyIcon);
            hourlyTimeList.add(hourlyTime);
            hourlyTempList.add(hourlyTemp);
        }

        // Toggle the units when the switch units button is clicked
        switchUnitsBtn.setOnAction(e -> {
            if (switchUnitsBtn.isSelected()) {
                // Change hourly temp units
                for (int i = 0; i < TIME_INTERVAL; i++) {
                    Integer tempHourlyCelsius = (hourlyWeather.get(i).temperature - 32) * 5 / 9;
                    String tempHourlyCelsiusText = String.valueOf(tempHourlyCelsius);
                    hourlyTempList.get(i).setText(tempHourlyCelsiusText + "°C");
                }
                // Disable fahrenheit
                fahrenheitText.setDisable(true);
                // Enable celsius
                celsiusText.setDisable(false);

                // Disable mph
                mphWindSpeedText.setDisable(true);
                // Enable kmp
                kphWindSpeedText.setDisable(false);
            } else {
                for (int i = 0; i < TIME_INTERVAL; i++) {
                    hourlyTempList.get(i).setText(String.valueOf(hourlyWeather.get(i).temperature) + "°F");
                }
                celsiusText.setDisable(true);
                fahrenheitText.setDisable(false);

                kphWindSpeedText.setDisable(true);
                mphWindSpeedText.setDisable(false);
            }
        });
    }

    public StackPane getLayout() {
        this.setWeatherData();
        // Title, Today's Weather image, switch units toggle button
        GridPane mainContentLayout = new GridPane();
        // Add vertical spacing between rows
        mainContentLayout.setVgap(10);

        // Set up column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        // Set the first column to take up 20% of the total width of mainContentLayout
        col1.setPercentWidth(22);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(22);
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(26);
        col3.setHgrow(Priority.ALWAYS);
        mainContentLayout.getColumnConstraints().addAll(col1, col2, col3);

        // Define row constraints
        for (int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(15);
            row.setMinHeight(5);
            // Expand rows to fit vertical layout
            row.setVgrow(Priority.ALWAYS);
            mainContentLayout.getRowConstraints().add(row);
        }

        // Title, Icon, switch units button row
        mainContentLayout.add(todaysWeatherContentTitle, 0, 0, 1, 1);
        // Horizontally align to the bottom left
        GridPane.setHalignment(todaysWeatherContentTitle, HPos.LEFT);
        // Vertically align with the bottom of the weather icon
        GridPane.setValignment(todaysWeatherContentTitle, VPos.BOTTOM);
        mainContentLayout.add(weatherIconImageView, 1, 0, 1, 1);
        GridPane.setValignment(weatherIconImageView, VPos.CENTER);
        mainContentLayout.add(switchUnitsBtn, 2, 0, 1, 1);
        // Vertically align with the bottom of the weather icon
        GridPane.setValignment(switchUnitsBtn, VPos.BOTTOM);

        // Set margin for each element to add space around them
        GridPane.setMargin(todaysWeatherContentTitle, new Insets(0, 10, 0, 0)); // Right 10px
        GridPane.setMargin(weatherIconImageView, new Insets(0, 50, 20, 0));   // Right 50px bottom 20px
        GridPane.setMargin(switchUnitsBtn, new Insets(0, 0, 0, 100)); // Left 100px

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

        Separator sep5 = new Separator();
        sep5.setStyle("-fx-background-color: #000000;");
        mainContentLayout.add(sep5, 0, 9, 3, 1);

        // Add the 12 hour temperature row
        GridPane hourlyLayout = new GridPane();
        for (int i = 0; i < TIME_INTERVAL; i++) {
            VBox hourlyContentSquare = new VBox(5, hourlyIconList.get(i), hourlyTempList.get(i), hourlyTimeList.get(i));
            hourlyContentSquare.setAlignment(Pos.CENTER);
            hourlyContentSquare.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(hourlyContentSquare, Priority.ALWAYS);
            hourlyLayout.add(hourlyContentSquare, i, 0);
        }

        // Add hourly gird to last row in main layout
        mainContentLayout.add(hourlyLayout, 0, 10, 3, 1);
        mainContentLayout.setStyle("-fx-background-color: linear-gradient(to bottom, white, gray); -fx-background-radius: 25px;");
        mainContentLayout.setAlignment(Pos.CENTER);
        mainContentLayout.setMaxHeight(440);

        StackPane backgroundWrapper = new StackPane(mainContentLayout);
        backgroundWrapper.setStyle("-fx-padding: 40px;");
        backgroundWrapper.setAlignment(Pos.CENTER);
        return backgroundWrapper;
    }
}