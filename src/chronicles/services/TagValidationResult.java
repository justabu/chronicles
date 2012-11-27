package chronicles.services;

import java.util.ArrayList;
import java.util.List;

public class TagValidationResult {
	private List<String> invalidTags = new ArrayList<String>();
	private Boolean updateResult;
      
	public TagValidationResult(List<String> invalidTags, Boolean updateResult) {
        this.invalidTags = invalidTags;
        this.updateResult= updateResult; 
        }

	public Boolean hasAnInvalidTag() {
		return(invalidTags.size()>0);
			
	}

	public List<String> getInvalidTags() {
		return invalidTags;
	}

	public Boolean isUpdateSuccessful() {
		return updateResult;
	}

}
