package profplan.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import profplan.commons.core.GuiSettings;
import profplan.commons.core.LogsCenter;
import profplan.logic.Logic;
import profplan.logic.commands.CommandResult;
import profplan.logic.commands.exceptions.CommandException;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.task.Task;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private VBox taskList;

    @FXML
    private GridPane resultGrid;

    @FXML
    private TableView matrixDisplay;

    @FXML
    private StackPane statusbarPlaceholder;

    private class Order {

        private final ObservableMap<Long, List<Task>> priorityTask = FXCollections.observableHashMap();
        private final String priority;

        public Order(ObservableMap<Task, Long> urgencies, String priority) {
            System.out.println("Priority: " + priority);
            this.priority = priority;
            for (long i = 1; i <= 10; i++) {
                priorityTask.put(i, new ArrayList<>());
            }

            for (Task task : urgencies.keySet()) {
                if (task.getPriority().toString().equals(priority)) {
                    System.out.println(task.getName());
                    System.out.println(urgencies.get(task));
                    priorityTask.getOrDefault(urgencies.get(task), new ArrayList<>()).add(task);
                }
            }
        }

        public List<String> getUrgency(long i) {
            return priorityTask.getOrDefault(i, new ArrayList<>()).stream()
                .map(t -> t.getName().toString()).collect(Collectors.toList());
        }

        public String getPriority() {
            return priority;
        }
    }

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getProfPlanFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        matrixDisplay.setEditable(false);

        taskListPanelPlaceholder.prefWidthProperty().bind(resultGrid.widthProperty().multiply(0.3));

        ArrayList<TableColumn<Order, ListView<String>>> columns = new ArrayList<>();
        TableColumn<Order, ListView<String>> priority = new TableColumn<>("");
        priority.setEditable(false);
        priority.setSortable(false);
        priority.setResizable(false);
        priority.prefWidthProperty().bind(matrixDisplay.widthProperty().multiply(0.1));
        priority.setCellValueFactory(o -> {
            ListView<String> temp = new ListView<>(FXCollections.observableArrayList(o.getValue().priority));
            temp.setSelectionModel(null);
            return new ReadOnlyObjectWrapper<>(temp);
        });
        columns.add(priority);

        loadMatrix();

        for (int i = 1; i <= 10; i++) {
            TableColumn<Order, ListView<String>> urgency = new TableColumn<>(String.valueOf(i));
            int finalI = i;
            urgency.setCellValueFactory(o -> {
                ListView<String> temp =
                    new ListView<>(FXCollections.observableArrayList(o.getValue().getUrgency(finalI)));
                temp.setSelectionModel(null);
                return new ReadOnlyObjectWrapper<>(temp);
            });
            urgency.setEditable(false);
            urgency.setSortable(false);
            urgency.setResizable(false);
            urgency.prefWidthProperty().bind(matrixDisplay.widthProperty().multiply(0.2));
            columns.add(urgency);
        }
        matrixDisplay.getColumns().addAll(columns);

        matrixDisplay.setSelectionModel(null);
        matrixDisplay.fixedCellSizeProperty().bind(matrixDisplay.heightProperty().multiply(0.2));
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    private void loadMatrix() {
        ObservableList<Order> rows = FXCollections.observableArrayList();

        ObservableList<Task> filteredTasks = logic.getFilteredTaskList();
        ObservableMap<Task, Long> taskUrgency = FXCollections.observableHashMap();

        for (Task t : filteredTasks) {
            long daysLeft = t.getDueDate().getDaysFromNow();
            taskUrgency.put(t, daysLeft);
        }
        long minDaysLeft = Collections.min(taskUrgency.values());
        long maxDaysLeft = Collections.max(taskUrgency.values());
        long split = (minDaysLeft + maxDaysLeft) / 10;
        System.out.println(split);
        System.out.println(minDaysLeft);
        System.out.println(maxDaysLeft);
        taskUrgency.replaceAll((t, v) -> v / split + 1);


        for (int i = 10; i >= 1; i--) {
            Order temp = new Order(taskUrgency, String.valueOf(i));
            rows.add(temp);
        }
        matrixDisplay.setItems(rows);
        matrixDisplay.refresh();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            loadMatrix();

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
