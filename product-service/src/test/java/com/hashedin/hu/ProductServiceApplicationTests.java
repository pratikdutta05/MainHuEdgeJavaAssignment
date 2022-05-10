package com.hashedin.hu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.hu.model.Category;
import com.hashedin.hu.model.Item;
import com.hashedin.hu.repository.CategoryRepository;
import com.hashedin.hu.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	private Category category1;

	private Item item1;
	//private LoginDto loginDto;

	@BeforeEach
	void setUp() throws Exception {
		category1=new Category("mobile");
		item1=new Item("samsung",100,10000.0,4.1,categoryRepository.getById(1));
	}

	@Test
	void testGetAllCategorySuccess() throws Exception {

		Category category=categoryRepository.save(category1);

		mockMvc.perform(get("/product/category/all")
						.contentType("application/json"))
				.andExpect(status().isOk());

	}

	@Test
	void testGetAllItemSuccess() throws Exception {

		Category category=categoryRepository.save(category1);
		itemRepository.save(item1);

		mockMvc.perform(get("/product/item/all")
						.param("shortParam", "price")
						.param("filterParam", "rating")
						.param("filterValue", "3")
						.contentType("application/json"))
				.andExpect(status().isOk());

	}


}
