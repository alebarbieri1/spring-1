package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.LivrosService;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

// Indica que a classe é um resource
@RestController
@RequestMapping("/livros")
public class LivrosResource {
	
	// Busca a instância da classe criada pelo Spring e insere no atributo
	@Autowired
	private LivrosService livrosService;
	
	// Mapeia uma URI para um determinado método
	// value = URI
	// method = Método HTTP
	//@RequestMapping(value = "/livros", method = RequestMethod.GET)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Livro>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
	}
	
	// POST = criação de novos recursos
	// @RequestBody = faz com que o Spring pegue o que está vindo na requisição e coloque as informações no objeto Livro
	@RequestMapping(method = RequestMethod.POST)
	// Void pois não terá retorno, será sempre vazio
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
		// Retorna o mesmo objeto com o id criado
		livro = livrosService.salvar(livro);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(livro.getId()).toUri();
		// Retorna a URI em Location (response header) para acessar o recurso criado
		// created = HTTP 201
		return ResponseEntity.created(uri).build();
	}
	
	// Pega o valor recebido na URI e insere na variável id
	// ResponseEntity = responsável por encapsular o objeto de retorno e de manipular informações do HTTP
	// ? = encapsula qualquer tipo de objeto. Ou seja, ? = qualquer tipo
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Livro livro;
		try {
			livro = livrosService.buscar(id);
		} catch (LivroNaoEncontradoException e) {
			// build = constrói a entidade de resposta sem nada no body
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}
		
	
	// Pega o valor recebido na URI e insere na variável id
	@RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
		try {
			livrosService.remover(id);
		}  catch (LivroNaoEncontradoException e) {
			// Caso não exista, retorna not found (http 404)
			return ResponseEntity.notFound().build();
		}
		// Se existir, retorna no content (http 204)
		// No Content: indica que a solicitação foi bem sucedida
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
		// Garante que o recurso a ser atualizado é o que está sendo passado no corpo da URI e não no corpo da mensagem
		livro.setId(id);
		try {
			livrosService.atualizar(livro);
		} catch(LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
