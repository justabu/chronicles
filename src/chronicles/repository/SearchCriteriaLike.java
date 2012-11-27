package chronicles.repository;

public class SearchCriteriaLike implements SearchCriteria {

		
	private final String column;
	private final String value;
	

	public SearchCriteriaLike(String column, String value){
		this.column = column; 
		this.value = value;
	}
	
	public String toHQL() {
		String likeValue = "%" + value + "%";
		return String.format("model.%s like \'%s\'", column, likeValue);
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
		SearchCriteriaLike other = (SearchCriteriaLike) obj;
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
}