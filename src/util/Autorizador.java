package util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import modelo.Usuario;

public class Autorizador implements PhaseListener {

    private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
	    String nomePagina = context.getViewRoot().getViewId();

	    System.out.println(nomePagina);
	    
	    if("/login.xhtml".equals(nomePagina)) {
	        return;
	    }
	    
	    Usuario usuarioLogado = (Usuario) context.getExternalContext()
                .getSessionMap().get("usuarioLogado");

	    if(usuarioLogado != null) {
	    	return;
	    	}
	    
	    NavigationHandler handler = context.getApplication().getNavigationHandler();
	    handler.handleNavigation(context, null, "/login?faces-redirect=true");

	    context.renderResponse();        
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}
}
