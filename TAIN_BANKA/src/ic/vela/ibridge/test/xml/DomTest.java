package ic.vela.ibridge.test.xml;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class DomTest {

	private static final String   XMLPATH       = "D:/KANG/WORK/workXML/"; 

	/////////////////////////////////////////////////////////////////////////////////
	// test01 : AttrGathering
	private static void test01() {
		try {
			String xmlFile = "DOMSample.xml";
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(XMLPATH + xmlFile);
			
			Element rootElement = document.getDocumentElement();
			
			if (rootElement.hasAttributes()) {
				NamedNodeMap attrNodes = rootElement.getAttributes();
				
				for (int i=0; i < attrNodes.getLength(); i++) {
					Node node = attrNodes.item(i);
					System.out.println("Attribute Name : " + node.getNodeName());
					System.out.println("     Attribute Value : " + node.getNodeValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test02 : CreateDOMParser
	private static void test02() {
		try {
			String xmlFile = "DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			
			Document emptyDocument = documentBuilder.newDocument();
			System.out.println("빈 Document 객체 생성 완료");
			System.out.println(">" + emptyDocument);
			
			Document xmlDocument = documentBuilder.parse(XMLPATH + xmlFile);
			System.out.println("DOMSample.xml 문서를 입력으로 Document 객체를 생성완료");
			System.out.println(">" + xmlDocument);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test03 : DBProgramming
	// 제어판 > 모든 제어판 항목 > 데이터 원본(ODBC) > 사용자DSN > BookCatalog (mdb)를 먼저 만든다.
	private static void test03() {
		try {
			String dbName = "jdbc:odbc:BookCatalog";
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection db = DriverManager.getConnection(dbName, "", "");
			
			Statement query = db.createStatement();
			ResultSet rs = query.executeQuery("select * from BookList");
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			
			while (rs.next()) {
				for (int i=1; i <= columnCount; i++) {
					System.out.print(rs.getRow() + ") ");
					System.out.print("[" + rsMetaData.getColumnName(i) + "] ");
					System.out.print("[" + rs.getString(i).trim() + "] ");
					System.out.println();
				}
			}
			
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test04 : DB2Element
	private static void test04() {
		try {
			// DB Query
			String dbName = "jdbc:odbc:BookCatalog";
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection db = DriverManager.getConnection(dbName, "", "");
			
			Statement query = db.createStatement();
			ResultSet rs = query.executeQuery("select * from BookList");
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columnCount = rsMeta.getColumnCount();
			
			// Empty Document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			
			// Create element
			Element rootElement = document.createElement("BookList");
			document.appendChild(rootElement);
			
			while (rs.next()) {
				Element bookElement = document.createElement("Book");
				rootElement.appendChild(bookElement);
				
				for (int i=1; i <= columnCount; i++) {
					Element element = document.createElement(rsMeta.getColumnName(i));
					Text text = document.createTextNode(rs.getString(i));
					element.appendChild(text);
					bookElement.appendChild(element);
				}
			}
			
			db.close();
			
			// make xml from document
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			//transformer.setOutputProperty("indent", "yes");
			DOMSource domSource = new DOMSource(document);
			//String xmlFile = "BookList.Element.xml";
			//StreamResult streamResult = new StreamResult(XMLPATH + xmlFile);
			
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();  // TODO : to use
			StreamResult streamResult = new StreamResult(byteStream);
			
			transformer.transform(domSource, streamResult);
			
			byte[] byteXml = byteStream.toByteArray();  // TODO : to use
			String strXml = byteStream.toString();  // TODO : to use
			
			System.out.println("size > " + byteStream.size());
			System.out.println("byteXml = [" + new String(byteXml) + "]");
			System.out.println("strXml = [" + strXml + "]");
			
			byteStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test05 : DB2Attribute
	private static void test05() {
		try {
			// DB Query
			String dbName = "jdbc:odbc:BookCatalog";
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection db = DriverManager.getConnection(dbName, "", "");
			
			Statement query = db.createStatement();
			ResultSet rs = query.executeQuery("select * from BookList");
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columnCount = rsMeta.getColumnCount();
			
			// Document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();  // Empty Document
			
			// make element
			Element rootElement = document.createElement("BookList");
			document.appendChild(rootElement);
			
			while (rs.next()) {
				Element book = document.createElement("Book");
				rootElement.appendChild(book);
				
				// make attribute
				for (int i=1; i <= columnCount; i++) {
					book.setAttribute(rsMeta.getColumnName(i), rs.getString(i));
				}
			}
			
			db.close();
			
			// Transformer
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			
			// source document
			DOMSource domSource = new DOMSource(document);
			
			// target document
			String xmlFile = "BookList.Attribute.xml";
			StreamResult streamResult = new StreamResult(XMLPATH + xmlFile);
			
			// do the transformer
			transformer.transform(domSource, streamResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test06 : DocumentNodeList
	private static void test06() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			NodeList nodeList = document.getChildNodes();
			for (int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.print("[NodeType:" + node.getNodeType() + "] ");
				System.out.print("[NodeName:" + node.getNodeName() + "] ");
				System.out.print("[NodeValue:" + node.getNodeValue() + "] ");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test07 : ExtractValue
	private static void test07() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Element rootElement = document.getDocumentElement();
			
			System.out.println("Root Element's Value > " + rootElement.getTextContent());  // root 밑에 있는 모든 Text
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test08 : IgnoringWhitespace
	private static void test08() {
		try {
			String xmlFile = "DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Element rootElement = document.getDocumentElement();
			NodeList nodeList = rootElement.getChildNodes();
			
			for (int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String nodeName = node.getNodeName();
				System.out.println(nodeName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test09 : NodeType
	private static void test09() {
		try {
			String xmlFile = "DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			if (document.getNodeType() == Node.DOCUMENT_NODE) {
				System.out.print("현재 노드는 도큐먼트 노드이고, 노드 상수는 ");
				System.out.print(document.getNodeType() + " 입니다.");
				System.out.println();
			}
			
			Element node = document.getDocumentElement();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				System.out.print("현재 노드는 엘리먼트 노드이고, 노드 상수는 ");
				System.out.print(node.getNodeType() + " 입니다.");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test10 : RemoveNode  : TODO 사용
	private static void test10() {
		try {
			//String xmlFile = "10.DOMSample.xml";
			String xmlFile = "_DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			// root Node
			Element rootElement = document.getDocumentElement();
			
			// remove Node
			Node removeElement = rootElement.getLastChild();
			System.out.println("removeElement:" + removeElement.getNodeName());
			rootElement.removeChild(removeElement);
			rootElement.removeAttribute("Attr3");
			
			// transformer
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			
			// source document
			DOMSource domSource = new DOMSource(document);

			// target document
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(byteStream);

			// do the tranformer
			transformer.transform(domSource, streamResult);
			
			byte[] byteXml = byteStream.toByteArray();
			
			System.out.println("size > " + byteStream.size());
			System.out.println("[" + new String(byteXml) + "]");
			
			byteStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test11 : ReplaceNode   : TODO 사용
	private static void test11() {
		try {
			//String xmlFile = "10.DOMSample.xml";
			String xmlFile = "_DOMSample.xml";

			// document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(XMLPATH + xmlFile);
			
			// root element
			Element rootElement = document.getDocumentElement();

			// first Node
			Node renameElement = rootElement.getFirstChild();
			System.out.println("renameElementName:" + renameElement.getNodeName());
			document.renameNode(renameElement, "", "rename_name");

			// create New Node
			Element newElement = document.createElement("blend");
			Text newText = document.createTextNode("TEST");
			newElement.appendChild(newText);
			
			System.out.println("newElementName:" + newElement.getNodeName());
			System.out.println("newElementValue:" + newElement.getNodeValue());
			
			// last Node
			Node replaceElement = rootElement.getLastChild();
			rootElement.replaceChild(newElement, replaceElement);
			
			// transformer
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			
			// source document
			DOMSource domSource = new DOMSource(document);

			// target document
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(byteStream);

			// do the transformer
			transformer.transform(domSource, streamResult);
			
			byte[] byteXml = byteStream.toByteArray();
			
			System.out.println("size > " + byteStream.size());
			System.out.println("[" + new String(byteXml) + "]");
			
			byteStream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test12 : SearchNode : TODO 사용
	private static void test12() {
		try {
			String xmlFile = "12.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			
			//
			Document document = dBuilder.parse(XMLPATH + xmlFile);
			Node authorsElement = document.getElementsByTagName("authors").item(0);
			
			Node firstAuthorNode = authorsElement.getFirstChild();
			Node lastAuthorNode = authorsElement.getLastChild();
			Node prevAuthorNode = lastAuthorNode.getPreviousSibling();
			Node preSiblingNode = authorsElement.getPreviousSibling();
			Node nextSiblingNode = authorsElement.getNextSibling();
			Node parentNode = authorsElement.getParentNode();

			//
			Document authorsDocument = authorsElement.getOwnerDocument();
			
			System.out.println(">" + authorsElement.getNodeName());
			System.out.println(">" + firstAuthorNode.getTextContent());
			System.out.println(">" + lastAuthorNode.getTextContent());
			System.out.println(">" + prevAuthorNode.getTextContent());
			System.out.println(">" + preSiblingNode.getTextContent());
			System.out.println(">" + nextSiblingNode.getTextContent());
			System.out.println(">" + parentNode.getNodeName());
			System.out.println(">" + authorsDocument.getDocumentURI());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test13 : Serialization  : TODO 사용
	private static void test13() {
		try {
			String xmlFile = "12.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			serializeNode1(document);
			
			System.out.println(xmlString);
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	static String xmlString = new String();

	private static void serializeNode1(Node node) {
		switch (node.getNodeType()) {
			case Node.DOCUMENT_NODE:
				Document xmlDoc = (Document)node;
				xmlString = "<?xml version=\"" + xmlDoc.getXmlVersion();
				xmlString += "\" encoding=\"" + xmlDoc.getXmlEncoding();
				xmlString += "\" standalone=\"" + xmlDoc.getXmlStandalone();
				xmlString += "\"?>\n";
				
				NodeList nodeList = node.getChildNodes();
				if (nodeList != null) {
					for (int i = 0 ; i < nodeList.getLength() ; i++) {
						serializeNode1(nodeList.item(i));
					}
				}
				break;
                	
			case Node.PROCESSING_INSTRUCTION_NODE:
				xmlString += "<?" + ((ProcessingInstruction)node).getTarget();
				xmlString += " " + ((ProcessingInstruction)node).getData() + "?>\n";
				break;
        	        
			case Node.DOCUMENT_TYPE_NODE: 
				DocumentType docType = (DocumentType)node;
				xmlString += "<!DOCTYPE " + docType.getName();
				if ((docType.getPublicId() != null) && (docType.getSystemId() != null)) {
					if (docType.getPublicId() != null) {
						xmlString += " PUBLIC \"" + docType.getPublicId() + "\" ";
					} else {
						xmlString += " SYSTEM ";
					}
					xmlString += "\"" + docType.getSystemId() + "\">\n";
				} else {
					xmlString += "[ ... ]>\n";
				}
				break;
        	        
			case Node.COMMENT_NODE:
				xmlString += "<!-- " + node.getNodeValue() + " -->";
				break;
        	        
			case Node.ELEMENT_NODE:
				xmlString += "<" + node.getNodeName();
				NamedNodeMap attributes = node.getAttributes();
				if (attributes.getLength() != 0) {
					for (int i = 0 ; i < attributes.getLength() ; i++) {
						serializeNode1(attributes.item(i));
					}
				}
				xmlString += ">";
	                
				NodeList elementNodeList = node.getChildNodes();
				if (elementNodeList != null) {
					for (int i = 0; i < elementNodeList.getLength() ; i++) {                        
						serializeNode1(elementNodeList.item(i));
					}
				}
				xmlString += "</" + node.getNodeName() + ">";
				break;
        	        
			case Node.ATTRIBUTE_NODE:
				xmlString += " " + node.getNodeName();
				xmlString += "=\"" + node.getNodeValue() + "\"";
				break;
        	        
			case Node.TEXT_NODE:
				String nodeValue = node.getNodeValue();
				if(nodeValue.trim().equals("") | nodeValue.trim().equals("\n")) {
				} else {
					xmlString += nodeValue;
				}
				break;
        	        
			case Node.CDATA_SECTION_NODE:
				xmlString += "<![CDATA[ " + node.getNodeValue() + "]]>";
				break;
        	        
			case Node.ENTITY_REFERENCE_NODE:
				xmlString += "&" + node.getNodeName() + ";";
				break;
		}
	}              

	/////////////////////////////////////////////////////////////////////////////////
	// test14 : SeializationWithIndent : TODO 사용
	private static void test14() {
		try {
			String xmlFile = "12.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			serializeNode2(document, "");
			
			System.out.println(serializeString);
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	static String serializeString = new String();
	static String whitespace = "     ";

	private static void serializeNode2(Node node, String indent) {
		switch (node.getNodeType()) {
			case Node.DOCUMENT_NODE:
				Document xmlDoc = (Document)node;
				
				serializeString = "<?xml version=\"" + xmlDoc.getXmlVersion() + "\" encoding=\"" + xmlDoc.getXmlEncoding() + "\" standalone=\"" + xmlDoc.getXmlStandalone() + "\"?>\n\n";
				
				NodeList nodeList = node.getChildNodes();
				
				if (nodeList != null) {
					for (int i = 0 ; i < nodeList.getLength() ; i++) {
						serializeNode2(nodeList.item(i), indent);
					}
				}
				
				break;
                	
			case Node.PROCESSING_INSTRUCTION_NODE:
				serializeString += "<?" + ((ProcessingInstruction)node).getTarget() + " " + ((ProcessingInstruction)node).getData() + "?>\n\n";

				break;
        	        
			case Node.DOCUMENT_TYPE_NODE: 
				DocumentType docType = (DocumentType)node;
				
				serializeString += "<!DOCTYPE " + docType.getName();
				
				if ((docType.getPublicId() != null) && (docType.getSystemId() != null)) {
					if (docType.getPublicId() != null) {
						serializeString += " PUBLIC \"" + docType.getPublicId() + "\" ";
					} else {
						serializeString += " SYSTEM ";
					}
					
					serializeString += "\"" + docType.getSystemId() + "\">\n";
				} else {
					serializeString += "[ ... ]>\n";
				}
								
				break;
        	        
			case Node.COMMENT_NODE:
				serializeString += "<!-- " + node.getNodeValue() + " -->\n";
				
				break;
        	        
			case Node.ELEMENT_NODE:
				serializeString += "\n" + indent;
				
				serializeString += "<" + node.getNodeName();
	                
				NamedNodeMap attributes = node.getAttributes();
        	        
				if (attributes.getLength() != 0) {
					for (int i = 0 ; i < attributes.getLength() ; i++) {
						serializeNode2(attributes.item(i), indent);
					}
				}
	                
				serializeString += ">";
	                
				NodeList elementNodeList = node.getChildNodes();
        	        
				if (elementNodeList != null) {
					for (int i = 0; i < elementNodeList.getLength() ; i++) {                        
						serializeNode2(elementNodeList.item(i), indent + whitespace);
					}
				}
				
				if ((elementNodeList.item(0) != null) && elementNodeList.item(elementNodeList.getLength() - 1).getNodeType() == Node.ELEMENT_NODE) {
					serializeString += "\n" + indent;
				}
	                
				serializeString += "</" + node.getNodeName() + ">";
				
				break;
        	        
			case Node.ATTRIBUTE_NODE:
				serializeString += " " + node.getNodeName() + "=\"" + node.getNodeValue() + "\"";
				
				break;
        	        
			case Node.TEXT_NODE:
				String nodeValue = node.getNodeValue();
				
				if(nodeValue.trim().equals("") | nodeValue.trim().equals("\n")) {
				} else {
					serializeString += nodeValue;
				}
				
				break;
        	        
			case Node.CDATA_SECTION_NODE:
				serializeString += "<![CDATA[ " + node.getNodeValue() + "]]>";
				
				break;
        	        
			case Node.ENTITY_REFERENCE_NODE:
				serializeString += "&" + node.getNodeName() + ";";
				
				break;
		}
	}              

	/////////////////////////////////////////////////////////////////////////////////
	// test15 : UseNodeList
	private static void test15() {
		try {
			String xmlFile = "15.DOMSample.xml";

			// document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// 원천
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Element rootElement = document.getDocumentElement();
			NodeList nodeList = rootElement.getChildNodes();
			
			for (int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String nodeName = node.getNodeName();
				System.out.println(">" + nodeName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// test16 : DisplayNodeI
	private static void test16() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Node rootElement = document.getDocumentElement();
			
			displayNode1(rootElement);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void displayNode1(Node node) {
		switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				System.out.println("Element Node");
				break;
			case Node.TEXT_NODE:
				System.out.println("Text Node");
				break;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// test17 : DisplayNodeII : TODO 사용
	private static void test17() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Node rootElement = document.getDocumentElement();
			
			displayNode2(rootElement);
			
			System.out.println(xmlString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void displayNode2(Node node) {
		switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				xmlString += "<" + node.getNodeName() + ">";
				NodeList elementNodeList = node.getChildNodes();
				if (elementNodeList != null) {
					for (int i = 0; i < elementNodeList.getLength() ; i++) {                        
						displayNode2(elementNodeList.item(i));
					}
				}
				xmlString += "</" + node.getNodeName() + ">";
				break;
			case Node.TEXT_NODE:
				String nodeValue = node.getNodeValue();
				if(nodeValue.trim().equals("") | nodeValue.trim().equals("\n")) {
					//
				} else {
					xmlString += nodeValue;
				}
				break;
		}
	}              

	/////////////////////////////////////////////////////////////////////////////////
	// test18 : DisplayNodeIII  : TODO 사용
	private static void test18() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Node rootElement = document.getDocumentElement();
			
			displayNode3(rootElement);
			
			System.out.println(xmlString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void displayNode3(Node node) {
		switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				xmlString += "<" + node.getNodeName();
				NamedNodeMap attributes = node.getAttributes();
				if (attributes.getLength() != 0) {
					for (int i = 0 ; i < attributes.getLength() ; i++) {
						displayNode3(attributes.item(i));
					}
				}
				xmlString += ">";
				NodeList elementNodeList = node.getChildNodes();
				if (elementNodeList != null) {
					for (int i = 0; i < elementNodeList.getLength() ; i++) {                        
						displayNode3(elementNodeList.item(i));
					}
				}
				xmlString += "</" + node.getNodeName() + ">";
				break;
			case Node.ATTRIBUTE_NODE:
				xmlString += " " + node.getNodeName();
				xmlString += "=\"" + node.getNodeValue() + "\"";
				break;
			case Node.TEXT_NODE:
				String nodeValue = node.getNodeValue();
				if(nodeValue.trim().equals("") | nodeValue.trim().equals("\n")) {
					//
				} else {
					xmlString += nodeValue;
				}
				break;
		}
	}              
	
	/////////////////////////////////////////////////////////////////////////////////
	// test19 : DisplayNodeIV  : TODO 사용
	private static void test19() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);
			
			Node rootElement = document.getDocumentElement();
			
			displayNode4(rootElement);
			
			System.out.println(xmlString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void displayNode4(Node node) {
		switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				xmlString += "<" + node.getNodeName();
				NamedNodeMap attributes = node.getAttributes();
				if (attributes.getLength() != 0) {
					for (int i = 0 ; i < attributes.getLength() ; i++) {
						displayNode4(attributes.item(i));
					}
				}
				xmlString += ">";
				NodeList elementNodeList = node.getChildNodes();
				if (elementNodeList != null) {
					for (int i = 0; i < elementNodeList.getLength() ; i++) {                        
						displayNode4(elementNodeList.item(i));
					}
				}
				xmlString += "</" + node.getNodeName() + ">";
				break;
			case Node.ATTRIBUTE_NODE:
				xmlString += " " + node.getNodeName();
				xmlString += "=\"" + node.getNodeValue() + "\"";
				break;
			case Node.TEXT_NODE:
				String nodeValue = node.getNodeValue();
				if(nodeValue.trim().equals("") | nodeValue.trim().equals("\n")) {
					//
				} else {
					xmlString += nodeValue;
				}
				break;
			case Node.COMMENT_NODE:
				xmlString += "<!-- " + node.getNodeValue() + " -->";
				break;
			case Node.CDATA_SECTION_NODE:
				xmlString += "<![CDATA[ " + node.getNodeValue() + "]]>";
				break;
			case Node.ENTITY_REFERENCE_NODE:
				xmlString += "&" + node.getNodeName() + ";";
				break;
		}
	}              

	/////////////////////////////////////////////////////////////////////////////////
	// test20 : XSLT_DOMTransform : TODO 사용
	private static void test20() {
		try {
			String xmlFile = "06.DOMSample.xml";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XMLPATH + xmlFile);

			printDocument(document);
			//System.out.println(">" + getXmlString(document));
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			DOMSource domSource = new DOMSource(document);
			DOMResult domResult = new DOMResult();
			
			transformer.transform(domSource, domResult);

			Document resultDoc = (Document) domResult.getNode();
			printDocument(resultDoc);
			//System.out.println(">" + getXmlString(resultDoc));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printDocument(Node node) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			//transformer.setOutputProperty("indent", "yes");

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(byteStream);
			
			transformer.transform(new DOMSource(node), streamResult);
			
			byte[] byteXml = byteStream.toByteArray();
			
			System.out.println("size > " + byteStream.size());
			System.out.println("[" + new String(byteXml) + "]");
			
			byteStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// main
	public static void main(String[] args) {
		switch (18) {
		case  1: test01(); break; // AttrGathering
		case  2: test02(); break; // CreateDOMParser
		case  3: test03(); break; // DBProgramming
		case  4: test04(); break; // DB2Element
		case  5: test05(); break; // DB2Attribute
		case  6: test06(); break; // DocumentNodeList
		case  7: test07(); break; // ExtractValue
		case  8: test08(); break; // IgnoringWhitespace
		case  9: test09(); break; // NodeType
		case 10: test10(); break; // RemoveNode
		case 11: test11(); break; // ReplaceNode
		case 12: test12(); break; // SearchNode
		case 13: test13(); break; // Serialization
		case 14: test14(); break; // SerializationWithIndent
		case 15: test15(); break; // UseNodeList
		case 16: test16(); break; // DisplayNodeI
		case 17: test17(); break; // DisplayNodeII
		case 18: test18(); break; // DisplayNodeIII
		case 19: test19(); break; // DisplayNodeIV
		case 20: test20(); break; // XSLT_DOMTransform
		default: break;
		}
	}
}
