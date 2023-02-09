package at.jku;

import javafx.scene.CacheHint;
import javafx.scene.chart.LineChart;

public class ChartUpdateProcess {
    public LineChart lineChart;
    private ChartUpdateTask task;
    Controller c;
    String type;
    public ChartUpdateProcess(LineChart peopleChart,Controller c, String type) {
        this.lineChart = peopleChart;
        this.c = c;
        this.type = type;
        task = new ChartUpdateTask(lineChart,c,type);

    }






    public void start(){
        task.start();
    }

    public void stop(){
        task.kill();
    }
}
