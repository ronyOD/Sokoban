package model.data.loaders;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Floor;
import model.data.cells.Target;
import model.data.cells.Wall;
import model.data.levels.Level;
import model.data.levels.LevelException;
import model.data.positions.Position;

public class MyXmlLevelLoader implements LevelLoader {
	
	ArrayList<Position> targets = new ArrayList<Position>();
	
	@Override
	public Level load(InputStream inputStream) throws LevelLoaderException {
		Document document;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			document = builder.parse(inputStream);
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}

		Element levelElement = document.getDocumentElement();

		NodeList rowNodeList = levelElement.getElementsByTagName("row");
		int height = rowNodeList.getLength();
		Cell[][] cells = new Cell[height + 1][];
		for (int h = height; h > 0; h--) {
			Element rowElement = (Element) rowNodeList.item(height - h);
			NodeList cellNodeList  = rowElement.getElementsByTagName("cell");
			int width = cellNodeList.getLength();
			cells[h] = new Cell[width + 1];
			Element cellElement;
			for (int w = 1;(cellElement = (Element) cellNodeList.item(w-1))!=null; w++) {
				cells[h][w] = decode(cellElement.getAttribute("type"));
				
				if(cells[h][w] instanceof Target){
					targets.add(new Position(h,w));
				}
			}
			
		}

		Element playerPositionElement = (Element) levelElement.getElementsByTagName("playerPosition").item(0);
		Position playerPosition = new Position(Integer.parseInt(playerPositionElement.getAttribute("h")), Integer.parseInt(playerPositionElement.getAttribute("w")));

		try {
			//ToDO build targets arrays for IsWon to work
			return new Level(cells, playerPosition, targets);
		} catch (LevelException e) {
			throw new LevelLoaderException(e);
		}
	}

	@Override
	public void save(OutputStream outputStream, Level level) throws LevelLoaderException {
		Document document;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}

		Element levelElement = document.createElement("level");

		Position position;
		Cell cell;
		for (int h = level.height(); h > 0; h--) {
			Element rowElement = document.createElement("row");
			for (int w = 1;; w++) {
				position = new Position(h, w);
				if ((cell = level.get(position)) == null)
					break;
				Element cellElement = document.createElement("cell");
				cellElement.setAttribute("type", encode(document, cell));
				rowElement.appendChild(cellElement);
			}
			levelElement.appendChild(rowElement);
		}

		Element playerPositionElement = document.createElement("playerPosition");
		playerPositionElement.setAttribute("h", Integer.toString(level.getPlayerPosition().getH()));
		playerPositionElement.setAttribute("w", Integer.toString(level.getPlayerPosition().getW()));
		levelElement.appendChild(playerPositionElement);
		document.appendChild(levelElement);

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);
			StreamResult streamResult = new StreamResult(outputStream);
			transformer.transform(source, streamResult);
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}
	}

	private String encode(Document document, Cell cell) throws LevelLoaderException {
		if (cell instanceof Box)
			return "box";
		if (cell instanceof Floor)
			return "floor";
		if (cell instanceof Target)
			return "target";
		if (cell instanceof Wall)
			return "wall";
		throw new LevelLoaderException("unknown cell type: " + cell);
	}

	private Cell decode(String type) throws LevelLoaderException {
		if (type.equals("box"))
			return new Box();
		if (type.equals("floor"))
			return new Floor();
		if (type.equals("target"))
			return new Target();
		if (type.equals("wall"))
			return new Wall();
		throw new LevelLoaderException("unknown cell type: " + type);
	}
}
