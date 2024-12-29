package Frontend;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.control.Alert;

public class KanbanBoardController {

    @FXML
    private ListView<String> todoList;

    @FXML
    private ListView<String> inProgressList;

    @FXML
    private ListView<String> doneList;

    @FXML
    public void initialize() {
        // Initialize drag-and-drop functionality for each ListView
        setupDragAndDrop(todoList);
        setupDragAndDrop(inProgressList);
        setupDragAndDrop(doneList);

        // Add sample tasks
        todoList.getItems().addAll("Task 1", "Task 2", "Task 3");
    }

    private void setupDragAndDrop(ListView<String> listView) {
        // Set drag detected event
        listView.setOnDragDetected(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItem);
                dragboard.setContent(content);
                event.consume();
            }
        });

        // Set drag over event
        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Set drag dropped event
        listView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String draggedItem = dragboard.getString();
                listView.getItems().add(draggedItem);
                ListView<String> sourceList = (ListView<String>) event.getGestureSource();
                sourceList.getItems().remove(draggedItem);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        // Set drag done event
        listView.setOnDragDone(event -> event.consume());
    }

    @FXML
    private void goToHome() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText("Navigating to Home Screen!");
        alert.showAndWait();
        // Add logic to switch to the Home Screen
    }

    @FXML
    private void addTask() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Task");
        alert.setHeaderText(null);
        alert.setContentText("Add Task functionality triggered!");
        alert.showAndWait();
        // Add logic to add a task to the Kanban Board
    }

    @FXML
    private void openSettings() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings");
        alert.setHeaderText(null);
        alert.setContentText("Opening Settings!");
        alert.showAndWait();
        // Add logic to open the settings page
    }

    @FXML
    private void openHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Opening Help Section!");
        alert.showAndWait();
        // Add logic to open the help page
    }
}
