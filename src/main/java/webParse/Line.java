package webParse;

import lombok.Getter;

import java.util.*;

@Getter
public class Line {

    private String lineName;
    private String lineNumber;
    private LinkedHashMap<String, Line> linesList;
    private ArrayList<Stations> stationList;

    public Line(String numberLine, String nameLine) {
        this.lineNumber = numberLine;
        this.lineName = nameLine;
        linesList = new LinkedHashMap<>();
        stationList = new ArrayList<>();
    }

    public Line() {
        this("", "");
    }

    protected void addLine(Line line) {
        linesList.put(line.getLineNumber(), line);
    }

    protected void addStation(Stations stations) {
        stationList.add(stations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        linesList.forEach((s, line) -> {
            sb.append(line.lineNumber)
                    .append("-> ")
                    .append(line.lineName)
                    .append("\n");
            stationList.forEach(stations -> {
                if (stations.getLineNumber()
                        .equals(s))
                {
                    sb.append("\t" + "  ")
                            .append(stations.getStationName())
                            .append("\n");
                }
            });
        });
        return sb.toString();
    }
}

