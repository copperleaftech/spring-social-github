/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.github.connect;

import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Github ServiceProvider implementation.
 * @author Keith Donald
 */
public class GitHubServiceProvider extends AbstractOAuth2ServiceProvider<GitHub> {

	private String apiHostname;
	private String serviceHostname;

	public GitHubServiceProvider(String clientId, String clientSecret, String serviceHostname, String apiHostname) {
		super(createOAuth2Template(clientId, clientSecret, serviceHostname));
		this.apiHostname = apiHostname;
		this.serviceHostname = serviceHostname;
	}

	private static OAuth2Template createOAuth2Template(String clientId, String clientSecret, String serviceHostname) {
		String authorizeUrl = "https://" + (serviceHostname != null ? serviceHostname : "github.com") + "/login/oauth/authorize";
		String accessTokenUrl = "https://" + (serviceHostname != null ? serviceHostname : "github.com") + "/login/oauth/access_token";
		OAuth2Template oAuth2Template = new OAuth2Template(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	public GitHub getApi(String accessToken) {
		return new GitHubTemplate(accessToken, serviceHostname, apiHostname);
	} 

}
