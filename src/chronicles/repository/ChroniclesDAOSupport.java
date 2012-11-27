package chronicles.repository;

import java.util.List;

import chronicles.models.Story;

public interface ChroniclesDAOSupport<Model> {
   
	boolean update(Model model);

    boolean save(Model model);

    boolean delete(Model model);

    Integer countAll(String modelName);

	List<Model> findAll(String string);

	Model findBy(String modelName, SearchCriteria ... searchCriteria);

	List<Model> findAllBy(String modelName, SearchCriteriaEquals criterion ,  SearchCriteria ... criteria);
	
}


