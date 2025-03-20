package Utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IconLoader {

    // Maps weather conditions to icon file names.
    // It is static because it shared across all instances of the IconLoader class. Not specific to one object.
    // It is final because it is not being changed after it is initialized.
    private static final Map<String, String> weatherIcons = new HashMap<>();

    // Put weather images into hashmap
    static {
        weatherIcons.put("Sunny", "sunny.png");
        weatherIcons.put("Mostly Sunny", "sunny.png");
        weatherIcons.put("Partly Sunny", "sunny.png");
        weatherIcons.put("Cloudy", "cloudy.png");
        weatherIcons.put("Mostly Cloudy", "cloudy.png");
        weatherIcons.put("Partly Cloudy", "cloudy.png");
        weatherIcons.put("Mostly Clear", "clear.png");
        weatherIcons.put("Clear", "clear.png");
        weatherIcons.put("Overcast", "cloudy.png");
        weatherIcons.put("Rain", "rainy.png");
        weatherIcons.put("Light Rain", "rainy.png");
        weatherIcons.put("Heavy Rain", "heavy_rain.png");
        weatherIcons.put("Thunderstorms", "thunderstorm.png");
        weatherIcons.put("Scattered Thunderstorms", "thunderstorm.png");
        weatherIcons.put("Snow", "snow.png");
        weatherIcons.put("Light Snow", "snow.png");
        weatherIcons.put("Flurries", "snow.png");
        weatherIcons.put("Freezing Rain", "sleet.png");
        weatherIcons.put("Sleet", "sleet.png");
        weatherIcons.put("Rain And Snow", "sleet.png");
        weatherIcons.put("Foggy", "fog.png");
        weatherIcons.put("Haze", "haze.png");
    }

    public static ImageView getWeatherIcon(String forecastCondition) {
        // Load the default icon in case forecast does not match
        String iconName = weatherIcons.get(forecastCondition);

        // Try to find the closest match forecast condition in the map if the exact match is not found
        if (iconName == null) {
            for (Map.Entry<String, String> pair : weatherIcons.entrySet()) {
                if (forecastCondition.toLowerCase().contains(pair.getKey().toLowerCase())) {
                    // get the closest match
                    iconName = pair.getValue();
                    break;
                }
            }
        }

        // No matches, use default
        if (iconName == null) {
            iconName = "default.png";
        }

        // Load the icon from the resources folder
        Image icon = new Image(Objects.requireNonNull(IconLoader.class.getResourceAsStream("/images/" + iconName)));

        // Create a new imageView object
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);

        return imageView;
    }
}