package ar.com.marcos;

import java.nio.file.*;
import java.util.stream.Stream;

/**
 * Created by Marcos.Ladino on 9/28/2017.
 */
public class TextFinder {

    public static void main(String... args){
        String text = args[0];
        try {
          Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
          for (Path dir : dirs){
              Thread thread = new Thread(new Runnable() {

                  @Override
                  public void run() {
                      find(dir,text);
                  }
              });

              thread.start();
              System.out.println("Lance: "+dir.toString());

          }
          System.out.print("Finishing");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
}

    public static void find(Path path, String text){
        if (Files.isDirectory(path)){
            try {
                DirectoryStream<Path> stream = Files.newDirectoryStream(path);
                for(Path pathContent:stream){
                    find(pathContent,text);
                }
            } catch (Exception e) {

            }

        }else {
            try {

                Stream<String> lines = Files.lines(path);
                long count = lines.filter(line -> line.contains(text)).count();
                if (count > 0)
                    System.out.println(path);
            } catch (Exception e) {

            }
        }
    }
}
