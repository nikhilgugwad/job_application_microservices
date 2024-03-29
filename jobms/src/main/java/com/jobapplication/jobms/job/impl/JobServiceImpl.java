package com.jobapplication.jobms.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jobapplication.jobms.job.Job;
import com.jobapplication.jobms.job.JobRepository;
import com.jobapplication.jobms.job.JobService;
import com.jobapplication.jobms.job.clients.CompanyClient;
import com.jobapplication.jobms.job.clients.ReviewClient;
import com.jobapplication.jobms.job.dto.JobDTO;
import com.jobapplication.jobms.job.external.Company;
import com.jobapplication.jobms.job.external.Review;
import com.jobapplication.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobServiceImpl implements JobService {
  // private List<Job> jobs = new ArrayList<>();
  JobRepository jobRepository;

  @Autowired RestTemplate restTemplate;

  private CompanyClient companyClient;
  private ReviewClient reviewClient;

  int attempt = 0;

  public JobServiceImpl(
      JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
    this.jobRepository = jobRepository;
    this.companyClient = companyClient;
    this.reviewClient = reviewClient;
  }

  @Override
  // @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
  // @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
  @RateLimiter(name = "companyBreaker")
  public List<JobDTO> findAll() {
    System.out.println("Attempt :" + ++attempt);
    List<Job> jobs = jobRepository.findAll();
    List<JobDTO> jobDTOS = new ArrayList<>();

    return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
  }

//  public List<String> companyBreakerFallback(Exception e) {
//    List<String> list = new ArrayList<>();
//    list.add("dummy");
//    return list;
//  }

  private JobDTO convertToDto(Job job) {

    // RestTemplate restTemplate = new RestTemplate();
    Company company = companyClient.getCompany(job.getCompanyId());

    List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

    JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);
    // jobDTO.setCompany(company);
    return jobDTO;
  }

  @Override
  public void createJob(Job job) {

    jobRepository.save(job);
  }

  @Override
  public JobDTO getJobById(Long id) {
    Job job = jobRepository.findById(id).orElse(null);
    return convertToDto(job);
  }

  @Override
  public boolean deleteById(Long id) {
    try {
      jobRepository.deleteById(id);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean updateJob(Long id, Job updatedJob) {
    Optional<Job> jobOptional = jobRepository.findById(id);
    if (jobOptional.isPresent()) {
      Job job = jobOptional.get();
      job.setTitle(updatedJob.getTitle());
      job.setDescription(updatedJob.getDescription());
      job.setMinSalary(updatedJob.getMinSalary());
      job.setMaxSalary(updatedJob.getMaxSalary());
      job.setLocation(updatedJob.getLocation());
      jobRepository.save(job);
      return true;
    }

    return false;
  }
}
