package com.lee.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author leeCoding
 * @since
 */
public class MockServer {
    public static void main(String[] args) throws IOException {
//        WireMock.configureFor( "localshot", 8061 );
        WireMock.configureFor(8061);
//        清空服务
        WireMock.removeAllMappings();

        mock("/order/1","01");
        mock("/order/2","02");

    }

    private static void mock(String url, String file) throws IOException {
        //友情提示：在IDEA中空文件夹折叠时mock.response及变成一个文件夹的名称了  此时应该是mock.response/01.txt
        ClassPathResource resource = new ClassPathResource( "mock/response/"+file+".txt" );
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(), "\n");
        //伪造一个测试桩
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url))
                .willReturn( WireMock.aResponse()
                        .withBody( content )
                        .withStatus( 200 ) .withHeader("Content-Type", "text/plain;charset=utf-8")));
    }
}
