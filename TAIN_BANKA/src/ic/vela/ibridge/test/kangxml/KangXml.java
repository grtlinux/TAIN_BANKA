package ic.vela.ibridge.test.kangxml;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@SuppressWarnings("unused")
public class KangXml {

	private static final String   XML_PATH       = "D:/KANG/WORK/workspace/KangProj/src/ic/test/kangxml/";
	
	private static final String[] FILE01         = { "test.xml", "test.xsl", "out.test.xml" };
	private static final String[] FILE02         = { "sample07.xml", "sample07.xsl", "out.sample07.xml" };
	private static final String[] FILE           = FILE02;

	/////////////////////////////////////////////////////////////////////////////////
	// FileInputStream
	public void fileInputStream() {
		try {
			FileInputStream fis = new FileInputStream(XML_PATH + "test.xml");
			byte[] data = new byte[5096];
			int ret = fis.read(data);
			fis.close();
			
			System.out.println("FILE DATA: ret:" + ret + "\n" + new String(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	// FileOutputStream
	public void fileOutputStream() {
		try {
			byte[] data = new byte[5096];
			
			FileOutputStream fos = new FileOutputStream(XML_PATH + "test.out.xml");
			fos.write(data, 0, 5096);
			fos.close();
			
			System.out.println("FILE DATA: \n" + new String(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// parser01 the XML . XSLT
	private static void parser01() {
		try {
			Source xmlSource = new StreamSource(XML_PATH + FILE[0]);
			Source xslSource = new StreamSource(XML_PATH + FILE[1]);
			Result xmlResult = new StreamResult(XML_PATH + FILE[2]);
			Result outResult = new StreamResult(System.out);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(xslSource);
			
			transformer.transform(xmlSource, xmlResult);
			transformer.transform(xmlSource, outResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	// parser01 the XML . XSLT
	private static void parser02() {
		try {
			Source xmlSource = new StreamSource(XML_PATH + FILE[0]);
			Source xslSource = new StreamSource(XML_PATH + FILE[1]);
			Result xmlResult = new StreamResult(XML_PATH + FILE[2]);
			Result outResult = new StreamResult(System.out);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", new Integer(2));
			Transformer transformer = transformerFactory.newTransformer(xslSource);
			
			if (transformer != null) {
				transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
				transformer.setOutputProperty(OutputKeys.ENCODING, "EUC-KR");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			}
			
			transformer.transform(xmlSource, xmlResult);
			transformer.transform(xmlSource, outResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		switch (2) {
		case 1: parser01(); break;
		case 2: parser02(); break;
		default: break;
		}
	}
}
