package webParse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class Stations {

    private String lineNumber;
    private String stationName;
    private ArrayList<Stations> stationsList;
    private ArrayList<HashMap> connectionOnStation;

    public Stations(String stationName, String lineNumber) {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
        stationsList = new ArrayList<>();
        connectionOnStation = new ArrayList<>();
    }

    protected void addStation(Stations stations) {
        stationsList.add(stations);
    }

    public Stations() {
        this("", "");
    }

    protected void addConnections(HashMap connection) {
        connectionOnStation.add(connection);
    }

    @Override
    public String toString() {
        return "\n"
                + getLineNumber()
                + " линия, "
                + "станция - "
                + stationName;
    }
}
