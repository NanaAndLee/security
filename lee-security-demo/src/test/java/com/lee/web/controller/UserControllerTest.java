package com.lee.web.controller;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup( webApplicationContext ).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String result = mockMvc.perform( MockMvcRequestBuilders.get( "/user" )
                .param( "username", "jojo" )
                .param( "age", "18" )
                .param( "ageTo", "30" )
                .param( "xxx", "other" )
                .param( "size", "15" )
                .param( "page", "3" )
                .param( "sort", "age,desc" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( jsonPath( "$.length()" ).value( 1 ) )
                .andReturn().getResponse().getContentAsString();

        System.out.println( "!!!!!!!!!!!!!!!!!!!test : "+result );
    }

    @Test
    public void whenGenInfoSuccess() throws Exception {
        String result = mockMvc.perform( MockMvcRequestBuilders.get( "/user/1" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( jsonPath( "$.username" ).value( "tom" ) )
                .andReturn().getResponse().getContentAsString();
        System.out.println( "!!!!!!!!!!!!!!!!!!!test : "+result );
    }

    @Test
    public void whenGenInfoFail() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get( "/user/a" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) )
                //期望返回的状态码是4开头的，因为资源请求的参数不符合要求
                .andExpect( MockMvcResultMatchers.status().is4xxClientError() );
    }

    @Test
    public void whenCreatSuccess() throws Exception {

        Date date = new Date();
        System.out.println( date.getTime() );
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String result = "";
        result = mockMvc.perform( MockMvcRequestBuilders.post( "/user" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 )
                .content( content ) )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).value( "1" ) )
                .andReturn().getResponse().getContentAsString();
        System.out.println( result );
//        Date date = new Date();
//        System.out.println(date.getTime());
//        String content = "{\"username\":\"tom\",\"password\":testpassword,\"birthday\":"+date.getTime()+"}";
//        String reuslt = mockMvc.perform( MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(content))
//                .andExpect( MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(reuslt);

    }

    @Test
    public void whenUpdateSuccess() throws Exception {
//        Date date = new Date();
        //传入一个一年以后的时间交给birthday上的注解去校验
        Date date = new Date( LocalDateTime.now().plusYears( 1 ).atZone( ZoneId.systemDefault() ).toInstant().toEpochMilli() );
        System.out.println( date.getTime() );
        String content = "{\"id\" : \"1\", \"username\" : \"Updatename\", \"password\" : null, \"birthday\" :" + date.getTime() + "}";
        String result = mockMvc.perform( MockMvcRequestBuilders.put( "/user/1" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 )
                .content( content ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id" ).value( "1" ) )
                .andReturn().getResponse().getContentAsString();
        System.out.println( result );
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.delete( "/user/1" )
        .contentType( MediaType.APPLICATION_JSON_UTF8 ) )
                .andExpect( status().isOk() );
//                .andExpect( jsonPath( "" ) )
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result =  mockMvc.perform( MockMvcRequestBuilders.fileUpload( "/file" )
         .file( new MockMultipartFile( "file",
                 "test.txt",
                 "multipart/form-data", "hello upload".getBytes("UTF-8") ) ))
                 .andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }








}
