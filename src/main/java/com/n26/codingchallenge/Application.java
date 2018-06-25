package com.n26.codingchallenge;

import java.sql.SQLException;

import org.h2.server.web.WebServlet;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.n26.codingchallenge.dao.TransactionDao;
import com.n26.codingchallenge.dao.TransactionDaoImpl;
import com.n26.codingchallenge.service.TransactionService;
import com.n26.codingchallenge.service.TransactionServiceImpl;


@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        context.getBean(TransactionService.class).populateStatistics(); //
	}

	@Bean
	public TransactionDao transactionDao() {
		return new TransactionDaoImpl();
	}
	
    @Bean
    protected TransactionService transactionService() {
    	TransactionServiceImpl transactionService = new TransactionServiceImpl();
    	transactionService.setTransactionDao(transactionDao());
        return transactionService;
    }
    
    @Bean
    @Profile("default")
    protected ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        registrationBean.addInitParameter("webAllowOthers", "true");
        return registrationBean;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("default")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9292");
    }
}
