import java.awt.*;
import java.io.*;

import javax.swing.*;
import java.util.*;
import java.util.List;
public class TextSource {
	private JPanel containerPanel = new JPanel();
    private List<String> v = new ArrayList<>();  
    private String[] IMAGES = { 
    	"cake.png",
        "zombie.png",
        "skeleton.png"
    };
    private static final String FILE_PATH = "words.txt"; 

    public TextSource() {
    	loadFromFile();
    	 for (String word : v) {
             containerPanel.add(createLabel(word));
         }
    }

    public JLabel getLabelWithImage() {
        String word = get();  
        String randomImage = IMAGES[(int)(Math.random() * IMAGES.length)]; 

        ImageIcon imageIcon = loadImage(randomImage);

        JLabel label = new JLabel(word, imageIcon, JLabel.CENTER);
        label.setSize(110, 130); 
        label.setForeground(Color.WHITE); 

        label.setVerticalTextPosition(JLabel.BOTTOM);  
        label.setHorizontalTextPosition(JLabel.CENTER);  

        return label;
    }
    public JLabel createLabel(String word) {
        String randomImage = IMAGES[(int)(Math.random() * IMAGES.length)];
        ImageIcon imageIcon = loadImage(randomImage);
        

        JLabel label = new JLabel(word, imageIcon, JLabel.CENTER);
        label.setPreferredSize(new Dimension(120, 120));
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);

        return label;
    }

    public String get() {
        int index = (int)(Math.random() * v.size());
        return v.get(index);
    }

    public boolean add(String word) {
        if (v.contains(word)) {
            return false; 
        }
        v.add(word); 
        saveToFile(); 
        return true; 
    }
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String word : v) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("단어 저장 실패: " + e.getMessage());
        }
    }
    
    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                v.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("단어 불러오기 실패: " + e.getMessage());
        }
    }


    private ImageIcon loadImage(String imagePath) {
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("이미지 로드 실패: " + imagePath);
            }
            return imageIcon;
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
            return new ImageIcon("default.png"); 
        }
    }
}
