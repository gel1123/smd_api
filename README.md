## SpringBoot + MySQL + Docker で APIサーバを構築する

### TIPS

#### SpringBoot初期セッティング
spring initializer を使用して生成したzipを展開して、apiディレクトリ以下を丸ごと spring_prj に配置する。
（これでapiディレクトリはいらなくなるので、以降は削除して問題ない）

なお念の為、展開前zip を init_spring_prj.zip として保存している。
https://start.spring.io/

- Project: Gradle Project
- Language: Java
- SpringBoot: 3.0.0 (M3)
- Project Metadata
  - Group: com.smd
  - Artifact: api
  - Name: api
  - Description: SpringBoot For API Server
  - Package name: com.smd.api
  - Packaging: Jar
  - Java: 17

#### Open JDK17 で gradlew 実行時に `gradlew: line 234: xargs: command not found` が発生する

Javaのコンテナで `microdnf install findutils` を実行すれば xargs をインストールできる。
（ microdnf は、パッケージマネージャ yum の後継である dnf の軽量版 ）

https://github.com/gradle/gradle/issues/19682

