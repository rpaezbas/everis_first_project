package controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//This class represents the JSON  the POST and PUT endpoints consume. Since the timestamps are recieved as longs, the class Car does the conversion
//To insert in Database

@XmlRootElement
public class JSONRequest {
	@XmlElement public int id;
	@XmlElement public String country;
    @XmlElement public long registration;
    @XmlElement public String brand;
    @XmlElement public long createdAt;
    @XmlElement public long lastUpdated;
}
