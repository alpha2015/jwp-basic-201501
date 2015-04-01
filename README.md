#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 
웹 어플리케이션 초기화 과정

1. 서블릿 컨테이너(이하 톰캣)은 Application 전체에서 어디서나 사용할수 있도록 전역변수로 설정한 ServletContext 인스턴를 생성하는데 이는 웹 플리케이션 초기화중에 한번만 수행되며, 전체 애플리케이션에서 정보 공유가 가능하고 유일한 객체이다.
   -. Servlet Context는 web.xml에서 모든 Servlet mapping 정보를 인자로 컨테이너에 의해 생성된다.

2. 컨테이너는 시작과 종료시(context가 유일해서 생성, 소멸시라고도 볼수 있을까??)에 이벤트를 발생시키는데, 이를통해 ServletContextListener를 상속하는 Listener들은 원하는 작업을 수행할 수있다.
   -. 이벤트가 발생했을때 재정의된 contextInitialized() 메소드가 자동으로 호출된다.
   -. 이 프로젝트에서는 ContextLoaderListener가 존재하며 ServletContextListener를 상속하고 있다.(DB 초기화 작업)

3. 혹시라도 servlet context 인스턴스 생성할때 필요했던 servlet mapping정보를에 속한 서블릿들 중 load-on-startup이 양수로 설정되어있는 servlet이 존재한다면 해당 서블릿의 init() 메소드가 먼저 실행이 된다.
   -. servlet()의 init() 메소드는 원래 첫 접근이 있을때 한번 호출되며, 그 이후로 접근시마다 service() 메소가 호출됨.


#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 
일반적인 url( http://localhost:8080 ) 접근 호출 흐름

1. index.jsp로 접근한다.

2. response.sendRedirect("/list.next");에 의해 annotation에 의해 mapping된 서블릿 클래스를 찾는다.

3. 만약, 해당 서블릿 인스턴스가 없다면 (최초 접근하는 시점) 서블릿 클래스를 로드하고 인스턴스를 생성한다. 이때 처음 init() 메소드가 호출되고 service()를 호출한다. 서버 시작 후 최초 접속이 아니라면 이미 존재하는 해당 서블릿 인스턴스에 service() 메소드를 호출한다.
   -. 이때 service() 메소드에 request와 response 객체를 인자로 전달한다. 

4. 




서블릿 인스턴스가 존재하지 않는다면..
클래스 로더는 서블릿 클래스를 로드한다.
서블릿 클래스의 인스턴스를 생성한다.
서블릿 클래스의 init() 메써드를 호출함으로서 초기화를 진행한다. 서블릿의 초기화 과정에 대해서는 Initializing a Servlet 문서를 참고하기 바란다.
서블릿 인스턴스가 존재한다면..
서블릿의 service() 메써드에 request, response 객체를 전달한다. 서블릿의 service() 메써드에 대해서는 Writing Service Methods 문서를 참고하기 바란다.


#### 8. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 

