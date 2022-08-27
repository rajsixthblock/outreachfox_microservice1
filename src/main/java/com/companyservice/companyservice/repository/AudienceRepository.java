package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Audience;
import com.companyservice.companyservice.entity.Campaign;
import com.companyservice.companyservice.entity.Company;






@Repository
public interface AudienceRepository extends CrudRepository<Audience, String>,PagingAndSortingRepository<Audience, String> {
	boolean existsByEmailAndCompanyId(String email,Company companyId);
	Audience getByEmailAndCompanyId(String email,Company companyId);
	boolean existsById(String id);
	Audience getById(String id);
	List<Audience> findByCompanyId(Company companyId);
	Page<Audience> findByCompanyId(Company companyId,Pageable paging);
	Page<Audience> findByCampaignId(Campaign campaginId,Pageable paging);
	List<Audience> findByCampaignId(Campaign campaginId);
	List<Audience> findByEmailInAndCompanyId(List<String> email,Company companyId);
	long countByCampaignId(Campaign campaign);
	Page<Audience> getByCompanyIdAndName(Company company,String name,Pageable paging);
	Page<Audience> getByCompanyIdAndEmail(Company company,String email,Pageable paging);
	Page<Audience> getByCompanyIdAndPhone(Company company,long phone,Pageable paging);
	Page<Audience> getByCompanyIdAndNameAndEmailAndPhone(Company company,String name,String email,long phone,Pageable paging);
	Page<Audience> getByCompanyIdAndNameAndEmail(Company company,String name,String email,Pageable paging);
	Page<Audience> getByCompanyIdAndNameAndPhone(Company company,String name,long phone,Pageable paging);
	Page<Audience> getByCompanyIdAndEmailAndPhone(Company company,String email,long phone,Pageable paging);
	
	Page<Audience> getByCampaignIdAndName(Campaign campaign,String name,Pageable paging);
	Page<Audience> getByCampaignIdAndEmail(Campaign campaign,String email,Pageable paging);
	Page<Audience> getByCampaignIdAndPhone(Campaign campaign,long phone,Pageable paging);
	Page<Audience> getByCampaignIdAndNameAndEmailAndPhone(Campaign campaign,String name,String email,long phone,Pageable paging);
	Page<Audience> getByCampaignIdAndNameAndEmail(Campaign campaign,String name,String email,Pageable paging);
	Page<Audience> getByCampaignIdAndNameAndPhone(Campaign campaign,String name,long phone,Pageable paging);
	Page<Audience> getByCampaignIdAndEmailAndPhone(Campaign campaign,String email,long phone,Pageable paging);
	
	long countByCompanyId(Company companyId);
	
	@Query(value = "select count(audience_id),c.title from outreach.audience_campaign_id as a,outreach.campaign as c where c.campaign_id = a.campaign_id_campaign_id group by campaign_id_campaign_id", nativeQuery = true)
	List getAudienceCoutByComapaignId();
	
	@Query(value = "select count(id),c.name from outreach.audience as aud,outreach.company as c where c.company_id = aud.company_id group by c.name", nativeQuery = true)
	List getAudienceCountByCompanyId();
}
