package com.nimbleways.springboilerplate.configuration;

import com.nimbleways.springboilerplate.posts.client.PostsClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Bean
    public PostsClient postsClient(final OkHttpClient client, @Value("${retrofit.posts.base-url}") final String baseUrl) {
        return retrofit(client, baseUrl).create(PostsClient.class);
    }

    private Retrofit retrofit(final OkHttpClient client, final String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
