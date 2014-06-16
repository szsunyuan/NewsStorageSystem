/**
 * Write a description of class Location here.
 * 
 * @author Group 6
 * @version 1.0
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Location
{
    private Document dom;
    private NodeList nl;
    private Element eElement;
    private Element docEle;
    private Node nNode;
    private int maxID;
    
    /**
     * Constructor for objects of class Location
     */
    public Location()
    {
        ParseLocation();
        maxID = getLocationCount();
        System.out.println(searchLocation("China"));
        System.out.println(getNewsFromLocation("Chisa"));
    }
    
    public void ParseLocation()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("database.xml");
            docEle = dom.getDocumentElement();
            nl = docEle.getElementsByTagName("Locations");
            nNode = nl.item(0);
            eElement = (Element) nNode;
        }catch(ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        }
    }
    
    public int searchLocation(String name)
    {
        if(name.length() > 0) {
            for(int i = 0;i < maxID;i++) {
                if(name.equals(eElement.getElementsByTagName("name").item(i).getTextContent()))
                    return i+1;
            }
            return -1;
        }
        else
            return -1;
    }
    
    public ArrayList<Integer> getNewsFromLocation(String name)
    {
        int index = searchLocation(name);
        ArrayList<Integer> newsList = new ArrayList<>();
        if(index != -1) {
            index--;
            String[] news = eElement.getElementsByTagName("ids").item(index).getTextContent().split(",");
            for(int i = 0;i < news.length;i++) {
                newsList.add(Integer.parseInt(news[i]));
            }
        }
        return newsList;
    }
    
    public boolean addNewsToLocation(String name)
    {
        return true;
    }
    
    public int getLocationCount()
    {
        return eElement.getElementsByTagName("location").getLength();
    }
}
