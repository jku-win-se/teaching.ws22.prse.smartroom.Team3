package at.jku;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

public class ChartUpdateTask extends Thread{
    private boolean isActive = true;
    private LineChart<String, Integer> chart;
    private XYChart.Series<String, Integer> series = new XYChart.Series<>();
    private ObservableList<XYChart.Series<String,Integer>> peopleChartData = FXCollections.observableArrayList();
    Controller controller;
    String type;
    public ChartUpdateTask(LineChart chart,Controller c, String t) {
        this.chart = chart;
        this.controller = c;
        this.type = t;
    }


    @Override
    public void run() {

        while (isActive) {



            Platform.runLater(() -> {
                if(peopleChartData.isEmpty()) {
                    series.getData().clear();
                    for (int i = 1; i < 10; i++) {
                        int random = 1;
                        if(type == "people") {
                             random = (int) (Math.random() * 30);
                        }
                        if(type == "temp") {
                            random = (int) (Math.random() * (100 - 20 + 1) + 20);
                        }
                        if(type == "co2") {
                            random = (int)(Math.random()*(1400-500+1)+500);
                        }

                        series.getData().add(new XYChart.Data<String, Integer>(Integer.toString(i), random));
                    }
                    int random = 1;
                    if(type == "people") {
                        random = (int) (Math.random() * 30);
                    }
                    if(type == "temp") {
                        random = (int) (Math.random() * (100 - 20 + 1) + 20);
                    }
                    if(type == "co2") {
                        random = (int)(Math.random()*(1400-500+1)+500);
                    }
                    series.getData().add(new XYChart.Data<String, Integer>(Integer.toString(10), random));

                    if (peopleChartData.size() == 0) {
                        peopleChartData.add(series);
                    }

                    chart.setData(peopleChartData);
                }
                else
                {
                    series.getData().remove(0);
                    for(int i = 0;i<9;i++)
                    {
                        XYChart.Data d =  series.getData().get(i);
                        d.setXValue(Integer.toString(i+1));
                    }
                    if(false)
                    {
                        //get datapoint from server
                    }
                    else {
                        int random = 1;
                        if(type == "people") {
                            random = (int) (Math.random() * 30);
                        }
                        if(type == "temp") {
                            random = (int) (Math.random() * (100 - 20 + 1) + 20);
                        }
                        if(type == "co2") {
                            random = (int)(Math.random()*(1400-500+1)+500);
                        }
                        series.getData().add(new XYChart.Data<String, Integer>(Integer.toString(10), random));

                    }

                }
                //rules

                if(controller.autoRules.isSelected()) {
                    if (type == "people") {
                        if (series.getData().get(9).getYValue() > 0) {
                            controller.turnAllLightsOn();
                            //controller.switchAllVentilatorsOn();
                        } else if (series.getData().get(9).getYValue() == 0) {
                            controller.turnAllLightsOff();
                            controller.switchAllVentilatorsOff();
                        }
                    }

                    if (type == "temp") {
                        if (series.getData().get(9).getYValue() > 70) {
                            controller.openAllDoors();
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Temperature is above 70 degrees");
                            alert.showAndWait();
                        } else if (series.getData().get(9).getYValue() <= 70) {
                            controller.closeAllDoors();
                        }
                    }
                    if (type == "co2") {
                        if (series.getData().get(9).getYValue() < 800) {
                            controller.roomTableView.setStyle("-fx-selection-bar: green; -fx-selection-bar-non-focused: green;");
                            controller.closeAllWindows();
                            controller.switchAllVentilatorsOff();
                        } else if (series.getData().get(9).getYValue() > 800 && series.getData().get(9).getYValue() < 1000) {
                            controller.roomTableView.setStyle("-fx-selection-bar: yellow; -fx-selection-bar-non-focused: yellow;");

                            controller.closeAllWindows();
                            controller.switchAllVentilatorsOff();
                        } else {
                            controller.roomTableView.setStyle("-fx-selection-bar: red; -fx-selection-bar-non-focused: red;");

                            controller.openAllWindows();
                            controller.switchAllVentilatorsOn();
                        }
                    }
                }

            });

            try {
                // Simulate heavy processing stuff
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void  kill(){
        isActive = false;
    }
}
