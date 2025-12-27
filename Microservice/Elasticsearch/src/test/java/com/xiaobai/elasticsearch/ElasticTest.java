package com.xiaobai.elasticsearch;

import cn.hutool.json.JSONUtil;
import com.xiaobai.elasticsearch.domain.ItemDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class ElasticTest {

    private RestHighLevelClient client;

    @BeforeEach
    public void init(){
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")
        ));
    }

    @Test
    public void testConnection() {
        System.out.println("client=" + client);
    }

    @Test
    void testCreateIndex() throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("items");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);

    }

    @Test
    void testGetIndex() throws IOException {
        GetIndexResponse items = client.indices().get(new GetIndexRequest("items"), RequestOptions.DEFAULT);
        System.out.println(items.getMappings());
    }

    @Test
    public void testCreateDocument() throws IOException {
        IndexRequest request = new IndexRequest("items").id("1");
        ItemDoc itemDoc = new ItemDoc();
        itemDoc.setId("1");
        itemDoc.setBrand("aaa");
        itemDoc.setCategory("cat");
        itemDoc.setImage("./img/a.png");
        itemDoc.setIsAD(false);
        itemDoc.setName("name");
        itemDoc.setPrice(25);
        itemDoc.setSold(90);
        itemDoc.setCommentCount(999);
        itemDoc.setUpdateTime(LocalDateTime.now());

        request.source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testGetDocument() throws IOException {
        GetResponse items = client.get(new GetRequest("items", "1"), RequestOptions.DEFAULT);
        System.out.println(items.getSourceAsString());
    }


    @Test
    public void testUpdateDocumentField() throws IOException {

        UpdateRequest request = new UpdateRequest("items", "1");
        request.doc("name","xiaobai","price",19999);
        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update);
    }

    @Test
    public void testBatch() throws IOException {
        ItemDoc itemDoc = new ItemDoc();
        itemDoc.setId("1");
        itemDoc.setBrand("aaa");
        itemDoc.setCategory("cat");
        itemDoc.setImage("./img/a.png");
        itemDoc.setIsAD(false);
        itemDoc.setName("name");
        itemDoc.setPrice(25);
        itemDoc.setSold(90);
        itemDoc.setCommentCount(999);
        itemDoc.setUpdateTime(LocalDateTime.now());
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("items").id("2").source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON));
        itemDoc.setName("name2");
        request.add(new IndexRequest("items").id("3").source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON));
        itemDoc.setName("name3");
        request.add(new IndexRequest("items").id("4").source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON));
        request.add(new DeleteRequest("items","2"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }


    static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"name\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      },\n" +
            "      \"price\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"stock\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"image\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"category\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"brand\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"sold\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"commentCount\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"isAD\":{\n" +
            "        \"type\": \"boolean\"\n" +
            "      },\n" +
            "      \"updateTime\":{\n" +
            "        \"type\": \"date\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    @Test
    public void testDslQuery() throws IOException {
        SearchRequest request = new SearchRequest("items");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }


    @AfterEach
    public void close() throws IOException {
        if(client!=null){
            client.close();
        }
    }
}
