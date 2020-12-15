# config
一个简单的基于spring实现的属性动态配置中心，包含前后端管理站点及运行demo

#### 项目组成

* config：读取最新的属性配置信息，并动态修改相应的属性值。
* configManager：动态配置中心管理站点的后端，用户在这里对所有应用的动态属性配置进行管理。
* configManagerWeb：动态配置中心管理站点的前端。
* configTest：测试应用，测试动态配置功能是否运行成功。

![Image text](https://github.com/timmypes/lsconfig/blob/main/images/process.png)

#### 前期准备

1. 需要配置好以下环境：maven、zookeeper、npm、mysql
2. mysql新建数据库"lsconfig",并运行项目中的"lsconfig.sql"文件建表
3. 如果管理站点的前后端都是在本地运行，使用chrome浏览器可能会出现跨域问题，需要在"快捷方式-目标"后面添加"--disable-web-security --user-data-dir=C:\Users\你的用户名\MyChromeDevUserData",并在"C:\Users\你的用户名\"路径下新建名为"MyChromeDevUserData"的文件夹即可。

##### 启动configManager项目：

1. 打开"src\main\resources"文件夹下的application.properties文件：

   `spring.datasource.driverClassName = com.mysql.cj.jdbc.Driver`

   `spring.datasource.url = jdbc:mysql://127.0.0.1:3306/lsconfig?useUnicode=true&characterEncoding=utf-8`

   `spring.datasource.username = root`

   `spring.datasource.password = root`

   `config.zkAddress = 127.0.0.1:2181`

   `mybatis.mapper-locations=classpath:mappers/*.xml`

   `server.port=8080`

2. 修改"spring.datasource.url","spring.datasource.username","spring.datasource.password"为你的mysql地址。用户名及密码。

3. 修改"config.zkAddress"的地址为你部署的zk地址。

4. 启动该springboot项目。

##### 启动configManagerWeb项目：

1. 打开文件"src\constant.ts"文件，修改`SERVER_ADDRESS`为configManager项目的地址

2. 在项目根目录下执行"npm install"后，启动该react项目

##### config项目：

​	根目录下执行"mvn clean install"命令对项目进行打包

##### configTest:

1. 打开application.properties文件，同第一步一样，修改mysql和zk的地址，并设置应用在配置中心的名称config.appName = testApp

2. 打开TestController文件，里面有4种不同类型的变量，都被@Conf注解，注解内的name属性值标识了该属性在配置中心的名称，可以任意命名。test方法返回了这4种属性的值。后面将通过调用该方法，来查看属性值是否修改成功。 

3. 执行"mvn clean install"命令后启动项目

#### 功能测试

1. 调用configTest的test方法: 在浏览器中输入"http://localhost:8080/test" ，返回报文时"{"prop2":null,"prop1":null,"prop4":null,"prop3":null}"，说明这4个属性没有被赋值。

2. 浏览器输入"http://localhost:3000/" 进入配置中心管理站点，点击添加属性，分别给4个属性设置对应的值：

   ![Image text](https://github.com/timmypes/lsconfig/blob/main/images/addProperty.png)

3. 再次请求"http://localhost:8080/test" 接口，报文中返回了每个属性对应的属性值，则测试成功。
