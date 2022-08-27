package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.companyservice.companyservice.entity.*;


@Repository
public interface TemplateRepository extends CrudRepository<Template, String>,PagingAndSortingRepository<Template, String>{
	
	Template getById(String id);

	boolean existsByName(String name);

	Page<Template> findByCompanyIdOrderByUpdatedAtDesc(Company companyId,Pageable paging);
	Page<Template> findByAdminIdOrderByUpdatedAtDesc(AdminUser adminId,Pageable paging);
	Page<Template> findAllByOrderByUpdatedAtDesc(Pageable paging);
	@Query(value = "select * from outreach.template as t where t.company_id is null order by updated_at desc", nativeQuery = true)
	Page<Template> getByAdminId(Pageable paging);
	@Query(value = "select * from outreach.template as t where t.company_id is null order by updated_at desc", nativeQuery = true)
	List<Template> getByAdminId();
	@Query(value = "select * from outreach.template as t where t.company_id =?1  OR t.company_id is null", nativeQuery = true)
	List<Template> getByCompanyId(Company companyId);
}
