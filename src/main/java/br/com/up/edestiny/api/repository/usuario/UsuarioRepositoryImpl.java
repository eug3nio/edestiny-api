package br.com.up.edestiny.api.repository.usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.dto.UsuarioDTO;
import br.com.up.edestiny.api.repository.filter.UsuarioFilter;


@SuppressWarnings("unchecked")
public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT u FROM Usuario u ");
		sql.append(" INNER JOIN u.empresa e ");
		sql.append(" WHERE admin IS FALSE ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<UsuarioDTO> resumir(UsuarioFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new br.com.up.edestiny.api.repository.dto.UsuarioDTO(u) FROM Usuario u ");
		sql.append(" INNER JOIN u.empresa e ");
		sql.append(" WHERE admin IS FALSE ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

		adicionarRestricoesDePaginacao(q, pageable);

		return new PageImpl<>(q.getResultList(), pageable, total(filter));
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private String getWhere(UsuarioFilter filter) {
		StringBuilder where = new StringBuilder();

		if (!StringUtils.isEmpty(filter.getEmpresaId())) {
			where.append(" AND e.id = :empresaId ");
		}

		if (!StringUtils.isEmpty(filter.getNome())) {
			where.append(" AND LOWER(u.nome) LIKE :nome ");
		}

		if (!StringUtils.isEmpty(filter.getEmail())) {
			where.append(" AND LOWER(u.email) LIKE :email ");
		}

		return where.toString();
	}

	/**
	 * 
	 * @param q
	 * @param filter
	 */
	private void setParameter(Query q, UsuarioFilter filter) {

		if (!StringUtils.isEmpty(filter.getEmpresaId())) {
			q.setParameter("empresaId", Long.parseLong(filter.getEmpresaId()));
		}

		if (!StringUtils.isEmpty(filter.getNome())) {
			q.setParameter("nome", filter.getNome().toLowerCase() + "%");
		}

		if (!StringUtils.isEmpty(filter.getEmail())) {
			q.setParameter("email", filter.getEmail().toLowerCase() + "%");
		}

	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private Long total(UsuarioFilter filter) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(u) FROM Usuario u ");
		sql.append(" INNER JOIN u.empresa e ");
		sql.append(" WHERE admin IS FALSE ").append(getWhere(filter));

		Query q = manager.createQuery(sql.toString());

		setParameter(q, filter);

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
