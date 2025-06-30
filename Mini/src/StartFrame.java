import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {

    public StartFrame() {
        setTitle("게임 시작 화면");
        setSize(800, 449);
        setLocationRelativeTo(null);  // 화면 가운데 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new StartPanel());
        setVisible(true);
    }

    private class StartPanel extends JPanel {
        private ImageIcon backImage = new ImageIcon("startback.jpg");     
        private Image scaledImage;
        private int prevWidth = -1, prevHeight = -1;

        public StartPanel() {
            setLayout(null);
            setOpaque(false);
            
            JLabel label = new JLabel("좀비를 피해 생존하라!");
            label.setSize(360, 80);
            label.setLocation(240, 130);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Gothic", Font.BOLD, 18));
            add(label);

            JButton startBtn = createBtn("GAME START", "stone.jpg", 210, 188);
            startBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switchToMainGame(); // 메인 게임 화면으로 이동
                }
            });
            add(startBtn);

            JButton setWordBtn = createBtn("단어 설정", null, 210, 230);
            setWordBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switchToWordSetting(); // 단어 설정 화면으로 이동
                }
            });
            add(setWordBtn);

            JButton rankingBtn = createBtn("랭킹 보기", null, 210, 272);
            rankingBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(StartFrame.this, "랭킹 보기 기능은 준비 중입니다!");
                }
            });
            add(rankingBtn);
            
            JButton quitBtn = createBtn("Ouit Game",null,210,335);
            quitBtn.addActionListener(new ActionListener() {
            	 public void actionPerformed(ActionEvent e) {
                     int result = JOptionPane.showConfirmDialog(
                             null, 
                             "정말 게임을 종료하시겠습니까?", 
                             "게임 종료", 
                             JOptionPane.YES_NO_OPTION
                     );
                     if (result == JOptionPane.YES_OPTION) {
                         System.exit(0); 
                     }
                 }            	
            });
            add(quitBtn);;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backImage != null) {
                int currentWidth = getWidth();
                int currentHeight = getHeight();

                if (currentWidth != prevWidth || currentHeight != prevHeight) {
                    scaledImage = backImage.getImage().getScaledInstance(currentWidth, currentHeight, Image.SCALE_SMOOTH);
                    prevWidth = currentWidth;
                    prevHeight = currentHeight;
                }

                if (scaledImage != null) {
                    g.drawImage(scaledImage, 0, 0, this);
                }
            }
        }
        private JButton createBtn(String text, String imagePath, int x, int y) {
        	JButton button;
            if (imagePath != null) {
                ImageIcon icon = new ImageIcon(imagePath);
                button = new JButton(text, icon);
            } else {
                button = new JButton(text);
            }
            button.setHorizontalTextPosition(SwingConstants.CENTER); 
            button.setVerticalTextPosition(SwingConstants.CENTER);  
            button.setFont(new Font("Gothic", Font.BOLD, 16));         
            button.setForeground(Color.WHITE);                       
            button.setBackground(Color.GRAY);                       
            button.setOpaque(true);
            button.setBounds(x, y, 366, 30);                         
            return button;
        }
    }

    private void switchToMainGame() {
        this.dispose(); 
        new GameFrame(); 
    }

    private void switchToWordSetting() {
        this.dispose(); 
        new TextSourceFrame(new TextSource(), this); 
    }

    public static void main(String[] args) {
        new StartFrame();
    }
}
