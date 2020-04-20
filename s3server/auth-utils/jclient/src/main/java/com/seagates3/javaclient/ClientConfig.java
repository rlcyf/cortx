 /*
 * COPYRIGHT 2015 SEAGATE LLC
 *
 * THIS DRAWING/DOCUMENT, ITS SPECIFICATIONS, AND THE DATA CONTAINED
 * HEREIN, ARE THE EXCLUSIVE PROPERTY OF SEAGATE TECHNOLOGY
 * LIMITED, ISSUED IN STRICT CONFIDENCE AND SHALL NOT, WITHOUT
 * THE PRIOR WRITTEN PERMISSION OF SEAGATE TECHNOLOGY LIMITED,
 * BE REPRODUCED, COPIED, OR DISCLOSED TO A THIRD PARTY, OR
 * USED FOR ANY PURPOSE WHATSOEVER, OR STORED IN A RETRIEVAL SYSTEM
 * EXCEPT AS ALLOWED BY THE TERMS OF SEAGATE LICENSES AND AGREEMENTS.
 *
 * YOU SHOULD HAVE RECEIVED A COPY OF SEAGATE'S LICENSE ALONG WITH
 * THIS RELEASE. IF NOT PLEASE CONTACT A SEAGATE REPRESENTATIVE
 * http://www.seagate.com/contact
 *
 * Original author:  Arjun Hariharan <arjun.hariharan@seagate.com>
 * Original creation date: 12-Feb-2016
 */
package com.seagates3.javaclient;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientConfig {


    public static ClientConfiguration getClientConfiguration() {
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTP);

        return config;
    }

    public static String getEndPoint(Class<?> service) throws IOException {
        Properties endpointConfig = new Properties();
        String endpoint = null;

        InputStream input = new FileInputStream(JavaClient.CONFIG_FILE_NAME);
        endpointConfig.load(input);

        if (service.equals(AmazonS3Client.class)) {
              if (Boolean.valueOf(endpointConfig.getProperty("use_https")))
                  endpoint = "https://" + endpointConfig.getProperty("s3_endpoint")
                                + ":" + endpointConfig.getProperty("s3_https_port");
              else
                  endpoint = "http://" + endpointConfig.getProperty("s3_endpoint")
                                + ":" + endpointConfig.getProperty("s3_http_port");
        } else {
              if (Boolean.valueOf(endpointConfig.getProperty("use_https")))
                  endpoint = "https://" + endpointConfig.getProperty("iam_endpoint")
                                +":"+ endpointConfig.getProperty("iam_https_port");
              else
                  endpoint = "http://" + endpointConfig.getProperty("iam_endpoint")
                                +":"+ endpointConfig.getProperty("iam_http_port");
        }
        return endpoint;
    }

    public static AWSCredentials getCreds(String accessKeyId, String secretkey) {
        return new BasicAWSCredentials(accessKeyId, secretkey);
    }

    public static AWSCredentials getCreds(String accessKeyId, String secretkey,
            String sessionToken) {
        return new BasicSessionCredentials(accessKeyId, secretkey, sessionToken);
    }
}