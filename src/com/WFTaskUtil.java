package com;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WFTaskUtil {

	public static void main(String[] args) {
		String xmlStr = null;
		try {
			long startTime = System.currentTimeMillis();
			xmlStr = new String(Files.readAllBytes(Paths.get("request.xml")));
			InputSource source = new InputSource(new StringReader(xmlStr));
			WFTaskRequestDTO wfDto = getWorkFlowDTO(source);
			long stopTime = System.currentTimeMillis();
			System.out.println(wfDto.toString());
			System.out.println(stopTime - startTime + " ms");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the work flow dto.
	 * @param stream
	 * @return the work flow dto
	 */
	private static WFTaskRequestDTO getWorkFlowDTO(InputSource stream) {
		WFTaskRequestDTO wfTask = new WFTaskRequestDTO();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			XPath xPath = XPathFactory.newInstance().newXPath();

			XPathExpression expr = xPath.compile("/*"); // getting elements of under root element
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList rootNdList = (NodeList) result; // rootNdList.item(0).getNodeName() - rootElement

			for (int i = 0; i < rootNdList.getLength(); i++) {
				Node rootNd = rootNdList.item(i); // rootNd.getNodeName() - root element
				if (rootNd.getNodeType() == Node.ELEMENT_NODE) {
					Element subRootElement = (Element) rootNd;
					wfTask.setApplicationName(
							subRootElement.getElementsByTagName("applicationName").item(i).getTextContent());
					wfTask.setWfIndentifier(
							(HashMap<String, String>) getSubNodeDetails(subRootElement, "wfIdentifier"));
					wfTask.setFromStage(subRootElement.getElementsByTagName("fromStage").item(i).getTextContent());
					wfTask.setAssignedBy(subRootElement.getElementsByTagName("assignedBy").item(i).getTextContent());
					wfTask.setAssignedTo(subRootElement.getElementsByTagName("assignedTo").item(i).getTextContent());
					wfTask.setSla(subRootElement.getElementsByTagName("sla").item(i).getTextContent());
					wfTask.setData((HashMap<String, String>) getSubNodeDetails(subRootElement, "data"));
					wfTask.setDataStr(getXMLStr(subRootElement, "data"));
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return wfTask;
	}

	/**
	 * Gets the sub node details.
	 * @param element
	 * @param tagName  
	 * @return the sub node details
	 */
	private static Map<String, String> getSubNodeDetails(Element element, String tagName) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nodeList = element.getElementsByTagName(tagName);
		Node node = nodeList.item(0);
		NodeList childNodeList = node.getChildNodes();
		for (int j = 0; j < childNodeList.getLength(); j++) {
			Node childNd = childNodeList.item(j);
			if (childNd.getNodeType() == Node.ELEMENT_NODE && null != childNd.getFirstChild()) {
				map.put(childNd.getNodeName(), childNd.getFirstChild().getNodeValue());
			}
		}
		return map;
	}

	/**
	 * Gets the xML str.
	 * @param element
	 * @param tagName
	 * @return the xML str
	 */
	private static String getXMLStr(Element element, String tagName) {
		StringBuilder xmlStr = new StringBuilder();
		NodeList nodeList = element.getElementsByTagName(tagName);
		Node node = nodeList.item(0);
		NodeList childNodeList = node.getChildNodes();
		xmlStr.append("<" + tagName + ">"); // xml tag start
		for (int k = 0; k < childNodeList.getLength(); k++) {
			Node childNd = childNodeList.item(k);
			if (childNd.getNodeType() == Node.ELEMENT_NODE && null != childNd.getFirstChild())
				xmlStr.append("<" + childNd.getNodeName() + ">").append(childNd.getFirstChild().getNodeValue())
						.append("</" + childNd.getNodeName() + ">");
		}
		xmlStr.append("</" + tagName + ">"); // xml tag end
		return xmlStr.toString();
	}

}
