package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class BigShape implements Shape{

	protected Point myPos=new Point(0, 0);
	protected Map<String, Double> myProp=new TreeMap<>();
	protected Color myColor=Color.BLACK;
	protected Color myFillColor=Color.BLACK;
	
	@Override
	public void setPosition(Point position) {
		if(position==null)return;
		myPos = new Point(position);
	}
	@Override
	public Point getPosition() {
		return myPos;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		if(properties==null)return;
		for(Entry<String, Double> mp : properties.entrySet()){
			if(myProp.containsKey(mp.getKey()))
				myProp.put(mp.getKey(), mp.getValue());
		}
	}

	@Override
	public Map<String, Double> getProperties() {
		return myProp;
	}

	@Override
	public void setColor(Color color) {
		myColor = color;
	}

	@Override
	public Color getColor() {
		return myColor;
	}

	@Override
	public void setFillColor(Color color) {
		myFillColor = color;
	}

	@Override
	public Color getFillColor() {
		return myFillColor;
	}
	@Override
	public Object clone()  {
		Shape tmp;
		try {
			tmp = this.getClass().newInstance();
			tmp.setPosition(this.getPosition());
			tmp.setProperties(this.getProperties());
			tmp.setColor(this.myColor);
			tmp.setFillColor(this.myFillColor);
			return tmp;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}
}
