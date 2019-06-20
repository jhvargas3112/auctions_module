package org.openbravo.auction.datasource;

import java.util.List;
import java.util.Map;

import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.businesspartner.Category;
import org.openbravo.service.datasource.BaseDataSourceService;

/**
 * @author Jhonny Vargas.
 */

public class BidderDataSource extends BaseDataSourceService {

  @Override
  public String fetch(Map<String, String> parameters) {

    List<Category> bidders = OBDal.getInstance().createQuery(Category.class, "").list();
    int i = 0;

    return null;
  }

  @Override
  public String remove(Map<String, String> parameters) {
    return null;
  }

  @Override
  public String add(Map<String, String> parameters, String content) {
    return null;
  }

  @Override
  public String update(Map<String, String> parameters, String content) {
    return null;
  }

}
