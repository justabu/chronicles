package chronicles.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public class ChroniclesHibernateDAOSupport<Model> extends HibernateDaoSupport implements ChroniclesDAOSupport<Model> {
    
	@Override
    public boolean update(Model model) {
		try {
        	getHibernateTemplate().merge(model);
            return true;
        } catch (DataAccessException e) {
        	e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(Model model) {
        try {
            getHibernateTemplate().save(model);
        } catch (DataAccessException e) {
        	e.printStackTrace();
        	System.out.println(e);
            return false;
        }
        return true;
    }
    
	@Override
	public List<Model> findAll(String modelName) {
		try {
			return getHibernateTemplate().find(String.format("from %s", modelName));
		} catch (DataAccessException e) {
			return new ArrayList<Model>();
		} 
	}

	@Override
	public Integer countAll(String modelName) {
		try {
			return DataAccessUtils.intResult(getHibernateTemplate().find(
					String.format("select count(*) from %s", modelName))
				);
		} catch (DataAccessException e) {
			return 0;
		}
	}

	@Override
	public boolean delete(Model model) {
		try {
			getHibernateTemplate().delete(model);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public Model findBy(String modelName, SearchCriteria ... criterias) {
		String query = String.format("from %s model where %s", modelName, createConstraint(criterias, " and "));
		try {
 			List results = getHibernateTemplate().find(query);
 			return (Model) DataAccessUtils.uniqueResult(results);
		} catch (DataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Model> findAllBy(String modelName,SearchCriteriaEquals criterion, SearchCriteria ... criterias) {
		String query = String.format(
			"from %s model where %s (%s)", modelName, 
			createConstraint(criterion, " and "), 
			createConstraint(criterias, " or ")
		);
		try {
			return (List<Model>) getHibernateTemplate().find(query);
		} catch (DataAccessException e) {
			return null;
		}
	}
    
	private String createConstraint(SearchCriteriaEquals criterion,
			String booleanConector) {
		String constraint = "";
		return constraint + criterion.toHQL() + booleanConector;
	}

	private String createConstraint(SearchCriteria[] criterias, String booleanConector) {
		String constraint = "";
		for(int i=0; i<criterias.length - 1; i++) {
			constraint += (criterias[i].toHQL() + booleanConector); 
		}
		return constraint + criterias[criterias.length - 1].toHQL();
	}
}