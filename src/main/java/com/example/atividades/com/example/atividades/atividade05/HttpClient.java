package com.example.atividades.atividade05;

import java.io.IOException;

public class HttpClient implements IHttpClient {
    private IHttpClient _client;

    public HttpClient(IHttpClient httpClient) {
    	_client = httpClient;
	}

	public String getDataFromApi(String url) throws IOException {
		return _client.getDataFromApi(url);
    }
}
