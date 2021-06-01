package me.blf.controllers;

import me.blf.model.PathRepresentation;
import me.blf.services.FileSearcher;
import me.blf.services.FileViewer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

public class MainFormController {
    private final String fileImagePath = "images/file.png";
    private final String folderImagePath = "images/folder.png";
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private TextField rootDir;

    @FXML
    private TextField searchPhrase;

    @FXML
    private TextField extension;

    @FXML
    private TreeView<PathRepresentation> filesTreeView;

    @FXML
    private TextArea fileContentArea;

    @FXML
    private void treeViewOnMouseClick(){
        var clickedItem = filesTreeView.getSelectionModel().getSelectedItem();
        clickedItem.setExpanded(!clickedItem.isExpanded());
        if(clickedItem.isLeaf())
            fileContentArea.setText(FileViewer.getFileText(clickedItem.getValue().getPath()));
    }

    @FXML
    private void processSearching(ActionEvent event) {
        //TODO: implement various checks
        filesTreeView.setRoot(null);
        Path rootPath = Paths.get(rootDir.getText());
        FileSearcher fileSearcher = new FileSearcher(searchPhrase.getText(), rootPath, extension.getText().replace("*", ""));
        var pathsLst = fileSearcher.processSearching();
        populateFilesTreeView(pathsLst, rootPath);
        //expand first level of the tree
        filesTreeView.getRoot().setExpanded(true);
    }

    @FXML
    private void processFileChoosing(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        if (!rootDir.getText().isEmpty() && Files.exists(Paths.get(rootDir.getText())))
            directoryChooser.setInitialDirectory(new File(rootDir.getText()));

        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        rootDir.setText(selectedDirectory.getAbsolutePath());
    }

    private void populateFilesTreeView(List<Path> pathLst, Path rootPath) {
        TreeItem<PathRepresentation> root = new TreeItem<>(new PathRepresentation(rootPath, true), new ImageView(new Image(ClassLoader.getSystemResourceAsStream(folderImagePath))));
        for (Path path : pathLst) {
            insertFileToTree(path, root);
        }
        filesTreeView.setRoot(root);
    }

    private void insertFileToTree(Path path, TreeItem<PathRepresentation> root) {
        //convert file's location to a stack of paths
        var curParent = path.getParent();
        Stack<Path> pathStack = new Stack<>();
        while (curParent != null && !root.getValue().getPath().equals(curParent)) {
            pathStack.push(curParent);
            curParent = curParent.getParent();
        }

        //forward from the tree root to the target folder, if exists
        var found = true;
        while (!pathStack.isEmpty() && found) {
            found = false;
            var curPath = pathStack.peek();
            for (var rootCh : root.getChildren())
                if (rootCh.getValue().getPath().equals(curPath)) {
                    root = rootCh;
                    found = true;
                    pathStack.pop();
                    break;
                }
        }
        //if target folder does not exist in the tree, add it to the tree
        while (!pathStack.isEmpty()) {
            var newRoot = new TreeItem<>(new PathRepresentation(pathStack.pop()),new ImageView(new Image(ClassLoader.getSystemResourceAsStream(folderImagePath))));
            root.getChildren().add(newRoot);
            root = newRoot;
        }

        //finally add file to its proper folder
        root.getChildren().add(new TreeItem<>(new PathRepresentation(path),new ImageView(new Image(ClassLoader.getSystemResourceAsStream(fileImagePath)))));
    }
}