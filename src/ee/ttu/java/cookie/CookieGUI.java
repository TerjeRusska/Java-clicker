package ee.ttu.java.cookie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.File;

/**
 * Created by Terje on 03.04.2017.
 */
public class CookieGUI extends Application {

    /**
     * Screen Width.
     */
    public static final int WIDTH = 1200;
    /**
     * Screen height.
     */
    public static final int HEIGTH = 800;
    /**
     * Padding.
     */
    public static final int PADDING = 20;

    /**
     * Size for second screen.
     */
    public static final double DECREASE = 0.8;

    /**
     * Small java image.
     */
    public static final int SMALL = 300;

    /**
     * Normal java image.
     */
    public static final int NORM = 375;

    /**
     * Constant 10.
     */
    public static final int TEN = 10;
    /**
     * Constant 30.
     */
    public static final int THIRTY = 30;

    /**
     * Constant 100.
     */
    public static final int HUNDRED = 100;
    /**
     * Constant 50.
     */
    public static final int FIFTY = 50;
    /**
     * Size 580.
     */
    public static final int SIZE580 = 580;
    /**
     * Size 380.
     */
    public static final int SIZE380 = 380;
    /**
     * Size 760.
     */
    public static final int SIZE760 = 760;
    /**
     * Refrence to mediaplayer.
     */
    private static MediaPlayer player;

    /**
     * Starts the application.
     * @param args Javafx nodes.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CookieGame game = new CookieGame();
        primaryStage.setTitle("Coffee Clicker");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        StackPane backPanel = new StackPane();
        backPanel.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        backPanel.setStyle("-fx-background-color: transparent");
        HBox split = new HBox();
        split.setAlignment(Pos.CENTER);
        split.setStyle("-fx-background-color: rgba(1,1,1,0);");

        Button exit = exit(primaryStage);

        Media media = new Media(getClass().getClassLoader().getResource("ee/ttu/java/cookie/BackgroundMusic.mp3").toString());
        player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        Label cookies = new Label("0" + "\n" + "Lines of (code);");
        cookies.setStyle("-fx-font-size: 50 px;" + "-fx-font-weight: bold;"
                + "-fx-background-color: rgba(1,1,1,0);" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");

        javafx.scene.control.Button buyCursor = new Button("Buy Cursor");
        mouseEffects(buyCursor);

        javafx.scene.control.Button buyClicker = new Button("Buy Clicker");
        mouseEffects(buyClicker);

        Image image = background();
        ImageView imageView2 = javaClick(game, buyClicker, buyCursor, cookies);
        ImagePattern pattern = new ImagePattern(image);

        StackPane leftCanvas = new StackPane();
        leftCanvas.setPrefSize(SIZE760, SIZE580);
        leftCanvas.setStyle("-fx-background-color: rgba(1,1,1,0);");

        leftCanvas.getChildren().addAll(imageView2);
        leftCanvas.setAlignment(Pos.CENTER);

        VBox leftSplit = new VBox();
        leftSplit.setAlignment(Pos.CENTER);
        leftSplit.getChildren().addAll(cookies, leftCanvas);
        VBox rightSplit = new VBox();
        rightSplit.setAlignment(Pos.CENTER);

        VBox up = new VBox();
        up.setStyle("-fx-background-color: rgba(255,215,0,0.5);");
        up.setPrefSize(SIZE580, SIZE380);
        up.setPadding(new Insets(THIRTY, TEN, THIRTY, TEN));
        up.setAlignment(Pos.CENTER_RIGHT);

        Label price = new Label("Price:");
        price.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;");

        Label cursorInf = new Label(String.format(
                "%8s\t%4d\t\t%5d", "Cursors:", game.getCursorCount(), game.getCursorPrice()));
        cursorInf.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;"
                + "-fx-pref-width: 580;");

        Label clickerInf = new Label(String.format(
                "%8s\t%4d\t\t%5d", "Clickers:", game.getClickerCount(), game.getClickerPrice()));
        clickerInf.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;"
                + "-fx-pref-width: 580;");

        up.getChildren().addAll(price, cursorInf, clickerInf);

        VBox down = new VBox();
        down.setStyle("-fx-background-color: rgba(127,255,212,0.5);");
        down.setPadding(new Insets(THIRTY, TEN, THIRTY, TEN));
        down.setSpacing(TEN);
        down.setPrefSize(SIZE580, SIZE380);
        down.setAlignment(Pos.TOP_CENTER);

        Timeline clickerAction = clickerAction(game, buyClicker, buyCursor, cookies, imageView2);

        buyCursor.setOnMouseClicked(event -> {
            game.buyCursor();
            cookies.setText(game.getCookies() + "\n" + "Lines of (code);");
            cursorInf.setText(String.format(
                    "%8s\t%4d\t\t%5d", "Cursors:", game.getCursorCount(), game.getCursorPrice()));
            if (!game.canBuyCursor()) {
                buyCursor.setVisible(false);
            }
            if (!game.canBuyClicker()) {
                buyClicker.setVisible(false);
            }
        });

        buyClicker.setOnMouseClicked(event -> {
            game.buyClicker();
            clickerAction.stop();
            clickerAction.setRate((1.0 / game.getClickerInterval()));
            clickerAction.play();
            cookies.setText(game.getCookies() + "\n" + "Lines of (code);");
            clickerInf.setText(String.format(
                    "%8s\t%4d\t\t%5d", "Clickers:", game.getClickerCount(), game.getClickerPrice()));
            if (!game.canBuyClicker()) {
                buyClicker.setVisible(false);
            }
            if (!game.canBuyCursor()) {
                buyCursor.setVisible(false);
            }
        });

        Button info = info(pattern);

        down.getChildren().addAll(buyCursor, buyClicker);

        rightSplit.getChildren().addAll(up, down);
        split.getChildren().addAll(leftSplit, rightSplit);
        backPanel.getChildren().addAll(split, exit, info);

        primaryStage.setScene(new Scene(backPanel, WIDTH, HEIGTH, pattern));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Mouse hovering button changes.
     * @param button button that will change.
     */
    private void mouseEffects(Button button) {
        button.managedProperty().bind(button.visibleProperty());
        button.setVisible(false);
        button.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                + "-fx-font-size: 30 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-font-weight: bold;");
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                    + "-fx-font-size: 30 px;" + "-fx-text-fill: gold;"
                    + "-fx-font-family: Impact;" + "-fx-font-weight: bold;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                    + "-fx-font-size: 30 px;" + "-fx-text-fill: gold;"
                    + "-fx-font-family: Impact;" + "-fx-font-weight: bold;");
        });
    }

    /**
     * Creates a background image.
     * @return image.
     */
    private Image background() {
        return image("ee/ttu/java/cookie/Background.jpg");
    }

    /**
     * Creates a clickable pictrue.
     * @param game CookieGame object
     * @param buyClicker button to buy clickers
     * @param buyCursor button to buy cursors
     * @param cookies label that shows cookies
     * @return picture
     */
    private ImageView javaClick(CookieGame game, Button buyClicker, Button buyCursor, Label cookies) {
        Image image2 = image("ee/ttu/java/cookie/JavaImg.png");
        ImageView imageView2 = new ImageView(image2);

        imageView2.setFitHeight(NORM);
        imageView2.setFitWidth(NORM);
        imageView2.setOnMouseClicked(event -> {
            game.click();
            if (game.canBuyClicker()) {
                buyClicker.setVisible(true);
            }
            if (game.canBuyCursor()) {
                buyCursor.setVisible(true);
            }
            cookies.setText(game.getCookies() + "\n" + "Lines of (code);");
            Timeline timeline2 = new Timeline(new KeyFrame(
                    Duration.millis(HUNDRED),
                    ae -> {
                        imageView2.setFitHeight(NORM);
                        imageView2.setFitWidth(NORM);
                    }));
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(FIFTY),
                    ae -> {
                        imageView2.setFitHeight(SMALL);
                        imageView2.setFitWidth(SMALL);
                    }));
            timeline.play();
            timeline2.play();
        });
        return imageView2;
    }

    /**
     * Creates an image object.
     *
     * @param pathname location of the image.
     * @return image object
     */
    private Image image(String pathname) {
        javafx.scene.image.Image image = new Image(getClass().getClassLoader().getResource(pathname).toString());
        return image;
    }

    /**
     * Timeline for automatic clicking.
     * @param game CookieGame object
     * @param buyClicker button to buy clickers
     * @param buyCursor button to buy cursors
     * @param cookies label that shows cookies
     * @param imageView2 clickable picture
     * @return timeline
     */
    private Timeline clickerAction(
            CookieGame game, Button buyClicker, Button buyCursor, Label cookies, ImageView imageView2) {
        Timeline clickerAction = new Timeline(new KeyFrame(
                Duration.millis(1),
                ae -> {
                    if (game.getClickerCount() != 0) {
                        game.clickerAction();
                        if (game.canBuyClicker()) {
                            buyClicker.setVisible(true);
                        }
                        if (game.canBuyCursor()) {
                            buyCursor.setVisible(true);
                        }
                        cookies.setText(game.getCookies() + "\n" + "Lines of (code);");

                        Timeline timeline2 = new Timeline(new KeyFrame(
                                Duration.millis(HUNDRED),
                                e -> {
                                    imageView2.setFitHeight(NORM);
                                    imageView2.setFitWidth(NORM);
                                }));
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.millis(FIFTY),
                                e -> {
                                    imageView2.setFitHeight(SMALL);
                                    imageView2.setFitWidth(SMALL);
                                }));
                        timeline.play();
                        timeline2.play();
                    }
                }));
        clickerAction.setCycleCount(Timeline.INDEFINITE);
        clickerAction.play();
        return clickerAction;
    }

    /**
     * Creates a new window.
     * @param pattern background
     */
    private void infoStage(ImagePattern pattern) {
        Stage infostage = new Stage();
        infostage.initStyle(StageStyle.UNDECORATED);
        StackPane infoscreen = new StackPane();
        infoscreen.setStyle("-fx-background-color: rgba(33,33,33,0.77)");
        infoscreen.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        infostage.setTitle("Info");
        infostage.setResizable(false);

        Label infolabel = new Label("Created by Terje Russka");
        infolabel.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: gold;" + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(infolabel, Pos.CENTER);

        Button close = new Button("Close");
        close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        close.setOnMouseEntered(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        close.setOnMouseExited(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        close.setOnMouseClicked(eventclose -> infostage.close());

        infoscreen.getChildren().addAll(close, infolabel);
        infostage.setScene(new Scene(infoscreen, WIDTH * DECREASE, HEIGTH * DECREASE, pattern));
        infostage.show();
    }

    /**
     * Creates a button that closes the application.
     * @param primaryStage application primary stage.
     * @return close button.
     */
    private Button exit(Stage primaryStage) {
        Button exit = new Button("Exit");
        exit.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        exit.setOnMouseEntered(event -> {
            exit.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        exit.setOnMouseExited(event -> {
            exit.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        exit.setOnMouseClicked(event -> primaryStage.close());
        return exit;
    }

    /**
     * Button that opens a new window.
     * @param pattern background
     * @return info button.
     */
    private Button info(ImagePattern pattern) {
        Button info = new Button("Info");
        info.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;"
                + "-fx-font-family: Impact;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(info, Pos.BOTTOM_LEFT);
        info.setOnMouseEntered(event -> {
            info.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: gold;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        info.setOnMouseExited(event -> {
            info.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: gold;" + "-fx-font-family: Impact;");
        });
        info.setOnMouseClicked(event -> infoStage(pattern));
        return info;
    }
}
