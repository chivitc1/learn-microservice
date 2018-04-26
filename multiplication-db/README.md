# multiplication database
Postgres

## Directory structure

`` `
multiplication-db /
| - Dockerfile # Build a container image for postgres Dockerfile
| - install # When executed, it will expand to /usr/local/multiplication-db/
| - conf # Configuration file referenced during install
| - files / # Files to be placed in the Docker container
`- script / # Script storage space for manipulating the Docker container
`` `

## scriptの使用方法

script/以下にコンテナ操作用の各種スクリプトを設置しています。

* build

    コンテナをビルドします。
    公式のレジストリからpostgresイメージを取得して、それをベースにビルドを行います。

* run

    コンテナを起動します。

* service

    コンテナをswarmモードで起動します。

* login

    runで起動したイメージにログインします。
    serviceで起動したイメージにはログインできません。

* conf

    ベースイメージの指定やコンテナ名の指定などを設定します。

## インストール、コンテナ起動方法

```
$ ./install
$ cd /usr/local/spiral/service/user-database/script/
$ ./build
$ ./run
```

