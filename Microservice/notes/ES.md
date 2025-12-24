# ES

---

docker 安装：

    docker network create es

    docker run -d \
    --name es \
    -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
    -e "discovery.type=single-node" \
    -v es-data:/usr/share/elasticsearch/data \
    -v es-plugins:/usr/share/elasticsearch/plugins \
    --privileged \
    --network es \
    -p 9200:9200 \
    -p 9300:9300 \
    elasticsearch:7.12.1
    
    
    
    docker run -d \
    --name kibana \
    -e ELASTICSEARCH_HOSTS=http://es:9200 \
    --network=es \
    -p 5601:5601  \
    kibana:7.12.1

---

## 倒排索引

传统数据库采用正向索引，elasticsearch采用的是倒排索引：

**文档（document）: 每条数据就是一个文档**

**词条（term）： 文档按照语义分成的词语**

正向索引： 基于文档id创建索引，根据id查询快，但是查询词条时必须先找到文档，而后判断是否包含词条

倒排索引： 对文档内容分词，对词条创建索引，并记录词条所在文档的id。查询时现根据词条查询到文档id，而后根据文档id查询文档

![es-term](./img/ES-TERM.png)

---

# IK分词器

中文分词往往需要根据语义分析，比较复杂，这就需要用到中文分词器，例如IK分词器。IK分词器时林良益在2006年开源发布的，其采用的正向迭代最细粒度切分算法一直沿用至今

[一个常见的ik分词器](https://release.infinilabs.com/analysis-ik/stable/)

支持的两种分词模式

1. ik_smart: 智能切分，粗粒度
2. ik_max_word: 最细切分，细粒度IK分词器

这个分词器允许我们自己配置拓展词典来增加自定义词库：

    通过修改目录下 config文件夹下的 IKAnalyzer.cfg.xml 指定自己的词典

![IK-CUSTOM-DIC](./img/IK-Custom-dic.png)

---

## 一些基础概念

elasticsearch 中的文档数据会被序列化为json格式后存储在elasticsearch中

1. 索引(index)： 相同类型的文档集合，类似于数据库的表

2. 映射(mapping): 索引中文档的字段约束信息，类似表的结构约束

3. 字段(field): Json文档中的字段，类似于表中的列

4. 文档(Document): 就是一条条的数据，类似于表中的一行

5. DSL: es中提供的json风格的请求语句，用来定义搜索条件，类似于sql

---

## 索引库操作

常见 Mapping 映射属性：

1. type： 字段数据类型，常见的简单类型有：
   1. 字符串： text(可分词的文本)、keyword(精确值，例如：品牌、国家、IP地址)
   2. 数值： long、integer、short、byte、double、float
   3. 布尔： boolean
   4. 日期： date
   5. 对象： object
2. index： 是否创建索引， 默认值为true
3. analyzer： 使用哪种分词器
4. properties： 该字段的子字段

创建索引库请求语法：

    PUT /索引库名称
    {
        "mappings": {
            "properties": {
                "字段名1": {
                    "type": "text",
                    "analyzer": "ik_smart"
                },
                "字段名2": {
                    "type": "keyword",
                    "index": "false"
                },
                "字段名3": {
                    "properties": {
                        "子字段": {
                            "type": "keyword"
                        }
                    }
                }
            }
        }
    }

查询、删除 都遵循RestFull操作模式  （GET /索引库名称 、DELETE /索引库名称）

索引库和mapping一旦创建就无法修改，但是可以添加新的字段，添加新的字段用 PUT /索引库名/_mapping

es-01-08-文档操作

