import UnZipAndSearcheFile.SearchFIle;
import UnZipAndSearcheFile.UnZIPFile;
import fileParse.DateCSV;
import fileParse.DepthJSON;
import fileRecord.DataRecord;
import fileRecord.FileFormatFromProject;
import fileRecord.FileStationProperty;
import webParse.PageParser;

public class Main {
    public static void main(String[] args)
    {
        String path = "data/";

        String webPage = "https://skillbox-java.github.io/";
        PageParser mosMetro = new PageParser(webPage);
        System.out.println(mosMetro.getLinesClass());

        String pathOnZIPArchive = path + "stations-data.zip";
        String pathOnUnZIPArchive = path + "UnZIP-stations-data";
        UnZIPFile unZIPFile = new UnZIPFile(pathOnZIPArchive, pathOnUnZIPArchive);

        String pathOnSearchFolder = path + "UnZIP-stations-data/data/";
        SearchFIle searchFIle = new SearchFIle(pathOnSearchFolder);
        System.out.println(searchFIle);

        DepthJSON depthJSON = new DepthJSON();

        DateCSV dateCSV = new DateCSV();
        searchFIle.getResult()
                .forEach(result -> {
                    if (result.indexOf(".json") > 0)
                    {
                        depthJSON.fileParse(result);
                    }
                    if (result.indexOf(".csv") > 0)
                    {
                        dateCSV.fileParse(result);
                    }
                });
        System.out.println(depthJSON);
        System.out.println(dateCSV);

        DataRecord recordJSON = new DataRecord();
        recordJSON.setStationList(mosMetro.getLinesClass()
                                          .getStationList());
        recordJSON.setLinesList(mosMetro.getLinesClass()
                                        .getLinesList());
        recordJSON.setConnectionOnStation(mosMetro.getStationsClass()
                                                  .getConnectionOnStation());
        recordJSON.setCommonArrayDateCSV(dateCSV.getArrayCSV());
        recordJSON.setCommonArrayDepthJSON(depthJSON.getArrayJSON());

        String formatProject = path + "list-by-format-project.json";
        FileFormatFromProject format = new FileFormatFromProject();
        format.getDataStringByFormatProject(recordJSON);
        format.recordFileByFormatProject(formatProject);

        String stationProperty = path + "list-station-property.json";
        FileStationProperty property = new FileStationProperty();
        property.getDataStringStationProperty(recordJSON);
        property.recordFileStationProperty(stationProperty);
    }
}