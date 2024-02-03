package fileRecord;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
@Setter
public class FileFormatFromProject extends DataRecord {
    private ArrayList<String> stationListByFormat;
    private LinkedHashMap<String, ArrayList> stationMapByFormat;
    private LinkedHashMap<String, String> linesObjectByFormat;
    private ArrayList<LinkedHashMap> linesListByFormat;
    private LinkedHashMap<String, Object> metroJSONByFormat;

    public FileFormatFromProject() {
        super();
        stationMapByFormat = new LinkedHashMap<>();
        stationListByFormat = new ArrayList<>();
        metroJSONByFormat = new LinkedHashMap<>();
        linesListByFormat = new ArrayList<>();
        linesObjectByFormat = new LinkedHashMap<>();
    }

    public void getDataStringByFormatProject(DataRecord object) {
        object.getLinesList()
                .forEach((l, line1) -> {
                    stationListByFormat = new ArrayList<>();
                    linesObjectByFormat = new LinkedHashMap<>();
                    object.getStationList()
                            .forEach(st -> {
                                if (st.getLineNumber()
                                        .equals(l))
                                {
                                    stationListByFormat.add(st.getStationName());
                                }
                            });
                    stationMapByFormat.put(l, stationListByFormat);
                    linesObjectByFormat.put("number", line1.getLineNumber());
                    linesObjectByFormat.put("name", line1.getLineName());
                    linesListByFormat.add(linesObjectByFormat);
                });
        metroJSONByFormat.put("Stations", stationMapByFormat);
        metroJSONByFormat.put("Lines", linesListByFormat);
    }

    public void recordFileByFormatProject(String path) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try
        {
            writer.writeValue(Paths.get(path)
                                      .toFile(), metroJSONByFormat);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
