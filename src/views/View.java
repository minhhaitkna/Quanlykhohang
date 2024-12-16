package views;

import javax.swing.*;
import java.sql.SQLException;

public abstract class View<T> {

    protected abstract void loadData() throws SQLException;

    protected abstract void addEntity();

    protected abstract void editEntity();

    protected abstract void deleteEntity();

    protected abstract JButton createButtonWithIcon(String text, String iconPath);

    public abstract JPanel getContentPanel();
}

