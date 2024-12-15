package views;

import javax.swing.*;
import java.awt.*;

public class SettingView extends JPanel {

    public SettingView() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 204)); // Màu nền vàng nhạt

        JLabel titleLabel = new JLabel("Nhóm 6", SwingConstants.CENTER);
        titleLabel.setBackground(SystemColor.info);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.RED);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setBackground(SystemColor.info); // Cùng màu với nền chính
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Tạo khoảng cách xung quanh

        
        add(titleLabel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.CENTER);
        settingsPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Đề tài : Quản lý kho hàng ");
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(50, 20, 312, 33);
        settingsPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Tên thành viên :");
        lblNewLabel_1.setForeground(new Color(255, 0, 0));
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(50, 63, 216, 33);
        settingsPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Hoàng Minh Hải - 20235072");
        lblNewLabel_2.setForeground(new Color(255, 0, 0));
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(50, 116, 263, 42);
        settingsPanel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Nguyễn Duy Mạnh - 20235374");
        lblNewLabel_3.setForeground(new Color(255, 0, 0));
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_3.setBounds(50, 153, 312, 33);
        settingsPanel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Trần Bá Dân - 20235291");
        lblNewLabel_4.setForeground(new Color(255, 0, 0));
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_4.setBounds(50, 185, 287, 33);
        settingsPanel.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Nguyễn Hữu Hiệp - 20207603");
        lblNewLabel_5.setForeground(new Color(255, 0, 0));
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_5.setBounds(50, 216, 287, 33);
        settingsPanel.add(lblNewLabel_5);
    }

    private JPanel createSettingItem(String text, String iconPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 255, 204));
        panel.setMaximumSize(new Dimension(800, 50)); // Kích thước tối đa
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách xung quanh

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        }

        panel.add(label, BorderLayout.WEST);
        return panel;
    }
    
    public JPanel getContentPanel() {
        return this; 
    }

   
}
