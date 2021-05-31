import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class PathRepresentation {
    private final Path path;
    private final String representation;
    private final boolean isDirectory;
    private final boolean isRoot;

    public PathRepresentation(Path path, boolean isRoot) {
        this.isRoot = isRoot;
        this.path = path;
        this.representation = path.toString();
        this.isDirectory = Files.isDirectory(path);
    }

    public PathRepresentation(Path path) {
        this.isRoot = false;
        this.path = path;
        this.representation = path.getFileName().toString();
        this.isDirectory = Files.isDirectory(path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathRepresentation that = (PathRepresentation) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return representation;
    }

    public Path getPath() {
        return path;
    }

    public boolean isRoot(){
        return isRoot;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}
