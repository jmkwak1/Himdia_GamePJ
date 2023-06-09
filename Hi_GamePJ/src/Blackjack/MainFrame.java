package Blackjack;
import java.awt.*;
import javax.swing.*;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.application.Platform;

public class MainFrame extends JFrame {
	
	//기본 창은 배경 이미지를 표시합니다(주어진 창 크기에 맞게 이미지가 잘 늘어나도록 동적 경계에 유의하십시오).

	public MainFrame() {
		setTitle("Blackjack");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		
		ImagePanel bgImagePanel = new ImagePanel("src/Blackjack/background.png");
		bgImagePanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		setContentPane(bgImagePanel);
	}

}
