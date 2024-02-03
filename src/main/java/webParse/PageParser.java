package webParse;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

@Getter
public class PageParser {

    private String url;
    protected Line linesClass;
    protected Stations stationsClass;
    private Elements elements;

    public PageParser(String url) {
        this.url = url;
        readHTMLPage();
    }

    private void readHTMLPage() {
        try
        {
            Document doc = Jsoup.connect(url)
                    .get();
            parseLine(doc);
            parseStation(doc);
            parseConnections(doc);
        } catch (IOException e)
        {
            System.err.println("Произошла ошибка при чтении HTML страницы: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void parseLine(Document document) {
        linesClass = new Line();
        elements = document.select("span.js-metro-line");
        elements.forEach(element -> {
            String nameLine = element.text();
            String numberLine = element.attr("data-line");
            linesClass.addLine(new Line(numberLine, nameLine));
        });
    }

    private void parseStation(Document document) {
        stationsClass = new Stations();
        elements = document.select("div.js-metro-stations"); // divы со станциями
        elements.forEach(element -> {
            String numberLine = (element.attr("data-line")); // номер линии
            Elements stationsName = element.select("p > span.name"); //имя станции
            Line line = linesClass.getLinesList()
                    .get(numberLine);
            stationsName.forEach(station -> {
                String textNameStation = station.text();
                stationsClass.addStation(new Stations(textNameStation, line.getLineNumber()));
                linesClass.addStation(new Stations(textNameStation, line.getLineNumber()));
            });
        });
    }

    private void parseConnections(Document document) {
        try
        {
            elements = document.select("p.single-station"); // строчка станции - станция, название
            elements.forEach(element -> {
                boolean crossing = false;
                Elements connect = element.select("span.t-icon-metroln");
                String title = connect.attr("title");
                if (!title.isEmpty())
                {
                    HashMap<String, Boolean> connection = new HashMap<>();
                    Elements name = element.select("span.name");
                    String nameStation = name.text();
                    crossing = true;
                    connection.put(nameStation, crossing);
                    stationsClass.addConnections(connection);
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
