package nl.niranjan.stub;

import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
@Slf4j
@AutoConfigureWireMock
public class StubServerDemoApplication {

	private String prefix = "BOOT-INF/classes/";

	@Value("${stub.server.port}")
	private int port;

	@Value("${stub.server.files}")
	private String path;

	public static void main(String[] args) {
		log.info("Starting  Application");
		new SpringApplicationBuilder(StubServerDemoApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public Options wireMockOptions() throws IOException {
		final WireMockConfiguration options = WireMockSpring.options();
		options.port(port);
		ClasspathFileSource fileSource = new ClasspathFileSource(path);
		if (fileSource.getUri().getPath() == null) {
			options.usingFilesUnderClasspath(prefix + path);
		} else {
			options.usingFilesUnderClasspath(path);
		}
		return options;
	}

}
