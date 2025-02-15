# domain

서비스의 도메인들이 존재하는 곳이다. 도메인의 정의와 규칙 및 행위들이 존재한다.

도메인과 직접적으로 관련된 Entity와 Repository가 존재한다.

Repository는 인터페이스 형태로 존재하며 구현은 infrastructure 패키지에서 담당한다.

즉, domain 패키지에서는 도메인을 정의하며 기술적인 구현에 의존 받지 않는다.