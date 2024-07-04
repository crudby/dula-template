## DULA-TEMPLATE 单体服务开发模板

### 写在前面
单体架构更适用于小型系统和个人开发项目，不需要拆分多个服务，也不用在乎服务间通信方式。去掉服务注册发现中心，配置中心，减少需要维护的一些中间件。
DUAL-TEMPLATE(以下简称dula)就是基于这样的需要下开发的单体应用开发脚手架，模板代码中已完成用户登录权限管理的接口开发，可以直接对接前端使用。


### 快速开始
#### 环境依赖：
- jdk11
- maven
- mysql8.0x 
- redis

#### 组件&技术

##### redis
系统采用了redis来做会话管理，考虑后续系统如果有集群部署需求更加方便，如果不想用redis也可以用系统内存，在配置里切换即可。

##### mysql
这里采用的是mysql8.0x，因为sql脚本的维护使用了**flyway**，使用的当前最新版本(8.5.13)。如果有数据库版本要求可以根据需求调整。

##### spring-security + jwt
既然是spring boot项目，干脆就直接用security了，自定义认证，然后去支持多端，代码挺简单，看了就会，不了解的也可以看了去搜一下

##### mybatis-flex
这里的orm框架是用的[mybatis-flex](https://mybatis-flex.com/),使用mybatis3原生支持的**Provider**,代码清晰简单，体验感还是不错的;
然后基于配套的代码生成器也做了一些配置，也可以按需求自定义，这个在结构里会说一下

##### caffeine
用Caffeine做了个本地缓存`LocalCache`，可能偶尔会用得上

##### 其他
好像没什么值得介绍的，没几个组件还是很通用的。。

#### 代码结构
```text
├─java
│  └─com
│      └─crud
│          └─dula
│              ├─common  // 通用基础包  包含公共类或工具类
│              │  ├─base  // 包含使用的基础包，这一套用起来代码逻辑清晰、拆分也清除
│              │  ├─cache // 缓存
│              │  ├─dict  // 字典
│              │  ├─gen // 代码生成配置类
│              │  ├─id  // 使用了雪花算法生成id
│              │  ├─jwt // jwt的工具类
│              │  ├─result // 接口返回通用处理
│              │  ├─spi // spring-spi工具类
│              │  ├─thread // 自定义线程池
│              │  ├─upload // 文件上传，现在是简单的保存到本地磁盘，可以自定义集成第三方如minio
│              │  └─utils // 一些工具类
│              ├─platform  // 系统管理包 包含系统的用户等信息接口
│              │  ├─config // 一些配置类
│              │  ├─constant  // 常量
│              │  ├─dto // query、dto、vo、entity拆分为标准规范
│              │  ├─email // 邮件通知，不用可以删掉或注释
│              │  ├─entity // query、dto、vo、entity拆分为标准规范
│              │  │  └─table
│              │  ├─mapper // mapper层
│              │  ├─query // query、dto、vo、entity拆分为标准规范
│              │  ├─service // service层
│              │  │  └─impl
│              │  ├─support // 介于service和controller的操作类
│              │  ├─vo // query、dto、vo、entity拆分为标准规范
│              │  └─web // controller层
│              └─security  // 安全配置认证
│                  ├─authenticate // 权限的细致控制
│                  ├─exception // 权限相关异常
│                  ├─filter // 认证过滤
│                  ├─provider // 第三方认证
│                  ├─service // 登录业务层
│                  │  └─impl
│                  ├─session // 会话管理
│                  ├─support // 业务层补充
│                  └─third // 第三方认证
└─resources
    ├─db
    │  └─migration  // flyway的脚本控制文件
    └─gen  // 代码生成的模板文件
```

