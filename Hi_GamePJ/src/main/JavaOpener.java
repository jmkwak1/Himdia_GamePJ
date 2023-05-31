package main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class JavaOpener {
	public void boomOpen() {
		Button openButton = new Button("Open Java File");
		openButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	try {
		            // 열고자 하는 Java 파일 경로 설정
		            File javaFile = new File("project/Game.java");
		            
		            // Desktop 클래스를 사용하여 Java 파일 열기
		            Desktop.getDesktop().open(javaFile);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		});
	}
}
