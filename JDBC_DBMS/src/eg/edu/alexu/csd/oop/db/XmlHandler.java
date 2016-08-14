package eg.edu.alexu.csd.oop.db;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlHandler {
	
	private final int greaterCondition = 1;
	private final int lessCondition = 2;
	private final int equalsCondition = 3;
	private final int elseCondition = 4;
	
	public boolean createXml(String root, String path, String[] names) throws SQLException {
		File tempFile = new File(path + ".xml");
		if (tempFile.exists()) {
			return false;
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(true);
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement(root);
			doc.appendChild(rootElement);
			createDtd(names, path, root);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMSource source = new DOMSource(doc);
			DOMImplementation domImpl = doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype", "-//Oberon//YOUR PUBLIC DOCTYPE//EN",root + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			StreamResult result = new StreamResult(new File(path + ".xml"));
			transformer.transform(source, result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void createDtd(String[] names, String path, String root) {
		File dtd = new File(path + ".dtd");
		try {
			PrintWriter write = new PrintWriter(dtd);
			write.println("<!ELEMENT " + root + " (element)>");
			write.print("<!ELEMENT element (");
			for (int i = 0; i < names.length - 1; i++) {
				String[] type_ = names[i].trim().split(" ");
				write.print(type_[0].trim() + ",");
			}
			write.print(names[names.length - 1].trim().split(" ")[0].trim());
			write.println(")>");
			for (String s : names) {
				String[] type_ = s.split("\\s+");
				write.println(String.format("<!ELEMENT %s (#PCDATA)>", type_[0].trim()));
				write.println(
						String.format("<!ATTLIST %s type CDATA #FIXED \"%s\">", type_[0].trim(), type_[1].trim()));
			}
			write.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int insertNode(String path, String names[]) throws SQLException {
		try {
			File inputFile = new File(path + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			Element rootElement = doc.getDocumentElement();
			Element element = doc.createElement("element");
			Map<String, String> mp = new TreeMap<>();
			String[][] arr = new String[names.length][2];
			BufferedReader rd = new BufferedReader(new FileReader(path + ".dtd"));
			
			rd.readLine();
			String[] tag = rd.readLine().split("(\\(|\\))")[1].split(",");
			rd.readLine();
			for (int i = 0; i < tag.length; i++) {
				String cLine = rd.readLine();
				if (cLine.toLowerCase().contains("varchar")) {
					mp.put(tag[i], "varchar");
				} else {
					mp.put(tag[i], "int");
				}
				rd.readLine();
			}
			rd.close();
			if (!names[0].contains("=")) {
				BufferedReader read = new BufferedReader(new FileReader(path + ".dtd"));
				read.readLine();
				String[] tags = read.readLine().split("(\\(|\\))")[1].split(",");
				read.readLine();
				for (int i = 0; i < tags.length; i++) {
					String currentTag = tags[i];
					String currentLine = read.readLine();
					
					Element z = doc.createElement(currentTag);
					if (currentLine.toLowerCase().contains("varchar")) {
						z.setAttribute("type", "varchar");
					} else {
						z.setAttribute("type", "int");
					}
					if((names[i].toLowerCase().contains("'")&&"varchar".equals(mp.get(tag[i])))||
							(!names[i].toLowerCase().contains("'")&&"int".equals(mp.get(tag[i])))){
						z.appendChild(doc.createTextNode(names[i]));
						element.appendChild(z);
						read.readLine();
					}
					else {
						read.close();
						throw new SQLException();
					}
				}
				read.close();
			} else {
				for (int i = 0; i < names.length; i++) {
					arr[i][0] = names[i].split("\\s*\\=\\s*")[0];
					arr[i][1] = names[i].split("\\s*\\=\\s*")[1];
				}

				for (int i = 0; i < arr.length; i++) {
					Element newElement = doc.createElement(arr[i][0]);
					
					if((names[i].toLowerCase().contains("'")&&mp.get(arr[i][0]).contains("varchar"))||
							(!names[i].toLowerCase().contains("'")&&mp.get(arr[i][0]).contains("int"))){
						if (arr[i][1].contains("'")) {
							newElement.setAttribute("type", "varchar");
						} else {
							newElement.setAttribute("type", "int");
						}
						newElement.appendChild(doc.createTextNode(arr[i][1]));
						element.appendChild(newElement);
					}
					else {
						throw new SQLException();
					}
				}
			}
			rootElement.appendChild(element);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMSource source = new DOMSource(doc);
			DOMImplementation domImpl = doc.getImplementation();
			String[] tempy = path.split("\\" + File.separator);
			DocumentType doctype = domImpl.createDocumentType("doctype", "-//Oberon//YOUR PUBLIC DOCTYPE//EN",
					tempy[tempy.length - 1] + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			StreamResult result = new StreamResult(new File(path + ".xml"));
			transformer.transform(source, result);
			return 1;
		} catch (org.xml.sax.SAXException sExcep) {
			sExcep.printStackTrace();
			throw new SQLException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		}

	}

	public void dropTbl(String tableName) {
		File target = new File(tableName + ".xml");
		if (target.exists())
			target.delete();
		File targetDtd = new File(tableName + ".dtd");
		if (targetDtd.exists())
			targetDtd.delete();
	}

	public int deleteRange(String tableName, String order) {
		int flag = 0, count = 0;
		String tar = new String();
		int condition = -1;
		String[] ranges = null;
		if (order == null)
			flag = 3;
		else {
			if (order.contains(">")) {
				ranges = order.split("\\s*\\>\\s*");
				try {
					condition = Integer.parseInt(ranges[1]);
					flag = greaterCondition;
				} catch (Exception e) {
					throw new RuntimeException();
				}
			} else if (order.contains("<")) {
				ranges = order.split("<");
				try {
					condition = Integer.parseInt(ranges[1]);
					flag = lessCondition;
				} catch (Exception e) {
					throw new RuntimeException();
				}
			} else if (order.contains("=")) {
				ranges = order.split("=");
				tar = ranges[1];
				flag = equalsCondition;
			}
		}
		try {
			File inputFile = new File(tableName + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("element");
			for (int i = 0; i < list.getLength(); i++) {
				Node current = list.item(i);
				if (current.getNodeType() == Node.ELEMENT_NODE) {
					Element target = (Element) current;
					NodeList nodeList = null;
					Element trgt = null;
					if (order != null) {
						nodeList = target.getElementsByTagName(ranges[0]);
						if (nodeList.getLength() == 0)
							throw new RuntimeException();
						trgt = (Element) nodeList.item(0);
					}

					if (flag == greaterCondition) {
						if (Integer.parseInt(trgt.getTextContent()) > condition) {
							doc.getDocumentElement().removeChild(current);
							count++;
							i--;
						}
					} else if (flag == lessCondition) {
						if (Integer.parseInt(trgt.getTextContent()) < condition) {
							doc.getDocumentElement().removeChild(current);
							count++;
							i--;
						}
					} else if (order == null || flag == equalsCondition) {
						if (order == null || trgt.getTextContent().equals(tar)) {
							doc.getDocumentElement().removeChild(current);
							count++;
							i--;
						}
					}
				}
			}
			inputFile.delete();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMSource source = new DOMSource(doc);
			DOMImplementation domImpl = doc.getImplementation();
			String[] tempy = tableName.split("\\" + File.separator);
			DocumentType doctype = domImpl.createDocumentType("doctype", "-//Oberon//YOUR PUBLIC DOCTYPE//EN",
					tempy[tempy.length - 1] + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			StreamResult result = new StreamResult(new File(tableName + ".xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public  ResultInfo select(String[] selections, String tableName, String condition) {
		ResultInfo myRes = new ResultInfo();
		LinkedList<Object[]> ret = new LinkedList<Object[]>();
		LinkedList <String> types = new LinkedList<String>();
		try {
			File inputFile = new File(tableName + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			System.out.println(inputFile);
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList list = doc.getElementsByTagName("element");
			String[] parameters = new String[2];
			
			int cond = 0;
			if (condition != null && condition.contains(">"))
				cond = greaterCondition;
			else if (condition != null && condition.contains("<"))
				cond = lessCondition;
			else if (condition != null && condition.contains("="))
				cond = equalsCondition;
			else
				cond = elseCondition;
			if (cond != elseCondition)
				parameters = condition.split("\\s*[<>=]\\s*");

			if ("*".equals(selections[0])) {				
				BufferedReader rd = new BufferedReader(new FileReader(tableName + ".dtd"));
				rd.readLine();
				String line = rd.readLine();
				String[] tag = line.split("(\\(|\\))")[1].split(",");
				rd.close();
				selections = tag;
				
				
			}
			LinkedList <String> MyResultTag = new LinkedList <String>();
			for(String temp:selections)
				MyResultTag.add(temp);
			myRes.setResultCols(MyResultTag);

			for (int i = 0; i < list.getLength(); i++) {
				Node current = list.item(i);
				
				if (current.getNodeType() == Node.ELEMENT_NODE) {
					Element target = (Element) current;
					
					if (cond == greaterCondition) {
						NodeList nd = target.getElementsByTagName(parameters[0]);
						if (Integer.parseInt(nd.item(0).getTextContent()) > Integer.parseInt(parameters[1])) {
							LinkedList<Object> ll = new LinkedList<Object>();
							for (int j = 0; j < selections.length; j++) {
								ll.add(target.getElementsByTagName(selections[j]).item(0).getTextContent());
								types.add(target.getElementsByTagName(selections[j]).item(0).getAttributes().item(0).getTextContent());
							}
							ret.add(ll.toArray());
						}
					} else if (cond == lessCondition) {
						NodeList nd = target.getElementsByTagName(parameters[0]);
						if (Integer.parseInt(nd.item(0).getTextContent()) < Integer.parseInt(parameters[1])) {
							LinkedList<Object> ll = new LinkedList<Object>();
							for (int j = 0; j < selections.length; j++) {
								ll.add(target.getElementsByTagName(selections[j]).item(0).getTextContent());
								types.add(target.getElementsByTagName(selections[j]).item(0).getAttributes().item(0).getTextContent());
							}
							ret.add(ll.toArray());
						}
					} else if (cond == equalsCondition || cond == elseCondition) {
						NodeList nd = null;
						if (cond == equalsCondition)
							nd = target.getElementsByTagName(parameters[0]);
						if (cond == elseCondition || nd.item(0).getTextContent().equals(parameters[1])) {
							LinkedList<Object> ll = new LinkedList<Object>();
							for (int j = 0; j < selections.length; j++) {
								ll.add(target.getElementsByTagName(selections[j]).item(0).getTextContent());
								types.add(target.getElementsByTagName(selections[j]).item(0).getAttributes().item(0).getTextContent());
							}
							ret.add(ll.toArray());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int tiempo = ret.size();
		if (tiempo == 0)
			myRes.setResult(new Object[0][0]);
		else {
			Object gattaca[][] = new Object[tiempo][ret.get(0).length];
			for (int i = 0; i < tiempo; i++)
				gattaca[i] = ret.get(i);
			myRes.setResult(gattaca);
		}
		myRes.setResultType(types);
		return myRes;
	}

	public int update(String tableName, String[] selections, String condition) throws SQLException {
		int count = 0;
		try {
			File inputFile = new File(tableName + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			String[] parameters = new String[2];
			int cond = 0;
			String[][] arr = new String[selections.length][2];
			
			for (int i = 0; i < selections.length; i++) {
				arr[i][0] = selections[i].split("\\s*\\=\\s*")[0];
				arr[i][1] = selections[i].split("\\s*\\=\\s*")[1];
			}
			if (condition != null) {
				if (condition.contains(">"))
					cond = greaterCondition;
				else if (condition.contains("<"))
					cond = lessCondition;
				else if (condition.contains("="))
					cond = equalsCondition;
				else
					cond = elseCondition;
			} else
				cond = elseCondition;
			if (cond != elseCondition)
				parameters = condition.split("\\s*[<>=]\\s*");

			NodeList list = doc.getElementsByTagName("element");
			for (int i = 0; i < list.getLength(); i++) {
				Node current = list.item(i);
				if (current.getNodeType() == Node.ELEMENT_NODE) {
					Element target = (Element) current;
					if ("*".equals(selections[0])) {
						NodeList nlist = target.getChildNodes();
						String[] temp = new String[nlist.getLength()];
						for (int x = 0; x < nlist.getLength(); x++)
							temp[x] = nlist.item(x).getNodeName();

						selections = temp;
					}
					if (cond == greaterCondition) {
						NodeList nd = target.getElementsByTagName(parameters[0]);
						if (Integer.parseInt(nd.item(0).getTextContent()) > Integer.parseInt(parameters[1])) {
							count++;
							for (int j = 0; j < selections.length; j++)
								target.getElementsByTagName(arr[j][0]).item(0).setTextContent(arr[j][1]);
						}
					} else if (cond == lessCondition) {
						count++;
						NodeList nd = target.getElementsByTagName(parameters[0]);
						if (Integer.parseInt(nd.item(0).getTextContent()) < Integer.parseInt(parameters[1])) {
							for (int j = 0; j < selections.length; j++)
								target.getElementsByTagName(arr[j][0]).item(0).setTextContent(arr[j][1]);
						}
					} else if (cond == equalsCondition || cond == elseCondition) {
						NodeList nd = null;
						if (cond == equalsCondition)
							nd = target.getElementsByTagName(parameters[0]);
						if (cond == elseCondition || nd.item(0).getTextContent().equals(parameters[1])) {
							count++;
							for (int j = 0; j < selections.length; j++)
								target.getElementsByTagName(arr[j][0]).item(0).setTextContent(arr[j][1]);
						}
					}
				}
			}
			inputFile.delete();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMSource source = new DOMSource(doc);
			DOMImplementation domImpl = doc.getImplementation();
			String[] tempy = tableName.split("\\" + File.separator);
			DocumentType doctype = domImpl.createDocumentType("doctype", "-//Oberon//YOUR PUBLIC DOCTYPE//EN",
					tempy[tempy.length - 1] + ".dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			StreamResult result = new StreamResult(new File(tableName + ".xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		}
		return count;
	}

}
