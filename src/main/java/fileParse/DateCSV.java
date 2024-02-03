package fileParse;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Getter
public class DateCSV {
    private String nameStation;
    private String dateStation;
    private ArrayList<HashMap> arrayCSV;

    public DateCSV() {
        arrayCSV = new ArrayList<>();
    }

    public void fileParse(String pathCSVFile) {
        try
        {
            List<String> list = Files.readAllLines(Path.of(pathCSVFile));
            int counter = 0;
            for (String line : list
            )
            {
                if (counter == 0)
                {
                    counter++;
                    continue;
                }
                String[] fragments = line.split(",");
                if (fragments.length != 2)
                {
                    System.out.println("\n" + "Wrong line: " + line);
                    continue;
                }
                HashMap<String, String> objectCSV = new HashMap<>();
                nameStation = fragments[0];
                dateStation = fragments[1];
                objectCSV.put(nameStation, dateStation);

                if (checkStationOnList(nameStation, dateStation))
                {
                    arrayCSV.add(objectCSV);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Boolean checkStationOnList(String nameStation, String dateStation) {
        for (HashMap e : arrayCSV
        )
        {
            if (e.containsKey(nameStation)
                    && e.containsValue(dateStation))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        arrayCSV.forEach(value -> {
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
