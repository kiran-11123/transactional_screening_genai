package com.example.transaction_screening;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.example.transaction_screening.config.RateLimitFilter;

@SpringBootTest
class TransactionScreeningApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void loginRequestsAreRateLimitedAfterThreshold() throws Exception {
		RateLimitFilter filter = new RateLimitFilter();

		for (int i = 0; i < 5; i++) {
			MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/auth/login");
			request.setRemoteAddr("127.0.0.1");
			MockHttpServletResponse response = new MockHttpServletResponse();
			filter.doFilter(request, response, new MockFilterChain());
			assertEquals(200, response.getStatus());
		}

		MockHttpServletRequest blockedRequest = new MockHttpServletRequest("POST", "/api/auth/login");
		blockedRequest.setRemoteAddr("127.0.0.1");
		MockHttpServletResponse blockedResponse = new MockHttpServletResponse();
		filter.doFilter(blockedRequest, blockedResponse, new MockFilterChain());

		assertEquals(429, blockedResponse.getStatus());
	}

}
