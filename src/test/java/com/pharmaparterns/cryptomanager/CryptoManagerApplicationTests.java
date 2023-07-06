package com.pharmaparterns.cryptomanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmaparterns.cryptomanager.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
class CryptoManagerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		jdbcTemplate.execute("DELETE FROM currencies");
		jdbcTemplate.execute("INSERT INTO currencies (id, ticker, name, number_of_coins, market_cap) VALUES (1, 'BTC', 'Bitcoin', 16770000, 189580000000)");
		jdbcTemplate.execute("INSERT INTO currencies (id, ticker, name, number_of_coins, market_cap) VALUES (2, 'ETH', 'Ethereum', 96710000, 69280000000)");
		jdbcTemplate.execute("INSERT INTO currencies (id, ticker, name, number_of_coins, market_cap) VALUES (3, 'XRP', 'Ripple', 38590000000, 64750000000)");
		jdbcTemplate.execute("INSERT INTO currencies (id, ticker, name, number_of_coins, market_cap) VALUES (4, 'BCH', 'BitcoinCash', 16670000, 69020000000)");

	}

	@Test
	void testGetAllCurrencies() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/currencies")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].name").value("Bitcoin"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].name").value("Ethereum"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.[2].name").value("Ripple"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.[3].name").value("BitcoinCash"));
	}

	@Test
	void testGetCurrencyById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/currencies/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bitcoin"));
	}

	@Test
	public void testCreateCurrency() throws Exception {
		Currency currency = new Currency.Builder().withName("Test Currency").withTicker("TST").withMarketCap(new BigDecimal(10000)).withNumberOfCoins(new BigDecimal(10000000)).build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/currencies")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(currency)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Currency"));
	}

	@Test
	public void testUpdateCurrency() throws Exception {
		Currency currency = new Currency.Builder().withName("Test Currency").withTicker("TST").withMarketCap(new BigDecimal(10000)).withNumberOfCoins(new BigDecimal(10000000)).build();
		currency.setId(1L);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/currencies/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(currency)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Currency"));
	}

	@Test
	public void testDeleteCurrency() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/currencies/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
