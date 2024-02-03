package fileParse;

import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class DepthJSON {
    private String nameStation;
    private String depthStation;
    private ArrayList<HashMap> arrayJSON;

    public DepthJSON() {
        arrayJSON = new ArrayList<>();
    }

    public void fileParse(String pathJSONFile) {
        JSONParser parser = new JSONParser();
        try
        {
            String reader = Files.readString(Paths.get(pathJSONFile));
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            jsonArray.forEach(lineObject -> {
                HashMap<String, String> objectJSON = new HashMap<>();
                JSONObject lineJsonObject = (JSONObject) lineObject;
                nameStation = (String) lineJsonObject.get("station_name");
                depthStation = (String) lineJsonObject.get("depth");
                if (!depthStation.equals("?"))
                {
                    objectJSON.put(nameStation, depthStation);
                    arrayJSON.add(objectJSON);
                }

            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        arrayJSON.forEach(value -> {
            value.forEach((k, v) -> {
                sb.append(k)
                        .append(": ")
                        .append(v);
            });
            sb.append("\n");
        });
        return sb.toString();
    }
}
