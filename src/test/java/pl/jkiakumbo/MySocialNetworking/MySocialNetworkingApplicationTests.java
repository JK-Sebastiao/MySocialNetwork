package pl.jkiakumbo.MySocialNetworking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class MySocialNetworkingApplicationTests {

	@Test
	void contextLoads() {
	}

}
