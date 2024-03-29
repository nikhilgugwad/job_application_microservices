package com.jobapplication.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import com.jobapplication.companyms.company.Company;
import com.jobapplication.companyms.company.CompanyRepository;
import com.jobapplication.companyms.company.CompanyService;
import com.jobapplication.companyms.company.client.ReviewClient;
import com.jobapplication.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
  private CompanyRepository companyRepository;
  private ReviewClient reviewClient;

  public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
    this.companyRepository = companyRepository;
    this.reviewClient = reviewClient;
  }

  @Override
  public List<Company> getAllCompanies() {
    return companyRepository.findAll();
  }

  @Override
  public boolean updateCompany(Company company, Long id) {
    Optional<Company> companyOptional = companyRepository.findById(id);
    if (companyOptional.isPresent()) {
      Company companytoUpdate = companyOptional.get();
      companytoUpdate.setDescription(company.getDescription());
      companytoUpdate.setDescription(company.getDescription());
      companytoUpdate.setName(company.getName());

      companyRepository.save(companytoUpdate);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void createCompany(Company company) {
    companyRepository.save(company);
  }

  @Override
  public boolean deleteCompanyById(Long id) {
    if (companyRepository.existsById(id)) {
      companyRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Company getCompanyById(Long id) {
    return companyRepository.findById(id).orElse(null);
  }

  @Override
  public void updateCompanyRating(ReviewMessage reviewMessage) {
    System.out.println(reviewMessage.getDescription());
    Company company =
        companyRepository
            .findById(reviewMessage.getCompanyId())
            .orElseThrow(
                () -> new NotFoundException("Company not found" + reviewMessage.getCompanyId()));
    double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
    company.setRating(averageRating);
    companyRepository.save(company);
  }
}
