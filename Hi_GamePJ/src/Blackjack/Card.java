package Blackjack;

public class Card { // 이 클래스는 모든 카드에 대해 생성되며 각 카드에 대한 정보를 저장합니다.
	// 카드의 등급, 모양 및 값을 저장하는 변수
	public String rank = "", suit = "";
	public int value = 0;

	Card(String r, String s, int v) { // 생성자 - 값 초기화
		this.rank = r;
		this.suit = s;
		this.value = v;
	}

	public void print() { // 디버그 - 카드에 정보 출력
		System.out.printf("%s of %s, value %d\n", this.rank, this.suit, this.value);
	}

	public String getFileName() { // 이 카드의 이미지 파일 이름을 가져옵니다.
		if (value == 0) // 이것이 딜러의 뒤집힌 카드인 경우(값 0)
			return "src/Blackjack/cardImages/backCover.png";
		return String.format("src/Blackjack/cardImages/%s/%s.gif", this.suit, this.rank); // 파일 이름 반환
	}
}
