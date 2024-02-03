package UnZipAndSearcheFile;

import fileParse.DateCSV;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchFIle {
    DateCSV dateCSV;
    private String csvExtension = ".csv";
    private String jsonExtension = ".json";
    private String path;
    private List<String> result;

    public SearchFIle(String path) {
        this.path = path;
        result = new ArrayList<>();
        findFiles(path);
    }

    private void findFiles(String path) {
        File file = new File(path);
        if (file.isFile())
        {
            result.add(checkExtension(path));
            return;
        }
        File[] files = file.listFiles();
        for (File f : files
        )
        {
            if (f.isFile())
            {
                result.add(checkExtension(f.getPath()));
            } else
            {
                findFiles(f.getPath());
            }
        }
    }

    private String checkExtension(String path) {
        boolean csv = path.indexOf(csvExtension) > 0;
        boolean json = path.indexOf(jsonExtension) > 0;
        if (csv || json)
        {
            return path;
        }
        return "";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        result.forEach(e -> sb.append(e)
                .append("\n"));
        return sb.toString();
    }
}
