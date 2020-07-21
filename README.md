# Spring Cloud Notes

## 一、Forward

### a、一些话

本文是结合atguigu Spring Cloud 2020教学视频的学习笔记，仅供自己学习或者其他道友参考，如有错误之处还望指正，请不要无脑喷，有错就改嘛，没啥大不了的，大家一同进步。

### b、版本

| 技术         | version       |
| ------------ | ------------- |
| Spring Cloud | Hoxton.SR1    |
| Spring Boot  | 2.2.2.RELEASE |

`ps:由于时间精力有限，计划先整理Spring Cloud部分，Spring Cloud Alibaba部分后续会补上。`
### c、Module说明

| module                     | 组件      |
| -------------------------- | --------- |
| cloud-provider-payment8001 | Eureka    |
| cloud-provider-payment8002 | Eureka    |
| cloud-consumer-order80     | Eureka    |
| cloud-eureka-server7001    | Eureka    |
| cloud-eureka-server7002    | Eureka    |
| cloud-provider-payment8004 | Zookeeper |
| cloud-zk-consumer-order80  | Zookeeper |
|                            |           |



## 


## 二、项目构建

###  a、聚合父工程  cloud2020

File -> new -> Project -> Maven - > org.apache.maven.archetypes:maven-archetype-site

#### 0、准备工作

- 字符

  ![image-20200719144609272](https://s1.ax1x.com/2020/07/19/UWSzxH.png)


- 注解生效激活

  ![image-20200719144727599](https://s1.ax1x.com/2020/07/19/UWppMd.png)

####  1、pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.id01.springcloud</groupId>
    <artifactId>cloud-h</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- packaging pom 总工程-->
    <packaging>pom</packaging>

    <name>Maven</name>
    <!-- FIXME change it to the project's website -->
    <url>http://maven.apache.org/</url>
    <inceptionYear>2001</inceptionYear>

    <distributionManagement>
        <site>
            <id>website</id>
            <url>scp://webhost.company.com/www/website</url>
        </site>
    </distributionManagement>
    <!-- 统一jar包版本 -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>12</maven.compiler.source>
        <maven.compiler.target>12</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <lombok.version>1.18.10</lombok.version>
        <log4j.version>1.2.17</log4j.version>
        <mysql.version>5.1.47</mysql.version>
        <druid.version>1.1.16</druid.version>
        <mybatis.spring.boot.version>1.3.0</mybatis.spring.boot.version>
    </properties>


    <!--子模块继承之后，提供作用：锁定版本且子module不用写groupId和version-->
    <dependencyManagement><!--定义规范，但不导入-->
        <dependencies>
            <dependency>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-project-info-reports-plugin</artifactId>
                <version>3.0.0</version>
            </dependency>
            <!--spring boot 2.2.2-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR1-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud 阿里巴巴-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--mysql-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
                <scope>runtime</scope>
            </dependency>
            <!-- druid-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--mybatis-->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.spring.boot.version}</version>
            </dependency>
            <!--junit-->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!--log4j-->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>

        </dependencies>
    </dependencyManagement>

    <build>

        <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
            <plugins>
                <plugin>
                    <artifactId>maven-clean-plugin</artifactId>
                    <version>3.1.0</version>
                </plugin>
                <plugin>
                    <artifactId>maven-site-plugin</artifactId>
                    <version>3.7.1</version>
                </plugin>
                <plugin>
                    <artifactId>maven-project-info-reports-plugin</artifactId>
                    <version>3.0.0</version>
                </plugin>
            </plugins>
        </pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-site-plugin</artifactId>
                <configuration>
                    <locales>en,fr</locales>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <reporting>
        <plugins>
            <plugin>
                <artifactId>maven-project-info-reports-plugin</artifactId>
            </plugin>
        </plugins>
    </reporting>
</project>

```

### b、Eureka

#### 1、前期准备

#####  A、创建cloud-provider-payment8001    支付相关的服务，即服务生产者8001

相关步骤：

- 新建Module，cloud-provider-payment8001
- 修改pom文件
- 新建yml文件
- 新建启动类
- 编写业务类
- 测试

######   ①、pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud-h</artifactId>
        <groupId>com.id01.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>

    <dependencies>

        <!-- spring-boot-starter-web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!-- mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!-- spring-boot-devtools 热部署-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- spring-boot-starter-test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

`devtools热部署插件：简单理解为当开发人员修改了代码，项目会自动加载新改动的代码，无需手动重启项目，具体请自行baidu，步骤如下：`

1. pom.xml中添加依赖（已经添加了）

   ```xml
   <!-- spring-boot-devtools 热部署-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-devtools</artifactId>
       <scope>runtime</scope>
       <optional>true</optional>
   </dependency>
   ```

2. 父工程中pom.xml添加

   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
               <version>2.2.2.RELEASE</version>
               <configuration>
                   <fork>true</fork>
                   <addResources>true</addResources>
               </configuration>
           </plugin>
       </plugins>
   </build>
   ```

3. IDEA中设置

   1. File -> Settings -> Build, Execution, Deployment -> Compiler 中，全部勾选

      ![](https://s1.ax1x.com/2020/07/19/UWSxRe.md.png)

   2. 快捷键 Ctrl+Shift+Alt+/ -> 1.Registry... -> 勾选 compiler.automake.allow.when.app.running 和 actionSystem.assertFocusAccessFromEdt

      ![](https://s1.ax1x.com/2020/07/19/UWSvGD.md.png)

      ![](https://s1.ax1x.com/2020/07/19/UWp9sA.md.png)

######  ②、application.yml

```yaml
server:
  port: 8001

#spring相关
spring:
  application:
    name: cloud-payment-service  #服务名
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource        #当前数据源操作类型
    driver-class-name: org.gjt.mm.mysql.Driver          #mysql驱动包
    url: jdbc:mysql://127.0.0.1:3306/db2019?useUnicode=true&charcterEncoding=utf-8&useSSL=false
    username: root
    password: root
    
#mybatis相关
mybatis:
  mapper-locations: classpath:mapper/*.xml         #指定mybatis.xml文件路径
  type-aliases-package: com.id01.springcloud.entities        #entity所在包
```

###### ③、启动类

```java
/**
 @author 01
 @date 2020/7/8 0008 - 14:25
 */
@SpringBootApplication
@MapperScan(basePackages = "com.id01.springcloud.dao")
public class PaymentMain8001 {
    public static void main(String[] args){
        SpringApplication.run(PaymentMain8001.class);
    }
}
```

###### ④、entity

**CommonResult**

```java
/**
 @author 01
 @date 2020/7/8 0008 - 14:37
 结果返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;

    private String message;

    private T data;

    public CommonResult(Integer code, String message){
        this(code,message,null);
    }

}
```

**Payment**

```java
/**
 @author 01
 @date 2020/7/8 0008 - 14:36
 支付类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
```

`这里使用了lombok，需要先在idea中下载插件，之前pom.xml中也引入了对应的依赖`

###### ⑤、controller

```java
/**
 @author 01
 @date 2020/7/8 0008 - 15:09
 */
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment")
    public CommonResult create(Payment payment){
        return paymentService.create(payment);
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/payment/serverPort")
    public String getServerPort(){
        return serverPort;
    }
}
```

###### ⑥、service

**PaymentService**

```java
/**
 @author 01
 @date 2020/7/8 0008 - 15:02
 */
@Transactional(rollbackFor = Exception.class)
public interface PaymentService {

    CommonResult create(Payment payment);

    CommonResult getPaymentById(Long id);
}
```

**PaymentServiceImpl**

```java
/**
 @author 01
 @date 2020/7/8 0008 - 15:03
 */
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public CommonResult create(Payment payment){
        log.info("端口："+serverPort+"的服务  ====》 插入订单信息 ："+payment);
        int result = paymentMapper.create(payment);
        if(result>0){
            log.info("====》 新建订单信息成功！");
            return new CommonResult(200,"服务: "+serverPort+" , 新建订单信息成功！");
        }else{
            log.info("====》 新建订单信息失败！");
            return new CommonResult(500,"服务："+serverPort+" , 新建订单信息失败！");
        }
    }

    @Override
    public CommonResult getPaymentById(Long id){
        log.info("端口："+serverPort+"的服务  ====》 查询订单信息 ："+id);
        return new CommonResult(200,"服务端口："+serverPort,paymentMapper.getPaymentById(id));
    }

}
```

###### ⑦、mapper(dao)

**PaymentMapper**

```java
/**
 @author 01
 @date 2020/7/8 0008 - 14:43
 */
public interface PaymentMapper {
    int create(Payment payment);

    Payment getPaymentById(@Param("id")Long id);
}
```

###### ⑧、PaymentMapper.xml

resource->mapper下创建PaymentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.id01.springcloud.dao.PaymentMapper">
    <insert id="create" parameterType="Payment" useGeneratedKeys="true" keyProperty="id">
        insert into payment (serial) values(#{serial})
    </insert>
    
    <resultMap id="BaseResultMap" type="com.id01.springcloud.entities.Payment">
        <id column="id" property="id" jdbcType="BIGINT"></id>
        <result column="serial" property="serial" jdbcType="VARCHAR"></result>
    </resultMap>
    
    <select id="getPaymentById" parameterType="Long" resultMap="BaseResultMap">
        select * from payment where id = #{id};
    </select>
</mapper>
```

###### ⑨、测试

启动->测试


##### B、创建cloud-consumer-order80,服务消费者

步骤：相同

###### ①、pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2020</artifactId>
        <groupId>com.id01.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumer-order80</artifactId>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- spring-boot-devtools 热部署-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

</project>
```

###### ②、application.yml

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-order-service
```

###### ③、启动类

```java
/**
 @author 01
 @date 2020/7/8 0008 - 17:30
 */
@SpringBootApplication
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class);
    }
}
```

###### ④、controller

之前需要创建ApplicationContextConfig，由RestTemplate提供REST api调用

```java
/**
 @author 01
 @date 2020/7/8 0008 - 17:44
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * RestTemplate提供http调用
     * REST api 非RPC
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

OrderController

```java
/**
 @author 01
 @date 2020/7/8 0008 - 17:43
 */
@RestController
@Slf4j
public class OrderController {
    /**
     * 服务url
     */
    private static final String PAYMENT_URL = "http://127.0.0.1:8001";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 新建订单
     * @param payment
     * @return
     */
    @PostMapping("/consumer/payment")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment", payment, CommonResult.class);
    }

    /**
     * 查询订单
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/" + id, CommonResult.class);
    }

}
```

`由于entities在两个module中都有，存在冗余，抽取entities包出来，打包install到maven中。再添加依赖。`

**创建module  cloud-commons-api**

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud-h</artifactId>
        <groupId>com.id01.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-commons-api</artifactId>
    
    <dependencies>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.1.12</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
    </dependencies>

</project>
```

把entities包抽取到module：cloud-commons-api中，删除cloud-provider-payment8001、cloud-consumer-order80中的entities包

![image-20200719173755020](https://s1.ax1x.com/2020/07/19/UW38zQ.png)

再使用maven先clean在install到本地仓库中

![image-20200719173539550](https://s1.ax1x.com/2020/07/19/UW3ad0.png)

分别在cloud-provider-payment8001和cloud-consumer-order80的pom.xml文件中添加以下依赖

```xml
<!-- 抽取的公共api -->
<dependency>
    <groupId>com.id01.springcloud</groupId>
    <artifactId>cloud-commons-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

###### ④、测试

这里因为开始我就添加了一些数据，所以直接查询

```txt
http://127.0.0.1/consumer/payment/1
```

返回

```jso
{"code":200,"message":"服务端口：8001","data":{"id":1,"serial":"红楼梦"}}
```
#### 2、引入Eureka组件

##### A、创建 cloud-eureka-server7001  

- 写pom.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <parent>
          <artifactId>cloud-h</artifactId>
          <groupId>com.id01.springcloud</groupId>
          <version>1.0-SNAPSHOT</version>
      </parent>
      <modelVersion>4.0.0</modelVersion>
  
      <artifactId>cloud-eureka-server7001</artifactId>
  
      <dependencies>
  
          <!-- eureka-server -->
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
          </dependency>
  
          <!-- 公共api -->
          <dependency>
              <groupId>com.id01.springcloud</groupId>
              <artifactId>cloud-commons-api</artifactId>
              <version>1.0-SNAPSHOT</version>
          </dependency>
          <!-- boot -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <!-- actuator -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-actuator</artifactId>
          </dependency>
  
          <!--热部署  -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
          </dependency>
          <!-- lombok -->
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
          </dependency>
  
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
          </dependency>
  
      </dependencies>
  </project>
  ```

- 写application.yml

  ```yaml
  server:
    port: 7001
  
  eureka:
    instance:
      hostname: eureka7001.com   #eureka服务端的实例名称
    client:
      #false表示不向注册中心注册自己
      register-with-eureka: false
      #false表示自己就是注册中心，职责就是维护服务实例，并不需要去检索服务
      fetch-registry: false
      service-url:
        #eureka单机时，就是自己
        defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  
  ```

- 主启动类

  ```java
  /**
   @author 01
   @date 2020/7/8 0008 - 22:22
   EnableEurekaServer  表示这个服务就是服务注册中心
   */
  @SpringBootApplication
  @EnableEurekaServer
  public class EurekaMain7001 {
      public static void main(String[] args) {
          SpringApplication.run(EurekaMain7001.class);
      }
  }
  ```

- 测试

  ```txt
  http://127.0.0.1:7001/
  ```

##### **B、cloud-provider-payment8001 改动**

- pom.xml 添加以下依赖

  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```

- application.yml  添加以下配置

  ```yaml
  #eureka相关
  eureka:
    client:
      #把自己给注册到Eureka中
      register-with-eureka: true
      #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合Ribbon使用负载均衡。
      fetch-registry: true
      #提供注册服务的服务器url
      service-url:
        # 单机版
        defaultZone: http://127.0.0.1:7001/eureka/
        
    instance:
      instance-id: payment8001   #修改，使Eureka中的图形界面显示不是主机名
      prefer-ip-address: true 
  ```

- 启动类 改动 增加 注解 @EnableEurekaClient

##### C、cloud-consumer-order80 改动

- pom.xml 添加以下依赖

  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```

- application.yml  添加以下配置

  ```yaml
  #eureka相关
  eureka:
    client:
      #把自己给注册到Eureka中
      register-with-eureka: true
      #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合Ribbon使用负载均衡。
      fetch-registry: true
      #提供注册服务的服务器url
      service-url:
        # 单机版
        defaultZone: http://127.0.0.1:7001/eureka/
        
    instance:
      instance-id: order80   #修改，使Eureka中的图形界面显示不是主机名
      prefer-ip-address: true 
  ```

- 启动类 改动 增加 注解 @EnableEurekaClient

##### D、测试

- 127.0.0.1:7001 看Eureka注册中心中是否有对应服务

  ![](https://s1.ax1x.com/2020/07/19/UfSNT0.png)

##### E、Eureka集群

再建一个cloud-eureka-order7002

- 步骤同上A

- 修改

  `假设cloud-eureka-order7002已经创建好了的基础上。`

  - 修改cloud-eureka-order7001、cloud-eureka-order7002的yml配置

    7001的application.yml

    ```yaml
    server:
      port: 7001
    
    eureka:
      instance:
        hostname: eureka7001.com   #eureka服务端的实例名称
      client:
        #false表示不向注册中心注册自己
        #register-with-eureka: false
        #这里要改为true
        register-with-eureka: true
        #false表示自己就是注册中心，职责就是维护服务实例，并不需要去检索服务
        fetch-registry: false
        service-url:
          #eureka单机时，就是自己
          #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
          #eureka集群时，需要多个eureka互相注册，互相守望。需要改动的配置
          defaultZone: http://eureka7002.com:7002/eureka/
    ```

    7002的application.yml

    ```yaml
    server:
      port: 7002
    
    eureka:
      instance:
        hostname: eureka7002.com   #eureka服务端的实例名称
      client:
        #false表示不向注册中心注册自己
        #register-with-eureka: false
        #这里要改为true
        register-with-eureka: true
        #false表示自己就是注册中心，职责就是维护服务实例，并不需要去检索服务
        fetch-registry: false
        service-url:
          #eureka单机时，就是自己
          #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
          #eureka集群时，需要多个eureka互相注册，互相守望。需要改动的配置
          defaultZone: http://eureka7001.com:7001/eureka/
    ```

  - 修改cloud-provider-payment8001的application.yml

    ```yaml
    server:
      port: 8001
    
    #spring相关
    spring:
      application:
        name: cloud-payment-service  #服务名
      datasource:
        type: com.alibaba.druid.pool.DruidDataSource        #当前数据源操作类型
        driver-class-name: org.gjt.mm.mysql.Driver          #mysql驱动包
        url: jdbc:mysql://127.0.0.1:3306/db2019?useUnicode=true&charcterEncoding=utf-8&useSSL=false
        username: root
        password: root
    
    #mybatis相关
    mybatis:
      mapper-locations: classpath:mapper/*.xml         #指定mybatis.xml文件路径
      type-aliases-package: com.id01.springcloud.entities        #entity所在包
    
    
    #eureka相关
    eureka:
      client:
        #把自己给注册到Eureka中
        register-with-eureka: true
        #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合Ribbon使用负载均衡。
        fetch-registry: true
        #提供注册服务的服务器url
        service-url:
          # 单机版
          #defaultZone: http://127.0.0.1:7001/eureka/
          # 集群后需要注册到多个Eureka中
          defaultZone: http://eureka7002.com:7002/eureka/,http://eureka7001.com:7001/eureka/
      instance:
        instance-id: payment8001   #修改，使Eureka中的图形界面显示不是主机名
        prefer-ip-address: true
    ```

  - 修改cloud-consumer-order80的application.yml

    ```yaml
    server:
      port: 80
    
    spring:
      application:
        name: cloud-order-service
    
    #eureka相关
    eureka:
      client:
        #把自己给注册到Eureka中
        register-with-eureka: true
        #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合Ribbon使用负载均衡。
        fetch-registry: true
        #提供注册服务的服务器url
        service-url:
          # 单机版
          # defaultZone: http://127.0.0.1:7001/eureka/
          # 集群后需要注册到多个Eureka中
          defaultZone: http://eureka7002.com:7002/eureka/,http://eureka7001.com:7001/eureka/
    
      instance:
        instance-id: order80   #修改，使Eureka中的图形界面显示不是主机名
        prefer-ip-address: true
    ```

  - 测试

    - 依次启动7001、7002、8001、80

    - 访问：http://127.0.0.1:7001/

      ![image-20200719192656996](https://s1.ax1x.com/2020/07/19/UfpAhT.png)

##### F、新建cloud-provider-payment8002

- pom.xml

  pom.xml的依赖与cloud-provider-payment8001完全一样

- application.yml

  server.port: 8002

  instance-id: payment8002

  其他与cloud-provider-payment8001完全一样

##### G、修改cloud-consumer-order80

OrderController修改PAYMENT_URL

```java
/**
 @author 01
 @date 2020/7/8 0008 - 17:43
 */
@RestController
@Slf4j
public class OrderController {
    /**
     * 服务url
     */
    //private static final String PAYMENT_URL = "http://127.0.0.1:8001";
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
```

ApplicationContextConfig.getRestTemplate()方法增加注解**@LoadBalanced**

```java
@Configuration
public class ApplicationContextConfig {
    /**
     * RestTemplate提供http调用
     * REST api 非RPC
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

H、测试

`说明：以上模块cloud-provider-payment8001、cloud-provider-payment8002、cloud-eureka-server7001、cloud-eureka-server7002、cloud-consumer-order80主要是想模拟Eureka集群、生产者服务集群的案例。`



​	依次启动：cloud-eureka-server7001、cloud-eureka-server7002、cloud-provider-payment8001、cloud-provider-payment8002、cloud-consumer-order80

​	访问：http://127.0.0.1/consumer/payment/1

​	结果（可能不太明显，这里多次访问的接口，返回的端口会在8001、8002来回切换，@LoadBalance的作用默认是来回交替调用）：

​	![image-20200719223723089](https://s1.ax1x.com/2020/07/19/UfplAx.gif)


##### H、Eureka服务发现 Discovery

`对应注册到Eureka注册中心的微服务，可以通过服务发现来获取服务的相关信息。`

以8001为例

- 启动类上添加@EnableDiscoveryClient

- 修改controller,添加内容

  ```java
  /**
   @author 01
   @date 2020/7/8 0008 - 15:09
   */
  @RestController
  @Slf4j
  public class PaymentController {
      /**
       * 演示服务发现,配合@EnableDiscoveryClient获取微服务相关信息
       */
      @Autowired
      private DiscoveryClient discoveryClient;
  
      /**
       * 演示服务发现,配合@EnableDiscoveryClient获取微服务相关信息
       */
      @GetMapping("/payment/discovery")
      public Object discovery(){
          List<String> services = discoveryClient.getServices();
          log.info("services   =====>   "+services.toString());
          for (String service : services) {
              List<ServiceInstance> instances = discoveryClient.getInstances(service);
              for (ServiceInstance instance : instances) {
                  log.info("detail service info  =====>    "+instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
              }
          }
          return this.discoveryClient;
      }
      
      //省去其他无关内容
  ```

- 访问：http://127.0.0.1:8001/payment/discovery

- 结果：

  ![image-20200720133858346](https://s1.ax1x.com/2020/07/20/UhJXYq.png)

##### I、Eureka的自我保护机制

`场景：当Eureka中注册的微服务死掉了，Eureka不会立刻清理，依旧会在注册中心中保留相关信息。`

Spring Cloud 在CAP中是属于AP

**如何修改配置自我保护机制**

- 服务端yml新增内容如下：

```yaml
  server:
    #关闭自我保护机制
    enable-self-preservation: false
    #超时时间，超过该时间没有监听到服务就注销掉该服务。
    eviction-interval-timer-in-ms: 2000
```

- 客户端yml新增内容如下(instance下一级)：

```yaml
    #eureka客户端向服务端发送心跳时间间隔，单位s,默认是30s
    lease-renewal-interval-in-seconds: 1
    #eureka服务端在收到最后一次心跳后等待时间上限，单位为s，默认90，超时则会注销服务。
    lease-expiration-duration-in-seconds: 2
```

### c、Zookeeper

`在使用Spring Cloud时，也可以使用zookeeper或者consul作为注册中心,我这里使用的docker安装的Zookeeper，具体步骤请自行baidu`

`新建module：cloud-provider-payment8004  作为服务提供者`

1. 写pom.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <parent>
           <artifactId>cloud-h</artifactId>
           <groupId>com.id01.springcloud</groupId>
           <version>1.0-SNAPSHOT</version>
       </parent>
       <modelVersion>4.0.0</modelVersion>
   
       <artifactId>cloud-provider-payment8004</artifactId>
   
       <dependencies>
           <!-- zookeeper-discovery -->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
           </dependency>
   
           <!-- spring-boot-starter-web -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
   
           <!-- actuator -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-actuator</artifactId>
           </dependency>
   
           <!-- spring-boot-devtools 热部署-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
   
           <!-- lombok -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
   
           <!-- spring-boot-starter-test -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
   
           <!-- 抽取的公共api -->
           <dependency>
               <groupId>com.id01.springcloud</groupId>
               <artifactId>cloud-commons-api</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
   
       </dependencies>
   </project>
   ```

2. 写yml

   ```yaml
   server:
     port: 8004
   
   #spring相关
   spring:
     application:
       name: cloud-payment-service  #服务名
   
   # zookeeper相关配置 : 与eureka对比，zookeeper在某个服务挂掉后，默认服务心跳验证超时后，会直接将该服务注销掉。
     cloud:
       zookeeper:
         #zookeeper地址，服务需要注册到zookeeper中。
         connect-string: 你zookeeper所在服务器ip:2181
   
   ```

3. 主启动类

   ```java
   /**
    @author 01
    @date 2020/7/19 0019 - 15:05
    */
   @SpringBootApplication
   @EnableDiscoveryClient
   public class PaymentMain8004 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentMain8004.class);
       }
   }
   
   ```

4. 其他类

   PaymentController

   ```java
   /**
    @author 01
    @date 2020/7/8 0008 - 15:09
    */
   @RestController
   @Slf4j
   public class PaymentController {
   
       @Value("${server.port}")
       private String serverPort;
   
       @GetMapping("/payment/zk")
       public String paymentZK(){
           return "SpringCloud with zookeeper :"+ serverPort + UUID.randomUUID().toString();
       }
   
   }
   ```

   

`新建module：cloud-zk-consumer-order80作为服务消费者`

1. pom.xml

   ```xml
   <dependencies>
   
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
           </dependency>
   
   
           <!-- spring-boot-starter-web -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
   
           <!-- actuator -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-actuator</artifactId>
           </dependency>
   
           <!-- spring-boot-devtools 热部署-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
   
           <!-- lombok -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
   
           <!-- spring-boot-starter-test -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
   
           <!-- 抽取的公共api -->
           <dependency>
               <groupId>com.id01.springcloud</groupId>
               <artifactId>cloud-commons-api</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
   
       </dependencies>
   ```

2. application.yml

   ```yaml
   server:
     port: 80
   
   spring:
     application:
       name: cloud-zk-consumer-payment
     # zookeeper相关配置 : 与eureka对比，zookeeper在某个服务挂掉后，默认服务心跳验证超时后，会直接将该服务注销掉。
     cloud:
       zookeeper:
         #zookeeper地址，服务需要注册到zookeeper中。
         connect-string: 你zookeeper所在主机的ip:2181
   
   ```

3. 启动类

   ```java
   /**
    @author 01
    @date 2020/7/9 0009 - 20:18
    */
   @SpringBootApplication
   @EnableDiscoveryClient
   public class OrderZkMain80 {
       public static void main(String[] args) {
           SpringApplication.run(OrderZkMain80.class);
       }
   }
   ```

4. 其他类

   ApplicationContextConfig

   ```java
   /**
    @author 01
    @date 2020/7/9 0009 - 20:20
    */
   @Configuration
   public class ApplicationContextConfig {
   
       @Bean
       @LoadBalanced
       public RestTemplate getRestTemplate(){
           return new RestTemplate();
       }
   
   }
   ```

   OrderZkController

   ```java
   /**
    @author 01
    @date 2020/7/9 0009 - 20:23
    */
   @RestController
   public class OrderZkController {
   
       public static final String INVOKE_URL = "http://cloud-payment-service";
   
       @Autowired
       private RestTemplate restTemplate;
   
       @GetMapping("/consumer/paymentInfo")
       public String paymentInfo(){
           return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
       }
   }
   ```

`测试`

![image-20200720144716325](https://s1.ax1x.com/2020/07/20/UhD3ND.png)




