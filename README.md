# YouBikeSystem
# 功能
這是OOPL的指定題，實作了以下多項功能:
1. 會員註冊/登入
2. 站位查詢
3. 租還車紀錄查詢
4. 悠遊卡儲值
5. 租車
6. 還車
7. 維修通報

# 資料夾目錄
- YouBikeSystemBackend : 後端資料夾
- YouBikeSystemFrontend :　前端資料夾
- README.md
- docker-compose.yaml :　docker設定mysql、apacheds
- mysql.cnf : mysql設定檔

# 套件
- 後端：
  -  Java 17： 主要的開發程式語言。
  -  Spring Boot 3：主要開發框架。
  -  Maven：用於管理及配置依賴。
  -  JWT/Spring Security：用於生成token，實現身份驗證機制。
  -  Spring Framework LdapTemplate：進行SSO登錄功能。
  -  Spring Data JPA(底層仍是用JDBC實現)與部分SQL指令: 進行DB的CRUD。
  
- 前端： (可在JavaFX的Webview上啟動)
  - HTML 和 JavaScript：用於開發網頁操作介面。
  - CSS：用於網頁版面設計。

- 維運 :
  - Docker : 使用docker compose 進行MySQL資料庫、ApachDS(LDAP)的設定與管理。
  - MySQL：關聯式資料庫。
  - ApacheDS: 目錄伺服器。

