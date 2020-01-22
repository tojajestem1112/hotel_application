package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.dao.Dao;


public class Main extends Application {

    private static Thread appThread, dbConnectionThread;
    private static volatile boolean isLoading = true;
    private static volatile boolean isConnected = false;
    private static Parent loadingRoot;
    private static Parent appRoot;
    private static Parent connectingError;
    private static Stage mainStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        System.out.print(System.getProperty("os.name"));
        loadingRoot = FXMLLoader.load(getClass().getResource("/loader.fxml"));//"/logging/logging.fxml"));
        appRoot = FXMLLoader.load(getClass().getResource("/logging/Logging.fxml"));
        connectingError = FXMLLoader.load(getClass().getResource("/ConnectionError.fxml"));

        primaryStage.setMaxWidth(950);
        primaryStage.setMinWidth(950);
        primaryStage.setMaxHeight(600);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(loadingRoot, 960, 600));
        primaryStage.show();

        dbConnectionThread = new Thread(Main::loading);
        dbConnectionThread.start();
        appThread=new Thread(Main::runApp);
        appThread.start();

    }

    public static void loading()
    {
        try {
            Dao dao = new Dao();
            isConnected = true;
        }catch (Exception e)
        {
            isConnected=false;
        }
        finally
        {
            isLoading = false;
        }

    }
    private static void runApp() {
        while (isLoading && !isConnected) {
        }
        Platform.runLater(new Runnable() {
            public void run() {
                if(isConnected)
                    mainStage.setScene(new Scene(appRoot, 950, 600));
                else
                    mainStage.setScene(new Scene(connectingError, 950, 600));
                mainStage.show();
                System.out.println("ended");
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
