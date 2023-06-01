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

public class Blackjack {
	private static JFrame frame = new MainFrame(); // MainFrame 클래스의 인스턴스 생성.

	private static CardGroup deck, dealerCards, playerCards; //변수 선언:
	private static CardGroupPanel dealerCardPanel = null, playerCardPanel = null; // The deck of cards, the dealer's cards, the player's cards, the panels for the player's and dealer's cards
	private static Card dealerHiddenCard; //  and the hidden card of the dealer.

	private static double balance = 0.0; // Setting the initial amounts for the Balance,
	private static int betAmount = 0, roundCount = 0; // the amount the player bets and the number of rounds.

	// Creating the GUI elements in the window builder
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
			if (Integer.parseInt(s) > 0) // Ensure amount entered is > 0
				return true;
			else
				return false;
		} catch (NumberFormatException e) { // If not valid integer
			return false;
		}
	}

	// 이 기능은 프로그램이 시작되거나 게임이 종료될 때 실행됩니다. 초기 잔액을 입력하고 게임을 시작/중지하기 위한 초기 GUI 개체를 표시합니다.
	public static void initGuiObjects() {
		btnNewGame = new JButton("New Game"); // New game button
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(); // Start game
			}
		});
		btnNewGame.setBounds(20, 610, 99, 50);
		frame.getContentPane().add(btnNewGame);

		btnEndGame = new JButton("End Game"); // 게임 종료 버튼, 모든 GUI 개체를 제거하고 처음부터 시작합니다.
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
		tfBalance.setText("100");
		tfBalance.setBounds(131, 580, 89, 28);
		frame.getContentPane().add(tfBalance);
		tfBalance.setColumns(10);

		lblInitialBalance = new JLabel("Initial Balance:"); // 초기 잔액 레이블
		lblInitialBalance.setFont(new Font("Arial", Font.BOLD, 13));
		lblInitialBalance.setForeground(Color.WHITE);
		lblInitialBalance.setBounds(30, 586, 100, 16);
		frame.getContentPane().add(lblInitialBalance);
	}

	public static void showBetGui() { // 새 게임이 시작될 때 실행됩니다. 현재 잔액 레이블, 거래 금액 및 거래 버튼을 초기화하고 표시합니다.

		btnEndGame.setEnabled(true);

		lblCurrentBalance = new JLabel("Current Balance:"); // Current balance label
		lblCurrentBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentBalance.setFont(new Font("Arial", Font.BOLD, 16));
		lblCurrentBalance.setForeground(Color.WHITE);
		lblCurrentBalance.setBounds(315, 578, 272, 22);
		frame.getContentPane().add(lblCurrentBalance);

		lblBalanceAmount = new JLabel(); // Balance label, shows current balance
		lblBalanceAmount.setText(String.format("$%.2f", balance));
		lblBalanceAmount.setForeground(Color.ORANGE);
		lblBalanceAmount.setFont(new Font("Arial", Font.BOLD, 40));
		lblBalanceAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalanceAmount.setBounds(315, 600, 272, 50);
		frame.getContentPane().add(lblBalanceAmount);

		lblInfo = new JLabel("Please enter a bet and click Deal"); // Deal info label
		lblInfo.setBackground(Color.ORANGE);
		lblInfo.setOpaque(false);
		lblInfo.setForeground(Color.ORANGE);
		lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(290, 482, 320, 28);
		frame.getContentPane().add(lblInfo);

		tfBetAmount = new JTextField(); // Bet amount text field
		tfBetAmount.setText("10");
		tfBetAmount.setBounds(790, 580, 89, 28);
		frame.getContentPane().add(tfBetAmount);

		lblEnterBet = new JLabel("Enter Bet:"); // Bet amount info label
		lblEnterBet.setFont(new Font("Arial", Font.BOLD, 14));
		lblEnterBet.setForeground(Color.WHITE);
		lblEnterBet.setBounds(689, 586, 100, 16);
		frame.getContentPane().add(lblEnterBet);

		btnDeal = new JButton("Deal"); // Deal button
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

		if (lblShuffleInfo != null) // (Every 5 rounds the deck is reshuffled and this label is displayed. Hide it when a new round is started
			frame.getContentPane().remove(lblShuffleInfo);

		// Initialise dealer/player card arrays
		dealerCards = new CardGroup();
		playerCards = new CardGroup();

		if (isValidAmount(tfBetAmount.getText()) == true) { // Parse bet amount given
			betAmount = Integer.parseInt(tfBetAmount.getText());
		} else {
			lblInfo.setText("Error: Bet must be a natural number!"); // Give an error
			tfBetAmount.requestFocus();
			return;
		}

		if (betAmount > balance) { // If bet is higher than balance
			lblInfo.setText("Error: Bet higher than balance!"); // Give an error
			tfBetAmount.requestFocus();
			return;
		}
		balance -= betAmount; // Subtract bet from balance

		lblBalanceAmount.setText(String.format("$%.2f", balance));

		tfBetAmount.setEnabled(false);
		btnDeal.setEnabled(false);

		lblInfo.setText("Please Hit or Stand"); // Next instruction

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

		btnContinue = new JButton("Continue"); // When the final outcome is reached, press this to accept and continue the game
		btnContinue.setEnabled(false);
		btnContinue.setVisible(false);
		btnContinue.setBounds(290, 444, 320, 35);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptOutcome(); // Accept outcome
			}
		});
		frame.getContentPane().add(btnContinue);

		lblBetAmount = new JLabel(); // Show bet amount
		lblBetAmount.setText("$" + betAmount);
		lblBetAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetAmount.setForeground(Color.ORANGE);
		lblBetAmount.setFont(new Font("Arial", Font.BOLD, 40));
		lblBetAmount.setBounds(679, 488, 200, 50);
		frame.getContentPane().add(lblBetAmount);

		lblBetAmountDesc = new JLabel("Bet Amount:"); // Bet amount info label
		lblBetAmountDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetAmountDesc.setForeground(Color.WHITE);
		lblBetAmountDesc.setFont(new Font("Arial", Font.BOLD, 16));
		lblBetAmountDesc.setBounds(689, 465, 190, 22);
		frame.getContentPane().add(lblBetAmountDesc);

		frame.repaint(); // Redraw frame to show changes

		dealerHiddenCard = deck.takeCard(); // Take a card from top of deck for dealer but hide it
		dealerCards.cards.add(new Card("", "", 0)); // Add turned over card to dealer's cards
		dealerCards.cards.add(deck.takeCard()); // Add card from top of deck to dealer's cards

		// Add two cards from top of deck to player's cards
		playerCards.cards.add(deck.takeCard());
		playerCards.cards.add(deck.takeCard());

		updateCardPanels(); // Display the two card panels

		simpleOutcomes(); // Check for any automatic outcomes (i.e. immediate blackjack)

	}

	public static void hit() { // 플레이어 카드에 다른 카드를 추가하고 새 카드를 보여주고 결과를 확인합니다.

		playerCards.cards.add(deck.takeCard());
		updateCardPanels();

		simpleOutcomes();

	}

	public static boolean simpleOutcomes() { //거래를 누르거나 플레이어가 명중할 때마다 자동으로 실행됩니다.
		boolean outcomeHasHappened = false;
		int playerScore = playerCards.getTotalValue(); // Get player score as total of cards he has
		if (playerScore > 21 && playerCards.getNumAces() > 0) // If player has at least one ace and would otherwise lose (>21), subtract 10
			playerScore -= 10;

		if (playerScore == 21) { // Potential player blackjack

			dealerCards.cards.set(0, dealerHiddenCard); // Replace hidden dealer's card with actual card
			updateCardPanels(); // Display new card
			if (dealerCards.getTotalValue() == 21) { // If dealer ALSO gets a blackjack
				lblInfo.setText("Push!"); // Push
				balance += betAmount; // Give bet back to player
			} else {
				// Player gets a blackjack only
				lblInfo.setText(String.format("Player gets Blackjack! Profit: $%.2f", 1.5f * betAmount));
				balance += 2.5f * betAmount; // Add profits to balance
			}
			lblBalanceAmount.setText(String.format("$%.2f", balance)); // Show new balance

			outcomeHasHappened = true;
			outcomeHappened(); // If something's happened, this round is over. Show the results of round and Continue button
		} else if (playerScore > 21) { // If player goes bust
			lblInfo.setText("Player goes Bust! Loss: $" + betAmount);
			dealerCards.cards.set(0, dealerHiddenCard); // Replace hidden dealer's card with actual card
			updateCardPanels();
			outcomeHasHappened = true;
			outcomeHappened(); // If something's happened, this round is over. Show the results of round and Continue button
		}
		return outcomeHasHappened;

	}

	public static void stand() { // 스탠드 버튼을 눌렀을 때
		if (simpleOutcomes()) // Check for any normal outcomes. If so, we don't need to do anything here so return.
			return;

		int playerScore = playerCards.getTotalValue(); // Get player score as total of cards he has
		if (playerScore > 21 && playerCards.getNumAces() > 0) // If player has at least one ace and would otherwise lose (>21), subtract 10
			playerScore -= 10;

		dealerCards.cards.set(0, dealerHiddenCard); // Replace hidden dealer's card with actual card

		int dealerScore = dealerCards.getTotalValue(); // Get dealer score as total of cards he has

		while (dealerScore < 16) { // If dealer's hand is < 16, he needs to get more cards until it's > 16
			dealerCards.cards.add(deck.takeCard()); // Take a card from top of deck and add
			dealerScore = dealerCards.getTotalValue();
			if (dealerScore > 21 && dealerCards.getNumAces() > 0) // If there's an ace and total > 21, subtract 10
				dealerScore -= 10;
		}
		updateCardPanels(); // Display new dealer's cards

		// Determine final outcomes, give profits if so and display outcomes
		if (playerScore > dealerScore) { // Player wins
			lblInfo.setText("Player wins! Profit: $" + betAmount);
			balance += betAmount * 2;
			lblBalanceAmount.setText(String.format("$%.2f", balance));
		} else if (dealerScore == 21) { // Dealer blackjack
			lblInfo.setText("Dealer gets Blackjack! Loss: $" + betAmount);
		} else if (dealerScore > 21) { // Dealer bust
			lblInfo.setText("Dealer goes Bust! Profit: $" + betAmount);
			balance += betAmount * 2;
			lblBalanceAmount.setText(String.format("$%.2f", balance));
		} else if (playerScore == dealerScore) { // Push
			lblInfo.setText("Push!");
			balance += betAmount;
			lblBalanceAmount.setText(String.format("$%.2f", balance));
		} else { // Otherwise - dealer wins
			lblInfo.setText("Dealer Wins! Loss: $" + betAmount);
		}
		outcomeHappened(); // If something's happened, this round is over. Show the results of round and Continue button

	}

	public static void outcomeHappened() { //무슨 일이 생기면 이 라운드는 끝납니다. 반올림 결과 표시 및 계속 버튼

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);

		// Fancy effects, highlight info label orange and delay the display of Continue button by 500ms
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
		
		// Remove deal objects

		frame.getContentPane().remove(lblDealer);
		frame.getContentPane().remove(lblPlayer);
		frame.getContentPane().remove(btnHit);
		frame.getContentPane().remove(btnStand);
		frame.getContentPane().remove(lblBetAmount);
		frame.getContentPane().remove(lblBetAmountDesc);
		frame.getContentPane().remove(btnContinue);
		frame.getContentPane().remove(dealerCardPanel);
		frame.getContentPane().remove(playerCardPanel);
		lblInfo.setText("Please enter a bet and click Deal");
		tfBetAmount.setEnabled(true);
		btnDeal.setEnabled(true);
		btnDeal.requestFocus();
		frame.repaint();

		if (balance <= 0) { // If out of funds, either top up or end game
			int choice = JOptionPane.showOptionDialog(null, "You have run out of funds. Press Yes to add $100, or No to end the current game.", "Out of funds", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (choice == JOptionPane.YES_OPTION) {
				balance += 100;
				lblBalanceAmount.setText(String.format("$%.2f", balance));
			} else {
				frame.getContentPane().removeAll();
				frame.repaint();
				initGuiObjects();
				return;
			}
		}

		roundCount++; // If 5 rounds, reinitialise the deck and reshuffle to prevent running out of cards
		if (roundCount >= 5) {
			deck.initFullDeck();
			deck.shuffle();

			lblShuffleInfo = new JLabel("Deck has been replenished and reshuffled!");
			//lblShuffleInfo.setBackground(new Color(0, 128, 0));
			lblShuffleInfo.setForeground(Color.ORANGE);
			//lblShuffleInfo.setOpaque(true);
			lblShuffleInfo.setFont(new Font("Arial", Font.BOLD, 20));
			lblShuffleInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lblShuffleInfo.setBounds(235, 307, 430, 42);
			frame.getContentPane().add(lblShuffleInfo);

			roundCount = 0;
		}
	}

	public static void newGame() { // When new game is started

		if (isValidAmount(tfBalance.getText()) == true) { // Check that balance is valid
			balance = Integer.parseInt(tfBalance.getText());
		} else {
			JOptionPane.showMessageDialog(frame, "Invalid balance! Please ensure it is a natural number.", "Error", JOptionPane.ERROR_MESSAGE);
			tfBalance.requestFocus();
			return;
		}

		btnNewGame.setEnabled(false);
		tfBalance.setEnabled(false);
		
		showBetGui(); // Show bet controls

		roundCount = 0;

		deck = new CardGroup(); // Initialize dealer deck
		deck.initFullDeck(); // Add all the cards (default 52 card)
		deck.shuffle(); // Shuffle

	}

	public static void updateCardPanels() { // Displays dealer and player cards as images
		if (dealerCardPanel != null) { // If they're already added, remove them
			frame.getContentPane().remove(dealerCardPanel);
			frame.getContentPane().remove(playerCardPanel);
		}
		// Create and display two panels
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

	public static int heightFromWidth(int width) { // 500x726 original size, helper function to get height proportional to width
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