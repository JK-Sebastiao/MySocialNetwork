package pl.jkiakumbo.MySocialNetworking.resource;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import pl.jkiakumbo.MySocialNetworking.DTO.UserDTO;
import pl.jkiakumbo.MySocialNetworking.model.User;
import pl.jkiakumbo.MySocialNetworking.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.jkiakumbo.MySocialNetworking.util.JsonConverter.asJsonString;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class UserResourceTest {
    private UserService  userServiceMock = mock(UserService.class);
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    public void getAllUsersTest() throws Exception{
        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        this.getResponseFields(true)));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists())
                .andExpect(jsonPath("$.joinDate").exists())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user to be obtained.")),
                        this.getResponseFields(false))
                );
    }

    @Test
    public void shouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user to be obtained.")))
                );
    }

    @Test
    public void saveUserTest() throws Exception {
        UserDTO user = new UserDTO("Robert","Haland", "robert.haland@hotmail.com","12345678","12345678");
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                      this.getRequestFields(), this.getResponseFields(false)));
    }

    @Test
    public void updateUserTest() throws Exception {
        UserDTO user = new UserDTO("John","Trump","john.doe@gmail.com");
        mockMvc.perform(RestDocumentationRequestBuilders.put("/users/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.surname").value("Trump"))
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user to be updated.")),
                        this.getRequestFields(), this.getResponseFields(false)));

    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/users/{id}", 2)).andExpect(status().isAccepted())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user to be deleted.")))
                );
    }

    @Test
    public void addFollowedUserTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.put("/users/{id}/follow/user/{userId}", 1,3)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (
                                parameterWithName("id").description("The unique identifier of the user who will follow another user."),
                                parameterWithName("userId").description("The unique identifier of the user to be followed."))
                        )
                );

    }

    @Test
    public void getFollowedUsersTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/followed/users/{id}", 3))
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        pathParameters (parameterWithName("id").description("The unique identifier of the user who follow other users.")),
                        this.getResponseFields(true)
                ));
    }

    private RequestFieldsSnippet getRequestFields(){
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(User.class);
        return requestFields(
                fieldWithPath("name").description("The name of the user")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("name"))),
                fieldWithPath("surname").description("The surname of the user")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("surname"))),
                fieldWithPath("username").description("The e-mail of the user")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("username"))),
                fieldWithPath("password").description("The password of the user")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("password"))),
                fieldWithPath("passwordConfirmation").description("The password confirmation of the user")
                        .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("passwordConfirmation"))));
    }

    private ResponseFieldsSnippet getResponseFields(boolean list){
        return responseFields(
                fieldWithPath(list ? "[].id" : "id") .description("The unique identifier of the user"),
                fieldWithPath(list ? "[].name" : "name").description("The name of the user"),
                fieldWithPath(list ? "[].surname" : "surname").description("The surname of the user"),
                fieldWithPath(list ? "[].username" : "username").description("The user e-mail"),
                fieldWithPath(list ? "[].joinDate" : "joinDate").description("The date of user registration")
        );
    }
}
