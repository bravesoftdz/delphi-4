package edu.mq.comp.delphi.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMFService {
  private static final PMFService INSTANCE = new PMFService();
  private final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactional");

  public static final PMFService getInstance() {
	return INSTANCE;
  }

  public PersistenceManagerFactory getPersistenceManagerFactory() {
	return pmf;
  }

  private PMFService() {
  }
}
