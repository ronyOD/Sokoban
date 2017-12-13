package application;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import errors.Errors;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SokobanKeys {

	private static SokobanKeys sokobanKeys = new SokobanKeys();

	public static SokobanKeys it() {
		return sokobanKeys;
	}

	public static void load(InputStream inputStream) {
		Document document;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			document = builder.parse(inputStream);
		} catch (Exception e) {
			Errors.report(e);
			return;
		}

		Element keysElement = document.getDocumentElement();
		sokobanKeys.setUpText(keysElement.getAttribute("up"));
		sokobanKeys.setDownText(keysElement.getAttribute("down"));
		sokobanKeys.setLeftText(keysElement.getAttribute("left"));
		sokobanKeys.setRightText(keysElement.getAttribute("right"));
	}

	private KeyCode upCode = KeyCode.UP;
	private KeyCode downCode = KeyCode.DOWN;
	private KeyCode leftCode = KeyCode.LEFT;
	private KeyCode rightCode = KeyCode.RIGHT;

	public KeyCode getUpCode() {
		return upCode;
	}

	public void setUpCode(KeyCode upCode) {
		this.upCode = upCode;
	}

	public KeyCode getDownCode() {
		return downCode;
	}

	public void setDownCode(KeyCode downCode) {
		this.downCode = downCode;
	}

	public KeyCode getLeftCode() {
		return leftCode;
	}

	public void setLeftCode(KeyCode leftCode) {
		this.leftCode = leftCode;
	}

	public KeyCode getRightCode() {
		return rightCode;
	}

	public void setRightCode(KeyCode rightCode) {
		this.rightCode = rightCode;
	}

	public String getUpText() {
		return upText;
	}

	public void setUpText(String upText) {
		this.upText = upText;
	}

	public String getDownText() {
		return downText;
	}

	public void setDownText(String downText) {
		this.downText = downText;
	}

	public String getLeftText() {
		return leftText;
	}

	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}

	public String getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = rightText;
	}

	private String upText;
	private String downText;
	private String leftText;
	private String rightText;

	public boolean isUp(KeyEvent event) {
		return is(event.getCode(), upCode) || is(event.getText(), upText);
	}

	public boolean isDown(KeyEvent event) {
		return is(event.getCode(), downCode) || is(event.getText(), downText);
	}

	public boolean isLeft(KeyEvent event) {
		return is(event.getCode(), leftCode) || is(event.getText(), leftText);
	}

	public boolean isRight(KeyEvent event) {
		return is(event.getCode(), rightCode) || is(event.getText(), rightText);
	}

	private static boolean is(KeyCode codeFirst, KeyCode codeSecond) {
		if (codeFirst != null && codeSecond != null && codeFirst == codeSecond)
			return true;
		return false;
	}

	private static boolean is(String textFirst, String textSecond) {
		if (textFirst != null && textSecond != null && textFirst.equals(textSecond))
			return true;
		return false;
	}
}
