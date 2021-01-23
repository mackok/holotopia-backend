package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import nhl.stenden.config.TestConfig;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@Transactional
class HololiveMemberTest {

    private final WebApplicationContext webApplicationContext;
    private final ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private String member;

    @Autowired
    public HololiveMemberTest(WebApplicationContext webApplicationContext, ObjectMapper objectMapper){
        this.webApplicationContext = webApplicationContext;
        this.objectMapper = objectMapper;
        member = "";
    }

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addMemberStatusOKTest() throws Exception {
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        addMember()
                .andExpect(status().isOk());
    }

    @Test
    void addMemberNoNameTest()  throws Exception {
        member = "{" +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        addMember()
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMemberNoChannelIdTest()  throws Exception {
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"uploads_id\":\"UUoSrY_IQQVpmIRZ9Xf-y93g\"" +
                "}";

        addMember()
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMemberNoUploadsIdTest()  throws Exception {
        member = "{" +
                "\"name\":\"Gawr Gura\"," +
                "\"channel_id\":\"UCoSrY_IQQVpmIRZ9Xf-y93g\"," +
                "}";

        addMember()
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMemberVideosStatusOKTest() throws Exception {
        
    }

    private ResultActions addMember() throws Exception {
        return mockMvc.perform(post("/hololive-members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(member));
    }

    private ResultActions getMemberVideos(Long memberId) throws Exception {
        return mockMvc.perform(get("/hololive-members/" + memberId + "/videos"));
    }
}
