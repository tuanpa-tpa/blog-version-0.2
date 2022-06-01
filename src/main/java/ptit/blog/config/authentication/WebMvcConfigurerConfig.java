package ptit.blog.config.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ptit.blog.interceptor.IsSetSubcriberInterceptor;
import ptit.blog.interceptor.LoggerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfigurerConfig implements WebMvcConfigurer {
    private final LoggerInterceptor loggerInterceptor;
    private final IsSetSubcriberInterceptor isSetSubcriberInterceptor;

    public WebMvcConfigurerConfig(LoggerInterceptor loggerInterceptor, IsSetSubcriberInterceptor isSetSubcriberInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
        this.isSetSubcriberInterceptor = isSetSubcriberInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> httpPatterns = new ArrayList<>(Arrays.asList("/user/info/**"));
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(isSetSubcriberInterceptor).addPathPatterns(httpPatterns);
    }
}
