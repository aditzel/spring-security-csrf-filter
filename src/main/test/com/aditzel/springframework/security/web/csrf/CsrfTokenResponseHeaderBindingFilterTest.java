/*
 * Copyright 2014 Allan Ditzel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aditzel.springframework.security.web.csrf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Test for {@link com.aditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter}.
 *
 * @author Allan Ditzel
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class CsrfTokenResponseHeaderBindingFilterTest  {
    private CsrfTokenResponseHeaderBindingFilter filter;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    HttpSession session;

    @Mock
    CsrfToken token;

    @Before
    public void setUp() {
        filter = new CsrfTokenResponseHeaderBindingFilter();
    }

    @Test
    public void shouldContinueProcessingFilterChainIfTokenNotPresentInRequest() throws ServletException, IOException {
        when(request.getAttribute(CsrfTokenResponseHeaderBindingFilter.REQUEST_ATTRIBUTE_NAME)).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(request).getAttribute(CsrfTokenResponseHeaderBindingFilter.REQUEST_ATTRIBUTE_NAME);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(request, response, filterChain);
    }

    @Test
    public void shouldBindCsrfValuesToResponseHeadersWhenTokenIsPresentInRequest() throws ServletException, IOException {
        String headerName = "headerName";
        String paramName = "paramName";
        String tokenValue = "token";

        when(request.getAttribute(CsrfTokenResponseHeaderBindingFilter.REQUEST_ATTRIBUTE_NAME)).thenReturn(token);
        when(token.getHeaderName()).thenReturn(headerName);
        when(token.getParameterName()).thenReturn(paramName);
        when(token.getToken()).thenReturn(tokenValue);

        filter.doFilterInternal(request, response, filterChain);

        verify(request).getAttribute(CsrfTokenResponseHeaderBindingFilter.REQUEST_ATTRIBUTE_NAME);
        verify(token).getHeaderName();
        verify(token).getParameterName();
        verify(token).getToken();
        verify(response).setHeader(CsrfTokenResponseHeaderBindingFilter.RESPONSE_HEADER_NAME, headerName);
        verify(response).setHeader(CsrfTokenResponseHeaderBindingFilter.RESPONSE_PARAM_NAME, paramName);
        verify(response).setHeader(CsrfTokenResponseHeaderBindingFilter.RESPONSE_TOKEN_NAME, tokenValue);
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(token, request, response, filterChain);
    }
}