# Spring Cloud Notes

## 一、Forward

### a、一些话

本文是结合atguigu Spring Cloud 2020教学视频的学习笔记，仅供自己学习或者其他道友参考，如有错误之处还望指正，请不要无脑喷，有错就改嘛，没啥大不了的，大家一同进步。

### b、版本

| 技术         | version       |
| ------------ | ------------- |
| Spring Cloud | Hoxton.SR1    |
| Spring Boot  | 2.2.2.RELEASE |

==ps:由于时间精力有限，先整理了Spring Cloud部分，Spring Cloud Alibaba部分后续会补上。==



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

==devtools热部署插件：简单理解为当开发人员修改了代码，项目会自动加载新改动的代码，无需手动重启项目，具体请自行baidu，步骤如下：==

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

==这里使用了lombok，需要先在idea中下载插件，之前pom.xml中也引入了对应的依赖==

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






