package net.hassani.springwebservicesexample1.ws;

import jakarta.servlet.ServletRegistration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SpringWebServicesConfig {
    @Bean
    public ServletRegistrationBean customMessageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "banque")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema comptesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("banqueService");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.hassani.net/soap");
        wsdl11Definition.setSchema(comptesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema banqueSchema() {
        return new SimpleXsdSchema(new ClassPathResource("contract.xsd"));
    }
}
