package fileRecord;

import lombok.Getter;
import lombok.Setter;
import webParse.Line;
import webParse.Stations;

import java.util.*;

@Getter
@Setter
public class DataRecord {

    private ArrayList<Stations> stationList;                            // common list Stations
    private LinkedHashMap<String, Line> linesList;                      // common list Lines
    private ArrayList<HashMap> commonArrayDateCSV;                      // common list Date
    private ArrayList<HashMap> commonArrayDepthJSON;                    // common list Depth
    private ArrayList<HashMap> connectionOnStation;                     //common list Connections

    public DataRecord() {
        connectionOnStation = new ArrayList<>();
    }
}
