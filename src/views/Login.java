package views;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Login extends javax.swing.JFrame {

  
    public Login() {
        initComponents();
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        LoginBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setForeground(new Color(255, 165, 0));
        jButton2 = new javax.swing.JButton();
        jButton2.setBackground(new Color(255, 248, 220));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new Color(255, 248, 220));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        
        jLabel6.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new Color(255, 165, 0));
        jLabel6.setText("QUAN LY BAN HANG");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Created by Nhom6");
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setForeground(SystemColor.textHighlight);
        lblNewLabel.setBackground(SystemColor.inactiveCaptionBorder);
        lblNewLabel.setIcon(new ImageIcon("D:\\workspace\\baitap1\\src\\resources\\image\\Remove-bg.ai_1732132749254.png"));
        
        JLabel lblNewLabel_1 = new JLabel(" Created by Nhom6");
        lblNewLabel_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
        lblNewLabel_1.setForeground(new Color(255, 165, 0));

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        RightLayout.setHorizontalGroup(
        	RightLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(RightLayout.createSequentialGroup()
        			.addGroup(RightLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(RightLayout.createSequentialGroup()
        					.addGap(145)
        					.addComponent(jLabel5))
        				.addGroup(RightLayout.createSequentialGroup()
        					.addGap(147)
        					.addComponent(jLabel7))
        				.addGroup(RightLayout.createSequentialGroup()
        					.addGap(84)
        					.addComponent(jLabel6))
        				.addGroup(RightLayout.createSequentialGroup()
        					.addGap(124)
        					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(48, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, RightLayout.createSequentialGroup()
        			.addContainerGap(135, Short.MAX_VALUE)
        			.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        			.addGap(115))
        );
        RightLayout.setVerticalGroup(
        	RightLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(RightLayout.createSequentialGroup()
        			.addGap(51)
        			.addComponent(lblNewLabel)
        			.addGap(47)
        			.addComponent(jLabel6)
        			.addGap(68)
        			.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        			.addGap(112)
        			.addComponent(jLabel5)
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        			.addGap(90))
        );
        Right.setLayout(RightLayout);

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new Color(255, 165, 0));
        jLabel1.setText("LOGIN");

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Email");

        email.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        email.setForeground(new java.awt.Color(102, 102, 102));

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mật Khẩu");

        LoginBtn.setBackground(new Color(255, 248, 220));
        LoginBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LoginBtn.setForeground(new Color(255, 165, 0));
        LoginBtn.setText("Đăng nhập");
        LoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBtnActionPerformed(evt);
            }
        });

        jLabel4.setText("Tôi không có tài khoản");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new Color(255, 165, 0));
        jButton2.setText("Đăng ký");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        LeftLayout.setHorizontalGroup(
        	LeftLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(LeftLayout.createSequentialGroup()
        			.addGroup(LeftLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(LeftLayout.createSequentialGroup()
        					.addGap(138)
        					.addComponent(jLabel1))
        				.addGroup(LeftLayout.createSequentialGroup()
        					.addGap(30)
        					.addGroup(LeftLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(LeftLayout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(jLabel2)
        							.addComponent(email)
        							.addComponent(jLabel3)
        							.addComponent(password, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
        						.addComponent(LoginBtn)
        						.addGroup(LeftLayout.createSequentialGroup()
        							.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
        							.addGap(47)
        							.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap(27, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
        	LeftLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(LeftLayout.createSequentialGroup()
        			.addGap(51)
        			.addComponent(jLabel1)
        			.addGap(40)
        			.addComponent(jLabel2)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(email, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(jLabel3)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(password, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(26)
        			.addComponent(LoginBtn, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
        			.addGap(33)
        			.addGroup(LeftLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton2)
        				.addComponent(jLabel4))
        			.addContainerGap(79, Short.MAX_VALUE))
        );
        Left.setLayout(LeftLayout);

        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        
        SignUp SignUpFrame = new SignUp();
        SignUpFrame.setVisible(true);
        SignUpFrame.pack();
        SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();
    }

    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {
       
        String Email, Password, query, fname = null, passDb = null;
        String SUrl, SUser, SPass;
        SUrl = "jdbc:MySQL://localhost:3306/java_user_database";
        SUser = "root";
        SPass = "";
        int notFound = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            if("".equals(email.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Địa chỉ Email không hợp lệ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else if("".equals(password.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Mật khẩu không hợp lệ", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else {
            Email    = email.getText();
            Password = password.getText();
            
            query = "SELECT * FROM user WHERE email= '"+Email+"'";
       
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                passDb = rs.getString("password");
                fname = rs.getString("full_name");
                notFound = 1;
            }
            if(notFound == 1 && Password.equals(passDb)){
                HomeView HomeFrame = new HomeView();
                HomeFrame.setVisible(true);
                HomeFrame.pack();
                HomeFrame.setLocationRelativeTo(null); 
                this.dispose();
            }else{
               JOptionPane.showMessageDialog(new JFrame(), "Email hoặc mật khẩu không đúng", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            password.setText("");
            
            }
        }catch(Exception e){
           System.out.println("Error!" + e.getMessage()); 
        }
        
    }
    public static void main(String[] args) {

        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
    }

    
    


    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JButton LoginBtn;
    private javax.swing.JPanel Right;
    private javax.swing.JTextField email;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField password;
}
