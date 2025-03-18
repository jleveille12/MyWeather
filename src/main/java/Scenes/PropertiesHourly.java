package Scenes;

import weather.Elevation;
import weather.Period;

import java.util.ArrayList;
import java.util.Date;

public class PropertiesHourly {
    public String units;
    public String forecastGenerator;
    public Date generatedAt;
    public Date updateTime;
    public String validTimes;
    public Elevation elevation;
    public ArrayList<PeriodHourly> periods;
}
