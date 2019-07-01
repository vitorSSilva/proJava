package bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import dao.DAO;
import modelo.Autor;

@ManagedBean
public class AutorBean {

	private Autor autor = new Autor();
	private Integer autorId;
	

	public Autor getAutor() {
		return autor;
	}
	
	public Integer getAutorId() {
	    return autorId;
	}

	public void setAutorId(Integer autorId) {
	    this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
	    this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}


	public void carregar(Autor autor) {
		System.out.println("Carregando autor");
		this.autor = autor;
	}

	public void remover(Autor autor) {
		System.out.println("Removendo autor");
		new DAO<Autor>(Autor.class).remove(autor);
	}

	
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}

		this.autor = new Autor();
		
		return "livro?faces-redirect=true";
	}
}
