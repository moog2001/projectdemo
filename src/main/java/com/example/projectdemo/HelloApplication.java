package com.example.projectdemo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloApplication extends Application {

    static final Random rand = new Random();
    private List<Task> list = new ArrayList<>();
    int fieldNumber = (Constants.ONE_SIDE / 2) * (Constants.ONE_SIDE / 2);
    private int totalTotal;
    Label lblMax = new Label();
    int max = 0;

    @Override
    public void start(Stage stage) throws IOException {
        lblMax.setPrefWidth(500);
        lblMax.setAlignment(Pos.CENTER);

        VBox root = new VBox();
        HBox field1 = createField();
        HBox field2 = createField();
        HBox field3 = createField();
        HBox field4 = createField();

        Label lblTotal = new Label();
        lblTotal.setPrefWidth(500);
        lblTotal.setAlignment(Pos.CENTER);

        for (Task n : list) {
            n.valueProperty().addListener(new ChangeListener<Integer>() {

                @Override
                public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                    if (t1 != null) {
                        if (integer == null) totalTotal += t1;
                        else totalTotal = totalTotal + t1 - integer;
                    }
                    lblTotal.setText("All: " + totalTotal);
                    if (t1 != null) {
                        if (t1 > max) {
                            max = t1;
                        }

                        setMax();
                    }
                }
            });
        }

        root.getChildren().addAll(field1, field2, field3, field4, lblTotal, lblMax);
        Scene scene = new Scene(root);
        stage.setTitle("Farm!");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createField() {
        Field[] field = new Field[fieldNumber];
        ProgressBar progressBar = new ProgressBar();

        Task task = new Task() {
            int total = 0;

            @Override
            protected Integer call() throws Exception {
                int i;
                for (i = 0; i < fieldNumber; i++) {
                    if (isCancelled()) break;

                    int harvested = rand.nextInt(5) + 1;
                    Thread.sleep((6 - harvested) * Constants.SLEEP);
                    // энд хураасан төмсний тоог төмсний чанараар тооцов.
                    total += harvested;
                    updateValue(total);
                    updateMessage("this field potatoes/quality: " + harvested);
                    updateTitle("total: " + total);
                    updateProgress(i, fieldNumber);
                }
                return null;
            }
        };

        list.add(task);

        System.out.println("working: ");
        progressBar.progressProperty().bind(task.progressProperty());
        Thread thread = new Thread(task);
        thread.start();
        progressBar.setPrefWidth(300);

        Label lblTitle = new Label();
        lblTitle.textProperty().bind(task.titleProperty());
        lblTitle.setPrefWidth(100);

        Label lblText = new Label();
        lblText.textProperty().bind(task.messageProperty());
        lblText.setPrefWidth(150);

        HBox root = new HBox();
        root.getChildren().add(progressBar);
        root.getChildren().add(lblTitle);
        root.getChildren().add(lblText);

        return root;
    }

    private void setMax() {
        lblMax.setText("max: " + this.max);
    }

    public static void main(String[] args) {
        launch();
    }
}