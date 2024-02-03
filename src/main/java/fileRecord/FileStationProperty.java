package fileRecord;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import webParse.Stations;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FileStationProperty extends DataRecord {

    private LinkedHashMap<String, Object> stationDataByExample;
    private ArrayList<LinkedHashMap> stationDataListByExample;
    private LinkedHashMap<String, ArrayList> metroJSONByExample;
    private ArrayList<HashMap> usedDateValues;
    private ArrayList<HashMap> usedDepthValues;
    private ArrayList<HashMap> usedConnectionValues;

    public FileStationProperty() {
        stationDataListByExample = new ArrayList<>();
        metroJSONByExample = new LinkedHashMap<>();
        usedDateValues = new ArrayList<>();
        usedDepthValues = new ArrayList<>();
        usedConnectionValues = new ArrayList<>();
    }

    public void getDataStringStationProperty(DataRecord object) {
        Collections.sort(object.getStationList(), new Comparator<Stations>() {
            @Override
            public int compare(Stations s1, Stations s2) {
                return s1.getStationName()
                        .compareTo(s2.getStationName());
            }
        });
        object.getStationList()
                .forEach(stations -> {
                    stationDataByExample = new LinkedHashMap<>();
                    stationDataByExample.put("name", stations.getStationName());
                    String nameLine = object.getLinesList()
                            .get(stations.getLineNumber())
                            .getLineName();
                    stationDataByExample.put("line", nameLine);
                    getDateOpen(object, stations);
                    getStationDepth(object, stations);
                    getStationConnection(object, stations);
                    stationDataListByExample.add(stationDataByExample);
                });
        metroJSONByExample.put("stations", stationDataListByExample);
    }

    private void getDateOpen(DataRecord object, Stations stations) {
        HashMap<String, String> usedValues = new HashMap<>();
        object.getCommonArrayDateCSV()
                .forEach(arrayDate -> {
                    boolean toWrite = getWriteAccess(arrayDate, stations);
                    if (toWrite && !usedDateValues.contains(
                            arrayDate))
                    {
                        arrayDate.forEach((k, v) -> {
                            stationDataByExample.put("date", v.toString());
                            usedValues.put(k.toString()
                                                   .toUpperCase(), v.toString());
                            usedDateValues.add(usedValues);
                        });
                    }
                });
    }

    private void getStationDepth(DataRecord object, Stations stations) {
        HashMap<String, String> usedValues = new HashMap<>();
        object.getCommonArrayDepthJSON()
                .forEach(arrayDepth -> {
                    boolean toWrite = getWriteAccess(arrayDepth, stations);
                    if (toWrite && !usedDepthValues.contains(
                            arrayDepth))
                    {
                        arrayDepth.forEach((k, v) -> {
                            compareDepth();
                            stationDataByExample.put("depth", v.toString());
                            usedValues.put(k.toString(), v.toString());
                            usedDepthValues.add(usedValues);
                        });
                    }
                });
    }

    private void getStationConnection(DataRecord object, Stations stations) {
        HashMap<String, String> usedValues = new HashMap<>();
        object.getConnectionOnStation()
                .forEach(arrayConnect -> {
                    boolean toWrite = getWriteAccess(arrayConnect, stations);
                    if (toWrite && !usedConnectionValues.contains(
                            arrayConnect))
                    {
                        arrayConnect.forEach((k, v) -> {
                            stationDataByExample.put("hasConnection", v);
                            usedValues.put(k.toString(), v.toString());
                            usedConnectionValues.add(usedValues);
                        });
                    }
                });
    }

    private Boolean getWriteAccess(HashMap array, Stations stations) {
        String upperKeySet = array.keySet()
                .toString();
        upperKeySet = upperKeySet.replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .toUpperCase();
        if (upperKeySet.equals(stations.getStationName()
                                       .toUpperCase()))
        {
            return true;
        }
        return false;
    }

    public void recordFileStationProperty(String path) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try
        {
            writer.writeValue(Paths.get(path)
                                      .toFile(), metroJSONByExample);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void compareDepth() {
        Collections.sort(stationDataListByExample, new Comparator<LinkedHashMap>() {
            @Override
            public int compare(LinkedHashMap map1, LinkedHashMap map2) {
                if (map1.size() < 5 || map2.size() < 5)
                {
                    return 0;
                }
                if (!map1.get("name")
                        .equals(map2.get("name")))
                {
                    return 0;
                }
                double value1 = Double.valueOf(map1.get("depth")
                                                       .toString()
                                                       .replace(',', '.'));
                double value2 = Double.valueOf(map2.get("depth")
                                                       .toString()
                                                       .replace(',', '.'));
                return Double.compare(value1, value2);
            }
        });
    }
}
