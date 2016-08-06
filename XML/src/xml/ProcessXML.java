package xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.DOMException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Reference: http://stackoverflow.com/questions/12459712/modify-xml-file-with-xpath
 *
 * @author skorkmaz
 */
public class ProcessXML {

    public static void processXMLFile(String xmlPath) {
        try {
            File file = new File(xmlPath);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            Document doc = domFactory.newDocumentBuilder().parse(file.getAbsolutePath());

            XPath xpath = XPathFactory.newInstance().newXPath();
            String xPathStr = "/somGBYConfig/dted1Path/text()";
            Node node = ((NodeList) xpath.compile(xPathStr).evaluate(doc, XPathConstants.NODESET)).item(0);
            String existingValueStr = node.getNodeValue();
            System.out.printf("Existing value: %s\n", existingValueStr);
            String newValueStr = "4.0";
            System.out.printf("New value     : %s\n", newValueStr);
            node.setNodeValue(newValueStr);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(file));
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | DOMException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
