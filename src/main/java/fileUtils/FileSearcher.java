package fileUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileSearcher {
    private final List<Path> pathList = new ArrayList<>();
    private final String searchPhrase;
    private final Path rootDir;
    private final String extension;

    public FileSearcher(String searchPhrase, Path rootDir, String extension) {
        this.searchPhrase = searchPhrase;
        this.rootDir = rootDir;
        this.extension = extension;
    }

    //implements file selection
    private class CustomFileVisitor extends SimpleFileVisitor<Path>{
        @Override
        public FileVisitResult visitFileFailed(Path path, IOException exc){
            //TODO: log failed file access
            return FileVisitResult.SKIP_SUBTREE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
            if(file.toString().endsWith(extension) && fileContainsPhrase(file))
                pathList.add(file);
            return super.visitFile(file, attrs);
        }
    }

    public List<Path> processSearching(){
        //get all suitable files
        try {
            Files.walkFileTree(rootDir, new CustomFileVisitor());
            return pathList;
        }catch (IOException e){
            //TODO: log exception
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean fileContainsPhrase(Path filePath){
        try {
            var stringStream = Files.lines(filePath);
            var foundString = stringStream
                    .parallel()
                    .filter(str->str.contains(searchPhrase))
                    .findFirst();
            return foundString.isPresent();
        } catch (Exception e) {
            //TODO: log file checking fail
            //e.printStackTrace();
        }
        return false;
    }
}
