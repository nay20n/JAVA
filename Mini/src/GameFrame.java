import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameFrame extends JFrame {
  
	private JMenuItem startItem = new JMenuItem("Start");
	private JMenuItem stopItem = new JMenuItem("Stop");
	private JMenuItem backItem = new JMenuItem("Back");

	private JButton startBtn = new JButton("Start");
	private JButton stopBtn = new JButton("Stop");
        
	
    private ScorePanel scorePanel = new ScorePanel();
    private GamePanel gamePanel = new GamePanel(scorePanel);

    public GameFrame() {
    	setTitle("게임 화면");
        setSize(800, 600);
        setLocationRelativeTo(null); // 화면 가운데 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        makeMenuBar();
        makeSplit(); 
        
        setVisible(true);
    }

    private void makeSplit() {
        JSplitPane hPane = new JSplitPane();
        hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        hPane.setDividerLocation(560);
        hPane.setEnabled(false);
        
        hPane.setLeftComponent(gamePanel);
        hPane.setRightComponent(scorePanel);

        add(hPane, BorderLayout.CENTER);
    }


    private void makeMenuBar() {
    	JMenuBar menuBar = new JMenuBar();
    	setJMenuBar(menuBar);
    	
        JMenu fileMenu = new JMenu("Game");
        menuBar.add(fileMenu);

        startItem.addActionListener(new StartAction());
        stopItem.addActionListener(new StopAction());
        backItem.addActionListener(new BackAction());
        
        fileMenu.add(startItem);
        fileMenu.add(stopItem);        
        fileMenu.addSeparator();
        fileMenu.add(backItem);
               
    }

    private class StartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gamePanel.startGame();
        }
    }
    
    private class StopAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		
		}
    }
    private class BackAction implements ActionListener{
    	 @Override
    	    public void actionPerformed(ActionEvent e) {
    	        int result = JOptionPane.showConfirmDialog(
    	                GameFrame.this, 
    	                "정말 게임에서 나가시겠습니까?", 
    	                "게임 종료 확인",
    	                JOptionPane.YES_NO_OPTION
    	        );
    	        if (result == JOptionPane.YES_OPTION) {
    	            dispose();
    	            new StartFrame();
    	        }
    	 }
    }

}
