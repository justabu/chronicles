package chronicles.repository;

public class SearchCriteriaEquals implements SearchCriteria {

	private String column;
	private String value;

	public SearchCriteriaEquals(String column, String value) {
		this.column = column;
		this.value = value;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchCriteriaEquals other = (SearchCriteriaEquals) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String toHQL(){
		return String.format("model.%s='%s'", column, value);
	}


}