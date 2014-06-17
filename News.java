
/**
 * Write a description of class News here.
 * 
 * @author Group 6
 * @version 1.0
 */
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class News
{
    private Document dom;
    private NodeList nl;
    private Element eElement;
    private Node nNode;
    private String fileName;
    
    /**
     * Constructor for objects of class News
     */
    public News(String fileName)
    {
        this.fileName = fileName;
        addNews();
    }
    
    public boolean addNews()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(fileName);
            Element docEle = dom.getDocumentElement();
            nl = docEle.getElementsByTagName("News");
            nNode = nl.item(0);
            eElement = (Element) nNode;
            NewsStorage store = new NewsStorage();
            if(store.addNewsToFile(eElement))
                return true;
            else
                return false;
        }catch(ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
