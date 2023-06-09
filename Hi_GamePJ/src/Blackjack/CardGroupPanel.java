package Blackjack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class CardGroupPanel extends JPanel {
	
	// 이 클래스는 JPanel을 확장하고 여러 카드의 이미지를 표시하는 패널을 만듭니다.
	// 서로 옆에 있는 CardGroup의 인스턴스에 저장됩니다. 또한 카드 합계는 표준 Ace 빼기 규칙을 사용하여 표시됩니다.
	
	CardGroupPanel(CardGroup cardGroup, int left, int top, int width, int height, int gap) {

		int numCards = cardGroup.cards.size();

		setBounds(left, top, 35 + numCards * (width + gap), height);
		setLayout(null);
		setOpaque(false); // for transparent background

		int total = cardGroup.getTotalValue();
		if (total > 21 && cardGroup.getNumAces() > 0)
			total -= 10;

		JLabel playerScoreLbl = new JLabel((total == 21 ? "BJ" : total) + "");
		playerScoreLbl.setForeground(Color.WHITE);
		playerScoreLbl.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		playerScoreLbl.setVerticalAlignment(SwingConstants.CENTER);
		playerScoreLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		playerScoreLbl.setBounds(0, 0, 30, height);
		add(playerScoreLbl);

		for (int i = 0; i < numCards; i++) {
			ImagePanel cardImagePanel = new ImagePanel(cardGroup.cards.get(i).getFileName());
			cardImagePanel.setBounds(35 + i * (width + gap), 0, width, height);
			add(cardImagePanel);
		}
	}
}