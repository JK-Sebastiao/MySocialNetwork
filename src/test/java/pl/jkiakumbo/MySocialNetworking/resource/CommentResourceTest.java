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
import pl.jkiakumbo.MySocialNetworking.DTO.CommentDTO;
import pl.jkiakumbo.MySocialNetworking.data.LoadTestData;
import pl.jkiakumbo.MySocialNetworking.service.CommentService;

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
public class CommentResourceTest {
    private CommentService postServiceMock = mock(CommentService.class);
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }
    @Test
    public void getAllCommentsTest() throws Exception{
        mockMvc.perform(get("/comments").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        this.getResponseFields(true)));
    }

    @Test
    public void getCommentByIdTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comments/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.comment").exists())
                .andExpect(jsonPath("$.commentDate").exists())
                .andExpect(jsonPath("$.post").isNumber())
                .andExpect(jsonPath("$.user").isNumber())
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the post to be obtained.")),
                        this.getResponseFields(false)) );
    }

    @Test
    public void saveCommentTest() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(CommentDTO.class);
        CommentDTO comment = new CommentDTO("Ut at ultricies justo. In eleifend ex sit amet lacus maximus, vel tempor nisl tincidunt.");
        mockMvc.perform(RestDocumentationRequestBuilders.post("/comments/post/{postId}/user/{userId}", 1,1).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (
                                parameterWithName("postId").description("The unique identifier of post to be commented."),
                                parameterWithName("userId").description("The unique identifier of the user who comment the post.")),
                        this.getRequestFields(), this.getResponseFields(false)));
    }

    @Test
    public void updateCommentTest() throws Exception {
        String message = "Lorem ipsum dolor. In eleifend ex sit amet lacus maximus, vel tempor nisl tincidunt.";
        CommentDTO comment = new CommentDTO(message);
        mockMvc.perform(RestDocumentationRequestBuilders.put("/comments/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comment").value(message))
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the post to be updated.")),
                        this.getRequestFields(), this.getResponseFields(false)));

    }
    @Test
    public void deleteCommentTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/comments/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the comment to be deleted.")))
                );
    }

    @Test
    public void getCommentsByPostTest() throws Exception{
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comments/post/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the post that was commented.")),
                        this.getResponseFields(true)));
    }

    @Test
    public void getCommentsByUserTest() throws Exception{
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comments/user/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user who commented the posts.")),
                        this.getResponseFields(true)));
    }

    private RequestFieldsSnippet getRequestFields(){
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(CommentDTO.class);
        return requestFields(
                fieldWithPath("comment").description("The message to be commented")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("post"))));
    }

    private ResponseFieldsSnippet getResponseFields(boolean list){
        return responseFields(
                fieldWithPath(list ? "[].id" : "id") .description("The unique identifier of the comment"),
                fieldWithPath(list ? "[].comment" : "comment").description("The comment message"),
                fieldWithPath(list ? "[].commentDate" : "commentDate").description("The date of comment"),
                fieldWithPath(list ? "[].post" : "post").description("The unique identifier of the post that was commented"),
                fieldWithPath(list ? "[].user" : "user").description("The unique identifier of the user who comment the post")
        );
    }
}
