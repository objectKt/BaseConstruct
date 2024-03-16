package module.module_db.backups;

import android.os.Environment;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import module.module_db.entity.CarTravelTable;

/**
* @description:
* @createDate: 2023/8/30
*/
public class BackupsDataManager {

    private static final String TAG = BackupsDataManager.class.getSimpleName();


    private static final String CAR_TRAVEL_NAME = "CarTravel.xml";
    private static final String CAR_TRAVEL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "carData";

    public CarTravelTable readCarTravel(){
        try {
            File dir = new File(CAR_TRAVEL_PATH);
            if(!dir.exists()){
                dir.mkdir();
            }
            NodeList nodeList = getNodeList(dir.getAbsolutePath()+ File.separator + CAR_TRAVEL_NAME);

            CarTravelTable travelTable = new CarTravelTable();
            // 遍历子节点
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                // 判断如果是元素节点（即标签节点）
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Log.i(TAG, "readCarTravel---tagName="+element.getTagName() +", value="+element.getTextContent());

                    // 获取标签名
                    String tagName = element.getTagName();
                    if(tagName.equals("totalMile")){
                        travelTable.setTotalMile(Float.parseFloat(element.getTextContent()));
                    }else if(tagName.equals("totalRunTime")){
                        travelTable.setTotalRunTime(Long.parseLong(element.getTextContent()));
                    }else if(tagName.equals("totalConsumeOil")){
                        travelTable.setTotalConsumeOil(Float.parseFloat(element.getTextContent()));
                    }else if(tagName.equals("userConsumeOil")){
                        travelTable.setUserConsumeOil(Float.parseFloat(element.getTextContent()));
                    }else if(tagName.equals("userMile")){
                        travelTable.setUserMile(Float.parseFloat(element.getTextContent()));
                    }else if(tagName.equals("userRunTime")){
                        travelTable.setUserRunTime(Long.parseLong(element.getTextContent()));
                    }
                }
            }
            return travelTable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeCarTravel(CarTravelTable carTravelTable){
        try {
            Log.i(TAG, "writeCarTravel---carTravelTable="+carTravelTable);

                 // 创建DocumentBuilderFactory实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 创建DocumentBuilder实例
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 创建Document对象
            Document document = builder.newDocument();

            // 创建根元素
            Element root = document.createElement("root");
            document.appendChild(root);

            // 创建子元素
            Element totalMile = document.createElement("totalMile");
            totalMile.setTextContent(carTravelTable.getTotalMile()+"");
            root.appendChild(totalMile);

            Element totalRunTime = document.createElement("totalRunTime");
            totalRunTime.setTextContent(carTravelTable.getTotalRunTime()+"");
            root.appendChild(totalRunTime);

            Element qtrip = document.createElement("totalConsumeOil");
            qtrip.setTextContent(carTravelTable.getTotalConsumeOil()+"");
            root.appendChild(qtrip);

            Element speed = document.createElement("userConsumeOil");
            speed.setTextContent(carTravelTable.getUserConsumeOil()+"");
            root.appendChild(speed);

            Element userMile = document.createElement("userMile");
            userMile.setTextContent(carTravelTable.getUserMile()+"");
            root.appendChild(userMile);

            Element userRunTime = document.createElement("userRunTime");
            userRunTime.setTextContent(carTravelTable.getUserRunTime()+"");
            root.appendChild(userRunTime);

            // 创建TransformerFactory实例
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            // 创建Transformer实例
            Transformer transformer = transformerFactory.newTransformer();

            // 创建DOMSource对象
            DOMSource domSource = new DOMSource(document);

            File dir = new File(CAR_TRAVEL_PATH);
            if(!dir.exists()){
                dir.mkdir();
            }
            // 创建StreamResult对象，指定输出文件路径
            StreamResult streamResult = new StreamResult(dir.getAbsolutePath()+ File.separator + CAR_TRAVEL_NAME);

            // 将DOM树写入XML文件
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private NodeList getNodeList(String path) throws Exception {
        File file = new File(path);

        // 创建DocumentBuilderFactory实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建DocumentBuilder实例
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 使用DocumentBuilder解析XML文件，生成Document对象
        Document document = builder.parse(file);

        // 获取根元素
        Element root = document.getDocumentElement();

        // 获取根元素下的所有子节点
        return root.getChildNodes();
    }
}
