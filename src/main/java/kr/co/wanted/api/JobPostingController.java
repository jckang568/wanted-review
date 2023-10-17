package kr.co.wanted.api;

import kr.co.wanted.common.http.ApiResult;
import kr.co.wanted.domain.entity.JobPosting;
import kr.co.wanted.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobpostings")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @Autowired
    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @PostMapping
    public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPosting jobPosting) {
        JobPosting savedJobPosting = jobPostingService.createJobPosting(jobPosting);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJobPosting);
    }

    @PutMapping("/{jobPostingId}")
    public ResponseEntity<JobPosting> updateJobPosting(@PathVariable Long jobPostingId, @RequestBody JobPosting updatedJobPosting) {
        JobPosting updatedPosting = jobPostingService.updateJobPosting(jobPostingId, updatedJobPosting);

        if (updatedPosting != null) {
            return ResponseEntity.ok(updatedPosting); // 업데이트된 공고를 응답으로 반환
        } else {
            return ResponseEntity.notFound().build(); // 공고가 찾을 수 없을 경우 404 응답 반환
        }
    }

    @DeleteMapping("/{jobPostingId}")
    public void deleteJobPosting(@PathVariable Long jobPostingId) {
        jobPostingService.deleteJobPosting(jobPostingId);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<JobPosting>>> getAllJobPostings() {
        return ResponseEntity.ok(
                jobPostingService.getAllJobPostings()
        );
    }

    @GetMapping("/search")
    public List<JobPosting> searchJobPostings(@RequestParam String keyword) {
        return jobPostingService.searchJobPostings(keyword);
    }

    @GetMapping("/{jobPostingId}")
    public JobPosting getJobPostingDetails(@PathVariable Long jobPostingId) {
        return jobPostingService.getJobPostingDetails(jobPostingId);
    }
}
