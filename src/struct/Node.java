package struct;

/***
 * 
 * @author Hernan
 * Representa un usuario de la red social
 * Se identifica mediante el campo Integer id
 *
 */
public class Node {

	protected Integer id;

	public Node(Integer id) {
		super();
		this.id = id;
	}
	
	public Integer getID() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// Verificar que sea subclase
		//if (getClass() != obj.getClass())
		//	return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + id;
	}

}
