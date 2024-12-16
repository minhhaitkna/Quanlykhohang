package views;

import models.charts.BlankPlotChart;
import models.charts.BlankPlotChatRender;
import models.charts.SeriesSize;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import models.charts.Chart;
import models.charts.Legend;




public class ChartView extends javax.swing.JPanel {

    private List<Legend> legends = new ArrayList<>();
    private List<Chart> model = new ArrayList<>();
    private final int seriesSize = 12;
    private final int seriesSpace = 6;

    public ChartView() {
        initComponents();
        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                double x = (size.getWidth() - totalSeriesWidth) / 2;
                for (int i = 0; i < legends.size(); i++) {
                    Legend legend = legends.get(i);
                    g2.setColor(legend.getColor());
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight());
                    g2.fillRect((int) (size.getX() + x), (int) (size.getY() + size.getHeight() - seriesValues), seriesSize, (int) seriesValues);
                    x += seriesSpace + seriesSize;
                }
            }
        });
        initializeChartData();
    }

    private void initializeChartData() {
        addLegend("Hóa đơn", new Color(0, 192, 239));
        addLegend("Doanh thu", new Color(221, 75, 57));
        addLegend("Nhân viên", new Color(0, 166, 90));
        addLegend("Khách hàng", new Color(243, 156, 18));
        addData(new Chart("Tháng 1", new double[]{500, 200, 80, 89}));
        addData(new Chart("Tháng 2", new double[]{600, 750, 90, 150}));
        addData(new Chart("Tháng 3", new double[]{900, 350, 120, 200}));
        addData(new Chart("Tháng 4", new double[]{700, 150, 150, 480}));
        addData(new Chart("Tháng 5", new double[]{350, 540, 200, 150}));
        addData(new Chart("Tháng 6", new double[]{350, 280, 180, 200}));
        addData(new Chart("Tháng 7", new double[]{380, 300, 120, 200}));
        addData(new Chart("Tháng 8", new double[]{350, 540, 250, 200}));
    }

    public void addLegend(String name, Color color) {
        Legend data = new Legend(name, color);
        legends.add(data);
        panelLegend.add(new LegendItem(data));
        panelLegend.repaint();
        panelLegend.revalidate();
    }

    public void addData(Chart data) {
        model.add(data);
        blankPlotChart.setLabelCount(model.size());
        double max = data.getMaxValues();
        if (max > blankPlotChart.getMaxValues()) {
            blankPlotChart.setMaxValues(max);
        }
    }

    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        blankPlotChart = new BlankPlotChart();
        panelLegend = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        panelLegend.setOpaque(false);
        panelLegend.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                    .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }

    

   
    private BlankPlotChart blankPlotChart;
    private javax.swing.JPanel panelLegend;
    
}
