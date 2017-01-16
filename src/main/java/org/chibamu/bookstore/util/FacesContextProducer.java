package org.chibamu.bookstore.util;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

@Stateless
public class FacesContextProducer {

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext(){
		return FacesContext.getCurrentInstance();
	}
}
