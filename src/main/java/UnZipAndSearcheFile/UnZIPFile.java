package UnZipAndSearcheFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZIPFile {

    private String pathOnZIPArchive;
    private String pathOnUnZIPArchive;

    public UnZIPFile(String pathOnZIPArchive, String pathOnUnZIPArchive) {
        this.pathOnZIPArchive = pathOnZIPArchive;
        this.pathOnUnZIPArchive = pathOnUnZIPArchive + "\\";
        extractFromArchive();
    }

    private void extractFromArchive() {
        {
            try
            {
                FileInputStream inputStream = new FileInputStream(pathOnZIPArchive);
                ZipInputStream zipInput = new ZipInputStream(inputStream);
                while (true)
                {
                    ZipEntry entry = zipInput.getNextEntry();
                    if (entry == null)
                    {
                        break;
                    }
                    File file = new File(pathOnUnZIPArchive + entry.getName());
                    if (entry.isDirectory())
                    {
                        file.mkdirs();
                    } else
                    {
                        byte[] bytes = zipInput.readAllBytes();
                        Files.write(Paths.get(file.getAbsolutePath()), bytes, StandardOpenOption.CREATE);
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
