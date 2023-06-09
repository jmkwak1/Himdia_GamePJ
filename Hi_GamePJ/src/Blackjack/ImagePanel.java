package Blackjack;
import java.awt.*;

import javax.swing.*;

// 파일 이름이 지정되면 해당 경계에 맞게 확장된 이미지가 있는 JPanel을 반환하는 클래스입니다.

class ImagePanel extends JPanel {
	private Image img;

	public ImagePanel(String imgStr) { // 생성자, 전달된 이미지 문자열
		this.img = new ImageIcon(imgStr).getImage();
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null)); // setBounds를 사용하지 않으면(인스턴스가 생성된 후) 이는 이미지의 실제 크기로 대체됩니다.
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) { // Draw the image to the JPanel
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}