package Blackjack;

import javax.swing.*;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import main.Login;
import main.MemberDAO;

public class Blackjack {
	private static JFrame frame = new MainFrame(); // MainFrame 클래스의 인스턴스 생성.

	private static CardGroup deck, dealerCards, playerCards; //변수 선언:
	private static CardGroupPanel dealerCardPanel = null, playerCardPanel = null; // 카드 덱, 딜러 카드, 플레이어 카드, 플레이어 및 딜러 카드용 패널
	private static Card dealerHiddenCard; //  그리고 딜러의 히든카드

	private static double balance = 0.0; // 잔액의 초기 금액 설정
	private static int betAmount = 0, roundCount = 0; // 플레이어가 베팅한 금액과 라운드 수

	// 윈도우 빌더에서 GUI 요소 생성
	private static JTextField tfBalance;
	private static JLabel lblInitialBalance;
	private static JButton btnNewGame;
	private static JButton btnEndGame;
	private static JTextField tfBetAmount;
	private static JLabel lblEnterBet;
	private static JButton btnDeal;
	private static JLabel lblCurrentBalance;
	private static JLabel lblBalanceAmount;
	private static JLabel lblDealer;
	private static JLabel lblPlayer;
	private static JButton btnHit;
	private static JButton btnStand;
	private static JLabel lblBetAmount;
	private static JLabel lblBetAmountDesc;
	private static JLabel lblInfo;
	private static JButton btnContinue;
	private static JLabel lblShuffleInfo = null;

	public static boolean isValidAmount(String s) { // 이는 초기 잔액과 플레이어의 내기에 입력한 값이 자연수임을 확인하기 위한 것입니다.
		try {
			if (Integer.parseInt(s) > 0) // 입력한 금액이 0보다 큰지 확인
				return true;
			else
				return false;
		} catch (NumberFormatException e) { // 정수가 아닌 경우
			return false;
		}
	}

	// 이 기능은 프로그램이 시작되거나 게임이 종료될 때 실행됩니다. 초기 잔액을 입력하고 게임을 시작/중지하기 위한 초기 GUI 개체를 표시합니다.
	public static void initGuiObjects() {
		btnNewGame = new JButton("게임 시작"); // 새로운 게임 버튼
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(); // 게임 시작
			}
		});
		btnNewGame.setBounds(20, 610, 99, 50);
		frame.getContentPane().add(btnNewGame);

		btnEndGame = new JButton("종료"); // 게임 종료 버튼, 모든 GUI 개체를 제거하고 처음부터 시작합니다.
		btnEndGame.setEnabled(false);
		btnEndGame.setBounds(121, 610, 99, 50);
		btnEndGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll(); // 화면에서 모든 개체 제거
				frame.repaint(); // 업데이트를 표시하도록 다시 그리기
				initGuiObjects(); // 게임 로직을 다시 시작하고 New Game 메뉴를 표시합니다.
			}
		});
		frame.getContentPane().add(btnEndGame);

		tfBalance = new JTextField(); // 초기 잔액을 저장하는 텍스트 필드
		tfBalance.setText(Login.getGold());
		tfBalance.setBounds(131, 580, 89, 28);
		frame.getContentPane().add(tfBalance);
		tfBalance.setColumns(10);

		lblInitialBalance = new JLabel(" 소지 금액 : "); // 초기 잔액 레이블
		lblInitialBalance.setFont(new Font("굴림", Font.BOLD, 13));
		lblInitialBalance.setForeground(Color.WHITE);
		lblInitialBalance.setBounds(30, 586, 100, 18);
		frame.getContentPane().add(lblInitialBalance);
	}

	public static void showBetGui() { // 새 게임이 시작될 때 실행됩니다. 현재 잔액 레이블, 거래 금액 및 거래 버튼을 초기화하고 표시합니다.

		btnEndGame.setEnabled(true);

		lblCurrentBalance = new JLabel("현재 금액:"); // 현재 잔액 레이블
		lblCurrentBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentBalance.setFont(new Font("굴림", Font.BOLD, 16));
		lblCurrentBalance.setForeground(Color.WHITE);
		lblCurrentBalance.setBounds(315, 578, 272, 22);
		frame.getContentPane().add(lblCurrentBalance);

		lblBalanceAmount = new JLabel(); // 잔액 레이블, 현재 잔액 표시
		lblBalanceAmount.setText(String.format("$%.2f", balance));
		lblBalanceAmount.setForeground(Color.ORANGE);
		lblBalanceAmount.setFont(new Font("굴림", Font.BOLD, 40));
		lblBalanceAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalanceAmount.setBounds(315, 600, 272, 50);
		frame.getContentPane().add(lblBalanceAmount);

		lblInfo = new JLabel("베팅금액을 입력하고 베팅을 클릭하세요"); //
		lblInfo.setBackground(Color.ORANGE);
		lblInfo.setOpaque(false);
		lblInfo.setForeground(Color.ORANGE);
		lblInfo.setFont(new Font("굴림", Font.BOLD, 16));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(290, 482, 320, 28);
		frame.getContentPane().add(lblInfo);

		tfBetAmount = new JTextField(); // 베팅 금액 텍스트 필드
		tfBetAmount.setText("10");
		tfBetAmount.setBounds(790, 580, 89, 28);
		frame.getContentPane().add(tfBetAmount);

		lblEnterBet = new JLabel("베팅 금액:"); // 베팅 금액 정보 라벨
		lblEnterBet.setFont(new Font("굴림", Font.BOLD, 14));
		lblEnterBet.setForeground(Color.WHITE);
		lblEnterBet.setBounds(689, 586, 100, 16);
		frame.getContentPane().add(lblEnterBet);

		btnDeal = new JButton("베팅"); // Deal 버튼
		btnDeal.setBounds(679, 610, 200, 50);
		btnDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deal(); // When clicked, deal
			}
		});
		frame.getContentPane().add(btnDeal);
		btnDeal.requestFocus();

		frame.repaint();

	}

	public static void deal() { // 거래 버튼을 누르면 실행됩니다. 두 개의 플레이어 및 딜러 카드를 뽑고(딜러의 카드 중 하나만 표시) 플레이어에게 조치를 요청하거나 즉각적인 결과가 있는 경우(예: 즉시 블랙잭) 조치를 취합니다.

		if (lblShuffleInfo != null) // (5라운드마다 덱 재편성되고 이 레이블이 표시됩니다. 새 라운드가 시작되면 숨기기
			frame.getContentPane().remove(lblShuffleInfo);

		// 딜러/플레이어 카드 어레이 초기화
		dealerCards = new CardGroup();
		playerCards = new CardGroup();

		if (isValidAmount(tfBetAmount.getText()) == true) { // 주어진 베팅 금액 분석
			betAmount = Integer.parseInt(tfBetAmount.getText());
		} else {
			lblInfo.setText("베팅은 자연수만 가능합니다"); // 오류시 문구출력
			tfBetAmount.requestFocus();
			return;
		}

		if (betAmount > balance) { // 내기가 잔액보다 높은 경우
			lblInfo.setText("잔액보다 높게 베팅하세요!"); // 오류시 문구출력
			tfBetAmount.requestFocus();
			return;
		}
		balance -= betAmount; // 잔액에서 베팅 빼기

		lblBalanceAmount.setText(String.format("$%.2f", balance));

		tfBetAmount.setEnabled(false);
		btnDeal.setEnabled(false);

		lblInfo.setText("Hit or Stand"); // 다음 명령

		lblDealer = new JLabel("Dealer"); // Dealer label
		lblDealer.setForeground(Color.WHITE);
		lblDealer.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblDealer.setBounds(415, 158, 82, 28);
		frame.getContentPane().add(lblDealer);

		lblPlayer = new JLabel("Player"); // Player label
		lblPlayer.setForeground(Color.WHITE);
		lblPlayer.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblPlayer.setBounds(415, 266, 82, 28);
		frame.getContentPane().add(lblPlayer);

		btnHit = new JButton("Hit"); // Hit button
		btnHit.setBounds(290, 515, 140, 35);
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hit(); // When pressed, hit
			}
		});
		frame.getContentPane().add(btnHit);
		btnHit.requestFocus();

		btnStand = new JButton("Stand"); // Stand button
		btnStand.setBounds(470, 515, 140, 35);
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stand(); // When pressed, stand
			}
		});
		frame.getContentPane().add(btnStand);

		btnContinue = new JButton("확인"); // 최종 결과에 도달하면 이 버튼을 눌러 수락하고 게임을 계속합니다.
		btnContinue.setEnabled(false);
		btnContinue.setVisible(false);
		btnContinue.setBounds(290, 444, 320, 35);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptOutcome(); // Accept outcome
			}
		});
		frame.getContentPane().add(btnContinue);

		lblBetAmount = new JLabel(); // 베팅 금액 표시
		lblBetAmount.setText("$" + betAmount);
		lblBetAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetAmount.setForeground(Color.ORANGE);
		lblBetAmount.setFont(new Font("Arial", Font.BOLD, 40));
		lblBetAmount.setBounds(679, 488, 200, 50);
		frame.getContentPane().add(lblBetAmount);

		lblBetAmountDesc = new JLabel("베팅 금액:"); // 베팅 금액 정보 라벨
		lblBetAmountDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetAmountDesc.setForeground(Color.WHITE);
		lblBetAmountDesc.setFont(new Font("굴림", Font.BOLD, 16));
		lblBetAmountDesc.setBounds(689, 465, 190, 22);
		frame.getContentPane().add(lblBetAmountDesc);

		frame.repaint(); // 프레임을 다시 그려 변경 사항 표시

		dealerHiddenCard = deck.takeCard(); // 딜러를 위해 덱 맨 위에서 카드를 가져오지만 숨깁니다.
		dealerCards.cards.add(new Card("", "", 0)); // 딜러 카드추가(뒤집힌카드)
		dealerCards.cards.add(deck.takeCard()); // 덱 상단의 카드를 딜러의 카드에 추가

		// 덱 맨 위에서 두 장의 카드를 플레이어의 카드에 더합니다.
		playerCards.cards.add(deck.takeCard());
		playerCards.cards.add(deck.takeCard());

		updateCardPanels(); // 두 개의 카드 패널 표시

		simpleOutcomes(); // 자동적으로 결과 확인 (i.e. immediate blackjack)

	}

	public static void hit() { // 플레이어 카드에 다른 카드를 추가하고 새 카드를 보여주고 결과를 확인합니다.

		playerCards.cards.add(deck.takeCard());
		updateCardPanels();

		simpleOutcomes();

	}

	public static boolean simpleOutcomes() { //거래를 누르거나 플레이어가 명중할 때마다 자동으로 실행됩니다.
		boolean outcomeHasHappened = false;
		int playerScore = playerCards.getTotalValue(); // 플레이어 카드의 총합으로 플레이어 점수를 얻는다.
		if (playerScore > 21 && playerCards.getNumAces() > 0) // 플레이어가 최소한 하나의 에이스를 가지고 있지 않거나 카드 총 합이 21을 넘길 경우 패배 10을 뺍니다
			playerScore -= 10;

		if (playerScore == 21) { // 플레이어가 블랙잭인 경우

			dealerCards.cards.set(0, dealerHiddenCard); // 딜러 뒤집힌 카드 오픈
			updateCardPanels(); // 새로운 카드 표시
			if (dealerCards.getTotalValue() == 21) { // 플레이어와 딜러가 블랙잭인 경우
				lblInfo.setText("무승부!"); // 무승부처리
				balance += betAmount; // 플레이어 배팅한 금액 되돌려주기
			} else {
				// 플레이어만 블랙잭일 경우
				lblInfo.setText(String.format("플레이어 블랙잭! Win 금액: $%.2f", 1.5f * betAmount));
				balance += 2.5f * betAmount; // 베팅한 금액 * 2.5 받음
			}
			lblBalanceAmount.setText(String.format("$%.2f", balance)); // 새로운 잔액 표시

			outcomeHasHappened = true;
			outcomeHappened(); // 라운드가 끝나고 배팅금액 반올림처리 및 결과표시, 계속버튼
		} else if (playerScore > 21) { // 플레이어 Bust(숫자 합이 21이 넘을때 터짐)
			lblInfo.setText("플레이어 Bust! Loss : -$" + betAmount);
			dealerCards.cards.set(0, dealerHiddenCard); // 딜러의 히든카드를 실제카드로 교체
			updateCardPanels();
			outcomeHasHappened = true;
			outcomeHappened(); // 라운드가 끝나고 배팅금액 반올림처리 및 결과표시, 계속버튼
		}
		return outcomeHasHappened;

	}

	public static void stand() { // stand 버튼을 눌렀을 때
		if (simpleOutcomes()) // 정상적인 결과 화면일 경우 되돌아 가기
			return;

		int playerScore = playerCards.getTotalValue(); // 플레이어 카드의 총합으로 플레이어 점수를 얻습니다.
		if (playerScore > 21 && playerCards.getNumAces() > 0) // 플레이어가 최소한 하나의 에이스를 가지고 있지 않거나 카드 총 합이 21을 넘길 경우 패배 10을 뺍니다
			playerScore -= 10;

		dealerCards.cards.set(0, dealerHiddenCard); // 딜러 뒤집힌 카드 오픈

		int dealerScore = dealerCards.getTotalValue(); // 플레이어 카드의 총합으로 딜러 점수를 얻습니다.

		while (dealerScore < 16) { // 딜러의 핸드가 16보다 작을 경우 16보다 크게 될 때까지 더 많은 카드를 가져와야 합니다.
			dealerCards.cards.add(deck.takeCard()); // 덱 맨 위에서 카드를 가져와 추가
			dealerScore = dealerCards.getTotalValue();
			if (dealerScore > 21 && dealerCards.getNumAces() > 0) // 에이스가 있고 합계가 21를 넘으면 10을 뺍니다
				dealerScore -= 10;
		}
		updateCardPanels(); // 새로운 딜러의 카드 표시

		// 최종 결과를 결정하고 이익이 있을경우 수익을 부여하고 결과 표시
		if (playerScore > dealerScore) { // Player wins
			lblInfo.setText("플레이어 Victor! Win 금액 : $" + betAmount);
			balance += betAmount * 2;
			lblBalanceAmount.setText(String.format("$%.2f", balance));
		} else if (dealerScore == 21) { // Dealer blackjack
			lblInfo.setText("딜러가 블랙잭! Loss: -$" + betAmount);
		} else if (dealerScore > 21) { // Dealer bust
			lblInfo.setText("딜러 Bust! Win 금액 : $" + betAmount);
			balance += betAmount * 2;
			lblBalanceAmount.setText(String.format("$%.2f", balance));
		} else if (playerScore == dealerScore) { // Push
			lblInfo.setText("무승부!");
			balance += betAmount;
			lblBalanceAmount.setText(String.format("$%.2f", balance));
		} else { // Otherwise - dealer wins
			lblInfo.setText("딜러 Victor! Loss : -$" + betAmount);
		}
		outcomeHappened(); // 라운드가 끝나고 배팅금액 반올림처리 및 결과표시, 계속버튼

	}

	public static void outcomeHappened() { //무슨 일이 생기면 이 라운드는 끝납니다. 반올림 결과 표시 및 계속 버튼

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);

		// 정보 레이블을 주황색으로 강조 표시하고 계속 버튼 표시를 500ms 지연
		lblInfo.setOpaque(true);
		lblInfo.setForeground(Color.RED);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				btnContinue.setEnabled(true);
				btnContinue.setVisible(true);
				btnContinue.requestFocus();
			}
		}, 500);

	}

	public static void acceptOutcome() { // 결과 도달 시

		lblInfo.setOpaque(false);
		lblInfo.setForeground(Color.ORANGE);
		
		// 거래 개체 제거

		frame.getContentPane().remove(lblDealer);
		frame.getContentPane().remove(lblPlayer);
		frame.getContentPane().remove(btnHit);
		frame.getContentPane().remove(btnStand);
		frame.getContentPane().remove(lblBetAmount);
		frame.getContentPane().remove(lblBetAmountDesc);
		frame.getContentPane().remove(btnContinue);
		frame.getContentPane().remove(dealerCardPanel);
		frame.getContentPane().remove(playerCardPanel);
		lblInfo.setText("베팅을 입력하고 거래를 클릭하세요.");
		tfBetAmount.setEnabled(true);
		btnDeal.setEnabled(true);
		btnDeal.requestFocus();
		frame.repaint();

		if (balance <= 0) { // 자금이 부족하면 충전하거나 게임을 종료하십시오.
			int choice = JOptionPane.showOptionDialog(null, "자금이 부족합니다. $50를 추가하려면 예를 누르고 현재 게임을 종료하려면 아니오를 누르세요.", "자금 부족", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (choice == JOptionPane.YES_OPTION) {
				balance += 50;
				lblBalanceAmount.setText(String.format("$%.2f", balance));
			} else {
				frame.getContentPane().removeAll();
				frame.repaint();
				initGuiObjects();
				return;
			}
		}

		roundCount++; // 5라운드이면 덱을 다시 초기화하고 카드가 부족하지 않도록 다시 섞습니다.
		if (roundCount >= 5) {
			deck.initFullDeck();
			deck.shuffle();

			lblShuffleInfo = new JLabel("덱이 추가되고 개편 되었습니다.");
			//lblShuffleInfo.setBackground(new Color(0, 128, 0));
			lblShuffleInfo.setForeground(Color.ORANGE);
			//lblShuffleInfo.setOpaque(true);
			lblShuffleInfo.setFont(new Font("굴림", Font.BOLD, 20));
			lblShuffleInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lblShuffleInfo.setBounds(235, 307, 430, 42);
			frame.getContentPane().add(lblShuffleInfo);

			roundCount = 0;
		}
	}

	public static void newGame() { // 새로운 게임이 시작

		if (isValidAmount(tfBalance.getText()) == true) { // 잔액이 유효한지 확인
			balance = Integer.parseInt(tfBalance.getText());
		} else {
			JOptionPane.showMessageDialog(frame, "소지금액이 잘 못 되었습니다! 자연수인지 확인하십시오.", "Error", JOptionPane.ERROR_MESSAGE);
			tfBalance.requestFocus();
			return;
		}

		btnNewGame.setEnabled(false);
		tfBalance.setEnabled(false);
		
		showBetGui(); // 베팅 컨트롤 표시

		roundCount = 0;

		deck = new CardGroup(); // 딜러 데크 초기화
		deck.initFullDeck(); // 모든 카드 추가(기본 52개 카드)
		deck.shuffle(); // 셔플

	}

	public static void updateCardPanels() { // 딜러 및 플레이어 카드를 이미지로 표시
		if (dealerCardPanel != null) { // 이미 추가된 경우 제거
			frame.getContentPane().remove(dealerCardPanel);
			frame.getContentPane().remove(playerCardPanel);
		}
		// 두 개의 패널 생성 및 표시
		dealerCardPanel = new CardGroupPanel(dealerCards, 420 - (dealerCards.getCount() * 40), 50, 70, 104, 10);
		frame.getContentPane().add(dealerCardPanel);
		playerCardPanel = new CardGroupPanel(playerCards, 420 - (playerCards.getCount() * 40), 300, 70, 104, 10);
		frame.getContentPane().add(playerCardPanel);
		frame.repaint();
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		// 프로그램 시작
		
		//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		
		
		
		initGuiObjects(); // 초기 잔액을 입력하고 게임을 시작/중지하기 위한 초기 GUI 개체를 표시합니다.

		frame.setVisible(true);

	}

	public static int heightFromWidth(int width) { // 500x726 원본 크기, 너비에 비례하여 높이를 구하는 도우미 기능
		return (int) (1f * width * (380f / 255f));
	}

	public void start(Stage blackjackStage) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
	    try {
	    	initGuiObjects();
	    	frame.setVisible(true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}