package com.guidely.exhibitionservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guidely.exhibitionservice.dto.ArtworkCreateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionCreateRequest;
import com.guidely.exhibitionservice.dto.ExhibitionUpdateRequest;
import com.guidely.exhibitionservice.dto.ArtworkUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ExhibitionServiceApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void testExhibitionApis() throws Exception {
		// MockMvc 설정
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		// 1. 전시회 목록 조회 (200 OK)
		System.out.println("=== 전시회 목록 조회 API 테스트 ===");
		mockMvc.perform(get("/api/exhibitions"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		// 2. 전시회 생성 (201 CREATED)
		System.out.println("=== 전시회 생성 API 테스트 ===");
		ExhibitionCreateRequest createRequest = new ExhibitionCreateRequest(
				"현대미술 전시회",
				"현대미술 작품들을 전시하는 특별 전시회입니다.",
				LocalDate.of(2024, 1, 1),
				LocalDate.of(2024, 12, 31)
		);

		MvcResult createResult = mockMvc.perform(post("/api/admin/exhibitions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(createRequest)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("현대미술 전시회"))
				.andReturn();

		// 생성된 전시회 ID 추출
		String responseContent = createResult.getResponse().getContentAsString();
		Long exhibitionId = objectMapper.readTree(responseContent).get("id").asLong();

		// 3. 전시회 상세 조회 (200 OK)
		System.out.println("=== 전시회 상세 조회 API 테스트 ===");
		mockMvc.perform(get("/api/exhibitions/{id}", exhibitionId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(exhibitionId))
				.andExpect(jsonPath("$.title").value("현대미술 전시회"));

		// 4. 전시회 수정 (200 OK)
		System.out.println("=== 전시회 수정 API 테스트 ===");
		ExhibitionUpdateRequest updateRequest = new ExhibitionUpdateRequest();
		updateRequest.setTitle("수정된 현대미술 전시회");
		updateRequest.setDescription("수정된 설명입니다.");

		mockMvc.perform(patch("/api/admin/exhibitions/{id}", exhibitionId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateRequest)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("수정된 현대미술 전시회"));

		// 5. 작품 생성 (201 CREATED)
		System.out.println("=== 작품 생성 API 테스트 ===");
		ArtworkCreateRequest artworkCreateRequest = new ArtworkCreateRequest(
				"모나리자",
				"레오나르도 다 빈치",
				"르네상스",
				"유명한 초상화 작품입니다.",
				"https://example.com/monalisa.jpg",
				exhibitionId
		);

		MvcResult artworkCreateResult = mockMvc.perform(post("/api/admin/artworks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(artworkCreateRequest)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("모나리자"))
				.andExpect(jsonPath("$.artist").value("레오나르도 다 빈치"))
				.andReturn();

		// 생성된 작품 ID 추출
		String artworkResponseContent = artworkCreateResult.getResponse().getContentAsString();
		Long artworkId = objectMapper.readTree(artworkResponseContent).get("id").asLong();

		// 6. 작품 목록 조회 (200 OK)
		System.out.println("=== 작품 목록 조회 API 테스트 ===");
		mockMvc.perform(get("/api/artworks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		// 7. 작품 상세 조회 (200 OK)
		System.out.println("=== 작품 상세 조회 API 테스트 ===");
		mockMvc.perform(get("/api/artworks/{id}", artworkId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(artworkId))
				.andExpect(jsonPath("$.title").value("모나리자"));

		// 8. 작품 수정 (200 OK)
		System.out.println("=== 작품 수정 API 테스트 ===");
		ArtworkUpdateRequest artworkUpdateRequest = new ArtworkUpdateRequest();
		artworkUpdateRequest.setTitle("수정된 모나리자");
		artworkUpdateRequest.setArtist("레오나르도 다빈치");

		mockMvc.perform(patch("/api/admin/artworks/{id}", artworkId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(artworkUpdateRequest)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("수정된 모나리자"));

		// 9. 작품 삭제 (204 NO CONTENT)
		System.out.println("=== 작품 삭제 API 테스트 ===");
		mockMvc.perform(delete("/api/admin/artworks/{id}", artworkId))
				.andDo(print())
				.andExpect(status().isNoContent());

		// 10. 전시회 삭제 (204 NO CONTENT)
		System.out.println("=== 전시회 삭제 API 테스트 ===");
		mockMvc.perform(delete("/api/admin/exhibitions/{id}", exhibitionId))
				.andDo(print())
				.andExpect(status().isNoContent());

		// 11. 존재하지 않는 전시회 조회 (404 NOT FOUND)
		System.out.println("=== 존재하지 않는 전시회 조회 API 테스트 ===");
		mockMvc.perform(get("/api/exhibitions/{id}", exhibitionId))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
