import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
	
    private ImageIcon scoreImage = new ImageIcon("scorePanelBack.jpg");
    private ImageIcon heartImage = new ImageIcon("heart.png");
    private int score = 0;
    private JLabel scoreLabel = new JLabel(Integer.toString(score));
    private JLabel[] heart = new JLabel[3];  
    private int remainingHearts = 3;

    public ScorePanel() {
        setLayout(null); 

        JLabel scoreText = new JLabel("점수 :");
        int textWidth = scoreText.getPreferredSize().width;
        scoreText.setBounds(80, 20, 100, 30);
        scoreText.setFont(new Font("Gothic", Font.BOLD, 15)); 
        this.add(scoreText);

        scoreLabel.setBounds(95 + textWidth, 20, 100, 30);
        scoreLabel.setFont(new Font("Gothic", Font.BOLD, 15)); 
        this.add(scoreLabel);

        for (int i = 0; i < heart.length; i++) {
            heart[i] = new JLabel(heartImage);
            heart[i].setBounds(20 + (i * 60), 80, 50, 50);  
            this.add(heart[i]);
        }
    }
  

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scoreImage != null) {
            g.drawImage(scoreImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    public JLabel getscoreLabel() {
        return scoreLabel;
    }

    public void increase() {
        score += 10;
        scoreLabel.setText(Integer.toString(score));
    }
    public void decrease() {
        score -= 5;
        scoreLabel.setText(Integer.toString(score));
    }
    public int removeHeart() {
        if (remainingHearts > 0) {
            heart[remainingHearts - 1].setVisible(false);  
            remainingHearts--; 
        }
        return remainingHearts;
    }
    public void addHeart() {
        if (canAddHeart()) {
            heart[remainingHearts].setVisible(true);
            remainingHearts++;
            return;
        }
        else return;
    }
    public void restoreHearts() {
        for (int i = 0; i < heart.length; i++) {
            heart[i].setVisible(true); 
        }
        remainingHearts = 3;
    }
    public boolean canAddHeart() {
        return remainingHearts < heart.length;
    }
    
    public int getFinalScore() {
        return score;
    }
    public void resetScore() {
        score = 0;
        scoreLabel.setText(Integer.toString(score));
    }

    
}
