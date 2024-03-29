package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import dao.DAO;
import modelo.Autor;
import modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Livro livro = new Livro();
    private Integer livroId;
    private Integer autorId;
    
    
    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }

    public void carregaPelaId() {
        this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
    }
    
    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public Livro getLivro() {
        return livro;
    }

    public List<Livro> getLivros() {
        return new DAO<Livro>(Livro.class).listaTodos();
    }

    public List<Autor> getAutores() {
        return new DAO<Autor>(Autor.class).listaTodos();
    }

    public List<Autor> getAutoresDoLivro() {
        return this.livro.getAutores();
    }

    public void gravarAutor() {
        Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
        this.livro.adicionaAutor(autor);
    }

    public void gravar() {
        System.out.println("Gravando livro " + this.livro.getTitulo());

        if (livro.getAutores().isEmpty()) {
          //  throw new RuntimeException("Livro deve ter pelo menos um Autor.");
            FacesContext.getCurrentInstance().addMessage("autor",  new FacesMessage("Livro deve ter pelo menos um Autor"));
            return;
        }

        if (this.livro.getId() == null) {
            new DAO<Livro>(Livro.class).adiciona(this.livro);        
        } else {
            new DAO<Livro>(Livro.class).atualiza(this.livro);
        }        
        
        this.livro = new Livro();
    }
    
    public void remover(Livro livro) {
        System.out.println("Removendo livro " + livro.getTitulo());
        new DAO<Livro>(Livro.class).remove(livro);
       
    }
    
    
    public void removerAutorDoLivro(Autor autor) {
        this.livro.removeAutor(autor);
    }

    public void carregar(Livro livro) {
        System.out.println("Carregando livro " + livro.getTitulo());
        this.livro = livro;
    }    
    
	public String formAutor() {
		System.out.println("Chamanda do formulario do Autor.");
		return "autor?faces-redirect=true";
	}

    
    public void comecaComDigitoUm(FacesContext fc, UIComponent component,
			Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"ISBN deveria come�ar com 1"));
		}

	}
}
