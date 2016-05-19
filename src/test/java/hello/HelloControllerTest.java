package hello;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class HelloControllerTest {
  private MockMvc mockMvc;
  
  @Autowired
  private WebApplicationContext webApplicationContext;

  
  @Before
  public void setup(){
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }
  
  /**
   * To test with the Valid input
   * @throws Exception
   */
  @Test
  public void testServicewithValidInput() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/matchphrase").param("input", "I have a sore throat and headache"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder("sore throat","headache")));
      
      
  }
  
  /**
   * To test with valid input with the post option
   * @throws Exception
   */
  @Test
  public void testServicewithValidPostInput() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.post("/matchphrase").param("input", "I have a sore throat and headache"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder("sore throat","headache")));
      
      
  }
  
  /**
   * Test the service without any input
   * @throws Exception
   */
  @Test
  public void testServicewithoutInput() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/matchphrase"))
      .andExpect(MockMvcResultMatchers.status().isOk())
     .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder(("Please pass the string to be Phrase matched using request parameter named 'input' . Example: '/matchphrase?input=i have headache' or '/matchphrase?input=I%20have%20a%20headache' "))));
      
  }
  
  /**
   * Test the service with Null input
   * @throws Exception
   */
  @Test
  public void testServicewithNullInput() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/matchphrase").param("input", ""))
      .andExpect(MockMvcResultMatchers.status().isOk())
     .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
      
  }

}
