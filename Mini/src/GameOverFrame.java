import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverFrame extends JFrame {
    private ImageIcon backImage = new ImageIcon("GameOverBack.jpg");
    private ScorePanel scorePanel = new ScorePanel();
    private GameFrame gameFrame; 

    public GameOverFrame(int finalScore) {
        
        setTitle("게임 오버");
        setSize(800, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel gameOverLabel = new JLabel("게임 오버");
        gameOverLabel.setSize(360, 80); 
        gameOverLabel.setLocation(220, 50);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Gothic", Font.BOLD, 18));
        add(gameOverLabel);

        JLabel scoreLabel = new JLabel("최종 점수: " + finalScore);
        scoreLabel.setSize(360, 80);
        scoreLabel.setLocation(220, 120);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Gothic", Font.BOLD, 18));
        add(scoreLabel);
        
        JButton restartBtn = createBtn("게임 재시작", 230, 200);
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();  
                dispose();  
            }
        });
        add(restartBtn);
        
        JButton startBtn = createBtn("게임 홈으로 돌아가기", 230, 238); 
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToHome();  
                dispose();  
            }
        });
        add(startBtn);

        setVisible(true);  
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backImage != null) {
            g.drawImage(backImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JButton createBtn(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setHorizontalTextPosition(SwingConstants.CENTER); 
        button.setVerticalTextPosition(SwingConstants.CENTER);  
        button.setFont(new Font("Gothic", Font.BOLD, 16));         
        button.setForeground(Color.WHITE);                       
        button.setBackground(Color.GRAY);                       
        button.setOpaque(true);
        button.setBounds(x, y, 327, 30);                         
        return button;
    }

    private void restartGame() {
        if (gameFrame != null) {
            gameFrame.dispose();  
        }
        GamePanel gamePanel = new GamePanel(scorePanel);  
        gamePanel.resetGame();  
        new GameFrame(); 
    }

    private void goToHome() {
        if (gameFrame != null) {
            gameFrame.dispose();  
        }
        GamePanel gamePanel = new GamePanel(scorePanel);  
        gamePanel.resetGame(); 
        new StartFrame();  
    }
}
