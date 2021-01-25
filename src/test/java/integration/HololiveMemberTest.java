package integration;

import nhl.stenden.config.TestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@Transactional
class HololiveMemberTest {

    private final WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private String member;
    private String video;

    @Autowired
    public HololiveMemberTest(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }


    /**
     * This method setups and configures the testdata before each use
     */
    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";
        video = "{" +
                "\"youtube_id\":\"5454rserfs\"," +
                "\"thumbnail\":\"test thumbnail\"" +
                "}";
    }

    /**
     * This method test if adding a correct hololive member JSON causes a OK response
     * @throws Exception
     */
    @Test
    void addMemberStatusOKTest() throws Exception {
        addMember()
                .andExpect(status().isOk());
    }

    /**
     * This method test if adding a hololive member JSON object causes a BadRequest error when "name" is missing
     * @throws Exception
     */
    @Test
    void addMemberNoNameTest()  throws Exception {
        member = "{" +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        addMember()
                .andExpect(status().isBadRequest());
    }

    /**
     * This method test if adding a hololive member JSON object causes a BadRequest error when "channel_id" is missing
     * @throws Exception
     */
    @Test
    void addMemberNoChannelIdTest()  throws Exception {
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        addMember()
                .andExpect(status().isBadRequest());
    }

    /**
     * This method test if adding a hololive member JSON object causes a BadRequest error when "uploads_id" is missing
     * @throws Exception
     */
    @Test
    void addMemberNoUploadsIdTest()  throws Exception {
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "}";

        addMember()
                .andExpect(status().isBadRequest());
    }

    /**
     * This method test if deleting a hololive member causes a OK response
     * @throws Exception
     */
    @Test
    void deleteMemberStatusOKTest() throws Exception {
        Long memberId = Long.parseLong(addMember().andReturn().getResponse().getContentAsString());
        deleteMember(memberId).andExpect(status().isOk());
    }

    /**
     * This method test if deleting a hololive member on an invalid index causes a BadRequest response
     * @throws Exception
     */
    @Test
    void deleteMemberInvalidIdTest() throws Exception {
        deleteMember(1000L).andExpect(status().isBadRequest());
    }

    /**
     *  This method test if updating a correct hololive member JSON causes a OK response
     * @throws Exception
     */
    @Test
    void updateMemberStatusOKTest() throws Exception {
        Long memberId = Long.parseLong(addMember().andReturn().getResponse().getContentAsString());
        member = "{" +
                "\"name\":\"Mori Calliope\"," +
                "\"channel_id\":\"UCL_qhgtOy0dy1Agp8vkySQg\"," +
                "\"uploads_id\":\"UUL_qhgtOy0dy1Agp8vkySQg\"" +
                "}";

        updateMember(memberId)
                .andExpect(status().isOk());
    }

    /**
     * This method test if updating a invalid database index causes a BadRequest error
     * @throws Exception
     */
    @Test
    void updateMemberInvalidIdTest() throws Exception {
        member = "{" +
                "\"name\":\"Mori Calliope\"," +
                "\"channel_id\":\"UCL_qhgtOy0dy1Agp8vkySQg\"," +
                "\"uploads_id\":\"UUL_qhgtOy0dy1Agp8vkySQg\"" +
                "}";

        updateMember(1000L)
                .andExpect(status().isBadRequest());
    }

    /**
     * This method test if updating a hololive member JSON object causes a BadRequest error when "name" is missing
     * @throws Exception
     */
    @Test
    void updateMemberNoNameTest()  throws Exception {
        Long memberId = Long.parseLong(addMember().andReturn().getResponse().getContentAsString());
        member = "{" +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        updateMember(memberId)
                .andExpect(status().isBadRequest());
    }

    /**
     * This method test if updating a hololive member JSON object causes a BadRequest error when "channel_id" is missing
     * @throws Exception
     */
    @Test
    void updateMemberNoChannelIdTest()  throws Exception {
        Long memberId = Long.parseLong(addMember().andReturn().getResponse().getContentAsString());
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        updateMember(memberId)
                .andExpect(status().isBadRequest());
    }

    /**
     * This method test if updating a hololive member JSON object causes a BadRequest error when "uploads_id" is missing
     * @throws Exception
     */
    @Test
    void updateMemberNoUploadsIdTest()  throws Exception {
        Long memberId = Long.parseLong(addMember().andReturn().getResponse().getContentAsString());
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "}";

        updateMember(memberId)
                .andExpect(status().isBadRequest());
    }

    /**
     * This method sends a POST request to add a hololive member
     * @return A POST that adds a hololive member to the database
     * @throws Exception
     */
    private ResultActions addMember() throws Exception {
        String jsonToken = getValidToken();
        return mockMvc.perform(post("/hololive-members")
                .header("Authorization", "Bearer " + jsonToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(member));
    }

    /**
     * This method sends a DELETE request to delete a hololive member
     * @param memberId The id of the hololive member
     * @return A DELETE request that deletes the respective hololive member from the database
     * @throws Exception
     */
    private ResultActions deleteMember(Long memberId) throws Exception {
        String jsonToken = getValidToken();
        return mockMvc.perform(delete("/hololive-members/" + memberId)
                .header("Authorization", "Bearer " + jsonToken));
    }

    /**
     * This method sends a PUT request to update a hololive member
     * @param memberId The id of the hololive member
     * @return A PUT request that updates the hololive member in the database
     * @throws Exception
     */
    private ResultActions updateMember(Long memberId) throws Exception {
        String jsonToken = getValidToken();
        return mockMvc.perform(put("/hololive-members/" + memberId)
                .header("Authorization", "Bearer " + jsonToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(member));
    }


    /**
     * This method uses user credentials to login in order to do API request
     * @param user Username used to login
     * @param password Password used to login
     * @return Successful login response
     * @throws Exception
     */
    private ResultActions login(String user, String password) throws Exception {
        return mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("username", user),
                                new BasicNameValuePair("password", password)
                        ))))
        );
    }

    /**
     * This method returns a valid JSON token from the http response header using login credentials
     * @return Valid JSON token
     * @throws Exception
     */
    private String getValidToken() throws Exception {
        return login("admin", "admin").andReturn().getResponse().getHeader("json-token");
    }
}
