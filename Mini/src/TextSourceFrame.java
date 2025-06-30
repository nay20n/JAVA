import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextSourceFrame extends JFrame {
    private TextSource textSource = new TextSource(); // 단어벡터 생성
    private StartFrame startFrame;
    private ScorePanel scorePanel;

    public TextSourceFrame(TextSource textSource, StartFrame startFrame) {
        this.textSource = textSource;
        this.startFrame = startFrame;

        setTitle("단어 설정");
        setSize(300, 300);
        setLocationRelativeTo(null); // 화면 가운데 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(new TextSourcePanel());

        setVisible(true);
    }


    private class TextSourcePanel extends JPanel {
        private ImageIcon backImage = new ImageIcon("setWordBack.jpg");
        private JButton addButton = new JButton("추가");
        private JButton backButton = new JButton("뒤로");
        private JTextField textField = new JTextField(15);

        public TextSourcePanel() {
            setLayout(new FlowLayout());

            add(new JLabel("단어를 입력하세요:"));
            add(textField);
            add(addButton);
            add(backButton);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word = textField.getText().trim();
                    if (word.isEmpty()) {
                        JOptionPane.showMessageDialog(TextSourceFrame.this, "단어를 입력하세요.");
                        return;
                    }
                    if (textSource.add(word)) {
                        JOptionPane.showMessageDialog(TextSourceFrame.this, word + " 추가 완료!");
                    } else {
                        JOptionPane.showMessageDialog(TextSourceFrame.this, "기존에 있는 단어입니다.");
                    }

                    textField.setText("");
                }
            });

            // 텍스트 필드 엔터 입력 시 단어 추가
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word = textField.getText().trim();
                    if (word.isEmpty()) {
                        JOptionPane.showMessageDialog(TextSourceFrame.this, "단어를 입력하세요.");
                        return;
                    }
                    if (textSource.add(word)) {
                        JOptionPane.showMessageDialog(TextSourceFrame.this, word + " 추가 완료!");
                    } else {
                        JOptionPane.showMessageDialog(TextSourceFrame.this, "기존에 있는 단어입니다.");
                    }

                    textField.setText("");
                }
            });

            // 뒤로 버튼
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose(); // 현재 TextSourceFrame 닫기
                    startFrame.setVisible(true); // StartFrame 다시 표시
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backImage != null)
                g.drawImage(backImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
