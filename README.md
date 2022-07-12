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

#### Open JDK17 で `./gradlew build` 実行時に `gradlew: line 234: xargs: command not found` が発生する

Javaのコンテナで `microdnf install findutils` を実行すれば xargs をインストールできる。
（ microdnf は、パッケージマネージャ yum の後継である dnf の軽量版 ）

https://github.com/gradle/gradle/issues/19682

#### devcontainer.json の extensions で指定したVSCode拡張機能がインストールされない

extensions はコンテナ作成時に動作する。
つまり、コンテナ作成をVSCode経由ではなく、コマンド `docker-compose up` でコンテナを作成した場合には、その後 VSCode 経由でコンテナに入っても、拡張機能がインストールされていないままになる。

この状態から拡張機能を自動でインストールさせたいなら、一度コンテナを削除した上で、VSCodeの `Rebuild Container` を実行する。

#### VSCodeを使わずにコンテナに入る方法

```
# コンテナ起動
docker-compose up -d

# javaコンテナの中に入る
docker exec -it java /bin/bash
```

#### SpringBoot アプリケーションをビルドする

```
./gradlew build
```

#### Spring Boot アプリケーションを 起動 / 停止 したい

```
# 起動
java -jar build/libs/api-0.0.1-SNAPSHOT.jar

# 停止
java -jar build/libs/api-0.0.1-SNAPSHOT.jar stop
```
