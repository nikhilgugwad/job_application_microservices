package com.jobapplication.jobms.job;

import java.util.List;

import com.jobapplication.jobms.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {
  private JobService jobService;

  public JobController(JobService jobservice) {
    this.jobService = jobservice;
  }

  @GetMapping
  public ResponseEntity<List<JobDTO>> findAll() {
    return ResponseEntity.ok(jobService.findAll());
  }

  @PostMapping
  public ResponseEntity<String> createJob(@RequestBody Job job) {
    jobService.createJob(job);
    return new ResponseEntity<>("Job added successfully", HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
    JobDTO jobDTO = jobService.getJobById(id);
    if (jobDTO != null) return new ResponseEntity<>(jobDTO, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteJob(@PathVariable Long id) {
    boolean deleted = jobService.deleteById(id);
    if (deleted) return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}")
  // @RequestMapping(value = "/jobs/{id}", method = RequestMethod.PUT)
  public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
    boolean updated = jobService.updateJob(id, updatedJob);
    if (updated) return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
