package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.companyservice.companyservice.entity.Campaign;
import com.companyservice.companyservice.entity.Company;



public interface CampaignRepository extends CrudRepository<Campaign,String>,PagingAndSortingRepository<Campaign, String> {
	
	boolean existsByTitleAndCompanyId(String title, Company companyId);
	boolean existsByCampaignId(String campaignId);
	Campaign getByCampaignId(String campaignId);
	List<Campaign> findByCompanyId(Company companyId);
	Page<Campaign> findByCompanyId(Company companyId,Pageable paging);
	Page<Campaign> getByCompanyIdAndNameAndEmailAndTitle(Company company,String name,String email_id, String c_title,Pageable paging);
	Page<Campaign> getByCompanyIdAndNameAndEmail(Company company,String name,String email_id,Pageable paging);
	Page<Campaign> getByCompanyIdAndEmailAndTitle(Company company,String email_id,String c_title,Pageable paging);
	Page<Campaign> getByCompanyIdAndNameAndTitle(Company company,String name, String c_title,Pageable paging);
	Page<Campaign> getByCompanyIdAndName(Company company,String name,Pageable paging);
	Page<Campaign> getByCompanyIdAndEmail(Company company,String email_id,Pageable paging);
	Page<Campaign> getByCompanyIdAndTitle(Company company,String c_title,Pageable paging);
	
	long countByCompanyId(Company companyId);
	
	@Query(value = "select count(campaign_id),c.name from outreach.campaign as camp,outreach.company as c where c.company_id = camp.company_id group by c.name", nativeQuery = true)
	List getCampaignsCountByCompanyId();
	
	//long countByCompanyId(Company company);
}
