package kr.co.wanted.service;

import kr.co.wanted.common.http.ApiResult;
import kr.co.wanted.domain.entity.JobPosting;
import kr.co.wanted.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    // 생성자 주입

    public JobPosting createJobPosting(JobPosting jobPosting) {
        // 채용공고 등록 로직
        return jobPostingRepository.save(jobPosting);
    }

    public JobPosting updateJobPosting(Long jobPostingId, JobPosting updateJobPosting) {
        // 채용공고 수정 로직
        // jobPostingRepository.findById(jobPostingId)로 기존 공고를 가져와 수정 후 저장
        Optional<JobPosting> optionalJobPosting = jobPostingRepository.findById(jobPostingId);

        if (optionalJobPosting.isPresent()) {
            JobPosting existingJobPosting = optionalJobPosting.get();

            // 업데이트할 필드를 설정합니다.
            existingJobPosting.setPosition(updateJobPosting.getPosition());
            existingJobPosting.setRewardAmount(updateJobPosting.getRewardAmount());
            existingJobPosting.setJobDescription(updateJobPosting.getJobDescription());
            existingJobPosting.setRequiredSkills(updateJobPosting.getRequiredSkills());

            // 수정된 공고를 저장합니다.

            return jobPostingRepository.save(existingJobPosting);
        } else {
            // 공고를 찾을 수 없을 때는 null을 반환
            return null;
        }

    }

    public void deleteJobPosting(Long jobPostingId) {
        // 채용공고 삭제 로직
        jobPostingRepository.deleteById(jobPostingId);
    }

    public ApiResult<List<JobPosting>> getAllJobPostings() {
        // 채용공고 목록 조회 로직
        return ApiResult.ok(jobPostingRepository.findAll());
    }

    public List<JobPosting> searchJobPostings(String keyword) {
        // 채용공고 검색 조회 로직
        // jobPostingRepository.findByPositionContaining(keyword) 등을 사용
        return null;
    }

    public JobPosting getJobPostingDetails(Long jobPostingId) {
        // 채용상세페이지 조회 로직
        return jobPostingRepository.findById(jobPostingId).orElse(null);
    }
}

