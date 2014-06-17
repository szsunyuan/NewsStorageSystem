/**
 * Write a description of class NewsStorage here.
 * 
 * @author Group 6
 * @version 1.0
 */
import java.io.File;
import java.io.IOException;
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

public class NewsStorage
{
    private Document dom;
    private NodeList nl;
    private Element eElement;
    private Node nNode;
    private int maxID;
    /**
     * Constructor for objects of class NewsStorage
     */
    public NewsStorage()
    {
        ParseNews();
        maxID = getCurrentID();
    }
    
    public void ParseNews()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("database.xml");
            Element docEle = dom.getDocumentElement();
            nl = docEle.getElementsByTagName("NewsList");
            nNode = nl.item(0);
            eElement = (Element) nNode;
        }catch(ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        }
    }
    
    public int addNewsToFile(Element ele)
    {
        if(ele != null) {
            int id = getCurrentID()+1;
            Element newsNode = dom.createElement("news");
            newsNode.setAttribute("id", Integer.toString(id));
            newsNode.appendChild(createNode("title",ele.getElementsByTagName("title").item(0).getTextContent()));
            newsNode.appendChild(createNode("body",ele.getElementsByTagName("body").item(0).getTextContent()));
            newsNode.appendChild(createNode("year",ele.getElementsByTagName("year").item(0).getTextContent()));
            newsNode.appendChild(createNode("month",ele.getElementsByTagName("month").item(0).getTextContent()));
            newsNode.appendChild(createNode("day",ele.getElementsByTagName("day").item(0).getTextContent()));
            newsNode.appendChild(createNode("time",ele.getElementsByTagName("time").item(0).getTextContent()));
            newsNode.appendChild(createNode("like","0"));
            newsNode.appendChild(createNode("dislike","0"));
            nNode.appendChild(newsNode);
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(dom);
                StreamResult file = new StreamResult(new File("database.xml"));
                transformer.transform(source, file);
                return id;
            }catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        else
            return -1;
    }
    
    public Element createNode(String name,String content)
    {
        Element newNode = dom.createElement(name);
        newNode.setTextContent(content);
        return newNode;
    }
    
    public String getTitle(int id)
    {
        if(id > 0 && id <= maxID)
            id--;
        else
            return null;
        return eElement.getElementsByTagName("title").item(id).getTextContent();
    }
    
    public String getContent(int id)
    {
        if(id > 0 && id <= maxID)
            id--;
        else
            return null;
        return eElement.getElementsByTagName("body").item(id).getTextContent();
    }
    
    public String getTime(int id)
    {
        if(id > 0 && id <= maxID)
            id--;
        else
            return null;
        return eElement.getElementsByTagName("month").item(id).getTextContent() + "/" + 
            eElement.getElementsByTagName("day").item(id).getTextContent() + "/" +
            eElement.getElementsByTagName("year").item(id).getTextContent() + " " +
            eElement.getElementsByTagName("time").item(id).getTextContent();
    }
    
    public int getCurrentID()
    {
        return eElement.getElementsByTagName("news").getLength();
    }
}
