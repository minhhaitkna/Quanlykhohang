package controllers;

import dao.StatisticsDao;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import utils.Debouncer;
import utils.IntervalIncrease;
import views.RevenueStatisticsView;


public class StatisticsController {

    RevenueStatisticsView view;
    private static StatisticsDao statisticsDao;

    static {
        try {
            statisticsDao = new StatisticsDao();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể khởi tạo StatisticsDao.", e);
        }
    }    DecimalFormat formatter = new DecimalFormat("###,###,###");
    Debouncer debouncer = new Debouncer();
    RevenueGraphController revenueGraphController = new RevenueGraphController();

    public StatisticsController() {
    }

   

    public RevenueStatisticsView getView() {
        return view;
    }
    
    public void setView(RevenueStatisticsView view) {
        if (view != this.view) {
            this.view = view;
            addEvent();
        }
    }

    public void addEvent() {
        Runnable onDateChange = () -> {
            Date startDate = view.getDateChooserStart().getDate();
            Date endDate = view.getDateChooserEnd().getDate();
            if (startDate.after(endDate)) {
                view.showError("Ngày bắt đầu không thể sau ngày kết thúc");
                return;
            }
            try {
                renderData(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
            } catch (SQLException ex) {
                view.showError(ex);
            }

        };
        PropertyChangeListener eventListener = evt -> debouncer.debounce("date_change", onDateChange, 1000);//Chờ 3s không thay đổi mới hiển thị data

        view.getDateChooserStart().addPropertyChangeListener(eventListener);
        view.getDateChooserEnd().addPropertyChangeListener(eventListener);
    }

    public void initData() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        view.getDateChooserEnd() ;
        c.add(Calendar.DATE, -30);
        view.getDateChooserStart();
    }

    public void renderData(Timestamp start, Timestamp end) throws SQLException {
        int totalIncome = statisticsDao.getTotalIncome(start, end);
        int totalOrder = statisticsDao.getTotalOrder(start, end);

        view.getLbTotalEmployee().setText(statisticsDao.getTotalEmployee() + "");
        view.getLbTotalCustomer().setText(statisticsDao.getTotalCustomer() + "");
        IntervalIncrease.create(totalIncome, 1500, 25, (i) -> {
            view.getLbTotalIncome().setText(formatter.format(i));
        });
        renderChart(start, end);
    }

    public void renderChart(Timestamp start, Timestamp end) throws SQLException {
        revenueGraphController.show(view.getPnlContent(), start, end);
    }

}
