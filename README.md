## SpringBoot + MySQL + Docker で APIサーバを構築する

### これは何？
基本的には、こちらの記事に記載の SpringBoot + MySQL + Docker 環境のコードです。
https://zenn.dev/nishiharu/articles/7f27b8c580f896

ただ、下記の点などいくつか記事のコードから改変した部分があります。

+ Javaのバージョンを15から17に引き上げた
+ 上記に伴い、Javaコンテナを用意するときにxargsインストールのためのコマンド実行処理が必要になったので、`openjdk:17` 用の Dockerfile を用意し、そこからビルドを行うよう変更した
+ SpringBootのバージョンを 3.0.0 (M3) に引き上げた
+ リポジトリの名前に沿ったSpringBootプロジェクト名にしてあるので、SpringBootプロジェクトのパッケージ名が異なる
+ Debug構成を `ApiApplicatioin.java` からVSCodeで直接開けるよう追加

### 始め方
このリポジトリをpullしたらVSCodeで F1 のメニューから下記を実行してください。

```
Remote Containers: Rebuild and Reopen Container
```

するとコンテナが作成され、Java開発に必要なVSCode拡張機能がインストールされます。 
（`.devcontainer/devcontainer.json` の `extensions` でコンテナ作成時の拡張機能が定義されています）

以降は通常通り、 `Remote Containers: Reopen in Container` でコンテナに入れます。

コンテナに入る際、どのコンテナに入るべきか問われた場合には `java` コンテナを選んでください。
（他のコンテナは中に入って開発する用途ではないため）

### 概要

docker-compose で 3つのコンテナを定義しています。

#### 1. MySQLコンテナ
文字通りMySQLのコンテナです。
コンテナ作成時に、初期データとして `docker/mysql/sql_init` 配下のSQLを実行しています。

#### 2. PhpMyAdminコンテナ
PhpMyAdminという比較的歴史の長いGUIのDBクライアントを入れています。
今現在、MySQLコンテナ内のデータがどのようになっているのかをブラウザから確認できます。

コンテナ起動後、 http://localhost:4200 でアクセスできます。

#### 3. Javaコンテナ
SpringBootの実行環境であり開発環境でもあるコンテナです。
`spring_prj` 配下の資材がコンテナ内の `/app` ディレクトリ配下に展開されます。
開発時にはVSCodeのRemoteContainerでJavaコンテナに入って、コードを書くのをおすすめします。
（デバッグ環境なども整っているので）

RemoteContainerで初めてVSCodeからコンテナに入ったとき、
どのディレクトリでVSCodeを開くか尋ねられるかもしれません。
その時は、/app ディレクトリで開いてください。

また、初めてコンテナに入った時はコードがビルドされておらず、
JavaScriptで言うところの「npm install」していない状態であるため、
必要なライブラリが入っておらず、各所にエラーが出ます。

そのためコンテナに初めて入った時にはまず、 `/app` ディレクトリ直下で下記コードを実行してください。

```
# ./XXXX は LinuxやMacなどのCUIにおける「スクリプトを実行」する際の書き方。
# sh XXXX と概ね同じであるため、下記は sh gradlew でもOK。
./gradlew build
```

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
