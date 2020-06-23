package pl.jkiakumbo.MySocialNetworking.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jkiakumbo.MySocialNetworking.DTO.PostDTO;
import pl.jkiakumbo.MySocialNetworking.data.LoadTestData;
import pl.jkiakumbo.MySocialNetworking.model.Post;
import pl.jkiakumbo.MySocialNetworking.model.User;
import pl.jkiakumbo.MySocialNetworking.service.PostService;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.jkiakumbo.MySocialNetworking.util.JsonConverter.asJsonString;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class PostResourceTest {
    private PostService postServiceMock = mock(PostService.class);
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }
    @Test
    public void getAllPostsTest() throws Exception{
        mockMvc.perform(get("/posts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        this.getResponseFields(true)));
    }

    @Test
    public void getPostByIdTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.post").exists())
                .andExpect(jsonPath("$.user").isNumber())
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the post to be obtained.")),
                        this.getResponseFields(false)) );
    }

    @Test
    public void savePostTest() throws Exception {
        PostDTO post = new PostDTO("Ut at ultricies justo. In eleifend ex sit amet lacus maximus, vel tempor nisl tincidunt. Maecenas quis feugiat metus. Etiam imperdiet at ex.");
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/user/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(post))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user who post the message.")),
                        this.getRequestFields(), this.getResponseFields(false)));
    }

    @Test
    public void updatePostTest() throws Exception {
        String message = "Lorem ipsum dolor. In eleifend ex sit amet lacus maximus, vel tempor nisl tincidunt.";
        PostDTO post = new PostDTO(message);
        mockMvc.perform(RestDocumentationRequestBuilders.put("/posts/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(post))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.post").value(message))
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the post to be updated.")),
                        this.getRequestFields(), this.getResponseFields(false)));

    }
    @Test
    public void deletePostTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the post to be deleted.")))
                );
    }
    @Test
    public void getFollowedUsersPostsTest() throws Exception{
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/user/{id}/followed", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user who follow the users that posted the posts.")),
                        this.getResponseFields(true)));
    }

    @Test
    public void getUserPostsTest() throws Exception{
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/user/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user who posted the posts.")),
                        this.getResponseFields(true)));
    }

    private RequestFieldsSnippet getRequestFields(){
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(User.class);
        return requestFields(
                fieldWithPath("post").description("The message to be posted")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("post"))));
    }

    private ResponseFieldsSnippet getResponseFields(boolean list){
        return responseFields(
                fieldWithPath(list ? "[].id" : "id") .description("The unique identifier of the post"),
                fieldWithPath(list ? "[].post" : "post").description("The posted message "),
                fieldWithPath(list ? "[].user" : "user").description("The unique identifier of the user who post the message"),
                fieldWithPath(list ? "[].postedDate" : "postedDate").description("The date when was posted the message")
        );
    }
}
