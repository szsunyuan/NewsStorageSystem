
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
        int currentNewsID = addNews();
        updateCategory(currentNewsID);
        updateLocation(currentNewsID);
    }
    
    public int addNews()
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
            return store.addNewsToFile(eElement);
        }catch(ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
            return -1;
        }
    }
    
    public boolean updateCategory(int id)
    {
        Category cat = new Category();
        return cat.addNewsToCategory(id,eElement.getElementsByTagName("category").item(0).getTextContent());
    }
    
    public boolean updateLocation(int id)
    {
        Location loc = new Location();
        return loc.addNewsToLocation(id,eElement.getElementsByTagName("location").item(0).getTextContent());
    }
}
