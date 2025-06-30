import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel {
	private String level ="Level: ";
	private int levelNum = 1;
    private TextSource textSource = new TextSource(); 
    private ScorePanel scorePanel = new ScorePanel();
    private GameGroundPanel1 ground = new GameGroundPanel1();
    private JTextField textField = new JTextField(10); 
   
    private List<Point> fallingPositions = new ArrayList<>(); 
    private List<JLabel> fallingLabels = new ArrayList<>();
    private List<FallingThread> fallingThreads = new ArrayList<>();
    private Timer gameTimer; // 게임 진행 타이머 추가
    private boolean gameOverFlag = false; 

    public GamePanel(ScorePanel scorePanel ) {
        this.scorePanel = scorePanel;
        this.setLayout(new BorderLayout());
        
        add(scorePanel, BorderLayout.NORTH);
        add(ground, BorderLayout.CENTER);
        add(textField,BorderLayout.SOUTH);
        
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameOverFlag) return;
                
                String input = textField.getText(); 
                textField.setText("");  // 입력란 초기화
                
                boolean heartAdded = false; // 하트를 추가했는지 여부를 추적
                
                for (int i = 0; i < fallingLabels.size(); i++) {
                    JLabel label = fallingLabels.get(i);
                    if (label != null && label.getText().equals(input)) {  // 입력과 일치하는 단어 찾기
                        ImageIcon icon = (ImageIcon) label.getIcon();
                        if (icon != null && icon.getDescription().contains("cake.png") && !heartAdded) {
                            scorePanel.addHeart(); 
                            heartAdded = true; 
                        }

                        scorePanel.increase();  // 점수 증가
                        ground.removeLabel(label);  
                        fallingLabels.remove(i);  
                        fallingPositions.remove(i); 
                        ground.update(); 
                        break; 
                     }
                 }
             }
        });
    }
    
    class GameGroundPanel1 extends JPanel {   
    	private ImageIcon backImage = new ImageIcon("Game1Back.jpg");
        private Image scaledImage;
        

    	@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        if (backImage != null) {
	            int currentWidth = getWidth();
	            int currentHeight = getHeight();
	            if (scaledImage == null || scaledImage.getWidth(null) != currentWidth || scaledImage.getHeight(null) != currentHeight) {
	                scaledImage = backImage.getImage().getScaledInstance(currentWidth, currentHeight, Image.SCALE_SMOOTH);
	            }
	            g.drawImage(scaledImage, 0, 0, this);
	        }		
	    }
    	
        public GameGroundPanel1() {
        	setLayout(null);
        	JLabel levelLabel = new JLabel(level + Integer.toString(levelNum)); 
        	levelLabel.setForeground(Color.WHITE);
        	levelLabel.setFont(new Font("Gothic", Font.BOLD, 18));
        	levelLabel.setSize(100,100);
        	levelLabel.setLocation(10,10);
            add(levelLabel);
        }       
        public void addLabel(JLabel label) {
            add(label);
        }        
        public void removeLabel(JLabel label) {
            remove(label);
        }
        public void update() {
            revalidate();
            repaint();
        }
    }
    
    class FallingThread extends Thread {
    	 private boolean isRunning = true;
        private int index;
        
        public FallingThread(int index) {
            this.index = index;
        }
        
        @Override
        public void run() {
            while (isRunning) {
                try {
                	if (index >= fallingLabels.size() || index >= fallingPositions.size()) {
                         break;
                    }
                    JLabel label = fallingLabels.get(index);
                    Point position = fallingPositions.get(index);
                    //if (label == null) break; // null 체크 추가
                    position.y += 50; // 레이블 떨어지는 속도 (10픽셀씩 떨어짐)
                    label.setLocation(position.x, position.y);
                    
                    if (position.y > ground.getHeight()) {
                    	ground.removeLabel(label);
                        fallingLabels.set(index, null);
                        fallingPositions.set(index, null);
                        ground.update();
                        scorePanel.decrease();
                        gameOver();
                        break;
                    }
                    sleep(1000); // 1s마다 이동
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        public void stopThread() {
            isRunning = false; // 실행 여부를 false로 변경하여 스레드를 종료
        }
       
    }
    
    public void gameOver() {
    	int remainingHearts = scorePanel.removeHeart();
    	 if (remainingHearts <= 0) {
    		 gameOverFlag  = true;
    		 for (FallingThread thread : fallingThreads) {
    		        thread.stopThread();
    		 }
    		 if (gameTimer != null) {
                 gameTimer.stop();
             }
    		 int finalScore = scorePanel.getFinalScore();
    		 GameFrame c =(GameFrame) this.getTopLevelAncestor();
    		 //System.out.print(c);
    		 if (c != null) {
    	            c.dispose();  
    	        }
    	     new GameOverFrame(finalScore);  	        
    	 }
    }
    
  
    public void startGame() {
    	if (gameOverFlag) return;
    	
    	gameTimer  = new Timer(5000, new ActionListener() { // 5초마다 새로운 단어 생성
            @Override
            public void actionPerformed(ActionEvent e) {
                newWord();
            }
        });
    	gameTimer.start();
    }
    
     
    public void stopGame() {
    	
    }
    
    public void resetGame() {
    	 for (FallingThread thread : fallingThreads) {
    		 thread.interrupt();
    	 }
    	 fallingThreads.clear();
    	 fallingLabels.clear();
    	 fallingPositions.clear();
    	 
    	 scorePanel.resetScore();
    	 scorePanel.restoreHearts();
    	 
    	 gameOverFlag = false;
    	 startGame();
    }
    
 
    public void newWord() {
        JLabel newLabel = textSource.getLabelWithImage();
        int x = (int) (Math.random() * (ground.getWidth() - 100));
        newLabel.setLocation(x, 0);
        

        
        ground.addLabel(newLabel);
        fallingLabels.add(newLabel);
        fallingPositions.add(new Point(x, 0));

        int index = fallingLabels.size() - 1;
        FallingThread thread = new FallingThread(index);
        fallingThreads.add(thread);
        thread.start();
    }
}