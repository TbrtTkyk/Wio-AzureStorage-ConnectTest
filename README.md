# Wio-AzureStorage-ConnectTest
プロメン後期で行うJavaでのWioNodeアプリ-AzureStorage間の連携テスト。

どうしたらできるかを試行錯誤しながら作成。

## 開発目標
Java側でWioNodeからJson形式のセンサー情報を取得し、加工してAzureCloud上に格納する。

Unity側でAzureCloud上のデータを取り出してhololensに投影を行う。

## 制作目的
扱えるデータベース選択でAzureCloudの話が出たので、個人で実装できるか試作。

初めて触るデータベースなので、調べながらの実装。

表形式のデータベースとしてAzure Table Storageを選択したが、Azure SQL Databaseの方がよかったかも。

## 参考元
・『Javaを使用したAzure Storageサンプル | Microsoft Docs』

https://docs.microsoft.com/ja-jp/azure/storage/common/storage-samples-java

・↑のサイトから引っ張った、Github上のSampleソースコード

https://github.com/Azure-Samples/storage-table-java-getting-started/
