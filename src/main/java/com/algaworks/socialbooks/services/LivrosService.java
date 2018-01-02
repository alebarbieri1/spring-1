package com.algaworks.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.ComentariosRepository;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {
	
	@Autowired
	private LivrosRepository livrosRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	public List<Livro> listar(){
		return livrosRepository.findAll();
	}
	
	public Livro buscar(Long id) {
		Livro livro = livrosRepository.findOne(id);
		
		if (livro == null) {
			throw new LivroNaoEncontradoException("O livro " + id.toString() + " não foi encontrado");
		}
		
		return livro;
	}
	
	public Livro salvar(Livro livro) {
		// Garante que um novo livro seja criado e nenhum outro já existente seja atualizado
		livro.setId(null);
		return livrosRepository.save(livro);
	}
	
	public void remover(Long id) {
		try {
			livrosRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("O livro " + id.toString() + " não foi encontrado");
		}
	}
	
	public void atualizar(Livro livro) {
		verificarExistencia(livro);
		// Método save: se a entidade a ser persistida já existir no banco de dados, ela será atualizada, senão, será criada
		livrosRepository.save(livro);
	}
	
	// Método criado para melhorar a semântica
	private void verificarExistencia(Livro livro) {
		buscar(livro.getId());
	}
	
	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		Livro livro = buscar(livroId);
		comentario.setLivro(livro);
		comentario.setData(new Date());
		
		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long livroId){
		Livro livro = buscar(livroId);
		
		return livro.getComentarios();
	}
}
