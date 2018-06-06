package brands.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity("brand")
public class Brand {

	@Id
	@Property("id")
	private String id;

	private String name;

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return " name: " + this.name;
	}
	
	public String getId() {
		return this.id;
	}

}
