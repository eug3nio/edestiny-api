package br.com.up.edestiny.api.repository.solicitacao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.dto.SolicitacaoDTO;

@SuppressWarnings("unchecked")
public class SolicitacaoRepositoryImpl implements SolicitacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Solicitacao> filtrar(Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT s FROM Solicitacao s ");
		sql.append(getWhere());

		Query q = manager.createQuery(sql.toString());

		setParameter(q);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total());
	}

	@Override
	public Page<SolicitacaoDTO> resumir(Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new br.com.up.edestiny.api.repository.dto.SolicitacaoDTO(s) FROM Solicitacao s ");
		sql.append(getWhere());

		Query q = manager.createQuery(sql.toString());

		setParameter(q);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total());
	}

	/**
	 * 
	 * @return
	 */
	private String getWhere() {
		StringBuilder where = new StringBuilder();

		where.append(" WHERE s.situacao = :situacao ");

		return where.toString();
	}

	/**
	 * 
	 * @param q
	 */
	private void setParameter(Query q) {
		q.setParameter("situacao", SituacaoSolicitacao.ABERTA);
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private Long total() {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(s) FROM Solicitacao s ");

		Query q = manager.createQuery(sql.toString());

		Number result = q.getFirstResult();

		return result.longValue();
	}

	/**
	 * 
	 * @param q
	 * @param pageable
	 */
	private void adicionarRestricoesDePaginacao(Query q, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		q.setFirstResult(primeiroRegistroDaPagina);
		q.setMaxResults(totalRegistrosPorPagina);
	}
}
