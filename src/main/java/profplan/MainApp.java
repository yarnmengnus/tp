package profplan;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import profplan.commons.core.Config;
import profplan.commons.core.LogsCenter;
import profplan.commons.core.Version;
import profplan.commons.exceptions.DataLoadingException;
import profplan.commons.util.ConfigUtil;
import profplan.commons.util.StringUtil;
import profplan.logic.Logic;
import profplan.logic.LogicManager;
import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.ReadOnlyUserConfigs;
import profplan.model.ReadOnlyUserPrefs;
import profplan.model.UserConfigs;
import profplan.model.UserPrefs;
import profplan.model.util.SampleDataUtil;
import profplan.storage.JsonProfPlanStorage;
import profplan.storage.JsonUserConfigsStorage;
import profplan.storage.JsonUserPrefsStorage;
import profplan.storage.ProfPlanStorage;
import profplan.storage.Storage;
import profplan.storage.StorageManager;
import profplan.storage.UserConfigsStorage;
import profplan.storage.UserPrefsStorage;
import profplan.ui.Ui;
import profplan.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ProfPlan ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        UserConfigsStorage userConfigsStorage = new JsonUserConfigsStorage(config.getUserConfigsFilePath());
        UserConfigs userConfigs = initConfigs(userConfigsStorage);
        ProfPlanStorage profPlanStorage = new JsonProfPlanStorage(userPrefs.getProfPlanFilePath());
        storage = new StorageManager(profPlanStorage, userPrefsStorage, userConfigsStorage);

        model = initModelManager(storage, userPrefs, userConfigs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s task list and {@code userPrefs}. <br>
     * The data from the sample task list will be used instead if {@code storage}'s task list is not found,
     * or an empty task list will be used instead if errors occur when reading {@code storage}'s task list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs,
                                   ReadOnlyUserConfigs userConfigs) {
        logger.info("Using data file : " + storage.getProfPlanFilePath());

        Optional<ReadOnlyProfPlan> addressBookOptional;
        ReadOnlyProfPlan initialData;
        try {
            addressBookOptional = storage.readProfPlan();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getProfPlanFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleProfPlan);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getProfPlanFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new ProfPlan();
        }

        return new ModelManager(initialData, userPrefs, userConfigs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    /**
     * Returns a {@code UserConfigs} using the file at {@code storage}'s user settings file path,
     * or a new {@code UserConfigs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserConfigs initConfigs(UserConfigsStorage storage) {
        Path configsFilePath = storage.getUserConfigsFilePath();
        logger.info("Using settings file : " + configsFilePath);

        UserConfigs initializedConfigs;
        try {
            Optional<UserConfigs> configsOptional = storage.readUserConfigs();
            if (!configsOptional.isPresent()) {
                logger.info("Creating new config file " + configsFilePath);
            }
            initializedConfigs = configsOptional.orElse(new UserConfigs());
        } catch (DataLoadingException e) {
            logger.warning("Settings file at " + configsFilePath + " could not be loaded."
                    + " Using default settings.");
            initializedConfigs = new UserConfigs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserConfigs(initializedConfigs);
        } catch (IOException e) {
            logger.warning("Failed to save settings file : " + StringUtil.getDetails(e));
        }

        return initializedConfigs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ProfPlan ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save user preferences " + StringUtil.getDetails(e));
        }
        try {
            storage.saveUserConfigs(ModelManager.getUserConfigs());
        } catch (IOException e) {
            logger.severe("Failed to save user configs " + StringUtil.getDetails(e));
        }
    }
}
