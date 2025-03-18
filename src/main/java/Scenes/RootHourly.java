package Scenes;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RootHourly {
    public PropertiesHourly properties;
}
