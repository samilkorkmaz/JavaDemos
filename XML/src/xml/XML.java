package xml;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 * XML read/write demo.
 *
 * @author skorkmaz, 2014
 */
public class XML {

    public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        //QueryXML process = new QueryXML();
        //process.query();
       ProcessXML.processXMLFile("config.xml");
    }
}
