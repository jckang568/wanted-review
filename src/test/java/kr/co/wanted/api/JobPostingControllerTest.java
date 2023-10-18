package kr.co.wanted.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wanted.api.request.JobPostingRequest;
import kr.co.wanted.domain.entity.Company;
import kr.co.wanted.domain.entity.JobPosting;
import kr.co.wanted.service.JobPostingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class JobPostingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobPostingService jobPostingService;

    @Test
    public void testCreateJobPosting() throws Exception {

        JobPostingRequest jobPostingRequest = new JobPostingRequest();
        jobPostingRequest.setCompanyId(1);
        jobPostingRequest.setPosition("백엔드 시니어 개발자");
        jobPostingRequest.setRewardAmount(1000000);
        jobPostingRequest.setJobDescription("원티드 인서트 테스트");
        jobPostingRequest.setRequiredSkills("JPA, Hibernate");

        mockMvc.perform(MockMvcRequestBuilders.post("/jobpostings")
                        .content(objectMapper.writeValueAsString(jobPostingRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateJobPosting() throws Exception {
        Long jobPostingId = 1L;
        JobPosting updatedJobPosting = new JobPosting();
        updatedJobPosting.setPosition("Updated Backend Developer");
        updatedJobPosting.setRewardAmount(1500000);
        updatedJobPosting.setJobDescription("Updated job description...");
        updatedJobPosting.setRequiredSkills("Java, Spring, Hibernate");

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/jobpostings/{id}", jobPostingId)
                        .content(objectMapper.writeValueAsString(updatedJobPosting))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

// Then
// 업데이트된 채용공고를 조회하고, 해당 공고의 내용을 확인하여 업데이트가 제대로 이루어졌는지 검증
        JobPosting updatedJobPosting2 = jobPostingService.getJobPostingDetails(1L);
        assertEquals("Updated Backend Developer", updatedJobPosting2.getPosition());
        assertEquals(1500000, updatedJobPosting2.getRewardAmount());
        assertEquals("Updated job description...", updatedJobPosting2.getJobDescription());
        assertEquals("Java, Spring, Hibernate", updatedJobPosting2.getRequiredSkills());


    }

    @Test
    public void testDeleteJobPosting() throws Exception {
        Long jobPostingId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/jobpostings/{id}", jobPostingId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testGetAllJobPostings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jobpostings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testSearchJobPostings() throws Exception {
        String keyword = "Backend";

        mockMvc.perform(MockMvcRequestBuilders.get("/jobpostings/search")
                        .param("keyword", keyword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetJobPostingDetails() throws Exception {
        Long jobPostingId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/jobpostings/{id}", jobPostingId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("백엔드 주니어 개발자"));
    }


}
