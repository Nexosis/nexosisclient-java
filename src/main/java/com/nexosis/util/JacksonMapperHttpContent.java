/*
 * Copyright (c) 2015 Dictanova SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.nexosis.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.json.Json;
import com.google.api.client.util.Preconditions;

import java.io.IOException;
import java.io.OutputStream;

    /**
     * JSON deserializer implementation based on Jackson <i>databinding</i> module.
     *
     * Usage:
     * {@code
     * HttpContent content = new JacksonMapperHttpContent(new ObjectMapper(), data);
     * HttpRequest request = requestFactory.buildPostRequest(url, content);
     * HttpResponse response = request.execute();
     * }
     *
     * Implementation is not thread-safe.
     *
     *
     * @author Damien Raude-Morvan
     *
     */
    public class JacksonMapperHttpContent extends AbstractHttpContent {

        /**
         * JSON key name/value data.
         */
        private final Object data;

        /**
         * Jackson {@link ObjectMapper} that will perform databinding.
         */
        private final ObjectMapper objectMapper;

        /**
         * @param objectMapper Jackson databinder
         * @param data         JSON key name/value data
         */
        public JacksonMapperHttpContent(final ObjectMapper objectMapper, final Object data) {
            super(Json.MEDIA_TYPE);
            this.objectMapper = Preconditions.checkNotNull(objectMapper);
            this.data = Preconditions.checkNotNull(data);
        }

        public void writeTo(OutputStream out) throws IOException {
            objectMapper.writeValue(out, data);
        }

    }